<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>商品品牌列表</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code="sp.brand.commodity.brand.management" /></h5>
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
                                <input type="text" name="sname" id="sname" value="" placeholder='<spring:message code="sp.brand.brand.name" />...' class="layui-input">
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
                            <shiro:hasPermission name="SP_BRAND_INFO_ADD">
                            <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"><i class="layui-icon"></i><spring:message code="main.add" /></button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="SP_BRAND_INFO_DELETE">
                            <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i><spring:message code="main.delete" /></button>
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
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    var colModel =[
        {name: 'id', index: 'id', hidden: true},
        {name: 'sname', index: 'SNAME', label: '<spring:message code="sp.brand.brand.name" />',width:230},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode',label : '<spring:message code="main.merchant.number" />',width:230,index: 'SMERCHANT_CODE'},
        {name: 'merchantName',label : '<spring:message code="main.merchant.name" />',index: 'sname',width:360,sortable : false},
        </c:if>
        {name: 'isort', index: 'ISORT', label: '<spring:message code="main.sort" />',width:180},
//                {name: 'slogo', index: 'SLOGO', label: '<spring:message code="sp.brand.brand.logo" />',width:120},
        {name: 'taddTime', index: 'TADD_TIME', label: '<spring:message code="sp.brand.add.date" />',formatter: function (value) {
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        },width:220},
        {name: "process", title:false,index: "process", label: '<spring:message code="main.operation" />', sortable: false, frozen: true, width: 330}
    ];
    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
            var ids = $("#gridTable").jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                var cl = ids[i];
                var str = '';
                <shiro:hasPermission name="SP_BRAND_INFO_VIEW">
                    str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"viewMethod('"
                        + cl + "')\" title='<spring:message code="sp.brand.view" />' alt='<spring:message code="sp.brand.view" />'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="SP_BRAND_INFO_EDIT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                        + cl + "')\" title='<spring:message code="sp.brand.edit" />' alt='<spring:message code="sp.brand.edit" />'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="SP_BRAND_INFO_DELETE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                        + cl + "')\" title='<spring:message code="sp.brand.delete" />' alt='<spring:message code="sp.brand.delete" />'  /> | ";
                </shiro:hasPermission>
                $("#gridTable").jqGrid('setRowData',
                    ids[i], {
                        process: str.substr(0, str.lastIndexOf(" | "))
                    });
            }
        }, 0);
    }

    $(function () {
        var config = {
            sortname: "tadd_Time",
            sortorder: "desc"
        };
        initTable(ctx+"/commodity/brandInfo/queryData",colModel,tableCallBack,config);
    });
    var initBtnConfig = {
        addUrl: ctx+'/commodity/brandInfo/toAdd',
        deleteUrl: ctx+"/commodity/brandInfo/delete",
        addTitle: '<spring:message code="sp.brand.add.product.brand.information" />',
        addFn:showLayerMin
    };
    initBtnByForm4(initBtnConfig);

    function viewMethod(sid) {
        showLayerMin('<spring:message code="sp.brand.product.brand.details" />', ctx + '/commodity/brandInfo/view?sid=' + sid);
    }
    function editMethod(sid) {
        showLayerOne('<spring:message code="sp.brand.edit.product.brand.information" />', ctx + '/commodity/brandInfo/toEdit?sid=' + sid);
    }
    function delMethod(sid) {
        confirmDelTip('<spring:message code="sp.brand.are.you.sure.you.want.to.delete.all.data" />?', ctx + "/commodity/brandInfo/delete", {checkboxId: sid});
    }
</script>
</body>
</html>



