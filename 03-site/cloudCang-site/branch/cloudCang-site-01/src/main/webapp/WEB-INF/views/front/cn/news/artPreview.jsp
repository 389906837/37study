<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/include/taglibs.jsp"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="视觉识别，机器视觉，新零售，无人零售" />
    <meta name="description" content="${article.snewsAbstract}" />
    <title>${article.stitle} - 江苏叁拾柒号仓智能科技有限公司</title>
    <link rel="stylesheet" href="${staticSource}/static/css/index.css" />
    <link rel="shortcut icon" href="${staticSource}/static/favicon.ico" type="image/x-icon" />
    <script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="${staticSource}/static/js/jquery.lazyload.min.js"></script>
    <script type="text/javascript" src="${staticSource}/static/js/jquery.three-slide.js"></script>
</head>
<body>
<div class="mediareportsdetails">
    <div class="container">
        <div class="navpath">
            <img src="${staticSource}/static/images/inner/home.png" /> 官网首页 > 新闻预览 > 新闻详情
        </div>
        <div class="details">
            <h1>${article.stitle}</h1>
            <p>${content.scontent}</p>
        </div>
    </div>
</div>
</body>
</html>
