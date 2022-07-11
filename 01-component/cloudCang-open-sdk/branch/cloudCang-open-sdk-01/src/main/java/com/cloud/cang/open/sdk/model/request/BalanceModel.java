package com.cloud.cang.open.sdk.model.request;

import java.io.Serializable;

/**
 * @version 1.0
 * @Description: 接口账户信息
 * @Author: zhouhong
 * @Date: 2018/9/20 20:22
 */
public class BalanceModel implements Serializable {
    //=========必填========
    private String interfaceAction;//账户对应接口动作指令

    public String getInterfaceAction() {
        return interfaceAction;
    }

    public void setInterfaceAction(String interfaceAction) {
        this.interfaceAction = interfaceAction;
    }

    @Override
    public String toString() {
        return "BalanceModel{" +
                "interfaceAction='" + interfaceAction + '\'' +
                '}';
    }
}
