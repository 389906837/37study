<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title>会员第三方授权信息</title>
    <link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
    <link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code='hy.member.third.party.authorization.information' /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <input type="text" name="smemberCode" id="smemberCode" value="" placeholder='<spring:message code="main.member.number" />...' class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="smemberName" id="smemberName" value="" placeholder='<spring:message code="main.member.username" />...' class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 170px">
                                <select class="form-control" name="ithirdType" id="ithirdType">
                                    <option value=""><spring:message code='hy.please.select.a.third.party.type' /></option>
                                    <option value="10"><spring:message code="main.wechat" /></option>
                                    <option value="20"><spring:message code="main.alipay" /></option>
                                </select>
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sotherNickname" id="sotherNickname" value="" placeholder="<spring:message code='hy.third.party.user.nickname' />..." class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 150px">
                                <select class="form-control" name="istatus" id="istatus">
                                    <option value=""><spring:message code="main.state.select" /></option>
                                    <option value="10"><spring:message code='hy.association' /></option>
                                    <option value="20"><spring:message code='hy.disassociate' /></option>
                                </select>
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="tauthoriseTimeStr" id="tauthoriseTimeStr" placeholder="<spring:message code='hy.authorization.time' />" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn layui-btn" id="searchBtn" data-type="query"><i class="layui-icon">&#xe615;</i><spring:message code="main.query" /></button>
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
    /**
     * 选择日期范围
     */
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //执行一个laydate实例
        laydate.render({
            elem: '#tauthoriseTimeStr', //指定元素
            range:true,
            type: 'date'
        });
    });

    // 渲染数据
    var colModel = [{name: 'id', index: 'id', hidden: true},
        {name: 'smemberCode', index: 'SMEMBER_CODE', label: '<spring:message code="main.member.number" />', sortable: false, width: 220},
        {name: 'smemberNameDesensitize', index: 'SMEMBER_NAME', label: '<spring:message code="main.member.username" />', sortable: false, width: 220},
        {
            name: 'ithirdType', index: 'ITHIRD_TYPE', label: "<spring:message code='hy.third.party.type' />", editable: true,
            formatter: "select", editoptions: {value: '10:<spring:message code="main.wechat" />;20:<spring:message code="main.alipay" />'}, sortable: false, width: 210
        },
        {name: 'sotherNickname', index: 'SOTHER_NICKNAME', label: "<spring:message code='hy.third.party.user.nickname' />", sortable: false, width: 220},
        {
            name: 'istatus', index: 'ISTATUS', label: '<spring:message code="main.state" />', editable: true,
            formatter: "select", editoptions: {value: '10:<spring:message code='hy.association' />;20:<spring:message code='hy.disassociate' />'}, sortable: false, width: 200
        },
        {
            name: 'tauthoriseTime', index: 'TAUTHORISE_TIME', label: "<spring:message code='hy.authorization.time' />", formatter: function (value) {
                return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }, width: 220
        },
        {name: 'sremark', index: 'SREMARK', label: '<spring:message code="main.remarks" />', sortable: false, width: 300}
    ];
    // 表格回调
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
        }, 0);
    }
    // 初始化表格,传入页面所需参数
    $(function () {
        var config = {
            rownumbers: true,
            multiselect:false,
            sortname:"ISTATUS=10",
            sortorder:"desc"
        };
        initTable(ctx+"/thirdauthoriseinfo/queryData",colModel,tableCallBack,config);
    });
    // 调用Button按钮方法(查询,清除)
    initBtnByForm2();
</script>
</body>
</html>

