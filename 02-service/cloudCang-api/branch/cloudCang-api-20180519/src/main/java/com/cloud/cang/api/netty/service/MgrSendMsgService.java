package com.cloud.cang.api.netty.service;

import com.cloud.cang.api.common.ReturnCodeEnum;
import com.cloud.cang.api.common.utils.CommonUtils;
import com.cloud.cang.api.common.utils.MsgToJsonUtils;
import com.cloud.cang.api.common.utils.UuidUtils;
import com.cloud.cang.api.sb.service.DeviceInfoService;
import com.cloud.cang.api.singleton.SingletonClientMap;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.common.TypeConstant;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.NettyConst;
import com.cloud.cang.model.AdListModel;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.sb.DeviceDto;
import com.cloud.cang.sb.SendOperatingAdvertisingDto;
import io.netty.channel.ChannelHandlerContext;
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

    private static final Logger logger = LoggerFactory.getLogger(MgrSendMsgService.class);
    private static ConcurrentMap<String, ChannelHandlerContext> ctxMap = SingletonClientMap.newInstance().getCtxMap();  //在线设备map
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
            if (clientIpList.size() == 1) {
                return sendMsgToOne(clientIpList.get(0), methodType, data, userId);
            } else {
                for (String str : clientIpList) {
                    sendMsgToOne(str, methodType, data, userId);
                }
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
                String resultMsg = MsgToJsonUtils.setAndroidMsg(true, 200, data, deviceId, userId, methodType);
                if (StringUtils.isNotBlank(resultMsg)) {
                    ctx.writeAndFlush(resultMsg);
                    return ResponseVo.getSuccessResponse(NettyConst.SUCCESS);
                }
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
            ChannelHandlerContext ctx = ctxMap.get(deviceId);
            //发送给客户端的消息体
//            String msg = MsgToJsonUtils.setAndroidMsg(true, null, deviceId, userId, TypeConstant.INVENTORY);
            MsgToJsonUtils.asynSendMsgByCtxMap(deviceId, ctxMap, true, "", userId, TypeConstant.INVENTORY);
//            ctx.writeAndFlush(msg);
            return ResponseVo.getSuccessResponse(NettyConst.SUCCESS);
        } catch (Exception e) {
            logger.error("发送盘货消息出现异常:{}", e);
        }
        return new ResponseVo<>(false, 10002, "发送盘货指令失败");
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
                        //异步修改设备状态
                        asynSetDeviceOffline(id);
                    } else {
                        // 异步发送消息，然后检测是否收到消息，收到消息显示在线否则修改为离线
                        asynTestDeviceIsAlive(id, ctxMap);
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
                    MsgToJsonUtils.asynSendMsgByCtxMap(id, ctxMap, true, uuid, "", TypeConstant.IS_ALIVE); // 发送消息

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
        String deviceId = sendOperatingAdvertisingDto.getDeviceId();
        String position = sendOperatingAdvertisingDto.getPosition();
        String userId = sendOperatingAdvertisingDto.getUserId();
        ResponseVo<AdListModel> adListModelResponseVo = null;
        if (NettyConst.AD_ONE.equals(position)) {               // 运营广告位1
            adListModelResponseVo =  deviceInfoService.selectOperatingAdOne(deviceId);
            return sendAdToDevice(deviceId, userId, TypeConstant.OPERATING_AD_ONE, adListModelResponseVo);

        } else if (NettyConst.AD_TWO.equals(position)) {        // 运营广告位2
            adListModelResponseVo =  deviceInfoService.selectOperatingAdOne(deviceId);
            return sendAdToDevice(deviceId, userId, TypeConstant.OPERATING_AD_TWO, adListModelResponseVo);

        } else if (NettyConst.AD_THREE.equals(position)) {      // 运营广告位3
            adListModelResponseVo =  deviceInfoService.selectOperatingAdOne(deviceId);
            return sendAdToDevice(deviceId, userId, TypeConstant.OPERATING_AD_THREE, adListModelResponseVo);

        } else {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("运营广告位置参数不正确");
        }
    }

    /**
     * 向设备推送广告消息
     * @param deviceId 设备ID
     * @param userId 用户ID
     * @param type 消息类型
     * @param adListModelResponseVo 广告模型
     * @return 发送消息结果
     */
    private ResponseVo<String> sendAdToDevice(String deviceId,String userId,String type, ResponseVo<AdListModel> adListModelResponseVo) {
        if (null != adListModelResponseVo) {
            AdListModel adListModel = adListModelResponseVo.getData();
            if (null != adListModel) {
                MsgToJsonUtils.asynSendMsgByCtxMap(deviceId, ctxMap, true, adListModel, userId, type);
            }
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("未查询到广告信息");
    }


}
