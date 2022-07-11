package com.cloud.cang.api.netty.vo;

import com.cloud.cang.generic.GenericEntity;
import com.cloud.cang.model.TagModel;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sb.DeviceModel;
import com.cloud.cang.model.sb.DeviceRegister;
import com.cloud.cang.model.sb.MonitorDataConf;

import java.util.Date;
import java.util.List;

/**
 * 设备信息，用于通道中保存设备相关信息
 * session
 *
 * @author zengzexiong
 * @version 1.0
 * @date 2018年3月14日18:43:25
 */
public class ClientVo extends GenericEntity {

    private static final long serialVersionUID = 6433135149679692665L;


    private String deviceId;                        //设备ID
    private String deviceCode;                      //设备编号
    private String ctxId;                           //ctx唯一长整数Id
    private Date openDoorTime;                      //开门时间
    private Integer openDoorType;                   //开门类型 10 开门 20 补货开门 30游客开门（没买东西）
    private String userId;                          //开门用户ID
    private Integer door;                           //门状态：10关，20开


//    List<TagModel> commodityList;           //商品集合
//    DeviceInfo deviceInfo;                  //设备信息
//    DeviceRegister deviceRegister;          //设备注册信息
//    DeviceModel deviceModel;                //设备详情信息
//    MonitorDataConf monitorDataConf;        //设备监控信息


    public Date getOpenDoorTime() {
        return openDoorTime;
    }

    public void setOpenDoorTime(Date openDoorTime) {
        this.openDoorTime = openDoorTime;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getOpenDoorType() {
        return openDoorType;
    }

    public void setOpenDoorType(Integer openDoorType) {
        this.openDoorType = openDoorType;
    }

    public Integer getDoor() {
        return door;
    }

    public void setDoor(Integer door) {
        this.door = door;
    }

    public String getCtxId() {
        return ctxId;
    }

    public void setCtxId(String ctxId) {
        this.ctxId = ctxId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

//    public List<TagModel> getCommodityList() {
//        return commodityList;
//    }
//
//    public void setCommodityList(List<TagModel> commodityList) {
//        this.commodityList = commodityList;
//    }
//
//
//
//    public DeviceInfo getDeviceInfo() {
//        return deviceInfo;
//    }
//
//    public void setDeviceInfo(DeviceInfo deviceInfo) {
//        this.deviceInfo = deviceInfo;
//    }
//
//    public DeviceRegister getDeviceRegister() {
//        return deviceRegister;
//    }
//
//    public void setDeviceRegister(DeviceRegister deviceRegister) {
//        this.deviceRegister = deviceRegister;
//    }
//
//    public DeviceModel getDeviceModel() {
//        return deviceModel;
//    }
//
//    public void setDeviceModel(DeviceModel deviceModel) {
//        this.deviceModel = deviceModel;
//    }
//
//    public MonitorDataConf getMonitorDataConf() {
//        return monitorDataConf;
//    }
//
//    public void setMonitorDataConf(MonitorDataConf monitorDataConf) {
//        this.monitorDataConf = monitorDataConf;
//    }

    @Override
    public String toString() {
        return "ClientVo{" +
                "deviceId='" + deviceId + '\'' +
                ", deviceCode='" + deviceCode + '\'' +
                ", ctxId='" + ctxId + '\'' +
                ", openDoorTime=" + openDoorTime +
                ", openDoorType=" + openDoorType +
                ", userId='" + userId + '\'' +
                ", door=" + door +
                '}';
    }
}
