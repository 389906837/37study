<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>数据字典管理</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet"/>
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css" rel="stylesheet"/>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code='sh.data.dictionary.management' /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="sgroupNo" id="sgroupNo" value="" placeholder="<spring:message code='sh.data.dictionary.number' />..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sgroupName" id="sgroupName" value="" placeholder="<spring:message code='sh.data.dictionary.name' />..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control" name="bisModify" id="bisModify">
                                    <option value=""><spring:message code='sh.is.it.modifiable' /></option>
                                    <option value="0"><spring:message code="main.no" /></option>
                                    <option value="1"><spring:message code="main.yes" /></option>
                                </select>
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
                            <shiro:hasPermission name="paraMgr_addParamGroup">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" style="margin-left: 0px;"
                                        data-type="add"><i class="layui-icon"></i><spring:message code="main.add" />
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="paraMgr_delParamGroup">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i><spring:message code="main.delete" />
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="ZKP_DATADICT">
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
        {name: 'sgroupNo', index: 'sgroup_no', label: "<spring:message code='sh.data.dictionary.number' />", width: 260},
        {name: 'sgroupName', index: 'sgroup_name', label: "<spring:message code='sh.data.dictionary.name' />", width: 320},
        {
            name: 'bisModify',
            index: 'bis_modify',
            label: "<spring:message code='sh.is.it.modifiable' />",
            width: 150,
            sortable: false,
            formatter: "select",
            editoptions: {value: '0:<spring:message code="main.no" />;1:<spring:message code="main.yes" />'}
        },
        {name: 'sremark', index: 'sremark', label: '<spring:message code="main.remarks" />', sortable: false, width: 430},
        {name: 'process', index: 'process',title:false, label: '<spring:message code="main.operation" />', sortable: false, width: 400}
    ];

    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
            var ids = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < ids.length; i++) {
                var cl = ids[i];
                var str = "";
                <shiro:hasPermission name="paraMgr_upParamGroup">
                str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                    + cl.sgroupNo + "')\" title=\"<spring:message code='sh.edit.data.dictionary' />\" alt=\"<spring:message code='sh.edit.data.dictionary' />\"  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="paraMgr_delParamGroup">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl.id + "')\" title=\"<spring:message code='sh.delete.data.dictionary' />\" alt=\"<spring:message code='sh.delete.data.dictionary' />\"  /> | ";
                </shiro:hasPermission>
                $("#gridTable").jqGrid('setRowData',
                    cl.id, {
                        process: str.length > 0 ? str.substr(0, str.lastIndexOf(" | ")) : ""
                    });
            }
        }, 0);
    }
    $(function () {
        var config = {
            sortname: "sgroup_no",
            sortorder: "asc"
        };
        initTable(ctx + "/paramGroup/query", colModel, tableCallBack, config);
    });
    //初始化form中按钮
    var initBtnConfig = {
            addUrl: ctx + '/paramGroup/edit',
            deleteUrl: ctx + "/paramGroup/delete",
            updatecacheUrl: ctx + '/zooSyn/cacheDic?t=' + new Date(),
            addTitle: "<spring:message code='sh.add.data.dictionary.information' />",
            addFn:showLayerMedium
        }
    ;
    initBtnByForm5(initBtnConfig);

    function refreshTable() {
        reloadGrid(true);
    }

    function editMethod(groupNo) {
        showLayerMedium("<spring:message code='sh.edit.data.dictionary.information' />", ctx + '/paramGroup/edit?groupNo=' + groupNo);
    }
    function delMethod(sid) {
        confirmDelTip('<spring:message code="main.delete.confirm" />', ctx + "/paramGroup/delete", {checkboxId: sid});
    }
</script>
</body>
</html>

