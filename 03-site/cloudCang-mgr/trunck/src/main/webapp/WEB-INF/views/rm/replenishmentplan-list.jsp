<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>商品计划补货列表</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>商品计划补货查询</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder="计划补货单编号..."
                                       class="layui-input">
                            </div>
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-inline">
                                    <input type="text" name="shcode" id="shcode" value=""
                                           placeholder="商户编号..." class="layui-input">
                                </div>
                                <div class="layui-inline">
                                    <input type="text" name="merchantName" id="merchantName" value=""
                                           placeholder="商户名称..." class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-inline">
                                <input type="text" name="sdeviceCode" id="sdeviceCode" value="" placeholder="设备编号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sbsname" id="sbsname" value="" placeholder="设备名称..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sdeviceAddress" id="sdeviceAddress" value="" placeholder="设备地址..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="srenewalName" id="srenewalName" value="" placeholder="补货员姓名..."
                                       class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="srenewalMobile" id="srenewalMobile" value="" placeholder="补货员手机号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <select class="form-control" name="istatus" id="istatus">
                                    <option value="">补货状态</option>
                                    <option value="10">待配货</option>
                                    <option value="20">配送中</option>
                                    <option value="30">已完成</option>
                                    <option value="40">取消配货</option>
                                </select>
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="toperateStartDateStr" id="toperateStartDateStr"
                                       placeholder="生成时间" class="layui-input">
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
<script type="text/javascript" src="${staticSource}/resources/page/sl/vistlog/vistLog-list.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">

    /**
     * 选择日期范围
     */
    layui.use('laydate', function () {
        var laydate = layui.laydate;
        //执行一个laydate实例
        laydate.render({
            elem: '#toperateStartDateStr' //指定元素
            , range: true
            , type: 'date'
        });
    });

    // 渲染数据
    var colModel = [{name: 'id', index: 'id', hidden: true},
        {name: 'scode', index: 'SCODE', label: "计划补货单编号", sortable: false},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'shcode', label: "商户编号", index: 'SHCODE', sortable: false, width: 140},
        {name: 'merchantName', label: "商户名称", index: 'MERCHANTNAME', sortable: false, width: 260},
        </c:if>
        {name: 'sdeviceCode', index: 'SDEVICE_CODE', label: "设备编号", width: 90},
        {name: 'sbsname', index: 'SBSNAME', label: "设备名称", width: 120},
        {name: 'sdeviceAddress', index: 'sdeviceAddress', label: "设备地址", sortable: false, width: 400},
        {name: 'srenewalName', index: 'srenewalName', label: "补货员姓名", sortable: false, width: 90},
        {name: 'srenewalMobile', index: 'SRENEWAL_MOBILE', label: "补货员手机号"},
        {
            name: 'istatus', index: 'istatus', label: "补货状态", editable: true,
            formatter:function (value, options, rowObject) {return '<span id="istatus_'+rowObject.id+'" data="'+value+'" class="'+ (({"10":"istatus-warm","20":"istatus-radius","30":"istatus-normal","40":"istatus-danger"})[value])+'"> '+(({"10":"● 待配货","20":"● 配送中","30":"● 已完成","40":"● 取消配送"})[value])+'</span>'},
            sortable: false, width: 80
        },
        {
            name: 'tgenerateTime', index: 'TGENERATE_TIME', label: "生成时间", formatter: function (value) {
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }, width: 160
        },
        {name: 'sremark', index: 'sremark', label: "备注", sortable: false, width: 100},
        {name: 'process',title:false, index: 'process', label: "操作", sortable: false, width: 260}
    ];
    // 表格回调
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "scroll" });
            var data = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = "";
                <shiro:hasPermission name="INFO_REPLENISHMENTPLANT">
                str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"queryInfoMethod('"
                    + cl + "')\" title='计划补货详情' alt='计划补货详情'  /> | ";
                </shiro:hasPermission>
                if ($('#istatus_'+cl).attr('data') == 10) {
                    <shiro:hasPermission name="REPLENISHMENTPLANT_DISTRIBUTION">
                    str += "<img src='${staticSource}/resources/images/oper_icon/rm_distribution.png' class=\"oper_icon\" onclick=\"istatusMethod('"
                        + cl + "',20)\" title='配送中' alt='配送中'  /> | ";
                    </shiro:hasPermission>
                    <shiro:hasPermission name="REPLENISHMENTPLANT_COMPLETED">
                    str += "<img src='${staticSource}/resources/images/oper_icon/rm_completed.png' class=\"oper_icon\" onclick=\"istatusMethod('"
                        + cl + "',30)\" title='已完成' alt='已完成'  /> | ";
                    </shiro:hasPermission>
                    <shiro:hasPermission name="REPLENISHMENTPLANT_CANCELLATION">
                    str += "<img src='${staticSource}/resources/images/oper_icon/rm_cancellation.png' class=\"oper_icon\" onclick=\"istatusMethod('"
                        + cl + "',40)\" title='取消配送' alt='取消配送'  /> | ";
                    </shiro:hasPermission>
                }
                if($('#istatus_'+cl).attr('data') == 20){
                    <shiro:hasPermission name="REPLENISHMENTPLANT_COMPLETED">
                    str += "<img src='${staticSource}/resources/images/oper_icon/rm_completed.png' class=\"oper_icon\" onclick=\"istatusMethod('"
                        + cl + "',30)\" title='已完成' alt='已完成'  /> | ";
                    </shiro:hasPermission>
                    <shiro:hasPermission name="REPLENISHMENTPLANT_CANCELLATION">
                    str += "<img src='${staticSource}/resources/images/oper_icon/rm_cancellation.png' class=\"oper_icon\" onclick=\"istatusMethod('"
                        + cl + "',40)\" title='取消配送' alt='取消配送'  /> | ";
                    </shiro:hasPermission>
                }
                $("#gridTable").jqGrid('setRowData',
                    cl, {
                        process: str.length > 0 ? str.substr(0, str.lastIndexOf(" | ")) : ""
                    });
            }
        }, 0);
    }
    // 初始化表格,传入页面所需参数
    $(function () {
        var config = {
            shrinkToFit: false,
            autoScroll: true,
            rownumbers: true,
            multiselect: false,
            sortname: 'A.ISTATUS=10',
            sortorder: 'desc'
        };
        initTable(ctx + "/replenishmentplant/queryData", colModel, tableCallBack, config);
    });
    // 调用Button按钮方法(查询,清除)
    var initBtnConfig = {
        addModelConfig: {area: ['60%', '80%']}
    };
    initBtnByForm2(initBtnConfig);

    function queryInfoMethod(sid) {
        showLayerMax("计划商品补货详情", ctx + '/replenishmentplant/queryReplenishMentPlanInfo?sid=' + sid);
    }

    function istatusMethod(sid, type) {
        var alertStr = "";
        if (type == 20){
            alertStr = "是否确认修改订单状态为配送中?";
        }
        if (type == 30) {
            alertStr = "是否确认修改订单状态为已完成?";
        }
        if (type == 40){
            alertStr = "是否确认修改订单状态为取消配货?";
        }
        confirmOperTip(alertStr, ctx + "/replenishmentplant/replenishMentStatus", {checkboxId: sid, type: type});
    }

</script>
</body>
</html>

