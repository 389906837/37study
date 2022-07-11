package com.cloud.cang.rec.cr.vo;

import com.cloud.cang.model.op.BuyRecord;

/**
 * Created by yan on 2018/11/16.
 */
public class BuyRecordVo extends BuyRecord {
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
}
