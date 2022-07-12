<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备网络摄像头列表</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>设备摄像头管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="sip" id="sip" value="" placeholder="IP..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sport" id="sport" value="" placeholder="端口号..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sserialNumber" id="sserialNumber" value="" placeholder="序列号..." class="layui-input">
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
                        <div class="layui-form-item">
                            <shiro:hasPermission name="DEVICE_NET_CAMERA_ADD">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"><i class="layui-icon"></i>添加</button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="DEVICE_NET_CAMERA_DELETE">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i>删除</button>
                            </shiro:hasPermission>
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
<input type="hidden" id="sdeviceId" name="sdeviceId" value="${sdeviceId}" />
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    var sdeviceId = $("#sdeviceId").val();
    var colModel =[
        {name: 'id', index: 'id', hidden: true},
        {name: 'sip', index: 'sip', label: "IP",width:120},
        {name: 'sport', index: 'sport', label: "端口号",width:120},
        {name: 'sserialNumber', index: 'sserialNumber', label: "序列号",width:120},
        {name: 'taddTime', index: 'TADD_TIME', label: "添加日期",formatter: function (value) {
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        },width:120},
        {name: "process", title:false,index: "process", label: "操作", sortable: false, frozen: true, width: 200}
    ];
    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            var ids = $("#gridTable").jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                var cl = ids[i];
                var str = '';
                <shiro:hasPermission name="DEVICE_NET_CAMERA_VIEW">
                str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"viewMethod('"
                    + cl + "')\" title='查看' alt='查看'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="DEVICE_NET_CAMERA_EDIT">
                str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                    + cl + "')\" title='编辑' alt='编辑'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="DEVICE_NET_CAMERA_DELETE">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='删除' alt='删除'  /> | ";
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
        initTable(ctx + "/cameraConfig/queryData?sdeviceId=" + sdeviceId, colModel, tableCallBack, config);
    });
    var initBtnConfig = {
        addUrl: ctx + '/cameraConfig/toAdd?sdeviceId=' + sdeviceId,
        deleteUrl: ctx + "/cameraConfig/delete",
        addTitle: '添加网络摄像头配置信息',
        addFn: showLayerMin
    };
    initBtnByForm4(initBtnConfig);

    function viewMethod(sid) {
        showLayerMin("网络摄像头配置详情", ctx + '/cameraConfig/view?sid=' + sid);
    }
    function editMethod(sid) {
        showLayerMin("编辑网络摄像头配置信息", ctx + '/cameraConfig/toEdit?sdeviceId=' + sdeviceId + "&sid=" + sid);
    }
    function delMethod(sid) {
        confirmDelTip("确定要删除数据?", ctx + "/cameraConfig/delete", {checkboxId: sid});
    }
</script>
</body>
</html>



