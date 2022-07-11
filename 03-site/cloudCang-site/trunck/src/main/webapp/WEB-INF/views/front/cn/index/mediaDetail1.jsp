<%--
  Created by IntelliJ IDEA.
  User: 37cang-1
  Date: 2018/12/27
  Time: 11:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/include/taglibs.jsp"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="视觉识别，机器视觉，新零售，无人零售，新零售最新资讯" />
    <meta name="description" content="${article.snewsAbstract}" />
    <title>${article.stitle}  - 江苏叁拾柒号仓智能科技有限公司</title>
    <link rel="stylesheet" href="${staticSource}/static/css/index.css">
    <link rel="shortcut icon" href="${staticSource}/static/favicon.ico" type="image/x-icon">
    <script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="${staticSource}/static/js/jquery.lazyload.min.js"></script>
    <script type="text/javascript" src="${staticSource}/static/js/jquery.three-slide.js"></script>
</head>

<body>
<jsp:include page="/common/include/mediaFrontTop.jsp" /><hr>
<%--${param.stitle}--%>
<div class="mediareportsdetails">
    <div class="container">
        <div class="navpath">
            <img src="${staticSource}/static/images/inner/home.png">
            <a href="${ctx}/"> 官网首页 > </a> <a href="${ctx}/news/media">媒体报道 > </a><a class="active">新闻详情 </a>
        </div>
        <div class="details">
           <%-- <h1>${mediaDetail.get[0].stitle}</h1>
            <p>${mediaDetail.get[0].stitle}</p>--%>

<%--
            <c:if test="${not empty content }">
                <c:forEach items="${content}" var="art1">

                        <h1>${art1.scontent}<h1>

                          &lt;%&ndash;  <p>${content.scontent}</p>&ndash;%&gt;
                </c:forEach>
            </c:if>--%>

               <h1>${article.stitle}</h1>
               <p>${content.scontent}</p>

        </div>
    </div>

</div>

<jsp:include page="/common/include/indexFrontBottom.jsp" />

</body>

<script type="text/javascript">
    $(document).ready(function(){


        $('#mtbd').parent().addClass('active');

    });
    <%@ include file="/common/include/baidu.jsp"%>
</script>
</html>
