<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>商品补货列表</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code="rm.Commodity.replenishment.management" /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                    <input type="text" name="scode" id="scode" value="" placeholder='<spring:message code="rm.replenishment.bill.number" />...'
                                           class="layui-input">
                            </div>
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-inline">
                                    <input type="text" name="shcode" id="shcode" value=""
                                           placeholder='<spring:message code="main.merchant.number" />...' class="layui-input">
                                </div>
                                <div class="layui-inline">
                                    <input type="text" name="merchantName" id="merchantName" value=""
                                           placeholder='<spring:message code="main.merchant.name" />...' class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-inline">
                                    <input type="text" name="sdeviceCode" id="sdeviceCode" value=""
                                           placeholder='<spring:message code="main.device.id" />...' class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sbsname" id="sbsname" value="" placeholder='<spring:message code="main.device.name" />...'
                                       class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sdeviceAddress" id="sdeviceAddress" value=""
                                       placeholder='<spring:message code="main.device.address" />...' class="layui-input">
                            </div>
                            <div class="layui-inline">
                                    <input type="text" name="srenewalName" id="srenewalName" value=""
                                           placeholder='<spring:message code="rm.name.of.replenisher" />...' class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="srenewalMobile" id="srenewalMobile" value="" placeholder='<spring:message code="rm.replenisher-s.mobile.phone.number" />...'
                                       class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="treplenishmentTimeStr" id="treplenishmentTimeStr" placeholder='<spring:message code="rm.time.of.replenishment" />' class="layui-input">
                            </div>

                            <div class="layui-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i
                                        class="layui-icon">&#xe615;</i><spring:message code="main.query" />
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i
                                        class="layui-icon">&#x1006;</i><spring:message code="main.clear" />
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
</div>
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<script src="${staticSource}/resources/js/tableExportComm-${currentLanguage}.js"></script>
<script src="${staticSource}/resources/js/xlsx.core.min.js"></script>
<script src="${staticSource}/resources/js/tableExport.min.js"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    /**
     * 选择日期范围
     */
    layui.use('laydate', function () {
        var laydate = layui.laydate;

        //执行一个laydate实例
        laydate.render({
            elem: '#treplenishmentTimeStr', //指定元素
            range:true,
            type: 'date'
        });
    });

    // 渲染数据
    var colModel = [{name: 'id', index: 'id', hidden: true},
        {name: 'scode', index: 'SCODE', label: "<spring:message code='rm.replenishment.bill.number' />", sortable: false, width: 190},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'shcode', label: '<spring:message code="main.merchant.number" />', index: 'SHCODE', sortable: false},
        {name: 'merchantName', label: '<spring:message code="main.merchant.name" />', index: 'MERCHANTNAME', sortable: false, width: 320},
        </c:if>
        {name: 'sdeviceCode', index: 'SDEVICE_CODE', label: '<spring:message code="main.device.id" />', width: 130},
        {name: 'sbsname', index: 'SBSNAME', label: '<spring:message code="main.device.name" />', width: 180},
        {name: 'sdeviceAddress', index: 'SDEVICE_ADDRESS', label: '<spring:message code="main.device.address" />', sortable: false, width: 330},
        {name: 'srenewalName', index: 'srenewalName', label: '<spring:message code="rm.name.of.replenisher" />', width: 120},
        {name: 'srenewalMobile', index: 'SRENEWAL_MOBILE', label: '<spring:message code="rm.replenisher-s.mobile.phone.number" />', width: 200},
        {name: 'iactualShelves', index: 'IACTUAL_SHELVES', label: '<spring:message code="rm.number.of.on.the.shelves" />', width: 200},
        {name: 'iactualUnder', index: 'IACTUAL_UNDER', label: '<spring:message code="rm.number.of.off.the.shelves" />', width: 200},
        {
            name: 'treplenishmentTime',
            index: 'TREPLENISHMENT_TIME',
            label: '<spring:message code="rm.time.of.replenishment" />',
            formatter: function (value) {
                return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
            },
            width: 200
        },
        {name: 'process',title:false, index: 'process', label: '<spring:message code="main.operation" />', sortable: false, width: 150}
    ];
    // 表格回调
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "scroll" });
            var data = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = "";
                <shiro:hasPermission name="INFO_REPLENISHMENT">
                str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"queryInfoMethod('"
                    + cl + "')\" title='<spring:message code="rm.replenishment.details" />' alt='<spring:message code="rm.replenishment.details" />'  /> | ";
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
            shrinkToFit: false,
            autoScroll: true,
            rownumbers: true,
            multiselect: false,
            sortname: 'TREPLENISHMENT_TIME',
            sortorder: 'desc'
        };
        initTable(ctx + "/replenishment/queryData", colModel, tableCallBack, config);
        <shiro:hasPermission name="REPLENISHMENT_DOWNLOADEXCEL">
        addExportBtnByUrl("${ctx}/replenishment/downloadExcel");
        </shiro:hasPermission>
    });
    // 调用Button按钮方法(查询,清除)
    var initBtnConfig = {
        addModelConfig: {area: ['60%', '80%']}
    };
    initBtnByForm2(initBtnConfig);

    function queryInfoMethod(sid) {
        showLayerMax('<spring:message code="rm.replenishment.details" />', ctx + '/replenishment/queryReplenishMentInfo?sid=' + sid);
    }

</script>
</body>
</html>

