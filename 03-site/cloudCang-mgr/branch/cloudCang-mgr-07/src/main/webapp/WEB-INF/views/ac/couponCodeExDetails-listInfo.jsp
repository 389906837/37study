<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title>券码兑换明细列表</title>
    <link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
    <link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>券码明细</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-inline">
                                    <input type="text" name="merchantCode" id="merchantCode" value="" placeholder="商户编号..." class="layui-input">
                                </div>
                                <div class="layui-inline">
                                    <input type="text" name="merchantName" id="merchantName" value="" placeholder="商户名称..." class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-inline">
                                    <input type="text" name="sbatchCode" id="sbatchCode" value="" placeholder="下发批次编号..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                    <input type="text" name="smemberCode" id="smemberCode" value="" placeholder="会员编号..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                    <input type="text" name="smemberName" id="smemberName" value="" placeholder="会员用户名..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                    <input type="text" name="sexCouponsCode" id="sexCouponsCode" value="" placeholder="兑换券码..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                    <input type="text" name="iexTimes" id="iexTimes" value="" placeholder="兑换次数..." class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                    <cang:select type="list" list="{1:未兑换,2:兑换中,3:已兑换,4:兑换失败,5:失效}" id="istatus" name="istatus" entire="true" entireName="请选择状态"/>
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="texTimeStr" id="texTimeStr" placeholder="兑换时间" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="texEndtimeStr" id="texEndtimeStr" placeholder="兑换失效时间" class="layui-input">
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
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //执行一个laydate实例
        laydate.render({
            elem: '#texTimeStr', //指定元素,兑换日期
            range:true,
            type: 'date'
        });

        laydate.render({
            elem: '#texEndtimeStr', //指定元素,兑换失效日期
            range:true,
            type: 'date'
        });
    });

    // 渲染数据
    var colModel = [{name: 'id', index: 'id',hidden:true},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'merchantCode',label : "商户编号",index: 'smerchant_code'},
        {name: 'merchantName',label : "商户名称",index: 'merchantName'},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'smerchantCode',index: 'smerchant_code',hidden:true},
        </c:if>
        {name: 'sbatchCode',index: 'sbatchCode',label : "下发批次编号",sortable : true,width:120},
        {name: 'smemberCode',index: 'smemberCode',label : "会员编号",sortable : false,width:90},
        {name: 'smemberName',index: 'smemberName',label : "会员用户名",sortable : false,width:80},
        {name: 'sexCouponsCode',index: 'sexCouponsCode',label : "兑换券码",sortable : false,width:120},
        {name: 'iexTimes',index: 'iexTimes',label : "兑换次数",sortable : false,width:120},
        {name: 'istatus',index: 'istatus',label : "状态", editable: true,
            formatter:"select",editoptions:{value:'1:未兑换;2:兑换中;3:已兑换;4:兑换失败;5:失效'},sortable : false,width:80},
        {name: 'texTime',index: 'TEX_TIME',label : "兑换日期",formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:170},
        {name: 'texEndtime',index: 'TEX_ENDTIME',label : "兑换失效日期",formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:170},
        {name: 'sremark',index: 'sremark',label : "备注",sortable : false,width:90},
    ];
    // 表格回调
    function tableCallBack() {
    }
    // 初始化表格,传入页面所需参数
    $(function () {
        var config = {
            rownumbers: true,
            multiselect:false,
            sortname:'TEX_ENDTIME',
            sortorder:'desc',
        };
        initTable(ctx+"/couponBatch/queryDataInfo",colModel,tableCallBack,config);
    });
    // 调用Button按钮方法(查询,清除)
    initBtnByForm2();
</script>
</body>
</html>

