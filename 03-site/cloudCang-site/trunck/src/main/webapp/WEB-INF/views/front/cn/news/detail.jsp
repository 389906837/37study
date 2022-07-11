<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${article.stitle }-${article.snavigationName}</title>
<meta name="keywords" content="江苏子雨集团新闻,子雨集团最新动态" />
<meta name="description" content="江苏子雨集团新闻中心为用户提供关于江苏子雨集团有限公司最新资讯,互融宝平台新闻等等最新消息介绍,让用户实时知晓江苏子雨集团公司的最新动态!" />
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
	<div class="ab_ban group"></div>
    <div class="ab_top wd" style="padding-top:0px;">
    	<div class="ab_tp_lf" style="padding-bottom:15px;">
        	<i class="ab_icon ab_address"></i>您当前所在位置：<a href="${ctx}/cn/index.html">首页 ></a>
        	<c:choose>
        		<c:when test="${fn:trim(article.snavigationName)=='集团新闻'}">
        				<a href="${ctx}/cn/news/group.html">新闻中心 ></a><a href="${ctx}/cn/news/group.html">${article.snavigationName} ></a> 
        		</c:when>
        		<c:when test="${fn:trim(article.snavigationName)=='政策法规'}">
        				<a href="${ctx}/cn/news/group.html">新闻中心 ></a><a href="${ctx}/cn/news/laws.html">${article.snavigationName} ></a> 
        		</c:when>
        		<c:when test="${fn:trim(article.snavigationName)=='行业动态'}">
        				<a href="${ctx}/cn/news/group.html">新闻中心 ></a><a href="${ctx}/cn/news/industry.html">${article.snavigationName} ></a> 
        		</c:when>
        	    <c:when test="${fn:trim(article.snavigationName)=='互公益'}">
        				<a href="${ctx}/cn/duty/commonweal.html">社会责任></a><a href="${ctx}/cn/duty/commonweal.html">${article.snavigationName} ></a> 
        		</c:when>
        		 <c:when test="${fn:trim(article.snavigationName)=='集团公益活动'}">
        				<a href="${ctx}/cn/duty/commonweal.html">社会责任></a><a href="${ctx}/cn/duty/groupCommonweal.html">${article.snavigationName} ></a> 
        		</c:when>
        		 <c:when test="${fn:trim(article.snavigationName)=='党建活动'}">
        				<a href="${ctx}/cn/duty/commonweal.html">社会责任></a><a href="${ctx}/cn/duty/partyBuilding.html">${article.snavigationName} ></a> 
        		</c:when>
        		 <c:when test="${fn:trim(article.snavigationName)=='工会'}">
        				<a href="${ctx}/cn/duty/commonweal.html">社会责任></a><a href="${ctx}/cn/duty/unions.html">${article.snavigationName} ></a> 
        		</c:when>
        	</c:choose>
        
        	正文
        </div>
    </div><!-- ab_top end-->
    <div class="ab_bg">
   	   <div class="news_content">
            <div class="news_link">
            	<c:choose>
            		<c:when test="${not empty next }">
            			<a class="hover" href="${ctx}/cn/news/detail/${next}.html">下一条</a>
            		</c:when>
            		<c:otherwise>
            			
            		</c:otherwise>
            	</c:choose>
            	
               <c:choose>
            		<c:when test="${not empty pre }">
            			<a class="hover" href="${ctx}/cn/news/detail/${pre}.html">上一条</a>
            		</c:when>
            		<c:otherwise>
            			
            		</c:otherwise>
            	</c:choose>
            </div>
            <div class="news_title">
            	<h4>${article.stitle }</h4>
                <h5><f:formatDate value="${article.tpublishDate}"  pattern="yyyy-MM-dd HH:mm:ss"/></h5>
            </div>
            ${content.scontent}
        </div><!-- news_content end -->
    </div><!-- ab_bg end -->
    
   <!-- 底部 -->
	<jsp:include page="/common/include/frontBottom.jsp" />
    

<script type="text/javascript" >
	$(".dj_active_list ul >li:nth-child(3n)").css("margin-right",0);

</script>



</body>
</html>
