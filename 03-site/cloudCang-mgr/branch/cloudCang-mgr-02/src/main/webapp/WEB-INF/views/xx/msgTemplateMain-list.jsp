<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title>消息模板分类管理</title>
    <link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
    <link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>消息模板分类管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-inline">
                                    <input type="text" name="smerchantCode" id="smerchantCode" value="" placeholder="商户编号..." class="layui-input">
                                </div>
                                <div class="layui-inline">
                                    <input type="text" name="merchantName" id="merchantName" value="" placeholder="商户名称..." class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder="编号..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="smsgName" id="smsgName" value="" placeholder="名称..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sfunctionCode" id="sfunctionCode" value="" placeholder="功能编号..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i class="layui-icon">&#xe615;</i>查询</button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i class="layui-icon">&#x1006;</i>清除</button>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <shiro:hasPermission name="ADD_MSGTEMPMAIN">
                            <button class="layui-btn layui-btn-sm layui-btn-warm" style="margin-left: 0px;" data-type="add"><i class="layui-icon"></i>添加</button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="DELETE_MSGTEMPMAIN">
                            <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i>删除</button>
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
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    var colModel= [{name: 'id', index: 'id',hidden:true},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'SMERCHANT_CODE',label : "商户编号",index: 'SMERCHANT_CODE',width:120},
        {name: 'MERCHANTNAME',label : "商户名称",index: 'MERCHANTNAME',width:260,sortable : false},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'SMERCHANT_CODE', index: 'SMERCHANT_CODE', hidden: true},
        </c:if>
        {name: 'SCODE',index: 'SCODE',label : "编号",width:130},
        {name: 'SMSG_NAME',index: 'SMSG_NAME',label : "名称",sortable : false,width:300},
        {name: 'SFUNCTION_CODE',index: 'SFUNCTION_CODE',label : "功能编号",sortable : false,width:300},
        {name: 'SREMARK',index: 'SREMARK',label : "描述",sortable : false,width:300},
        {name : 'process',title:false,index : 'process',label : "操作",sortable : false,width:120}
    ];
    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            var data = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = '';
                <shiro:hasPermission name="EDIT_MSGTEMPMAIN">
                str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                    + cl + "')\" title='编辑' alt='编辑'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="DELETE_MSGTEMPMAIN">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='删除' alt='删除'  /> | ";
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
            sortname: "SCODE",
            sortorder: "desc",
        };
        initTable(ctx + "/msgTemplateMain/queryData", colModel, tableCallBack, config);
    });
    //初始化form中按钮
    var initBtnConfig = {
            addUrl: ctx + '/msgTemplateMain/edit',
            deleteUrl: ctx + "/msgTemplateMain/delete",
            addTitle: '添加消息模板分类信息',
            addFn: showLayerMin
        }
    ;
    initBtnByForm4(initBtnConfig);
    function editMethod(sid) {
        showLayerMin("编辑消息模板分类信息", 'edit?sid='+sid);
    }
    function delMethod(sid) {
        confirmDelTip("确定要删除数据?",ctx+"/msgTemplateMain/delete",{checkboxId:sid});
    }


</script>
</body>
</html>

