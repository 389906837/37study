<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备升级记录列表</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
<style>
    .ui-jqgrid tr.jqgrow td {
        white-space: normal !important;
        height: auto;
    }
    .layui-btn-hui{background:#f2f2f2;border:1px solid #e6e6e6;color:#323232;}
    .layui-btn-hui:hover{color:#323232;}
</style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <%--<h5>设备商品管理</h5>--%>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="iversion" id="iversion" value="" placeholder="版本号..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sresourcesUrl" id="sresourcesUrl" value="" placeholder="升级包url..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control"  name="itype" id="itype" >
                                    <option value="">升级类型</option>
                                    <option value="10">视觉服务</option>
                                    <option value="20">视觉库</option>
                                    <option value="30">客户端apk</option>
                                    <option value="40">开门语音</option>
                                    <option value="50">购物中语音</option>
                                    <option value="60">关门语音</option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control"  name="ideviceType" id="ideviceType" >
                                    <option value="">升级范围</option>
                                    <option value="10">全部</option>
                                    <option value="20">部分</option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control"  name="iactionType" id="iactionType" >
                                    <option value="">升级时间类型</option>
                                    <option value="10">立即</option>
                                    <option value="20">定时</option>
                                </select>
                            </div>
                            <%--<div class="layui-input-inline">--%>
                                <%--<select class="form-control"  name="istatus" id="istatus" >--%>
                                    <%--<option value="">完成状态</option>--%>
                                    <%--<option value="10">草稿</option>--%>
                                    <%--<option value="20">已通知</option>--%>
                                    <%--<option value="30">已完成</option>--%>
                                    <%--<option value="40">已取消</option>--%>
                                <%--</select>--%>
                            <%--</div>--%>
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

                        </div>
                        <div class="layui-form-item">
                            <%-- <button class="layui-btn layui-btn-sm " data-type="add"><i class="layui-icon"></i>添加</button>
                             <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i>删除</button>--%>
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
        {
            name: 'itype',
            index: 'ITYPE',
            label: "升级类型",
            formatter: "select",
            editoptions: {value: '10:视觉服务;20:视觉库;30:客户端Apk;40:开门语音;50:购物中语音;60:关门语音'}
            , width: 100
        },
        {name: 'iversion', index: 'IVERSION', label: "版本号",width:50,sortable : false},
        {name: 'sresourcesUrl', index: 'SRESOURCES_URL', label: "升级包url",width:200,sortable : false},
        {name: 'ideviceNum', index: 'IDEVICE_NUM', label: "计划升级数量",width:80,sortable : false},
        {name: 'ideviceNum', index: 'INOTICE_NUM', label: "通知完成数量",width:80,sortable : false},
        {name: 'iupgradeSuccessNum', index: 'IUPGRADE_SUCCESS_NUM', label: "升级成功数量",width:80,sortable : false},
        {name: 'iupgradeFailNum', index: 'IUPGRADE_FAIL_NUM', label: "升级失败数量",width:80,sortable : false},
        {
            name: 'ideviceType',
            index: 'IDEVICE_TYPE',
            label: "升级范围",
            formatter: "select",
            editoptions: {value: '10:全部;20:部分'}
            , width: 80
        },
        {
            name: 'istatus',
            index: 'ISTATUS',
            label: "完成状态",
            formatter: "select",
            editoptions: {value: '10:草稿;20:已通知;30:已完成;40:已取消;50:部分完成'}
            , width: 80
        },
        {
            name: 'iactionType',
            index: 'IACTION_TYPE',
            label: "升级时间类型",
            formatter: "select",
            editoptions: {value: '10:立即;20:定时'}
            , width: 80
        },
        {
            name: 'sexecutionTime', index: 'SEXECUTION_TIME', width: 80,label: "定时执行时间", formatter: function (value) {
            if(isEmpty(value)){
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }
        },
        {
            name: 'taddTime', index: 'TADD_TIME', width: 80,label: "添加时间", formatter: function (value) {
            if(isEmpty(value)){
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }
        },
        {name: "process",title:false, index: "process", label: "操作", sortable: false, frozen: true, width: 100}
    ];
    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            var data = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = '';
                <shiro:hasPermission name="DEVICE_UPGRADE_VIEW">
                str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"viewMethod('"
                    + cl + "')\" title='查看' alt='查看'  /> | ";
                </shiro:hasPermission>
                $("#gridTable").jqGrid('setRowData',
                    cl, {
                        process: str.substr(0, str.lastIndexOf(" | "))
                    });
            }
        }, 0);
    }
    $(function () {
        var config = {
            sortname: "A.ISTATUS",
            sortorder: "desc"
        };
        initTable(ctx + "/device/upgrade/queryData",colModel,tableCallBack,config);
    });

    initBtnByForm2();

    function viewMethod(sid) {
        showLayerMax("详情", ctx + '/device/upgrade/toView?sid=' + sid);
    }


</script>
</body>
</html>


