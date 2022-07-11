<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>补货员已关门</title>
    <link rel="stylesheet" href="${staticSource}/static/css/comm.css"/>
    <link rel="stylesheet" href="${staticSource}/static/css/index.css?2">
</head>
<body>
<%@ include file="/common/include/commonHeader.jsp" %>
<%@ include file="/common/include/socket.jsp" %>
<div class="open_container" style="margin-bottom:8rem;">
    <div class="door_success">
        <div class="success">
            <div></div>
        </div>
        <p class="door_title">已关门请确认补货信息</p>
    </div>
    <div class="replenishment-info">
        <div class="replenishment-info-title">
            <div>商品名</div>
            <div>上架数量</div>
            <div>现有总数量</div>
        </div>
        <div class="replenishment-info-list">
            <c:forEach var="item" items="${replenishmentConfirmResult.replenishmentConfirmCommodityList}" varStatus="i">
                <ul>
                    <li>${item.scommodityFullName}</li>
                    <c:if test="${item.type == 10 }">
                        <li class="jin">+${item.num}</li>
                    </c:if>
                    <c:if test="${item.type == 20 }">
                        <li class="chu">-${item.num}</li>
                    </c:if>
                    <li>${item.currentNum}</li>
                </ul>
            </c:forEach>

        </div>
    </div>
</div>
<div class="replenishment-fix">
    <div class="replenishment-num">
        <span>上架总数：${replenishmentConfirmResult.upShelfTotal}</span>
        <span>下架总数：${replenishmentConfirmResult.lowShelfTotal}</span>
        <span>当前总数：${replenishmentConfirmResult.currentTotal}</span>
    </div>
    <div class="replenishment-button">
        <a href="javascript:void(0);" onclick="toConfirmz();">确 认</a>
        <%--   <a href="">重新调整</a>
           <a href="">异常反馈</a>--%>
    </div>
</div>

<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js?1"></script>
<script type="text/javascript" src="${staticSource}/static/js/comm.js?1"></script>
<script type="text/javascript" src="${staticSource}/static/js/soketUtilN.js?version=3.0"></script>
<script type="text/javascript">

    function toConfirmz() {
        $.ajax({
            type: "POST",
            url: "${ctx}/replenishment/confirm",
            data: {"deviceId": "${deviceId}","userId":"${userId}"},
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    jumpUrl(ctxRoot + "/replenishment/closeDoorSuccess");
                } else {
                    jumpUrl(ctxRoot + "/uc/error?errorCode=10016&code=" + data.errorCode);
                }
            }
        });
    }
    <%@ include file="/common/include/baidu.jsp"%>
</script>

</body>
</html>