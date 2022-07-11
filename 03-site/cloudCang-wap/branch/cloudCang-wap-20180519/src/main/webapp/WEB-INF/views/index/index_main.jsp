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
    <link rel="stylesheet" href="${staticSource}/static/css/style.css" />
</head>
<body>

<iframe class="wap_iframe" id="wap_iframe" name="wap_iframe" width="100%" height="100%" src="${ctx}${indexPath}" frameborder="0" scrolling="yes"></iframe>
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/comm.js?1"></script>
<script type="text/javascript" src="${staticSource}/static/js/util.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/socket.io.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/soketUtil.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/alipayjsapi.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/jweixin-1.0.0.js"></script>
<c:if test="${!empty connectParam}">
    <script type="text/javascript">
        socketObj = connect(JSON.parse('${connectParam}'));
    </script>
</c:if>
<script type="text/javascript">
    var childWindow = $("#wap_iframe")[0].contentWindow;
    function sendMsg(msg) {//发送消息
        socketObj.emit('socketEvent', msg);
    }
    function jumpUrl(url) {//业务路径跳转
        document.getElementById('wap_iframe').src=url;
    }
    function alipayScan() {
        AlipayJSBridge.call('scan', {
            type: 'qr',
            actionType: 'scanAndRoute'
        });
    }
    function wechatScan() {
        wx.config(${jsonStr});
        wx.error(function(res){
            Util.Alert(res.errMsg);
        });
        wx.ready(function(){
            wx.scanQRCode({
                needResult : 0,
                scanType : [ "qrCode", "barCode" ]
            });
        });
    }
    function alipayExitApp() {
        window.location.href=ctxRoot+"/logout";
        AlipayJSBridge.call('exitApp');
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

    function alipayPhoto(imgObj,inputObj) {
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
                Util.Alert("操作失败");
                return;
            }
            $(imgObj).attr("src", "data:image/jpeg;base64," + result.dataURL);
            $(inputObj).val(result.dataURL);
        });
    }

    function wechatPhoto(imgObj,inputObj) {
        wx.config(${jsonStr});
        wx.error(function(res){
            Util.Alert(res.errMsg);
        });
        wx.ready(function(){
            wx.chooseImage({
                count: 1,
                success: function (res) {
                    // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
                    wx.getImageInfo({
                        src: res.tempFilePaths[0],
                        success: function (res) {
                            var image = new Image();
                            image.src = res.path;
                            var base64 = getBase64Image(image);
                            $(imgObj).attr("src", "data:image/jpeg;base64," + base64);
                            $(inputObj).val(base64);
                        }
                    })
                }
            });
        });
    }
    function getBase64Image(img,width,height) {
        var canvas = document.createElement("canvas");
        canvas.width = width ? width : img.width;
        canvas.height = height ? height : img.height;
        var ctx = canvas.getContext("2d");
        ctx.drawImage(img, 0, 0, img.width, img.height);
        var ext = img.src.substring(img.src.lastIndexOf(".")+1).toLowerCase();
        var dataURL = canvas.toDataURL("image/"+ext);
        return dataURL;
    }
    <%@ include file="/common/include/baidu.jsp"%>
</script>
</body>
</html>