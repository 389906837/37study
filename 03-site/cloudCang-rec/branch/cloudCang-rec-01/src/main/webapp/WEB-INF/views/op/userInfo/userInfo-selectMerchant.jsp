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
                    <h5>选择指定商户</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">

                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder="商户名编号"
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sname" id="sname" value="" placeholder="商户名称"
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
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    /**
     * 时间组键
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
            multiselect: false,
            colModel: [{name: 'id', index: 'id', hidden: true},
                {name: 'scode', label: "商户编号", index: 'scode'},
                {name: 'sname', label: "商户名", index: 'sname'},
                {
                    name: 'itype',
                    index: 'itype',
                    label: "商户类型",
                    formatter: "select",
                    editoptions: {value: '10:个人;20:企业'}
                },
                {
                    name: 'icooperationMode',
                    index: 'ICOOPERATION_MODE',
                    label: "合作模式",
                    formatter: "select",
                    editoptions: {value: '10:采用;20:租用;30:自用'}
                }
            ],
            rownumbers: true,
            pager: "#gridPager",
            viewrecords: true,
            sortname: "id",
            sortorder: "asc",
            // 双击添加
            ondblClickRow: function (rowId) {
                var data = $("#gridTable").jqGrid('getRowData', rowId);
                parent.selectSupp(data.id, data.scode, data.sname);
                parent.layer.close(index);
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
    });
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

