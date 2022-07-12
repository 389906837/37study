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
                    <h5><spring:message code="sl.access.log" /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="susername" id="susername" value="" placeholder='<spring:message code="sl.username" />...' class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="srealName" id="srealName" value="" placeholder='<spring:message code="sl.username" />...' class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sip" id="sip" value="" placeholder='<spring:message code="sl.access.ip" />...' class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sactionpath" id="sactionpath" value="" placeholder='<spring:message code="sl.access.address" />...' class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="toperateDateStr" id="toperateDateStr" placeholder='<spring:message code="sl.access.time" />' class="layui-input">
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
            elem: '#toperateDateStr', //指定元素
            range: true,
            type: 'datetime'
        });
    });

    // 渲染数据
    var colModel = [{name: 'id', index: 'id',hidden:true},
        {name: 'susername',index: 'susername',label : '<spring:message code="sl.username" />',sortable : false,width:240},
        {name: 'srealName',index: 'srealName',label : '<spring:message code="sl.username" />',sortable : false,width:230},
        {name: 'sip',index: 'sip',label : '<spring:message code="sl.access.ip" />',sortable : false,width:300},
        {name: 'sactionpath',index: 'sactionpath',label : '<spring:message code="sl.access.address" />',sortable : false,width:460},
        {name: 'toperateDate',index: 'toperate_date',label : '<spring:message code="sl.operating.time" />',formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:380}
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
            sortname:'toperate_date'
        };
        initTable(ctx+"/vistLog/queryData",colModel,tableCallBack,config);
    });
    // 调用Button按钮方法(查询,清除)
    initBtnByForm2();
</script>
</body>
</html>

