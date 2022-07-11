<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>欢迎登录叁拾柒号仓官网后台管理系统</title>

    <!-- Bootstrap Core CSS -->
    
    <link href="${staticSource}/static/template/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${staticSource}/static/template/vendor/bootstrap/css/style.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="${staticSource}/static/template/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="${staticSource}/static/template/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="${staticSource}/static/template/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>
<div class="wfcontainer">
    <div class="container">
    	<div class="wrap-content">
    	<h2>叁拾柒号仓官网后台管理系统</h2>
    </div>
        <div class="row">
            <div class="col-md-5 col-md-offset-3">
                <div class="login-panel panel panel-default register-main" style="margin-top:0">
                	<div class="register-maintitle">
						<span>用户登录</span>
					</div>
                    <div class="panel-body" style="position:relative;padding-top:65px;">
                        <form id="loginForm" role="form" name="loginForm" action="${ctx}/mgr/login" method="post">
                            <fieldset>
	                            <div class="form-group" style="position:absolute;top:25px;left:82px">
	                            	<span id="error_tip" style="color:red;"><c:if test="${not empty shiroLoginFailure}">${shiroLoginFailure}</c:if></span>
	                            </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="请输入账户名" id="username" name="username" type="text" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="请输入登录密码" id="password" name="password" type="password" autocomplete="off">
                                </div>
                                 <div class="form-group">
                                    <input class="form-control" id="piccode" placeholder="请输入验证码" name="captcha" type="text" maxlength="5" autocomplete="off" style="width:50%">
                                	 <img class="tx_get" id="captchaImg" src="${ctx}/Kaptcha" onclick="refreshCaptcha();" />
                                </div>
                                <!-- Change this to a button or input when using this as a form -->
                                <a href="javascript:void(0);" onclick="submit();" class="btn btn-lg btn-success btn-block loginsubmit">登录</a>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
    <!-- jQuery -->
    <script src="${staticSource}/static/template/vendor/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="${staticSource}/static/template/vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="${staticSource}/static/template/vendor/metisMenu/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="${staticSource}/static/template/dist/js/sb-admin-2.js"></script>

<script type="text/javascript">
function showTip(msg){
	$('#error_tip').html(msg);
}
function refreshCaptcha() {
	document.getElementById('captchaImg').src ='${ctx}/Kaptcha?' + Math.floor(Math.random() * 100);
  }
  
function submit() {
	var username = $("#username").val();
	var password = $("#password").val();
	if (username.length == 0 && password.length == 0) {
		showTip('请输入账户名和登录密码');
		return ;
		
	}
	if (username.length == 0) {
		showTip('请输入登录账户名');
		return ;
	}
	
	if (password.length == 0) {
		showTip('请输入登录密码');
		return ;
	}
	
	
	var piccode = $("#piccode").val();
	
	if (piccode.length == 0) {
		showTip('请输入图片验证码');
		return ;
	}
	
	
	 $("#loginForm").submit();
}

$(document).keyup(function(event){
	  if(event.keyCode ==13){
		  submit();
	  }
});

</script>
</body>

</html>
