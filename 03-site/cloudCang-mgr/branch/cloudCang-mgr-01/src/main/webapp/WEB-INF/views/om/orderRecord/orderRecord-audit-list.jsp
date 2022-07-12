<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>异常订单审核</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet"/>
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css" rel="stylesheet"/>
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
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
                    <h5>异常订单审核</h5>
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
                                <cang:select name="itype" id="itype" type="dic" entire="true" groupNo="SP000142"
                                             entireName="请选择审核类型"/>
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" id="istatus" name="istatus" value=""
                                             list="{10:待处理,20:异常处理成功,30:异常处理忽略}" entire="true"
                                             entireName="请选择订单状态"/>
                            </div>
                            <%--   <div class="layui-input-inline">
                                   <cang:select type="list" id="ipayType" name="ipayType" value=""
                                                list="{30:微信,40:支付宝}" entire="true"
                                                entireName="请选择类信息"/>
                               </div>--%>

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
<!--bootstrap双日历插件 -->
<script type="text/javascript" src="${staticSource}/resources/js/moment.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/daterangepicker.js"></script>
<script src="${staticSource}/resources/js/xlsx.core.min.js"></script>
<script src="${staticSource}/resources/js/tableExport.min.js"></script>
<script src="${staticSource}/resources/js/tableExportComm.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/formatUtil.js"></script>
<script type="text/javascript">
    function closeLayerAndRefresh(index) {
        layer.close(index);
        reloadGrid(true);
    }
    $(function () {
        $.jgrid.defaults.styleUI = 'Bootstrap';
        $("#gridTable").jqGrid({
            url: ctx + "/orderAudit/queryOrderAuditData",
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
                {name: 'smerchantCode', label: "商户编号", index: 'smerchant_code', sortable: false, width: 170},
                {name: 'merchantName', label: "商户名称", index: 'merchantName', sortable: false, width: 250},
                </c:if>
                <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
                {name: 'smerchantCode', index: 'smerchant_code', hidden: true},
                </c:if>
                {name: 'sorderCode', label: "订单编号", index: 'SORDER_CODE', sortable: false, width: 110},
                {name: 'sdeviceCode', index: 'SDEVICE_CODE', label: "设备编号", width: 120},
                {name: 'sdeviceName', index: 'SDEVICE_NAME', label: "设备名称", width: 240},
                {name: 'sdeviceAddress', index: 'SDEVICE_ADDRESS', label: "设备地址", width: 250},
                {
                    name: 'itype', index: 'ITYPE', label: "审核类型", width: 100, formatter: "select",
                    editoptions: {value: '10:盘点异常;20:已售补扣;30:创建订单异常;40:视觉重力不匹配'}
                },
                {name: 'smemberNameDesensitize', index: 'SMEMBER_NAME', label: "会员用户名", width: 130},
                {name: 'ftotalAmount', index: 'FTOTAL_AMOUNT', label: "订单总额（元）", width: 110},
                {
                    name: 'istatus', index: 'ISTATUS', label: "状态", width: 120,
                    formatter: function (value, options, rowObject) {
                        return '<span id="istatus_' + rowObject.id + '" data="' + value + '" class="' + (({
                                "10": "istatus-warm",
                                "20": "istatus-normal",
                                "30": ""
                            })[value]) + '">' + (({
                                "10": "● 待处理",
                                "20": "● 异常处理成功",
                                "30": "● 异常处理忽略"
                            })[value]) + '</span>'
                    }
                },
                {
                    name: 'taddTime',
                    index: 'TADD_TIME',
                    label: "异常时间",
                    width: 180,
                    formatter: function (value) {
                        if (isEmpty(value)) {
                            return '';
                        }
                        return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
                    }
                },
                /*   {
                 name: 'ipayType', index: 'IPAY_TYPE', label: "退款类型", width: 120, formatter: "select",
                 editoptions: {value: '30:微信;40:支付宝'}
                 },*/
                {name: 'shandlerUsername', index: 'SHANDLER_USERNAME', label: "处理人", width: 133},
                {
                    name: 'thandlerTime',
                    index: 'THANDLER_TIME',
                    label: "处理时间",
                    width: 180,
                    formatter: function (value) {
                        if (isEmpty(value)) {
                            return '';
                        }
                        return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
                    }
                },
                {name: 'shandlerRemark', index: 'SHANDLER_REMARK', label: "处理备注", width: 133, sortable: false},
                {name: 'process', title: false, index: 'process', label: "操作", sortable: false, width: 150}
            ],
            pager: "#gridPager",
            viewrecords: true,
            sortname: "",
            sortorder: "",
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
                            if (10 == $('#istatus_' + cl).attr('data')) {
                                <shiro:hasPermission name="ORDER_AUDIT_WITHDRAWING">
                                str += "<img src='${staticSource}/resources/images/order/koukuan.png' class=\"order\" onclick=\"withdrawingMethod('"
                                    + cl + "')\" title='扣款' alt='扣款'  /> | ";
                                </shiro:hasPermission>
                                <shiro:hasPermission name="ORDER_AUDIT_IGNORE">
                                str += "<img src='${staticSource}/resources/images/order/hulue.png' class=\"order\" onclick=\"ignoreMethod('"
                                    + cl + "')\" title='忽略' alt='忽略'  /> | ";
                                </shiro:hasPermission>
                                if (40 == ids[i].itype) {
                                    str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                                        + cl + "')\" title='编辑订单' alt='编辑订单'  /> | ";
                                }
                            }
                        }
                        $("#gridTable").jqGrid('setRowData',
                            cl, {
                                process: str.substr(0, str.lastIndexOf(" | "))
                            });
                    }
                    /*   if (isExport) {//判断是否导出
                     exportFileCallback();
                     }*/
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
                    url: ctx + "/orderAudit/queryDetails?sorderId=" + row_id,
                    datatype: "json",
                    height: 'auto',
                    autowidth: true,
                    shrinkToFit: true,
                    rowNum: rownum,
                    rowList: rowList,
                    rownumbers: true,
                    viewrecords: true,
                    pager: "#chilgridPager",
                    sortname: "",
                    sortorder: "asc",
                    colModel: [
                        {
                            name: 'scommodityCode',
                            index: 'scommodityCode',
                            label: "商品编号",
                            width: 150,
                            sortable: false
                        }, {
                            name: 'commodityFullName',
                            index: 'commodityFullName',
                            label: "商品名称",
                            width: 150,
                            sortable: false
                        },
                        {name: 'fcommodityPrice', index: 'FCOMMODITY_PRICE', label: "商品单价", width: 150},
                        {name: 'forderCount', index: 'FORDER_COUNT', label: "订单数量", width: 150},
                        {name: 'fcommodityAmount', index: 'FCOMMODITY_AMOUNT', label: "商品总额", width: 150}
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

        /*   addExportBtn("异常订单导出");*/
        <shiro:hasPermission name="ORDER_AUDIT_DOWNLOAD_EXCEL">
        addExportBtnByUrl("${ctx}/orderAudit/downloadExcel");
        </shiro:hasPermission>
        /*  $(window).bind('resize', function () {
         var width = $('.jqGrid_wrapper').width();
         $('#gridTable').setGridWidth(width);
         });*/

        $(window).bind('resize', function () {
            var width = $('.jqGrid_wrapper').width();
            $('#gridTable').setGridWidth(width);
        });
    });
    layui.use('form', function () {
        var $ = layui.$, active = {
            query: function () {//查询
                reloadGrid(true);
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
    function withdrawingMethod(sid) {
        confirmOperTip("确定要扣款?", ctx + "/orderAudit/payOrder", {checkboxId: sid});
    }
    function ignoreMethod(sid) {
        confirmOperTip("确定忽略异常订单?", ctx + "/orderAudit/ignoreOrder", {checkboxId: sid});
    }
    function editMethod(sid) {
        showLayerMax("编辑订单", ctx + '/orderAudit/edit?sid=' + sid);
    }
    function getPageFooterTotal(totalRow) {//处理合计
        var pageObj = {
            ftotalAmount: 0
        };
        $.each(totalRow, function (i, item) {
            pageObj.ftotalAmount += parseFloat(item.ftotalAmount);

        });
        $("tr.footRow2").remove();
        var $footerRow = $("tr.footrow");
        $footerRow.after("<tr role='row' class='footrow footRow2 footrow-ltr ui-widget-content'>" + $footerRow.html() + "</tr>");
        var $newFooterRow = $("tr.footRow2");
        $("#gridTable").footerData("set", {
            "smemberNameDesensitize": "页合计：",
            "ftotalAmount": formateMoney(pageObj.ftotalAmount)
        });
        var sdata = getFormValues();
        $.ajax({
            url: ctx + "/orderAudit/queryTotalData",
            type: "post",
            dataType: "json",
            data: sdata,
            success: function (resp) {
                if (resp.data == null || resp.data == 'null') {
                    $newFooterRow.find("td[aria-describedby*='smemberNameDesensitize']").text("总合计：");
                    $newFooterRow.find("td[aria-describedby*='ftotalAmount']").text(0.00);
                }
                $newFooterRow.find("td[aria-describedby*='smemberNameDesensitize']").text("总合计：");
                $newFooterRow.find("td[aria-describedby*='ftotalAmount']").text(formateMoney(resp.data));

            }
        });
    }
</script>
</body>

