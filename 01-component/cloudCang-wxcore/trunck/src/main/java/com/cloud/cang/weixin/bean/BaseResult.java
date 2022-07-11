package com.cloud.cang.weixin.bean;

import java.io.Serializable;

/**
 * 微信请求状态数据
 *
 * @author zhouhong
 */
public class BaseResult implements Serializable{

  private static final String SUCCESS_CODE = "0";

  private String errcode;
  private String errmsg;

  public String getErrcode() {
    return errcode;
  }

  public void setErrcode(String errcode) {
    this.errcode = errcode;
  }

  public String getErrmsg() {
    return errmsg;
  }

  public void setErrmsg(String errmsg) {
    this.errmsg = errmsg;
  }

  public boolean isSuccess() {
    return errcode == null || errcode.isEmpty() || errcode.equals(SUCCESS_CODE);
  }

}
