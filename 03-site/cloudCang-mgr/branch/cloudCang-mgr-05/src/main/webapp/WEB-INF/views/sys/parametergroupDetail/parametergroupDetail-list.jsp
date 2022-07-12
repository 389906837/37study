<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<c:if test="${'SP000000' eq groupNo }">
    <title>系统参数配置</title>
</c:if>
<c:if test="${'SP000010' eq groupNo }">
    <title>黑白名单配置</title>
</c:if>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet"/>
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css" rel="stylesheet"/>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <c:if test="${'SP000000' eq groupNo }">
                        <h5><spring:message code='sh.system.parameter.configuration' /></h5>
                    </c:if>
                    <c:if test="${'SP000010' eq groupNo }">
                        <h5><spring:message code='sh.black.and.white.list.configuration' /></h5>
                    </c:if>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="sname" id="sname" value="" placeholder="<spring:message code='sys.business.para.name' />..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="svalue" id="svalue" value="" placeholder="<spring:message code='sys.business.parameter.values' />..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sremark" id="sremark" value="" placeholder="<spring:message code='sys.business.description.parameters' />..."
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
                            <shiro:hasPermission name="ADDPARADETAIL_${groupNo}">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" style="margin-left: 0px;"
                                        data-type="add"><i class="layui-icon"></i><spring:message code="main.add" />
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="DELPARADETAIL_${groupNo}">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i><spring:message code="main.delete" />
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="ZKP_${groupNo}">
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
    var title = "";
    <c:if test="${'SP000000' eq groupNo }">
        title = "<spring:message code='menu.system.parameter' />";
    </c:if>
    <c:if test="${'SP000010' eq groupNo }">
        title = "<spring:message code='menu.black.and.white.list' />";
    </c:if>
    var colModel = [{name: 'id', index: 'id', hidden: true},
        {name: 'sname', index: 'sname', label: "<spring:message code='sys.business.para.name' />", width: 300},
        {name: 'svalue', index: 'svalue', label: "<spring:message code='sys.business.parameter.values' />", width: 240},
        {name: 'isort', index: 'isort', label: '<spring:message code="main.sort" />', width: 160},
        {name: 'sremark', index: 'sremark', sortable: false, label: "<spring:message code='sys.business.description.parameters' />", width: 450}, /*sortable: false,*/
        {name: 'process', index: 'process',title:false, label: '<spring:message code="main.operation" />', sortable: false, width: 400}
    ];
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "scroll" });
            var ids = $("#gridTable").jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                var cl = ids[i];
                var str = '';
                <shiro:hasPermission name="UPPARADETAIL_${groupNo}">
                    str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                        + cl + "')\" title='<spring:message code='sh.edit.data' />' alt='<spring:message code='sh.edit.data' />'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="DELPARADETAIL_${groupNo}">
                    str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                        + cl + "')\" title='<spring:message code='sh.delete.data' />' alt='<spring:message code='sh.delete.data' />'  /> | ";
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
            sortname: "isort",
            sortorder: "asc"
        };
        initTable(ctx + "/paramGroupDetail/${groupNo}/queryParamGroupDetail", colModel, tableCallBack, config);
    });
    //初始化form中按钮
    var initBtnConfig = {
        addUrl: ctx + '/paramGroupDetail/${groupNo}/edit',
        deleteUrl:  ctx + "/paramGroupDetail/${groupNo}/delete",
        updatecacheUrl: ctx + '/zooSyn/cacheDic?t=' + new Date(),
        addTitle:"<spring:message code='main.add' />"+title+"<spring:message code='main.information' />",
        addFn: showLayerMin
    };
    initBtnByForm5(initBtnConfig);

    function editMethod(sid) {
        showLayerMin("<spring:message code='main.edit' />"+title+"<spring:message code='main.information' />", ctx + '/paramGroupDetail/${groupNo}/edit?sid=' + sid);
    }
    function delMethod(sid) {
        confirmDelTip('<spring:message code="main.delete.confirm" />', ctx + "/paramGroupDetail/${groupNo}/delete", {checkboxId: sid});
    }
</script>
</body>
</html>

