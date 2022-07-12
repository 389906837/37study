<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>${index.indexTitle}</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no" />
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="${staticSource}/static/css/comm.css" />
    <link rel="stylesheet" href="${staticSource}/static/css/style.css?1.0" />
</head>
<body>
    <%@ include file="/common/include/commonHeader.jsp" %>
    <div class="container">
        <div class="container-c">
            <c:if test="${fn:length(index.announcementList) > 0}">
                <div class="scroll-notice refund-inforLi-flex">
                    <span>最新通知：</span>
                    <div class="shop-infor-flex scroll-colum" id="scrollDiv">
                        <ul>
                            <c:forEach var="item" items="${index.announcementList}" varStatus="L">
                                <li><a href="javascript:void(0);">${item.stitle}</a></li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </c:if>
            <div class="md logo-pack">
                <div class="logo"  <c:if test="${fn:length(index.announcementList) > 0}">style="margin-top:5.5rem;"</c:if>>
                    <img src="<c:if test="${empty index.slogo}">${staticSource}/static/images/logo.png</c:if>
                         <c:if test="${!empty index.slogo}">${dynamicSource}${index.slogo}</c:if>" class="logo-pic"
                         <c:if test="${index.isReplenishment ne 1}">style="float:none;" </c:if> />
                    <c:if test="${index.isReplenishment eq 1}">
                        <span>|</span>
                        <div class="replenishment" onclick="jumpReplenishmentIndex();"><img src="${staticSource}/static/images/shopping.png"/>我是补货员</div>
                    </c:if>
                </div>
                <p class="md kaimen-tip">${index.indexHint}</p>
            </div>
            <img class="round-head" src="${staticSource}/static/images/round-head.png" />
            <div class="kaimen-bg">
                <div class="kaimen-round md">请稍后…</div>
            </div>
            <c:if test="${fn:length(index.advertisList) > 0}">
                <div class="adv_index">
                    <div class="buy-avd slide">
                        <c:forEach items="${index.advertisList}" var="item" varStatus="L">
                            <img src="${dynamicSource}${item.scontentUrl}" />
                        </c:forEach>
                    </div>
                </div>
            </c:if>
        </div>
        <%@ include file="/common/include/service-tell.jsp"%>
        <jsp:include page="/common/include/foot.jsp">
            <jsp:param  name="selected" value="1" />
        </jsp:include>
    </div>
    <!-- 开通免密支付弹出框 -->
    <div class="eject"></div>
    <div class="popup-bg">
        <div class="popup-contant md relative">
            <img class="indexclose-icon" src="${staticSource}/static/images/indexclose-icon.png" />
            <img class="avoidClose-pic" src="${staticSource}/static/images/avoidClose-pic.png" />
            <p class="popup-txt">立即开通免密支付功能</p>
            <p class="popup-txt">享受无感购物新体验</p>
            <a class="button-icon check-btn check-btn-e1" href="javascript:void(0);" onclick="noRemind();" style="margin-top:2.5rem; width: 40%">不再提醒</a>
            <a class="button-icon check-btn" href="javascript:void(0);" onclick="openFree('${signPage}');" style="margin-top:2.5rem; width: 40%">立即开通</a>
        </div>
    </div>
    <div class="popup-bg">
        <div class="popup-contant md relative">
            <img class="avoidClose-pic" src="${staticSource}/static/images/avoidClose-pic.png" />
            <p class="popup-txt">立即开通免密支付功能</p>
            <p class="popup-txt">享受无感购物新体验</p>
            <a class="button-icon check-btn" href="javascript:void(0);" onclick="openFree('${signPage}');" style="margin-top:2.5rem;">立即开通</a>
        </div>
    </div>
    <!-- 非正常开门的情况 -->
    <div class="popup-bg">
        <div class="popup-contant md relative">
            <img class="indexclose-icon" src="${staticSource}/static/images/indexclose-icon.png" />
            <img class="noopen-icon" src="${staticSource}/static/images/index-noopen-icon.png" />
            <p class="popup-txt" id="alert_text"></p>
            <p class="popup-txt" style="font-size:1.4rem;padding-top:0.5rem;">如有需要，请联系客服</p>
            <a class="button-icon check-btn" href="javascript:void(0);" onclick="closeTip();" style="margin-top:2.5rem;">关闭</a>
        </div>
    </div>

    <!-- 异常订单 -->
    <div class="popup-bg">
        <div class="popup-contant md relative">
            <img class="noopen-icon" src="${staticSource}/static/images/index-noopen-icon.png" />
            <p class="popup-txt">有异常订单未支付</p>
            <a class="button-icon check-btn" href="javascript:void(0);" onclick="myOrder();" style="margin-top:2.5rem;">去支付</a>
        </div>
    </div>
    <script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="${staticSource}/static/js/comm.js?236"></script>
    <script type="text/javascript" src="${staticSource}/static/js/util.js"></script>
    <c:if test="${fn:length(index.announcementList) > 0}">
        <script type="text/javascript" src="${staticSource}/static/js/scroll.js"></script>
        <script type="text/javascript">
            $("#scrollDiv").Scroll({line:1,speed:500,timer:3000,up:"but_up",down:"but_down"});
        </script>
    </c:if>
    <c:if test="${fn:length(index.advertisList) > 0}">
        <script type="text/javascript" src="${staticSource}/static/js/jquery-slide.js"></script>
        <script type="text/javascript">
            $('.slide').slide({
                actionIn: 'left',
                actionOut: 'left',
                delay: 5000,
                page: {
                    shape: 'oval',
                    no: false,
                    margin: 'auto auto 10 auto',
                    borderWidth: 1,
                    borderColor: '#ffffff',
                    bgcolorHover: '#ffffff',
                    width: 8,
                    height:8
                }
            });
        </script>
    </c:if>

    <c:choose>
        <c:when test="${(empty index.isOpenFreeData or index.isOpenFreeData eq 0) and index.isRemindOpenAndClose eq 1}">
            <script type="text/javascript">
                $(".eject").show();
                $(".popup-bg").eq(1).show();

            </script>
        </c:when>
        <c:when test="${index.isExceptionOrder eq 1}">
            <script type="text/javascript">
                $(".eject").show();
                $(".popup-bg").eq(3).show();
                $(".eject").click(function () {
                    closeTip();
                });
            </script>
        </c:when>
        <c:when test="${(empty index.isOpenFreeData or index.isOpenFreeData eq 0) and index.isRemindOpen eq 1}">
            <script type="text/javascript">
                $(".eject").show();
                $(".popup-bg").eq(0).show();
                $(".eject").click(function () {
                    closeTip();
                });
            </script>
        </c:when>
        <c:otherwise>
            <script type="text/javascript">
                $(".eject").click(function () {
                    closeTip();
                });
            </script>
        </c:otherwise>
    </c:choose>
    <script type="text/javascript">
        var isExit = false;
        $(function (){
            $('.container-c').css('height', $(window.top).height());
            $(".kaimen-round").attr("onclick","openDoor(1);");
            $(".kaimen-round").html("开 门");
            setTitle();
            openDoor(0);//系统主动开门
            $(".indexclose-icon").click(function(){
                closeTip();
            });
        });
        function closeTip() {
            $(".eject,.popup-bg").hide();
            openFlag = true;
            $(".kaimen-round").attr("onclick", "openDoor(1);");
            $(".kaimen-round").html("开 门");
            if (isExit) {
                exitApp();
            }
        }
        //发送开门操作
        function operOpenDoor() {
            global.reconnectFailCount = 0;
            parent.sendMsg('${index.openParam}');
        }
        var openFlag = true;
        function openDoor(operType){

            if(openFlag) {
                openFlag = false;
                if (operType == 1) {
                    Util.Waiting({wid: 'wait_popup'});
                    $(".kaimen-round").removeAttr("onclick");
                    $(".kaimen-round").html("请稍后…");
                }
                $.ajax({
                    type : "post",
                    data : {'operType': operType},
                    url : ctxRoot + "/uc/isAutoOpen",
                    dataType : "json",
                    success: function (msg) {
                        if (operType == 1) {
                            Util.waitingClose($("#wait_popup"));
                        }
                        if(msg.success) {
                            //成功返回
                            if(msg.data == '1'){
                                operOpenDoor();
                            } else {
                                openFlag = true;
                                if(operType != 0) {
                                    $(".kaimen-round").attr("onclick", "openDoor(1);");
                                    $(".kaimen-round").html("开 门");
                                    if (msg.msg == "1") {
                                        parent.wechatScan();
                                    } else if (msg.msg == "2") {
                                        parent.alipayScan();
                                    }
                                }
                            }
                        } else {
                            openFlag = true;
                            if (operType == 1) {
                                $(".kaimen-round").attr("onclick", "openDoor(1);");
                                $(".kaimen-round").html("开 门");
                            }
                            if (msg.errorCode == '1002') {
                                $(".eject").show();
                                $(".popup-bg").eq(3).show();
                            } else {
                                if (msg.errorCode == '1006') {
                                    isExit = true;
                                }
                                $("#alert_text").html(msg.msg);
                                $(".eject").show();
                                $(".popup-bg").eq(2).show();
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
            $(".eject").show();
            $(".popup-bg").eq(2).show();
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
        <%@ include file="/common/include/baidu.jsp"%>
    </script>
</body>
</html>