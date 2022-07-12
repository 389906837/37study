<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title>验证码记录查询</title>
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
                    <h5><spring:message code="xx.verification.code.sending.record" /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-inline">
                                    <input type="text" name="smerchantCode" id="smerchantCode" value="" placeholder='<spring:message code="main.merchant.number" />...' class="layui-input">
                                </div>
                                <div class="layui-inline">
                                    <input type="text" name="merchantName" id="merchantName" value="" placeholder='<spring:message code="main.merchant.name" />...' class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-inline">
                                <input type="text" name="smsgRecipient" id="smsgRecipient" value="" placeholder='<spring:message code="xx.message.recipient" />...' class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <select class="form-control" name="istate" id="istate">
                                    <option value=""><spring:message code="xx.please.select.message.status" /></option>
                                    <option value="1"><spring:message code="xx.to.be.sent" /></option>
                                    <option value="2"><spring:message code="xx.sending" /></option>
                                    <option value="3"><spring:message code="xx.successful.delivery" /></option>
                                    <option value="4"><spring:message code="xx.fail.in.send" /></option>
                                    <option value="5"><spring:message code="xx.excess.invalid" /></option>
                                </select>
                            </div>
                            <div class="layui-inline" style="width: 180px">
                                <input type="text" name="tbeginSendDatetimeStr" id="tbeginSendDatetimeStr" placeholder='<spring:message code="xx.delivery.time" />' class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 180px">
                                <input type="text" name="taddTimeStr" id="taddTimeStr" placeholder='<spring:message code="main.add.time" />' class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="scontent" id="scontent" value="" placeholder='<spring:message code="xx.message.content" />...' class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i class="layui-icon">&#xe615;</i><spring:message code="main.query" /></button>
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
<script type="text/javascript" src="${staticSource}/resources/page/xx/msgtask/codeTask-list.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    /**
     * 选择日期范围
     */
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //执行一个laydate实例(发送时间)
        laydate.render({
            elem: '#tbeginSendDatetimeStr', //指定元素
            range: true,
            type: 'date'
        });
        //添加时间
        laydate.render({
            elem: '#taddTimeStr', //指定元素
            range: true,
            type: 'date'
        });
    });
    // 渲染数据
    var colModel = [{name: 'id', index: 'id',hidden:true},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'SHCODE',label : '<spring:message code="main.merchant.number" />',index: 'SHCODE',width:180},
        {name: 'MERCHANTNAME',label : '<spring:message code="main.merchant.name" />',index: 'MERCHANTNAME',width:320,sortable : false},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'smerchantCode', index: 'smerchant_code', hidden: true},
        </c:if>
        {name: 'SMSG_RECIPIENT',index: 'SMSG_RECIPIENT',label : '<spring:message code="xx.message.recipient" />',sortable : false,width:130},
        {name: 'ISTATE',index: 'ISTATE',label : '<spring:message code="xx.message.status" />', editable: true,
            formatter:"select",editoptions:{value:'1:<spring:message code="xx.to.be.sent" />;2:<spring:message code="xx.sending" />;3:<spring:message code="xx.successful.delivery" />;4:<spring:message code="xx.fail.in.send" />;5:<spring:message code="xx.excess.invalid" />'},sortable : false,width:140},
        {name: 'SCONTENT',index: 'SCONTENT',label : '<spring:message code="xx.message.content" />',sortable : false,width:370},
        {name: 'TBEGIN_SEND_DATETIME',index: 'TBEGIN_SEND_DATETIME',label : '<spring:message code="xx.delivery.time" />',formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:220},
        {name: 'TADDTIME',index: 'TADDTIME',label : '<spring:message code="main.add.time" />',formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:260},

    ];
    // <spring:message code="xx.form.callback" />
    function tableCallBack() {
        $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
    }
    // <spring:message code="xx.initialize.the.form.pass.in.the.required.parameters.of.the.page" />
    $(function () {
        var config = {
            rownumbers: true,
            multiselect:false,
            sortname:'TBEGIN_SEND_DATETIME',
            sortorder:'desc'
        };
        initTable(ctx+"/msgTask/queryCodeData",colModel,tableCallBack,config);
    });
    // <spring:message code="xx.call.the.button.button.method.query.clear" />
    initBtnByForm2();
</script>
</body>
</html>

