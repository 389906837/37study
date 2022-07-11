<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>董事长致辞-江苏子雨集团有限公司官网</title>
<meta name="keywords" content="江苏子雨集团董事长致辞,江苏子雨集团董事长寄语" />
<meta name="description" content="子雨诞生在一个年代充满机遇与变革的时代，于六朝古都起步，立足南京，面向全国。辛勤耕耘和创业图强，铸就了子雨“自强不息 追求卓越”的企业精神。" />
<link rel="stylesheet" type="text/css" href="${staticSource}/static/front/css/comm.css" />
<link rel="stylesheet" type="text/css"  href="${staticSource}/static/front/css/index.css" />
<script type="text/javascript" src="${staticSource}/static/front/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/front/js/common.js"></script>
<script type="text/javascript" src="${staticSource}/static/front/js/jquery.roundabout.js"></script>
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
        	<i class="ab_icon ab_address"></i>您当前所在位置：<a href="${ctx}/cn/index.html">首页 ></a><a href="${ctx}/cn/zy/statements.html">子雨集团 ></a> 董事长致辞
        </div>
      <!-- 子导航  -->
	<jsp:include page="/common/include/zyTitle.jsp" />
	<script>
		$('div.company_rg').find('li').eq(0).addClass('current');
	</script>
    </div><!-- ab_top end-->
    <div class="ab_bg">
	  <div class="president_content">
        <div class="president_content_lf">
            <h4><img src="${staticSource}/static/front/images/president_title.jpg" width="242" height="51" /></h4>
            <p>江苏子雨诞生在20世纪90年代，一个充满机遇与变革的时代，于六朝古都起步，立足南京，面向全国。集团从创业至今已有27年，这一路走来，子雨的每一步都走得异常坚定。从初期创业到实现集团化经营，子雨都紧跟时代的步伐，依靠技术创新、高效管理和制度改革，大胆进行产品结构调整，大力拓宽产品销售渠道，努力探索企业发展之路。</p>
            <p>在当今这“互联网＋”创业大潮带来的全面产业升级的背景下，更是充满着无限机遇与挑战。从创业至今，我们时刻保持着创新和危机意识，精益求精、追求完美的脚步从未停歇。为了创建一个公平竞争和惟才是举的事业平台，让德才兼备的人实现人生价值，子雨集团一直在努力！与此同时，子雨还通过不断提升经营业绩来创造更大的经济价值，积极投身社会公益来承担更多的社会责任。</p>
            <p>今天的子雨集团，已发展成为跨行业、跨地区、跨所有制的综合性企业集团，集团下设六大板块，包括以江苏强业金融信息服务有限公司为代表的子雨科技金融版块；以句容振尧包装容器有限责任公司、南京振尧包装容器有限责任公司为代表的子雨工业制造版块；以山东序元环保科技有限公司为代表的子雨高新环保版块；以南京艾格智通农业科技有限公司作为代表的子雨智慧农业版块；以南京畅友路桥工程有限公司为代表的子雨路桥建设版块；以江苏叁拾柒号仓电子商务有限公司为代表的子雨人工智能版块。所有这些成就，离不开社会各界人士的鼎力相助，更离不开几代子雨人的努力拼搏，没有你们的肝胆相照和忘我付出，就没有今天的子雨，在此，我代表子雨集团向你们致以崇高的敬意和衷心的感谢！</p>
            <p>在未来的发展中，我们将继续开拓进取、转变观念，顺应时代潮流，抓住发展机遇，以优秀的文化、成熟的管理、创新的产品，与全体子雨同仁和各界友人一道，共图繁荣前景，共创美好明天！</p>
            <h5>江苏子雨集团董事长 孟雷</h5>
            <h5>2018年1月1日</h5>
        </div>
        <div class="president_content_rg"><img src="${staticSource}/static/front/images/president_img.jpg" width="447" height="663" /></div>
      </div><!-- president_content end -->
      <div class="honor_content">
      	<div class="honor_title"><img src="${staticSource}/static/front/images/honor_title.jpg" width="1144" height="37" /></div>
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
