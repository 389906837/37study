package com.cloud.cang.rfid;

import java.util.List;

/**
 * @program: 37cang
 * @description:
 * @author: qzg
 * @create: 2019-09-02 19:27
 **/
public class CommodityRfidDto {
    private String smerchantId;
    private String scommodityCode;
    private String scommodityName;
    private String soperatorId;
    private List<String> rfids;

    public String getSmerchantId() {
        return smerchantId;
    }

    public void setSmerchantId(String smerchantId) {
        this.smerchantId = smerchantId;
    }

    public String getScommodityCode() {
        return scommodityCode;
    }

    public void setScommodityCode(String scommodityCode) {
        this.scommodityCode = scommodityCode;
    }

    public String getSoperatorId() {
        return soperatorId;
    }

    public void setSoperatorId(String soperatorId) {
        this.soperatorId = soperatorId;
    }

    public List<String> getRfids() {
        return rfids;
    }

    public void setRfids(List<String> rfids) {
        this.rfids = rfids;
    }

    public String getScommodityName() {
        return scommodityName;
    }

    public void setScommodityName(String scommodityName) {
        this.scommodityName = scommodityName;
    }
}
