<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>促销活动编辑</title>
<link href="${staticSource}/resources/layui/css/layui.css?2" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?201810" rel="stylesheet">
<style type="text/css">
    .layui-form-label {
        width: 200px;
    }
</style>
</head>
<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/activityConf/savePromotions" method="post" id="myForm">
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="main.code" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sconfCode" id="sconfCode" disabled value="${conf.sconfCode}"
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="main.name" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sconfName" datatype="*" nullmsg='<spring:message code="activity.enter.name" />' id="sconfName"
                           value="${conf.sconfName}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="activity.type" /></label>
                <div class="layui-input-inline">
                    <cang:select type="dic" groupNo="SP000113" name="idiscountWay" layFilter="discountWay"
                                 exp="springMessageCode=activity.enter.type"
                                 id="idiscountWay" entire="true" value="${conf.idiscountWay}"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="activity.superimposed.or.not" /></label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="iisSuperposition" id="iisSuperposition" disabled="disabled"
                                 value="${conf.iisSuperposition}" list="{0:springMessageCode=main.no,1:springMessageCode=main.yes}"/>
                </div>
            </div>
        </div>
        <!--首单 -->
        <div style="display: none;" id="firstOrder">
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="activity.target.amount" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="firstTargetMoney" id="firstTargetMoney" ignore="ignore"
                               datatype="/^-?[1-9]+(\.\d+)?$|^-?0(\.\d+)?$|^-?[1-9]+[0-9]*(\.\d+)?$/"
                               nullmsg='<spring:message code="activity.target.amount.notnull" />' errormsg='<spring:message code="activity.target.amount.must.number" />' placeholder='(0<spring:message code="main.unlimited" />)'
                               value="<fmt:formatNumber pattern="#0.##" value="${confDetail.ftargetMoney}" />"
                               class="layui-input"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="activity.peferential.amount" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="firstDiscountAmount" id="firstDiscountAmount" ignore="ignore"
                               datatype="/^-?[1-9]+(\.\d+)?$|^-?0(\.\d+)?$|^-?[1-9]+[0-9]*(\.\d+)?$/"
                               nullmsg='<spring:message code="activity.peferential.amount.notnull" />' errormsg='<spring:message code="activity.peferential.amount.must.number" />'
                               value="<fmt:formatNumber pattern="#0.##" value="${confDetail.fdiscountMoney}" />"
                               class="layui-input"/>
                    </div>
                </div>
            </div>
        </div>
        <!--折扣 -->
        <div style="display: none;" id="discount">
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="activity.count.type" /></label>
                    <div class="layui-input-inline">
                        <cang:select type="list" name="discountSelect" id="discountSelect" layFilter="discountSelect"
                                     value="${confDetail.idiscountType}" list='{20:springMessageCode=activity.count.detile.20,30:springMessageCode=activity.count.detile.30,40:springMessageCode=activity.count.detile.40}'/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="activity.peferential.amount" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="discountTargetMoney" id="discountTargetMoney" placeholder='(0<spring:message code="main.unlimited" />)'
                               ignore="ignore" datatype="/^-?[1-9]+(\.\d+)?$|^-?0(\.\d+)?$|^-?[1-9]+[0-9]*(\.\d+)?$/"
                               nullmsg='<spring:message code="activity.peferential.amount.notnull" />' errormsg='<spring:message code="activity.peferential.amount.must.number" />'
                               value="<fmt:formatNumber pattern="#0.##" value="${confDetail.ftargetMoney}" />"
                               class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="activity.target.count" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="discountTargetNum" id="discountTargetNum" placeholder='(0<spring:message code="main.unlimited" />)'
                               ignore="ignore" datatype="n" disabled="disabled"
                               errormsg='<spring:message code="activity.target.count.must.number" />' value="${confDetail.ftargetNum}" class="layui-input"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="activity.discount" />(0-10)</label>
                    <div class="layui-input-inline">
                        <input type="text" name="fdiscount" id="fdiscount" ignore="ignore"
                               datatype="/^-?[1-9]+(\.\d+)?$|^-?0(\.\d+)?$|^-?[1-9]+[0-9]*(\.\d+)?$/"
                               nullmsg='<spring:message code="activity.discount.notnull" />' errormsg='<spring:message code="activity.discount.must.number" />'
                               value="<fmt:formatNumber pattern="#0.##" value="${confDetail.fdiscount}" />"
                               class="layui-input"/>
                    </div>
                </div>
            </div>
        </div>
        <!--满减 -->
        <div style="display: none;" id="fullReduction">
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="activity.full.reduction" /></label>
                    <div class="layui-input-inline">
                        <cang:select type="list" name="fullReductionSelect" id="fullReductionSelect"
                                     layFilter="fullReductionSelect"
                                     value="${confDetail.idiscountType}" list='{50:springMessageCode=activity.full.reduction.50,60:springMessageCode=activity.full.reduction.60,70:springMessageCode=activity.full.reduction.70,80:springMessageCode=activity.full.reduction.80}'/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="activity.target.count" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="fullReductionTargetNum" id="fullReductionTargetNum" ignore="ignore"
                               placeholder='(0<spring:message code="main.unlimited" />)' placeholder='(0<spring:message code="main.unlimited" />)' datatype="n" disabled="disabled"
                               errormsg='<spring:message code="activity.target.count.must.number" />' value="${confDetail.ftargetNum}" class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="activity.target.amount" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="fullReductionTargetMoney" id="fullReductionTargetMoney" ignore="ignore"
                               placeholder='(0<spring:message code="main.unlimited" />)'
                               datatype="/^-?[1-9]+(\.\d+)?$|^-?0(\.\d+)?$|^-?[1-9]+[0-9]*(\.\d+)?$/"
                               nullmsg='<spring:message code="activity.peferential.amount.notnull" />' errormsg='<spring:message code="activity.peferential.amount.must.number" />'
                               value="<fmt:formatNumber pattern="#0.##" value="${confDetail.ftargetMoney}" />"
                               class="layui-input"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="activity.peferential.amount" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="fullReductionDiscountMoney" id="fullReductionDiscountMoney"
                               ignore="ignore" datatype="/^-?[1-9]+(\.\d+)?$|^-?0(\.\d+)?$|^-?[1-9]+[0-9]*(\.\d+)?$/"
                               nullmsg='<spring:message code="activity.peferential.amount.notnull" />' errormsg='<spring:message code="activity.peferential.amount.must.number" />'
                               value="<fmt:formatNumber pattern="#0.##" value="${confDetail.fdiscountMoney}" />"
                               class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item" id="discountLimit" style="display: none;">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="activity.preferential.cap" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="fdiscountLimit" id="fdiscountLimit" ignore="ignore"
                               placeholder='(0<spring:message code="main.unlimited" />)'
                               datatype="/^-?[1-9]+(\.\d+)?$|^-?0(\.\d+)?$|^-?[1-9]+[0-9]*(\.\d+)?$/"
                               nullmsg='<spring:message code="activity.preferential.cap.notnull" />' errormsg='<spring:message code="activity.preferential.cap.must.number" />'
                               value="<fmt:formatNumber pattern="#0.##" value="${confDetail.fdiscountLimit}" />"
                               class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item" id="fullReductionMoney">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="activity.target.amount" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="fullReductionTargetMoney1" id="fullReductionTargetMoney1"
                               placeholder='(0<spring:message code="main.unlimited" />)' ignore="ignore"
                               datatype="/^-?[1-9]+(\.\d+)?$|^-?0(\.\d+)?$|^-?[1-9]+[0-9]*(\.\d+)?$/"
                               nullmsg='<spring:message code="activity.peferential.amount.notnull" />' errormsg='<spring:message code="activity.peferential.amount.must.number" />'
                               value="<fmt:formatNumber pattern="#0.##" value="${confDetail1.ftargetMoney}" />"
                               class="layui-input"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="activity.peferential.amount" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="fullReductionDiscountMoney1" id="fullReductionDiscountMoney1"
                               ignore="ignore" datatype="/^-?[1-9]+(\.\d+)?$|^-?0(\.\d+)?$|^-?[1-9]+[0-9]*(\.\d+)?$/"
                               nullmsg='<spring:message code="activity.peferential.amount.notnull" />' errormsg='<spring:message code="activity.peferential.amount.must.number" />'
                               value="<fmt:formatNumber pattern="#0.##" value="${confDetail1.fdiscountMoney}" />"
                               class="layui-input"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="activity.target.amount" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="fullReductionTargetMoney2" id="fullReductionTargetMoney2"
                               placeholder='(0<spring:message code="main.unlimited" />)' ignore="ignore"
                               datatype="/^-?[1-9]+(\.\d+)?$|^-?0(\.\d+)?$|^-?[1-9]+[0-9]*(\.\d+)?$/"
                               nullmsg='<spring:message code="activity.peferential.amount.notnull" />' errormsg='<spring:message code="activity.peferential.amount.must.number" />'
                               value="<fmt:formatNumber pattern="#0.##" value="${confDetail2.ftargetMoney}" />"
                               class="layui-input"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="activity.peferential.amount" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="fullReductionDiscountMoney2" id="fullReductionDiscountMoney2"
                               ignore="ignore" datatype="/^-?[1-9]+(\.\d+)?$|^-?0(\.\d+)?$|^-?[1-9]+[0-9]*(\.\d+)?$/"
                               nullmsg='<spring:message code="activity.peferential.amount.notnull" />' errormsg='<spring:message code="activity.peferential.amount.must.number" />'
                               value="<fmt:formatNumber pattern="#0.##" value="${confDetail2.fdiscountMoney}" />"
                               class="layui-input"/>
                    </div>
                </div>
            </div>
        </div>
        <!--返现 -->
        <div style="display: none;" id="cashBack">
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="activity.target.amount" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="cashBackTargetMoney" id="cashBackTargetMoney" ignore="ignore"
                               placeholder='(0<spring:message code="main.unlimited" />)'
                               datatype="/^-?[1-9]+(\.\d+)?$|^-?0(\.\d+)?$|^-?[1-9]+[0-9]*(\.\d+)?$/"
                               nullmsg='<spring:message code="activity.target.amount.notnull" />' errormsg='<spring:message code="activity.target.amount.must.number" />'
                               value="<fmt:formatNumber pattern="#0.##" value="${confDetail.ftargetMoney}" />"
                               class="layui-input"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="activity.cash.return" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="fcashBackMoney" id="fcashBackMoney" ignore="ignore"
                               datatype="/^-?[1-9]+(\.\d+)?$|^-?0(\.\d+)?$|^-?[1-9]+[0-9]*(\.\d+)?$/"
                               nullmsg='<spring:message code="activity.cash.return.notnull" />' errormsg='<spring:message code="activity.cash.return.must.number" />'
                               value="<fmt:formatNumber pattern="#0.##" value="${confDetail.fcashBackMoney}" />"
                               class="layui-input"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="activity.all.count" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="iallCount" datatype="n" nullmsg='<spring:message code="activity.all.count.notnull" />' errormsg='<spring:message code="activity.all.count.must.number" />'
                           id="iallCount"
                           value="${conf.iallCount}" placeholder='(0<spring:message code="main.unlimited" />)' class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="activity.all.count.type" /></label>
                <div class="layui-input-inline">
                    <cang:select type="dic" groupNo="SP000115" name="icountType" layFilter="countType"
                                 exp="springMessageCode=activity.all.count.type.select"
                                 id="icountType" entire="true" value="${conf.icountType}"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label" id="allLabel">(<c:choose><c:when
                        test="${conf.icountType eq 10}"><spring:message code="main.user" /></c:when>
                    <c:when test="${conf.icountType eq 20}"><spring:message code="main.device" /></c:when><c:otherwise><spring:message code="activity.restriction.type" /></c:otherwise></c:choose>)<spring:message code="activity.total.number" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="iuserAllCount" datatype="n" nullmsg='<spring:message code="activity.oneday.total.number.notnull" />' errormsg='<spring:message code="activity.oneday.total.number.must.number" />'
                           id="iuserAllCount"
                           value="${conf.iuserAllCount}" placeholder="(0main.unlimited)" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label" id="dayLabel">(<c:choose><c:when
                        test="${conf.icountType eq 10}"><spring:message code="main.user" /></c:when>
                    <c:when test="${conf.icountType eq 20}"><spring:message code="main.device"/></c:when><c:otherwise><spring:message code="activity.restriction.type" /></c:otherwise></c:choose>)<spring:message code="activity.oneday.total.number" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="iuserDayCount" datatype="n" nullmsg='<spring:message code="activity.oneday.total.number.notnull" />' errormsg='<spring:message code="activity.oneday.total.number.must.number" />'
                           id="iuserDayCount"
                           value="${conf.iuserDayCount}" placeholder='(0<spring:message code="main.unlimited" />)' class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="activity.start.time" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="tactiveStartTime" datatype="*" nullmsg='<spring:message code="activity.start.time.notnull" />' id="tactiveStartTime"
                           value="<fmt:formatDate value='${conf.tactiveStartTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="activity.end.time" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="tactiveEndTime" datatype="*" nullmsg='<spring:message code="activity.end.time.notnull" />' id="tactiveEndTime"
                           value="<fmt:formatDate value='${conf.tactiveEndTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"
                           class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="activity.equipment" /></label>
                <div class="layui-input-inline">
                    <input type="radio" name="irangeDevice" lay-filter="rangeDevice" value="0" <c:if
                            test="${conf.irangeType eq 10 || conf.irangeType == 30}"> checked</c:if> title='<spring:message code="main.whole"/>' />
                    <input type="radio" name="irangeDevice" lay-filter="rangeDevice" value="1" <c:if
                            test="${conf.irangeType eq 20 || conf.irangeType == 40}"> checked</c:if> title='<spring:message code="main.part"/>' />
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="activity.product" /></label>
                <div class="layui-input-inline">
                    <input type="radio" name="irangeCommodity" lay-filter="rangeCommodity" value="0" <c:if
                            test="${conf.irangeType eq 10 || conf.irangeType == 20}"> checked</c:if> title='<spring:message code="main.whole"/>' />
                    <input type="radio" name="irangeCommodity" lay-filter="rangeCommodity" value="1" <c:if
                            test="${conf.irangeType eq 30 || conf.irangeType == 40}"> checked</c:if> title='<spring:message code="main.part"/>' />
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <table class="layui-table" id="deviceTable" style="display:none;">
                    <colgroup>
                        <col width="100">
                        <col width="150">
                        <col>
                    </colgroup>
                    <thead>
                    <tr>
                        <th><spring:message code="main.device.id" /></th>
                        <th><spring:message code="main.device.name" /></th>
                        <th><spring:message code="main.address" /></th>
                    </tr>
                    </thead>
                    <tbody id="deviceBody">
                    </tbody>
                </table>
                <input type="hidden" id="deviceIds" name="deviceIds" value="${useRange.sdeviceId}"/>
                <input type="hidden" id="deviceCodes" name="deviceCodes" value="${useRange.sdeviceCode}"/>
                <button class="layui-btn layui-btn-primary" id="selectDevice"
                        style="margin-left: 200px;margin-top: 10px;" type="button" <c:if
                        test="${conf.irangeType eq 10 || conf.irangeType == 30}"> disabled="disabled"</c:if>><spring:message code="activity.select.equipment" />
                </button>
            </div>
            <div class="layui-col-md6">
                <table class="layui-table" id="commodityTable" style="display:none;">
                    <colgroup>
                        <col width="100">
                        <col width="150">
                        <col>
                    </colgroup>
                    <thead>
                    <tr>
                        <th><spring:message code="main.product.number" /></th>
                        <th><spring:message code="main.product.name" /></th>
                        <th><spring:message code="sp.commodityInfo.selling.price" /></th>
                    </tr>
                    </thead>
                    <tbody id="commodityBody">
                    </tbody>
                </table>
                <input type="hidden" id="commodityIds" name="commodityIds" value="${useRange.scommodityId}"/>
                <input type="hidden" id="commodityCodes" name="commodityCodes" value="${useRange.scommodityCode}"/>
                <button class="layui-btn layui-btn-primary" id="selectCommodity"
                        style="margin-left: 200px;margin-top: 10px;" type="button" <c:if
                        test="${conf.irangeType eq 10 || conf.irangeType == 20}"> disabled="disabled"</c:if>><spring:message code="sb.select.product" />
                </button>
            </div>
        </div>
        <div class="layui-form-item mt13">
            <label class="layui-form-label"><spring:message code="activity.detail" /></label>
            <div class="layui-input-block block_textarea">
                <textarea class="layui-textarea layui-form-textarea80p" datatype="*" nullmsg='<spring:message code="activity.detail.notnull" />' id="sdescription"
                          name="sdescription">${conf.sdescription}</textarea>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel"/></button>
                <button class="layui-btn" id="myFormSub" style="float:none"><spring:message code="main.save"/></button>
            </div>
        </div>
        <input type="hidden" id="id" name="id" value="${conf.id}"/>
    </form>
</div>
<script id="commodity_table_tmpl" type="text/x-jsrender">
{{for data}}
    <tr>
      <td>{{:commodityCode}}</td>
      <td>{{:commodityName}}</td>
      <td>{{:salePrice}}</td>
    </tr>
{{/for}}

</script>
<script id="device_table_tmpl" type="text/x-jsrender">
{{for data}}
    <tr>
      <td>{{:deviceCode}}</td>
      <td>{{:deviceName}}</td>
      <td>{{:address}}</td>
    </tr>
{{/for}}

</script>
<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/jsrender.min.js" type="text/javascript"></script>
<script src="${staticSource}/resources/js/jsrender.converters.js" type="text/javascript"></script>
<script src="${staticSource}/resources/js/validform_min.js" type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate', 'element'], function () {
        var element = layui.element;
        var laydate = layui.laydate;

        var nowTime = new Date().valueOf();
        var min = null;
        var max = null;

        var start = laydate.render({
            elem: '#tactiveStartTime',
            type: 'datetime',
            min: nowTime,
            done: function (value, date) {
                endMax = end.config.max;
                end.config.min = date;
                end.config.min.month = date.month - 1;
                if (!isEmpty(value)) { //监听日期被切换
                    $("#tactiveStartTime").removeClass("Validform_error");
                    $("#tactiveStartTime").parent().find("span").hide();
                } else {
                    $("#tactiveStartTime").addClass("Validform_error");
                    $("#tactiveStartTime").parent().find("span").html($("#tactiveStartTime").attr("nullmsg"));
                    $("#tactiveStartTime").parent().find("span").removeClass("Validform_right");
                    $("#tactiveStartTime").parent().find("span").addClass("Validform_wrong");
                    $("#tactiveStartTime").parent().find("span").show();
                }
            }
        });
        var end = laydate.render({
            elem: '#tactiveEndTime',
            type: 'datetime',
            min: nowTime,
            done: function (value, date) {
                if ($.trim(value) == '') {
                    var curDate = new Date();
                    date = {'date': curDate.getDate(), 'month': curDate.getMonth() + 1, 'year': curDate.getFullYear()};
                }
                start.config.max = date;
                start.config.max.month = date.month - 1;
                if (!isEmpty(value)) {
                    $("#tactiveEndTime").removeClass("Validform_error");
                    $("#tactiveEndTime").parent().find("span").hide();
                } else {
                    $("#tactiveEndTime").addClass("Validform_error");
                    $("#tactiveEndTime").parent().find("span").html($("#tactiveEndTime").attr("nullmsg"));
                    $("#tactiveEndTime").parent().find("span").removeClass("Validform_right");
                    $("#tactiveEndTime").parent().find("span").addClass("Validform_wrong");
                    $("#tactiveEndTime").parent().find("span").show();
                }
            }
        });

        var form = layui.form;
        //监听提交
        $("#myForm").Validform({
            btnSubmit: "#myFormSub",  //根据id触发
            tiptype: 3,                  //第三种方式
            showAllError: true,
            beforeSubmit: function () { //验证过后执行save方法
                var idiscountWay = $("#idiscountWay").val();
                if (idiscountWay == 10) {//首单优惠
                    if (isEmpty($("#firstTargetMoney").val())) {
                        alertDel('<spring:message code="activity.first.target.money.notnull"/>');
                        return false;
                    }
                    if (isEmpty($("#firstDiscountAmount").val())) {
                        alertDel('<spring:message code="activity.first.discount.amount"/>');
                        return false;
                    }
                } else if (idiscountWay == 20) {//折扣
                    var discountSelect = $("#discountSelect").val();//折扣类型
                    if (discountSelect == 30 || discountSelect == 40) {//满X件
                        if (isEmpty($("#discountTargetNum").val())) {
                            alertDel('<spring:message code="activity.discount.target.num"/>');
                            return false;
                        }
                    }
                    if (discountSelect == 20 || discountSelect == 40) {//满X元
                        if (isEmpty($("#discountTargetMoney").val())) {
                            alertDel('<spring:message code="activity.discount.target.money"/>');
                            return false;
                        }
                    }
                    if (isEmpty($("#fdiscount").val())) {
                        alertDel('<spring:message code="activity.discount.notnull"/>');
                        return false;
                    }
                } else if (idiscountWay == 30) {//满减
                    var fullReductionSelect = $("#fullReductionSelect").val();//满减类型
                    if (fullReductionSelect == 70 || fullReductionSelect == 80) {//满X件 //满X元且满X件
                        if (isEmpty($("#fullReductionTargetNum").val())) {
                            alertDel('<spring:message code="activity.target.count.must.notnull"/>');
                            return false;
                        }
                    }
                    if (fullReductionSelect == 50 || fullReductionSelect == 60 || fullReductionSelect == 80) {//满X元
                        if (isEmpty($("#fullReductionTargetMoney").val())) {
                            alertDel('<spring:message code="activity.discount.target.count"/>');
                            return false;
                        }
                        if (isEmpty($("#fullReductionDiscountMoney").val())) {
                            alertDel('<spring:message code="activity.peferential.amount.notnull"/>');
                            return false;
                        }
                    }
                    if (fullReductionSelect == 60) {//每满X元
                        if (isEmpty($("#fdiscountLimit").val())) {
                            alertDel('<spring:message code="activity.full.reduction.notnull"/>');
                            return false;
                        }
                    }
                } else if (idiscountWay == 50) {//返现优惠
                    if (isEmpty($("#cashBackTargetMoney").val())) {
                        alertDel('<spring:message code="activity.target.amount.must.notnull"/>');
                        return false;
                    }
                    if (isEmpty($("#fcashBackMoney").val())) {
                        alertDel('<spring:message code="activity.cash.return.notnull"/>');
                        return false;
                    }
                }
                var rangeDevice = $('input[name="irangeDevice"]:checked').val();
                if (rangeDevice == 1) {
                    if (isEmpty($("#deviceIds").val())) {
                        alertDel('<spring:message code="activity.equipment.notnull"/>');
                        return false;
                    }
                }
                var rangeCommodity = $('input[name="irangeCommodity"]:checked').val();
                if (rangeCommodity == 1) {
                    if (isEmpty($("#commodityIds").val())) {
                        alertDel('<spring:message code="activity.product.notnull"/>');
                        return false;
                    }
                }
                var loadIndex = loading();
                $('#myForm').ajaxSubmit({
                    type: "post",
                    dataType: "json",
                    async: true,
                    error: function () {
                        alertDelAndReload('<spring:message code="main.error.try.again"/>');
                    },
                    success: function (msg) {
                        //成功返回
                        closeLayer(loadIndex);//关闭加载框
                        if (msg.success) {
                            alertSuccess('<spring:message code="main.success"/>', {
                                'index': index
                            }, function () {
                                parent.closeLayerAndRefresh(index);
                            });
                        } else {
                            alertDel(msg.msg);
                        }
                    }
                });
                return false;
            }
        });
        //活动类型
        form.on('select(discountWay)', function (data) {
            if (data.value == 10) {//首单
                $("#firstOrder").show();
                $("#discount").hide();
                $("#fullReduction").hide();
                $("#cashBack").hide();
                $("#iisSuperposition").removeAttr("disabled");
            } else if (data.value == 20) {//折扣
                $("#firstOrder").hide();
                $("#discount").show();
                $("#fullReduction").hide();
                $("#cashBack").hide();
                $("#iisSuperposition").attr("disabled", "disabled");
            } else if (data.value == 30) {//满减
                $("#firstOrder").hide();
                $("#discount").hide();
                $("#fullReduction").show();
                $("#cashBack").hide();
                $("#iisSuperposition").attr("disabled", "disabled");
            } else if (data.value == 50) {//返现
                $("#firstOrder").hide();
                $("#discount").hide();
                $("#fullReduction").hide();
                $("#cashBack").show();
                $("#iisSuperposition").attr("disabled", "disabled");
            } else {//返券
                $("#firstOrder").hide();
                $("#discount").hide();
                $("#fullReduction").hide();
                $("#cashBack").hide();
                $("#iisSuperposition").attr("disabled", "disabled");
            }
            form.render('select');
            if (!isEmpty(data.value)) {
                $("#idiscountWay").parent().find("span").hide();
            } else {
                $("#idiscountWay").parent().find("span").show();
                if (!$("#idiscountWay").parent().find("span").hasClass("Validform_wrong")) {
                    $("#idiscountWay").parent().find("span").html($("#idiscountWay").attr("nullmsg"));
                    $("#idiscountWay").parent().find("span").removeClass("Validform_right");
                    $("#idiscountWay").parent().find("span").addClass("Validform_wrong");
                }
            }
        });
        //折扣类型
        form.on('select(discountSelect)', function (data) {
            discountSelect(data.value);
        });
        //满减类型
        form.on('select(fullReductionSelect)', function (data) {
            fullReductionSelect(data.value);
        });
        //参与次数限制类型
        form.on('select(countType)', function (data) {
            if (data.value == 10) {
                //用户
                $("#allLabel").html('<spring:message code="activity.limit.user.count"/>');
                $("#dayLabel").html('<spring:message code="activity.limit.user.oneday.count"/>');
            } else if (data.value == 20) {
                //设备
                $("#allLabel").html('<spring:message code="activity.limit.equipment.count"/>');
                $("#dayLabel").html('<spring:message code="activity.limit.equipment.oneday.count"/>');
            } else {
                //限制类型
                $("#allLabel").html('<spring:message code="activity.limit.count"/>');
                $("#dayLabel").html('<spring:message code="activity.limit.oneday.count"/>');
            }
            if (!isEmpty(data.value)) {
                $("#icountType").parent().find("span").hide();
            } else {
                $("#icountType").parent().find("span").show();
                if (!$("#icountType").parent().find("span").hasClass("Validform_wrong")) {
                    $("#icountType").parent().find("span").html($("#icountType").attr("nullmsg"));
                    $("#icountType").parent().find("span").removeClass("Validform_right");
                    $("#icountType").parent().find("span").addClass("Validform_wrong");
                }
            }
        });
        //设备单选
        form.on('radio(rangeDevice)', function (data) {
            if (data.value == 1) {
                //部分设备
                $("#selectDevice").removeAttr("disabled");
            } else {
                //全部设备
                $("#selectDevice").attr("disabled", "disabled");
                $("#deviceIds").val("");
                $("#deviceCodes").val("");
                hideDeviceTable();
            }
            $(this).blur();
        });
        //商品单选
        form.on('radio(rangeCommodity)', function (data) {
            if (data.value == 1) {
                //部分商品
                $("#selectCommodity").removeAttr("disabled");
            } else {
                //全部商品
                $("#selectCommodity").attr("disabled", "disabled");
                $("#commodityIds").val("");
                $("#commodityCodes").val("");
                hideCommodityTable();
            }
            $(this).blur();
        });
    });
    function fullReductionSelect(val) {
        if (val == 50) {//满减满X元
            $("#fullReductionMoney").show();
            $("#discountLimit").hide();
            $("#fullReductionTargetNum").attr("disabled", "disabled");
            $("#fullReductionTargetNum").val("");
            $("#fullReductionTargetMoney").removeAttr("disabled");
            $("#fullReductionDiscountMoney").removeAttr("disabled");
        } else if (val == 60) {//满减每满X元
            $("#fullReductionMoney").hide();
            $("#discountLimit").show();
            $("#fullReductionTargetNum").attr("disabled", "disabled");
            $("#fullReductionTargetNum").val("");
            $("#fullReductionTargetMoney").removeAttr("disabled");
            $("#fullReductionDiscountMoney").removeAttr("disabled");
        } else if (val == 70) {//满减满X件
            $("#fullReductionMoney").hide();
            $("#discountLimit").hide();
            $("#fullReductionTargetMoney").attr("disabled", "disabled");
            $("#fullReductionDiscountMoney").attr("disabled", "disabled");
            $("#fullReductionTargetMoney").val("");
            $("#fullReductionDiscountMoney").val("");
            $("#fullReductionTargetNum").removeAttr("disabled");
        } else if (val == 80) {//满减满X元且满Y件
            $("#fullReductionMoney").show();
            $("#discountLimit").hide();
            $("#fullReductionTargetMoney").removeAttr("disabled");
            $("#fullReductionDiscountMoney").removeAttr("disabled");
            $("#fullReductionTargetNum").removeAttr("disabled");
        }
        if (!isEmpty(val)) {
            $("#fullReductionSelect").parent().find("span").hide();
        } else {
            $("#fullReductionSelect").parent().find("span").show();
            if (!$("#fullReductionSelect").parent().find("span").hasClass("Validform_wrong")) {
                $("#fullReductionSelect").parent().find("span").html($("#fullReductionSelect").attr("nullmsg"));
                $("#fullReductionSelect").parent().find("span").removeClass("Validform_right");
                $("#fullReductionSelect").parent().find("span").addClass("Validform_wrong");
            }
        }
    }
    function discountSelect(val) {
        if (val == 20) {//打折满X元
            $("#discountTargetNum").attr("disabled", "disabled");
            $("#discountTargetNum").val("");
            $("#discountTargetMoney").removeAttr("disabled");
        } else if (val == 30) {//打折满X件
            $("#discountTargetMoney").attr("disabled", "disabled");
            $("#discountTargetMoney").val("");
            $("#discountTargetNum").removeAttr("disabled");
        } else if (val == 40) {//打折满X元且满X件
            $("#discountTargetMoney").removeAttr("disabled");
            $("#discountTargetNum").removeAttr("disabled");
        }
        if (!isEmpty(val)) {
            $("#discountSelect").parent().find("span").hide();
        } else {
            $("#discountSelect").parent().find("span").show();
            if (!$("#discountSelect").parent().find("span").hasClass("Validform_wrong")) {
                $("#discountSelect").parent().find("span").html($("#discountSelect").attr("nullmsg"));
                $("#discountSelect").parent().find("span").removeClass("Validform_right");
                $("#discountSelect").parent().find("span").addClass("Validform_wrong");
            }
        }
    }
    $(function () {
        //返回
        $("#cancelBtn").click(function () {
            parent.layer.close(index);
            return false;
        });
        //选择设备
        $("#selectDevice").click(function () {
            var deviceIds = $("#deviceIds").val();
            var deviceCodes = $("#deviceCodes").val();
            if (!isEmpty(deviceIds)) {
                showLayerMax('<spring:message code="activity.select.equipment"/>', ctx + "/common/selectDevice?deviceIds=" + deviceIds + "&deviceCodes=" + deviceCodes + "&allIstatus=20");
            } else {
                showLayerMax('<spring:message code="activity.select.equipment"/>', ctx + "/common/selectDevice?allIstatus=20");
            }
        });
        //选择商品
        $("#selectCommodity").click(function () {
            var commodityIds = $("#commodityIds").val();
            var commodityCodes = $("#commodityCodes").val();
            if (!isEmpty(commodityIds)) {
                showLayerMax('<spring:message code="activity.select.product"/>', ctx + "/common/selectCommodity?commodityIds=" + commodityIds + "&commodityCodes=" + commodityCodes);
            } else {
                showLayerMax('<spring:message code="activity.select.product"/>', ctx + "/common/selectCommodity");
            }
        });
        initDeviceTable();
        initCommodityTable();
        initDiscountDetailByPage();
    });
    //初始化优惠显示
    function initDiscountDetailByPage() {
        var discountType = "${conf.idiscountWay}";
        if (discountType == 10) {
            $("#firstOrder").show();
            $("#iisSuperposition").removeAttr("disabled");
        } else if (discountType == 20) {
            $("#discount").show();
            discountSelect("${confDetail.idiscountType}")
        } else if (discountType == 30) {
            $("#fullReduction").show();
            fullReductionSelect("${confDetail.idiscountType}")
        } else if (discountType == 50) {
            $("#cashBack").show();
        }
    }
    //初始化优惠显示
    function initDiscountDetail() {
        var discountType = "${conf.idiscountWay}";
        if (discountType == 10) {
            $("#firstOrder").show();
            initDiscountValue();
            initFullReductionValue();
            initCashBackValue();
        } else if (discountType == 20) {
            $("#discount").show();
            initFirstValue();
            initFullReductionValue();
            initCashBackValue();
        } else if (discountType == 30) {
            $("#fullReduction").show();
            initDiscountValue();
            initFirstValue();
            initCashBackValue();
        } else if (discountType == 50) {
            initDiscountValue();
            initFullReductionValue();
            initFirstValue();
            $("#cashBack").show();
        }
    }
    //初始化优惠值
    function initFirstValue() {
        $("#firstTargetMoney").val("");
        $("#firstDiscountAmount").val("");
    }
    function initDiscountValue() {
        $("#fullReductionTargetMoney").val("");
        $("#fullReductionTargetNum").val("");
        $("#fullReductionDiscountMoney").val("");
        $("#fdiscountLimit").val("");
        $("#fullReductionTargetMoney1").val("");
        $("#fullReductionTargetMoney2").val("");
        $("#fullReductionDiscountMoney1").val("");
        $("#fullReductionDiscountMoney2").val("");
    }
    function initFullReductionValue() {
        $("#discountTargetMoney").val("");
        $("#discountTargetNum").val("");
        $("#fdiscount").val("");
    }
    function initCashBackValue() {
        $("#cashBackTargetMoney").val("");
        $("#fcashBackMoney").val("");
    }
    //初始化设备表格
    function initDeviceTable() {
        var deviceIds = $("#deviceIds").val();
        if (isEmpty(deviceIds)) {//没有选择设备
            hideDeviceTable();
            return;
        }
        $.ajax({
            url: ctx + '/common/getDeviceByIds',
            type: 'POST', //GET
            async: false,    //或false,是否异步
            data: {deviceIds: deviceIds},
            dataType: 'json',
            error: function () {
                hideDeviceTable();
            },
            success: function (msg) {
                if (msg.success) {//成功返回
                    var deviceList = msg.data;
                    if (!isEmpty(deviceList)) {
                        var html = $("#device_table_tmpl").render(msg);
                        $("#deviceBody").html(html);
                        $("#deviceTable").show();
                    } else {
                        hideDeviceTable();
                    }
                } else {
                    hideDeviceTable();
                }
            }
        });
    }
    //隐藏表格 设备
    function hideDeviceTable() {
        $("#deviceBody").html("");
        $("#deviceTable").hide();
    }
    //隐藏表格 商品
    function hideCommodityTable() {
        $("#commodityBody").html("");
        $("#commodityTable").hide();
    }
    //初始化商品表格
    function initCommodityTable() {
        var commodityIds = $("#commodityIds").val();
        if (isEmpty(commodityIds)) {//没有选择设备
            hideCommodityTable();
            return;
        }
        $.ajax({
            url: ctx + '/common/getCommodityByIds',
            type: 'POST', //GET
            async: false,    //或false,是否异步
            data: {commodityIds: commodityIds},
            dataType: 'json',
            error: function () {
                hideCommodityTable();
            },
            success: function (msg) {
                if (msg.success) {//成功返回
                    var commodityList = msg.data;
                    if (!isEmpty(commodityList)) {
                        var html = $("#commodity_table_tmpl").render(msg);
                        $("#commodityBody").html(html);
                        $("#commodityTable").show();
                    } else {
                        hideCommodityTable();
                    }
                } else {
                    hideCommodityTable();
                }
            }
        });
    }
    //确认选择设备
    function selectDevice(deviceIds, deviceCodes) {
        $("#deviceIds").val(deviceIds);
        $("#deviceCodes").val(deviceCodes);
        initDeviceTable();
    }
    //确认选择商品
    function selectCommodity(commodityIds, commodityCodes) {
        $("#commodityIds").val(commodityIds);
        $("#commodityCodes").val(commodityCodes);
        initCommodityTable();
    }
</script>
</body>
</html>

