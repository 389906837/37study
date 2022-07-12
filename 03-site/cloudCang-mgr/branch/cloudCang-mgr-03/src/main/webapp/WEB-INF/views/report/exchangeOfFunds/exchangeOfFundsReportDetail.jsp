<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title><spring:message code="report.fund.transaction.report.details" /></title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet"/>
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css" rel="stylesheet"/>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <%-- <div class="ibox-title">
                     <h5><spring:message code="report.fund.transaction.report.details" /></h5>
                 </div>--%>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-inline" style="width: 210px">
                                <input type="text" name="commodityName" id="commodityName" placeholder='<spring:message code="report.product.name" />'
                                       class="layui-input">
                            </div>

                            <div class="layui-inline">
                                <button class="layui-btn layui-btn layui-btn" id="searchBtn" data-type="query"><i
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
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    var colModel = [
        {name: 'commodityCode', index: 'AMOUNT', label: '<spring:message code="report.commodity.number" />', sortable: false, width: 210},
        {name: 'commodityFullName', index: 'commodityFullName', label: '<spring:message code="report.commodity.name" />',  sortable: false,width: 310},
        {name: 'commodityTotal', index: 'AMOUNT', label: '<spring:message code="report.total.number.of.products" />', sortable: false, width: 240},
        {name: 'commodityFactualAmount', index: 'AMOUNT', label: '<spring:message code="report.total.amount" />', sortable: false, width: 200}
    ];
    $(function () {
        var config = {
            sortname: "",
            sortorder: "desc"
        };
        initTable(ctx + "/exchangeOfFundsReport/queryDataDetail?type=${type}&dateResult=${dateResult}", colModel, null, config);
    });
    initBtnByForm2();
</script>
</body>
</html>

