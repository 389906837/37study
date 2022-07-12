<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title>菜单管理</title>
    <link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet" />
    <link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css" rel="stylesheet"/>
<style>
    .ui-jqgrid tr.jqgrow td {
        white-space: normal !important;
        height: auto;
    }
</style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code='menu.privilege.code.management' /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <input type="hidden" id="menuId" name="menuId" value="${menuId}" />
                        <div class="layui-form-item">
                            <shiro:hasPermission name="ADD_PURV">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" style="margin-left: 0px;" data-type="add"><i class="layui-icon"></i><spring:message code="main.add" /></button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="DELETE_PURV">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i><spring:message code="main.delete" /></button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="SYNC_PURV">
                                <button class="layui-btn layui-btn-sm" data-type="sync"><i class="layui-icon">&#xe631;</i><spring:message code='sh.privilege.synchronization' /></button>
                            </shiro:hasPermission>
                            <button class="layui-btn layui-btn-sm layui-btn-primary" data-type="refresh"><i class="layui-icon layui-icon-refresh"></i><spring:message code='sh.refresh' /></button>
                            <shiro:hasPermission name="ZPK_PURV">
                                <button class="layui-btn layui-btn-sm layui-btn-primary" data-type="refreshCache"><i class="layui-icon layui-icon-refresh"></i><spring:message code="main.refresh.cache" />
                                </button>
                            </shiro:hasPermission>
                        </div>
                    </div>
                    <div class="jqGrid_wrapper">
                        <table id="gridTable"></table>
                        <div id="gridPager"></div>
                    </div>
                    <%--<table id="layuiTable" lay-filter="layuiTable"></table>
                    <script type="text/html" id="barLayui">
                        <a class="layui-btn layui-btn-xs" lay-event="edit"><spring:message code='main.edit' /></a>
                        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><spring:message code="main.delete" /></a>
                    </script>--%>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    /**
     * 重新加载表格
     */
    function reloadData(menuId) {
        if (!isEmpty(menuId)) {
            $("#menuId").val(menuId);
        }
        resetReloadGrid();
    }
    var colModel= [{name: 'id', index: 'id',hidden:true},
        {name: 'spurNo',index: 'spur_no',label : "<spring:message code='hy.authority.code.number' />",width:200},
        {name: 'spurCode',index: 'spur_code',label : "<spring:message code='hy.authority.code' />",width:320},
        {name: 'spurName',index: 'spur_name',label : "<spring:message code='sh.privilege.code.name' />",width:200},
        {name: 'surlPath',index: 'surl_path',label : "<spring:message code='sh.path' />",width:300},
        {name: 'iisMerchantUsed',index: 'iis_merchant_used',label : "<spring:message code='sh.is.the.merchant.available' />",width:160,formatter:"select",editoptions:{value:'0:<spring:message code="main.no" />;1:<spring:message code="main.yes" />'}},
        {name: 'sdescription',index: 'sdescription',label : '<spring:message code="main.remarks" />',sortable: false,width:240},
        {name : 'process',index : 'process',title:false,label : '<spring:message code="main.operation" />',sortable : false,width:300}
    ];
    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function(){
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "scroll" });
            var ids = $("#gridTable").jqGrid('getDataIDs');
            for ( var i = 0; i < ids.length; i++) {
                var cl = ids[i];
                var str = "";
                <shiro:hasPermission name="EDIT_PURV">
                str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                    + cl + "')\" title=\"<spring:message code='main.edit' />\" alt=\"<spring:message code='main.edit' />\" /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="DELETE_PURV">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='<spring:message code="main.delete" />' alt='<spring:message code="main.delete" />'  /> | ";
                </shiro:hasPermission>
                $("#gridTable").jqGrid('setRowData',
                    ids[i], {
                        process : str.length > 0 ? str.substr(0,str.lastIndexOf(" | ")) : ""
                    });
            }
        }, 0);
    }
    $(function () {
        var config = {
            sortname: "id",
            sortorder: "desc",
        };
        initTable(ctx + "/purview/getPurviewByMenuId", colModel, tableCallBack, config);
    });

    layui.use('form', function(){
        var $ = layui.$, active = {
            add: function(){//添加
                var menuId = $("#menuId").val();
                if (isEmpty(menuId)) {
                    alertDel("<spring:message code='sh.please.select.a.node.first' />!");
                    return;
                }
                showLayerMin("<spring:message code='sh.add.permission.code.information' />", ctx+'/purview/edit?sparentId='+menuId);
            },refreshCache: function(){//刷新缓存
                var loadIndex = loading();
                $.ajax({
                    type : "POST",
                    dataType : "json",
                    url : ctx+'/zooSyn/cacheMgrPurview?t='+new Date(),
                    success : function(msg) {
                        closeLayer(loadIndex);//关闭加载框
                        if (msg.success) {
                            alertInfo(msg.data)
                        } else {
                            alertDel(msg.data);
                        }
                    }
                });
            },refresh: function(){//刷新
                reloadGrid(true);
            },delete: function(){//删除
                var sid = $("#gridTable").jqGrid('getGridParam','selarrrow');
                if(isEmpty(sid)) {
                    alertDel('<spring:message code="main.delete.data.empty" />');
                    return;
                }
                confirmDelTip('<spring:message code="main.delete.confirm" />',ctx+"/purview/delete",{checkboxId:sid.join(",")});
            },sync: function(){//权限同步
                var sid = $("#gridTable").jqGrid('getGridParam','selarrrow');
                if(isEmpty(sid)) {
                    alertDel("<spring:message code='sh.please.select.the.data.you.want.to.operate.first' />");
                    return;
                }
                confirmOperTip("<spring:message code='sh.are.you.sure.you.want.to.sync.this.permission.to.your.business' />?",ctx+"/purview/sync",{checkboxId:sid.join(",")});
            }
        };
        $('.layui-form .layui-btn').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });
    function editMethod(sid) {
        showLayerMin("<spring:message code='sh.edit.permission.code.information' />", ctx+'/purview/edit?sid='+sid);
    }
    function delMethod(sid) {
        confirmDelTip('<spring:message code="main.delete.confirm" />',ctx+"/purview/delete",{checkboxId:sid});
    }
</script>
</body>
</html>

