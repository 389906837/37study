<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>用户管理</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
<style>
    .ui-jqgrid tr.jqgrow td {
        white-space: normal !important;
        height: auto;
    }

</style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>用户管理</h5>
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
                                <input type="text" name="suserName" id="suserName" value="" placeholder="用户名..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="srealName" id="srealName" value="" placeholder="真实姓名..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="smobile" id="smobile" value="" placeholder="手机号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control" name="bisFreeze" id="bisFreeze">
                                    <option value="">请选择状态</option>
                                    <option value="0">禁用</option>
                                    <option value="1">启用</option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control" name="bisAdmin" id="bisAdmin">
                                    <option value="">是否为超级管理员</option>
                                    <option value="0">否</option>
                                    <option value="1">是</option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control" name="isReplenishment" id="isReplenishment">
                                    <option value="">是否为补货员</option>
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
                            <shiro:hasPermission name="ADD_OPT">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"><i
                                        class="layui-icon"></i>添加
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="DELETE_OPT">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i
                                        class="layui-icon"></i>删除
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

    var colModel = [{name: 'id', index: 'id', hidden: true},
        {name: 'bisFreeze', index: 'BIS_FREEZE', hidden: true},
        {name: 'suserName', label: "用户名", index: 'suser_name', width: 70},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode', label: "商户编号", index: 'smerchant_code', sortable: false, width: 70},
        {name: 'merchantName', label: "商户名称", index: 'merchantName', sortable: false, width: 70},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'smerchantCode', index: 'smerchant_code', hidden: true},
        </c:if>
        {name: 'srealName', index: 'sreal_name', label: "真实姓名", width: 50},
        {name: 'srole', index: 'srole', label: "角色", width: 100},
        {name: 'smobile', index: 'smobile', label: "手机号", width: 70},

        {name: 'smail', index: 'smail', label: "邮箱", width: 100},
        {
            name: 'bisFreeze',
            index: 'bis_freeze',
            label: "状态",
            formatter: "select",
            editoptions: {value: '0:禁用;1:启用'},
            width: 50
        },
        {
            name: 'bisAdmin', index: 'bis_admin', label: "是否超级管理员", width: 70, formatter: "select",
            editoptions: {value: '0:否;1:是'}
        },
        {
            name: 'isReplenishment',
            index: 'iis_Replenishment',
            label: "是否为补货员",
            width: 57,
            formatter: "select",
            editoptions: {value: '0:否;1:是'}
        },
        {name: "process", index: "process", title: false, label: "操作", sortable: false, frozen: true, width: 190}
    ];
    function tableCallBack() {
        setTimeout(function () {
            var ids = $("#gridTable").jqGrid('getRowData');
            var merchantCode = "${SESSION_KEY_SMERCHANT_CODE}";
            for (var i = 0; i < ids.length; i++) {
                var cl = ids[i].id;
                var smobile = ids[i].smobile;
                var str = '';
                if (merchantCode == ids[i].smerchantCode) {
                    <shiro:hasPermission name="EDIT_OPT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                        + cl + "')\" title='编辑用户' alt='编辑用户'  /> | ";
                    </shiro:hasPermission>
                    <shiro:hasPermission name="DELETE_OPT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                        + cl + "')\" title='删除用户' alt='删除用户'  /> | ";
                    </shiro:hasPermission>

                    if (ids[i].bisFreeze == 0) {
                        <shiro:hasPermission name="PROHIBITION_OPT">
                        str += "<img src='${staticSource}/resources/images/oper_icon/open.png' class=\"oper_icon\" onclick=\"prohibitionMethod('"
                            + cl + "',1)\" title='启用' alt='启用'  /> | ";
                        </shiro:hasPermission>
                    } else {
                        <shiro:hasPermission name="PROHIBITION_OPT">
                        str += "<img src='${staticSource}/resources/images/oper_icon/close.png' class=\"oper_icon\" onclick=\"prohibitionMethod('"
                            + cl + "',0)\" title='禁用' alt='禁用'  /> | ";
                        </shiro:hasPermission>
                    }
                    if (ids[i].bisAdmin == 0) {
                        <shiro:hasPermission name="SETADMIN_OPERATOR">
                        str += "<img src='${staticSource}/resources/images/oper_icon/super_manage_off.png' class=\"oper_icon\" onclick=\"setAdminMethod('"
                            + cl + "',1)\" title='设置为超级管理员' alt='设置为超级管理员'  /> | ";
                        </shiro:hasPermission>
                    } else {
                        <shiro:hasPermission name="SETADMIN_OPERATOR">
                        str += "<img src='${staticSource}/resources/images/oper_icon/super_manage_on.png' class=\"oper_icon\" onclick=\"setAdminMethod('"
                            + cl + "',0)\" title='取消超级管理员' alt='取消超级管理员'  /> | ";
                        </shiro:hasPermission>
                    }
                    if (1 == ids[i].isReplenishment) {
                        <shiro:hasPermission name="SETREP_OPERATOR">
                        str += "<img src='${staticSource}/resources/images/oper_icon/bhy_on.png' class=\"oper_icon\" onclick=\"setRepMethod('"
                            + smobile + "',0)\" title='取消其补货员' alt='取消其补货员'  /> | ";

                        </shiro:hasPermission>
                    } else {
                        <shiro:hasPermission name="SETREP_OPERATOR">
                        str += "<img src='${staticSource}/resources/images/oper_icon/bhy_off.png' class=\"oper_icon\" onclick=\"setRepMethod('"
                            + smobile + "',1)\" title='设置为补货员' alt='设置为补货员'  /> | ";

                        </shiro:hasPermission>
                    }
                    if (0 == ids[i].bisAdmin) {
                        <shiro:hasPermission name="ASSIGN_OPT">
                        str += "<img src='${staticSource}/resources/images/oper_icon/role.png' class=\"oper_icon\" onclick=\"saveOperatorRoleMethod('"
                            + cl + "')\" title='分配角色' alt='分配角色'  /> | ";

                        </shiro:hasPermission>
                    }
                    <shiro:hasPermission name="PASS_OPT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/update_pwd.png' class=\"oper_icon\" onclick=\"updatePasswordMethod('"
                        + cl + "')\" title='修改密码' alt='修改密码'  /> | ";
                    </shiro:hasPermission>
                    <shiro:hasPermission name="RESET_PASS_OPT	">
                    str += "<img src='${staticSource}/resources/images/oper_icon/reset_pwd.png' class=\"oper_icon\" onclick=\"resetPasswordMethod('"
                        + cl + "')\" title='重置密码' alt='重置密码'  /> | ";
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
            sortname: "A.BIS_FREEZE=1",
            sortorder: "desc"
        };
        initTable(ctx + "/operator/queryData", colModel, tableCallBack, config);
    });
    //初始化form中按钮
    var initBtnConfig = {
        addUrl: ctx + '/operator/edit',
        deleteUrl: ctx + "/operator/delete",
        addTitle: '添加用户信息',
        addFn: showLayerMax
    };
    initBtnByForm4(initBtnConfig);

    function editMethod(sid) {
        showLayerMax("编辑用户信息", ctx + '/operator/edit?sid=' + sid);
    }
    function delMethod(sid) {

        confirmOperTip("确定要删除数据?", ctx + "/operator/delete", {checkboxId: sid});
    }
    function saveOperatorRoleMethod(sid) {
        showLayerMedium("分配角色", ctx + '/operator/toSaveOperatoRrole?sid=' + sid);
    }
    function updatePasswordMethod(sid) {
        showLayerMin("修改密码", ctx + '/operator/toUpdatePassword?sid=' + sid, {area: ['30%', '32%']});
    }
    function resetPasswordMethod(sid) {
        confirmOperTip("确定要重置密码?", ctx + "/operator/resetPassword", {checkboxId: sid});
    }
    function setRepMethod(smobile, type) {
        var alertStr = "确定要设置为补货员?";
        if (type == 0) {
            alertStr = "确定要取消其补货员资格?";
        }
        confirmOperTip(alertStr, ctx + "/operator/setReplenishment ", {smobile: smobile, type: type});
    }
    function setAdminMethod(sid, type) {
        var alertStr = "确定要设置为超级管理员?";
        if (type == 0) {
            alertStr = "确定要取消其超级管理员?";
        }
        confirmOperTip(alertStr, ctx + "/operator/setAdmin", {checkboxId: sid, type: type});
    }
    function prohibitionMethod(sid, type) {
        var alertStr = "确定要禁用该用户?";
        if (type == 1) {
            alertStr = "确定要启用该用户?";
        }
        confirmOperTip(alertStr, ctx + "/operator/prohibitionMethod", {checkboxId: sid, type: type});
    }
</script>
</body>

