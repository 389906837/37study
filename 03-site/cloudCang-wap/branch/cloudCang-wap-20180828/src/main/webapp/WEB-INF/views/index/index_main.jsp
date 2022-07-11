<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>${indexTitle}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no" />
    <title>智能无人零售</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="${staticSource}/static/css/comm.css" />
    <link rel="stylesheet" href="${staticSource}/static/css/style.css?1.0" />
</head>
<body>
<iframe class="wap_iframe" id="wap_iframe" name="wap_iframe" width="100%" height="100%" src="${ctx}${indexPath}" frameborder="0" scrolling="auto"></iframe>
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/comm.js?236"></script>
<script type="text/javascript" src="${staticSource}/static/js/util.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/socket.io.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/soketUtil.js?version=1.4.0"></script>
<c:if test="${alipay eq 1}">
<script type="text/javascript" src="${staticSource}/static/js/alipayjsapi.min.js"></script>
</c:if>
<c:if test="${wechat eq 1}">
<script type="text/javascript" src="${staticSource}/static/js/jweixin-1.2.0.js"></script>
</c:if>
<c:if test="${!empty connectParam}">
    <script type="text/javascript">
        function initConnect() {
            socketObj = connect(JSON.parse('${connectParam}'));
        }
        $(function() {
            initConnect();
        });
    </script>
</c:if>
<script type="text/javascript">
    var childWindow = $("#wap_iframe")[0].contentWindow;//客户端
    function sendMsg(msg) {//发送消息
        if (connectFlag != 1) {
            if (connectFlag == 2) {
                childWindow.openFail("连接服务器异常，请重新扫码");
                return;
            }
            if($.isFunction(initConnect)) {
                initConnect();
                var timer = setInterval(function () {//定时器1秒执行
                    clearInterval(timer);
                    if (connectFlag != 1) {
                        childWindow.openFail("连接服务器异常，请重新扫码");
                    } else {
                        socketObj.emit('socketEvent', msg);
                    }
                }, 1000);
                return;
            }
            childWindow.openFail("连接服务器异常，请重新扫码");
            return;
        }
        socketObj.emit('socketEvent', msg);
    }

    function jumpUrl(url) {//业务路径跳转
        document.getElementById('wap_iframe').src=url;
    }

    <c:if test="${alipay eq 1}">
        //======支付宝JSAPI操作=====
        function alipayReady(callback) {
            if (window.AlipayJSBridge) {
                callback && callback();
            } else {
                // 如果没有注入则监听注入的事件
                document.addEventListener('AlipayJSBridgeReady', callback, false);
            }
        }
        function alipayCallback() {
            document.addEventListener('resume', function(event) {
                event.preventDefault();
                alipayReady(alipayCallback);
            });
            /*document.addEventListener('back', function(e) {
             //e.preventDefault();
             if(location.href.indexOf("index/page") != -1
             || location.href.indexOf("index/main") != -1
             || location.href.indexOf("replenishment/index") != -1) {
             alipayExitApp();
             } else {
             AlipayJSBridge.call('popWindow');
             }
             });*/
        }
        alipayReady(alipayCallback);

        function alipaySetTitle(title) {
            if (Util.isEmpty(title)) {
                title = "${indexTitle}";
            }
            AlipayJSBridge.call('setTitle', {
                title: title
            });
        }

        function alipayScan(memberType) {
            if (Util.isEmpty(memberType)) {
                memberType = 0;
            }
            AlipayJSBridge.call('scan', {
                type: 'qr',
                actionType: 'scan'
            },function (result) {
                if(!Util.isEmpty(result.qrCode)) {
                    $.ajax({
                        type : "POST",
                        dataType : "json",
                        data : {'memberType': memberType},
                        url : ctxRoot + '/index/successScan',
                        success:function () {
                            parent.window.location.href = result.qrCode;
                        }
                    });
                }
            });
        }
        function alipayExitApp() {
            window.location.href=ctxRoot+"/logout";
            AlipayJSBridge.call('exitApp');
        }
        function alipayPhoto(liObj,inputObj) {
            AlipayJSBridge.call('photo', {
                dataType: 'dataURL',
                imageFormat: 'jpg/jpeg/png/gif/bmp',
                quality: 75,
                maxWidth: 540,
                maxHeight: 540,
                allowEdit: true,
                multimediaConfig: { // 可选，仅当该项被配置时，图片被传输至 APMultimedia
                    compress: 2, // 可选，默认为4。 0-低质量，1-中质量，2-高质量，3-不压缩，4-根据网络情况自动选择
                    business: "multiMedia" // 可选，默认为“NebulaBiz”
                }
            }, function(result) {
                if(result.error == 10 || result.error == 11) {
                    //Util.Alert("操作失败");
                    return;
                }
                var html = '<img src="data:image/jpeg;base64,'+ result.dataURL + '" />';
                $(liObj).html(html);
                //$(liObj).removeAttr("onclick");
                $(inputObj).val(result.dataURL);
            });
        }
        function ailpayPreviewImage(init,array) {
            AlipayJSBridge.call('imageViewer', {
                images: array,
                init:init
            },function () {

            });
        }
    </c:if>
    <c:if test="${wechat eq 1}">
        //======微信JSAPI操作=====
        function wechatSetTitle(title) {
            if (Util.isEmpty(title)) {
                title = "${indexTitle}";
            }
            document.title = title;
        }

        function wechatScan(memberType) {
            if (Util.isEmpty(memberType)) {
                memberType = 0;
            }
            wx.config(${jsonStr});
            wx.error(function(res){
                Util.Alert(res.errMsg);
            });
            wx.ready(function(){
                wx.scanQRCode({
                    needResult : 1,
                    scanType : ["qrCode"],
                    success: function (res) {
                        $.ajax({
                            type : "POST",
                            dataType : "json",
                            data : {'memberType': memberType},
                            url : ctxRoot + '/index/successScan',
                            success:function () {
                                window.location.href = res.resultStr;
                            }
                        });
                    }
                });
            });
        }

        function wechatExitApp() {
            window.location.href=ctxRoot+"/logout";
            wx.config(${jsonStr});
            wx.error(function(res){
                Util.Alert(res.errMsg);
            });
            wx.ready(function(){
                wx.closeWindow();
            });
        }


        function wechatPhoto(liObj,inputObj) {
            wx.config(${jsonStr});
            wx.error(function(res){
                Util.Alert(res.errMsg);
            });
            wx.ready(function(){
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
                                    var html = '<img src="'+base64+'" />';
                                    $(liObj).html(html);
                                }
                            });
                        } else {
                            var html = '<img src="'+ localIds + '" />';
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
        function wechatPreviewImage(path,array) {
            wx.config(${jsonStr});
            wx.error(function(res){
                Util.Alert(res.errMsg);
            });
            wx.ready(function(){
                wx.previewImage({
                    current: path, // 当前显示图片的http链接
                    urls: array // 需要预览的图片http链接列表
                });
            });
        }


        function onBridgeReady(orderId, sorderCode, data){
            WeixinJSBridge.invoke(
                'getBrandWCPayRequest', {
                    "appId":data.appId,     //公众号名称，由商户传入
                    "timeStamp":data.timestamp,         //时间戳，自1970年以来的秒数
                    "nonceStr":data.nonce, //随机串
                    "package":data.packageName,
                    "signType":data.signType,         //微信签名方式：
                    "paySign":data.signature //微信签名
                },
                function(res){
                    if(res.err_msg == "get_brand_wcpay_request:ok" ) {
                        $.ajax({
                            type : "POST",
                            dataType : "json",
                            url : ctxRoot + '/order/updateStatus',
                            data : {orderId:orderId},
                            async : false,
                            success : function(msg) {
                                if (msg.success) {
                                    jumpUrl(ctxRoot + "/order/paySuccess/"+sorderCode+".html");
                                }
                            }
                        });
                    } else {
                        jumpUrl(ctxRoot + "/personal/orderFail.html");
                    }// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
                }
            );
        }
    </c:if>
    <%@ include file="/common/include/baidu.jsp"%>
</script>
</body>
</html>