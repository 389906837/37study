<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>场景活动详情</title>
<link href="${staticSource}/resources/layui/css/layui.css?2" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?1" rel="stylesheet">
</head>
<body>
<div class="ibox-content">
    <div class="layui-form-item wflayui-form-item">
        <table  cellspacing="0" border="0">
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="main.code" />：</td>
                <td class="text-right" style="width:32%">${conf.sconfCode}</td>
                <td class="text-left" style="width:18%">&nbsp;</td>
                <td class="text-right" style="width:32%">&nbsp;</td>

            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code="main.name" />：</td>
                <td class="text-right"  style="width:32%">${conf.sconfName}</td>
                <td class="text-left"  style="width:18%"><spring:message code='activity.scenario.type' />：</td>
                <td class="text-right"  style="width:32%">
                <c:if test="${conf.iscenesType eq 10}"><spring:message code='ac.activityconf.platform.gift' /></c:if>
                <c:if test="${conf.iscenesType eq 20}"><spring:message code='ac.activityconf.sign.up' /></c:if>
                <c:if test="${conf.iscenesType eq 30}"><spring:message code="main.first.open.alipay.free" /></c:if>
                <c:if test="${conf.iscenesType eq 40}"><spring:message code="main.first.open.wechat.free" /></c:if>
                <c:if test="${conf.iscenesType eq 50}"><spring:message code='ac.activityconf.recommended.registration' /></c:if>
                <c:if test="${conf.iscenesType eq 60}"><spring:message code='ac.activityconf.first.order' /></c:if>
                <c:if test="${conf.iscenesType eq 70}"><spring:message code='ac.activityconf.order' /></c:if>
                    <c:if test="${conf.iscenesType eq 51}"><spring:message code='ac.activityconf.recommended.first' /></c:if></td>

            </tr>
            <tr>
                <td class="text-left" style="width:19%"><spring:message code="activity.all.count"/>:</td>
                <td class="text-right"  style="width:32%">${(conf.iallCount eq 0) ? '<spring:message code="main.unlimited"/>':conf.iallCount}<c:if test="${conf.iallCount>0}"><spring:message code='ac.activityconf.times' /></c:if></td>
                <td class="text-left"  style="width:18%"><spring:message code='activity.all.count.type' />：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${conf.icountType eq 10}"><spring:message code='activity.all.count.type.user' /></c:if>
                    <c:if test="${conf.icountType eq 20}"><spring:message code='activity.all.count.type.equipment' /></c:if></td>
                </td>
            </tr>
            <c:if test="${conf.icountType eq 10}">
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="activity.limit.user.count"/></td>
                <td class="text-right"  style="width:32%">${(conf.iuserAllCount eq 0) ? '<spring:message code="main.unlimited"/>':conf.iuserAllCount}<c:if test="${conf.iuserAllCount>0}"><spring:message code='ac.activityconf.times' /></c:if></td>
                <td class="text-left"  style="width:18%"><spring:message code="activity.limit.user.oneday.count"/>：</td>
                <td class="text-right"  style="width:32%">${(conf.iuserDayCount eq 0) ? '<spring:message code="main.unlimited"/>':conf.iuserDayCount}<c:if test="${conf.iuserDayCount>0}"><spring:message code='ac.activityconf.times' /></c:if></td>
            </tr>
            </c:if>
            <c:if test="${conf.icountType eq 20}">
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="activity.limit.equipment.count"/>：</td>
                <td class="text-right"  style="width:32%">${conf.iuserAllCount}<spring:message code='ac.activityconf.times' /></td>
                <td class="text-left"  style="width:18%"><spring:message code='activity.limit.equipment.oneday.count' />：</td>
                <td class="text-right"  style="width:32%">${conf.iuserDayCount}<spring:message code='ac.activityconf.times' />=</td>
            </tr>
            </c:if>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="activity.start.time"/>：</td>
                <td class="text-right" style="width:32%"><fmt:formatDate value='${conf.tactiveStartTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                <td class="text-left" style="width:18%"><spring:message code="activity.end.time"/>：</td>
                <td class="text-right" style="width:32%"><fmt:formatDate value='${conf.tactiveEndTime}' pattern='yyyy-MM-dd HH:mm:ss'/> </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='activity.description' />：</td>
                <td class="text-right"  style="width:32%">${conf.sdescription}</td>
                <td class="text-left" style="width:18%">&nbsp;</td>
                <td class="text-right" style="width:32%">&nbsp;</td>
            </tr>
        </table>
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

