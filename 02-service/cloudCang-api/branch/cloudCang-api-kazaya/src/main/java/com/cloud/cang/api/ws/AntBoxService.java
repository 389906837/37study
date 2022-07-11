package com.cloud.cang.api.ws;

import com.cloud.cang.api.antbox.AntboxDeviceContextRegistry;
import com.cloud.cang.api.antbox.BoxContext;
import com.cloud.cang.api.antbox.InventoryContext;
import com.cloud.cang.api.antbox.constant.BizError;
import com.cloud.cang.api.antbox.constant.BoxStatus;
import com.cloud.cang.api.antbox.constant.ParamKey;
import com.cloud.cang.api.antbox.exception.BoxStatusLimitedException;
import com.cloud.cang.api.antbox.exception.OperationException;
import com.cloud.cang.dispatcher.annotation.RegisterRestResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/command")
@RegisterRestResource
public class AntBoxService {

    @RequestMapping("/getDeviceInfo")
    public Object getDeviceInfo(long deviceId) {
        BoxContext ctx = AntboxDeviceContextRegistry.lookup(deviceId);
        try {
            ctx.getCommandSender().getBoxInfo();
        } catch (Exception e) {
            ctx.logError(e, e.getMessage());
            throw new OperationException(BizError.SEND_COMMAND_FAIL, e);
        }
        return null;
    }

    @RequestMapping("/openDoor")
    public Object openDoor(long deviceId) {
        BoxContext ctx = AntboxDeviceContextRegistry.lookup(deviceId);
        try {
            ctx.getCommandSender().openDoor();
        } catch (Exception e) {
            ctx.logError(e, e.getMessage());
            throw new OperationException(BizError.SEND_COMMAND_FAIL, e);
        }
        return null;
    }

    /*@RequestMapping("/reboot")
    public Object reboot(long deviceId) {
        BoxContext ctx = AntboxDeviceContextRegistry.lookup(deviceId);
        try {
            ctx.getCommandSender().reboot();
        } catch (Exception e) {
            ctx.logError(e, e.getMessage());
            throw new OperationException(BizError.SEND_COMMAND_FAIL, e);
        }
        return null;
    }*/
    /**
     * 盘货
     * @param deviceId       设备ID
     * @param isForTest      是否为测试盘点
     * @param deviceId        售货机id
     * @return
     */
    @RequestMapping("/inventory")
    public Object inventory(boolean isForTest, long deviceId) {
        BoxContext ctx = AntboxDeviceContextRegistry.lookup(deviceId);
        if (ctx == null)
            throw new OperationException(BizError.DEVICE_NOT_FOUND);
        InventoryContext inventoryContext = ctx.getInventoryContext();
        try {
            if (!ctx.isAllowInventoryNow())
                throw new BoxStatusLimitedException(
                        "Unable to orderSend command[inventory] in status: Non-idle",
                        new BoxStatus[]{BoxStatus.INIT, BoxStatus.IDLE},
                        ctx.getBoxInfo().getStatus());

            inventoryContext.presetInventoryConfig(isForTest, 1, 0);

            ctx.getCommandSender().inventory();
        } catch (Exception e) {
            ctx.logError(e, e.getMessage());
            throw new OperationException(BizError.SEND_COMMAND_FAIL, e);
        }
        return null;
    }

    /**
     * 批量盘货
     * @param deviceId
     * @param inventoryTimes
     * @param roundInterval
     * @return
     */
    @RequestMapping("/inventory/batch")
    public Object batchInventory(long deviceId, int inventoryTimes, int roundInterval) {
        BoxContext ctx = AntboxDeviceContextRegistry.lookup(deviceId);
        if (ctx == null)
            throw new OperationException(BizError.DEVICE_NOT_FOUND);

        InventoryContext inventoryContext = ctx.getInventoryContext();
        try {
            if (!ctx.isAllowInventoryNow())
                throw new BoxStatusLimitedException(
                        "Unable to orderSend command[inventory] in status: Non-idle",
                        new BoxStatus[]{BoxStatus.INIT, BoxStatus.IDLE},
                        ctx.getBoxInfo().getStatus());

            inventoryContext.presetInventoryConfig(true, inventoryTimes, roundInterval);

            ctx.getCommandSender().inventory();
        } catch (Exception e) {
            ctx.logError(e, e.getMessage());
            throw new OperationException(BizError.SEND_COMMAND_FAIL, e);
        }
        return null;
    }

    /**
     * 停止下一轮盘点
     * @param deviceId
     * @return
     */
    @RequestMapping("/inventory/stopNextRound")
    public Object stopNextRoundInventory(long deviceId) {
        BoxContext ctx = AntboxDeviceContextRegistry.lookup(deviceId);
        if (ctx == null)
            throw new OperationException(BizError.DEVICE_NOT_FOUND);

        try {
            ctx.stopNextRoundInventory();
        } catch (BoxStatusLimitedException e) {
        }
        return null;
    }

    /**
     * 获取售货机设置参数
     * @return
     */
    @RequestMapping("/property/get")
    public Object getParam(long deviceId,int paramCode) {
        BoxContext ctx = AntboxDeviceContextRegistry.lookup(deviceId);
        if (ctx == null)
            throw new OperationException(BizError.DEVICE_NOT_FOUND);
        List<Integer> codes = new ArrayList<>();
        codes.add(paramCode);
        ParamKey[] keys = new ParamKey[codes.size()];
        int index = 0;
        for (int code : codes) {
            ParamKey pk = ParamKey.getByCode(Byte.parseByte(Integer.toHexString(code)));
            keys[index++] = pk;
        }

        try {
            ctx.getCommandSender().getDeviceParam(keys);
        } catch (Exception e) {
            ctx.logError(e, e.getMessage());
            throw new OperationException(BizError.SEND_COMMAND_FAIL, e);
        }
        return null;
    }

    @RequestMapping("/playAudio")
    public Object playAudio(long deviceId,short audioNo) {
        BoxContext ctx = AntboxDeviceContextRegistry.lookup(deviceId);
        try {
            ctx.getCommandSender().playAudio(audioNo);
        } catch (Exception e) {
            ctx.logError(e, e.getMessage());
            throw new OperationException(BizError.SEND_COMMAND_FAIL, e);
        }
        return null;
    }

    @RequestMapping("/setDeviceParam")
    public Object setDeviceParam(long deviceId,short code,short value) {
        BoxContext ctx = AntboxDeviceContextRegistry.lookup(deviceId);
        try {
            ParamKey pk = ParamKey.getByCode(Byte.parseByte(Integer.toHexString(code)));
            ctx.getCommandSender().setDeviceParam(pk,value);
        } catch (Exception e) {
            ctx.logError(e, e.getMessage());
            throw new OperationException(BizError.SEND_COMMAND_FAIL, e);
        }
        return null;
    }


}
