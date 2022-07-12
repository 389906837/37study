<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>人脸注册信息列表</title>
<link href="${staticSource}/resources/layui/css/layui.css?5" rel="stylesheet">
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
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code='fr.face.registration.information.management' /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="sfaceCode" id="sfaceCode" value="" placeholder="<spring:message code='fr.face.information.number' />..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="srealName" id="srealName"  placeholder="<spring:message code='fr.username' />..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control" name="iauditStatus" id="iauditStatus">
                                    <option value=""><spring:message code='sb.please.select.the.audit.status' /></option>
                                    <option value="10"><spring:message code="main.audited" /></option>
                                    <option value="20"><spring:message code="main.approval" /></option>
                                    <option value="30"><spring:message code="main.rejection" /></option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control" name="itype" id="itype">
                                    <option value=""><spring:message code="main.register.status.select" /></option>
                                    <option value="10"><spring:message code="main.registered" /></option>
                                    <option value="20"><spring:message code='fr.deleted' /></option>
                                    <option value="30"><spring:message code='fr.already.applied' /></option>
                                </select>
                            </div>
                            <%--<div class="layui-input-inline">--%>
                            <%--<input type="text" name="sauditUser" id="sauditUser" value="" placeholder='<spring:message code="main.reviewer" />...' class="layui-input">--%>
                            <%--</div>--%>
                        </div>
                        <div class="layui-form-item" style="margin-top: 10px;">
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
                            <shiro:hasPermission name="ANNUAL_MEETINGFACE_INFO_ADD">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"><i
                                        class="layui-icon"></i><spring:message code="main.add" />
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="ANNUAL_MEETINGFACE_INFO_DELETE">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i><spring:message code="main.delete" />
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="ANNUAL_MEETINGFACE_INFO_BATCHIMPORT">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="faceBatchImport"><i
                                        class="layui-icon"></i><spring:message code='vrsku.batch.import' />
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
        {name: 'sfaceCode', index: 'SFACE_CODE', label: "<spring:message code='fr.face.information.number' />",width:100},
        {name: 'srealName', index: 'SREAL_NAME', label: "<spring:message code='fr.username' />",width:100},
        {name: 'sgroupId', index: 'SGROUP_ID', label: "<spring:message code='fr.user.group' />",width:100},
        {
            name: 'iauditStatus',
            index: 'IAUDIT_STATUS',
            label: "<spring:message code='sb.audit.status' />",
            formatter: "select",
            editoptions: {value: '10:<spring:message code="main.audited" />;20:<spring:message code="main.approval" />;30:<spring:message code="main.rejection" />'}
            ,width:100
        },
        {
            name: 'itype',
            index: 'ITYPE',
            label: "<spring:message code='fr.registration.status' />",
            formatter: "select",
            editoptions: {value: '10:<spring:message code="main.registered" />;20:<spring:message code='fr.deleted' />;30:<spring:message code='fr.already.applied' />'}
            ,width:100
        },
        {
            name: 'iregSource',
            index: 'IREG_SOURCE',
            label: "<spring:message code='fr.registration.source' />",
            formatter: "select",
            editoptions: {value: '10:<spring:message code='fr.backstage' />;20:<spring:message code='main.device' />;30:H5<spring:message code='fr.page' />'}
            ,width:100
        },
        {name: 'tregisterTime', index: 'TREGISTER_TIME', label: '<spring:message code="sb.registration.time" />',width: 80,formatter: function (value) {
                if(isEmpty(value)){
                    return '';
                }
                return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');}},

        {name: "process", title:false, index: "process", label: '<spring:message code="main.operation" />', sortable: false, frozen: true, width: 100}
    ];
    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            var data = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = '';
                <shiro:hasPermission name="ANNUAL_MEETINGFACE_INFO_VIEW">
                str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"viewMethod('"
                    + cl + "')\" title=\"<spring:message code='sh.view' />\" alt=\"<spring:message code='sh.view' />\" /> | ";
                </shiro:hasPermission>
                if (data[i].iauditStatus == 10) {
                    <shiro:hasPermission name="ANNUAL_MEETINGFACE_INFO_EDIT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                        + cl + "')\" title=\"<spring:message code='main.edit' />\" alt=\"<spring:message code='main.edit' />\" /> | ";
                    </shiro:hasPermission>
                    <shiro:hasPermission name="ANNUAL_MEETINGFACE_INFO_AUDIT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/review.png' class=\"oper_icon\" onclick=\"auditMethod('"
                        + cl + "')\" title='<spring:message code='main.audit' />' alt='<spring:message code='main.audit' />'  /> | ";
                    </shiro:hasPermission>
                }
                if ((data[i].iauditStatus == 10 && data[i].itype != 20) || (data[i].iauditStatus == 30 && data[i].itype != 20)) {
                    <shiro:hasPermission name="ANNUAL_MEETINGFACE_INFO_DELETE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                        + cl + "')\" title='<spring:message code="main.delete" />' alt='<spring:message code="main.delete" />'  /> | ";
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
            sortname: "ITYPE=10",
            sortorder: "desc"
        };
        initTable(ctx+"/annualMeetingFaceInfo/queryData",colModel,tableCallBack,config);
    });

    var initBtnConfig = {
        addUrl: ctx+'/annualMeetingFaceInfo/toAdd',
        deleteUrl: ctx+"/annualMeetingFaceInfo/delete",
        addTitle: '<spring:message code='fr.add.face.registration.information' />',
        addFn:showLayerMedium
    };
    initBtnByForm4(initBtnConfig);

    layui.use('form', function () {
        var $ = layui.$, active = {
            faceBatchImport : function () {
                showLayerOne("<spring:message code='fr.import.face.information.in.batches' />", ctx + "/annualMeetingFaceInfo/batchImport");
            }
        };
        $('.layui-form .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });

    function viewMethod(sid) {
        showLayerOne("<spring:message code='fr.view.face.registration.information' />", ctx + '/annualMeetingFaceInfo/toView?sid=' + sid);
    }
    function editMethod(sid) {
        showLayerOne("<spring:message code='fr.edit.face.registration.information' />", ctx + '/annualMeetingFaceInfo/toEdit?sid=' + sid);
    }
    function auditMethod(sid) {
        showLayerOne("<spring:message code='fr.review.face.registration.information' />", ctx + '/annualMeetingFaceInfo/toAudit?sid=' + sid);
    }
    function delMethod(sid) {
        confirmDelTip('<spring:message code="main.delete.confirm" />', ctx + "/annualMeetingFaceInfo/delete", {checkboxId: sid});
    }
</script>
</body>
</html>


