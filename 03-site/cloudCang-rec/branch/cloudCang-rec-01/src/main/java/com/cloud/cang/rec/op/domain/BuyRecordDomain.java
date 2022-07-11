package com.cloud.cang.rec.op.domain;

import com.cloud.cang.model.op.BuyRecord;

/**
 * BuyRecord扩展类
 * Created by yan on 2018/10/16.
 */
public class BuyRecordDomain extends BuyRecord {
    private String orderStr; //排序
    private String  snickName;//用户昵称
    private String  interfaceName;//接口名

    public String getSnickName() {
        return snickName;
    }

    public void setSnickName(String snickName) {
        this.snickName = snickName;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }
}
