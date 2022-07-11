<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>Organizations-Jiangsu Ziyu Group</title>
<meta name="keywords" content="江苏子雨集团组织架构,江苏子雨集团组织关系" />
<meta name="description" content="江苏子雨集团有限公司初步完成了集团化、产业化布局，集团由传统的生产制造型企业完美过渡到以工业制造、互联网金融、环保科技为主要产业集群的企业集团公司。" />
<link rel="stylesheet" type="text/css" href="${staticSource}/static/front/en/css/comm.css" />
<link rel="stylesheet" type="text/css"  href="${staticSource}/static/front/en/css/index.css" />
<style>
body{background:#f4f4f4;}
.english-nav ul.nav-parent li a.hover6{color:#fff;background:#258beb;}
</style>
</head>	
<body>
	<!-- 头部  -->
	<jsp:include page="/common/include/enFrontTop.jsp" />
	<!--    banner  -->
	<div class="ab_ban"  style="background:url(${staticSource}/static/front/en/images/6.jpg) no-repeat" ></div>
    <div class="ab_bg">
	  <div class="president_content">
      	<img class="organization_pic" src="${staticSource}/static/front/en/images/organization_pic.jpg" width="1134" height="590" /> 
      </div><!-- honor_content end -->
    </div><!-- ab_bg end -->
    
   <!-- 底部  -->
	<jsp:include page="/common/include/enFrontButtom.jsp" /> 
    
<script type="text/javascript" src="${staticSource}/static/front/en/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" >
	$(".dj_active_list ul >li:nth-child(3n)").css("margin-right",0);
	// 二级导航条的选中样式
	$(".company_rg ul li").click(function(){
		$(this).addClass("current");
		$(this).siblings().removeClass("current");	
	});
	$(".company_content_pic >li:nth-child(3n)").css("margin-right",0);

</script>



</body>
</html>
