<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>总库存列表</title>
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
                    <h5><spring:message code="sm.total.inventory.query" /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder='<spring:message code="sm.document.number" />...'
                                       class="layui-input">
                            </div>
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-inline">
                                    <input type="text" name="shcode" id="shcode" value=""
                                           placeholder='<spring:message code="sm.merchant.code" />...' class="layui-input">
                                </div>
                                <div class="layui-inline">
                                    <input type="text" name="merchantName" id="merchantName" value=""
                                           placeholder='<spring:message code="sm.merchant.name" />...' class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-inline">
                                <input type="text" name="scommodityCode" id="scommodityCode" value=""
                                       placeholder='<spring:message code="sm.product.id" />...' class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="spName" id="spName" value="" placeholder='<spring:message code="sm.product.name" />...'
                                       class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i
                                        class="layui-icon">&#xe615;</i><spring:message code="sm.query" />
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i
                                        class="layui-icon">&#x1006;</i><spring:message code="sm.remove" />
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
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/page/sm/stockoperlog/stockoperLog-list.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    // 渲染数据
    var colModel = [{name: 'id', index: 'id', hidden: true},
        {name: 'scode', index: 'SCODE', label: '<spring:message code="sm.document.number" />', width: 170},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode', label: '<spring:message code="sm.merchant.code" />', index: 'SMERCHANT_CODE', sortable: false, width: 170},
        {name: 'merchantName', label: '<spring:message code="sm.merchant.name" />', index: 'MERCHANTNAME', sortable: false, width: 340},
        </c:if>
        {name: 'scommodityCode', index: 'SCOMMODITY_CODE', label: '<spring:message code="sm.product.id" />', width: 165, sortable: false},
        {name: 'spName', index: 'spName', label: '<spring:message code="sm.product.name" />', width: 260, sortable: false},
        {name: 'istock', index: 'ISTOCK', label: '<spring:message code="sm.real-time.inventory.quantity" />', width: 190, sortable: false, formatter: function(value) { return '<span class="' + (value == 0 ? 'istatus-danger' : 'istatus-normal') + '">' +  value + '</span>' }},
        {name: 'sremark', index: 'sremark', label: '<spring:message code="sm.remarks" />', sortable: false, width: 140},
        {name: 'process',title:false, index: 'process', label: '<spring:message code="sm.operation" />', sortable: false, width: 150}
    ];
    // 表格回调
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
            var data = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = "";
                <shiro:hasPermission name="INFO_QUERYDEVICESTOCkINFO">
                str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"queryInfoMethod('"
                    + cl + "')\" title='<spring:message code="sm.total.inventory.details" />' alt='<spring:message code="sm.total.inventory.details" />'  /> | ";
                </shiro:hasPermission>
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
            rownumbers: true,
            multiselect: false,
            sortname: 'ISTOCK',
            sortorder: 'desc'
        };
        initTable(ctx + "/devicestock/queryData", colModel, tableCallBack, config);
    });
    // 调用Button按钮方法(查询,清除)
    var initBtnConfig = {
        addModelConfig: {area: ['60%', '80%']}
    };
    initBtnByForm2(initBtnConfig);

    function queryInfoMethod(sid) {
        showLayer('<spring:message code="sm.details.of.total.inventory.goods" />', ctx + '/devicestock/queryDevicestockInfo?sid=' + sid, {area: ['70%', '90%']});
    }

</script>
</body>
</html>

