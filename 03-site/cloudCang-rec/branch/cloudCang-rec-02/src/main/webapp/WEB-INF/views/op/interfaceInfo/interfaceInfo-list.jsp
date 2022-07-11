<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>平台接口信息管理</title>
<link href="${staticSource}/resources/layui/css/layui.css?1" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>平台接口信息管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder="接口编号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sname" id="sname" value="" placeholder="接口名称..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="saction" id="saction" value="" placeholder="接口动作..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="saddress" id="saddress" value="" placeholder="接口地址..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <cang:select name="ipayType" id="ipayType" type="dic" entire="true" groupNo="SP000165"
                                             entireName="请选择接口收费类型"/>
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" list="{10:免费无需开通,20:免费需开通,30:收费接口}" id="ipayWay"
                                             name="ipayWay" entire="true" entireName="请选择接口收费方式"/>
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" list="{10:同步接口,20:异步接口}" id="itype"
                                             name="itype" entire="true" entireName="请选择接口类型"/>
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" list="{10:待上线,20:已上线,30:已废弃}" id="istatus"
                                             name="istatus" entire="true" entireName="请选择接口状态"/>
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
                            <shiro:hasPermission name="INTERFACE_INFO_ADD">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"><i
                                        class="layui-icon"></i>添加
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="INTERFACE_INFO_DEL">
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
        {name: 'scode', index: 'scode', label: "接口编号", width: 80},
        {name: 'sname', index: 'SNAME', label: "接口名称", width: 80},
        {name: 'saction', index: 'SACTION', label: "接口动作", width: 100},
        {name: 'saddress', index: 'SADDRESS', label: "接口地址", width: 150},
        {
            name: 'ipayType', index: 'IPAY_TYPE', label: "接口收费类型", width: 120, formatter: "select",
            editoptions: {value: '10:按次计费;20:按单位次数计费 （识别图片张数）;30:按时间计费;40:通用时间按次收费;50:通用时间按单位次数计费'}
        },
        {
            name: 'ipayWay', index: 'IPAY_WAY', label: "接口收费方式", width: 60, formatter: "select",
            editoptions: {value: '10:免费无需开通;20:免费需开通;30:收费接口'}
        },
        {
            name: 'itype', index: 'ITYPE', label: "接口类型", width: 60, formatter: "select",
            editoptions: {value: '10:同步接口;20:异步接口'}
        },
        {
            name: 'istatus', index: 'ISTATUS', label: "接口状态", width: 60, formatter: "select",
            editoptions: {value: '10:待上线;20:已上线;30:已废弃'}
        },
        {name: "process", index: "process", title: false, label: "操作", sortable: false, frozen: true, width: 60}
    ]
    // 表格回调
    function tableCallBack() {
        setTimeout(function () {
            var ids = $("#gridTable").jqGrid('getRowData');
            var merchantCode = "${SESSION_KEY_SMERCHANT_CODE}";
            for (var i = 0; i < ids.length; i++) {
                var cl = ids[i].id;
                var str = '';
                <shiro:hasPermission name="INTERFACE_INFO_EDIT">
                str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                    + cl + "')\" title='编辑' alt='编辑'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="INTERFACE_INFO_EDIT_STATUS">
                str += "<img src='${staticSource}/resources/images/rec/editServerStatus.png' class=\"oper_icon\" onclick=\"editStatusMethod('"
                    + cl + "')\" title='编辑接口状态' alt='编辑接口状态'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="INTERFACE_INFO_DEL">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='删除' alt='删除' /> | ";
                </shiro:hasPermission>
                /* if (10 == ids[i].iauditStatus) {
                 str += "<img src=/resources/images/order/shenhe.png' class=\"order\" onclick=\"exMethod('"
                 + cl + "')\" title='审核' alt='审核' /> | ";
                 }*/
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
        initTable(ctx + "/interfaceInfo/queryData", colModel, tableCallBack, config);
    });
    // 调用Button按钮方法(查询,清除)
    //初始化form中按钮
    var initBtnConfig = {
        addUrl: ctx + '/interfaceInfo/edit',
        deleteUrl: ctx + "/interfaceInfo/delete",
        addTitle: '添加平台接口信息',
        addFn: showLayerMedium
    };
    initBtnByForm4(initBtnConfig);

    function editMethod(sid) {
        showLayerMedium("编辑平台接口信息", ctx + '/interfaceInfo/edit?sid=' + sid);
    }
    function editStatusMethod(sid) {
        showLayerMedium("编辑平台接口状态", ctx + '/interfaceInfo/editStatus?sid=' + sid, {area: ['30%', '40%']});
    }

    function delMethod(sid) {
        confirmDelTip("确定要删除平台接口信息?", ctx + "/interfaceInfo/delete", {checkboxId: sid});
    }
    function exMethod(sid) {
        showLayerMin("平台接口信息审核", ctx + '/interfaceInfo/examine?sid=' + sid);
    }
</script>
</body>
</html>

