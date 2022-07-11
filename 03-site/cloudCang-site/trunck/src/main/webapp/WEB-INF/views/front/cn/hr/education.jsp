<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <title>人才战略-人力资源-江苏子雨集团有限公司官网</title>
<meta name="keywords" content="江苏子雨集团人才战略" />
<meta name="description" content="江苏子雨集团坚持坚持党管人才原则，牢固树立科学人才观，壮大人才队伍，提高人才素质，优化人才结构，完善用人机制，发挥人才作用，推进人才强国战略。" />
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
        	<i class="ab_icon ab_address"></i>您当前所在位置：<a href="${ctx}/cn/index.html">首页 ></a><a href="${ctx}/cn/hr/employee.html">人力资源 ></a> 人才战略
        </div>
         <div class="ab_tp_rg">
        	<ul>
            	<li><a href="${ctx}/cn/hr/employee.html">员工福利</a></li>
                <li class="title01"><a href="${ctx}/cn/hr/education.html" class="hover">人才战略</a></li>
                <li><a href="${ctx}/cn/hr/joinus.html">加入我们</a></li>
            </ul>
       </div>
    </div><!-- ab_top end-->
    <div class="ab_bg">
   	   <div class="welfare_content">
   	   	   <img src="${staticSource}/static/front/images/tactic_img.jpg" width="1066" height="420" />
   	   	    <a class="tactic_btn" href="javascript:void(0);" style="display:block"><img src="${staticSource}/static/front/images/welfare_down.png" msrc="${staticSource}/static/front/images/welfare_up.png" /></a>
           <div class="tactic_intro" style="display:none">
           	  
               <div class="tactic_txt" style="display:block">治国经邦，人才为急，子雨集团成立二十多年来，一直秉持“人才资源是第一生产力”的用人理念，把人才队伍建设作为企业发展壮大、基业长青的全局性、战略性和基础性工作。子雨集团大力实施“以人为本、规范管理”的人才战略，不断深化体制改革，全面加强高层管理人才队伍、党群工作者队伍、专业技术人才队伍建设。
子雨坚持以事业感召人才，以文化凝聚人才，知人善用，人尽其才。远大的企业目标、先进的管理体系、科学的激励机制，铸就了一支高学历、高素质的子雨铁军。子雨人崇高的理想、坚定的价值观和一往无前的执行力，形成了先进卓越的企业文化、戮力同心的工作氛围、超越完美的价值体系，成为公司屡创奇迹的强大推动力！</div>
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
		$(".tactic_btn").animate({"top":"-100px"},300,"linear");
		$(".tactic_btn").animate({"top":"-80px"},600,"linear");
		$(".tactic_btn").animate({"top":"-60px"},300,"linear");
		
	}
	$(".tactic_btn").click(function(){
		var src,msrc;
		var src=$(this).find("img").attr("src");
		var msrc=$(this).find("img").attr("msrc");
		$(this).find("img").attr("src",msrc);
		$(this).find("img").attr("msrc",src);
		$(".tactic_intro").toggle();
    });
	
	setTimeout(move(),5);
</script>



</body>
</html>
