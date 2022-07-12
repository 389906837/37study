<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备基础信息模板查看</title>
<link href="${staticSource}/resources/layui/css/layui.css?4" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?1" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <div class="layui-form-item wflayui-form-item">
        <table cellspacing="0" border="0">
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="tpinfotoadd.template.name" />：</td>
                <td class="text-right" style="width:32%">${deviceInfoTemplate.sname}</td>
                <td class="text-left" style="width:18%"><spring:message code="tpinfo.template.number" />：</td>
                <td class="text-right" style="width:32%">${deviceInfoTemplate.scode}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="tpinfotoadd.equipment.type" />：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${deviceInfoTemplate.itype == 10 }"><spring:message code="tpinfo.traditional" /></c:if>
                    <c:if test="${deviceInfoTemplate.itype == 20 }"><spring:message code="tpinfo.rfid.radio.frequency" /></c:if>
                    <c:if test="${deviceInfoTemplate.itype == 30 }"><spring:message code="tpinfo.vision" /></c:if>
                    <c:if test="${deviceInfoTemplate.itype == 40 }"><spring:message code="tpinfo.front-end.vision.gravity" /></c:if>
                    <c:if test="${deviceInfoTemplate.itype == 50 }"><spring:message code="tpinfo.cloud.recognition" /></c:if>
                    <c:if test="${deviceInfoTemplate.itype == 60 }"><spring:message code="tpinfo.cloud.recognition.gravity" /></c:if>
                </td>
                <td class="text-left" style="width:18%"><spring:message code="tpinfotoadd.cooperation.mode" />：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${deviceInfoTemplate.icooperationMode == 10 }"><spring:message code="tpinfo.purchase" /></c:if>
                    <c:if test="${deviceInfoTemplate.icooperationMode == 20 }"><spring:message code="tpinfo.rent" /></c:if>
                    <c:if test="${deviceInfoTemplate.icooperationMode == 30 }"><spring:message code="tpinfo.independence" /></c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="tpinfotoadd.reader.serial.number" />：</td>
                <td class="text-right" style="width:32%">${deviceInfoTemplate.sreaderSerialNumber}</td>
                <td class="text-left" style="width:18%"><spring:message code="tpinfotoadd.equipment.model" />：</td>
                <td class="text-right" style="width:32%">${deviceInfoTemplate.sdeviceModel}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="tpinfotoadd.container.type" />：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${deviceInfoTemplate.icontainerType == 10 }"><spring:message code="tpinfotoadd.single.door" /></c:if>
                    <c:if test="${deviceInfoTemplate.icontainerType == 20 }"><spring:message code="tpinfotoadd.double.door" /></c:if>
                </td>
                <td class="text-left"><spring:message code="tpinfotoadd.screen.display.type" />：</td>
                <td class="text-right">
                    <c:if test="${deviceInfoTemplate.iscreenDisplayType == 10 }"><spring:message code="tpinfo.horizontal.screen" /></c:if>
                    <c:if test="${deviceInfoTemplate.iscreenDisplayType == 20 }"><spring:message code="tpinfo.vertical.screen" /></c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="tpinfotoadd.compressor.type" />：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${deviceInfoTemplate.icompressorType == 10 }"><spring:message code="tpinfotoadd.refrigeration" /></c:if>
                    <c:if test="${deviceInfoTemplate.icompressorType == 20 }"><spring:message code="tpinfotoadd.heating" /></c:if>
                    <c:if test="${deviceInfoTemplate.icompressorType == 30 }"><spring:message code="tpinfotoadd.cooling.heat" /></c:if>
                    <c:if test="${deviceInfoTemplate.icompressorType == 40 }"><spring:message code="tpinfotoadd.no.compressor" /></c:if>
                </td>
                <td class="text-left" style="width:18%"><spring:message code="tpinfotoadd.whether.advertising.machine" />：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${deviceInfoTemplate.iinstallAd == 0 }"><spring:message code="main.no" /></c:if>
                    <c:if test="${deviceInfoTemplate.iinstallAd == 1 }"><spring:message code="main.yes" /></c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="tpinfo.delivery.scenario" />：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${deviceInfoTemplate.sputScenes == 10 }"><spring:message code="tpinfo.park" /></c:if>
                    <c:if test="${deviceInfoTemplate.sputScenes == 20 }"><spring:message code="tpinfo.school" /></c:if>
                    <c:if test="${deviceInfoTemplate.sputScenes == 30 }"><spring:message code="tpinfo.hospital" /></c:if>
                </td>
                <td class="text-left" style="width:18%"><spring:message code="tpinfo.whether.to.support.ai.devices" />：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${deviceInfoTemplate.isupportAi == 0 }"><spring:message code="main.no" /></c:if>
                    <c:if test="${deviceInfoTemplate.isupportAi == 1 }"><spring:message code="main.yes" /></c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="tpinfotoadd.whether.to.support.weighing" />：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${deviceInfoTemplate.isupportWeigh == 0 }"><spring:message code="main.no" /></c:if>
                    <c:if test="${deviceInfoTemplate.isupportWeigh == 1 }"><spring:message code="main.yes" /></c:if>
                </td>
                <td class="text-left" style="width:18%"><spring:message code="tpinfo.electronic.scale.floating.value" />：</td>
                <td class="text-right" style="width:32%">${deviceInfoTemplate.ielectronicFloat} </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="tpinfotoadd.system.docking.type" />：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${deviceInfoTemplate.idockingType == 10 }"><spring:message code="tpinfotoadd.independent.research.and.development" /></c:if>
                    <c:if test="${deviceInfoTemplate.idockingType == 20 }"><spring:message code="tpinfo.docking.third.parties" /></c:if>
                </td>
                <td class="text-left" style="width:18%"><spring:message code="tpinfo.whether.to.open.real-time.inventory" />：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${deviceInfoTemplate.iisOpeningInventory == 0 }"><spring:message code="main.no" /></c:if>
                    <c:if test="${deviceInfoTemplate.iisOpeningInventory == 1 }"><spring:message code="main.yes" /></c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="sm.remarks" />：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${deviceInfoTemplate.isupportWeigh == 0 }">False</c:if>
                    <c:if test="${deviceInfoTemplate.isupportWeigh == 1 }">True</c:if>
                </td>
                <td class="text-left" style="width:18%">electronic scale floating value：</td>
                <td class="text-right" style="width:32%">${deviceInfoTemplate.ielectronicFloat} </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">weighing type：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${deviceInfoTemplate.iweightType == 10 }">232</c:if>
                    <c:if test="${deviceInfoTemplate.iweightType == 20 }">485</c:if>
                </td>
                <td class="text-left" style="width:18%">remark：</td>
                <td class="text-right" style="width:32%">${deviceInfoTemplate.sremark} </td>
            </tr>
        </table>
    </div>
</div>
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    $(".wflayui-form-item tr:even").addClass("odd");
</script>
</body>
</html>


