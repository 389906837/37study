<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>人脸注册信息列表</title>
<link href="${staticSource}/resources/layui/css/layui.css?5" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
<style>
    .ui-jqgrid tr.jqgrow td {
        white-space: normal !important;
        height: auto;
    }
</style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>人脸注册信息管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="sfaceCode" id="sfaceCode" value="" placeholder="人脸信息编号..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="srealName" id="srealName"  placeholder="用户名..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control" name="iauditStatus" id="iauditStatus">
                                    <option value="">请选择审核状态</option>
                                    <option value="10">待审核</option>
                                    <option value="20">审核通过</option>
                                    <option value="30">审核拒绝</option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control" name="itype" id="itype">
                                    <option value="">请选择注册状态</option>
                                    <option value="10">已注册</option>
                                    <option value="20">已删除</option>
                                    <option value="30">已申请</option>
                                </select>
                            </div>
                            <%--<div class="layui-input-inline">--%>
                            <%--<input type="text" name="sauditUser" id="sauditUser" value="" placeholder="审核人..." class="layui-input">--%>
                            <%--</div>--%>
                        </div>
                        <div class="layui-form-item" style="margin-top: 10px;">
                            <div class="layui-input-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query">
                                    <i class="layui-icon">&#xe615;</i>查询
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset">
                                    <i class="layui-icon">&#x1006;</i>清除
                                </button>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <shiro:hasPermission name="ANNUAL_MEETINGFACE_INFO_ADD">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"><i
                                        class="layui-icon"></i>添加
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="ANNUAL_MEETINGFACE_INFO_DELETE">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i>删除
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="ANNUAL_MEETINGFACE_INFO_BATCHIMPORT">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="faceBatchImport"><i
                                        class="layui-icon"></i>批量导入
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
    var colModel = [
        {name: 'id', index: 'id', hidden: true},
        {name: 'sfaceCode', index: 'SFACE_CODE', label: "人脸信息编号",width:100},
        {name: 'srealName', index: 'SREAL_NAME', label: "用户名",width:100},
        {name: 'sgroupId', index: 'SGROUP_ID', label: "用户组",width:100},
        {
            name: 'iauditStatus',
            index: 'IAUDIT_STATUS',
            label: "审核状态",
            formatter: "select",
            editoptions: {value: '10:待审核;20:审核通过;30:审核拒绝'}
            ,width:100
        },
        {
            name: 'itype',
            index: 'ITYPE',
            label: "注册状态",
            formatter: "select",
            editoptions: {value: '10:已注册;20:已删除;30:已申请'}
            ,width:100
        },
        {
            name: 'iregSource',
            index: 'IREG_SOURCE',
            label: "注册来源",
            formatter: "select",
            editoptions: {value: '10:后台;20:设备;30:H5页面'}
            ,width:100
        },
        {name: 'tregisterTime', index: 'TREGISTER_TIME', label: "注册时间",width: 80,formatter: function (value) {
                if(isEmpty(value)){
                    return '';
                }
                return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');}},

        {name: "process", title:false, index: "process", label: "操作", sortable: false, frozen: true, width: 100}
    ];
    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            var data = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = '';
                <shiro:hasPermission name="ANNUAL_MEETINGFACE_INFO_VIEW">
                str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"viewMethod('"
                    + cl + "')\" title='查看' alt='查看'  /> | ";
                </shiro:hasPermission>
                if (data[i].iauditStatus == 10) {
                    <shiro:hasPermission name="ANNUAL_MEETINGFACE_INFO_EDIT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                        + cl + "')\" title='编辑' alt='编辑'  /> | ";
                    </shiro:hasPermission>
                    <shiro:hasPermission name="ANNUAL_MEETINGFACE_INFO_AUDIT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/review.png' class=\"oper_icon\" onclick=\"auditMethod('"
                        + cl + "')\" title='审核' alt='审核'  /> | ";
                    </shiro:hasPermission>
                }
                if ((data[i].iauditStatus == 10 && data[i].itype != 20) || (data[i].iauditStatus == 30 && data[i].itype != 20)) {
                    <shiro:hasPermission name="ANNUAL_MEETINGFACE_INFO_DELETE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                        + cl + "')\" title='删除' alt='删除'  /> | ";
                    </shiro:hasPermission>
                }
                $("#gridTable").jqGrid('setRowData',
                    cl, {
                        process: str.substr(0, str.lastIndexOf(" | "))
                    });
            }
        }, 0);
    }

    $(function () {
        var config = {
            sortname: "ITYPE=10",
            sortorder: "desc"
        };
        initTable(ctx+"/annualMeetingFaceInfo/queryData",colModel,tableCallBack,config);
    });

    var initBtnConfig = {
        addUrl: ctx+'/annualMeetingFaceInfo/toAdd',
        deleteUrl: ctx+"/annualMeetingFaceInfo/delete",
        addTitle: '添加人脸注册信息',
        addFn:showLayerMedium
    };
    initBtnByForm4(initBtnConfig);

    layui.use('form', function () {
        var $ = layui.$, active = {
            faceBatchImport : function () {
                showLayerOne("批量导入人脸信息", ctx + "/annualMeetingFaceInfo/batchImport");
            }
        };
        $('.layui-form .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });

    function viewMethod(sid) {
        showLayerOne("查看人脸注册信息", ctx + '/annualMeetingFaceInfo/toView?sid=' + sid);
    }
    function editMethod(sid) {
        showLayerOne("编辑人脸注册信息", ctx + '/annualMeetingFaceInfo/toEdit?sid=' + sid);
    }
    function auditMethod(sid) {
        showLayerOne("审核人脸注册信息", ctx + '/annualMeetingFaceInfo/toAudit?sid=' + sid);
    }
    function delMethod(sid) {
        confirmDelTip("确定要删除数据?", ctx + "/annualMeetingFaceInfo/delete", {checkboxId: sid});
    }
</script>
</body>
</html>


