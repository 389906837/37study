<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备升级记录列表</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
<style>
    .ui-jqgrid tr.jqgrow td {
        white-space: normal !important;
        height: auto;
    }
    .layui-btn-hui{background:#f2f2f2;border:1px solid #e6e6e6;color:#323232;}
    .layui-btn-hui:hover{color:#323232;}
</style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <%--<h5><spring:message code='sb.equipment.commodity.management' /></h5>--%>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="iversion" id="iversion" value="" placeholder="<spring:message code='vrsku.version.number' />..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sresourcesUrl" id="sresourcesUrl" value="" placeholder="<spring:message code='sb.upgrade.package' />url..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control"  name="itype" id="itype" >
                                    <option value=""><spring:message code='sb.upgrade.type' /></option>
                                    <option value="10"><spring:message code='sb.visual.service' /></option>
                                    <option value="20"><spring:message code='sb.vision.library' /></option>
                                    <option value="30"><spring:message code='sb.client' />apk</option>
                                    <option value="40"><spring:message code='sb.open.door.voice' /></option>
                                    <option value="50"><spring:message code='sb.shopping.voice' /></option>
                                    <option value="60"><spring:message code='sb.closed.voice' /></option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control"  name="ideviceType" id="ideviceType" >
                                    <option value=""><spring:message code='sb.upgrade.scope' /></option>
                                    <option value="10"><spring:message code="main.whole" /></option>
                                    <option value="20"><spring:message code="main.part" /></option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control"  name="iactionType" id="iactionType" >
                                    <option value=""><spring:message code='sb.upgrade.time.type' /></option>
                                    <option value="10"><spring:message code='main.immediately' /></option>
                                    <option value="20"><spring:message code='main.timing' /></option>
                                </select>
                            </div>
                            <%--<div class="layui-input-inline">--%>
                                <%--<select class="form-control"  name="istatus" id="istatus" >--%>
                                    <%--<option value=""><spring:message code='sb.finished.condition' /></option>--%>
                                    <%--<option value="10"><spring:message code='vrsku.draft' /></option>--%>
                                    <%--<option value="20"><spring:message code='sb.notified' /></option>--%>
                                    <%--<option value="30"><spring:message code="main.completed" /></option>--%>
                                    <%--<option value="40"><spring:message code="main.cancelled" /></option>--%>
                                <%--</select>--%>
                            <%--</div>--%>
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

                        </div>
                        <div class="layui-form-item">
                            <%-- <button class="layui-btn layui-btn-sm " data-type="add"><i class="layui-icon"></i><spring:message code="main.add" /></button>
                             <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i><spring:message code="main.delete" /></button>--%>
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
        {
            name: 'itype',
            index: 'ITYPE',
            label: "<spring:message code='sb.upgrade.type' />",
            formatter: "select",
            editoptions: {value: '10:<spring:message code='sb.visual.service' />;20:<spring:message code='sb.vision.library' />;30:<spring:message code='sb.client' />Apk;40:<spring:message code='sb.open.door.voice' />;50:<spring:message code='sb.shopping.voice' />;60:<spring:message code='sb.closed.voice' />'}
            , width: 160
        },
        {name: 'iversion', index: 'IVERSION', label: "<spring:message code='vrsku.version.number' />",width:120,sortable : false},
        {name: 'sresourcesUrl', index: 'SRESOURCES_URL', label: "<spring:message code='sb.upgrade.package.url' />",width:200,sortable : false},
        {name: 'ideviceNum', index: 'IDEVICE_NUM', label: "<spring:message code='sb.planned.upgrade.quantity' />",width:160,sortable : false},
        {name: 'ideviceNum', index: 'INOTICE_NUM', label: "<spring:message code='sb.notification.completion.quantity' />",width:190,sortable : false},
        {name: 'iupgradeSuccessNum', index: 'IUPGRADE_SUCCESS_NUM', label: "<spring:message code='sb.number.of.successful.upgrades' />",width:190,sortable : false},
        {name: 'iupgradeFailNum', index: 'IUPGRADE_FAIL_NUM', label: "<spring:message code='sb.number.of.upgrade.failures' />",width:230,sortable : false},
        {
            name: 'ideviceType',
            index: 'IDEVICE_TYPE',
            label: "<spring:message code='sb.upgrade.scope' />",
            formatter: "select",
            editoptions: {value: '10:<spring:message code="main.whole" />;20:<spring:message code="main.part" />'}
            , width: 160
        },
        {
            name: 'istatus',
            index: 'ISTATUS',
            label: "<spring:message code='sb.finished.condition' />",
            formatter: "select",
            editoptions: {value: '10:<spring:message code='vrsku.draft' />;20:<spring:message code='sb.notified' />;30:<spring:message code="main.completed" />;40:<spring:message code="main.cancelled" />;50:<spring:message code='sb.partially.completed' />'}
            , width: 160
        },
        {
            name: 'iactionType',
            index: 'IACTION_TYPE',
            label: "<spring:message code='sb.upgrade.time.type' />",
            formatter: "select",
            editoptions: {value: '10:<spring:message code='main.immediately' />;20:<spring:message code='main.timing' />'}
            , width: 160
        },
        {
            name: 'sexecutionTime', index: 'SEXECUTION_TIME', width: 80,label: "<spring:message code='sb.timing.execution.time' />", formatter: function (value) {
            if(isEmpty(value)){
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }
        },
        {
            name: 'taddTime', index: 'TADD_TIME', width: 160,label: '<spring:message code="main.add.time" />', formatter: function (value) {
            if(isEmpty(value)){
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }
        },
        {name: "process",title:false, index: "process", label: '<spring:message code="main.operation" />', sortable: false, frozen: true, width: 260}
    ];
    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "scroll" });
            var data = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = '';
                <shiro:hasPermission name="DEVICE_UPGRADE_VIEW">
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
            sortname: "A.ISTATUS",
            multiselect:false,
            sortorder: "desc"
        };
        initTable(ctx + "/device/upgrade/queryData",colModel,tableCallBack,config);
    });

    initBtnByForm2();

    function viewMethod(sid) {
        showLayerMax('<spring:message code="sp.commodityBatch.details" />', ctx + '/device/upgrade/toView?sid=' + sid);
    }


</script>
</body>
</html>


