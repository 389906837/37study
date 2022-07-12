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
                    <h5><spring:message code='menu.refund.review' /></h5>
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
                                <input type="text" name="srefundCode" id="srefundCode" value=""
                                       placeholder="<spring:message code='sq.refund.number' />..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="spaySerialNumber" id="spaySerialNumber" value=""
                                       placeholder="<spring:message code='sq.trade.serial.number' />..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sorderCode" id="sorderCode" value=""
                                       placeholder='<spring:message code="main.order.number" />...' class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="deviceCode" id="deviceCode" value=""
                                       placeholder='<spring:message code="main.device.id" />...' class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="deviceName" id="deviceName" value=""
                                       placeholder='<spring:message code="main.device.name" />...' class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="deviceAddress" id="deviceAddress" value=""
                                       placeholder='<spring:message code="main.device.address" />...' class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="smemberName" id="smemberName" value=""
                                       placeholder='<spring:message code="main.member.username" />...' class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" id="ipayType" name="ipayType" value=""
                                             list='{30:springMessageCode=main.wechat,40:springMessageCode=main.alipay}' entire="true"
                                             entireName='springMessageCode=om.please.select.a.refund.channel' />
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" id="irefundStatus" name="irefundStatus" value=""
                                             list="{10:springMessageCode=om.pending.refund,20:springMessageCode=sq.refund.failure,30:springMessageCode=sq.refund.success}" entire="true"
                                             entireName='springMessageCode=om.please.select.a.refund.status' />
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" id="iauditStatus" name="iauditStatus" value=""
                                             list='{10:springMessageCode=main.audited,20:springMessageCode=main.approval,30:springMessageCode=sh.review.the.rejected}' entire="true"
                                             entireName='springMessageCode=sb.please.select.the.audit.status' />
                            </div>

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


<script src="${staticSource}/resources/js/tableExport.min.js"></script>
<script src="${staticSource}/resources/js/tableExportComm-${currentLanguage}.js"></script>
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
                {name: 'smerchantCode', label: '<spring:message code="main.merchant.number" />', index: 'smerchant_code', sortable: false, width: 180},
                {name: 'merchantName', label: '<spring:message code="main.merchant.name" />', index: 'MERCHANT_NAME', sortable: false, width: 280},
                </c:if>
                <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
                {name: 'smerchantCode', index: 'smerchant_code', hidden: true},
                </c:if>
                {name: 'srefundCode', index: 'SREFUND_CODE', label: "<spring:message code='sq.refund.number' />", width: 180},
                {name: 'spaySerialNumber', index: 'SPAY_SERIAL_NUMBER', label: "<spring:message code='sq.trade.serial.number' />", width: 280},
                {
                    name: 'irefundStatus', index: 'IREFUND_STATUS', label: "<spring:message code='om.refund.status' />", width: 200,
                    formatter: function(value, options, rowObject) {
                        if(isEmpty(value)) {
                            return '<span id="irefundStatus_'+rowObject.id+'" data="'+value+'" class="istatus-warm">● <spring:message code='om.pending.refund' /></span>';
                        }
                        return '<span id="irefundStatus_'+rowObject.id+'" data="'+value+'" class="' + (({"10":"istatus-warm","20":"istatus-danger","30":"istatus-normal"})[value]) + '">' +  (({"10":"● <spring:message code='om.pending.refund' />","20":"● <spring:message code='sq.refund.failure' />","30":"● <spring:message code='sq.refund.success' />"})[value])+ '</span>' }
                },
                {name: 'sorderCode', index: 'SORDER_CODE', label: '<spring:message code="main.order.number" />', width: 260},

                {name: 'smemberNameDesensitize', index: 'SMEMBER_NAME', label: "<spring:message code='om.member.name' />", width: 180},
                {name: 'ftotalAmount', index: 'FTOTAL_AMOUNT', label: "<spring:message code='sq.total.order.amount.yuan' />", width: 220},
                {name: 'factualPayAmount', index: 'FACTUAL_PAY_AMOUNT', label: "<spring:message code='om.total.order.payment.yuan' />", width: 280},
                {
                    name: 'fapplyRefundAmount',
                    index: 'FAPPLY_REFUND_AMOUNT',
                    label: "<spring:message code='om.request.for.refund.yuan' />",
                    width: 260
                },
                {
                    name: 'ipayType', index: 'IPAY_TYPE', label: "<spring:message code='om.refund.type' />", width: 200, formatter: "select",
                    editoptions: {value: '30:<spring:message code="main.wechat" />;40:<spring:message code="main.alipay" />'}
                },
                {
                    name: 'tapplyTime', index: 'TAPPLY_TIME', label: "<spring:message code='om.application.time' />", width: 220,
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
                    label: "<spring:message code='om.refund.picture.description' />",
                    sortable: false
                    , width: 280,
                    formatter: function (value, rowObject) {
                        return "<a href='javascript:void(0);' onclick=\"viewDevice('" + rowObject.rowId + "')\"><spring:message code='main.view' /></a>";
                    }
                },
                {
                    name: 'iauditStatus', index: 'IAUDIT_STATUS', label: "<spring:message code='sb.audit.status' />", width: 200, formatter: "select",
                    editoptions: {value: '10:<spring:message code="main.audited" />;20:<spring:message code="main.approval" />;30:<spring:message code='sh.review.the.rejected' />'}
                },
                {name: 'sauditOperName', index: 'SAUDIT_OPER_NAME', label: '<spring:message code="main.reviewer" />', width: 180},
                {
                    name: 'tauditTime', index: 'TAUDIT_TIME', label: "<spring:message code='sb.audit.time' />", width: 180,
                    formatter: function (value) {
                        if (isEmpty(value)) {
                            return '';
                        } else {
                            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
                        }
                    }
                },
                {name: 'process', title: false, index: 'process', label: '<spring:message code="main.operation" />', sortable: false, width: 260},
                {name: 'deviceCode', index: 'DEVICE_CODE', label: '<spring:message code="main.device.id" />', width: 160, sortable: false},
                {name: 'deviceName', index: 'DEVICE_NAME', label: '<spring:message code="main.device.name" />', width: 260, sortable: false},
                {name: 'deviceAddress', index: 'DEVICE_ADDRESS', label: '<spring:message code="main.device.address" />', width: 300, sortable: false}
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
                 $("#groupProfitTable").parent().append("</prex<div id= 'norecords' style= 'text:center padding: 8px 8px; '><spring:message code='om.no.query.record' />!</div><pre>");
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
                                str += "<img src='${staticSource}/resources/images/order/shenhe.png' class=\"order\" onclick=\"AuditMethod('"
                                    + cl + "')\" title='<spring:message code='main.audit' />' alt='<spring:message code='main.audit' />'  /> | ";
                                </shiro:hasPermission>
                            }
                            if (20 == ids[i].iauditStatus && 10 == $('#irefundStatus_'+cl).attr('data')) {
                                <shiro:hasPermission name="ORDER_REFUND">
                                str += "<img src='${staticSource}/resources/images/order/tuikuan.png' class=\"order\" onclick=\"RefundMethod('"
                                    + cl + "')\" title='<spring:message code='om.refund' />' alt='<spring:message code='om.refund' />'  /> | ";
                                </shiro:hasPermission>
                            }
                            if (20 == $('#irefundStatus_'+cl).attr('data') && 20 == ids[i].iauditStatus) {
                                <shiro:hasPermission name="REFUND_RECHARGE_AGAIN">
                                str += "<img src='${staticSource}/resources/images/order/buchuli.png' class=\"order\" onclick=\"rechargeAgainMethod('"
                                    + cl + "')\" title='<spring:message code='om.complement.processing' />' alt='<spring:message code='om.complement.processing' />'  /> | ";
                                </shiro:hasPermission>
                                <shiro:hasPermission name="REFUND_DISMISSAL">
                                str += "<img src='${staticSource}/resources/images/order/shenhebohui.png' class=\"order\" onclick=\"dismissalMethod('"
                                    + cl + "')\" title='<spring:message code='sh.review.the.rejected' />' alt='<spring:message code='sh.review.the.rejected' />'  /> | ";
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
                    shrinkToFit: false,
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
                            label: '<spring:message code="main.product.number" />',
                            width: 150,
                            sortable: false
                        },
                        {
                            name: 'commodityFullName',
                            index: 'commodityFullName',
                            label: '<spring:message code="main.product.name" />',
                            width: 340,
                            sortable: false
                        },
                        {name: 'fcommodityPrice', index: 'FCOMMODITY_PRICE', label: "<spring:message code='sm.item.pricing' />", width: 160},
                        {name: 'frefundCount', index: 'FREFUND_COUNT', label: "<spring:message code='om.refund.amount' />", width: 160},
                        {name: 'fcommodityAmount', index: 'FCOMMODITY_AMOUNT', label: "<spring:message code='rm.total.merchandise' />", width: 160},
                        {name: 'frefundAmount', index: 'FREFUND_AMOUNT', label: "<spring:message code='om.refund.amount' />", width: 160}
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
        showLayer("<spring:message code='om.refund.picture.description' />", ctx + '/refundAudit/viewImg?sid=' + sid, {area: ['32%', '75%']});
    }
    function AuditMethod(sid) {
        showLayerMin("<spring:message code='main.audit' />", ctx + '/refundAudit/audit?sid=' + sid);
    }
    //退款
    function RefundMethod(sid) {
        confirmOperTip("<spring:message code='om.are.you.sure.you.want.to.refund' />？", ctx + "/refundAudit/refund", {checkboxId: sid});
    }
    //补处理
    function rechargeAgainMethod(sid) {
        confirmOperTip("<spring:message code='hy.are.you.sure.you.want.to.process.it' />?", ctx + "/refundAudit/rechargeAgain", {checkboxId: sid});
    }
    //补处理
    function dismissalMethod(sid) {
        confirmOperTip("<spring:message code='om.are.you.sure.you.want.to.reject.your.refund.request' />?", ctx + "/refundAudit/dismissal", {checkboxId: sid});
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
            "factualPayAmount": "<spring:message code='sq.pages.in.total' />：",
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
                    $newFooterRow.find("td[aria-describedby*='factualPayAmount']").text("<spring:message code='sq.in.total' />：");
                    $newFooterRow.find("td[aria-describedby*='fapplyRefundAmount']").text(0.00);
                }
                $newFooterRow.find("td[aria-describedby*='factualPayAmount']").text("<spring:message code='sq.in.total' />：");
                $newFooterRow.find("td[aria-describedby*='fapplyRefundAmount']").text(formateMoney(resp.data));

            }
        });
    }
</script>
</body>
</html>

