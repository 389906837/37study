<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>选择指定商户</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet"/>
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code="sh.select.a.specific.merchant" /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">

                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder='<spring:message code="main.merchant.number" />'
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sname" id="sname" value="" placeholder='<spring:message code="main.merchant.name" />'
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i
                                        class="layui-icon">&#xe615;</i><spring:message code="main.query" />
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i
                                        class="layui-icon">&#x1006;</i><spring:message code="main.clear" />
                                </button>
                            </div>
                        </div>

                    </div>
                    <div class="jqGrid_wrapper">
                        <table id="gridTable"></table>
                        <div id="gridPager"></div>
                    </div>

                    <input id="commodityIds" name="commodityIds" value="${commodityIds}" type="hidden"/>
                    <input id="commodityCodes" name="commodityCodes" value="${commodityCodes}" type="hidden"/>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="layui-form-item fixed-bottom">
    <div class="layui-input-block">
        <label class="lable-left"><spring:message code="sh.selected" /> <i id="selectNums">${empty selectNums ? 0 : selectNums}</i> <spring:message code="sh.businesses" /></label>
        <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
        <button class="layui-btn" id="confirmBtn"><spring:message code="sh.confirm" /></button>
    </div>
</div>
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    /**
     * <spring:message code="sh.time.group.key" />
     */
    layui.use('laydate', function () {
        var laydate = layui.laydate;

        //执行一个laydate实例
        laydate.render({
            elem: '#dexpireDate' //指定元素
        });
        laydate.render({
            elem: '#queryTimeCondition', //指定元素,查询商户合约到期日
            range: true,
            type: 'date'
        });
    });
    /**
     * 关闭并加载更新表格
     */
    function closeLayerAndRefresh(index) {
        layer.close(index);
        reloadGrid("");
    }
    $(function () {
        $.jgrid.defaults.styleUI = 'Bootstrap';
        $("#gridTable").jqGrid({
            url: ctx + "/merchantInfo/queryData",
            postData: {selectMerchantInfo: true},
            datatype: "json",
            height: 'auto',
            width: $(".ibox-content").width(),
            shrinkToFit: true,
            rowNum: rownum,
            rowList: rowList,
            multiselect: true,
            colModel: [{name: 'id', index: 'id', hidden: true},
                {name: 'scode', label: '<spring:message code="main.merchant.number" />', index: 'scode'},
                {name: 'sname', label: '<spring:message code="main.merchant.name" />', index: 'sname'},
                {
                    name: 'itype',
                    index: 'itype',
                    label: '<spring:message code="sh.business.type" />',
                    formatter: "select",
                    editoptions: {value: '10:<spring:message code="sh.personal" />;20:<spring:message code="sh.enterprise" />'}
                },
                {
                    name: 'icooperationMode',
                    index: 'ICOOPERATION_MODE',
                    label: '<spring:message code="sh.cooperation.model" />',
                    formatter: "select",
                    editoptions: {value: '10:<spring:message code="sh.use" />;20:<spring:message code="sh.rent" />;30:<spring:message code="sh.self.use" />'}
                }
            ],
            rownumbers: true,
            pager: "#gridPager",
            viewrecords: true,
            sortname: "id",
            sortorder: "asc",
            gridComplete: function () {
                setTimeout(function () {
                    //商品
                    var commodityIds = $("#commodityIds").val();
                    if (!isEmpty(commodityIds)) {
                        commodityIds = commodityIds.substr(0, commodityIds.length - 1);
                        var arr = commodityIds.split(",");
                        for (var i = 0; i < arr.length; i++) {
                            $("#gridTable").jqGrid('setSelection', arr[i], false);
                        }
                    }
                }, 0);
            }, onSelectAll: function (rowIds, status) {
                if (rowIds.length > 0) {
                    for (var i = 0; i < rowIds.length; i++) {
                        checkBoxStatus(rowIds[i], status);
                    }
                }
            }, onSelectRow: function (rowId, status) {
                checkBoxStatus(rowId, status);
            }
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
        $("#cancelBtn").click(function () {
            parent.layer.close(index);
            return false;
        });
        $("#confirmBtn").click(function () {
            //选择
            var commodityIds = $("#commodityIds").val();
            var commodityCodes = $("#commodityCodes").val();
            if (isEmpty(commodityIds) || isEmpty(commodityCodes)) {
                alertDel("<spring:message code='sh.please.select.merchant' />!");
                return;
            }
            parent.selectCommodity(commodityIds.substr(0, commodityIds.length - 1), commodityCodes.substr(0, commodityCodes.length - 1));
            parent.layer.close(index);
        });
    });
    function checkBoxStatus(rowId, status) {
        var commodityIds = $("#commodityIds").val();
        var commodityCodes = $("#commodityCodes").val();
        var tempData = $("#gridTable").jqGrid('getRowData', rowId);
        if (status) {//选中
            if (commodityIds.indexOf(rowId) == -1) {
                if (isEmpty(commodityIds)) {
                    commodityIds = rowId + ",";
                    commodityCodes = tempData.scode + ",";
                } else {
                    commodityIds += rowId + ",";
                    commodityCodes += tempData.scode + ",";
                }
                $("#commodityIds").val(commodityIds);
                $("#commodityCodes").val(commodityCodes);
                $("#selectNums").html(parseInt($("#selectNums").html()) + 1);
            }
        } else {//取消
            if (commodityIds.indexOf(rowId) != -1) {
                commodityIds = commodityIds.replace(rowId + ",", "");
                commodityCodes = commodityCodes.replace(tempData.scode + ",", "");
                $("#commodityIds").val(commodityIds);
                $("#commodityCodes").val(commodityCodes);
                $("#selectNums").html(parseInt($("#selectNums").html()) - 1);
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

