<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>视觉商品列表</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code="vrsku.visual.merchandise.management" /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder='<spring:message code="main.product.number" />...'
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="scommodityName" id="scommodityName" value=""
                                       placeholder='<spring:message code="main.product.name" />...' class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="svrCode" id="svrCode" value="" placeholder='<spring:message code="vrsku.visual.identity.number" />...'
                                       class="layui-input">
                            </div>

                            <div class="layui-input-inline">
                                <input type="text" name="ssmallCategoryName" id="ssmallCategoryName" value=""
                                       placeholder="<spring:message code='sp.commodityInfo.small.class.name' />..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sbrandName" id="sbrandName" value="" placeholder='<spring:message code="vrsku.brand.name" />...'
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control" name="istatus" id="istatus">
                                    <option value=""><spring:message code="vrsku.product.status" /></option>
                                    <option value="10"><spring:message code="vrsku.on.sale" /></option>
                                    <option value="20"><spring:message code="vrsku.discontinue.use" /></option>
                                </select>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <select class="form-control" name="ionlineStatus" id="ionlineStatus">
                                    <option value=""><spring:message code="vrsku.on.state" /></option>
                                    <option value="10"><spring:message code="vrsku.draft" /></option>
                                    <option value="20"><spring:message code="vrsku.has.been.on" /></option>
                                </select>
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query">
                                    <i class="layui-icon">&#xe615;</i><spring:message code="main.query" />
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset">
                                    <i class="layui-icon">&#x1006;</i><spring:message code="main.clear" />
                                </button>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <shiro:hasPermission name="SP_SKU_ADD">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"><i
                                        class="layui-icon"></i><spring:message code="main.add" />
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="SP_SKU_DELETE">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i><spring:message code="main.delete" />
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="SP_SKU_BATCH_ADD">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="batchImportVrCommodity"><i
                                        class="layui-icon"></i><spring:message code="vrsku.batch.import" />
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
    var colModel = [
        {name: 'id', index: 'id', hidden: true},
        {
            name: 'scommodityImg',
            index: 'SCOMMODITY_IMG',
            label: "Product Picture",
            formatter: function (value) {
                if (isEmpty(value)){
                    return "<img style=\"width: auto; height:auto; max-width:50px; max-height:50px;\" src=\"${staticSource}"+"/resources/images/37cang.png" +"\"/>";
                }else{
                    return "<img style=\"width: auto; height:auto; max-width:50px; max-height:50px;\" src=\"${dynamicSource}"+value +"\"/>";
                }
            },
            width:150
        },
        {name: 'scode', index: 'SCODE', label: '<spring:message code="main.visual.product.number" />',width:180},
        {name: 'commodityFullName', index: 'SCOMMODITY_NAME', label: '<spring:message code="main.product.name" />',width:250},
        {name: 'svrCode', index: 'SVR_CODE', label: "<spring:message code='vrsku.visual.identity.number' />",width:190},
        {name: 'ssmallCategoryName', index: 'SSMALL_CATEGORY_NAME', label: "<spring:message code='vrsku.small.class.name' />",width:150},
        {name: 'sbrandName', index: 'SBRAND_NAME', label: "<spring:message code='sp.brand.brand.name' />",width:120},
        {   name: 'istatus',
            index: 'ISTATUS',
            label: "<spring:message code='sm.product.status' />",
            formatter: "select",
            editoptions: {value: '10:<spring:message code='vrsku.on.sale' />;20:<spring:message code='tpmanage.disable' />'},
            width:140
        },
        {   name: 'ionlineStatus',
            index: 'IONLINE_STATUS',
            label: "<spring:message code='sp.commodityInfo.shelf.status' />",
            formatter: "select",
            editoptions: {value: '10:<spring:message code='vrsku.draft' />;20:<spring:message code="index.shelf" />'},
            width:110
        },
        {name: 'taddTime', index: 'TADD_TIME', label: '<spring:message code="sp.brand.add.date" />',formatter: function (value) {
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        },width:150},
        {name: "process",title:false, index: "process", label: '<spring:message code="main.operation" />', sortable: false, frozen: true, width: 350}
    ];

    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
            var data = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = '';
                <shiro:hasPermission name="SP_SKU_VIEW">
                    str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"viewMethod('"
                        + cl + "')\" title=\"<spring:message code='sh.view' />\" alt=\"<spring:message code='sh.view' />\" /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="SP_SKU_EDIT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                        + cl + "')\" title=\"<spring:message code='main.edit' />\" alt=\"<spring:message code='main.edit' />\" /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="SP_SKU_SYNCHRONIZE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/sync_vrCommodity.png' class=\"oper_icon\" onclick=\"synchMethod('"
                        + cl + "')\" title='<spring:message code='vr.synchronize.visual.product.information' />' alt='<spring:message code='vr.synchronize.visual.product.information' />'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="SP_SKU_DELETE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                        + cl + "')\" title='<spring:message code="main.delete" />' alt='<spring:message code="main.delete" />'  /> | ";
                </shiro:hasPermission>
                if (data[i].istatus == 10) {
                    <shiro:hasPermission name="SP_SKU_DISABLE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/disable.png' class=\"oper_icon\" onclick=\"skuDisableMethod('"
                        + cl + "')\" title='<spring:message code='sh.disable' />' alt='<spring:message code='sh.disable' />'  /> | ";
                    </shiro:hasPermission>
                } else if (data[i].istatus == 20){
                    <shiro:hasPermission name="SP_SKU_ENABLE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/enabled.png' class=\"oper_icon\" onclick=\"skuEnableMethod('"
                        + cl + "')\" title='<spring:message code="main.enable" />' alt='<spring:message code="main.enable" />'  /> | ";
                    </shiro:hasPermission>
                }
                if (data[i].ionlineStatus == 10 || data[i].ionlineStatus == 30) {
                    <shiro:hasPermission name="SP_SKU_SHELF">
                    str += "<img src='${staticSource}/resources/images/oper_icon/shelf.png' class=\"oper_icon\" onclick=\"skuShelfMethod('"
                    + cl + "')\" title='<spring:message code="rm.upper.shelf" />' alt='<spring:message code="rm.upper.shelf" />' /> | ";
                    </shiro:hasPermission>
                }
                <%--else if (data[i].ionlineStatus == 20){--%>
                    <%--<shiro:hasPermission name="SP_SKU_DROPOFF">--%>
                    <%--str += "<img src='${staticSource}/resources/images/oper_icon/drop_off.png' class=\"oper_icon\" onclick=\"skuDropoffMethod('"--%>
                        <%--+ cl + "')\" title='下架' alt='下架'  /> | ";--%>
                    <%--</shiro:hasPermission>--%>
                <%--}--%>
                $("#gridTable").jqGrid('setRowData',
                    cl, {
                        process: str.substr(0, str.lastIndexOf(" | "))
                    });
            }
        }, 0);
    }

    $(function () {
        var config = {
            sortname: "B.ISTATUS=10",
            sortorder: "desc"
        };
        initTable(ctx+"/commoditySku/list/queryData",colModel,tableCallBack,config);
    });
    var initBtnConfig = {
        addUrl: ctx+'/commoditySku/toAdd',
        deleteUrl: ctx+"/commoditySku/delete",
        addTitle: '<spring:message code='vr.add.visual.product.information' />',
        addFn:showLayerMedium
    };
    initBtnByForm4(initBtnConfig);

    layui.use('form', function () {
        var $ = layui.$, active = {
            batchImportVrCommodity : function () {
                showLayerOne("<spring:message code='vr.import.visual.product.information.in.batches' />", ctx + "/commoditySku/toBatchImportVr");
            }
        };
        $('.layui-form .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });
    function viewMethod(sid) {
        showLayerMedium('<spring:message code="sp.visual.product.details" />', ctx + '/commoditySku/view?sid=' + sid);
    }
    function editMethod(sid) {
        showLayerMedium("<spring:message code='vr.edit.visual.product.information' />", ctx + '/commoditySku/toEdit?sid=' + sid);
    }
    function synchMethod(sid) {
        confirmOperTip("<spring:message code='vr.determine.synchronized.visual.product.information.to.the.product.list' />", ctx + "/commoditySku/synchronize" , {checkboxId: sid});
    }
    function delMethod(sid) {
        confirmDelTip('<spring:message code="main.delete.confirm" />', ctx + "/commoditySku/delete", {checkboxId: sid});
    }
    function skuDisableMethod(sid) {
        confirmOperTip("<spring:message code='vr.are.you.sure.you.want.to.disable.this.item' />", ctx + "/commoditySku/skuDisable", {checkboxId: sid});
    }
    function skuEnableMethod(sid) {
        confirmOperTip("<spring:message code='vr.make.sure.you.want.to.enable.this.item' />", ctx + "/commoditySku/skuEnable", {checkboxId: sid});
    }
    function skuShelfMethod(sid) {
        confirmOperTip("<spring:message code='sb.make.sure.you.want.to.put.this.item.on.the.shelf' />", ctx + "/commoditySku/skuShelf", {checkboxId: sid});
    }
    function skuDropoffMethod(sid) {
        confirmOperTip("<spring:message code='sb.make.sure.to.remove.the.item' />", ctx + "/commoditySku/skuDropoff", {checkboxId: sid});
    }
</script>
</body>
</html>



