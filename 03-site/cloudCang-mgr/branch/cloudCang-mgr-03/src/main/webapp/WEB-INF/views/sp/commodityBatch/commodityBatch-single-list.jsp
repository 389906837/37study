<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>商品批次列表</title>
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
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <input type="hidden" id="scommodityId" name="scommodityId" value="${sid}" /><%--<spring:message code='main.product.number' />--%>
                        <div class="layui-form-item">
                                <shiro:hasPermission name="SINGLE_COMMODITY_BATCH_ADD">
                            <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"><i class="layui-icon"></i><spring:message code="main.add" /></button>
                                </shiro:hasPermission>
                        <%--<shiro:hasPermission name="COMMODITY_INFO_GROUP_ADDBATCH">
                            <button class="layui-btn layui-btn-sm" data-type="batchManage"><i class="layui-icon">&#xe63c;</i><spring:message code="sp.commodityBatch.batch" /></button>
                            </shiro:hasPermission>--%>
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
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode',label : '<spring:message code="main.merchant.number" />',index: 'SMERCHANT_CODE',width:200},
        {name: 'merchantName',label : '<spring:message code="main.merchant.name" />',index: 'merchantName',sortable : false,width:200},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'smerchantCode', index: 'smerchant_code', hidden: true},
        </c:if>
        {name: 'scommodityCode', index: 'SCOMMODITY_CODE', label: '<spring:message code="main.product.number" />',width:220},
        {name: 'commodityFullName', index: 'commodityName', label: '<spring:message code="main.product.name" />',width:220},
        {name: 'sbatchNo', index: 'SBATCH_NO', label: '<spring:message code="sm.batch.number" />',width:240},
        {name: 'fcostAmount', index: 'FCOST_AMOUNT', label: '<spring:message code="sp.commodityBatch.cost.price" />',width:200},
        {name: 'ftaxPoint', index: 'FTAX_POINT', label: '<spring:message code="sp.commodityBatch.tax.point" />',width:200},
        {name: 'icommodityNum', index: 'ICOMMODITY_NUM', label: '<spring:message code="sp.commodityBatch.quantity" />',width:200},
        {name: 'ishelfNum', index: 'ISHELF_NUM', label: '<spring:message code="sp.commodityBatch.number.of.shelves" />',width:200},
        {name: 'ilossGoodsNum', index: 'ilossGoodsNum', label: '<spring:message code="sp.commodityBatch.number.of.damage" />',width:200},
        {name: 'dproduceDate', index: 'DPRODUCE_DATE', label : '<spring:message code="sm.date.of.production" />',formatter: function (value) {
            return formatDate(new Date(value), 'yyyy-MM-dd');
        },width:220},
        {name: 'dexpiredDate', index: 'DEXPIRED_DATE', label : '<spring:message code="sm.expiration.date" />',formatter: function (value) {
            if(isEmpty(value)){
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd');
        },width:200},
//        {   name: 'istatus',
//            index: 'ISTATUS',
//            label: '<spring:message code="sp.commodityBatch.operation.state" />',
//            formatter: "select",
//            editoptions: {value: '10:<spring:message code="main.enable" />;20:<spring:message code="main.disable" />'},
//            width:120},
        {   name: 'istockStatus',
            index: 'ISTOCK_STATUS',
            label: '<spring:message code="sp.commodityBatch.inventory.status" />',
            formatter: "select",
            editoptions: {value: '10:<spring:message code="sp.commodityBatch.waiting.to.be.put.on.the.shelves" />;20:<spring:message code="sp.commodityBatch.partly.on.the.shelf" />;30:<spring:message code="sp.commodityBatch.all.shelves" />'},
            width:200},
//        {   name: 'isaleStatus',
//            index: 'ISALE_STATUS',
//            label: '<spring:message code="sp.commodityBatch.sales.status" />',
//            formatter: "select",
//            editoptions: {value: '10:<spring:message code="sp.commodityBatch.sale" />;20:<spring:message code="sp.commodityBatch.sold.out" />'},
//            width:120},
//        {name: 'taddTime', index: 'TADD_TIME', label: '<spring:message code="sp.brand.add.date" />',formatter: function (value) {
//            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
//        },width:160},
        {name: "process",title:false, index: "process", label: '<spring:message code="main.operation" />', sortable: false, frozen: true, width: 200}
    ];

    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
            var data = $("#gridTable").jqGrid('getRowData');
            var merchantCode = "${SESSION_KEY_SMERCHANT_CODE}";
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = '';
                <shiro:hasPermission name="SP_COMMODITY_BATCH_VIEW">
                str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"viewMethod('"
                    + cl + "')\" title=\"<spring:message code='sh.view' />\" alt=\"<spring:message code='sh.view' />\" /> | ";
                </shiro:hasPermission>
                if (merchantCode == data[i].smerchantCode) {
                    <shiro:hasPermission name="SP_COMMODITY_BATCH_EDIT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                        + cl + "')\" title=\"<spring:message code='main.edit' />\" alt=\"<spring:message code='main.edit' />\" /> | ";
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
            sortname: "tadd_Time",
            sortorder: "desc",
            multiselect: false

        };
        var commodityId = $("#scommodityId").val();
        initTable(ctx+"/commodity/commodityBatch/single/queryData?sid=" + commodityId ,colModel,tableCallBack,config);
    });
    var initBtnConfig = {
        addUrl: ctx+'/commodity/commodityBatch/toAddBatch?sid='+$("#scommodityId").val(),
        addTitle: '<spring:message code="sp.commodityBatch.add.product.batch.information" />',
        addFn:showLayerMedium
    };

    initBtnByForm3(initBtnConfig);

    function viewMethod(sid) {
        showLayerMax('<spring:message code="sp.commodityBatch.details" />', ctx + '/commodity/commodityBatch/view?sid=' + sid);
    }
    function editMethod(sid) {
        showLayerMin('<spring:message code="sp.commodityBatch.edit.product.batch.information" />', ctx + '/commodity/commodityBatch/toEditBatch?sid=' + sid);
    }

</script>
</body>
</html>



