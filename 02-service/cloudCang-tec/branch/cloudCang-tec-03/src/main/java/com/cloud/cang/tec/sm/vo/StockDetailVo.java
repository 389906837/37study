package com.cloud.cang.tec.sm.vo;

import java.util.Date;

/**
 * Created by yan on 2018/3/17.
 */
public class StockDetailVo {
    private static final long serialVersionUID = 1L;
    private String id;
    private String smerchantId;//商户id
    private Date dexpiredDate;//过期时间
    private String sdeviceName;//设备名
    private String sdeviceId;//设备Id
    private String scommidityName;//商品名

    public String getSdeviceName() {
        return sdeviceName;
    }

    public void setSdeviceName(String sdeviceName) {
        this.sdeviceName = sdeviceName;
    }

    public String getScommidityName() {
        return scommidityName;
    }

    public void setScommidityName(String scommidityName) {
        this.scommidityName = scommidityName;
    }

    public Date getDexpiredDate() {
        return dexpiredDate;
    }

    public void setDexpiredDate(Date dexpiredDate) {
        this.dexpiredDate = dexpiredDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSmerchantId() {
        return smerchantId;
    }

    public void setSmerchantId(String smerchantId) {
        this.smerchantId = smerchantId;
    }

    public String getSdeviceId() {
        return sdeviceId;
    }


    public void setSdeviceId(String sdeviceId) {
        this.sdeviceId = sdeviceId;
    }


}
