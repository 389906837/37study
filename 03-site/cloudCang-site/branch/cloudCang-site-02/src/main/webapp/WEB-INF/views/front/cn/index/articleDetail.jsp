<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/include/taglibs.jsp"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="${article.skeys}" />
    <meta name="description" content="${article.snewsAbstract}" />
    <title>${article.stitle} - 江苏叁拾柒号仓智能科技有限公司</title>
    <link rel="stylesheet" href="${staticSource}/static/css/index.css">
    <link rel="shortcut icon" href="${staticSource}/static/favicon.ico" type="image/x-icon">
    <script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="${staticSource}/static/js/jquery.lazyload.min.js"></script>
    <script type="text/javascript" src="${staticSource}/static/js/jquery.three-slide.js"></script>
</head>
<body>
<div class="main mediareportslist-bg">
<jsp:include page="/common/include/mediaFrontTop.jsp">
    <jsp:param name="selected" value="2"/>
</jsp:include>
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
    </div>
</div>
<jsp:include page="/common/include/indexFrontBottom.jsp" />
</body>
<script type="text/javascript">
    <%@ include file="/common/include/baidu.jsp"%>
</script>
</html>
