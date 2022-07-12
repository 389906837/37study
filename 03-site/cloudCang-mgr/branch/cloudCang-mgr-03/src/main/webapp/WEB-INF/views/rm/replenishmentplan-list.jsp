<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>商品计划补货列表</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
<style>
    .layui-form-item .layui-inline{width:220px;}

</style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code="rm.commodity.plan.replenishment.inquiry" /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder='<spring:message code="rm.planned.replenishment.order.number" />...'
                                       class="layui-input">
                            </div>
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-inline">
                                    <input type="text" name="shcode" id="shcode" value=""
                                           placeholder='<spring:message code="main.merchant.number" />...' class="layui-input">
                                </div>
                                <div class="layui-inline">
                                    <input type="text" name="merchantName" id="merchantName" value=""
                                           placeholder='<spring:message code="main.merchant.name" />...' class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-inline">
                                <input type="text" name="sdeviceCode" id="sdeviceCode" value="" placeholder='<spring:message code="main.device.id" />...'
                                       class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sbsname" id="sbsname" value="" placeholder='<spring:message code="main.device.name" />...' class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sdeviceAddress" id="sdeviceAddress" value="" placeholder='<spring:message code="main.device.address" />...' class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="srenewalName" id="srenewalName" value="" placeholder='<spring:message code="rm.name.of.replenisher" />...'
                                       class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="srenewalMobile" id="srenewalMobile" value="" placeholder='<spring:message code="rm.replenisher-s.mobile.phone.number" />...'
                                       class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 220px">
                                <select class="form-control" name="istatus" id="istatus">
                                    <option value=""><spring:message code="rm.replenishment.status" /></option>
                                    <option value="10"><spring:message code="rm.to.be.delivered" /></option>
                                    <option value="20"><spring:message code="rm.distribution" /></option>
                                    <option value="30"><spring:message code="rm.completed" /></option>
                                    <option value="40"><spring:message code="rm.cancellation.of.distribution" /></option>
                                </select>
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="toperateStartDateStr" id="toperateStartDateStr"
                                       placeholder='<spring:message code="rm.generation.time" />' class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i
                                        class="layui-icon">&#xe615;</i><spring:message code="main.query" />
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i
                                        class="layui-icon">&#x1006;</i><spring:message code="main.clear" />
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
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/page/sl/vistlog/vistLog-list.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">

    /**
     * 选择日期范围
     */
    layui.use('laydate', function () {
        var laydate = layui.laydate;
        //执行一个laydate实例
        laydate.render({
            elem: '#toperateStartDateStr' //指定元素
            , range: true
            , type: 'date'
        });
    });

    // 渲染数据
    var colModel = [{name: 'id', index: 'id', hidden: true},
        {name: 'scode', index: 'SCODE', label: '<spring:message code="rm.planned.replenishment.order.number" />', sortable: false,width: 250},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'shcode', label: '<spring:message code="main.merchant.number" />', index: 'SHCODE', sortable: false, width: 150},
        {name: 'merchantName', label: '<spring:message code="main.merchant.name" />', index: 'MERCHANTNAME', sortable: false, width: 320},
        </c:if>
        {name: 'sdeviceCode', index: 'SDEVICE_CODE', label: '<spring:message code="main.device.id" />', width: 100},
        {name: 'sbsname', index: 'SBSNAME', label: '<spring:message code="main.device.name" />', width: 120},
        {name: 'sdeviceAddress', index: 'sdeviceAddress', label: '<spring:message code="main.device.address" />', sortable: false, width: 350},
        {name: 'srenewalName', index: 'srenewalName', label: '<spring:message code="rm.name.of.replenisher" />', sortable: false, width: 100},
        {name: 'srenewalMobile', index: 'SRENEWAL_MOBILE', label: '<spring:message code="rm.replenisher-s.mobile.phone.number" />',width: 180},
        {
            name: 'istatus', index: 'istatus', label: '<spring:message code="rm.replenishment.status" />', editable: true,
            formatter:function (value, options, rowObject) {return '<span id="istatus_'+rowObject.id+'" data="'+value+'" class="'+ (({"10":"istatus-warm","20":"istatus-radius","30":"istatus-normal","40":"istatus-danger"})[value])+'"> '+(({"10":'● <spring:message code="rm.to.be.delivered" />',"20":'● <spring:message code="rm.distribution" />',"30":'● <spring:message code="rm.completed" />',"40":'● <spring:message code="rm.cancellation.of.distribution" />'})[value])+'</span>'},
            sortable: false, width: 170
        },
        {
            name: 'tgenerateTime', index: 'TGENERATE_TIME', label: '<spring:message code="rm.generation.time" />', formatter: function (value) {
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }, width: 160
        },
        {name: 'sremark', index: 'sremark', label: '<spring:message code="main.remarks" />', sortable: false, width: 100},
        {name: 'process',title:false, index: 'process', label: '<spring:message code="main.operation" />', sortable: false, width: 260}
    ];
    // 表格回调
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "scroll" });
            var data = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = "";
                <shiro:hasPermission name="INFO_REPLENISHMENTPLANT">
                str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"queryInfoMethod('"
                    + cl + "')\" title='<spring:message code="rm.plan.replenishment.details" />' alt='<spring:message code="rm.plan.replenishment.details" />'  /> | ";
                </shiro:hasPermission>
                if ($('#istatus_'+cl).attr('data') == 10) {
                    <shiro:hasPermission name="REPLENISHMENTPLANT_DISTRIBUTION">
                    str += "<img src='${staticSource}/resources/images/oper_icon/rm_distribution.png' class=\"oper_icon\" onclick=\"istatusMethod('"
                        + cl + "',20)\" title='<spring:message code="rm.distribution" />' alt='<spring:message code="rm.distribution" />'  /> | ";
                    </shiro:hasPermission>
                    <shiro:hasPermission name="REPLENISHMENTPLANT_COMPLETED">
                    str += "<img src='${staticSource}/resources/images/oper_icon/rm_completed.png' class=\"oper_icon\" onclick=\"istatusMethod('"
                        + cl + "',30)\" title='<spring:message code="rm.completed" />' alt='<spring:message code="rm.completed" />'  /> | ";
                    </shiro:hasPermission>
                    <shiro:hasPermission name="REPLENISHMENTPLANT_CANCELLATION">
                    str += "<img src='${staticSource}/resources/images/oper_icon/rm_cancellation.png' class=\"oper_icon\" onclick=\"istatusMethod('"
                        + cl + "',40)\" title='<spring:message code="rm.cancellation.of.distribution" />' alt='<spring:message code="rm.cancellation.of.distribution" />'  /> | ";
                    </shiro:hasPermission>
                }
                if($('#istatus_'+cl).attr('data') == 20){
                    <shiro:hasPermission name="REPLENISHMENTPLANT_COMPLETED">
                    str += "<img src='${staticSource}/resources/images/oper_icon/rm_completed.png' class=\"oper_icon\" onclick=\"istatusMethod('"
                        + cl + "',30)\" title='<spring:message code="rm.completed" />' alt='<spring:message code="rm.completed" />'  /> | ";
                    </shiro:hasPermission>
                    <shiro:hasPermission name="REPLENISHMENTPLANT_CANCELLATION">
                    str += "<img src='${staticSource}/resources/images/oper_icon/rm_cancellation.png' class=\"oper_icon\" onclick=\"istatusMethod('"
                        + cl + "',40)\" title='<spring:message code="rm.cancellation.of.distribution" />' alt='<spring:message code="rm.cancellation.of.distribution" />'  /> | ";
                    </shiro:hasPermission>
                }
                $("#gridTable").jqGrid('setRowData',
                    cl, {
                        process: str.length > 0 ? str.substr(0, str.lastIndexOf(" | ")) : ""
                    });
            }
        }, 0);
    }
    // 初始化表格,传入页面所需参数
    $(function () {
        var config = {
            shrinkToFit: false,
            autoScroll: true,
            rownumbers: true,
            multiselect: false,
            sortname: 'A.ISTATUS=10',
            sortorder: 'desc'
        };
        initTable(ctx + "/replenishmentplant/queryData", colModel, tableCallBack, config);
    });
    // 调用Button按钮方法(查询,清除)
    var initBtnConfig = {
        addModelConfig: {area: ['60%', '80%']}
    };
    initBtnByForm2(initBtnConfig);

    function queryInfoMethod(sid) {
        showLayerMax('<spring:message code="rm.planned.product.replenishment.details" />', ctx + '/replenishmentplant/queryReplenishMentPlanInfo?sid=' + sid);
    }

    function istatusMethod(sid, type) {
        var alertStr = "";
        if (type == 20){
            alertStr = '<spring:message code="rm.whether.to.confirm.the.modification.of.the.order.status.to.the.delivery" />?';
        }
        if (type == 30) {
            alertStr = '<spring:message code="rm.whether.to.confirm.that.the.order.status.is.changed.to.completed" />?';
        }
        if (type == 40){
            alertStr = '<spring:message code="rm.whether.to.confirm.the.modification.of.the.order.status.to.cancel.the.distribution" />?';
        }
        confirmOperTip(alertStr, ctx + "/replenishmentplant/replenishMentStatus", {checkboxId: sid, type: type});
    }

</script>
</body>
</html>

