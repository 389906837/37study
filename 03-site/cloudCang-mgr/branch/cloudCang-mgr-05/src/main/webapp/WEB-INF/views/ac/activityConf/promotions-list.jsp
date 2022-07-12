<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title>促销活动列表</title>
    <link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
    <link href="${staticSource}/resources/css/override.css" rel="stylesheet">
    <link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
<style>
    .ui-jqgrid tr.jqgrow td {
        white-space: normal !important;
        height: auto;
    }
</style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox">
                <div class="ibox-title">
                    <h5><spring:message code='menu.sales.promotion' /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="sconfCode" id="sconfCode" value="" placeholder='<spring:message code="activity.code" />...' class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sconfName" id="sconfName" value="" placeholder='<spring:message code="activity.name"/>...' class="layui-input">
                            </div>
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-input-inline">
                                    <input type="text" name="smerchantCode" id="smerchantCode" value="" placeholder='<spring:message code="main.merchant.number" />...' class="layui-input">
                                </div>
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantName" id="merchantName" value="" placeholder='<spring:message code="main.merchant.name" />...' class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-input-inline">
                                <cang:select type="list" list="{0:springMessageCode=main.draft,1:springMessageCode=main.open,-1:springMessageCode=main.close}" id="iisAvailable" name="iisAvailable"  entire="true" entireName='springMessageCode=activity.status' />
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="activeStartTimeStr" id="activeStartTimeStr" placeholder='<spring:message code="activity.start.time"/>' class="layui-input" />
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="activeEndTimeStr" id="activeEndTimeStr" placeholder='<spring:message code="activity.end.time"/>' class="layui-input" />
                            </div>
                            <div class="layui-input-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i class="layui-icon">&#xe615;</i><spring:message code="main.query"/></button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i class="layui-icon">&#x1006;</i><spring:message code="main.clear"/></button>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <shiro:hasPermission name="ACT_PROMOTIONS_ADD">
                            <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"><i class="layui-icon"></i><spring:message code="main.add"/></button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="ACT_PROMOTIONS_DELETE">
                            <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i><spring:message code="main.delete"/></button>
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
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    var colModel = [{name: 'id', index: 'id',hidden:true},{name: 'idiscountWay',hidden:true},
        {name: 'sconfCode',label : '<spring:message code="activity.code" />',index: 'sconf_code'},
        {name: 'sconfName',index: 'sconf_name',label : '<spring:message code="activity.name"/>',width:180},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode',label : '<spring:message code="main.merchant.number" />',index: 'smerchant_code'},
        {name: 'merchantName',label : '<spring:message code="main.merchant.name" />',index: 'sname',width:260},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'smerchantCode',index: 'smerchant_code',hidden:true},
        </c:if>
        {name: 'discountWay',index: 'idiscount_way',label : '<spring:message code="activity.type"/>'},
        {name: 'iisAvailable',index: 'iis_available',label : '<spring:message code="activity.status"/>'  ,
            formatter:function (value, options, rowObject) {return '<span id="iisAvailable_'+rowObject.id+'" data="'+value+'" class="'+ (({"1":"istatus-normal","0":"istatus-warm","-1":"istatus-danger"})[value])+'"> '+(({"1":'● <spring:message code="main.enable"/>',"0":'● <spring:message code="main.draft"/>',"-1":"●<spring:message code="main.close"/> "})[value])+'</span>'}
        },
        {name: 'irangeType',index: 'irange_type',label : '<spring:message code="activity.equipment"/>',sortable : false,formatter:function(value,rowObject){
            return "<a href='javascript:void(0);' onclick=\"viewDevice('"+rowObject.rowId+"')\"><spring:message code="activity.equipment.info"/></a>";}},
        {name: 'irangeType',index: 'irange_type',label : '<spring:message code="activity.product"/>',sortable : false,formatter:function(value,rowObject){
            return "<a href='javascript:void(0);' onclick=\"viewCommodity('"+rowObject.rowId+"')\"><spring:message code="activity.product.info"/></a>";}},
        {name: 'tactiveStartTime',label : '<spring:message code="activity.start.time"/>',index: 'tactive_start_time',formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');}},
        {name: 'tactiveEndTime',label : '<spring:message code="activity.end.time"/>',index: 'tactive_end_time',formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');}},
        {name: 'ijoinNum',index: 'ijoin_num',label : '<spring:message code="activity.participants.count"/>'},
        {name: 'sdescription',index: 'sdescription',label : '<spring:message code="main.remarks"/>',sortable : false},
        {name : "process", title:false ,index : "process",label : '<spring:message code="main.operation"/>',sortable : false,frozen:true,width:350}
    ];
    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function(){
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "scroll" });
            var data = $("#gridTable").jqGrid('getRowData');
            var merchantCode = "${SESSION_KEY_SMERCHANT_CODE}";
            for ( var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = "";
                <shiro:hasPermission name="ACT_PROMOTIONS_VIEW">
                    str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"viewMethod('"
                        + cl + "')\" title='<spring:message code="activity.info"/>activity.start' alt='<spring:message code="activity.info"/>activity.start'  /> | ";
                </shiro:hasPermission>
                if(merchantCode == data[i].smerchantCode) {
                    if ($('#iisAvailable_'+cl).attr('data') == 0) {
                        <shiro:hasPermission name="ACT_PROMOTIONS_ENABLED">
                            str += "<img src='${staticSource}/resources/images/oper_icon/open.png' class=\"oper_icon\" onclick=\"enabledMethod('"
                                + cl + "')\" title='<spring:message code="activity.start"/>activity.start' alt='<spring:message code="activity.start"/>activity.start'  /> | ";
                        </shiro:hasPermission>
                        <shiro:hasPermission name="ACT_PROMOTIONS_EDIT">
                            str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                                + cl + "')\" title='<spring:message code="activity.edit"/>' alt='<spring:message code="activity.edit"/>'  /> | ";
                        </shiro:hasPermission>
                    } else if ($('#iisAvailable_'+cl).attr('data') == 1) {
                        <shiro:hasPermission name="ACT_PROMOTIONS_CLOSED">
                            str += "<img src='${staticSource}/resources/images/oper_icon/close.png' class=\"oper_icon\" onclick=\"closedMethod('"
                                + cl + "')\" title='<spring:message code="activity.close"/>' alt='<spring:message code="activity.close"/>'  /> | ";
                        </shiro:hasPermission>
                    }
                    if ($('#iisAvailable_'+cl).attr('data') != 1) {
                        <shiro:hasPermission name="ACT_PROMOTIONS_DELETE">
                            str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                                + cl + "')\" title='<spring:message code="activity.delet"/>' alt='<spring:message code="activity.delet"/>'  /> | ";
                        </shiro:hasPermission>
                    }
                }
                if(data[i].idiscountWay == 40) {//下单返券
                    <shiro:hasPermission name="ACT_PROMOTIONS_COUPON_RULE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/coupon_rule.png' class=\"oper_icon\" onclick=\"couponRule('"
                        + cl + "',"+(merchantCode == data[i].smerchantCode)+","+ $('#iisAvailable_'+cl).attr('data') +")\" title='<spring:message code="activity.rule"/>' alt='<spring:message code="activity.rule"/>'  /> | ";
                    </shiro:hasPermission>
                }
                $("#gridTable").jqGrid('setRowData',
                    cl, {
                        process : str.length > 0 ? str.substr(0, str.lastIndexOf(" | ")) : ""
                    });
            }
        }, 0);
    }

    $(function () {
        var config = {
            shrinkToFit: false,
            autoScroll: true,
            sortname:"A.IIS_AVAILABLE=1",
            sortorder: "desc",
        };
        initTable(ctx+"/activityConf/queryPromotionsData",colModel,tableCallBack,config);
    });
    var initBtnConfig = {
        addUrl: ctx+'/activityConf/editPromotions',
        deleteUrl: ctx+"/activityConf/delete",
        addTitle: '<spring:message code="activity.add"/>',
        addFn:showLayerMedium
    };
    initBtnByForm4(initBtnConfig);
    layui.use('laydate', function(){
        var laydate = layui.laydate;
        laydate.render({
            elem: '#activeStartTimeStr',
            range:true,
            type: 'date'
        });
        laydate.render({
            elem: '#activeEndTimeStr',
            range:true,
            type: 'date'
        });
    });
    layui.use('form', function () {
        <c:if test="${!empty method}">
            <c:if test="${method eq 'add'}">
                if($.isFunction(initBtnConfig.addFn)) {
                    initBtnConfig.addFn(initBtnConfig.addTitle, initBtnConfig.addUrl, initBtnConfig.addModelConfig);
                } else {
                    showLayer(initBtnConfig.addTitle, initBtnConfig.addUrl, initBtnConfig.addModelConfig)
                }
            </c:if>
        </c:if>
    });
    //促销活动详情
    function viewMethod(sid) {
        showLayerMin('<spring:message code="activity.info"/>', ctx+'/activityConf/promotionsView?sid='+sid);
    }
    //查看活动设备
    function viewDevice(sid) {
        showLayerMedium('<spring:message code="activity.equipment.info"/>', ctx+'/activityConf/viewDevice?sid='+sid);
    }
    //查看活动商品
    function viewCommodity(sid) {
        showLayerMedium('<spring:message code="activity.product.info"/>', ctx+'/activityConf/viewCommodity?sid='+sid);
    }
    function editMethod(sid) {
        showLayerMedium('<spring:message code="activity.edit"/>', ctx+'/activityConf/editPromotions?sid='+sid);
    }
    //删除数据
    function delMethod(sid) {
        confirmDelTip('<spring:message code="activity.delet.confirm" />?',ctx+"/activityConf/delete",{checkboxId:sid});
    }
    //<spring:message code="main.close"/>活动
    function closedMethod(sid) {
        confirmOperTip('<spring:message code="activity.close.confirm" />?' , ctx+"/activityConf/closed",{sid:sid});
    }
    //启用活动
    function enabledMethod(sid) {
        confirmOperTip('<spring:message code="activity.start.confirm" />?' , ctx+"/activityConf/enabled",{sid:sid});
    }
    //送券规则
    function couponRule(sid,flag,istatus) {
        var title = "<spring:message code="activity.coupon.edit"/>";
        if(!flag || istatus == 1) {
            title = "<spring:message code="activity.coupon.info"/>";
        }
        showLayerMedium(title, ctx+'/couponRule/edit?sid='+sid);
    }

</script>
</body>
</html>

