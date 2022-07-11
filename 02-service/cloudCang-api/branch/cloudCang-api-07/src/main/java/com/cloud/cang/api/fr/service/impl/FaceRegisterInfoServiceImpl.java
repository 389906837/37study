package com.cloud.cang.api.fr.service.impl;

import com.cloud.cang.api.common.utils.IPUtils;
import com.cloud.cang.api.common.utils.LogUtils;
import com.cloud.cang.api.fr.dao.FaceRegisterInfoDao;
import com.cloud.cang.api.fr.service.FaceRegisterInfoService;
import com.cloud.cang.api.hy.dao.MemberInfoDao;
import com.cloud.cang.api.sb.dao.AiInfoDao;
import com.cloud.cang.api.sb.dao.DeviceInfoDao;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.NettyConst;
import com.cloud.cang.core.common.aiFace.Location;
import com.cloud.cang.core.common.aiFace.ResponseAip;
import com.cloud.cang.core.common.aiFace.Result;
import com.cloud.cang.core.common.aiFace.UserDomain;
import com.cloud.cang.core.common.utils.AiFaceUtils;
import com.cloud.cang.core.common.utils.IdGenerator;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.core.utils.FtpParamUtil;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.core.utils.SysParaUtil;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.fr.FaceRegisterInfo;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.sb.AiInfo;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.FtpUtils;
import com.cloud.cang.zookeeper.utils.EvnUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

@Service
public class FaceRegisterInfoServiceImpl extends GenericServiceImpl<FaceRegisterInfo, String> implements FaceRegisterInfoService {

	private static final Logger logger = LoggerFactory.getLogger(FaceRegisterInfoServiceImpl.class);

	@Autowired
	private ICached iCached;

	@Autowired
	private FaceRegisterInfoDao faceRegisterInfoDao;	// 人脸注册

	@Autowired
	private MemberInfoDao memberInfoDao;	// 会员信息表

	@Autowired
	private AiInfoDao aiInfoDao;	// 人脸识别信息表

	@Autowired
	private DeviceInfoDao deviceInfoDao;	// 售货机设备基础信息表


	@Override
	public GenericDao<FaceRegisterInfo, String> getDao() {
		return faceRegisterInfoDao;
	}


	/**
	 * 用户人脸信息注册
	 *
	 * @param deviceId     设备ID
	 * @param key          通信密钥
	 * @param userId       用户ID
	 * @param payType		支付类型
	 * @param base64String 用户图片字符串
	 * @param request      请求
	 * @return
	 */
	@Override
	public ResponseVo<String> faceRegister(String deviceId, String key, String userId, Integer payType, String base64String, HttpServletRequest request) {

		//校验用户是否存在，是否已经注册过人脸信息，
		MemberInfo memberInfo = memberInfoDao.selectByPrimaryKey(userId);
		if (null == memberInfo) {
			logger.error("用户ID：{}，不存在");
			return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("用户不存在");
		}
		if (null != memberInfo.getIisRegFace() && 1 == memberInfo.getIisRegFace()) {
			logger.error("用户ID：{}，已经注册过，不能重复注册");
			return new ResponseVo<String>(false, 30002, "该账号已经注册过人脸识别信息，请勿重复注册");
		}
		// 调用百度人脸识别API查询该人脸是否已经注册过，人脸只能绑定一个账号
		ResponseAip responseAipDomain = AiFaceUtils.findUser(base64String);
		if ("SUCCESS".equals(responseAipDomain.getError_msg())) {
			List<UserDomain> userDomainList = responseAipDomain.getResult().getUser_list();
			if (CollectionUtils.isNotEmpty(userDomainList)) {
				Float tempScore = new Float(0);
				String userIdTemp = "";
				for (UserDomain u : userDomainList) {
					// 循环比较取最高分
					if (tempScore.compareTo(u.getScore()) == -1) {
						tempScore = u.getScore();
						userIdTemp = u.getUser_id();
					}
					if (tempScore.compareTo(new Float(80)) == 1 && StringUtils.isNotBlank(userIdTemp)) {    // 阈值设置为80分
						logger.error("用户已经使用一个账号注册过人脸信息，只能绑定一个账号");
						return new ResponseVo<String>(false, 30001, "用户已经使用一个账号注册过人脸信息，只能绑定一个账号");
					}
				}
			}
		}

		// 调用百度人脸识别API注册人脸信息
		ResponseAip responseAip = AiFaceUtils.register(userId, base64String);
		String ip = IPUtils.getIpFromRequest(request);
		if ("SUCCESS".equals(responseAip.getError_msg()) && 0 == responseAip.getError_code()) {    // 注册成功

			/* 上传用户图片到本地服务器 */
			InputStream inputStream = null;
			String url = "";
			try {
				// base64转图片,存储到本地服务器
				String path = System.getProperty("catalina.home") + File.separator + "temp" + File.separator + DateUtils.getCurrentSTimeNumber() + ".jpg";//指定输出路径
				AiFaceUtils.genetareImg(base64String, path);
				File file = new File(path);
				if (file.exists()) {
					inputStream = new FileInputStream(file);
					MultipartFile multipartFile = new MockMultipartFile("file", IOUtils.toByteArray(inputStream));
					//上传图片到图片服务器
					if (null != file) {
						//图片上传
						url = uploadHome(multipartFile, "userFaceImg");
						if (StringUtils.isNotBlank(url)) {
							if (inputStream != null) {      //关闭流
								inputStream.close();
							}
							if (file.exists() && file.isFile()) {
								file.delete();
								logger.debug("已经删除文件：{}", file.getPath());
							}
						}
					}
				} else {
					logger.error("base64图片转换失败");
					return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("base64图片转换失败");
				}
			} catch (FileNotFoundException e) {
				logger.error("用户人脸信息注册出现异常,用户ID：{},异常信息：{}", userId, e);
				return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("用户人脸信息注册出现异常");
			} catch (IOException e) {
				e.printStackTrace();
				return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("用户人脸信息注册出现异常");
			}

			String faceCode = CoreUtils.newCode(EntityTables.FR_FACE_REGISTER_INFO);	//人脸编号
			if (StringUtils.isBlank(faceCode)) {
				throw new ServiceException("生成人脸编号出错");
			}

			/* 记录人脸识别会员注册表 */
			insertFaceRegisterInfo(deviceId, userId, url, payType, ip, responseAip, faceCode);

			/* 修改会员信息表 */
			MemberInfo memberInfoTemp = new MemberInfo();
			memberInfoTemp.setId(userId);
			memberInfoTemp.setIisRegFace(1);
			memberInfoTemp.setUpdateTime(DateUtils.getCurrentDateTime());
			memberInfoDao.updateByIdSelective(memberInfoTemp);

			/* 人脸识别操作日志记录 */
			LogUtils.insertFaceOperLog(deviceId, userId, 10, base64String, "用户人脸信息注册成功", 1, ip, 10);
			return new ResponseVo<>(true, 200, "用户人脸信息注册成功", "用户人脸信息注册成功");
		} else {    // 注册失败
			// 记录日志
			LogUtils.insertFaceOperLog(deviceId, userId, 10, "", "用户人脸信息注册失败", 0, ip, 10);
			return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(responseAip.getError_msg());
		}
	}

	/**
	 * 人脸识别会员注册表
	 *
	 * @param deviceId    注册AI设备ID
	 * @param userId      会员ID
	 * @param url         图片地址
	 * @param payType     支付方式
	 * @param ip          注册IP
	 * @param responseAip 百度返回结果
	 * @param faceCode    每张图片的唯一编号
	 */
	private void insertFaceRegisterInfo(String deviceId, String userId, String url, Integer payType, String ip, ResponseAip responseAip, String faceCode) {
		String GROUP_ID = GrpParaUtil.getValue(GrpParaUtil.AI_FACE_CONFIG, "faceGroup");    //数据字典配置
		ExecutorManager.getInstance().execute(() -> {
			Result result = responseAip.getResult();
			Location location = result.getLocation();
			MemberInfo memberInfo = memberInfoDao.selectByPrimaryKey(userId);
			AiInfo aiInfo = aiInfoDao.selectByPrimaryKey(deviceId);
			DeviceInfo deviceInfoVo = new DeviceInfo();
			deviceInfoVo.setSaiId(deviceId);
			List<DeviceInfo> deviceInfoList = deviceInfoDao.selectByEntityWhere(deviceInfoVo);
			if (null != memberInfo && null != aiInfo) {
				FaceRegisterInfo faceRegisterInfo = new FaceRegisterInfo();
				faceRegisterInfo.setSfaceCode(faceCode);                        //人脸编号
				faceRegisterInfo.setSmerchantId(aiInfo.getSmerchantId());        //商户ID
				faceRegisterInfo.setSmerchantCode(aiInfo.getSmerchantCode());    //商户编号
				faceRegisterInfo.setSmemberId(memberInfo.getId());                //会员ID
				faceRegisterInfo.setSmemberCode(memberInfo.getScode());            //会员编号
				faceRegisterInfo.setSmemberName(memberInfo.getSmemberName());    //会员名称
				faceRegisterInfo.setSregAiCode(aiInfo.getSaiCode());            //AI设备编号
				if (CollectionUtils.isNotEmpty(deviceInfoList)) {
					DeviceInfo deviceInfo = deviceInfoList.get(0);
					faceRegisterInfo.setSdeviceAddress(deviceInfo.getSprovinceName() + deviceInfo.getScityName() + deviceInfo.getSareaName() + deviceInfo.getSputAddress());
				}
				faceRegisterInfo.setIbindPayType(payType);                        //绑定支付方式
				faceRegisterInfo.setSregisterFaceAddress(url);                    //人脸图片地址
				faceRegisterInfo.setSregIp(ip);                                    //注册时的IP地址
				faceRegisterInfo.setItype(10);                                    //状态 10：注册
				faceRegisterInfo.setTregisterTime(DateUtils.getCurrentDateTime());    // 注册时间
				faceRegisterInfo.setTbindTime(DateUtils.getCurrentDateTime());            // 绑定时间
				faceRegisterInfo.setSfaceToken(result.getFace_token());            // 人脸识别face_token
				faceRegisterInfo.setSgroupId(GROUP_ID);        // 人脸识别库用户组
				faceRegisterInfo.setSleft(location.getLeft().toString());        // 左边距
				faceRegisterInfo.setStop(location.getTop().toString());            // 高度
				faceRegisterInfo.setSwidth(location.getWidth().toString());        // 宽度
				faceRegisterInfo.setSheight(location.getHeight().toString());    // 上边距
				faceRegisterInfo.setSratation(location.getRotation().toString());    // 旋转角
				faceRegisterInfo.setSuserInfo("");                                // 人脸库会员信息
				faceRegisterInfo.setIdelFlag(0);
				faceRegisterInfo.setTaddTime(DateUtils.getCurrentDateTime());
				faceRegisterInfo.setTupdateTime(DateUtils.getCurrentDateTime());
				faceRegisterInfoDao.insert(faceRegisterInfo);
			}
		});

	}

	/**
	 * 人脸信息注册二维码生成
	 *
	 * @param deviceId 设备ID
	 * @param key      通信密钥
	 * @return
	 */
	@Override
	public ResponseVo<String> generateRegisterQrCode(String deviceId, String key) {

		String faceRegisterQrCodeUrl = "";
		String url = ""; //服务器地址图片
		FileInputStream inputStream = null;
		try {
			String token = IdGenerator.randomUUID();    //随机生成UUID
			iCached.put(NettyConst.FACE_REGISTER_TOKEN + token, "valid", 30 * 60); // 授权连接有时长 token 30分钟
			//生成二维码
			String contents = EvnUtils.getValue("wap.http.domain") + "/index/faceAiVisit.html?aiId=" + deviceId + "&isScan=1&token=" + token;  //用户注册人脸识别二维码

			int width = 430; // 二维码图片宽度 300
			int height = 430; // 二维码图片高度300

			String format = "png";// 二维码的图片格式 png
			Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();

			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); // 指定纠错等级,纠错级别（L 7%、M 15%、Q 25%、H 30%）
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");   // 内容所使用字符集编码
			BitMatrix bitMatrix = null;//生成条形码时的一些配置,此项可选

			bitMatrix = new MultiFormatWriter().encode(contents,//要编码的内容
					BarcodeFormat.QR_CODE,      //二维码
					width, //条形码的宽度
					height, //条形码的高度
					hints);

			File file = new File(System.getProperty("catalina.home") + File.separator + "temp" + File.separator + DateUtils.getCurrentSTimeNumber() + ".png");//指定输出路径
			MatrixToImageWriter.writeToFile(bitMatrix, format, file);
			inputStream = new FileInputStream(file);
			MultipartFile multipartFile = new MockMultipartFile("file", IOUtils.toByteArray(inputStream));

			//上传图片到图片服务器
			if (null != file) {
				//图片上传
				url = uploadHome(multipartFile, "faceRegisterQrCode");
				if (StringUtils.isNotBlank(url)) {
					if (inputStream != null) {      //关闭流
						inputStream.close();
					}
					if (file.exists() && file.isFile()) {
						file.delete();
						System.out.println("已经删除文件：" + file.getPath());
					}
				}
			}

			String preUrl = SysParaUtil.getValue(NettyConst.FTP_IMAGE_PATH);                            // 图片地址前缀
			String faceRegisterQrCodevalidTime = GrpParaUtil.getValue(GrpParaUtil.AI_FACE_CONFIG, "faceRegisterQrCodevalidTime");    //数据字典配置
			faceRegisterQrCodeUrl = preUrl + url + "-//-" + faceRegisterQrCodevalidTime + "-//-" + token;
			if (StringUtils.isNotBlank(faceRegisterQrCodeUrl)) {
				iCached.put(NettyConst.FACE_REGISTER_QR_CODE + token, faceRegisterQrCodeUrl, new Integer(faceRegisterQrCodevalidTime)); // 注册图片存在秒数
				logger.debug("注册二维码：{},二维码有效时长（秒）:{}", preUrl + url, faceRegisterQrCodevalidTime);

				iCached.put(NettyConst.FACE_REGISTER_QRCODE_ISSCAN + token, "no", new Integer(faceRegisterQrCodevalidTime)); // 二维码是否被扫码过
				return new ResponseVo<>(true, 200, "生成注册二维码成功", faceRegisterQrCodeUrl);
			}
		} catch (WriterException e) {
			logger.error("人脸注册生成二维码写入文件出现异常：{}", e);
		} catch (FileNotFoundException e) {
			logger.error("人脸注册生成二维码读取文件出现异常：{}", e);
		} catch (IOException e) {
			logger.error("人脸注册生成二维码出现IO流异常：{}", e);
		} catch (Exception e) {
			logger.error("人脸注册生成二维码出现异常：{}", e);
		} finally {
			if (inputStream != null) {      //关闭流
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error("人脸注册二维码输入流关闭异常：{}", e);
				}
			}
		}
		return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("生成二维码出错");
	}


	/**
	 * 设备二维码上传
	 *
	 * @param file     图片文件
	 * @param pathName 图片路径
	 * @return
	 */
	private String uploadHome(MultipartFile file, String pathName) {
		try {
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(pathName).append("/").append(DateUtils.dateParseShortString(new Date())).append("/");
			String serverPath = stringBuffer.toString();//------>pathName/2018-03-07/
			String tempName = DateUtils.getCurrentSTimeNumber() + ".png";//文件名=="时间"+"."+"原图片名后缀"
			// 返回URL地址
			if (FtpUtils.uploadToFtpServer(file.getInputStream(), serverPath, tempName, FtpParamUtil.getFtpUser())) {
				StringBuffer stringBuffer1 = new StringBuffer();
				stringBuffer1.append("/").append(serverPath).append(tempName);// "/" + serverPath + tempName
				return stringBuffer1.toString();// 路径为------> /commodityInfo/2018-03-07/20180307151504492.jpg
			}
		} catch (IOException e) {
			logger.error("上传文件出现IOException异常：{}", e);
		}
		return null;
	}
}