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
                    <h5>视觉商品管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder="商品编号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="scommodityName" id="scommodityName" value=""
                                       placeholder="商品名称..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="svrCode" id="svrCode" value="" placeholder="视觉识别编号..."
                                       class="layui-input">
                            </div>

                            <div class="layui-input-inline">
                                <input type="text" name="ssmallCategoryName" id="ssmallCategoryName" value=""
                                       placeholder="小类名称..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sbrandName" id="sbrandName" value="" placeholder="品牌名称..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control" name="istatus" id="istatus">
                                    <option value="">商品状态</option>
                                    <option value="10">在售</option>
                                    <option value="20">停用</option>
                                </select>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <select class="form-control" name="ionlineStatus" id="ionlineStatus">
                                    <option value="">上架状态</option>
                                    <option value="10">草稿</option>
                                    <option value="20">已上架</option>
                                </select>
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query">
                                    <i class="layui-icon">&#xe615;</i>查询
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset">
                                    <i class="layui-icon">&#x1006;</i>清除
                                </button>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <shiro:hasPermission name="SP_SKU_ADD">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"><i
                                        class="layui-icon"></i>添加
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="SP_SKU_DELETE">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i>删除
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="SP_SKU_BATCH_ADD">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="batchImportVrCommodity"><i
                                        class="layui-icon"></i>批量导入
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
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    var colModel = [
        {name: 'id', index: 'id', hidden: true},
        {
            name: 'scommodityImg',
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
        {name: 'scode', index: 'SCODE', label: "视觉商品编号",width:120},
        {name: 'commodityFullName', index: 'SCOMMODITY_NAME', label: "商品名称",width:120},
        {name: 'svrCode', index: 'SVR_CODE', label: "视觉识别编号",width:120},
        {name: 'ssmallCategoryName', index: 'SSMALL_CATEGORY_NAME', label: "小类名称",width:80},
        {name: 'sbrandName', index: 'SBRAND_NAME', label: "品牌名称",width:120},
        {   name: 'istatus',
            index: 'ISTATUS',
            label: "商品状态",
            formatter: "select",
            editoptions: {value: '10:在售;20:停用'},
            width:80
        },
        {   name: 'ionlineStatus',
            index: 'IONLINE_STATUS',
            label: "上架状态",
            formatter: "select",
            editoptions: {value: '10:草稿;20:已上架'},
            width:80
        },
        {name: 'taddTime', index: 'TADD_TIME', label: "添加日期",formatter: function (value) {
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        },width:120},
        {name: "process",title:false, index: "process", label: "操作", sortable: false, frozen: true, width: 200}
    ];

    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            var data = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = '';
                <shiro:hasPermission name="SP_SKU_VIEW">
                    str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"viewMethod('"
                        + cl + "')\" title='查看' alt='查看'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="SP_SKU_EDIT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                        + cl + "')\" title='编辑' alt='编辑'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="SP_SKU_SYNCHRONIZE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/sync_vrCommodity.png' class=\"oper_icon\" onclick=\"synchMethod('"
                        + cl + "')\" title='同步视觉商品信息' alt='同步视觉商品信息'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="SP_SKU_DELETE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                        + cl + "')\" title='删除' alt='删除'  /> | ";
                </shiro:hasPermission>
                if (data[i].istatus == 10) {
                    <shiro:hasPermission name="SP_SKU_DISABLE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/disable.png' class=\"oper_icon\" onclick=\"skuDisableMethod('"
                        + cl + "')\" title='停用' alt='停用'  /> | ";
                    </shiro:hasPermission>
                } else if (data[i].istatus == 20){
                    <shiro:hasPermission name="SP_SKU_ENABLE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/enabled.png' class=\"oper_icon\" onclick=\"skuEnableMethod('"
                        + cl + "')\" title='启用' alt='启用'  /> | ";
                    </shiro:hasPermission>
                }
                if (data[i].ionlineStatus == 10 || data[i].ionlineStatus == 30) {
                    <shiro:hasPermission name="SP_SKU_SHELF">
                    str += "<img src='${staticSource}/resources/images/oper_icon/shelf.png' class=\"oper_icon\" onclick=\"skuShelfMethod('"
                    + cl + "')\" title='上架' alt='上架'  /> | ";
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
        addTitle: '添加视觉商品信息',
        addFn:showLayerMedium
    };
    initBtnByForm4(initBtnConfig);

    layui.use('form', function () {
        var $ = layui.$, active = {
            batchImportVrCommodity : function () {
                showLayerOne("批量导入视觉商品信息", ctx + "/commoditySku/toBatchImportVr");
            }
        };
        $('.layui-form .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });
    function viewMethod(sid) {
        showLayerMedium("视觉商品详情", ctx + '/commoditySku/view?sid=' + sid);
    }
    function editMethod(sid) {
        showLayerMedium("编辑视觉商品信息", ctx + '/commoditySku/toEdit?sid=' + sid);
    }
    function synchMethod(sid) {
        confirmOperTip("确定同步视觉商品信息到商品列表？", ctx + "/commoditySku/synchronize" , {checkboxId: sid});
    }
    function delMethod(sid) {
        confirmDelTip("确定要删除数据?", ctx + "/commoditySku/delete", {checkboxId: sid});
    }
    function skuDisableMethod(sid) {
        confirmOperTip("确定要停用该商品?", ctx + "/commoditySku/skuDisable", {checkboxId: sid});
    }
    function skuEnableMethod(sid) {
        confirmOperTip("确定要启用该商品?", ctx + "/commoditySku/skuEnable", {checkboxId: sid});
    }
    function skuShelfMethod(sid) {
        confirmOperTip("确定要上架该商品?", ctx + "/commoditySku/skuShelf", {checkboxId: sid});
    }
    function skuDropoffMethod(sid) {
        confirmOperTip("确定要下架该商品?", ctx + "/commoditySku/skuDropoff", {checkboxId: sid});
    }
</script>
</body>
</html>



