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
                    <h5>运营区域管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder="运营区域编号..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sregionName" id="sregionName" value="" placeholder="运营区域名称..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" name="itype" id="itype" entire="true" value=""
                                             list="{10:广告,20:系统公告}" entireName="请选择运营区域类型"/>
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i class="layui-icon">&#xe615;</i>查询</button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i class="layui-icon">&#x1006;</i>清除</button>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <shiro:hasPermission name="REGION_ADD">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" style="margin-left: 0px;" data-type="add"><i class="layui-icon"></i>添加</button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="REGION_DELETE">
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
    //渲染数据
    var colModel = [{name: 'id', index: 'id',hidden:true},
        {name: 'scode',index: 'scode',label : "运营区域编号",width:200},
        {name: 'sregionName',index: 'sregionName',label : "运营区域名称",width:200,sortable : false},
        {name: 'itype',index: 'itype',label : "运营区域类型", editable: true,
            formatter:"select",editoptions:{value:'10:广告;20:系统公告'},sortable : false,width:135},
        {name: 'icount',index: 'icount',label : "数量",width:80,sortable : false},
        {name: 'sremark',index: 'sremark',label : "说明",width:240,sortable : false},
        {name: 'taddTime',index: 'TADD_TIME',label : "添加日期",formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:200},
        {name: 'saddUser',index: 'saddUser',label : "添加人",width:150,sortable : false},
        {name: 'tupdateTime',index: 'tupdateTime',label : "修改日期",formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:200,sortable : false},
        {name: 'supateUser',index: 'supateUser',label : "修改人",width:150,sortable : false},
        {name : 'process',title:false,index : 'process',label : "操作",sortable : false,width:130}
    ];
    // 表格回调
    function tableCallBack() {
        setTimeout(function () {
            var data = $("#gridTable").jqGrid('getRowData');

            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = "";
                <shiro:hasPermission name="REGION_EDIT">
                str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                    + cl + "')\" title='编辑运营区域' alt='编辑运营区域'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="REGION_DELETE">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='删除运营区域' alt='删除运营区域'  /> | ";
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
            sortname: "TADD_TIME",
            sortorder: "desc"
        };
        initTable(ctx + "/region/queryData", colModel, tableCallBack, config);
    });
    //初始化form中按钮
    var initBtnConfig = {
        addUrl: ctx + '/region/edit',
        deleteUrl: ctx + "/region/delete",
        addTitle: '添加运营区域信息',
        addFn:showLayerMin
    };
    initBtnByForm4(initBtnConfig);

    function editMethod(rid) {
        showLayerMin("编辑运营区域信息", 'edit?rid='+rid);
    }
    function delMethod(rid) {
        confirmDelTip("确定要删除数据?",ctx+"/region/delete",{checkboxId:rid});
    }


</script>
</body>
</html>

