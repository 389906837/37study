<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>角色管理</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code='menu.role.management' /></h5>
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
                                <input type="text" name="sroleName" id="sroleName" value="" placeholder='<spring:message code="hy.character.name" />...'
                                       class="layui-input">
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
                            <shiro:hasPermission name="ADD_ROLE">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"><i
                                        class="layui-icon"></i><spring:message code="main.add" />
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="DELETE_ROLE">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i
                                        class="layui-icon"></i><spring:message code="main.delete" />
                                </button>
                               <%-- <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="addFastMenu"><i
                                        class="layui-icon"></i><spring:message code="main.add" /><spring:message code='main.shortcut.menu' />
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
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    var colModel = [{name: 'id', index: 'id', hidden: true},
        {name: 'smerchantId', index: 'SMERCHANT_ID', hidden: true},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode', label: '<spring:message code="main.merchant.number" />', index: 'smerchant_code', sortable: false, width: 260},
        {name: 'merchantName', label: '<spring:message code="main.merchant.name" />', index: 'merchantName', sortable: false, width: 400},
        </c:if>
        {name: 'sroleName', label: '<spring:message code="hy.character.name" />', index: 'srole_name',width: 300},
        {name: 'sremark', index: 'sremark', label: '<spring:message code="main.remarks" />',width: 320},
        {name: "process", index: "process",title:false, label: '<spring:message code="main.operation" />',width: 300, sortable: false, frozen: true}
    ];
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
            var ids = $("#gridTable").jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                var cl = ids[i];
                var str = '';
                <shiro:hasPermission name="EDIT_ROLE">
                str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                    + cl + "')\" title='<spring:message code='sh.editing.role' />' alt='<spring:message code='sh.editing.role' />'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="DELETE_ROLE">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='<spring:message code='sh.delete.role' />' alt='<spring:message code='sh.delete.role' />'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="ASSIGN_ROLE">
                str += "<img src='${staticSource}/resources/images/oper_icon/assign_permissions.png' class=\"oper_icon\" onclick=\"allotRoleMethod('"
                    + cl + "')\" title='<spring:message code='hy.assign.permissions' />' alt='<spring:message code='hy.assign.permissions' />'  /> | ";
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
            addTitle: '<spring:message code='sh.add.role.information' />',
            addFn:showLayerMin
        }
    ;
    initBtnByForm4(initBtnConfig);


  /*  layui.use('form', function () {
        var $ = layui.$, active = {
            addFastMenu: function () {
                showLayerMin("<spring:message code='sh.add.shortcut.menu' />", ctx + '/fastMenu/toAddFastMenu');
            }
        };
        $('.layui-form .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });*/
    function editMethod(sid) {
        showLayerMin("<spring:message code='sh.edit.role.information' />", ctx + '/role/edit?sid=' + sid);
    }
    function delMethod(sid) {

        confirmDelTip('<spring:message code="main.delete.confirm" />', ctx + "/role/delete", {checkboxId: sid});
    }
    function allotRoleMethod(sid) {
        showLayerMedium("<spring:message code='hy.assign.permissions' />", ctx + '/role/toSavePurview?sid=' + sid);
    }
</script>
</body>
</html>

