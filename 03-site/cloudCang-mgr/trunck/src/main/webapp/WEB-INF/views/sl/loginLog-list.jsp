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
                    <h5>登录日志</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="suserCode" id="suserCode" value="" placeholder="会员编号..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="smemberName" id="smemberName" value="" placeholder="会员用户名..."     class="layui-input">
                            </div>
                            <div class="layui-input-inline" >
                                <select class="form-control" name="itype" id="itype">
                                    <option value="">登录类型</option>
                                    <option value="10">授权登录</option>
                                    <option value="20">账号登录</option>
                                    <option value="30">SSO登录</option>
                                </select>
                            </div>
                            <div class="layui-input-inline" >
                                <select class="form-control" name="ideviceType" id="ideviceType">
                                    <option value="">登录设备类型</option>
                                    <option value="10">微信</option>
                                    <option value="20">支付宝</option>
                                    <option value="30">APP</option>
                                    <option value="40">WAP站</option>
                                    <option value="30">PC站</option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sip" id="sip" value="" placeholder="登录IP地址..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="tloginTimeStr" id="tloginTimeStr" placeholder="登录时间" class="layui-input">
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
            elem: '#tloginTimeStr', //指定元素
            range:true,
            type: 'date'
        });
    });

    // 渲染数据
    var colModel = [{name: 'id', index: 'id',hidden:true},
        {name: 'suserCode',index: 'SUSER_CODE',label : "会员编号",sortable : false,width:145},
            {name: 'smemberNameDesensitize',index: 'SMEMBER_NAME',label : "会员用户名",sortable : false,width:160},
        {name: 'sip',index: 'SIP',label : "登录IP地址",sortable : false,width:160},
        {name: 'itype',index: 'ITYPE',label : "登录类型", editable: true,
            formatter:"select",editoptions:{value:'10:授权登录;20:账号登录;30:SSO登录'},sortable : false,width:100},
        {name: 'ideviceType',index: 'IDEVICE_TYPE',label : "登录设备类型", editable: true,
            formatter:"select",editoptions:{value:'10:微信;20:支付宝;30:APP;40:WAP站;50:PC站'},sortable : false,width:100},
        {name: 'tloginTime',index: 'TLOGIN_TIME',label : "登录时间",formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:160},
    ];
    // 表格回调
    function tableCallBack() {
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

