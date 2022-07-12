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
                    <h5>入库汇总报表</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="commodityCode" id="commodityCode" value=""
                                       placeholder="商品编号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="commodityName" id="commodityName" value="" placeholder="商品名..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="merchantName" id="merchantName" value="" placeholder="所属商户..."
                                       class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 210px">
                                <input type="text" name="enterWarehouseTime" id="enterWarehouseTime" placeholder="入库时间"
                                       class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn layui-btn" id="searchBtn" data-type="query"><i
                                        class="layui-icon">&#xe615;</i>查询
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i
                                        class="layui-icon">&#x1006;</i>清除
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
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/formatUtil.js"></script>

<!--导出Ex -->
<script src="${staticSource}/resources/js/xlsx.core.min.js"></script>
<script src="${staticSource}/resources/js/tableExport.min.js"></script>
<script src="${staticSource}/resources/js/tableExportComm.js?121"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    var colModel = [
        {name: 'commodityCode', index: 'SCOMMODITY_CODE', label: "商品编号", width: 150},
        {name: 'commodityFullName', index: 'commodityFullName', label: "商品名",sortable: false, width: 300},
        {name: 'orderCount', index: 'FORDER_COUNT', label: "入库数量", width: 150},
        {name: 'commodityPrice', index: 'FCOMMODITY_PRICE', label: "商品单价（元）", width: 150},
        {name: 'commodityAmount', index: 'FCOMMODITY_AMOUNT', label: "商品总额（元）", width: 150},
        {name: 'merchantName', index: 'merchantName', label: "所属商户", sortable: false, width: 300}
    ];
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
                exportFileCallback1("入库汇总报表导出");
                isExport = false;
            }
            </shiro:hasPermission>
        }, config,loadCompleteFn);
        <shiro:hasPermission name="ENTERWAREHOUSEREPORT_DOWNLOADEXCEL">
        addExportBtn1("入库汇总报表导出");
        </shiro:hasPermission>
    });
    function loadCompleteFn(xhr) {
        $(".ui-jqgrid-sdiv").show();
        //处理合计
        var GroupList = xhr.rows;
        getPageFooterTotal(GroupList);
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
            "commodityFullName": "页合计：",
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
                    $newFooterRow.find("td[aria-describedby*='commodityFullName']").text("总合计：");
                    $newFooterRow.find("td[aria-describedby*='orderCount']").text(0.00);
                    $newFooterRow.find("td[aria-describedby*='commodityAmount']").text(0.00);
                }
                $newFooterRow.find("td[aria-describedby*='commodityFullName']").text("总合计：");
                $newFooterRow.find("td[aria-describedby*='orderCount']").text(formateMoney(resp.data['FORDER_TOTAL_NUM']));
                $newFooterRow.find("td[aria-describedby*='commodityAmount']").text(formateMoney(resp.data['FCOMMODITY_TOTAL_AMOUNT']));
            }
        });
    }
</script>
</body>
</html>

