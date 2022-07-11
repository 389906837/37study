package com.cloud.cang.act;

import com.cloud.cang.common.SuperDto;

/**
 * 更新活动优惠记录服务Dto
 *
 * @author yanlingfeng
 * @version 1.0
 */
public class OrderInvocationActivityDto extends SuperDto {
    //-----------------------------------必填----------------------------
    /* 活动ID  多个用,隔开*/
    private String sacId;
    /* 会员编号 */
    private String smemberCode;
    /* 会员ID */
    private String smemberId;
    /* 会员用户名（手机号） */
    private String smemberName;
    /* 来源单据编号 */
    private String ssourceCode;
    /*来源单据类型*/
    private Integer isourceType;
    /* 来源单据设备编号 */
    private String ssourceDeviceCode;
    /* 来源单据设备ID */
    private String ssourceDeviceId;
    /* 来源单据设备地址 */
    private String ssourceDeviceAddress;
    /* 来源单据ID */
    private String ssourceId;
    /*商户编号*/
    private String smerchantCode;
    /*商户ID*/
    private String smerchantId;
    /*来源是否拆单*/
    private Integer iisDismantling;
    /*更新活动优惠表来源类型
    * 10:批量下发券 - 平台赠送
    * 20:活动赠送 - 券服务
    * 30:促销活动 - 下单
    * */
    private Integer upType;

    //-----------------------------------------使用券-------------------------------
    /*用户持有券Id*/
    private String scouponId;
    /*用户持有券code*/
    private String scouponCode;

    //-----------------------------------------END-------------------------------


    @Override
    public String toString() {
        return "OrderInvocationActivityDto{" +
                "sacId='" + sacId + '\'' +
                ", smemberCode='" + smemberCode + '\'' +
                ", smemberId='" + smemberId + '\'' +
                ", smemberName='" + smemberName + '\'' +
                ", ssourceCode='" + ssourceCode + '\'' +
                ", isourceType=" + isourceType +
                ", ssourceDeviceCode='" + ssourceDeviceCode + '\'' +
                ", ssourceDeviceId='" + ssourceDeviceId + '\'' +
                ", ssourceDeviceAddress='" + ssourceDeviceAddress + '\'' +
                ", ssourceId='" + ssourceId + '\'' +
                ", smerchantCode='" + smerchantCode + '\'' +
                ", smerchantId='" + smerchantId + '\'' +
                ", iisDismantling=" + iisDismantling +
                ", upType=" + upType +
                ", scouponId='" + scouponId + '\'' +
                ", scouponCode='" + scouponCode + '\'' +
                '}';
    }

    public Integer getUpType() {
        return upType;
    }

    public void setUpType(Integer upType) {
        this.upType = upType;
    }

    public String getSsourceDeviceAddress() {
        return ssourceDeviceAddress;
    }

    public void setSsourceDeviceAddress(String ssourceDeviceAddress) {
        this.ssourceDeviceAddress = ssourceDeviceAddress;
    }

    public String getSmerchantCode() {
        return smerchantCode;
    }

    public void setSmerchantCode(String smerchantCode) {
        this.smerchantCode = smerchantCode;
    }

    public Integer getIisDismantling() {
        return iisDismantling;
    }

    public void setIisDismantling(Integer iisDismantling) {
        this.iisDismantling = iisDismantling;
    }

    public String getScouponId() {
        return scouponId;
    }

    public void setScouponId(String scouponId) {
        this.scouponId = scouponId;
    }

    public String getScouponCode() {
        return scouponCode;
    }

    public void setScouponCode(String scouponCode) {
        this.scouponCode = scouponCode;
    }


    public String getSacId() {
        return sacId;
    }

    public void setSacId(String sacId) {
        this.sacId = sacId;
    }


    public String getSmemberCode() {
        return smemberCode;
    }

    public void setSmemberCode(String smemberCode) {
        this.smemberCode = smemberCode;
    }

    public String getSmemberId() {
        return smemberId;
    }

    public void setSmemberId(String smemberId) {
        this.smemberId = smemberId;
    }

    public String getSmemberName() {
        return smemberName;
    }

    public void setSmemberName(String smemberName) {
        this.smemberName = smemberName;
    }

    public Integer getIsourceType() {
        return isourceType;
    }

    public void setIsourceType(Integer isourceType) {
        this.isourceType = isourceType;
    }

    public String getSsourceCode() {
        return ssourceCode;
    }

    public void setSsourceCode(String ssourceCode) {
        this.ssourceCode = ssourceCode;
    }

    public String getSsourceDeviceCode() {
        return ssourceDeviceCode;
    }

    public void setSsourceDeviceCode(String ssourceDeviceCode) {
        this.ssourceDeviceCode = ssourceDeviceCode;
    }

    public String getSsourceDeviceId() {
        return ssourceDeviceId;
    }

    public void setSsourceDeviceId(String ssourceDeviceId) {
        this.ssourceDeviceId = ssourceDeviceId;
    }

    public String getSsourceId() {
        return ssourceId;
    }

    public void setSsourceId(String ssourceId) {
        this.ssourceId = ssourceId;
    }

    public String getSmerchantId() {
        return smerchantId;
    }

    public void setSmerchantId(String smerchantId) {
        this.smerchantId = smerchantId;
    }
}
