<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>平台应用管理</title>
<link href="${staticSource}/resources/layui/css/layui.css?1" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>平台应用管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder="应用编号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sname" id="sname" value="" placeholder="应用名称..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sappId" id="sappId" value="" placeholder="应用ID..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sappSecretKey" id="sappSecretKey" value=""
                                       placeholder="应用秘钥..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="dic" name="stype" id="stype" entire="true"
                                             entireName="请选择应用类型" groupNo="SP000159"/>
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="dic" name="ssystemType" id="ssystemType" entire="true"
                                             entireName="请选择应用系统分属" groupNo="SP000160"/>
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="dic" name="sencrypType" id="sencrypType" entire="true"
                                             entireName="请选择应用数据加密类型" groupNo="SP000161"/>
                            </div>
                        <%--    <div class="layui-input-inline">
                                <cang:select type="dic" name="stollType" id="stollType" entire="true"
                                             entireName="请选择应用收费类型" groupNo="SP000162"/>
                            </div>--%>
                            <div class="layui-input-inline">
                                <cang:select type="list" list="{10:申请中,20:已上线，待上架,30:已上线，已上架}" id="istatus"
                                             name="istatus" entire="true" entireName="应用状态"/>
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" list="{10:待审核,20:审核通过,30:审核驳回}" id="iauditStatus"
                                             name="iauditStatus" entire="true" entireName="审核状态"/>
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
                            <shiro:hasPermission name="APP_MANAGE_ADD">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"><i
                                        class="layui-icon"></i>添加
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="APP_MANAGE_DEL">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i
                                        class="layui-icon"></i>删除
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="APP_MANAGE_CACHE">
                                <button class="layui-btn layui-btn-sm layui-btn-primary" data-type="refreshCache"><i
                                        class="layui-icon layui-icon-refresh"></i>更新缓存
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
    layui.use('laydate', function () {
        var laydate = layui.laydate;

//执行一个laydate实例
        laydate.render({
            elem: '#toperateDateStr', //指定元素
            range: true,
            type: 'datetime'
        });
    });

    // 渲染数据
    var colModel = [{name: 'id', index: 'id', hidden: true},
        {name: 'scode', index: 'scode', label: "编号", width: 100},
        {name: 'sname', index: 'SNAME', label: "应用名", width: 100},
        {name: 'sappId', index: 'SAPP_ID', label: "应用ID", width: 100},
        {name: 'sappSecretKey', index: 'SAPP_SECRET_KEY', label: "应用秘钥", width: 100},
        {
            name: 'stype', index: 'STYPE', label: "应用类型", width: 80, formatter: "select",
            editoptions: {value: '10:识别算法'}
        },
        {
            name: 'ssystemType', index: 'SSYSTEM_TYPE', label: "应用分属系统", width: 80, formatter: "select",
            editoptions: {value: '10:云识别;20:视觉识别'}
        },
        {
            name: 'sencrypType', index: 'SENCRYP_TYPE', label: "数据加密类型", width: 80, formatter: "select",
            editoptions: {value: '10:RSA2'}
        },
        /*{
            name: 'stollType', index: 'STOLL_TYPE', label: "应用收费类型", width: 80, formatter: "select",
            editoptions: {value: '10:按次计费;20:按单位次数计费'}
        },*/
        {
            name: 'istatus', index: 'ISTATUS', label: "应用状态", width: 80, formatter: "select",
            editoptions: {value: '10:申请中;20:已上线，待上架;30:已上线，已上架'}
        },
        {
            name: 'iauditStatus', index: 'IAUDIT_STATUS', label: "审核状态", width: 80, formatter: "select",
            editoptions: {value: '10:待审核;20:审核通过;30:审核驳回'}
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
                <shiro:hasPermission name="APP_MANAGE_VIEW">
                str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"viewMethod('"
                    + cl + "')\" title='查看' alt='查看'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="APP_MANAGE_DEL">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='删除' alt='删除' /> | ";
                </shiro:hasPermission>
                if (10 == ids[i].iauditStatus) {
                    <shiro:hasPermission name="APP_MANAGE_EDIT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                        + cl + "')\" title='编辑' alt='编辑' /> | ";
                    </shiro:hasPermission>
                    <shiro:hasPermission name="APP_MANAGE_EX">
                    str += "<img src='${staticSource}/resources/images/order/shenhe.png' class=\"order\" onclick=\"exMethod('"
                        + cl + "')\" title='审核' alt='审核' /> | ";
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
            sortname: "",
            sortorder: ""
        };
        initTable(ctx + "/appManage/queryData", colModel, tableCallBack, config);
    });
    // 调用Button按钮方法(查询,清除)
    //初始化form中按钮
    var initBtnConfig = {
        addUrl: ctx + '/appManage/edit',
        deleteUrl: ctx + "/appManage/delete",
        updatecacheUrl: ctx + '/appManage/cache?t=' + new Date(),
        addTitle: '添加平台应用',
        addFn: showLayerMedium
    };
    initBtnByForm5(initBtnConfig);
    function viewMethod(sid) {
        showLayerMedium("详情", ctx + '/appManage/view?sid=' + sid);
    }
    function editMethod(sid) {
        showLayerMedium("编辑平台应用", ctx + '/appManage/edit?sid=' + sid);
    }
    function delMethod(sid) {
        confirmDelTip("确定要删除平台应用?", ctx + "/appManage/delete", {checkboxId: sid});
    }
    function exMethod(sid) {
        showLayerMin("平台应用审核", ctx + '/appManage/examine?sid=' + sid);
    }
</script>
</body>
</html>

