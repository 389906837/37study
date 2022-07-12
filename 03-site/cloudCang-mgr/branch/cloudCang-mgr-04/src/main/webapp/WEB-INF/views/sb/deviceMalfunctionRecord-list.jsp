<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备故障记录</title>
<link href="${staticSource}/resources/layui/css/layui.css?1" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
<style>
</style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>设备故障记录</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm" style="margin-bottom:13px;">
                        <div class="layui-form-item">
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-inline">
                                    <input type="text" name="smerchantCode" id="smerchantCode" value="" placeholder="商户编号" class="layui-input">
                                </div>
                                <div class="layui-inline">
                                    <input type="text" name="merchantName" id="merchantName" value="" placeholder="商户名称" class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-inline">
                                <input type="text" name="sdeviceCode" id="sdeviceCode" value="" placeholder="设备编号" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sbName" id="sbName" value="" placeholder="设备名称" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sdeviceAddress" id="sdeviceAddress" value="" placeholder="设备地址" class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <select class="form-control"  name="itype" id="itype" >
                                    <option value="">请选择故障申报类型</option>
                                    <option value="10">系统警报-长连接</option>
                                    <option value="20">系统警报-短连接</option>
                                    <option value="30">手动申报</option>
                                </select>
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="tdeclareTimeStr" id="tdeclareTimeStr" placeholder="申报日期" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sdeclareMan" id="sdeclareMan" value="" placeholder="申报人" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sdealwithTimeStr" id="sdealwithTimeStr" placeholder="处理日期" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sdealwithMan" id="sdealwithMan" value="" placeholder="处理人" class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <select class="form-control"  name="istatus" id="istatus" >
                                    <option value="">请选择状态</option>
                                    <option value="10">待处理</option>
                                    <option value="20">已处理</option>
                                    <%--<option value="30">已废弃</option>--%>
                                </select>
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="serrorCode" id="serrorCode" value="" placeholder="故障错误代码" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query" style="float:left">
                                    <i class="layui-icon">&#xe615;</i>查询
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset" style="margin-bottom:0">
                                    <i class="layui-icon">&#x1006;</i>清除
                                </button>
                            </div>
                        </div>
                        <div class="layui-form-item" style="margin-bottom: 10px;">
                            <shiro:hasPermission name="DEVICE_MAL_ADD">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"
                                        style="float:left"><i class="layui-icon"></i>添加
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="DEVICE_MAL_DELETE">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i>删除
                                </button>
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
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<script src="${staticSource}/resources/js/xlsx.core.min.js"></script>
<script src="${staticSource}/resources/js/tableExport.min.js"></script>
<script src="${staticSource}/resources/js/tableExportComm.js?2"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    /**
     * 选择日期范围
     */
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //执行一个laydate实例(申报时间)
        laydate.render({
            elem: '#tdeclareTimeStr', //指定元素
            range:true,
            type:'date'
        });
        //处理时间
        laydate.render({
            elem: '#sdealwithTimeStr', //指定元素
            range:true,
            type:'date'
        });
    });

    // 渲染数据
    var colModel =[
        {name: 'id', index: 'id', hidden: true},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode',label : "商户编号",index: 'smerchant_code',width:150,sortable : false},
        {name: 'merchantName',label : "商户名称",index: 'merchantName',width:280,sortable : false},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'smerchantCode', index: 'smerchant_code', hidden: true},
        </c:if>
        {name: 'sdeviceCode', index: 'SDEVICE_CODE', label: "设备编号",width:100,sortable : false},
        {name: 'sbName', index: 'sbName', label: "设备名称",width:160,sortable : false},
        {name: 'sdeviceAddress', index: 'sdeviceAddress', label: "设备地址",width:180,sortable : false},
        {name: 'serrorCode',label : "故障错误代码",index: 'SERROR_CODE',width:120,sortable : false},
        {name: 'smalfunctionDesc',label : "故障问题描述",index: 'SMALFUNCTION_DESC',width:120,sortable : false},
        {name: 'itype',index: 'itype',label: "故障申报类型",formatter: "select", editoptions: {value: '10:系统警报-长连接;20:系统警报-短连接;30:手动申报'},width:140,sortable : false},
        {
            name: 'tdeclareTime', index: 'TDECLARE_TIME', label: "申报日期", width: 170,formatter: function (value) {
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');}
        },
        {name: 'sdeclareMan', index: 'SDECLARE_MAN', label: "申报人",width:80,sortable : false},
        {
            name: 'sdealwithTime', index: 'SDEALWITH_TIME', label: "处理日期", width: 170,formatter: function (value) {
            if (isEmpty(value)) {
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');},sortable : false
        },
        {name: 'sdealwithMan', index: 'SDEALWITH_MAN', label: "处理人",width:80,sortable : false},
        {name: 'istatus',index: 'istatus',label: "状态",formatter: "select", editoptions: {value: '10:待处理;20:已处理'},width:70,sortable : false},
        {name: 'sremark',label : "处理备注",index: 'SREMARK',width:100,sortable : false},
        {name: "process",title:false, index: "process", label: "操作", sortable: false, frozen: true, width: 180}
    ];
    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            var data = $("#gridTable").jqGrid('getRowData');
            var merchantCode = "${SESSION_KEY_SMERCHANT_CODE}";
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = '';
                if (data[i].istatus == 10) {
                <shiro:hasPermission name="DEVICE_MAL_EDIT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                        + cl + "')\" title='编辑' alt='编辑'  /> | ";
                </shiro:hasPermission>
                }
                if (data[i].istatus == 20) {
                <shiro:hasPermission name="DEVICE_MAL_DELETE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                        + cl + "')\" title='删除' alt='删除'  /> | ";
                </shiro:hasPermission>
                }
                if (data[i].istatus == 10) {
                    <shiro:hasPermission name="DEVICE_MAL_PROCESSED">
                    str += "<img src='${staticSource}/resources/images/oper_icon/process.png' class=\"oper_icon\" onclick=\"deviceFailure('"
                        + cl + "')\" title='已处理' alt='已处理'  /> | ";
                    </shiro:hasPermission>
                }
                $("#gridTable").jqGrid('setRowData',
                    cl, {
                        process: str.substr(0, str.lastIndexOf(" | "))
                    });
            }
            if(isExport) {//判断是否导出
                exportFileCallback();
            }
        }, 0);
    }

    $(function () {
        var config = {
            sortname: "ISTATUS=10",
            sortorder: "desc"
        };
        initTable(ctx+"/deviceMal/queryData",colModel,tableCallBack,config);
    });
    var initBtnConfig = {
        addUrl: ctx+'/deviceMal/edit',
        deleteUrl: ctx+"/deviceMal/delete",
        addTitle: '添加设备故障信息',
        addFn:showLayerMin
    };
    initBtnByForm4(initBtnConfig);

    function editMethod(sid) {
        showLayerMin("编辑设备故障信息", ctx + '/deviceMal/edit?sid=' + sid);
    }
    function delMethod(sid) {
        confirmDelTip("确定要删除数据?", ctx + "/deviceMal/delete", {checkboxId: sid});
    }

    function deviceFailure(sid) {
        showLayerMin("修改状态", 'deviceFailureState?sid='+sid);
    }
</script>
</body>
</html>


