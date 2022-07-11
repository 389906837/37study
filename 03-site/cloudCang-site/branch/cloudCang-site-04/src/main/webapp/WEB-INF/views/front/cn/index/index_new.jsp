<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/include/taglibs.jsp" %>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="format-detection" content="telephone=no,email=no,address=no">
    <title>37号仓-计算机视觉识别核心算法供应商，可广泛应用于新零售、环保、安防等领域！- 江苏叁拾柒号仓智能科技有限公司</title>
    <meta name="keywords" content="37号仓，视觉识别，机器视觉，人工智能，智能科技，新零售，无人零售，无人售货机，视觉识别售货机，江苏叁拾柒号仓智能科技有限公司"/>
    <meta name="description" content="37号仓是一家人工智能领域机器视觉方面的研发型公司，企业拥有一支高水平的研发和管理团队，致力于人工智能视觉识别系统的软硬件研发、生产和推广。"/>
    <link rel="stylesheet" href="${staticSource}/static/css/index_new.css">
    <link rel="stylesheet" href="${staticSource}/static/css/swiper.min.css">
    <link rel="shortcut icon" href="${staticSource}/static/favicon.ico" type="image/x-icon" />
    <style>
        .swiper-pagination-bullet {
            width: 14px;
            height: 14px;
            display: inline-block;
            border-radius: 50%;
            border: 1px solid #0c4284;
            background: #fff;
            opacity: 1;
        }

        .swiper-pagination-bullet-active {
            background: #0c4284;
        }
        .swiper-wrapper{height:565px!important;}
    </style>
    <%@ include file="/common/include/baidu_sq.jsp"%>
</head>
<body>
<div class="main">
    <!--头-->
    <%--    <jsp:include page="/common/include/indexFrontTop_new.jsp" />--%>
    <div class="index-head head">
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
                        <li><a href="#" id="lxfs">联系方式</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <div class="index-banner">
        <div class="index-ban-img">
            <ul>
                <c:if test="${not empty albumImages }">
                    <c:forEach items="${albumImages}" var="art">
                        <li><a href="#"><img src="${art.contenturl}" alt="${art.desc}" title="${art.desc}"></a></li>
                    </c:forEach>
                </c:if>
            </ul>
        </div>
        <div class="index-ban-dot">
            <ul>
                <c:if test="${not empty albumImages }">
                    <c:forEach items="${albumImages}" var="art" varStatus="status">
                        <c:choose>
                            <c:when test="${status.index eq 0}">
                                <li class="on"><a></a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a></a></li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </c:if>
            </ul>
        </div>
        <a class="index-prev" href="javascript:void(0)"></a>
        <a class="index-next" href="javascript:void(0)"></a>
    </div>


    <div class="index-product">
        <div class="index-product-title">
            <h4>产品中心</h4>
            <p>Product Center</p>
        </div>
        <div class="index-product-list container">
            <div class="product-list-item">
                <a href="${ctx}/product_vending.html">
                    <div class="product-list-item-img" title="智能自取售货柜">
                        <img src="${staticSource}/static/images/index-product-icon01.png" alt="智能自取售货柜" title="智能自取售货柜">
                    </div>
                    <p>智能自取售货柜</p>
                </a>
            </div>
            <div class="product-list-item">
                <a href="${ctx}/product_species.html">
                    <div class="product-list-item-img" title="珍稀物种智能分析平台"><img
                            src="${staticSource}/static/images/index-product-icon02.png" alt="珍稀物种智能分析平台" title="珍稀物种智能分析平台">
                    </div>
                    <p>珍稀物种智能分析平台</p>
                </a>
            </div>
            <div class="product-list-item">
                <a href="${ctx}/product_display.html">
                    <div class="product-list-item-img" title="商品智能展示系统"><img
                            src="${staticSource}/static/images/index-product-icon03.png" alt="商品智能展示系统" title="商品智能展示系统">
                    </div>
                    <p>商品智能展示系统</p>
                </a>
            </div>
            <div class="product-list-item">
                <a href="${ctx}/product_classification.html">
                    <div class="product-list-item-img" title="商品智能展示系统"><img
                            src="${staticSource}/static/images/index-product-icon04.png" alt="垃圾分类智能识别系统" title="垃圾分类智能识别系统">
                    </div>
                    <p>垃圾分类智能识别系统</p>
                </a>
            </div>
            <div class="product-list-item">
                <a href="${ctx}/product_commodity.html">
                    <div class="product-list-item-img" title="售货机出口商品检测"><img
                            src="${staticSource}/static/images/index-product-icon05.png" alt="售货机出口商品检测" title="售货机出口商品检测">
                    </div>
                    <p>售货机出口商品检测</p>
                </a>
            </div>
        </div>
    </div>
    <!-- 应用场景 -->
    <div class="index-application">
        <div class="index-application-title">
            <h4>应用场景</h4>
            <p>Application Scenarios</p>
        </div>
        <div class="swiper-container container">
            <div class="swiper-wrapper">
                <div class="swiper-slide">
                    <img class="applcation-bigimg" src="${staticSource}/static/images/applcation-img01.jpg" alt="智能自取售货柜" title="智能自取售货柜">
                    <div class="application-main">
                        <h4>智能自取售货柜</h4>
                        <p>智能视觉识别技术</p>
                        <p>复合重力传感</p>
                        <p>识别准确率高</p>
                        <img class="applcation-smallimg" src="${staticSource}/static/images/applcation-img01-1.jpg">
                    </div>
                </div>
                <div class="swiper-slide">
                    <img class="applcation-bigimg" src="${staticSource}/static/images/applcation-img02.jpg" alt="珍稀物种智能分析平台" title="珍稀物种智能分析平台">
                    <div class="application-main">
                        <h4>珍稀物种智能分析平台</h4>
                        <p>实时识别与统计</p>
                        <p>亿级像素摄像头</p>
                        <p>物种统计大数据</p>
                        <img class="applcation-smallimg" src="${staticSource}/static/images/applcation-img02-2.jpg">
                    </div>
                </div>
                <div class="swiper-slide">
                    <img class="applcation-bigimg" src="${staticSource}/static/images/applcation-img03.jpg" alt="商品智能展示系统" title="商品智能展示系统">
                    <div class="application-main">
                        <h4>商品智能展示系统</h4>
                        <p>识别速度快</p>
                        <p>识别率高</p>
                        <p>人机互动体验佳</p>
                        <img class="applcation-smallimg" src="${staticSource}/static/images/applcation-img03-3.jpg">
                    </div>
                </div>
                <div class="swiper-slide">
                    <img class="applcation-bigimg" src="${staticSource}/static/images/applcation-img04.jpg" alt="垃圾分类智能识别系统" title="垃圾分类智能识别系统">
                    <div class="application-main">
                        <h4>垃圾分类智能识别系统</h4>
                        <p>本地识别</p>
                        <p>无惧变形</p>
                        <p>国内领先</p>
                        <img class="applcation-smallimg" src="${staticSource}/static/images/applcation-img04-4.jpg">
                    </div>
                </div>
                <div class="swiper-slide">
                    <img class="applcation-bigimg" src="${staticSource}/static/images/applcation-img05.jpg" alt="售货机出口商品检测" title="售货机出口商品检测">
                    <div class="application-main">
                        <h4>售货机出口商品检测</h4>
                        <p>识别速度快</p>
                        <p>识别准确率高</p>
                        <p>无惧商品摆放角度</p>
                        <img class="applcation-smallimg" src="${staticSource}/static/images/applcation-img05-5.jpg">
                    </div>
                </div>
            </div>
            <div class="swiper-pagination swiper-pagination-clickable swiper-pagination-bullets">
            </div>
        </div>
    </div>
    <!-- 合作伙伴 -->
    <div class="index-partner">
        <div class="index-partner-title">
            <h4>合作伙伴</h4>
            <p>Cooperative Partners</p>
        </div>
        <div class="index-partner-list container">
            <ul>
                <c:if test="${not empty cooPartnerImages }">
                    <c:forEach items="${cooPartnerImages}" var="art">
                        <li><img src="${art.contenturl}"></li>
                    </c:forEach>
                </c:if>
            </ul>
        </div>
    </div>

    <jsp:include page="/common/include/indexFrontBottom_new.jsp"/>
</div>
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/jquery.SuperSlide.2.1.3.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/swiper.min.js"></script>

<script>
    (function(w) {
        var pcm = readCookie('pcm');
        var ua = w.navigator.userAgent.toLocaleLowerCase();
        var url = '${ctx}/m/index';
        var matchedRE = /iphone|android|symbianos|windows\sphone/g;
        function readCookie(name) {
            var nameEQ = name + "=";
            var ca = document.cookie.split(';');
            for (var i = 0; i < ca.length; i++) {
                var c = ca[i];
                while (c.charAt(0) == ' ') {
                    c = c.substring(1, c.length)
                }
                if (c.indexOf(nameEQ) == 0) {
                    return c.substring(nameEQ.length, c.length)
                }
            }
            return null
        }
        if (matchedRE.test(ua) && pcm != '1') {
            w.location.href = url;
            return false;
        }
    })(window);
    // banner
    jQuery(".index-banner").slide({
        mainCell: ".index-ban-img ul",
        titCell: ".index-ban-dot li",
        prevCell: ".index-prev",
        nextCell: ".index-next",
        autoPlay: true,
        pnLoop: true,
        effect: "leftLoop",
        trigger: "mouseover",
        interTime: 2000
    });
    // 应用场景
    var swiper = new Swiper('.swiper-container', {
        slidesPerView: 4,
        autoplay: true,
        pagination: {
            el: '.swiper-pagination',
            clickable: true,
        },
    });
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
            var url = "${staticSource}/static/images/index-logo-hover.png";
            $('.index-head').addClass('head-hover');
            $(".logo1").attr("src", url);
        } else {
            var url = "${staticSource}/static/images/index-logo.png";
            $('.index-head').removeClass('head-hover');
            $(".logo1").attr("src", url);
        }
    });

    /* 产品中心动画 */
    var animated = false, mtop = 300;
    $(document).on('scroll', function (event) {
        if (animated) return;
        var stop = $(document).scrollTop();
        var sbottom = stop + $(window).height();
        var ptop = $('.index-product-list').offset().top;
        var pbottom = ptop + $('.index-product').height();

        if (sbottom >= ptop && pbottom >= stop) {
            $('.index-product-list').stop().css({marginTop: mtop, opacity: 0}).animate({
                marginTop: 0,
                opacity: 1
            }, 1200);
            animated = true;
        }
    });
   /* 联系方式 */
    $('#lxfs').on('click',function () {
        $('html,body').animate({'scrollTop':$(".index-foot-bg").offset().top});
    });
    <%@ include file="/common/include/baidu.jsp"%>

</script>

</body>
</html>