package com.cloud.cang.model.sq;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;

import com.cloud.cang.generic.GenericEntity;

/**
 * 付款申请(SQ_CREAT_APPLY)
 **/
public class CreatApply extends GenericEntity {

    private static final long serialVersionUID = 1L;

    /*ID*/
    private String id;

    /*ID*/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /* 状态
10  初始化
20  已取消
30  已完成
40  创建失败*/
    private Integer istatus;

    public Integer getIstatus() {
        return istatus;
    }

    public void setIstatus(Integer istatus) {
        this.istatus = istatus;
    }

    /* 操作人(用户名) */
    private String sadduserId;

    public String getSadduserId() {
        return sadduserId;
    }

    public void setSadduserId(String sadduserId) {
        this.sadduserId = sadduserId;
    }

    /* 编号 */
    private String scode;

    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        this.scode = scode;
    }

    /* 设备编号 */
    private String sdeviceCode;

    public String getSdeviceCode() {
        return sdeviceCode;
    }

    public void setSdeviceCode(String sdeviceCode) {
        this.sdeviceCode = sdeviceCode;
    }

    /* 商户ID */
    private String sdeviceId;

    public String getSdeviceId() {
        return sdeviceId;
    }

    public void setSdeviceId(String sdeviceId) {
        this.sdeviceId = sdeviceId;
    }

    /* 会员ID */
    private String smemberId;

    public String getSmemberId() {
        return smemberId;
    }

    public void setSmemberId(String smemberId) {
        this.smemberId = smemberId;
    }

    /* 会员编号 */
    private String smemberNo;

    public String getSmemberNo() {
        return smemberNo;
    }

    public void setSmemberNo(String smemberNo) {
        this.smemberNo = smemberNo;
    }

    /* 订单支付编号 */
    private String sorderPayCode;

    public String getSorderPayCode() {
        return sorderPayCode;
    }

    public void setSorderPayCode(String sorderPayCode) {
        this.sorderPayCode = sorderPayCode;
    }

    /* 备注 */
    private String sremark;

    public String getSremark() {
        return sremark;
    }

    public void setSremark(String sremark) {
        this.sremark = sremark;
    }

    /* 创建时间 */
    private Date taddTime;

    public Date getTaddTime() {
        return taddTime;
    }

    public void setTaddTime(Date taddTime) {
        this.taddTime = taddTime;
    }


}