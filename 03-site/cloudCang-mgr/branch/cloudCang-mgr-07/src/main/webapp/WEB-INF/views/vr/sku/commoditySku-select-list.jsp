<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>选择视觉商品</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <%--<div class="ibox-title">
                    <h5>选择视觉商品</h5>
                </div>--%>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder="商品编号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="scommodityName" id="scommodityName" value="" placeholder="商品名称..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="ssmallCategoryName" id="ssmallCategoryName" value="" placeholder="小类名称..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="svrCode" id="svrCode" value="" placeholder="视觉识别编号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i
                                        class="layui-icon">&#xe615;</i>查询
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i
                                        class="layui-icon">&#x1006;</i>清除
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
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>

<script type="text/javascript">
    /**
     * 关闭并加载更新表格
     */
    function closeLayerAndRefresh(index) {
        layer.close(index);
        reloadGrid("");
    }
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    $(function () {
        $.jgrid.defaults.styleUI = 'Bootstrap';
        $("#gridTable").jqGrid({

            url: ctx + "/commoditySku/selectList/queryData",
            datatype: "json",
            height: 'auto',
            width:$(".ibox-content").width(),
            shrinkToFit: true,
            rowNum: rownum,
            rowList: rowList,
            multiselect: false,
            colModel: [{name: 'id', index: 'id', hidden: true},
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
                {name: 'scode', index: 'SCODE', label: "商品编号",width:80},
                {name: 'commodityFullName', index: 'SCOMMODITY_NAME', label: "商品名称",width:150},
                {name: 'svrCode', index: 'SVR_CODE', label: "视觉识别编号",width:100},
                {name: 'ssmallCategoryName', index: 'SSMALL_CATEGORY_NAME', label: "小类名称",width:80},
                {name: 'sbrandName', index: 'SBRAND_NAME', label: "品牌名称",width:80},
                {name: 'sbigCategoryName', index: '', label: "大类名称", hidden: true},
                {name: 'ishelfLife', index: '', label: "保质期", hidden: true},
                {name: 'ilifeType', index: '', label: "保质期类型", hidden: true},
                {name: 'sspecUnit', index: '', label: "规格单位", hidden: true},
                {name: 'ispecWeight', index: '', label: "规格/重量", hidden: true},
                {name: 'spackageUnit', index: '', label: "最小销售包装单位", hidden: true},
                {name: 'iidentificationType', index: '', label: "标识类型", hidden: true},
                {name: 'staste', index: '', label: "口味", hidden: true},
                {name: 'spackagingMaterial', index: '', label: "包装材质", hidden: true},
                {name: 'sorigin', index: '', label: "产地", hidden: true},
                {name: 'sproductBarcode', index: '', label: "商品条形码", hidden: true},
                {name: 'iweigth', index: '', label: "商品重量（g）", hidden: true},
                {name: 'icommodityFloat', index: '', label: "商品重量浮动值", hidden: true}
            ],
            // 双击添加
            ondblClickRow: function(rowId){
                var data = $("#gridTable").jqGrid('getRowData',rowId);
                parent.selectSKU(data.id,data.sbrandName,data.sbigCategoryName,data.ssmallCategoryName,data.ilifeType,data.ishelfLife,data.commodityFullName,
                    data.sspecUnit,data.ispecWeight,data.spackageUnit,data.scode,data.svrCode,data.iidentificationType,data.staste,data.spackagingMaterial,
                    data.sorigin,data.sproductBarcode,data.iweigth,data.icommodityFloat);
                parent.layer.close(index);
            },
            rownumbers: true,
            pager: "#gridPager",
            viewrecords: true,
            sortname: "id",
            sortorder: "asc",

        });
        //冻结列
        $("#gridTable").jqGrid('setFrozenColumns');
        //导航按钮区域
        $("#gridTable").jqGrid('navGrid', '#gridPager', {edit: false, add: false, del: false, search: false}, {
            height: 200,
            reloadAfterSubmit: true
        });

        $(window).bind('resize', function () {
            var width = $('.jqGrid_wrapper').width();
            $('#gridTable').setGridWidth(width);
        });

    });
    layui.use('form', function () {
        var $ = layui.$, active = {
            query: function () {//查询
                reloadGrid(true);
            },
            reset: function () {//清除
                setResetFormValues();
            },
        };
        $('.layui-form .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });
</script>
</body>
</html>

