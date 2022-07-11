<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <title>加入我们-人力资源-江苏子雨集团有限公司官网</title>
<meta name="keywords" content="江苏子雨集团招聘信息,江苏子雨集团工资待遇" />
<meta name="description" content="江苏子雨集团坚信，与优秀的人才携手并肩才能打造成功的未来。我们愿尽最大的努力在充满希望与想象的互联网金融行业为我们的英才们提供广阔的舞台。" />
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
        	<i class="ab_icon ab_address"></i>您当前所在位置：<a href="${ctx}/cn/index.html">首页 ></a><a href="${ctx}/cn/hr/employee.html">人力资源></a> 加入我们
        </div>
        <div class="ab_tp_rg">
        	<ul>
            	<li><a href="${ctx}/cn/hr/employee.html">员工福利</a></li>
                <li><a href="${ctx}/cn/hr/education.html">人才战略</a></li>
                <li class="title01"><a href="${ctx}/cn/hr/joinus.html" class="hover">加入我们</a></li>
            </ul>
       </div>
    </div><!-- ab_top end-->
    <div class="ab_bg">
   	   <div class="joinus_content">
       	  <div class="joinus_lf">
	       	  <c:if test="${not empty vos}">
	       	  	<c:forEach items="${vos}" var="item">
	       	  		<div class="joinus_list">
	            	<div class="joinus_title">
	               	    <div class="joinus_title_top">
	                        <h4>${item.stitle }</h4>
	                        <div class="overflow-left joinus_address"><i class="ab_icon address_icon"></i>工作地：${item.ssubjoinTitle}</div>
	                        <div class="overflow-right">
	                        	<a href="javascript:void(0);" class="joinus_btn"><img src="${staticSource}/static/front/images/join_us_down.png" msrc="${staticSource}/static/front/images/join_us_up.png" /></a>
	                        </div>
	                    </div>
	                    <p>${item.snewsAbstract }</p>
	                </div>
	                <div class="joinus_txt">
	                 ${item.contents}
	               	  <!--<h4>岗位职责：</h4>
	                   <p>1、协助公司制定中长期的年度业务发展目标及计划，以及各阶段的业务目标分解工作实施与推进；跟踪公司经营目标达成情况，提供分析意见及改进建议；</p>
	                  <p>2、负责参加各类重大会议和活动的组织、策划，记录会议纪要，问题跟踪与反馈，并进行相关文件汇总及存档；</p>
	                  <p>3、配合处理外部机关或客户等的公共关系；</p>
	                  <p>4、负责协调各部门做好公司各类接待工作；</p>
	                  <p>5、完成临时安排或突发性的工作。</p>
	                  <h4>任职资格：</h4>
	                  <p>1、协助公司制定中长期的年度业务发展目标及计划，以及各阶段的业务目标分解工作实施与推进；跟踪公司经营目标达成情况，提供分析意见及改进建议；</p>
	                  <p>2、负责参加各类重大会议和活动的组织、策划，记录会议纪要，问题跟踪与反馈，并进行相关文件汇总及存档；</p>
	                  <p>3、配合处理外部机关或客户等的公共关系；</p>
	                  <p>4、负责协调各部门做好公司各类接待工作；</p>
	                  <p>5、完成临时安排或突发性的工作。</p> -->
	               </div>
	            </div><!-- joinus_list end -->
	       	  	</c:forEach>
		       	  </c:if>

           
         </div><!-- joinus_lf end  -->
          <div class="joinus_rg"><img src="${staticSource}/static/front/images/join_us_pic.jpg" width="331" height="560" /></div>
       </div><!-- joinus_content end -->
    </div><!-- ab_bg end -->
    
	<!-- 底部 -->
	<jsp:include page="/common/include/frontBottom.jsp" />
    
<script type="text/javascript" src="${staticSource}/static/front/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/front/js/common.js"></script>
<script type="text/javascript" >
	$(".joinus_btn").click(function(){
		var src,msrc;
		var src=$(this).find("img").attr("src");
		var msrc=$(this).find("img").attr("msrc");
		$(this).find("img").attr("src",msrc);
		$(this).find("img").attr("msrc",src);
		$(this).parents('.joinus_list').find(".joinus_txt").slideToggle();
    });
</script>



</body>
</html>
