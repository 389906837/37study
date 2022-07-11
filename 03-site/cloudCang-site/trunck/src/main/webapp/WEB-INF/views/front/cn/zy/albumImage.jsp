<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   <title>${album.sname}-企业文化-江苏子雨集团有限公司相册</title>
<link rel="stylesheet" type="text/css" href="${staticSource}/static/front/css/comm.css" />
<link rel="stylesheet" type="text/css"  href="${staticSource}/static/front/css/index.css" />
<script type="text/javascript" src="${staticSource}/static/front/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/front/js/common.js"></script>
<script type="text/javascript" src="${staticSource}/static/front/js/jquery.roundabout.js" ></script>
<style >
body{background:#f4f4f4;}
#eject{background: #000 url("about:blank") repeat scroll 0 0;left: 0;opacity: 0.9;filter:alpha(opacity=90); position: fixed;top: 0;width: 100%;height:100%;z-index: 1001;display:none;min-width:1200px;}
</style>
</head>	
<body>
<!-- 头部  -->
	<jsp:include page="/common/include/frontTop.jsp" />
	<!--    banner  -->
	<div class="ab_ban statements"></div>
    <div class="ab_top wd">
    	<div class="ab_tp_lf" style="padding-bottom:20px;width:100%">
        	<i class="ab_icon ab_address"></i>您当前所在位置：<a href="${ctx}/cn/index.html">首页 ></a><a href="${ctx}/cn/zy/statements.html">子雨集团 ></a><a href="${ctx}/cn/zy/culture.html">企业文化 ></a> ${album.sname}
        </div>
    </div><!-- ab_top end-->
    <div class="ab_bg">
		<div class="album-pic overflow">
			<div class="overflow-left album-picleft">
				<img src="${album.coverimg}" style="width:100px;height:100px;"/>
			</div>
			<div class="overflow-left album-picright">
				<h2>${album.sname}<span>${album.icount}张/<f:formatDate value="${album.taddTime }" pattern="yyyy-MM-dd"/><span></h2>
				<p>${album.remark}</p>
			</div>
		</div>
     	<div class="album-picontent">
     		<ul>
     		<c:if test="${not empty  albumimgs}">
						<c:forEach items="${albumimgs}" var="item">
						<li>
		     				<img src="${item.contenturl}" bgsrc="${item.contenturl}" mdsrc="${item.contenturl}" style="width:160px;height:120px;"/>
		     				<p>${item.desc }</p>    				
     					</li>
	     			</c:forEach>
					</c:if>
     			
     			
     		</ul>
     	</div>
    </div><!-- ab_bg end -->
   
 <!-- 底部 -->
<jsp:include page="/common/include/frontBottom.jsp" />
	<!--   图片弹出框  -->
	<div id="eject"></div>
	<div class="picpop">
		<div class="picpop-pic">
		<div class="wfclose"></div>
			<div class="picpop-picleft"></div>
			<div class="picpop-picmid">
				<img src="images/team_pic_liu.jpg"/>
			</div>
			<div class="picpop-picright"></div>
		</div>
		<div class="picpnum">《${album.sname}》 <span>1</span>/<em>${album.icount}</em></div>
		<div class="picpop-picsun">
			<div class="picpop-picsunleft"></div>
			<div class="picpop-picsunmid">
				<ul>
					<c:if test="${not empty  albumimgs}">
						<c:forEach items="${albumimgs}" var="item">
		     			 <li>
							<img src="${fn:replace(item.contenturl, '_.', '_160x160.')}" style="width:80px;height:80px;"/>				
						  </li>
	     			</c:forEach>
					</c:if>
     			</ul>
			</div>
			<div class="picpop-picsunright"></div>
		</div>
	</div>
    
<script type="text/javascript">
	$(window).load(function(){
		$(".picpop").each(function(i){
			var widWidth=document.documentElement.clientWidth; 
			var widHight=document.documentElement.clientHeight; 
			var onwidth=$(".picpop").eq(i).outerWidth();
			var onhight=$(".picpop").eq(i).outerHeight();
			$(".picpop").eq(i).css("left",(widWidth-onwidth)/2);
			$(".picpop").eq(i).css("top",(widHight-onhight)/2);
		});
		
		$(window).resize(function(){
			$(".picpop").each(function(i){
				var widWidth=document.documentElement.clientWidth; 
				var widHight=document.documentElement.clientHeight; 
				var onwidth=$(".picpop").eq(i).outerWidth();
				var onhight=$(".picpop").eq(i).outerHeight();
				$(".picpop").eq(i).css("left",(widWidth-onwidth)/2);
				$(".picpop").eq(i).css("top",(widHight-onhight)/2);
			});
		});
		
		var liWidth=$(".picpop-picsunmid ul li").outerWidth(true);//li的宽度
		var ulWidth=$(".picpop-picsunmid ul").outerWidth(true);//ul的宽度
		var linum = $(".picpop-picsunmid ul").find("li").length;//li的个数
		var pmun=linum/11;//总频数
		var n = 0,m=0,inDex;
		function marginRight(){
			if(linum>11){
				$(".picpop-picsunleft").removeClass("picpop-picsunlt");
				n=n+1;
				midnum=-(n*11*liWidth);
				midWidth=n*11*liWidth+ulWidth;
				if(n*11>linum){
					n=m;
				}
				else if(n*11<linum){
					$(".picpop-picsunmid ul").animate({width : midWidth,marginLeft : midnum}, 100);
					m++;
					if(n==Math.ceil(linum/11)-1){
						$(".picpop-picsunright").addClass("picpop-picsunrt");
					}
				}
			}

		};
		function marginLeft(){
			if(linum>11){
				$(".picpop-picsunright").removeClass("picpop-picsunrt");
				m=0;
				n=n-1;
				if(n==0){
					$(".picpop-picsunleft").addClass("picpop-picsunlt");
				}
				midnum=-(n*11*liWidth);
				midWidth=n*11*liWidth+ulWidth;
				if(n*11<0){
					
					n=0;
				}
				else if(n*11 < linum && n*11>=0){
					$(".picpop-picsunmid ul").animate({width : midWidth,marginLeft : midnum}, 100);
					
					
				}
			}

		};
		$(".picpop-picsunleft").click(function(){
			marginLeft();
		});
			$(".picpop-picsunright").click(function(){
				marginRight();
		});
		/* 弹出框点击小图 */
		$(".picpop-picsunmid ul li").click(function(){
			inDex=$(".picpop-picsunmid ul li").index(this);
			$(".picpnum span").html(inDex+1);
			$(this).addClass("hover");
			$(this).siblings().removeClass("hover");
			$(".picpop-picmid img").attr("src",$(".album-picontent ul li img").eq(inDex).attr("bgsrc"));
			$(".picpop-picmid img").removeAttr("style");
			pict();
			if(inDex==0){
				$(".picpop-picleft").addClass("picpop-picleft1");
				$(".picpop-picright").removeClass("picpop-picright1");
			}
			if(inDex==linum-1){
				$(".picpop-picright").addClass("picpop-picright1");
				$(".picpop-picleft").removeClass("picpop-picleft1");
			}
			else if(inDex>0 && inDex<linum){
				$(".picpop-picleft").removeClass("picpop-picleft1");
				$(".picpop-picright").removeClass("picpop-picright1");
			}
		});

		$(".album-picontent ul li img").click(function(){
			$("#eject").show();
			$(".picpop").addClass("picpopp");
			inDex=$(".album-picontent ul li img").index(this);
			$(".picpnum span").html(inDex+1);
			
			if((inDex+1)%11==0)
			{
				n=parseInt((inDex+1)/11)-2;
				marginRight();
			}
			else {
				n=parseInt((inDex+1)/11)-1;
				marginRight();
			}
			if(inDex+1<=11)
			{	$(".picpop-picsunleft").addClass("picpop-picsunlt");//按钮变灰色
				if(inDex==0){
					$(".picpop-picleft").addClass("picpop-picleft1");//按钮变灰色
					}
				
			}
			 if(inDex+12>=linum)
			{	if(inDex==linum-1){
				$(".picpop-picright").addClass("picpop-picright1");//按钮变灰色
				$(".picpop-picsunright").addClass("picpop-picsunrt");//按钮变灰色
				}
				else if(Math.ceil((inDex+1)/11)==Math.ceil(linum/11)){
					$(".picpop-picsunright").addClass("picpop-picsunrt");//按钮变灰色
				}
			}
			
			$(".picpop-picmid img").attr("src",$(this).attr("bgsrc"));
			$(".picpop-picsunmid ul li").eq(inDex).addClass("hover")
			$(".picpop-picsunmid ul li").eq(inDex).siblings().removeClass("hover");
			$(".picpop-picmid img").removeAttr("style");
			pict();			
		});			
		$(".picpop-picleft").click(function(){
			if(linum==1)
			{}
			else{
				$(".picpop-picright").removeClass("picpop-picright1");//按钮去灰色
			}
			inDex--;	
			if(inDex>=0){
				$(".picpnum span").html(inDex+1);
				if(inDex+1<=11)
					{	if(inDex==0){
							$(".picpop-picleft").addClass("picpop-picleft1");//按钮变灰色
						}
						$(".picpop-picsunleft").addClass("picpop-picsunlt");//按钮变灰色
					}
					else if(inDex+12>=linum)
					{	if(inDex==linum-1){
						$(".picpop-picright").addClass("picpop-picright1");//按钮变灰色
						}
						$(".picpop-picsunright").addClass("picpop-picsunright1");//按钮变灰色
					}
				$(".picpop-picsunmid ul li").eq(inDex).addClass("hover");
				$(".picpop-picsunmid ul li").eq(inDex).siblings().removeClass("hover");
				$(".picpop-picmid img").attr("src",$(".album-picontent ul li img").eq(inDex).attr("bgsrc"));
				$(".picpop-picmid img").removeAttr("style");
				pict();
				if((inDex+1)%11==0)
					{
						n=parseInt((inDex+1)/11);
						marginLeft();
					}
					else {
						n=parseInt((inDex+1)/11)+1;
						
						marginLeft();
					}
				}
			else{
				inDex=0;			
			}			
		});
		$(".picpop-picright").click(function(){
			if(linum==1)
			{}
			else{
				$(".picpop-picleft").removeClass("picpop-picleft1");//按钮去灰色
			}
			
			inDex++;
			if(inDex<=linum-1){
				$(".picpnum span").html(inDex+1);
				if(inDex+1<=11)
					{	if(inDex==0){
							$(".picpop-picleft").addClass("picpop-picleft1");//按钮变灰色
						}
						$(".picpop-picsunleft").addClass("picpop-picsunlt");//按钮变灰色
					}
					if(inDex+12>=linum)
					{	if(inDex==linum-1){
						$(".picpop-picright").addClass("picpop-picright1");//按钮变灰色
						}
						//$(".picpop-picsunright").addClass("picpop-picsunright1");//按钮变灰色
					}
				$(".picpop-picsunmid ul li").eq(inDex).addClass("hover");
				$(".picpop-picsunmid ul li").eq(inDex).siblings().removeClass("hover");
				$(".picpop-picmid img").attr("src",$(".album-picontent ul li img").eq(inDex).attr("bgsrc"));
				
				$(".picpop-picmid img").removeAttr("style");
				pict();
				if((inDex+1)%11==0)
				{
					n=parseInt((inDex+1)/11)-2;
					marginRight();
				}
				else {
					n=parseInt((inDex+1)/11)-1;
					marginRight();
				}
			}
			else{
					inDex=linum-1;
			}
		});	

		/*    大图处理  */
		
		function pict(){
		var bgpicHight=$(".picpop-picmid img").height();
			
			
				$(".picpop-picmid img").css("margin-top",(500-bgpicHight)/2);
		}	

		$(".wfclose").click(function(){
			$("#eject").hide();
			$(".picpop").removeClass("picpopp");
			n = 0;m=0;inDex=0;
		});
	});
</script>
</body>
</html>


