<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>第三方商户设备SKU库列表</title>
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
                    <h5>第三方商户设备SKU库管理</h5>
                    <input type="hidden" id="sdeviceId" name="sdeviceId" value="${sdeviceId}"/>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="sthirdPartSkuCode" id="sthirdPartSkuCode" value=""
                                       placeholder="第三方SKU编号..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sname" id="sname" value="" placeholder="视觉商品名称..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="svrCode" id="svrCode" value="" placeholder="本地视觉识别编号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="iweight" id="iweight" value="" placeholder="视觉商品重量..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control"  name="iisLowerShelf" id="iisLowerShelf" >
                                    <option value="">下架</option>
                                    <option value="0">否</option>
                                    <option value="1">是</option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
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
    var colModel =[
        {name: 'id', index: 'id', hidden: true},
        {name: 'sthirdPartSkuCode',label : "第三方SKU编号",index: 'STHIRD_PART_SKU_CODE',sortable : false,width:100},
        {name: 'sname', index: 'SNAME', label: "视觉商品名称",sortable : false,width:200},
        {name: 'svrCode',label : "本地视觉识别编号",index: 'SVR_CODE',sortable : false,width:100},
        {name: 'iweight', index: 'IWEIGHT', label: "视觉商品重量（g）",sortable : false,width:100},
        {   name: 'iisLowerShelf',
            index: 'IIS_LOWER_SHELF',
            label: "下架状态",
            formatter: "select",
            editoptions: {value: '0:否;1:是'},
            sortable : false,
            width:60},
        // {name: "process",title:false, index: "process", label: "操作", sortable: false, frozen: true, width: 200}
    ];

    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            var data = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = '';
                <%--<shiro:hasPermission name="TB_INTERFACE_TRANSFER_LOG_VIEW">--%>
                str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"viewMethod('"
                    + cl + "')\" title='查看' alt='查看'  /> | ";
                <%--</shiro:hasPermission>--%>
                $("#gridTable").jqGrid('setRowData',
                    cl, {
                        process: str.substr(0, str.lastIndexOf(" | "))
                    });
            }
        }, 0);
    }

    <%--var id = ${sdeviceId}+"";--%>
    $(function () {
        var config = {
            sortname: "",
            sortorder: "",
            multiselect:false
        };
        initTable(ctx + "/tbThirdDeviceSku/queryViewData?sdeviceId=" + $("#sdeviceId").val(), colModel, tableCallBack, config);
    });
    var initBtnConfig = {
        addUrl: ctx+'/tbThirdDeviceSku/toAdd',
        addTitle: 'xxxx',
        addFn:showLayerOne
    };
    initBtnByForm4(initBtnConfig);



</script>
</body>
</html>



