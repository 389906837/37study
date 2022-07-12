<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备详细信息列表</title>
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
                <div class="ibox-title">
                    <h5>设备详细信息</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder="设备编号..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sname" id="sname" value="" placeholder="设备名称..." class="layui-input">
                            </div>
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantCode" id="merchantCode" value="" placeholder="商户编号..." class="layui-input">
                                </div>
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantName" id="merchantName" value="" placeholder="商户名称..." class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-input-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query">
                                    <i class="layui-icon">&#xe615;</i>查询
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset">
                                    <i class="layui-icon">&#x1006;</i>清除
                                </button>
                            </div>
                        </div>
                        <%--<div class="layui-form-item">
                            <button class="layui-btn layui-btn-sm " data-type="add"><i class="layui-icon"></i>添加</button>
                            <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i>删除</button>
                        </div>--%>
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
    var colModel = [
        {name: 'id', index: 'id', hidden: true},
        {name: 'scode', index: 'scode', label: "设备编号",width:120,sortable : false},
        {name: 'sname', index: 'SNAME', label: "设备名称",width:120,sortable : false},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'merchantCode', index: 'merchantCode', label: "商户编号",width:160,sortable : false},
        {name: 'merchantName', index: 'merchantName', label: "商户名称",width:200,sortable : false},
        </c:if>
        {name: 'sdeviceModel', index: 'sdeviceModel', label: "设备核心型号",width:120},
        {name: 'smanufacturer', index: 'smanufacturer', label: "核心生产厂商",width:200},
        {name: 'scoreDesc', index: 'scoreDesc', label: "核心配置描述",width:200},
//                {name: 'sweight', index: 'SREG_PORT', label: "重量",width:100},
//                {name: 'sratedPower', index: 'SAUDIT_USER', label: "额定功率",width:100},
//                {name: 'sproductCapacity', index: 'TAUDIT_TIME', label: "商品容量"},
//                {name: 'sproductTypes', index: 'SREMARK', label: "商品类型",width:100},
//                {name: 'sdailyPower', index: 'SREMARK', label: "日耗电量",width:100},
//                {name: 'slocksModel', index: 'SREMARK', label: "电子锁型号",width:100},
//                {name: 'slocksManufacturer', index: 'SREMARK', label: "电子锁生产厂商",width:100},
//                {name: 'sadDimensions', index: 'SREMARK', label: "广告机屏幕尺寸说明",width:100},
//                {name: 'sadConf', index: 'SREMARK', label: "广告机配置描述",width:100},
//                {
//                    name: 'spayWap',
//                    index: 'ISTATUS',
//                    label: "支持支付方式",
//                    formatter: "select",
//                    editoptions: {value: '10:待审核;20:审核通过;30:审核拒绝;40:已注册'}
//                    ,width:100
//                },
//                {
//                    name: 'ielectricShock',
//                    index: 'IDEL_FLAG',
//                    label: "是否删除",
//                    formatter: "select",
//                    editoptions: {value: '0:否;1:是'}
//                    ,width:100
//                },
//                {name: 'saddUser', index: 'SADD_USER', label: "添加人",width:70},
//                {name: 'taddTime', index: 'TADD_TIME', label: "添加日期"},
//                {name: 'supdateUser', index: 'SUPDATE_USER', label: "修改人",width:70},
//                {name: 'tupdateTime', index: 'TUPDATE_TIME', label: "修改日期"},
        {name: "process", title:false, index: "process", label: "操作", sortable: false, frozen: true, width: 100}
    ];
    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            var ids = $("#gridTable").jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                var cl = ids[i];
                var str = '';
                <shiro:hasPermission name="DEVICE_MODEL_VIEW">
                str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"viewMethod('"
                    + cl + "')\" title='查看' alt='查看'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="DEVICE_MODEL_EDIT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                        + cl + "')\" title='编辑' alt='编辑'  /> | ";
                </shiro:hasPermission>
                $("#gridTable").jqGrid('setRowData',
                    ids[i], {
                        process: str.substr(0, str.lastIndexOf(" | "))
                    });
            }
        }, 0);
    }
    $(function () {
        var config = {
            sortname: "tadd_Time",
            multiselect:false,
            sortorder: "desc"
        };
        initTable(ctx+"/device/model/queryData",colModel,tableCallBack,config);
    });
    initBtnByForm2();

    function viewMethod(sid) {
        showLayerMedium("设备详细信息详情", ctx + '/device/deviceModel/toView?sid=' + sid);
    }
    function editMethod(sid) {
        showLayerMedium("编辑设备详细信息", ctx + '/device/deviceModel/toEdit?sid=' + sid);
    }

</script>
</body>
</html>


