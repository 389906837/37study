<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title>系统日志</title>
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
                    <h5><spring:message code="sm.inventory.operation.log" /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <input type="text" name="smerchantCode" id="smerchantCode" value="" placeholder='<spring:message code="main.merchant.number" />...' class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sdeviceCode" id="sdeviceCode" value="" placeholder='<spring:message code="main.device.id" />...' class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="scommodityCode" id="scommodityCode" value="" placeholder='<spring:message code="main.product.number" />...' class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <select class="form-control" name="istatus" id="istatus">
                                    <option value=""><spring:message code="sm.operational.status" /></option>
                                    <option value="10"><spring:message code="sm.upper.shelf" /></option>
                                    <option value="20"><spring:message code="sm.lower.shelf" /></option>
                                    <option value="30"><spring:message code="sm.sell.out" /></option>
                                    <option value="40"><spring:message code="sm.abnormal.mounting" /></option>
                                    <option value="50"><spring:message code="sm.abnormal.undercarriage" /></option>
                                    <option value="60"><spring:message code="sm.abnormal.sale" /></option>
                                    <option value="70"><spring:message code="sm.manually.off.the.shelf" /></option>
                                </select>
                            </div>
                            <div class="layui-inline">
                                    <input type="text" name="toperateStartDateStr" id="toperateStartDateStr" placeholder='<spring:message code="sm.operating.time" />'  autocomplete="off" class="layui-input">
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
<script type="text/javascript" src="${staticSource}/resources/page/sm/stockoperlog/stockoperLog-list.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    /**
     * <spring:message code="sm.select.date.range" />
     */
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //执行一个laydate实例(开门时间)
        laydate.render({
            elem: '#toperateStartDateStr', //指定元素
            range:true,
            type:'date'
        });
    });

    //渲染数据
    var colModel = [{name: 'id', index: 'id',hidden:true},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode',index: 'smerchantCode',label : '<spring:message code="main.merchant.number" />',sortable : false,width:150},
        {name: 'shName',index: 'shName',label : '<spring:message code="main.merchant.name" />',sortable : false,width:150},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'smerchantCode',index: 'smerchant_code',hidden:true},
        </c:if>
        {name: 'scommodityCode',index: 'scommodityCode',label : '<spring:message code="main.product.number" />',sortable : false,width:150},
        {name: 'spName',index: 'spName',label : '<spring:message code="main.product.name" />',sortable : false,width:200},
        {name: 'sidentifies',index: 'sidentifies',label : '<spring:message code="sm.unique.commodity.identification" />',sortable : false,width:200},
        {name: 'sdeviceCode',index: 'sdeviceCode',label : '<spring:message code="main.device.id" />',sortable : false,width:150},
        {name: 'sbName',index: 'sbName',label : '<spring:message code="main.device.name" />',sortable : false,width:150},
        {name: 'adress',index: 'adress',label : '<spring:message code="main.device.address" />',sortable : false,width:150},
        {name: 'istatus',index: 'istatus',label : '<spring:message code="sm.operational.status" />', editable: true,
            formatter:"select",editoptions:{value:'10:<spring:message code="sm.upper.shelf" />;20:<spring:message code="sm.lower.shelf" />;30:<spring:message code="sm.sell.out" />;40:<spring:message code="sm.abnormal.mounting" />;50:<spring:message code="sm.abnormal.undercarriage" />;60:<spring:message code="sm.abnormal.sale" />;70:<spring:message code="sm.manually.off.the.shelf" />'},sortable : false,width:140},
        {name: 'taddTime',index: 'tadd_Time',label : '<spring:message code="sm.operating.time" />',formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:150},
        {name: 'sremark',index: 'sremark',label : '<spring:message code="main.remarks" />',sortable : false,width:150},
    ];
    // 表格回调
    function tableCallBack() {
        $("#gridTable").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "scroll" });
    }
    // 初始化表格,传入页面所需参数
    $(function () {
        var config = {
            rownumbers: true,
            multiselect:false,
            sortname: 'A.tadd_time',
            sortorder: 'desc'
        };
        initTable(ctx+"/stockoperLog/queryData",colModel,tableCallBack,config);
    });
    // 调用Button按钮方法(查询,清除)
    initBtnByForm2();
</script>
</body>
</html>

