<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备商品列表</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code="sb.equipment.commodity.management" /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="sdeviceCode" id="sdeviceCode" value="" placeholder='<spring:message code="main.device.id" />...' class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sname" id="sname" value="" placeholder='<spring:message code="main.device.name" />...' class="layui-input">
                            </div>
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantCode" id="merchantCode" value="" placeholder='<spring:message code="main.merchant.number" />...'
                                           class="layui-input">
                                </div>
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantName" id="merchantName" value="" placeholder='<spring:message code="main.merchant.name" />...'
                                           class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-input-inline">
                                <input type="text" name="scommodityCode" id="scommodityCode" value="" placeholder='<spring:message code="main.product.number" />...' class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="commodityName" id="commodityName" value="" placeholder='<spring:message code="main.product.name" />...' class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control"  name="istatus" id="istatus" >
                                    <option value=""><spring:message code="rm.commodity.status" /></option>
                                    <option value="10"><spring:message code="sb.on.sale" /></option>
                                    <option value="20"><spring:message code="sb.lower.shelf" /></option>
                                </select>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query">
                                    <i class="layui-icon">&#xe615;</i><spring:message code="main.query" />
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset">
                                    <i class="layui-icon">&#x1006;</i><spring:message code="main.clear" />
                                </button>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <%-- <button class="layui-btn layui-btn-sm " data-type="add"><i class="layui-icon"></i><spring:message code="main.add" /></button>
                             <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i><spring:message code="main.delete" /></button>--%>
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
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    var colModel = [
        {name: 'id', index: 'id', hidden: true},
        {name: 'sdeviceCode', index: 'sdeviceCode', label: '<spring:message code="main.device.id" />',width:120,sortable : false},
        {name: 'sname', index: 'SNAME', label: '<spring:message code="main.device.name" />',width:120,sortable : false},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'merchantCode', index: 'merchantCode', label: '<spring:message code="main.merchant.number" />',width:160,sortable : false},
        {name: 'merchantName', index: 'merchantName', label: '<spring:message code="main.merchant.name" />',width:200,sortable : false},
        </c:if>
        {name: 'scommodityCode', index: 'scommodityCode', label: '<spring:message code="main.product.number" />',width:120,sortable : false},
        {name: 'commodityFullName', index: 'commodityName', label: '<spring:message code="main.product.name" />',width:120,sortable : false},
        {name: 'totalSalesNum', index: 'totalSalesNum', label: '<spring:message code="sb.total.sales" />',width:120,sortable : false},
        {
            name: 'istatus',
            index: 'ISTATUS',
            label: '<spring:message code="rm.commodity.status" />',
            formatter: "select",
            editoptions: {value: '10:<spring:message code="sb.on.sale" />;20:<spring:message code="sb.lower.shelf" />'}
            , width: 100
        },
//                {
//                    name: 'taddTime', index: 'TADD_TIME', label: '<spring:message code="sb.add.date" />', formatter: function (value) {
//                    return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
//                }
//                },
//                {
//                    name: 'tupdateTime', index: 'TUPDATE_TIME', label: '<spring:message code="sb.modified.date" />', formatter: function (value) {
//                    return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
//                }
//                },
        {name: "process",title:false, index: "process", label: '<spring:message code="main.operation" />', sortable: false, frozen: true, width: 100}
    ];
    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            var data = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = '';
                <%--<shiro:hasPermission name="DEVICE_COMMODITY_VIEW">--%>
                <%--str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"viewMethod('"--%>
                <%--+ cl + "')\"><spring:message code='sp.brand.view' /></a> | ";--%>
                <%--</shiro:hasPermission>--%>
                if (data[i].istatus == 20) {
                    <%--<shiro:hasPermission name="DEVICE_COMMODITY_SHELF">--%>
                    <%--str += "<img src='${staticSource}/resources/images/oper_icon/shelf.png' class=\"oper_icon\" onclick=\"shelf('"--%>
                        <%--+ cl + "')\" title='<spring:message code="rm.upper.shelf" />' alt='<spring:message code="rm.upper.shelf" />'  /> | ";--%>
                    <%--</shiro:hasPermission>--%>
                }else if (data[i].istatus == 10) {
                    <shiro:hasPermission name="DEVICE_COMMODITY_DROPOFF">
                    str += "<img src='${staticSource}/resources/images/oper_icon/drop_off.png' class=\"oper_icon\" onclick=\"dropoff('"
                        + cl + "')\" title='<spring:message code='sb.lower.shelf'/>' alt='<spring:message code='sb.lower.shelf'/>'  /> | ";
                    </shiro:hasPermission>
                }
                $("#gridTable").jqGrid('setRowData',
                    cl, {
                        process: str.substr(0, str.lastIndexOf(" | "))
                    });
            }
        }, 0);
    }
    $(function () {
        var config = {
            sortname: "A.ISTATUS=10",
            multiselect:false,
            sortorder: "desc"
        };
        initTable(ctx + "/device/commodity/queryData",colModel,tableCallBack,config);
    });

    initBtnByForm2();

//    function viewMethod(sid) {
//        showLayer('<spring:message code="main.details" />', ctx + '/device/commodity/toView?sid=' + sid, {area: ['90%', '80%']});
//    }
    function shelf(sid) {
        confirmOperTip('<spring:message code="sb.make.sure.you.want.to.put.this.item.on.the.shelf" />?', ctx + "/device/commodity/shelf", {checkboxId: sid});
    }
    function dropoff(sid) {
        confirmOperTip('<spring:message code="sb.make.sure.to.remove.the.item" />?', ctx + "/device/commodity/dropoff", {checkboxId: sid});
    }
</script>
</body>
</html>


