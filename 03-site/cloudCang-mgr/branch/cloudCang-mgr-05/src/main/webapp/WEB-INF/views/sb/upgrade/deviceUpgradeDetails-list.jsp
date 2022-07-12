<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备升级记录明细列表</title>
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
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="sdeviceCode" id="sdeviceCode" value="" placeholder='<spring:message code="main.device.id" />...' class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sname" id="sname" value="" placeholder='<spring:message code="main.device.name" />...' class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sdeviceAddress" id="sdeviceAddress" value="" placeholder='<spring:message code="main.device.address" />...' class="layui-input">
                            </div>
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantCode" id="merchantCode" value="" placeholder='<spring:message code="main.merchant.number" />...' class="layui-input">
                                </div>
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantName" id="merchantName" value="" placeholder='<spring:message code="main.merchant.name" />...' class="layui-input">
                                </div>
                            </c:if>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <select class="form-control"  name="istatus" id="istatus" >
                                    <option value=""><spring:message code='sb.upgrade.status' /></option>
                                    <option value="10"><spring:message code="main.to.be.processed" /></option>
                                    <option value="20"><spring:message code='sb.update.successed' /></option>
                                    <option value="30"><spring:message code='sb.upgrade.unsuccessful' /></option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query">
                                    <i class="layui-icon">&#xe615;</i><spring:message code="main.query" />
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset">
                                    <i class="layui-icon">&#x1006;</i><spring:message code="main.clear" />
                                </button>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <%-- <button class="layui-btn layui-btn-sm " data-type="add"><i class="layui-icon"></i><spring:message code="main.add" /></button>
                             <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i><spring:message code="main.delete" /></button>--%>
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
<input type="hidden" name="smainId" id="smainId" value="${smainId}" class="layui-input"/>
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    var colModel = [
        {name: 'id', index: 'id', hidden: true},
        {name: 'sdeviceCode', index: 'SDEVICE_CODE', label: '<spring:message code="main.device.id" />',width:120,sortable : false},
        {name: 'sname', index: 'SNAME', label: '<spring:message code="main.device.name" />',width:120,sortable : false},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'merchantCode',label : '<spring:message code="main.merchant.number" />',index: 'smerchantCode',width:120},
        {name: 'merchantName',label : '<spring:message code="main.merchant.name" />',index: 'merchantName',width:120,sortable : false},
        </c:if>
        {name: 'sdeviceAddress', index: 'SDEVICE_ADDRESS', label: '<spring:message code="main.device.address" />',width:120,sortable : false},
        {
            name: 'istatus',
            index: 'ISTATUS',
            label: "<spring:message code='sb.upgrade.status' />",
            formatter: "select",
            editoptions: {value: '10:<spring:message code="main.to.be.processed" />;20:<spring:message code='sb.update.successed' />;30:<spring:message code='sb.upgrade.unsuccessful' />'}
            , width: 100
        },
        {name: 'sexceptionDesc', index: 'SEXCEPTION_DESC', label: "<spring:message code='sb.reason.for.failure' />",width:120,sortable : false},
        {
            name: 'tstartTime', index: 'TSTART_TIME', label: "<spring:message code='sb.upgrade.start.time' />", formatter: function (value) {
                if(isEmpty(value)){
                    return '';
                }
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
            }
        },
        {
            name: 'tendTime', index: 'TEND_TIME', label: "<spring:message code='sb.upgrade.end.time' />", formatter: function (value) {
                if(isEmpty(value)){
                    return '';
                }
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
            }
        },
        {name: "process",title:false, index: "process", label: '<spring:message code="main.operation" />', sortable: false, frozen: true, width: 100}
    ];
    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            var data = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = '';
                <shiro:hasPermission name="DEVICE_UPGRADE_DETAILS_VIEW">
                str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"viewMethod('"
                    + cl + "')\" title=\"<spring:message code='sh.view' />\" alt=\"<spring:message code='sh.view' />\" /> | ";
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
            sortname: "B.ISTATUS",
            sortorder: "desc"
        };
        var smainId = $("#smainId").val();
        initTable(ctx + "/device/upgradeDetails/queryData?smainId="+smainId,colModel,tableCallBack,config);
    });

    initBtnByForm2();

    function viewMethod(sid) {
        showLayerMedium("<spring:message code='sb.upgrade.details' />", ctx + '/device/upgradeDetails/toView?sid=' + sid);
    }


</script>
</body>
</html>


