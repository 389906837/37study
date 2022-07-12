<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备分组列表</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?20180408" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <%--<div class="ibox-title">--%>
                <%--<h5>设备分组管理</h5>--%>
                <%--</div>--%>
                <div class="layui-form-item ">
                    <table class="layui-table" id="deviceTable">
                        <colgroup>
                            <col width="150">
                            <col width="150">
                            <col width="250">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>已选设备编号</th>
                            <th>设备名称</th>
                            <th>设备地址</th>
                        </tr>
                        </thead>
                        <tbody id="deviceBody">
                        <c:forEach items="${deviceList}" var="vo">
                            <tr>
                                <td>${vo.scode}</td>
                                <td>${vo.sname}</td>
                                <td>${vo.address}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="ibox-content m-t">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="sgroupName" id="sgroupName" value="" placeholder="分组名称..."
                                       class="layui-input">
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
<form class="layui-form" action="${ctx}/device/groupManage/addToGroup" method="post" id="myForm">
    <div class="layui-form-item fixed-bottom">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
            <button class="layui-btn" lay-submit="" lay-filter="myFormSub">确认</button>
        </div>
    </div>
    <input id="deviceIds" name="deviceIds" value="${sid}" type="hidden" />
    <input id="groupId" name="groupId" value="" type="hidden" />
</form>
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    /**
     * 关闭并加载更新表格
     */
    function closeLayerAndRefresh(index) {
        layer.close(index);
        reloadGrid("");
    }

    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var form;
    $(function () {
        $.jgrid.defaults.styleUI = 'Bootstrap';
        $("#gridTable").jqGrid({
            url: ctx + "/device/groupManage/queryMerchantGroupData",
            datatype: "json",
            height: 'auto',
            autowidth: true,
            shrinkToFit: true,
            multiselect:false,//是否多选
            rowNum: rownum,
            rowList: rowList,
            rownumbers: true,
            colModel: [{name: 'id', index: 'id', hidden: true},
                {name: 'sgroupName', index: 'SGROUP_NAME', label: "分组名称"},
                {name: 'isort', index: 'ISORT', label: "排序号"},
                {name: 'sremark', index: 'SREMARK', label: "备注"}
            ],
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
        $("#cancelBtn").click(function() {
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

        form = layui.form;
        //监听提交
        form.on('submit(myFormSub)', function () {
            var sid = $("#gridTable").jqGrid('getGridParam', 'selrow');
            if (isEmpty(sid)) {
                alertDel("请先选择要加入的分组数据");
                return false;
            }
            $("#groupId").val(sid);
            var loadIndex = loading();
            $('#myForm').ajaxSubmit({
                type: "post",
                dataType: "json",
                async: true,
                error: function () {
                    alertDelAndReload("操作异常，请重新操作");
                },
                success: function (msg) {
                    //成功返回
                    closeLayer(loadIndex);//关闭加载框
                    if (msg.success) {
                        alertSuccess("操作成功", {
                            'index': index
                        }, function () {
                            parent.closeLayerAndRefresh(index);
                        });
                    } else {
                        alertDel(msg.msg);
                    }
                }
            });
            return false;
        });
    });

</script>
</body>
</html>



