<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title>登录日志</title>
    <link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
    <link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code="sl.login.log" /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="suserCode" id="suserCode" value="" placeholder='<spring:message code="main.member.number" />...' class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="smemberName" id="smemberName" value="" placeholder='<spring:message code="main.device.id" />...'     class="layui-input">
                            </div>
                            <div class="layui-input-inline" >
                                <select class="form-control" name="itype" id="itype">
                                    <option value=""><spring:message code="sl.login.type" /></option>
                                    <option value="10"><spring:message code="sl.authorized.login" /></option>
                                    <option value="20"><spring:message code="sl.account.login" /></option>
                                    <option value="30">SSO<spring:message code="sl.log.in" /></option>
                                </select>
                            </div>
                            <div class="layui-input-inline" >
                                <select class="form-control" name="ideviceType" id="ideviceType">
                                    <option value=""><spring:message code="sl.login.device.type" /></option>
                                    <option value="10"><spring:message code="sl.wechat" /></option>
                                    <option value="20"><spring:message code="sl.alipay" /></option>
                                    <option value="30">APP</option>
                                    <option value="40"><spring:message code="sl.wap.station" /></option>
                                    <option value="30"><spring:message code="sl.pc.station" /></option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sip" id="sip" value="" placeholder='<spring:message code="sl.login.ip.address" />...' class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="tloginTimeStr" id="tloginTimeStr" placeholder='<spring:message code="sl.log.in.time" />' class="layui-input">
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
            elem: '#tloginTimeStr', //指定元素
            range:true,
            type: 'date'
        });
    });

    // 渲染数据
    var colModel = [{name: 'id', index: 'id',hidden:true},
        {name: 'suserCode',index: 'SUSER_CODE',label : '<spring:message code="main.member.number" />',sortable : false,width:240},
            {name: 'smemberNameDesensitize',index: 'SMEMBER_NAME',label : '<spring:message code="main.device.id" />',sortable : false,width:260},
        {name: 'sip',index: 'SIP',label : '<spring:message code="sl.login.ip.address" />',sortable : false,width:260},
        {name: 'itype',index: 'ITYPE',label : '<spring:message code="sl.login.type" />', editable: true,
            formatter:"select",editoptions:{value:'10:<spring:message code="sl.authorized.login" />;20:<spring:message code="sl.account.login" />;30:SSO<spring:message code="sl.log.in" />'},sortable : false,width:260},
        {name: 'ideviceType',index: 'IDEVICE_TYPE',label : '<spring:message code="sl.login.device.type" />', editable: true,
            formatter:"select",editoptions:{value:'10:<spring:message code="sl.wechat" />;20:<spring:message code="sl.alipay" />;30:APP;40:<spring:message code="sl.wap.station" />;50:<spring:message code="sl.pc.station" />'},sortable : false,width:260},
        {name: 'tloginTime',index: 'TLOGIN_TIME',label : '<spring:message code="sl.log.in.time" />',formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:320},
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
            sortname:'tlogin_time',
            sortorder:'desc'
        };
        initTable(ctx+"/loginLog/queryData",colModel,tableCallBack,config);
    });
    // 调用Button按钮方法(查询,清除)
    initBtnByForm2();
</script>
</body>
</html>

