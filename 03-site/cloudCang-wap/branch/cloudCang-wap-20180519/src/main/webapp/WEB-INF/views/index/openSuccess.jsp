<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>开门成功</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no" />
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="${staticSource}/static/css/comm.css">
    <link rel="stylesheet" href="${staticSource}/static/css/style.css">

</head>
<body>
    <div class="container">
        <%@ include file="/common/include/commonHeader.jsp"%>
        <div class="buy-success md">
            <img class="buy-successicon" src="${staticSource}/static/images/buy-successicon.png" >
            <h4>${open.openHint}</h4>
        </div>
        <c:if test="${fn:length(preferentialInfos) > 0}">
            <div class="buy-quan">
                <div class="buy-quan-title">
                    <span>本机优惠</span>
                </div>
                <div class="preferential-infor">
                    <c:forEach items="${preferentialInfos}" var="item" varStatus="status">
                      <p>
                       ${status.count}：${item}
                      </p>
                    </c:forEach>
                </div>
            </div>
        </c:if>
        <%@ include file="/common/include/foot.jsp"%>
    </div>
    <script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="${staticSource}/static/js/comm.js"></script>
    <script type="text/javascript" src="${staticSource}/static/js/socket.io.js"></script>
    <script type="text/javascript" src="${staticSource}/static/js/soketUtil.js"></script>
    <script type="text/javascript">
        <%@ include file="/common/include/baidu.jsp"%>
    </script>
</body>
</html>