<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title>资金账户管理</title>
    <link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
    <link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>资金账户管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-inline" style="width: 120px">
                                <input type="text" name="scode" id="scode" value="" placeholder="账户编号..." class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 120px">
                                <input type="text" name="smemberCode" id="smemberCode" value="" placeholder="会员编号..." class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 120px">
                                <input type="text" name="smemberName" id="smemberName" value="" placeholder="会员用户名..." class="layui-input">
                            </div>
                            <div class="layui-inline" style="margin-top: 10px">
                                <label class="layui-form-label" style="width: 120px">账户可用余额</label>
                                <div class="layui-inline" style="width: 70px;">
                                    <input type="text" name="fusableBalanceBegin" id="fusableBalanceBegin" autocomplete="off" class="layui-input">
                                </div>
                                <div class="layui-form-mid">-</div>
                                <div class="layui-inline" style="width: 70px;">
                                    <input type="text" name="fusableBalanceEnd" id="fusableBalanceEnd" autocomplete="off" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-inline" style="margin-top: 10px">
                                <label class="layui-form-label" style="width: 120px">提现冻结金额</label>
                                <div class="layui-inline" style="width: 70px;">
                                    <input type="text" name="famountFrozenBegin" id="famountFrozenBegin" autocomplete="off" class="layui-input">
                                </div>
                                <div class="layui-form-mid">-</div>
                                <div class="layui-inline" style="width: 70px;">
                                    <input type="text" name="famountFrozenEnd" id="famountFrozenEnd" autocomplete="off" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-inline" style="width: 100px">
                                <cang:select type="list" id="istatus" name="istatus" list="{10:正常,20:冻结,30:销户}" entire="true" entireName="账户状态" />
                            </div>
                            <div class="layui-inline" style="width: 100px">
                               <cang:select type="list" id="iaccountType" name="iaccountType" list="{10:平台账户,20:会员账户}" entire="true" entireName="账户类型" />
                            </div>
                            <div class="layui-inline" style="width: 100px">
                               <cang:select type="list" id="iaccountUse" name="iaccountUse" groupNo="SP000117"  list="{10:活动费户,20:会员户}" entire="true" entireName="账户用途" />
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn layui-btn layui-btn" id="searchBtn" data-type="query"><i class="layui-icon">&#xe615;</i>查询</button>
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
    // 渲染数据
    var colModel = [{name: 'id', index: 'id',hidden:true},
        {name: 'scode',index: 'SCODE',label : "账户编号",sortable : false,width:150},
        {name: 'smemberCode',index: 'SMEMBER_CODE',label : "会员编号",sortable : false,width:150},
        {name: 'smemberNameDesensitize',index: 'SMEMBER_NAME',label : "会员用户名",sortable : false,width:150},
        {name: 'ftotalBalance',index: 'FTOTAL_BALANCE',label : "账户总余额",sortable : false,width:150},
        {name: 'fusableBalance',index: 'FUSABLE_BALANCE',label : "账户可用余额",sortable : false,width:150},
        {name: 'famountFrozen',index: 'FAMOUNT_FROZEN',label : "提现冻结金额",sortable : false,width:150},
        {name: 'famountFrozenOther',index: 'FAMOUNT_FROZEN_OTHER',label : "其他冻结金额",sortable : false,width:150},
        {name: 'istatus',index: 'ISTATUS',label : "账户状态", editable: true,
            formatter:"select",editoptions:{value:'10:正常;20:冻结;30:销户'},sortable : false,width:90},
        {name: 'iaccountType',index: 'IACCOUNT_TYPE',label : "账户类型", editable: true,
            formatter:"select",editoptions:{value:'10:平台账户;20:会员账户'},sortable : false,width:90},
        {name: 'iaccountUse',index: 'IACCOUNT_USE',label : "账户用途", editable: true,
            formatter:"select",editoptions:{value:'10:活动费户;20:会员户'},sortable : false,width:90},
        {name: 'taddTime',index: 'TADD_TIME',label : "创建时间",formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:160}
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
        initTable(ctx+"/fundaccount/queryData",colModel,tableCallBack,config);
    });
    // 调用Button按钮方法(查询,清除)
    initBtnByForm2();
</script>
</body>
</html>

