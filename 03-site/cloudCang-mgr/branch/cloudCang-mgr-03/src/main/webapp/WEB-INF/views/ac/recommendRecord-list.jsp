<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title>好友推荐</title>
    <link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
    <link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code='ac.recommended.friends' /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-input-inline">
                                    <input type="text" name="smerchantCode" id="smerchantCode" value="" placeholder='<spring:message code="main.merchant.number" />...' class="layui-input">
                                </div>
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantName" id="merchantName" value="" placeholder='<spring:message code="main.merchant.name" />...' class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-input-inline">
                                <input type="text" name="sreferrerCode" id="sreferrerCode" value="" placeholder="<spring:message code='ac.referrer.number' />..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sreferrerName" id="sreferrerName" value="" placeholder="<spring:message code='ac.referrer.name' />..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="spresenteeCode" id="spresenteeCode" value="" placeholder="<spring:message code='ac.referred.person.number' />..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="spresenteeName" id="spresenteeName" value="" placeholder="<spring:message code='ac.name.of.the.referee' />..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sorderCode" id="sorderCode" value="" placeholder="<spring:message code='ac.recommended.number.of.the.first.person' />..." class="layui-input">
                            </div>
                                <label class="layui-form-label" style="width: 90px"><spring:message code='ac.first.amount' /></label>
                                <div class="layui-input-inline" style="width: 70px;">
                                    <input type="text" name="sorderMoneyFrom" id="sorderMoneyFrom" autocomplete="off" class="layui-input">
                                </div>
                                <div class="layui-form-mid">~</div>
                                <div class="layui-input-inline" style="width: 70px;">
                                    <input type="text" name="sorderMoneyTo" id="sorderMoneyTo" autocomplete="off" class="layui-input">
                                </div>
                            <div class="layui-inline" style="width: 210px">
                                    <input type="text" name="toperateStartDateStr" id="toperateStartDateStr" placeholder='<spring:message code="sb.registration.time" />' class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 210px">
                                    <input type="text" name="torderDatetimeStr" id="torderDatetimeStr" placeholder="<spring:message code='ac.first.time' />" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn layui-btn" id="searchBtn" data-type="query"><i class="layui-icon">&#xe615;</i><spring:message code="main.query" /></button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i class="layui-icon">&#x1006;</i><spring:message code="main.clear" /></button>
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
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //执行一个laydate实例(注册时间)
        laydate.render({
            elem: '#toperateStartDateStr', //指定元素
            range: true,
            type: 'date'
        });
        //首单时间
        laydate.render({
            elem: '#torderDatetimeStr', //指定元素
            range: true,
            type: 'date'
        });
    });

    // 渲染数据
    var colModel = [{name: 'id', index: 'id',hidden:true},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode',label : '<spring:message code="main.merchant.number" />',index: 'smerchant_code'},
        {name: 'merchantName',label : '<spring:message code="main.merchant.name" />',index: 'merchantName'},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'smerchantCode',index: 'smerchant_code',hidden:true},
        </c:if>
        {name: 'sreferrerCode',index: 'sreferrerCode',label : "<spring:message code='ac.referrer.number' />",sortable : false,width:100},
        {name: 'sreferrerName',index: 'sreferrerName',label : "<spring:message code='ac.referrer.name' />",sortable : true,width:80},
        {name: 'spresenteeCode',index: 'spresenteeCode',label : "<spring:message code='ac.referred.person.number' />",sortable : true,width:80},
        {name: 'spresenteeName',index: 'spresenteeName',label : "<spring:message code='ac.name.of.the.referee' />",sortable : true,width:80},
        {name: 'sorderCode',index: 'sorderCode',label : "<spring:message code='ac.recommended.number.of.the.first.person' />",sortable : true,width:100},
        {name: 'sorderMoney',index: 'sorderMoney',label : "<spring:message code='ac.recommended.amount.of.the.first.person' />",sortable : true,width:100},
        {name: 'sorderActualMoney',index: 'sorderActualMoney',label : "<spring:message code='ac.the.recommended.amount.of.the.first.person.to.be.paid' />",sortable : true,width:110},
        {name: 'srewardInstruction',index: 'srewardInstruction',label : "<spring:message code='ac.integralrule.reward.description' />",sortable : true,width:90},
        {name: 'tregDatetime',index: 'TREG_DATETIME',label : '<spring:message code="sb.registration.time" />',formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:110},
        {name: 'torderDatetime',index: 'TORDER_DATETIME',label : "<spring:message code='ac.first.time' />",formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:110},
        {name: 'sremark',index: 'SREMARK',label : '<spring:message code="sm.remarks" />',sortable : false,width:150}
    ];
    // 表格回调
    function tableCallBack() {
    }
    // 初始化表格,传入页面所需参数
    $(function () {
        var config = {
            rownumbers: true,
            multiselect:false,
            sortname:'TORDER_DATETIME',
            sortorder:'desc'
        };
        initTable(ctx+"/recommendRecord/queryData",colModel,tableCallBack,config);
    });
    // 调用Button按钮方法(查询,清除)
    initBtnByForm2();
</script>
</body>
</html>

