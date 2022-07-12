<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>入库汇总报表</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet"/>
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css" rel="stylesheet"/>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code="report.storage.summary.report" /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="commodityCode" id="commodityCode" value=""
                                       placeholder='<spring:message code="report.commodity.number" />...'
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="commodityName" id="commodityName" value="" placeholder='<spring:message code="report.commodity.name" />...'
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="merchantName" id="merchantName" value="" placeholder='<spring:message code="report.subordinate.merchants" />...'
                                       class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 210px">
                                <input type="text" name="enterWarehouseTime" id="enterWarehouseTime" placeholder='<spring:message code="report.storage.time" />'
                                       class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn layui-btn" id="searchBtn" data-type="query"><i
                                        class="layui-icon">&#xe615;</i><spring:message code="main.query" />
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i
                                        class="layui-icon">&#x1006;</i><spring:message code="main.clear" />
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
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/formatUtil.js"></script>

<!--导出Ex -->
<script src="${staticSource}/resources/js/xlsx.core.min.js"></script>
<script src="${staticSource}/resources/js/tableExport.min.js"></script>
<script src="${staticSource}/resources/js/tableExportComm-${currentLanguage}.js"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    var colModel = [
        {name: 'commodityCode', index: 'SCOMMODITY_CODE', label: '<spring:message code="report.commodity.number" />', width: 200},
        {name: 'commodityFullName', index: 'commodityFullName', label: '<spring:message code="report.commodity.name" />',sortable: false, width: 320},
        {name: 'orderCount', index: 'FORDER_COUNT', label: '<spring:message code="report.input.quantity" />', width: 160},
        {name: 'commodityPrice', index: 'FCOMMODITY_PRICE', label: '<spring:message code="report.unit.price.of.commodity.yuan" />', width: 250},
        {name: 'commodityAmount', index: 'FCOMMODITY_AMOUNT', label: '<spring:message code="report.total.merchandise.yuan" />', width: 240},
        {name: 'merchantName', index: 'merchantName', label: '<spring:message code="report.subordinate.merchants" />', sortable: false, width: 380}
    ];
    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
        }, 0);
    }
    $(function () {
        var config = {
            sortname: "TADD_TIME",
            sortorder: "desc",
            footerrow: true,//分页上添加一行，用于显示统计信息
            userDataOnFooter: true
        };
        initTable(ctx + "/enterWarehouseReport/queryData", colModel, function () {
            <shiro:hasPermission name="ENTERWAREHOUSEREPORT_DOWNLOADEXCEL">
            if (isExport) {//判断是否导出
                exportFileCallback1('<spring:message code="report.inbound.summary.report.export" />');
                isExport = false;
            }
            </shiro:hasPermission>
        }, config,loadCompleteFn);
        <shiro:hasPermission name="ENTERWAREHOUSEREPORT_DOWNLOADEXCEL">
        addExportBtn1('<spring:message code="report.inbound.summary.report.export" />');
        </shiro:hasPermission>
    });
    function loadCompleteFn(xhr) {
        $(".ui-jqgrid-sdiv").show();
        //处理合计
        var GroupList = xhr.rows;
        getPageFooterTotal(GroupList);
        // 表格回调(滚动条)
        tableCallBack();
    }

    initBtnByForm2();
    layui.use('laydate', function () {
        var laydate = layui.laydate;
        //查询入库时间
        laydate.render({
            elem: '#enterWarehouseTime', //指定元素
            range: true,
            type: 'date'
        });
    });

    function getPageFooterTotal(totalRow) {//处理合计
        var pageObj = {
            orderCount: 0,
            commodityAmount: 0

        };
        $.each(totalRow, function (i, item) {

            pageObj.orderCount += parseFloat(item.orderCount);

            pageObj.commodityAmount += parseFloat(item.commodityAmount);

        });
        $("tr.footRow2").remove();
        var $footerRow = $("tr.footrow");
        $footerRow.after("<tr role='row' class='footrow footRow2 footrow-ltr ui-widget-content'>" + $footerRow.html() + "</tr>");
        var $newFooterRow = $("tr.footRow2");
        $("#gridTable").footerData("set", {
            "commodityFullName": '<spring:message code="report.total.page" />：',
            "orderCount": formateMoney(pageObj.orderCount),
            "commodityAmount": formateMoney(pageObj.commodityAmount)
        });
        var sdata = getFormValues();
        $.ajax({
            url: ctx + "/enterWarehouseReport/queryTotalData",
            type: "post",
            dataType: "json",
            data: sdata,
            success: function (resp) {
                if (resp.data == null || resp.data == 'null') {
                    $newFooterRow.find("td[aria-describedby*='commodityFullName']").text('<spring:message code="report.in.total" />：');
                    $newFooterRow.find("td[aria-describedby*='orderCount']").text(0.00);
                    $newFooterRow.find("td[aria-describedby*='commodityAmount']").text(0.00);
                }
                $newFooterRow.find("td[aria-describedby*='commodityFullName']").text('<spring:message code="report.in.total" />：');
                $newFooterRow.find("td[aria-describedby*='orderCount']").text(formateMoney(resp.data['FORDER_TOTAL_NUM']));
                $newFooterRow.find("td[aria-describedby*='commodityAmount']").text(formateMoney(resp.data['FCOMMODITY_TOTAL_AMOUNT']));
            }
        });
    }
</script>
</body>
</html>

