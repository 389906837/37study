<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备搬迁记录</title>
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
                    <h5><spring:message code='sb.equipment.relocation.record' /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm" style="margin-bottom:13px;">
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <input type="text" name="sdeviceCode" id="sdeviceCode" value="" placeholder='<spring:message code="main.device.id" />...' class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sbName" id="sbName" value="" placeholder='<spring:message code="main.device.name" />...' class="layui-input">
                            </div>
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-inline">
                                    <input type="text" name="smerchantCode" id="smerchantCode" value="" placeholder='<spring:message code="main.merchant.number" />...' class="layui-input">
                                </div>
                                <div class="layui-inline">
                                    <input type="text" name="merchantName" id="merchantName" value="" placeholder='<spring:message code="main.merchant.name" />...' class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-inline">
                                <input type="text" name="sdeviceOldAddress" id="SDEVICE_OLD_ADDRESS" value="" placeholder="<spring:message code='sb.device.original.address' />..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sdeviceNewAddress" id="SDEVICE_NEW_ADDRESS" value="" placeholder="<spring:message code='sb.device.new.address' />..." class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <select class="form-control"  name="istatus" id="istatus" >
                                    <option value=""><spring:message code='sb.please.select.the.relocation.status' /></option>
                                    <option value="10"><spring:message code="main.audited" /></option>
                                    <option value="20"><spring:message code="main.approval" /></option>
                                    <option value="30"><spring:message code="main.rejection" /></option>
                                    <option value="40"><spring:message code="main.completed" /></option>
                                    <option value="50"><spring:message code="main.cancelled" /></option>
                                </select>
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="tplanMoveTimeStr" id="tplanMoveTimeStr" placeholder="<spring:message code='sb.planned.relocation.time' />" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="tmoveTimeStr" id="tmoveTimeStr" placeholder="<spring:message code='sb.relocation.time' />" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="tauditTimeStr" id="tauditTimeStr" placeholder="<spring:message code='sb.audit.time' />" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="tapplyTimeStr" id="tapplyTimeStr" placeholder="<spring:message code='om.application.time' />" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sprincipal" id="sprincipal" value="" placeholder="<spring:message code='sb.relocation.person.in.charge' />..." class="layui-input">
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
                            <shiro:hasPermission name="DEVICE_MOVE_ADD">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"
                                        style="float:left"><i class="layui-icon"></i><spring:message code="main.add" />
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="DEVICE_MOVE_DELETE">
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

        //执行一个laydate实例(计划搬迁时间)
        laydate.render({
            elem: '#tplanMoveTimeStr', //指定元素
            range:true,
            type:'date'
        });
        //搬迁时间
        laydate.render({
            elem: '#tmoveTimeStr', //指定元素
            range:true,
            type:'date'
        });
        // <spring:message code='sb.audit.time' />
        laydate.render({
            elem: '#tauditTimeStr', //指定元素
            range:true,
            type:'date'
        });
        // 申请时间
        laydate.render({
            elem: '#tapplyTimeStr', //指定元素
            range:true,
            type:'date'
        });
    });


    // 渲染数据
    var colModel =[
        {name: 'id', index: 'id', hidden: true},
        {name: 'sdeviceCode', index: 'SDEVICE_CODE', label: '<spring:message code="main.device.id" />',width:140,sortable : false},
        {name: 'sbName', index: 'sbName', label: '<spring:message code="main.device.name" />',width:140,sortable : false},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode',label : '<spring:message code="main.merchant.number" />',index: 'smerchant_code',width:200,sortable : false},
        {name: 'merchantName',label : '<spring:message code="main.merchant.name" />',index: 'merchantName',width:330,sortable : false},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'smerchantCode', index: 'smerchant_code', hidden: true},
        </c:if>
        {name: 'sdeviceOldAddress', index: 'SDEVICE_OLD_ADDRESS', label: "<spring:message code='sb.device.original.address' />",width:280,sortable : false},
        {name: 'sdeviceNewAddress', index: 'SDEVICE_NEW_ADDRESS', label: "<spring:message code='sb.device.new.address' />",width:280,sortable : false},
        {name: 'istatus',index: 'ISTATUS',label: "<spring:message code='sb.audit.status' />",formatter: "select", editoptions: {value: '10:<spring:message code="main.audited" />;20:<spring:message code="main.approval" />;30:<spring:message code="main.rejection" />;40:<spring:message code="main.completed" />;50:<spring:message code="main.cancelled" />'},width:200},
        {
            name: 'tplanMoveTime', index: 'TPLAN_MOVE_TIME', label: "<spring:message code='sb.planned.relocation.date' />", width: 240,formatter: function (value) {
            if (isEmpty(value)) {
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd');},sortable : false
        },
        {
            name: 'tmoveTime', index: 'TMOVE_TIME', label: "<spring:message code='sb.relocation.date' />", width: 240,formatter: function (value) {
            if (isEmpty(value)) {
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd');}
        },
        {name: 'sprincipal',label : "<spring:message code='sb.relocation.person.in.charge' />",index: 'SPRINCIPAL',width:260,sortable : false},
        {
            name: 'tauditTime', index: 'TAUDIT_TIME', label: "<spring:message code='ac.date.of.review' />", width: 240,formatter: function (value) {
            if (isEmpty(value)) {
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd');},sortable : false
        },
        {name: 'sauditUser',label : '<spring:message code="main.reviewer" />',index: 'sauditUser',width:150,sortable : false},
        {
            name: 'tapplyTime', index: 'TAPPLY_TIME', label: "<spring:message code='sb.date.of.application' />", width: 240,formatter: function (value) {
            if (isEmpty(value)) {
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd');},sortable : false
        },
        {name: "process",title:false, index: "process", label: '<spring:message code="main.operation" />', sortable: false, frozen: true, width: 300}
    ];
    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
            var data = $("#gridTable").jqGrid('getRowData');
            var merchantCode = "${SESSION_KEY_SMERCHANT_CODE}";
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = '';
                if (data[i].istatus == 10 || data[i].istatus == 30 ){
                <shiro:hasPermission name="DEVICE_MOVE_EDIT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                        + cl + "')\" title=\"<spring:message code='main.edit' />\" alt=\"<spring:message code='main.edit' />\" /> | ";
                </shiro:hasPermission>
                }
                if (data[i].istatus == 10 || data[i].istatus == 30 ) {
                    <shiro:hasPermission name="DEVICE_REVIEW_ISTATUS">
                    str += "<img src='${staticSource}/resources/images/oper_icon/review.png' class=\"oper_icon\" onclick=\"deviceMoveState('"
                        + cl + "')\" title='<spring:message code='main.audit' />' alt='<spring:message code='main.audit' />'  /> | ";
                    </shiro:hasPermission>
                }
                if (data[i].istatus == 20) {
                <shiro:hasPermission name="DEVICET_COMPLETED">
                str += "<img src='${staticSource}/resources/images/oper_icon/completed.png' class=\"oper_icon\" onclick=\"comMethod('"
                    + cl + "')\" title='<spring:message code="main.completed" />' alt='<spring:message code="main.completed" />'  /> | ";
                </shiro:hasPermission>
                }
                if (data[i].istatus == 20) {
                <shiro:hasPermission name="DEVICET_CLOSE">
                str += "<img src='${staticSource}/resources/images/oper_icon/close.png' class=\"oper_icon\" onclick=\"closeMethod('"
                    + cl + "',50)\" title='<spring:message code="main.cancelled" />' alt='<spring:message code="main.cancelled" />'  /> | ";
                </shiro:hasPermission>
                }
                if (data[i].istatus == 10 || data[i].istatus == 30 || data[i].istatus == 40 || data[i].istatus == 50){
                <shiro:hasPermission name="DEVICE_MOVE_DELETE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                        + cl + "')\" title='<spring:message code="main.delete" />' alt='<spring:message code="main.delete" />'  /> | ";
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
            sortname: "ISTATUS=10",
            sortorder: "desc",
            shrinkToFit: false,
            autoScroll: true,
        };
        initTable(ctx+"/device/queryData",colModel,tableCallBack,config);
    });
    var initBtnConfig = {
        addUrl: ctx+'/device/edit',
        deleteUrl: ctx+"/device/delete",
        addTitle: "<spring:message code='sb.add.device.relocation.information' />",
        addFn:showLayerMedium
    };
    initBtnByForm4(initBtnConfig);

    function editMethod(sid) {
        showLayerMedium("<spring:message code='sb.edit.device.information' />", ctx + '/device/edit?sid=' + sid);
    }
    function delMethod(sid) {
        confirmDelTip('<spring:message code="main.delete.confirm" />', ctx + "/device/delete", {checkboxId: sid});
    }

    function deviceMoveState(sid) {
        showLayerMin("<spring:message code='main.audit' />", 'deviceMoveState?sid='+sid);
    }
    function comMethod(sid) {
        showLayerMin("<spring:message code='sb.relocation.status' />", 'deviceMoveCom?sid='+sid);
    }

//    function comMethod(sid,type) {
//        operDealwith(ctx+"/device/deviceComplete",{checkboxId:sid, type:type});
//    }
    function closeMethod(sid,type) {
        confirmDelTip("<spring:message code='sb.are.you.sure.you.want.to.cancel' />?",ctx+"/device/deviceClose",{checkboxId:sid, type:type});
    }
</script>
</body>
</html>


