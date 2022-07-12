<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>订单管理</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet"/>
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css" rel="stylesheet"/>
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
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
                    <h5>订单管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">

                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-input-inline">
                                    <input type="text" name="smerchantCode" id="smerchantCode" value=""
                                           placeholder="商户编号..." class="layui-input">
                                </div>
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantName" id="merchantName" value=""
                                           placeholder="商户名称..." class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-input-inline">
                                <input type="text" name="sorderCode" id="sorderCode" value=""
                                       placeholder="订单编号..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="spaySerialNumber" id="spaySerialNumber" value=""
                                       placeholder="交易流水号..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sdeviceCode" id="sdeviceCode" value=""
                                       placeholder="设备编号..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sdeviceName" id="sdeviceName" value=""
                                       placeholder="设备名称..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sdeviceAddress" id="sdeviceAddress" value=""
                                       placeholder="设备地址..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="smemberName" id="smemberName" value=""
                                       placeholder="会员用户名..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" id="ichargebackWay" name="ichargebackWay" value=""
                                             list="{10:商户代扣,20:主动支付,30:单次代扣}" entire="true"
                                             entireName="请选择扣款方式"/>
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" id="ipayType" name="ipayType" value=""
                                             list="{30:微信支付,40:支付宝}"
                                             entire="true"
                                             entireName="请选择支付方式"/>
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" id="istatus" name="istatus" value=""
                                             list="{10:付款成功,20:付款失败,100:待付款,110:付款处理中}" entire="true"
                                             entireName="请选择订单状态"/>
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="queryTimeCondition" id="queryTimeCondition" placeholder="订单时间"
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="payTimeCondition" id="payTimeCondition" placeholder="支付完成时间"
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


<script src="${staticSource}/resources/js/xlsx.core.min.js"></script>
<script src="${staticSource}/resources/js/tableExport.min.js"></script>
<script src="${staticSource}/resources/js/tableExportComm.js"></script>
<script type="text/javascript">
    function closeLayerAndRefresh(index) {
        layer.close(index);
        reloadGrid(true);
    }
    $(function () {
        $.jgrid.defaults.styleUI = 'Bootstrap';
        $("#gridTable").jqGrid({
            url: ctx + "/orderRecord/queryData",
            datatype: "json",
            height: 'auto',
            autowidth: false,
            width: $(".ibox-content").width(),
            shrinkToFit: false,
            autoScroll: true,
            rowNum: rownum,
            rowList: rowList,
            rownumbers: true,
            colModel: [{name: 'id', index: 'id', hidden: true},
                {name: 'sorderCode', index: 'SORDER_CODE', label: "订单编号", width: 150},
                {name: 'sdeviceCode', index: 'SDEVICE_CODE', label: "设备编号", width: 120},
                {name: 'sdeviceName', index: 'SDEVICE_NAME', label: "设备名称", width: 200},
                {name: 'smemberNameDesensitize', index: 'SMEMBER_NAME', label: "会员用户名", width: 130},
                {
                    name: 'factualPayAmount',
                    index: 'FACTUAL_PAY_AMOUNT',
                    label: "实付总额（元）",
                    width: 125,
                    formatter: function (cellValue) {
                        return cellValue;
                    }
                },
                {
                    name: 'istatus', index: 'ISTATUS', label: "状态", width: 100,
                    formatter: function (value, options, rowObject) {
                        return '<span id="istatus_' + rowObject.id + '" data="' + value + '" class="' + (({
                                "10": "istatus-normal",
                                "20": "istatus-danger",
                                "30": "istatus-danger",
                                "40": "istatus-normal",
                                "50": "",
                                "100": "istatus-warm",
                                "110": "istatus-warm"
                            })[value]) + '">' + (({
                                "10": "● 付款成功",
                                "20": "● 付款失败",
                                "30": "● 订单异常",
                                "40": "● 异常处理成功",
                                "50": "● 异常处理忽略",
                                "100": "● 待付款",
                                "110": "● 付款处理中"
                            })[value]) + '</span>'
                    }
                },
                {
                    name: 'torderTime', index: 'TORDER_TIME', label: "订单时间", width: 150,
                    formatter: function (value) {
                        if (isEmpty(value)) {
                            return "";
                        }
                        return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
                    }
                },
                {
                    name: 'factualRefundAmount',
                    index: 'FACTUAL_REFUND_AMOUNT',
                    label: "退款总额（元）",
                    width: 125,
                    formatter: function (cellValue) {
                        return cellValue;
                    }
                },
                {
                    name: 'tpayCompleteTime',
                    index: 'TPAY_COMPLETE_TIME',
                    label: "支付完成时间",
                    width: 160,
                    formatter: function (value) {
                        if (isEmpty(value)) {
                            return "";
                        }
                        return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
                    }
                },
                {
                    name: 'ftotalAmount',
                    index: 'FTOTAL_AMOUNT',
                    label: "总金额（元）",
                    width: 110,
                    formatter: function (cellValue) {
                        return cellValue;
                    }
                },
                {
                    name: 'fdiscountAmount',
                    index: 'FDISCOUNT_AMOUNT',
                    label: "优惠总额（元）",
                    width: 133,
                    formatter: function (cellValue) {
                        return cellValue;
                    }
                },
                {
                    name: 'ffirstDiscountAmount',
                    index: 'FFIRST_DISCOUNT_AMOUNT',
                    label: "首单优惠金额（元）",
                    width: 150,
                    formatter: function (cellValue) {
                        return cellValue;
                    }
                },
                {
                    name: 'fpromotionDiscountAmount',
                    index: 'FPROMOTION_DISCOUNT_AMOUNT',
                    label: "促销优惠金额（元）",
                    width: 135,
                    formatter: function (cellValue) {
                        return cellValue;
                    }
                },
                {
                    name: 'fcouponDeductionAmount',
                    index: 'FCOUPON_DEDUCTION_AMOUNT',
                    label: "券抵扣金额（元）",
                    width: 140,
                    formatter: function (cellValue) {
                        return cellValue;
                    }
                },
                {name: 'spaySerialNumber', index: 'SPAY_SERIAL_NUMBER', label: "交易流水号", width: 240},
                {name: 'sdeviceAddress', index: 'SDEVICE_ADDRESS', label: "设备地址", width: 280},
                <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                {name: 'smerchantCode', label: "商户编号", index: 'smerchant_code', sortable: false, width: 180},
                {name: 'merchantName', label: "商户名称", index: 'merchantName', sortable: false, width: 280},
                </c:if>
                <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
                {name: 'smerchantCode', index: 'smerchant_code', hidden: true},
                </c:if>
                {
                    name: 'ichargebackWay', index: 'ICHARGEBACK_WAY', label: "扣款方式", width: 100, formatter: "select",
                    editoptions: {value: '10:商户代扣;20:主动支付;30:单次代扣'}
                },
                {
                    name: 'ipayType', index: 'IPAY_TYPE', label: "支付类型", width: 100, formatter: "select",
                    editoptions: {value: '10:账户余额;20:银行卡;30:微信支付;40:支付宝;50:银联支付;60:现金支付;70:第三方支付;80:其他'}
                },
                {name: 'process', title: false, index: 'process', label: "操作", sortable: false, width: 150}
            ],
            pager: "#gridPager",
            viewrecords: true,
            sortname: "TORDER_TIME",
            sortorder: "desc",
            footerrow: true,//分页上添加一行，用于显示统计信息
            userDataOnFooter: true,
            loadComplete: function (xhr) {
                $(".ui-jqgrid-sdiv").show();
                //处理合计
                var GroupList = xhr.rows;
                getPageFooterTotal(GroupList);
            },
            gridComplete: function () {
                setTimeout(function () {
                    $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
                    var ids = $("#gridTable").jqGrid('getRowData');
                    var merchantCode = "${SESSION_KEY_SMERCHANT_CODE}";
                    for (var i = 0; i < ids.length; i++) {
                        var str = "";
                        var cl = ids[i].id;
                        if (merchantCode == ids[i].smerchantCode) {
                            if (110 == $('#istatus_' + cl).attr('data')) {
                                <shiro:hasPermission name="ORDERRECORD_RA">
                                str += "<img src='${staticSource}/resources/images/order/buchuli.png' class=\"order\" onclick=\"rechargeAgainMethod('"
                                    + cl + "')\" title='补处理' alt='补处理'  /> | ";
                                </shiro:hasPermission>
                            }
                            if (10 != $('#istatus_' + cl).attr('data')) {
                                <shiro:hasPermission name="ORDERRECORD_EDIT">
                                str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                                    + cl + "')\" title='编辑订单' alt='编辑订单'  /> | ";
                                </shiro:hasPermission>
                            }
                            if (20 == $('#istatus_' + cl).attr('data') || 100 == $('#istatus_' + cl).attr('data')) {
                                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                                    + cl + "')\" title='删除订单' alt='删除订单'  /> | ";
                            }
                        }
                        $("#gridTable").jqGrid('setRowData',
                            cl, {
                                process: str.substr(0, str.lastIndexOf(" | "))
                            });
                    }
                }, 0);
                /*     var rowNum = parseInt($(this).getGridParam('records'), 10);
                 if (rowNum > 0) {
                 $(".ui-jqgrid-sdiv").show();
                 var Hits = jQuery(this).getCol('ftotalAmount', false, 'sum');
                 var hitsSum = $(this).getGridParam('ftotalAmount');
                 $(this).footerData("set", {
                 "Source": "<font color='red'>当前页点击合计<font>",
                 "ftotalAmount": "<font color='red'>" + "当前页合计:" + Hits + "<font>",
                 "CreateUser": "<font color='red'>总点击合计<font>",
                 "fdiscountAmount": "<font color='red'>" + "总合计:" + hitsSum + "<font>"
                 });
                 } else {
                 $(".ui-jqgrid-sdiv").hide();
                 }*/

            },
            subGridOptions: {
                openicon: "ui-icon-carat-1-sw",
                expandOnLoad: false,//设置为true，当子表格数据加载完毕后自动展开
                selectOnExpand: false,//设置为true，点击展开图标（plusicon）将会选择父表格中的此行数据
                reloadOnExpand: false //设置为false，展开时仅加载一次数据，随后的点击图标点击操作只是显示或者隐藏子表格，而不会再发送ajax重新获取数据
            },
            subGrid: true,  // (1)开启子表格支持
            subGridRowExpanded: function (subgrid_id, row_id) {
                //   (2)子表格容器的id和需要展开子表格的行id，将传入此事件函数
                var subgrid_table_id;
                subgrid_table_id = subgrid_id + "_t";   //
                //  (3)根据subgrid_id定义对应的子表格的table的id

                var subgrid_pager_id;
                subgrid_pager_id = subgrid_id + "_pgr"; //
                //    (4)根据subgrid_id定义对应的子表格的pager的id

                // (5)动态添加子报表的table和pager
                $("#" + subgrid_id).html("<table id='" + subgrid_table_id + "'class='scroll'></table><div id='" + subgrid_pager_id + "'class='scroll'></div>");

                // (6)创建jqGrid对象
                $("#" + subgrid_table_id).jqGrid({
                    // (7)子表格数据对应的url，注意传入的参数
                    url: ctx + "/orderRecord/queryDetails?sorderId=" + row_id,
                    datatype: "json",
                    height: 'auto',
                    autowidth: true,
                    shrinkToFit: true,
                    rowNum: rownum,
                    rowList: rowList,
                    rownumbers: true,
                    viewrecords: true,
                    pager: "#chilgridPager",
                    sortname: "TADD_TIME",
                    sortorder: "desc",
                    colModel: [
                        {name: 'scommodityCode', index: 'SCOMMODITY_CODE', label: "商品编号", width: 180},
                        {
                            name: 'commodityFullName',
                            index: 'commodityFullName',
                            label: "商品名称",
                            width: 240,
                            sortable: false
                        },
                        {name: 'fcostAmount', index: 'FCOST_AMOUNT', label: "商品成本（元）", width: 125},
                        {name: 'fcommodityPrice', index: 'FCOMMODITY_PRICE', label: "商品销售单价（元）", width: 135},
                        {name: 'forderCount', index: 'FORDER_COUNT', label: "订单数量", width: 100},
                        {name: 'fcommodityAmount', index: 'FCOMMODITY_AMOUNT', label: "总金额（元）", width: 110},
                        {name: 'fdiscountAmount', index: 'FDISCOUNT_AMOUNT', label: "优惠总额（元）", width: 125},
                        {name: 'ffirstDiscountAmount', index: 'FFIRST_DISCOUNT_AMOUNT', label: "首单优惠金额（元）", width: 135},
                        {
                            name: 'fpromotionDiscountAmount',
                            index: 'FPROMOTION_DISCOUNT_AMOUNT',
                            label: "促销优惠金额（元）",
                            width: 135
                        },
                        {
                            name: 'fcouponDeductionAmount',
                            index: 'FCOUPON_DEDUCTION_AMOUNT',
                            label: "券抵扣金额（元）",
                            width: 140
                        },
                        {name: 'factualAmount', index: 'FACTUAL_AMOUNT', label: "实付金额（元）", width: 125},
                        {name: 'frefundCount', index: 'FREFUND_COUNT', label: "退款数量", width: 100},
                        {name: 'frefundAmount', index: 'FREFUND_AMOUNT', label: "退款金额（元）", width: 125}

                    ]
                });
            }
        });
        //冻结列
        $("#gridTable").jqGrid('setFrozenColumns');
        //导航按钮区域
        $("#gridTable").jqGrid('navGrid', '#gridPager', {edit: false, add: false, del: false, search: false}, {
            height: 200,
            reloadAfterSubmit: true
        });
        <shiro:hasPermission name="ORDERRECORD_DOWNLOAD_EXCEL">
        addExportBtnByUrl("${ctx}/orderRecord/downloadExcel");
        </shiro:hasPermission>
        $(window).bind('resize', function () {
            var width = $('.jqGrid_wrapper').width();
            $('#gridTable').setGridWidth(width);
        });
    });
    layui.use(['form', 'laydate'], function () {
        /**
         * 时间组键
         */
        var laydate = layui.laydate;
        laydate.render({
            elem: '#queryTimeCondition', //指定元素,查询订单时间
            range: true,
            type: 'date'
        });
        laydate.render({
            elem: '#payTimeCondition', //指定元素,查询支付完成时间
            range: true,
            type: 'date'
        });
        var $ = layui.$, active = {
            query: function () {//查询
                resetReloadGrid();
            },
            reset: function () {//清除
                setResetFormValues();
            }
        };
        $('.layui-form .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });
    function rechargeAgainMethod(sid) {
        confirmOperTip("确定要补处理?", ctx + "/orderRecord/rechargeAgain", {checkboxId: sid});
    }
    function delMethod(sid) {
        confirmOperTip("确定要删除?", ctx + "/orderRecord/delete", {checkboxId: sid});
    }
    function editMethod(sid) {
        showLayerMax("编辑订单", ctx + '/orderRecord/edit?sid=' + sid);
    }
    function getPageFooterTotal(totalRow) {//处理合计
        var pageObj = {
            ftotalAmount: 0,
            fdiscountAmount: 0,
            ffirstDiscountAmount: 0,
            fpromotionDiscountAmount: 0,
            fcouponDeductionAmount: 0,
            factualPayAmount: 0,
            factualRefundAmount: 0
        };
        $.each(totalRow, function (i, item) {
            pageObj.ftotalAmount += parseFloat(item.ftotalAmount);
            pageObj.fdiscountAmount += parseFloat(item.fdiscountAmount);
            pageObj.ffirstDiscountAmount += parseFloat(item.ffirstDiscountAmount);
            pageObj.fpromotionDiscountAmount += parseFloat(item.fpromotionDiscountAmount);
            pageObj.fcouponDeductionAmount += parseFloat(item.fcouponDeductionAmount);
            pageObj.factualPayAmount += parseFloat(item.factualPayAmount);
            pageObj.factualRefundAmount += parseFloat(item.factualRefundAmount);
        });
        $("tr.footRow2").remove();
        var $footerRow = $("tr.footrow");
        $footerRow.after("<tr role='row' class='footrow footRow2 footrow-ltr ui-widget-content'>" + $footerRow.html() + "</tr>");
        var $newFooterRow = $("tr.footRow2");
        $("#gridTable").footerData("set", {
            "smemberNameDesensitize": "页合计：",
            "ftotalAmount": formateMoney(pageObj.ftotalAmount),
            "fdiscountAmount": formateMoney(pageObj.fdiscountAmount),
            "ffirstDiscountAmount": formateMoney(pageObj.ffirstDiscountAmount),
            "fpromotionDiscountAmount": formateMoney(pageObj.fpromotionDiscountAmount),
            "fcouponDeductionAmount": formateMoney(pageObj.fcouponDeductionAmount),
            "factualPayAmount": formateMoney(pageObj.factualPayAmount),
            "factualRefundAmount": formateMoney(pageObj.factualRefundAmount)
        });
        var sdata = getFormValues();
        $.ajax({
            url: ctx + "/orderRecord/queryTotalData",
            type: "post",
            dataType: "json",
            data: sdata,
            success: function (resp) {
                if (resp.data == null || resp.data == 'null') {
                    $newFooterRow.find("td[aria-describedby*='smemberNameDesensitize']").text("总合计：");
                    $newFooterRow.find("td[aria-describedby*='ftotalAmount']").text(0.00);
                    $newFooterRow.find("td[aria-describedby*='fdiscountAmount']").text(0.00);
                    $newFooterRow.find("td[aria-describedby*='ffirstDiscountAmount']").text(0.00);
                    $newFooterRow.find("td[aria-describedby*='fpromotionDiscountAmount']").text(0.00);
                    $newFooterRow.find("td[aria-describedby*='fcouponDeductionAmount']").text(0.00);
                    $newFooterRow.find("td[aria-describedby*='factualPayAmount']").text(0.00);
                    $newFooterRow.find("td[aria-describedby*='factualRefundAmount']").text(0.00);
                }
                $newFooterRow.find("td[aria-describedby*='smemberNameDesensitize']").text("总合计：");
                $newFooterRow.find("td[aria-describedby*='ftotalAmount']").text(formateMoney(resp.data.ftotalAmountSta));
                $newFooterRow.find("td[aria-describedby*='fdiscountAmount']").text(formateMoney(resp.data.fdiscountAmountSta));
                $newFooterRow.find("td[aria-describedby*='ffirstDiscountAmount']").text(formateMoney(resp.data.ffirstDiscountAmountSta));
                $newFooterRow.find("td[aria-describedby*='fpromotionDiscountAmount']").text(formateMoney(resp.data.fpromotionDiscountAmountSta));
                $newFooterRow.find("td[aria-describedby*='fcouponDeductionAmount']").text(formateMoney(resp.data.fcouponDeductionAmountSta));
                $newFooterRow.find("td[aria-describedby*='factualPayAmount']").text(formateMoney(resp.data.factualPayAmountSta));
                $newFooterRow.find("td[aria-describedby*='factualRefundAmount']").text(formateMoney(resp.data.factualRefundAmountSta));
            }
        });
    }
</script>
</body>

