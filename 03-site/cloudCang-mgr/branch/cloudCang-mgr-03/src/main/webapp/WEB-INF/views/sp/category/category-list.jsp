<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>商品分类列表</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code="sp.category.commodity.classification.management" /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <input type="hidden" id="categoryId" name="categoryId" value="${categoryId}" />
                        <input type="hidden" id="sparentId" name="sparentId" value="${sparentId}" />
                        <%--<div class="layui-form-item">--%>
                            <%--<div class="layui-input-inline">--%>
                                <%--<input type="text" name="sname" id="sname" value="" placeholder='<spring:message code="sp.category.category.name" />...' class="layui-input">--%>
                            <%--</div>--%>
                            <%--<c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">--%>
                                <%--<div class="layui-input-inline">--%>
                                    <%--<input type="text" name="smerchantCode" id="smerchantCode" value="" placeholder='<spring:message code="main.merchant.number" />...' class="layui-input">--%>
                                <%--</div>--%>
                                <%--<div class="layui-input-inline">--%>
                                    <%--<input type="text" name="merchantName" id="merchantName" value="" placeholder='<spring:message code="main.merchant.name" />...' class="layui-input">--%>
                                <%--</div>--%>
                            <%--</c:if>--%>
                            <%--<div class="layui-input-inline">--%>
                                <%--<select class="form-control" name="iisParent" id="iisParent">--%>
                                    <%--<option value=""><spring:message code="sp.category.parent.class" /></option>--%>
                                    <%--<option value="0"><spring:message code="main.no" /></option>--%>
                                    <%--<option value="1"><spring:message code="main.yes" /></option>--%>
                                <%--</select>--%>
                            <%--</div>--%>
                            <%--<div class="layui-input-inline">--%>
                                <%--<select class="form-control" name="iisHot" id="iisHot">--%>
                                    <%--<option value=""><spring:message code="sp.category.popular.categories" /></option>--%>
                                    <%--<option value="0"><spring:message code="main.no" /></option>--%>
                                    <%--<option value="1"><spring:message code="main.yes" /></option>--%>
                                <%--</select>--%>
                            <%--</div>--%>
                            <%--<div class="layui-input-inline">--%>
                                <%--<select class="form-control" name="iisDisplay" id="iisDisplay">--%>
                                    <%--<option value=""><spring:message code="sp.category.external.display" /></option>--%>
                                    <%--<option value="0"><spring:message code="main.no" /></option>--%>
                                    <%--<option value="1"><spring:message code="main.yes" /></option>--%>
                                <%--</select>--%>
                            <%--</div>--%>
                            <%--<div class="layui-input-inline">--%>
                                <%--<button class="layui-btn layui-btn" id="searchBtn" data-type="query">--%>
                                    <%--<i class="layui-icon">&#xe615;</i><spring:message code="main.query" />--%>
                                <%--</button>--%>
                                <%--<button class="layui-btn layui-btn layui-btn-primary" data-type="reset">--%>
                                    <%--<i class="layui-icon">&#x1006;</i><spring:message code="main.clear" />--%>
                                <%--</button>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <div class="layui-form-item">
                            <shiro:hasPermission name="SP_CATEGORY_ADD">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"><i class="layui-icon"></i><spring:message code="main.add" /></button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="SP_CATEGORY_DELETE">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i><spring:message code="main.delete" /></button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="SP_CATEGORY_INIT">
<%--
                                <button class="layui-btn layui-btn-sm layui-btn-primary" onclick="initCategory()" ><i class="layui-icon layui-icon-refresh"></i><spring:message code="sp.category.initialization.data" /></button>
--%>
                                <button class="layui-btn layui-btn-sm layui-btn-primary" onclick="initCategory()" ><i class="layui-icon layui-icon-refresh"></i><spring:message code="sp.category.initialization.data" />
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
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    /**
     * <spring:message code="sp.category.reload.the.form" />
     */
    function reloadData(categoryId,sparentId) {
        if (!isEmpty(categoryId)) {
            $("#categoryId").val(categoryId);
        }
        if (!isEmpty(sparentId)) {
            $("#sparentId").val(sparentId);
        } else {
            $("#sparentId").val("");
        }
        resetReloadGrid();
    }
    var colModel= [
        {name: 'id', index: 'id', hidden: true},
        {name: 'scode', index: 'SCODE', label: '<spring:message code="sp.category.category.id" />', width: 150},
        {name: 'sname', index: 'SNAME', label: '<spring:message code="sp.category.category.name" />',width:140},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode',label : '<spring:message code="main.merchant.number" />',index: 'SMERCHANT_CODE',width:160},
        {name: 'merchantName',label : '<spring:message code="main.merchant.name" />',index: 'merchantName',sortable : false,width:300},
        </c:if>
        {   name: 'iisParent',
            index: 'IIS_PARENT',
            label: '<spring:message code="sp.category.parent.class" />',
            formatter: "select",
            editoptions: {value: '0:<spring:message code="main.no" />;1:<spring:message code="main.yes" />'},
            width:110},
        {   name: 'iisHot',
            index: 'IIS_HOT',
            label: '<spring:message code="sp.category.popular.categories" />',
            formatter: "select",
            editoptions: {value: '0:<spring:message code="main.no" />;1:<spring:message code="main.yes" />'},
            width:120},
        {   name: 'iisDisplay',
            index: 'IIS_DISPLAY',
            label: '<spring:message code="sp.category.external.display" />',
            formatter: "select",
            editoptions: {value: '0:<spring:message code="main.no" />;1:<spring:message code="main.yes" />'},
            width:130},
//        {name: 'taddTime', index: 'TADD_TIME', label: '<spring:message code="sp.brand.add.date" />',formatter: function (value) {
//            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
//        },width:140},
        {name: 'isort', index: 'ISORT', label: '<spring:message code="main.sort" />',width:80},
        {name: "process",title:false, index: "process", label: '<spring:message code="main.operation" />', sortable: false, frozen: true, width: 200}
    ];
    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function(){
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
            var ids = $("#gridTable").jqGrid('getDataIDs');
            for ( var i = 0; i < ids.length; i++) {
                var cl = ids[i];
                var str = "";
                <shiro:hasPermission name="SP_CATEGORY_VIEW">
                str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"viewMethod('"
                    + cl + "')\" title='<spring:message code="sp.brand.view" />' alt='<spring:message code="sp.brand.view" />'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="SP_CATEGORY_EDIT">
                str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                    + cl + "')\" title='<spring:message code="sp.brand.edit" />' alt='<spring:message code="sp.brand.edit" />'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="SP_CATEGORY_DELETE">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='<spring:message code="main.delete" />' alt='<spring:message code="main.delete" />'  /> | ";
                </shiro:hasPermission>
                $("#gridTable").jqGrid('setRowData',
                    ids[i], {
                        process : str.length > 0 ? str.substr(0,str.lastIndexOf(" | ")) : ""
                    });
            }
        }, 0);
    }
    $(function () {
        var config = {
            sortname: "isort",
            sortorder: "asc",
        };
        initTable(ctx + "/commodity/getCategoryByCategoryId", colModel, tableCallBack, config);
    });

    layui.use('form', function(){
        var $ = layui.$, active = {
            add: function(){//添加
                var categoryId = $("#categoryId").val();
                var param = $("#categoryId").val() + "-//-" + $("#sparentId").val();
                if (isEmpty(categoryId)) {
                    alertDel('<spring:message code="sp.category.please.select.the.parent.or.main.category.first" />');
                    return;
                }
                showLayerMin('<spring:message code="sp.category.add.product.category.information" />', ctx+'/commodity/category/toAdd?param='+param);
            },delete: function(){//删除
                var sid = $("#gridTable").jqGrid('getGridParam','selarrrow');
                if(isEmpty(sid)) {
                    alertDel('<spring:message code="sp.category.please.select.the.data.you.want.to.delete.first" />');
                    return;
                }
                confirmDelTip('<spring:message code="sp.category.are.you.sure.you.want.to.delete.all.data" />?',ctx+"/commodity/category/delete",{checkboxId:sid.join(",")});
            }
        };
        $('.layui-form .layui-btn').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });

    function viewMethod(sid) {
        showLayerMin('<spring:message code="sp.category.product.classification.details" />', ctx + '/commodity/category/view?sid=' + sid);
    }
    function editMethod(sid) {
        showLayerMin('<spring:message code="sp.category.edit.product.classification.information" />', ctx + '/commodity/category/toEdit?sid=' + sid);
    }
    function delMethod(sid) {
        confirmDelTip('<spring:message code="sp.category.are.you.sure.you.want.to.delete.all.data" />?', ctx + "/commodity/category/delete", {checkboxId: sid});
    }
    function initCategory() {
        var sid = "";
        confirmDelTip('<spring:message code="sp.category.initialization.data" />',ctx+"/commodity/category/initCategory",{checkboxId:sid});
    }

</script>
</body>
</html>



