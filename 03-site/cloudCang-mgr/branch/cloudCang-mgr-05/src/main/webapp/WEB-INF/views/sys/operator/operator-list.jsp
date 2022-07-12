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
                    <h5><spring:message code='menu.user.management' /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-input-inline">
                                    <input type="text" name="smerchantCode" id="smerchantCode" value=""
                                           placeholder='<spring:message code="main.merchant.number" />...' class="layui-input">
                                </div>
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantName" id="merchantName" value=""
                                           placeholder='<spring:message code="main.merchant.name" />...' class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-input-inline">
                                <input type="text" name="suserName" id="suserName" value="" placeholder="<spring:message code='main.username' />..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="srealName" id="srealName" value="" placeholder="<spring:message code='hy.actual.name' />..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="smobile" id="smobile" value="" placeholder="<spring:message code='sh.phone.number' />..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control" name="bisFreeze" id="bisFreeze">
                                    <option value=""><spring:message code="main.state.select" /></option>
                                    <option value="0"><spring:message code='main.disable' /></option>
                                    <option value="1"><spring:message code="main.enable" /></option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control" name="bisAdmin" id="bisAdmin">
                                    <option value=""><spring:message code='sh.is.it.a.super.administrator' /></option>
                                    <option value="0"><spring:message code="main.no" /></option>
                                    <option value="1"><spring:message code="main.yes" /></option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control" name="isReplenishment" id="isReplenishment">
                                    <option value=""><spring:message code='sh.is.it.a.replenisher' /></option>
                                    <option value="0"><spring:message code="main.no" /></option>
                                    <option value="1"><spring:message code="main.yes" /></option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i
                                        class="layui-icon">&#xe615;</i><spring:message code="main.query" />
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i
                                        class="layui-icon">&#x1006;</i><spring:message code="main.clear" />
                                </button>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <shiro:hasPermission name="ADD_OPT">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"><i
                                        class="layui-icon"></i><spring:message code="main.add" />
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="DELETE_OPT">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i
                                        class="layui-icon"></i><spring:message code="main.delete" />
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
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">

    var colModel = [{name: 'id', index: 'id', hidden: true},
        {name: 'bisFreeze', index: 'BIS_FREEZE', hidden: true},
        {name: 'suserName', label: "<spring:message code='main.username' />", index: 'suser_name', width: 120},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode', label: '<spring:message code="main.merchant.number" />', index: 'smerchant_code', sortable: false, width: 180},
        {name: 'merchantName', label: '<spring:message code="main.merchant.name" />', index: 'merchantName', sortable: false, width: 300},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'smerchantCode', index: 'smerchant_code', hidden: true},
        </c:if>
        {name: 'srealName', index: 'sreal_name', label: "<spring:message code='hy.actual.name' />", width: 180},
        {name: 'srole', index: 'srole', label: "<spring:message code='sh.character' />", width: 100},
        {name: 'smobile', index: 'smobile', label: "<spring:message code='sh.phone.number' />", width: 200},

        {name: 'smail', index: 'smail', label: '<spring:message code="main.mailbox" />', width: 200},
        {
            name: 'bisFreeze',
            index: 'bis_freeze',
            label: '<spring:message code="main.state" />',
            formatter: "select",
            editoptions: {value: '1:<spring:message code="main.enable" />;0:<spring:message code="main.disable" />'},
            width: 140
        },
        {
            name: 'bisAdmin', index: 'bis_admin', label: "<spring:message code='sh.is.it.a.super.administrator' />", width: 180, formatter: "select",
            editoptions: {value: '0:<spring:message code="main.no" />;1:<spring:message code="main.yes" />'}
        },
        {
            name: 'isReplenishment',
            index: 'iis_Replenishment',
            label: "<spring:message code='sh.is.it.a.replenisher' />",
            width: 180,
            formatter: "select",
            editoptions: {value: '0:<spring:message code="main.no" />;1:<spring:message code="main.yes" />'}
        },
        {name: "process", index: "process", title: false, label: '<spring:message code="main.operation" />', sortable: false, frozen: true, width: 280}
    ];
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "scroll" });
            var ids = $("#gridTable").jqGrid('getRowData');
            var merchantCode = "${SESSION_KEY_SMERCHANT_CODE}";
            for (var i = 0; i < ids.length; i++) {
                var cl = ids[i].id;
                var smobile = ids[i].smobile;
                var str = '';
                if (merchantCode == ids[i].smerchantCode) {
                    <shiro:hasPermission name="EDIT_OPT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                        + cl + "')\" title='<spring:message code='sh.edit.user' />' alt='<spring:message code='sh.edit.user' />'  /> | ";
                    </shiro:hasPermission>
                    <shiro:hasPermission name="DELETE_OPT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                        + cl + "')\" title='<spring:message code='sh.delete.users' />' alt='<spring:message code='sh.delete.users' />'  /> | ";
                    </shiro:hasPermission>

                    if (ids[i].bisFreeze == 0) {
                        <shiro:hasPermission name="PROHIBITION_OPT">
                        str += "<img src='${staticSource}/resources/images/oper_icon/open.png' class=\"oper_icon\" onclick=\"prohibitionMethod('"
                            + cl + "',1)\" title='<spring:message code="main.enable" />' alt='<spring:message code="main.enable" />'  /> | ";
                        </shiro:hasPermission>
                    } else {
                        <shiro:hasPermission name="PROHIBITION_OPT">
                        str += "<img src='${staticSource}/resources/images/oper_icon/close.png' class=\"oper_icon\" onclick=\"prohibitionMethod('"
                            + cl + "',0)\" title='<spring:message code='main.disable' />' alt='<spring:message code='main.disable' />'  /> | ";
                        </shiro:hasPermission>
                    }
                    if (ids[i].bisAdmin == 0) {
                        <shiro:hasPermission name="SETADMIN_OPERATOR">
                        str += "<img src='${staticSource}/resources/images/oper_icon/super_manage_off.png' class=\"oper_icon\" onclick=\"setAdminMethod('"
                            + cl + "',1)\" title='<spring:message code='sh.set.as.super.administrator' />' alt='<spring:message code='sh.set.as.super.administrator' />'  /> | ";
                        </shiro:hasPermission>
                    } else {
                        <shiro:hasPermission name="SETADMIN_OPERATOR">
                        str += "<img src='${staticSource}/resources/images/oper_icon/super_manage_on.png' class=\"oper_icon\" onclick=\"setAdminMethod('"
                            + cl + "',0)\" title='<spring:message code='sh.cancel.super.administrator' />' alt='<spring:message code='sh.cancel.super.administrator' />'  /> | ";
                        </shiro:hasPermission>
                    }
                    if (1 == ids[i].isReplenishment) {
                        <shiro:hasPermission name="SETREP_OPERATOR">
                        str += "<img src='${staticSource}/resources/images/oper_icon/bhy_on.png' class=\"oper_icon\" onclick=\"setRepMethod('"
                            + cl + "', 0)\" title='<spring:message code='sh.cancel.its.replenisher' />' alt='<spring:message code='sh.cancel.its.replenisher' />'  /> | ";
                        </shiro:hasPermission>
                    } else {
                        <shiro:hasPermission name="SETREP_OPERATOR">
                        str += "<img src='${staticSource}/resources/images/oper_icon/bhy_off.png' class=\"oper_icon\" onclick=\"setRepMethod('"
                            + cl + "', 1)\" title='<spring:message code='sh.set.as.a.replenisher' />' alt='<spring:message code='sh.set.as.a.replenisher' />'  /> | ";
                        </shiro:hasPermission>
                    }
                    if (0 == ids[i].bisAdmin) {
                        <shiro:hasPermission name="ASSIGN_OPT">
                        str += "<img src='${staticSource}/resources/images/oper_icon/role.png' class=\"oper_icon\" onclick=\"saveOperatorRoleMethod('"
                            + cl + "')\" title='<spring:message code='sh.assigning.roles' />' alt='<spring:message code='sh.assigning.roles' />'  /> | ";
                        </shiro:hasPermission>
                    }
                    <shiro:hasPermission name="PASS_OPT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/update_pwd.png' class=\"oper_icon\" onclick=\"updatePasswordMethod('"
                        + cl + "')\" title='<spring:message code='sh.change.password' />' alt='<spring:message code='sh.change.password' />'  /> | ";
                    </shiro:hasPermission>
                    <shiro:hasPermission name="RESET_PASS_OPT	">
                    str += "<img src='${staticSource}/resources/images/oper_icon/reset_pwd.png' class=\"oper_icon\" onclick=\"resetPasswordMethod('"
                        + cl + "')\" title='<spring:message code='hy.reset.password' />' alt='<spring:message code='hy.reset.password' />'  /> | ";
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
        addTitle: '<spring:message code='sh.add.user.information' />',
        addFn: showLayerMax
    };
    initBtnByForm4(initBtnConfig);

    function editMethod(sid) {
        showLayerMax("<spring:message code='sh.edit.user.information' />", ctx + '/operator/edit?sid=' + sid);
    }
    function delMethod(sid) {

        confirmOperTip('<spring:message code="main.delete.confirm" />', ctx + "/operator/delete", {checkboxId: sid});
    }
    function saveOperatorRoleMethod(sid) {
        showLayerMedium("<spring:message code='sh.assigning.roles' />", ctx + '/operator/toSaveOperatoRrole?sid=' + sid);
    }
    function updatePasswordMethod(sid) {
        showLayerMin("<spring:message code='sh.change.password' />", ctx + '/operator/toUpdatePassword?sid=' + sid, {area: ['30%', '32%']});
    }
    function resetPasswordMethod(sid) {
        confirmOperTip("<spring:message code='hy.are.you.sure.you.want.to.reset.your.password' />?", ctx + "/operator/resetPassword", {checkboxId: sid});
    }
    function setRepMethod(operatorId, type) {
        var alertStr = "<spring:message code='sh.are.you.sure.you.want.to.set.up.as.a.replenisher' />?";
        if (type == 0) {
            alertStr = "<spring:message code='sh.are.you.sure.you.want.to.cancel.your.replenishment.status' />?";
        }
        confirmOperTip(alertStr, ctx + "/operator/setReplenishment", {operatorId: operatorId, type: type});
    }
    function setAdminMethod(sid, type) {
        var alertStr = "<spring:message code='sh.are.you.sure.you.want.to.set.up.as.a.super.administrator' />?";
        if (type == 0) {
            alertStr = "<spring:message code='sh.are.you.sure.you.want.to.cancel.their.super.administrator' />?";
        }
        confirmOperTip(alertStr, ctx + "/operator/setAdmin", {checkboxId: sid, type: type});
    }
    function prohibitionMethod(sid, type) {
        var alertStr = "<spring:message code='sh.are.you.sure.you.want.to.disable.this.user' />?";
        if (type == 1) {
            alertStr = "<spring:message code='sh.are.you.sure.you.want.to.enable.this.user' />?";
        }
        confirmOperTip(alertStr, ctx + "/operator/prohibitionMethod", {checkboxId: sid, type: type});
    }
</script>
</body>

