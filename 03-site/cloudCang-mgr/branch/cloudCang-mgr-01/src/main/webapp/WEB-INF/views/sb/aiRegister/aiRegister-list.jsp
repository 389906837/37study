<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>AI设备注册授权列表</title>
<link href="${staticSource}/resources/layui/css/layui.css?5" rel="stylesheet">
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
                    <h5>AI设备注册管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="sdeviceCode" id="sdeviceCode" value="" placeholder="AI设备编号..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="deviceCode" id="deviceCode" value="" placeholder="大屏设备编号..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="deviceName" id="deviceName" value="" placeholder="大屏设备名称..." class="layui-input">
                            </div>
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantCode" id="merchantCode" value="" placeholder="商户编号..."
                                           class="layui-input">
                                </div>
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantName" id="merchantName" value="" placeholder="商户名称..."
                                           class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-input-inline">
                                <select class="form-control"  name="istatus" id="istatus" >
                                    <option value="">请选择注册状态</option>
                                    <option value="10">待审核</option>
                                    <option value="20">审核通过</option>
                                    <option value="30">审核拒绝</option>
                                    <option value="40">已注册</option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sauditUser" id="sauditUser" value="" placeholder="审核人..." class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item" style="margin-top: 10px;">
                            <div class="layui-input-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query">
                                    <i class="layui-icon">&#xe615;</i>查询
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset">
                                    <i class="layui-icon">&#x1006;</i>清除
                                </button>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <%-- <button class="layui-btn layui-btn-sm " data-type="add"><i class="layui-icon"></i>添加</button>
                             <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i>删除</button>--%>
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
    var colModel = [
        {name: 'id', index: 'id', hidden: true},
        {name: 'sdeviceCode', index: 'SDEVICE_CODE', label: "AI设备编号",width:120},
        {name: 'deviceCode', index: 'deviceCode', label: "大屏设备编号",width:120,sortable : false},
        {name: 'deviceName', index: 'deviceName', label: "大屏设备名称",width:120,sortable : false},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'merchantCode', index: 'merchantCode', label: "商户编号",width:120,sortable : false},
        {name: 'merchantName', index: 'merchantName', label: "商户名称",width:120,sortable : false},
        </c:if>
        {name: 'sregIp', index: 'SNAME', label: "注册码",width:100},
        {name: 'tregTime', index: 'TREG_TIME', label: "注册时间",width: 80,formatter: function (value) {
            if(isEmpty(value)){
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');}},
        {
            name: 'istatus',
            index: 'ISTATUS',
            label: "注册码状态",
            formatter: "select",
            editoptions: {value: '10:待审核;20:审核通过;30:审核拒绝;40:已注册'}
            ,width:100
        },
        {name: 'sauditUser', index: 'SAUDIT_USER', label: "审核人",width:100},
        {name: 'tauditTime', index: 'TAUDIT_TIME', label: "审核时间",width: 80,formatter: function (value) {
            if(isEmpty(value)){
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');}},
        {name: 'sremark', index: 'SREMARK', label: "审核备注",width:100},
        {name: "process", title:false, index: "process", label: "操作", sortable: false, frozen: true, width: 100}
    ];
    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            var data = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = '';
                if(data[i].istatus == 30) {
                    <shiro:hasPermission name="DEVICE_REGISTER_EDIT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                        + cl + "')\" title='编辑' alt='编辑'  /> | ";
                    </shiro:hasPermission>
                } else if (data[i].istatus == 10) {
                    <shiro:hasPermission name="DEVICE_REGISTER_AUDIT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/review.png' class=\"oper_icon\" onclick=\"auditMethod('"
                        + cl + "')\" title='审核' alt='审核'  /> | ";
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
            sortname: "B.ISTATUS=10",
            sortorder: "desc"
        };
        initTable(ctx+"/ai/aiRegister/queryData",colModel,tableCallBack,config);
    });
    initBtnByForm2();


    function editMethod(sid) {
        showLayerOne("编辑设备注册信息", ctx + '/ai/aiRegister/toEdit?sid=' + sid);
    }
    function auditMethod(sid) {
        showLayerOne("审核设备注册信息", ctx + '/ai/aiRegister/toAudit?sid=' + sid);
    }
</script>
</body>
</html>


