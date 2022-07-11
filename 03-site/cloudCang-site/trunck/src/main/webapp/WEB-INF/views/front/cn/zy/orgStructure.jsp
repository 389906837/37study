<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>组织架构-江苏子雨集团有限公司官网</title>
<meta name="keywords" content="江苏子雨集团组织架构,江苏子雨集团组织关系" />
<meta name="description" content="江苏子雨集团有限公司初步完成了集团化、产业化布局，集团由传统的生产制造型企业完美过渡到以工业制造、互联网金融、环保科技为主要产业集群的企业集团公司。" />
<link rel="stylesheet" type="text/css" href="${staticSource}/static/front/css/comm.css" />
<link rel="stylesheet" type="text/css"  href="${staticSource}/static/front/css/index.css" />
<script type="text/javascript" src="${staticSource}/static/front/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/front/js/common.js"></script>
<style>
body{background:#f4f4f4;}
</style>
</head>	
<body>
<!-- 头部  -->
	<jsp:include page="/common/include/frontTop.jsp" />
	<!--    banner  -->
	<div class="ab_ban statements"></div>
    <div class="ab_top wd">
    	<div class="ab_tp_lf">
        	<i class="ab_icon ab_address"></i>您当前所在位置：<a href="${ctx}/cn/index.html">首页 ></a><a href="${ctx}/cn/zy/statements.html">子雨集团 ></a> 组织架构
        </div>
         <!-- 子导航  -->
	<jsp:include page="/common/include/zyTitle.jsp" />
	<script>
		$('div.company_rg').find('li').eq(2).addClass('current');
	</script>
    </div><!-- ab_top end-->
    <div class="ab_bg">
	  <div class="president_content">
      	<img class="organization_pic" src="${staticSource}/static/front/images/organization_pic.jpg?2" width="1133" height="589" /> 
      </div><!-- honor_content end -->
    </div><!-- ab_bg end -->
    
  <!-- 底部 -->
<jsp:include page="/common/include/frontBottom.jsp" />
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
