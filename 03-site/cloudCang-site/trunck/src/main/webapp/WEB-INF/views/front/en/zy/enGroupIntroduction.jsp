<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>About Ziyu Group-Jiangsu Ziyu Group</title>
<meta name="keywords" content="江苏子雨集团介绍,江苏子雨集团简介" />
<meta name="description" content="江苏子雨集团有限公司初步完成了集团化、产业化布局，集团由传统的生产制造型企业完美过渡到以工业制造、互联网金融、环保科技为主要产业集群的企业集团公司。" />
<link rel="stylesheet" type="text/css" href="${staticSource}/static/front/en/css/comm.css" />
<link rel="stylesheet" type="text/css"  href="${staticSource}/static/front/en/css/index.css" />
<style>
body{background:#f4f4f4;}
.english-nav ul.nav-parent li a.hover{color:#fff;background:#258beb;}
</style>
</head>	
<body>
	<!-- 头部  -->
	<jsp:include page="/common/include/enFrontTop.jsp" />
	
	<!--    banner  -->
	<div class="ab_ban" style="background:url(${staticSource}/static/front/en/images/1.jpg) no-repeat"></div>
    <div class="ab_bg">
    	<div class="company_title"><img src="${staticSource}/static/front/en/images/company_title.jpg" width="1135" height="53" /></div>
      <div class="company_content">
        	<div class="company_content_lf">
       	  		<img src="${staticSource}/static/front/en/images/pic01.jpg?2" width="515" height="343" /> 
          		<p>Jiangsu Ziyu Group Co., Ltd. was established in March, 2010. Its predecessor, Qixia Textile Accessories Factory was established in 1991. It is headquartered in Nanjing, Jiangsu province. The group has six sections: Jiangsu Qiangye Financial Information Service Co., ltd. as the representative of Ziyu technology finance section, Jurong Zhenyao Packaging Container Co., Ltd. and Nanjing Zhenyao Packaging Container Co., Ltd. as the representative of Ziyu industrial manufacturing section, Shandong Xuyuan Environmental Science and Technology Co., Ltd. as the representative of Ziyu new and high environmental section, Nanjing Aige Zhitong Agricultural Science and Technology Co., Ltd. as the representative of Ziyu intelligent agriculture section, Nanjing Changyou Road and Bridge Engineering Co., Ltd. as the representative of Ziyu road and bridge construction section, Jiangsu No.37 Store Electronic Commerce Co., Ltd. as the representative of Ziyu artificial intelligence section. Each subsidiary is engaged in the business of investment consultation, packaging container manufacturing, machinery manufacturing, environmental protection technology and so on. At this point, Jiangsu Ziyu Group Co., Ltd. has preliminarily completed the collectivization and industrialization layout. The group is transformed from a traditional manufacturing enterprise to an enterprise group with industrial manufacturing, Internet finance and environmental protection technology as the main industrial cluster.</p>
             
            </div>
        	<div class="company_content_rg">
        	<p>Under the influence of the "Chinese dream" in the 21st century, Jiangsu Ziyu Group holds the banner of the national enterprise to inherit the national culture, build the national brand, revitalize the national industry, insist on the management of the enterprise with scientific ideas and build the road of the characteristics development. Ziyu Group will continue to expand the market step by step. We strive to make Jiangsu Ziyu group become China's first-class enterprise with philosophy of "The company has concept, the personnel has the accomplishment, the market has public praise, the enterprise has foreground, the society has influence." </p>
        	             <ul class="company_content_pic">
                	<li><img src="${staticSource}/static/front/en/images/pic02.jpg?2" width="166" height="166" /></li>
                    <li><img src="${staticSource}/static/front/en/images/pic03.jpg?2" width="166" height="166" /></li>
                    <li><img src="${staticSource}/static/front/en/images/pic04.jpg?2" width="166" height="166" /></li>
                </ul>
                 <p>Under the calling of “China Dream” in the 21st century, Jiangsu Ziyu Group highly holds the national enterprise flag, inherits national culture, builds national brands, adheres to scientific enterprise management concept of management and builds the development road with Ziyu characteristics. The enterprise will constantly expand the market in order to make it become a top brand in China with “excellent-thought company, high quality personnel, good reputation market, bright future enterprise and high social influence”.</p>
            </div>
      </div><!-- company_content end -->
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
