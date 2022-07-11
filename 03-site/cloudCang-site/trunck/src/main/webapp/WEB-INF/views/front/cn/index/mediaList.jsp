<%--
  Created by IntelliJ IDEA.
  User: 37cang-1
  Date: 2018/12/27
  Time: 11:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="视觉识别，机器视觉，新零售，无人零售，新零售最新资讯" />
    <meta name="description" content="最新媒体发布新闻；37号仓是一家人工智能领域机器视觉方面的研发型公司，企业拥有一支高水平的研发和管理团队，致力于人工智能视觉识别系统的软硬件研发、生产和推广 - 江苏叁拾柒号仓智能科技有限公司。
" />
    <title>媒体报道 - 江苏叁拾柒号仓智能科技有限公司</title>
    <link rel="stylesheet" href="${staticSource}/static/css/index.css">
    <link rel="shortcut icon" href="${staticSource}/static/favicon.ico" type="image/x-icon">

</head>
<body>
<jsp:include page="/common/include/mediaFrontTop.jsp" /><hr>
<%--<div class="support-img"></div>--%>
<div class="mediareportslist">
    <div class="container">
        <div class="navpath">
            <img src="${staticSource}/static/images/inner/home.png">
            <a href="${ctx}/">官网首页 > </a> <a class="active">媒体报道 </a>
        </div>
        <div class="listall">


            <c:if test="${not empty mediaList }">
                <c:forEach items="${mediaList}" var="art">



                    <div class="listeach">
                        <div class="date">

                            <h1><fmt:formatDate value="${art.taddTime}" pattern="dd" /></h1>

                            <p><fmt:formatDate value="${art.taddTime}" pattern="yyyy-MM" /></p>
                        </div>
                        <div class="pic"><img src="${art.stitleImage}"></div>
                        <div class="details">
                            <a href="/news/media/${fn:replace(art.id,
                                '0000000000000000000000', '')}.html" ><h3>${art.stitle}</h3></a>
                            <p>${art.stitle}</p>
                        </div>

                    </div>


                    <%--    <a href="../cn/skip?parameter=${art.stitle}" >${art.stitle}</a>
                        <input type="hidden" id="ssss" name="parameter" value="${art.stitle}">--%>

                </c:forEach>
            </c:if>







        </div>
    </div>

</div>

<jsp:include page="/common/include/indexFrontBottom.jsp" />
</body>



<script>
    $(document).ready(function(){


        $('#mtbd').parent().addClass('active');

    });

</script>
</html>
