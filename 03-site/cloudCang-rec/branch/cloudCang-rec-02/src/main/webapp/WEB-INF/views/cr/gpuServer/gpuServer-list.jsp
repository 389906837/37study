<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>GPU识别服务器列表</title>
<link href="${staticSource}/resources/layui/css/layui.css?1.0" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>GPU识别服务器列表</h5>
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
                                <cang:select type="list" list="{10:离线,20:在线,30:在线并且模型已初始化}" id="irunStatus"
                                             name="irunStatus" entire="true" entireName="请选择初始化状态"/>
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" list="{10:图像识别服务器}" id="itype"
                                             name="itype" entire="true" entireName="请选择服务器类型"/>
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" list="{10:申请中,50:待上线,20:已上线,60:申请已拒绝,30:故障,40:已废弃}"
                                             id="istatus"
                                             name="istatus" entire="true" entireName="请选择服务器状态"/>
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
                            <shiro:hasPermission name="GPU_SERVER_ADD">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"><i
                                        class="layui-icon"></i>添加
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="GPU_SERVER_DEL">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i
                                        class="layui-icon"></i>删除
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="GPU_SERVER_MODEL_UP">
                                <button class="layui-btn layui-btn-sm layui-btn-hui" data-type="modelUpgrade"><i
                                        class="layui-icon">&#xe628;</i>模型更新
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="GPU_SERVER_SERVICE_UP">
                                <button class="layui-btn layui-btn-sm layui-btn-hui" data-type="serviceUpgrade"><i
                                        class="layui-icon">&#xe628;</i>服务更新
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
            modelUpgrade: function () { // 模型更新
                showLayerMedium("模型更新", ctx + "/gpuServer/upModel");
            },
            serviceUpgrade: function () { // 服务更新
                showLayerMedium("服务更新", ctx + "/gpuServer/upService");
            }
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
        {
            name: 'irunStatus', index: 'IRUN_STATUS', label: "初始化状态", width: 80, formatter: "select",
            editoptions: {value: '10:离线;20:在线;30:在线并且模型已初始化'}
        },
        {name: 'smodelCode', index: 'SMODEL_CODE', label: "视觉模型编号", width: 80},
        {
            name: 'itype', index: 'ITYPE', label: "服务器类型", width: 80, formatter: "select",
            editoptions: {value: '10:图像识别服务器'}
        },
        {
            name: 'istatus', index: 'ISTATUS', label: "服务器状态", width: 80, formatter: "select",
            editoptions: {value: '10:申请中;20:已上线;30:故障;40:已废弃;50:待上线;60:申请已拒绝'}
        },
        {
            name: 'iauditStatus', index: 'IAUDIT_STATUS', label: "审核状态", width: 80, formatter: "select",
            editoptions: {value: '10:待审核;20:审核通过;30:审核驳回'}
        }, {
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
                    <shiro:hasPermission name="GPU_SERVER_EDIT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                        + cl + "')\" title='编辑' alt='编辑' /> | ";
                    </shiro:hasPermission>
                    <shiro:hasPermission name="GPU_SERVER_DEL">
                    str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                        + cl + "')\" title='删除' alt='删除' /> | ";
                    </shiro:hasPermission>
                    <shiro:hasPermission name="GPU_SERVER_EX">
                    str += "<img src='${staticSource}/resources/images/order/shenhe.png' class=\"order\" onclick=\"exMethod('"
                        + cl + "')\" title='审核' alt='审核' /> | ";
                    </shiro:hasPermission>
                }
                if (20 == ids[i].iauditStatus) {
                    <shiro:hasPermission name="GPU_SERVER_EDIT_SERVER_STA">
                    str += "<img src='${staticSource}/resources/images/rec/editServerStatus.png' class=\"oper_icon\" onclick=\"modifyServerStatusMethod('"
                        + cl + "')\" title='编辑服务器状态' alt='编辑服务器状态' /> | ";
                    </shiro:hasPermission>
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
            sortname: "TAUDIT_TIME",
            sortorder: "desc"
        };
        initTable(ctx + "/gpuServer/queryData", colModel, tableCallBack, config);
    });
    // 调用Button按钮方法(查询,清除)
    //初始化form中按钮
    var initBtnConfig = {
        addUrl: ctx + '/gpuServer/edit',
        deleteUrl: ctx + "/gpuServer/delete",
        addTitle: '添加GPU识别服务器',
        addFn: showLayerMedium
    };
    initBtnByForm4(initBtnConfig);

    function editMethod(sid) {
        showLayerMedium("编辑GPU识别服务器", ctx + '/gpuServer/edit?sid=' + sid);
    }
    function modifyServerStatusMethod(sid) {
        showLayerMin("编辑服务器状态", ctx + '/gpuServer/modifyServerStatus?sid=' + sid, {area: ['30%', '40%']});
    }
    function delMethod(sid) {
        confirmDelTip("确定要删除数据?", ctx + "/gpuServer/delete", {checkboxId: sid});
    }
    function exMethod(sid) {
        showLayerMin("GPU识别服务器审核", ctx + '/gpuServer/examine?sid=' + sid);
    }

</script>
</body>
</html>

