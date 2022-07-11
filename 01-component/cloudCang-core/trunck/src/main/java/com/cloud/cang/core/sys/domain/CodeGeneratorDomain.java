package com.cloud.cang.core.sys.domain;


import com.cloud.cang.model.sys.CodeGenerator;

public class CodeGeneratorDomain extends CodeGenerator {

    private static final long serialVersionUID = 1L;
    private String orderStr;//排序字段

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }
}
