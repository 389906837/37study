package com.cloud.cang.api.antbox.dto;



/**
 * Created by oyhk on 2017/3/31.
 * <p>
 * 客户表
 */

public class Customer {
	private static final long serialVersionUID = 1L;
    public static final String ATTR_CLASS_NAME = "客户";
    public String id;
    private String mobile;
    private String name;
    private String alipayUserId;
    private Boolean alipayFreePayment;
    private String alipayAgreementno;
    private String alipayAgreementExternalSignno;
    private String alipayAgreementSignTime;
    private Long registerBoxId;
    private String wechatOpenId;
    private Boolean wechatFreePayment;
    private String wechatAgreementno;
    private String badOrderList;

    private String spareMobile;

    private String alipayHeadImgUrl;
    private String wechatHeadImgUrl;


    public Customer() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlipayHeadImgUrl() {
        return alipayHeadImgUrl;
    }

    public void setAlipayHeadImgUrl(String alipayHeadImgUrl) {
        this.alipayHeadImgUrl = alipayHeadImgUrl;
    }

    public String getWechatHeadImgUrl() {
        return wechatHeadImgUrl;
    }

    public void setWechatHeadImgUrl(String wechatHeadImgUrl) {
        this.wechatHeadImgUrl = wechatHeadImgUrl;
    }

    public String getWechatAgreementno() {
        return wechatAgreementno;
    }

    public void setWechatAgreementno(String wechatAgreementno) {
        this.wechatAgreementno = wechatAgreementno;
    }

    public Boolean getWechatFreePayment() {
        return wechatFreePayment;
    }

    public void setWechatFreePayment(Boolean wechatFreePayment) {
        this.wechatFreePayment = wechatFreePayment;
    }

    public String getAlipayAgreementno() {
        return alipayAgreementno;
    }

    public void setAlipayAgreementno(String alipayAgreementno) {
        this.alipayAgreementno = alipayAgreementno;
    }

    public String getAlipayAgreementExternalSignno() {
        return alipayAgreementExternalSignno;
    }



    public void setAlipayAgreementExternalSignno(String alipayAgreementExternalSignno) {
        this.alipayAgreementExternalSignno = alipayAgreementExternalSignno;
    }

    public String getAlipayAgreementSignTime() {
        return alipayAgreementSignTime;
    }

    public void setAlipayAgreementSignTime(String alipayAgreementSignTime) {
        this.alipayAgreementSignTime = alipayAgreementSignTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAlipayUserId() {
        return alipayUserId;
    }

    public void setAlipayUserId(String alipayUserId) {
        this.alipayUserId = alipayUserId;
    }

    public Boolean getAlipayFreePayment() {
        return alipayFreePayment;
    }

    public void setAlipayFreePayment(Boolean alipayFreePayment) {
        this.alipayFreePayment = alipayFreePayment;
    }



    public Long getRegisterBoxId() {
        return registerBoxId;
    }

    public void setRegisterBoxId(Long registerBoxId) {
        this.registerBoxId = registerBoxId;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWechatOpenId() {
        return wechatOpenId;
    }

    public void setWechatOpenId(String wechatOpenId) {
        this.wechatOpenId = wechatOpenId;
    }

    public String getBadOrderList() {
        return badOrderList;
    }

    public void setBadOrderList(String badOrderList) {
        this.badOrderList = badOrderList;
    }

    public String getSpareMobile() {
        return spareMobile;
    }

    public void setSpareMobile(String spareMobile) {
        this.spareMobile = spareMobile;
    }
}
