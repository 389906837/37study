<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备监控信息列表</title>
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
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder="设备编号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sname" id="sname" value="" placeholder="设备名称..."
                                       class="layui-input">
                            </div>
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantCode" id="merchantCode" value=""
                                           placeholder="商户编号..."
                                           class="layui-input">
                                </div>
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantName" id="merchantName" value=""
                                           placeholder="商户名称..."
                                           class="layui-input">
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
        {name: 'scode', index: 'SCODE', label: "设备编号",width:120,sortable : false},
        {name: 'sname', index: 'SNAME', label: "设备名称",width:200,sortable : false},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'merchantCode', index: 'merchantCode', label: "商户编号",width:200,sortable : false},
        {name: 'merchantName', index: 'merchantName', label: "商户名称",width:400,sortable : false},
        </c:if>
        {name: 'tinventoryTime', index: 'TINVENTORY_TIME', label: "上次盘货时间",formatter: function (value) {
            if(isEmpty(value)){
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        },width:200},
        {name: 'tbootTime', index: 'TBOOT_TIME', label: "开机时间",width:160},
        {name: 'tshutDownTime', index: 'TSHUT_DOWN_TIME', label: "关机时间",width:160},
        {name: 'iactualVolume', index: 'IACTUAL_VOLUME', label: "实时音量",width:160},
        {name: 'iinventoryNum', index: 'IINVENTORY_NUM', label: "每次盘货次数",width:160},
        {name: 'tinventoryBeginTime', index: 'TINVENTORY_BEGIN_TIME', label: "自动盘货开始时间",width:160},
        {name: 'tinventoryEndTime', index: 'TINVENTORY_END_TIME', label: "自动盘货结束时间",width:160},
        {name: "process", title:false, index: "process", label: "操作", sortable: false, frozen: true, width: 200}
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
            sortorder: "desc"
        };
        initTable(ctx+"/device/monitorDataConf/queryData",colModel,tableCallBack,config);
    });
    initBtnByForm2();


    function viewMethod(sid) {
        showLayerMedium("设备实时监控详情", ctx + '/device/monitorDataConf/toView?sid=' + sid);
    }
    function editMethod(sid) {
        showLayerMedium("编辑设备实时监控信息", ctx + '/device/monitorDataConf/toEdit?sid=' + sid);
    }
</script>
</body>
</html>


