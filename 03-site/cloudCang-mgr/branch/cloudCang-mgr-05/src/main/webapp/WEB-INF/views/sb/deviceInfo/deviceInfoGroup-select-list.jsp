<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title><spring:message code="activity.select.equipment" /></title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
<style>
    .ui-jqgrid tr.jqgrow td {
        white-space: normal !important;
        height: auto;
    }
</style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox-content">
                <div class="layui-form" id="searchForm">
                    <div class="layui-form-item">

                            <div class="layui-input-inline">
                                <input type="text" name="sgroupName" id="sgroupName" value="" placeholder="<spring:message code='sb.group.name' />..." class="layui-input">
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
            <div class="layui-form-item fixed-bottom">
                <div class="layui-input-block">
                    <label><spring:message code='sb.selected'/>
 <i id="selectNums">${empty selectNums ? 0 : selectNums}</i> <spring:message code='sb.grouping'/>
</label>
                    <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
                    <button class="layui-btn" id="confirmBtn"><spring:message code="main.confirm" /></button>
                </div>
            </div>
            <input id="deviceIdGroups" name="deviceIdGroups" value="${deviceIdGroups}" type="hidden" />
            <input id="sremarks" name="sremarks" value="" type="hidden" />
        </div>
    </div>
</div>
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    $(function () {
        $.jgrid.defaults.styleUI = 'Bootstrap';
        $("#gridTable").jqGrid({
            url: ctx + "/device/groupManage/queryData",
            datatype: "json",
            height: 'auto',
            autowidth: true,
            shrinkToFit: true,
            rowNum: rownum,
            rowList: rowList,
            rownumbers: true,
            multiselect: true,
            colModel: [{name: 'id', index: 'id', hidden: true},
                {name: 'sgroupName', index: 'SGROUP_NAME', label: "<spring:message code='sb.group.name' />", width: 120},
                {name: 'isort', index: 'ISORT', label: '<spring:message code="main.sort" />', width: 260},
                {name: 'sremark', index: 'SREMARK', label: '<spring:message code="main.remarks" />', width: 260},
            ],
            pager: "#gridPager",
            viewrecords: true,
            sortname: "isort",
            sortorder: "asc",
            gridComplete: function () {
                setTimeout(function () {
                    //设备
                    var deviceIdGroups = $("#deviceIdGroups").val();
                    if(!isEmpty(deviceIdGroups)) {
                        deviceIdGroups = deviceIdGroups.substr(0,deviceIdGroups.length-1);
                        var arr = deviceIdGroups.split(",");
                        for (var i=0;i<arr.length;i++) {
                            $("#gridTable").jqGrid('setSelection',arr[i],false);
                        }
                    }
                }, 0);
            },onSelectRow: function(rowId,status) {
                var deviceIdGroups = $("#deviceIdGroups").val();
                var tempData = $("#gridTable").jqGrid('getRowData', rowId);
                if (status) {//选中
                    if (deviceIdGroups.indexOf(rowId) == -1) {
                        if (isEmpty(deviceIdGroups)) {
                            deviceIdGroups = rowId + ",";
                        } else {
                            deviceIdGroups += rowId + ",";

                        }
                        $("#deviceIdGroups").val(deviceIdGroups);

                        $("#selectNums").html(parseInt($("#selectNums").html()) + 1);
                    }
                } else {//取消
                    if (deviceIdGroups.indexOf(rowId) != -1) {
                        deviceIdGroups = deviceIdGroups.replace(rowId + ",", "");
                        $("#deviceIdGroups").val(deviceIdGroups);
                        $("#selectNums").html(parseInt($("#selectNums").html()) - 1);
                    }
                }
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
            var deviceIdGroups = $("#deviceIdGroups").val();

           /* if (isEmpty(deviceIdGroups) || isEmpty(isorts) ) {*/
            if (isEmpty(deviceIdGroups )) {
                alertDel('<spring:message code="syscon.operator.device.select" />');
                return;
            }
            parent.selectDeviceGroup(deviceIdGroups.substr(0,deviceIdGroups.length-1));
            parent.layer.close(index);
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


