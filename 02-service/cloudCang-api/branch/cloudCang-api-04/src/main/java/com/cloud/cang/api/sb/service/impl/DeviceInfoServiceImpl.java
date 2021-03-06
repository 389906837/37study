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
     * ?????????????????????
     *
     * @param deviceDto ??????
     * @return ?????????????????????????????????success????????????????????????
     * @throws Exception
     */
    @Override
    public ResponseVo<String> operateDevice(DeviceDto deviceDto) throws Exception {
        //???redis?????????????????????????????????
        Map<String, ChannelHandlerContext> channelMap = (Map<String, ChannelHandlerContext>) iCached.get("synclientMap");

        //?????????????????????channel?????????
        if (MapUtils.isNotEmpty(channelMap)) {
            List<String> clientIds = CommonUtils.stringToList(deviceDto.getId());//????????????ID----ID???????????????????????????
            String method = deviceDto.getFunction();//????????????????????????
            String params = deviceDto.getOperateParam();//????????????
            if (CollectionUtils.isNotEmpty(clientIds)) {
                if (clientIds.size() == 1) {    //????????????
                    ControlDeviceModel msg = new ControlDeviceModel();
                    DeviceInfo deviceInfo = deviceInfoDao.selectByPrimaryKey(clientIds.get(0));
                    if (deviceInfo.getIstatus() == 20) {    /* ?????????10=????????? 20=?????? 30=?????? 40=?????? */
                        MessageBody messageBody = new MessageBody();
                        messageBody.setType(1);
                        msg.setData(messageBody);
                        String resultMsg = JSON.toJSONString(msg);
//                        msgVo.setData(method);//??????????????????????????????????????????
//                        msgVo.setParams(params);//????????????
                        ChannelHandlerContext ctx = channelMap.get(clientIds);
//                        ctx.writeAndFlush(msgVo + "\r\n");
                        ChannelFuture channelFuture = ctx.writeAndFlush(resultMsg + System.lineSeparator());//??????+???????????????
                        for (; ; ) {
                            if (channelFuture.isSuccess()) {
                                //??????????????????????????????????????????

                                return ResponseVo.getSuccessResponse(deviceDto.getId());//
                            }
                        }
                    }
                } else {
                    for (String id : clientIds) {   //????????????
                        ControlDeviceModel msg = new ControlDeviceModel();

                        DeviceInfo deviceInfo = deviceInfoDao.selectByPrimaryKey(id);
                        if (deviceInfo.getIstatus() == 20) {    /* ?????????10=????????? 20=?????? 30=?????? 40=?????? */
                            MessageBody messageBody = new MessageBody();

                            messageBody.setType(1);//??????????????????????????????????????????

                            msg.setData(messageBody);
                            ChannelHandlerContext ctx = channelMap.get(clientIds);
//                        ctx.writeAndFlush(msgVo + "\r\n");
                            ctx.writeAndFlush(msg + System.lineSeparator());//??????+???????????????
                        }
                    }
                }
                return ResponseVo.getSuccessResponse(deviceDto.getId());//???????????????????????????
            } else {
                return new ResponseVo<String>(false, ReturnCodeEnum.ERROR_PARAM_FAILED.getCode(), "ID?????????");
            }
        } else {
            return new ResponseVo<String>(false, ReturnCodeEnum.ERROR_CHANNEL.getCode(), "???????????????????????????");
        }
    }


    /**
     * ????????????ID????????????????????????????????????ID
     * @param merchantId ??????ID
     * @return
     */
    @Override
    public List<String> selectDeviceIdByMerchantId(String merchantId) {
        return deviceInfoDao.selectIdByMerchantId(merchantId);
    }

    /**
     * ?????????????????????????????????1???????????????
     * @param merchantCode
     * @return
     */
    @Override
    public ResponseVo<AdListModelTemp> selectOperatingAdOne(String merchantCode) throws Exception {
        return getAdListModelByRegion(merchantCode, NettyConst.AD_ONE_NUMBER);
    }

    /**
     * ?????????????????????????????????2???????????????
     * @param merchantCode
     * @return
     * @throws Exception
     */
    @Override
    public ResponseVo<AdListModelTemp> selectOperatingAdTwo(String merchantCode) throws Exception {
        return getAdListModelByRegion(merchantCode, NettyConst.AD_TWO_NUMBER);
    }

    /**
     * ?????????????????????????????????3???????????????
     * @param merchantCode
     * @return
     * @throws Exception
     */
    @Override
    public ResponseVo<AdListModelTemp> selectOperatingAdThree(String merchantCode) throws Exception {
        return getAdListModelByRegion(merchantCode, NettyConst.AD_THREE_NUMBER);
    }

    /**
     * AI????????????????????????????????????
     *
     * @param deviceId
     * @param key
     * @return
     */
    @Override
    public ResponseVo<String> getOpenDoorQrCode(String deviceId, String key) {
        // ????????????????????????
        DeviceRegister deviceRegisterVo = new DeviceRegister();
        deviceRegisterVo.setSsecurityKey(key);
        deviceRegisterVo.setSdeviceId(deviceId);
        deviceRegisterVo.setIdelFlag(0);
        List<DeviceRegister> deviceRegisterList = deviceRegisterService.selectByEntityWhere(deviceRegisterVo);
        if (CollectionUtils.isEmpty(deviceRegisterList)) {
            return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("????????????AI????????????????????????????????????");
        }


        DeviceRegister deviceRegisterDomain = deviceRegisterList.get(0);
        if (10 == deviceRegisterDomain.getIstatus() || 30 == deviceRegisterDomain.getIstatus()) {
            return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("????????????????????????");
        }
        Map<String, String> map = new HashMap<>();
        map.put("saiId", deviceId);
        map.put("idelFlag", "0");
        map.put("isupportAi", "1");
        List<DeviceInfo> deviceInfoList = deviceInfoDao.selectByMapWhere(map);
        if (CollectionUtils.isEmpty(deviceInfoList)) {
            return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("?????????????????????????????????????????????????????????????????????");
        }
        String url = deviceInfoList.get(0).getSqrUrl();
        String preUrl = SysParaUtil.getValue(NettyConst.FTP_IMAGE_PATH);        //  ???????????????????????????
        return ResponseVo.getSuccessResponse(preUrl + url);
    }

    /**
     * ????????????????????????????????????
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

        // ??????
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
     * ????????????????????????openSDK????????????
     *
     * @return
     * @param deviceId
     */
    @Override
    public ResponseVo<OpenSdkConfigInfo> getCloudOpenSDKConfig(String deviceId) throws Exception {
        DeviceInfo deviceInfo = this.selectByPrimaryKey(deviceId);
        if (null == deviceInfo) {//???????????????????????????
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo();
        }
        //??????????????????
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
            logger.error("???Redis?????????openSdk??????????????????");
        }
        if (null == appManage) {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo();
        }
        OpenSdkConfigInfo openSdkConfigInfo = new OpenSdkConfigInfo();
        openSdkConfigInfo.setAppId(app_id);
        openSdkConfigInfo.setAppSercetKey(appManage.getSappSecretKey());
        openSdkConfigInfo.setHost(merchantClientConf.getScloudHost());
        //??????????????????????????????
        if (deviceInfo.getIisOpeningInventory() != null && deviceInfo.getIisOpeningInventory().intValue() == 1) {
            openSdkConfigInfo.setIisOpeningInventory("open");
        } else {
            openSdkConfigInfo.setIisOpeningInventory("close");
        }
        //??????????????????????????????
        if (merchantClientConf.getIisOpeningInventory() != null && merchantClientConf.getIisOpeningInventory().intValue() == 1) {
            openSdkConfigInfo.setReplenstimer("reopen");
        } else {
            openSdkConfigInfo.setReplenstimer("reclose");
        }
        //openSdkConfigInfo.setAppSercetKey(GrpParaUtil.getValue(GrpParaUtil.CLOUD_RECOGNITION_OPEN_SDK, "app_sercet_key"));
        //openSdkConfigInfo.setHost(GrpParaUtil.getValue(GrpParaUtil.CLOUD_RECOGNITION_OPEN_SDK, "host"));
        //openSdkConfigInfo.setIisOpeningInventory(GrpParaUtil.getValue(GrpParaUtil.CLOUD_RECOGNITION_OPEN_SDK, "iisOpeningInventory"));  // open=?????????close=??????
        openSdkConfigInfo.setSappPrivateKey(appManage.getSappPrivateKey());
        openSdkConfigInfo.setSplatformPublicKey(appManage.getSplatformPublicKey());*/
        return ResponseVo.getSuccessResponse(openSdkConfigInfo);
    }


    /**
     * ???????????????????????????????????????
     *
     * @param merchantCode ????????????
     * @return
     */
    private ResponseVo<AdListModelTemp> getAdListModelByRegion(String merchantCode, String region) throws Exception {
        List<Advertis> advertisList = new ArrayList<>();
        if (StringUtils.isNotBlank(merchantCode)) {
            String catcheKey = RedisConst.WZ_REGIO_DETAIL_ + region + "_" + merchantCode;
            String json = (String) iCached.hget(RedisConst.WZ_REGIO_ADVERTIS + merchantCode, catcheKey); // ??????????????????
            if (StringUtil.isNotBlank(json)) {
                advertisList = JSONObject.parseArray(json, Advertis.class);
                if (CollectionUtils.isNotEmpty(advertisList)) {
                    List<AdModel> adModelList = new ArrayList<>();
                    List<AdModel> vcadModelList = new ArrayList<>();
                    AdModel adModel = null;
                    String preUrl = SysParaUtil.getValue(NettyConst.FTP_IMAGE_PATH);        //  ???????????????????????????
                    for (Advertis ad : advertisList) {
                        adModel = new AdModel();
                        String adId = ad.getId();
                        String adUrl = preUrl + ad.getScontentUrl();
                        Integer istatus = ad.getIstatus();  // 0: INVALID:????????? 1: USING:????????? 2: WAIT:????????? 3: PAUSE:??????
                        if (ad.getTendDate().after(new Date()) && StringUtils.isNotBlank(adId) && StringUtils.isNotBlank(adUrl) && istatus.intValue() == 1) {
                            adModel.setAdId(adId);
                            adModel.setScontentUrl(adUrl);
                            adModel.setIadvType(ad.getIadvType());
                            adModel.setShref(ad.getShref());
                            adModel.setIlinkType(ad.getIlinkType());
                            //??????????????????
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
        return ReturnCodeEnum.ERROR_NO_AD.getResponseVo("??????????????????");
    }

}