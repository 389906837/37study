<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>优惠券券码生成列表</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code='ac.ticket.generation' /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-inline">
                                    <input type="text" name="smerchantCode" id="smerchantCode" value=""
                                           placeholder='<spring:message code="main.merchant.number" />...' class="layui-input">
                                </div>
                                <div class="layui-inline">
                                    <input type="text" name="merchantName" id="merchantName" value=""
                                           placeholder='<spring:message code="main.merchant.name" />...' class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-inline">
                                <input type="text" name="sbatchCode" id="sbatchCode" value="" placeholder='<spring:message code="activity.issuance.batch.number" />...'
                                       class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <cang:select type="list" list="{10:springMessageCode=ac.couponrule.cash.coupon,20:springMessageCode=ac.couponrule.full.coupon,30:springMessageCode=ac.couponrule.discount.coupon,40:springMessageCode=ac.couponrule.commodity.ticket}" id="icouponType"
                                             name="icouponType" entire="true" entireName='springMessageCode=ac.please.select.the.coupon.type' />
                            </div>
                            <div class="layui-inline">
                                <label class="layui-form-label" style="width: 80px;margin-top: 10px"><spring:message code='ac.voucher.value' /></label>
                                <div class="layui-inline" style="width: 70px;margin-top: 10px">
                                    <input type="text" name="fcouponValueFrom" id="fcouponValueFrom" autocomplete="off"
                                           class="layui-input">
                                </div>
                                <div class="layui-form-mid" style="margin-top: 10px">~</div>
                                <div class="layui-inline" style="width: 70px;margin-top: 10px">
                                    <input type="text" name="fcouponValueTo" id="fcouponValueTo" autocomplete="off"
                                           class="layui-input">
                                </div>
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <cang:select type="list" list='{10:springMessageCode=main.draft,11:springMessageCode=main.audited,20:springMessageCode=main.approval,21:springMessageCode=main.rejection"/>}' id="istatus"
                                             name="istatus" entire="true" entireName='springMessageCode=main.state.select' />
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <cang:select type="list" list="{0:springMessageCode=ac.coupon,1:springMessageCode=ac.coupon.code,2:springMessageCode=ac.marketing.event.voucher}" id="itype" name="itype"
                                             entire="true" entireName='springMessageCode=ac.please.select.the.delivery.type' />
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="ibatchNumber" id="ibatchNumber" value="" placeholder="<spring:message code='ac.number.of.issues' />..."
                                       class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <cang:select type="list" list="{10:springMessageCode=' /><spring:message code='ac.only.one.time,20:springMessageCode=ac.once.a.month}" id="icouponCodeUseType" name="icouponCodeUseType"
                                             entire="true" entireName='springMessageCode=ac.please.select.the.type.of.coupon.code.to.use' />
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="dcouponEffectiveDateStr" id="dcouponEffectiveDateStr"
                                       placeholder="<spring:message code='ac.voucher.effective.time' />" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="dcouponExpiryDateStr" id="dcouponExpiryDateStr"
                                       placeholder='<spring:message code="main.voucher.expiration.time" />' class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="texStarttimeStr" id="texStarttimeStr"
                                       placeholder="<spring:message code='ac.effective.start.time.of.the.voucher' />" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="texEndtimeStr" id="texEndtimeStr"
                                       placeholder="<spring:message code='ac.effective.time.limit.for.redemption' />" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i
                                        class="layui-icon">&#xe615;</i><spring:message code="main.query" />
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i
                                        class="layui-icon">&#x1006;</i><spring:message code="main.clear" />
                                </button>
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <shiro:hasPermission name="COUPONBATCH_ADD">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" style="margin-left: 0px;"
                                        data-type="add"><i class="layui-icon"></i><spring:message code="main.add" />
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="COUPONBATCH_DELETE">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i><spring:message code="main.delete" />
                                </button>
                            </shiro:hasPermission>
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
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">

    layui.use('laydate', function () {
        var laydate = layui.laydate;

        //执行一个laydate实例
        laydate.render({
            elem: '#dcouponEffectiveDateStr', //指定元素,券生效日期
            range: true,
            type: 'date'
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#dcouponExpiryDateStr', //指定元素,券失效日期
            range: true,
            type: 'date'
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#texStarttimeStr', //指定元素,兑券有效开始日期
            range: true,
            type: 'date'
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#texEndtimeStr', //指定元素,兑券有效截止日期
            range: true,
            type: 'date'
        });
    });

    // 渲染数据
    var colModel = [{name: 'id', index: 'id', hidden: true},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode', label: '<spring:message code="main.merchant.number" />', index: 'smerchant_code'},
        {name: 'merchantName', label: '<spring:message code="main.merchant.name" />', index: 'merchantName'},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'smerchantCode', index: 'smerchant_code', hidden: true},
        </c:if>
        {name: 'sbatchCode', index: 'sbatchCode', label: '<spring:message code="activity.issuance.batch.number" />', sortable: true, width: 120},
        {
            name: 'icouponType', index: 'icouponType', label: "<spring:message code='ac.couponrule.voucher.type' />", editable: true,
            formatter: "select", editoptions: {value: '10:<spring:message code='ac.couponrule.cash.coupon' />;20:<spring:message code='ac.couponrule.full.coupon' />;30:<spring:message code='ac.couponrule.discount.coupon' />;40:<spring:message code='ac.couponrule.commodity.ticket' />'}, sortable: false, width: 70
        },
        {name: 'fcouponValue', index: 'fcouponValue', label: "<spring:message code='ac.voucher.value.yuan' />", sortable: false, width: 90},
        {
            name: 'istatus', index: 'istatus', label: "<spring:message code='main.state' />", editable: true,
            formatter: "select", editoptions: {value: '10:<spring:message code="main.draft"/>;11:<spring:message code="main.audited"/>;20:<spring:message code="main.approval"/>;21:<spring:message code="main.rejection"/>'}, sortable: false, width: 70
        },
        {
            name: 'itype', index: 'itype', label: "<spring:message code='ac.delivery.type' />", editable: true,
            formatter: "select", editoptions: {value: '0:<spring:message code='ac.coupon' />;1:<spring:message code='ac.coupon.code' />;2:<spring:message code='ac.marketing.event.voucher' />'}, sortable: false, width: 90
        },
        {name: 'ibatchNumber', index: 'ibatchNumber', label: "<spring:message code='ac.quantity.issued.piece' />", sortable: true, width: 130},
        {
            name: 'icouponCodeUseType', index: 'icouponCodeUseType', label: "<spring:message code='ac.ticket.code.usage.type' />", editable: true,
            formatter: "select", editoptions: {value: '10:<spring:message code='ac.only.one.time' />;20:<spring:message code='ac.once.a.month' />'}, sortable: false
        },
        {
            name: 'dcouponEffectiveDate', index: 'dcouponEffectiveDate', label: "<spring:message code='ac.voucher.effective.time' />", formatter: function (value) {
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }, width: 170
        },
        {
            name: 'dcouponExpiryDate', index: 'dcouponExpiryDate', label: '<spring:message code="main.voucher.expiration.date" />', formatter: function (value) {
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }, width: 170
        },

        {
            name: 'texStarttime', index: 'TEX_STARTTIME', label: "<spring:message code='ac.effective.start.time.of.the.voucher' />", formatter: function (value) {
            if (isEmpty(value)) {
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }, width: 170
        },
        {
            name: 'texEndtime', index: 'TEX_ENDTIME', label: "<spring:message code='ac.effective.time.limit.for.redemption' />", formatter: function (value) {
            if (isEmpty(value)) {
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }, width: 170
        },

        {name: 'scouponValidityValue', index: 'scouponValidityValue', label: "<spring:message code='ac.valid.date' />", sortable: true, width: 100},

        {name: 'scouponInstruction', index: 'scouponInstruction', label: "<spring:message code='ac.couponrule.voucher.description' />", sortable: false, width: 100},
        {name: 'sbriefDesc', index: 'sbriefDesc', label: "<spring:message code='ac.couponrule.voucher.brief' />", sortable: false, width: 100},
        {name: 'ssubmitOperatorId', index: 'ssubmitOperatorId', label: '<spring:message code="main.submitter" />', sortable: false, width: 70},
        {
            name: 'tsubmitDatetime', index: 'tsubmitDatetime', label: '<spring:message code="main.submission.time" />', formatter: function (value) {
            if (isEmpty(value)) {
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }, width: 170
        },
        {name: 'sauditOperatorName', index: 'sauditOperatorName', label: '<spring:message code="main.reviewer" />', sortable: false, width: 70},
        {
            name: 'tauditDatetime', index: 'tauditDatetime', label: "<spring:message code='sb.audit.time' />", formatter: function (value) {
            if (isEmpty(value)) {
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }, width: 170
        },
        {name: 'process', index: 'process', label: '<spring:message code="main.operation" />', sortable: false, width: 190}
    ];
    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            var data = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = '';
                <shiro:hasPermission name="COUPONBATCH_LOOK">
                str += "<img src='${staticSource}/resources/images/oper_icon/view.png' class=\"oper_icon\" onclick=\"queryDetailsMethod('"
                    + cl + "')\" title='<spring:message code='ac.bulk.issue.details' />' alt='<spring:message code='ac.bulk.issue.details' />'  /> | ";
                </shiro:hasPermission>
                if (data[i].istatus != 11) {
                    <shiro:hasPermission name="COUPONBATCH_EDIT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                        + cl + "')\" title=\"<spring:message code='main.edit' />\" alt=\"<spring:message code='main.edit' />\" /> | ";
                    </shiro:hasPermission>
                }
                <shiro:hasPermission name="COUPONBATCH_DELETE">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='<spring:message code="main.delete" />' alt='<spring:message code="main.delete" />'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="COUPONBATCH_AUDIT">
                str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"arraignMethod('"
                    + cl + "')\"><spring:message code='ac.submit.review' /></a> |  ";
                </shiro:hasPermission>
                <shiro:hasPermission name="COUPONBATCH_REVIEW">
                str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"reviewMethod('"
                    + cl + "')\"><spring:message code='main.audit' /></a> |  ";
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
            sortname: "TEX_STARTTIME",
            sortorder: "desc"
        };
        initTable(ctx + "/couponBatch/queryCodeData", colModel, tableCallBack, config);
    });
    //初始化form中按钮
    var initBtnConfig = {
            addUrl: ctx + '/couponBatch/toEdit',
            deleteUrl: ctx + "/couponBatch/delete",
            addTitle: '<spring:message code='ac.add.bulk.coupon.information' />',
            addFn: showLayerMax
        }
    ;
    initBtnByForm4(initBtnConfig);

    function editMethod(sid) {
        showLayerMax("<spring:message code='ac.edit.bulk.coupon.information' />", ctx + '/couponBatch/toEdit?sid=' + sid);
    }
    function delMethod(checkboxId) {
        confirmDelTip('<spring:message code="main.delete.confirm" />', ctx + "/couponBatch/delete", {checkboxId: checkboxId});
    }
    function queryDetailsMethod(sid) {
        showLayer("<spring:message code='ac.coupon.delivery.details.in.bulk' />", ctx + '/couponBatch/queryCouponDetails?sid=' + sid, {area: ['70%', '100%']});
    }
    function arraignMethod(couponBatchId) {
        confirmOperTip("<spring:message code='ac.after.the.trial.operation,.it.will.not.be.editable' />?", ctx + "/couponBatch/arraign", {couponBatchId: couponBatchId});
    }
    function reviewMethod(rid) {
        showLayer("<spring:message code='main.audit' />", ctx + '/couponBatch/reviewEdit?rid=' + rid, {area: ['30%', '60%']});
    }

</script>
</body>
</html>

