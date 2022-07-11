package com.cloud.cang.message;


import com.cloud.cang.common.SuperDto;

import java.util.List;
import java.util.Map;

/**
 * 消息参数
 *
 * @author zhouhong
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MessageDto extends SuperDto {
    /**
     * 商户编号
     */
    private String smerchantCode;
    /**
     * 商户ID
     */
    private String smerchantId;
    /**
     * 模板类型
     */
    private String templateType;

    /**
     * 模板编号
     */
    private String templateCode;

    /**
     * template 渲染内容
     */
    private Map<String, Object> contentParam;

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

    public String getSmerchantCode() {
        return smerchantCode;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
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

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public Map<String, Object> getContentParam() {
        return contentParam;
    }

    public void setContentParam(Map<String, Object> contentParam) {
        this.contentParam = contentParam;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public List<String> getMobiles() {
        return mobiles;
    }

    public void setMobiles(List<String> mobiles) {
        this.mobiles = mobiles;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    @Override
    public String toString() {
        return "MessageDto [templateCode=" + templateCode + ", contentParam="
                + contentParam + ", mobile=" + mobile + ", mobiles=" + mobiles
                + ", email=" + email + ", emails=" + emails
                + ", attachLocation=" + attachLocation + ", sitMsgType="
                + sitMsgType + ", siteMsgReceiveId=" + siteMsgReceiveId
                + ", siteMsgReceiveType=" + siteMsgReceiveType + ", serialNum="
                + serialNum + "]";
    }

}
