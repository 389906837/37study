<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/include/taglibs.jsp"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="公司动态，新闻中心，37号仓动态，37号仓新闻，37号仓动态，媒体报道，媒体新闻，37号仓媒体报道，37号仓新闻报道" />
    <meta name="description" content="37号仓报道动态为用户提供37号仓资讯，37号仓平台新闻等最新消息介绍，让用户知晓37号仓的最新动态！37号仓-计算机视觉识别核心算法供应商，可广泛应用于新零售、环保、安防等领域！" />
    <title>报道动态-37号仓-计算机视觉识别核心算法供应商-江苏叁拾柒号仓智能科技有限公司</title>
    <link rel="stylesheet" href="${staticSource}/static/css/index.css">
    <link rel="shortcut icon" href="${staticSource}/static/favicon.ico" type="image/x-icon">
</head>
<body>

<div class="main">

    <!--头-->
    <jsp:include page="/common/include/noIndexFrontTop.jsp">
        <jsp:param name="selected" value="2"/>
    </jsp:include>
    <div class="mediareportslist">
        <div class="mediareportslist-img"></div>
        <div class="navpath">
            <a id="a_companyNews" href="javascript:void(0);" onclick="selectMenu('companyNews');" <c:if test="${type eq 'companyNews'}">class="active"</c:if>>公司动态</a>
            <a id="a_mediaReport" href="javascript:void(0);" onclick="selectMenu('mediaReport');" <c:if test="${type eq 'mediaReport'}">class="active"</c:if>>媒体报道</a>
            <a id="a_industryNews" href="javascript:void(0);" onclick="selectMenu('industryNews');" <c:if test="${type eq 'industryNews'}">class="active"</c:if>>行业新闻</a>
        </div>
        <div class="container">
            <div class="listall" id="div_companyNews" <c:if test="${type ne 'companyNews'}">style="display:none"</c:if>>
                <c:if test="${fn:length(companyNews) <= 0}">
                    <div class="listeach">
                        <div style="text-align: center;width: 100%;">暂无数据</div>
                    </div>
                </c:if>
                <c:if test="${fn:length(companyNews) > 0}">
                    <c:forEach var="item" items="${companyNews}" varStatus="L">
                        <div class="listeach">
                            <div class="date">
                                <h1><f:formatDate value="${item.taddTime}" pattern="dd" /></h1>
                                <p><f:formatDate value="${item.taddTime}" pattern="yyyy/MM" /></p>
                            </div>
                            <div class="pic"><c:if test="${empty item.stitleImage}"><img src="${staticSource}/static/images/mediareports/defaultThumbnail.jpg" /></c:if><c:if test="${!empty item.stitleImage}"><img src="${item.stitleImage}" /></c:if></div>
                            <div class="details">
                                <a href="${ctx}/news/company/${item.isort}.html" ><h3>${item.stitle}</h3></a>
                                <p>${item.snewsAbstract}</p>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
            </div>
            <div class="listall" id="div_mediaReport" <c:if test="${type ne 'mediaReport'}">style="display:none"</c:if>>
                <c:if test="${fn:length(mediaReport) <= 0}">
                    <div class="listeach">
                        <div style="text-align: center;width: 100%;">暂无数据</div>
                    </div>
                </c:if>
                <c:if test="${fn:length(mediaReport) > 0}">
                    <c:forEach var="item" items="${mediaReport}" varStatus="L">
                        <div class="listeach">
                            <div class="date">
                                <h1><f:formatDate value="${item.taddTime}" pattern="dd" /></h1>
                                <p><f:formatDate value="${item.taddTime}" pattern="yyyy/MM" /></p>
                            </div>
                            <div class="pic"><c:if test="${empty item.stitleImage}"><img src="${staticSource}/static/images/mediareports/defaultThumbnail.jpg" /></c:if><c:if test="${!empty item.stitleImage}"><img src="${item.stitleImage}" /></c:if></div>
                            <div class="details">
                                <a href="${ctx}/news/media/${item.isort}.html" ><h3>${item.stitle}</h3></a>
                                <p>${item.snewsAbstract}</p>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
            </div>
            <div class="listall" id="div_industryNews" <c:if test="${type ne 'industryNews'}">style="display:none"</c:if>>
                <c:if test="${fn:length(industryNews) <= 0}">
                    <div class="listeach">
                        <div style="text-align: center;width: 100%;">暂无数据</div>
                    </div>
                </c:if>
                <c:if test="${fn:length(industryNews) > 0}">
                    <c:forEach var="item" items="${industryNews}" varStatus="L">
                        <div class="listeach">
                            <div class="date">
                                <h1><f:formatDate value="${item.taddTime}" pattern="dd" /></h1>
                                <p><f:formatDate value="${item.taddTime}" pattern="yyyy/MM" /></p>
                            </div>
                            <div class="pic"><c:if test="${empty item.stitleImage}"><img src="${staticSource}/static/images/mediareports/defaultThumbnail.jpg" /></c:if><c:if test="${!empty item.stitleImage}"><img src="${item.stitleImage}" /></c:if></div>
                            <div class="details">
                                <a href="${ctx}/news/industry/${item.isort}.html" ><h3>${item.stitle}</h3></a>
                                <p>${item.snewsAbstract}</p>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
            </div>
        </div>
    </div>
    <!--尾-->
    <jsp:include page="/common/include/indexFrontBottom.jsp" />
</div>

<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript">
    function selectMenu(type) {
        $(".listall").hide();
        $(".navpath a").removeClass("active");
        $("#div_"+type).show();
        $("#a_"+type).addClass("active");
    }
    <%@ include file="/common/include/baidu.jsp"%>
</script>
</body>
</html>
