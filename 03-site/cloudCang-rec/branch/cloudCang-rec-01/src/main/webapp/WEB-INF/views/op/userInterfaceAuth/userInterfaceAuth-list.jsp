<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>用户接口权限管理列表</title>
<link href="${staticSource}/resources/layui/css/layui.css?1" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>用户接口权限管理列表</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder="编号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="userName" id="userName" value="" placeholder="用户昵称..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="appName" id="appName" value="" placeholder="应用名称..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="interfaceName" id="interfaceName" value=""
                                       placeholder="接口名称..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="saction" id="saction" value=""
                                       placeholder="接口动作..."
                                       class="layui-input">
                            </div>

                            <div class="layui-input-inline">
                                <cang:select type="list" list="{10:已开通,20:已关闭}" id="iauthStatus"
                                             name="iauthStatus" entire="true" entireName="请选择接口权限状态"/>
                            </div>

                            <div class="layui-input-inline">
                                <input type="text" name="openTimeStr" id="openTimeStr" value=""
                                       placeholder="开通时间..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="lastCloseTimeStr" id="lastCloseTimeStr" value=""
                                       placeholder="最后一次关闭时间..."
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
            elem: '#lastCloseTimeStr', //指定元素
            range: true,
            type: 'date'
        });
        laydate.render({
            elem: '#openTimeStr', //指定元素
            range: true,
            type: 'date'
        });
    });

    // 渲染数据
    var colModel = [{name: 'id', index: 'id', hidden: true},
        {name: 'scode', index: 'scode', label: "编号", width: 100},
        {name: 'userName', index: 'userName', label: "用户昵称", width: 100},
        {name: 'appManageName', index: 'appManageName', label: "应用名", width: 100},
        {name: 'interfaceName', index: 'interfaceName', label: "接口名", width: 100},
        {name: 'saction', index: 'saction', label: "接口动作", width: 100},
        {
            name: 'iauthStatus', index: 'IAUTH_STATUS', label: "接口权限状态", width: 80, formatter: "select",
            editoptions: {value: '10:已开通;20:已关闭'}
        },
        {
            name: 'iopenTime', index: 'IOPEN_TIME', label: "开通时间", width: 80, formatter: function (value) {
            if (isEmpty(value)) {
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }
        },
        {
            name: 'ilastCloseTime',
            index: 'ILAST_CLOSE_TIME',
            label: "最近一次关闭时间",
            width: 80,
            formatter: function (value) {
                if (isEmpty(value)) {
                    return '';
                }
                return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
            }
        },
        {name: "process", index: "process", title: false, label: "操作", sortable: false, frozen: true, width: 120}
    ]
    // 表格回调
    function tableCallBack() {
        setTimeout(function () {
            var ids = $("#gridTable").jqGrid('getRowData');
            var merchantCode = "${SESSION_KEY_SMERCHANT_CODE}";
            for (var i = 0; i < ids.length; i++) {
                var cl = ids[i].id;
                var str = '';
                if (10 == ids[i].iauthStatus) {
                    <shiro:hasPermission name="USER_INTERFACE_AUTH_CLOSE">
                    str += "<img src='${staticSource}/resources/images/rec/closeInterfaceAuth.png' class=\"oper_icon\" onclick=\"closeMethod('"
                        + cl + "')\" title='关闭' alt='关闭' /> | ";
                    </shiro:hasPermission>
                }
                if (20 == ids[i].iauthStatus) {
                    str += "<img src='${staticSource}/resources/images/rec/openInterfaceAuth.png' class=\"oper_icon\" onclick=\"openMethod('"
                        + cl + "')\" title='开通' alt='开通' /> | ";
                }
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
            sortname: "",
            sortorder: ""
        };
        initTable(ctx + "/userInterfaceAuth/queryData", colModel, tableCallBack, config);
    });
    // 调用Button按钮方法(查询,清除)
    //初始化form中按钮
    var initBtnConfig = {
        addUrl: ctx + '/userInterfaceAuth/edit',
        deleteUrl: ctx + "/userInterfaceAuth/delete",
        addTitle: '添加开放平台用户',
        addFn: showLayerMedium
    };
    initBtnByForm4(initBtnConfig);

    function closeMethod(sid) {
        confirmDelTip("确定要关闭接口权限?", ctx + "/userInterfaceAuth/close", {checkboxId: sid});
    }
    function openMethod(sid) {
        confirmDelTip("确定要开通接口权限?", ctx + "/userInterfaceAuth/open", {checkboxId: sid});
    }
</script>
</body>
</html>

