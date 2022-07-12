<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<c:if test="${'SP000000' eq groupNo }">
    <title>系统参数配置</title>
</c:if>
<c:if test="${'SP000010' eq groupNo }">
    <title>黑白名单配置</title>
</c:if>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet"/>
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css" rel="stylesheet"/>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <c:if test="${'SP000000' eq groupNo }">
                        <h5>系统参数配置</h5>
                    </c:if>
                    <c:if test="${'SP000010' eq groupNo }">
                        <h5>黑白名单配置</h5>
                    </c:if>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="sname" id="sname" value="" placeholder="参数名称..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="svalue" id="svalue" value="" placeholder="参数值..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sremark" id="sremark" value="" placeholder="参数说明..."
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
                            <shiro:hasPermission name="ADDPARADETAIL_${groupNo}">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" style="margin-left: 0px;"
                                        data-type="add"><i class="layui-icon"></i>添加
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="DELPARADETAIL_${groupNo}">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i>删除
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="ZKP_${groupNo}">
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
    var title = "";
    <c:if test="${'SP000000' eq groupNo }">
        title = "系统参数";
    </c:if>
    <c:if test="${'SP000010' eq groupNo }">
        title = "黑白名单";
    </c:if>
    var colModel = [{name: 'id', index: 'id', hidden: true},
        {name: 'sname', index: 'sname', label: "参数名称", width: 200},
        {name: 'svalue', index: 'svalue', label: "参数值", width: 120},
        {name: 'isort', index: 'isort', label: "排序号", width: 80},
        {name: 'sremark', index: 'sremark', sortable: false, label: "参数说明", width: 300}, /*sortable: false,*/
        {name: 'process', index: 'process',title:false, label: "操作", sortable: false, width: 150}
    ];
    function tableCallBack() {
        setTimeout(function () {
            //$("#gridTable").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "scroll" });
            var ids = $("#gridTable").jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                var cl = ids[i];
                var str = '';
                <shiro:hasPermission name="UPPARADETAIL_${groupNo}">
                    str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                        + cl + "')\" title='编辑数据' alt='编辑数据'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="DELPARADETAIL_${groupNo}">
                    str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                        + cl + "')\" title='删除数据' alt='删除数据'  /> | ";
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
            sortname: "isort",
            sortorder: "asc"
        };
        initTable(ctx + "/paramGroupDetail/${groupNo}/queryParamGroupDetail", colModel, tableCallBack, config);
    });
    //初始化form中按钮
    var initBtnConfig = {
        addUrl: ctx + '/paramGroupDetail/${groupNo}/edit',
        deleteUrl:  ctx + "/paramGroupDetail/${groupNo}/delete",
        updatecacheUrl: ctx + '/zooSyn/cacheDic?t=' + new Date(),
        addTitle:'添加'+title+'信息',
        addFn: showLayerMin
    };
    initBtnByForm5(initBtnConfig);

    function editMethod(sid) {
        showLayerMin("编辑"+title+"信息", ctx + '/paramGroupDetail/${groupNo}/edit?sid=' + sid);
    }
    function delMethod(sid) {
        confirmDelTip("确定要删除数据?", ctx + "/paramGroupDetail/${groupNo}/delete", {checkboxId: sid});
    }
</script>
</body>
</html>

