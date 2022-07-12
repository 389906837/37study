<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备销售分析报表</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet"/>
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css" rel="stylesheet"/>
<!--Bootstrap双日历 -->
<link rel="stylesheet" type="text/css" href="${staticSource}/resources/css/daterangepicker.css"/>
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
                    <h5>设备销售分析报表</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="deviceName" id="deviceName" value=""
                                       placeholder="设备名..." class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 210px">
                                <input type="text" name="queryTimeCondition" id="queryTimeCondition" placeholder="时间"
                                       class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i
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
<!--bootstrap双日历插件 -->
<script type="text/javascript" src="${staticSource}/resources/js/moment.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/daterangepicker.js"></script>
<!--导出Ex -->
<script src="${staticSource}/resources/js/xlsx.core.min.js"></script>
<script src="${staticSource}/resources/js/tableExport.min.js"></script>
<script src="${staticSource}/resources/js/tableExportComm.js?123"></script>
<script type="text/javascript">
    /**
     * 关闭并加载更新表格
     */
    $(document).ready(function () {
        $('.memberClass i').click(function () {
            $(this).parent().find('input').click();
        });
        $('input[name=memberEchartDatetime]').daterangepicker({

            startDate: moment().subtract(7, 'days'),
            endDate: moment().subtract(1, 'days'),
            maxDate: new Date(),
            alwaysShowCalendars: true,
            locale: {
                format: 'YYYY-MM-DD',
                applyLabel: '确认',
                cancelLabel: '取消',
                fromLabel: '起始时间',
                toLabel: '结束时间',
                weekLabel: 'W',
                customRangeLabel: '选择时间',
                daysOfWeek: ["日", "一", "二", "三", "四", "五", "六"],
                monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
            },
            ranges: {
                '今日': [moment().startOf('day'), moment()],
                '昨日': [moment().subtract('days', 1).startOf('day'), moment().subtract('days', 1).endOf('day')],
                '最近7日': [moment().subtract('days', 6), moment()],
                '最近30日': [moment().subtract('days', 29), moment()]
            }
        }, function (start, end, label) {
            document.getElementById('start').value = start;
            document.getElementById('end').value = end;
        });
    });
    var colModel = [{name: 'deviceId', index: 'DEVICE_ID', hidden: true},
        {name: 'deviceName', index: 'DEVICE_NAME', label: "设备名", width: 120},
        {name: 'deviceSalesAmount', index: 'THIS_YEAR_PAY_AMOUNT', label: "设备销售金额", width: 120},
        {name: 'salesAmountYearOnYear', index: 'salesAmountYearOnYear', label: "销售金额同比增长率", width: 120},
        {name: 'salesAmountAnnulusRatio', index: 'salesAmountAnnulusRatio', label: "销售环比增长率", width: 120},
        {name: 'commodityName', index: 'commodityName', label: "热销商品", width: 120},
        {name: 'maxSalesMonth', index: 'MAX_SALES_MONTH', label: "热销时段(月)", width: 120},
        {
            name: 'payTypeMost', index: 'PAY_TYPE_MOST', label: "支付渠道分析", width: 120, formatter: "select",
            editoptions: {value: '10:账户余额;20:银行卡;30:微信支付;40:支付宝;50:银联支付;60:现金支付;70:第三方支付;80:其他'}
        }
    ];

    $(function () {
        var config = {
            sortname: "",
            sortorder: "",
            footerrow: true,//分页上添加一行，用于显示统计信息
            userDataOnFooter: true
        };
        initTable(ctx + "/deviceReport/queryData", colModel, function () {
            <shiro:hasPermission name="DEVICEREPORT_DOWNLOADEXCEL">
            if (isExport) {//判断是否导出
                exportFileCallback1("设备销售报表导出");
                isExport = false;
            }
            </shiro:hasPermission>
        }, config,loadCompleteFn);
        <shiro:hasPermission name="DEVICEREPORT_DOWNLOADEXCEL">
        addExportBtn1("设备销售报表导出");
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
            deviceSalesAmount: 0
        };
        $.each(totalRow, function (i, item) {

            pageObj.deviceSalesAmount += parseFloat(item.deviceSalesAmount);


        });
        $("tr.footRow2").remove();
        var $footerRow = $("tr.footrow");
        $footerRow.after("<tr role='row' class='footrow footRow2 footrow-ltr ui-widget-content'>" + $footerRow.html() + "</tr>");
        var $newFooterRow = $("tr.footRow2");
        $("#gridTable").footerData("set", {
            "deviceName": "页合计：",
            "deviceSalesAmount": formateMoney(pageObj.deviceSalesAmount)
        });
        var sdata = getFormValues();
        $.ajax({
            url: ctx + "/deviceReport/queryTotalData",
            type: "post",
            dataType: "json",
            data: sdata,
            success: function (resp) {
                if (resp == null || resp.data == null || resp.data == 'null') {
                    $newFooterRow.find("td[aria-describedby*='deviceName']").text("总合计：");
                    $newFooterRow.find("td[aria-describedby*='deviceSalesAmount']").text(0.00);

                }
                $newFooterRow.find("td[aria-describedby*='deviceName']").text("总合计：");
                $newFooterRow.find("td[aria-describedby*='deviceSalesAmount']").text(formateMoney(resp.data));

            }
        });
    }
</script>
</body>

