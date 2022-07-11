<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>选择对接业务员</title>
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

                            <div class="layui-input-inline">
                                <input type="text" name="suserName" id="suserName" value="" placeholder="用户名..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="srealName" id="srealName" value="" placeholder="真实姓名..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="smobile" id="smobile" value="" placeholder="手机号..."
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
            postData: {selectBd: 'true'},
            url: ctx + "/operator/queryData",
            datatype: "json",
            height: 'auto',
            width: $(".ibox-content").width(),
            shrinkToFit: true,
            rowNum: rownum,
            rowList: rowList,
            multiselect: false,
            colModel: [{name: 'id', index: 'id', hidden: true},
                {name: 'suserName', label: "用户名", index: 'suser_name', width: 100},
                {name: 'srealName', index: 'sreal_name', label: "真实姓名", width: 70},
                {name: 'smobile', index: 'smobile', label: "手机号", width: 70},
                {name: 'smail', index: 'smail', label: "邮箱", width: 100}
            ],
            // 双击添加
            ondblClickRow: function (rowId) {
                var data = $("#gridTable").jqGrid('getRowData', rowId);
                parent.selectSupp(data.id, data.srealName, data.suserName);
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
</script>
</body>
</html>

