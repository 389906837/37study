<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备故障记录</title>
<link href="${staticSource}/resources/layui/css/layui.css?1" rel="stylesheet">
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
                    <h5><spring:message code='sb.equipment.fault.record' /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm" style="margin-bottom:13px;">
                        <div class="layui-form-item">
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-inline">
                                    <input type="text" name="smerchantCode" id="smerchantCode" value="" placeholder='<spring:message code="main.merchant.number" />' class="layui-input">
                                </div>
                                <div class="layui-inline">
                                    <input type="text" name="merchantName" id="merchantName" value="" placeholder='<spring:message code="main.merchant.name" />' class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-inline">
                                <input type="text" name="sdeviceCode" id="sdeviceCode" value="" placeholder='<spring:message code="main.device.id" />' class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sbName" id="sbName" value="" placeholder='<spring:message code="main.device.name" />' class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sdeviceAddress" id="sdeviceAddress" value="" placeholder='<spring:message code="main.device.address" />' class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <select class="form-control"  name="itype" id="itype" >
                                    <option value=""><spring:message code='sb.please.select.the.type.of.fault.report' /></option>
                                    <option value="10"><spring:message code='sb.system.alert.long.connection' /></option>
                                    <option value="20"><spring:message code='sb.system.alert.short.connection' /></option>
                                    <option value="30"><spring:message code='sb.manual.declaration' /></option>
                                </select>
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="tdeclareTimeStr" id="tdeclareTimeStr" placeholder="<spring:message code='sb.filing.date' />" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sdeclareMan" id="sdeclareMan" value="" placeholder="<spring:message code='sb.reporter' />" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sdealwithTimeStr" id="sdealwithTimeStr" placeholder="<spring:message code='sb.processing.date' />" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sdealwithMan" id="sdealwithMan" value="" placeholder="<spring:message code='main.handler' />" class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <select class="form-control"  name="istatus" id="istatus" >
                                    <option value=""><spring:message code="main.state.select" /></option>
                                    <option value="10"><spring:message code="main.to.be.processed" /></option>
                                    <option value="20"><spring:message code='sb.processed' /></option>
                                    <%--<option value="30"><spring:message code='sb.deprecated' /></option>--%>
                                </select>
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="serrorCode" id="serrorCode" value="" placeholder="<spring:message code='sb.fault.error.code' />" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query" style="float:left">
                                    <i class="layui-icon">&#xe615;</i><spring:message code="main.query" />
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset" style="margin-bottom:0">
                                    <i class="layui-icon">&#x1006;</i><spring:message code="main.clear" />
                                </button>
                            </div>
                        </div>
                        <div class="layui-form-item" style="margin-bottom: 10px;">
                            <shiro:hasPermission name="DEVICE_MAL_ADD">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"
                                        style="float:left"><i class="layui-icon"></i><spring:message code="main.add" />
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="DEVICE_MAL_DELETE">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i><spring:message code="main.delete" />
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
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js?0820"></script>
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
        {name: 'smerchantCode',label : '<spring:message code="main.merchant.number" />',index: 'smerchant_code',width:170,sortable : false},
        {name: 'merchantName',label : '<spring:message code="main.merchant.name" />',index: 'merchantName',width:300,sortable : false},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'smerchantCode', index: 'smerchant_code', hidden: true},
        </c:if>
        {name: 'sdeviceCode', index: 'SDEVICE_CODE', label: '<spring:message code="main.device.id" />',width:150,sortable : false},
        {name: 'sbName', index: 'sbName', label: '<spring:message code="main.device.name" />',width:200,sortable : false},
        {name: 'sdeviceAddress', index: 'sdeviceAddress', label: '<spring:message code="main.device.address" />',width:220,sortable : false},
        {name: 'serrorCode',label : "<spring:message code='sb.fault.error.code' />",index: 'SERROR_CODE',width:200,sortable : false},
        {name: 'smalfunctionDesc',label : "<spring:message code='sb.fault.description' />",index: 'SMALFUNCTION_DESC',width:220,sortable : false},
        {name: 'itype',index: 'itype',label: "<spring:message code='sb.fault.declaration.type' />",formatter: "select", editoptions: {value: '10:<spring:message code='sb.system.alert.long.connection' />;20:<spring:message code='sb.system.alert.short.connection' />;30:<spring:message code='sb.manual.declaration' />'},width:260,sortable : false},
        {
            name: 'tdeclareTime', index: 'TDECLARE_TIME', label: "<spring:message code='sb.filing.date' />", width: 170,formatter: function (value) {
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');}
        },
        {name: 'sdeclareMan', index: 'SDECLARE_MAN', label: "<spring:message code='sb.reporter' />",width:120,sortable : false},
        {
            name: 'sdealwithTime', index: 'SDEALWITH_TIME', label: "<spring:message code='sb.processing.date' />", width: 180,formatter: function (value) {
            if (isEmpty(value)) {
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');},sortable : false
        },
        {name: 'sdealwithMan', index: 'SDEALWITH_MAN', label: "<spring:message code='main.handler' />",width:100,sortable : false},
        {name: 'istatus',index: 'istatus',label: '<spring:message code="main.state" />',formatter: "select", editoptions: {value: '10:<spring:message code="main.to.be.processed" />;20:<spring:message code='sb.processed' />'},width:120,sortable : false},
        {name: 'sremark',label : '<spring:message code="main.processing.notes" />',index: 'SREMARK',width:220,sortable : false},
        {name: "process",title:false, index: "process", label: '<spring:message code="main.operation" />', sortable: false, frozen: true, width: 200}
    ];
    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "scroll" });
            var data = $("#gridTable").jqGrid('getRowData');
            var merchantCode = "${SESSION_KEY_SMERCHANT_CODE}";
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = '';
                if (data[i].istatus == 10) {
                <shiro:hasPermission name="DEVICE_MAL_EDIT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                        + cl + "')\" title=\"<spring:message code='main.edit' />\" alt=\"<spring:message code='main.edit' />\" /> | ";
                </shiro:hasPermission>
                }
                if (data[i].istatus == 20) {
                <shiro:hasPermission name="DEVICE_MAL_DELETE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                        + cl + "')\" title='<spring:message code="main.delete" />' alt='<spring:message code="main.delete" />'  /> | ";
                </shiro:hasPermission>
                }
                if (data[i].istatus == 10) {
                    <shiro:hasPermission name="DEVICE_MAL_PROCESSED">
                    str += "<img src='${staticSource}/resources/images/oper_icon/process.png' class=\"oper_icon\" onclick=\"deviceFailure('"
                        + cl + "')\" title='<spring:message code='sb.processed' />' alt='<spring:message code='sb.processed' />'  /> | ";
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
        addTitle: "<spring:message code='sb.add.device.fault.information' />",
        addFn:showLayerMin
    };
    initBtnByForm4(initBtnConfig);

    function editMethod(sid) {
        showLayerMin("<spring:message code='sb.edit.device.fault.information' />", ctx + '/deviceMal/edit?sid=' + sid);
    }
    function delMethod(sid) {
        confirmDelTip('<spring:message code="main.delete.confirm" />', ctx + "/deviceMal/delete", {checkboxId: sid});
    }

    function deviceFailure(sid) {
        showLayerMin("<spring:message code='sb.modify.status' />", 'deviceFailureState?sid='+sid);
    }
</script>
</body>
</html>


