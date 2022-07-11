<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <title>Qualifications&Honors-Jiangsu Ziyu Group</title>
<meta name="keywords" content="资质荣誉,集团荣誉,集团资质" />
<meta name="description" content="江苏子雨集团有限公司先后获得了南京电子商务协会理事单位、江苏省互联网金融协会会员单位、中国互联网金融协会会员单位等数项资质荣誉。" />
<link rel="stylesheet" type="text/css" href="${staticSource}/static/front/en/css/comm.css" />
<link rel="stylesheet" type="text/css"  href="${staticSource}/static/front/en/css/index.css" />
<link rel="stylesheet" type="text/css" href="${staticSource}/static/front/en/css/lightbox.css" >
<style>
body{background:#f4f4f4;}
.english-nav ul.nav-parent li a.hover3{color:#fff;background:#258beb;}
</style>
</head>	
<body>
	<!-- 头部  -->
	<jsp:include page="/common/include/enFrontTop.jsp" />
	<!--    banner  -->
	<div class="ab_ban"  style="background:url(${staticSource}/static/front/en/images/7.jpg) no-repeat" ></div>
    <div class="ab_bg" style="height:auto;">
       <div class="qualification_content overflow">
        <c:if test="${not empty page.results}">
       	  	<c:forEach items="${page.results}" var="item">
       	  		 <li>
		          	<a class="quanlification_pic" href="${item.scontentUrl}"  data-lightbox="list">
		               <img src="${item.sicoUrl}" width="326" height="243" title="查看原图" />
		            </a>
		            <p class="quanlification_txt">${item.sremark}</p>
          		</li>
       	  	
       	  	</c:forEach>
       	  
       	  
       	  </c:if>
       </div><!-- qualification_content end -->
       <ul class="list overflow">数据加载中，请稍后...</ul>
         <c:if test="${not empty page.results and fn:length(page.results) > 6}">
       		<div class="qualification_more"><a href="javascript:;" onClick="check.loadMore();">MORE</a></div>
       </c:if>
    </div><!-- ab_bg end -->
    
   <!-- 底部  -->
	<jsp:include page="/common/include/enFrontButtom.jsp" /> 
    
<script type="text/javascript" src="${staticSource}/static/front/en/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/front/en/js/lightbox.min.js"  ></script>
<script type="text/javascript" >
	$(".dj_active_list ul >li:nth-child(3n)").css("margin-right",0);
	// 二级导航条的选中样式
	$(".company_rg ul li").click(function(){
		$(this).addClass("current");
		$(this).siblings().removeClass("current");	
	});
	$(".company_content_pic >li:nth-child(3n)").css("margin-right",0);
	$(".team_member ul >li:nth-child(2n)").css("margin-right",0);
	
	//  点击加载更多
	var _content = []; //临时存储li循环内容
	var check = {
	     _default:6, //默认显示图片个数
	     _loading:${fn:length(page.results)},  //每次点击按钮后加载的个数
	init:function(){
		var lis = $(".ab_bg .qualification_content li");
		$(".ab_bg ul.list").html("");
		for(var n=0;n<check._default;n++){
			lis.eq(n).appendTo(".ab_bg ul.list");
		}
		$(".ab_bg ul.list li").each(function(){
			$(this).attr('src',$(this).attr('realSrc'));
		})
		for(var i=check._default;i<lis.length;i++){
			_content.push(lis.eq(i));
		}
		$(".ab_bg .qualification_content").html("");
		$(".list li:nth-child(3n)").css("margin-right",0);
	},
	loadMore:function(){
		var mLis = $(".ab_bg ul.list li").length;
		for(var i =0;i<check._loading;i++){
			var target = _content.shift();
			if(!target){
				$('.qualification_more').hide();
				$('.ab_bg .more').html("<p>全部加载完毕...</p>");
				break;
			}
			$(".ab_bg ul.list").append(target);
			$(".ab_bg ul.list li").eq(mLis+i).each(function(){
				$(this).attr('src',$(this).attr('realSrc'));
			});
			$(".list li:nth-child(3n)").css("margin-right",0);
		}
	}
  }
  check.init();

</script>



</body>
</html>
