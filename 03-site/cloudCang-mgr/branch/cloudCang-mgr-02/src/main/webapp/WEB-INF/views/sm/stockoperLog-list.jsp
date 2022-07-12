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
                    <h5>库存操作日志</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <input type="text" name="smerchantCode" id="smerchantCode" value="" placeholder="商户编号..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sdeviceCode" id="sdeviceCode" value="" placeholder="设备编号..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="scommodityCode" id="scommodityCode" value="" placeholder="商品编号..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <select class="form-control" name="istatus" id="istatus">
                                    <option value="">操作状态</option>
                                    <option value="10">上架</option>
                                    <option value="20">下架</option>
                                    <option value="30">售出</option>
                                    <option value="40">异常上架</option>
                                    <option value="50">异常下架</option>
                                    <option value="60">异常售出</option>
                                    <option value="70">手动下架</option>
                                </select>
                            </div>
                            <div class="layui-inline">
                                    <input type="text" name="toperateStartDateStr" id="toperateStartDateStr" placeholder="操作时间"  autocomplete="off" class="layui-input">
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
<script type="text/javascript" src="${staticSource}/resources/page/sm/stockoperlog/stockoperLog-list.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    /**
     * 选择日期范围
     */
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //执行一个laydate实例(开门时间)
        laydate.render({
            elem: '#toperateStartDateStr', //指定元素
            range:true,
            type:'date'
        });
    });

    //渲染数据
    var colModel = [{name: 'id', index: 'id',hidden:true},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode',index: 'smerchantCode',label : "商户编号",sortable : false,width:150},
        {name: 'shName',index: 'shName',label : "商户名称",sortable : false,width:150},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'smerchantCode',index: 'smerchant_code',hidden:true},
        </c:if>
        {name: 'sdeviceCode',index: 'sdeviceCode',label : "设备编号",sortable : false,width:150},
        {name: 'sbName',index: 'sbName',label : "设备名称",sortable : false,width:150},
        {name: 'adress',index: 'adress',label : "设备地址",sortable : false,width:150},
        {name: 'scommodityCode',index: 'scommodityCode',label : "商品编号",sortable : false,width:150},
        {name: 'spName',index: 'spName',label : "商品名称",sortable : false,width:150},
        {name: 'sidentifies',index: 'sidentifies',label : "商品唯一标识",sortable : false,width:100},
        {name: 'istatus',index: 'istatus',label : "操作状态", editable: true,
            formatter:"select",editoptions:{value:'10:上架;20:下架;30:售出;40:异常上架;50:异常下架;60:异常售出;70:手动下架'},sortable : false,width:80},
        {name: 'taddTime',index: 'tadd_Time',label : "操作时间",formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:160},
        {name: 'sremark',index: 'sremark',label : "备注",sortable : false,width:150},
    ];
    // 表格回调
    function tableCallBack() {
    }
    // 初始化表格,传入页面所需参数
    $(function () {
        var config = {
            rownumbers: true,
            multiselect:false,
            sortname: 'A.tadd_time',
            sortorder: 'desc'
        };
        initTable(ctx+"/stockoperLog/queryData",colModel,tableCallBack,config);
    });
    // 调用Button按钮方法(查询,清除)
    initBtnByForm2();
</script>
</body>
</html>

