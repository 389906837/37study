<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no" />
    <meta name="keywords" content="${article.skeys}"/>
    <meta name="description" content="${article.snewsAbstract}"/>
    <title>${article.stitle}-37号仓-计算机视觉识别核心算法供应商-江苏叁拾柒号仓智能科技有限公司</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="${staticSource}/static/wap/css/common_new.css">
    <link rel="stylesheet" href="${staticSource}/static/wap/css/index_new.css">
    <script type="text/javascript" src="${staticSource}/static/wap/js/jquery.min.js"></script>
    <script type="text/javascript" src="${staticSource}/static/wap/js/swiper.min.js"></script>
</head>
<body>
<%@ include file="/common/include/head.jsp" %>
${content2.scontent}
<%@ include file="/common/include/tail.jsp" %>
<script>
    $(function(){
        $(".garbage-classcify-title a").click(function(){
            var ind = $('.garbage-classcify-title a').index(this)+1; //序号
            var topHeight = $('#pro0'+ind).offset().top;
            $('body,html').animate({scrollTop:topHeight},500);
            $(this).addClass("hover").siblings().removeClass("hover")
        })
        $(window).scroll(function(){
            var f1,f2,f3;
            var fixRight = $('.garbage-classcify-title a');
            var sTop = $(window).scrollTop();
            f1=$('#pro01').offset().top;
            f2=$('#pro02').offset().top;
            f3=$('#pro03').offset().top;
            if(sTop>=f1){
                fixRight.eq(0).addClass('hover').siblings().removeClass('hover');
            }
            if(sTop>=f2){
                fixRight.eq(1).addClass('hover').siblings().removeClass('hover');
            }
            if(sTop>=f3){
                fixRight.eq(2).addClass('hover').siblings().removeClass('hover');
            }
        })

    })
</script>
</body>
</html>