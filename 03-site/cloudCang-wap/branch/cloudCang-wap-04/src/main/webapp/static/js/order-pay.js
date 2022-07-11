function payment(sorderCode) {
    $.ajax({
        type: "post",
        data: {'orderCode': sorderCode},
        url: ctxRoot + "/order/payment",
        dataType: "json",
        success: function (msg) {
            if (msg.success) {
                var datas = msg.data;
                if (null != datas) {
                    if (datas.ipayType == 30) {//微信支付
                        if (datas.ipayWay == 10) {
                            onBridgeReady(datas.orderId, sorderCode, datas.jsApi);//公众号支付
                        } else if (datas.ipayWay == 20) {//H5支付
                            window.location.href = datas.mwebUrl;
                        } else if (datas.ipayWay == 70) {
                            Util.Alert(msg.msg);
                        }
                    } else if (datas.ipayType == 40) {//支付宝支付
                        if (datas.ipayWay == 70) {
                            Util.Alert(msg.msg);
                        } else {
                            $("body").html(datas.form);
                        }
                    }
                }
            } else {
                Util.Alert(msg.msg);
            }
        }
    });
}
function onBridgeReady(orderId, sorderCode, data) {
    WeixinJSBridge.invoke(
        'getBrandWCPayRequest', {
            "appId": data.appId,     //公众号名称，由商户传入
            "timeStamp": data.timestamp,         //时间戳，自1970年以来的秒数
            "nonceStr": data.nonce, //随机串
            "package": data.packageName,
            "signType": data.signType,         //微信签名方式：
            "paySign": data.signature //微信签名
        },
        function (res) {
            if (res.err_msg == "get_brand_wcpay_request:ok") {
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: ctxRoot + '/order/updateStatus',
                    data: {orderId: orderId},
                    async: false,
                    success: function (msg) {
                        if (msg.success) {
                            jumpUrl(ctxRoot + "/order/paySuccess/" + sorderCode + ".html");
                        }
                    }
                });
            } else {
                jumpUrl(ctxRoot + "/personal/orderFail.html");
            }// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
        }
    );
}
