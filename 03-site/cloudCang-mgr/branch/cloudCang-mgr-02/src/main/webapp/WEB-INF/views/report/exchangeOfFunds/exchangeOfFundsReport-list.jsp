<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>资金往来报表</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet"/>
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css" rel="stylesheet"/>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>资金往来报表</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-inline" style="width: 210px">
                                <input type="text" name="queryTimeCondition" id="queryTimeCondition" placeholder="时间"
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" id="type" name="type" value=""
                                             list="{1:收款,2:付款}" entire="true"
                                             entireName="请选择资金来往类型"/>
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
<script type="text/javascript" src="${staticSource}/resources/js/formatUtil.js"></script>

<!--导出Ex -->
<script src="${staticSource}/resources/js/xlsx.core.min.js"></script>
<script src="${staticSource}/resources/js/tableExport.min.js"></script>
<script src="${staticSource}/resources/js/tableExportComm.js?333"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    var colModel = [
        {
            name: 'dateResult', index: 'DATE_RESULT', label: "日期", width: 150,
            formatter: function (value) {
                if (isEmpty(value)) {
                    return '';
                } else {
                    if(value =='页合计：'){
                        return value;
                    }
                    return formatDate(new Date(value), 'yyyy-MM-dd');
                }
            }
        },
        {name: 'amount', index: 'AMOUNT', label: "金额", width: 150},
        {
            name: 'type', index: 'TYPE', label: "类型", width: 150, formatter: "select",
            editoptions: {value: '1:收款;2:付款'}
        },
        {
            name: 'details',
            index: 'details',
            label: "明细",
            sortable: false,
            formatter: function (value, rowObject, cellvalue) {
                var type = cellvalue.type;
                var dateResult = cellvalue.dateResult;

                return "<a href='javascript:void(0);' onclick=\"queryDetailsMethod('" + type + "," + dateResult + "  ')\">查看</a>";
            }
        }
    ];
    $(function () {
        var config = {
            sortname: "DATE_RESULT",
            sortorder: "desc",
            footerrow: true,//分页上添加一行，用于显示统计信息
            userDataOnFooter: true
        };
        initTable(ctx + "/exchangeOfFundsReport/queryData", colModel, function () {
            <shiro:hasPermission name="EXCHANGEOFFUNDSREPORT_DOWNLOADEXCEL">
            if (isExport) {//判断是否导出
                exportFileCallback1("资金往来报表导出");
                isExport = false;
            }
            </shiro:hasPermission>
        }, config, loadCompleteFn);
        <shiro:hasPermission name="EXCHANGEOFFUNDSREPORT_DOWNLOADEXCEL">
        addExportBtn1("资金往来报表导出");
        </shiro:hasPermission>
    });
    function loadCompleteFn(xhr) {
        $(".ui-jqgrid-sdiv").show();
        //处理合计
        var GroupList = xhr.rows;
        getPageFooterTotal(GroupList);
    }

    initBtnByForm2();
    layui.use('laydate', function () {
        var laydate = layui.laydate;
        //查询入库时间
        laydate.render({
            elem: '#queryTimeCondition', //指定元素
            range: true,
            type: 'date'
        });
    });
    //查资金往来明细
    function queryDetailsMethod(type) {
        if (!isEmpty(type)) {
            var type2 = type.split(",")[0];
            var dateResult = type.split(",")[1];
        }
        showLayerMedium("资金往来明细", ctx + '/exchangeOfFundsReport/detailList?type=' + type2 + "&dateResult=" + dateResult);
    }


    function getPageFooterTotal(totalRow) {//处理合计
        var pageObj = {
            payamount: 0,
            refundamount: 0
        };
        $.each(totalRow, function (i, item) {
            if (1 == item.type) {
                pageObj.payamount += parseFloat(item.amount);
            } else if (2 == item.type) {
                pageObj.refundamount += parseFloat(item.amount);
            }
        });
        $("tr.footRow2").remove();
        var $footerRow = $("tr.footrow");
        $footerRow.after("<tr role='row' class='footrow footRow2 footrow-ltr ui-widget-content'>" + $footerRow.html() + "</tr>");
        var $newFooterRow = $("tr.footRow2");
        $("#gridTable").footerData("set", {
            "dateResult": "页合计：",
            "amount": "收款:" + formateMoney(pageObj.payamount) + "  付款:" + formateMoney(pageObj.refundamount)
        });
        var sdata = getFormValues();
        $.ajax({
            url: ctx + "/exchangeOfFundsReport/queryTotalData",
            type: "post",
            dataType: "json",
            data: sdata,
            success: function (resp) {
                if (resp.data == null || resp.data == 'null') {
                    $newFooterRow.find("td[aria-describedby*='dateResult']").text("总合计：");
                    $newFooterRow.find("td[aria-describedby*='amount']").text("收款:0.00  付款:0.00");
                }
                else {
                    json = eval(resp.data);
                    var pageObj = {
                        payamount: 0.00,
                        refundamount: 0.00
                    };
                    for (var i = 0; i < json.length; i++) {
                        if (1 == json[i].type) {
                            pageObj.payamount += parseFloat(json[i].totalAmount);
                        } else if (2 == json[i].type) {
                            pageObj.refundamount += parseFloat(json[i].totalAmount);
                        }
                    }
                    $newFooterRow.find("td[aria-describedby*='dateResult']").text("总合计：");
                    $newFooterRow.find("td[aria-describedby*='amount']").text("收款:" + formateMoney(pageObj.payamount) + "  付款:" + formateMoney(pageObj.refundamount));
                }
            }
        });
    }
</script>
</body>
</html>

