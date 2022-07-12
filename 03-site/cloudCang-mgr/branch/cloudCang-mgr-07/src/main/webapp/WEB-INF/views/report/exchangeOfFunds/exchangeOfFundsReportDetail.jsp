<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>资金往来报表详情</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet"/>
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css" rel="stylesheet"/>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <%-- <div class="ibox-title">
                     <h5>资金往来报表详情</h5>
                 </div>--%>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-inline" style="width: 210px">
                                <input type="text" name="commodityName" id="commodityName" placeholder="商品名称"
                                       class="layui-input">
                            </div>

                            <div class="layui-inline">
                                <button class="layui-btn layui-btn layui-btn" id="searchBtn" data-type="query"><i
                                        class="layui-icon">&#xe615;</i>查询
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i
                                        class="layui-icon">&#x1006;</i>清除
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
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    var colModel = [
        {name: 'commodityCode', index: 'AMOUNT', label: "商品编号", sortable: false, width: 150},
        {name: 'commodityFullName', index: 'commodityFullName', label: "商品名",  sortable: false,width: 300},
        {name: 'commodityTotal', index: 'AMOUNT', label: "商品总数", sortable: false, width: 150},
        {name: 'commodityFactualAmount', index: 'AMOUNT', label: "总金额", sortable: false, width: 150}
    ];
    $(function () {
        var config = {
            sortname: "",
            sortorder: "desc"
        };
        initTable(ctx + "/exchangeOfFundsReport/queryDataDetail?type=${type}&dateResult=${dateResult}", colModel, null, config);
    });
    initBtnByForm2();
</script>
</body>
</html>

