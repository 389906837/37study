<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>系统日志</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>操作日志</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="sbusinessCode" id="sbusinessCode" value=""
                                       placeholder="业务编号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="userName" id="userName" value="" placeholder="用户名..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="appName" id="appName" value="" placeholder="应用名..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="interfaceName" id="interfaceName" value="" placeholder="接口名..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="soperIp" id="soperIp" value="" placeholder="调用Ip..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" list="{10:操作日志,20:错误日志,30:警告日志}" id="itype"
                                             name="itype" entire="true" entireName="请选择日志类型"/>
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="dic" groupNo="SP000168" entire="true" value="" entireName="请选择操作类型"
                                             name="ioperType" id="ioperType"/>
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="toperateDateStr" id="toperateDateStr" placeholder="操作时间"
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="scontent" id="scontent" value="" placeholder="内容..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i
                                        class="layui-icon">&#xe615;</i>查询
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i
                                        class="layui-icon">&#x1006;</i>清除
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
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    /**
     * 选择日期范围
     */
    layui.use('laydate', function () {
        var laydate = layui.laydate;

        //执行一个laydate实例
        laydate.render({
            elem: '#toperateDateStr', //指定元素
            range: true,
            type: 'date'
        });
    });

    // 渲染数据
    var colModel = [{name: 'id', index: 'id', hidden: true},
        {name: 'sbusinessCode', index: 'SBUSINESS_CODE', label: "业务编号", sortable: false, width: 80},
        {name: 'userName', index: 'userName', label: "用户名", sortable: false, width: 50},
        {name: 'appName', index: 'appName', label: "应用名", sortable: false, width: 80},
        {name: 'interfaceName', index: 'interfaceName', label: "接口名", sortable: false, width: 80},
        {name: 'soperIp', index: 'SOPER_IP', label: "调用IP", width: 60},
        {
            name: 'itype', index: 'ITYPE', label: "日志类型", editable: true,
            formatter: "select", editoptions: {value: '10:操作日志;20:错误日志;30:警告日志'}, width: 50
        },
        {
            name: 'ioperType',
            index: 'IOPER_TYPE',
            label: "操作类型",
            editable: true,
            formatter: "select",
            editoptions: {value: '10:image_recognition;20:image_recognition_async;30:recognition_query;40:interface_balance_query'},
            sortable: false,
            width: 70
        },
        {
            name: 'toperTime', index: 'TOPER_TIME', label: "操作时间", formatter: function (value) {
            if (isEmpty(value)) {
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }, width: 130
        },
        {name: 'scontent', index: 'SCONTENT', label: "操作内容", sortable: false, width: 350}
    ];
    // 表格回调
    function tableCallBack() {
    }
    // 初始化表格,传入页面所需参数
    $(function () {
        var config = {
            rownumbers: true,
            multiselect: false,
            sortname: '',
            sortorder: ''
        };
        initTable(ctx + "/transferLog/queryData", colModel, tableCallBack, config);
    });
    // 调用Button按钮方法(查询,清除)
    initBtnByForm2();
</script>
</body>
</html>

