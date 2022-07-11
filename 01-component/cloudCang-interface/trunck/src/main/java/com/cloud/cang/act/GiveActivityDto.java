package com.cloud.cang.act;

import com.cloud.cang.common.SuperDto;

import java.util.Date;

/**
 * 赠送活动参数
 * 说明：
 *
 * @author yanlingfeng
 * @version 1.0
 */
public class GiveActivityDto extends SuperDto {

    private String activeConfCode;//活动配置编号

    private String memberId;//会员ID

    private String memberCode;//会员编号

    private String smerchantCode;// 商户编号

    private String smerchantId;// 商户ID

    private String memberRealName;//会员用户名(手机号)

    private Integer ssourceType;//来源单据类型

    private String ssourceAcName;//来源活动名称

    private String ssourceAcCode;// 来源活动编号

    private String sdeviceId;//设备ID

    private String sdeviceCode;  //设备编号

    private String sdeviceAddress;  //设备地址

    private String ssourceId;//来源单据ID

    private String ssourceCode;// 来源单据编号

    private String ssourceInstruction;// 来源说明 如果有则传

    private Date memberBirthDay;//会员生日 如果有则传

    /*------------------------------------------------------*/

    /**
     * 订单相关参数
     */
    private OrderParam orderParam;

    /**
     * 推荐相关参数
     */
    private RecommendParam recommendParam;

    /**
     * 启用积分活动
     */
    private boolean enableIntegration = true;


    @Override
    public String toString() {
        return "GiveActivityDto{" +
                "activeConfCode='" + activeConfCode + '\'' +
                ", memberId='" + memberId + '\'' +
                ", memberCode='" + memberCode + '\'' +
                ", memberRealName='" + memberRealName + '\'' +
                ", ssourceType=" + ssourceType +
                ", ssourceAcName='" + ssourceAcName + '\'' +
                ", ssourceAcCode='" + ssourceAcCode + '\'' +
                ", sdeviceId='" + sdeviceId + '\'' +
                ", sdeviceCode='" + sdeviceCode + '\'' +
                ", sdeviceAddress='" + sdeviceAddress + '\'' +
                ", ssourceId='" + ssourceId + '\'' +
                ", ssourceCode='" + ssourceCode + '\'' +
                ", ssourceInstruction='" + ssourceInstruction + '\'' +
                ", smerchantCode='" + smerchantCode + '\'' +
                ", smerchantId='" + smerchantId + '\'' +
                ", memberBirthDay=" + memberBirthDay +
                ", recommendParam=" + recommendParam +
                ", enableIntegration=" + enableIntegration +
                '}';
    }

    public OrderParam getOrderParam() {
        return orderParam;
    }

    public void setOrderParam(OrderParam orderParam) {
        this.orderParam = orderParam;
    }

    public String getSmerchantCode() {
        return smerchantCode;
    }

    public void setSmerchantCode(String smerchantCode) {
        this.smerchantCode = smerchantCode;
    }

    public String getSmerchantId() {
        return smerchantId;
    }

    public void setSmerchantId(String smerchantId) {
        this.smerchantId = smerchantId;
    }

    public String getSdeviceAddress() {
        return sdeviceAddress;
    }

    public void setSdeviceAddress(String sdeviceAddress) {
        this.sdeviceAddress = sdeviceAddress;
    }

    public String getSdeviceCode() {
        return sdeviceCode;
    }

    public void setSdeviceCode(String sdeviceCode) {
        this.sdeviceCode = sdeviceCode;
    }

    public String getSdeviceId() {
        return sdeviceId;
    }

    public void setSdeviceId(String sdeviceId) {
        this.sdeviceId = sdeviceId;
    }

    public String getActiveConfCode() {
        return activeConfCode;
    }

    public void setActiveConfCode(String activeConfCode) {
        this.activeConfCode = activeConfCode;
    }

    public String getMemberId() {
        return memberId;
    }

    public Date getMemberBirthDay() {
        return memberBirthDay;
    }

    public void setMemberBirthDay(Date memberBirthDay) {
        this.memberBirthDay = memberBirthDay;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getMemberRealName() {
        return memberRealName;
    }

    public void setMemberRealName(String memberRealName) {
        this.memberRealName = memberRealName;
    }


    public Integer getSsourceType() {
        return ssourceType;
    }

    public void setSsourceType(Integer ssourceType) {
        this.ssourceType = ssourceType;
    }

    public String getSsourceId() {
        return ssourceId;
    }

    public void setSsourceId(String ssourceId) {
        this.ssourceId = ssourceId;
    }

    public String getSsourceCode() {
        return ssourceCode;
    }

    public void setSsourceCode(String ssourceCode) {
        this.ssourceCode = ssourceCode;
    }

    public String getSsourceInstruction() {
        return ssourceInstruction;
    }

    public void setSsourceInstruction(String ssourceInstruction) {
        this.ssourceInstruction = ssourceInstruction;
    }


    public RecommendParam getRecommendParam() {
        return recommendParam;
    }

    public void setRecommendParam(RecommendParam recommendParam) {
        this.recommendParam = recommendParam;
    }


    public boolean isEnableIntegration() {
        return enableIntegration;
    }

    public void setEnableIntegration(boolean enableIntegration) {
        this.enableIntegration = enableIntegration;
    }

/*
    public OrderParam getOrderParam() {
        return orderParam;
    }

    public void setOrderParam(OrderParam orderParam) {
        this.orderParam = orderParam;
    }
*/

    public String getSsourceAcName() {
        return ssourceAcName;
    }

    public void setSsourceAcName(String ssourceAcName) {
        this.ssourceAcName = ssourceAcName;
    }

    public String getSsourceAcCode() {
        return ssourceAcCode;
    }

    public void setSsourceAcCode(String ssourceAcCode) {
        this.ssourceAcCode = ssourceAcCode;
    }
}
