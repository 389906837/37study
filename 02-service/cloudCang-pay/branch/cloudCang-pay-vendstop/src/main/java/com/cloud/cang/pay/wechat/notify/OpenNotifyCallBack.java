package com.cloud.cang.pay.wechat.notify;

/**
 * Created by YLF on 2019/7/22.
 */
public interface OpenNotifyCallBack {
    void onSuccess(WechatPointsNotifyData openNotifyData) throws Exception;
}
