<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title>优惠券批量下发列表</title>
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
                    <h5><spring:message code='menu.bulk.issuing' /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-inline">
                                    <input type="text" name="smerchantCode" id="smerchantCode" value="" placeholder='<spring:message code="main.merchant.number" />...' class="layui-input">
                                </div>
                                <div class="layui-inline">
                                    <input type="text" name="merchantName" id="merchantName" value="" placeholder='<spring:message code="main.merchant.name" />...' class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-inline">
                                    <input type="text" name="sbatchCode" id="sbatchCode" value="" placeholder='<spring:message code="activity.issuance.batch.number" />...' class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                    <cang:select type="list" list="{10:springMessageCode=ac.couponrule.cash.coupon,20:springMessageCode=ac.couponrule.full.coupon,30:springMessageCode=ac.couponrule.discount.coupon,40:springMessageCode=ac.couponrule.commodity.ticket}" id="icouponType" name="icouponType" entire="true" entireName='springMessageCode=ac.please.select.the.coupon.type' />
                            </div>
                            <div class="layui-inline">
                                <label class="layui-form-label" style="width: 122px;margin-top: 10px"><spring:message code='ac.voucher.value' /></label>
                                <div class="layui-input-inline" style="width: 70px;margin-top: 10px">
                                    <input type="text" name="fcouponValueFrom" id="fcouponValueFrom" autocomplete="off" class="layui-input">
                                </div>
                                <div class="layui-form-mid" style="margin-top: 10px">~</div>
                                <div class="layui-input-inline" style="width: 70px;margin-top: 10px">
                                    <input type="text" name="fcouponValueTo" id="fcouponValueTo" autocomplete="off" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                    <cang:select type="list" list='{10:springMessageCode=main.draft,11:springMessageCode=main.audited,20:springMessageCode=main.approval,21:springMessageCode=main.rejection"/>}' id="istatus" name="istatus" entire="true" entireName='springMessageCode=main.state.select' />
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                    <cang:select type="list" list="{0:springMessageCode=ac.coupon,1:springMessageCode=ac.coupon.code,2:springMessageCode=ac.marketing.event.voucher}" id="itype" name="itype" entire="true" entireName='springMessageCode=ac.please.select.the.delivery.type' />
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="ibatchNumber" id="ibatchNumber" value="" placeholder="<spring:message code='ac.number.of.issues' />..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="dcouponEffectiveDateStr" id="dcouponEffectiveDateStr" placeholder="<spring:message code='ac.voucher.effective.time' />" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="dcouponExpiryDateStr" id="dcouponExpiryDateStr" placeholder='<spring:message code="main.voucher.expiration.time" />' class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i class="layui-icon">&#xe615;</i><spring:message code="main.query" /></button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i class="layui-icon">&#x1006;</i><spring:message code="main.clear" /></button>
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <shiro:hasPermission name="COUPONBATCH_ADD">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" style="margin-left: 0px;" data-type="add"><i class="layui-icon"></i><spring:message code="main.add" /></button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="COUPONBATCH_DELETE">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i><spring:message code="main.delete" /></button>
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

    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //执行一个laydate实例
        laydate.render({
            elem: '#dcouponEffectiveDateStr', //指定元素,券生效日期
            range:true,
            type: 'date'
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#dcouponExpiryDateStr', //指定元素,券失效日期
            range:true,
            type: 'date'
        });
    });

    // 渲染数据
    var colModel = [{name: 'id', index: 'id',hidden:true},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode',label : '<spring:message code="main.merchant.number" />',index: 'smerchant_code'},
        {name: 'merchantName',label : '<spring:message code="main.merchant.name" />',index: 'merchantName',width:300},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'smerchantCode',index: 'smerchant_code',hidden:true},
        </c:if>
        {name: 'sbatchCode',index: 'SBATCH_CODE',label : '<spring:message code="activity.issuance.batch.number" />',sortable : true,width:160},
        {name: 'icouponType',index: 'ICOUPON_TYPE',label : "<spring:message code='ac.couponrule.voucher.type' />",editable: true,
            formatter:"select",editoptions:{value:'10:<spring:message code='ac.couponrule.cash.coupon' />;20:<spring:message code='ac.couponrule.full.coupon' />;30:<spring:message code='ac.couponrule.discount.coupon' />;40:<spring:message code='ac.couponrule.commodity.ticket' />'},sortable : false,width:150},
        {name: 'fcouponValue',index: 'FCOUPON_VALUE',label : "<spring:message code='ac.voucher.value.yuan' />",width:170},
        {name: 'itype',index: 'ITYPE',label : "<spring:message code='ac.delivery.type' />", editable: true,
            formatter:"select",editoptions:{value:'0:<spring:message code='ac.coupon' />;1:<spring:message code='ac.coupon.code' />;2:<spring:message code='ac.marketing.event.voucher' />'},sortable : false,width:120},
        {name: 'ibatchNumber',index: 'IBATCH_NUMBER',label : "<spring:message code='ac.quantity.issued.piece' />",width:170},
        {name: 'istatus',index: 'ISTATUS',label : "<spring:message code='main.state' />", editable: true,
            formatter:function (value, options, rowObject) {return '<span id="istatus_'+rowObject.id+'" data="'+value+'" class="'+ (({"10":"istatus-warm","11":"istatus-warm","20":"istatus-normal","21":"istatus-danger"})[value])+'"> '+(({"10":'● <spring:message code="main.draft"/>',"11":'● <spring:message code="main.audited"/>',"20":'● <spring:message code="main.approval"/>',"21":'● <spring:message code="main.rejection"/>'})[value])+'</span>'},width:140
        },
        {name: 'dcouponEffectiveDate',index: 'DCOUPON_EFFECTIVE_DATE',label : "<spring:message code='ac.couponrule.bond.effective.date' />",formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd ');
        }},
        {name: 'dcouponExpiryDate',index: 'DCOUPON_EXPIRY_DATE',label : '<spring:message code="main.voucher.expiration.date" />',width:190,formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd ');
        }},
        {name: 'scouponValidityValue',index: 'SCOUPON_VALIDITY_VALUE',label : "<spring:message code='ac.valid.date' />",sortable : true,width:100},
        {name: 'scouponInstruction',index: 'SCOUPON_INSTRUCTION',label : "<spring:message code='ac.couponrule.voucher.description' />",sortable : false,width:110},

        {name: 'ssubmitOperatorId',index: 'SSUBMIT_OPERATOR_ID',label : '<spring:message code="main.submitter" />',sortable : false,width:90},
        {name: 'tsubmitDatetime',index: 'TSUBMIT_DATETIME',label : '<spring:message code="main.submission.time" />',width:100,formatter:function(value){
            if (isEmpty(value)) {
                return '';
            }
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:140},
        {name: 'sauditOperatorName',index: 'SAUDIT_OPERATOR_NAME',label : '<spring:message code="main.reviewer" />',sortable : false,width:70},
        {name: 'tauditDatetime',index: 'TAUDIT_DATETIME',label : "<spring:message code='sb.audit.time' />",formatter:function(value){
            if (isEmpty(value)) {
                return '';
            }
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:150},
        {name: 'process',title:false, index: 'process', label: '<spring:message code="main.operation" />', sortable: false, width: 240}
    ];
    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "scroll" });
            var data = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var merchantCode = "${SESSION_KEY_SMERCHANT_CODE}";
                var str = '';
                <shiro:hasPermission name="COUPONBATCH_LOOK">
                str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"queryDetailsMethod('"
                    + cl + "')\" title='<spring:message code='ac.bulk.issue.details' />' alt='<spring:message code='ac.bulk.issue.details' />'  /> | ";
                </shiro:hasPermission>
                if (merchantCode == data[i].smerchantCode) {
                    if ($('#istatus_'+cl).attr('data') == 10) {
                    <shiro:hasPermission name="COUPONBATCH_EDIT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                        + cl + "')\" title=\"<spring:message code='main.edit' />\" alt=\"<spring:message code='main.edit' />\" /> | ";
                    </shiro:hasPermission>
                    }
                    <shiro:hasPermission name="COUPONBATCH_DELETE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                        + cl + "')\" title='<spring:message code="main.delete" />' alt='<spring:message code="main.delete" />'  /> | ";
                    </shiro:hasPermission>
                    if ($('#istatus_'+cl).attr('data') == 10) {
                        <shiro:hasPermission name="COUPONBATCH_AUDIT">
                        str += "<img src='${staticSource}/resources/images/oper_icon/arraign.png' class=\"oper_icon\" onclick=\"arraignMethod('"
                            + cl + "')\" title='<spring:message code='ac.submit.review' />' alt='<spring:message code='ac.submit.review' />'  /> | ";
                        </shiro:hasPermission>
                    }
                    if ($('#istatus_'+cl).attr('data') == 11) {
                        <shiro:hasPermission name="COUPONBATCH_REVIEW">
                        str += "<img src='${staticSource}/resources/images/oper_icon/review.png' class=\"oper_icon\" onclick=\"reviewMethod('"
                            + cl + "')\" title='<spring:message code='main.audit' />' alt='<spring:message code='main.audit' />'  /> | ";
                        </shiro:hasPermission>
                    }
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
            sortorder: "desc",
            shrinkToFit: false,
            autoScroll: true,
        };
        initTable(ctx + "/couponBatch/queryData", colModel, tableCallBack, config);
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
        showLayerMax("<spring:message code='ac.edit.bulk.coupon.information' />", ctx + '/couponBatch/toEdit?sid='+sid);
    }
    function delMethod(checkboxId) {
        confirmDelTip('<spring:message code="main.delete.confirm" />',ctx+"/couponBatch/delete",{checkboxId:checkboxId});
    }
    function queryDetailsMethod(sid) {
        showLayerMax("<spring:message code='ac.coupon.delivery.details.in.bulk' />", ctx + '/couponBatch/queryCouponDetails?sid=' + sid);
    }
    function arraignMethod(couponBatchId) {
        confirmOperTip("<spring:message code='ac.after.the.trial.operation,.it.will.not.be.editable' />?",ctx+"/couponBatch/arraign",{couponBatchId:couponBatchId});
    }
    function reviewMethod(rid) {
        showLayerMin("<spring:message code='main.audit' />", ctx + '/couponBatch/reviewEdit?rid=' + rid);
    }

</script>
</body>
</html>

