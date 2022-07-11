<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>集团介绍-江苏子雨集团有限公司官网</title>
<meta name="keywords" content="江苏子雨集团介绍,江苏子雨集团简介" />
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
        	<i class="ab_icon ab_address"></i>您当前所在位置：<a href="${ctx}/cn/index.html">首页 ></a><a href="${ctx}/cn/zy/statements.html">子雨集团 ></a> 集团介绍
        </div>
      <!-- 子导航  -->
	<jsp:include page="/common/include/zyTitle.jsp" />
	<script>
		$('div.company_rg').find('li').eq(1).addClass('current');
	</script>
    </div><!-- ab_top end-->
    <div class="ab_bg">
    	<div class="company_title jtan-pic"><img src="${staticSource}/static/front/images/jtan-pic.jpg" /></div>
      <div class="company_content">
        	<div class="company_content_lf">
       	  		<img src="${staticSource}/static/front/images/pic01.jpg?2" width="515" height="343" /> 
          		<p>集团为每一家子公司都赋予了核心使命，从产品研发到生产，从投资咨询到金融信息中介服务等事务上合理规划、周密安排，360度全方位拓展集团业务，使公司在多领域行业中良好有序地稳健发展。江苏子雨集团一贯坚持“以人为本、长效管理”的原则，秉承“产品优质、安全可靠、持续改进、顾客满意”的质量方针，以“永远追求卓越”这种不断突破创新的精神，探索新时期、新环境下的发展战略。</p>
            </div>
        	<div class="company_content_rg">
            	<p>江苏子雨集团有限公司成立于2010年3月，其前身—“栖霞纺织配件厂”成立于1991年，现总部位于江苏省南京市，集团下设六大板块，包括以江苏强业金融信息服务有限公司为代表的子雨科技金融版块；以句容振尧包装容器有限责任公司、南京振尧包装容器有限责任公司为代表的子雨工业制造版块；以山东序元环保科技有限公司为代表的子雨高新环保版块；以南京艾格智通农业科技有限公司作为代表的子雨智慧农业版块；以南京畅友路桥工程有限公司为代表的子雨路桥建设版块；以江苏叁拾柒号仓电子商务有限公司为代表的子雨人工智能版块。各家子公司分别在投资咨询、包装容器制造、机械制造、环保科技等范围开展业务，至此，江苏子雨集团有限公司初步完成了集团化、产业化布局，集团由传统的生产制造型企业完美过渡到以工业制造、互联网金融、环保科技为主要产业集群的企业集团公司。</p>
                <ul class="company_content_pic">
                	<li><img src="${staticSource}/static/front/images/pic02.jpg?2" width="166" height="166" /></li>
                    <li><img src="${staticSource}/static/front/images/pic03.jpg?2" width="166" height="166" /></li>
                    <li><img src="${staticSource}/static/front/images/pic04.jpg?2" width="166" height="166" /></li>
                </ul>
            </div>
      </div><!-- company_content end -->
      <div class="company_contentend">
      	在21世纪“中国梦”的感召下，江苏子雨集团高举民族企业大旗，传承民族文化、打造民族品牌、振兴民族产业，坚持以科学理念管理企业，打造子雨特色发展之路，集团将不断开拓市场，循序渐进，力争将江苏子雨集团打造成“公司有理念、人员有素养、市场有口碑、企业有前景、社会有影响”的中国一流企业。
      </div>
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
