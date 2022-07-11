package com.cloud.cang.api.sl.service.impl;

import com.cloud.cang.api.common.ReturnCodeEnum;
import com.cloud.cang.api.common.utils.LogUtils;
import com.cloud.cang.api.common.utils.MsgToJsonUtils;
import com.cloud.cang.api.fr.dao.FaceOperLogDao;
import com.cloud.cang.api.fr.dao.FaceRegisterInfoDao;
import com.cloud.cang.api.hy.dao.MemberInfoDao;
import com.cloud.cang.api.netty.vo.ClientVo;
import com.cloud.cang.api.netty.vo.aiFace.FaceDeviceVo;

import com.cloud.cang.api.om.dao.OrderRecordDao;
import com.cloud.cang.api.sb.dao.AiInfoDao;
import com.cloud.cang.api.sb.dao.DeviceInfoDao;
import com.cloud.cang.api.sb.dao.DeviceRegisterDao;
import com.cloud.cang.api.singleton.SingletonClientMap;
import com.cloud.cang.api.sl.dao.LoginLogDao;
import com.cloud.cang.api.sl.service.LoginLogService;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.common.TypeConstant;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.NettyConst;
import com.cloud.cang.core.common.aiFace.ResponseAip;
import com.cloud.cang.core.common.aiFace.UserDomain;
import com.cloud.cang.core.common.utils.AiFaceUtils;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.fr.FaceRegisterInfo;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.model.sb.AiInfo;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sb.DeviceRegister;
import com.cloud.cang.model.sl.LoginLog;
import com.cloud.cang.utils.DateUtils;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

@Service
public class LoginLogServiceImpl extends GenericServiceImpl<LoginLog, String> implements
		LoginLogService {

	private static final Logger logger = LoggerFactory.getLogger(LoginLogServiceImpl.class);

	@Autowired
	ICached iCached;

	@Autowired
	LoginLogDao loginLogDao;

	@Autowired
	AiInfoDao aiInfoDao;

	@Autowired
	DeviceRegisterDao deviceRegisterDao;

	@Autowired
	FaceOperLogDao faceOperLogDao;

	@Autowired
	MemberInfoDao memberInfoDao;

	@Autowired
	DeviceInfoDao deviceInfoDao;

	@Autowired
	FaceRegisterInfoDao faceRegisterInfoDao;

	@Autowired
	OrderRecordDao orderRecordDao;


	private static ConcurrentMap<String, ChannelHandlerContext> ctxMap = SingletonClientMap.newInstance().getCtxMap();  //netty通道

	
	@Override
	public GenericDao<LoginLog, String> getDao() {
		return loginLogDao;
	}


	/**
	 * 刷脸登录
	 *
	 * @param aiId         设备ID
	 * @param key          通信密钥
	 * @param base64String 图片字符串
	 * @param userIds      用户ID集合
	 * @param ip           访问IP地址
	 * @return
	 */
	@Override
	public ResponseVo<String> faceLogin(String aiId, String key, String base64String, String userIds, String ip) {
		ResponseVo responseVo = new ResponseVo(false, ErrorCodeEnum.ERROR_COMMON_SYSTEM.getCode(), "系统错误");
		// 校验设备是否正常
		List<DeviceRegister> deviceRegisterList = selectRegisterDevice(aiId, key);
		if (CollectionUtils.isNotEmpty(deviceRegisterList)) {
			DeviceRegister deviceRegister = deviceRegisterList.get(0);
			if (deviceRegister.getIstatus() == 40) { //10 待审核 20  审核通过  30 审核拒绝 40 已注册
				AiInfo aiInfo = aiInfoDao.selectByPrimaryKey(aiId);
				if (null != aiInfo && 20 == aiInfo.getIstatus()) { //  10=未注册 20=正常 30=故障 40=报废 50=离线
					String[] userIdArr = userIds.split(",");
					ResponseAip responseAip = null;
					//0.调用百度API对比用户,返回查询结果
					if (userIdArr.length == 1) {
						responseAip = AiFaceUtils.findUser(base64String, userIdArr[0]);
					} else {
						responseAip = AiFaceUtils.findUser(base64String);
					}

					// 1.处理百度返回的人脸库对比结果
					if (null == responseAip) {
						responseVo.setMsg("调用百度人脸识别Api出错");
						return responseVo;
					}
					if (0 != responseAip.getError_code() || !("SUCCESS".equals(responseAip.getError_msg()))) {    //未找到对应用户
						responseVo.setMsg("人脸库未找到匹配用户");
						return responseVo;
					}

					// 2.从人脸库返回的匹配用户集中，获取匹配度最高的用户ID
					List<UserDomain> userDomainList = responseAip.getResult().getUser_list();
					String userId = findBestMatch(userDomainList);
					if (StringUtils.isBlank(userId)) {
						return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("用户不匹配");
					}

					// 3.0判断用户状态是否正常
					MemberInfo memberInfo = memberInfoDao.selectByPrimaryKey(userId);
					if (null == memberInfo || 1 != memberInfo.getIstatus() || 1 == memberInfo.getIdelFlag()) {
						responseVo.setMsg("用户状态非法");
						return responseVo;
					}

					// 3.1是否含有未支付订单,未支付订单不能开门
					List<OrderRecord> orderRecords = orderRecordDao.selectExceptionOrder(userId);
					if (null != orderRecords && orderRecords.size() > 0) {
						responseVo.setMsg("有未支付订单，请处理后再次尝试开门");
						return responseVo;
					}

					// 4.发送开门消息
					ResponseVo responseVo1 = sendOpenDoorMsg(aiId, userId);
					String deviceId = (String) responseVo1.getData();
					if (BooleanUtils.isFalse(responseVo1.isSuccess())) {
						logger.error("人脸识别AI设备ID：{},发送开门消息失败，设备ID：{}", aiId, deviceId);
						return responseVo1;
					}

					// 记录人脸识别相关设备信息
					recordFaceDeviceVo(aiId, ip, deviceId, userId);

					// 5.记录登录日志
					insertFaceLoginLog(aiInfo, memberInfo, deviceId, ip);

					// 6.记录操作日志
					LogUtils.insertFaceOperLog(deviceId, memberInfo.getId(), 20, base64String, "人脸登录成功", 1, ip, 10);

					return new ResponseVo<>(true, 200, "刷脸登录成功", "刷脸登录成功");

				}
			}
			responseVo.setMsg("人脸识别设备故障");
		}

		return responseVo;
	}

	/**
	 * 记录AI人脸设备基础信息
	 * @param aiId
	 * @param ip
	 * @param deviceId
	 * @param userId
	 */
	private void recordFaceDeviceVo(String aiId, String ip, String deviceId, String userId) {

		ExecutorManager.getInstance().execute(() -> {
			DeviceInfo deviceInfo = deviceInfoDao.selectByPrimaryKey(deviceId);		//	售货机基础信息
			AiInfo aiInfo = aiInfoDao.selectByPrimaryKey(aiId);						//	AI设备基础信息
			FaceRegisterInfo faceRegisterInfoVo = new FaceRegisterInfo();
			faceRegisterInfoVo.setSmemberId(userId);
			faceRegisterInfoVo.setSmerchantId(aiInfo.getSmerchantId());
			FaceRegisterInfo faceRegisterInfo = faceRegisterInfoDao.selectUserFaceRegisterDomain(faceRegisterInfoVo);	//查询人脸注册信息

			FaceDeviceVo faceDeviceVo = new FaceDeviceVo();
			faceDeviceVo.setAiId(aiId);
			faceDeviceVo.setDeviceId(deviceId);
			faceDeviceVo.setIp(ip);			// 登录IP
			faceDeviceVo.setOpenDoorUserId(userId); // 开门用户ID
			if (null != aiInfo) {
				faceDeviceVo.setAiCode(aiInfo.getSaiCode());
			}
			if (null != deviceInfo) {
				faceDeviceVo.setDeviceCode(deviceInfo.getScode());
			}
			if (null != faceRegisterInfo) {
				faceDeviceVo.setOpenDoorPayType(faceRegisterInfo.getIbindPayType()); // 开门用户使用的支付方式 10 ：微信支付 20 ：支付宝 30：其他
			}

			try {
				iCached.put(NettyConst.FACE_DEVICE_VO + deviceId, faceDeviceVo);
			} catch (Exception e) {
				logger.error("向Redis中存储人脸识别基础信息出现异常，AI设备ID：{},售货机设备ID：{}", aiId, deviceId);
			}
		});
	}


	/**
	 * 人脸登录记录日志
	 *
	 * @param aiInfo     AI设备
	 * @param memberInfo 会员信息
	 * @param deviceId   售货机设备ID
	 * @param ip         http访问IP地址
	 */
	private void insertFaceLoginLog(AiInfo aiInfo, MemberInfo memberInfo, String deviceId, String ip) {
		ExecutorManager.getInstance().execute(() -> {
			DeviceInfo deviceInfo = deviceInfoDao.selectByPrimaryKey(deviceId);
			LoginLog loginLog = new LoginLog();
			loginLog.setSmemberId(memberInfo.getId());                //会员ID
			loginLog.setSmemberName(memberInfo.getSmemberName());    //会员名
			loginLog.setSuserCode(memberInfo.getScode());            // 会员编号
			loginLog.setSuserRealname(memberInfo.getSrealName());    // 会员真实姓名
			loginLog.setItype(40); // 登录类型 10：授权登录 20：账号登录 30：SSO登录 40:人脸识别登录
			loginLog.setIdeviceType(60); // 10：微信 20：支付宝 30：APP	40：WAP站 50：PC站 60：人脸识别设备
			loginLog.setSip(ip);    // 访问IP地址
			loginLog.setSaiCode(aiInfo.getSaiCode());        // AI设备编号
			loginLog.setCountry("中国");                    // 国家
			if (null != deviceInfo) {
				loginLog.setSprovince(deviceInfo.getSprovinceName());    // 开户省份
				loginLog.setScity(deviceInfo.getScityName());            // 开户城市
			}
			loginLog.setTloginTime(DateUtils.getCurrentDateTime());        // 登录时间
			loginLogDao.insert(loginLog);
		});
	}

	/**
	 * 向售货机发送开门消息
	 *
	 * @param aiId   人脸设备ID
	 * @param userId 用户ID
	 */
	private ResponseVo<String> sendOpenDoorMsg(String aiId, String userId) {
		logger.debug("准备向用户：{}发送设备开门消息", userId);
		ResponseVo<String> responseVo = new ResponseVo<>(false, ErrorCodeEnum.ERROR_COMMON_SYSTEM.getCode(), "售货机设备故障");
		DeviceInfo deviceInfo = null;
		String deviceId = "";
		DeviceInfo deviceInfoVo = new DeviceInfo();
		deviceInfoVo.setSaiId(aiId);
		deviceInfoVo.setIdelFlag(0);
		deviceInfoVo.setIstatus(20);// 状态：10=未注册 20=正常 30=故障 40=报废50=离线
		deviceInfoVo.setIoperateStatus(10); // 10=启用 20=停用
		List<DeviceInfo> deviceInfoList = deviceInfoDao.selectByEntityWhere(deviceInfoVo);
		if (CollectionUtils.isNotEmpty(deviceInfoList)) {
			deviceInfo = deviceInfoList.get(0);
			deviceId = deviceInfo.getId();
			//先判断门是否已经打开
			ClientVo clientVo = null;   //从redis中获取设备相关信息
			try {
				clientVo = (ClientVo) iCached.hget(NettyConst.SYN_CLIENT_MAP, deviceId);
			} catch (Exception e) {
				logger.error("人脸登录时，从redis中获取设备相关信息出现异常");
			}
			Integer doorStatus = null;
			if (null != clientVo) {
				doorStatus = clientVo.getDoor();    //门的状态 10关，20开
			}

			//门处于打开状态
			if (null != doorStatus && Integer.valueOf(20).equals(doorStatus)) {
				logger.debug("设备门已经打开");
				return new ResponseVo<>(false, ReturnCodeEnum.ERROR_DOOR_OPEN.getCode(), "设备门已经打开");
			}

			ChannelHandlerContext ctx = ctxMap.get(deviceId);
			//设备未连接到服务器，处于离线状态
			if (null == ctx) {
				logger.error("人脸登录时，设备离线，设备编号:{}", deviceInfo.getScode());
				return new ResponseVo<>(false, ReturnCodeEnum.ERROR_DEVICE_OFFLINE.getCode(), "设备离线");
			}

			//拼装消息体 = 用户ID，设备ID，操作类型，10顾客/20补货员/30装机员
			MsgToJsonUtils.asynSendMsgByCtx(deviceId, ctx, true, 10, userId, TypeConstant.OPEN_DOOR, 10);

			logger.debug("用户ID：{}，发送设备开门消息成功，设备编号：{}", userId, deviceInfo.getScode());
			return ResponseVo.getSuccessResponse(deviceId);
		}

		return responseVo;

	}

	/**
	 * 查找最匹配的人
	 *
	 * @param userDomainList 百度返回人脸库对比结果
	 * @return 返回最匹配用户ID
	 */
	private String findBestMatch(List<UserDomain> userDomainList) {
		if (CollectionUtils.isNotEmpty(userDomainList)) {

			// 循环比较取最高分
			Float tempScore = new Float(0);
			String userId = "";
			for (UserDomain u : userDomainList) {
				if (tempScore.compareTo(u.getScore()) == -1) {
					tempScore = u.getScore();
					userId = u.getUser_id();
				}
			}
			if (tempScore.compareTo(new Float(80)) == 1) {    // 阈值设置为80分
				return userId;
			}
		}

		return "";
	}

	private List<DeviceRegister> selectRegisterDevice(String deviceId, String key) {
		DeviceRegister deviceRegister = new DeviceRegister();
		deviceRegister.setSdeviceId(deviceId);
		deviceRegister.setSsecurityKey(key);
		return deviceRegisterDao.selectByEntityWhere(deviceRegister);
	}


}