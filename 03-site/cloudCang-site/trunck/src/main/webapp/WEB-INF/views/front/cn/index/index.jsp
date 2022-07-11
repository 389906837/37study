<%--
  Created by IntelliJ IDEA.
  User: 37cang-1
  Date: 2018/12/26
  Time: 15:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/include/taglibs.jsp"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="37号仓，视觉识别，机器视觉，新零售，无人零售，无人售货机，视觉识别售货机，江苏叁拾柒号仓智能科技有限公司" />
    <meta name="description" content="37号仓是一家人工智能领域机器视觉方面的研发型公司，企业拥有一支高水平的研发和管理团队，致力于人工智能视觉识别系统的软硬件研发、生产和推广 - 江苏叁拾柒号仓智能科技有限公司。" />
    <title>37号仓-计算机视觉识别核心算法供应商，可广泛应用于新零售、环保、安防等领域！- 江苏叁拾柒号仓智能科技有限公司</title>
        <link rel="stylesheet" href="${staticSource}/static/css/index.css">
        <link rel="shortcut icon" href="${staticSource}/static/favicon.ico" type="image/x-icon">
</head>
<body>

<div class="main">

    <!--头-->
    <jsp:include page="/common/include/indexFrontTop.jsp" />
    <!--解决方案-->
    <div class="solution">
        <div class="container">
            <div class="solution-title"><img class="lazy" data-original="${staticSource}/static/images/solution_title.png"></div>
            <div class="solution-menu">
                <div class="rgzn-wrapper">
                    <div class="rgzn">
                        <p class="details">模拟人的视觉神经网络，可以不断提高商品识别率。同时不增加前端硬件压力。</p>
                        <img class="lazy" data-original="${staticSource}/static/images/solution_rgzn.png">
                        <p class="title">人工智能</p>
                    </div>
                </div>
                <div class="rgzn-wrapper">
                    <div class="rgzn">
                        <p class="details">机器视觉就是用机器代替人眼来做测量和判断，通过机器视觉产品将被摄取目标转换成图像信号，传送给专用的图像处理系统，得到被摄目标的.</p>
                        <img class="lazy" data-original="${staticSource}/static/images/solution_jqsj.png">
                        <p class="title">机器视觉</p>
                    </div>
                </div>
                <div class="rgzn-wrapper">
                    <div class="rgzn">
                        <p class="details">采用分布式系统构架，可为用户提供安全、高效、稳定的系统服务</p>
                        <img class="lazy" data-original="${staticSource}/static/images/solution_zhypt.png">
                        <p class="title">智慧云平台</p>
                    </div>
                </div>
            </div>

        </div>
    </div>
    <!--核心技术-->
    <div class="coretechnology">
        <div class="coretechnology-title">
            <img class="lazy" data-original="${staticSource}/static/images/CoreTechnology.png">
        </div>
        <div class="coretechnology-menu">
            <div class="menu-l">
                <div class="menu-l-title">
                    <div>
                        <h1>产品训练标准化</h1>
                        <p>360度自动拍照记录</p>
                        <p>可自动进行特征标注</p>
                        <p>强大的视觉训练服务器</p>
                    </div>

                </div>
                <div class="menu-pic menu-l-pic">
                    <img class="lazy" data-original="${staticSource}/static/images/core_1.png">
                </div>
            </div>
            <div class="menu-r">
                <div class="menu-r-title">
                    <div>
                        <h1>深度学习</h1>
                        <p>采用多重卷积神经网络，仿真人脑学习模式，识别时间短效率高，</p>
                        <p>准确度更高</p>
                    </div>
                </div>
                <div class="menu-pic menu-r-pic">
                    <img class="lazy" data-original="${staticSource}/static/images/core_2.png">
                </div>
            </div>
        </div>
    </div>
    <!--智慧零售云管理-->
    <div class="controlcenter">
        <div class="controlcenter-title">
            <h1>智慧零售云管理中心</h1>
            <p>完善智能的数据浏览和分析功能；丰富的营销配置方案和报表管理系统；分布式部署系统架构，支持高负载、高并发、高设备接入量、更高容错、更强的可扩展性；灵活、便捷，使用感更流畅、稳定、安全。</p>
        </div>
        <div class="controlcenter-pic">
            <img class="lazy" data-original="${staticSource}/static/images/controlcenter.png">
        </div>

    </div>
    <!--关于我们-->
    <div class="aboutus">
        <a name="aboutus"><div class="aboutus-title"><img  data-original="${staticSource}/static/images/aboutus.png" src="${staticSource}/static/images/aboutus.png"></div></a>
        <div class="aboutus-pic">
            <div class="aboutus_l"></div>
            <div class="container">
                <ul>
                    <c:if test="${not empty artsss }">
                       <%-- <c:forEach items="${artsss}" var="ar">
                                <li> <a target="_blank" href="${ctx}/news/company/${fn:replace(ar.id,
                                '0000000000000000000000', '')}.html"  ><img class="lazy" data-original="${ar.stitleImageIndex}" src="${ar.stitleImageIndex}"></a></li>
                        </c:forEach>--%>
                        <li> <a target="_blank" href="${ctx}/news/company/${fn:replace(artsss[0].id,'0000000000000000000000', '')}.html"  ><img class="lazy" data-original="${artsss[0].stitleImageIndex}" src="${artsss[0].stitleImageIndex}"></a></li>
                        <li> <a target="_blank" href="${ctx}/news/company/${fn:replace(artsss[1].id,'0000000000000000000000', '')}.html"  ><img class="lazy" data-original="${artsss[1].stitleImageIndex}" src="${artsss[1].stitleImageIndex}"></a></li>
                        <li> <a target="_blank" href="${ctx}/news/company/${fn:replace(artsss[2].id,'0000000000000000000000', '')}.html"  ><img class="lazy" data-original="${artsss[2].stitleImageIndex}" src="${artsss[2].stitleImageIndex}"></a></li>
                        <li> <a target="_blank" href="${ctx}/news/company/${fn:replace(artsss[3].id,'0000000000000000000000', '')}.html"  ><img class="lazy" data-original="${artsss[3].stitleImageIndex}" src="${artsss[3].stitleImageIndex}"></a></li>
                        <li> <a target="_blank" href="${ctx}/news/company/${fn:replace(artsss[4].id,'0000000000000000000000', '')}.html"  ><img class="lazy" data-original="${artsss[4].stitleImageIndex}" src="${artsss[4].stitleImageIndex}"></a></li>
                      

                    </c:if>
                  <%--  <li><img class="lazy" data-original="${staticSource}/static/images/aboutuspic_2.png"></li>
                    <li><img class="lazy" data-original="${staticSource}/static/images/aboutuspic_3.png"></li>--%>
                </ul>
            </div>
            <div class="aboutus_r"></div>
        </div>
        <div class="aboutus-desc" >


            <c:if test="${not empty artsss }">
                <c:forEach items="${artsss}" var="al">

                    <div>
                        <a target="_blank" href="${ctx}/news/company/${fn:replace(al.id,
                                '0000000000000000000000', '')}.html" ><p>${al.ssubjoinTitle}</p></a>
                    </div>

                </c:forEach>

            </c:if>
        </div>
        <div>
            <a name="aboutus"  href="${ctx}/news/company" class="more">了解更多</a>
        </div>

    </div>
    <!--媒体报道-->
    <div class="mediareports">
        <a name="mediareports"><div class="mediareports-title"><img class="lazy" data-original="${staticSource}/static/images/mediareports.png"></div></a>
        <div class="mediareports-pic">

            <div class="mediareports-l">
                <img class="lazy" data-original="${artss[0].stitleImage}" onclick="javascript:window.open('${ctx}/news/media/${fn:replace(artss[0].id,
                    '0000000000000000000000', '')}.html');" style="cursor: pointer;" />
                <p><a target="_blank" href="${ctx}/news/media/${fn:replace(artss[0].id,
                    '0000000000000000000000', '')}.html">${artss[0].stitle}</a></p>
            </div>
            <div class="mediareports-r">
                 <img class="lazy" data-original="${artss[1].stitleImage}" onclick="javascript:window.open('${ctx}/news/media/${fn:replace(artss[1].id,
                         '0000000000000000000000', '')}.html');" style="cursor: pointer;" />
                <p><a target="_blank" href="${ctx}/news/media/${fn:replace(artss[1].id,
                    '0000000000000000000000', '')}.html">${artss[1].stitle}</a></p>
            </div>

        </div>
        <a class="more" href="${ctx}/news/media">了解更多</a>
    </div>


    <!--合作伙伴-->
    <div class="partner">
        <div class="partner-title"><img src="${staticSource}/static/images/cooperativepartner.png"></div>
        <div class="partner-logo">
            <ul>
                <li>
                    <img src="${staticSource}/static/images/partner_sn.png">
                </li>
                <li>
                    <img src="${staticSource}/static/images/partner_ml.png">
                </li>
                <li>
                    <img src="${staticSource}/static/images/partner_wg.png">
                </li>
                <li>
                    <img src="${staticSource}/static/images/partner_bs.png">
                </li>
                <li>
                    <img src="${staticSource}/static/images/partner_zqbhq.png">
                </li>
                <li>
                    <img src="${staticSource}/static/images/partner_pa.png">
                </li>
                <li>
                    <img src="${staticSource}/static/images/partner_bitmain.png">
                </li>
                <li>
                    <img src="${staticSource}/static/images/partner_aoi.png">
                </li>
            </ul>
        </div>

    </div>

    <!--尾-->
    <a name="contactus"></a>
    <jsp:include page="/common/include/indexFrontBottom.jsp" />
</div>
<!--立即申请-->
<div class="mask"></div>
<form action="/applicants/save1/首页申请入驻" id="ffrom" method="post">
<div class="applyfrom">
    <a class="circle"><img src="${staticSource}/static/images/circle.png"></a>
    <p>申请37号仓</p>
    <div class="control">
        <select class="form-control">
            <option>中国</option>
        </select>
    </div>
    <div class="control"><input class="form-control" placeholder="公司名称" name="userName"></div>
    <div class="control"><input class="form-control" placeholder="联系人名称" name="linkMan"></div>
    <div class="control"><input class="form-control" placeholder="手机号码" name="tel"><%--<span>您的格式不正确</span>--%></div>
    <button type="button"class="btn" id="ljsq">提交</button>

</div>
</form>
<!--提交成功-->
<div class="success">
    <a class="circle"><img src="${staticSource}/static/images/circle.png"></a>
    <p style="padding-top: 90px"><img src="${staticSource}/static/images/success.png"></p>
    <p style="font-size: 36px;padding-top: 33px;">提交成功！</p>
    <p style="color: #8b8b8b;padding: 26px 0px 87px 0px">我们会有专人与您联系，请您耐心等待。</p>
    <a class="btn">确 定</a>
</div>
<!-- 侧边栏 -->
<div class="sidebar">
    <ul>
        <li>
            <a><img src="${staticSource}/static/images/sidebar_phone.png"  msrc="${staticSource}/static/images/sidebar_phonehove.png"></a>
            <div class="sidebar_qq">
                <div class="sidebar_qqs">

                    <div class="sidebar_qqsimg"> <img src="${staticSource}/static/images/sidebar_phones.png"></div>
                    <div class="sidebar_qqsnum">
                        <p>咨询电话：</p>
                        <p>400 828 3737</p>
                    </div>
                </div>
            </div>
        </li>
        <li>
            <a><img src="${staticSource}/static/images/sidebar_qq.png" msrc="${staticSource}/static/images/sidebar_qqhove.png"></a>
            <div class="sidebar_qq">
                <div class="sidebar_qqs">

                    <div class="sidebar_qqsimg"> <img src="${staticSource}/static/images/sidebar_qqs.png"></div>
                    <div class="sidebar_qqsnum">
                        <p>客服QQ：</p>
                        <a href="http://wpa.qq.com/msgrd?v=3&uin=2667259864&site=qq&menu=yes" title="点击联系客服" target="_blank">
                            <p>2667259864</p></a>
                    </div>
                </div>
                <div class="sidebar_qqt">
                    <div class="sidebar_qqtimg">
                        <img src="${staticSource}/static/images/sidebar_qqt.png">
                    </div>
                    <div class="sidebar_qqtnum">
                        <p>在线时间：</p>
                        <p>9:00-17:30</p>
                    </div>

                </div>


            </div>
        </li>
        <li>
            <a class="scrolltop"><img src="${staticSource}/static/images/sidebar_up.png" msrc="${staticSource}/static/images/sidebar_uphove.png"></a>
        </li>
    </ul>
</div>
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/jquery.lazyload.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/jquery.three-slide.js"></script>
<script type="text/javascript">

    /*滚动定位导航菜单*/
    function doScroll() {
        $(window).on('scroll', function(){
            if($(window).scrollTop() > 4700 ){
                $('.menu').find('li').removeClass('active');
                $('#lxfs').parent().addClass('active');
            }else if($(window).scrollTop() > 3700 ){
                $('.menu').find('li').removeClass('active');
                $('#mtbd').parent().addClass('active');
            }else if($(window).scrollTop() > 2700 ){
                $('.menu').find('li').removeClass('active');
                $('#gywm').parent().addClass('active');
            }else if($(window).scrollTop() > 0 ){
                $('.menu').find('li').removeClass('active');
                $('#home').parent().addClass('active');
            }
        });
    };

    $(window).on('scroll', doScroll);

    doScroll();



    $(function () {
        $('img.lazy').lazyload();


        /*导航菜单定位*/
        $('#gywm').on('click',function () {
            $('html,body').animate({'scrollTop':$(".aboutus").offset().top});
        });
        $('#mtbd').on('click',function () {
            $('html,body').animate({'scrollTop':$(".mediareports").offset().top});
        });
        $('#lxfs').on('click',function () {
            $('html,body').animate({'scrollTop':$(".contactus").offset().top});
        });

        /*导航菜单*/
        $(window).scroll(function(){
            var scrollTop = $(window).scrollTop();
            if($(document).scrollTop() > 200){
                $('.head').addClass('head-bg');
                $('.head').addClass('head-p');
                $('.menu').removeClass('menu-bg');
            }else {
                $('.head').removeClass('head-bg');
                $('.head').removeClass('head-p');
                $('.menu').addClass('menu-bg');
            }
        });


        /*点击申请入驻*/
        $('.apply').on('click',function () {
            $('.mask').css('display','block');
            $('.applyfrom').css('display','block');
        });
        $('.mask,.circle').on('click',function () {
            $('.mask').css('display','none');
            $('.applyfrom').css('display','none');
            $('.success').css('display','none');
        });
        $('.btn').on('click',function () {
            $(this).parent().css('display','none');
            $('.success').css('display','block');

            /**点击后写入数据库*/

        });

        var animated = false, mtop = 550;
        $(document).on('scroll', function(event) {
            if (animated) return;
            var stop = $(document).scrollTop();
            var sbottom = stop + $(window).height();
            var ptop = $('.coretechnology-menu').offset().top;
            var pbottom = ptop + $('.coretechnology').height();
            var height = $('.coretechnology-menu').height() + $(window).height();

            if (sbottom >= ptop && pbottom >= stop) {
                $('.coretechnology .menu-pic>img').stop().css({ marginTop: mtop, opacity: 0 }).animate({ marginTop: 0, opacity: 1 }, 1200);
                animated = true;
            }
        });

        $('.aboutus-pic .container').threeSlide('ul', 'li', 500, 5000, '.aboutus_l', '.aboutus_r').on('slide', function(event, index) {
            $('.aboutus-desc > div').removeClass('active').eq(index).addClass('active');
        });
    })

    /*验证*/

    $('.btn').on('click',function () {
        var validate = true;
        if(  $('#userName').val()=="" || $('#userName').val() == null){
            $('#name').nextAll('.help-block').text('请输入申请公司名称');
            validate = false;
        }
        if(  $('#linkMan').val()=="" || $('#linkMan').val() == null){
            $('#name').nextAll('.help-block').text('请输入联系人');
            validate = false;
        }
        if(  $('#tel').val()=="" || $('#tel').val() == null){
            $('#thecontact').nextAll('.help-block').text('请输入电话');
            validate = false;
        }

        if (validate) {
            $("#fform").submit();
            /*alert("恭喜您提交成功");*/
            /* $(this).parents('.applyaccount').css('display','none');
             $('.success').css('display','block');*/
            /*
             * 提交申请 tj();
             * */


        } else {

            return false;
        }
    });

    var src,msrc;
    $('.sidebar ul li').hover(function () {
        $('.sidebar ul li').removeClass('active');
        $(this).addClass('active');
        $(this).children('div').css('display','block');
        src=$(this).children('a').children().attr("src");
        msrc=$(this).children('a').children().attr("msrc");
        $(this).children('a').children().attr("src",msrc);
        $(this).children('a').children().attr("msrc",src);
    },function () {
        $('.sidebar ul li').removeClass('active');
        $(this).children('div').css('display','none');
        src=$(this).children('a').children().attr("src");
        msrc=$(this).children('a').children().attr("msrc");
        $(this).children('a').children().attr("src",msrc);
        $(this).children('a').children().attr("msrc",src);
    });
    $('.scrolltop').click(function() {
        $('body,html').animate({
            scrollTop: '0px'
        }, 900);
    });
    $(window).on('scroll', function(){
        if($(window).scrollTop() > 1000 ){
            $('.sidebar').css('display','block');
        }else if($(window).scrollTop() < 1000 ){
            $('.sidebar').css('display','none');
        }
    });
    <%@ include file="/common/include/baidu.jsp"%>
</script>

</body>
</html>
