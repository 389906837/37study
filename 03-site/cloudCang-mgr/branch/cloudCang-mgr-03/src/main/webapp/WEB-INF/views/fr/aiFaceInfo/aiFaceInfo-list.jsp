<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>人脸注册信息列表</title>
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
                    <h5><spring:message code='fr.face.registration.information.management' /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <%--<div class="layui-input-inline">--%>
                                <%--<input type="text" name="deviceCode" id="deviceCode" value="" placeholder="<spring:message code='fr.vending.machine.equipment.number' />...' class="layui-input">--%>
                            <%--</div>--%>
                            <%--<div class="layui-input-inline">--%>
                                <%--<input type="text" name="deviceName" id="deviceName" value="" placeholder="<spring:message code='fr.vending.machine.equipment.name' />...' class="layui-input">--%>
                            <%--</div>--%>
                            <div class="layui-input-inline">
                                <input type="text" name="sregAiCode" id="sregAiCode" value="" placeholder='AI<spring:message code="main.device.id" />...' class="layui-input">
                            </div>
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-input-inline">
                                    <input type="text" name="smerchantCode" id="smerchantCode" value="" placeholder='<spring:message code="main.merchant.number" />...'
                                           class="layui-input">
                                </div>
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantName" id="merchantName" value="" placeholder='<spring:message code="main.merchant.name" />...'
                                           class="layui-input">
                                </div>
                            </c:if>
                                <div class="layui-input-inline">
                                    <input type="text" name="smemberCode" id="smemberCode"  placeholder='<spring:message code="main.member.number" />...'
                                           class="layui-input">
                                </div>
                                <div class="layui-input-inline">
                                    <input type="text" name="smemberName" id="smemberName"  placeholder='<spring:message code="main.member.username" />...'
                                           class="layui-input">
                                </div>
                            <%--<div class="layui-input-inline">--%>
                                <%--<select class="form-control"  name="istatus" id="istatus" >--%>
                                    <%--<option value=""><spring:message code="main.register.status.select" /></option>--%>
                                    <%--<option value="10"><spring:message code='main.audited' /></option>--%>
                                    <%--<option value="20"><spring:message code='main.approval' /></option>--%>
                                    <%--<option value="30"><spring:message code='main.rejection' /></option>--%>
                                    <%--<option value="40"><spring:message code='main.registered' /></option>--%>
                                <%--</select>--%>
                            <%--</div>--%>
                            <%--<div class="layui-input-inline">--%>
                                <%--<input type="text" name="sauditUser" id="sauditUser" value="" placeholder='<spring:message code="main.reviewer" />...' class="layui-input">--%>
                            <%--</div>--%>
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
//        {name: 'deviceCode', index: 'deviceCode', label: "<spring:message code='fr.vending.machine.equipment.number' />",width:120},
//        {name: 'deviceName', index: 'deviceName', label: "<spring:message code='fr.vending.machine.equipment.name' />",width:120},
        {name: 'sregAiCode', index: 'SREG_AI_CODE', label: "<spring:message code='sb.ai.equipment.number' />",width:100},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode', index: 'SMERCHANT_CODE', label: '<spring:message code="main.merchant.number" />',width:120,sortable : false},
        {name: 'merchantName', index: 'merchantName', label: '<spring:message code="main.merchant.name" />',width:120,sortable : false},
        </c:if>
        {name: 'sfaceCode', index: 'SFACE_CODE', label: '<spring:message code="face.code" />',width:100},
        {name: 'smemberCode', index: 'SMEMBER_CODE', label: '<spring:message code="main.member.number" />',width:100},
        {name: 'smemberName', index: 'SMEMBER_NAME', label: '<spring:message code="main.member.username" />',width:100},
        {name: 'sdeviceAddress', index: 'SDEVICE_ADDRESS', label: '<spring:message code="sb.registered.device.address" />',width:100},
        {
            name: 'itype',
            index: 'ITYPE',
            label: '<spring:message code="main.state" />',
            formatter: "select",
            editoptions: {value: '10:<spring:message code='fr.registered' />;20:<spring:message code='fr.binding' />;30:<spring:message code='fr.untied' />;40:<spring:message code="main.delete" />'}
            ,width:100
        },
        {
            name: 'ibindPayType',
            index: 'IBIND_PAY_TYPE',
            label: "<spring:message code='fr.binding.payment.method' />",
            formatter: "select",
            editoptions: {value: '10:<spring:message code="main.wechat" />;20:<spring:message code="main.alipay" />;30:<spring:message code="main.other" />'}
            ,width:100
        },
        {name: 'tregisterTime', index: 'TREGISTER_TIME', label: '<spring:message code="sb.registration.time" />',width: 80,formatter: function (value) {
            if(isEmpty(value)){
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');}},
        {name: 'sregIp', index: 'SREG_IP', label: "<spring:message code='fr.registered.ip.address' />",width:100},
        {name: 'sgroupId', index: 'SGROUP_ID', label: "<spring:message code='fr.user.group' />",width:100},
//        {name: 'tauditTime', index: 'TAUDIT_TIME', label: "<spring:message code='sb.audit.time' />",width: 80,formatter: function (value) {
//            if(isEmpty(value)){
//                return '';
//            }
//            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');}},
//        {name: 'sremark', index: 'SREMARK', label: '<spring:message code="sb.audit.remarks" />',width:100},
        {name: "process", title:false, index: "process", label: '<spring:message code="main.operation" />', sortable: false, frozen: true, width: 100}
    ];
    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            var data = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = '';
                <shiro:hasPermission name="AI_FACEINFO_VIEW">
                str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"viewMethod('"
                    + cl + "')\" title=\"<spring:message code='sh.view' />\" alt=\"<spring:message code='sh.view' />\" /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="AI_FACEINFO_DELETE">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='<spring:message code="main.delete" />' alt='<spring:message code="main.delete" />'  /> | ";
                </shiro:hasPermission>
                $("#gridTable").jqGrid('setRowData',
                    cl, {
                        process: str.substr(0, str.lastIndexOf(" | "))
                    });
            }
        }, 0);
    }

    $(function () {
        var config = {
            sortname: "B.ITYPE=10",
            sortorder: "desc"
        };
        initTable(ctx+"/aiFaceInfo/queryData",colModel,tableCallBack,config);
    });
    initBtnByForm2();


    function viewMethod(sid) {
        showLayerOne("<spring:message code='fr.view.face.registration.information' />", ctx + '/aiFaceInfo/toView?sid=' + sid);
    }
    function delMethod(sid) {
        confirmDelTip('<spring:message code="main.delete.confirm" />', ctx + "/aiFaceInfo/delete", {checkboxId: sid});
    }
</script>
</body>
</html>


