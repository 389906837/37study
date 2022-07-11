<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no"/>
    <title>报道/动态</title>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="${staticSource}/static/wap/css/common_new.css">
    <link rel="stylesheet" href="${staticSource}/static/wap/css/index_new.css">
    <script type="text/javascript" src="${staticSource}/static/wap/js/jquery.min.js"></script>

</head>
<body>
<%@ include file="/common/include/head.jsp" %>

<div class="reports-title">
    <ul>
        <li class="hover"><a>公司动态</a></li>
        <li><a>媒体报道 </a></li>
        <li><a>行业新闻</a></li>
    </ul>
</div>
<div class="reports-main">
    <div class="reports-item" id="div_companyNews" <c:if test="${type ne 'companyNews'}">style="display:none"</c:if>>
        <c:if test="${fn:length(companyNews) > 0}">
            <ul>
                <c:forEach var="item" items="${companyNews}" varStatus="L">
                    <li>
                        <div class="reports-item-img"><c:if test="${empty item.stitleImage}"><img
                                src="${staticSource}/static/images/mediareports/defaultThumbnail.jpg"/></c:if><c:if
                                test="${!empty item.stitleImage}"><img src="${item.stitleImage}"/></c:if></div>
                        <div class="reports-item-txt">
                            <h4 onclick="toDetail('${ctx}/m/company/${item.isort}.html')">${item.stitle}</h4>
                            <p><f:formatDate value="${item.taddTime}" pattern="yyyy-MM-dd"/><a class="reports-item-more"
                                                                                               href="${ctx}/m/company/${item.isort}.html">[详细]</a>
                            </p>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </c:if>
        <c:if test="${not empty articles}">
            <div class="recommended-main">
                <div class="recommended-title">相关推荐</div>
                <ul>
                    <c:forEach items="${articles}" var="item">
                        <li>
                            <div class="reports-item-img"><img src="${item.stitleImage}"></div>
                            <div class="reports-item-txt">
                                <h4 onclick="toDetail('${item.advertisUrl}')">${item.sketch}</h4>
                                <p><f:formatDate value="${item.taddTime}" pattern="yyyy-MM-dd"/><a
                                        class="reports-item-more" href="${item.advertisUrl}">[详细]</a></p>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>
    </div>
    <div class="reports-item" id="div_mediaReport" <c:if test="${type ne 'mediaReport'}">style="display:none"</c:if>>
        <c:if test="${fn:length(mediaReport) > 0}">
            <ul>
                <c:forEach var="item" items="${mediaReport}" varStatus="L">
                    <li>
                        <div class="reports-item-img"><c:if test="${empty item.stitleImage}"><img
                                src="${staticSource}/static/images/mediareports/defaultThumbnail.jpg"/></c:if><c:if
                                test="${!empty item.stitleImage}"><img src="${item.stitleImage}"/></c:if></div>
                        <div class="reports-item-txt">
                            <h4 onclick="toDetail('${ctx}/m/media/${item.isort}.html')">${item.stitle}</h4>
                            <p><f:formatDate value="${item.taddTime}" pattern="yyyy-MM-dd"/><a class="reports-item-more"
                                                                                               href="${ctx}/m/media/${item.isort}.html">[详细]</a>
                            </p>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </c:if>
        <c:if test="${not empty articles}">
            <div class="recommended-main">
                <div class="recommended-title">相关推荐</div>
                <ul>
                    <c:forEach items="${articles}" var="item">
                        <li>
                            <div class="reports-item-img"><img src="${item.stitleImage}"></div>
                            <div class="reports-item-txt">
                                <h4 onclick="toDetail('${item.advertisUrl}')">${item.sketch}</h4>
                                <p><f:formatDate value="${item.taddTime}" pattern="yyyy-MM-dd"/><a
                                        class="reports-item-more" href="${item.advertisUrl}">[详细]</a></p>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>
    </div>
    <div class="reports-item" id="div_industryNews" <c:if test="${type ne 'industryNews'}">style="display:none"</c:if>>
        <c:if test="${fn:length(industryNews) <= 0}">
            <div class="reports-item-txt">
                <div style="text-align: center;width: 100%;">暂无数据</div>
            </div>
        </c:if>
        <c:if test="${fn:length(industryNews) > 0}">
            <ul>
                <c:forEach var="item" items="${industryNews}" varStatus="L">
                    <li>
                        <div class="reports-item-img"><c:if test="${empty item.stitleImage}"><img
                                src="${staticSource}/static/images/mediareports/defaultThumbnail.jpg"/></c:if><c:if
                                test="${!empty item.stitleImage}"><img src="${item.stitleImage}"/></c:if></div>
                        <div class="reports-item-txt">
                            <h4 onclick="toDetail('${ctx}/m/industry/${item.isort}.html')">${item.stitle}</h4>
                            <p><f:formatDate value="${item.taddTime}" pattern="yyyy-MM-dd"/><a class="reports-item-more"
                                                                                               href="${ctx}/m/industry/${item.isort}.html">[详细]</a>
                            </p>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </c:if>
        <c:if test="${not empty articles}">
            <div class="recommended-main">
                <div class="recommended-title">相关推荐</div>
                <ul>
                    <c:forEach items="${articles}" var="item">
                        <li>
                            <div class="reports-item-img"><img src="${item.stitleImage}"></div>
                            <div class="reports-item-txt">
                                <h4 onclick="toDetail('${item.advertisUrl}')">${item.sketch}</h4>
                                <p><f:formatDate value="${item.taddTime}" pattern="yyyy-MM-dd"/><a
                                        class="reports-item-more" href="${item.advertisUrl}">[详细]</a></p>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>
    </div>
</div>
<script>
    function toDetail(url) {
        window.location.href=url;
    }
    /* 报道 */
    $(".reports-title ul li").click(function () {
        var index = $(".reports-title ul li").index(this); // 找到当前的序号
        $(this).addClass("hover").siblings().removeClass("hover");
        $(".reports-item").eq(index).show().siblings().hide();
    });

</script>
<%@ include file="/common/include/tail.jsp" %>

</body>
</html>