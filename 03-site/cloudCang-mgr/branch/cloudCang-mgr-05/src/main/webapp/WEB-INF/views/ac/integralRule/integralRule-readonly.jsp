<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>积分规则</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?20188" rel="stylesheet">
</head>
<body>
<div class="ibox-content">
    <div class="layui-form-item wflayui-form-item">
        <table  cellspacing="0" border="0">
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='ac.couponrule.is.it.effective' />：</td>
                <td class="text-right" style="width:32%"><c:if test="${integralRule.iisValid eq 1}"><spring:message code="main.enable" /></c:if><c:if test="${(empty integralRule.iisValid ? 0 : integralRule.iisValid) eq 0}"><spring:message code='sh.prohibit' /></c:if></td>
                <td class="text-left" style="width:18%">&nbsp;</td>
                <td class="text-right" style="width:32%">&nbsp;</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code='ac.couponrule.way.of.sending.coupons' />：</td>
                <td class="text-right"  style="width:32%"><c:if test="${integralRule.igiveMethod eq 10}"><spring:message code='ac.couponrule.proportion' /></c:if>
                    <c:if test="${integralRule.igiveMethod eq 20}"><spring:message code='ac.couponrule.fixed.amount' /></c:if></td>
                <td class="text-left" style="width:18%"><spring:message code='ac.integralrule.points.minutes.ratio.%' />：</td>
                <td class="text-right"  style="width:32%"><fmt:formatNumber value="${integralRule.fbaseIntegral}" pattern="#0.##"/></td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='ac.integralrule.gift.lower.limit.minutes' />：</td>
                <td class="text-right"  style="width:32%">${integralRule.fminValue}</td>
                <td class="text-left"  style="width:18%"><spring:message code='ac.integralrule.gift.limit.minutes' />：</td>
                <td class="text-right"  style="width:32%">${integralRule.fmaxValue}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='ac.integralrule.reward.description' />：</td>
                <td class="text-right"  style="width:32%">${integralRule.sactivityInstruction}</td>
                <td class="text-left"  style="width:18%"><spring:message code="sm.remarks" />：</td>
                <td class="text-right"  style="width:32%">${integralRule.sremark}</td>
            </tr>
        </table>
    </div>
</div>
<script type="text/javascript">
    $(".wflayui-form-item tr:even").addClass("odd");
</script>
</body>
</html>