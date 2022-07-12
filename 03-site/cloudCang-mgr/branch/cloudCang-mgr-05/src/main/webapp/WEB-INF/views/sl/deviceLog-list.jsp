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
                    <h5><spring:message code="sl.device.operation.log" /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <input type="text" name="smemberCode" id="smemberCode" value="" placeholder='<spring:message code="main.member.number" />...' class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="smemberName" id="smemberName" value="" placeholder='<spring:message code="main.device.id" />...' class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sdeviceCode" id="sdeviceCode" value="" placeholder='<spring:message code="main.device.id" />...' class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sbName" id="sbName" value="" placeholder='<spring:message code="main.device.name" />...' class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="adress" id="adress" value="" placeholder='<spring:message code="main.device.address" />...' class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sip" id="sip" value="" placeholder='<spring:message code="sl.access.ip" />...' class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <select class="form-control" name="itype" id="itype">
                                    <option value=""><spring:message code="sl.operation.type" /></option>
                                    <option value="10"><spring:message code="sl.shopping" /></option>
                                    <option value="20"><spring:message code="sl.replenishment" /></option>
                                    <option value="30"><spring:message code="sl.shopping.visitors" /></option>
                                    <option value="40"><spring:message code="sl.replenishment.visitor" /></option>
                                </select>
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <select class="form-control" name="iclientType" id="iclientType">
                                    <option value=""><spring:message code="sl.client.source" /></option>
                                    <option value="10"><spring:message code="sl.wechat" /></option>
                                    <option value="20"><spring:message code="sl.alipay" /></option>
                                </select>
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="topenTimeStr" id="topenTimeStr" placeholder='<spring:message code="sl.opening.hours" />' class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="tcloseTimeStr" id="tcloseTimeStr" placeholder='<spring:message code="sl.closing.time" />' class="layui-input">
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
<script type="text/javascript" src="${staticSource}/resources/page/sl/vistlog/vistLog-list.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    /**
     * 选择日期范围
     */
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //执行一个laydate实例(开门时间)
        laydate.render({
            elem: '#topenTimeStr', //指定元素
            range:true,
            type:'date'
        });
        //关门时间
        laydate.render({
            elem: '#tcloseTimeStr', //指定元素
            range:true,
            type:'date'
        });
    });

    // 渲染数据
    var colModel = [{name: 'id', index: 'id',hidden:true},
        {name: 'smemberCode',index: 'smemberCode',label : '<spring:message code="main.member.number" />',sortable : false,width:150},
        {name: 'smemberNameDesensitize',index: 'smemberName',label : '<spring:message code="main.device.id" />',sortable : false,width:140},
        {name: 'sdeviceCode',index: 'sdeviceCode',label : '<spring:message code="main.device.id" />',sortable : false,width:140},
        {name: 'sbName',index: 'sbName',label : '<spring:message code="main.device.name" />',sortable : false,width:150},
        {name: 'adress',index: 'adress',label : '<spring:message code="main.device.address" />',sortable : false,width:200},
        {name: 'sip',index: 'sip',label : '<spring:message code="sl.access.ip" />',sortable : false,width:130},
        {name: 'itype',index: 'itype',label : '<spring:message code="sl.operation.type" />', editable: true,
            formatter:"select",editoptions:{value:'10:<spring:message code="sl.shopping" />;20:<spring:message code="sl.replenishment" />;30:<spring:message code="sl.shopping.visitors" />;40:<spring:message code="sl.replenishment.visitor" />'},sortable : false,width:180},
        {name: 'iclientType',index: 'iclientType',label : '<spring:message code="sl.client.source" />', editable: true,
            formatter:"select",editoptions:{value:'10:<spring:message code="sl.wechat" />;20:<spring:message code="sl.alipay" />'},sortable : false,width:160},
        {name: 'topenTime',index: 'TOPEN_TIME',label : '<spring:message code="sl.opening.hours" />',formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:160},
        {name: 'tcloseTime',index: 'TCLOSE_TIME',label : '<spring:message code="sl.closing.time" />',formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:160},
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
            sortname:'TOPEN_TIME',
            sortorder:'desc'
        };
        initTable(ctx+"/deviceLog/queryData",colModel,tableCallBack,config);
    });
    // 调用Button按钮方法(查询,清除)
    initBtnByForm2();
</script>
</body>
</html>

