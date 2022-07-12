<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title>验证码记录查询</title>
    <link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
    <link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>验证码发送记录</h5>
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
                                <input type="text" name="smsgRecipient" id="smsgRecipient" value="" placeholder="消息收件人..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <select class="form-control" name="istate" id="istate">
                                    <option value="">请选择消息状态</option>
                                    <option value="1">待发送</option>
                                    <option value="2">正在发送</option>
                                    <option value="3">发送成功</option>
                                    <option value="4">发送失败</option>
                                    <option value="5">超额无效</option>
                                </select>
                            </div>
                            <div class="layui-inline" style="width: 180px">
                                <input type="text" name="tbeginSendDatetimeStr" id="tbeginSendDatetimeStr" placeholder="发送时间" class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 180px">
                                <input type="text" name="taddTimeStr" id="taddTimeStr" placeholder="添加时间" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="scontent" id="scontent" value="" placeholder="消息内容..." class="layui-input">
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
<script type="text/javascript" src="${staticSource}/resources/page/xx/msgtask/codeTask-list.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    /**
     * 选择日期范围
     */
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //执行一个laydate实例(发送时间)
        laydate.render({
            elem: '#tbeginSendDatetimeStr', //指定元素
            range: true,
            type: 'date'
        });
        //添加时间
        laydate.render({
            elem: '#taddTimeStr', //指定元素
            range: true,
            type: 'date'
        });
    });
    // 渲染数据
    var colModel = [{name: 'id', index: 'id',hidden:true},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'SHCODE',label : "商户编号",index: 'SHCODE',width:130},
        {name: 'MERCHANTNAME',label : "商户名称",index: 'MERCHANTNAME',width:230,sortable : false},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'smerchantCode', index: 'smerchant_code', hidden: true},
        </c:if>
        {name: 'SMSG_RECIPIENT',index: 'SMSG_RECIPIENT',label : "消息收件人",sortable : false,width:100},
        {name: 'ISTATE',index: 'ISTATE',label : "消息状态", editable: true,
            formatter:"select",editoptions:{value:'1:待发送;2:正在发送;3:发送成功;4:发送失败;5:超额无效'},sortable : false,width:80},
        {name: 'TBEGIN_SEND_DATETIME',index: 'TBEGIN_SEND_DATETIME',label : "发送时间",formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:150},
        {name: 'TADDTIME',index: 'TADDTIME',label : "添加时间",formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:150},
        {name: 'SCONTENT',index: 'SCONTENT',label : "消息内容",sortable : false,width:550},
    ];
    // 表格回调
    function tableCallBack() {
    }
    // 初始化表格,传入页面所需参数
    $(function () {
        var config = {
            rownumbers: true,
            multiselect:false,
            sortname:'TBEGIN_SEND_DATETIME',
            sortorder:'desc'
        };
        initTable(ctx+"/msgTask/queryCodeData",colModel,tableCallBack,config);
    });
    // 调用Button按钮方法(查询,清除)
    initBtnByForm2();
</script>
</body>
</html>

