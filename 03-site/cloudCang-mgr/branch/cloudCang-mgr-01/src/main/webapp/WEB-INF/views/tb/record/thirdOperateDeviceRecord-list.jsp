<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>第三方订单/补货列表</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
<style>
    .ui-jqgrid tr.jqgrow td {
        white-space: normal !important;
        height: auto;
    }
    .layui-btn-hui{background:#f2f2f2;border:1px solid #e6e6e6;color:#323232;}
    .layui-btn-hui:hover{color:#323232;}
</style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>第三方购物/补货单管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder="订单编号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="merchantCode" id="merchantCode" value=""
                                       placeholder="商户编号..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="merchantName" id="merchantName" value="" placeholder="商户名称..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sdeviceCode" id="sdeviceCode" value="" placeholder="设备编号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sname" id="sname" value="" placeholder="设备名称..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control"  name="iorderType" id="iorderType" >
                                    <option value="">请选择订单类型</option>
                                    <option value="10">购物</option>
                                    <option value="20">补货</option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query">
                                    <i class="layui-icon">&#xe615;</i>查询
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset">
                                    <i class="layui-icon">&#x1006;</i>清除
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
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    var colModel =[
        {name: 'id', index: 'id', hidden: true},
        {name: 'scode', index: 'SCODE', label: "订单编号",width:130},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'merchantCode',label : "商户编号",index: 'merchantCode',width:120},
        {name: 'merchantName',label : "商户名称",index: 'merchantName',sortable : false,width:200},
        </c:if>
        {name: 'sdeviceCode', index: 'SDEVICE_CODE', label: "设备编号",width:100},
        {name: 'sname', index: 'sname', label: "设备名称",width:140},
        {   name: 'iorderType',
            index: 'IORDER_TYPE',
            label: "订单类型",
            formatter: "select",
            editoptions: {value: '10:购物订单;20:补货订单'},
            width:100},
        // {name: 'faddTotalAmount', index: 'FADD_TOTAL_AMOUNT', label: "增加总额",width:80},
        // {name: 'fsubTotalAmount', index: 'FSUB_TOTAL_AMOUNT', label: "减少总额",width:80},
        {name: 'faddTotalNum', index: 'FADD_TOTAL_NUM', label: "增加总数",width:80},
        {name: 'fsubTotalNum', index: 'FSUB_TOTAL_NUM', label: "减少总数",width:80},
        {name: 'taddTime', index: 'TADD_TIME', label: "订单时间",formatter: function (value) {
                return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
            },width:140},
        {name: "process",title:false, index: "process", label: "操作", sortable: false, frozen: true, width: 200}
    ];

    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            var data = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = '';
                <shiro:hasPermission name="TB_OPERATE_DEVICE_RECORD_VIEW">
                str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"viewMethod('"
                    + cl + "')\" title='查看' alt='查看'  /> | ";
                </shiro:hasPermission>
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
            sortorder: "desc"
        };
        initTable(ctx+"/tbOperateDeviceRecord/queryData",colModel,tableCallBack,config);
    });
    var initBtnConfig = {
        addUrl: ctx+'/commodity/labelInfo/toAdd',
        addTitle: '添加商品标签信息',
        addFn:showLayerOne
    };
    initBtnByForm4(initBtnConfig);

    function viewMethod(sid) {
        showLayerMedium("详情", ctx + '/tbOperateDeviceRecord/view?sid=' + sid);
    }

</script>
</body>
</html>



