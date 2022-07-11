<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>云识别平台首页</title>
<link href="${staticSource}/resources/layui/css/layui.css?1" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?1.2" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${staticSource}/resources/css/comm.css?3"/>
<link rel="stylesheet" type="text/css" href="${staticSource}/resources/css/index.css?1.1"/>
<!--Bootstrap双日历 -->
<link rel="stylesheet" type="text/css" href="${staticSource}/resources/css/daterangepicker.css"/>
<style>
    html,body{width: 100%; height: 100%;  }
    .main{width:100%;height: 100%;min-width: 1065px;min-height: 770px;background-color: #FFFFFF;display: flex;justify-content: center;align-items: center;  }
    .homebg{width: 1065px;height: 770px;background-image: url(${staticSource}/resources/images/homebg.png);background-repeat:no-repeat;background-position: center center;  background-color: #FFFFFF;  text-align: center;  position: relative;  }
    .homebg div{  position: absolute; left: 33%; top: 35%;  }
    .p2{  padding-top: 20px;color: #e14e4e;font-size: 32px;}
</style>
<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="main">
                    <div class="homebg">
                        <div>
                            <p class="p1"><img src="${staticSource}/resources/images/logo.png"></p>
                            <p class="p2">欢迎使用37号仓智能云识别平台</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%--<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="main">
                   &lt;%&ndash; <div class="homebg">
                        <div>
                            <p class="p1"><img src="${staticSource}/resources/images/logo.png"></p>
                            <p class="p2">欢迎使用云识别管理平台</p>
                        </div>
                    </div>&ndash;%&gt;
                </div>
            </div>
        </div>
    </div>
</div>--%>
<!--bootstrap双日历插件 -->
<script type="text/javascript" src="${staticSource}/resources/js/moment.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/daterangepicker.js"></script>

<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/jsrender.min.js" type="text/javascript"></script>
<script src="${staticSource}/resources/js/jsrender.converters.js" type="text/javascript"></script>

<!--echarts-->
<script src="${staticSource}/resources/js/echarts/esl.js" type="text/javascript"></script>
<script src="${staticSource}/resources/js/echarts/echarts.js" type="text/javascript"></script>
<script src="${staticSource}/resources/js/WapCharts.js" type="text/javascript"></script>
<script src="${staticSource}/resources/js/MyEcharts.js?2" type="text/javascript"></script>
<script src="${staticSource}/resources/js/base.js" type="text/javascript"></script>

<script type="text/javascript">
</script>
</body>
</html>