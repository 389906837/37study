<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/common/include/taglibs.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   <title>没有授权</title>

<style type="text/css">
/*	.nofind{margin:200px auto 0;width:800px;height:350px;background:url(${ctx}/resources/images/nofindbg.png) no-repeat;}
	.nofind-top{padding-top:16px;margin-left:173px;width:230px;color:#fff;text-align:center;}
	.nofind-top h3{font-size:36px;}
	.nofind-top p{font-size:24px;}
	.nofind-con{margin-top:20px;float:right;width:440px;font-size:24px;color:#7b7b7b;}
	.nofind-con p{line-height:50px;}
	.nofind-con span a{color:#0057d1;font-size:30px;}*/
html,body{
	width: 100%;
	height: 100%;
}
.main{
	width:80%;
	height: 100%;
	background-image: url(${staticSource}/resources/images/error-bg.png);
	background-repeat:no-repeat;
	background-size: 100% 100%;
	text-align: center;
	margin: 0 auto;
	display: flex;
	justify-content: center;
	align-items: center;
}
p{margin-top: 1.5rem}
.p1{
	font-size: 1.75rem;
}
.p2{
	color: #acadaa;
	font-size: 1.25rem
}
</style>
</head>	
	<body>
	<div class="main">
		<div>
			<img src="${staticSource}/resources/images/error-sorry.png">
			<p class="p1">您没有权限访问！请联系管理员</p>
		</div>

	</div>
		<%--<div class="nofind">--%>
			<%--<div class="nofind-top">--%>
				<%--<h3>咦？</h3>--%>
				<%--<p>页面怎么打不开了？</p>--%>
			<%--</div>--%>
			<%--<div class="nofind-con">--%>
				<%--<p>抱歉，您还未授权，不能进行相关操作</p>--%>
				<%--<p>请联系相关负责人或系统管理员！</p>			--%>
			<%--</div>--%>
		<%--</div>--%>
	</body>
</html>
