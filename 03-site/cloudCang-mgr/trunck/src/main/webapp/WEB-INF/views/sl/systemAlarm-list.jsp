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
                    <h5>系统警报日志</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <input type="text" name="smemberCode" id="smemberCode" value="" placeholder="操作会员编号..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="smemberName" id="smemberName" value="" placeholder="操作会员用户名..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="soperIp" id="soperIp" value="" placeholder="操作IP..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <cang:select type="list" list="{10:购物会员,2:补货会员,3:系统用户}" id="itype"
                                             name="itype" entire="true" entireName="请选择操作类型"/>
                            </div>
                            <div class="layui-inline">
                                <cang:select type="dic" name="iproblemType" id="iproblemType" entire="true"
                                             entireName="请选择警报问题类型" groupNo="SP000151"/>
                            </div>
                            <div class="layui-inline">
                                <cang:select type="dic" name="isystemType" id="isystemType" entire="true"
                                             entireName="请选择来源系统" groupNo="SP000152"/>
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="talarmTimeStr" id="talarmTimeStr" placeholder="报警时间" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sproblemDesc" id="sproblemDesc" value="" placeholder="报警问题描述..." class="layui-input">
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
            elem: '#talarmTimeStr', //指定元素
            range:true,
            type: 'date'
        });
    });

    // 渲染数据
    var colModel = [{name: 'id', index: 'id',hidden:true},
        {name: 'smemberCode',index: 'SMEMBER_CODE',label : "操作会员编号",sortable : false,width:130},
        {name: 'smemberName',index: 'SMEMBER_NAME',label : "操作会员用户名",sortable : false,width:130},
        {name: 'soperIp',index: 'SOPER_IP',label : "操作IP",sortable : false,width:150},
        {name: 'itype',index: 'ITYPE',label : "操作会员类型", editable: true,
            formatter:"select",editoptions:{value:'10:购物会员;20:补货会员;30:系统用户'},sortable : false,width:110},
        {name: 'iproblemType',index: 'IPROBLEM_TYPE',label : "警报问题类型", editable: true,
            formatter:"select",editoptions:{value:'10:购物会员库存上架;20:;30:'},sortable : false,width:130},
        {name: 'isystemType',index: 'ISYSTEM_TYPE',label : "来源系统", editable: true,
            formatter:"select",editoptions:{value:'cloud-cang-bzc:业务处理服务;20:;30:'},sortable : false,width:100},
        {name: 'talarmTime',index: 'TALARM_TIME',label : "报警时间",formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:150},
        {name: 'sproblemDesc',index: 'SPROBLEM_DESC',label : "报警问题描述",sortable : false,width:670},
    ];
    // 表格回调
    function tableCallBack() {
    }
    // 初始化表格,传入页面所需参数
    $(function () {
        var config = {
            rownumbers: true,
            multiselect:false,
            sortname:'talarm_time',
            sortorder:'desc'
        };
        initTable(ctx+"/systemAlarm/queryData",colModel,tableCallBack,config);
    });
    // 调用Button按钮方法(查询,清除)
    initBtnByForm2();
</script>
</body>
</html>

