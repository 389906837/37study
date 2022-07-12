<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title>会员第三方授权信息</title>
    <link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
    <link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>会员第三方授权信息</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <input type="text" name="smemberCode" id="smemberCode" value="" placeholder="会员编号..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="smemberName" id="smemberName" value="" placeholder="会员用户名..." class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 170px">
                                <select class="form-control" name="ithirdType" id="ithirdType">
                                    <option value="">请选择第三方类型</option>
                                    <option value="10">微信</option>
                                    <option value="20">支付宝</option>
                                </select>
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sotherNickname" id="sotherNickname" value="" placeholder="第三方用户昵称..." class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 150px">
                                <select class="form-control" name="istatus" id="istatus">
                                    <option value="">请选择状态</option>
                                    <option value="10">关联</option>
                                    <option value="20">解除关联</option>
                                </select>
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="tauthoriseTimeStr" id="tauthoriseTimeStr" placeholder="授权时间" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn layui-btn" id="searchBtn" data-type="query"><i class="layui-icon">&#xe615;</i>查询</button>
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
            elem: '#tauthoriseTimeStr', //指定元素
            range:true,
            type: 'date'
        });
    });

    // 渲染数据
    var colModel = [{name: 'id', index: 'id', hidden: true},
        {name: 'smemberCode', index: 'SMEMBER_CODE', label: "会员编号", sortable: false, width: 100},
        {name: 'smemberNameDesensitize', index: 'SMEMBER_NAME', label: "会员用户名", sortable: false, width: 100},
        {
            name: 'ithirdType', index: 'ITHIRD_TYPE', label: "第三方类型", editable: true,
            formatter: "select", editoptions: {value: '10:微信;20:支付宝'}, sortable: false, width: 80
        },
        {name: 'sotherNickname', index: 'SOTHER_NICKNAME', label: "第三方用户昵称", sortable: false, width: 100},
        {
            name: 'istatus', index: 'ISTATUS', label: "状态", editable: true,
            formatter: "select", editoptions: {value: '10:关联;20:解除关联'}, sortable: false, width: 80
        },
        {
            name: 'tauthoriseTime', index: 'TAUTHORISE_TIME', label: "授权时间", formatter: function (value) {
                return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }, width: 130
        },
        {name: 'sremark', index: 'SREMARK', label: "备注", sortable: false, width: 160}
    ];
    // 表格回调
    function tableCallBack() {
    }
    // 初始化表格,传入页面所需参数
    $(function () {
        var config = {
            rownumbers: true,
            multiselect:false,
            sortname:"ISTATUS=10",
            sortorder:"desc"
        };
        initTable(ctx+"/thirdauthoriseinfo/queryData",colModel,tableCallBack,config);
    });
    // 调用Button按钮方法(查询,清除)
    initBtnByForm2();
</script>
</body>
</html>

