<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title><spring:message code="activity.select.equipment" /></title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?001" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
<style>
    .ui-jqgrid tr.jqgrow td {
        white-space: normal !important;
        height: auto;
    }
    .wrapper-h{
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
                            <input type="text" name="scode" id="scode" value="" placeholder='<spring:message code="main.device.id" />...'
                                   class="layui-input">
                        </div>
                        <div class="layui-input-inline">
                            <input type="text" name="sname" id="sname" value="" placeholder='<spring:message code="main.device.name" />...'
                                   class="layui-input">
                        </div>
                        <div class="layui-input-inline">
                            <input type="text" name="sputAddress" id="sputAddress" value="" placeholder='<spring:message code="main.device.address" />...'
                                   class="layui-input">
                        </div>
                        <div class="layui-input-inline">
                            <select class="form-control"  name="istatus" id="istatus" >
                                <option value=""><spring:message code="sb.equipment.status.select" /></option>
                                <option value="10"><spring:message code='sb.unregistered'/></option>
                                <option value="20"><spring:message code="main.normal" /></option>
                                <option value="30"><spring:message code='sb.fault' /></option>
                                <option value="40"><spring:message code='sb.scrap' /></option>
                                <option value="50"><spring:message code='sb.offline' /></option>
                            </select>
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

            <input id="deviceIds" name="deviceIds" value="${deviceIds}" type="hidden"/>
            <input id="deviceCodes" name="deviceCodes" value="${deviceCodes}" type="hidden"/>
        </div>
    </div>

</div>
<div class="layui-form-item fixed-bottom">
    <div class="layui-input-block">
        <label class="lable-left">${fn:replace('<spring:message code="sb.devices.have.been.selected" />','{0}' , (empty selectNums ? 0 : selectNums))}</label>
        <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
        <button class="layui-btn" id="confirmBtn"><spring:message code="main.confirm" /></button>
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
            url: ctx + "/common/queryDeviceData",
            datatype: "json",
            height: 'auto',
            width: $(".ibox-content").width(),
            postData: {allIstatus: '${allIstatus}'},
            shrinkToFit: true,
            rowNum: rownum,
            rowList: rowList,
            rownumbers: true,
            multiselect: true,
            colModel: [{name: 'id', index: 'id', hidden: true},
                {name: 'scode', index: 'scode', label: '<spring:message code="main.device.id" />'},
                {name: 'sname', index: 'sname', label: '<spring:message code="main.device.name" />'},
                {name: 'address', label: "<spring:message code='sb.address'/>", sortable: false},
                {name: 'sgroupName', index: 'SGROUP_NAME', label: "<spring:message code='sb.device.grouping' />", sortable: false},
                {name: 'istatus',index: 'ISTATUS',label: '<spring:message code="sb.equipment.status" />',
                    formatter: function(value, options, rowObject) { return '<span id="istatus_'+rowObject.id+'" data="'+value+'" class="' + (({"10":"istatus-normal","20":"","30":"istatus-warm","40":"istatus-radius","50":"istatus-danger"})[value]) + '">' +  (({"10":'● 未注册',"20":'● <spring:message code="main.normal" />',"30":'● <spring:message code='sb.fault' />',"40":'● <spring:message code='sb.scrap' />',"50":'● <spring:message code='sb.offline' />'})[value])+ '</span>' },
                    width:80
                }
            ],
            pager: "#gridPager",
            viewrecords: true,
            sortname: "",
            sortorder: "",
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
                /*alertDel("<spring:message code='sb.please.select.a.device' />");
                 return;*/
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


