<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>会员注册</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no" />
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="${staticSource}/static/css/comm.css" />
    <link rel="stylesheet" href="${staticSource}/static/css/style.css" />
    <style type="text/css">
        body{background:#fff;}
    </style>
</head>
<body>
<form action="${ctx}/uc/alipayRegister" method="post" id="myForm">
<div class="container">
    <%@ include file="/common/include/commonHeader.jsp"%>
    <div class="iphone-wrap">
        <div class="iphone-list">
            <img class="ihpone-icon" src="${staticSource}/static/images/iphone-nameicon.png" >
            <input type="text" name="mobileNumber" id="mobileNumber" placeholder="请输入绑定手机号" />
        </div>
        <div class="error-tips"></div>
        <div class="iphone-list">
            <img class="ihpone-icon" src="${staticSource}/static/images/iphone-codeicon.png" >
            <input type="text" id="txCode" name="txCode" maxlength="4" placeholder="请输入图形验证码" >
            <img class="code-icon" src="${ctx}/Kaptcha" id="captchaImg" onclick="refreshCaptcha();" alt="点击刷新图形验证码" />
        </div>
        <div class="error-tips"></div>
        <div class="iphone-list">
            <img class="ihpone-icon" src="${staticSource}/static/images/iphone-numbericon.png" >
            <input type="text" id="msgCode" name="msgCode" type="tel" maxlength="6" placeholder="请输入手机短信验证码" />
            <span class="get-code" onclick="sendCodeByBind();" id="huoqu">获取验证码</span>
        </div>
        <div class="error-tips"></div>
    </div>
    <a href="javascript:void(0);" onclick="submitForm();" class="button-icon check-btn">提交注册</a>
</div>
</form>
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/comm.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/jquery.form.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/util.js"></script>
<script type="text/javascript">
    //绑定
    function submitForm() {
        if (!verifyPhone()) {
            return false;
        }
        var msgCode = $("#msgCode").val().trim();
        var mobileNumber = $("#mobileNumber").val().trim();
        var captcha = $("#txCode").val().trim();
        if (Util.isEmpty(msgCode)) {
            Util.Alert('请输入短信验证码');
            return false;
        }
        Util.Waiting({wid: 'wait_popup'});

        $.ajax({
            type : "post",
            data : {
                'msgCode': msgCode,
                'mobileNumber': mobileNumber,
                'captcha':captcha
            },
            url : ctxRoot + "/uc/alipayRegister",
            dataType : "json",
            success: function (msg) {
                //成功返回
                if (msg.success) {
                    Util.waitingClose($("#wait_popup"), function () {
                        Util.href(msg.data);
                    });
                } else {
                    Util.waitingClose($("#wait_popup"), function () {
                        Util.Alert(msg.msg);
                    });
                }
            }
        });
           
    }
    //发送短信
    function sendCodeByBind() {
        var code = $("#huoqu");
        if (code.hasClass("code_get")) {
            return;
        }
        if (!verifyPhone()) {
            return;
        }
        code.removeAttr("onclick");
        code.addClass("code_get");
        Util.Waiting({wid : "wait_popup"});
        
        $.ajax({
            type : "post",
            data : {
                mobileNumber : $("#mobileNumber").val(),
                txCode : $("#txCode").val(),
                types : 10
            },
            url : ctxRoot + "/base/sendMessageByTypes",
            dataType : "json",
            success : function(data) {
                if (data.success) {
                    Util.waitingClose($("#wait_popup"));
                    timer = setInterval(function() {
                        msgtime--;
                        code.html("重新获取(" + msgtime + ")");
                        if (msgtime == 0) {
                            clearInterval(timer);
                            msgtime = 90;
                            code.html("获取验证码");
                            code.removeClass("code_get");
                            code.attr("onclick", "sendCodeByBind();");
                        }
                    }, 1000);
                } else {
                    Util.waitingClose($("#wait_popup"), function() {
                        refreshCaptcha();
                        $("#txCode").val("");
                        clearInterval(timer);
                        msgtime = 90;
                        code.html("获取验证码");
                        Util.Alert(data.msg);
                        code.removeClass("code_get");
                        code.attr("onclick", "sendCodeByBind();");
                    }, 200);
                }
            }
        });
    }
    //验证手机号
    function verifyPhone() {
        var mobileNumber = $("#mobileNumber").val().trim();
        if(Util.isEmpty(mobileNumber)) {
            Util.Alert("请输入绑定手机号码");
            return false;
        }
        if(!regPhone.test(mobileNumber)) {
            Util.Alert("绑定手机号码格式不正确");
            return false;
        }
        var txCode = $("#txCode").val().trim();
        if (Util.isEmpty(txCode)) {
            Util.Alert('请输入图形验证码');
            return;
        }
        return true;
    }

    //图片验证码刷新
    function refreshCaptcha() {
        document.getElementById('captchaImg').src = '${ctx}/Kaptcha?'
            + Math.floor(Math.random() * 100);
    }
    <%@ include file="/common/include/baidu.jsp"%>
</script>
</body>
</html>