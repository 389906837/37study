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
    <link rel="stylesheet" href="${staticSource}/static/css/style.css?1" />
</head>
<body>
    <div class="container">
        <div class="container-c">
            <%@ include file="/common/include/commonHeader.jsp"%>
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
            <div class="md">
                <div class="logo">
                    <img src="${staticSource}/static/images/logo.png" class="logo-pic"
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
                <div class="kaimen-round md" onclick="openDoor(1);">开 门</div>
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
        <%@ include file="/common/include/foot.jsp"%>
    </div>
    <!-- 开通免密支付弹出框 -->
    <div class="eject"></div>
    <div class="popup-bg">
        <div class="popup-contant md relative">
            <img class="indexclose-icon" src="${staticSource}/static/images/indexclose-icon.png" />
            <img class="avoidClose-pic" src="${staticSource}/static/images/avoidClose-pic.png" />
            <p class="popup-txt">立即开通免密支付功能</p>
            <p class="popup-txt">享受无感购物新体验</p>
            <a class="button-icon check-btn" href="javascript:void(0);" onclick="openFree('${signPage}');" style="margin-top:2.5rem;">立即开通</a>
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
            <p class="popup-txt">有异常订单未支付 </p>
            <a class="button-icon check-btn" href="javascript:void(0);" onclick="myOrder();" style="margin-top:2.5rem;">去支付</a>
        </div>
    </div>
    <script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="${staticSource}/static/js/comm.js?34"></script>
    <script type="text/javascript" src="${staticSource}/static/js/socket.io.js"></script>
    <script type="text/javascript" src="${staticSource}/static/js/soketUtil.js"></script>
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
            $(".slide").slide({
                actionIn: 'left',
                actionOut: 'left',
                delay: 5000
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
        $(function (){
            openDoor(0);//系统主动开门
            $(".indexclose-icon").click(function(){
                $(".eject,.popup-bg").hide();
            });
            $('.container-c').css('height', $(window.top).height());
        });
        function closeTip() {
            $(".eject,.popup-bg").hide();
        }
        //发送开门操作
        function operOpenDoor() {
            global.reconnectFailCount = 0;
            parent.sendMsg('${index.openParam}');
        }
        function openDoor(operType){
            $.ajax({
               type : "post",
               data : {'operType': operType},
               url : ctxRoot + "/index/isAutoOpen",
               dataType : "json",
               success: function (msg) {
                   if(msg.success) {
                       //成功返回
                       if(msg.data == '1'){
                           $(".kaimen-round").removeAttr("onclick");
                           operOpenDoor();
                       } else {
                           if(operType != 0) {
                               if (msg.msg == "1") {
                                   parent.wechatScan();
                               } else if (msg.msg == "2") {
                                   parent.alipayScan();
                               }
                           }
                       }
                   } else {
                       if (msg.errorCode == '1002') {
                           $(".eject").show();
                           $(".popup-bg").eq(3).show();
                       } else {
                           $("#alert_text").html(msg.msg);
                           $(".eject").show();
                           $(".popup-bg").eq(2).show();
                       }
                   }
                }
            });
        }
        function openFail(msg) {
            $(".kaimen-round").attr("onclick", "openDoor(1);");
            Util.Alert("开门失败(" + msg + ")");
        }
        <%@ include file="/common/include/baidu.jsp"%>
    </script>
</body>
</html>