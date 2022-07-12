<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>商品分析报表</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet"/>
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css" rel="stylesheet"/>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>商品分析报表</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="commodityName" id="commodityName" value="" placeholder="商品名..."
                                       class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 210px">
                                <input type="text" name="queryTimeCondition" id="queryTimeCondition" placeholder="销售时段"
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

<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<!--导出Ex -->
<script src="${staticSource}/resources/js/xlsx.core.min.js"></script>
<script src="${staticSource}/resources/js/tableExport.min.js"></script>
<script src="${staticSource}/resources/js/tableExportComm.js?1.1000"></script>
<script type="text/javascript">
    var colModel = [{name: 'commodityId', index: 'SCOMMODITY_ID', hidden: true},
        {name: 'commodityFullName', index: 'commodityFullName', label: "商品名", width: 120, sortable: false},
        {name: 'commoditySaleNum', index: 'commoditySaleNum', label: "商品销售总数量", width: 120},
        {name: 'commoditySalesVolume', index: 'commoditySalesVolume', label: "商品销售额", width: 120},
        {
            name: 'commodityGrossProfit',
            index: 'commodityGrossProfit',
            label: "商品毛利润",
            width: 120,
            sortable: false
        },
        {name: 'commodityNetProfit', index: 'commodityNetProfit', label: "商品净利润", width: 120, sortable: false},
        {name: 'grossInterestRate', index: 'grossInterestRate', label: "毛利率", sortable: false, width: 120},
        {name: 'maxSaleMounth', index: 'maxSaleMounth', label: "销售量最高时段(月)", width: 120, sortable: false}
    ];

    $(function () {
        var config = {
            sortname: "",
            sortorder: "",
            footerrow: true,//分页上添加一行，用于显示统计信息
            userDataOnFooter: true
        };
        initTable(ctx + "/commodityReport/queryData", colModel, function () {
            <shiro:hasPermission name="COMMODITYREPORT_DOWNLOADEXCEL">
            if (isExport) {//判断是否导出
                exportFileCallback1("商品分析报表导出");
                isExport = false;
            }
            </shiro:hasPermission>
        }, config,loadCompleteFn);
        <shiro:hasPermission name="COMMODITYREPORT_DOWNLOADEXCEL">
        addExportBtn1("商品分析报表导出");
        </shiro:hasPermission>
    });
    function loadCompleteFn(xhr) {
        $(".ui-jqgrid-sdiv").show();
        //处理合计
        var GroupList = xhr.rows;
        getPageFooterTotal(GroupList);
    }

    initBtnByForm2();

    layui.use(['form', 'laydate'], function () {
        var laydate = layui.laydate;
        //查询出库时间
        laydate.render({
            elem: '#queryTimeCondition', //指定元素
            range: true,
            type: 'date'
        });
    });

    function getPageFooterTotal(totalRow) {//处理合计
        var pageObj = {
            commoditySalesVolume: 0,
            commodityGrossProfit: 0,
            commodityNetProfit: 0

        };
        $.each(totalRow, function (i, item) {

            pageObj.commoditySalesVolume += parseFloat(item.commoditySalesVolume);

            pageObj.commodityGrossProfit += parseFloat(item.commodityGrossProfit);
            pageObj.commodityNetProfit += parseFloat(item.commodityNetProfit);

        });
        $("tr.footRow2").remove();
        var $footerRow = $("tr.footrow");
        $footerRow.after("<tr role='row' class='footrow footRow2 footrow-ltr ui-widget-content'>" + $footerRow.html() + "</tr>");
        var $newFooterRow = $("tr.footRow2");
        $("#gridTable").footerData("set", {
            "commoditySaleNum": "页合计：",
            "commoditySalesVolume": formateMoney(pageObj.commoditySalesVolume),
            "commodityGrossProfit": formateMoney(pageObj.commodityGrossProfit),
            "commodityNetProfit": formateMoney(pageObj.commodityNetProfit)
        });
        var sdata = getFormValues();
        $.ajax({
            url: ctx + "/commodityReport/queryTotalData",
            type: "post",
            dataType: "json",
            data: sdata,
            success: function (resp) {
                if (resp.data == null || resp.data == 'null') {
                    $newFooterRow.find("td[aria-describedby*='commoditySaleNum']").text("总合计：");
                    $newFooterRow.find("td[aria-describedby*='commoditySalesVolume']").text(0.00);
                    $newFooterRow.find("td[aria-describedby*='commodityGrossProfit']").text(0.00);
                    $newFooterRow.find("td[aria-describedby*='commodityNetProfit']").text(0.00);
                }
                $newFooterRow.find("td[aria-describedby*='commoditySaleNum']").text("总合计：");
                $newFooterRow.find("td[aria-describedby*='commoditySalesVolume']").text(formateMoney(resp.data['COMMODITY_SALES_VALUE_TOTAL']));
                $newFooterRow.find("td[aria-describedby*='commodityGrossProfit']").text(formateMoney(resp.data['COMMODITY_GROSSPROFIT_TOTAL']));
                $newFooterRow.find("td[aria-describedby*='commodityNetProfit']").text(formateMoney(resp.data['COMMODITY_NETPROFIT_TOTAL']));
            }
        });
    }
</script>
</body>
</html>

