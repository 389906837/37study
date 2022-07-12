<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title>短信记录查询</title>
    <link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
    <link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code='xx.sms.sending.record' /></h5>
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
                            <div class="layui-inline" style="width: 150px">
                                <input type="text" name="smsgRecipient" id="smsgRecipient" value="" placeholder="<spring:message code='xx.message.recipient' />..." class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 150px">
                                    <cang:select type="list" list='{1:springMessageCode=xx.to.be.sent,2:springMessageCode=xx.sending,3:springMessageCode=xx.successful.delivery,4:springMessageCode=xx.fail.in.send,5:springMessageCode=xx.excess.invalid}' id="istate"
                                                 name="istate" entire="true" entireName='springMessageCode=xx.please.select.message.status' />
                            </div>
                            <div class="layui-inline" style="width: 180px">
                                <input type="text" name="tbeginSendDatetimeStr" id="tbeginSendDatetimeStr" placeholder="<spring:message code='xx.delivery.time' />" class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 150px">
                                <select class="form-control" name="itimeout" id="itimeout">
                                    <option value=""><spring:message code='xx.please.choose.whether.to.overtime.or.not' /></option>
                                    <option value="0"><spring:message code='xx.no.timeout' /></option>
                                    <option value="1"><spring:message code='xx.time.out' /></option>
                                </select>
                            </div>
                            <div class="layui-inline" style="width: 150px">
                                <select class="form-control" name="isenderType" id="isenderType">
                                    <option value=""><spring:message code='xx.please.select.a.delivery.category' /></option>
                                    <option value="1"><spring:message code='xx.single.sending' /></option>
                                    <option value="2"><spring:message code='xx.group.sending' /></option>
                                </select>
                            </div>
                            <div class="layui-inline" style="width: 150px">
                                <select class="form-control" name="iisRealtime" id="iisRealtime">
                                    <option value=""><spring:message code='xx.please.select.the.sending.policy' /></option>
                                    <option value="1"><spring:message code='xx.real.time' /></option>
                                    <option value="2"><spring:message code='xx.delayed' /></option>
                                </select>
                            </div>
                            <div class="layui-inline" style="width: 150px">
                                <select class="form-control" name="iisBatchSend" id="iisBatchSend">
                                    <option value=""><spring:message code='xx.please.choose.whether.to.batch.or.not' /></option>
                                    <option value="0"><spring:message code="main.no" /></option>
                                    <option value="1"><spring:message code="main.yes" /></option>
                                </select>
                            </div>
                            <div class="layui-inline" style="width: 180px">
                                <input type="text" name="taddTimeStr" id="taddTimeStr" placeholder='<spring:message code="main.add.time" />' class="layui-input">
                            </div>
                            <%--<div class="layui-inline" style="width: 150px">--%>
                                <%--<input type="text" name="scontent" id="scontent" value="" placeholder="消息内容..." class="layui-input">--%>
                            <%--</div>--%>
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
<script type="text/javascript" src="${staticSource}/resources/page/xx/msgtask/msgTask-list.js"></script>
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
        {name: 'SHCODE',label : '<spring:message code="main.merchant.number" />',index: 'SHCODE',width:160},
        {name: 'MERCHANTNAME',label : '<spring:message code="main.merchant.name" />',index: 'MERCHANTNAME',width:340,sortable : false},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'smerchantCode', index: 'smerchant_code', hidden: true},
        </c:if>
        {name: 'SMSG_RECIPIENT',index: 'SMSG_RECIPIENT',label : "<spring:message code='xx.message.recipient' />",sortable : false,width:160},
        {name: 'ISTATE',index: 'ISTATE',label : "<spring:message code='xx.message.status' />", editable: true,
            formatter:"select",editoptions:{value:'1:<spring:message code='xx.to.be.sent' />;2:<spring:message code='xx.sending' />;3:<spring:message code='xx.successful.delivery' />;4:<spring:message code='xx.fail.in.send' />;5:<spring:message code='xx.excess.invalid' />'},sortable : false,width:140},
        {name: 'SCONTENT',index: 'SCONTENT',label : "<spring:message code='xx.message.content' />",sortable : false,width:340},
        {name: 'TBEGIN_SEND_DATETIME',index: 'TBEGIN_SEND_DATETIME',label : "<spring:message code='xx.delivery.time' />",formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:160},
        {name: 'ITIMEOUT',index: 'ITIMEOUT',label : "<spring:message code='xx.whether.it.times.out' />", editable: true,
            formatter:"select",editoptions:{value:'0:<spring:message code='xx.no.timeout' />;1:<spring:message code='xx.time.out' />'},sortable : false,width:180},
        {name: 'ISENDER_TYPE',index: 'ISENDER_TYPE',label : "<spring:message code='xx.send.category' />", editable: true,
            formatter:"select",editoptions:{value:'1:<spring:message code='xx.single.sending' />;2:<spring:message code='xx.group.sending' />'},sortable : false,width:160},
        {name: 'IIS_REALTIME',index: 'IIS_REALTIME',label : "<spring:message code='xx.sending.policy' />", editable: true,
            formatter:"select",editoptions:{value:'1:<spring:message code='xx.real.time' />;2:<spring:message code='xx.delayed' />'},sortable : false,width:160},
        {name: 'IIS_BATCH_SEND',index: 'IIS_BATCH_SEND',label : "<spring:message code='xx.whether.batch' />", editable: true,
            formatter:"select",editoptions:{value:'0:<spring:message code="main.no" />;1:<spring:message code="main.yes" />'},sortable : false,width:160},
        {name: 'TADDTIME',index: 'TADDTIME',label : '<spring:message code="main.add.time" />',formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:160}

    ];
    // 表格回调
    function tableCallBack() {
        $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
    }
    // 初始化表格,传入页面所需参数
    $(function () {
        var config = {
            rownumbers: true,
            multiselect:false,
            sortname:'TBEGIN_SEND_DATETIME',
            sortorder:'desc',
            shrinkToFit: false,
            autoScroll: true,
        };
        initTable(ctx+"/msgTask/queryData",colModel,tableCallBack,config);
    });
    // 调用Button按钮方法(查询,清除)
    initBtnByForm2();
</script>
</body>
</html>





