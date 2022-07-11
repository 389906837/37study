<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <title>行业动态-新闻中心-江苏子雨集团有限公司官网</title>
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
    <div class="ab_top wd">
    	<div class="ab_tp_lf">
        	<i class="ab_icon ab_address"></i>您当前所在位置：<a href="${ctx}/cn/index.html">首页 ></a><a href="${ctx}/cn/news/group.html">新闻中心 ></a> 行业动态
        </div>
        <div class="ab_tp_rg">
        	<ul>
            	<li ><a href="${ctx}/cn/news/group.html">集团新闻</a></li>
                <li class="title01"><a href="${ctx}/cn/news/industry.html" class="hover">行业动态</a></li>
                <li><a href="${ctx}/cn/news/laws.html">政策法规</a></li>
            </ul>
       </div>
    </div><!-- ab_top end-->
    <div class="ab_bg">
     <form action="${ctx}/cn/news/industry" method="get" class="form-inline" role="form">   
   	   <div class="dj_content">
            <div class="dj_active_list">
            	<ul>
                <c:if test="${not empty page.results}">
                      	  <c:forEach items="${page.results}" var="op">
            			<li onclick="javascript:window.location.href='${ctx}/cn/news/detail/${op.id}.html'">
                    	<a href="${ctx}/cn/news/detail/${op.id}.html" class="dj_list_pic"><img src="${op.stitleImage }" width="348" height="176" /></a>
                        <div class="dj_active_txt">
                            <h4><a>${op.stitle }</a></h4>
                            <p>${op.snewsAbstract}</p>
                            <div class="dj_list_date"><span class="overflow-left"><f:formatDate value="${op.tpublishDate}" pattern="yyyy-MM-dd"/></span><a href="" class="ab_icon dj_list_link"></a></div>
                        </div>
                    </li>
            		
            		 	</c:forEach>
                  </c:if>

                </ul>
            </div>
            <div class="fenye">
            	<jsp:include page="/common/include/pagenation.jsp"></jsp:include>
            </div><!-- fenye end -->
        </div><!-- dj_content end -->
        </form>
    </div><!-- ab_bg end -->
    
   <!-- 底部 -->
	<jsp:include page="/common/include/frontBottom.jsp" />
 
<script type="text/javascript" >
	$(".dj_active_list ul >li:nth-child(3n)").css("margin-right",0);

</script>



</body>
</html>
