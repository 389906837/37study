<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>数据字典管理</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet"/>
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css" rel="stylesheet"/>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>数据字典管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="sgroupNo" id="sgroupNo" value="" placeholder="数据字典编号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sgroupName" id="sgroupName" value="" placeholder="数据字典名称..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control" name="bisModify" id="bisModify">
                                    <option value="">是否可修改</option>
                                    <option value="0">否</option>
                                    <option value="1">是</option>
                                </select>
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
                        <div class="layui-form-item">
                            <shiro:hasPermission name="paraMgr_addParamGroup">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" style="margin-left: 0px;"
                                        data-type="add"><i class="layui-icon"></i>添加
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="paraMgr_delParamGroup">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i>删除
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="ZKP_DATADICT">
                                <button class="layui-btn layui-btn-sm layui-btn-primary" data-type="refreshCache"><i
                                        class="layui-icon">&#x1006;</i>更新缓存
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
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    var colModel = [{name: 'id', index: 'id', hidden: true},
        {name: 'sgroupNo', index: 'sgroup_no', label: "数据字典编号", width: 150},
        {name: 'sgroupName', index: 'sgroup_name', label: "数据字典名称", width: 200},
        {
            name: 'bisModify',
            index: 'bis_modify',
            label: "是否可修改",
            width: 120,
            sortable: false,
            formatter: "select",
            editoptions: {value: '0:否;1:是'}
        },
        {name: 'sremark', index: 'sremark', label: "备注", sortable: false, width: 300},
        {name: 'process', index: 'process',title:false, label: "操作", sortable: false, width: 150}
    ];

    function tableCallBack() {
        setTimeout(function () {
            var ids = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < ids.length; i++) {
                var cl = ids[i];
                var str = "";
                <shiro:hasPermission name="paraMgr_upParamGroup">
                str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                    + cl.sgroupNo + "')\" title='编辑数据字典' alt='编辑数据字典'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="paraMgr_delParamGroup">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl.id + "')\" title='删除数据字典' alt='删除数据字典'  /> | ";
                </shiro:hasPermission>
                $("#gridTable").jqGrid('setRowData',
                    cl.id, {
                        process: str.length > 0 ? str.substr(0, str.lastIndexOf(" | ")) : ""
                    });
            }
        }, 0);
    }
    $(function () {
        var config = {
            sortname: "sgroup_no",
            sortorder: "asc"
        };
        initTable(ctx + "/paramGroup/query", colModel, tableCallBack, config);
    });
    //初始化form中按钮
    var initBtnConfig = {
            addUrl: ctx + '/paramGroup/edit',
            deleteUrl: ctx + "/paramGroup/delete",
            updatecacheUrl: ctx + '/zooSyn/cacheDic?t=' + new Date(),
            addTitle: '添加数据字典信息',
            addFn:showLayerMedium
        }
    ;
    initBtnByForm5(initBtnConfig);

    function refreshTable() {
        reloadGrid(true);
    }

    function editMethod(groupNo) {
        showLayerMedium("编辑数据字典信息", ctx + '/paramGroup/edit?groupNo=' + groupNo);
    }
    function delMethod(sid) {
        confirmDelTip("确定要删除数据?", ctx + "/paramGroup/delete", {checkboxId: sid});
    }
</script>
</body>
</html>

