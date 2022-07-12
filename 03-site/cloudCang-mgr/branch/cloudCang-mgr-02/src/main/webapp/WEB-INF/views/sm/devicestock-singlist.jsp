<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>单机库存列表查询</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>单机库存查询</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-inline">
                                    <input type="text" name="shcode" id="shcode" value=""
                                           placeholder="商户编号..." class="layui-input">
                                </div>
                                <div class="layui-inline">
                                    <input type="text" name="merchantName" id="merchantName" value=""
                                           placeholder="商户名称..." class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-inline">
                                <input type="text" name="sdeviceCode" id="sdeviceCode" value="" placeholder="设备编号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sbName" id="sbName" value="" placeholder="设备名称..."
                                       class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="scommodityCode" id="scommodityCode" value=""
                                       placeholder="商品编号..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="spName" id="spName" value="" placeholder="商品名称..."
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
<script type="text/javascript" src="${staticSource}/resources/page/sm/stockoperlog/stockoperLog-list.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    // 渲染数据
    var colModel = [{name: 'id', index: 'id', hidden: true},
        {name: 'sdeviceCode', index: 'SDEVICE_CODE', label: "设备编号", width: 130, sortable: false},
        {name: 'sbName', index: 'sbName', label: "设备名称", width: 150, sortable: false},
        {name: 'adress', index: 'adress', label: "设备地址", width: 80, sortable: false, width: 400},
        {name: 'scommodityCode', index: 'SCOMMODITY_CODE', label: "商品编号", width: 150, sortable: false},
        {name: 'spName', index: 'spName', label: "商品名称", sortable: false, width: 220},
        {name: 'fsalePrice', index: 'fsalePrice', label: "单价", width: 80},
        {
            name: 'istock', index: 'ISTOCK', label: "库存数量", width: 100,
            formatter: function (istock, opt, row) {
                return '<span id="istock_' + row.id + '" data="' + istock + '"  class="' + (istock <= row.stockWarningValue ? 'istatus-danger' : 'istatus-normal') + '">' + istock + '</span>'
            }
        },
        {name: 'kcistandardstock', index: 'KCISTANDARDSTOCK', label: "标准库存", width: 90, sortable: false},
        {name: 'stockWarningValue', index: 'STOCKWARNINGVALUE', label: "库存预警值", width: 90, sortable: false},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode', label: "商户编号", index: 'SMERCHANT_CODE', sortable: false},
        {name: 'merchantName', label: "商户名称", index: 'MERCHANTNAME', sortable: false, width: 260},
        </c:if>
        {name: 'sremark', index: 'sremark', label: "备注", sortable: false, width: 120},
        {name: 'process', title: false, index: 'process', label: "操作", sortable: false, width: 220}
    ];
    // 表格回调
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
            var data = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = "";
                <shiro:hasPermission name="INFO_QUERYONEDEVICESTOCKINFO">
                str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"queryInfoMethod('"
                    + cl + "')\" title='单机库存详情' alt='单机库存详情'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="UPDATECOMFSALEPRICE_QUERYONEDEVICESTOCKINFO">
                str += "<img src='${staticSource}/resources/images/oper_icon/update_fsale.png' class=\"oper_icon\" onclick=\"comFsalePriceMethod('"
                    + cl + "')\" title='修改单价' alt='修改单价'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="SYNCCOMFSALEPRICE_QUERYONEDEVICESTOCKINFO">
                str += "<img src='${staticSource}/resources/images/oper_icon/async_fsale.png' class=\"oper_icon\" onclick=\"comSyncFsalePriceMethod('"
                    + cl + "')\" title='恢复原价' alt='恢复原价'  /> | ";
                </shiro:hasPermission>
                if ($('#istock_' + cl).attr('data') > 0) {
                    <shiro:hasPermission name="STOCKDETAIL_UNDERCARRIAGE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/drop_off.png' class=\"oper_icon\" onclick=\"undercarriageMethod('"
                        + cl + "')\" title='手动下架' alt='手动下架'  /> | ";
                    </shiro:hasPermission>
                }
                $("#gridTable").jqGrid('setRowData',
                    cl, {
                        process: str.length > 0 ? str.substr(0, str.lastIndexOf(" | ")) : ""
                    });
            }
        }, 0);
    }
    // 初始化表格,传入页面所需参数
    $(function () {
        var config = {
            shrinkToFit: false,
            autoScroll: true,
            rownumbers: true,
            multiselect: false,
            sortname: 'ISTOCK',
            sortorder: 'desc'
        };
        initTable(ctx + "/devicestock/queryOneData", colModel, tableCallBack, config);
    });
    // 调用Button按钮方法(查询,清除)
    var initBtnConfig = {
        addModelConfig: {area: ['60%', '80%']}
    };
    initBtnByForm2(initBtnConfig);

    function queryInfoMethod(sid) {
        showLayerMedium("单机库存商品详情", ctx + '/devicestock/queryOneDevicestockInfo?sid=' + sid);
    }

    function comFsalePriceMethod(sid) {
        showLayerMin("修改商品单价", ctx + 'comFsalePricelist?sid=' + sid);
    }

    function undercarriageMethod(sid) {
        showLayerMin("手动下架商品库存", ctx + 'undercarriage?sid=' + sid);
    }

    function comSyncFsalePriceMethod(sid) {
        var alertStr = "确定要同步商品单价?";
        confirmOperTip(alertStr, ctx + "/devicestock/syncComFsalePrice", {checkboxId: sid});
    }

</script>
</body>
</html>

