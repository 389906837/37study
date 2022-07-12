<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备后台操作记录</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>设备后台操作记录</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <select class="form-control" name="itype" id="itype">
                                    <option value="">操作类型</option>
                                    <option value="10">调节音量</option>
                                    <option value="20">重启设备</option>
                                    <option value="30">关机</option>
                                    <option value="40">定时开关机</option>
                                    <option value="50">主动盘货</option>
                                    <option value="60">云端购物车开启</option>
                                    <option value="70">云端购物车关闭</option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control" name="ideviceType" id="ideviceType">
                                    <option value="">操作对象类型</option>
                                    <option value="10">全部</option>
                                    <option value="20">部分</option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="saddUser" id="saddUser" value="" placeholder="操作人..."
                                       class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query">
                                    <i class="layui-icon">&#xe615;</i>查询
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset">
                                    <i class="layui-icon">&#x1006;</i>清除
                                </button>
                            </div>
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
        {   name: 'itype',
            index: 'ITYPE',
            label: "操作类型",
            formatter: "select",
            editoptions: {value: '10:调节音量;20:重启设备;30:关机;40:定时开关机;50:主动盘货;60:云端购物车开启;70:云端购物车关闭'},
            width:120
        },
        {   name: 'ideviceType',
            index: 'IDEVICE_TYPE',
            label: "操作对象类型",
            formatter: "select",
            editoptions: {value: '10:全部;20:部分'},
            width:120
        },
        {name: 'saddUser', index: 'SADD_USER', label: "操作人",width:80},
        {name: 'taddTime', index: 'TADD_TIME', label: "操作日期",formatter: function (value) {
                return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
            },width:120},
        {name: "process",title:false, index: "process", label: "操作", sortable: false, frozen: true, width: 200}
    ];

    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            var data = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = '';
                <shiro:hasPermission name="DEVICE_BACKSTAGEOPER_VIEW">
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
            multiselect:false,
            sortname: "TADD_TIME",
            sortorder: "desc"
        };
        initTable(ctx+"/device/backstageOper/queryData",colModel,tableCallBack,config);
    });

    initBtnByForm2();

    function viewMethod(sid) {
        showLayerMedium("操作内容/对象详情", ctx + '/device/backstageOper/view?sid=' + sid);
    }

</script>
</body>
</html>



