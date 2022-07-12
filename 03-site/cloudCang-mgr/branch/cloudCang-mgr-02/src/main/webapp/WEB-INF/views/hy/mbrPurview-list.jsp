<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title>会员权限码管理</title>
    <link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
    <link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>会员权限码管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="spurNo" id="spurNo" value="" placeholder="权限码编号..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="spurCode" id="spurCode" value="" placeholder="权限码..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="spurName" id="spurName" value="" placeholder="权限名称..." class="layui-input">
                            </div>
                            <div class="layui-input-inline" style="width: 182px">
                                <cang:select type="list" id="ssysCode" name="ssysCode" groupNo="SP000003"  list="{10:SYS-WEB,20:SYS-WAP}" entire="true" entireName="请选择系统代码" exp="datatype=\"*\" nullmsg=\"请选择系统代码\"" />
                            </div>
                            <div class="layui-input-inline" style="width: 182px">
                                <select class="form-control" name="ssysType" id="ssysType">
                                    <option value="">请选择系统分类</option>
                                    <option value="10">PC</option>
                                    <option value="20">WAP</option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <button class="layui-btn layui-btn layui-btn" id="searchBtn" data-type="query"><i class="layui-icon">&#xe615;</i>查询</button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i class="layui-icon">&#x1006;</i>清除</button>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <shiro:hasPermission name="ADD_MBRPURVIEW">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" style="margin-left: 0px;" data-type="add"><i class="layui-icon"></i>添加</button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="DELETE_MBRPURVIEW">
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
    // 渲染数据
    var colModel = [{name: 'id', index: 'id',hidden:true},
        {name: 'spurNo',index: 'SPUR_NO',label : "权限码编号",sortable : false,width:150},
        {name: 'spurCode',index: 'SPUR_CODE',label : "权限码",sortable : false,width:150},
        {name: 'spurName',index: 'SPUR_NAME',label : "权限名称",sortable : false,width:150},
        {name: 'ssysCode',index: 'SSYS_TYPE',label : "系统代码", editable: true,
            formatter:"select",editoptions:{value:'10:SYS-WEB;20:SYS-WAP'},sortable : false,width:90},
        {name: 'ssysType',index: 'SSYS_TYPE',label : "系统分类", editable: true,
            formatter:"select",editoptions:{value:'10:PC;20:WAP'},sortable : false,width:90},
        {name: 'surlPath',index: 'SURL_PATH',label : "访问路径",sortable : false,width:100},
        {name: 'daddDate',index: 'DADD_DATE',label : "添加日期",formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:160},
        {name: 'sdescription',index: 'sdescription',label : "权限说明",sortable : false,width:150},
        {name : 'process',title:false,index : 'process',label : "操作",sortable : false,width:100}
    ];
    // 表格回调
    function tableCallBack() {
        setTimeout(function () {
            var data = $("#gridTable").jqGrid('getRowData');

            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = "";
                <shiro:hasPermission name="EDIT_MBRPURVIEW">
                str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                    + cl + "')\" title='编辑会员权限码' alt='编辑会员权限码'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="DELETE_MBRPURVIEW">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='删除会员权限码' alt='删除会员权限码'  /> | ";
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
        initTable(ctx + "/mbrPurview/queryData", colModel, tableCallBack, config);
    });
    //初始化form中按钮
    var initBtnConfig = {
        addUrl: ctx + '/mbrPurview/edit',
        deleteUrl: ctx + "/mbrPurview/delete",
        addTitle: '添加会员权限码信息',
        addFn:showLayerMedium
    };
    initBtnByForm4(initBtnConfig);

    function editMethod(sid) {
        showLayerMedium("编辑会员权限码信息", 'edit?sid='+sid);
    }
    function delMethod(sid) {
        confirmDelTip("确定要删除数据?",ctx+"/mbrPurview/delete",{checkboxId:sid});
    }


</script>
</body>
</html>

