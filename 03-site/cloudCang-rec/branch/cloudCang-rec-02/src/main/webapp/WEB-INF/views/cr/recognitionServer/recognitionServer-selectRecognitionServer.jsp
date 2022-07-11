<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>识别服务器列表</title>
<link href="${staticSource}/resources/layui/css/layui.css?1.0" rel="stylesheet">
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
                                <input type="text" name="sname" id="sname" value="" placeholder="服务器名称..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sip" id="sip" value="" placeholder="服务器IP..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sport" id="sport" value="" placeholder="服务器端口..."
                                       class="layui-input">
                            </div>
                          <%--  <div class="layui-input-inline">
                                <cang:select type="list" list="{10:待审核,20:审核通过,30:审核驳回}" id="iauditStatus"
                                             name="iauditStatus" entire="true" entireName="请选择审核状态"/>
                            </div>--%>
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
            <input id="recognitionServerIds" name="recognitionServerIds" value="${recognitionServerIds}" type="hidden"/>
            <input id="recognitionServerCodes" name="recognitionServerCodes" value="${recognitionServerCodes}" type="hidden"/>
            <input id="queryDeviceType" name="queryDeviceType" value="${queryDeviceType}" type="hidden" disabled/>
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
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    /**
     * 选择日期范围
     */
    layui.use(['form', 'laydate'], function () {
        var laydate = layui.laydate;
        laydate.render({
            elem: '#toperateDateStr', //指定元素
            range: true,
            type: 'datetime'
        });
        $('.layui-form .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });




    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var deviceType = $('#queryDeviceType').val();
    $(function () {
        $.jgrid.defaults.styleUI = 'Bootstrap';
        $("#gridTable").jqGrid({
            url: ctx + "/recognitionServer/queryData?iauditStatus=20",
            datatype: "json",
            height: 'auto',
            width: $(".ibox-content").width(),
            shrinkToFit: true,
            rowNum: rownum,
            rowList: rowList,
            rownumbers: true,
            multiselect: true,
            colModel: [{name: 'id', index: 'id', hidden: true},
                {name: 'scode', index: 'SCODE', label: "服务器编号", width: 70},
                {name: 'sname', index: 'SNAME', label: "服务器名称", width: 100},
                {name: 'sip', index: 'SIP', label: "服务器IP", width: 80},
                {name: 'sport', index: 'SPORT', label: "服务器端口", width: 80},
                {name: 'smodelCode', index: 'SMODEL_CODE', label: "视觉模型编号", width: 80},
             /*   {
                    name: 'iauditStatus', index: 'IAUDIT_STATUS', label: "审核状态", width: 80, formatter: "select",
                    editoptions: {value: '10:待审核;20:审核通过;30:审核驳回'}
                },
                {
                    name: 'tauditTime', index: 'TAUDIT_TIME', label: "审核时间", width: 80, formatter: function (value) {
                    if (isEmpty(value)) {
                        return '';
                    }
                    return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
                }
                },
                {name: 'sauditOperName', index: 'SAUDIT_OPER_NAME', label: "审核人姓名", width: 80},*/
            ],
            pager: "#gridPager",
            viewrecords: true,
            sortname: "scode",
            sortorder: "asc",
            gridComplete: function () {
                setTimeout(function () {
                    //设备
                    var recognitionServerIds = $("#recognitionServerIds").val();
                    if (!isEmpty(recognitionServerIds)) {
                        recognitionServerIds = recognitionServerIds.substr(0, recognitionServerIds.length - 1);
                        var arr = recognitionServerIds.split(",");
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
            var recognitionServerIds = $("#recognitionServerIds").val();
            var recognitionServerCodes = $("#recognitionServerCodes").val();
            if (isEmpty(recognitionServerIds) || isEmpty(recognitionServerCodes)) {
                /*alertDel("请选择设备!");
                 return;*/
                parent.selectServer("", "");
            } else {
                parent.selectServer(recognitionServerIds.substr(0, recognitionServerIds.length - 1), recognitionServerCodes.substr(0, recognitionServerCodes.length - 1));
            }
            parent.layer.close(index);
        });
    });

    //操作选择复选框
    function checkBoxStatus(rowId, status) {
        var recognitionServerIds = $("#recognitionServerIds").val();
        var recognitionServerCodes = $("#recognitionServerCodes").val();
        var tempData = $("#gridTable").jqGrid('getRowData', rowId);
        if (status) {//选中
            if (recognitionServerIds.indexOf(rowId) == -1) {
                if (isEmpty(recognitionServerIds)) {
                    recognitionServerIds = rowId + ",";
                    recognitionServerCodes = tempData.scode + ",";
                } else {
                    recognitionServerIds += rowId + ",";
                    recognitionServerCodes += tempData.scode + ",";
                }
                $("#recognitionServerIds").val(recognitionServerIds);
                $("#recognitionServerCodes").val(recognitionServerCodes);
                $("#selectNums").html(parseInt($("#selectNums").html()) + 1);
            }
        } else {//取消
            if (recognitionServerIds.indexOf(rowId) != -1) {
                recognitionServerIds = recognitionServerIds.replace(rowId + ",", "");
                recognitionServerCodes = recognitionServerCodes.replace(tempData.scode + ",", "");
                $("#recognitionServerIds").val(recognitionServerIds);
                $("#recognitionServerCodes").val(recognitionServerCodes);
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

