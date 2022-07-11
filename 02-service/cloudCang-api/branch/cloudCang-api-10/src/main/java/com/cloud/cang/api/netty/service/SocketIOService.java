package com.cloud.cang.api.netty.service;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.api.common.ReturnCodeEnum;
import com.cloud.cang.api.common.utils.MsgToJsonUtils;
import com.cloud.cang.api.netty.vo.ClientVo;
import com.cloud.cang.api.sb.service.DeviceInfoService;
import com.cloud.cang.api.singleton.SingletonClientMap;
import com.cloud.cang.api.socketIo.vo.SocketResponseVo;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.common.TypeConstant;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.NettyConst;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.inventory.CommodityDiffVo;
import com.cloud.cang.inventory.InventoryDto;
import com.cloud.cang.inventory.InventoryResult;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.rm.ReplenishmentDto;
import com.cloud.cang.rm.ReplenishmentResult;
import com.cloud.cang.rm.StockServicesDefine;
import com.cloud.cang.utils.StringUtil;
import com.corundumstudio.socketio.SocketIOClient;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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
    @Autowired
    DeviceInfoService deviceInfoService;
    private static final Logger logger = LoggerFactory.getLogger(SocketIOService.class);
    private static ConcurrentMap<String, ChannelHandlerContext> ctxMap = SingletonClientMap.newInstance().getCtxMap();

    private static ConcurrentMap<String, SocketIOClient> socketIoMap = SingletonClientMap.newInstance().getSocketIoMap();   //socketIo通道

    /**
     * 向设备发送开门指令（1）
     *
     * @param deviceId 设备ID
     * @param userId   用户ID
     * @return 发送结果
     * @throws Exception
     */
    public synchronized ResponseVo<String> openDoor(String deviceId, String userId, Integer type) throws Exception {
        logger.debug("准备向用户：{}发送设备开门消息", userId);
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

        ChannelHandlerContext ctx = ctxMap.get(deviceId);
        //设备未连接到服务器，处于离线状态
        if (null == ctx) {
            logger.error("设备离线:{}", deviceId);
            return new ResponseVo<>(false, ReturnCodeEnum.ERROR_DEVICE_OFFLINE.getCode(), "设备离线");
        }

        //拼装消息体 = 用户ID，设备ID，操作类型，10顾客/20补货员/30装机员
//        MsgToJsonUtils.asynSendMsgByCtx(deviceId, ctx, true, type, userId, TypeConstant.OPEN_DOOR);
        DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceId);
        //type 10 购物开门 20 补货开门
        if (type == 10) {
            if (deviceInfo.getItype() == 40 || deviceInfo.getItype() == 60) {
                iCached.put("shopping_" + userId, userId, 2 * 60 * 60);
                MsgToJsonUtils.asynSendMsgByCtx(deviceId, ctx, true, type, userId, TypeConstant.OPEN_DOOR_CHECK, 20);
            } else {
                if (deviceInfo.getItype() == 50) {
                    iCached.put("cloud_open_door_" + userId, userId, 2 * 60 * 60);
                }
                MsgToJsonUtils.asynSendMsgByCtx(deviceId, ctx, true, type, userId, TypeConstant.OPEN_DOOR, 20);
            }
        } else {
            if ((deviceInfo.getItype() == 40 || deviceInfo.getItype() == 60) && deviceInfo.getIsupportWeigh() == 1) {
                MsgToJsonUtils.asynSendMsgByCtx(deviceId, ctx, true, type, userId, TypeConstant.OPEN_DOOR_CHECK, 20);
            } else {
                if (deviceInfo.getItype() == 50) {
                    iCached.put("cloud_open_door_" + userId, userId, 2 * 60 * 60);
                }
                MsgToJsonUtils.asynSendMsgByCtx(deviceId, ctx, true, type, userId, TypeConstant.OPEN_DOOR, 20);
            }
        }
        logger.debug("向用户：{}发送设备开门消息成功", userId);
        return responseVo;
    }

}
