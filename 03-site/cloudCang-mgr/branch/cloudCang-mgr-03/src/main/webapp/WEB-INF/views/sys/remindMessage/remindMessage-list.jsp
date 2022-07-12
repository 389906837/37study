<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>温馨提醒配置</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet"/>
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css" rel="stylesheet"/>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code='sh.warm.reminder.configuration' /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder="<spring:message code='main.code' />..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sname" id="sname" value="" placeholder="<spring:message code='main.name' />..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control" name="itype" id="itype">
                                    <option value=""><spring:message code='sh.publishing.platform' /></option>
                                    <c:forEach items="${meeTypeMap}" var="mymap">
                                        <option value="${mymap.key}">${mymap.value}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="svalue" id="svalue" value="" placeholder="<spring:message code='sh.message.value' />..."
                                       class="layui-input">
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
                            <shiro:hasPermission name="ADD_REMINDMESSAGE">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" style="margin-left: 0px;"
                                        data-type="add"><i class="layui-icon"></i><spring:message code="main.add" />
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="DEL_REMINDMESSAGE">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i><spring:message code="main.delete" />
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="ZKP_REMINDMESSAGE">
                                <button class="layui-btn layui-btn-sm layui-btn-primary" data-type="refreshCache"><i class="layui-icon layui-icon-refresh"></i><spring:message code="main.refresh.cache" />
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
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    var colModel = [{name: 'id', index: 'id', hidden: true},
        {name: 'scode', index: 'scode', label: "<spring:message code='sh.warm.reminder.number' />", width: 150},
        {name: 'sname', index: 'sname', label: "<spring:message code='sh.warm.reminder.name' />", width: 200},
        {
            name: 'itype', index: 'itype', label: "<spring:message code='sh.publishing.platform' />", width: 150, formatter: "select",
            editoptions: {value: '1:web;2:wap;3:app;4:<spring:message code='sh.mobile.end' />'}
        },
        {name: 'svalue', index: 'svalue', label: "<spring:message code='sh.message.value' />", width: 120},
        {name: 'sort', index: 'sort', label: '<spring:message code="main.sort" />', width: 80},
        {name: 'remark', index: 'remark', label: '<spring:message code="main.remarks" />', sortable: false, width: 200},
        {name: 'process', index: 'process',title:false, label: '<spring:message code="main.operation" />', sortable: false, width: 150}
    ];
    function tableCallBack() {
        setTimeout(function () {
            var ids = $("#gridTable").jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                var cl = ids[i];
                var str = '';
                <shiro:hasPermission name="EDIT_REMINDMESSAGE">
                str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                    + cl + "')\" title='<spring:message code='syscon.modify.remind.log' />' alt='<spring:message code='syscon.modify.remind.log' />'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="DEL_REMINDMESSAGE">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='<spring:message code='sh.delete.warm.reminder' />' alt='<spring:message code='sh.delete.warm.reminder' />'  /> | ";
                </shiro:hasPermission>
                $("#gridTable").jqGrid('setRowData',
                    ids[i], {
                        process: str.substr(0, str.lastIndexOf(" | "))
                    });
            }
        }, 0);
    }
    $(function () {
        var config = {
            sortname: "sort",
            sortorder: "asc"
        };
        initTable(ctx + "/remindMessage/queryData", colModel, tableCallBack, config);
    });
    //初始化form中按钮
    var initBtnConfig = {
        addUrl: ctx + '/remindMessage/edit',
        deleteUrl: ctx + "/remindMessage/delete",
        addTitle: '<spring:message code='sh.add.a.warm.reminder' />',
        addFn:showLayerMin,
        updatecacheUrl: ctx + '/zooSyn/cacheRemindMessage?t=' + new Date()
    };
    initBtnByForm5(initBtnConfig);

    function editMethod(sid) {
        showLayerMin("<spring:message code='sh.edit.warm.reminder.information' />", ctx + '/remindMessage/edit?sid=' + sid);
    }
    function delMethod(sid) {
        confirmDelTip('<spring:message code="main.delete.confirm" />', ctx + "/remindMessage/delete", {checkboxId: sid});
    }
</script>
</body>
</html>

