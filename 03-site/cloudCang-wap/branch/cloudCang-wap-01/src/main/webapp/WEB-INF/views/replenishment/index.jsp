<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>${index.indexTitle}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="${staticSource}/static/css/comm.css"/>
    <link rel="stylesheet" href="${staticSource}/static/css/style.css?1"/>
</head>
<body>
<%@ include file="/common/include/commonHeader.jsp" %>
<div class="container">
    <div class="container-c">
        <div class="md logo-pack">
            <div class="logo">
                <img src="<c:if test="${empty slogo}">${staticSource}/static/images/logo.png</c:if><c:if test="${!empty slogo}">${dynamicSource}${slogo}</c:if>"
                     class="logo-pic"/>
                <span>|</span>
                <div class="replenishment shoping-cart" onclick="jumpIndex();"><img
                        src="${staticSource}/static/images/shopping-cart.png"/>我要购物
                </div>
            </div>
            <p class="kaimen-tip">补货员姓名：${srealName}</p>
        </div>
        <img class="round-head" src="${staticSource}/static/images/round-head.png"/>
        <div class="kaimen-bg">
            <div class="kaimen-round md">请稍后…</div>
        </div>
    </div>
    <%@ include file="/common/include/service-tell.jsp" %>
    <%@ include file="/common/include/foot.jsp" %>
</div>
<div class="eject"></div>
<!-- 非正常开门的情况 -->
<div class="popup-bg">
    <div class="popup-contant md relative">
        <img class="indexclose-icon" src="${staticSource}/static/images/indexclose-icon.png">
        <img class="noopen-icon" src="${staticSource}/static/images/index-noopen-icon.png">
        <p class="popup-txt" id="alert_text"></p>
        <p class="popup-txt" style="font-size:1.4rem;padding-top:0.5rem;">如有需要，请联系客服</p>
        <a class="button-icon check-btn" href="javascript:void(0);" onclick="closeTip();"
           style="margin-top:2.5rem;">关闭</a>
    </div>
</div>
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/comm.js?236"></script>
<script type="text/javascript" src="${staticSource}/static/js/util.js"></script>
<script type="text/javascript">
    var isExit = false;
    $(function () {
        $('.container-c').css('height', $(window.top).height());
        $(".kaimen-round").attr("onclick", "openDoor(1);");
        $(".kaimen-round").html("补货员开门");
        setTitle();
        openDoor(0);//系统主动开门
        $(".indexclose-icon").click(function () {
            closeTip();
        });
    });
    function closeTip() {
        $(".eject,.popup-bg").hide();
        openFlag = true;
        $(".kaimen-round").attr("onclick", "openDoor(1);");
        $(".kaimen-round").html("补货员开门");
        if (isExit) {
            exitApp();
        }
    }
    //发送开门操作
    function operOpenDoor() {
        global.reconnectFailCount = 0;
        parent.sendMsg('${openParam}');
    }
    var openFlag = true;
    function openDoor(operType) {
        if (openFlag) {
            openFlag = false;
            if (operType == 1) {
                Util.Waiting({wid: 'wait_popup'});
                $(".kaimen-round").removeAttr("onclick");
                $(".kaimen-round").html("请稍后…");
            }
            $.ajax({
                type: "post",
                data: {'operType': operType, 'memberType': 1},
                url: ctxRoot + "/uc/isAutoOpen",
                dataType: "json",
                success: function (msg) {
                    if (operType == 1) {
                        Util.waitingClose($("#wait_popup"));
                    }
                    if (msg.success) {
                        //成功返回
                        if (msg.data == 1) {
                            operOpenDoor();
                        } else {
                            openFlag = true;
                            if (operType != 0) {
                                $(".kaimen-round").attr("onclick", "openDoor(1);");
                                $(".kaimen-round").html("补货员开门");
                                if (msg.msg == "1") {
                                    parent.wechatScan(1);
                                } else if (msg.msg == "2") {
                                    parent.alipayScan(1);
                                }
                            }
                        }
                    } else {
                        openFlag = true;
                        if (operType == 1) {
                            $(".kaimen-round").attr("onclick", "openDoor(1);");
                            $(".kaimen-round").html("补货员开门");
                        }
                        if (msg.errorCode == '1002') {
                            $(".eject").show();
                            $(".popup_bg").eq(3).show();
                        } else {
                            if (msg.errorCode == '1006') {
                                isExit = true;
                            }
                            $("#alert_text").html(msg.msg);
                            $(".eject").show();
                            // $(".eject,.popup-bg").show();
                            $(".popup_bg").eq(2).removeClass('close');
                        }
                    }
                }
            });
        }
    }
    function openFail(msg) {
        $(".kaimen-round").attr("onclick", "openDoor(1);");
        $(".kaimen-round").html("开 门");
        $("#alert_text").html(msg);
        $(".eject,.popup-bg").show();
    }
    <%@ include file="/common/include/baidu.jsp"%>
</script>
</body>
</html>