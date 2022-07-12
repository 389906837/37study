<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title>系统日志</title>
    <link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
    <link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code="sl.system.alert.log" /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <input type="text" name="smemberCode" id="smemberCode" value="" placeholder='<spring:message code="sl.operation.member.number" />...' class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="smemberName" id="smemberName" value="" placeholder='<spring:message code="sl.operating.member.username" />...' class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="soperIp" id="soperIp" value="" placeholder='<spring:message code="sl.operational.ip" />...' class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <cang:select type="list" list='{10:springMessageCode=sl.shopping.member,2:springMessageCode=sl.replenishment.member,3:springMessageCode=sl.system.user}' id="itype"
                                             name="itype" entire="true" entireName='springMessageCode=sl.please.select.the.type.of.operation'/>
                            </div>
                            <div class="layui-inline">
                                <cang:select type="dic" name="iproblemType" id="iproblemType" entire="true"
                                             entireName='springMessageCode=sl.please.select.an.alert.question.type' groupNo="SP000151"/>
                            </div>
                            <div class="layui-inline">
                                <cang:select type="dic" name="isystemType" id="isystemType" entire="true"
                                             entireName='springMessageCode=sl.please.select.the.source.system' groupNo="SP000152"/>
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="talarmTimeStr" id="talarmTimeStr" placeholder='<spring:message code="sl.alarm.time" />' class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sproblemDesc" id="sproblemDesc" value="" placeholder='<spring:message code="sl.alarm.problem.description" />...' class="layui-input">
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
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    /**
     * 选择日期范围
     */
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //执行一个laydate实例
        laydate.render({
            elem: '#talarmTimeStr', //指定元素
            range:true,
            type: 'date'
        });
    });

    // 渲染数据
    var colModel = [{name: 'id', index: 'id',hidden:true},
        {name: 'smemberCode',index: 'SMEMBER_CODE',label : '<spring:message code="sl.operation.member.number" />',sortable : false,width:190},
        {name: 'smemberName',index: 'SMEMBER_NAME',label : '<spring:message code="sl.operating.member.username" />',sortable : false,width:240},
        {name: 'soperIp',index: 'SOPER_IP',label : '<spring:message code="sl.operational.ip" />',sortable : false,width:150},
        {name: 'itype',index: 'ITYPE',label : '<spring:message code="sl.operation.member.type" />', editable: true,
            formatter:"select",editoptions:{value:'10:<spring:message code="sl.shopping.member" />;20:<spring:message code="sl.replenishment.member" />;30:<spring:message code="sl.system.user" />'},sortable : false,width:220},
        {name: 'iproblemType',index: 'IPROBLEM_TYPE',label : '<spring:message code="sl.alert.question.type" />', editable: true,
            formatter:"select",editoptions:{value:'10:<spring:message code="sl.shopping.member.inventory" />;20:;30:'},sortable : false,width:210},
        {name: 'isystemType',index: 'ISYSTEM_TYPE',label : '<spring:message code="sl.source.system" />', editable: true,
            formatter:"select",editoptions:{value:'cloud-cang-bzc:<spring:message code="sl.business.processing.service" />;20:;30:'},sortable : false,width:180},
        {name: 'talarmTime',index: 'TALARM_TIME',label : '<spring:message code="sl.alarm.time" />',formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:160},
        {name: 'sproblemDesc',index: 'SPROBLEM_DESC',label : '<spring:message code="sl.alarm.problem.description" />',sortable : false,width:380},
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
            sortname:'talarm_time',
            sortorder:'desc'
        };
        initTable(ctx+"/systemAlarm/queryData",colModel,tableCallBack,config);
    });
    // 调用Button按钮方法(查询,清除)
    initBtnByForm2();
</script>
</body>
</html>

