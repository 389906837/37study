<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title><spring:message code="wz.device.ad.mgr"/></title>
    <link href="${staticSource}/resources/layui/css/layui.css?201" rel="stylesheet">
    <link href="${staticSource}/resources/css/override.css" rel="stylesheet">
    <link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
    <style type="text/css">
        .ui-jqgrid tr.jqgrow td {white-space: normal !important;height: auto;}
    </style>
</head>

<body>
<div class="ibox-content">
    <div class="layui-form" id="searchForm">
        <div class="layui-form-item">
            <div class="layui-input-inline">
                <input type="text" name="stitle" id="stitle" value="" placeholder="<spring:message code='sb.ad.resource.title'/>" class="layui-input">
            </div>
            <div class="layui-input-inline">
                <input type="text" name="sregionName" id="sregionName" value="" placeholder="<spring:message code='wz.operation.area.name'/>" class="layui-input">
            </div>
            <div class="layui-input-inline">
                <input type="text" name="sregionCode" id="sregionCode" value="" placeholder="<spring:message code='wz.operation.area.number' />" class="layui-input">
            </div>
            <c:if test="${SESSION_KEY_ROOT_CHANNEL eq 1}">
                <div class="layui-input-inline">
                    <input type="text" name="schannelCode" id="schannelCode" value="" placeholder="<spring:message code='sb.ad.channel.code' />" class="layui-input">
                </div>
                <div class="layui-input-inline">
                    <input type="text" name="schannelName" id="schannelName" value="" placeholder="<spring:message code='sb.ad.channel.name' />" class="layui-input">
                </div>
            </c:if>
            <div class="layui-input-inline">
                <cang:select type="list" name="istatus" id="istatus" entire="true" value=""
                             list="{0:springMessageCode=sm.expired,1:springMessageCode=wz.in.delivery,2:springMessageCode=wz.pending,3:springMessageCode=wz.time.out}" entireName="springMessageCode=sb.device.select.ad.status"/>
            </div>
            <div class="layui-input-inline">
                <cang:select type="list" name="iadvType" id="iadvType" value=""
                             list="{10:springMessageCode=sh.image,20:springMessageCode=wz.audio,30:springMessageCode=wz.video}" entireName="springMessageCode=wz.please.select.an.ad.type" entire="true" />
            </div>
            <div class="layui-input-inline">
                <cang:select type="list" name="iuseType" id="iuseType" value=""
                             list="{0:springMessageCode=excel.false,1:springMessageCode=excel.true}" entireName="springMessageCode=wz.ad.is.inCommonUse" entire="true" />
            </div>

            <div class="layui-input-inline">
                <input type="text" name="startTimeStr" id="startTimeStr" placeholder="<spring:message code='main.start.time'/>" class="layui-input" />
            </div>
            <div class="layui-input-inline">
                <input type="text" name="endTimeStr" id="endTimeStr" placeholder="<spring:message code='main.end.time'/>" class="layui-input" />
            </div>
            <div class="layui-inline">
                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i class="layui-icon">&#xe615;</i><spring:message code="main.query"/></button>
                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i class="layui-icon">&#x1006;</i><spring:message code="main.clear"/></button>
            </div>
        </div>
        <div class="layui-form-item">
            <%--<shiro:hasPermission name="DEVICE_INFO_ADVERTIS_ADD">
                <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"><i class="layui-icon"></i>添加</button>
            </shiro:hasPermission>--%>
            <shiro:hasPermission name="DEVICE_INFO_ADVERTIS_DELETE">
                <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i><spring:message code="main.delete"/></button>
            </shiro:hasPermission>
        </div>
    </div>
    <div class="jqGrid_wrapper">
        <table id="gridTable"></table>
        <div id="gridPager"></div>
    </div>
</div>

<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    layui.use(['form', 'laydate'], function () {
        //选择日期范围
        var laydate = layui.laydate;
        laydate.render({
            elem: '#startTimeStr',
            range:true,
            type: 'date'
        });
        laydate.render({
            elem: '#endTimeStr',
            range:true,
            type: 'date'
        });
    });
    //渲染数据
    var colModel = [{name: 'id', index: 'id',hidden:true},{name: 'istatusTmp', index: 'istatusTmp',hidden:true},
        {name: 'sregionCode',label : "<spring:message code='wz.operation.area.number'/>",index: 'sregion_code',width:190},
        {name: 'sregionName',label : "<spring:message code='wz.operation.area.name'/>",index: 'sregionName',width:320,sortable : false},
        <c:if test="${SESSION_KEY_ROOT_CHANNEL eq 1}">
        {name: 'schannelCode',label : "<spring:message code='sb.ad.channel.code'/>",index: 'schannel_code',width:100},
        {name: 'schannelName',label : "<spring:message code='sb.ad.channel.name'/>",index: 'schannelName',width:120,sortable : false},
        </c:if>
        {name: 'stitle',index: 'stitle',label : "<spring:message code='sb.ad.resource.title'/>",width:140},
        {name: 'istatus',index: 'istatus',label : "<spring:message code='main.state'/>", editable: true,
            formatter:function (value) {return '<span class="'+ (({"0":"istatus-radius","1":"istatus-normal","2":"istatus-warm","3":"istatus-danger"})[value])+'"> '+(({"0":"● <spring:message code='sm.expired'/>","1":"● <spring:message code='wz.in.delivery'/>","2":"● <spring:message code='wz.pending'/>","3":"● <spring:message code='wz.time.out'/>"})[value])+'</span>'},
            sortable : false,width:100},
        {name: 'isort',index: 'isort',label : "<spring:message code='sys.business.sort'/>",width:90},
        {name: 'scontentUrl',index: 'scontent_url',label : "<spring:message code='sb.device.resource.url'/>",width:300},
        {name: 'iadvType',index: 'iadvType',label : "<spring:message code='wz.advertisement.type'/>",formatter:"select",editoptions:{value:'10:<spring:message code="sh.image"/>;20:<spring:message code="wz.audio"/>;30:<spring:message code="wz.video"/>'},width:100},
        {name: 'iuseType',index: 'iuseType',label : "<spring:message code='wz.ad.is.inCommonUse'/>",formatter:"select",editoptions:{value:'0:<spring:message code="excel.false"/>;1:<spring:message code="excel.true"/> '},width:310},
        {name: 'tstartDate',index: 'TSTART_DATE',label : "<spring:message code='main.start.date'/>",formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:100},
        {name: 'tendDate',index: 'TEND_DATE',label : "<spring:message code='main.end.date'/>",formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:100}
    ];
    // 表格回调
    function tableCallBack() {
        $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
    }

    $(function () {
        var config = {
            sortname: "isort",
            sortorder: "asc"
        };
        initTable(ctx + "/device/queryAdvertisData?deviceId=${deviceInfo.id}", colModel, tableCallBack, config);
    });

    //初始化form中按钮
    var initBtnConfig = {
        addUrl: ctx + '/device/toAdvertisBinding?sid=${deviceInfo.id}',
        deleteUrl: ctx + "/device/advertisDelete?sid=${deviceInfo.id}",
        addTitle: "<spring:message code='sb.add.advertising.resource'/>",
        addFn:showLayerMedium
    };
    initBtnByForm4(initBtnConfig);
</script>
</body>
</html>