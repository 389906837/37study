package com.cloud.cang.pay.vendstop;

import java.io.Serializable;

/**
 * @program: 37cang
 * @description: 调用Vendstop请求，返回结果
 * @author: qzg
 * @create: 2019-08-08 15:42
 **/
public class VendstopResponse implements Serializable {
    //调用Vendstop的api, vendstop返回成功/失败
    private boolean success;
    //vendstop返回数据
    private String data;
    //if success== false,返回错误信息
    private String error;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
