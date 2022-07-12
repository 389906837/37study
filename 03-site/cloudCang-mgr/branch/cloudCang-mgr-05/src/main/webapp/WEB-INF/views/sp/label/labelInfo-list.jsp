<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>商品标签列表</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code="sp.label.product.label.management" /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-input-inline">
                                    <input type="text" name="smerchantCode" id="smerchantCode" value="" placeholder='<spring:message code="main.merchant.number" />...' class="layui-input">
                                </div>
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantName" id="merchantName" value="" placeholder='<spring:message code="main.merchant.name" />...' class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-input-inline">
                                <input type="text" name="sname" id="sname" value="" placeholder='<spring:message code="sp.commodityInfo.label.name" />...' class="layui-input">
                            </div>
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
                            <shiro:hasPermission name="SP_LABEL_INFO_ADD">
                            <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"><i class="layui-icon"></i><spring:message code="main.add" /></button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="SP_LABEL_INFO_DELETE">
                            <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i><spring:message code="main.delete" /></button>
                            </shiro:hasPermission>
                            <%--<button class="layui-btn layui-btn-sm" data-type="testWebSocket"><i class="layui-icon"></i><spring:message code="sp.label.test" /></button>--%>
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
    var colModel =[
        {name: 'id', index: 'id', hidden: true},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode',label : '<spring:message code="main.merchant.number" />',index: 'SMERCHANT_CODE',width:180},
        {name: 'merchantName',label : '<spring:message code="main.merchant.name" />',index: 'merchantName',sortable : false,width:330},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'smerchantCode', index: 'smerchant_code', hidden: true},
        </c:if>
        {name: 'sname', index: 'SNAME', label: '<spring:message code="sp.commodityInfo.label.name" />',width:170},
        {name: 'isort', index: 'ISORT', label: '<spring:message code="main.sort" />',width:140},

        {   name: 'iisParent',
            index: 'IIS_PARENT',
            label: '<spring:message code="sp.label.whether.subtags.can.be.generated" />',
            formatter: "select",
            editoptions: {value: '0:<spring:message code="main.no" />;1:<spring:message code="main.yes" />'},
            width:290},
        {name: 'taddTime', index: 'TADD_TIME', label: '<spring:message code="sp.brand.add.date" />',formatter: function (value) {
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        },width:220},
        {name: "process",title:false, index: "process", label: '<spring:message code="main.operation" />', sortable: false, frozen: true, width: 220}
    ];

    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
            var data = $("#gridTable").jqGrid('getRowData');
            var merchantCode = "${SESSION_KEY_SMERCHANT_CODE}";
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = '';
                <%--<shiro:hasPermission name="SP_LABEL_INFO_VIEW">
                str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"viewMethod('"
                    + cl + "')\"><spring:message code="sp.brand.view" /></a> | ";
                </shiro:hasPermission>--%>
                if (merchantCode == data[i].smerchantCode) {
                <shiro:hasPermission name="SP_LABEL_INFO_EDIT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                        + cl + "')\" title=\"<spring:message code='main.edit' />\" alt=\"<spring:message code='main.edit' />\" /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="SP_LABEL_INFO_DELETE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                        + cl + "')\" title='<spring:message code="main.delete" />' alt='<spring:message code="main.delete" />'  /> | ";
                </shiro:hasPermission>
                $("#gridTable").jqGrid('setRowData',
                    cl, {
                        process: str.substr(0, str.lastIndexOf(" | "))
                    });
                }
            }
        }, 0);
    }

    $(function () {
        var config = {
            sortname: "tadd_Time",
            sortorder: "desc"
        };
        initTable(ctx+"/commodity/labelInfo/queryData",colModel,tableCallBack,config);
    });
    var initBtnConfig = {
        addUrl: ctx+'/commodity/labelInfo/toAdd',
        addTitle: '<spring:message code="sp.label.add.product.tag.information" />',
        addFn:showLayerOne
    };
    initBtnByForm4(initBtnConfig);

//    function viewMethod(sid) {
//        showLayerOne('<spring:message code="main.details" />', ctx + '/commodity/labelInfo/view?sid=' + sid);
//    }
    function editMethod(sid) {
        showLayerOne("<spring:message code='main.edit' />", ctx + '/commodity/labelInfo/toEdit?sid=' + sid);
    }
    function delMethod(sid) {
        confirmDelTip('<spring:message code="main.delete.confirm" />', ctx + "/commodity/labelInfo/delete", {checkboxId: sid});
    }
</script>
</body>
</html>



