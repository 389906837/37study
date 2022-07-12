<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备详细信息模板列表</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code="tpmodel.device.management.information.template.management" /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder='<spring:message code="tpmodel.template.number" />...'
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sname" id="sname" value=""
                                       placeholder='<spring:message code="tpmodel.template.name" />...' class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control" name="istatus" id="istatus">
                                    <option value=""><spring:message code="tpmanage.template.status" /></option>
                                    <option value="10"><spring:message code="main.enable" /></option>
                                    <option value="20"><spring:message code="tpmanage.disable" /></option>
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
                        <div class="layui-form-item">
                            <shiro:hasPermission name="TP_DEVICE_MODEL_TEMPLATE_ADD">
                            <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"><i
                                    class="layui-icon"></i><spring:message code="main.add" />
                            </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="TP_DEVICE_MODEL_TEMPLATE_DELETE">
                            <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i><spring:message code="main.delete" />
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
        {name: 'sname', index: 'SNAME', label: "<spring:message code='tpmodel.template.name' />",width:320},
        {
            name: 'istatus',
            index: 'ISTATUS',
            label: "<spring:message code='tpmanage.template.status' />",
            width:320,
            formatter: "select",
            editoptions: {value: '10:<spring:message code="main.enable" />;20:<spring:message code="main.disable" />'}

        },
        {
            name: 'taddTime', index: 'TADD_TIME', label: '<spring:message code="sp.brand.add.date" />',width:320, formatter: function (value) {
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }
        },
        {name: "process", title: false, index: "process", label: '<spring:message code="main.operation" />',width:320, sortable: false, frozen: true}
    ];

    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
            var data = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = '';
                <shiro:hasPermission name="TP_DEVICE_MODEL_TEMPLATE_VIEW">
                str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"viewMethod('"
                    + cl + "')\" title=\"<spring:message code='sh.view' />\" alt=\"<spring:message code='sh.view' />\" /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="TP_DEVICE_MODEL_TEMPLATE_EDIT">
                str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                    + cl + "')\" title=\"<spring:message code='main.edit' />\" alt=\"<spring:message code='main.edit' />\" /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="TP_DEVICE_MODEL_TEMPLATE_DELETE">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='<spring:message code="main.delete" />' alt='<spring:message code="main.delete" />'  /> | ";
                </shiro:hasPermission>
                if (data[i].istatus == 10) {
                    <shiro:hasPermission name="TP_DEVICE_MODEL_TEMPLATE_CHANGESTATUS">
                    str += "<img src='${staticSource}/resources/images/oper_icon/disable.png' class=\"oper_icon\" onclick=\"disableMethod('"
                        + cl + "')\" title='停用' alt='停用'  /> | ";
                    </shiro:hasPermission>
                } else if (data[i].istatus == 20) {
                    <shiro:hasPermission name="TP_DEVICE_MODEL_TEMPLATE_CHANGESTATUS">
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
        initTable(ctx + "/deviceModelTemplate/list/queryData", colModel, tableCallBack, config);
    });
    var initBtnConfig = {
        addUrl: ctx + '/deviceModelTemplate/toAdd',
        deleteUrl: ctx + "/deviceModelTemplate/delete",
        addTitle: '<spring:message code="tpmanage.add.template.information" />',
        addFn: showLayerMedium
    };
    initBtnByForm4(initBtnConfig);

    function viewMethod(sid) {
        showLayerMedium('<spring:message code="tpmanage.template.details" />', ctx + '/deviceModelTemplate/view?sid=' + sid);
    }
    function editMethod(sid) {
        showLayerMedium('<spring:message code="tpmanage.edit.template.information" />', ctx + '/deviceModelTemplate/toEdit?sid=' + sid);
    }
    function delMethod(sid) {
        confirmDelTip('<spring:message code="tpmanage.make.sure.you.want.to.delete.data" />?', ctx + "/deviceModelTemplate/delete", {checkboxId: sid});
    }
    function disableMethod(sid) {
        confirmOperTip('<spring:message code="tpmanage.make.sure.you.want.to.deactivate.the.template" />?', ctx + "/deviceModelTemplate/changeStatus", {checkboxId: sid});
    }
    function enableMethod(sid) {
        confirmOperTip('<spring:message code="tpmanage.make.sure.you.want.to.enable.the.template" />?', ctx + "/deviceModelTemplate/changeStatus", {checkboxId: sid});
    }
</script>
</body>
</html>



