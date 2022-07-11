<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>角色管理</title>
<link href="${staticSource}/resources/layui/css/layui.css?1" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>角色管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-input-inline">
                                    <input type="text" name="smerchantCode" id="smerchantCode" value=""
                                           placeholder="商户编号..." class="layui-input">
                                </div>
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantName" id="merchantName" value=""
                                           placeholder="商户名称..." class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-input-inline">
                                <input type="text" name="sroleName" id="sroleName" value="" placeholder="角色名称..."
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
                            <shiro:hasPermission name="ADD_ROLE">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"><i
                                        class="layui-icon"></i>添加
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="DELETE_ROLE">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i
                                        class="layui-icon"></i>删除
                                </button>
                               <%-- <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="addFastMenu"><i
                                        class="layui-icon"></i>添加快捷菜单
                                </button>--%>
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
    var colModel = [{name: 'id', index: 'id', hidden: true},
        {name: 'smerchantId', index: 'SMERCHANT_ID', hidden: true},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode', label: "商户编号", index: 'smerchant_code', sortable: false, width: 150},
        {name: 'merchantName', label: "商户名称", index: 'merchantName', sortable: false, width: 200},
        </c:if>
        {name: 'sroleName', label: "角色名称", index: 'srole_name'},
        {name: 'sremark', index: 'sremark', label: "备注"},
        {name: "process", index: "process",title:false, label: "操作", sortable: false, frozen: true}
    ];
    function tableCallBack() {
        setTimeout(function () {
            var ids = $("#gridTable").jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                var cl = ids[i];
                var str = '';
                <shiro:hasPermission name="EDIT_ROLE">
                str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                    + cl + "')\" title='编辑角色' alt='编辑角色'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="DELETE_ROLE">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='删除角色' alt='删除角色'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="ASSIGN_ROLE">
                str += "<img src='${staticSource}/resources/images/oper_icon/assign_permissions.png' class=\"oper_icon\" onclick=\"allotRoleMethod('"
                    + cl + "')\" title='分配权限' alt='分配权限'  /> | ";
                </shiro:hasPermission>
                $("#gridTable").jqGrid('setRowData',
                    ids[i], {
                        process: str.substr(0, str.lastIndexOf(" | "))
                    });
            }
        }, 0);
    }
    $(function () {
        var config = {
            sortname: "id",
            sortorder: "desc"
        };
        initTable(ctx + "/role/queryData", colModel, tableCallBack, config);
    });
    //初始化form中按钮
    var initBtnConfig = {
            addUrl: ctx + '/role/edit',
            deleteUrl: ctx + "/role/delete",
            addTitle: '添加角色信息',
            addFn:showLayerMin
        }
    ;
    initBtnByForm4(initBtnConfig);


  /*  layui.use('form', function () {
        var $ = layui.$, active = {
            addFastMenu: function () {
                showLayerMin("添加快捷菜单", ctx + '/fastMenu/toAddFastMenu');
            }
        };
        $('.layui-form .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });*/
    function editMethod(sid) {
        showLayerMin("编辑角色信息", ctx + '/role/edit?sid=' + sid);
    }
    function delMethod(sid) {

        confirmDelTip("确定要删除数据?", ctx + "/role/delete", {checkboxId: sid});
    }
    function allotRoleMethod(sid) {
        showLayerMedium("分配权限", ctx + '/role/toSavePurview?sid=' + sid);
    }
</script>
</body>
</html>

