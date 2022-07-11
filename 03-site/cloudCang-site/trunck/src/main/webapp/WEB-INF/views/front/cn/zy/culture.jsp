<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <title>企业文化-江苏子雨集团有限公司官网</title>
<meta name="keywords" content="企业文化,企业精神,企业愿景,企业使命" />
<meta name="description" content="打造子雨企业核心竞争力，成就百年子雨基业，以培养和造就人才为己任，实现员工与企业共同发展，提供客户满意的产品，以企业财富的不断积累惠及广大的利益相关者。" />
<link rel="stylesheet" type="text/css" href="${staticSource}/static/front/css/comm.css" />
<link rel="stylesheet" type="text/css"  href="${staticSource}/static/front/css/index.css" />
<script type="text/javascript" src="${staticSource}/static/front/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/front/js/common.js"></script>
<script type="text/javascript" src="${staticSource}/static/front/js/jquery.roundabout.js" ></script>
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
        	<i class="ab_icon ab_address"></i>您当前所在位置：<a href="${ctx}/cn/index.html">首页 ></a><a href="${ctx}/cn/zy/statements.html">子雨集团 ></a> 企业文化
        </div>
          <!-- 子导航  -->
	<jsp:include page="/common/include/zyTitle.jsp" />
	<script>
		$('div.company_rg').find('li').eq(6).addClass('current');
	</script>
    </div><!-- ab_top end-->
    <div class="ab_bg">
		<div class="wfculture overflow">
			<div class="overflow-left wfculture-left">
				<img src="${staticSource}/static/front/images/culture.jpg"/>
				<div class="wfculture-content">
					<h2 class="yellow">企业精神：自强不息  追求卓越</h2>
					<p>塑造自尊自强、自省自励，坚韧执着的优秀品格。形成拒绝平庸、永不自满，超越自我的人生态度。</p>
					<h2 class="blue">企业愿景：立足江苏  勇于奉献</h2>
					<p>以江苏区域为事业发展的根据地，实现企业又好又快发展，探索积累成功的运作模式，打造子雨品牌，将企业发展壮大，以能为当地经济、社会、文化发展做出贡献而自豪！</p>
					<h2 class="red">企业使命：强企报国  泽被大众</h2>
					<p> 打造子雨企业核心竞争力，成就百年子雨基业；崇尚产业报国的理想，以企业的持续发展回馈社会、报效国家；以培养和造就人才为己任，实现员工与企业共同发展；倡导新的生活理念，提供客户满意的产品，以企业财富的不断积累惠及广大的利益相关者。</p>
				</div>
				<div class="wfculture-imgbottom overflow">
					<img src="${staticSource}/static/front/images/culture1.jpg" class="overflow-right"/>
				</div>
			</div>
			<div class="overflow-right wfculture-right">
				<img src="${staticSource}/static/front/images/culture3.jpg" class="img-position" style="left:-25px;"/>
				<img src="${staticSource}/static/front/images/culture2.jpg" class="img-responsive"/>
				<img src="${staticSource}/static/front/images/culture3.jpg" class="img-position" style="right:0px;"/>
			</div>
		</div> 
     
    </div><!-- ab_bg end -->
    <div class="album">
    	<ul>
    		<c:if test="${not empty albums }">
    			<c:forEach items="${albums}" var="item">
    				<li>
		    			<a href="${ctx}/cn/zy/albumImage/${item.id}.html">
		    				<img src="${item.coverimg}"/>
		    				<span>${item.sname }</span>
		    				<em>${item.icount}</em>
		    			</a>	
    				</li>
    			</c:forEach>
    		</c:if>
    	</ul>
    </div>
    <script type="text/javascript">
    	var tnum=$(".album ul li").length;
    	if(tnum>0)
    		{
    		
    		}
    	else{
    		$(".album").hide();
    	}
    </script>
 <!-- 底部 -->
<jsp:include page="/common/include/frontBottom.jsp" />
</body>
</html>
