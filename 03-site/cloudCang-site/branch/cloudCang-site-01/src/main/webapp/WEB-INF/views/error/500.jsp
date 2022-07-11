<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>500</title>
    <link rel="stylesheet" href="${staticSource}/static/css/index.css">

</head>

<body>

<div class="four-error">
    <img src="${staticSource}/static/images/five-pic1.jpg"/>
    <div class="return-index">
        <p>抱歉~服务器出现故障o(╥﹏╥)o </p>
        <p><span id="jumpTo">5</span>秒后自动返回首页……</p>
        <a href="${ctx}/">返回首页</a>
    </div>
    <img src="${staticSource}/static/images/five-pic2.jpg"/>
</div>
<script  src="${staticSource}/static/js/jquery.min.js"  type="text/javascript"></script>
<script type="text/javascript">
    var allHg=$(document).height();
    $(".four-error").css("padding-top",allHg/5);

    /*倒计时*/
    var wait=$("#jumpTo").html();;
    timeOut();
    function timeOut(){
        if(wait==0){
            window.location.href="${ctx}/";
        }else{
            setTimeout(function(){
                wait--;
                $("#jumpTo").html(wait);
                timeOut();
            },1000)
        }
    }

</script>
</body>

</html>
