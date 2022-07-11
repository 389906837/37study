package com.cloud.cang.api.sb.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.api.common.ReturnCodeEnum;
import com.cloud.cang.api.common.utils.CommonUtils;
import com.cloud.cang.api.netty.service.MgrSendMsgService;
import com.cloud.cang.api.netty.vo.send.ControlDeviceModel;
import com.cloud.cang.api.netty.vo.send.MessageBody;
import com.cloud.cang.api.sb.dao.DeviceInfoDao;
import com.cloud.cang.api.sb.service.DeviceInfoService;
import com.cloud.cang.api.sb.service.DeviceRegisterService;
import com.cloud.cang.api.ws.domain.AndroidConfigInfo;
import com.cloud.cang.api.ws.domain.OpenSdkConfigInfo;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.NettyConst;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.core.utils.SysParaUtil;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.AdListModel;
import com.cloud.cang.model.AdListModelTemp;
import com.cloud.cang.model.AdModel;
import com.cloud.cang.model.op.AppManage;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sb.DeviceRegister;
import com.cloud.cang.model.sh.MerchantClientConf;
import com.cloud.cang.model.wz.Advertis;
import com.cloud.cang.sb.DeviceDto;
import com.cloud.cang.utils.StringUtil;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DeviceInfoServiceImpl extends GenericServiceImpl<DeviceInfo, String> implements
        DeviceInfoService {

    private static final Logger logger = LoggerFactory.getLogger(DeviceInfoServiceImpl.class);

    @Autowired
    private DeviceInfoDao deviceInfoDao;

    @Autowired
    private ICached iCached;

    @Autowired
    private DeviceRegisterService deviceRegisterService;
    @Autowired
    MgrSendMsgService mgrSendMsgService;

    @Override
    public GenericDao<DeviceInfo, String> getDao() {
        return deviceInfoDao;
    }


    /**
     * 向设备发送消息
     *
     * @param deviceDto 服务
     * @return 发送消息结果，成功返回success，失败返回错误码
     * @throws Exception
     */
    @Override
    public ResponseVo<String> operateDevice(DeviceDto deviceDto) throws Exception {
        //从redis中获取客户端连接的通道
        Map<String, ChannelHandlerContext> channelMap = (Map<String, ChannelHandlerContext>) iCached.get("synclientMap");

        //缓存中设备通信channel不为空
        if (MapUtils.isNotEmpty(channelMap)) {
            List<String> clientIds = CommonUtils.stringToList(deviceDto.getId());//分割设备ID----ID为“，”拼接字符串
            String method = deviceDto.getFunction();//要执行的操作名称
            String params = deviceDto.getOperateParam();//方法参数
            if (CollectionUtils.isNotEmpty(clientIds)) {
                if (clientIds.size() == 1) {    //一台设备
                    ControlDeviceModel msg = new ControlDeviceModel();
                    DeviceInfo deviceInfo = deviceInfoDao.selectByPrimaryKey(clientIds.get(0));
                    if (deviceInfo.getIstatus() == 20) {    /* 状态：10=未注册 20=正常 30=故障 40=报废 */
                        MessageBody messageBody = new MessageBody();
                        messageBody.setType(1);
                        msg.setData(messageBody);
                        String resultMsg = JSON.toJSONString(msg);
//                        msgVo.setData(method);//发送给客户端的需要执行的操作
//                        msgVo.setParams(params);//方法参数
                        ChannelHandlerContext ctx = channelMap.get(clientIds);
//                        ctx.writeAndFlush(msgVo + "\r\n");
                        ChannelFuture channelFuture = ctx.writeAndFlush(resultMsg + System.lineSeparator());//消息+系统换行符
                        for (; ; ) {
                            if (channelFuture.isSuccess()) {
                                //查询数据库看消息是否发送成功

                                return ResponseVo.getSuccessResponse(deviceDto.getId());//
                            }
                        }
                    }
                } else {
                    for (String id : clientIds) {   //多台设备
                        ControlDeviceModel msg = new ControlDeviceModel();

                        DeviceInfo deviceInfo = deviceInfoDao.selectByPrimaryKey(id);
                        if (deviceInfo.getIstatus() == 20) {    /* 状态：10=未注册 20=正常 30=故障 40=报废 */
                            MessageBody messageBody = new MessageBody();

                            messageBody.setType(1);//发送给客户端的需要执行的操作

                            msg.setData(messageBody);
                            ChannelHandlerContext ctx = channelMap.get(clientIds);
//                        ctx.writeAndFlush(msgVo + "\r\n");
                            ctx.writeAndFlush(msg + System.lineSeparator());//消息+系统换行符
                        }
                    }
                }
                return ResponseVo.getSuccessResponse(deviceDto.getId());//发送消息完成后返回
            } else {
                return new ResponseVo<String>(false, ReturnCodeEnum.ERROR_PARAM_FAILED.getCode(), "ID不存在");
            }
        } else {
            return new ResponseVo<String>(false, ReturnCodeEnum.ERROR_CHANNEL.getCode(), "设备未连接到服务器");
        }
    }


    /**
     * 通过商户ID查询商户下正常使用的设备ID
     * @param merchantId 商户ID
     * @return
     */
    @Override
    public List<String> selectDeviceIdByMerchantId(String merchantId) {
        return deviceInfoDao.selectIdByMerchantId(merchantId);
    }

    /**
     * 查询商户下的设备运营位1的广告信息
     * @param merchantCode
     * @return
     */
    @Override
    public ResponseVo<AdListModelTemp> selectOperatingAdOne(String merchantCode) throws Exception {
        return getAdListModelByRegion(merchantCode, NettyConst.AD_ONE_NUMBER);
    }

    /**
     * 查询商户下的设备运营位2的广告信息
     * @param merchantCode
     * @return
     * @throws Exception
     */
    @Override
    public ResponseVo<AdListModelTemp> selectOperatingAdTwo(String merchantCode) throws Exception {
        return getAdListModelByRegion(merchantCode, NettyConst.AD_TWO_NUMBER);
    }

    /**
     * 查询商户下的设备运营位3的广告信息
     * @param merchantCode
     * @return
     * @throws Exception
     */
    @Override
    public ResponseVo<AdListModelTemp> selectOperatingAdThree(String merchantCode) throws Exception {
        return getAdListModelByRegion(merchantCode, NettyConst.AD_THREE_NUMBER);
    }

    /**
     * AI设备获取售货机开门二维码
     *
     * @param deviceId
     * @param key
     * @return
     */
    @Override
    public ResponseVo<String> getOpenDoorQrCode(String deviceId, String key) {
        // 校验设备是否存在
        DeviceRegister deviceRegisterVo = new DeviceRegister();
        deviceRegisterVo.setSsecurityKey(key);
        deviceRegisterVo.setSdeviceId(deviceId);
        deviceRegisterVo.setIdelFlag(0);
        List<DeviceRegister> deviceRegisterList = deviceRegisterService.selectByEntityWhere(deviceRegisterVo);
        if (CollectionUtils.isEmpty(deviceRegisterList)) {
            return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("未查询到AI设备，请检查参数是否正确");
        }


        DeviceRegister deviceRegisterDomain = deviceRegisterList.get(0);
        if (10 == deviceRegisterDomain.getIstatus() || 30 == deviceRegisterDomain.getIstatus()) {
            return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("该设备未注册激活");
        }
        Map<String, String> map = new HashMap<>();
        map.put("saiId", deviceId);
        map.put("idelFlag", "0");
        map.put("isupportAi", "1");
        List<DeviceInfo> deviceInfoList = deviceInfoDao.selectByMapWhere(map);
        if (CollectionUtils.isEmpty(deviceInfoList)) {
            return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("未查询到售货机设备相关信息，请检查参数是否正确");
        }
        String url = deviceInfoList.get(0).getSqrUrl();
        String preUrl = SysParaUtil.getValue(NettyConst.FTP_IMAGE_PATH);        //  图片服务器地址前缀
        return ResponseVo.getSuccessResponse(preUrl + url);
    }

    /**
     * 获取年会人脸识别配置信息
     *
     * @return
     */
    @Override
    public ResponseVo<AndroidConfigInfo> getAmFaceConfig() {
        String apiKey = GrpParaUtil.getValue(GrpParaUtil.AM_FACE_CONFIG, "apiKey");
        String secretKey = GrpParaUtil.getValue(GrpParaUtil.AM_FACE_CONFIG, "secretKey");
        String licenseID = GrpParaUtil.getValue(GrpParaUtil.AM_FACE_CONFIG, "licenseID");
        String licenseFileName = GrpParaUtil.getValue(GrpParaUtil.AM_FACE_CONFIG, "licenseFileName");
        String groupID = GrpParaUtil.getValue(GrpParaUtil.AM_FACE_CONFIG, "groupID");
        String isOpenScreenSaver = GrpParaUtil.getValue(GrpParaUtil.AM_FACE_CONFIG, "isOpenScreenSaver");
        String score = GrpParaUtil.getValue(GrpParaUtil.AM_FACE_CONFIG, "score");

        // 塞值
        AndroidConfigInfo androidConfigInfo = new AndroidConfigInfo();
        androidConfigInfo.setApiKey(apiKey);
        androidConfigInfo.setSecretKey(secretKey);
        androidConfigInfo.setLicenseID(licenseID);
        androidConfigInfo.setLicenseFileName(licenseFileName);
        androidConfigInfo.setIsOpenScreenSaver(isOpenScreenSaver);
        androidConfigInfo.setGroupID(groupID);
        androidConfigInfo.setScore(score);
        if (StringUtils.isNotEmpty(isOpenScreenSaver) && "10".equals(isOpenScreenSaver)) {
            String url = GrpParaUtil.getValue(GrpParaUtil.AM_FACE_CONFIG, "url");
            androidConfigInfo.setUrl(url);
        }
        return ResponseVo.getSuccessResponse(androidConfigInfo);
    }

    /**
     * 获取云端识别柜子openSDK配置信息
     *
     * @return
     * @param deviceId
     */
    @Override
    public ResponseVo<OpenSdkConfigInfo> getCloudOpenSDKConfig(String deviceId) throws Exception {
        DeviceInfo deviceInfo = this.selectByPrimaryKey(deviceId);
        if (null == deviceInfo) {//设备状态和数据判断
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo();
        }
        //组装发送参数
        OpenSdkConfigInfo openSdkConfigInfo = mgrSendMsgService.getOpenSdkConfigInfo(10,"", deviceInfo.getSmerchantCode(), deviceInfo.getIisOpeningInventory(),null);
        if (null == openSdkConfigInfo) {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo();
        }
       /* MerchantClientConf merchantClientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_CLIENT_CONFIG+deviceInfo.getSmerchantCode());
        String app_id = merchantClientConf.getScloudAppId();
        //String app_id = GrpParaUtil.getValue(GrpParaUtil.CLOUD_RECOGNITION_OPEN_SDK, "app_id");//
        AppManage appManage = null;
        try {
            String str = (String) iCached.get(RedisConst.OP_APP_MANAGE + app_id);
            appManage = JSON.parseObject(str, AppManage.class);
        } catch (Exception e) {
            logger.error("从Redis中获取openSdk信息出现异常");
        }
        if (null == appManage) {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo();
        }
        OpenSdkConfigInfo openSdkConfigInfo = new OpenSdkConfigInfo();
        openSdkConfigInfo.setAppId(app_id);
        openSdkConfigInfo.setAppSercetKey(appManage.getSappSecretKey());
        openSdkConfigInfo.setHost(merchantClientConf.getScloudHost());
        //购物是否开启实时盘货
        if (deviceInfo.getIisOpeningInventory() != null && deviceInfo.getIisOpeningInventory().intValue() == 1) {
            openSdkConfigInfo.setIisOpeningInventory("open");
        } else {
            openSdkConfigInfo.setIisOpeningInventory("close");
        }
        //补货是否开启实时盘货
        if (merchantClientConf.getIisOpeningInventory() != null && merchantClientConf.getIisOpeningInventory().intValue() == 1) {
            openSdkConfigInfo.setReplenstimer("reopen");
        } else {
            openSdkConfigInfo.setReplenstimer("reclose");
        }
        //openSdkConfigInfo.setAppSercetKey(GrpParaUtil.getValue(GrpParaUtil.CLOUD_RECOGNITION_OPEN_SDK, "app_sercet_key"));
        //openSdkConfigInfo.setHost(GrpParaUtil.getValue(GrpParaUtil.CLOUD_RECOGNITION_OPEN_SDK, "host"));
        //openSdkConfigInfo.setIisOpeningInventory(GrpParaUtil.getValue(GrpParaUtil.CLOUD_RECOGNITION_OPEN_SDK, "iisOpeningInventory"));  // open=开启，close=关闭
        openSdkConfigInfo.setSappPrivateKey(appManage.getSappPrivateKey());
        openSdkConfigInfo.setSplatformPublicKey(appManage.getSplatformPublicKey());*/
        return ResponseVo.getSuccessResponse(openSdkConfigInfo);
    }


    /**
     * 转换从缓存中获取的数据对象
     *
     * @param merchantCode 商户编号
     * @return
     */
    private ResponseVo<AdListModelTemp> getAdListModelByRegion(String merchantCode, String region) throws Exception {
        List<Advertis> advertisList = new ArrayList<>();
        if (StringUtils.isNotBlank(merchantCode)) {
            String catcheKey = RedisConst.WZ_REGIO_DETAIL_ + region + "_" + merchantCode;
            String json = (String) iCached.hget(RedisConst.WZ_REGIO_ADVERTIS + merchantCode, catcheKey); // 从缓存中取值
            if (StringUtil.isNotBlank(json)) {
                advertisList = JSONObject.parseArray(json, Advertis.class);
                if (CollectionUtils.isNotEmpty(advertisList)) {
                    List<AdModel> adModelList = new ArrayList<>();
                    List<AdModel> vcadModelList = new ArrayList<>();
                    AdModel adModel = null;
                    String preUrl = SysParaUtil.getValue(NettyConst.FTP_IMAGE_PATH);        //  图片服务器地址前缀
                    for (Advertis ad : advertisList) {
                        adModel = new AdModel();
                        String adId = ad.getId();
                        String adUrl = preUrl + ad.getScontentUrl();
                        Integer istatus = ad.getIstatus();  // 0: INVALID:已过期 1: USING:投放中 2: WAIT:待投放 3: PAUSE:暂停
                        if (ad.getTendDate().after(new Date()) && StringUtils.isNotBlank(adId) && StringUtils.isNotBlank(adUrl) && istatus.intValue() == 1) {
                            adModel.setAdId(adId);
                            adModel.setScontentUrl(adUrl);
                            adModel.setIadvType(ad.getIadvType());
                            adModel.setShref(ad.getShref());
                            adModel.setIlinkType(ad.getIlinkType());
                            //判断横屏竖屏
                            if (null != ad.getIscreenDisplayType() && ad.getIscreenDisplayType().intValue() == 20) {
                                vcadModelList.add(adModel);
                            } else {
                                adModelList.add(adModel);
                            }
                        }
                    }
                    AdListModelTemp adListModel = new AdListModelTemp();
                    if (CollectionUtils.isNotEmpty(adModelList)) {
                        adListModel.setAdModelList(adModelList);
                    }
                    if (CollectionUtils.isNotEmpty(vcadModelList)) {
                        adListModel.setVcadModelList(vcadModelList);
                    }
                    return ResponseVo.getSuccessResponse(adListModel);
                }
            }
        }
        return ReturnCodeEnum.ERROR_NO_AD.getResponseVo("没有查到广告");
    }

}