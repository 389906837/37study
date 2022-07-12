<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title><spring:message code="sb.background.operational.record.of.equipment" /></title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code="sb.background.operational.record.of.equipment" /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <select class="form-control" name="itype" id="itype">
                                    <option value=""><spring:message code="sb.type.of.operation" /></option>
                                    <option value="10"><spring:message code="sb.adjust.volume.for" /></option>
                                    <option value="20"><spring:message code="sb.reboot.device" /></option>
                                    <option value="30"><spring:message code="sb.shutdown" /></option>
                                    <option value="40"><spring:message code="sb.timing.switch" /></option>
                                    <option value="50"><spring:message code="sb.active.inventory" /></option>
                                    <option value="60"><spring:message code="sb.cloud.shopping.cart.open" /></option>
                                    <option value="70"><spring:message code="sb.cloud.shopping.cart.closed" /></option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control" name="ideviceType" id="ideviceType">
                                    <option value=""><spring:message code="sb.operating.object.type" /></option>
                                    <option value="10"><spring:message code="main.whole" /></option>
                                    <option value="20"><spring:message code="main.part" /></option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="saddUser" id="saddUser" value="" placeholder='<spring:message code="sb.operator" />...'
                                       class="layui-input">
                            </div>
                            <div class="layui-inline">
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
        {   name: 'itype',
            index: 'ITYPE',
            label: '<spring:message code="sb.type.of.operation" />',
            formatter: "select",
            editoptions: {value: '10:<spring:message code="sb.adjust.volume.for" />;20:<spring:message code="sb.reboot.device" />;30:<spring:message code="sb.shutdown" />;40:<spring:message code="sb.timing.switch" />;50:<spring:message code="sb.active.inventory" />;60:<spring:message code="sb.cloud.shopping.cart.open" />;70:<spring:message code="sb.cloud.shopping.cart.closed" />'},
            width:320
        },
        {   name: 'ideviceType',
            index: 'IDEVICE_TYPE',
            label: '<spring:message code="sb.operating.object.type" />',
            formatter: "select",
            editoptions: {value: '10:<spring:message code="main.whole" />;20:<spring:message code="main.part" />'},
            width:320
        },
        {name: 'saddUser', index: 'SADD_USER', label: '<spring:message code="sb.operator" />',width:300},
        {name: 'taddTime', index: 'TADD_TIME', label: '<spring:message code="sb.date.of.operation" />',formatter: function (value) {
                return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
            },width:320},
        {name: "process",title:false, index: "process", label: '<spring:message code="main.operation" />', sortable: false, frozen: true, width: 400}
    ];

    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
            var data = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = '';
                <shiro:hasPermission name="DEVICE_BACKSTAGEOPER_VIEW">
                str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"viewMethod('"
                    + cl + "')\" title=\"<spring:message code='sh.view' />\" alt=\"<spring:message code='sh.view' />\" /> | ";
                </shiro:hasPermission>
                $("#gridTable").jqGrid('setRowData',
                    cl, {
                        process: str.substr(0, str.lastIndexOf(" | "))
                    });
            }
        }, 0);
    }

    $(function () {
        var config = {
            multiselect:false,
            sortname: "TADD_TIME",
            sortorder: "desc"
        };
        initTable(ctx+"/device/backstageOper/queryData",colModel,tableCallBack,config);
    });

    initBtnByForm2();

    function viewMethod(sid) {
        showLayerMedium('<spring:message code="sb.operational.content" />/<spring:message code="sb.object.details" />', ctx + '/device/backstageOper/view?sid=' + sid);
    }

</script>
</body>
</html>



