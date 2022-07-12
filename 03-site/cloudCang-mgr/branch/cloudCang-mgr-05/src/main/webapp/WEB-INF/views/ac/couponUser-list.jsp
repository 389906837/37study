<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>用户持有券</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
<style>
    .ui-jqgrid tr.jqgrow td {
        white-space: normal !important;
        height: auto;
    }
</style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code='menu.users.hold.coupons'/></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-inline">
                                    <input type="text" name="smerchantCode" id="smerchantCode" value=""
                                           placeholder='<spring:message code="main.merchant.number" />...'
                                           class="layui-input">
                                </div>
                                <div class="layui-inline">
                                    <input type="text" name="merchantName" id="merchantName" value=""
                                           placeholder='<spring:message code="main.merchant.name" />...'
                                           class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-inline">
                                <input type="text" name="scouponCode" id="scouponCode" value=""
                                       placeholder="<spring:message code='ac.voucher.number' />..."
                                       class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <cang:select type="list"
                                             list="{10:springMessageCode=ac.couponrule.cash.coupon,20:springMessageCode=ac.couponrule.full.coupon,30:springMessageCode=ac.couponrule.discount.coupon,40:springMessageCode=ac.couponrule.commodity.ticket}"
                                             id="icouponType"
                                             name="icouponType" entire="true"
                                             entireName='springMessageCode=ac.please.select.the.coupon.type'/>
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="smemberCode" id="smemberCode" value=""
                                       placeholder='<spring:message code="main.member.number" />...'
                                       class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="smemberName" id="smemberName" value=""
                                       placeholder="<spring:message code='main.member.username' />..."
                                       class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="ssourceAcName" id="ssourceAcName" value=""
                                       placeholder="<spring:message code='ac.source.activity.name' />..."
                                       class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <cang:select type="list" name="isourceType" id="isourceType"
                                             list='{10:springMessageCode=ac.activityconf.platform.gift,20:springMessageCode=ac.activityconf.sign.up,30:springMessageCode=main.first.open.alipay.free,40:springMessageCode=main.first.open.wechat.free,50:springMessageCode=ac.activityconf.recommended.registration,60:springMessageCode=ac.activityconf.first.order,70:springMessageCode=ac.activityconf.order,80:springMessageCode=menu.sales.promotion}'
                                             entire="true"
                                             entireName='springMessageCode=ac.please.select.a.source.type'/>
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="tgetDatetimeStartStr" id="tgetDatetimeStartStr"
                                       placeholder="<spring:message code='ac.voucher.time' />" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="dcouponEffectiveDateStr" id="dcouponEffectiveDateStr"
                                       placeholder="<spring:message code='ac.voucher.effective.time' />"
                                       class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="dcouponExpiryDateStr" id="dcouponExpiryDateStr"
                                       placeholder='<spring:message code="main.voucher.expiration.time" />'
                                       class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="tactualUseDatetimeStr" id="tactualUseDatetimeStr"
                                       placeholder="<spring:message code='ac.actual.use.time' />" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="suseTargetCode" id="suseTargetCode" value=""
                                       placeholder="<spring:message code='ac.use.order.number' />..."
                                       class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <cang:select type="list"
                                             list='{1:springMessageCode=ac.unused,2:springMessageCode=ac.used,3:springMessageCode=sm.expired,4:springMessageCode=ac.freeze,5:springMessageCode=main.delete}'
                                             id="istatus"
                                             name="istatus" entire="true"
                                             entireName='springMessageCode=main.state.select'/>
                            </div>

                            <div class="layui-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i
                                        class="layui-icon">&#xe615;</i><spring:message code="main.query"/>
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i
                                        class="layui-icon">&#x1006;</i><spring:message code="main.clear"/>
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
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js?0820"></script>
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
        {name: 'smerchantCode', label: '<spring:message code="main.merchant.number" />', index: 'smerchant_code'},
        {
            name: 'merchantName',
            label: '<spring:message code="main.merchant.name" />',
            index: 'merchantName',
            width: 260
        },
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'smerchantCode', index: 'smerchant_code', hidden: true},
        </c:if>
        {
            name: 'scouponCode',
            index: 'scouponCode',
            label: "<spring:message code='ac.voucher.number' />",
            sortable: false,
            width: 160
        },
        {
            name: 'icouponType',
            index: 'icouponType',
            label: "<spring:message code='ac.couponrule.voucher.type' />",
            editable: true,
            formatter: "select",
            editoptions: {value: '10:<spring:message code='ac.couponrule.cash.coupon' />;20:<spring:message code='ac.couponrule.full.coupon' />;30:<spring:message code='ac.couponrule.discount.coupon' />;40:<spring:message code='ac.couponrule.commodity.ticket' />'},
            sortable: false,
            width: 130
        },
        {
            name: 'fcouponValue',
            label: "<spring:message code='ac.voucher.value' />",
            index: 'fcouponValue',
            sortable: false,
            width: 120
        },
        {
            name: 'smemberCode',
            label: '<spring:message code="main.member.number" />',
            index: 'smemberCode',
            sortable: false,
            width: 160
        },
        {
            name: 'smemberNameDesensitize',
            label: "<spring:message code='main.member.username' />",
            index: 'smemberName',
            sortable: false,
            width: 150
        },
        {
            name: 'ssourceAcName',
            index: 'ssourceAcName',
            label: "<spring:message code='ac.source.activity.name' />",
            width: 160,
            sortable: false
        },
        {
            name: 'isourceType',
            index: 'ISOURCE_TYPE',
            label: "<spring:message code='ac.source.type' />",
            editable: true,
            formatter: "select",
            editoptions: {value: '10:<spring:message code='ac.activityconf.platform.gift' />;20:<spring:message code='ac.activityconf.sign.up' />;30:<spring:message code="main.first.open.alipay.free" />;40:<spring:message code="main.first.open.wechat.free"/>;50:<spring:message code='ac.activityconf.recommended.registration' />;60:<spring:message code='ac.activityconf.first.order' />;70:<spring:message code='ac.activityconf.order' />;80:<spring:message code='menu.sales.promotion' />'},
            sortable: false,
            width: 120
        },
        {
            name: 'istatus',
            index: 'istatus',
            label: "<spring:message code='main.state' />",
            editable: true,
            formatter: "select",
            editoptions: {value: '1:<span class="istatus-normal">● <spring:message code='ac.unused' /></span>;2:<span class="istatus-danger">● <spring:message code='ac.used' /></span>;3:<span class="istatus-warm">● <spring:message code='sm.expired' /></span>;4:<span class="istatus-warm">● <spring:message code='ac.freeze' /></span>;5:<span class="istatus-danger">● <spring:message code="main.delete" /></span>'},
            sortable: false,
            width: 90
        },
        {
            name: 'tgetDatetime',
            index: 'tgetDatetime',
            label: "<spring:message code='ac.voucher.time' />",
            formatter: function (value) {
                return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
            },
            width: 200,
            sortable: false
        },
        {
            name: 'dcouponEffectiveDate',
            index: 'dcouponEffectiveDate',
            label: "<spring:message code='ac.voucher.effective.time' />",
            formatter: function (value) {
                return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
            },
            width: 200,
            sortable: false
        },
        {
            name: 'dcouponExpiryDate',
            index: 'dcouponExpiryDate',
            label: '<spring:message code="main.voucher.expiration.time" />',
            formatter: function (value) {
                return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
            },
            width: 200,
            sortable: false
        },
        {
            name: 'scouponValidityValue',
            index: 'scouponValidityValue',
            label: "<spring:message code='ac.valid.days.of.coupons' />",
            width: 150,
            sortable: false
        },
        {
            name: 'tactualUseDatetime',
            index: 'TACTUAL_USE_DATETIME',
            label: "<spring:message code='ac.actual.use.time' />",
            formatter: function (value) {
                if (isEmpty(value)) {
                    return '';
                }
                return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
            },
            width: 200
        },
        {
            name: 'factualExchangeAmount',
            index: 'factualExchangeAmount',
            label: "<spring:message code='ac.actual.usage.amount.yuan' />",
            width: 200
        },
        {
            name: 'suseTargetCode',
            index: 'suseTargetCode',
            label: "<spring:message code='ac.use.order.number' />",
            width: 150,
            sortable: false
        },
        {
            name: 'scouponInstruction',
            index: 'scouponInstruction',
            label: "<spring:message code='ac.couponrule.voucher.description' />",
            sortable: false,
            width: 130
        },
        {
            name: 'process',
            index: 'process',
            label: '<spring:message code="main.operation" />',
            sortable: false,
            width: 100
        }
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
                    + cl + "')\" title='<spring:message code='ac.user.holds.coupon.details' />' alt='<spring:message code='ac.user.holds.coupon.details' />'  /> | ";
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
        showLayer("<spring:message code='ac.user.holds.coupon.details' />", ctx + '/couponUser/listInfo?sid=' + sid, {area: ['70%', '90%']});
    }

    function getPageFooterTotal(totalRow) {//处理合计
        var pageObj = {
            fcouponValue: 0,
            factualExchangeAmount: 0

        };
        $.each(totalRow, function (i, item) {
            if (isEmpty(item.fcouponValue)) {
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
            "scouponCode": "<spring:message code='sq.pages.in.total' />：",
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
                    $newFooterRow.find("td[aria-describedby*='scouponCode']").text("<spring:message code='sq.in.total' />：");
                    $newFooterRow.find("td[aria-describedby*='fcouponValue']").text(0.00);
                    $newFooterRow.find("td[aria-describedby*='factualExchangeAmount']").text(0.00);
                }
                $newFooterRow.find("td[aria-describedby*='scouponCode']").text("<spring:message code='sq.in.total' />：");
                $newFooterRow.find("td[aria-describedby*='fcouponValue']").text(formateMoney(resp.data['FCOUPON_VALUE_TOTAL']));
                $newFooterRow.find("td[aria-describedby*='factualExchangeAmount']").text(formateMoney(resp.data['FACTUAL_EXCHANGE_AMOUNT_TOTAL']));
            }
        });
    }

</script>
</body>
</html>

