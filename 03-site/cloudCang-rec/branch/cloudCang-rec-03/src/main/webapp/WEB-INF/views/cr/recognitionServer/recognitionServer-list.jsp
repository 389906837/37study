<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>识别服务器列表</title>
<link href="${staticSource}/resources/layui/css/layui.css?1.0" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>识别服务器列表</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="sname" id="sname" value="" placeholder="服务器名称..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sip" id="sip" value="" placeholder="服务器IP..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sport" id="sport" value="" placeholder="服务器端口..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" list="{10:待审核,20:审核通过,30:审核驳回}" id="iauditStatus"
                                             name="iauditStatus" entire="true" entireName="请选择审核状态"/>
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
                        <div class="layui-form-item">
                            <shiro:hasPermission name="RECOGNITION_SERVER_EDIT">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"><i
                                        class="layui-icon"></i>添加
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="RECOGNITION_SERVER_DELETE">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i
                                        class="layui-icon"></i>删除
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="RECOGNITION_SERVER_REBOOT">
                                <button class="layui-btn layui-btn-sm layui-btn-hui" data-type="reboot"><i
                                        class="layui-icon">&#x1002;</i>重启
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
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    /**
     * 选择日期范围
     */
    layui.use(['form', 'laydate'], function () {
        var laydate = layui.laydate;

//执行一个laydate实例
        laydate.render({
            elem: '#toperateDateStr', //指定元素
            range: true,
            type: 'datetime'
        });
        var $ = layui.$, active = {
            reboot: function () {//重启
                showLayerMedium("选择需要重启的识别服务器", ctx + '/recognitionServer/serveReboot');
            },
        };
        $('.layui-form .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });

    // 渲染数据
    var colModel = [{name: 'id', index: 'id', hidden: true},
        {name: 'scode', index: 'SCODE', label: "服务器编号", width: 70},
        {name: 'sname', index: 'SNAME', label: "服务器名称", width: 100},
        {name: 'sip', index: 'SIP', label: "服务器IP", width: 80},
        {name: 'sport', index: 'SPORT', label: "服务器端口", width: 80},
        {name: 'smodelCode', index: 'SMODEL_CODE', label: "视觉模型编号", width: 80},
        {
            name: 'iauditStatus', index: 'IAUDIT_STATUS', label: "审核状态", width: 80, formatter: "select",
            editoptions: {value: '10:待审核;20:审核通过;30:审核驳回'}
        },
        {
            name: 'tauditTime', index: 'TAUDIT_TIME', label: "审核时间", width: 80, formatter: function (value) {
            if (isEmpty(value)) {
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }
        },
        {name: 'sauditOperName', index: 'SAUDIT_OPER_NAME', label: "审核人姓名", width: 80},
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
                if (10 == ids[i].iauditStatus) {
                    <shiro:hasPermission name="RECOGNITION_SERVER_EDIT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                        + cl + "')\" title='编辑' alt='编辑' /> | ";
                    </shiro:hasPermission>
                    <shiro:hasPermission name="RECOGNITION_SERVER_DELETE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                        + cl + "')\" title='删除' alt='删除' /> | ";
                    </shiro:hasPermission>
                    <shiro:hasPermission name="RECOGNITION_SERVER_AUDIT">
                    str += "<img src='${staticSource}/resources/images/order/shenhe.png' class=\"order\" onclick=\"exMethod('"
                        + cl + "')\" title='审核' alt='审核' /> | ";
                    </shiro:hasPermission>
                }
                <shiro:hasPermission name="RECOGNITION_SERVER_UPMODEL">
                str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"order\" onclick=\"upModelMethod('"
                    + cl + "')\" title='更换模型' alt='更换模型' /> | ";
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
            sortname: "TAUDIT_TIME",
            sortorder: "desc"
        };
        initTable(ctx + "/recognitionServer/queryData", colModel, tableCallBack, config);
    });
    // 调用Button按钮方法(查询,清除)
    //初始化form中按钮
    var initBtnConfig = {
        addUrl: ctx + '/recognitionServer/edit',
        deleteUrl: ctx + "/recognitionServer/delete",
        addTitle: '添加识别服务器',
        addFn: showLayerMedium
    };
    initBtnByForm4(initBtnConfig);

    function editMethod(sid) {
        showLayerMedium("编辑识别服务器", ctx + '/recognitionServer/edit?sid=' + sid);
    }
    function delMethod(sid) {
        confirmDelTip("确定要删除数据?", ctx + "/recognitionServer/delete", {checkboxId: sid});
    }
    function exMethod(sid) {
        showLayerMin("识别服务器审核", ctx + '/recognitionServer/examine?sid=' + sid);
    }
    function upModelMethod(sid) {
        showLayerMin("更新模型", ctx + '/recognitionServer/upModel?sid=' + sid);
    }

</script>
</body>
</html>

