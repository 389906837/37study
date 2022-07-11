package com.cloud.cang.inventory;

import com.cloud.cang.common.SuperDto;

/**
 * @version 1.0
 * @Description: 柜子某层某一货道的重力值
 * @Author: zengzexiong
 * @Date: 2018年11月1日17:57:49
 */
public class CargoRoadGravityVo extends SuperDto {

    private String cargoRoadNo;               // 第几货道
    private String gravityIncrement;        // 重力增加值
    private String gravityDecrement;        // 重力减少值
    private String gravityRemain;           // 当前重力值

    public String getCargoRoadNo() {
        return cargoRoadNo;
    }

    public void setCargoRoadNo(String cargoRoadNo) {
        this.cargoRoadNo = cargoRoadNo;
    }

    public String getGravityIncrement() {
        return gravityIncrement;
    }

    public void setGravityIncrement(String gravityIncrement) {
        this.gravityIncrement = gravityIncrement;
    }

    public String getGravityDecrement() {
        return gravityDecrement;
    }

    public void setGravityDecrement(String gravityDecrement) {
        this.gravityDecrement = gravityDecrement;
    }

    public String getGravityRemain() {
        return gravityRemain;
    }

    public void setGravityRemain(String gravityRemain) {
        this.gravityRemain = gravityRemain;
    }

    @Override
    public String toString() {
        return "CargoRoadGravityVo{" +
                "cargoRoadNo='" + cargoRoadNo + '\'' +
                ", gravityIncrement='" + gravityIncrement + '\'' +
                ", gravityDecrement='" + gravityDecrement + '\'' +
                ", gravityRemain='" + gravityRemain + '\'' +
                '}';
    }
}
