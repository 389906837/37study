<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备管理信息模板列表</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>设备管理信息模板管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder="模板编号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sname" id="sname" value=""
                                       placeholder="模板名称..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control" name="istatus" id="istatus">
                                    <option value="">模板状态</option>
                                    <option value="10">启用</option>
                                    <option value="20">停用</option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query">
                                    <i class="layui-icon">&#xe615;</i>查询
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset">
                                    <i class="layui-icon">&#x1006;</i>清除
                                </button>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <shiro:hasPermission name="TP_DEVICE_MANAGE_TEMPLATE_ADD">
                            <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"><i
                                    class="layui-icon"></i>添加
                            </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="TP_DEVICE_MANAGE_TEMPLATE_DELETE">
                            <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i>删除
                            </button>
                            </shiro:hasPermission>
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
    var colModel = [
        {name: 'id', index: 'id', hidden: true},
        {name: 'scode', index: 'SCODE', label: "模板编号"},
        {name: 'sname', index: 'SNAME', label: "模板名称"},
        {
            name: 'istatus',
            index: 'ISTATUS',
            label: "模板状态",
            formatter: "select",
            editoptions: {value: '10:启用;20:禁用'}

        },
        {
            name: 'taddTime', index: 'TADD_TIME', label: "添加日期", formatter: function (value) {
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }
        },
        {name: "process", title: false, index: "process", label: "操作", sortable: false, frozen: true}
    ];

    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            var data = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = '';
                <shiro:hasPermission name="TP_DEVICE_MANAGE_TEMPLATE_VIEW">
                str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"viewMethod('"
                    + cl + "')\" title='查看' alt='查看'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="TP_DEVICE_MANAGE_TEMPLATE_EDIT">
                str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                    + cl + "')\" title='编辑' alt='编辑'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="TP_DEVICE_MANAGE_TEMPLATE_DELETE">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='删除' alt='删除'  /> | ";
                </shiro:hasPermission>
                if (data[i].istatus == 10) {
                    <shiro:hasPermission name="TP_DEVICE_MANAGE_TEMPLATE_CHANGESTATUS">
                    str += "<img src='${staticSource}/resources/images/oper_icon/disable.png' class=\"oper_icon\" onclick=\"disableMethod('"
                        + cl + "')\" title='停用' alt='停用'  /> | ";
                    </shiro:hasPermission>
                } else if (data[i].istatus == 20) {
                    <shiro:hasPermission name="TP_DEVICE_MANAGE_TEMPLATE_CHANGESTATUS">
                    str += "<img src='${staticSource}/resources/images/oper_icon/enabled.png' class=\"oper_icon\" onclick=\"enableMethod('"
                        + cl + "')\" title='启用' alt='启用'  /> | ";
                    </shiro:hasPermission>
                }
                $("#gridTable").jqGrid('setRowData',
                    cl, {
                        process: str.substr(0, str.lastIndexOf(" | "))
                    });
            }
        }, 0);
    }

    $(function () {
        var config = {
            sortname: "ISTATUS=10",
            sortorder: "desc"
        };
        initTable(ctx + "/deviceManageTemplate/list/queryData", colModel, tableCallBack, config);
    });
    var initBtnConfig = {
        addUrl: ctx + '/deviceManageTemplate/toAdd',
        deleteUrl: ctx + "/deviceManageTemplate/delete",
        addTitle: '添加模板信息',
        addFn: showLayerMedium
    };
    initBtnByForm4(initBtnConfig);

    function viewMethod(sid) {
        showLayerMedium("模板详情", ctx + '/deviceManageTemplate/view?sid=' + sid);
    }
    function editMethod(sid) {
        showLayerMedium("编辑模板信息", ctx + '/deviceManageTemplate/toEdit?sid=' + sid);
    }
    function delMethod(sid) {
        confirmDelTip("确定要删除数据?", ctx + "/deviceManageTemplate/delete", {checkboxId: sid});
    }
    function disableMethod(sid) {
        confirmOperTip("确定要停用该模板?", ctx + "/deviceManageTemplate/changeStatus", {checkboxId: sid});
    }
    function enableMethod(sid) {
        confirmOperTip("确定要启用该模板?", ctx + "/deviceManageTemplate/changeStatus", {checkboxId: sid});
    }
</script>
</body>
</html>



