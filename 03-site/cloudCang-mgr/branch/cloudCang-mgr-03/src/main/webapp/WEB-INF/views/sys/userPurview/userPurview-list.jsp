<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>用户权限</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet"/>
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code='menu.user.rights' /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="suserName" id="suserName" value="" placeholder="<spring:message code='main.username' />..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="srealName" id="srealName" value="" placeholder="<spring:message code='sh.name' />..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="smobile" id="smobile" value="" placeholder="<spring:message code='sh.phone.number' />..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sroleName" id="sroleName" value="" placeholder='<spring:message code="hy.character.name" />...'
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="spurName" id="spurName" value="" placeholder="<spring:message code='sh.privilege.name' />..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="spurCode" id="spurCode" value="" placeholder="<spring:message code='hy.authority.code' />..."
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
            {name: 'suserName', index: 'suser_name', label: "<spring:message code='main.username' />", width: 150},
            {name: 'srealName', index: 'sreal_name', label: "<spring:message code='sh.name' />", width: 150},
            {name: 'smobile', index: 'smobile', label: "<spring:message code='sh.phone.number' />", width: 150},
            {name: 'sroleName', index: 'srole_name', label: '<spring:message code="hy.character.name" />', width: 150},
            {name: 'spurName', index: 'spur_name', label: "<spring:message code='sh.privilege.name' />", width: 150},
            {name: 'spurCode', index: 'spur_code', label: "<spring:message code='hy.authority.code' />", width: 150},
        ];
    $(function () {
        var config = {
            multiselect:false
        };
        initTable(ctx + "/userPurview/queryData", colModel,"",config);
    });

    initBtnByForm2();

</script>
</body>
</html>

