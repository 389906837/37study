<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>开放平台用户管理</title>
<link href="${staticSource}/resources/layui/css/layui.css?1" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>开放平台用户管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder="会员编号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="snickName" id="snickName" value="" placeholder="会员昵称..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="smobile" id="smobile" value="" placeholder="手机号码..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="semail" id="semail" value="" placeholder="邮箱..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" list="{0:女,1:男}" id="isex"
                                             name="isex" entire="true" entireName="请选择会员性别"/>
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" list="{10:个人会员,20:企业会员}" id="imemberType"
                                             name="imemberType" entire="true" entireName="请选择会员类型"/>
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" list="{1:正常,2:注销停用,3:系统黑名单,4:冻结}" id="istatus"
                                             name="istatus" entire="true" entireName="请选择会员状态"/>
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" list="{0:否,1:是}" id="iisMerchaant"
                                             name="iisMerchaant" entire="true" entireName="是否挂靠后台商户"/>
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
                            <shiro:hasPermission name="USER_INFO_ADD">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"><i
                                        class="layui-icon"></i>添加
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="USER_INFO_DEL">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i
                                        class="layui-icon"></i>删除
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
        {name: 'snickName', index: 'SNICK_NAME', label: "用户昵称", width: 70},
        {name: 'smobile', index: 'SMOBILE', label: "手机号码", width: 80},
        {name: 'semail', index: 'SEMAIL', label: "邮箱", width: 100},
        {
            name: 'isex', index: 'ISEX', label: "性别", width: 30, formatter: "select",
            editoptions: {value: '0:女;1:男'}
        },
        {
            name: 'dbirthdate', index: 'DBIRTHDATE', label: "生日", width: 80, formatter: function (value) {
            if (isEmpty(value)) {
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd');
        }
        },
        {
            name: 'imemberType', index: 'IMEMBER_TYPE', label: "会员类型", width: 80, formatter: "select",
            editoptions: {value: '10:个人会员;20:企业会员'}
        }, {
            name: 'tregisterTime', index: 'TREGISTER_TIME', label: "注册时间", width: 100, formatter: function (value) {
                if (isEmpty(value)) {
                    return '';
                }
                return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
            }
        },
        {
            name: 'istatus', index: 'istatus', label: "状态", width: 60, formatter: "select",
            editoptions: {value: '1:正常;2:注销停用;3:系统黑名单;4:冻结'}
        }, {
            name: 'iisMerchaant', index: 'IIS_MERCHAANT', label: "是否挂靠后台商户", width: 80, formatter: "select",
            editoptions: {value: '0:否;1:是'}
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
                <shiro:hasPermission name="USER_INFO_DEL">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='删除' alt='删除' /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="USER_INFO_EDIT">
                str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                    + cl + "')\" title='编辑' alt='编辑'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="USER_INFO_UP_PASSWORD">
                str += "<img src='${staticSource}/resources/images/oper_icon/update_pwd.png' class=\"oper_icon\" onclick=\"updatePasswordMethod('"
                    + cl + "')\" title='修改密码' alt='修改密码'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="USER_INFO_RESET_PASSWORD">
                str += "<img src='${staticSource}/resources/images/oper_icon/reset_pwd.png' class=\"oper_icon\" onclick=\"resetPasswordMethod('"
                    + cl + "')\" title='重置密码' alt='重置密码'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="USER_INFO_OPEN_INTERFACE_AUTH">
                str += "<img src='${staticSource}/resources/images/rec/openInterfaceAuth.png' class=\"oper_icon\" onclick=\"openInterfaceAuthMethod('"
                    + cl + "')\" title='开通接口权限' alt='开通接口权限'  /> | ";
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
            sortname: "",
            sortorder: ""
        };
        initTable(ctx + "/userInfo/queryData", colModel, tableCallBack, config);
    });
    // 调用Button按钮方法(查询,清除)
    //初始化form中按钮
    var initBtnConfig = {
        addUrl: ctx + '/userInfo/edit',
        deleteUrl: ctx + "/userInfo/delete",
        addTitle: '添加开放平台用户',
        addFn: showLayerMedium
    };
    initBtnByForm4(initBtnConfig);

    function editMethod(sid) {
        showLayerMedium("编辑用户", ctx + '/userInfo/edit?sid=' + sid);
    }
    function delMethod(sid) {
        confirmDelTip("确定要删除用户?", ctx + "/userInfo/delete", {checkboxId: sid});
    }
    function updatePasswordMethod(sid) {
        showLayerMin("修改密码", ctx + '/userInfo/toUpdatePassword?sid=' + sid, {area: ['30%', '32%']});
    }
    function openInterfaceAuthMethod(sid) {
        showLayerMedium("开通接口权限", ctx + '/userInfo/openInterfaceAuth?sid=' + sid);
    }
    function resetPasswordMethod(sid) {
        confirmOperTip("确定要重置密码?", ctx + "/userInfo/resetPassword", {checkboxId: sid});
    }
</script>
</body>
</html>

