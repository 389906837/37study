<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   <title>${article.stitle}-子雨产业</title>
 <meta name="keywords" content="集团产业,子雨产业" />
<meta name="description" content="江苏子雨集团下属有5家公司：江苏强业金融信息服务有限公司、南京子雨机械有限责任公司、南京振尧包装容器有限责任公司、句容振尧包装容器有限责任公司、山东序元有限责任公司。" />
<link rel="stylesheet" type="text/css" href="${staticSource}/static/front/css/comm.css" />
<link rel="stylesheet" type="text/css"  href="${staticSource}/static/front/css/index.css" />
<style type="text/css">
body{background:#f4f4f4;}
</style>
</head>	
<body>
	<!-- 头部  -->
	<jsp:include page="/common/include/frontTop.jsp" />
	<div class="ab_ban"></div>
	<div class="legal" style="padding-bottom:0">
		<div class="overflow wd">
			<div class="ab_tp_lf legal-title" style="width:100%">
				<i class="ab_icon ab_address"></i>
				您当前所在位置：
				<a href="${ctx}/">首页 ></a><a href="${ctx}/cn/zy/industry.html">子雨产业 ></a>
				${article.stitle}
			</div>
		</div>
      <div class="ab_bg">
   	   <div class="news_content">
            <div class="news_title">
            	<h4>${article.stitle } <c:if test="${not empty  article.sresourceUrl}"><a href="${article.sresourceUrl}" id="comein" target="_blank">点击进入官网</a></c:if></h4>
            </div>
            <div class="company-detail">
            	${content.scontent}
            </div>
        </div><!-- news_content end -->
    </div><!-- ab_bg end -->
	</div>
		<!-- 底部 -->
<jsp:include page="/common/include/frontBottom.jsp" />
<script type="text/javascript" src="${staticSource}/static/front/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/front/js/common.js"></script>

</body>
</html>