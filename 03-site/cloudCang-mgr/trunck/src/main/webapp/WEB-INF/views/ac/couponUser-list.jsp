<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>用户持有券</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>用户持有券</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-inline">
                                    <input type="text" name="smerchantCode" id="smerchantCode" value=""
                                           placeholder="商户编号..." class="layui-input">
                                </div>
                                <div class="layui-inline">
                                    <input type="text" name="merchantName" id="merchantName" value=""
                                           placeholder="商户名称..." class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-inline">
                                <input type="text" name="scouponCode" id="scouponCode" value="" placeholder="券编号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <cang:select type="list" list="{10:现金券,20:满减券,30:抵扣券,40:商品券}" id="icouponType"
                                             name="icouponType" entire="true" entireName="请选择券类型"/>
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="smemberCode" id="smemberCode" value=""
                                       placeholder="会员编号..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="smemberName" id="smemberName" value=""
                                       placeholder="会员用户名..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="ssourceAcName" id="ssourceAcName" value=""
                                       placeholder="来源活动名称..."
                                       class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <cang:select type="list" name="isourceType" id="isourceType"
                                             list="{10:平台赠送,20:会员注册,30:首次开通支付宝免密,40:首次开通微信免密,50:推荐注册,60:首次下单,70:下单,80:促销活动}"
                                             entire="true" entireName="请选择来源类型"/>
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="tgetDatetimeStartStr" id="tgetDatetimeStartStr"
                                       placeholder="获券时间" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="dcouponEffectiveDateStr" id="dcouponEffectiveDateStr"
                                       placeholder="券生效时间" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="dcouponExpiryDateStr" id="dcouponExpiryDateStr"
                                       placeholder="券失效时间" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="tactualUseDatetimeStr" id="tactualUseDatetimeStr"
                                       placeholder="实际使用时间" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="suseTargetCode" id="suseTargetCode" value=""
                                       placeholder="使用订单号..." class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <cang:select type="list" list="{1:未使用,2:已使用,3:已过期,4:冻结,5:删除}" id="istatus"
                                             name="istatus" entire="true" entireName="请选择状态"/>
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
<script type="text/javascript" src="${staticSource}/resources/js/formatUtil.js?3"></script>

<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    /**
     * 选择日期范围
     */
    layui.use('laydate', function () {
        var laydate = layui.laydate;

        //执行一个laydate实例(获券时间)
        laydate.render({
            elem: '#tgetDatetimeStartStr', //指定元素
            range: true,
            type: 'date'
        });
        //券生效时间
        laydate.render({
            elem: '#dcouponEffectiveDateStr', //指定元素
            range: true,
            type: 'date'
        });
        //券失效时间
        laydate.render({
            elem: '#dcouponExpiryDateStr', //指定元素
            range: true,
            type: 'date'
        });
        //实际使用时间
        laydate.render({
            elem: '#tactualUseDatetimeStr', //指定元素
            range: true,
            type: 'date'
        });
    });

    // 渲染数据
    var colModel = [{name: 'id', index: 'id', hidden: true},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode', label: "商户编号", index: 'smerchant_code'},
        {name: 'merchantName', label: "商户名称", index: 'merchantName', width: 260},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'smerchantCode', index: 'smerchant_code', hidden: true},
        </c:if>
        {name: 'scouponCode', index: 'scouponCode', label: "券编号", sortable: false, width: 100},
        {
            name: 'icouponType',
            index: 'icouponType',
            label: "券类型",
            editable: true,
            formatter: "select",
            editoptions: {value: '10:现金券;20:满减券;30:抵扣券;40:商品券'},
            sortable: false,
            width: 80
        },
        {
            name: 'fcouponValue',
            label: "券面值",
            index: 'fcouponValue',
            sortable: false,
            width: 90
        },
        {name: 'smemberCode', label: "会员编号", index: 'smemberCode', sortable: false, width: 140},
        {name: 'smemberNameDesensitize', label: "会员用户名", index: 'smemberName', sortable: false, width: 120},
        {name: 'ssourceAcName', index: 'ssourceAcName', label: "来源活动名称", width: 140, sortable: false},
        {
            name: 'isourceType',
            index: 'ISOURCE_TYPE',
            label: "来源类型",
            editable: true,
            formatter: "select",
            editoptions: {value: '10:平台赠送;20:会员注册;30:首次开通支付宝免密;40:首次开通微信免密,50:推荐注册;60:首次下单;70:下单;80:促销活动'},
            sortable: false,
            width: 120
        },
        {
            name: 'istatus',
            index: 'istatus',
            label: "状态",
            editable: true,
            formatter: "select",
            editoptions: {value: '1:<span class="istatus-normal">● 未使用</span>;2:<span class="istatus-danger">● 已使用</span>;3:<span class="istatus-warm">● 已过期</span>;4:<span class="istatus-warm">● 冻结</span>;5:<span class="istatus-danger">● 删除</span>'},
            sortable: false,
            width: 90
        },
        {
            name: 'tgetDatetime', index: 'tgetDatetime', label: "获券时间", formatter: function (value) {
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }, width: 200, sortable: false
        },
        {
            name: 'dcouponEffectiveDate', index: 'dcouponEffectiveDate', label: "券生效时间", formatter: function (value) {
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }, width: 200, sortable: false
        },
        {
            name: 'dcouponExpiryDate', index: 'dcouponExpiryDate', label: "券失效时间", formatter: function (value) {
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }, width: 200, sortable: false
        },
        {name: 'scouponValidityValue', index: 'scouponValidityValue', label: "券有效天数", width: 130, sortable: false},
        {
            name: 'tactualUseDatetime', index: 'TACTUAL_USE_DATETIME', label: "实际使用时间", formatter: function (value) {
            if (isEmpty(value)) {
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }, width: 200
        },
        {name: 'factualExchangeAmount', index: 'factualExchangeAmount', label: "实际使用金额", width: 130},
        {name: 'suseTargetCode', index: 'suseTargetCode', label: "使用订单号", width: 150, sortable: false},
        /*   {
         name: 'istatus',
         index: 'istatus',
         label: "状态",
         editable: true,
         formatter: "select",
         editoptions: {value: '1:未使用;2:已使用;3:已过期;4:冻结;5:删除'},
         sortable: false,
         width: 90
         },*/
        {name: 'scouponInstruction', index: 'scouponInstruction', label: "券说明", sortable: false, width: 130},
        {name: 'process', index: 'process', label: "操作", sortable: false, width: 100}
    ];
    // 表格回调
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
            var data = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = "";
                <shiro:hasPermission name="COUPONUSER_INFO">
                str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"queryInfoMethod('"
                    + cl + "')\" title='用户持有券详情' alt='用户持有券详情'  /> | ";
                </shiro:hasPermission>
                $("#gridTable").jqGrid('setRowData',
                    cl, {
                        process: str.length > 0 ? str.substr(0, str.lastIndexOf(" | ")) : ""
                    });
            }
        }, 0);
    }
    // 初始化表格,传入页面所需参数
    $(function () {
        var config = {
            rownumbers: true,
            multiselect: false,
            sortname: 'A.ISTATUS=1',
            sortorder: 'desc',
            shrinkToFit: false,
            autoScroll: true,
            footerrow: true,//分页上添加一行，用于显示统计信息
            userDataOnFooter: true
        };
        initTable(ctx + "/couponUser/queryData", colModel, tableCallBack, config, loadCompleteFn);
    });
    function loadCompleteFn(xhr) {
        $(".ui-jqgrid-sdiv").show();
        //处理合计
        var GroupList = xhr.rows;
        getPageFooterTotal(GroupList);
    }
    // 调用Button按钮方法(查询,清除)
    var initBtnConfig = {
        addModelConfig: {area: ['60%', '80%']}
    };
    initBtnByForm2(initBtnConfig);

    function queryInfoMethod(sid) {
        showLayer("用户持有券详情", ctx + '/couponUser/listInfo?sid=' + sid, {area: ['70%', '90%']});
    }

    function getPageFooterTotal(totalRow) {//处理合计
        var pageObj = {
            fcouponValue: 0,
            factualExchangeAmount: 0

        };
        $.each(totalRow, function (i, item) {
            if(isEmpty(item.fcouponValue)){
                item.fcouponValue = 0.00;
            }
            pageObj.fcouponValue += parseFloat(item.fcouponValue);
            if (isEmpty(item.factualExchangeAmount)) {
                item.factualExchangeAmount = 0.00;
            }
            pageObj.factualExchangeAmount += parseFloat(item.factualExchangeAmount);

        });
        $("tr.footRow2").remove();
        var $footerRow = $("tr.footrow");
        $footerRow.after("<tr role='row' class='footrow footRow2 footrow-ltr ui-widget-content'>" + $footerRow.html() + "</tr>");
        var $newFooterRow = $("tr.footRow2");
        $("#gridTable").footerData("set", {
            "scouponCode": "页合计：",
            "fcouponValue": formateMoney(pageObj.fcouponValue),
            "factualExchangeAmount": formateMoney(pageObj.factualExchangeAmount)
        });
        var sdata = getFormValues();
        $.ajax({
            url: ctx + "/couponUser/queryTotalData",
            type: "post",
            dataType: "json",
            data: sdata,
            success: function (resp) {
                if (resp.data == null || resp.data == 'null') {
                    $newFooterRow.find("td[aria-describedby*='scouponCode']").text("总合计：");
                    $newFooterRow.find("td[aria-describedby*='fcouponValue']").text(0.00);
                    $newFooterRow.find("td[aria-describedby*='factualExchangeAmount']").text(0.00);
                }
                $newFooterRow.find("td[aria-describedby*='scouponCode']").text("总合计：");
                $newFooterRow.find("td[aria-describedby*='fcouponValue']").text(formateMoney(resp.data['FCOUPON_VALUE_TOTAL']));
                $newFooterRow.find("td[aria-describedby*='factualExchangeAmount']").text(formateMoney(resp.data['FACTUAL_EXCHANGE_AMOUNT_TOTAL']));
            }
        });
    }

</script>
</body>
</html>

