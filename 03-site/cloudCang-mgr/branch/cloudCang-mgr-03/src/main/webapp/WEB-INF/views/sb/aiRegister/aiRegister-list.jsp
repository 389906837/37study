<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>AI设备注册授权列表</title>
<link href="${staticSource}/resources/layui/css/layui.css?5" rel="stylesheet">
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
                    <h5><spring:message code="sb.ai.device.registration.management" /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="sdeviceCode" id="sdeviceCode" value="" placeholder='AI<spring:message code="main.device.id" />...'
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="deviceCode" id="deviceCode" value="" placeholder='<spring:message code="main.big.screen" /><spring:message code="main.device.id" />...'
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="deviceName" id="deviceName" value="" placeholder='<spring:message code="main.big.screen" /><spring:message code="main.device.name" />...'
                                       class="layui-input">
                            </div>
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantCode" id="merchantCode" value=""
                                           placeholder='<spring:message code="main.merchant.number" />...'
                                           class="layui-input">
                                </div>
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantName" id="merchantName" value=""
                                           placeholder='<spring:message code="main.merchant.name" />...'
                                           class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-input-inline">
                                <select class="form-control" name="istatus" id="istatus">
                                    <option value=""><spring:message code="main.register.status.select" /></option>
                                    <option value="10"><spring:message code="main.audited" /></option>
                                    <option value="20"><spring:message code="main.approval" /></option>
                                    <option value="30"><spring:message code="main.rejection" /></option>
                                    <option value="40"><spring:message code="main.registered" /></option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sauditUser" id="sauditUser" value="" placeholder='<spring:message code="sb.auditor" />...'
                                       class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item" style="margin-top: 10px;">
                            <div class="layui-input-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query">
                                    <i class="layui-icon">&#xe615;</i><spring:message code="main.query" />
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset">
                                    <i class="layui-icon">&#x1006;</i><spring:message code="main.clear" />
                                </button>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <%-- <button class="layui-btn layui-btn-sm " data-type="add"><i class="layui-icon"></i><spring:message code="main.add" /></button>
                             <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i><spring:message code="main.delete" /></button>--%>
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
        {name: 'sdeviceCode', index: 'SDEVICE_CODE', label: '<spring:message code="sb.ai.equipment.number" />', width: 120},
        {name: 'deviceCode', index: 'deviceCode', label: '<spring:message code="sb.large.screen.device.number" />', width: 120, sortable: false},
        {name: 'deviceName', index: 'deviceName', label: '<spring:message code="sb.name.of.large.screen.device" />', width: 120, sortable: false},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'merchantCode', index: 'merchantCode', label: '<spring:message code="main.merchant.number" />', width: 120, sortable: false},
        {name: 'merchantName', index: 'merchantName', label: '<spring:message code="main.merchant.name" />', width: 120, sortable: false},
        </c:if>
        {name: 'sregIp', index: 'SNAME', label: '<spring:message code="sb.registration.code" />', width: 100},
        {
            name: 'tregTime', index: 'TREG_TIME', label: '<spring:message code="sb.registration.time" />', width: 80, formatter: function (value) {
            if (isEmpty(value)) {
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }
        },
        {
            name: 'istatus',
            index: 'ISTATUS',
            label: '<spring:message code="sb.registration.code.status" />',
            formatter: "select",
            editoptions: {value: '10:<spring:message code="main.audited" />;20:<spring:message code="main.approval" />;30:<spring:message code="main.rejection" />;40:<spring:message code="main.registered" />'}
            , width: 100
        },
        {name: 'sauditUser', index: 'SAUDIT_USER', label: '<spring:message code="sb.auditor" />', width: 100},
        {
            name: 'tauditTime', index: 'TAUDIT_TIME', label: '<spring:message code="sb.audit.time" />', width: 80, formatter: function (value) {
            if (isEmpty(value)) {
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }
        },
        {name: 'sremark', index: 'SREMARK', label: '<spring:message code="sb.audit.remarks" />', width: 100},
        {name: "process", title: false, index: "process", label: '<spring:message code="main.operation" />', sortable: false, frozen: true, width: 100}
    ];
    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            var data = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = '';
                if (data[i].istatus == 30) {
                    <shiro:hasPermission name="DEVICE_REGISTER_EDIT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                        + cl + "')\" title=\"<spring:message code='main.edit' />\" alt=\"<spring:message code='main.edit' />\" /> | ";
                    </shiro:hasPermission>
                } else if (data[i].istatus == 10) {
                    <shiro:hasPermission name="DEVICE_REGISTER_AUDIT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/review.png' class=\"oper_icon\" onclick=\"auditMethod('"
                        + cl + "')\" title='<spring:message code='sb.audit' />' alt='<spring:message code='sb.audit' />'  /> | ";
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
            sortname: "B.ISTATUS=10",
            multiselect: false,
            sortorder: "desc"
        };
        initTable(ctx + "/ai/aiRegister/queryData", colModel, tableCallBack, config);
    });
    initBtnByForm2();


    function editMethod(sid) {
        showLayerOne('<spring:message code="sb.edit.device.registration.information" />', ctx + '/ai/aiRegister/toEdit?sid=' + sid);
    }
    function auditMethod(sid) {
        showLayerOne('<spring:message code="sb.review.device.registration.information" />', ctx + '/ai/aiRegister/toAudit?sid=' + sid);
    }
</script>
</body>
</html>


