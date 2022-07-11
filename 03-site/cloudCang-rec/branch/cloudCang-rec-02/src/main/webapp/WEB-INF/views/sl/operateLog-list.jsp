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
                    <h5>操作日志</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <input type="text" name="susername" id="susername" value="" placeholder="用户名..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="srealName" id="srealName" value="" placeholder="用户姓名..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sip" id="sip" value="" placeholder="访问IP..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <cang:select type="list" list="{1:添加,2:修改,3:删除,4:查看,5:其他,6:登录}" id="iaction"
                                             name="iaction" entire="true" entireName="请选择操作动作"/>
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="scontent" id="scontent" value="" placeholder="内容..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="toperateDateStr" id="toperateDateStr" placeholder="操作时间" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i class="layui-icon">&#xe615;</i>查询</button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i class="layui-icon">&#x1006;</i>清除</button>
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
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
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
            elem: '#toperateDateStr', //指定元素
            range:true,
            type: 'date'
        });
    });

    // 渲染数据
    var colModel = [{name: 'id', index: 'id',hidden:true},
        {name: 'susername',index: 'susername',label : "用户名",sortable : false,width:200},
        {name: 'srealName',index: 'srealName',label : "用户姓名",sortable : false,width:200},
        {name: 'sip',index: 'sip',label : "访问IP",sortable : false,width:150},
        {name: 'iaction',index: 'iaction',label : "动作", editable: true,
            formatter:"select",editoptions:{value:'1:添加;2:修改;3:删除;4:查看;5:其他;6:登录'},sortable : false,width:130},
        {name: 'scontent',index: 'scontent',label : "内容",sortable : false,width:670},
        {name: 'toperateDate',index: 'toperate_date',label : "操作时间",formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:240},
    ];
    // 表格回调
    function tableCallBack() {
    }
    // 初始化表格,传入页面所需参数
    $(function () {
        var config = {
            rownumbers: true,
            multiselect:false,
            sortname:'toperate_date',
            sortorder:'desc'
        };
        initTable(ctx+"/operateLog/queryData",colModel,tableCallBack,config);
    });
    // 调用Button按钮方法(查询,清除)
    initBtnByForm2();
</script>
</body>
</html>

