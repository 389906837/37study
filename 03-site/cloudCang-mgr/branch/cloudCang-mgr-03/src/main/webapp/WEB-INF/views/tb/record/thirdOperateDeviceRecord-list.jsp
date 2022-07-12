<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>第三方订单/补货列表</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
<style>
    .ui-jqgrid tr.jqgrow td {
        white-space: normal !important;
        height: auto;
    }
    .layui-btn-hui{background:#f2f2f2;border:1px solid #e6e6e6;color:#323232;}
    .layui-btn-hui:hover{color:#323232;}
</style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code='tb.third.party.shopping.replenishment.order.management' /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder="<spring:message code='tb.order.number' />..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="merchantCode" id="merchantCode" value=""
                                       placeholder='<spring:message code="main.merchant.number" />...' class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="merchantName" id="merchantName" value="" placeholder='<spring:message code="main.merchant.name" />...'
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sdeviceCode" id="sdeviceCode" value="" placeholder='<spring:message code="main.device.id" />...'
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sname" id="sname" value="" placeholder='<spring:message code="main.device.name" />...'
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control"  name="iorderType" id="iorderType" >
                                    <option value=""><spring:message code='tb.please.select.an.order.type' /></option>
                                    <option value="10"><spring:message code='tb.shopping' /></option>
                                    <option value="20"><spring:message code='tb.replenishment' /></option>
                                </select>
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
    var colModel =[
        {name: 'id', index: 'id', hidden: true},
        {name: 'scode', index: 'SCODE', label: '<spring:message code="main.order.number" />',width:130},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'merchantCode',label : '<spring:message code="main.merchant.number" />',index: 'merchantCode',width:120},
        {name: 'merchantName',label : '<spring:message code="main.merchant.name" />',index: 'merchantName',sortable : false,width:200},
        </c:if>
        {name: 'sdeviceCode', index: 'SDEVICE_CODE', label: '<spring:message code="main.device.id" />',width:100},
        {name: 'sname', index: 'sname', label: '<spring:message code="main.device.name" />',width:140},
        {   name: 'iorderType',
            index: 'IORDER_TYPE',
            label: "<spring:message code='tb.order.type' />",
            formatter: "select",
            editoptions: {value: '10:<spring:message code='tb.shopping.order' />;20:<spring:message code='tb.replenishment.order' />'},
            width:100},
        // {name: 'faddTotalAmount', index: 'FADD_TOTAL_AMOUNT', label: "增加总额",width:80},
        // {name: 'fsubTotalAmount', index: 'FSUB_TOTAL_AMOUNT', label: "减少总额",width:80},
        {name: 'faddTotalNum', index: 'FADD_TOTAL_NUM', label: "<spring:message code='tb.increase.total' />",width:80},
        {name: 'fsubTotalNum', index: 'FSUB_TOTAL_NUM', label: "<spring:message code='tb.reduce.the.total' />",width:80},
        {name: 'taddTime', index: 'TADD_TIME', label: "<spring:message code='tb.order.time' />",formatter: function (value) {
                return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
            },width:140},
        {name: "process",title:false, index: "process", label: '<spring:message code="main.operation" />', sortable: false, frozen: true, width: 200}
    ];

    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            var data = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = '';
                <shiro:hasPermission name="TB_OPERATE_DEVICE_RECORD_VIEW">
                str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"viewMethod('"
                    + cl + "')\" title=\"<spring:message code='sh.view' />\" alt=\"<spring:message code='sh.view' />\" /> | ";
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
            sortname: "tadd_Time",
            multiselect:false,
            sortorder: "desc"
        };
        initTable(ctx+"/tbOperateDeviceRecord/queryData",colModel,tableCallBack,config);
    });
    var initBtnConfig = {
        addUrl: ctx+'/commodity/labelInfo/toAdd',
        addTitle: '<spring:message code="sp.label.add.product.tag.information" />',
        addFn:showLayerOne
    };
    initBtnByForm4(initBtnConfig);

    function viewMethod(sid) {
        showLayerMedium('<spring:message code="main.details" />', ctx + '/tbOperateDeviceRecord/view?sid=' + sid);
    }

</script>
</body>
</html>



