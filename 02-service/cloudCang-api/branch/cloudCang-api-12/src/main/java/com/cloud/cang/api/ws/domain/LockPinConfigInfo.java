package com.cloud.cang.api.ws.domain;

import com.cloud.cang.generic.GenericEntity;

/**
 * 开门引脚序号
 * 锁芯引脚序号
 * 门引脚序号
 * 霍尔引脚序号
 */
public class LockPinConfigInfo extends GenericEntity {

    Integer idoorPinSn; // 门引脚序号
    Integer ihallPinSn; // 霍尔引脚序号
    Integer iopendoorPinSn; // 开门引脚序号
    Integer ilockCylinder; // 锁芯引脚序号

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

    @Override
    public String toString() {
        return "LockPinConfigInfo{" +
                "idoorPinSn=" + idoorPinSn +
                ", ihallPinSn=" + ihallPinSn +
                ", iopendoorPinSn=" + iopendoorPinSn +
                ", ilockCylinder=" + ilockCylinder +
                '}';
    }
}
