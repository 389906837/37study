<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=10">
    <link rel="shortcut icon" href="${staticSource}/ico/favicon_${sessionScope.SESSION_KEY_SMERCHANT_CODE}.ico" />
    <title>登录 - 智能云服务管理平台</title>
    <meta name="keywords" content="智能云服务管理平台" />
    <meta name="description" content="智能云服务管理平台" />
    <link href="${staticSource}/resources/hplus/css/login.css?1.3" rel="stylesheet">
    <script src="${staticSource}/resources/js/jquery.min.js?v=2.1.4"></script>
</head>
<body>
<div class="logon">
    <form action="${ctx}/mgr/login" accept-charset="utf-8" id="loginForm" method="post" onsubmit="return doSubmit();">
        <div class="logon-main">
            <div class="logon-content">
                <div class="logon-title">
                    <c:if test="${isDefault eq 0}">
                        <img src="${dynamicSource}${loginLogo}" style="height: 39px;width: 79px;" />
                    </c:if>
                    <c:if test="${isDefault ne 0}">
                        <img src="${staticSource}${loginLogo}" style="height: 39px;width: 79px;" />
                    </c:if>
                    <span>|</span><p>智慧云服务管理平台</p>
                </div>
                <ul>
                    <li><input type="text" name="username" id="username" placeholder="用户名"/></li>
                    <li class="error" id="usernameError"></li>
                    <li><input type="password" name="password" id="password" placeholder="密码"/></li>
                    <li class="error" id="passwordError"></li>
                    <li style="width:190px;">
                        <input type="text" name="captcha" value="" id="captcha" autocomplete="off" placeholder="验证码"/>
                        <img src="${ctx}/Kaptcha" class="captchaImg" id="captchaImg" />
                    </li>
                    <li class="error" id="captchaError"></li>
                    <c:if test="${LOGIN_IP_STATUS==3}">
                        <li style="width:190px;">
                            <input type="text" name="msgCode" id="msgCode" autocomplete="off" placeholder="短信验证码"/>
                            <span>获取验证码</span>
                        </li>
                        <li class="error" id="msgCodeError"></li>
                    </c:if>
                    <li class="button"><button type="submit" onclick="return doSubmit();">立即登录</button></li>
                </ul>
                <div id="errorDiv">
                    <span id="errorspan"></span>
                </div>
            </div>
            <div class="logon-ip">
                <span>当前ip：${ip}</span>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript">
    <shiro:user>
    window.location.href="${ctx}/mgr/main";
    </shiro:user>
    $(".logon-main").each(function (i) {
        var widWidth = document.documentElement.clientWidth;
        var widHight = document.documentElement.clientHeight;
        var onwidth = $(".logon-main").eq(i).outerWidth();
        var onhight = $(".logon-main").eq(i).outerHeight();
        $(".logon").height(widHight);
        $(".logon-main").eq(i).css("left", (widWidth - onwidth) / 2);
        $(".logon-main").eq(i).css("top", (widHight - onhight) / 2);
    });

    $(window).resize(function () {
        $(".logon-main").each(function (i) {
            var widWidth = document.documentElement.clientWidth;
            var widHight = document.documentElement.clientHeight;
            var onwidth = $(".logon-main").eq(i).outerWidth();
            var onhight = $(".logon-main").eq(i).outerHeight();
            $(".logon-main").eq(i).css("left", (widWidth - onwidth) / 2);
            $(".logon-main").eq(i).css("top", (widHight - onhight) / 2);
        });
    });
    var validCode=true;
    $(function() {
        $(".logon-content ul li input").blur(function(){
            $(this).parent().removeClass("hover");
        });
        $(".logon-content ul li input").focus(function(){
            $(this).parent().addClass("hover");
        });
        $(".logon-content ul li span").click(function(){
            if(!validCode)return;
            if(!checkCaptcha()){
                $("#captcha").focus();
                return;
            }
            $.ajax({
                type : "POST",
                dataType : "json",
                url :"${ctx}/mgr/getMsgCode",
                data : {
                    kaptchaCode:$("#captcha").val(),
                    username:$('#username').val()
                },
                success : function(msg) {
                    if (msg.success) {
                        document.getElementById("errorDiv").style.display = "none";
                        $('#msgCodeError').html("");
                        getMsgSuccess();
                    }else{
                        $("#captchaImg").trigger("click");
                        $('#msgCodeError').html(msg.msg);
                        $("#captcha").focus();
                        $("#captcha").select();
                    }
                }
            });
        });
    });


    function getMsgSuccess(){
        var time=90;
        var code=$(".logon-content ul li span");
        if (validCode) {
            validCode=false;
            code.addClass("wfpassword-huoqu1");
            var t=setInterval(function  () {
                time--;
                code.html("重新获取("+time+")");
                if (time==0) {
                    clearInterval(t);
                    code.html("获取短信验证码");
                    validCode=true;
                    code.removeClass("wfpassword-huoqu1");
                }
            },1000);
        }
    }
    $(".captchaImg").click(function(){
        $("#captchaImg").attr('src','${ctx}/Kaptcha?' + Math.floor(Math.random() * 100));
    });

    function checkUsername(){
        if($('#username').val()==""){
            $('#usernameError').html("请输入用户名！");
            return false;
        }
        $('#usernameError').html("");
        return true;
    }

    function checkPassword(){
        if($('#password').val()==""){
            $('#passwordError').html("请输入密码！");
            return false;
        }
        $('#passwordError').html("");
        return true;
    }

    function checkCaptcha(){
        if($('#captcha').val()==""){
            $('#captchaError').html("请输入验证码！");
            return false;
        }
        $('#captchaError').html("");
        return true;
    }


    function checkMsgCode(){
        <c:if test="${LOGIN_IP_STATUS==3}">
        if($('#msgCode').val()==""){
            $('#msgCodeError').html("请输入短信验证码！");
            return false;
        }
        $('#msgCodeError').html("");
        </c:if>
        return true;
    }
    function doSubmit() {
        document.getElementById("errorDiv").style.display = "none";
        var f1=checkUsername() ;
        var f2=checkPassword() ;
        var f3=checkCaptcha() ;
        var f4=checkMsgCode() ;

        if(f1&&f2&&f3&&f4){
            return true;
        }
        return false;
    }

    if ('${shiroLoginFailure}' != "" && '${shiroLoginFailure}' != null) {
        document.getElementById("errorspan").innerHTML = '${shiroLoginFailure}';
        document.getElementById("errorDiv").style.display = "block";
    } else {
        document.getElementById("errorDiv").style.display = "none";
    }

    if (!(window.parent == window)){
        window.parent.location.href = self.location.href;
    }
</script>
</body>
</html>
