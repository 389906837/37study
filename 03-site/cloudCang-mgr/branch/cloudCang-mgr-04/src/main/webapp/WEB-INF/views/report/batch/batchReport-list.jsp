<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>批次报表</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet"/>
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css" rel="stylesheet"/>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>批次报表</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="commodityName" id="commodityName" value="" placeholder="商品名..."
                                       class="layui-input">
                            </div>
                         <%--   <div class="layui-input-inline">
                                <input type="text" name="deviceName" id="deviceName" value="" placeholder="设备名..."
                                       class="layui-input">
                            </div>--%>
                            <div class="layui-input-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i
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
<script type="text/javascript" src="${staticSource}/resources/js/formatUtil.js"></script>

<!--导出Ex -->
<script src="${staticSource}/resources/js/xlsx.core.min.js"></script>
<script src="${staticSource}/resources/js/tableExport.min.js"></script>
<script src="${staticSource}/resources/js/tableExportComm.js?345"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    var colModel = [{name: 'id', index: 'id', hidden: true},
        {name: 'commodityFullName', index: 'commodityFullName', label: "商品名", width: 300, sortable: false},
        {name: 'batchTotal', index: 'batchTotal', label: "总批次数", width: 200},
        {name: 'fcostAmount', index: 'fcostAmount', label: "商品总成本", width: 300},
        {name: 'lossNum', index: 'lossNum', label: "已损耗数量", width: 80},
        {name: 'expiredNum', index: 'expiredNum', label: "已过期数", sortable: false, width: 150}
    ];
    $(function () {
        var config = {
            sortname: "",
            sortorder: "",
            footerrow: true,//分页上添加一行，用于显示统计信息
            userDataOnFooter: true
        };
        initTable(ctx + "/batchReport/queryData", colModel, function () {
            <shiro:hasPermission name="BATCHREPORT_DOWNLOADEXCEL">
                if(isExport) {//判断是否导出
                    exportFileCallback1("批次报表导出");
                    isExport = false;
                }
            </shiro:hasPermission>
        }, config,loadCompleteFn);
        <shiro:hasPermission name="BATCHREPORT_DOWNLOADEXCEL">
            addExportBtn1("批次报表导出");
        </shiro:hasPermission>
    });
    function loadCompleteFn(xhr) {
        $(".ui-jqgrid-sdiv").show();
        //处理合计
        var GroupList = xhr.rows;
        getPageFooterTotal(GroupList);
    }
    initBtnByForm2();



    function getPageFooterTotal(totalRow) {//处理合计
        var pageObj = {
            lossNum: 0,
            expiredNum: 0

        };
        $.each(totalRow, function (i, item) {

            pageObj.lossNum += (item.lossNum);

            pageObj.expiredNum += (item.expiredNum);

        });
        $("tr.footRow2").remove();
        var $footerRow = $("tr.footrow");
        $footerRow.after("<tr role='row' class='footrow footRow2 footrow-ltr ui-widget-content'>" + $footerRow.html() + "</tr>");
        var $newFooterRow = $("tr.footRow2");
        $("#gridTable").footerData("set", {
            "fcostAmount": "页合计：",
            "lossNum": (pageObj.lossNum),
            "expiredNum": (pageObj.expiredNum)
        });
        var sdata = getFormValues();
        $.ajax({
            url: ctx + "/batchReport/queryTotalData",
            type: "post",
            dataType: "json",
            data: sdata,
            success: function (resp) {
                if (resp.data == null || resp.data == 'null') {
                    $newFooterRow.find("td[aria-describedby*='fcostAmount']").text("总合计：");
                    $newFooterRow.find("td[aria-describedby*='lossNum']").text(0);
                    $newFooterRow.find("td[aria-describedby*='expiredNum']").text(0);
                }
                $newFooterRow.find("td[aria-describedby*='fcostAmount']").text("总合计：");
                $newFooterRow.find("td[aria-describedby*='lossNum']").text((resp.data['LOSS_NUM']));
                $newFooterRow.find("td[aria-describedby*='expiredNum']").text((resp.data['EXPIRED_NUM']));
            }
        });
    }
</script>
</body>
</html>

