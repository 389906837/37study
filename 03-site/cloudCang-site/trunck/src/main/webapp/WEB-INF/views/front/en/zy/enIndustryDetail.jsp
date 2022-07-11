<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${article.stitle}-ZiyuIndustries</title>
<meta name="keywords" content="集团产业,子雨产业" />
<meta name="description" content="江苏子雨集团下属有5家公司：江苏强业金融信息服务有限公司、南京子雨机械有限责任公司、南京振尧包装容器有限责任公司、句容振尧包装容器有限责任公司、山东序元有限责任公司。" />
<link rel="stylesheet" type="text/css" href="${staticSource}/static/front/en/css/comm.css" />
<link rel="stylesheet" type="text/css"  href="${staticSource}/static/front/en/css/index.css" />
<style>
body{background:#f4f4f4;}
.english-nav ul.nav-parent li a.hover4{color:#fff;background:#258beb;}
</style>
</head>	
<body>
	<!-- 头部  -->
	<jsp:include page="/common/include/enFrontTop.jsp" />
	<!--    banner  -->
	<div class="ab_ban"></div>
    
    <div class="ab_bg">
   	   <div class="news_content">
            <div class="news_title">
            	<h4>${article.stitle }</h4>
            </div>
            ${content.scontent}
        </div><!-- news_content end -->
    </div><!-- ab_bg end -->
    
   <!-- 底部  -->
	<jsp:include page="/common/include/enFrontButtom.jsp" /> 
    
<script type="text/javascript" src="${staticSource}/static/front/en/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" >
	$(".dj_active_list ul >li:nth-child(3n)").css("margin-right",0);

</script>



</body>
</html>
