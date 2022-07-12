<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备管理员信息列表</title>
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
                    <h5>设备管理员信息</h5>
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
                                <select name="sareaCode" id="sareaCode" class="state" lay-filter="scategoryName">
                                    <option value="">请选择所属区域</option>
                                    <c:forEach items="${dic}" var="vo">
                                        <option value="${vo.svalue}">${vo.sname}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sareaPrincipal" id="sareaPrincipal" value="" placeholder="区域负责人..." class="layui-input">
                            </div><div class="layui-input-inline">
                                <input type="text" name="sdevicePrincipal" id="sdevicePrincipal" value="" placeholder="设备负责人..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="smaintain" id="smaintain" value="" placeholder="设备维护人..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sreplenishment" id="sreplenishment" value="" placeholder="补货员..." class="layui-input">
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
    var colModel = [
        {name: 'id', index: 'id', hidden: true},
        {name: 'scode', index: 'SCODE', label: "设备编号",width:120,sortable : false},
        {name: 'sname', index: 'SNAME', label: "设备名称",width:120,sortable : false},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'merchantCode', index: 'merchantCode', label: "商户编号",width:160,sortable : false},
        {name: 'merchantName', index: 'merchantName', label: "商户名称",width:120,sortable : false},
        </c:if>
        {   name: 'sareaCode',
            index: 'sareaCode',
            label: "所属区域",
            formatter: "select",
            editoptions: {value: '10:区域1;20:区域2;30:区域3'},
            width:120},
        {name: 'sareaPrincipal', index: 'sareaPrincipal', label: "区域负责人",width:120,sortable : false},
//                {name: 'sareaPrincipalContact', index: 'sareaPrincipalContact', label: "联系方式",width:160},
        {name: 'sdevicePrincipal', index: 'sdevicePrincipal', label: "设备负责人",width:160,sortable : false},
//                {name: 'sdevicePrincipalContact', index: 'sdevicePrincipalContact', label: "联系方式",width:160},
        {name: 'smaintain', index: 'smaintain', label: "设备维护人",width:160,sortable : false},
//                {name: 'smaintainContact', index: 'smaintainContact', label: "联系方式",width:160},
        {name: 'sreplenishment', index: 'sreplenishment', label: "补货员",width:160,sortable : false},
//                {name: 'sreplenishmentContact', index: 'sreplenishmentContact', label: "联系方式",width:160},
        {name: "process", title:false, index: "process", label: "操作", sortable: false, frozen: true, width: 200}
    ];
    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            var ids = $("#gridTable").jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                var cl = ids[i];
                var str = '';
                <shiro:hasPermission name="DEVICE_MANAGE_VIEW">
                    str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"viewMethod('"
                        + cl + "')\" title='查看' alt='查看'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="DEVICE_MANAGE_EDIT">
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
        initTable(ctx+"/device/manage/queryData",colModel,tableCallBack,config);
    });
    initBtnByForm2();

    function viewMethod(sid) {
        showLayerMin("设备管理员详情", ctx + '/device/manage/toView?sid=' + sid);
    }
    function editMethod(sid) {
        showLayerMedium("编辑设备管理员信息", ctx + '/device/manage/toEdit?sid=' + sid);
    }

</script>
</body>
</html>


