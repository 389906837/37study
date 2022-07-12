<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title>会员积分管理</title>
    <link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
    <link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code='hy.member.points.management' /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="saccountCode" id="spusaccountCoderNo" value="" placeholder="<spring:message code='hy.account.number' />..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="smemberCode" id="smemberCode" value="" placeholder='<spring:message code="main.member.number" />...' class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="smemberName" id="smemberName" value="" placeholder='<spring:message code="main.member.username" />...' class="layui-input">
                            </div>
                            <div class="layui-inline" >
                                <label class="layui-form-label" style="width: 120px"><spring:message code='hy.used.points' /></label>
                                <div class="layui-input-inline" style="width: 70px;">
                                    <input type="text" name="iusedPointsBegin" id="iusedPointsBegin" autocomplete="off" class="layui-input">
                                </div>
                                <div class="layui-form-mid">~</div>
                                <div class="layui-input-inline" style="width: 70px;">
                                    <input type="text" name="iusedPointsEnd" id="iusedPointsEnd" autocomplete="off" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-inline">
                                <label class="layui-form-label" style="width: 120px"><spring:message code='hy.points.available' /></label>
                                <div class="layui-input-inline" style="width: 70px;">
                                    <input type="text" name="iusablePoints" id="iusablePoints" autocomplete="off" class="layui-input">
                                </div>
                                <div class="layui-form-mid">~</div>
                                <div class="layui-input-inline" style="width: 70px;">
                                    <input type="text" name="iusablePointsEnd" id="iusablePointsEnd" autocomplete="off" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn layui-btn layui-btn" id="searchBtn" data-type="query"><i class="layui-icon">&#xe615;</i><spring:message code="main.query" /></button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i class="layui-icon">&#x1006;</i><spring:message code="main.clear" /></button>
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
    // 渲染数据
    var colModel = [{name: 'id', index: 'id',hidden:true},
        {name: 'saccountCode',index: 'SACCOUNT_CODE',label : "<spring:message code='hy.account.number' />",sortable : false,width:130},
        {name: 'smemberCode',index: 'SMEMBER_CODE',label : '<spring:message code="main.member.number" />',sortable : false,width:130},
        {name: 'smemberNameDesensitize',index: 'SMEMBER_NAME',label : '<spring:message code="main.member.username" />',sortable : false,width:150},
        {name: 'itotalPoints',index: 'ITOTAL_POINTS',label : "<spring:message code='hy.total.points' />",sortable : false,width:80},
        {name: 'iusedPoints',index: 'IUSED_POINTS',label : "<spring:message code='hy.used.points' />",sortable : false,width:80},
        {name: 'iusablePoints',index: 'IUSABLE_POINTS',label : "<spring:message code='hy.points.available' />",sortable : false,width:80},
        {name: 'ifrozenPoints',index: 'IFROZEN_POINTS',label : "<spring:message code='hy.freeze.points' />",sortable : false,width:80},
        {name : 'process',title:false,index : 'process',label : '<spring:message code="main.operation" />',sortable : false,width:150}
    ];
    // 表格回调
    function tableCallBack() {
        setTimeout(function () {
            var data = $("#gridTable").jqGrid('getRowData');

            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = "";
                <shiro:hasPermission name="INFO_INTEGRALACCOUNT">
                str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"infoMethod('"
                    + cl + "')\" title='<spring:message code='hy.points.details' />' alt='<spring:message code='hy.points.details' />'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="EDIT_INTEGRALACCOUNT">
                str += "<img src='${staticSource}/resources/images/oper_icon/integral_governing.png' class=\"oper_icon\" onclick=\"integralMethod('"
                    + cl + "')\" title='<spring:message code='hy.point.adjustment' />' alt='<spring:message code='hy.point.adjustment' />'  /> | ";
                </shiro:hasPermission>

                $("#gridTable").jqGrid('setRowData',
                    cl, {
                        process: str.substr(0, str.lastIndexOf(" | "))
                    });
            }
        }, 0);
    }
    // 初始化表格,传入页面所需参数
    $(function () {
        var config = {
            rownumbers: true,
            multiselect:false,
            sortname: "ITOTAL_POINTS",
            sortorder: "desc"
        };
        initTable(ctx + "/integralAccount/queryData", colModel, tableCallBack, config);
    });
    initBtnByForm2();

    function integralMethod(sid) {
        showLayer("<spring:message code='hy.point.adjustment' />", 'adjustmentlist?addLess=0&sid='+sid,{ area: ['30%', '30%']});
    }
    function infoMethod(sid) {
        showLayer("<spring:message code='hy.points.details' />",ctx+"/integralAccount/queryDataInfo?sid="+sid,{ area: ['60%', '60%']});
    }


</script>
</body>
</html>

