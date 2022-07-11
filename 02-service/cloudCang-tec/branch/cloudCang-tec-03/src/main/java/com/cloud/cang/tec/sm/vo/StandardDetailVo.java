package com.cloud.cang.tec.sm.vo;

import com.cloud.cang.model.sm.StandardDetail;

/**
 * Created by yan on 2018/3/19.
 */
public class StandardDetailVo extends StandardDetail {
    private String scommodityName; //商品名
    private String sdeviceName;//设备名

    public String getSdeviceName() {
        return sdeviceName;
    }

    public void setSdeviceName(String sdeviceName) {
        this.sdeviceName = sdeviceName;
    }

    public String getScommodityName() {
        return scommodityName;
    }

    public void setScommodityName(String scommodityName) {
        this.scommodityName = scommodityName;
    }
}
