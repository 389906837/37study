<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>选择开放平台用户</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>选择开放平台用户</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="snickName" id="snickName" value="" placeholder="会员昵称..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="smobile" id="smobile" value="" placeholder="手机号码..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="semail" id="semail" value="" placeholder="邮箱..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" list="{0:女,1:男}" id="isex"
                                             name="isex" entire="true" entireName="请选择会员性别"/>
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" list="{10:个人会员,20:企业会员}" id="imemberType"
                                             name="imemberType" entire="true" entireName="请选择会员类型"/>
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
            postData: {selectUser: 'true'},
            url: ctx + "/userInfo/queryData",
            datatype: "json",
            height: 'auto',
            width: $(".ibox-content").width(),
            shrinkToFit: true,
            rowNum: rownum,
            rowList: rowList,
            multiselect: false,
            colModel: [{name: 'id', index: 'id', hidden: true},
                {name: 'scode', index: 'scode', hidden: true},
                {name: 'snickName', index: 'SNICK_NAME', label: "用户昵称", width: 100},
                {name: 'smobile', index: 'SMOBILE', label: "手机号码", width: 80},
                {name: 'semail', index: 'SEMAIL', label: "邮箱", width: 80},
                {
                    name: 'isex', index: 'ISEX', label: "性别", width: 40, formatter: "select",
                    editoptions: {value: '0:女;1:男'}
                },
                {
                    name: 'dbirthdate', index: 'DBIRTHDATE', label: "生日", width: 80, formatter: function (value) {
                    if (isEmpty(value)) {
                        return '';
                    }
                    return formatDate(new Date(value), 'yyyy-MM-dd');
                }
                },
                {
                    name: 'imemberType', index: 'IMEMBER_TYPE', label: "会员类型", width: 80, formatter: "select",
                    editoptions: {value: '10:个人会员;20:企业会员'}
                }, {
                    name: 'tregisterTime',
                    index: 'TREGISTER_TIME',
                    label: "注册时间",
                    width: 120,
                    formatter: function (value) {
                        if (isEmpty(value)) {
                            return '';
                        }
                        return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
                    }
                }
            ],
            // 双击添加
            ondblClickRow: function (rowId) {
                var data = $("#gridTable").jqGrid('getRowData', rowId);
                parent.selectSupp(data.id, data.scode, data.snickName);
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

