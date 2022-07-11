<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>平台接口购买记录管理</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>平台接口购买记录管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="snickName" id="snickName" value="" placeholder="用户昵称..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="interfaceName" id="interfaceName" value=""
                                       placeholder="接口名称..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="spayNumber" id="spayNumber" value="" placeholder="支付流水号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" list="{10:按次购买,20:资源包购买,30:按时间购买}" id="sbuyWay"
                                             name="sbuyWay" entire="true" entireName="请选择购买方式"/>
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" list="{10:微信支付,20:支付宝,30:银行卡支付}" id="ipayWay"
                                             name="ipayWay" entire="true" entireName="请选择支付方式"/>
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" list="{10:待付款,20:已付款}" id="istatus"
                                             name="istatus" entire="true" entireName="请选择订单状态"/>
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i
                                        class="layui-icon">&#xe615;</i>查询
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i
                                        class="layui-icon">&#x1006;</i>清除
                                </button>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <shiro:hasPermission name="BUY_RECORD_ADD">
                               <%-- <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"><i
                                        class="layui-icon"></i>添加
                                </button>--%>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="BUY_RECORD_DEL">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i
                                        class="layui-icon"></i>删除
                                </button>
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
    var colModel = [{name: 'id', index: 'id', hidden: true},
        {name: 'suserCode', index: 'SUSER_CODE', label: "用户编号", width: 100},
        {name: 'snickName', index: 'snickName', label: "用户昵称", width: 100},
        {name: 'sinterfaceCode', index: 'SINTERFACE_CODE', label: "接口编号", width: 100},
        {name: 'interfaceName', index: 'interfaceName', label: "接口名称", width: 100},
        {name: 'fpayAmount', index: 'FPAY_AMOUNT', label: "支付金额", width: 100},
        {name: 'spayNumber', index: 'SPAY_NUMBER', label: "支付流水号", width: 100},
        {
            name: 'sbuyWay', index: 'SBUY_WAY', label: "购买方式", width: 80, formatter: "select",
            editoptions: {value: '10:按次购买;20:资源包购买;30:按时间购买'}
        }, {
            name: 'ipayWay', index: 'IPAY_WAY', label: "支付方式", width: 80, formatter: "select",
            editoptions: {value: '10:微信支付;20:支付宝;30:银联支付'}
        }, {
            name: 'tpayFinishTime', index: 'TPAY_FINISH_TIME', label: "支付完成时间", width: 80, formatter: function (value) {
                if (isEmpty(value)) {
                    return '';
                }
                return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
            }
        }, {
            name: 'tbuyTime', index: 'TBUY_TIME', label: "购买时间", width: 80, formatter: function (value) {
                if (isEmpty(value)) {
                    return '';
                }
                return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
            }
        }, {
            name: 'istatus', index: 'ISTATUS', label: "订单状态", width: 80, formatter: "select",
            editoptions: {value: '10:待付款;20:已付款'}
        },
        {name: "process", index: "process", title: false, label: "操作", sortable: false, frozen: true, width: 120}
    ]
    // 表格回调
    function tableCallBack() {
        setTimeout(function () {
            var ids = $("#gridTable").jqGrid('getRowData');
            var merchantCode = "${SESSION_KEY_SMERCHANT_CODE}";
            for (var i = 0; i < ids.length; i++) {
                var cl = ids[i].id;
                var str = '';
                <shiro:hasPermission name="BUY_RECORD_EDIT">
                str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                    + cl + "')\" title='编辑' alt='编辑'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="BUY_RECORD_DEL">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='删除' alt='删除' /> | ";
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
            sortname: "",
            sortorder: ""
        };
        initTable(ctx + "/buyRecord/queryData", colModel, tableCallBack, config);
    });
    // 调用Button按钮方法(查询,清除)
    //初始化form中按钮
    var initBtnConfig = {
        addUrl: ctx + '/buyRecord/edit',
        deleteUrl: ctx + "/buyRecord/delete",
        addTitle: '添加平台接口购买记录',
        addFn: showLayerMedium
    };
    initBtnByForm4(initBtnConfig);

    function editMethod(sid) {
        showLayerMedium("编辑平台接口购买记录", ctx + '/buyRecord/edit?sid=' + sid);
    }
    function delMethod(sid) {
        confirmDelTip("确定要删除平台接口购买记录?", ctx + "/buyRecord/delete", {checkboxId: sid});
    }
</script>
</body>
</html>

