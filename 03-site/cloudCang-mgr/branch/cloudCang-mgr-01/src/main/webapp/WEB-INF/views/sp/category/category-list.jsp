<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>商品分类列表</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>商品分类管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <input type="hidden" id="categoryId" name="categoryId" value="${categoryId}" />
                        <input type="hidden" id="sparentId" name="sparentId" value="${sparentId}" />
                        <%--<div class="layui-form-item">--%>
                            <%--<div class="layui-input-inline">--%>
                                <%--<input type="text" name="sname" id="sname" value="" placeholder="分类名称..." class="layui-input">--%>
                            <%--</div>--%>
                            <%--<c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">--%>
                                <%--<div class="layui-input-inline">--%>
                                    <%--<input type="text" name="smerchantCode" id="smerchantCode" value="" placeholder="商户编号..." class="layui-input">--%>
                                <%--</div>--%>
                                <%--<div class="layui-input-inline">--%>
                                    <%--<input type="text" name="merchantName" id="merchantName" value="" placeholder="商户名称..." class="layui-input">--%>
                                <%--</div>--%>
                            <%--</c:if>--%>
                            <%--<div class="layui-input-inline">--%>
                                <%--<select class="form-control" name="iisParent" id="iisParent">--%>
                                    <%--<option value="">父类</option>--%>
                                    <%--<option value="0">否</option>--%>
                                    <%--<option value="1">是</option>--%>
                                <%--</select>--%>
                            <%--</div>--%>
                            <%--<div class="layui-input-inline">--%>
                                <%--<select class="form-control" name="iisHot" id="iisHot">--%>
                                    <%--<option value="">热门分类</option>--%>
                                    <%--<option value="0">否</option>--%>
                                    <%--<option value="1">是</option>--%>
                                <%--</select>--%>
                            <%--</div>--%>
                            <%--<div class="layui-input-inline">--%>
                                <%--<select class="form-control" name="iisDisplay" id="iisDisplay">--%>
                                    <%--<option value="">对外显示</option>--%>
                                    <%--<option value="0">否</option>--%>
                                    <%--<option value="1">是</option>--%>
                                <%--</select>--%>
                            <%--</div>--%>
                            <%--<div class="layui-input-inline">--%>
                                <%--<button class="layui-btn layui-btn" id="searchBtn" data-type="query">--%>
                                    <%--<i class="layui-icon">&#xe615;</i>查询--%>
                                <%--</button>--%>
                                <%--<button class="layui-btn layui-btn layui-btn-primary" data-type="reset">--%>
                                    <%--<i class="layui-icon">&#x1006;</i>清除--%>
                                <%--</button>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <div class="layui-form-item">
                            <shiro:hasPermission name="SP_CATEGORY_ADD">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"><i class="layui-icon"></i>添加</button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="SP_CATEGORY_DELETE">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i>删除</button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="SP_CATEGORY_INIT">
                                <button class="layui-btn layui-btn-sm" onclick="initCategory()" ><i class="layui-icon"></i>初始化数据</button>
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
     * 重新加载表格
     */
    function reloadData(categoryId,sparentId) {
        if (!isEmpty(categoryId)) {
            $("#categoryId").val(categoryId);
        }
        if (!isEmpty(sparentId)) {
            $("#sparentId").val(sparentId);
        } else {
            $("#sparentId").val("");
        }
        resetReloadGrid();
    }
    var colModel= [
        {name: 'id', index: 'id', hidden: true},
        {name: 'sname', index: 'SNAME', label: "分类名称",width:120},
        {name: 'scode', index: 'SCODE', label: "分类编号", width: 120},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode',label : "商户编号",index: 'SMERCHANT_CODE',width:140},
        {name: 'merchantName',label : "商户名称",index: 'merchantName',sortable : false,width:220},
        </c:if>
        {   name: 'iisParent',
            index: 'IIS_PARENT',
            label: "父类",
            formatter: "select",
            editoptions: {value: '0:否;1:是'},
            width:80},
        {   name: 'iisHot',
            index: 'IIS_HOT',
            label: "热门分类",
            formatter: "select",
            editoptions: {value: '0:否;1:是'},
            width:80},
        {   name: 'iisDisplay',
            index: 'IIS_DISPLAY',
            label: "对外显示",
            formatter: "select",
            editoptions: {value: '0:否;1:是'},
            width:80},
//        {name: 'taddTime', index: 'TADD_TIME', label: "添加日期",formatter: function (value) {
//            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
//        },width:140},
        {name: 'isort', index: 'ISORT', label: "排序号",width:50},
        {name: "process",title:false, index: "process", label: "操作", sortable: false, frozen: true, width: 200}
    ];
    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function(){
            var ids = $("#gridTable").jqGrid('getDataIDs');
            for ( var i = 0; i < ids.length; i++) {
                var cl = ids[i];
                var str = "";
                <shiro:hasPermission name="SP_CATEGORY_VIEW">
                str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"viewMethod('"
                    + cl + "')\" title='查看' alt='查看'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="SP_CATEGORY_EDIT">
                str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                    + cl + "')\" title='编辑' alt='编辑'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="SP_CATEGORY_DELETE">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='删除' alt='删除'  /> | ";
                </shiro:hasPermission>
                $("#gridTable").jqGrid('setRowData',
                    ids[i], {
                        process : str.length > 0 ? str.substr(0,str.lastIndexOf(" | ")) : ""
                    });
            }
        }, 0);
    }
    $(function () {
        var config = {
            sortname: "isort",
            sortorder: "asc",
        };
        initTable(ctx + "/commodity/getCategoryByCategoryId", colModel, tableCallBack, config);
    });

    layui.use('form', function(){
        var $ = layui.$, active = {
            add: function(){//添加
                var categoryId = $("#categoryId").val();
                var param = $("#categoryId").val() + "-//-" + $("#sparentId").val();
                if (isEmpty(categoryId)) {
                    alertDel("请先选择要父分类或主分类");
                    return;
                }
                showLayerMin("添加商品分类信息", ctx+'/commodity/category/toAdd?param='+param);
            },delete: function(){//删除
                var sid = $("#gridTable").jqGrid('getGridParam','selarrrow');
                if(isEmpty(sid)) {
                    alertDel("请先选择要删除的数据");
                    return;
                }
                confirmDelTip("确定要删除数据?",ctx+"/commodity/category/delete",{checkboxId:sid.join(",")});
            }
        };
        $('.layui-form .layui-btn').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });

    function viewMethod(sid) {
        showLayerMin("商品分类详情", ctx + '/commodity/category/view?sid=' + sid);
    }
    function editMethod(sid) {
        showLayerMin("编辑商品分类信息", ctx + '/commodity/category/toEdit?sid=' + sid);
    }
    function delMethod(sid) {
        confirmDelTip("确定要删除数据?", ctx + "/commodity/category/delete", {checkboxId: sid});
    }
    function initCategory() {
        var sid = "";
        confirmDelTip("初始化数据",ctx+"/commodity/category/initCategory",{checkboxId:sid});
    }

</script>
</body>
</html>



