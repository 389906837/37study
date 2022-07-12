<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>促销活动详情</title>
<link href="${staticSource}/resources/layui/css/layui.css?2" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?1" rel="stylesheet">
</head>
<body>
<div class="ibox-content">
    <div class="layui-form-item wflayui-form-item">
        <table  cellspacing="0" border="0">
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="main.code" />:</td>
                <td class="text-right" style="width:32%">${conf.sconfCode}</td>
                <td class="text-left"  style="width:18%"><spring:message code="main.name" />:</td>
                <td class="text-right"  style="width:32%">${conf.sconfName}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code="activity.type"/>:</td>
                <td class="text-right"  style="width:32%">
                <c:if test="${conf.idiscountWay eq 10}"><spring:message code="activity.type.first"/></c:if>
                <c:if test="${conf.idiscountWay eq 20}"><spring:message code="activity.type.discount"/></c:if>
                <c:if test="${conf.idiscountWay eq 30}"><spring:message code="activity.type.full.reduction"/></c:if>
                <c:if test="${conf.idiscountWay eq 40}"><spring:message code="activity.type.return.ticket"/></c:if>
                <c:if test="${conf.idiscountWay eq 50}"><spring:message code="activity.type.return.cash"/></c:if>
                </td>
                <c:if test="${conf.idiscountWay eq 10}">
                    <td class="text-left"  style="width:18%"><spring:message code="activity.is.superposition"/>:</td>
                    <td class="text-right"  style="width:32%">
                    <c:if test="${conf.iisSuperposition eq 0}"><spring:message code="main.no"/></c:if>
                    <c:if test="${conf.iisSuperposition eq 1}"><spring:message code="main.yes"/></c:if></td>
                </c:if>
                <c:if test="${conf.idiscountWay eq 20}">
                <td class="text-left" style="width:18%"><spring:message code="activity.count.type"/>:</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${confDetail.idiscountType eq 20}"><spring:message code="activity.count.detile.20"/></c:if>
                    <c:if test="${confDetail.idiscountType eq 30}"><spring:message code="activity.count.detile.30"/></c:if>
                    <c:if test="${confDetail.idiscountType eq 40}"><spring:message code="activity.count.detile.40"/></c:if>
                </td>
                </c:if>
                <c:if test="${conf.idiscountWay eq 30}">
                <td class="text-left" style="width:18%"><spring:message code="activity.full.reduction"/>:</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${confDetail.idiscountType eq 50}"><spring:message code="activity.full.reduction.50"/></c:if>
                    <c:if test="${confDetail.idiscountType eq 60}"><spring:message code="activity.full.reduction.60"/></c:if>
                    <c:if test="${confDetail.idiscountType eq 70}"><spring:message code="activity.full.reduction.70"/></c:if>
                    <c:if test="${confDetail.idiscountType eq 80}"><spring:message code="activity.full.reduction.80"/></c:if>
                </td>
                </c:if>
                <c:if test="${conf.idiscountWay eq 40}">
                    <td class="text-left" style="width:18%">&nbsp;</td>
                    <td class="text-right" style="width:32%">&nbsp;</td>
                </c:if>
                <c:if test="${conf.idiscountWay eq 50}">
                    <td class="text-left" style="width:18%">&nbsp;</td>
                    <td class="text-right" style="width:32%">&nbsp;</td>
                </c:if>
            </tr>
            <c:if test="${conf.idiscountWay eq 10}">
            <tr>
                    <td class="text-left" style="width:18%"><spring:message code="activity.target.amount"/>:</td>
                    <td class="text-right"  style="width:32%">${confDetail.ftargetMoney}<spring:message code="main.currency"/></td>
                    <td class="text-left" style="width:18%"><spring:message code="activity.peferential.amount"/>:</td>
                    <td class="text-right" style="width:18%">${confDetail.fdiscountMoney}<spring:message code="main.currency"/></td>
            </tr>
            </c:if>
            <c:if test="${confDetail.idiscountType eq 20}">
                <tr>
                    <td class="text-left" style="width:18%"><spring:message code="activity.target.amount"/>:</td>
                    <td class="text-right"  style="width:32%">${confDetail.ftargetMoney}<spring:message code="main.currency"/></td>
                    <td class="text-left"  style="width:18%"><spring:message code="activity.discount"/>(0-10)：</td>
                    <td class="text-right"  style="width:32%"><fmt:formatNumber pattern="#0.00##" value="${confDetail.fdiscount}" /></td>
                </tr>
            </c:if>
            <c:if test="${confDetail.idiscountType eq 30}">
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code="activity.target.count"/>:</td>
                <td class="text-right"  style="width:32%">${confDetail.ftargetNum}</td>
                <td class="text-left"  style="width:18%"><spring:message code="activity.discount"/>(0-10)：</td>
                <td class="text-right"  style="width:32%"><fmt:formatNumber pattern="#0.00##" value="${confDetail.fdiscount}" /></td>
            </tr>
            </c:if>
            <c:if test="${confDetail.idiscountType eq 40}">
                <tr>
                    <td class="text-left"  style="width:18%"><spring:message code="activity.target.count"/>：</td>
                    <td class="text-right"  style="width:32%">${confDetail.ftargetNum}</td>
                    <td class="text-left" style="width:18%"><spring:message code="activity.target.amount"/>:</td>
                    <td class="text-right"  style="width:32%">${confDetail.ftargetMoney}<spring:message code="main.currency"/></td>
                </tr>
                <tr>
                    <td class="text-left"  style="width:18%"><spring:message code="activity.discount"/>(0-10):</td>
                    <td class="text-right"  style="width:32%"><fmt:formatNumber pattern="#0.00##" value="${confDetail.fdiscount}"/></td>
                    <td class="text-left" style="width:18%">&nbsp;</td>
                    <td class="text-right" style="width:32%">&nbsp;</td>
                </tr>
            </c:if>
            <c:if test="${confDetail.idiscountType eq 50}">
                <tr>
                    <td class="text-left" style="width:18%"><spring:message code="activity.target.amount"/>:</td>
                    <td class="text-right"  style="width:32%">${confDetail.ftargetMoney}<spring:message code="main.currency"/></td>
                    <td class="text-left"  style="width:18%"><spring:message code="activity.peferential.amount"/>:</td>
                    <td class="text-right"  style="width:32%">${confDetail.fdiscountMoney}<spring:message code="main.currency"/></td>
                </tr>
            </c:if>
                <c:if test="${confDetail1.idiscountType eq 50}">
                <tr>
                    <td class="text-left" style="width:18%"><spring:message code="activity.target.amount"/>:</td>
                    <td class="text-right"  style="width:32%">${confDetail1.ftargetMoney}</td>
                    <td class="text-left"  style="width:18%"><spring:message code="activity.peferential.amount"/>:</td>
                    <td class="text-right"  style="width:32%">${confDetail1.fdiscountMoney}<spring:message code="main.currency"/></td>
                </tr>
                </c:if>
            <c:if test="${confDetail2.idiscountType eq 50}">
                <tr>
                    <td class="text-left" style="width:18%"><spring:message code="activity.target.amount"/>:</td>
                    <td class="text-right"  style="width:32%">${confDetail2.ftargetMoney}<spring:message code="main.currency"/></td>
                    <td class="text-left"  style="width:18%"><spring:message code="activity.peferential.amount"/>:</td>
                    <td class="text-right"  style="width:32%">${confDetail2.fdiscountMoney}<spring:message code="main.currency"/></td>
                </tr>
            </c:if>
            <c:if test="${confDetail.idiscountType eq 60}">
                <tr>
                    <td class="text-left" style="width:18%"><spring:message code="activity.target.amount"/>:</td>
                    <td class="text-right"  style="width:32%">${confDetail.ftargetMoney}<spring:message code="main.currency"/></td>
                    <td class="text-left" style="width:18%">&nbsp;</td>
                    <td class="text-right" style="width:32%">&nbsp;</td>
                </tr>
                <tr>
                    <td class="text-left"  style="width:18%"><spring:message code="activity.peferential.amount"/>:</td>
                    <td class="text-right"  style="width:32%">${confDetail.fdiscountMoney}<spring:message code="main.currency"/></td>
                    <td class="text-left"  style="width:18%"><spring:message code="activity.preferential.cap"/>:</td>
                    <td class="text-right"  style="width:32%">${confDetail.fdiscountLimit}<spring:message code="main.currency"/></td>
                </tr>
            </c:if>
            <c:if test="${confDetail.idiscountType eq 70}">
                <tr>
                    <td class="text-left"  style="width:18%"><spring:message code="activity.target.count"/>:</td>
                    <td class="text-right"  style="width:32%">${confDetail.ftargetNum}</td>
                    <td class="text-left" style="width:18%">&nbsp;</td>
                    <td class="text-right" style="width:32%">&nbsp;</td>
                </tr>
            </c:if>
            <c:if test="${confDetail.idiscountType eq 80}">
                <tr>
                    <td class="text-left"  style="width:18%"><spring:message code="activity.target.count"/>:</td>
                    <td class="text-right"  style="width:32%">${confDetail.ftargetNum}</td>
                    <td class="text-left" style="width:18%">&nbsp;</td>
                    <td class="text-right" style="width:32%">&nbsp;</td>
                </tr>
                <tr>
                    <td class="text-left" style="width:18%"><spring:message code="activity.target.amount"/>:</td>
                    <td class="text-right"  style="width:32%">${confDetail.ftargetMoney}<spring:message code="main.currency"/></td>
                    <td class="text-left"  style="width:18%"><spring:message code="activity.peferential.amount"/>:</td>
                    <td class="text-right"  style="width:32%">${confDetail.fdiscountMoney}<spring:message code="main.currency"/></td>
                </tr>
                <tr>
                    <td class="text-left" style="width:18%"><spring:message code="activity.target.amount"/>:</td>
                    <td class="text-right"  style="width:32%">${confDetail1.ftargetMoney}<spring:message code="main.currency"/></td>
                    <td class="text-left"  style="width:18%"><spring:message code="activity.peferential.amount"/>:</td>
                    <td class="text-right"  style="width:32%">${confDetail1.fdiscountMoney}<spring:message code="main.currency"/></td>
                </tr>
                <tr>
                    <td class="text-left" style="width:18%"><spring:message code="activity.target.amount"/>:</td>
                    <td class="text-right"  style="width:32%">${confDetail2.ftargetMoney}<spring:message code="main.currency"/></td>
                    <td class="text-left"  style="width:18%"><spring:message code="activity.peferential.amount"/>:</td>
                    <td class="text-right"  style="width:32%">${confDetail2.fdiscountMoney}<spring:message code="main.currency"/></td>
                </tr>
            </c:if>

            <c:if test="${conf.idiscountWay eq 50}">
                <tr>
                    <td class="text-left" style="width:18%"><spring:message code="activity.target.amount"/>:</td>
                    <td class="text-right"  style="width:32%">${confDetail.ftargetMoney}<spring:message code="main.currency"/></td>
                    <td class="text-left"  style="width:18%"><spring:message code="activity.cash.return"/></td>
                    <td class="text-right"  style="width:32%">${confDetail.fcashBackMoney}<spring:message code="main.currency"/></td>
                </tr>
            </c:if>
            <tr>
                <td class="text-left" style="width:19%"><spring:message code="activity.all.count"/>:</td>
                <td class="text-right"  style="width:32%">${(conf.iallCount eq 0) ? '<spring:message code="main.unlimited"/>':conf.iallCount}<c:if test="${conf.iallCount>0}"></c:if></td>
                <td class="text-left"  style="width:18%"><spring:message code="activity.all.count.type"/>:</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${conf.icountType eq 10}"><spring:message code="activity.all.count.type.user"/></c:if>
                    <c:if test="${conf.icountType eq 20}"><spring:message code="activity.all.count.type.equipment"/></c:if></td>
                </td>
            </tr>
            <c:if test="${conf.icountType eq 10}">
                <tr>
                    <td class="text-left" style="width:18%"><spring:message code="activity.limit.user.count"/>:</td>
                    <td class="text-right"  style="width:32%">${(conf.iuserAllCount eq 0) ? '<spring:message code="main.unlimited"/>':conf.iuserAllCount}<c:if test="${conf.iuserAllCount>0}"></c:if></td>
                    <td class="text-left"  style="width:19%"><spring:message code="activity.limit.user.oneday.count"/>:</td>
                    <td class="text-right"  style="width:32%">${(conf.iuserDayCount eq 0) ? '<spring:message code="main.unlimited"/>':conf.iuserDayCount}<c:if test="${conf.iuserDayCount>0}"></c:if></td>
                </tr>
            </c:if>
            <c:if test="${conf.icountType eq 20}">
                <tr>
                    <td class="text-left" style="width:18%"><spring:message code="activity.limit.equipment.count"/>:</td>
                    <td class="text-right"  style="width:32%">${conf.iuserAllCount}</td>
                    <td class="text-left"  style="width:18%"><spring:message code="activity.limit.equipment.oneday.count"/>:</td>
                    <td class="text-right"  style="width:32%">${conf.iuserDayCount}</td>
                </tr>
            </c:if>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="activity.start.time"/>:</td>
                <td class="text-right" style="width:32%"><fmt:formatDate value='${conf.tactiveStartTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                <td class="text-left" style="width:18%"><spring:message code="activity.end.time"/>:</td>
                <td class="text-right" style="width:32%"><fmt:formatDate value='${conf.tactiveEndTime}' pattern='yyyy-MM-dd HH:mm:ss'/> </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="activity.description"/>:</td>
                <td class="text-right"  style="width:32%">${conf.sdescription}</td>
                <td class="text-left" style="width:18%">&nbsp;</td>
                <td class="text-right" style="width:32%">&nbsp;</td>
            </tr>
        </table>
        <input type="hidden" id="id" name="id" value="${conf.id}"/>
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

