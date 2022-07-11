package com.cloud.cang.api.ws.domain;

import com.cloud.cang.generic.GenericEntity;
import com.cloud.cang.model.sb.CameraConfig;

import java.util.List;

/**
 * 设备详细信息部分配置项
 */
public class DeviceModelConfigInfo extends GenericEntity {

    Integer idoorPinSn;             // 门引脚序号
    Integer ihallPinSn;             // 霍尔引脚序号
    Integer iopendoorPinSn;         // 开门引脚序号
    Integer ilockCylinder;          // 锁芯引脚序号
    String caremaConfig;            // 摄像头相关参数 = 摄像头+品牌+型号+方法
    String iisUseExpandGpio;       // 是否使用扩展GPIO 0否1是
    String iisDetectHall;          // 是否检测霍尔值 0否1是
    String svisualServiceProvider;  // 视觉服务提供商
    String spcbModel;               // PCB板型号
    List<CameraConfig> cameraConfigList; // 摄像头配置信息
    String scontrastMode; //盘货对比方式 rawstock 原始库存 openDoor 开门前后
    Integer isupportSingleLayer;//是否支持分层盘货 0 否 1 是


    public Integer getIsupportSingleLayer() {
        return isupportSingleLayer;
    }

    public void setIsupportSingleLayer(Integer isupportSingleLayer) {
        this.isupportSingleLayer = isupportSingleLayer;
    }

    public String getScontrastMode() {
        return scontrastMode;
    }

    public void setScontrastMode(String scontrastMode) {
        this.scontrastMode = scontrastMode;
    }

    public List<CameraConfig> getCameraConfigList() {
        return cameraConfigList;
    }

    public void setCameraConfigList(List<CameraConfig> cameraConfigList) {
        this.cameraConfigList = cameraConfigList;
    }

    public Integer getIdoorPinSn() {
        return idoorPinSn;
    }

    public void setIdoorPinSn(Integer idoorPinSn) {
        this.idoorPinSn = idoorPinSn;
    }

    public Integer getIhallPinSn() {
        return ihallPinSn;
    }

    public void setIhallPinSn(Integer ihallPinSn) {
        this.ihallPinSn = ihallPinSn;
    }

    public Integer getIopendoorPinSn() {
        return iopendoorPinSn;
    }

    public void setIopendoorPinSn(Integer iopendoorPinSn) {
        this.iopendoorPinSn = iopendoorPinSn;
    }

    public Integer getIlockCylinder() {
        return ilockCylinder;
    }

    public void setIlockCylinder(Integer ilockCylinder) {
        this.ilockCylinder = ilockCylinder;
    }

    public String getCaremaConfig() {
        return caremaConfig;
    }

    public void setCaremaConfig(String caremaConfig) {
        this.caremaConfig = caremaConfig;
    }

    public String getIisUseExpandGpio() {
        return iisUseExpandGpio;
    }

    public void setIisUseExpandGpio(String iisUseExpandGpio) {
        this.iisUseExpandGpio = iisUseExpandGpio;
    }

    public String getIisDetectHall() {
        return iisDetectHall;
    }

    public void setIisDetectHall(String iisDetectHall) {
        this.iisDetectHall = iisDetectHall;
    }

    public String getSvisualServiceProvider() {
        return svisualServiceProvider;
    }

    public void setSvisualServiceProvider(String svisualServiceProvider) {
        this.svisualServiceProvider = svisualServiceProvider;
    }

    public String getSpcbModel() {
        return spcbModel;
    }

    public void setSpcbModel(String spcbModel) {
        this.spcbModel = spcbModel;
    }

    @Override
    public String toString() {
        return "DeviceModelConfigInfo{" +
                "idoorPinSn=" + idoorPinSn +
                ", ihallPinSn=" + ihallPinSn +
                ", iopendoorPinSn=" + iopendoorPinSn +
                ", ilockCylinder=" + ilockCylinder +
                ", caremaConfig='" + caremaConfig + '\'' +
                ", iisUseExpandGpio='" + iisUseExpandGpio + '\'' +
                ", iisDetectHall='" + iisDetectHall + '\'' +
                ", svisualServiceProvider='" + svisualServiceProvider + '\'' +
                ", spcbModel='" + spcbModel + '\'' +
                ", cameraConfigList='" + cameraConfigList + '\'' +
                '}';
    }
}
