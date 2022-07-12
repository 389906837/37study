<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>选择商品</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?1.0" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
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
                            <input type="text" name="ssmallCategoryName" id="ssmallCategoryName" value="" placeholder='<spring:message code="sp.commodityInfo.small.class.name" />...' class="layui-input">
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
                </div>
                <div class="jqGrid_wrapper">
                    <table id="gridTable"></table>
                    <div id="gridPager"></div>
                </div>
            </div>

            <input id="commodityIds" name="commodityIds" value="${commodityIds}" type="hidden" />
            <input id="oldCommodityIds" name="oldCommodityIds" value="${commodityIds}" type="hidden" />
            <input id="deleteCommodityIds" name="deleteCommodityIds" value="" type="hidden" />
        </div>
    </div>
</div>
<div class="layui-form-item fixed-bottom">
    <div class="layui-input-block">
        <label class="lable-left"><spring:message code="sp.commodityInfo.selected" /> <i id="selectNums">${empty selectNums ? 0 : selectNums}</i> <spring:message code="sp.commodityInfo.products" /></label>
        <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
        <button class="layui-btn" id="confirmBtn"><spring:message code="main.confirm" /></button>
    </div>
</div>
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var commodityMap = {};
    $(function () {
        $.jgrid.defaults.styleUI = 'Bootstrap';
        $("#gridTable").jqGrid({
            url: ctx + "/common/queryCommodityData",
            datatype: "json",
            postData: {deviceId: '${deviceId}'},
            height: 'auto',
            width:$(".ibox-content").width(),
            shrinkToFit: true,
            rowNum: rownum,
            rowList: rowList,
            rownumbers: true,
            multiselect: true,
            colModel: [{name: 'id', index: 'id', hidden: true},
                {name: 'scode', index: 'scode', label: '<spring:message code="main.product.number" />'},
                {name: 'commodityFullName', index: 'sname', label: '<spring:message code="main.product.name" />'},
                {name: 'ssmallCategoryName', index: 'SSMALL_CATEGORY_NAME', label: '<spring:message code="sp.commodityInfo.small.class.name" />'},
                {name: 'fcostPrice', label: '<spring:message code="sp.commodityInfo.cost.price.yuan" />',index: 'FCOST_PRICE'},
                {name: 'fsalePrice', label: '<spring:message code="sp.commodityInfo.sale.price.yuan" />',index: 'fsale_price'}
            ],
            pager: "#gridPager",
            viewrecords: true,
            sortname: "tadd_time",
            sortorder: "desc",
            gridComplete: function () {
                setTimeout(function () {
                    //商品
                    var commodityIds = $("#commodityIds").val();
                    if(!isEmpty(commodityIds)) {
                        commodityIds = commodityIds.substr(0,commodityIds.length-1);
                        var arr = commodityIds.split(",");
                        for (var i=0;i<arr.length;i++) {
                            $("#gridTable").jqGrid('setSelection',arr[i],false);
                            commodityMap[arr[i]] = $("#gridTable").jqGrid('getRowData',arr[i]);
                        }
                    }
                }, 0);
            },onSelectAll:function(rowIds,status){
                if(rowIds.length > 0) {
                    for(var i=0;i<rowIds.length;i++) {
                        checkBoxStatus(rowIds[i],status);
                    }
                }
            },onSelectRow: function(rowId,status){
                checkBoxStatus(rowId,status);
            }
        });
        //导航按钮区域
        $("#gridTable").jqGrid('navGrid', '#gridPager', {edit: false, add: false, del: false, search: false}, {
            height: 200,
            reloadAfterSubmit: true
        });
        $(window).bind('resize', function () {
            var width = $('.jqGrid_wrapper').width();
            $('#gridTable').setGridWidth(width);
        });
        $("#cancelBtn").click(function() {
            parent.layer.close(index);
            return false;
        });
        $("#confirmBtn").click(function() {
            //选择
            var commodityIds = $("#commodityIds").val();
            var deleteCommodityIds = $("#deleteCommodityIds").val();//删除的原有记录的
            if (isEmpty(commodityIds)) {
                parent.confirmSelectCommodity("");
            } else {
                parent.confirmSelectCommodity(commodityIds,commodityMap,deleteCommodityIds);
            }
            parent.layer.close(index);
        });
    });

    //操作选择复选框
    function checkBoxStatus(rowId,status) {
        var commodityIds = $("#commodityIds").val();
        var tempData = $("#gridTable").jqGrid('getRowData',rowId);
        if(status) {//选中
            if(commodityIds.indexOf(rowId) == -1) {
                if(isEmpty(commodityIds)) {
                    commodityIds = rowId+",";
                } else {
                    commodityIds += rowId+",";
                }
                $("#commodityIds").val(commodityIds);
                commodityMap[rowId] = tempData;
                $("#selectNums").html(parseInt($("#selectNums").html())+1);
            }
        } else {//取消
            if(commodityIds.indexOf(rowId) != -1) {
                commodityIds = commodityIds.replace(rowId+",","");
                $("#commodityIds").val(commodityIds);
                delete commodityMap[rowId];
                $("#selectNums").html(parseInt($("#selectNums").html())-1);
                var deleteCommodityIds = $("#deleteCommodityIds").val();//删除的原有记录的
                var oldCommodityIds = $("#oldCommodityIds").val();//判断原有的
                if (oldCommodityIds.indexOf(rowId) != -1) {
                    if(deleteCommodityIds.indexOf(rowId) == -1) {
                        if (isEmpty(deleteCommodityIds)) {
                            deleteCommodityIds = rowId + ",";
                        } else {
                            deleteCommodityIds += rowId + ",";
                        }
                        $("#deleteCommodityIds").val(deleteCommodityIds);
                    }
                }
            }
        }
    }
    layui.use('form', function () {
        var $ = layui.$, active = {
            query: function () {//查询
                reloadGrid(true);
            },
            reset: function () {//清除
                setResetFormValues();
            }
        };
        $('.layui-form .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });
</script>
</body>
</html>


