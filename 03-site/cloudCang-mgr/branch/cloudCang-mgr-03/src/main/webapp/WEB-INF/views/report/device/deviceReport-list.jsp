<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title><spring:message code="report.equipment.sales.analysis.report" /></title>
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
                    <h5><spring:message code="report.equipment.sales.analysis.report" /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="deviceName" id="deviceName" value=""
                                       placeholder='<spring:message code="report.equipment.name" />...' class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 210px">
                                <input type="text" name="queryTimeCondition" id="queryTimeCondition" placeholder="<spring:message code='main.time' />"
                                       class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i
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
                applyLabel: '<spring:message code="report.confirm" />',
                cancelLabel: '<spring:message code="main.cancel" />',
                fromLabel: '<spring:message code="report.start.time" />',
                toLabel: '<spring:message code="report.end.time" />',
                weekLabel: 'W',
                customRangeLabel: '<spring:message code="report.selection.period" />',
                daysOfWeek: ['<spring:message code="report.sun" />', '<spring:message code="report.mon" />', '<spring:message code="report.tue" />', '<spring:message code="report.wed" />', '<spring:message code="report.thu" />', '<spring:message code="report.fri" />', '<spring:message code="report.sat" />'],
                monthNames: ['<spring:message code="report.jan" />', '<spring:message code="report.feb" />', '<spring:message code="report.mar" />', '<spring:message code="report.apr" />', '<spring:message code="report.may" />', '<spring:message code="report.jun" />', '<spring:message code="report.jul" />',
                             '<spring:message code="report.aug" />', '<spring:message code="report.sept" />', '<spring:message code="report.oct" />', '<spring:message code="report.nov" />', '<spring:message code="report.dec" />']
            },
            ranges: {
                '<spring:message code="report.today" />': [moment().startOf('day'), moment()],
                '<spring:message code="report.yesterday" />': [moment().subtract('days', 1).startOf('day'), moment().subtract('days', 1).endOf('day')],
                '<spring:message code="report.last.7.days" />': [moment().subtract('days', 6), moment()],
                '<spring:message code="report.last.30.days" />': [moment().subtract('days', 29), moment()]
            }
        }, function (start, end, label) {
            document.getElementById('start').value = start;
            document.getElementById('end').value = end;
        });
    });
    var colModel = [{name: 'deviceId', index: 'DEVICE_ID', hidden: true},
        {name: 'deviceName', index: 'DEVICE_NAME', label: '<spring:message code="report.equipment.name" />', width: 220},
        {name: 'deviceSalesAmount', index: 'THIS_YEAR_PAY_AMOUNT', label: '<spring:message code="report.equipment.sales.amount" />', width: 220},
        {name: 'salesAmountYearOnYear', index: 'salesAmountYearOnYear', label: '<spring:message code="report.year-on-year.growth.in.sales" />', width: 230},
        {name: 'salesAmountAnnulusRatio', index: 'salesAmountAnnulusRatio', label: '<spring:message code="report.sales.growth.rate" />', width: 220},
        {name: 'commodityName', index: 'commodityName', label: '<spring:message code="report.hot.goods" />', width: 220},
        {name: 'maxSalesMonth', index: 'MAX_SALES_MONTH', label: '<spring:message code="report.hot.sale.period.month" />', width: 220},
        {
            name: 'payTypeMost', index: 'PAY_TYPE_MOST', label: '<spring:message code="report.payment.channel.analysis" />', width: 220, formatter: "select",
            editoptions: {value: '10:<spring:message code="report.account.balance" />;20:<spring:message code="report.bank.card" />;30:<spring:message code="report.weChat.payment" />;40:<spring:message code="report.alipay" />;50:<spring:message code="report.unionpay.payment" />;60:<spring:message code="report.cash.payment" />;70:<spring:message code="report.third.party.payment" />;80:<spring:message code="report.other" />'}
        }
    ];
    // 表格回调
    function tableCallBack() {
        $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
    }
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
                exportFileCallback1('<spring:message code="report.device.sales.report.export" />');
                isExport = false;
            }
            </shiro:hasPermission>
        }, config,loadCompleteFn);
        <shiro:hasPermission name="DEVICEREPORT_DOWNLOADEXCEL">
        addExportBtn1('<spring:message code="report.device.sales.report.export" />');
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
            "deviceName": '<spring:message code="report.total.page" />：',
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
                    $newFooterRow.find("td[aria-describedby*='deviceName']").text('<spring:message code="report.in.total" />：');
                    $newFooterRow.find("td[aria-describedby*='deviceSalesAmount']").text(0.00);

                }
                $newFooterRow.find("td[aria-describedby*='deviceName']").text('<spring:message code="report.in.total" />：');
                $newFooterRow.find("td[aria-describedby*='deviceSalesAmount']").text(formateMoney(resp.data));

            }
        });
    }
</script>
</body>

