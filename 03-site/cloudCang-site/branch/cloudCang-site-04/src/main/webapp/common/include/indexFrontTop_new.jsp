<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/jquery.three-slide.js"></script>

<div class="head head-bg">
    <div class="container">
        <div class="nav">
            <div class="logo">
                <img src="${staticSource}/static/images/index-logo.png" class="logo1">
                <img src="${staticSource}/static/images/index-logo-hover.png" class="logo2">
            </div>
            <div class="menu menu-bg">
                <ul>
                    <li class="active"><a href="/" id="home">首页</a></li>
                    <c:if test="${not empty arts }">
                        <c:forEach items="${arts}" var="art">
                            <c:choose>
                                <c:when test="${art.ssubjoinTitle eq 'product'}">
                                    <li>
                                        <a href="#">产品中心</a>
                                        <div class="menu-down">
                                            <c:forEach items="${ttt_artsss}" var="artT">
                                                <a href="${ctx}/${artT.ssubjoinTitle}_${artT.sresourceName}.html">${artT.stitle}</a>
                                            </c:forEach>
                                            <%--<a href="${ctx}/${art.ssubjoinTitle}_vending.html">智能自取售货柜</a>
                                            <a href="${ctx}/${art.ssubjoinTitle}_species.html">珍稀物种智能分析平台</a>
                                            <a href="${ctx}/${art.ssubjoinTitle}_display.html">商品智能展示系统</a>
                                            <a href="${ctx}/${art.ssubjoinTitle}_classification.html">垃圾分类智能识别系统</a>
                                            <a href="${ctx}/${art.ssubjoinTitle}_commodity.html">售货机出口商品检测</a>
                                            <a href="${ctx}/${art.ssubjoinTitle}_perception.html">无感知人脸测温系统</a>
                                            <a href="${ctx}/${art.ssubjoinTitle}_faceTemperature.html">智能人脸识别体温检测终端</a>--%>
                                        </div>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li>
                                        <a href="${ctx}/${art.ssubjoinTitle}.html">${art.stitle}</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </c:if>
                    <li><a href="${ctx}/aboutus.html">关于我们</a></li>
                    <li><a href="${ctx}/report.html">报道动态</a></li>
                    <li><a href="/#contactus" id="lxfs">联系方式</a></li>
                </ul>
            </div>
        </div>
    </div>
</div>
<script>
    /** 导航栏菜单下拉*/
    $(".menu li").hover(function () {
        $(this).children(".menu-down").show();
    }, function () {
        $(this).children(".menu-down").hide();
    })
    // 导航栏滚动
    $(window).scroll(function () {
        var scrollTop = $(window).scrollTop();
        if ($(document).scrollTop() > 200) {
            var url = "/static/images/index-logo-hover.png";
            $('.head').addClass('head-hover');
            $(".logo1").attr("src", url);
        } else {
            var url = "/static/images/index-logo.png";
            $('.head').removeClass('head-hover');
            $(".logo1").attr("src", url);
        }
    });

</script>
</body>
