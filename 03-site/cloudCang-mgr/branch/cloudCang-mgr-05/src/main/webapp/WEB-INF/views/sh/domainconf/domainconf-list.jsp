<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>域名管理</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">

</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code="sh.domain.name.management" /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantCode" id="merchantCode" value=""
                                           placeholder='<spring:message code="sh.business.number" />...' class="layui-input">
                                </div>
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantName" id="merchantName" value=""
                                           placeholder='<spring:message code="sh.business.name" />...' class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-input-inline">
                                <input type="text" name="sdomainUrl" id="sdomainUrl" value="" placeholder='<spring:message code="sh.domain.name" />...'
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" name="istatus" id="istatus" entire="true" value=""
                                             list='{10:springMessageCode=sh.in.application=,20:springMessageCode=sh.audit.and.approval,30:springMessageCode=sh.audit.rejection,40:springMessageCode=sh.discontinue.use=}' entireName='springMessageCode=main.state.select' />
                            </div>
                            <div class="layui-input-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i
                                        class="layui-icon">&#xe615;</i><spring:message code="main.query" />
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i
                                        class="layui-icon">&#x1006;</i><spring:message code="main.clear" />
                                </button>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <shiro:hasPermission name="DOMAINCONF_ADD">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"><i
                                        class="layui-icon"></i><spring:message code="sh.add.to" />
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="DOMAINCONF_DELETE">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i
                                        class="layui-icon"></i><spring:message code="main.delete" />
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="DOMAINCONF_CACHE">
                                <button class="layui-btn layui-btn-sm layui-btn-primary" data-type="refreshCache"><i class="layui-icon layui-icon-refresh"></i><spring:message code="sh.update.cache" />
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
    var colModel = [{name: 'id', index: 'id', hidden: true},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'merchantCode', label: '<spring:message code="sh.business.number" />', index: 'merchantCode', sortable: false, width: 200},
        {name: 'merchantName', label: '<spring:message code="sh.business.name" />', index: 'merchantName', sortable: false, width: 360},
        </c:if>
        {name: 'sdomainUrl', index: 'SDOMAIN_URL', label: '<spring:message code="sh.domain.name" />', width: 200},
        {
            name: 'istatus',
            index: 'ISTATUS',
            label: '<spring:message code="sh.state" />',
            width: 200,
            formatter: "select",
            editoptions: {value: '10:<spring:message code="sh.in.application" />;20:<spring:message code="sh.application.passed" />;30:<spring:message code="sh.apply.to.dismiss" />;40:<spring:message code="sh.discontinue.use" />'}
        },
        {name: 'sauditOperName', index: 'SAUDIT_OPER_NAME', label: '<spring:message code="sh.name.of.examiner.and.approver" />', width: 200},
        {name: 'sremark', index: 'sremark', label: '<spring:message code="sh.remarks" />', width: 200},
        {name: 'process', title:false,index: 'process', label: '<spring:message code="sh.operation" />', sortable: false, width: 200}
    ];
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
            var ids = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < ids.length; i++) {
                var str = "";
                var cl = ids[i].id;
                if (ids[i].istatus == 20) {
                    <shiro:hasPermission name="DOMAINCONF_ENABLE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/close.png' class=\"oper_icon\" onclick=\"enableMethod('"
                        + cl + "',40)\" title='<spring:message code="sh.disable" />' alt='<spring:message code="sh.disable" />'  /> | ";
                    </shiro:hasPermission>
                } else {
                    <shiro:hasPermission name="DOMAINCONF_EDIT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                        + cl + "')\" title='<spring:message code="sh.edit.domain.name" />' alt='<spring:message code="sh.edit.domain.name" />'  /> | ";
                    </shiro:hasPermission>
                    <shiro:hasPermission name="DOMAINCONF_DELETE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                        + cl + "')\" title='<spring:message code="sh.delete.domain.name" />' alt='<spring:message code="sh.delete.domain.name" />'  /> | ";
                    </shiro:hasPermission>
                    if (ids[i].istatus == 40) {
                        <shiro:hasPermission name="DOMAINCONF_ENABLE">
                        str += "<img src='${staticSource}/resources/images/oper_icon/open.png' class=\"oper_icon\" onclick=\"enableMethod('"
                            + cl + "',10)\" title='<spring:message code="sh.enable" />' alt='<spring:message code="sh.enable" />'  /> | ";
                        </shiro:hasPermission>
                    } else {
                        <shiro:hasPermission name="DOMAINCONF_ENABLE">
                        str += "<img src='${staticSource}/resources/images/oper_icon/close.png' class=\"oper_icon\" onclick=\"enableMethod('"
                            + cl + "',40)\" title='<spring:message code="sh.disable" />' alt='<spring:message code="sh.disable" />'  /> | ";
                        </shiro:hasPermission>
                        <shiro:hasPermission name="DOMAINCONF_EXAMINE">
                        str += "<img src='${staticSource}/resources/images/order/shenhe.png' class=\"order\" onclick=\"exMethod('"
                            + cl + "')\" title='<spring:message code="sh.review" />' alt='<spring:message code="sh.review" />'  /> | ";
                        </shiro:hasPermission>
                    }
                }
                $("#gridTable").jqGrid('setRowData',
                    cl, {
                        process : str.length > 0 ? str.substr(0, str.lastIndexOf(" | ")) : ""
                    });
            }
        }, 0);
    }
    $(function () {
        var config = {
            sortname: "ISTATUS=10",
            sortorder: "desc"
        };
        initTable(ctx + "/domainconf/queryData", colModel, tableCallBack, config);
    });
    //<spring:message code="sh.initialize.the.form.button" />
    var initBtnConfig = {
        addUrl: ctx + '/domainconf/edit',
        deleteUrl: ctx + "/domainconf/delete",
        addTitle: '<spring:message code="sh.add.domain.name" />',
        addFn:showLayerMin
    };
    initBtnByForm4(initBtnConfig);


    layui.use('form', function () {
        var $ = layui.$, active = {

            refreshCache: function () {//<spring:message code="sh.refresh.cache" />
                var loadIndex = loading();
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: ctx + '/domainconf/cache',
                    success: function (msg) {
                        closeLayer(loadIndex);//关闭加载框
                        if (msg.success) {
                            alertInfo(msg.data)
                        } else {
                            alertDel(msg.data);
                        }
                    }
                });
            }
        };
        $('.layui-form .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });
    function editMethod(sid) {
        showLayerMin('<spring:message code="sh.edit.domain.name" />', ctx + '/domainconf/edit?sid=' + sid);
    }
    function delMethod(sid) {
        confirmDelTip('<spring:message code="sh.make.sure.you.want.to.delete.data" />?', ctx + "/domainconf/delete", {checkboxId: sid});
    }
    function exMethod(sid) {
        showLayerMin('<spring:message code="sh.review.domain.name" />', ctx + '/domainconf/examine?sid=' + sid);
    }
    function enableMethod(sid, type) {
        var alertStr = '<spring:message code="sh.make.sure.you.want.to.enable.the.domain.name" />?';
        if (type == 40) {
            alertStr = '<spring:message code="sh.determine.to.disable.the.domain.name" />?';
        }
        confirmOperTip(alertStr, ctx + "/domainconf/enable", {checkboxId: sid, type: type});
    }
</script>
</body>
</html>

