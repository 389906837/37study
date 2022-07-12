<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备管理员信息列表</title>
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
                    <h5><spring:message code='sb.device.administrator.information' /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder='<spring:message code="main.device.id" />...' class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sname" id="sname" value="" placeholder='<spring:message code="main.device.name" />...' class="layui-input">
                            </div>
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantCode" id="merchantCode" value="" placeholder='<spring:message code="main.merchant.number" />...' class="layui-input">
                                </div>
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantName" id="merchantName" value="" placeholder='<spring:message code="main.merchant.name" />...' class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-input-inline">
                                <select name="sareaCode" id="sareaCode" class="state" lay-filter="scategoryName">
                                    <option value=""><spring:message code='sb.please.select.your.region' /></option>
                                    <c:forEach items="${dic}" var="vo">
                                        <option value="${vo.svalue}">${vo.sname}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sareaPrincipal" id="sareaPrincipal" value="" placeholder="<spring:message code='sb.regional.head' />..." class="layui-input">
                            </div><div class="layui-input-inline">
                                <input type="text" name="sdevicePrincipal" id="sdevicePrincipal" value="" placeholder="<spring:message code='tpmanage.equipment.manager' />..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="smaintain" id="smaintain" value="" placeholder="<spring:message code='sb.equipment.maintainer' />..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sreplenishment" id="sreplenishment" value="" placeholder="<spring:message code='tpmanage.replenisher' />..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query">
                                    <i class="layui-icon">&#xe615;</i><spring:message code="main.query" />
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset">
                                    <i class="layui-icon">&#x1006;</i><spring:message code="main.clear" />
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
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    var colModel = [
        {name: 'id', index: 'id', hidden: true},
        {name: 'scode', index: 'SCODE', label: '<spring:message code="main.device.id" />',width:140,sortable : false},
        {name: 'sname', index: 'SNAME', label: '<spring:message code="main.device.name" />',width:140,sortable : false},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'merchantCode', index: 'merchantCode', label: '<spring:message code="main.merchant.number" />',width:160,sortable : false},
        {name: 'merchantName', index: 'merchantName', label: '<spring:message code="main.merchant.name" />',width:140,sortable : false},
        </c:if>
        {   name: 'sareaCode',
            index: 'sareaCode',
            label: "<spring:message code='sb.area' />",
            formatter: "select",
            editoptions: {value: '10:<spring:message code='sb.region' />1;20:<spring:message code='sb.region' />2;30:<spring:message code='sb.region' />3'},
            width:140},
        {name: 'sareaPrincipal', index: 'sareaPrincipal', label: "<spring:message code='sb.regional.head' />",width:140,sortable : false},
//                {name: 'sareaPrincipalContact', index: 'sareaPrincipalContact', label: "<spring:message code='tpmanage.contact.information' />",width:160},
        {name: 'sdevicePrincipal', index: 'sdevicePrincipal', label: "<spring:message code='tpmanage.equipment.manager' />",width:180,sortable : false},
//                {name: 'sdevicePrincipalContact', index: 'sdevicePrincipalContact', label: "<spring:message code='tpmanage.contact.information' />",width:160},
        {name: 'smaintain', index: 'smaintain', label: "<spring:message code='sb.equipment.maintainer' />",width:180,sortable : false},
//                {name: 'smaintainContact', index: 'smaintainContact', label: "<spring:message code='tpmanage.contact.information' />",width:160},
        {name: 'sreplenishment', index: 'sreplenishment', label: "<spring:message code='tpmanage.replenisher' />",width:180,sortable : false},
//                {name: 'sreplenishmentContact', index: 'sreplenishmentContact', label: "<spring:message code='tpmanage.contact.information' />",width:160},
        {name: "process", title:false, index: "process", label: '<spring:message code="main.operation" />', sortable: false, frozen: true, width: 200}
    ];
    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
            var ids = $("#gridTable").jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                var cl = ids[i];
                var str = '';
                <shiro:hasPermission name="DEVICE_MANAGE_VIEW">
                    str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"viewMethod('"
                        + cl + "')\" title=\"<spring:message code='sh.view' />\" alt=\"<spring:message code='sh.view' />\" /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="DEVICE_MANAGE_EDIT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                        + cl + "')\" title=\"<spring:message code='main.edit' />\" alt=\"<spring:message code='main.edit' />\" /> | ";
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
            sortname: "tadd_Time",
            multiselect:false,
            sortorder: "desc"
        };
        initTable(ctx+"/device/manage/queryData",colModel,tableCallBack,config);
    });
    initBtnByForm2();

    function viewMethod(sid) {
        showLayerMin("<spring:message code='sb.device.administrator.details' />", ctx + '/device/manage/toView?sid=' + sid);
    }
    function editMethod(sid) {
        showLayerMedium("<spring:message code='sb.edit.device.administrator.information' />", ctx + '/device/manage/toEdit?sid=' + sid);
    }

</script>
</body>
</html>


