package com.cloud.cang.pay.wechat.notify;

public interface PaySuccessCallBack {

	void onSuccess(PayNotifyData payNotifyData) throws Exception;

}
