<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>域名管理</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">

</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>域名管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantCode" id="merchantCode" value=""
                                           placeholder="商户编号..." class="layui-input">
                                </div>
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantName" id="merchantName" value=""
                                           placeholder="商户名称..." class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-input-inline">
                                <input type="text" name="sdomainUrl" id="sdomainUrl" value="" placeholder="域名..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" name="istatus" id="istatus" entire="true" value=""
                                             list="{10:申请中,20:审核通过,30:审核驳回,40:停用}" entireName="请选择状态"/>
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
                            <shiro:hasPermission name="DOMAINCONF_ADD">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"><i
                                        class="layui-icon"></i>添加
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="DOMAINCONF_DELETE">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i
                                        class="layui-icon"></i>删除
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="DOMAINCONF_CACHE">
                                <button class="layui-btn layui-btn-sm layui-btn-primary" data-type="refreshCache"><i class="layui-icon layui-icon-refresh"></i>更新缓存
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
    var colModel = [{name: 'id', index: 'id', hidden: true},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'merchantCode', label: "商户编号", index: 'merchantCode', sortable: false, width: 150},
        {name: 'merchantName', label: "商户名称", index: 'merchantName', sortable: false, width: 250},
        </c:if>
        {name: 'sdomainUrl', index: 'SDOMAIN_URL', label: "域名", width: 150},
        {
            name: 'istatus',
            index: 'ISTATUS',
            label: "状态",
            width: 150,
            formatter: "select",
            editoptions: {value: '10:申请中;20:申请通过;30:申请驳回;40:停用'}
        },
        {name: 'sauditOperName', index: 'SAUDIT_OPER_NAME', label: "审批人姓名", width: 150},
        {name: 'sremark', index: 'sremark', label: "备注", width: 150},
        {name: 'process', title:false,index: 'process', label: "操作", sortable: false, width: 150}
    ];
    function tableCallBack() {
        setTimeout(function () {
            var ids = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < ids.length; i++) {
                var str = "";
                var cl = ids[i].id;
                if (ids[i].istatus == 20) {
                    <shiro:hasPermission name="DOMAINCONF_ENABLE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/close.png' class=\"oper_icon\" onclick=\"enableMethod('"
                        + cl + "',40)\" title='停用' alt='停用'  /> | ";
          /*          str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"enableMethod('"
                        + cl + "',40)\">停用</a> |  ";*/
                    </shiro:hasPermission>
                } else {
                    <shiro:hasPermission name="DOMAINCONF_EDIT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                        + cl + "')\" title='编辑域名' alt='编辑域名'  /> | ";
                    </shiro:hasPermission>
                    <shiro:hasPermission name="DOMAINCONF_DELETE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                        + cl + "')\" title='删除域名' alt='删除域名'  /> | ";
                    </shiro:hasPermission>
                    if (ids[i].istatus == 40) {
                        <shiro:hasPermission name="DOMAINCONF_ENABLE">
                        str += "<img src='${staticSource}/resources/images/oper_icon/open.png' class=\"oper_icon\" onclick=\"enableMethod('"
                            + cl + "',10)\" title='启用' alt='启用'  /> | ";
                       /* str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"enableMethod('"
                            + cl + "',10)\">启用</a> |  ";*/
                        </shiro:hasPermission>
                    } else {
                        <shiro:hasPermission name="DOMAINCONF_ENABLE">
                        str += "<img src='${staticSource}/resources/images/oper_icon/close.png' class=\"oper_icon\" onclick=\"enableMethod('"
                            + cl + "',40)\" title='停用' alt='停用'  /> | ";
                       /* str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"enableMethod('"
                            + cl + "',40)\">停用</a> |  ";*/
                        </shiro:hasPermission>
                        <shiro:hasPermission name="DOMAINCONF_EXAMINE">
                        str += "<img src='${staticSource}/resources/images/order/shenhe.png' class=\"order\" onclick=\"exMethod('"
                            + cl + "')\" title='审核' alt='审核'  /> | ";
                      /*  str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"exMethod('"
                            + cl + "')\">审核</a> |  ";*/
                        </shiro:hasPermission>
                    }
                }
                $("#gridTable").jqGrid('setRowData',
                    cl, {
                        process : str.length > 0 ? str.substr(0, str.lastIndexOf(" | ")) : ""
                    });
            }
        }, 0);
    }
    $(function () {
        var config = {
            sortname: "ISTATUS=10",
            sortorder: "desc"
        };
        initTable(ctx + "/domainconf/queryData", colModel, tableCallBack, config);
    });
    //初始化form中按钮
    var initBtnConfig = {
        addUrl: ctx + '/domainconf/edit',
        deleteUrl: ctx + "/domainconf/delete",
        addTitle: '添加域名',
        addFn:showLayerMin
    };
    initBtnByForm4(initBtnConfig);


    layui.use('form', function () {
        var $ = layui.$, active = {

            refreshCache: function () {//刷新缓存
                var loadIndex = loading();
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: ctx + '/domainconf/cache',
                    success: function (msg) {
                        closeLayer(loadIndex);//关闭加载框
                        if (msg.success) {
                            alertInfo(msg.data)
                        } else {
                            alertDel(msg.data);
                        }
                    }
                });
            }
        };
        $('.layui-form .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });
    function editMethod(sid) {
        showLayerMin("编辑域名", ctx + '/domainconf/edit?sid=' + sid);
    }
    function delMethod(sid) {
        confirmDelTip("确定要删除数据?", ctx + "/domainconf/delete", {checkboxId: sid});
    }
    function exMethod(sid) {
        showLayerMin("审核域名", ctx + '/domainconf/examine?sid=' + sid);
    }
    function enableMethod(sid, type) {
        var alertStr = "确定要启用该域名?";
        if (type == 40) {
            alertStr = "确定要停用该域名?";
        }
        confirmOperTip(alertStr, ctx + "/domainconf/enable", {checkboxId: sid, type: type});
    }
</script>
</body>
</html>

