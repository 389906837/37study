package com.cloud.cang.message.xx.domain;

public class TemplateDomain {
    
    /**
     * 主模板Id
     */
    private String mainId;
    
    /**
     * 主模板编号
     */
    private String mainCode;
    
    /**
     * 主模板名称
     */
    private String mainName;
    
    /**
     * 主模板描叙
     */
    private String mainRemark;
    
    /**
     * 从模板ID
     */
    private String templateId;
    
    /**
     * 从模板名称
     */
    private String templateName;
    
    /**
     * 从模板内容
     */
    private String templateContent;
    
    /**
     * 从模板标题
     */
    private String templateTitle;
    
    /**
     * 超时
     */
    private Integer timeout;
    
    /**
     * 供应商ID
     */
    private String supplierId;
    
    /**
     * 供应商编号
     */
    private String supplierCode;
   
    /**
     * 每日发送限制
     */
    private Integer countLimit;
    
    /**
     * 每日用户发送限制
     */
    private Integer userCountLimit;
    
    /**
     * 是否启用
     */
    private Integer isEnable;
    
    
    /**
     * 是否实时发送
     */
    private Integer isRealTime;
    
    /**
     * 发送开始小时
     */
    private String startTime;
    
    /**
     * 发送结束小时
     */
    private String endTime;
    
    /**
     * 模板类型  1 短信  2 供应商
     */
    private Integer iusage;
    
    /**
     * 消息类型 1 短信 2邮件
     */
    private Integer imsgType;
    

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }

    public String getMainCode() {
        return mainCode;
    }

    public void setMainCode(String mainCode) {
        this.mainCode = mainCode;
    }

    public String getMainName() {
        return mainName;
    }

    public void setMainName(String mainName) {
        this.mainName = mainName;
    }

    public String getMainRemark() {
        return mainRemark;
    }

    public void setMainRemark(String mainRemark) {
        this.mainRemark = mainRemark;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public Integer getCountLimit() {
        return countLimit;
    }

    public void setCountLimit(Integer countLimit) {
        this.countLimit = countLimit;
    }

    public Integer getUserCountLimit() {
        return userCountLimit;
    }

    public void setUserCountLimit(Integer userCountLimit) {
        this.userCountLimit = userCountLimit;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public Integer getIsRealTime() {
        return isRealTime;
    }

    public void setIsRealTime(Integer isRealTime) {
        this.isRealTime = isRealTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTemplateTitle() {
        return templateTitle;
    }

    public void setTemplateTitle(String templateTitle) {
        this.templateTitle = templateTitle;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getIusage() {
        return iusage;
    }

    public void setIusage(Integer iusage) {
        this.iusage = iusage;
    }

    public Integer getImsgType() {
        return imsgType;
    }

    public void setImsgType(Integer imsgType) {
        this.imsgType = imsgType;
    }
  
}
