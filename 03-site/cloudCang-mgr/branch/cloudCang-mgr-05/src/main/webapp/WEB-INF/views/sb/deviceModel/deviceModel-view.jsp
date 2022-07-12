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
        <table  cellspacing="0" border="0">
                <tr>
                    <td class="text-left" style="width:18%"><spring:message code="sb.equipment.core.model" />：</td>
                    <td class="text-right" style="width:32%">${deviceModel.sdeviceModel}</td>
                    <td class="text-left" style="width:18%"><spring:message code='tpmodel.core.manufacturer' />：</td>
                    <td class="text-right" style="width:32%">${deviceModel.smanufacturer}</td>
                </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='tpmodel.core.configuration.description' />：</td>
                <td class="text-right"  style="width:32%">${deviceModel.scoreDesc}</td>
                <td class="text-left" style="width:18%"><spring:message code='tpmodel.overall.shape.size' />：</td>
                <td class="text-right"  style="width:32%">${deviceModel.sdimensions}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code='tpmodel.weight' />：</td>
                <td class="text-right"  style="width:32%">${deviceModel.sweight}</td>
                <td class="text-left"  style="width:18%"><spring:message code='tpmodel.rated.power' />：</td>
                <td class="text-right"  style="width:32%">${deviceModel.sratedPower}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code='tpmodel.commodity.capacity' />：</td>
                <td class="text-right"  style="width:32%">${deviceModel.sproductCapacity}</td>
                <td class="text-left"  style="width:18%"><spring:message code='tpmodel.commodity.types' />：</td>
                <td class="text-right"  style="width:32%">${deviceModel.sproductTypes}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code='tpmodel.daily.power.consumption' />：</td>
                <td class="text-right"  style="width:32%">${deviceModel.sdailyPower}</td>
                <td class="text-left"  style="width:18%"><spring:message code='sl.payment.method' />：</td>
                <td class="text-right"  style="width:32%">${deviceModel.spayWap}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='tpmodel.anti-shock.type' />：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${deviceModel.ielectricShock == 10 }">0<spring:message code='sb.class' /></c:if>
                    <c:if test="${deviceModel.ielectricShock == 20 }">I<spring:message code='sb.class' /></c:if>
                    <c:if test="${deviceModel.ielectricShock == 30 }">II<spring:message code='sb.class' /></c:if>
                    <c:if test="${deviceModel.ielectricShock == 40 }">III<spring:message code='sb.class' /></c:if>
                </td>
                <td class="text-left" style="width:18%"><spring:message code='tpmodel.electronic.lock.model' />：</td>
                <td class="text-right" style="width:32%">${deviceModel.slocksModel}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='tpmodel.manufacturer.of.electronic.locks' />：</td>
                <td class="text-right" style="width:32%">${deviceModel.slocksManufacturer}</td>
                <td class="text-left" style="width:18%"><spring:message code='tpmodel.screen.dimension.instructions.for.advertising.machines' />：</td>
                <td class="text-right" style="width:32%">${deviceModel.sadDimensions}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='tpmodel.description.of.advertising.machine.configuration' />：</td>
                <td class="text-right" style="width:32%">${deviceModel.sadConf}</td>
                <td class="text-left" style="width:18%"><spring:message code='tpmodel.number.of.compressors' />：</td>
                <td class="text-right" style="width:32%">${deviceModel.icompressorNum}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='tpmodel.compressor.position' />：</td>
                <td class="text-right" style="width:32%">${deviceModel.scompressorPosition}</td>
                <td class="text-left" style="width:18%"><spring:message code='tpmodel.compressor.description' />：</td>
                <td class="text-right" style="width:32%">${deviceModel.scompressorDesc}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='tpmodel.number.of.cargo.paths' />：</td>
                <td class="text-right" style="width:32%">${deviceModel.icargoRoadNum}</td>
                <td class="text-left" style="width:18%"><spring:message code='tpmodel.number.of.cameras' />：</td>
                <td class="text-right" style="width:32%">${deviceModel.icameraNum}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='tpmodel.number.of.cargo.paths' />：</td>
                <td class="text-right" style="width:32%">${deviceModel.icargoRoadNum}</td>
                <td class="text-left" style="width:18%"><spring:message code='tpmodel.number.of.motherboard.blocks' />：</td>
                <td class="text-right" style="width:32%">${deviceModel.imotherboardNum}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='tpmodel.whether.to.use.extended.gpio' />：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${deviceModel.iisUseExpandGpio == 0 }"><spring:message code="main.no" /></c:if>
                    <c:if test="${deviceModel.iisUseExpandGpio == 1 }"><spring:message code="main.yes" /></c:if>
                </td>
                <td class="text-left" style="width:18%"><spring:message code='tpmodel.whether.to.detect.the.hall.value' />：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${deviceModel.iisDetectHall == 0 }"><spring:message code="main.no" /></c:if>
                    <c:if test="${deviceModel.iisDetectHall == 1 }"><spring:message code="main.yes" /></c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='tpmodel.camera.method' />：</td>
                <td class="text-right" style="width:32%">${deviceModel.scameraMethod}</td>
                <td class="text-left" style="width:18%"><spring:message code='tpmodel.camera.model' />：</td>
                <td class="text-right" style="width:32%">${deviceModel.scameraModel}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='tpmodel.camera.type' />：</td>
                <td class="text-right" style="width:32%">${deviceModel.scameraType}</td>
                <td class="text-left" style="width:18%"><spring:message code='tpmodel.camera.brand' />：</td>
                <td class="text-right" style="width:32%">${deviceModel.scameraBrand}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='tpmodel.visual.service.provider' />：</td>
                <td class="text-right" style="width:32%">${deviceModel.svisualServiceProvider}</td>
                <td class="text-left" style="width:18%"><spring:message code='tpmodel.pcb.board.model' />：</td>
                <td class="text-right" style="width:32%">${deviceModel.spcbModel}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='tpmodel.open.pin.serial.number' />：</td>
                <td class="text-right" style="width:32%">${deviceModel.iopendoorPinSn}</td>
                <td class="text-left" style="width:18%"><spring:message code='tpmodel.hall.pin.serial.number' />：</td>
                <td class="text-right" style="width:32%">${deviceModel.ihallPinSn}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='tpmodel.door.pin.serial.number' />：</td>
                <td class="text-right" style="width:32%">${deviceModel.idoorPinSn}</td>
                <td class="text-left" style="width:18%"><spring:message code='tpmodel.lock.pin.sequence.number' />：</td>
                <td class="text-right" style="width:32%">${deviceModel.ilockCylinder}</td>
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

