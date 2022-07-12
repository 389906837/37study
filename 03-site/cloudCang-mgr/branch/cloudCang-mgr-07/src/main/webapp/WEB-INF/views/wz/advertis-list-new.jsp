<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>广告区域管理</title>
<link href="${staticSource}/resources/layui/css/layui.css?201" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>广告区域管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="sregionCode" id="sregionCode" value="" placeholder="运营区域编号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sregionName" id="sregionName" value="" placeholder="运营区域名称..."
                                       class="layui-input">
                            </div>
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-input-inline">
                                    <input type="text" name="smerchantCode" id="smerchantCode" value=""
                                           placeholder="商户编号..." class="layui-input">
                                </div>
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantName" id="merchantName" value=""
                                           placeholder="商户名称..." class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-input-inline">
                                <input type="text" name="stitle" id="stitle" value="" placeholder="标题..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" list="{0:已过期,1:投放中,2:待投放,3:暂停}" id="istatus"
                                             name="istatus" entire="true" entireName="请选择状态"/>
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" list="{10:图片,20:视频,30:音频}" id="iadvType"
                                             name="iadvType" entire="true" entireName="请选择广告类型"/>
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" list="{10:横屏,20:竖屏}" id="iscreenDisplayType"
                                             name="iscreenDisplayType" entire="true" entireName="请选择屏幕显示类型"/>
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" list="{0:否,1:是}" id="iuseType"
                                             name="iuseType" entire="true" entireName="广告是否通用"/>
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i
                                        class="layui-icon">&#xe615;</i>查询
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i
                                        class="layui-icon">&#x1006;</i>清除
                                </button>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <shiro:hasPermission name="ADVERTIS_ADD">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" style="margin-left: 0px;"
                                        data-type="add"><i class="layui-icon"></i>添加
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="ADVERTIS_DELETE">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i>删除
                                </button>
                            </shiro:hasPermission>
                            <button class="layui-btn layui-btn-sm layui-btn-hui" data-type="advertisDeviceBind">
                                <i class="layui-icon">&#xe60b;</i>设备广告管理
                            </button>

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
    function reloadData(aid) {
        if (!isEmpty(aid)) {
            $("#aid").val(aid);
        }
        resetReloadGrid();
    }
    /**
     * 选择日期范围
     */
    layui.use(['form', 'laydate'], function () {
        var laydate = layui.laydate;
        laydate.render({
            elem: '#tendDateStart' //指定元素
        });
        laydate.render({
            elem: '#tendDateEnd' //指定元素
        });
        var $ = layui.$, active = {
            advertisDeviceBind: function () {//设备广告绑定
                var sid = $("#gridTable").jqGrid('getGridParam', 'selarrrow');
                if (isEmpty(sid)) {
                    alertDel("请先选择要要绑定的资讯信息");
                    return;
                }
                showLayerMax("设备广告绑定", ctx + "/advertis/toBinding?checkboxId=" + sid.join(","));
            }
        };
        $('.layui-form .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });
    //渲染数据
    var colModel = [{name: 'id', index: 'id', hidden: true},
        {name: 'sregionCode', label: "运营区域编号", index: 'sregion_code', width: 150, sortable: false},
        {name: 'sregionName', label: "运营区域名称", index: 'sregionName', width: 150, sortable: false},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode', label: "商户编号", index: 'smerchant_code', width: 180},
        {name: 'merchantName', label: "商户名称", index: 'MERCHANTNAME', width: 360, sortable: false},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'smerchantCode', index: 'smerchant_code', hidden: true},
        </c:if>
        {name: 'stitle', index: 'stitle', label: "标题", width: 200, sortable: false},
        {
            name: 'istatus', index: 'istatus', label: "状态", editable: true,
            formatter: function (value) {
                return '<span class="' + (({
                        "0": "istatus-radius",
                        "1": "istatus-normal",
                        "2": "istatus-warm",
                        "3": "istatus-danger"
                    })[value]) + '"> ' + (({"0": "● 已过期", "1": "● 投放中", "2": "● 待投放", "3": "● 暂停"})[value]) + '</span>'
            },
            sortable: false, width: 120
        },
        {name: 'isort', index: 'isort', label: "排序号", width: 80, sortable: false},
        {
            name: 'iadvType', index: 'iadvType', label: "广告类型", editable: true,
            formatter: "select", editoptions: {value: '10:图片;20:视频;30:音频'}, sortable: false, width: 120
        },
        {
            name: 'iuseType', index: 'iuseType', label: "是否通用", editable: true,
            formatter: "select", editoptions: {value: '0:否;1:是'}, sortable: false, width: 100
        },
        {
            name: 'iscreenDisplayType', index: 'iscreenDisplayType', label: "屏幕显示类型", editable: true,
            formatter: "select", editoptions: {value: '10:横屏;20:竖屏'}, sortable: false, width: 150
        },
        {
            name: 'tstartDate', index: 'TSTART_DATE', label: "开始日期", formatter: function (value) {
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }, width: 210
        },
        {
            name: 'tendDate', index: 'TEND_DATE', label: "结束日期", formatter: function (value) {
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }, width: 210
        },
        {name: 'process', title: false, index: 'process', label: "操作", sortable: false, width: 170}
    ];
    // 表格回调
    function tableCallBack() {
        setTimeout(function () {
            var ids = $("#gridTable").jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                var cl = ids[i];
                var str = "";
                <shiro:hasPermission name="ADVERTIS_EDIT">
                str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                    + cl + "')\" title='编辑广告区域' alt='编辑广告区域'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="ADVERTIS_DELETE">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='删除广告区域' alt='删除广告区域'  /> | ";
                </shiro:hasPermission>
                $("#gridTable").jqGrid('setRowData',
                    ids[i], {
                        process: str.length > 0 ? str.substr(0, str.lastIndexOf(" | ")) : ""
                    });
            }
        }, 0);
    }

    $(function () {
        var config = {
            sortname: "A.ISTATUS=2",
            sortorder: "desc"
        };
        initTable(ctx + "/advertis/queryData", colModel, tableCallBack, config);
    });

    //初始化form中按钮
    var initBtnConfig = {
        addUrl: ctx + '/advertis/edit',
        deleteUrl: ctx + "/advertis/delete",
        addTitle: '添加广告区域信息',
        addFn: showLayerMedium
    };
    initBtnByForm4(initBtnConfig);

    function editMethod(aid) {
        showLayerMedium("编辑广告区域信息", ctx + "/advertis/edit?aid=" + aid);
    }
    function delMethod(aid) {
        confirmDelTip("确定要删除数据?", ctx + "/advertis/delete", {checkboxId: aid});
    }

</script>
</body>
</html>

