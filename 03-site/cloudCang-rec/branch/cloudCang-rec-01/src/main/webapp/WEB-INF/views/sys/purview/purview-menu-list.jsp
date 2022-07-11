<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title>菜单管理</title>
    <link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet" />
    <link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css" rel="stylesheet"/>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>权限码管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <input type="hidden" id="menuId" name="menuId" value="${menuId}" />
                        <div class="layui-form-item">
                            <shiro:hasPermission name="ADD_PURV">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" style="margin-left: 0px;" data-type="add"><i class="layui-icon"></i>添加</button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="DELETE_PURV">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i>删除</button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="SYNC_PURV">
                                <button class="layui-btn layui-btn-sm" data-type="sync"><i class="layui-icon">&#xe631;</i>权限同步</button>
                            </shiro:hasPermission>
                            <button class="layui-btn layui-btn-sm layui-btn-primary" data-type="refresh"><i class="layui-icon">&#x1006;</i>刷新</button>
                            <shiro:hasPermission name="ZPK_PURV">
                                <button class="layui-btn layui-btn-sm layui-btn-primary" data-type="refreshCache"><i class="layui-icon layui-icon-refresh"></i>更新缓存
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
                        <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
                        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
                    </script>--%>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js"></script>
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
        {name: 'spurNo',index: 'spur_no',label : "权限码编号",width:150},
        {name: 'spurCode',index: 'spur_code',label : "权限码",width:150},
        {name: 'spurName',index: 'spur_name',label : "权限码名称",width:200},
        {name: 'surlPath',index: 'surl_path',label : "路径",width:300},
        {name: 'iisMerchantUsed',index: 'iis_merchant_used',label : "商户是否可用",width:150,formatter:"select",editoptions:{value:'0:否;1:是'}},
        {name: 'sdescription',index: 'sdescription',label : "备注",sortable: false,width:300},
        {name : 'process',index : 'process',title:false,label : "操作",sortable : false,width:150}
    ];
    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function(){
            var ids = $("#gridTable").jqGrid('getDataIDs');
            for ( var i = 0; i < ids.length; i++) {
                var cl = ids[i];
                var str = "";
                <shiro:hasPermission name="EDIT_PURV">
              /*  str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"editMethod('"
                    + cl + "')\">编辑</a> | ";*/
                str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                    + cl + "')\" title='编辑' alt='编辑'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="DELETE_PURV">
             /*   str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"delMethod('"
                    + cl + "')\">删除</a> | ";*/
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='删除' alt='删除'  /> | ";
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
                    alertDel("请先选择节点!");
                    return;
                }
                showLayerMin("添加权限码信息", ctx+'/purview/edit?sparentId='+menuId);
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
                    alertDel("请先选择要删除的数据");
                    return;
                }
                confirmDelTip("确定要删除数据?",ctx+"/purview/delete",{checkboxId:sid.join(",")});
            },sync: function(){//权限同步
                var sid = $("#gridTable").jqGrid('getGridParam','selarrrow');
                if(isEmpty(sid)) {
                    alertDel("请先选择要操作的数据");
                    return;
                }
                confirmOperTip("确定要此权限同步到商户?",ctx+"/purview/sync",{checkboxId:sid.join(",")});
            }
        };
        $('.layui-form .layui-btn').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });
    function editMethod(sid) {
        showLayerMin("编辑权限码信息", ctx+'/purview/edit?sid='+sid);
    }
    function delMethod(sid) {
        confirmDelTip("确定要删除数据?",ctx+"/purview/delete",{checkboxId:sid});
    }
</script>
</body>
</html>

