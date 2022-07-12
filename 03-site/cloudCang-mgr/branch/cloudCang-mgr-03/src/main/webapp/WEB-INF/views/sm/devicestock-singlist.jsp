<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>单机库存列表查询</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code="sm.single.inventory.query" /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-inline">
                                    <input type="text" name="shcode" id="shcode" value=""
                                           placeholder='<spring:message code="sm.merchant.code" />...' class="layui-input">
                                </div>
                                <div class="layui-inline">
                                    <input type="text" name="merchantName" id="merchantName" value=""
                                           placeholder='<spring:message code="sm.merchant.name" />...' class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-inline">
                                <input type="text" name="sdeviceCode" id="sdeviceCode" value="" placeholder='<spring:message code="sm.device.id" />...'
                                       class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sbName" id="sbName" value="" placeholder='<spring:message code="sm.device.name" />...'
                                       class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="scommodityCode" id="scommodityCode" value=""
                                       placeholder='<spring:message code="sm.product.id" />...' class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="spName" id="spName" value="" placeholder='<spring:message code="sm.product.name" />...'
                                       class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i
                                        class="layui-icon">&#xe615;</i><spring:message code="sm.query" />
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i
                                        class="layui-icon">&#x1006;</i><spring:message code="sm.remove" />
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
<script type="text/javascript" src="${staticSource}/resources/page/sm/stockoperlog/stockoperLog-list.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    // 渲染数据
    var colModel = [{name: 'id', index: 'id', hidden: true},
        {name: 'sdeviceCode', index: 'SDEVICE_CODE', label: '<spring:message code="sm.device.id" />', width: 130, sortable: false},
        {name: 'sbName', index: 'sbName', label: '<spring:message code="sm.device.name" />', width: 150, sortable: false},
        {name: 'adress', index: 'adress', label: '<spring:message code="sm.device.address" />', width: 80, sortable: false, width: 400},
        {name: 'scommodityCode', index: 'SCOMMODITY_CODE', label: '<spring:message code="sm.product.id" />', width: 150, sortable: false},
        {name: 'spName', index: 'spName', label: '<spring:message code="sm.product.name" />', sortable: false, width: 240},
        {name: 'fsalePrice', index: 'fsalePrice', label: '<spring:message code="sm.unit.price" />', width: 100},
        {
            name: 'istock', index: 'ISTOCK', label: '<spring:message code="sm.inventory.quantity" />', width: 120,
            formatter: function (istock, opt, row) {
                return '<span id="istock_' + row.id + '" data="' + istock + '"  class="' + (istock <= row.stockWarningValue ? 'istatus-danger' : 'istatus-normal') + '">' + istock + '</span>'
            }
        },
        {name: 'kcistandardstock', index: 'KCISTANDARDSTOCK', label: '<spring:message code="sm.label.stock" />', width: 100, sortable: false},
        {name: 'stockWarningValue', index: 'STOCKWARNINGVALUE', label: '<spring:message code="sm.warning.value.of.inventory" />', width: 200, sortable: false},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode', label: '<spring:message code="sm.merchant.code" />', index: 'SMERCHANT_CODE', sortable: false},
        {name: 'merchantName', label: '<spring:message code="sm.merchant.name" />', index: 'MERCHANTNAME', sortable: false, width: 300},
        </c:if>
        {name: 'sremark', index: 'sremark', label: '<spring:message code="sm.remarks" />', sortable: false, width: 120},
        {name: 'process', title: false, index: 'process', label: '<spring:message code="sm.operation" />', sortable: false, width: 220}
    ];
    // 表格回调
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
            var data = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = "";
                <shiro:hasPermission name="INFO_QUERYONEDEVICESTOCKINFO">
                str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"queryInfoMethod('"
                    + cl + "')\" title='<spring:message code="sm.stand-alone.inventory.details" />' alt='<spring:message code="sm.stand-alone.inventory.details" />'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="UPDATECOMFSALEPRICE_QUERYONEDEVICESTOCKINFO">
                str += "<img src='${staticSource}/resources/images/oper_icon/update_fsale.png' class=\"oper_icon\" onclick=\"comFsalePriceMethod('"
                    + cl + "')\" title='<spring:message code="sm.modify.unit.price" />' alt='<spring:message code="sm.modify.unit.price" />'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="SYNCCOMFSALEPRICE_QUERYONEDEVICESTOCKINFO">
                str += "<img src='${staticSource}/resources/images/oper_icon/async_fsale.png' class=\"oper_icon\" onclick=\"comSyncFsalePriceMethod('"
                    + cl + "')\" title='<spring:message code="sm.restore.the.original.price" />' alt='<spring:message code="sm.restore.the.original.price" />'  /> | ";
                </shiro:hasPermission>
                if ($('#istock_' + cl).attr('data') > 0) {
                    <shiro:hasPermission name="STOCKDETAIL_UNDERCARRIAGE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/drop_off.png' class=\"oper_icon\" onclick=\"undercarriageMethod('"
                        + cl + "')\" title='<spring:message code="sm.manually.off.the.shelf" />' alt='<spring:message code="sm.manually.off.the.shelf" />'  /> | ";
                    </shiro:hasPermission>
                }
                $("#gridTable").jqGrid('setRowData',
                    cl, {
                        process: str.length > 0 ? str.substr(0, str.lastIndexOf(" | ")) : ""
                    });
            }
        }, 0);
    }
    // 初始化表格,传入页面所需参数
    $(function () {
        var config = {
            shrinkToFit: false,
            autoScroll: true,
            rownumbers: true,
            multiselect: false,
            sortname: 'ISTOCK',
            sortorder: 'desc'
        };
        initTable(ctx + "/devicestock/queryOneData", colModel, tableCallBack, config);
    });
    // 调用Button按钮方法(查询,清除)
    var initBtnConfig = {
        addModelConfig: {area: ['60%', '80%']}
    };
    initBtnByForm2(initBtnConfig);

    function queryInfoMethod(sid) {
        showLayerMedium('<spring:message code="sm.details.of.single.machine.inventory.goods" />', ctx + '/devicestock/queryOneDevicestockInfo?sid=' + sid);
    }

    function comFsalePriceMethod(sid) {
        showLayerMin('<spring:message code="sm.modification.of.unit.price.of.goods" />', ctx + 'comFsalePricelist?sid=' + sid);
    }

    function undercarriageMethod(sid) {
        showLayerMin('<spring:message code="sm.manual.off-shelf.inventory" />', ctx + 'undercarriage?sid=' + sid);
    }

    function comSyncFsalePriceMethod(sid) {
        var alertStr = '<spring:message code="sm.determine.the.unit.price.of.goods.to.be.synchronized" />?';
        confirmOperTip(alertStr, ctx + "/devicestock/syncComFsalePrice", {checkboxId: sid});
    }

</script>
</body>
</html>

