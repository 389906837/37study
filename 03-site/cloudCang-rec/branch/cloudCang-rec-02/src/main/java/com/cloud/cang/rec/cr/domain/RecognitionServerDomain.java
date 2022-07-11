package com.cloud.cang.rec.cr.domain;

import com.cloud.cang.model.cr.RecognitionServer;

/**
 * Created by YLF on 2019/11/13.
 */
public class RecognitionServerDomain extends RecognitionServer {
    private String orderStr;

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }
}
