<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>Chairs’ Statements-Jiangsu Ziyu Group</title>
<meta name="keywords" content="江苏子雨集团董事长致辞,江苏子雨集团董事长寄语" />
<meta name="description" content="子雨诞生在一个年代充满机遇与变革的时代，于六朝古都起步，立足南京，面向全国。辛勤耕耘和创业图强，铸就了子雨“自强不息 追求卓越”的企业精神。" />
<link rel="stylesheet" type="text/css" href="${staticSource}/static/front/en/css/comm.css" />
<link rel="stylesheet" type="text/css"  href="${staticSource}/static/front/en/css/index.css" />
<style>
body{background:#f4f4f4;}
.english-nav ul.nav-parent li a.hover1{color:#fff;background:#258beb;}
</style>
</head>	
<body>
	<!-- 头部  -->
	<jsp:include page="/common/include/enFrontTop.jsp" />
	<!--    banner  -->
	<div class="ab_ban" style="background:url(${staticSource}/static/front/en/images/2.jpg) no-repeat"></div>
    <div class="ab_bg">
	  <div class="president_content">
        <div class="president_content_lf">
            <h4><img src="${staticSource}/static/front/en/images/president_title.jpg" width="242" height="51" /></h4>
            <p>Jiangsu Zi Yu was established in 1990s, an era full of opportunities and changes. It started in the ancient capital of the Six Dynasties, and based on Nanjing, facing the whole country. Zi Yu Group has been in business for 27 years now, and along this road, every step of Zi Yu has gone unsteadily. From the initial start to the group management, Zi Yu followed the pace of the times, relying on technological innovation, efficient management and the reformation of policies, the adjustment of the product structure, vigorously expansion of sales channels and hard exploration of enterprise development way.</p>
            <p>In the context of the overall industrial upgrading brought by the "Internet +" entrepreneurship wave, it will be full of unlimited opportunities and challenges for Zi Yu Group. Since starting our business, we have always maintained the sense of innovation and crisis. We have never ceased to strive for perfection and pursue perfection. In order to create a fair competition and only a career platform, let the people with integrity and integrity realize their life value, Zi Yu Group has been working hard. At the same time, Zi Yu also creates greater economic value by continuously improving its business performance, and actively takes part in social welfare to shoulder more social responsibilities.</p>
             <p>At the present, Ziyu is a cross-section, cross-region and cross-ownership comprehensive enterprise group. The group has six major sections:Jiangsu Qiangye Financial Information Service Co., ltd. as the representative of Ziyu technology finance section, Jurong Zhenyao Packaging Container Co., Ltd. and Nanjing Zhenyao Packaging Container Co., Ltd. as the representative of Ziyu industrial manufacturing section, Shandong Xuyuan Environmental Science and Technology Co., Ltd. as the representative of Ziyu new and high environmental section, Nanjing Aige Zhitong Agricultural Science and Technology Co., Ltd. as the representative of Ziyu intelligent agriculture section, Nanjing Changyou Road and Bridge Engineering Co., Ltd. as the representative of Ziyu road and bridge construction section, Jiangsu No.37 Store Electronic Commerce Co., Ltd. as the representative of Ziyu artificial intelligence section. Our all achievements cannot leave the support of the people in all fields and the efforts of all the employees. Here, on behalf of Ziyu, I express my sincere respect and heartfelt thanks.</p>
             <p>During the future development, we will continue to forge ahead and change our idea. we will also comply with the trend of the times and seize the development opportunities. With excellent culture, mature management and innovative products, we will create a harmonious and beautiful tomorrow with all the staff and the friends .</p>
                
            <h5>Meng Lei</h5>
            <h5>Chairman of Jiangsu Ziyu Group</h5>
            <h5>January 1, 2018</h5>
        </div>
        <div class="president_content_rg"><img src="${staticSource}/static/front/en/images/president_img.jpg" width="447" height="663" /></div>
      </div><!-- president_content end -->
      <div class="honor_content">
      	<div class="honor_title"><img src="${staticSource}/static/front/en/images/honor_title.jpg" width="1144" height="37" /></div>
        <div class="honor_ab_wrap">
		  <div class="switcher-wrap slider">
			<a class="prev jQ_sliderPrev" href=""></a>
			<a class="next jQ_sliderNext" href=""></a>
		    <ul id="img-slider" style="height:320px;" >
		    	<c:if test="${not empty ads}">
		    		<c:forEach items="${ads}" var="ad">
		    			<li class="img">
							<img src="${ad.scontentUrl}" >
		                    <div class="label">${ad.stitle}</div>
						</li>
		    		</c:forEach>
		    	</c:if>
			</ul>
		  </div>
	    </div><!-- honor_ab_wrap -->
      </div><!-- honor_content end -->
    </div><!-- ab_bg end -->
    
   <!-- 底部  -->
	<jsp:include page="/common/include/enFrontButtom.jsp" /> 
    
<script type="text/javascript" src="${staticSource}/static/front/en/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/front/en/js/jquery.roundabout.js" ></script>
<script type="text/javascript" >
	$(document).ready(function(){
		$('#img-slider li').bind({
        reposition: function() {
            var degrees = $(this).data('roundabout').degrees,
                roundaboutBearing = $(this).parent().data('roundabout').bearing,
                rotateY = Math.sin((roundaboutBearing - degrees) * (Math.PI/180)) * 9;
        }
    });

    $('.jQ_sliderPrev').on('click', function(){
        $('#img-slider').roundabout('animateToNextChild');

        return false;
    });

    $('.jQ_sliderNext').on('click', function(){
        $('#img-slider').roundabout('animateToPreviousChild');

        return false;
    });

    $('body').on('keyup', function(e) {
        var keyCode = e.which || e.keyCode;

        if(keyCode == 37) {
            $('#img-slider').roundabout('animateToPreviousChild');
            e.preventDefault();
            return false;
        } else if(keyCode == 39) {
            $('#img-slider').roundabout('animateToNextChild');
            e.preventDefault();
            return false;
        }
    });

    $('.jQ_sliderSwitch li').on('click', function() {
        var $elem = $(this);
        var index = $elem.index();

        $('#img-slider').roundabout('animateToChild', index);

        return false;
    });

    $('#img-slider').roundabout({
        minScale: 0.1,
        maxScale: 0.8,
		minOpacity: 0.9,         //最小的透明度
        maxOpacity: 1,         //最大的透明度    
        duration: 750
    }).bind({
        animationEnd: function(e) {
            var index = $('#img-slider').roundabout('getChildInFocus');
            $('.jQ_sliderSwitch li').removeClass('active');
            $('.jQ_sliderSwitch li').eq(index).addClass('active');
        }
     });
	});
	
</script>



</body>
</html>
