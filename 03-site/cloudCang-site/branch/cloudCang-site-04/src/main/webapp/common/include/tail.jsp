<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="foot md">
    <h4><img class="index-iphone-icon" src="${staticSource}/static/wap/image/index-iphone-icon.png">400-828-3737 /
        025-58061600</h4>
    <p>周一至周五：9:00-17:30</p>
    <div class="foot-contact">
        <a class="index-weibo" href="http://weibo.com/37cang"><img
                src="${staticSource}/static/wap/image/index-weibo.png"></a>
        <a class="index-qq"><img src="${staticSource}/static/wap/image/index-qq.png"></a>
        <div class="qq-ma">
            <a href="https://jq.qq.com/?_wv=1027&amp;k=52B1Cf8" title="点击链接加入群【37仓官方群】" target="_blank"><p>
                官方QQ群：665829442</p></a>
            <a href="http://wpa.qq.com/msgrd?v=3&amp;uin=2667259864&amp;site=qq&amp;menu=yes" title="点击联系客服"
               target="_blank"><p>客服QQ：2667259864</p></a>
            <p>在线时间：9:00-17:30</p>
        </div>
        <a class="index-weixin"><img src="${staticSource}/static/wap/image/index-weixin.png"></a>
        <div class="weixin-ma"><img src="${staticSource}/static/wap/image/index-weixin-pop.png"></div>
    </div>
    <p>地址：江苏省南京市栖霞区紫东创意园B7</p>
    <div class="foot-bot">
        <img class="index-logo" src="${staticSource}/static/wap/image/index-logo.png">
        <h5>37号仓视觉识别软硬件一体化解决方案提供商</h5>
    </div>
</div>

<script type="text/javascript">
    // 微信 qq 弹出
    $(".index-weixin img").click(function (e) {
        $(".weixin-ma").toggle();
        $(".qq-ma").hide();
        e.stopPropagation();//防止冒泡事件
    });
    $(".index-qq img").click(function (e) {
        $(".qq-ma").toggle();
        $(".weixin-ma").hide();
        e.stopPropagation();//防止冒泡事件
    });
    $(document).click(function () {
        $(".weixin-ma,.qq-ma").hide();
    });
    <%@ include file="/common/include/wap_baidu.jsp"%>
</script>