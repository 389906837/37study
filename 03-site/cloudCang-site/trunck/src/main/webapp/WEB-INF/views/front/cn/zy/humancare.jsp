<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <title>人文关怀-江苏子雨集团有限公司官网</title>
<meta name="keywords" content="人文关怀,团队建设活动,团建活动" />
<meta name="description" content="江苏子雨集团除了为员工足额缴纳社会保险和住房公积金外，还有为丰富员工业余生活、缓解工作压力而设立的各项福利项目，包括健身休闲中心、培训拓展、旅游度假、员工食堂、春节年会等。" />
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
        	<i class="ab_icon ab_address"></i>您当前所在位置：<a href="${ctx}/cn/index.html">首页 ></a><a href="${ctx}/cn/zy/statements.html">子雨集团 ></a> 人文关怀
        </div>
        <!-- 子导航  -->
	<jsp:include page="/common/include/zyTitle.jsp" />
	<script>
		$('div.company_rg').find('li').eq(7).addClass('current');
	</script>
    </div><!-- ab_top end-->
    <div class="ab_bg">
       <div class="cultural_content overflow">
	     <p style="text-indent:2em">在子雨集团，人文关怀一直是企业价值观建设的重要组成部分，是内部管理不可或缺的关键因素。在运营过程中，子雨集团更是将人文关怀落在了实处。除了为员工足额缴纳社会保险和住房公积金外，还有为丰富员工业余生活、缓解工作压力而设立的各项福利项目，包括健身休闲中心、培训拓展、旅游度假、员工食堂、春节年会等。在重要节假日，公司不仅为员工准备丰厚的节日礼金，还制作特色中秋手工糕点、万圣节糖果进行发放，举办圣诞节庆典等特色活动，营造
       </p> 
        <img src="${staticSource}/static/front/images/cultural_pic.jpg" width="585" height="498" />
        <p style="padding-bottom:40px;">轻松、温馨的工作氛围，体现公司对员工细致入微的关怀和以人为本的经营理念；员工结婚、生育等人生大事，公司也会给予特别礼金祝贺；在员工生日之际，公司会发放生日礼金；女性员工在三八妇女节享受特殊节日福利。除此之外，公司更斥资几十万元作为专项资金，作为子雨员工的团体活动经费。在2016年10月份，子雨集团正式成立工会委员会，着力维护员工权益，进一步关爱员工。</p>
        <p style="text-indent:2em">子雨集团的人文关怀，不仅给予了内部员工以凝聚力和幸福感，也在社会公益事业上体现出厚重的社会责任感和博爱情怀。子雨集团成立二十多年来，一直致力于社会公益事业，先后开展“扶贫帮困送温暖”系列活动，帮扶困难家庭；集团成立了“子雨教育基金”，对多家教学机构进行捐资助学，受捐者高达几千人次；集团与南京宁馨阳光家园成为公益合作伙伴，成立“互公益”爱心基金，同时子雨员工组成志愿者小分队，走近残障儿童、关爱特殊群体。在2016年南京高淳、八卦洲两地的抗洪救灾中，子雨集团积极捐赠物资，帮助灾区群众暂渡难关。子雨集团一直以来对公益事业不遗余力，却低调内敛、无意宣传，将“上善若水、大爱无疆”的哲学发挥到淋漓尽致，赋予了企业深厚的精神底蕴和人文魅力。</p> 
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
	$(".team_member ul >li:nth-child(2n)").css("margin-right",0);

</script>



</body>
</html>

