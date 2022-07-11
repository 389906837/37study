<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <title>发展历程-江苏子雨集团有限公司官网</title>
<meta name="keywords" content="子雨集团发展历程" />
<meta name="description" content="子雨集团发展历程频道为用户介绍了自江苏子雨集团有限公司成立以来所经历的大事记与所获得的大小荣誉。" />
<link rel="stylesheet" type="text/css" href="${staticSource}/static/front/css/comm.css" />
<link rel="stylesheet" type="text/css"  href="${staticSource}/static/front/css/index.css" />
<script type="text/javascript" src="${staticSource}/static/front/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/front/js/common.js"></script>
<script type="text/javascript" src="${staticSource}/static/front/js/jquery.roundabout.js"></script>
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
        	<i class="ab_icon ab_address"></i>您当前所在位置：<a href="${ctx}/cn/index.html">首页 ></a><a href="${ctx}/cn/zy/statements.html">子雨集团 ></a> 发展历程
        </div>
      <!-- 子导航  -->
	<jsp:include page="/common/include/zyTitle.jsp" />
	<script>
		$('div.company_rg').find('li').eq(5).addClass('current');
	</script>
    </div><!-- ab_top end-->
    <div class="ab_bg">
    	<div class="dev-content">
    		<img src="${staticSource}/static/front/images/develop.jpg"/>
    		${contents.scontent}
    	</div>		
    </div><!-- ab_bg end -->
    <script type="text/javascript">
    	$(".dev-right").each(function(){
    		$(this).css("padding-top",$(this).prev().height());
    	});
    	$(".dev-btn").eq(0).css("margin-top",0);
    	$(".dev").hide();
    	$(".dev-btn span").addClass("hover");
    	$(".dev-btn span").click(function(){
    		$(this).toggleClass("hover");
    		$(this).parent().next().slideToggle();
    	});
    	var numt=$(".dev").eq(0).find("div").length;
    	if(numt>8){
    		$(".dev").eq(0).show();
    		$(".dev-btn").eq(0).find("span").removeClass("hover");
    	}
    </script>
<!-- 底部 -->
<jsp:include page="/common/include/frontBottom.jsp" />
</body>
</html>

