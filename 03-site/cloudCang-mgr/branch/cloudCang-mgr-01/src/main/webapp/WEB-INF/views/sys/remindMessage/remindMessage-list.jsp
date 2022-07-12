<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>温馨提醒配置</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet"/>
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css" rel="stylesheet"/>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>温馨提醒配置</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder="编号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sname" id="sname" value="" placeholder="名称..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control" name="itype" id="itype">
                                    <option value="">发布平台</option>
                                    <c:forEach items="${meeTypeMap}" var="mymap">
                                        <option value="${mymap.key}">${mymap.value}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="svalue" id="svalue" value="" placeholder="消息值..."
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
                        <div class="layui-form-item">
                            <shiro:hasPermission name="ADD_REMINDMESSAGE">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" style="margin-left: 0px;"
                                        data-type="add"><i class="layui-icon"></i>添加
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="DEL_REMINDMESSAGE">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i>删除
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="ZKP_REMINDMESSAGE">
                                <button class="layui-btn layui-btn-sm layui-btn-primary" data-type="refreshCache"><i
                                        class="layui-icon">&#x1006;</i>更新缓存
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
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    var colModel = [{name: 'id', index: 'id', hidden: true},
        {name: 'scode', index: 'scode', label: "温馨提醒编号", width: 150},
        {name: 'sname', index: 'sname', label: "温馨提醒名称", width: 200},
        {
            name: 'itype', index: 'itype', label: "发布平台", width: 150, formatter: "select",
            editoptions: {value: '1:web;2:wap;3:app;4:移动端'}
        },
        {name: 'svalue', index: 'svalue', label: "消息值", width: 120},
        {name: 'sort', index: 'sort', label: "排序号", width: 80},
        {name: 'remark', index: 'remark', label: "备注", sortable: false, width: 200},
        {name: 'process', index: 'process',title:false, label: "操作", sortable: false, width: 150}
    ];
    function tableCallBack() {
        setTimeout(function () {
            var ids = $("#gridTable").jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                var cl = ids[i];
                var str = '';
                <shiro:hasPermission name="EDIT_REMINDMESSAGE">
                str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                    + cl + "')\" title='编辑温馨提醒' alt='编辑温馨提醒'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="DEL_REMINDMESSAGE">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='删除温馨提醒' alt='删除温馨提醒'  /> | ";
                </shiro:hasPermission>
                $("#gridTable").jqGrid('setRowData',
                    ids[i], {
                        process: str.substr(0, str.lastIndexOf(" | "))
                    });
            }
        }, 0);
    }
    $(function () {
        var config = {
            sortname: "sort",
            sortorder: "asc"
        };
        initTable(ctx + "/remindMessage/queryData", colModel, tableCallBack, config);
    });
    //初始化form中按钮
    var initBtnConfig = {
        addUrl: ctx + '/remindMessage/edit',
        deleteUrl: ctx + "/remindMessage/delete",
        addTitle: '添加温馨提醒信息',
        addFn:showLayerMin,
        updatecacheUrl: ctx + '/zooSyn/cacheRemindMessage?t=' + new Date()
    };
    initBtnByForm5(initBtnConfig);

    function editMethod(sid) {
        showLayerMin("编辑温馨提醒信息", ctx + '/remindMessage/edit?sid=' + sid);
    }
    function delMethod(sid) {
        confirmDelTip("确定要删除数据?", ctx + "/remindMessage/delete", {checkboxId: sid});
    }
</script>
</body>
</html>

