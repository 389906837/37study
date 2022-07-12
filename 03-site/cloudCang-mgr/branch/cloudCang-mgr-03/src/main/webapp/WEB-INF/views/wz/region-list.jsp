<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title>运营区域管理</title>
    <link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
    <link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code='wz.operation.area.management' /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder='<spring:message code="wz.operation.area.number" />...' class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sregionName" id="sregionName" value="" placeholder='<spring:message code="wz.operation.area.name" />...' class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" name="itype" id="itype" entire="true" value=""
                                             list="{10:springMessageCode=wz.ad,20:springMessageCode=menu.system.notification}" entireName='springMessageCode=wz.please.select.the.type.of.operation.area' />
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i class="layui-icon">&#xe615;</i><spring:message code="main.query" /></button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i class="layui-icon">&#x1006;</i><spring:message code="main.clear" /></button>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <shiro:hasPermission name="REGION_ADD">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" style="margin-left: 0px;" data-type="add"><i class="layui-icon"></i><spring:message code="main.add" /></button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="REGION_DELETE">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i><spring:message code="main.delete" /></button>
                            </shiro:hasPermission>
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
    //渲染数据
    var colModel = [{name: 'id', index: 'id',hidden:true},
        {name: 'scode',index: 'scode',label : "<spring:message code='wz.operation.area.number' />",width:190},
        {name: 'sregionName',index: 'sregionName',label : "<spring:message code='wz.operation.area.name' />",width:320,sortable : false},
        {name: 'itype',index: 'itype',label : "<spring:message code='wz.qperation.area.type' />", editable: true,
            formatter:"select",editoptions:{value:'10:<spring:message code='wz.ad' />;20:<spring:message code='menu.system.notification' />'},sortable : false,width:180},
        {name: 'icount',index: 'icount',label : "<spring:message code='wz.quantity' />",width:100,sortable : false},
        {name: 'sremark',index: 'sremark',label : "<spring:message code='sh.explain' />",width:280,sortable : false},
        {name: 'taddTime',index: 'TADD_TIME',label : '<spring:message code="sp.brand.add.date" />',formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:200},
        {name: 'saddUser',index: 'saddUser',label : "<spring:message code='wz.add.a.person' />",width:140,sortable : false},
        {name: 'tupdateTime',index: 'tupdateTime',label : "<spring:message code='sb.modified.date' />",formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:200,sortable : false},
        {name: 'supateUser',index: 'supateUser',label : "<spring:message code='wz.modifier' />",width:140,sortable : false},
        {name : 'process',title:false,index : 'process',label : '<spring:message code="main.operation" />',sortable : false,width:380}
    ];
    // 表格回调
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
            var data = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = "";
                <shiro:hasPermission name="REGION_EDIT">
                str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                    + cl + "')\" title='<spring:message code='wz.edit.operation.area' />' alt='<spring:message code='wz.edit.operation.area' />'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="REGION_DELETE">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='<spring:message code="wz.delete.operating.area" />' alt='<spring:message code="wz.delete.operating.area" />'  /> | ";
                </shiro:hasPermission>

                $("#gridTable").jqGrid('setRowData',
                    cl, {
                        process: str.substr(0, str.lastIndexOf(" | "))
                    });
            }
        }, 0);
    }
    $(function () {
        var config = {
            sortname: "TADD_TIME",
            sortorder: "desc"
        };
        initTable(ctx + "/region/queryData", colModel, tableCallBack, config);
    });
    //<spring:message code="wz.initialize.the.form.button" />
    var initBtnConfig = {
        addUrl: ctx + '/region/edit',
        deleteUrl: ctx + "/region/delete",
        addTitle: '<spring:message code="wz.add.operational.area.information" />',
        addFn:showLayerMedium
    };
    initBtnByForm4(initBtnConfig);

    function editMethod(rid) {
        showLayerMedium('<spring:message code="wz.edit.operating.area.information" />', 'edit?rid='+rid);
    }
    function delMethod(rid) {
        confirmDelTip('<spring:message code="main.delete.confirm" />',ctx+"/region/delete",{checkboxId:rid});
    }


</script>
</body>
</html>

