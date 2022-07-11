<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>用户权限</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet"/>
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>用户权限</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="suserName" id="suserName" value="" placeholder="用户名..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="srealName" id="srealName" value="" placeholder="姓名..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="smobile" id="smobile" value="" placeholder="手机号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sroleName" id="sroleName" value="" placeholder="角色名..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="spurName" id="spurName" value="" placeholder="权限名..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="spurCode" id="spurCode" value="" placeholder="权限码..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i
                                        class="layui-icon">&#xe615;</i>查询
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i
                                        class="layui-icon">&#x1006;</i>清除
                                </button>
                            </div>
                        </div>
                    </div>
                    <div class="jqGrid_wrapper">
                        <table id="gridTable"></table>
                        <div id="gridPager"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    var colModel = [{name: 'id', index: 'id', hidden: true},
            {name: 'suserName', index: 'suser_name', label: "用户名", width: 150},
            {name: 'srealName', index: 'sreal_name', label: "姓名", width: 150},
            {name: 'smobile', index: 'smobile', label: "手机号", width: 150},
            {name: 'sroleName', index: 'srole_name', label: "角色名", width: 150},
            {name: 'spurName', index: 'spur_name', label: "权限名", width: 150},
            {name: 'spurCode', index: 'spur_code', label: "权限码", width: 150},
        ];
    $(function () {
        initTable(ctx + "/userPurview/queryData", colModel);
    });

    initBtnByForm2();

</script>
</body>
</html>

