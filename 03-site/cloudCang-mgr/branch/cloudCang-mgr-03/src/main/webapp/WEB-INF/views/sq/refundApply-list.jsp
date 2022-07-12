<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title><spring:message code="sq.order.refund.record" /></title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet"/>
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css" rel="stylesheet"/>
</head>
<style>
    .ui-jqgrid tr.jqgrow td {
        white-space: normal !important;
        height: auto;
    }
</style>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code="sq.order.refund.record" /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">

                            <div class="layui-input-inline">
                                <input type="text" name="srefundNo" id="srefundNo" value="" placeholder='<spring:message code="sq.refund.number" />'
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="spaySerialNumber" id="spaySerialNumber" value=""
                                       placeholder='<spring:message code="sq.trade.serial.number" />'
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sorderCode" id="sorderCode" value="" placeholder='<spring:message code="main.order.number" />'
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="smemberName" id="smemberName" value="" placeholder='<spring:message code="main.member.username" />'
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="stransactionId" id="stransactionId" value=""
                                       placeholder='<spring:message code="sq.third.party.payment.serial.number" />'
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="queryFinishDatetime" id="queryFinishDatetime"
                                       placeholder='<spring:message code="sq.refund.completion.time" />'
                                       class="layui-input">
                            </div>
                            <%-- <div class="layui-input-inline">
                                 <cang:select name="scurrency" id="scurrency" type="dic" entire="true" groupNo="SP000016"
                                              entireName='springMessageCode=sq.please.select.a.currency'/>
                             </div>--%>
                            <div class="layui-input-inline">
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
<script type="text/javascript">

    var colModel = [{name: 'id', index: 'id', hidden: true},
        {name: 'srefundNo', index: 'SREFUND_NO', label: '<spring:message code="sq.refund.number" />', width: 150},
        {name: 'spaySerialNumber', index: 'SPAY_SERIAL_NUMBER', label: '<spring:message code="sq.trade.serial.number" />', width: 250},
        {name: 'sorderCode', index: 'SORDER_CODE', label: '<spring:message code="main.order.number" />', width: 150},
        {name: 'smemberNameDesensitize', index: 'SMEMBER_NAME', label: '<spring:message code="main.member.username" />', width:160},
        {name: 'stransactionId', index: 'STRANSACTION_ID', label: '<spring:message code="sq.third.party.payment.serial.number" />', width: 260},
        {name: 'ftotalAmount', index: 'FTOTAL_AMOUNT', label: '<spring:message code="sq.total.order.amount.yuan" />', width: 210},
        {name: 'frefundAmount', index: 'FREFUND_AMOUNT', label: '<spring:message code="sq.total.refund.yuan" />', width: 165},
        {
            name: 'factualRefundAmout',
            index: 'FACTUAL_REFUND_AMOUT',
            label: '<spring:message code="sq.actual.total.amount.of.refund.(yuan)" />',
            width: 260,
            formatter: function (cellValue) {
                return cellValue;
            }
        },
        {
            name: 'istatus', index: 'ISTATUS', label: '<spring:message code="sq.application.status" />', width: 160,
            formatter: function (value) {
                return '<span class="' + (({
                        "10": "istatus-warm",
                        "20": "istatus-normal",
                        "30": "istatus-danger"
                    })[value]) + '">' + (({"10": '● <spring:message code="sq.refund.application.in.progress" />', "20": '● <spring:message code="sq.refund.success" />', "30": '● <spring:message code="sq.refund.failure" />'})[value]) + '</span>'
            }
        },
        {
            name: 'tfinishDatetime',
            index: 'TFINISH_DATETIME',
            label: '<spring:message code="sq.refund.completion.time" />',
            width: 200,
            formatter: function (value) {
                if (isEmpty(value)) {
                    return '';
                }
                return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
            }
        }
    ];
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
        });
    }

    $(function () {
        var config = {
            autowidth: false,
            width:$(".ibox-content").width(),
            autoScroll: true,
            sortname: "TFINISH_DATETIME",
            sortorder: "desc",
            multiselect: false,
            footerrow: true,//分页上添加一行，用于显示统计信息
            userDataOnFooter: true
        };
        initTable(ctx + "/refundApply/queryData", colModel, tableCallBack, config,loadCompleteFn);

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
        laydate.render({
            elem: '#queryFinishDatetime', //指定元素,查询订单时间
            range: true,
            type: 'date'
        });
    });

    function getPageFooterTotal(totalRow) {//处理合计
        var pageObj = {
            factualRefundAmout: 0

        };
        $.each(totalRow, function (i, item) {
            pageObj.factualRefundAmout += parseFloat(item.factualRefundAmout);

        });
        $("tr.footRow2").remove();
        var $footerRow = $("tr.footrow");
        $footerRow.after("<tr role='row' class='footrow footRow2 footrow-ltr ui-widget-content'>" + $footerRow.html() + "</tr>");
        var $newFooterRow = $("tr.footRow2");
        $("#gridTable").footerData("set", {
            "frefundAmount": '<spring:message code="sq.pages.in.total" />：',
            "factualRefundAmout": formateMoney(pageObj.factualRefundAmout)
        });
        var sdata = getFormValues();
        $.ajax({
            url: ctx + "/refundApply/queryTotalData",
            type: "post",
            dataType: "json",
            data: sdata,
            success: function (resp) {
                if (resp.data == null || resp.data == 'null') {
                    $newFooterRow.find("td[aria-describedby*='frefundAmount']").text('<spring:message code="sq.in.total" />：');
                    $newFooterRow.find("td[aria-describedby*='factualRefundAmout']").text(0.00);
                }
                $newFooterRow.find("td[aria-describedby*='frefundAmount']").text('<spring:message code="sq.in.total" />：');
                $newFooterRow.find("td[aria-describedby*='factualRefundAmout']").text(formateMoney(resp.data));
            }
        });
    }
</script>
</body>
</html>

