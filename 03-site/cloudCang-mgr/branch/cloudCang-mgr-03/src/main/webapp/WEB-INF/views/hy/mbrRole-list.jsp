<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title>会员角色管理</title>
    <link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
    <link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code='menu.member.role.management' /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <input type="text" name="sroleName" id="sroleName" value="" placeholder='<spring:message code="hy.character.name" />...' class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="daddDateStr" id="daddDateStr" placeholder='<spring:message code="main.add.time" />' class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn layui-btn" id="searchBtn" data-type="query"><i class="layui-icon">&#xe615;</i><spring:message code="main.query" /></button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i class="layui-icon">&#x1006;</i><spring:message code="main.clear" /></button>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <shiro:hasPermission name="ADD_MBRROLE">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" style="margin-left: 0px;" data-type="add"><i class="layui-icon"></i><spring:message code="main.add" /></button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="DELETE_MBRROLE">
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
    /**
     * 选择日期范围
     */
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //执行一个laydate实例
        laydate.render({
            elem: '#daddDateStr', //指定元素
            range:true,
            type: 'date'
        });
    });
    // 渲染数据
    var colModel = [{name: 'id', index: 'id',hidden:true},
        {name: 'sroleName',index: 'SROLE_NAME',label : '<spring:message code="hy.character.name" />',sortable : false,width:100},
        {name: 'isortNo',index: 'ISORT_NO',label : '<spring:message code="main.sort" />',sortable : true,width:80},
        {name: 'daddDate',index: 'DADD_DATE',label : '<spring:message code="sp.brand.add.date" />',formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:160},
        {name: 'saddOperator',index: 'SADD_OPERATOR',label : "<spring:message code='hy.add.a.person' />",sortable : false,width:100},
        {name: 'dmodifyDate',index: 'DMODIFY_DATE',label : "<spring:message code='sb.modified.date' />",formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:160},
        {name: 'smodifyOperator',index: 'SMODIFY_OPERATOR',label : "<spring:message code='hy.modifier' />",sortable : false,width:100},
        {name: 'sremark',index: 'SREMARK',label : '<spring:message code="main.remarks" />',sortable : false,width:150},
        {name : 'process',title:false,index : 'process',label : '<spring:message code="main.operation" />',sortable : false,width:150}
    ];
    // 表格回调
    function tableCallBack() {
        setTimeout(function () {
            var data = $("#gridTable").jqGrid('getRowData');

            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = "";
                <shiro:hasPermission name="EDIT_MBRROLE">
                str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                    + cl + "')\" title='<spring:message code='hy.edit.member.role' />' alt='<spring:message code='hy.edit.member.role' />'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="DELETE_MBRROLE">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='<spring:message code='hy.delete.member.role' />' alt='<spring:message code='hy.delete.member.role' />'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="ASSGIN_MBRROLE">
                str += "<img src='${staticSource}/resources/images/oper_icon/assign_permissions.png' class=\"oper_icon\" onclick=\"roleAssignMethod('"
                    + cl + "')\" title='<spring:message code='hy.assign.permissions' />' alt='<spring:message code='hy.assign.permissions' />'  /> | ";
                </shiro:hasPermission>

                $("#gridTable").jqGrid('setRowData',
                    cl, {
                        process: str.substr(0, str.lastIndexOf(" | "))
                    });
            }
        }, 0);
    }
    // 初始化表格,传入页面所需参数
    $(function () {
        var config = {
            sortname: "DADD_DATE",
            sortorder: "desc"
        };
        initTable(ctx + "/mbrRole/queryData", colModel, tableCallBack, config);
    });
    //初始化form中按钮
    var initBtnConfig = {
        addUrl: ctx + '/mbrRole/edit',
        deleteUrl: ctx + "/mbrRole/delete",
        addTitle: '<spring:message code='hy.add.member.role.information' />',
        addFn:showLayerMin
    };
    initBtnByForm4(initBtnConfig);

    function editMethod(sid) {
        showLayerMin("<spring:message code='hy.edit.member.role.information' />", 'edit?sid='+sid);
    }
    function delMethod(sid) {
        confirmDelTip('<spring:message code="main.delete.confirm" />',ctx+"/mbrRole/delete",{checkboxId:sid});
    }
    function roleAssignMethod(sid) {
        showLayer("<spring:message code='hy.assign.permissions' />", ctx+'/mbrRole/assign?sid='+sid,{area: ['50%', '50%']});
    }


</script>
</body>
</html>

