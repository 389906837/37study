<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title><spring:message code="menu.replenishment.center" /></title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
</head>
<body>
<div class="ibox-content">
        <div class="layui-form-item wflayui-form-item">
        <div class="wfsplitt">
            <spring:message code="rm.replenishment.bill.number" />：${replenishmentRecord.scode}
        </div>

        <table  cellspacing="0" border="0">
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="main.merchant.number" />：</td>
                <td class="text-right" style="width:32%">${merchantInfo.scode}</td>
                <td class="text-left" style="width:18%"><spring:message code="main.merchant.name" />:</td>
                <td class="text-right" style="width:32%">${merchantInfo.sname}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="main.device.id" />：</td>
                <td class="text-right" style="width:32%">${replenishmentRecord.sdeviceCode}</td>
                <td class="text-left" style="width:18%"><spring:message code="main.device.name" />:</td>
                <td class="text-right" style="width:32%">${deviceInfo.sname}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="main.device.address" />：</td>
                <td class="text-right" style="width:32%">${replenishmentRecord.sdeviceAddress}</td>
                <td class="text-left" style="width:18%">&nbsp;</td>
                <td class="text-right" style="width:32%">&nbsp;</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="rm.replenishment.status" />：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${replenishmentRecord.istatus == 10 }"><spring:message code="rm.to.be.delivered" /></c:if>
                    <c:if test="${replenishmentRecord.istatus == 20 }"><spring:message code="rm.distribution" /></c:if>
                    <c:if test="${replenishmentRecord.istatus == 30 }"><spring:message code="rm.completed" /></c:if>
                    <c:if test="${replenishmentRecord.istatus == 40 }"><spring:message code="rm.cancellation.of.distribution" /></c:if>
                </td>
                <td class="text-left"  style="width:18%"><spring:message code="rm.replenishment.type" />：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${replenishmentRecord.itype == 10 }"><spring:message code="rm.dynamic.replenishment" /></c:if>
                    <c:if test="${replenishmentRecord.itype == 20 }"><spring:message code="rm.general.replenishment" /></c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="rm.name.of.replenisher" />：</td>
                <td class="text-right"  style="width:32%">${replenishmentRecord.srenewalName}</td>
                <td class="text-left"  style="width:18%"><spring:message code="rm.replenisher-s.mobile.phone.number" />：</td>
                <td class="text-right"  style="width:32%">${replenishmentRecord.srenewalMobile}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="rm.actual.number.of.on.the.shelves" />：</td>
                <td class="text-right"  style="width:32%">${replenishmentRecord.iactualShelves}</td>
                <td class="text-left"  style="width:18%"><spring:message code="rm.actual.number.of.off.the.shelves" />：</td>
                <td class="text-right"  style="width:32%">${replenishmentRecord.iactualUnder}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code="rm.actual.the.total.number.of.on.the.shelves" />：</td>
                <td class="text-right"  style="width:32%">${replenishmentRecord.iactualShelvesAmount}</td>
                <td class="text-left" style="width:18%"><spring:message code="rm.actual.the.total.number.of.off.the.shelves" />：</td>
                <td class="text-right"  style="width:32%">${replenishmentRecord.iactualUnderAmount}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="rm.time.of.replenishment" />：</td>
                <td class="text-right"  style="width:32%"><fmt:formatDate value="${replenishmentRecord.treplenishmentTime}" pattern="yyyy-MM-dd"/></td>
                <td class="text-left" style="width:18%"><spring:message code="main.remarks" />：</td>
                <td class="text-right"  style="width:32%">${replenishmentRecord.sremark}</td>
            </tr>
        </table>
</div>
        <div class="wfsplitt" style="margin-top: 20px">
            <p><spring:message code="rm.details.of.replenishment.commodities" /></p>
        </div>
        <table class="layui-table">
            <colgroup>
                <col width="100">
                <col width="150">
                <col width="100">
                <col width="100">
                <col width="100">
                <col width="100">
                <col width="100">
            </colgroup>
            <thead>
            <tr>
                <th><spring:message code="main.product.number" /></th>
                <th><spring:message code="main.product.name" /></th>
                <th><spring:message code="rm.unit.price.of.commodity.sale" /></th>
                <th><spring:message code="rm.replenishment.quantity" /></th>
                <th><spring:message code="rm.total.merchandise" /></th>
                <th><spring:message code="rm.replenishment.type" /></th>
                <th><spring:message code="rm.commodity.status" /></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${list}" var="item" varStatus="L">
                <tr>
                   <td>${item.scommodityCode}</td>
                    <td>${item.spsname}</td>
                    <td>${item.fcommodityPrice}</td>
                    <td>${item.forderCount}</td>
                    <td>${item.fcommodityAmount}</td>
                    <td>
                        <c:if test="${item.itype == 10}"><spring:message code="rm.upper.shelf" /></c:if>
                        <c:if test="${item.itype == 20}"><spring:message code="rm.lower.shelf" /></c:if>
                    </td>
                    <td>
                        <c:if test="${item.icommodityStatus == 10}"><spring:message code="main.normal" /></c:if>
                        <c:if test="${item.icommodityStatus == 20}"><spring:message code="main.invalid" /></c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
<input type="hidden" id="id" name="id" value="${replenishmentRecord.id}"/>
    </div>
</div>
<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    $(".wflayui-form-item tr:even").addClass("odd");

</script>
</body>
</html>

