<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="${staticSource}/static/js/alipayjsapi.min.js"></script>
<script type="text/javascript">
    function alipayReady(callback) {
        if (window.AlipayJSBridge) {
            callback && callback();
        } else {
            // 如果没有注入则监听注入的事件
            document.addEventListener('AlipayJSBridgeReady', callback, false);
        }
    }
    function alipayCallback() {
        document.addEventListener('resume', function (event) {
            event.preventDefault();
            alipayReady(alipayCallback);
        });
    }
    alipayReady(alipayCallback);

    function alipaySetTitle(title) {
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
        }, function (result) {
            if (!Util.isEmpty(result.qrCode)) {
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    data: {'memberType': memberType},
                    url: ctxRoot + '/index/successScan',
                    success: function () {
                        parent.window.location.href = result.qrCode;
                    }
                });
            }
        });
    }
    function alipayExitApp() {
        window.location.href = ctxRoot + "/logout";
        AlipayJSBridge.call('exitApp');
    }
    function alipayPhoto(liObj, inputObj) {
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
        }, function (result) {
            if (result.error == 10 || result.error == 11) {
                //Util.Alert("操作失败");
                return;
            }
            var html = '<img src="data:image/jpeg;base64,' + result.dataURL + '" />';
            $(liObj).html(html);
            //$(liObj).removeAttr("onclick");
            $(inputObj).val(result.dataURL);
        });
    }
    function ailpayPreviewImage(init, array) {
        AlipayJSBridge.call('imageViewer', {
            images: array,
            init: init
        }, function () {

        });
    }
</script>
