package com.cloud.cang.api.antbox.api;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.api.antbox.AntboxDeviceContextRegistry;
import com.cloud.cang.api.antbox.BoxContext;
import com.cloud.cang.api.antbox.constant.BoxBizCode;
import com.cloud.cang.api.antbox.constant.BoxStatus;
import com.cloud.cang.api.antbox.dto.BoxOpenDoorSource;
import com.cloud.cang.api.antbox.dto.CustomerDto;
import com.cloud.cang.api.antbox.exception.*;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.NettyConst;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.sb.DeviceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: 37cang
 * @description:
 * @author: qzg
 * @create: 2019-08-22 10:02
 **/
public class AntCommandUtils {
    private static final Logger logger = LoggerFactory.getLogger(AntCommandUtils.class);
    /**
     * 开门
     * @param userId 用户ID
     * @param deviceInfo 设备信息
     * @param type 10：购物开门 20：补货员开门
     * @return
     * @throws Exception
     */
    public static ResponseVo<String> openDoor(String userId, Integer type, DeviceInfo deviceInfo){
        Long boxId = Long.valueOf(deviceInfo.getSreaderSerialNumber());
        String deviceId = deviceInfo.getId();
        logger.info("request open door , userId : {} , boxId : {} ,deviceId : {} ,  distribution : {}", userId, boxId,deviceId,type);
        ResponseVo<String> resVo = ResponseVo.getSuccessResponse();
        resVo.setSuccess(false);
        BoxContext ctx = AntboxDeviceContextRegistry.lookup(boxId);
        if(ctx == null){
            logger.error("box is not register current serve：{}",boxId);
            resVo.setErrorCode(-1000);
            resVo.setMsg("box is not register current server");
            return resVo;
        }

        // 先检查售货机是否离线
        try {
            ctx.checkHeartbeatTimeout();
        } catch (BoxHeartbeatTimeoutException e) {
            logger.error("box DEVICE_LOST, boxId : {}", boxId);
            resVo.setErrorCode(-1001);
            resVo.setMsg("box DEVICE_LOST");
            return resVo;
        }

        //
        if (ctx.getBoxInfo().getStatus() == BoxStatus.LOGIN) {
            logger.error("box current status is login , boxId : {}", boxId);
            resVo.setErrorCode(-1002);
            resVo.setMsg("box current status is login");
            return resVo;
        }
        // 门已打开
        if (ctx.getBoxInfo().getStatus() == BoxStatus.OPENED) {
            logger.error("box  current status is opened , boxId : {}", boxId);
            resVo.setErrorCode(-1003);
            resVo.setMsg("box is opened");
            return resVo;
        }

        // 盘点中
        if (ctx.getBoxInfo().getStatus() == BoxStatus.BUSY) {
            logger.error("box current status  is busy , boxId : {}", boxId);
            resVo.setErrorCode(-1004);
            resVo.setMsg("box is busy");
            return resVo;
        }

        // 用户登录
        if(ctx.getCurrentCustomerDto() == null){
            CustomerDto user = new CustomerDto(userId);
            user.setDistribution(type == 10 ? false : true );
            user.setBoxOpenDoorSource(BoxOpenDoorSource.KAZAYA);
            user.setDeviceId(deviceId);
            try {
                ctx.login(user);
            } catch (BoxStatusLimitedException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                resVo.setSuccess(false);
                resVo.setErrorCode(-1005);
                resVo.setMsg(e.getMessage());
                return resVo;
            } catch (BoxTradingWithOtherException e) {
                logger.error("box current status  is busy , boxId : {}", boxId);
                resVo.setSuccess(false);
                resVo.setErrorCode(-1004);
                resVo.setMsg("box is busy");
                return resVo;
            } catch (BoxTradingWithSelfException e) {
                // ignore
            } catch (BoxHeartbeatTimeoutException e) {
                logger.error("box DEVICE_LOST , boxId : {} ", boxId);
                resVo.setSuccess(false);
                resVo.setErrorCode(-1001);
                resVo.setMsg("box is busy");
                return resVo;
            }
        }

        // 发送开门指令
        try {
            ctx.getCommandSender().openDoor();
        } catch (Exception e){
            logger.error("send open command fail : ",e);
            resVo.setSuccess(false);
            resVo.setErrorCode(-1006);
            resVo.setMsg("open fail");
            return resVo;
        }
        resVo.setSuccess(true);
        resVo.setMsg("Open Success");
        return resVo;
    }
}
