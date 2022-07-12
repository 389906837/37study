<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>退款审核</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet"/>
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css" rel="stylesheet"/>

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
                    <h5>退款审核</h5>
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
                                <input type="text" name="srefundCode" id="srefundCode" value=""
                                       placeholder="退款编号..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="spaySerialNumber" id="spaySerialNumber" value=""
                                       placeholder="交易流水号..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sorderCode" id="sorderCode" value=""
                                       placeholder="订单编号..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="deviceCode" id="deviceCode" value=""
                                       placeholder="设备编号..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="deviceName" id="deviceName" value=""
                                       placeholder="设备名称..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="deviceAddress" id="deviceAddress" value=""
                                       placeholder="设备地址..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="smemberName" id="smemberName" value=""
                                       placeholder="会员用户名..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" id="ipayType" name="ipayType" value=""
                                             list="{30:微信,40:支付宝}" entire="true"
                                             entireName="请选择退款渠道"/>
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" id="irefundStatus" name="irefundStatus" value=""
                                             list="{10:待退款,20:退款失败,30:退款成功}" entire="true"
                                             entireName="请选择退款状态"/>
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" id="iauditStatus" name="iauditStatus" value=""
                                             list="{10:待审核,20:审核通过,30:审核驳回}" entire="true"
                                             entireName="请选择审核状态"/>
                            </div>

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


<script src="${staticSource}/resources/js/tableExport.min.js"></script>
<script src="${staticSource}/resources/js/tableExportComm.js"></script>
<script type="text/javascript">
    /**
     * 关闭并加载更新表格
     */
    function closeLayerAndRefresh(index) {
        layer.close(index);
        reloadGrid(true);
    }
    $(function () {
        $.jgrid.defaults.styleUI = 'Bootstrap';
        $("#gridTable").jqGrid({
            url: ctx + "/refundAudit/queryData",
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
                <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                {name: 'smerchantCode', label: "商户编号", index: 'smerchant_code', sortable: false, width: 150},
                {name: 'merchantName', label: "商户名称", index: 'MERCHANT_NAME', sortable: false, width: 250},
                </c:if>
                <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
                {name: 'smerchantCode', index: 'smerchant_code', hidden: true},
                </c:if>
                {name: 'srefundCode', index: 'SREFUND_CODE', label: "退款编号", width: 150},
                {name: 'spaySerialNumber', index: 'SPAY_SERIAL_NUMBER', label: "交易流水号", width: 260},
                {
                    name: 'irefundStatus', index: 'IREFUND_STATUS', label: "退款状态", width: 100,
                    formatter: function (value, options, rowObject) {
                        if (isEmpty(value)) {
                            return '<span id="irefundStatus_' + rowObject.id + '" data="' + value + '" class="istatus-warm">● 待退款</span>';
                        }
                        return '<span id="irefundStatus_' + rowObject.id + '" data="' + value + '" class="' + (({
                                "10": "istatus-warm",
                                "20": "istatus-danger",
                                "30": "istatus-normal"
                            })[value]) + '">' + (({"10": "● 待退款", "20": "● 退款失败", "30": "● 退款成功"})[value]) + '</span>'
                    }
                },
                {name: 'sorderCode', index: 'SORDER_CODE', label: "订单编号", width: 150},
                {name: 'smemberNameDesensitize', index: 'SMEMBER_NAME', label: "会员名", width: 150},
                {name: 'ftotalAmount', index: 'FTOTAL_AMOUNT', label: "订单总额（元）", width: 150},
                {name: 'factualPayAmount', index: 'FACTUAL_PAY_AMOUNT', label: "订单实付总额（元）", width: 150},
                {name: 'iactualPayIpoint', index: 'IACTUAL_PAY_IPOINT', label: "订单实付积分", width: 100},
                {
                    name: 'fapplyRefundAmount',
                    index: 'FAPPLY_REFUND_AMOUNT',
                    label: "申请退款金额（元）",
                    width: 150
                },
                {
                    name: 'iapplyRefundIpoint',
                    index: 'IAPPLY_REFUND_IPOINT',
                    label: "申请退款积分",
                    width: 100
                },
                {
                    name: 'ipayType', index: 'IPAY_TYPE', label: "退款类型", width: 100, formatter: "select",
                    editoptions: {value: '30:微信;40:支付宝;90:积分'}
                },
                {
                    name: 'tapplyTime', index: 'TAPPLY_TIME', label: "申请时间", width: 180,
                    formatter: function (value) {
                        if (isEmpty(value)) {
                            return '';
                        } else {
                            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
                        }
                    }
                },
                {
                    name: 'irangeType',
                    index: 'irange_type',
                    label: "退款图片说明",
                    sortable: false
                    , width: 100,
                    formatter: function (value, rowObject) {
                        return "<a href='javascript:void(0);' onclick=\"viewDevice('" + rowObject.rowId + "')\">查看</a>";
                    }
                },
                {name: 'deviceCode', index: 'DEVICE_CODE', label: "设备编号", width: 150, sortable: false},
                {name: 'deviceName', index: 'DEVICE_NAME', label: "设备名称", width: 200, sortable: false},
                {name: 'deviceAddress', index: 'DEVICE_ADDRESS', label: "设备地址", width: 500, sortable: false},
                {
                    name: 'iauditStatus', index: 'IAUDIT_STATUS', label: "审核状态", width: 100, formatter: "select",
                    editoptions: {value: '10:待审核;20:审核通过;30:审核驳回'}
                },
                {name: 'sauditOperName', index: 'SAUDIT_OPER_NAME', label: "审核人", width: 100},
                {
                    name: 'tauditTime', index: 'TAUDIT_TIME', label: "审核时间", width: 180,
                    formatter: function (value) {
                        if (isEmpty(value)) {
                            return '';
                        } else {
                            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
                        }
                    }
                },
                {name: 'process', title: false, index: 'process', label: "操作", sortable: false, width: 100}
            ],
            pager: "#gridPager",
            viewrecords: true,
            sortname: "",
            sortorder: "",
            footerrow: true,//分页上添加一行，用于显示统计信息
            userDataOnFooter: true,
            loadComplete: function (xhr) {
                /*alert(xhr);
                 //查询为空的处理方式
                 var rowNum = $("#groupProfitTable").jqGrid('getGridParam', ' records');
                 if (rowNum == '0') {
                 if ($("#norecords").htm1() == nul1) {
                 $("#groupProfitTable").parent().append("</prex<div id= 'norecords' style= 'text:center padding: 8px 8px; '>没有查询记录!</div><pre>");
                 }
                 $(".ui-jqgrid-sdiv").show();
                 } else {
                 $(".ui-jqgrid-sdiv").hide();
                 }*/
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
                        var cl = ids[i].id;
                        var str = "";

                        if (merchantCode == ids[i].smerchantCode) {
                            if (10 == ids[i].iauditStatus) {
                                <shiro:hasPermission name="REFUND_AUDIT">
                                /*  str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"AuditMethod('"
                                 + cl + "')\">审核</a> | ";*/
                                str += "<img src='${staticSource}/resources/images/order/shenhe.png' class=\"order\" onclick=\"AuditMethod('"
                                    + cl + "')\" title='审核' alt='审核'  /> | ";
                                </shiro:hasPermission>
                            }
                            if (20 == ids[i].iauditStatus && 10 == $('#irefundStatus_' + cl).attr('data')) {
                                <shiro:hasPermission name="ORDER_REFUND">
                                /*       str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"RefundMethod('"
                                 + cl + "')\">退款</a> |  ";*/
                                str += "<img src='${staticSource}/resources/images/order/tuikuan.png' class=\"order\" onclick=\"RefundMethod('"
                                    + cl + "')\" title='退款' alt='退款'  /> | ";
                                </shiro:hasPermission>
                            }
                            if (20 == $('#irefundStatus_' + cl).attr('data') && 20 == ids[i].iauditStatus) {
                                <shiro:hasPermission name="REFUND_RECHARGE_AGAIN">
                                /*      str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"rechargeAgainMethod('"
                                 + cl + "')\">补处理</a> | ";*/
                                str += "<img src='${staticSource}/resources/images/order/buchuli.png' class=\"order\" onclick=\"rechargeAgainMethod('"
                                    + cl + "')\" title='补处理' alt='补处理'  /> | ";
                                </shiro:hasPermission>
                                <shiro:hasPermission name="REFUND_DISMISSAL">
                                /*      str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"dismissalMethod('"
                                 + cl + "')\">申请驳回</a> | ";*/
                                str += "<img src='${staticSource}/resources/images/order/shenhebohui.png' class=\"order\" onclick=\"dismissalMethod('"
                                    + cl + "')\" title='申请驳回' alt='申请驳回'  /> | ";
                                </shiro:hasPermission>
                            }
                        }
                        $("#gridTable").jqGrid('setRowData',
                            cl, {
                                process: str.substr(0, str.lastIndexOf(" | "))
                            });
                    }
                }, 0);
            },
            subGridOptions: {
                openicon: "ui-icon-carat-1-sw",
                expandOnLoad: false,
                selectOnExpand: false,
                reloadOnExpand: false
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
                    url: ctx + "/refundAudit/queryDetails?srefundId=" + row_id,
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
                        {
                            name: 'scommodityCode',
                            index: 'scommodityCode',
                            label: "商品编号",
                            width: 150,
                            sortable: false
                        },
                        {
                            name: 'commodityFullName',
                            index: 'commodityFullName',
                            label: "商品名称",
                            width: 150,
                            sortable: false
                        },
                        {name: 'fcommodityPrice', index: 'FCOMMODITY_PRICE', label: "商品单价", width: 150},
                        {name: 'frefundCount', index: 'FREFUND_COUNT', label: "退款数量", width: 150},
                        {name: 'fcommodityAmount', index: 'FCOMMODITY_AMOUNT', label: "商品总额", width: 150},
                        {name: 'frefundAmount', index: 'FREFUND_AMOUNT', label: "退款金额", width: 150},
                        {name: 'frefundPoint', index: 'FREFUND_POINT', label: "退回积分", width: 150}
                    ]
                });
            }
        });
        //导航按钮区域
        $("#gridTable").jqGrid('navGrid', '#gridPager', {edit: false, add: false, del: false, search: false}, {
            height: 200,
            reloadAfterSubmit: true
        });
        <shiro:hasPermission name="REFUND_ORDER_DOWNLOAD_EXCEL">

        addExportBtnByUrl("${ctx}/refundAudit/downloadExcel");
        </shiro:hasPermission>

        $(window).bind('resize', function () {
            var width = $('.jqGrid_wrapper').width();
            $('#gridTable').setGridWidth(width);
        });

    });
    layui.use('form', function () {
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
    //查看退款图片
    function viewDevice(sid) {
        showLayer("退款图片说明", ctx + '/refundAudit/viewImg?sid=' + sid, {area: ['32%', '75%']});
    }
    function AuditMethod(sid) {
        showLayerMin("审核", ctx + '/refundAudit/audit?sid=' + sid);
    }
    //退款
    function RefundMethod(sid) {
        confirmOperTip("确定要退款？", ctx + "/refundAudit/refund", {checkboxId: sid});
    }
    //补处理
    function rechargeAgainMethod(sid) {
        confirmOperTip("确定要补处理?", ctx + "/refundAudit/rechargeAgain", {checkboxId: sid});
    }
    //补处理
    function dismissalMethod(sid) {
        confirmOperTip("确定要驳回退款申请?", ctx + "/refundAudit/dismissal", {checkboxId: sid});
    }
    function getPageFooterTotal(totalRow) {//处理合计
        var pageObj = {
            fapplyRefundAmount: 0
        };
        $.each(totalRow, function (i, item) {
            pageObj.fapplyRefundAmount += parseFloat(item.fapplyRefundAmount);

        });
        $("tr.footRow2").remove();
        var $footerRow = $("tr.footrow");
        $footerRow.after("<tr role='row' class='footrow footRow2 footrow-ltr ui-widget-content'>" + $footerRow.html() + "</tr>");
        var $newFooterRow = $("tr.footRow2");
        $("#gridTable").footerData("set", {
            "factualPayAmount": "页合计：",
            "fapplyRefundAmount": formateMoney(pageObj.fapplyRefundAmount)
        });
        var sdata = getFormValues();
        $.ajax({
            url: ctx + "/refundAudit/queryTotalData",
            type: "post",
            dataType: "json",
            data: sdata,
            success: function (resp) {
                if (resp.data == null || resp.data == 'null') {
                    $newFooterRow.find("td[aria-describedby*='factualPayAmount']").text("总合计：");
                    $newFooterRow.find("td[aria-describedby*='fapplyRefundAmount']").text(0.00);
                }
                $newFooterRow.find("td[aria-describedby*='factualPayAmount']").text("总合计：");
                $newFooterRow.find("td[aria-describedby*='fapplyRefundAmount']").text(formateMoney(resp.data));

            }
        });
    }
</script>
</body>
</html>

