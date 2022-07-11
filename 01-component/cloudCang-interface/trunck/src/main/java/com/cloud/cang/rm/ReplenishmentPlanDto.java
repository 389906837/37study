package com.cloud.cang.rm;

import com.cloud.cang.common.SuperDto;

import java.util.List;

/**
 * @Description: 计划补货查询参数
 * @Author: ChangTanchang
 * @Date: 2018年01月10日
 * @version 1.0
 */
public class ReplenishmentPlanDto extends SuperDto {

    private static final long serialVersionUID = 1L;

    /*(必填参数)*/

    //=======必填=========
    // 设备ID
    private String sdeviceId;
    private String operMan;//操作人
    /* 补货员姓名 */
    private String srenewalName;
    /* 补货员手机号 */
    private String srenewalMobile;
    List<CommodityDto> commodityDtos;//商品集合


    //=======选择=========
    private String sremark;//补货单备注


    public String getSdeviceId() {
        return sdeviceId;
    }

    public void setSdeviceId(String sdeviceId) {
        this.sdeviceId = sdeviceId;
    }

    public String getOperMan() {
        return operMan;
    }

    public void setOperMan(String operMan) {
        this.operMan = operMan;
    }

    public List<CommodityDto> getCommodityDtos() {
        return commodityDtos;
    }

    public void setCommodityDtos(List<CommodityDto> commodityDtos) {
        this.commodityDtos = commodityDtos;
    }

    public String getSremark() {
        return sremark;
    }

    public void setSremark(String sremark) {
        this.sremark = sremark;
    }

    public String getSrenewalName() {
        return srenewalName;
    }

    public void setSrenewalName(String srenewalName) {
        this.srenewalName = srenewalName;
    }

    public String getSrenewalMobile() {
        return srenewalMobile;
    }

    public void setSrenewalMobile(String srenewalMobile) {
        this.srenewalMobile = srenewalMobile;
    }

    @Override
    public String toString() {
        return "ReplenishmentPlanDto{" +
                "sdeviceId='" + sdeviceId + '\'' +
                ", operMan='" + operMan + '\'' +
                ", srenewalName='" + srenewalName + '\'' +
                ", srenewalMobile='" + srenewalMobile + '\'' +
                ", commodityDtos=" + commodityDtos +
                ", sremark='" + sremark + '\'' +
                '}';
    }
}
