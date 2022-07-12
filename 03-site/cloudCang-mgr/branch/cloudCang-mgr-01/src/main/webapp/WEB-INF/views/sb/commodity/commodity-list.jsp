<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备商品列表</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>设备商品管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="sdeviceCode" id="sdeviceCode" value="" placeholder="设备编号..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sname" id="sname" value="" placeholder="设备名称..." class="layui-input">
                            </div>
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantCode" id="merchantCode" value="" placeholder="商户编号..."
                                           class="layui-input">
                                </div>
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantName" id="merchantName" value="" placeholder="商户名称..."
                                           class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-input-inline">
                                <input type="text" name="scommodityCode" id="scommodityCode" value="" placeholder="商品编号..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="commodityName" id="commodityName" value="" placeholder="商品名称..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control"  name="istatus" id="istatus" >
                                    <option value="">商品状态</option>
                                    <option value="10">在售</option>
                                    <option value="20">下架</option>
                                </select>
                            </div>
                        </div>
                        <div class="layui-form-item">
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
        {name: 'sdeviceCode', index: 'sdeviceCode', label: "设备编号",width:120,sortable : false},
        {name: 'sname', index: 'SNAME', label: "设备名称",width:120,sortable : false},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'merchantCode', index: 'merchantCode', label: "商户编号",width:160,sortable : false},
        {name: 'merchantName', index: 'merchantName', label: "商户名称",width:200,sortable : false},
        </c:if>
        {name: 'scommodityCode', index: 'scommodityCode', label: "商品编号",width:120,sortable : false},
        {name: 'commodityFullName', index: 'commodityName', label: "商品名称",width:120,sortable : false},
        {name: 'totalSalesNum', index: 'totalSalesNum', label: "总销量",width:120,sortable : false},
        {
            name: 'istatus',
            index: 'ISTATUS',
            label: "商品状态",
            formatter: "select",
            editoptions: {value: '10:在售;20:下架'}
            , width: 100
        },
//                {
//                    name: 'taddTime', index: 'TADD_TIME', label: "添加日期", formatter: function (value) {
//                    return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
//                }
//                },
//                {
//                    name: 'tupdateTime', index: 'TUPDATE_TIME', label: "修改日期", formatter: function (value) {
//                    return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
//                }
//                },
        {name: "process",title:false, index: "process", label: "操作", sortable: false, frozen: true, width: 100}
    ];
    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            var data = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = '';
                <%--<shiro:hasPermission name="DEVICE_COMMODITY_VIEW">--%>
                <%--str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"viewMethod('"--%>
                <%--+ cl + "')\">查看</a> | ";--%>
                <%--</shiro:hasPermission>--%>
                if (data[i].istatus == 20) {
                    <%--<shiro:hasPermission name="DEVICE_COMMODITY_SHELF">--%>
                    <%--str += "<img src='${staticSource}/resources/images/oper_icon/shelf.png' class=\"oper_icon\" onclick=\"shelf('"--%>
                        <%--+ cl + "')\" title='上架' alt='上架'  /> | ";--%>
                    <%--</shiro:hasPermission>--%>
                }else if (data[i].istatus == 10) {
                    <shiro:hasPermission name="DEVICE_COMMODITY_DROPOFF">
                    str += "<img src='${staticSource}/resources/images/oper_icon/drop_off.png' class=\"oper_icon\" onclick=\"dropoff('"
                        + cl + "')\" title='下架' alt='下架'  /> | ";
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
            sortname: "A.ISTATUS=10",
            sortorder: "desc"
        };
        initTable(ctx + "/device/commodity/queryData",colModel,tableCallBack,config);
    });

    initBtnByForm2();

//    function viewMethod(sid) {
//        showLayer("详情", ctx + '/device/commodity/toView?sid=' + sid, {area: ['90%', '80%']});
//    }
    function shelf(sid) {
        confirmOperTip("确定要上架该商品?", ctx + "/device/commodity/shelf", {checkboxId: sid});
    }
    function dropoff(sid) {
        confirmOperTip("确定要下架该商品?", ctx + "/device/commodity/dropoff", {checkboxId: sid});
    }
</script>
</body>
</html>


