<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>编号配置管理</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet"/>
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css" rel="stylesheet"/>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code='sh.number.configuration.management' /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="scodeType" id="scodeType" value="" placeholder='<spring:message code="sys.code.type" />...'
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
                            <shiro:hasPermission name="codeGen_add">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" style="margin-left: 0px;"
                                        data-type="add"><i class="layui-icon"></i><spring:message code="main.add" />
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="codeGen_delete">
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
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    var colModel = [{name: 'id', index: 'id', hidden: true},
        {name: 'scodeType', index: 'scode_type', label: '<spring:message code="sys.code.type" />', width: 220},
        {name: 'icodeStart', index: 'icode_start', label: "<spring:message code='sys.initial.code' />", width: 220},
        {name: 'icodeStep', index: 'icode_step', label: "<spring:message code='sys.code.setp' />", width: 220},
        {name: 'scodePrefix', index: 'scode_prefix', label: "<spring:message code='sys.code.prefix' />", width: 200},
        {name: 'icodeSize', index: 'icode_size', label: "<spring:message code='sys.code.size' />", width: 200},
        {name: 'scodeDateformat', index: 'scode_dateformat', label: "<spring:message code='main.data.format' />", sortable: false, width: 170},
        {
            name: 'banewBegin',
            index: 'banew_begin',
            label: "<spring:message code='sh.repeat.daily' />",
            sortable: false,
            width: 160,
            formatter: "select",
            editoptions: {value: '0:<spring:message code="main.no" />;1:<spring:message code="main.yes" />'}
        },
        {name: 'process', index: 'process',title:false, label: '<spring:message code="main.operation" />', sortable: false, width: 180}
    ];
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
            var ids = $("#gridTable").jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                var cl = ids[i];
                var str = "";
                <shiro:hasPermission name="codeGen_update">
                str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                    + cl + "')\" title='<spring:message code='sh.edit.number' />' alt='<spring:message code='sh.edit.number' />'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="codeGen_delete">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='<spring:message code='sh.delete.number' />' alt='<spring:message code='sh.delete.number' />'  /> | ";
                </shiro:hasPermission>
                $("#gridTable").jqGrid('setRowData',
                    ids[i], {
                        process: str.length > 0 ? str.substr(0, str.lastIndexOf(" | ")) : ""
                    });
            }
        }, 0);
    }
    $(function () {
        var config = {
            sortname: "DCURRENT_DATE",
            sortorder: "desc"
        };
        initTable(ctx + "/codeGenerator/query", colModel, tableCallBack, config);
    });
    //初始化form中按钮
    var initBtnConfig = {
        addUrl: ctx + '/codeGenerator/edit',
        deleteUrl: ctx + "/codeGenerator/delete",
        addTitle:'<spring:message code='sh.add.number.configuration.information' />',
        addFn: showLayerMin
    };
    initBtnByForm4(initBtnConfig);

    function editMethod(sid) {
        showLayerMin("<spring:message code='sh.edit.number.configuration.information' />", ctx + '/codeGenerator/edit?sid=' + sid);
    }
    function delMethod(sid) {
        confirmDelTip('<spring:message code="main.delete.confirm" />', ctx + "/codeGenerator/delete", {checkboxId: sid});
    }

</script>
</body>
</html>

