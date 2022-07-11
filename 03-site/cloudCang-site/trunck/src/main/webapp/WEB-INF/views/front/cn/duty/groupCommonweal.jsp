<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <title>集团公益活动-社会责任-江苏子雨集团有限公司官网</title>
<meta name="keywords" content="子雨集团公益活动" />
<meta name="description" content="江苏子雨集团热心公益慈善事业，关心弱势群体，坚持服务社会、承担责任，关爱民生、匡助教育、周济孤贫。" />
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
	<div class="ab_ban commonweal"></div>
    <div class="ab_top wd">
    	<div class="ab_tp_lf">
        	<i class="ab_icon ab_address"></i>您当前所在位置：<a href="${ctx}/cn/index.html">首页 ></a><a href="${ctx}/cn/duty/commonweal.html">社会责任 > </a> 集团公益活动
        </div>
        <div class="ab_tp_rg">
        	<ul>
            	<li ><a href="${ctx}/cn/duty/commonweal.html">互公益</a></li>
                <li class="title01"><a href="${ctx}/cn/duty/groupCommonweal.html" class="hover">集团公益活动</a></li>
                <li><a href="${ctx}/cn/duty/partyBuilding.html">党建活动</a></li>
                <li><a href="${ctx}/cn/duty/unions.html">工会</a></li>
            </ul>
       </div>
    </div><!-- ab_top end-->
    <div class="ab_bg">
    	<div class="ab_intro" onclick="javascript:window.location.href='${ctx}/cn/news/detail/${topArt.id}.html'">
           <img src="${topArt.stitleImage }" width="1133" height="528" />
           <div class="ab_intro_txt">
   	        <div class="ab_intro_title"><a>${topArt.stitle }</a><span><f:formatDate value="${topArt.tpublishDate}" pattern="yyyy-MM-dd"/></span></div>
                <a href="${ctx}/cn/news/detail/${topArt.id}.html" class="ab_intro_p">${topArt.snewsAbstract }<i class="ab_icon ab_intro_link"></i></a>
            </div>
        </div><!-- ab_intro end -->
       
        <form action="${ctx}/cn/duty/groupCommonweal" method="get" class="form-inline" role="form">   
   	   <div class="dj_content">
        	<div class="dj_title">集团公益活动</div>
 			<div class="dj_active_list">
            	<ul>
            		  <c:if test="${not empty page.results}">
                      	  <c:forEach items="${page.results}" var="op">
            			<li>
                    	<a href="${ctx}/cn/news/detail/${op.id}.html" class="dj_list_pic"><img src="${op.stitleImage }" width="348" height="176" /></a>
                        <div class="dj_active_txt" onclick="javascript:window.location.href='${ctx}/cn/news/detail/${op.id}.html'">
                            <h4><a>${op.stitle }</a></h4>
                            <p>${op.snewsAbstract}</p>
                            <div class="dj_list_date"><span class="overflow-left"><f:formatDate value="${op.tpublishDate}" pattern="yyyy-MM-dd"/></span><a href="${ctx}/cn/news/detail/${op.id}.html" class="ab_icon dj_list_link"></a></div>
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
