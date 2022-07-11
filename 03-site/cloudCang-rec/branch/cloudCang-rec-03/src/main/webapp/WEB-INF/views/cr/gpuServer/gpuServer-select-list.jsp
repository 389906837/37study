<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>选择设备</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?001" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
<style>
    .ui-jqgrid tr.jqgrow td {
        white-space: normal !important;
        height: auto;
    }

    .wrapper-h {
        height: 100%;
    }
</style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight wrapper-h">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox-content">
                <div class="layui-form" id="searchForm">
                    <div class="layui-form-item">
                        <div class="layui-input-inline">
                            <input type="text" name="scode" id="scode" value="" placeholder="设备编号..."
                                   class="layui-input">
                        </div>
                        <div class="layui-input-inline">
                            <input type="text" name="sname" id="sname" value="" placeholder="设备名称..."
                                   class="layui-input">
                        </div>
                        <div class="layui-input-inline">
                            <input type="text" name="sputAddress" id="sputAddress" value="" placeholder="设备地址..."
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

            <input id="deviceIds" name="deviceIds" value="${deviceIds}" type="hidden"/>
            <input id="deviceCodes" name="deviceCodes" value="${deviceCodes}" type="hidden"/>
        </div>
    </div>

</div>
<div class="layui-form-item fixed-bottom">
    <div class="layui-input-block">
        <label class="lable-left">已选 <i id="selectNums">${empty selectNums ? 0 : selectNums}</i> 台设备</label>
        <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
        <button class="layui-btn" id="confirmBtn">确认</button>
    </div>
</div>
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    $(function () {
        $.jgrid.defaults.styleUI = 'Bootstrap';
        $("#gridTable").jqGrid({
            url: ctx + "/gpuServer/queryData",
            datatype: "json",
            height: 'auto',
            width: $(".ibox-content").width(),
            shrinkToFit: true,
            rowNum: rownum,
            rowList: rowList,
            rownumbers: true,
            multiselect: true,
            colModel: [
                {name: 'scode', index: 'scode', hidden: true},
                {name: 'sname', index: 'SNAME', label: "服务器名称", width: 100},
                {name: 'sip', index: 'SIP', label: "服务器IP", width: 80},
                {name: 'sport', index: 'SPORT', label: "服务器端口", width: 80},
                {
                    name: 'irunStatus', index: 'IRUN_STATUS', label: "初始化状态", width: 80, formatter: "select",
                    editoptions: {value: '10:离线;20:在线;30:在线并且模型已初始化'}
                },
                {name: 'smodelCode', index: 'SMODEL_CODE', label: "视觉模型编号", width: 80},
                {
                    name: 'itype', index: 'ITYPE', label: "服务器类型", width: 80, formatter: "select",
                    editoptions: {value: '10:图像识别服务器'}
                }
            ],
            pager: "#gridPager",
            viewrecords: true,
            sortname: "IAUDIT_STATUS",
            sortorder: "asc",
            gridComplete: function () {
                setTimeout(function () {
                    //设备
                    var deviceIds = $("#deviceIds").val();
                    if (!isEmpty(deviceIds)) {
                        deviceIds = deviceIds.substr(0, deviceIds.length - 1);
                        var arr = deviceIds.split(",");
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
            var deviceIds = $("#deviceIds").val();
            var deviceCodes = $("#deviceCodes").val();
            if (isEmpty(deviceIds) || isEmpty(deviceCodes)) {
                parent.selectDevice("", "");
            } else {
                parent.selectDevice(deviceIds.substr(0, deviceIds.length - 1), deviceCodes.substr(0, deviceCodes.length - 1));
            }
            parent.layer.close(index);
        });
    });

    //操作选择复选框
    function checkBoxStatus(rowId, status) {
        var deviceIds = $("#deviceIds").val();
        var deviceCodes = $("#deviceCodes").val();
        var tempData = $("#gridTable").jqGrid('getRowData', rowId);
        if (status) {//选中
            if (deviceIds.indexOf(rowId) == -1) {
                if (isEmpty(deviceIds)) {
                    deviceIds = rowId + ",";
                    deviceCodes = tempData.scode + ",";
                } else {
                    deviceIds += rowId + ",";
                    deviceCodes += tempData.scode + ",";
                }
                $("#deviceIds").val(deviceIds);
                $("#deviceCodes").val(deviceCodes);
                $("#selectNums").html(parseInt($("#selectNums").html()) + 1);
            }
        } else {//取消
            if (deviceIds.indexOf(rowId) != -1) {
                deviceIds = deviceIds.replace(rowId + ",", "");
                deviceCodes = deviceCodes.replace(tempData.scode + ",", "");
                $("#deviceIds").val(deviceIds);
                $("#deviceCodes").val(deviceCodes);
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


