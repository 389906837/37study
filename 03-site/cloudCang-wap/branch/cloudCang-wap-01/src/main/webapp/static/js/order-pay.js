
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
                    if(datas.ipayType == 30) {//微信支付
                        if(datas.ipayWay == 10){
                            parent.onBridgeReady(datas.orderId, sorderCode, datas.jsApi);//公众号支付
                        } else if(datas.ipayWay == 20){//H5支付
                            window.location.href = datas.mwebUrl;
                        }else if(datas.ipayWay == 70){
                            Util.Alert(msg.msg);
                        }
                    } else if(datas.ipayType == 40) {//支付宝支付
                        if(datas.ipayWay==70){
                            Util.Alert(msg.msg);
                        }else {
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

