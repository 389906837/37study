<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
<title>商品分类</title>
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
<style type="text/css">
    #content-main {overflow: hidden;}

</style>
</head>

<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
<div id="wrapper" style="background:none">
    <!--<spring:message code="sp.category.left.navigation.starts" />-->
    <nav class="navbar-default navbar-static-side navbar-defaultbg" style="margin-top:20px;margin-left:3px;" role="navigation">
        <div class="sidebar-collapse">
            <div class="nav" id="side-menu">

            </div>
        </div>
    </nav>
    <!--<spring:message code="sp.category.left.navigation.ends" />-->
    <!--<spring:message code="sp.category.the.right.part.begins" />-->
    <div id="page-wrapper" style="padding:0 !important">
        <iframe class="J_iframe" name="iframe0" src="${ctx}/commodity/page" width="100%" height="100%" frameborder="0" scrolling="no"></iframe>
    </div>
    <!--<spring:message code="sp.category.end.of.the.right.part" />-->
</div>
<script src="${staticSource}/resources/hplus/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<!-- <spring:message code="sp.category.customize" />js -->
<script src="${staticSource}/resources/hplus/js/hplus.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/treeview/bootstrap-treeview.js"></script>
<script type="text/javascript">
    $(function () {
       $.ajax({
            type: "post",
            url: "${ctx}/commodity/category/getCategoryMenu",
            dataType: "json",
            success: function (result) {
                $('#side-menu').treeview({
                    data: result,         // 数据源
                    highlightSelected: true,    //是否高亮选中
                    showIcon:false,
                    multiSelect: false,    //多选
                    selectedColor :'#FFB800',
                    levels : 1,
                    onNodeSelected: function (event, data) {
                        selectPur(data.href, data.sparentId);
                    }
                });
            }
        });
    });
    function selectPur(categoryId,sparentId) {
        iframe0.window.reloadData(categoryId,sparentId);
    }
</script>
</body>

</html>
