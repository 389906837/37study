<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>运营参数管理</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet"/>
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css" rel="stylesheet"/>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code='sh.operational.parameter.management' /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">

                            <div class="layui-input-inline">
                                <input type="text" name="name" id="name" value="" placeholder='<spring:message code="sys.business.para.name" />...'
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="value" id="value" value="" placeholder='<spring:message code="sys.business.parameter.values" />...'
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="remark" id="remark" value="" placeholder='<spring:message code="sys.business.description.parameters" />...'
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
                            <shiro:hasPermission name="businessPara_insert">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" style="margin-left: 0px;"
                                        data-type="add"><i class="layui-icon"></i><spring:message code="main.add" />
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="businessPara_delete">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i><spring:message code="main.delete" />
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="ZKP_BUSINESSPARA">
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
        {name: 'name', index: 'name', label: '<spring:message code="sys.business.para.name" />', width: 360},
        {name: 'value', index: 'value', label: '<spring:message code="sys.business.parameter.values" />', width: 180},
        {name: 'sort', index: 'sort', label: '<spring:message code="main.sort" />', width: 160},
        {name: 'remark', index: 'remark', label: '<spring:message code="sys.business.description.parameters" />', sortable: false, width: 470},
        {name: 'process', index: 'process', title: false, label: '<spring:message code="main.operation" />', sortable: false, width: 400}
    ];
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
            var ids = $("#gridTable").jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                var cl = ids[i];
                var str = '';
                <shiro:hasPermission name="businessPara_update">
                str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                    + cl + "')\" title='<spring:message code='syscon.modify.business.para' />' alt='<spring:message code='syscon.modify.business.para' />'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="businessPara_delete">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='<spring:message code='sh.delete.operational.parameters' />' alt='<spring:message code='sh.delete.operational.parameters' />'  /> | ";
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
        initTable(ctx + "/businessPara/query", colModel, tableCallBack, config);
    });
    //初始化form中按钮
    var initBtnConfig = {
        addUrl: ctx + '/businessPara/edit',
        deleteUrl: ctx + "/businessPara/delete",
        updatecacheUrl: ctx + '/zooSyn/cacheBs?t=' + new Date(),
        addTitle: '<spring:message code='sh.add.operational.parameter.information' />',
        addFn: showLayerOne
    };
    initBtnByForm5(initBtnConfig);
    function editMethod(sid) {
        showLayerOne("<spring:message code='sh.edit.operational.parameter.information' />", ctx + '/businessPara/edit?sid=' + sid);
    }
    function delMethod(sid) {
        confirmDelTip('<spring:message code="main.delete.confirm" />', ctx + "/businessPara/delete", {checkboxId: sid});
    }

</script>
</body>
</html>

