<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%><html>
<head>
    <title>Title</title>
</head>

<body>
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/jquery.three-slide.js"></script>

<div class="index-foot-bg" id="contactus">
    <div class="container">
        <div class="index-foot-top">
            <div class="foot-top-lf">
                <img src="${staticSource}/static/images/index-logo.png">
                <p>37号仓视觉识别软硬件一体化解决方案提供商</p>
            </div>
            <div class="foot-top-rg">
                <div class="foot-top-item">
                    <h4>产品中心</h4>
                    <ul>
                        <li> <a href="${ctx}/product_vending.html">智能自取售货柜</a></li>
                        <li> <a href="${ctx}/product_species.html">珍稀物种智能分析平台</a></li>
                        <li> <a href="${ctx}/product_display.html">商品智能展示系统</a></li>
                        <li> <a href="${ctx}/product_classification.html">垃圾分类智能识别系统</a></li>
                        <li> <a href="${ctx}/product_commodity.html">售货机出口商品检测</a></li>
                    </ul>
                </div>
                <div class="foot-top-item">
                    <h4>支撑体系</h4>
                    <ul>
                        <li><a href="${ctx}/zctx.html#znyfwgl">智能云服务管理平台</a></li>
                        <li><a href="${ctx}/zctx.html#znyxxgl">智能云学习管理平台</a></li>
                        <li><a href="${ctx}/zctx.html#znysbgl">智能云识别管理平台</a></li>
                    </ul>
                </div>
                <div class="foot-top-item">
                    <h4>核心算法</h4>
                    <ul>
                        <li><a href="${ctx}/core.html#wpsb">物品识别算法</a></li>
                        <li><a href="${ctx}/core.html#gzsb">动态跟踪识别算法</a></li>
                        <li><a href="${ctx}/core.html#dwsb">动物识别算法</a></li>
                    </ul>
                </div>
                <div class="foot-top-item">
                    <h4>关于我们</h4>
                    <ul>
                        <li><a href="${ctx}/aboutus.html#qyjj">企业简介</a></li>
                        <li><a href="${ctx}/aboutus.html#qyyj">企业愿景</a></li>
                    </ul>
                </div>
                <div class="foot-top-item">
                    <h4>报道动态</h4>
                    <ul>
                        <li><a href="${ctx}/news/company">公司动态</a></li>
                        <li><a href="${ctx}/news/media">媒体报道</a></li>
                        <li><a href="${ctx}/news/industry">行业新闻</a></li>
                    </ul>
                </div>
            </div>
        </div>

        <div class="index-foot-bot">
            <div class="foot-bot-lf">
                <p style="font-size:16px;">400-828-3737（周一至周五：9:00-17:30）</p>
                <p style="font-size:14px;">地址：江苏省南京市栖霞区紫东创意园B7</p>
            </div>
            <div class="foot-bot-rg">
                <img class="index-ma" src="${staticSource}/static/images/index-ma.jpg">
                <div class="foot-bot-contract">
                    <img class="m7_wbhove" src="${staticSource}/static/images/wb.png">
                    <div class="m7_wb">
                        <div class="m7_wbsnum">
                            <a href="http://weibo.com/37cang" target="_blank"><p>weibo.com/37cang</p></a>
                        </div>
                    </div>
                    <img style="margin-left:5px;" class="m7_qqhove" src="${staticSource}/static/images/qq.png">
                    <div class="m7_qq">
                        <div class="m7_qqsnum">
                            <a href="https://jq.qq.com/?_wv=1027&amp;k=52B1Cf8" title="点击链接加入群【37仓官方群】"
                               target="_blank"><p>官方QQ群：665829442</p></a>
                            <a href="http://wpa.qq.com/msgrd?v=3&uin=2667259864&site=qq&menu=yes" title="点击联系客服"
                               target="_blank"><p>客服QQ：2667259864</p></a>
                            <p>在线时间：9:00-17:30</p>
                        </div>
                    </div>
                    <div class="foot-bot-text">
                        <p>©2017-2019 www.37cang.com All Rights Reserved</p>
                        <p>江苏叁拾柒号仓智能科技有限公司 版权所有</p>
                        <p><a href="http://www.miitbeian.gov.cn/" target="_blank" style="color:#fff;">苏ICP备17031594号</a></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
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
