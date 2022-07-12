<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备信息查看</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <div class="layui-form-item wflayui-form-item">
        <table cellspacing="0" border="0">
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='sb.number.of.shipments.per.shipment' />：</td>
                <td class="text-right" style="width:32%">${monitorDataConfDomain.iinventoryNum}</td>
                <td class="text-left" style="width:18%"><spring:message code='sb.last.shipment.time' />：</td>
                <td class="text-right" style="width:32%"><fmt:formatDate value="${monitorDataConfDomain.tinventoryTime}"
                                                                         pattern="yyyy-MM-dd HH:mm:ss"/></td>
            </tr>
            <tr>
                <td class="text-left"><spring:message code='sb.automatic.stock.start.time' />：</td>
                <td class="text-right">${monitorDataConfDomain.tinventoryBeginTime}</td>
                <td class="text-left" style="width:18%"><spring:message code='sb.automatic.stock.end.time' />：</td>
                <td class="text-right" style="width:32%">${monitorDataConfDomain.tinventoryEndTime}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='sb.real.time.temperature' />1：</td>
                <td class="text-right" style="width:32%">${monitorDataConfDomain.sltemperature}</td>
                <td class="text-left" style="width:18%"><spring:message code='sb.real.time.temperature' />2：</td>
                <td class="text-right" style="width:32%">${monitorDataConfDomain.srtemperature}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='sb.temperature.control' />1：</td>
                <td class="text-right" style="width:32%">${monitorDataConfDomain.slcontrolTemperature}</td>
                <td class="text-left" style="width:18%"><spring:message code='sb.temperature.control' />2：</td>
                <td class="text-right" style="width:32%">${monitorDataConfDomain.srcontrolTemperature}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='sb.temperature.control.start.time' />1：</td>
                <td class="text-right" style="width:32%">${monitorDataConfDomain.tlcontrolBeginTime}</td>
                <td class="text-left" style="width:18%"><spring:message code='sb.temperature.control.end.time' />1：</td>
                <td class="text-right" style="width:32%">${monitorDataConfDomain.tlcontrolEndTime}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='sb.temperature.control.start.time' />2：</td>
                <td class="text-right" style="width:32%">${monitorDataConfDomain.trcontrolBeginTime}</td>
                <td class="text-left" style="width:18%"><spring:message code='sb.temperature.control.end.time' />2：</td>
                <td class="text-right" style="width:32%">${monitorDataConfDomain.trcontrolEndTime}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='sb.time.switch.state' />：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${monitorDataConfDomain.iswitchStatus == 0 }"><spring:message code="main.no" /></c:if>
                    <c:if test="${monitorDataConfDomain.iswitchStatus == 1 }"><spring:message code="main.yes" /></c:if>
                </td>
                <td class="text-left" style="width:18%"><spring:message code='sb.real.time.volume' />：</td>
                <td class="text-right" style="width:32%">${monitorDataConfDomain.iactualVolume}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='sb.boot.time' />：</td>
                <td class="text-right" style="width:32%">${monitorDataConfDomain.tbootTime}</td>
                <td class="text-left" style="width:18%"><spring:message code='sb.shutdown.time' />：</td>
                <td class="text-right" style="width:32%">${monitorDataConfDomain.tshutDownTime}</td>
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

