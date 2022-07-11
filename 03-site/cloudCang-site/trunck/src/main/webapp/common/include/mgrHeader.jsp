<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>后台管理系统-子雨集团官网</title>

    <!-- Bootstrap Core CSS -->
    <link href="${staticSource}/static/template/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="${staticSource}/static/template/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="${staticSource}/static/template/dist/css/sb-admin-2.css" rel="stylesheet">
  
    <!-- Custom Fonts -->
    <link href="${staticSource}/static/template/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
   
     <!-- jQuery -->
    <script src="${staticSource}/static/template/vendor/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="${staticSource}/static/template/vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="${staticSource}/static/template/vendor/metisMenu/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="${staticSource}/static/template/dist/js/sb-admin-2.js"></script>
    
     <!-- bootstrapValidator -->
    <script src="${staticSource}/static/js/bootstrapValidator.min.js"></script>
    <script src="${staticSource}/static/js/zh_CN.js"></script>
    <link href="${staticSource}/static/css/bootstrapValidator.css" rel="stylesheet">
    
    <script src="${staticSource}/static/js/jquery-confirm.min.js"></script>
    <link href="${staticSource}/static/css/jquery-confirm.min.css" rel="stylesheet" type="text/css">
    
    <link href="${staticSource}/static/css/treeView.css" rel="stylesheet">
    <link href="${staticSource}/static/css/style.css?20161213" rel="stylesheet">
    <script src="${staticSource}/static/js/common.js?20161214"></script>
    
   

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>
<!--===================通用弹出层开始============================-->
 <!--通用弹出DIV-->
<div id="commonPopupDiv" class="popupLayer" style="display:none;width:460px;height:310px;" >
	<div class="commonPopupTitle">
		<div style="float:left" id="popTitle"></div><div style="float:right;font-size:20px;cursor:pointer"><div onclick="closeCommonDiv()">&nbsp;×&nbsp;</div></div>
	</div>
  	<iframe name="commonPopupDivFrm" clearindex="" splitindex="" stype="1" lookupid="" id="commonPopupDivFrm" src="" width="100%" height="92%" frameborder="0"  scrolling="no"></iframe>
</div>
<!--lookup弹出DIV-->
	<div id="lookUpDiv" class="popupLayer" style="display:none;width:460px;height:420px;cursor:move;overflow:hidden;"  >
		<iframe name="lookUpDivFrm" clearindex="" splitindex="" stype="1" lookupid="" id="lookUpDivFrm" src="" width="100%"  height="100%" frameborder="0" allowTransparency="true"  scrolling="no"></iframe>
	</div>

<!--通用弹出树目录的DIV-->
<div id="commonTreeViewDiv" class="popupLayer" style="cursor:move;display:none;width:300px;height:360px;" >
  	<iframe name="commonTreeViewDivFrm" id="commonTreeViewDivFrm" src="" width="100%" height="100%" frameborder="0" allowTransparency="true"  scrolling="no"></iframe>
</div>
<!--弹出层遮蔽层-->
<div id="commonRoofLayer" class="roofLayer"></div>
 <!--===================通用弹出层结束============================-->