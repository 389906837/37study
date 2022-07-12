<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备详细信息模板查看</title>
<link href="${staticSource}/resources/layui/css/layui.css?4" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?1" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <div class="layui-form-item wflayui-form-item">
        <table cellspacing="0" border="0">
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="tpmodel.template.number" />：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.scode}</td>
                <td class="text-left" style="width:18%"><spring:message code="tpmodel.template.name" />：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.sname}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="tpmodel.equipment.core.model" />：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.sdeviceModel}</td>
                <td class="text-left" style="width:18%"><spring:message code="tpmodel.core.manufacturer" />：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.smanufacturer}</td>
            </tr>
            <tr>
                <td class="text-left"><spring:message code="tpmodel.core.configuration.description" />：</td>
                <td class="text-right">${deviceModelTemplate.scoreDesc}</td>
                <td class="text-left" style="width:18%"><spring:message code="tpmodel.overall.shape.size" />：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.sdimensions}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="tpmodel.weight" />：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.sweight}</td>
                <td class="text-left" style="width:18%"><spring:message code="tpmodel.rated.power" />：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.sratedPower}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="tpmodel.commodity.capacity" />：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.sproductCapacity}</td>
                <td class="text-left" style="width:18%"><spring:message code="tpmodel.commodity.types" />：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.sproductTypes}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="tpmodel.daily.power.consumption" />：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.sdailyPower}</td>
                <td class="text-left" style="width:18%"><spring:message code="tpmodel.support.payment.method" />：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.spayWap}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="tpmodel.anti-shock.type" />：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${deviceModelTemplate.ielectricShock == 10 }"><spring:message code='tp.class' /></c:if>
                    <c:if test="${deviceModelTemplate.ielectricShock == 20 }"><spring:message code='tp.class.i' /></c:if>
                    <c:if test="${deviceModelTemplate.ielectricShock == 30 }"><spring:message code='tp.class.ii' /></c:if>
                    <c:if test="${deviceModelTemplate.ielectricShock == 40 }"><spring:message code='tp.class.iii' /></c:if>
                </td>
                <td class="text-left" style="width:18%"><spring:message code="tpmodel.electronic.lock.model" />：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.slocksModel}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="tpmodel.manufacturer.of.electronic.locks" />：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.slocksManufacturer}</td>
                <td class="text-left" style="width:18%"><spring:message code="tpmodel.screen.dimension.instructions.for.advertising.machines" />：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.sadDimensions}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="tpmodel.description.of.advertising.machine.configuration" />：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.sadConf}</td>
                <td class="text-left" style="width:18%"><spring:message code="tpmodel.number.of.compressors" />：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.icompressorNum}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="tpmodel.compressor.position" />：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.scompressorPosition}</td>
                <td class="text-left" style="width:18%"><spring:message code="tpmodel.compressor.description" />：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.scompressorDesc}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="tpmodel.number.of.cargo.paths" />：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.icargoRoadNum}</td>
                <td class="text-left" style="width:18%"><spring:message code="tpmodel.number.of.cameras" />：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.icameraNum}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="tpmodel.number.of.motherboard.blocks" />：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.imotherboardNum}</td>
                <td class="text-left" style="width:18%"></td>
                <td class="text-right" style="width:32%"></td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="tpmodel.whether.to.use.extended.gpio" />：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${deviceModelTemplate.iisUseExpandGpio == 0 }"><spring:message code="main.no" /></c:if>
                    <c:if test="${deviceModelTemplate.iisUseExpandGpio == 1 }"><spring:message code="main.yes" /></c:if>
                </td>
                <td class="text-left" style="width:18%"><spring:message code="tpmodel.whether.to.detect.the.hall.value" />：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${deviceModelTemplate.iisDetectHall == 0 }"><spring:message code="main.no" /></c:if>
                    <c:if test="${deviceModelTemplate.iisDetectHall == 1 }"><spring:message code="main.yes" /></c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="tpmodel.camera.method" />：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.scameraMethod}</td>
                <td class="text-left" style="width:18%"><spring:message code="tpmodel.camera.model" />：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.scameraModel}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="tpmodel.camera.type" />：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.scameraType}</td>
                <td class="text-left" style="width:18%"><spring:message code="tpmodel.camera.brand" />：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.scameraBrand}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="tpmodel.visual.service.provider" />：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.svisualServiceProvider}</td>
                <td class="text-left" style="width:18%"><spring:message code="tpmodel.pcb.board.model" />：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.spcbModel}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="tpmodel.open.pin.serial.number" />：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.iopendoorPinSn}</td>
                <td class="text-left" style="width:18%"><spring:message code="tpmodel.hall.pin.serial.number" />：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.ihallPinSn}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="tpmodel.door.pin.serial.number" />：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.idoorPinSn}</td>
                <td class="text-left" style="width:18%"><spring:message code="tpmodel.lock.pin.sequence.number" />：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.ilockCylinder}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="tpmodel.comparison.method" />：</td>
                <td class="text-right" style="width:32%"><c:if test="${deviceModelTemplate.scontrastMode eq 'rawstock' }"><spring:message code="tpmodel.original.stock" /></c:if>
                    <c:if test="${deviceModelTemplate.scontrastMode eq 'openDoor' }"><spring:message code="tpmodel.switch.door" /></c:if></td>
                <td class="text-left" style="width:18%"></td>
                <td class="text-right" style="width:32%"></td>
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


