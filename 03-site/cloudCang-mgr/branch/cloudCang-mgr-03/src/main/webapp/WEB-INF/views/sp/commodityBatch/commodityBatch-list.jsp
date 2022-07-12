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
                <div class="ibox-title">
                    <h5><spring:message code="sp.commodityBatch.batch.management" /></h5>
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
                                <input type="text" name="scommodityCode" id="scommodityCode" value=""
                                       placeholder='<spring:message code="main.product.number" />...' class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="commodityName" id="commodityName" value=""
                                       placeholder='<spring:message code="main.product.name" />...' class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sbatchNo" id="sbatchNo" value="" placeholder='<spring:message code="sm.batch.number" />...'
                                       class="layui-input">
                            </div>
                            <%--<div class="layui-input-inline">--%>
                            <%--<select class="form-control" name="istatus" id="istatus">--%>
                            <%--<option value=""><spring:message code="sp.commodityBatch.operation.state" /></option>--%>
                            <%--<option value="10"><spring:message code="main.enable" /></option>--%>
                            <%--<option value="20"><spring:message code='main.disable' /></option>--%>
                            <%--</select>--%>
                            <%--</div>--%>
                            <div class="layui-input-inline">
                                <select class="form-control" name="istockStatus" id="istockStatus">
                                    <option value=""><spring:message code="sp.commodityBatch.inventory.status" /></option>
                                    <option value="10"><spring:message code="sp.commodityBatch.waiting.to.be.put.on.the.shelves" /></option>
                                    <option value="20"><spring:message code="sp.commodityBatch.partly.on.the.shelf" /></option>
                                    <option value="30"><spring:message code="sp.commodityBatch.all.shelves" /></option>
                                </select>
                            </div>
                        </div>
                        <div class="layui-form-item">

                            <%--<div class="layui-input-inline">--%>
                            <%--<select class="form-control" name="isaleStatus" id="isaleStatus">--%>
                            <%--<option value=""><spring:message code="sp.commodityBatch.sales.status" /></option>--%>
                            <%--<option value="10"><spring:message code="sp.commodityBatch.sale" /></option>--%>
                            <%--<option value="20"><spring:message code="sp.commodityBatch.sold.out" /></option>--%>
                            <%--</select>--%>
                            <%--</div>--%>
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
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode', label: '<spring:message code="main.merchant.number" />', index: 'SMERCHANT_CODE', width: 150},
        {name: 'merchantName', label: '<spring:message code="main.merchant.name" />', index: 'merchantName', sortable: false, width: 200},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'smerchantCode', index: 'smerchant_code', hidden: true},
        </c:if>
        {name: 'scommodityCode', index: 'SCOMMODITY_CODE', label: '<spring:message code="main.product.number" />', width: 140},
        {name: 'commodityFullName', index: 'commodityName', label: '<spring:message code="main.product.name" />', width: 230},
        {name: 'sbatchNo', index: 'SBATCH_NO', label: '<spring:message code="sm.batch.number" />', width: 160},
        {name: 'fcostAmount', index: 'FCOST_AMOUNT', label: '<spring:message code="sp.commodityBatch.cost.price" />', width: 110},
        {name: 'ftaxPoint', index: 'FTAX_POINT', label: '<spring:message code="sp.commodityBatch.tax.point" />', width: 100},
        {name: 'icommodityNum', index: 'ICOMMODITY_NUM', label: '<spring:message code="sp.commodityBatch.product.number" />', width: 110},
        {name: 'ishelfNum', index: 'ISHELF_NUM', label: '<spring:message code="sp.commodityBatch.number.of.stores" />', width: 120},
        {name: 'ilossGoodsNum', index: 'ILOSS_GOODS_NUM', label: '<spring:message code="sp.commodityBatch.number.of.damage" />', width: 140},
        {
            name: 'dproduceDate', index: 'DPRODUCE_DATE', label : '<spring:message code="sm.date.of.production" />', formatter: function (value) {
            return formatDate(new Date(value), 'yyyy-MM-dd');
        }, width: 130
        },
        {
            name: 'dexpiredDate', index: 'DEXPIRED_DATE', label : '<spring:message code="sm.expiration.date" />', formatter: function (value) {
            if (isEmpty(value)) {
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd');
        }, width: 140
        },
        {
            name: 'istockStatus',
            index: 'ISTOCK_STATUS',
            label: '<spring:message code="sp.commodityBatch.inventory.status" />',
            formatter: "select",
            editoptions: {value: '10:<spring:message code="sp.commodityBatch.waiting.to.be.put.on.the.shelves" />;20:<spring:message code="sp.commodityBatch.partly.on.the.shelf" />;30:<spring:message code="sp.commodityBatch.all.shelves" />'},
            width: 140
        },
        {name: "process", title: false, index: "process", label: '<spring:message code="main.operation" />', sortable: false, frozen: true, width: 200}
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
        initTable(ctx + "/commodity/commodityBatch/queryData", colModel, tableCallBack, config);
    });

    initBtnByForm2();

    function viewMethod(sid) {
        showLayerMedium('<spring:message code="sp.commodityBatch.product.batch.details" />', ctx + '/commodity/commodityBatch/view?sid=' + sid);
    }
    function editMethod(sid) {
        showLayerMin('<spring:message code="sp.commodityBatch.edit.product.batch.information" />', ctx + '/commodity/commodityBatch/toEditBatch?sid=' + sid);
    }

</script>
</body>
</html>



