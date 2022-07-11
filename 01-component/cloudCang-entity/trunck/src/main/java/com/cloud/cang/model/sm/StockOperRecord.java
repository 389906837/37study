package com.cloud.cang.model.sm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;

import com.cloud.cang.generic.GenericEntity;

/**
 * 单设备库存操作纪律(SM_STOCK_OPER_RECORD)
 **/
public class StockOperRecord extends GenericEntity {

    private static final long serialVersionUID = 1L;

    /*主键*/
    private String id;

    /*主键*/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /* 操作状态10=上架
        20=下架
        30=售出
        40=异常上架
        50 异常下架
        60 异常售出
        70 手动下架 */
    private Integer istatus;

    public Integer getIstatus() {
        return istatus;
    }

    public void setIstatus(Integer istatus) {
        this.istatus = istatus;
    }

    /* 商品编号 */
    private String scommodityCode;

    public String getScommodityCode() {
        return scommodityCode;
    }

    public void setScommodityCode(String scommodityCode) {
        this.scommodityCode = scommodityCode;
    }

    /* 商品ID */
    private String scommodityId;

    public String getScommodityId() {
        return scommodityId;
    }

    public void setScommodityId(String scommodityId) {
        this.scommodityId = scommodityId;
    }

    /* 设备编号 */
    private String sdeviceCode;

    public String getSdeviceCode() {
        return sdeviceCode;
    }

    public void setSdeviceCode(String sdeviceCode) {
        this.sdeviceCode = sdeviceCode;
    }

    /* 设备ID */
    private String sdeviceId;

    public String getSdeviceId() {
        return sdeviceId;
    }

    public void setSdeviceId(String sdeviceId) {
        this.sdeviceId = sdeviceId;
    }

    /* 唯一标识 */
    private String sidentifies;

    public String getSidentifies() {
        return sidentifies;
    }

    public void setSidentifies(String sidentifies) {
        this.sidentifies = sidentifies;
    }

    /* 商户编号 */
    private String smerchantCode;

    public String getSmerchantCode() {
        return smerchantCode;
    }

    public void setSmerchantCode(String smerchantCode) {
        this.smerchantCode = smerchantCode;
    }

    /* 商户ID */
    private String smerchantId;

    public String getSmerchantId() {
        return smerchantId;
    }

    public void setSmerchantId(String smerchantId) {
        this.smerchantId = smerchantId;
    }

    /* 备注 */
    private String sremark;

    public String getSremark() {
        return sremark;
    }

    public void setSremark(String sremark) {
        this.sremark = sremark;
    }

    /* 操作时间 */
    private Date taddTime;

    public Date getTaddTime() {
        return taddTime;
    }

    public void setTaddTime(Date taddTime) {
        this.taddTime = taddTime;
    }


}