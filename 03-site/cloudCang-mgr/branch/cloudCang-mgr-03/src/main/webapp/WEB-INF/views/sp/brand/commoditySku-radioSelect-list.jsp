<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>选择指定品牌</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <%--<div class="layui-input-inline">--%>
                                <%--<input type="text" name="smerchantCode" id="smerchantCode" value="" placeholder='<spring:message code="main.merchant.number" />...'--%>
                                       <%--class="layui-input">--%>
                            <%--</div>--%>
                            <%--<div class="layui-input-inline">--%>
                                <%--<input type="text" name="merchantName" id="merchantName" value="" placeholder='<spring:message code="sp.brand.merchant.name" />...'--%>
                                       <%--class="layui-input">--%>
                            <%--</div>--%>
                            <div class="layui-input-inline">
                                <input type="text" name="sname" id="sname" value="" placeholder='<spring:message code="sp.brand.brand.name" />...'
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

                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>

<script type="text/javascript">
    /**
     * <spring:message code="sp.brand.close.and.load.the.update.form" />
     */
    function closeLayerAndRefresh(index) {
        layer.close(index);
        reloadGrid("");
    }

    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    $(function () {
        $.jgrid.defaults.styleUI = 'Bootstrap';
        $("#gridTable").jqGrid({

            url: ctx + "/commodity/brandInfo/queryData",
            datatype: "json",
            height: 'auto',
            width: $(".ibox-content").width(),
            shrinkToFit: true,
            rowNum: rownum,
            rowList: rowList,
            multiselect: false,
            colModel: [
                {name: 'id', index: 'id', hidden: true},
                {name: 'sname', index: 'SNAME', label: '<spring:message code="sp.brand.brand.name" />', width: 120},
                <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                {name: 'smerchantCode', label: '<spring:message code="main.merchant.number" />', index: 'SMERCHANT_CODE'},
                {name: 'merchantName', label: '<spring:message code="main.merchant.name" />', index: 'sname', sortable: false},
                </c:if>
                {name: 'isort', index: 'ISORT', label: '<spring:message code="main.sort" />', width: 50},
                {
                    name: 'taddTime', index: 'TADD_TIME', label: '<spring:message code="sp.brand.add.date" />', formatter: function (value) {
                        return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
                    }, width: 120
                }
            ],
            // 双击添加
            ondblClickRow: function (rowId) {
                var data = $("#gridTable").jqGrid('getRowData', rowId);
                parent.selectBrand(data.id, data.sname);
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

