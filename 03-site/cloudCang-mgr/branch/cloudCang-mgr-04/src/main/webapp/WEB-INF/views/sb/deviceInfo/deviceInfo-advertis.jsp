<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title>广告资源管理</title>
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
                <input type="text" name="sregionCode" id="sregionCode" value="" placeholder="运营区域编号..." class="layui-input">
            </div>
            <div class="layui-input-inline">
                <input type="text" name="sregionName" id="sregionName" value="" placeholder="运营区域名称..." class="layui-input">
            </div>
            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                <div class="layui-input-inline">
                    <input type="text" name="smerchantCode" id="smerchantCode" value="" placeholder="商户编号..." class="layui-input">
                </div>
                <div class="layui-input-inline">
                    <input type="text" name="merchantname" id="merchantname" value="" placeholder="商户名称..." class="layui-input">
                </div>
            </c:if>
            <div class="layui-input-inline">
                <input type="text" name="stitle" id="stitle" value="" placeholder="资源标题..." class="layui-input">
            </div>
            <div class="layui-input-inline">
                <cang:select type="list" name="istatus" id="istatus" entire="true" value=""
                             list="{0:已过期,1:投放中,2:待投放,3:暂停}" entireName="请选择广告状态"/>
            </div>
            <div class="layui-input-inline">
                <cang:select type="list" name="iadvType" id="iadvType" value=""
                             list="{10:图片,20:视频,30:音频}" entireName="请选择广告类型" entire="true" />
            </div>
            <div class="layui-input-inline">
                <cang:select type="list" name="iuseType" id="iuseType" value=""
                             list="{0:否,1:是}" entireName="是否通用" entire="true" />
            </div>

            <div class="layui-input-inline">
                <input type="text" name="startTimeStr" id="startTimeStr" placeholder="开始时间" class="layui-input" />
            </div>
            <div class="layui-input-inline">
                <input type="text" name="endTimeStr" id="endTimeStr" placeholder="结束时间" class="layui-input" />
            </div>
            <div class="layui-inline">
                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i class="layui-icon">&#xe615;</i>查询</button>
                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i class="layui-icon">&#x1006;</i>清除</button>
            </div>
        </div>
        <div class="layui-form-item">
            <%--<shiro:hasPermission name="DEVICE_INFO_ADVERTIS_ADD">
                <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"><i class="layui-icon"></i>添加</button>
            </shiro:hasPermission>--%>
            <shiro:hasPermission name="DEVICE_INFO_ADVERTIS_DELETE">
                <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i>删除</button>
            </shiro:hasPermission>
        </div>
    </div>
    <div class="jqGrid_wrapper">
        <table id="gridTable"></table>
        <div id="gridPager"></div>
    </div>
</div>

<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
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
        {name: 'sregionCode',label : "运营区域编号",index: 'sregion_code',width:100},
        {name: 'sregionName',label : "运营区域名称",index: 'sregionName',width:120,sortable : false},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode',label : "商户编号",index: 'SMERCHANT_CODE',width:100},
        {name: 'merchantName',label : "商户名称",index: 'MERCHANT_NAME',width:120,sortable : false},
        </c:if>
        {name: 'stitle',index: 'stitle',label : "资源标题",width:120},
        {name: 'istatus',index: 'istatus',label : "状态", editable: true,
            formatter:function (value) {return '<span class="'+ (({"0":"istatus-radius","1":"istatus-normal","2":"istatus-warm","3":"istatus-danger"})[value])+'"> '+(({"0":"● 已过期","1":"● 投放中","2":"● 待投放","3":"● 暂停"})[value])+'</span>'},
            sortable : false,width:90},
        /*{name: 'isort',index: 'isort',label : "排序号",width:60},*/
        {name: 'scontentUrl',index: 'scontent_url',label : "资源地址",width:120},
        {name: 'iadvType',index: 'iadvType',label : "广告类型",formatter:"select",editoptions:{value:'10:图片;20:视频;30:音频'},width:70},
        {name: 'iuseType',index: 'iuseType',label : "是否通用",formatter:"select",editoptions:{value:'0:否;1:是'},width:70},
        {name: 'tstartDate',index: 'TSTART_DATE',label : "开始日期",formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:100},
        {name: 'tendDate',index: 'TEND_DATE',label : "结束日期",formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:100}
    ];
    // 表格回调
    function tableCallBack() {

    }

    $(function () {
        var config = {
            sortname: "field(A.ISTATUS,1,2,3,0)",
            sortorder: "asc"
        };
        initTable(ctx + "/device/queryAdvertisData?deviceId=${deviceInfo.id}", colModel, tableCallBack, config);
    });

    //初始化form中按钮
    var initBtnConfig = {
        addUrl: ctx + '/device/toAdvertisBinding?sid=${deviceInfo.id}',
        deleteUrl: ctx + "/device/advertisDelete?sid=${deviceInfo.id}",
        addTitle: '设备添加广告资源',
        addFn:showLayerMedium
    };
    initBtnByForm4(initBtnConfig);
</script>
</body>
</html>

