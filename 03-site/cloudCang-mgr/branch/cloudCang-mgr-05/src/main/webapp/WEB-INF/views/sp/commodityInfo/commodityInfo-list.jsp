<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>商品信息列表</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
<style>
    .ui-jqgrid tr.jqgrow td {
        white-space: normal !important;
        text-align: center;
        height: auto;
    }
</style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code="sp.commodityInfo.commodity.information.management" /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder='<spring:message code="main.product.number" />...' class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sname" id="sname" value="" placeholder='<spring:message code="main.product.name" />...' class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="svrCode" id="svrCode" value="" placeholder='<spring:message code="sp.commodityInfo.product.visual.identification.number" />...' class="layui-input">
                            </div>
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-input-inline">
                                    <input type="text" name="smerchantCode" id="smerchantCode" value="" placeholder='<spring:message code="main.merchant.number" />...' class="layui-input">
                                </div>
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantName" id="merchantName" value="" placeholder='<spring:message code="main.merchant.name" />...' class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-input-inline">
                                <select class="form-control" name="istoreDevice" id="istoreDevice">
                                    <option value=""><spring:message code="sp.commodityInfo.equipment.type" /></option>
                                    <option value="10"><spring:message code="sp.commodityInfo.vision" /></option>
                                    <option value="20">rfid<spring:message code="sp.commodityInfo.radio.frequency" /></option>
                                    <option value="30"><spring:message code="sp.commodityInfo.traditional" /></option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control" name="istatus" id="istatus">
                                    <option value=""><spring:message code="sp.commodityInfo.upper.and.lower.shelf.status" /></option>
                                    <option value="10"><spring:message code="index.shelf" /></option>
                                    <option value="20"><spring:message code="index.obtained" /></option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="ssmallCategoryName" id="ssmallCategoryName" value=""
                                       placeholder='<spring:message code="sp.commodityInfo.small.class.name" />...' class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sbrandName" id="sbrandName" value=""
                                       placeholder='<spring:message code="sp.brand.brand.name" />...' class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="slabelName" id="slabelName" value=""
                                       placeholder='<spring:message code="sp.commodityInfo.product.label" />...' class="layui-input">
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
                            <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"><i class="layui-icon"></i><spring:message code="main.add" /></button>
                            <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i><spring:message code="main.delete" /></button>
                            <%--<shiro:hasPermission name="COMMODITY_INFO_GROUP_ADDBATCH">
                            <button class="layui-btn layui-btn-sm" data-type="batchManage"><i class="layui-icon">&#xe63c;</i><spring:message code="sp.commodityBatch.batch" /></button>
                            </shiro:hasPermission>--%>
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
<script src="${staticSource}/resources/js/tableExport.min.js"></script>
<script src="${staticSource}/resources/js/tableExportComm-${currentLanguage}.js"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    var colModel =[
        {name: 'id', index: 'id', hidden: true},
        {   name: 'scommodityImg',
            index: 'SCOMMODITY_IMG',
            label: '<spring:message code="sp.category.product.image" />',
            formatter: function (value) {
                if (isEmpty(value)){
                    return "<img style=\"width: auto; height:auto; max-width:50px; max-height:50px;\" src=\"${staticSource}"+"/resources/images/37cang.png" +"\"/>";
                }else{
                    return "<img style=\"width: auto; height:auto; max-width:50px; max-height:50px;\" src=\"${dynamicSource}"+value +"\"/>";
                }
            },
            width:180
        },
        {name: 'scode', index: 'SCODE', label: '<spring:message code="main.product.number" />',width:160},
        {name: 'commodityFullName', index: 'SNAME', label: '<spring:message code="main.product.name" />',width:160},
        {name: 'svrCode', index: 'SVR_CODE', label: '<spring:message code="sp.commodityInfo.product.visual.identification.number" />',width:160},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode',label : '<spring:message code="main.merchant.number" />',index: 'smerchant_code',width:160},
        {name: 'merchantName',label : '<spring:message code="main.merchant.name" />',index: 'sname',width:140,sortable : false},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'smerchantCode', index: 'smerchant_code', hidden: true},
        </c:if>
        {   name: 'istoreDevice',
            index: 'ISTORE_DEVICE',
            label: '<spring:message code="sp.commodityInfo.equipment.type" />',
            formatter: "select",
            editoptions: {value: '10:<spring:message code="sp.commodityInfo.vision" />;20:rfid<spring:message code="main.device" />;30:<spring:message code="sp.commodityInfo.other" />'},
            width:140
        },
        {name: 'fsalePrice', index: 'FSALE_PRICE', label: '<spring:message code="sp.commodityInfo.sale.price" />',width:120},
        {name: 'fcostPrice', index: 'FCOST_PRICE', label: '<spring:message code="sp.commodityBatch.cost.price" />',width:130},
        {name: 'ssmallCategoryName', index: 'SSMALL_CATEGORY_NAME', label: '<spring:message code="sp.commodityInfo.small.class.name" />',width:145},
        {name: 'sbrandName', index: 'SBRAND_NAME', label: '<spring:message code="sp.brand.brand.name" />',width:120},
        {name: 'slabelName', index: 'SLABEL_NAME', label: '<spring:message code="sp.commodityInfo.product.label" />',width:125},
//        {name: 'taddTime', index: 'TADD_TIME', label: '<spring:message code="sp.brand.add.date" />',formatter: function (value) {
//            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
//        },width:120},
        {   name: 'istatus',
            index: 'ISTATUS',
            label: '<spring:message code="sp.commodityInfo.shelf.status" />',
            formatter: function(value, options, rowObject) { return '<span id="istatus_'+rowObject.id+'" data="'+value+'" class="' + (({"10":"istatus-normal","20":"istatus-danger"})[value]) + '">' +  (({"10":'● <spring:message code="index.shelf" />',"20":'● <spring:message code="index.obtained" />'})[value])+ '</span>' },
            width:130
        },
        {name: "process", title:false,index: "process", label: '<spring:message code="main.operation" />', sortable: false, frozen: true, width: 200}
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
                <shiro:hasPermission name="COMMODITY_INFO_VIEW">
                    str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"viewMethod('"
                        + cl + "')\" title=\"<spring:message code='sh.view' />\" alt=\"<spring:message code='sh.view' />\" /> | ";
                </shiro:hasPermission>
                if (merchantCode == data[i].smerchantCode) {
                <shiro:hasPermission name="COMMODITY_INFO_EDIT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                        + cl + "')\" title=\"<spring:message code='main.edit' />\" alt=\"<spring:message code='main.edit' />\" /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="COMMODITY_INFO_DELETE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                        + cl + "')\" title='<spring:message code="main.delete" />' alt='<spring:message code="main.delete" />'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="SINGLE_COMMODITY_INFO_MANAGE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/batch.png' class=\"oper_icon\" onclick=\"addBatchMethod('"
                        + cl + "')\" title='<spring:message code="sp.commodityInfo.batch.management" />' alt='<spring:message code="sp.commodityInfo.batch.management" />'  /> | ";
                </shiro:hasPermission>
                if ($('#istatus_'+cl).attr('data') == 10) {
                    <shiro:hasPermission name="COMMODITY_INFO_SHELF">
                    str += "<img src='${staticSource}/resources/images/oper_icon/drop_off.png' class=\"oper_icon\" onclick=\"dropOff('"
                        + cl + "')\" title='<spring:message code="sm.lower.shelf" />' alt='<spring:message code="sm.lower.shelf" />'  /> | ";
                    </shiro:hasPermission>
                }
                if ($('#istatus_'+cl).attr('data') == 20) {
                    <shiro:hasPermission name="COMMODITY_INFO_DROPOFF">
                    str += "<img src='${staticSource}/resources/images/oper_icon/enabled.png' class=\"oper_icon\" onclick=\"shelf('"
                        + cl + "')\" title='<spring:message code="main.enable" />' alt='<spring:message code="main.enable" />'  /> | ";
                    </shiro:hasPermission>
                }
                }
                $("#gridTable").jqGrid('setRowData',
                    cl, {
                        process: str.substr(0, str.lastIndexOf(" | "))
                    });
            }
//            isExport = true;
//            if(isExport) {//判断是否导出
//                addExportBtn('<spring:message code="sp.commodityInfo.product.list" />');
//            }
        }, 0);
    }

    $(function () {
        var config = {
            sortname: "A.ISTATUS=10",
            sortorder: "desc"
        };
        initTable(ctx+"/commodity/commodityInfo/queryData",colModel,tableCallBack,config);
        <shiro:hasPermission name="COMMODITY_DOWNLOADEXCEL">
        addExportBtnByUrl("${ctx}/commodity/downloadExcel");
        </shiro:hasPermission>
    });

    var initBtnConfig = {
        addUrl: ctx+'/commodity/commodityInfo/toAdd',
        deleteUrl: ctx+"/commodity/commodityInfo/delete",
        addTitle: '<spring:message code="sp.commodityInfo.add.product.information" />',
        addFn:showLayerMedium
    };
    initBtnByForm4(initBtnConfig);


    layui.use('form', function () {
        var $ = layui.$, active = {
            batchManage: function () {//批次
                var sid = $("#gridTable").jqGrid('getGridParam', 'selarrrow');
                if (isEmpty(sid)) {
                    alertDel('<spring:message code="sp.commodityInfo.please.select.the.item.you.want.to.operate.first" />');
                    return;
                }
                showLayerMax('<spring:message code="sp.commodityInfo.make.sure.to.add.batch.information.for.the.item" />?', ctx + '/commodity/commodityBatch/manageCommodityBatch?sid=' + sid, {area: ['60%', '70%']});
            }
        };
        $('.layui-form .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

        <c:if test="${!empty method}">
            <c:if test="${method eq 'add'}">
                if($.isFunction(initBtnConfig.addFn)) {
                    initBtnConfig.addFn(initBtnConfig.addTitle, initBtnConfig.addUrl, initBtnConfig.addModelConfig);
                } else {
                    showLayer(initBtnConfig.addTitle, initBtnConfig.addUrl, initBtnConfig.addModelConfig)
                }
            </c:if>
        </c:if>
    });
    function viewMethod(sid) {
        showLayerMedium('<spring:message code="sp.commodityBatch.details" />', ctx + '/commodity/commodityInfo/view?sid=' + sid);
    }
    function editMethod(sid) {
        showLayerMedium('<spring:message code="main.edit" />', ctx + '/commodity/commodityInfo/toEdit?sid=' + sid);
    }
    function addBatchMethod(sid) {
//        showLayer('<spring:message code="sp.commodityBatch.add.product.batch.information" />', ctx + '/commodity/commodityBatch/toAddBatch?sid=' + sid, {area: ['35%', '80%']});
        showLayerMax('<spring:message code="sp.commodityBatch.add.product.batch.information" />', ctx + '/commodity/singleCommodityBatch/list?sid=' + sid);
    }
    function delMethod(sid) {
        confirmDelTip('<spring:message code="main.delete.confirm" />', ctx + "/commodity/commodityInfo/delete", {checkboxId: sid});
    }
    function shelf(sid) {
        confirmOperTip('<spring:message code="sp.commodityInfo.make.sure.you.want.to.enable.this.item" />?', ctx + "/commodity/commodityInfo/shelf", {checkboxId: sid});
    }
    function dropOff(sid) {
        confirmOperTip('<spring:message code="sp.commodityInfo.make.sure.to.remove.the.item" />?', ctx + "/commodity/commodityInfo/dropOff", {checkboxId: sid});
    }

</script>

</body>
</html>



