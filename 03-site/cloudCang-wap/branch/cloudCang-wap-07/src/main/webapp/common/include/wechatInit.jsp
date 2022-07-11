<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="${staticSource}/static/js/jweixin-1.2.0.js"></script>
<script type="text/javascript">

    function wechatSetTitle(title) {
        document.title = title;
    }
    function wechatScan(memberType) {
        if (Util.isEmpty(memberType)) {
            memberType = 0
        }
        wx.config(${jsonStr});
        wx.error(function (res) {
            Util.Alert(res.errMsg);
        });
        wx.ready(function () {
            wx.scanQRCode({
                needResult: 1,
                scanType: ["qrCode"],
                success: function (res) {
                    $.ajax({
                        type: "POST",
                        dataType: "json",
                        data: {'memberType': memberType},
                        url: ctxRoot + '/index/successScan',
                        success: function () {
                            window.location.href = res.resultStr;
                        }
                    });
                }
            });
        });
    }


    function wechatExitApp() {
        window.location.href = ctxRoot + "/logout";
        wx.config(${jsonStr});
        wx.error(function (res) {
            Util.Alert(res.errMsg);
        });
        wx.ready(function () {
            wx.closeWindow();
        });
    }


    function wechatPhoto(liObj, inputObj) {
        wx.config(${jsonStr});
        wx.error(function (res) {
            Util.Alert(res.errMsg);
        });
        wx.ready(function () {
            wx.chooseImage({
                count: 1,
                success: function (res) {
                    var localIds = res.localIds[0];
                    if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
                        wx.getLocalImgData({
                            localId: localIds, // 图片的localID
                            success: function (res) {
                                var base64 = res.localData; // localData是图片的base64数据，可以用img标签显示
                                base64 = base64.replace('jgp', 'jpeg');
                                var html = '<img src="' + base64 + '" />';
                                $(liObj).html(html);
                            }
                        });
                    } else {
                        var html = '<img src="' + localIds + '" />';
                        $(liObj).html(html);
                    }
                    wx.uploadImage({
                        localId: localIds, // 需要上传的图片的本地ID，由chooseImage接口获得
                        isShowProgressTips: 1,// 默认为1，显示进度提示
                        success: function (res) {
                            var serverId = res.serverId; // 返回图片的服务器端ID
                            $(inputObj).val(serverId);
                        }
                    });
                }
            });
        });
    }
    function wechatPreviewImage(path, array) {
        wx.config(${jsonStr});
        wx.error(function (res) {
            Util.Alert(res.errMsg);
        });
        wx.ready(function () {
            wx.previewImage({
                current: path, // 当前显示图片的http链接
                urls: array // 需要预览的图片http链接列表
            });
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
                }
                // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
            }
        );
    }
</script>
