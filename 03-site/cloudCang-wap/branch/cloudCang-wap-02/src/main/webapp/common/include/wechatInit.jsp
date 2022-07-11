<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="${staticSource}/static/js/jweixin-1.5.0.js"></script>
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


    /*#####################微信支付分#######################微信支付分###################微信支付分###########################微信支付分############################################################*/


    function openService(businessType, queryString) {
        wx.openBusinessView({
            businessType: 'wxpayscore',
            queryString: 'a=1&b=2&c=3',
            /* envVersion: 'release',*/
            success: function (res) {
                // 这里是回调函数
            }
        })
    }

    function wechatPoint(url) {
        $.ajax({
            type: "POST",
            dataType: "json",
            url: ctxRoot + url,
            success: function (msg) {
                if (msg.success) {
                    goToWXScore(msg.data)
                } else {

                }
            }
        });
    }
    /*    function openSerivice(queryString) {

     var wechatInfo = navigator.userAgent.match(/MicroMessenger\/([\d\.]+)/i);

     var wechatVersion = wechatInfo[1];

     if (compareVersion(wechatVersion, '7.0.5') >= 0) {
     goToWXScore(queryString);
     } else {
     // 提示用户升级微信客户端版本
     window.href = 'https://support.weixin.qq.com/cgi-bin/readtemplate?t=page/common_page__upgrade&text=text005&btn_text=btn_text_0'
     }
     }*/

    /**
     * 跳转微信支付分
     */
    function goToWXScore(queryString) {
        wx.config(${jsonStrPoint});
        wx.ready(function () {
            wx.checkJsApi({
                jsApiList: ['openBusinessView'], // 需要检测的JS接口列表
                success: function (res) {
                    // 以键值对的形式返回，可用的api值true，不可用为false
                    // 如：{"checkResult":{"openBusinessView":true},"errMsg":"checkJsApi:ok"}
                    if (res.checkResult.openBusinessView) {
                        wx.invoke(
                            'openBusinessView', {
                                businessType: 'wxpayScoreEnable',
                                queryString: queryString
                            },
                            function (res) {
                                // 从微信侧小程序返回时会执行这个回调函数
                                if (parseInt(res.err_code) === 0) {
                                    // 返回成功
                                    jumpIndex();
                                } else {
                                    // 返回失败
                                    jumpIndex();
                                }
                            });
                    }
                }
            });
        });
    }
    /**
     * 版本号比较
     * @param {String} v1
     * @param {String} v2
     */
    /*  function compareVersion(v1, v2) {
     v1 = v1.split('.')
     v2 = v2.split('.')
     const len = Math.max(v1.length, v2.length)

     while (v1.length < len) {
     v1.push('0')
     }
     while (v2.length < len) {
     v2.push('0')
     }

     for (let i = 0; i < len; i++) {
     const num1 = parseInt(v1[i])
     const num2 = parseInt(v2[i])

     if (num1 > num2) {
     return 1
     } else if (num1 < num2) {
     return -1
     }
     }

     return 0
     }*/


</script>
