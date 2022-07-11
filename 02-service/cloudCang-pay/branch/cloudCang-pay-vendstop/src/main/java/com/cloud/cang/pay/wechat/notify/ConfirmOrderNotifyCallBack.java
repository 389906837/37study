package com.cloud.cang.pay.wechat.notify;

/**
 * Created by YLF on 2019/7/22.
 */
public interface ConfirmOrderNotifyCallBack {
    void onSuccess(WechatPointsNotifyData wechatPointsNotifyData) throws Exception;
}
