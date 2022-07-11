package com.cloud.cang.api.netty.service;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.api.common.ReturnCodeEnum;
import com.cloud.cang.api.common.utils.*;
import com.cloud.cang.api.netty.vo.ClientVo;
import com.cloud.cang.api.sb.service.DeviceInfoService;
import com.cloud.cang.api.sb.service.DeviceUpgradeDetailsService;
import com.cloud.cang.api.singleton.SingletonClientMap;
import com.cloud.cang.api.ws.domain.OpenSdkConfigInfo;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.common.TypeConstant;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.NettyConst;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.faceCommon.FaceType;
import com.cloud.cang.model.*;
import com.cloud.cang.model.op.AppManage;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sb.DeviceUpgradeDetails;
import com.cloud.cang.model.sh.MerchantClientConf;
import com.cloud.cang.sb.*;
import com.cloud.cang.sb.operatingParams.AdjustVolumeVo;
import com.cloud.cang.sb.operatingParams.RebootVo;
import com.cloud.cang.sb.operatingParams.ShutdownVo;
import com.cloud.cang.sb.operatingParams.UpgradeVoiceVo;
import com.cloud.cang.utils.StringUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentMap;

/**
 * @version 1.0
 * @ClassName NettyService
 * @Description mgr向设备发送消息
 * @Author zengzexiong
 * @Date 2018年1月23日15:31:25
 */
@Service("mgrSendMsgService")
public class MgrSendMsgService {

    @Autowired
    private ICached iCached;

    @Autowired
    private DeviceInfoService deviceInfoService;

    @Autowired
    private DeviceUpgradeDetailsService deviceUpgradeDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(MgrSendMsgService.class);
    private static ConcurrentMap<String, ChannelHandlerContext> ctxMap = SingletonClientMap.newInstance().getCtxMap();  //在线设备map
    private static ConcurrentMap<String, ChannelHandlerContext> faceMap = SingletonClientMap.newInstance().getAiFaceMap();  //AI在线设备map
    private ChannelGroup channelGroup = SingletonClientMap.newInstance().getChannelGroup();  //连接但是未注册的TCP
    private ChannelHandlerContext context = null;


    /**
     * 给选中的客户端推送消息
     * @param deviceDto 页面Dto
     * @return 消息发送结果，成功success，失败failed
     * @throws Exception
     */
    public ResponseVo<String> sendMsgToMany(DeviceDto deviceDto) throws Exception {
        ResponseVo responseVo = new ResponseVo<String>(false, ReturnCodeEnum.ERROR_CHANNEL.getCode(), "设备未连接到服务器");
        String deviceID = deviceDto.getId();            //设备ID
        String methodType = deviceDto.getFunction();    //执行方法
        String data = deviceDto.getOperateParam();     //方法参数（可为null）
        String userId = deviceDto.getUserId();          //操作员ID
        List<String> clientIpList = CommonUtils.stringToList(deviceID);
        if (CollectionUtils.isNotEmpty(clientIpList)) {
            for (String str : clientIpList) {
                logger.debug("当前选中设备ID：{}", str);
                sendMsgToOne(str, methodType, data, userId);
            }
            return ResponseVo.getSuccessResponse(NettyConst.SUCCESS);
        }
        return responseVo;
    }


    /**
     * 后台管理页面给某台设备推送消息
     *
     * @param deviceId   客户端IP
     * @param methodType 操作方法
     * @param data       发送参数
     * @param userId     发消息的操作员Id（后台管理员，支付宝/微信用户，定时任务）
     * @return 消息发送结果，成功success，失败failed
     * @throws Exception
     */
    public ResponseVo<String> sendMsgToOne(String deviceId, String methodType, String data, String userId) throws Exception {
        if (StringUtils.isNotEmpty(deviceId)) {
            ChannelHandlerContext ctx = ctxMap.get(deviceId);
            if (null != ctx) {
                //发送给客户端的消息体
                if (TypeConstant.UPGRADE_SYSTEM.equals(methodType) || TypeConstant.VR_SERVER_UPGRADE.equals(methodType) || TypeConstant.UPDATE_FEATURE_LIBRARY.equals(methodType)) {  // 推送升级地址url，版本号，升级时间,升级主表ID
                    if (StringUtils.isNotBlank(data)) {
                        String[] arrays = data.split("-//-");
                        if (ArrayUtils.isNotEmpty(arrays) && arrays.length == 4) {

                            /* 向设备升级明细表插入设备本次升级数据*/
                            DeviceInfo deviceInfo = (DeviceInfo) iCached.hget(NettyConst.DEVICE_INFO, deviceId);
                            DeviceUpgradeDetails details = new DeviceUpgradeDetails();
                            details.setIstatus(10);                                         // 10待处理 20升级成功 30升级失败
                            details.setSmainId(arrays[3]);                                  // 升级主表ID
                            if (null != deviceInfo) {
                                details.setSdeviceCode(deviceInfo.getScode());              // 设备编号
                                details.setSdeviceAddress(deviceInfo.getSprovinceName() + deviceInfo.getScityName() + deviceInfo.getSareaName() + deviceInfo.getSputAddress());       // 设备地址
                            }
                            details.setSdeviceId(deviceId);       // 设备ID
                            deviceUpgradeDetailsService.insert(details);

                            /* 向设备推送升级消息*/
                            VersionModel versionModel = new VersionModel();
                            versionModel.setVersion(arrays[1]);     // 升级版本
                            versionModel.setUrl(arrays[0]);         // 升级url地址
                            versionModel.setPubtime(arrays[2]);     // 升级时间 立即/定时
                            versionModel.setDetailId(details.getId());    // 升级明细表ID
                            MsgToJsonUtils.asynSendMsgByCtx(deviceId, ctx, true, versionModel, null, methodType);
                        }
                    }
                } else if (TypeConstant.UPGRADE_VOICE.equals(methodType)) {    // 推送升级语音
                    logger.debug("向设备推送升级语音开始，设备ID：{}", deviceId);
                    if (StringUtils.isBlank(data)) {
                        logger.error("推送升级语音数据为空，无法推送，设备ID：{}", deviceId);
                        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("参数错误");
                    }

                    String[] arrays = data.split("-//-");
                    if (ArrayUtils.isEmpty(arrays)) {
                        logger.error("推送升级语音数据分割数组为空，无法推送，设备ID：{}", deviceId);
                        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("参数错误");
                    }
                    if (arrays.length != 3) {
                        logger.error("推送升级语音数据分割数组长度不为3，无法推送，设备ID：{}", deviceId);
                        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("参数不匹配");
                    }
                    String url = arrays[0];         // 升级地址
                    String voiceType = arrays[1];   // 升级语音类型
                    String mainId = arrays[2];    // 升级记录主表主键ID
                    String thankUrl = "noUrl";
                    String shoppingUrl = "noUrl";
                    String openDoorUrl = "noUrl";
                    String urlType = "";

                    /* 向设备升级明细表插入设备本次升级数据*/
                    DeviceInfo deviceInfo = (DeviceInfo) iCached.hget(NettyConst.DEVICE_INFO, deviceId);
                    DeviceUpgradeDetails details = new DeviceUpgradeDetails();
                    details.setIstatus(10);                                         // 10待处理 20升级成功 30升级失败
                    details.setSmainId(mainId);                                  // 升级主表ID
                    if (null != deviceInfo) {
                        details.setSdeviceCode(deviceInfo.getScode());              // 设备编号
                        details.setSdeviceAddress(deviceInfo.getSprovinceName() + deviceInfo.getScityName() + deviceInfo.getSareaName() + deviceInfo.getSputAddress());       // 设备地址
                    }
                    details.setSdeviceId(deviceId);       // 设备ID
                    deviceUpgradeDetailsService.insert(details);
                    logger.debug("推送语音数据记录主表ID：{}，明细表ID：{}，设备ID：{}", mainId, details.getId(), deviceId);

                    /* 准备推送语音*/
                    // 10 开门 20 购物 30 关门
                    if (voiceType.equals("10")) {
                        openDoorUrl = url;
                        urlType = "开门语音";
                    } else if (voiceType.equals("20")) {
                        shoppingUrl = url;
                        urlType = "购物语音";
                    } else if (voiceType.equals("30")) {
                        thankUrl = url;
                        urlType = "关门语音";
                    }
                    // 封装语音升级参数对象
                    VoiceModel voiceModel = new VoiceModel();
                    voiceModel.setOpenDoorUrl(openDoorUrl);
                    voiceModel.setShoppingUrl(shoppingUrl);
                    voiceModel.setThanksUrl(thankUrl);
                    voiceModel.setDetailId(details.getId());
                    MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, voiceModel, null, methodType);
                    logger.debug("向设备推送升级语音完成，设备ID：{}，语音类型为", deviceId, urlType);
                } else if (TypeConstant.QR_CODE.equals(methodType)) {    // 推送二维码
                    MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, data, null, methodType);

                    // 给小屏推送二维码
                    DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceId);
                    if (null != deviceInfo) {
                        String aiId = deviceInfo.getSaiId();
                        ChannelHandlerContext faceCtx = faceMap.get(aiId);
                        if (null != faceCtx) {
                            FaceMsgToJsonUtils.SendFaceCommonNoCodeMsgByCtx(aiId, faceCtx, true, data, null, FaceType.QR_CODE);
                        }
                    }
                } else if (TypeConstant.VOLUME.equals(methodType)) {    // 调节音量
                    logger.debug("向设备音量开始，设备ID：{}，音量值：{}", deviceId, data);
                    if (StringUtils.isBlank(data)) {
                        logger.error("音量数据为空，无法推送消息");
                        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("参数错误");
                    }
                    String volumeValue = data;
                    logger.debug("推送语音数据记录设备ID：{}", deviceId);
                    /* 开始封装推送model，准备推送消息*/
                    VolumeModel volumeModel = new VolumeModel();
                    volumeModel.setVolume(new Integer(volumeValue));
                    volumeModel.setDetailId("detailId is Discard");
                    MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, volumeModel, null, methodType);
                    logger.debug("向设备音量完成，设备ID：{}，音量值：{}", deviceId, data);
                } else if (NettyConst.REBOOT.equals(methodType)) {    // 重启
                    MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, null, null, methodType);
                    logger.debug("向设备发送重启消息完成，设备ID：{}", deviceId);
                }
                return ResponseVo.getSuccessResponse("发送成功");
            }
            return new ResponseVo<>(false, 10001, "设备离线，无法发送消息");
        }
        return new ResponseVo<>(false, 10002, "设备ID未空，无法发送消息");
    }


    /**
     * 向设备发送盘货指令
     *
     * @param deviceId 单个设备ID
     * @param userId   操作员ID
     * @return 发送成功返回success，失败返回false
     * @throws Exception
     */
    public ResponseVo<String> inventory(String deviceId, String userId) {
        try {
            //发送给客户端的消息体
            MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, "", userId, TypeConstant.INVENTORY);
            return ResponseVo.getSuccessResponse(NettyConst.SUCCESS);
        } catch (Exception e) {
            logger.error("发送盘货消息出现异常:{}", e);
        }
        return new ResponseVo<>(false, 10002, "发送主动盘货指令失败");
    }


    /**
     * 获取商户下的离线设备ID
     * @param merchantId 商户ID
     * @return 存在离线设备返回设备ID的list集合，否则返回空集合
     */
    public ResponseVo getOfflineDevice(String merchantId) {
        try {
            List<String> deviceIdList = deviceInfoService.selectDeviceIdByMerchantId(merchantId);   //  从设备表中查询商户下的设备ID
            // 循环设备ID，发送消息
            for (String id : deviceIdList) {
                if (StringUtils.isNotBlank(id)) {
                    if (!ctxMap.containsKey(id)) {

                        asynSetDeviceOffline(id);                       //异步修改设备状态
                        LogUtils.sendDeviceOfflineMsgToInternal(id);    // 发送离线短信给内部人员
                    } else {

                        asynTestDeviceIsAlive(id, ctxMap);              // 异步发送消息，然后检测是否收到消息，收到消息显示在线否则修改为离线
                    }
                }
            }
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            logger.error("获取商户下的离线设备信息出现异常:{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("获取商户下的离线设备信息出错");
    }



    /**
     * 如果设备连接到服务器，断开连接
     * 否则不错处理
     * @param deviceIds
     * @return
     */
    public ResponseVo<String> disconnectDevice(String deviceIds) {
        String[] ids= StringUtils.split(deviceIds, ",");
        try {
            if (ArrayUtils.isNotEmpty(ids)) {
                for (String id : ids) {
                    ChannelHandlerContext context = ctxMap.get(id);
                    if (null != context) {
                        ctxMap.remove(id);
                        context.close();
                        return ResponseVo.getSuccessResponse("断开连接成功");
                    }
                }
            }
        } catch (Exception e) {
            logger.error("断开连接出现异常:{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("获取商户下的离线设备信息出错");
    }

    /**
     * 定时盘货
     * 根据商户ID发送盘货指令
     * 正常使用的设备
     *
     * @param merchantId
     * @return
     */
    public ResponseVo<String> timingInventoryByMerchantId(String merchantId) {
        List<String> deviceIds = deviceInfoService.selectDeviceIdByMerchantId(merchantId);
        if (CollectionUtils.isNotEmpty(deviceIds)) {
            for (String deviceId : deviceIds) {
                this.inventory(deviceId, "");
            }
            return ResponseVo.getSuccessResponse(NettyConst.SUCCESS);
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("未查询到该商户下的设备");
    }


    /**
     * 异步发送消息检测设备是否离线
     *
     * @param id 设备ID
     * @param ctxMap 通道map
     */
    private void asynTestDeviceIsAlive(final String id, final ConcurrentMap<String, ChannelHandlerContext> ctxMap) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                logger.info("设备ID：{}", id);
                try {
                    final String uuid = UuidUtils.getUuid();   // 消息标志
                    iCached.put(id + uuid, true, 300);
                    MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(id, ctxMap, true, uuid, "", TypeConstant.IS_ALIVE); // 发送消息

                    // 定时器10秒后去查询消息是否被修改为true,如果结果为true，则设备离线，否则设备没有离线
                    Timer timer = new Timer();
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            try {
                                Boolean result = (Boolean) iCached.get(id + uuid);
                                if (null != result && BooleanUtils.isFalse(result)) {  // 设备未离线

                                } else {
                                    asynSetDeviceOffline(id);   // 异步修改设备状态为离线
                                    LogUtils.sendDeviceOfflineMsgToInternal(id);
                                }
                                this.cancel(); // 取消当前定时器任务
                            } catch (Exception e) {
                                logger.error("从redis中获取消息出现异常：{}", e);
                            }
                        }
                    };
                    timer.schedule(timerTask, 10000); // 10秒后开始执行
                    Thread.sleep(20000); // 当前线程休眠20秒
                    timer.cancel(); // 取消当前任务队列
                }catch (InterruptedException e) {
                    logger.error("向设备发送消息出现中断异常：{}", e);
                } catch (Exception e) {
                    logger.error("向设备发送消息异常：{}", e);
                }
            }
        });

    }

    /**
     * 异步修改设备状态为离线
     * @param id 设备ID
     */
    private void asynSetDeviceOffline(final String id) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                logger.info("设备ID：{}", id);
                try {
                    DeviceInfo deviceInfo = new DeviceInfo();
                    deviceInfo.setId(id);
                    deviceInfo.setIstatus(50);  // 状态：10=未注册 20=正常 30=故障 40=报废 50=离线
                    deviceInfoService.updateBySelective(deviceInfo);
                } catch (Exception e) {
                    logger.error("异步修改设备状态为离线出现异常：{}", e);
                }
            }
        });
    }


    /**
     * 向设备发送运营广告位消息
     * @param sendOperatingAdvertisingDto 设备ID与运营位置
     * @return 发送结果
     */
    public ResponseVo<String> sendOperatingAdvertising(SendOperatingAdvertisingDto sendOperatingAdvertisingDto) throws Exception {
        String deviceIds = sendOperatingAdvertisingDto.getDeviceIds();
        String position = sendOperatingAdvertisingDto.getPosition();
        String merchantCode = sendOperatingAdvertisingDto.getMerchantCode();
        String userId = sendOperatingAdvertisingDto.getUserId();
        ResponseVo<AdListModelTemp> adListModelResponseVo = null;

        if (NettyConst.AD_ONE.equals(position)) {               // 运营广告位1
            adListModelResponseVo = deviceInfoService.selectOperatingAdOne(merchantCode);
            return sendAdToDevice(deviceIds, userId, TypeConstant.OPERATING_AD_ONE, adListModelResponseVo);

        } else if (NettyConst.AD_TWO.equals(position)) {        // 运营广告位2
            adListModelResponseVo = deviceInfoService.selectOperatingAdTwo(merchantCode);
            return sendAdToDevice(deviceIds, userId, TypeConstant.OPERATING_AD_TWO, adListModelResponseVo);

        } else if (NettyConst.AD_THREE.equals(position)) {      // 运营广告位3
            adListModelResponseVo = deviceInfoService.selectOperatingAdThree(merchantCode);
            return sendAdToDevice(deviceIds, userId, TypeConstant.OPERATING_AD_THREE, adListModelResponseVo);
        } else {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("运营广告位置参数不正确");
        }
    }

    /**
     * 向设备推送广告消息
     *
     * @param deviceIds             设备ID ,"," 拼接
     * @param userId                用户ID
     * @param type                  消息类型
     * @param adListModelResponseVo 广告模型
     * @return 发送消息结果
     */
    private ResponseVo<String> sendAdToDevice(String deviceIds, String userId, String type, ResponseVo<AdListModelTemp> adListModelResponseVo) {
        DeviceInfo temp = null;
        AdListModel tempModel = null;
        AdListModelTemp adListModel = adListModelResponseVo.getData();
        if (null == adListModel) {
            adListModel = new AdListModelTemp();
        }
        if (null == adListModel.getAdModelList()) {
            adListModel.setAdModelList(new ArrayList<AdModel>());
        }
        if (null == adListModel.getVcadModelList()) {
            adListModel.setVcadModelList(new ArrayList<AdModel>());
        }
        if (StringUtil.isNotBlank(deviceIds)) {
            String[] ids = deviceIds.split(",");
            if (ArrayUtils.isNotEmpty(ids)) {
                for (String id : ids) {
                    if (StringUtils.isNotBlank(id)) {
                        temp = deviceInfoService.selectByPrimaryKey(id);
                        tempModel = new AdListModel();
                        tempModel.setAdModelList(adListModel.getAdModelList());
                        if (null != temp) {
                            if (null != temp.getIscreenDisplayType() && temp.getIscreenDisplayType().intValue() == 20) {
                                tempModel.setAdModelList(adListModel.getVcadModelList());
                            }
                        }
                        MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(id, ctxMap, true, tempModel, userId, type);
                    }
                }
                return ResponseVo.getSuccessResponse("广告推送成功");
            }
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("未选择任何设备");
       /* if (null != adListModelResponseVo) {
            AdListModel adListModel = adListModelResponseVo.getData();
            if (null != adListModel) {
                String[] ids = deviceIds.split(",");
                if (ArrayUtils.isNotEmpty(ids)) {
                    for (String id : ids) {
                        if (StringUtils.isNotBlank(id)) {
                            MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(id, ctxMap, true, adListModel, userId, type);
                        }
                    }
                    return ResponseVo.getSuccessResponse("广告推送成功");
                }
            }
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("未查询到广告信息");*/
    }


    /**
     * 获取连接的TCP
     * @return
     */
    public ResponseVo<TcpResult> getConnectedTcp() {
        TcpResult tcpResult = new TcpResult();
        List<TcpVo> tcpVoList = new ArrayList<>();
        Set<Channel> channelSet = new HashSet<Channel>();

        // 所有连接的TCP，包括未注册和已经注册的
        channelSet.addAll(channelGroup);
        Set<Channel> tempChannelSet = new HashSet<>();      // 临时存储已经注册的TCP
        if (ctxMap != null && ctxMap.size() > 0) {
            for (Map.Entry<String, ChannelHandlerContext> entry : ctxMap.entrySet()) {
                tempChannelSet.add(entry.getValue().channel());
            }
        }
        channelSet.removeAll(tempChannelSet);   // 移除已经注册的TCP

        if (channelSet != null && CollectionUtils.isNotEmpty(channelSet)) {
            TcpVo tcpVo = null;
            for (Channel channel : channelSet) {
                tcpVo = new TcpVo();
                tcpVo.setChannelId(channel.id().asLongText());                          // 通道long ID
                tcpVo.setIp(IPUtils.getIpString(channel.remoteAddress().toString()));   // IP地址
                tcpVo.setPort(IPUtils.getPort(channel));                                // 端口号
                tcpVoList.add(tcpVo);
            }
            tcpResult.setTcpVoList(tcpVoList);
            return ResponseVo.getSuccessResponse(tcpResult);
        } else {
            tcpResult.setTcpVoList(tcpVoList);
            return ResponseVo.getSuccessResponse(tcpResult);
        }
    }


    /**
     * 获取注册到服务器的TCP
     *
     * @return
     */
    public ResponseVo<TcpResult> getRegisterTcp() {
        TcpResult tcpResult = new TcpResult();
        List<TcpVo> tcpVoList = new ArrayList<>();
        if (ctxMap != null && ctxMap.size() > 0) {
            TcpVo tcpVo = null;
            ChannelHandlerContext ctx = null;
            String deviceId = "";
            for (Map.Entry<String, ChannelHandlerContext> entry : ctxMap.entrySet()) {
                tcpVo = new TcpVo();
                ctx = entry.getValue();
                deviceId = entry.getKey();
                tcpVo.setDeviceId(deviceId);
                tcpVo.setIp(IPUtils.getIpString(ctx));
                tcpVo.setPort(IPUtils.getPort(ctx));
                try {
                    ClientVo clientVo = (ClientVo) iCached.hget(NettyConst.SYN_CLIENT_MAP, deviceId);
                    if (clientVo != null) {
                        tcpVo.setDeviceCode(clientVo.getDeviceCode());
                    }
                } catch (Exception e) {
                    logger.error("从redis中获取设备相关信息出现异常");
                }
                tcpVoList.add(tcpVo);
            }
            tcpResult.setTcpVoList(tcpVoList);
            return ResponseVo.getSuccessResponse(tcpResult);
        } else {
            tcpResult.setTcpVoList(tcpVoList);
            return ResponseVo.getSuccessResponse(tcpResult);
        }
    }

    /**
     * 断开未注册的TCP连接
     *
     * @param unRegisterTcpDto 要断开的channel 长ID
     * @return
     */
    public ResponseVo<String> disconnectUnregisterTcp(UnRegisterTcpDto unRegisterTcpDto) {
        String channelId = unRegisterTcpDto.getChannelId();
        Channel tempChannel = null;
        if (CollectionUtils.isNotEmpty(channelGroup)) {
            for (Channel channel : channelGroup) {
                if (channelId.equals(channel.id().asLongText())) {
                    tempChannel = channel;
                }
            }
        }

        if (tempChannel != null) {
            tempChannel.close();
            return ResponseVo.getSuccessResponse("断开未注册的TCP连接成功！");
        }

        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("断开未注册的TCP连接出现错误");
    }

    /**
     * 断开已经注册到服务器上的TCP连接
     * @param registerTcpDto 要断开的channel使用的设备ID
     * @return
     */
    public ResponseVo<String> disconnectRegisterTcp(RegisterTcpDto registerTcpDto) {
        String deviceId = registerTcpDto.getDeviceId();
        ChannelHandlerContext ctx = null;
        if (ctxMap != null && ctxMap.size() > 0) {
            for (Map.Entry<String, ChannelHandlerContext> entry : ctxMap.entrySet()) {
                if (deviceId.equals(entry.getKey())) {
                    ctx = entry.getValue();
                }
            }
        }

        if (ctx != null) {
            ctx.close();
            return ResponseVo.getSuccessResponse("断开已经注册到服务器上的TCP连接");
        }

        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("断开已经注册到服务器上的TCP连接出现错误");
    }


    /**
     * 主动发送消息
     *
     * @param deviceId 设备ID
     * @return
     */
    public ResponseVo<String> activeInventory(String deviceId) {
        try {
            ChannelHandlerContext ctx = ctxMap.get(deviceId);
            if (null == ctx) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("设备离线");
            }
            //发送给客户端的消息体
            MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, "", "", TypeConstant.LOCAL_ACTIVE_INVENTORY);
            return ResponseVo.getSuccessResponse(NettyConst.SUCCESS);
        } catch (Exception e) {
            logger.error("发送盘货消息出现异常:{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("发送消息失败");
    }



    /**
     * 云端设备 配置参数调整 发送消息到设备
     * @param configDto 参数
     * @throws Exception
     */
    public ResponseVo<String> cloudDeviceParamConfig(CloudParamConfigDto configDto) throws Exception {
        ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
        //组装发送参数
        OpenSdkConfigInfo configInfo = getOpenSdkConfigInfo(20, configDto.getCloudAppId(), configDto.getMerchantCode(), configDto.getShoppingInventory(),configDto.getReplenishmentInventory());
        if (configInfo == null) {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("组装发送数据错误");
        }

        if (configDto.getType().intValue() == 10) {
            for (String deviceId : configDto.getDevices()) {
                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, configInfo, null, TypeConstant.CLOUD_PARAM_CONFIG);
            }
        } else if (configDto.getType().intValue() == 20) {

            //查询商户云端货柜正常 故障 设备
            Map<String, Object> paramsMap = new HashMap<String, Object>();
            paramsMap.put("smerchantId", configDto.getMerchantId());
            paramsMap.put("ioperateStatus", 10);
            paramsMap.put("queryCondition"," and ISTATUS in (20,30) and ITYPE in (50,60)");
            List<DeviceInfo> deviceInfos = deviceInfoService.selectByMapWhere(paramsMap);
            if (null != deviceInfos && deviceInfos.size() > 0) {
                for (DeviceInfo info : deviceInfos) {
                    MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(info.getId(), ctxMap, true, configInfo, null, TypeConstant.CLOUD_PARAM_CONFIG);
                }
            }
        }
        return responseVo;
    }


    public OpenSdkConfigInfo getOpenSdkConfigInfo(int type, String scloudAppId, String smerchantCode, Integer shoppingInventory, Integer replenishmentInventory) throws Exception {
        OpenSdkConfigInfo openSdkConfigInfo = new OpenSdkConfigInfo();
        if ((StringUtil.isNotBlank(scloudAppId) && type == 20) || type == 10) {
            MerchantClientConf merchantClientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_CLIENT_CONFIG + smerchantCode);

            String app_id = merchantClientConf.getScloudAppId();
            if(StringUtil.isNotBlank(scloudAppId)) {
                app_id = scloudAppId;
            }

            if(type == 10 && null == replenishmentInventory) {
                replenishmentInventory = merchantClientConf.getIisOpeningInventory();
            }
            AppManage appManage = null;
            try {
                String str = (String) iCached.get(RedisConst.OP_APP_MANAGE + app_id);
                appManage = JSON.parseObject(str, AppManage.class);
            } catch (Exception e) {
                logger.error("从Redis中获取openSdk信息出现异常");
            }
            if (null == appManage) {
                return null;
            }
            openSdkConfigInfo.setAppId(app_id);
            openSdkConfigInfo.setAppSercetKey(appManage.getSappSecretKey());
            openSdkConfigInfo.setHost(merchantClientConf.getScloudHost());
            openSdkConfigInfo.setSappPrivateKey(appManage.getSappPrivateKey());
            openSdkConfigInfo.setSplatformPublicKey(appManage.getSplatformPublicKey());
        }
        //购物是否开启实时盘货
        if (shoppingInventory != null && shoppingInventory.intValue() == 1) {
            openSdkConfigInfo.setIisOpeningInventory("open");
        } else {
            if(type == 10 || (type == 20 && null != shoppingInventory)) {
                openSdkConfigInfo.setIisOpeningInventory("close");
            }
        }
        //补货是否开启实时盘货
        if (replenishmentInventory != null && replenishmentInventory == 1) {
            openSdkConfigInfo.setReplenstimer("reopen");
        } else {
            if(type == 10 || (type == 20 && null != replenishmentInventory)) {
                openSdkConfigInfo.setReplenstimer("reclose");
            }
        }

        return openSdkConfigInfo;
    }

    /**
     * 给设备发送消息
     *
     * @param deviceOperatingDto
     * @return
     */
    public ResponseVo<String> sendDeviceOperatingMsg(DeviceOperatingDto deviceOperatingDto) {
        String method = deviceOperatingDto.getMethod();
        String operator = deviceOperatingDto.getOperator();
        String smerchantCode = deviceOperatingDto.getSmerchantCode();
        String smerchantId = deviceOperatingDto.getSmerchantId();
        try {
            if (NettyConst.SHUTDOWN.equals(method)) {    // 关机
                logger.debug("后台操作设备关机开始");
                String shutdownVoStr = deviceOperatingDto.getData();
                ShutdownVo shutdownVo = JSON.parseObject(shutdownVoStr, ShutdownVo.class);
                mgrSendShutdownMsg(ctxMap, shutdownVo, smerchantId, method);
                return ResponseVo.getSuccessResponse("发送关机消息完成");

            } else if (NettyConst.UPGRADEVOICE.equals(method)) { // 升级语音
                logger.debug("后台操作升级语音开始");
                String upgradeVoiceVoStr = deviceOperatingDto.getData();
                UpgradeVoiceVo upgradeVoiceVo = JSON.parseObject(upgradeVoiceVoStr, UpgradeVoiceVo.class);
                mgrSendUpgradeVoiceMsg(ctxMap, upgradeVoiceVo, method, operator, smerchantId, smerchantCode);
                return ResponseVo.getSuccessResponse("发送升级语音消息完成");

            } else if (NettyConst.VOLUME.equals(method)) {      // 调节音量
                logger.debug("后台操作调节音量开始");
                String adjustVolumeStr = deviceOperatingDto.getData();
                AdjustVolumeVo adjustVolumeVo = JSON.parseObject(adjustVolumeStr, AdjustVolumeVo.class);
                mgrSendAdjustVolumeMsg(ctxMap, adjustVolumeVo, method, operator, smerchantId, smerchantCode);
                return ResponseVo.getSuccessResponse("后台操作调节音量完成");

            } else if (NettyConst.REBOOT.equals(method)) {      // 重启
                logger.debug("后台操作设备重启开始");
                String rebootStr = deviceOperatingDto.getData();
                RebootVo rebootVo = JSON.parseObject(rebootStr, RebootVo.class);
                mgrSendRebootMsg(ctxMap, rebootVo, method, operator, smerchantId, smerchantCode);
                return ResponseVo.getSuccessResponse("后台操作调节音量完成");

            }
        } catch (Exception e) {
            logger.error("发送消息出现异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("发送消息失败，消息类型：" + method);
    }

    /**
     * 设备重启操作消息推送
     *
     * @param ctxMap        通道集合
     * @param rebootVo      重启
     * @param method        方法参数
     * @param operator      操作员
     * @param smerchantId   商户ID
     * @param smerchantCode 商户编号
     */
    private void mgrSendRebootMsg(ConcurrentMap<String, ChannelHandlerContext> ctxMap, RebootVo rebootVo, String method, String operator, String smerchantId, String smerchantCode) {
        String type = rebootVo.getType();
        List<String> deviceIdList = new ArrayList<>();
        List<String> deviceCodeList = new ArrayList<>();
        if ("10".equals(type)) {    // 全部，按照商户ID查找所有正常状态的设备
            //查询商户视觉识别货柜正常 故障 设备
            Map<String, Object> paramsMap = new HashMap<String, Object>();
            paramsMap.put("smerchantId", smerchantId);
            paramsMap.put("ioperateStatus", 10);
            paramsMap.put("queryCondition", " and ISTATUS in (20,30) and ITYPE in (30,40,50,60)");
            List<DeviceInfo> deviceInfos = deviceInfoService.selectByMapWhere(paramsMap);
            if (CollectionUtils.isNotEmpty(deviceInfos)) {
                for (DeviceInfo deviceInfo : deviceInfos) {
                    deviceIdList.add(deviceInfo.getId());
                    deviceCodeList.add(deviceInfo.getScode());
                }
            }
        } else {
            deviceIdList = rebootVo.getDeviceIdList();
            deviceCodeList = rebootVo.getDeviceCodeList();
        }
        for (String deviceId : deviceIdList) {
            ChannelHandlerContext context = ctxMap.get(deviceId);
            if (null == context) {
                logger.error("设备ID：{} 离线，无法推送该设备重启消息", deviceId);
                continue;
            }
            MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, "mgr send reboot message", null, method);
        }
        logger.debug("发送重启消息完成，设备编号：{}", StringUtils.join(deviceCodeList, ","));
    }

    /**
     * 调节设备音量大小
     *
     * @param ctxMap         通道map
     * @param adjustVolumeVo 调节音量参数
     * @param method         方法名
     * @param operator       后台操作员
     * @param smerchantId    商户ID
     * @param smerchantCode  商户编号
     */
    private void mgrSendAdjustVolumeMsg(ConcurrentMap<String, ChannelHandlerContext> ctxMap, AdjustVolumeVo adjustVolumeVo, String method, String operator, String smerchantId, String smerchantCode) {
        String type = adjustVolumeVo.getType();
        List<String> deviceIdList = new ArrayList<>();
        List<String> deviceCodeList = new ArrayList<>();
        if ("10".equals(type)) {
            logger.debug("调节设备音量大小消息设备范围为：全部设备");
            //查询商户视觉识别货柜正常 故障 设备
            Map<String, Object> paramsMap = new HashMap<String, Object>();
            paramsMap.put("smerchantId", smerchantId);
            paramsMap.put("ioperateStatus", 10);
            paramsMap.put("queryCondition", " and ISTATUS in (20,30) and ITYPE in (30,40,50,60)");
            List<DeviceInfo> deviceInfos = deviceInfoService.selectByMapWhere(paramsMap);
            if (CollectionUtils.isNotEmpty(deviceInfos)) {
                for (DeviceInfo deviceInfo : deviceInfos) {
                    deviceIdList.add(deviceInfo.getId());
                    deviceCodeList.add(deviceInfo.getScode());
                }
            }
        } else {
            logger.debug("调节设备音量大小消息设备范围为：部分设备");
            deviceIdList = adjustVolumeVo.getDeviceIdList();
            deviceCodeList = adjustVolumeVo.getDeviceCodeList();
        }
        for (String deviceId : deviceIdList) {
            ChannelHandlerContext context = ctxMap.get(deviceId);
            if (null == context) {
                logger.error("设备ID：{} 离线，无法发送调节设备音量大小消息", deviceId);
                continue;
            }
            String volumeValue = adjustVolumeVo.getVolumeValue();
            VolumeModel volumeModel = new VolumeModel();
            volumeModel.setVolume(new Integer(volumeValue));
            volumeModel.setDetailId("detailId is Discard");
            MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, volumeModel, null, method);
        }
        logger.debug("调节设备音量大小消息完成，设备编号：{}", StringUtils.join(deviceCodeList, ","));

    }

    /**
     * 后台mgr发送升级语音消息
     *
     * @param ctxMap         通信map
     * @param upgradeVoiceVo 升级参数
     * @param method         方法名
     * @param operator       后台操作员
     * @param smerchantId    商户ID
     * @param smerchantCode  商户编号
     */
    private void mgrSendUpgradeVoiceMsg(ConcurrentMap<String, ChannelHandlerContext> ctxMap, UpgradeVoiceVo upgradeVoiceVo, String method, String operator, String smerchantId, String smerchantCode) {
        String type = upgradeVoiceVo.getType();
        List<String> deviceIdList = new ArrayList<>();
        List<String> deviceCodeList = new ArrayList<>();
        if ("10".equals(type)) {
            logger.debug("升级语音消息设备范围为：全部设备");
            //查询商户视觉识别货柜正常 故障 设备
            Map<String, Object> paramsMap = new HashMap<String, Object>();
            paramsMap.put("smerchantId", smerchantId);
            paramsMap.put("ioperateStatus", 10);
            paramsMap.put("queryCondition", " and ISTATUS in (20,30) and ITYPE in (30,40,50,60)");
            List<DeviceInfo> deviceInfos = deviceInfoService.selectByMapWhere(paramsMap);
            if (CollectionUtils.isNotEmpty(deviceInfos)) {
                for (DeviceInfo deviceInfo : deviceInfos) {
                    deviceIdList.add(deviceInfo.getId());
                    deviceCodeList.add(deviceInfo.getScode());
                }
            }
        } else {
            logger.debug("升级语音消息设备范围为：部分设备");
            deviceIdList = upgradeVoiceVo.getDeviceIdList();
            deviceCodeList = upgradeVoiceVo.getDeviceCodeList();
        }

        for (String deviceId : deviceIdList) {
            ChannelHandlerContext context = ctxMap.get(deviceId);
            if (null == context) {
                logger.error("设备ID：{} 离线，无法发送升级语音消息", deviceId);
                continue;
            }
            String url = upgradeVoiceVo.getUrl();         // 升级地址
            String voiceType = upgradeVoiceVo.getVoiceType();   // 升级语音类型
            String mainId = upgradeVoiceVo.getMainId();    // 升级记录主表主键ID
            String thankUrl = "noUrl";
            String shoppingUrl = "noUrl";
            String openDoorUrl = "noUrl";

            DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceId);
            if (null == deviceInfo) {
                logger.error("设备ID:{}不存在", deviceId);
                continue;
            }
            DeviceUpgradeDetails details = new DeviceUpgradeDetails();
            details.setIstatus(10);                                         // 10待处理 20升级成功 30升级失败
            details.setSmainId(mainId);                                  // 升级主表ID
            details.setSdeviceCode(deviceInfo.getScode());              // 设备编号
            details.setSdeviceAddress(deviceInfo.getSprovinceName() + deviceInfo.getScityName() + deviceInfo.getSareaName() + deviceInfo.getSputAddress());       // 设备地址
            details.setSdeviceId(deviceId);       // 设备ID
            deviceUpgradeDetailsService.insert(details);
            logger.debug("推送语音数据记录主表ID：{}，明细表ID：{}，设备ID：{}", mainId, details.getId(), deviceId);

            // 10 开门 20 购物 30 关门
            if (voiceType.equals("10")) {
                openDoorUrl = url;
            } else if (voiceType.equals("20")) {
                shoppingUrl = url;
            } else if (voiceType.equals("30")) {
                thankUrl = url;
            }
            // 封装语音升级参数对象
            VoiceModel voiceModel = new VoiceModel();
            voiceModel.setOpenDoorUrl(openDoorUrl);
            voiceModel.setShoppingUrl(shoppingUrl);
            voiceModel.setThanksUrl(thankUrl);
            voiceModel.setDetailId(details.getId());
            MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, voiceModel, null, method);
        }
        logger.debug("发送升级语音消息完成，设备编号：{}", StringUtils.join(deviceCodeList, ","));
    }

    /**
     * 后台mgr发送关机消息
     *
     * @param ctxMap      通信map
     * @param shutdownVo  关机参数
     * @param smerchantId 商户ID
     * @param method      方法名
     */
    private void mgrSendShutdownMsg(ConcurrentMap<String, ChannelHandlerContext> ctxMap, ShutdownVo shutdownVo, String smerchantId, String method) {
        String type = shutdownVo.getType();
        List<String> deviceIdList = new ArrayList<>();
        List<String> deviceCodeList = new ArrayList<>();
        if ("10".equals(type)) {    // 全部，按照商户ID查找所有正常状态的设备
            //查询商户视觉识别货柜正常 故障 设备
            Map<String, Object> paramsMap = new HashMap<String, Object>();
            paramsMap.put("smerchantId", smerchantId);
            paramsMap.put("ioperateStatus", 10);
            paramsMap.put("queryCondition", " and ISTATUS in (20,30) and ITYPE in (30,40,50,60)");
            List<DeviceInfo> deviceInfos = deviceInfoService.selectByMapWhere(paramsMap);
            if (CollectionUtils.isNotEmpty(deviceInfos)) {
                for (DeviceInfo deviceInfo : deviceInfos) {
                    deviceIdList.add(deviceInfo.getId());
                    deviceCodeList.add(deviceInfo.getScode());
                }
            }
        } else {
            deviceIdList = shutdownVo.getDeviceIdList();
            deviceCodeList = shutdownVo.getDeviceCodeList();
        }
        for (String deviceId : deviceIdList) {
            ChannelHandlerContext context = ctxMap.get(deviceId);
            if (null == context) {
                logger.error("设备ID：{} 离线，无法推送后台mgr发送关机消息", deviceId);
                continue;
            }
            MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, "mgr send shutdown message", null, method);
        }
        logger.debug("发送关机消息完成，设备编号：{}", StringUtils.join(deviceCodeList, ","));
    }

    /**
     * 安卓发送请求移除map中的通道
     *
     * @param deviceId 设备ID
     * @return
     */
    public ResponseVo<String> removeChannelByDeviceId(String deviceId) {
        try {
            ChannelHandlerContext channelHandlerContext = ctxMap.get(deviceId);
            if (null != channelHandlerContext) {
                channelHandlerContext.close();
            }
            ctxMap.remove(deviceId);
            logger.debug("移除通信map中对应设备ID的通道完成");
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("移除通信map中对应设备ID的通道失败");
        }
    }
}
