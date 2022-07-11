<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%><html>
<head>
    <title>Title</title>
</head>
<body>
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/jquery.lazyload.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/jquery.three-slide.js"></script>


<!--联系方式-->
<div class="contactus" id="contactus">
    <div class="contactus-title">
        <p class="phone">
            <img class="lazy" style="vertical-align:middle;" data-original="${staticSource}/static/images/phone.png" src="${staticSource}/static/images/phone.png">
            <span style="font-size: 24px;">400-828-3737 / 025-58061600</span>
        </p>
        <p class="time">周一至周五：9：00-17：30</p>
        <p class="logotitle"><img class="lazy" data-original="${staticSource}/static/images/37logoNew.png" src="${staticSource}/static/images/37logoNew.png" ><span>智能零售核心解决方案提供商！</span></p>
    </div>
    <div class="contactus-online clearfix">
        <div class="contactus-online-m1 fl">
            <h4>产品中心</h4>
            <ul>
                <li><a href="${ctx}/product.html#sjsbznh">Cpower智能盒</a></li>
                <li><a href="${ctx}/product.html#dmwrshj">Cbox无人售货柜</a></li>
            </ul>
        </div>
        <div class="contactus-online-m2 fl">
            <h4>支撑体系</h4>
            <ul>
                <li><a href="${ctx}/zctx.html#znyfwgl">智能云服务管理平台</a></li>
                <li><a href="${ctx}/zctx.html#znyxxgl">智能云学习管理平台</a></li>
                <li><a href="${ctx}/zctx.html#znysbgl">智能云识别管理平台</a></li>
            </ul>
        </div>
        <div class="contactus-online-m3 fl">
            <h4>核心算法</h4>
            <ul>
                <li><a href="${ctx}/core.html#wpsb">物品识别算法</a></li>
                <li><a href="${ctx}/core.html#gzsb">动态跟踪识别算法</a></li>
                <li><a href="${ctx}/core.html#dwsb">动物识别算法</a></li>
            </ul>
        </div>
        <div class="contactus-online-m4 fl">
            <h4>关于我们</h4>
            <ul>
                <li><a  href="${ctx}/aboutus.html#qyjj">企业简介</a></li>
                <li><a  href="${ctx}/aboutus.html#qyyj">企业愿景</a></li>
            </ul>
        </div>
        <div class="contactus-online-m5 fl">
            <h4>报道动态</h4>
            <ul>
                <li><a href="${ctx}/news/company">公司动态</a></li>
                <li><a href="${ctx}/news/media">媒体报道</a></li>
                <li><a href="${ctx}/news/industry">行业新闻</a></li>
            </ul>
        </div>

        <div class="contactus-online-m6 fl">
            <img src="${staticSource}/static/images/qrcode.png">
            <p class="m-t5"> 关注我们</p>
        </div>
        <div class="contactus-online-m7 fl">
            <img src="${staticSource}/static/images/wb.png" class="m7_wbhove">
            <div class="m7_wb">
                <div class="m7_wbsnum">
                    <a href="http://weibo.com/37cang" target="_blank"><p>weibo.com/37cang</p></a>
                </div>
            </div>
            <img src="${staticSource}/static/images/qq.png" class="m-l18 m7_qqhove">
            <div class="m7_qq">
                <div class="m7_qqsnum">
                    <a href="https://jq.qq.com/?_wv=1027&amp;k=52B1Cf8" title="点击链接加入群【37仓官方群】" target="_blank"><p>官方QQ群：665829442</p></a>
                    <a href="http://wpa.qq.com/msgrd?v=3&amp;uin=2667259864&amp;site=qq&amp;menu=yes" title="点击联系客服" target="_blank"><p>客服QQ：2667259864</p></a>
                    <p>在线时间：9:00-17:30</p>
                </div>
            </div>
            <p>地址：江苏省南京市栖霞区紫东创意园B7</p>
        </div>
    </div>

</div>
<!--尾-->
<div class="foot">
    <p>  ©2017-2019 www.37cang.com All Rights Reserved 江苏叁拾柒号仓智能科技有限公司 版权所有</p>
    <p> <a href="http://www.miitbeian.gov.cn/" target="_blank">苏ICP备17031594号</a></p>
</div>
<script>
    $(function () {
        /*联系我们wb,QQ*/
        $('.m7_wbhove,.m7_wb').hover(function () {
            $('.m7_wb').css('display','block');
        },function () {
            $('.m7_wb').css('display','none');
        });
        $('.m7_qqhove,.m7_qq').hover(function () {
            $('.m7_qq').css('display','block');
        },function () {
            $('.m7_qq').css('display','none');
        });

    });
</script>

</body>
</html>
