<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <title>员工福利-人力资源-江苏子雨集团有限公司官网</title>
<meta name="keywords" content="江苏子雨集团员工福利" />
<meta name="description" content="江苏子雨集团除了为员工足额缴纳社会保险和住房公积金外，还有为丰富员工业余生活、缓解工作压力而设立的各项福利项目，包括健身休闲中心、培训拓展、旅游度假、员工食堂、春节年会等。" />
<link rel="stylesheet" type="text/css" href="${staticSource}/static/front/css/comm.css" />
<link rel="stylesheet" type="text/css"  href="${staticSource}/static/front/css/index.css" />
<style>
body{background:#f4f4f4;}
</style>
</head>	
<body>
<!-- 头部  -->
	<jsp:include page="/common/include/frontTop.jsp" />
	<!--    banner  -->
	<div class="ab_ban employee"></div>
    <div class="ab_top wd">
    	<div class="ab_tp_lf">
        	<i class="ab_icon ab_address"></i>您当前所在位置：<a href="${ctx}/cn/index.html">首页 ></a><a href="${ctx}/cn/hr/employee.html">人力资源 ></a> 员工福利
        </div>
        <div class="ab_tp_rg">
        	<ul>
            	<li class="title01"><a href="${ctx}/cn/hr/employee.html" class="hover">员工福利</a></li>
                <li><a href="${ctx}/cn/hr/education.html">人才战略</a></li>
                <li><a href="${ctx}/cn/hr/joinus.html">加入我们</a></li>
            </ul>
       </div>
    </div><!-- ab_top end-->
    <div class="ab_bg" style="padding-top:0px;position:relative">
   	   <div class="welfare_content">
   	   	   <img src="${staticSource}/static/front/images/welfare_pic.jpg" width="1133" height="480" />
           <img src="${staticSource}/static/front/images/benefits.png" class="ab_bgpic"/>
           <div class="welfare_intro">
           	   <a class="welfare_btn" href="javascript:void(0);" ><img src="${staticSource}/static/front/images/welfare_down.png" msrc="${staticSource}/static/front/images/welfare_up.png" /></a>
               <div class="welfare_txt">子雨集团坚持按劳分配与按生产要素分配相结合，树立全面薪酬观，建立了以岗位绩效工资制为主，年薪制、股权激励、分红激励等其他形式为补充的薪酬分配制度，并辅以企业年金、社会保险、住房公积金、休假旅游、节假日福利、健身休闲中心等福利保障项目。努力实现国际化高端人才的薪酬与国际标杆企业接轨，核心骨干人才的薪酬与发达地区劳动力市场价位接轨，其他人才的薪酬与当地劳动力市场价位接轨的薪酬管理目标。子雨始终关注员工的各项福利待遇，力争达到企业发展与员工发展的完美共赢。</div>
           </div> 
       </div>
    </div><!-- ab_bg end -->
    
	<!-- 底部 -->
	<jsp:include page="/common/include/frontBottom.jsp" />
    
<script type="text/javascript" src="${staticSource}/static/front/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/front/js/common.js"></script>
<script type="text/javascript" >
	$(".dj_active_list ul >li:nth-child(3n)").css("margin-right",0);
	function move(){
		$(".welfare_btn").animate({"top":"-40px"},300,"linear");
		$(".welfare_btn").animate({"top":"-30px"},600,"linear");
		$(".welfare_btn").animate({"top":"-20px"},300,"linear");
		
	}
	$(".welfare_btn").click(function(){
		var src,msrc;
		var src=$(this).find("img").attr("src");
		var msrc=$(this).find("img").attr("msrc");
		$(this).find("img").attr("src",msrc);
		$(this).find("img").attr("msrc",src);
		$(".welfare_txt").toggle();
    });
	
	setTimeout(move(),5);
	
</script>



</body>
</html>
