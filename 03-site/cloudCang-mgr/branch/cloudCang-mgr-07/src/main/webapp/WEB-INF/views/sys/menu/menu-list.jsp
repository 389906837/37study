<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>菜单管理</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet"/>
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?1.0" rel="stylesheet"/>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>菜单管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <select class="form-control" name="sparentId" id="sparentId">
                                    <option value="">请选择父菜单</option>
                                    <c:forEach items="${parentMenu}" varStatus="L" var="item">
                                        <option value="${item.id}">${item.sname}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="smenuNo" id="smenuNo" value="" placeholder="编号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sname" id="sname" value="" placeholder="菜单名称..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="smenuPath" id="smenuPath" value="" placeholder="菜单路径..."
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
                        <div class="layui-form-item">
                            <shiro:hasPermission name="ADD_MENU">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" style="margin-left: 0px;"
                                        data-type="add"><i class="layui-icon"></i>添加
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="DELETE_MENU">
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
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    var colModel = [{name: 'id', index: 'id', hidden: true},
        {name: 'smenuNo', index: 'smenu_no', label: "菜单编号", width: 150},
        {name: 'sname', index: 'sname', label: "菜单名称", width: 200},
        {name: 'simagePath', index: 'simage_path', label: "图片ICON", width: 120},
        {name: 'smenuPath', index: 'smenu_path', label: "菜单路径", width: 300},
        {name: 'isort', index: 'isort', label: "序号", width: 80},
        {name: 'sremark', index: 'sremark', label: "备注", sortable: false, width: 300},
        {name: 'process', index: 'process',title:false, label: "操作", sortable: false, width: 150}
    ];
    function tableCallBack() {
        setTimeout(function () {
            var ids = $("#gridTable").jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                var cl = ids[i];
                var str = "";
                <shiro:hasPermission name="EDIT_MENU">
                str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                    + cl + "')\" title='编辑菜单' alt='编辑菜单'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="DELETE_MENU">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='删除菜单' alt='删除菜单'  /> | ";
                </shiro:hasPermission>

                $("#gridTable").jqGrid('setRowData',
                    ids[i], {
                        process: str.length > 0 ? str.substr(0, str.lastIndexOf(" | ")) : ""
                    });
            }
        }, 0);
    }
    $(function () {
        var config = {
            sortname: "field(SPARENT_ID,'0')",
            sortorder: "desc"
        };
        initTable(ctx + "/menu/queryAllMenu", colModel, tableCallBack, config);
    });
    //初始化form中按钮
    var initBtnConfig = {
        addUrl: ctx + '/menu/edit',
        deleteUrl: ctx + "/menu/delete",
        addTitle: '添加菜单信息',
        addFn: showLayerMin
    };
    initBtnByForm4(initBtnConfig);


    function editMethod(sid) {
        showLayerMin("编辑菜单信息", ctx + '/menu/edit?sid=' + sid);
    }
    function delMethod(sid) {
        confirmDelTip("确定要删除数据?", ctx + "/menu/delete", {checkboxId: sid});
    }
</script>
</body>
</html>

