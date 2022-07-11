package com.cloud.cang.message;

import com.cloud.cang.common.SuperDto;
import com.cloud.cang.model.xx.MsgTemplate;

import java.util.List;

/**
 * 营销短信Dto
 */
public class SalesMsgDto extends SuperDto {
    private String merchantId;

    private  String merchantCode;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    /**
     * template 渲染内容
     */
    private String contentParam;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 群发手机号List<String>
     */
    private List<String> mobiles;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 群发邮箱List<String>
     */
    private List<String> emails;


    /**
     * 邮件附件地址
     */
    private String attachLocation;

    /**
     * 发送人类型
     */
    private Integer sitMsgType;

    /**
     * 站内信 接受人ID
     */
    private String siteMsgReceiveId;
    /**
     * 站内信 接受人类型
     */
    private Integer siteMsgReceiveType;

    private MsgTemplate msgTemplate;

    public void setContentParam(String contentParam) {
        this.contentParam = contentParam;
    }

    public MsgTemplate getMsgTemplate() {
        return msgTemplate;
    }

    public void setMsgTemplate(MsgTemplate msgTemplate) {
        this.msgTemplate = msgTemplate;
    }

    public String getContentParam() {
        return contentParam;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<String> getMobiles() {
        return mobiles;
    }

    public void setMobiles(List<String> mobiles) {
        this.mobiles = mobiles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public String getAttachLocation() {
        return attachLocation;
    }

    public void setAttachLocation(String attachLocation) {
        this.attachLocation = attachLocation;
    }

    public Integer getSitMsgType() {
        return sitMsgType;
    }

    public void setSitMsgType(Integer sitMsgType) {
        this.sitMsgType = sitMsgType;
    }

    public String getSiteMsgReceiveId() {
        return siteMsgReceiveId;
    }

    public void setSiteMsgReceiveId(String siteMsgReceiveId) {
        this.siteMsgReceiveId = siteMsgReceiveId;
    }

    public Integer getSiteMsgReceiveType() {
        return siteMsgReceiveType;
    }

    public void setSiteMsgReceiveType(Integer siteMsgReceiveType) {
        this.siteMsgReceiveType = siteMsgReceiveType;
    }


    @Override
    public String toString() {
        return "SalesMsgDto{" +
                "merchantId='" + merchantId + '\'' +
                ", merchantCode='" + merchantCode + '\'' +
                ", contentParam='" + contentParam + '\'' +
                ", mobile='" + mobile + '\'' +
                ", mobiles=" + mobiles +
                ", email='" + email + '\'' +
                ", emails=" + emails +
                ", attachLocation='" + attachLocation + '\'' +
                ", sitMsgType=" + sitMsgType +
                ", siteMsgReceiveId='" + siteMsgReceiveId + '\'' +
                ", siteMsgReceiveType=" + siteMsgReceiveType +
                ", msgTemplate=" + msgTemplate +
                '}';
    }
}
