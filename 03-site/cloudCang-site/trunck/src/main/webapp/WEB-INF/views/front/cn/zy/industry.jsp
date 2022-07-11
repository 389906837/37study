<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <title>子雨产业-江苏子雨集团有限公司官网</title>
<meta name="keywords" content="集团产业,子雨产业" />
<meta name="description" content="江苏子雨集团下属有5家公司：江苏强业金融信息服务有限公司、南京子雨机械有限责任公司、南京振尧包装容器有限责任公司、句容振尧包装容器有限责任公司、山东序元有限责任公司。" />
<link rel="stylesheet" type="text/css" href="${staticSource}/static/front/css/comm.css" />
<link rel="stylesheet" type="text/css"  href="${staticSource}/static/front/css/index.css?20180123" />
<style>
body{background:#f4f4f4;}
</style>
</head>	
<body>
<!-- 头部  -->
	<jsp:include page="/common/include/frontTop.jsp" />
	<!--    banner  -->
	<div class="ab_ban industry"></div>
    <div class="ab_top wd">
    	<div class="ab_tp_lf">
        	<i class="ab_icon ab_address"></i>您当前所在位置：<a href="${ctx}/cn/index.html">首页 ></a> 子雨产业
        </div>
        <div class="company_rg">
        	<a href="${ctx}/cn/zy/industry.html" class="industry_a" >子雨产业</a>
       </div>
    </div><!-- ab_top end-->
    <div class="ab_bg" >
   	   <div class="industry_content">
       	   <h4 class="industry_title"><img src="${staticSource}/static/front/images/industry_title.jpg" width="149" height="60" /></h4>
           <div class="industry_list">
           	 <ul>
           	  <c:forEach items="${arts}" var="item" varStatus="status">
           	 	<li>
                	<div class="industry_txt">
	                	<div class="industry_pic" onclick="window.location.href='${ctx}/cn/zy/industry/detail/${item.id}.html'"><img src="${item.stitleImage}" width="351" height="179" /></div>	
	                   	<h5><a href="${ctx}/cn/zy/industry/detail/${item.id}.html">${item.stitle}</a></h5>
	                   	<p>${item.snewsAbstract}</p>
	                    <a href="${ctx}/cn/zy/industry/detail/${item.id}.html" class="industry_detail"></a>
	                </div>
                </li>
           	 </c:forEach>
             </ul>
           </div>
       </div><!-- industry_content end -->
    </div><!-- ab_bg end -->
    
 <!-- 底部 -->
<jsp:include page="/common/include/frontBottom.jsp" />
    
<script type="text/javascript" src="${staticSource}/static/front/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/front/js/common.js"></script>
<script type="text/javascript" >
var nut=parseInt(($(".industry_list li").length)/3);
for(var nt=1;nt<=nut;nt++){
	$(".industry_list li").eq(3*nt-1).css("margin-right","0px");
}
</script>



</body>
</html>
