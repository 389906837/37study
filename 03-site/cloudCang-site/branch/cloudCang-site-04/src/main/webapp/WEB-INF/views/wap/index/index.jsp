<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no"/>
    <link rel="shortcut icon" href="${staticSource}/static/favicon.ico" type="image/x-icon">
    <title>37号仓-计算机视觉识别核心算法供应商，可广泛应用于新零售、环保、安防等领域！- 江苏叁拾柒号仓智能科技有限公司</title>
    <meta name="keywords" content="37号仓，视觉识别，机器视觉，人工智能，智能科技，新零售，无人零售，无人售货机，视觉识别售货机，江苏叁拾柒号仓智能科技有限公司"/>
    <meta name="description" content="37号仓是一家人工智能领域机器视觉方面的研发型公司，企业拥有一支高水平的研发和管理团队，致力于人工智能视觉识别系统的软硬件研发、生产和推广。"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="${staticSource}/static/wap/css/common_new.css">
    <link rel="stylesheet" href="${staticSource}/static/wap/css/index_new.css">
    <script type="text/javascript" src="${staticSource}/static/wap/js/jquery.min.js"></script>
    <script type="text/javascript" src="${staticSource}/static/wap/js/swiper.min.js"></script>
</head>
<body>
<%@ include file="/common/include/head.jsp" %>

<!-- banner -->
<div class="index-banner swiper-container">
    <div class="swiper-wrapper">
        <c:if test="${not empty albumImages }">
            <c:forEach items="${albumImages}" var="art">
                <div class="swiper-slide">
                    <a href="#"><img src="${art.contenturl}"></a>
                </div>
            </c:forEach>
        </c:if>
    </div>
    <div class="swiper-pagination swiper-pagination-bullets"></div>
</div>
<!-- 产品中心 -->
<div class="index-product-main md">
    <div class="index-product-title">
        <h4>产品中心</h4>
        <p>Product Center</p>
    </div>
    <div class="index-product-list">
        <div class="product-list-item">
            <a href="${ctx}/m/productCenter_vending.html">
                <div class="list-item-img"><img src="${staticSource}/static/wap/image/index-product-icon01.png"></div>
                <p>智能自取售货柜</p>
            </a>
        </div>
        <div class="product-list-item">
            <a href="${ctx}/m/productCenter_species.html">
                <div class="list-item-img"><img src="${staticSource}/static/wap/image/index-product-icon02.png"></div>
                <p>珍稀物种智能分析平台</p>
            </a>
        </div>
        <div class="product-list-item">
            <a href="${ctx}/m/productCenter_display.html">
                <div class="list-item-img"><img src="${staticSource}/static/wap/image/index-product-icon03.png"></div>
                <p>商品智能展示系统</p>
            </a>
        </div>
        <div class="product-list-item">
            <a href="${ctx}/m/productCenter_classification.html">
                <div class="list-item-img"><img src="${staticSource}/static/wap/image/index-product-icon05.png"></div>
                <p>垃圾分类智能识别系统</p>
            </a>
        </div>
        <div class="product-list-item">
            <a href="${ctx}/m/productCenter_commodity.html">
                <div class="list-item-img"><img src="${staticSource}/static/wap/image/index-product-icon04.png"></div>
                <p>售货机出口商品检测</p>
            </a>
        </div>
    </div>
</div>
<!-- 应用场景 -->
<div class="index-application-main">
    <div class="index-application-title md">
        <h4>应用场景</h4>
        <p>Application Scenarios</p>
    </div>
    <div class="index-application-list">
        <div class="application-list-item">
            <div class="application-list-lf"><img src="${staticSource}/static/wap/image/index-application-img01.jpg">
            </div>
            <div class="application-list-rg">
                <h4>智能自取售货柜</h4>
                <div class="application-list-text">
                    <p>智能视觉识别技术</p>
                    <p>复合重力传感</p>
                    <p>识别准确率高</p>
                    <div class="application-list-img"><img
                            src="${staticSource}/static/wap/image/applcation-img01-1.jpg"></div>
                </div>
            </div>
        </div>
        <div class="application-list-item">
            <div class="application-list-lf"><img src="${staticSource}/static/wap/image/index-application-img02.jpg">
            </div>
            <div class="application-list-rg">
                <h4>珍稀物种智能分析平台</h4>
                <div class="application-list-text">
                    <p>实时识别与统计</p>
                    <p>亿级像素摄像头</p>
                    <p>物种统计大数据</p>
                    <div class="application-list-img"><img
                            src="${staticSource}/static/wap/image/applcation-img02-2.jpg"></div>
                </div>
            </div>
        </div>
        <div class="application-list-item">
            <div class="application-list-lf"><img src="${staticSource}/static/wap/image/index-application-img03.jpg">
            </div>
            <div class="application-list-rg">
                <h4>商品智能展示系统</h4>
                <div class="application-list-text">
                    <p>识别速度快</p>
                    <p>识别率高</p>
                    <p>人机互动体验佳</p>
                    <div class="application-list-img"><img
                            src="${staticSource}/static/wap/image/applcation-img03-3.jpg"></div>
                </div>
            </div>
        </div>
        <div class="application-list-item">
            <div class="application-list-lf"><img src="${staticSource}/static/wap/image/index-application-img04.jpg">
            </div>
            <div class="application-list-rg">
                <h4>垃圾分类智能识别系统</h4>
                <div class="application-list-text">
                    <p>本地识别</p>
                    <p>无惧变形</p>
                    <p>国内领先</p>
                    <div class="application-list-img"><img
                            src="${staticSource}/static/wap/image/applcation-img04-4.jpg"></div>
                </div>
            </div>
        </div>
        <div class="application-list-item">
            <div class="application-list-lf"><img src="${staticSource}/static/wap/image/index-application-img05.jpg">
            </div>
            <div class="application-list-rg">
                <h4>售货机出口商品检测</h4>
                <div class="application-list-text">
                    <p>识别速度快</p>
                    <p>识别准确率高</p>
                    <p>无惧商品摆放角度</p>
                    <div class="application-list-img"><img
                            src="${staticSource}/static/wap/image/applcation-img05-5.jpg"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="technology-wrap">
    <div class="index-partners-title md">
        <h4>合作伙伴</h4>
        <p>Cooperative Partners</p>
    </div>
    <div class="partners-main">
        <ul class="partners-ul">
            <c:if test="${not empty cooPartnerImages }">
                <c:forEach items="${cooPartnerImages}" var="art">
                    <li><img src="${art.contenturl}"></li>
                </c:forEach>
            </c:if>
            <%--<li><img src="${staticSource}/static/wap/image/partners-img12.jpg"></li>--%>
        </ul>
    </div>
</div>
<script type="text/javascript">
    /* banner*/
    var mySwiperBanner = new Swiper('.swiper-container',{
        direction: 'horizontal',//水平滚动
        loop: true,//循环
        autoplay: 3000,//自动播放
        // 如果需要分页器
        pagination: '.swiper-pagination'
    });
</script>
<%@ include file="/common/include/tail.jsp" %>
</body>
</html>