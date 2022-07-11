package com.cloud.cang.api.netty.service;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.api.common.ReturnCodeEnum;
import com.cloud.cang.api.netty.vo.ClientVo;
import com.cloud.cang.api.singleton.SingletonClientMap;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.common.TypeConstant;
import com.cloud.cang.core.common.NettyConst;
import com.cloud.cang.pojo.BaseRequestVo;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentMap;

/**
 * @version 1.0
 * @ClassName NettyService
 * @Description netty后台服务类
 * @Author zengzexiong
 * @Date 2018年1月23日15:31:25
 */
@Service("socketIOService")
public class SocketIOService {

    @Autowired
    private ICached iCached;

    private static final Logger logger = LoggerFactory.getLogger(SocketIOService.class);
    private static ConcurrentMap<String, ChannelHandlerContext> ctxMap = SingletonClientMap.newInstance().getCtxMap();
    private ChannelHandlerContext context = null;

    /**
     * 向设备发送开门指令（1）
     *
     * @param deviceId 设备ID
     * @param userId   用户ID
     * @return 发送结果
     * @throws Exception
     */
    public ResponseVo<String> openDoor(String deviceId, String userId, Integer type) throws Exception {
        logger.info("准备发送设备开门消息");
        ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
        //先判断门是否已经打开
        ClientVo clientVo = (ClientVo) iCached.hget(NettyConst.SYN_CLIENT_MAP, deviceId);   //从redis中获取设备相关信息
        Integer doorStatus = null;
        if (null != clientVo) {
            doorStatus = clientVo.getDoor();    //门的状态 10关，20开
        }

        //门处于打开状态
        if (null != doorStatus && Integer.valueOf(20).equals(doorStatus)) {
            logger.debug("设备门已经打开");
            return new ResponseVo<>(false, ReturnCodeEnum.ERROR_DOOR_OPEN.getCode(), "设备门已经打开");
        }

        context = ctxMap.get(deviceId);
        //设备未连接到服务器，处于离线状态
        if (null == context) {
            logger.error("设备离线:{}", deviceId);
            return new ResponseVo<>(false, ReturnCodeEnum.ERROR_DEVICE_OFFLINE.getCode(), "设备离线");
        }

        //拼装消息体 = 用户ID，设备ID，操作类型，10顾客/20补货员/30装机员
        String msg = assemblySendMsg(userId, deviceId, TypeConstant.OPEN_DOOR, type.toString());
        context.writeAndFlush(msg);


        logger.info("发送设备开门消息成功");
        return responseVo;
    }



    /**
     * 拼装消息体
     * @param userId    用户ID
     * @param deviceID  设备ID
     * @param methodType  操作类型
     * @param object    操作参数（部分操作必填）
     * @return 消息发送结果
     */
    private String assemblySendMsg(String userId,String deviceID,String methodType,Object object) {
        BaseRequestVo baseRequestVo = new BaseRequestVo();  //  发送给设备的消息体对象

        //需要参数的操作类型
        if (methodType.equals(NettyConst.SYSTEMUPGRADE_TYPE) || methodType.equals(NettyConst.UPGRADEVOICE_TYPE)) { //系统升级地址 || 升级语音

        } else if (methodType.equals(NettyConst.TIMINGDEVICE_TYPE)) {  //定时开关机

        } else if (methodType.equals(NettyConst.VOLUME_TYPE)) {   //调节音量

        }

        baseRequestVo.setDeviceId(deviceID);
        baseRequestVo.setUserId(userId);
        baseRequestVo.setSuccess(true);
        baseRequestVo.setMethodType(methodType);
        return JSON.toJSONString(baseRequestVo) + System.lineSeparator();
    }

}
