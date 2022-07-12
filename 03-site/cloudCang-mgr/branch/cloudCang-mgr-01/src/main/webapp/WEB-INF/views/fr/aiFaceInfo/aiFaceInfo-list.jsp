<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>人脸注册信息列表</title>
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
                    <h5>人脸注册信息管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <%--<div class="layui-input-inline">--%>
                                <%--<input type="text" name="deviceCode" id="deviceCode" value="" placeholder="售货机设备编号..." class="layui-input">--%>
                            <%--</div>--%>
                            <%--<div class="layui-input-inline">--%>
                                <%--<input type="text" name="deviceName" id="deviceName" value="" placeholder="售货机设备名称..." class="layui-input">--%>
                            <%--</div>--%>
                            <div class="layui-input-inline">
                                <input type="text" name="sregAiCode" id="sregAiCode" value="" placeholder="AI设备编号..." class="layui-input">
                            </div>
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-input-inline">
                                    <input type="text" name="smerchantCode" id="smerchantCode" value="" placeholder="商户编号..."
                                           class="layui-input">
                                </div>
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantName" id="merchantName" value="" placeholder="商户名称..."
                                           class="layui-input">
                                </div>
                            </c:if>
                                <div class="layui-input-inline">
                                    <input type="text" name="smemberCode" id="smemberCode"  placeholder="会员编号..."
                                           class="layui-input">
                                </div>
                                <div class="layui-input-inline">
                                    <input type="text" name="smemberName" id="smemberName"  placeholder="会员名称..."
                                           class="layui-input">
                                </div>
                            <%--<div class="layui-input-inline">--%>
                                <%--<select class="form-control"  name="istatus" id="istatus" >--%>
                                    <%--<option value="">请选择注册状态</option>--%>
                                    <%--<option value="10">待审核</option>--%>
                                    <%--<option value="20">审核通过</option>--%>
                                    <%--<option value="30">审核拒绝</option>--%>
                                    <%--<option value="40">已注册</option>--%>
                                <%--</select>--%>
                            <%--</div>--%>
                            <%--<div class="layui-input-inline">--%>
                                <%--<input type="text" name="sauditUser" id="sauditUser" value="" placeholder="审核人..." class="layui-input">--%>
                            <%--</div>--%>
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
//        {name: 'deviceCode', index: 'deviceCode', label: "售货机设备编号",width:120},
//        {name: 'deviceName', index: 'deviceName', label: "售货机设备名称",width:120},
        {name: 'sregAiCode', index: 'SREG_AI_CODE', label: "AI设备编号",width:100},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode', index: 'SMERCHANT_CODE', label: "商户编号",width:120,sortable : false},
        {name: 'merchantName', index: 'merchantName', label: "商户名称",width:120,sortable : false},
        </c:if>
        {name: 'sfaceCode', index: 'SFACE_CODE', label: "人脸编号",width:100},
        {name: 'smemberCode', index: 'SMEMBER_CODE', label: "会员编号",width:100},
        {name: 'smemberName', index: 'SMEMBER_NAME', label: "会员用户名",width:100},
        {name: 'sdeviceAddress', index: 'SDEVICE_ADDRESS', label: "注册设备地址",width:100},
        {
            name: 'itype',
            index: 'ITYPE',
            label: "状态",
            formatter: "select",
            editoptions: {value: '10:注册;20:绑定;30:解绑;40:删除'}
            ,width:100
        },
        {
            name: 'ibindPayType',
            index: 'IBIND_PAY_TYPE',
            label: "绑定支付方式",
            formatter: "select",
            editoptions: {value: '10:微信;20:支付宝;30:其他'}
            ,width:100
        },
        {name: 'tregisterTime', index: 'TREGISTER_TIME', label: "注册时间",width: 80,formatter: function (value) {
            if(isEmpty(value)){
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');}},
        {name: 'sregIp', index: 'SREG_IP', label: "注册IP地址",width:100},
        {name: 'sgroupId', index: 'SGROUP_ID', label: "用户组",width:100},
//        {name: 'tauditTime', index: 'TAUDIT_TIME', label: "审核时间",width: 80,formatter: function (value) {
//            if(isEmpty(value)){
//                return '';
//            }
//            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');}},
//        {name: 'sremark', index: 'SREMARK', label: "审核备注",width:100},
        {name: "process", title:false, index: "process", label: "操作", sortable: false, frozen: true, width: 100}
    ];
    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            var data = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = '';
                <shiro:hasPermission name="AI_FACEINFO_VIEW">
                str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"viewMethod('"
                    + cl + "')\" title='查看' alt='查看'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="AI_FACEINFO_DELETE">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='删除' alt='删除'  /> | ";
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
            sortname: "B.ITYPE=10",
            sortorder: "desc"
        };
        initTable(ctx+"/aiFaceInfo/queryData",colModel,tableCallBack,config);
    });
    initBtnByForm2();


    function viewMethod(sid) {
        showLayerOne("查看人脸注册信息", ctx + '/aiFaceInfo/toView?sid=' + sid);
    }
    function delMethod(sid) {
        confirmDelTip("确定要删除数据?", ctx + "/aiFaceInfo/delete", {checkboxId: sid});
    }
</script>
</body>
</html>


