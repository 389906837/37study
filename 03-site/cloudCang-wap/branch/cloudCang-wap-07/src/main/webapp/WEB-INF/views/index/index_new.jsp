<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>${indexData.indexTitle}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="format-detection" content="telephone=yes"/>
    <meta name="renderer" content="webkit"/>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
    <meta name="format-detection" content="telephone=no"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <link rel="stylesheet" href="${staticSource}/static/css/comm.css?1"/>
    <link rel="stylesheet" href="${staticSource}/static/css/index.css?1.9"/>
    <script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
    <style type="text/css">
        /* .slide {height: 15rem !important;}*/
        html, body {
            height: 100%
        }

        body {
            background-color: #FFFFFF;
        }
    </style>
</head>
<body>
<%@ include file="/common/include/commonHeader.jsp" %>
<%-- 购物会员首页 --%>
<%@ include file="/common/include/shopping.jsp" %>
<%--购物开门成功--%>
<%@ include file="/common/include/shoppingOpenSuccess.jsp" %>
<%--实时订单--%>
<%@ include file="/common/include/shoppingRealtimeOrder.jsp" %>

<script type="text/javascript" src="${staticSource}/static/js/jquery-slide.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/comm.js?1.1"></script>
<script type="text/javascript" src="${staticSource}/static/js/util.js?226"></script>
<script type="text/javascript" src="${staticSource}/static/js/socket.io.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/soketUtilN.js?version=2.9"></script>
<script src="${staticSource}/static/js/jsrender.min.js" type="text/javascript"></script>
<script src="${staticSource}/static/js/jsrender.converters.js" type="text/javascript"></script>
<c:if test="${alipay eq 1}">
    <%@ include file="/common/include/alipayInit.jsp" %>
</c:if>
<c:if test="${wechat eq 1}">
    <%@ include file="/common/include/wechatInit.jsp" %>
</c:if>
<%@ include file="/common/include/socket.jsp" %>


<script type="text/javascript">
    var isExit = false;
    var openTimeOut = 30;//开门超时时间
    var openTimer;
    $(function () {
        $(".opendoor").attr("onclick", "openDoor(1);");
        // $(".opendoor").html("<span>购</span><span>物</span><span>开</span><span>门</span>");
        if (connectFlag == 1) {
            openDoor(0);
            //系统主动开门
        } else {
            openTimer = setInterval(function () {//定时器1秒执行
                openTimeOut--;
                if (openTimeOut <= 0 || connectFlag == 1) {
                    clearInterval(openTimer);
                    openTimeOut = 30;
                    if (connectFlag == 1) {
                        openDoor(0);
                    } else {
                        jumpUrl(ctxRoot + "/uc/error?errorCode=10018");
                    }
                }
            }, 1000);
        }

    });
    function closeTip() {
        $(".popup_bg").each(function () {
            if (!$(this).hasClass("close")) {
                $(this).addClass("close");
            }
        });
        openFlag = true;
        $(".opendoor").attr("onclick", "openDoor(1);");
        //$(".opendoor").html("<span>购</span><span>物</span><span>开</span><span>门</span>");
        if (isExit) {
            exitApp();
        }
    }
    //发送开门操作
    function operOpenDoor() {
        global.reconnectFailCount = 0;
        sendMsg('${openParam}');
    }
    var openFlag = true;
    function openDoor(operType) {
        if (openFlag) {
            openFlag = false;
            if (operType == 1) {
                Util.Waiting({wid: 'wait_popup'});
                $(".opendoor").removeAttr("onclick");
                // $(".opendoor").html("请稍后…");
            }
            $.ajax({
                type: "post",
                data: {'operType': operType},
                url: ctxRoot + "/uc/isAutoOpen",
                dataType: "json",
                success: function (msg) {
                    if (operType == 1) {
                        Util.waitingClose($("#wait_popup"));
                    }
                    if (msg.success) {
                        //成功返回
                        if (msg.data == '1') {
                            operOpenDoor();
                        } else {
                            openFlag = true;
                            if (operType != 0) {
                                $(".opendoor").attr("onclick", "openDoor(1);");
                                //$(".opendoor").html("<span>购</span><span>物</span><span>开</span><span>门</span>");
                                if (msg.msg == "1") {
                                    wechatScan();
                                } else if (msg.msg == "2") {
                                    alipayScan();
                                }
                            }
                        }
                    } else {

                        openFlag = true;
                        if (operType == 1) {
                            $(".opendoor").attr("onclick", "openDoor(1);");
                            //$(".opendoor").html("<span>购</span><span>物</span><span>开</span><span>门</span>");
                        }
                        if (msg.errorCode == '1002') {
                            //$(".eject").show();
                            $(".popup_bg").eq(3).removeClass('close');
                        } else if (msg.errorCode == '1009') {
                            $(".popup_bg").eq(1).removeClass('close');
                        } else {
                            if (msg.errorCode == '1006') {
                                isExit = true;
                            }
                            $("#alert_text").html(msg.msg);
                            //$(".eject").show();
                            $(".popup_bg").eq(2).removeClass('close');
                        }
                    }
                }
            });
        }
    }
    function openFail(msg) {
        $(".opendoor").attr("onclick", "openDoor(1);");
        //$(".opendoor").html("<span>购</span><span>物</span><span>开</span><span>门</span>");
        $("#alert_text").html(msg);
        //$(".eject").show();
        Util.waitingClose($("#wait_popup"));
        Util.waitingClose($("#wait_popup1"));
        $(".popup_bg").eq(2).removeClass('close');
    }
    function noRemind() {
        $.ajax({
            type: "post",
            url: ctxRoot + "/index/noRemind",
            dataType: "json",
            success: function () {
                closeTip();
            }
        });
    }
    <%@ include file="/common/include/baidu.jsp" %>
</script>
</body>
</html>