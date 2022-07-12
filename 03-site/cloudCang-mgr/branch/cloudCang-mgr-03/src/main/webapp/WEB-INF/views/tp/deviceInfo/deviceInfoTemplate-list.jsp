<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备基础信息模板列表</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code='tp.equipment.basic.information.template.management' /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder='<spring:message code="tpinfo.template.number" />...'
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sname" id="sname" value=""
                                       placeholder='<spring:message code="tpinfo.template.name" />...' class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control" name="istatus" id="istatus">
                                    <option value=""><spring:message code="tpinfo.template.status" /></option>
                                    <option value="10"><spring:message code="tpinfo.enable" /></option>
                                    <option value="20"><spring:message code="tpinfo.disable" /></option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query">
                                    <i class="layui-icon">&#xe615;</i><spring:message code="tpinfo.inquire" />
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset">
                                    <i class="layui-icon">&#x1006;</i><spring:message code="tpinfo.clear" />
                                </button>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <shiro:hasPermission name="TP_DEVICE_INFO_TEMPLATE_ADD">
                            <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"><i
                                    class="layui-icon"></i><spring:message code="tpinfo.add.to" />
                            </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="TP_DEVICE_INFO_TEMPLATE_DELETE">
                            <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i><spring:message code="tpinfo.delete" />
                            </button>
                            </shiro:hasPermission>
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
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    var colModel = [
        {name: 'id', index: 'id', hidden: true},
        {name: 'scode', index: 'SCODE', label: "<spring:message code='tpmodel.template.number' />",width:320},
        {name: 'sname', index: 'SNAME', label: "<spring:message code='tpmanage.template.name' />",width:320},
        {
            name: 'istatus',
            index: 'ISTATUS',
            label: "<spring:message code='tpinfo.template.status' />",
            width:300,
            formatter: "select",
            editoptions: {value: '10:<spring:message code="main.enable" />;20:<spring:message code="main.disable" />'}

        },
        {
            name: 'taddTime', index: 'TADD_TIME', label: '<spring:message code="sp.brand.add.date" />',width:320, formatter: function (value) {
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }
        },
        {name: "process", title: false, index: "process", label: '<spring:message code="main.operation" />', width:320,sortable: false, frozen: true}
    ];

    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
            var data = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = '';
                <shiro:hasPermission name="TP_DEVICE_INFO_TEMPLATE_VIEW">
                str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"viewMethod('"
                    + cl + "')\" title=\"<spring:message code='sh.view' />\" alt=\"<spring:message code='sh.view' />\" /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="TP_DEVICE_INFO_TEMPLATE_EDIT">
                str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                    + cl + "')\" title=\"<spring:message code='main.edit' />\" alt=\"<spring:message code='main.edit' />\" /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="TP_DEVICE_INFO_TEMPLATE_DELETE">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='<spring:message code="main.delete" />' alt='<spring:message code="main.delete" />'  /> | ";
                </shiro:hasPermission>
                if (data[i].istatus == 10) {
                    <shiro:hasPermission name="TP_DEVICE_INFO_TEMPLATE_CHANGESTATUS">
                    str += "<img src='${staticSource}/resources/images/oper_icon/disable.png' class=\"oper_icon\" onclick=\"disableMethod('"
                        + cl + "')\" title='<spring:message code="sh.disable" />' alt='<spring:message code="sh.disable" />'  /> | ";
                    </shiro:hasPermission>
                } else if (data[i].istatus == 20) {
                    <shiro:hasPermission name="TP_DEVICE_INFO_TEMPLATE_CHANGESTATUS">
                    str += "<img src='${staticSource}/resources/images/oper_icon/enabled.png' class=\"oper_icon\" onclick=\"enableMethod('"
                        + cl + "')\" title='<spring:message code="main.enable" />' alt='<spring:message code="main.enable" />'  /> | ";
                    </shiro:hasPermission>
                }
                $("#gridTable").jqGrid('setRowData',
                    cl, {
                        process: str.substr(0, str.lastIndexOf(" | "))
                    });
            }
        }, 0);
    }

    $(function () {
        var config = {
            sortname: "ISTATUS=10",
            sortorder: "desc"
        };
        initTable(ctx + "/deviceInfoTemplate/list/queryData", colModel, tableCallBack, config);
    });
    var initBtnConfig = {
        addUrl: ctx + '/deviceInfoTemplate/toAdd',
        deleteUrl: ctx + "/deviceInfoTemplate/delete",
        addTitle: '<spring:message code='tpmanage.add.template.information' />',
        addFn: showLayerMedium
    };
    initBtnByForm4(initBtnConfig);

    function viewMethod(sid) {
        showLayerMedium("<spring:message code='tpmanage.template.details' />", ctx + '/deviceInfoTemplate/view?sid=' + sid);
    }
    function editMethod(sid) {
        showLayerMedium("<spring:message code='tpmanage.edit.template.information' />", ctx + '/deviceInfoTemplate/toEdit?sid=' + sid);
    }
    function delMethod(sid) {
        confirmDelTip('<spring:message code="main.delete.confirm" />', ctx + "/deviceInfoTemplate/delete", {checkboxId: sid});
    }
    function disableMethod(sid) {
        confirmOperTip("<spring:message code='tpmanage.make.sure.you.want.to.deactivate.the.template' />?", ctx + "/deviceInfoTemplate/changeStatus", {checkboxId: sid});
    }
    function enableMethod(sid) {
        confirmOperTip("<spring:message code='tpmanage.make.sure.you.want.to.enable.the.template' />?", ctx + "/deviceInfoTemplate/changeStatus", {checkboxId: sid});
    }
</script>
</body>
</html>



