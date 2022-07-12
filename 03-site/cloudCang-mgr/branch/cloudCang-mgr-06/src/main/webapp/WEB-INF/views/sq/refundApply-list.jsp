<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>订单退款记录</title>
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
                    <h5>订单退款记录</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">

                            <div class="layui-input-inline">
                                <input type="text" name="srefundNo" id="srefundNo" value="" placeholder="退款编号"
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="spaySerialNumber" id="spaySerialNumber" value=""
                                       placeholder="交易流水号"
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sorderCode" id="sorderCode" value="" placeholder="订单编号"
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="smemberName" id="smemberName" value="" placeholder="会员用户名"
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="stransactionId" id="stransactionId" value=""
                                       placeholder="第三方支付流水号"
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="queryFinishDatetime" id="queryFinishDatetime"
                                       placeholder="退款完成时间"
                                       class="layui-input">
                            </div>
                            <%-- <div class="layui-input-inline">
                                 <cang:select name="scurrency" id="scurrency" type="dic" entire="true" groupNo="SP000016"
                                              entireName="请选择币种"/>
                             </div>--%>
                            <div class="layui-input-inline">
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
<script type="text/javascript">
    var colModel = [{name: 'id', index: 'id', hidden: true},
        {name: 'srefundNo', index: 'SREFUND_NO', label: "退款编号", width: 230},
        {name: 'spaySerialNumber', index: 'SPAY_SERIAL_NUMBER', label: "交易流水号", width: 400},
        {name: 'sorderCode', index: 'SORDER_CODE', label: "订单编号", width: 250},
        {name: 'smemberNameDesensitize', index: 'SMEMBER_NAME', label: "会员用户名", width: 170},
        {name: 'stransactionId', index: 'STRANSACTION_ID', label: "第三方支付流水号", width: 400},
        {name: 'ftotalAmount', index: 'FTOTAL_AMOUNT', label: "订单总额（元）", width: 180},
        {name: 'frefundAmount', index: 'FREFUND_AMOUNT', label: "退款总额（元）", width: 180},
        {
            name: 'factualRefundAmout',
            index: 'FACTUAL_REFUND_AMOUT',
            label: "实际退款总额（元）",
            width: 230,
            formatter: function (cellValue) {
                return cellValue;
            }
        },
        {
            name: 'istatus', index: 'ISTATUS', label: "申请状态", width: 170,
            formatter: function (value) {
                return '<span class="' + (({
                        "10": "istatus-warm",
                        "20": "istatus-normal",
                        "30": "istatus-danger"
                    })[value]) + '">' + (({"10": "● 退款申请中", "20": "● 退款成功", "30": "● 退款失败"})[value]) + '</span>'
            }
        },
        {
            name: 'tfinishDatetime',
            index: 'TFINISH_DATETIME',
            label: "退款完成时间",
            width: 260,
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
        });
    }


    $(function () {
        var config = {
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
            "frefundAmount": "页合计：",
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
                    $newFooterRow.find("td[aria-describedby*='frefundAmount']").text("总合计：");
                    $newFooterRow.find("td[aria-describedby*='factualRefundAmout']").text(0.00);
                }
                $newFooterRow.find("td[aria-describedby*='frefundAmount']").text("总合计：");
                $newFooterRow.find("td[aria-describedby*='factualRefundAmout']").text(formateMoney(resp.data));
            }
        });
    }
</script>
</body>
</html>

