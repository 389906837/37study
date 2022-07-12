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
                    <h5><spring:message code="sl.reconciliation.log" /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="stitle" id="stitle" value="" placeholder='<spring:message code="sl.title" />...' class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control" name="itype" id="itype">
                                    <option value=""><spring:message code="sl.please.select.the.payment.method" /></option>
                                    <option value="10"><spring:message code="sl.wechat" /></option>
                                    <option value="20"><spring:message code="sl.alipay" /></option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control" name="sresult" id="sresult">
                                    <option value=""><spring:message code="sl.please.select.the.reconciliation.result" /></option>
                                    <option value="1"><spring:message code="main.success.name" /></option>
                                    <option value="2"><spring:message code="tec.fail" /></option>
                                    <option value="3"><spring:message code="main.exception" /></option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="tbeginDatetimeStr" id="tbeginDatetimeStr" placeholder='<spring:message code="sl.reconciliation.start.time" />' class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="tendDatetimeStr" id="tendDatetimeStr" placeholder='<spring:message code="sl.reconciliation.end.time" />' class="layui-input">
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
<script type="text/javascript" src="${staticSource}/resources/page/sl/vistlog/checkLog-list.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    /**
     * 选择日期范围
     */
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //执行一个laydate实例(开始时间)
        laydate.render({
            elem: '#tbeginDatetimeStr', //指定元素
            range:true,
            type: 'date'
        });
        //结束时间
        laydate.render({
            elem: '#tendDatetimeStr', //指定元素
            range:true,
            type: 'date'
        });
    });

    // 渲染数据
    var colModel = [{name: 'id', index: 'id',hidden:true},
        {name: 'stitle',index: 'stitle',label : '<spring:message code="sl.title" />',sortable : false,width:175},
        {name: 'itype',index: 'itype',label : '<spring:message code="sl.payment.method" />', editable: true,
            formatter:"select",editoptions:{value:'10:<spring:message code="sl.wechat" />;20:<spring:message code="sl.alipay" />'},sortable : false,width:125},
        {name: 'sresult',index: 'sresult',label : '<spring:message code="sl.reconciliation.result" />', editable: true,
            formatter:"select",editoptions:{value:'1:<spring:message code="main.success.name" />;2:<spring:message code="tec.fail" />;3:<spring:message code="main.exception" />'},sortable : false,width:125},
        {name: 'dcheckDate',index: 'dcheck_date',label : '<spring:message code="sl.reconciliation.date" />',formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:240},
        {name: 'tbeginDatetime',index: 'tbegin_datetime',label : '<spring:message code="sl.reconciliation.start.time" />',formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:240},
        {name: 'tendDatetime',index: 'tend_datetime',label : '<spring:message code="sl.reconciliation.end.time" />',formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:240},
        {name: 'sremark',index: 'sremark',label : '<spring:message code="sl.reconciliation.description" />',sortable : false,width:240},
    ];
    // 表格回调
    function tableCallBack() {
    }
    // 初始化表格,传入页面所需参数
    $(function () {
        var config = {
            rownumbers: true,
            multiselect:false,
            sortname : "dcheck_date"
        };
        initTable(ctx+"/checkLog/queryData",colModel,tableCallBack,config);
    });
    // 调用Button按钮方法(查询,清除)
    initBtnByForm2();
</script>
</body>
</html>

