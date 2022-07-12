<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>商品批次列表</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
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
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <input type="hidden" id="scommodityId" name="scommodityId" value="${sid}" /><%--商品编号--%>
                        <div class="layui-form-item">
                                <shiro:hasPermission name="SINGLE_COMMODITY_BATCH_ADD">
                            <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"><i class="layui-icon"></i>添加</button>
                                </shiro:hasPermission>
                        <%--<shiro:hasPermission name="COMMODITY_INFO_GROUP_ADDBATCH">
                            <button class="layui-btn layui-btn-sm" data-type="batchManage"><i class="layui-icon">&#xe63c;</i>批次</button>
                            </shiro:hasPermission>--%>
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
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    var colModel =[
        {name: 'id', index: 'id', hidden: true},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode',label : "商户编号",index: 'SMERCHANT_CODE',width:160},
        {name: 'merchantName',label : "商户名称",index: 'merchantName',sortable : false,width:160},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'smerchantCode', index: 'smerchant_code', hidden: true},
        </c:if>
        {name: 'scommodityCode', index: 'SCOMMODITY_CODE', label: "商品编号",width:120},
        {name: 'commodityFullName', index: 'commodityName', label: "商品名称",width:120},
        {name: 'sbatchNo', index: 'SBATCH_NO', label: "批次号",width:160},
        {name: 'fcostAmount', index: 'FCOST_AMOUNT', label: "成本价",width:80},
        {name: 'ftaxPoint', index: 'FTAX_POINT', label: "税点",width:80},
        {name: 'icommodityNum', index: 'ICOMMODITY_NUM', label: "数量",width:80},
        {name: 'ishelfNum', index: 'ISHELF_NUM', label: "已上架数量",width:100},
        {name: 'ilossGoodsNum', index: 'ilossGoodsNum', label: "货损数量",width:80},
        {name: 'dproduceDate', index: 'DPRODUCE_DATE', label: "生产日期",formatter: function (value) {
            return formatDate(new Date(value), 'yyyy-MM-dd');
        },width:100},
        {name: 'dexpiredDate', index: 'DEXPIRED_DATE', label: "过期日期",formatter: function (value) {
            if(isEmpty(value)){
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd');
        },width:100},
//        {   name: 'istatus',
//            index: 'ISTATUS',
//            label: "运营状态",
//            formatter: "select",
//            editoptions: {value: '10:启用;20:禁用'},
//            width:120},
        {   name: 'istockStatus',
            index: 'ISTOCK_STATUS',
            label: "库存状态",
            formatter: "select",
            editoptions: {value: '10:待上架;20:部分上架;30:全部上架'},
            width:100},
//        {   name: 'isaleStatus',
//            index: 'ISALE_STATUS',
//            label: "销售状态",
//            formatter: "select",
//            editoptions: {value: '10:销售中;20:售罄'},
//            width:120},
//        {name: 'taddTime', index: 'TADD_TIME', label: "添加日期",formatter: function (value) {
//            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
//        },width:160},
        {name: "process",title:false, index: "process", label: "操作", sortable: false, frozen: true, width: 100}
    ];

    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            var data = $("#gridTable").jqGrid('getRowData');
            var merchantCode = "${SESSION_KEY_SMERCHANT_CODE}";
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = '';
                <shiro:hasPermission name="SP_COMMODITY_BATCH_VIEW">
                str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"viewMethod('"
                    + cl + "')\" title='查看' alt='查看'  /> | ";
                </shiro:hasPermission>
                if (merchantCode == data[i].smerchantCode) {
                    <shiro:hasPermission name="SP_COMMODITY_BATCH_EDIT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                        + cl + "')\" title='编辑' alt='编辑'  /> | ";
                    </shiro:hasPermission>
                }
                $("#gridTable").jqGrid('setRowData',
                    cl, {
                        process: str.substr(0, str.lastIndexOf(" | "))
                    });
            }
        }, 0);
    }

    $(function () {
        var config = {
            sortname: "tadd_Time",
            sortorder: "desc",
            multiselect: false

        };
        var commodityId = $("#scommodityId").val();
        initTable(ctx+"/commodity/commodityBatch/single/queryData?sid=" + commodityId ,colModel,tableCallBack,config);
    });
    var initBtnConfig = {
        addUrl: ctx+'/commodity/commodityBatch/toAddBatch?sid='+$("#scommodityId").val(),
        addTitle: '添加商品批次信息',
        addFn:showLayerMedium
    };

    initBtnByForm3(initBtnConfig);

    function viewMethod(sid) {
        showLayerMax("详情", ctx + '/commodity/commodityBatch/view?sid=' + sid);
    }
    function editMethod(sid) {
        showLayerMin("编辑商品批次信息", ctx + '/commodity/commodityBatch/toEditBatch?sid=' + sid);
    }

</script>
</body>
</html>



