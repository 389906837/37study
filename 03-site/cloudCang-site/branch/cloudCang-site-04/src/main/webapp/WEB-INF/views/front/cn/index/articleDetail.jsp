<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/include/taglibs.jsp"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="${article.skeys}" />
    <meta name="description" content="${article.snewsAbstract}" />
    <title>${article.stitle} - 江苏叁拾柒号仓智能科技有限公司</title>
    <link rel="stylesheet" href="${staticSource}/static/css/index_new.css">
    <link rel="shortcut icon" href="${staticSource}/static/favicon.ico" type="image/x-icon">
    <script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="${staticSource}/static/js/jquery.lazyload.min.js"></script>
    <script type="text/javascript" src="${staticSource}/static/js/jquery.three-slide.js"></script>
    <style type="text/css">
        .details img{margin: 10px 0;}
    </style>
</head>
<body>
<div class="main mediareportslist-bg">
<%--<jsp:include page="/common/include/mediaFrontTop.jsp">
    <jsp:param name="selected" value="2"/>
</jsp:include>--%>
    <jsp:include page="/common/include/indexFrontTop_new.jsp" />
    <div class="headHeight"></div>

    <div class="mediareportsdetails">
    <div class="container">
        <div class="navpathdetails">
            <img src="${staticSource}/static/images/inner/home.png" />
            <a href="${ctx}/"> 官网首页 > </a>
            <c:if test="${type eq 'company'}"><a href="${ctx}/news/company">公司动态 > </a></c:if>
            <c:if test="${type eq 'media'}"><a href="${ctx}/news/media">媒体报道 > </a></c:if>
            <c:if test="${type eq 'industry'}"><a href="${ctx}/news/industry">行业新闻 > </a></c:if>
            <a class="active">新闻详情</a>
        </div>
        <div class="details">
               <h1>${article.stitle}</h1>
               <p>${content.scontent}</p>
        </div>
        <div class="recommend-news">
            <c:if test="${not empty articles}">
                <div class="recommend-news-title">相关推荐</div>
                <div class="recommend-news-item">
                    <c:forEach items="${articles}" var="item">
                        <div class="recommend-news-list">
                            <a href="${item.advertisUrl}">
                                <img src="${item.stitleImage}">
                                <p>${item.sketch}</p>
                            </a>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
        </div>
    </div>
</div>
    <jsp:include page="/common/include/indexFrontBottom_new.jsp" />
</body>
<script type="text/javascript">
    <%@ include file="/common/include/baidu.jsp"%>
</script>
</html>
