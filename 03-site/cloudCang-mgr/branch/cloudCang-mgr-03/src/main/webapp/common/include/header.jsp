<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=10">
<link rel="shortcut icon" href="${staticSource}/ico/favicon_${sessionScope.SESSION_KEY_USER.smerchantCode}.ico" />
<link href="${staticSource}/resources/hplus/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/font-awesome.css?v=4.4.0" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/animate.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/style.css?v=5" rel="stylesheet">
<!--[if lt IE 9]>
<meta http-equiv="refresh" content="0;ie.html" />
<!-- 全局js -->
<script src="${staticSource}/resources/js/jquery.min.js?v=2.1.4"></script>
<script src="${staticSource}/resources/hplus/js/bootstrap.min.js?v=3.3.6"></script>
<script src="${staticSource}/resources/js/base-${currentLanguage}.js?7"></script>
<![endif]-->
<script type="text/javascript">
    var ctx='${ctx}';
    var rownum = 10;
    var rowList = [10, 20, 30, 50, 100, 200, 500, 1000];
    var isExport = false;
    var currRownum = 10;
    var uploadConfig = {
        elem: '#scommodityImgBut',
        fileSize : 200,
        auto: false,
        multiple: false,
        field: "scommodityImgfile",
        accept: 'images',
        exts: 'jpg'
    }
</script>



