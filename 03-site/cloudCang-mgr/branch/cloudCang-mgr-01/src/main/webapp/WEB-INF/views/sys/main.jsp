<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
<title>37号仓云管理平台</title>
<meta name="keywords" content="37号仓云管理平台" />
<meta name="description" content="37号仓云管理平台" />
<link href="${staticSource}/resources/css/override.css?2" rel="stylesheet">
</head>
<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
<div id="wrapper">
    <!--左侧导航开始-->
    <%@ include file="/common/include/left.jsp"%>
    <!--左侧导航结束-->
    <!--右侧部分开始-->
    <div id="page-wrapper" class="gray-bg" style="padding: 0 0;">
        <div class="wrapper-pisition">
            <%@ include file="/common/include/right-top.jsp"%>
        </div>
        <div class="J_mainContent" id="content-main" style="height: calc(100%); ">
            <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="${ctx}/mgr/index" frameborder="0" data-id="/mgr/index" seamless></iframe>
        </div>
        <%--<%@ include file="/common/include/footer.jsp"%>--%>
    </div>
    <!--右侧部分结束-->
</div>

<script src="${staticSource}/resources/hplus/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script type="text/javascript" src="${staticSource}/resources/layui/lay/modules/layer.js"></script>
<!-- 自定义js -->
<script src="${staticSource}/resources/hplus/js/hplus.js"></script>
<script type="text/javascript" src="${staticSource}/resources/hplus/js/contabs.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/pace/pace.min.js"></script>
<script type="text/javascript">
    //calTabsWidth();
    $(function () {
        $("#page-wrapper").css("padding-top",$(".wrapper-pisition").height()+"px");
    });
</script>
</body>

</html>
