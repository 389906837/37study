package com.cloud.cang.api.netty.service;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.api.common.ReturnCodeEnum;
import com.cloud.cang.api.common.utils.MsgToJsonUtils;
import com.cloud.cang.api.hy.service.MemberInfoService;
import com.cloud.cang.api.netty.vo.ClientVo;
import com.cloud.cang.api.sb.service.DeviceInfoService;
import com.cloud.cang.api.singleton.SingletonClientMap;
import com.cloud.cang.api.sl.service.DeviceOperService;
import com.cloud.cang.api.socketIo.vo.SessionVo;
import com.cloud.cang.api.socketIo.vo.SocketResponseVo;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.common.TypeConstant;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.NettyConst;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.inventory.CommodityDiffVo;
import com.cloud.cang.inventory.InventoryDto;
import com.cloud.cang.inventory.InventoryResult;
import com.cloud.cang.jy.ExceptionOrderCommodityDto;
import com.cloud.cang.jy.ExceptionOrderDto;
import com.cloud.cang.jy.OrderDiscountDefine;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sl.DeviceOper;
import com.cloud.cang.pojo.BaseResponseVo;
import com.cloud.cang.rm.ReplenishmentDto;
import com.cloud.cang.rm.ReplenishmentResult;
import com.cloud.cang.rm.StockServicesDefine;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import com.corundumstudio.socketio.SocketIOClient;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * @version 1.0
 * @ClassName NettyService
 * @Description netty???????????????
 * @Author zengzexiong
 * @Date 2018???1???23???15:31:25
 */
@Service("socketIOService")
public class SocketIOService {

    @Autowired
    private ICached iCached;
    @Autowired
    DeviceInfoService deviceInfoService;
    @Autowired
    MemberInfoService memberInfoService;
    @Autowired
    DeviceOperService deviceOperService;
    private static final Logger logger = LoggerFactory.getLogger(SocketIOService.class);
    private static ConcurrentMap<String, ChannelHandlerContext> ctxMap = SingletonClientMap.newInstance().getCtxMap();

    private static ConcurrentMap<String, SocketIOClient> socketIoMap = SingletonClientMap.newInstance().getSocketIoMap();   //socketIo??????

    /**
     * ??????????????????????????????1???
     *
     * @param deviceId ??????ID
     * @param userId   ??????ID
     * @return ????????????
     * @throws Exception
     */
    public synchronized ResponseVo<String> openDoor(String deviceId, String userId, Integer type) throws Exception {
        logger.debug("??????????????????{}????????????????????????", userId);
        ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
        //??????????????????????????????
        ClientVo clientVo = (ClientVo) iCached.hget(NettyConst.SYN_CLIENT_MAP, deviceId);   //???redis???????????????????????????
        Integer doorStatus = null;
        if (null != clientVo) {
            doorStatus = clientVo.getDoor();    //???????????? 10??????20???
        }

        //?????????????????????
        if (null != doorStatus && Integer.valueOf(20).equals(doorStatus)) {
            logger.debug("?????????????????????");
            return new ResponseVo<>(false, ReturnCodeEnum.ERROR_DOOR_OPEN.getCode(), "?????????????????????");
        }

        ChannelHandlerContext ctx = ctxMap.get(deviceId);
        //????????????????????????????????????????????????
        if (null == ctx) {
            logger.error("????????????:{}", deviceId);
            return new ResponseVo<>(false, ReturnCodeEnum.ERROR_DEVICE_OFFLINE.getCode(), "????????????");
        }

        //??????????????? = ??????ID?????????ID??????????????????10??????/20?????????/30?????????
//        MsgToJsonUtils.asynSendMsgByCtx(deviceId, ctx, true, type, userId, TypeConstant.OPEN_DOOR);
        DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceId);
        //type 10 ???????????? 20 ????????????
        if (type == 10) {
          /*  String token = (String) iCached.get("closeDoorOvertime_" + userId + "_" + deviceId);
            if (StringUtil.isNotBlank(token)) {
                MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(userId);*/
            closeDoorOvertime(deviceId, userId, 50);
          /*  }*/
            iCached.put("closeDoorOvertime_" + userId + "_" + deviceId, DateUtils.getCurrentTimeNumber(), 24 * 60 * 60);
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
        logger.debug("????????????{}??????????????????????????????", userId);
        return responseVo;
    }

    /**
     * ????????????????????????
     *
     * @param
     */
    private void closeDoorOvertime(/*final*/ String deviceId, /*final*/ String userId /*final*/, /*final*/ Integer type) {
        logger.debug("??????????????????????????????");
     /*   ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {*/
        try {
            String token = (String) iCached.get("closeDoorOvertime_" + userId + "_" + deviceId);
            if (StringUtil.isNotBlank(token)) {
                MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(userId);
                DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceId);
                //??????????????????
                DeviceOper deviceOper = deviceOperService.selectByUserIdAndDeviceCode(userId, deviceInfo.getScode());
                Integer iclientType = null;
                if (null == deviceOper) {
                    iclientType = memberInfo.getIlastShopPlatform();
                }else {
                    iclientType = deviceOper.getIclientType();
                }
                generateExceptionOrder(deviceInfo, memberInfo, iclientType, type);
                iCached.remove("closeDoorOvertime_" + userId + "_" + deviceId);
                //??????????????????
                DeviceOper temp = new DeviceOper();
                temp.setId(deviceOper.getId());
                temp.setTcloseTime(DateUtils.getCurrentDateTime());
                deviceOperService.updateBySelective(temp);
            }
        } catch (Exception e) {
            logger.info("???????????????????????????????????????{}", e);
        }
       /*     }
        });*/
    }

    /***
     * ????????????????????????
     * @param deviceInfo ????????????
     * @param memberInfo ????????????
     * @throws Exception
     */
    private ResponseVo<String> generateExceptionOrder(DeviceInfo deviceInfo, MemberInfo memberInfo, Integer isourceClientType, Integer itype) {
        try {
            //????????????????????????
            ExceptionOrderDto exceptionOrderDto = new ExceptionOrderDto();
            exceptionOrderDto.setSdeviceId(deviceInfo.getId());
            exceptionOrderDto.setSdeviceCode(deviceInfo.getScode());
            exceptionOrderDto.setSdeviceAddress(CoreUtils.generateDeviceAddress(deviceInfo));
            exceptionOrderDto.setSreaderSerialNumber(deviceInfo.getSreaderSerialNumber());
            exceptionOrderDto.setSmerchantId(deviceInfo.getSmerchantId());
            exceptionOrderDto.setSmerchantCode(deviceInfo.getSmerchantCode());
            exceptionOrderDto.setSmemberCode(memberInfo.getScode());
            exceptionOrderDto.setSmemberId(memberInfo.getId());
            exceptionOrderDto.setSmemberName(memberInfo.getSmemberName());
            exceptionOrderDto.setSdeviceName(deviceInfo.getSname());
            exceptionOrderDto.setIsourceClientType(isourceClientType);
            exceptionOrderDto.setItype(itype);//????????????

            BigDecimal allAmount = new BigDecimal("0");
            List<ExceptionOrderCommodityDto> commodityDtos = new ArrayList<ExceptionOrderCommodityDto>();

            exceptionOrderDto.setExceptionOrderCommodityDtoList(commodityDtos);
            exceptionOrderDto.setFtotalAmount(allAmount);
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(OrderDiscountDefine.CREATE_EXCEPTION_ORDER);// ????????????
            // ??????????????????????????????????????????????????????????????????????????????
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
            });
            invoke.setRequestObj(exceptionOrderDto); // post ??????
            ResponseVo<String> resVO = (ResponseVo<String>) invoke.invoke();
            return resVO;
        } catch (Exception e) {
            logger.error("???????????????????????????{}", e);
        }
        return null;
    }


}
