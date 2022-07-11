<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>选择识别模型</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>选择识别模型</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder="模型编号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sname" id="sname" value="" placeholder="模型名称..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="smodelAddress" id="smodelAddress" value="" placeholder="模型地址..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" list="{10:bmodel模型,20:darknet模型}" id="imodelType"
                                             name="imodelType" entire="true" entireName="请选择模型类型"/>
                            </div>

                            <div class="layui-input-inline">
                                <cang:select type="list" list="{10:通用,20:专用,30:多商户}" id="irangeType"
                                             name="irangeType" entire="true" entireName="请选择适用范围"/>
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
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
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
            postData: {selectServerModel: 'true'},
            url: ctx + "/serverModel/queryData",
            datatype: "json",
            height: 'auto',
            width: $(".ibox-content").width(),
            shrinkToFit: true,
            rowNum: rownum,
            rowList: rowList,
            multiselect: false,
            colModel: [{name: 'id', index: 'id', hidden: true},
                {name: 'scode', index: 'scode', label: "模型编号", width: 60},
                {name: 'sname', index: 'SNAME', label: "模型名称", width: 90},
                {name: 'smodelAddress', index: 'SMODEL_ADDRESS', label: "模型地址", width: 90},
                {
                    name: 'imodelType', index: 'IMODEL_TYPE', label: "模型类型", width: 30, formatter: "select",
                    editoptions: {value: '10:bmodel模型;20:darknet模型'}
                },
                {
                    name: 'irangeType', index: 'IRANGE_TYPE', label: "适用范围", width: 30, formatter: "select",
                    editoptions: {value: '10:通用;20:专用;30:多商户'}
                }
            ],
            // 双击添加
            ondblClickRow: function (rowId) {
                var data = $("#gridTable").jqGrid('getRowData', rowId);
                parent.selectSupp(data.scode,data.id);
                parent.layer.close(index);
            },
            rownumbers: true,
            pager: "#gridPager",
            viewrecords: true,
            sortname: "id",
            sortorder: "asc"
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
    /**
     * 选择日期范围
     */
    layui.use('laydate', function () {
        var laydate = layui.laydate;

//执行一个laydate实例
        laydate.render({
            elem: '#toperateDateStr', //指定元素
            range: true,
            type: 'datetime'
        });
    });

</script>
</body>
</html>

