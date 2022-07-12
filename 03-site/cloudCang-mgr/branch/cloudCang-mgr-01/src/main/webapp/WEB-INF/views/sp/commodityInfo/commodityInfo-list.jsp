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
                    <h5>商品信息管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder="商品编号..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sname" id="sname" value="" placeholder="商品名称..." class="layui-input">
                            </div>
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-input-inline">
                                    <input type="text" name="smerchantCode" id="smerchantCode" value="" placeholder="商户编号..." class="layui-input">
                                </div>
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantName" id="merchantName" value="" placeholder="商户名称..." class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-input-inline">
                                <select class="form-control" name="istoreDevice" id="istoreDevice">
                                    <option value="">设备类型</option>
                                    <option value="10">视觉</option>
                                    <option value="20">rfid射频</option>
                                    <option value="30">传统</option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control" name="istatus" id="istatus">
                                    <option value="">上下架状态</option>
                                    <option value="10">已上架</option>
                                    <option value="20">已下架</option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="ssmallCategoryName" id="ssmallCategoryName" value=""
                                       placeholder="小类名称..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sbrandName" id="sbrandName" value=""
                                       placeholder="品牌名称..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="slabelName" id="slabelName" value=""
                                       placeholder="商品标签..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query">
                                    <i class="layui-icon">&#xe615;</i>查询
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset">
                                    <i class="layui-icon">&#x1006;</i>清除
                                </button>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"><i class="layui-icon"></i>添加</button>
                            <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i>删除</button>
                            <%--<shiro:hasPermission name="COMMODITY_INFO_GROUP_ADDBATCH">
                            <button class="layui-btn layui-btn-sm" data-type="batchManage"><i class="layui-icon">&#xe63c;</i>批次</button>
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
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<script src="${staticSource}/resources/js/tableExport.min.js"></script>
<script src="${staticSource}/resources/js/tableExportComm.js"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    var colModel =[
        {name: 'id', index: 'id', hidden: true},
        {   name: 'scommodityImg',
            index: 'SCOMMODITY_IMG',
            label: "商品图片",
            formatter: function (value) {
                if (isEmpty(value)){
                    return "<img style=\"width: auto; height:auto; max-width:50px; max-height:50px;\" src=\"${staticSource}"+"/resources/images/37cang.png" +"\"/>";
                }else{
                    return "<img style=\"width: auto; height:auto; max-width:50px; max-height:50px;\" src=\"${dynamicSource}"+value +"\"/>";
                }
            },
            width:80
        },
        {name: 'scode', index: 'SCODE', label: "商品编号",width:100},
        {name: 'commodityFullName', index: 'SNAME', label: "商品名称",width:100},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode',label : "商户编号",index: 'smerchant_code',width:120},
        {name: 'merchantName',label : "商户名称",index: 'sname',width:120,sortable : false},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'smerchantCode', index: 'smerchant_code', hidden: true},
        </c:if>
        {   name: 'istoreDevice',
            index: 'ISTORE_DEVICE',
            label: "设备类型",
            formatter: "select",
            editoptions: {value: '10:视觉;20:rfid设备;30:其他'},
            width:80
        },
        {name: 'fsalePrice', index: 'FSALE_PRICE', label: "销售价",width:80},
        {name: 'fcostPrice', index: 'FCOST_PRICE', label: "成本价",width:80},
        {name: 'ssmallCategoryName', index: 'SSMALL_CATEGORY_NAME', label: "小类名称",width:80},
        {name: 'sbrandName', index: 'SBRAND_NAME', label: "品牌名称",width:80},
        {name: 'slabelName', index: 'SLABEL_NAME', label: "商品标签",width:80},
//        {name: 'taddTime', index: 'TADD_TIME', label: "添加日期",formatter: function (value) {
//            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
//        },width:120},
        {   name: 'istatus',
            index: 'ISTATUS',
            label: "上架状态",
            formatter: function(value, options, rowObject) { return '<span id="istatus_'+rowObject.id+'" data="'+value+'" class="' + (({"10":"istatus-normal","20":"istatus-danger"})[value]) + '">' +  (({"10":"● 已上架","20":"● 已下架"})[value])+ '</span>' },
            width:80
        },
        {name: "process", title:false,index: "process", label: "操作", sortable: false, frozen: true, width: 200}
    ];
    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            var data = $("#gridTable").jqGrid('getRowData');
            var merchantCode = "${SESSION_KEY_SMERCHANT_CODE}";
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = '';
                <shiro:hasPermission name="COMMODITY_INFO_VIEW">
                    str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"viewMethod('"
                        + cl + "')\" title='查看' alt='查看'  /> | ";
                </shiro:hasPermission>
                if (merchantCode == data[i].smerchantCode) {
                <shiro:hasPermission name="COMMODITY_INFO_EDIT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                        + cl + "')\" title='编辑' alt='编辑'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="COMMODITY_INFO_DELETE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                        + cl + "')\" title='删除' alt='删除'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="SINGLE_COMMODITY_INFO_MANAGE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/batch.png' class=\"oper_icon\" onclick=\"addBatchMethod('"
                        + cl + "')\" title='批次管理' alt='批次管理'  /> | ";
                </shiro:hasPermission>
                if ($('#istatus_'+cl).attr('data') == 10) {
                    <shiro:hasPermission name="COMMODITY_INFO_SHELF">
                    str += "<img src='${staticSource}/resources/images/oper_icon/drop_off.png' class=\"oper_icon\" onclick=\"dropOff('"
                        + cl + "')\" title='下架' alt='下架'  /> | ";
                    </shiro:hasPermission>
                }
                if ($('#istatus_'+cl).attr('data') == 20) {
                    <shiro:hasPermission name="COMMODITY_INFO_DROPOFF">
                    str += "<img src='${staticSource}/resources/images/oper_icon/enabled.png' class=\"oper_icon\" onclick=\"shelf('"
                        + cl + "')\" title='启用' alt='启用'  /> | ";
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
//                addExportBtn("商品列表");
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
        addTitle: '添加商品信息',
        addFn:showLayerMedium
    };
    initBtnByForm4(initBtnConfig);


    layui.use('form', function () {
        var $ = layui.$, active = {
            batchManage: function () {//批次
                var sid = $("#gridTable").jqGrid('getGridParam', 'selarrrow');
                if (isEmpty(sid)) {
                    alertDel("请先选择要操作的商品");
                    return;
                }
                showLayerMax("确定要为商品添加批次信息?", ctx + '/commodity/commodityBatch/manageCommodityBatch?sid=' + sid, {area: ['60%', '70%']});
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
        showLayerMedium("详情", ctx + '/commodity/commodityInfo/view?sid=' + sid);
    }
    function editMethod(sid) {
        showLayerMedium("编辑", ctx + '/commodity/commodityInfo/toEdit?sid=' + sid);
    }
    function addBatchMethod(sid) {
//        showLayer("添加商品批次信息", ctx + '/commodity/commodityBatch/toAddBatch?sid=' + sid, {area: ['35%', '80%']});
        showLayerMax("添加商品批次信息", ctx + '/commodity/singleCommodityBatch/list?sid=' + sid);
    }
    function delMethod(sid) {
        confirmDelTip("确定要删除数据?", ctx + "/commodity/commodityInfo/delete", {checkboxId: sid});
    }
    function shelf(sid) {
        confirmOperTip("确定要启用该商品?", ctx + "/commodity/commodityInfo/shelf", {checkboxId: sid});
    }
    function dropOff(sid) {
        confirmOperTip("确定要下架该商品?", ctx + "/commodity/commodityInfo/dropOff", {checkboxId: sid});
    }

</script>

</body>
</html>



