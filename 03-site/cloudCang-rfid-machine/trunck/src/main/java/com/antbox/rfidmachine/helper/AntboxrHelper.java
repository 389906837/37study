package com.antbox.rfidmachine.helper;


import com.antbox.common.RestResult;
import com.antbox.rfidmachine.dto.AntboxResponse;
import com.antbox.rfidmachine.dto.UserDto;
import com.antbox.rfidmachine.enumclass.SystemType;
import com.antbox.rfidmachine.service.LocalService;
import retrofit2.Response;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by DK on 17/5/12.
 * 读写器命令消息
 */
public enum AntboxrHelper {

    SINGLETON;

    /**
     * 新机rssi型号
     */
    public static final String NEW_MACHINE_MODEL = "RSSI_MACHINE_MODEL";

    /**
     * 旧机不带rssi
     */
    public static final String OLD_MACHINE_MODEL = "OLD_MACHINE_MODEL";


    /**
     * ISO/IEC 15693协议命令
     */
    public static final short STATE_TYPE_ISO15693 = 0x00;

    /**
     * 命令操作控制符 - 读写器自定义命令
     */
    public static final short STATE_READER_COMMAND = 0xf0;

    /**
     * 命令操作控制符 - 不带AFI的Inventory-scan（新的询查）
     */
    public static final short STATE_NEW_INVENTORY_SCAN = 0x06;


    /**
     * 命令操作控制符 - 不带AFI的Inventory-scan（新的rssi询查）
     */
    public static final short STATE_RSSI_INVENTORY_SCAN = 0x86;

    /**
     * 命令操作控制符 - 目前没有任何操作
     */
    public static final short NO_COMMAND = 0xFF;

    /**
     * Inventory
     */
    public static final short INVENTORY = 0x01;

    /**
     * Write InventoryScanTime
     */
    public static final short WRITE_INVENTORYSCANTIME = 0x04;

    /**
     * 无电子标签可操作 <br>
     * 上位机发送命令数据块，读写器在执行相应命令的过程中，感应场内没有电子标签可操作时返回给上位机的状态值
     */
    public static final int ALL_UID_SCANNED = 0x0E;

    /**
     * 指定的Inventory-Scan-Time 溢出<br>
     * 上位机发送命令数据块，读写器执行Inventory时，当在用户指定的时间Inventory-Scan-
     * Time溢出前还没有获得一张电子标签时返回给上位机的状态值
     */
    public static final int NO_UID_SCANNED = 0x0A;

    /**
     * 最大数据帧长度。<br>
     * 注：依据协议MAX_FRAME_LENGTH至少为419（卡号消息）。
     */
    public static final int MAX_FRAME_LENGTH = 30;

    /**
     * 获取读写器返回的结果<br>
     * <b>NOTE</b>: 在sendCommand后，必须调用该方法来获取结果并清理当前状态。
     */
    public  <T extends AntboxObject> List<T> getResult(Class<T> targetType, List<AntboxResponse> result, short currentCommand, short currentCommandState){
        List<T> targets = new LinkedList<>();

        for (AntboxResponse rsp : result) {
            short status = rsp.getStatus();
            if (AntboxrHelper.STATE_TYPE_ISO15693 == status) {
                targets.add(rsp.castTo(targetType));
            }else if ((AntboxrHelper.ALL_UID_SCANNED == status || AntboxrHelper.NO_UID_SCANNED == status)
                    && isInventoryCommand(currentCommand, currentCommandState)) {
                targets.add(rsp.castTo(targetType));
            }
        }

        return targets;
    }

    /**
     * 当前是否为检测标签命令
     */
    public boolean isInventoryCommand(short currentCommand, short currentCommandState) {
        if (currentCommandState == AntboxrHelper.STATE_NEW_INVENTORY_SCAN) {
            return AntboxrHelper.INVENTORY == currentCommand
                    && AntboxrHelper.STATE_TYPE_ISO15693 == (currentCommandState & 0xf0);
        }else {
            return AntboxrHelper.INVENTORY == currentCommand
                    && AntboxrHelper.STATE_TYPE_ISO15693 == ((currentCommandState & 0xf0) > 0 ? 0 : 1);
        }
    }

    public String cmdStr(short currentCommand, short currentCommandState) {
        return " cmd=0x" + Integer.toHexString(currentCommand).toUpperCase() + " state=0x"
                + Integer.toHexString(currentCommandState).toUpperCase();
    }

    //加盟商登陆
    public UserDto merchantLogin(UserDto dto, LocalService localService) throws Exception {
        dto.setSystemType(SystemType.MERCHANT);
        Response<RestResult<UserDto>> jsonResultResponse = localService.localLogin(dto).execute();
        String resultCode = jsonResultResponse.body().getCode();
        if (resultCode.equals(RestResult.CD1[0])) {
            UserDto user = jsonResultResponse.body().data;
            /*if (user != null && user.getAccessToken() != null) {
                dto.setAccessToken(user.getAccessToken());
            }*/
        }
        return dto;
    }

}
