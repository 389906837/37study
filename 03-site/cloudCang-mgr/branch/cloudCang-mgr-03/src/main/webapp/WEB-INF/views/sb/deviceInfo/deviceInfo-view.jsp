<%--
  Created by IntelliJ IDEA.
  User: Alex
  Date: 2018/2/5
  Time: 16:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备信息查看</title>
<link href="${staticSource}/resources/layui/css/layui.css?4" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?1" rel="stylesheet">

</head>

<body>
<div class="ibox-content noibox-content">
    <form class="layui-form" action="#" method="" id="myForm">
        <div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
            <ul class="layui-tab-title">
                <li class="layui-this"><spring:message code="menu.device.basic.info" /></li>
                <li><spring:message code='sb.equipment.registration.information' /></li>
                <li><spring:message code='sb.equipment.management.information' /></li>
                <li><spring:message code='sb.equipment.details' /></li>
                <li><spring:message code='sb.equipment.monitoring.information' /></li>
            </ul>
            <div class="layui-tab-content">
                <div class="layui-tab-item layui-show">
                    <div class="layui-form-item wflayui-form-item">
                        <table cellspacing="0" border="0">
                            <tr>
                                <td class="text-left" style="width:18%"><spring:message code="main.merchant.number" />：</td>
                                <td class="text-right" style="width:32%">${deviceInfo.smerchantCode}</td>
                                <td class="text-left" style="width:18%"><spring:message code="main.merchant.name" />：</td>
                                <td class="text-right" style="width:32%">${merchantInfo.sname}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%"><spring:message code="main.device.id" />：</td>
                                <td class="text-right" style="width:32%">${deviceInfo.scode}</td>
                                <td class="text-left"><spring:message code="main.device.name" />：</td>
                                <td class="text-right">${deviceInfo.sname}</td>
                            </tr>
                            <tr>
                                <td class="text-left"><spring:message code='sb.equipment.type'/>：</td>
                                <td class="text-right">
                                    <c:if test="${deviceInfo.itype == 10 }"><spring:message code='sb.traditional' /></c:if>
                                    <c:if test="${deviceInfo.itype == 20 }"><spring:message code='sb.rfid.radio.frequency' /></c:if>
                                    <c:if test="${deviceInfo.itype == 30 }"><spring:message code='sb.vision' /></c:if>
                                    <c:if test="${deviceInfo.itype == 40 }"><spring:message code='sb.front.end.vision.gravity' /></c:if>
                                    <c:if test="${deviceInfo.itype == 50 }"><spring:message code='sb.cloud.recognition' /></c:if>
                                    <c:if test="${deviceInfo.itype == 60 }"><spring:message code='sb.cloud.recognition.gravity' /></c:if>
                                </td>
                                <td class="text-left"><spring:message code='sb.reader.serial.number' />：</td>
                                <td class="text-right">${deviceInfo.sreaderSerialNumber}</td>
                            </tr>
                            <tr>
                                <td class="text-left"><spring:message code="tpinfotoadd.equipment.model" />：</td>
                                <td class="text-right">${deviceInfo.sdeviceModel}</td>
                                <td class="text-left"><spring:message code="sb.equipment.status" />：</td>
                                <td class="text-right">
                                    <c:if test="${deviceInfo.istatus == 10 }"><spring:message code='sb.unregistered'/></c:if>
                                    <c:if test="${deviceInfo.istatus == 20 }"><spring:message code="main.normal" /></c:if>
                                    <c:if test="${deviceInfo.istatus == 30 }"><spring:message code='sb.fault' /></c:if>
                                    <c:if test="${deviceInfo.istatus == 40 }"><spring:message code='sb.scrap' /></c:if>
                                    <c:if test="${deviceInfo.istatus == 50 }"><spring:message code='sb.offline' /></c:if>
                                </td>
                            </tr>
                            <tr>
                                <td class="text-left"><spring:message code='sb.device.grouping' />：</td>
                                <td class="text-right">
                                    ${groupManage.sgroupName}
                                </td>
                                <td class="text-left"><spring:message code='sb.operation.state' />：</td>
                                <td class="text-right">
                                    <c:if test="${deviceInfo.ioperateStatus == 10 }"><spring:message code="main.enable" /></c:if>
                                    <c:if test="${deviceInfo.ioperateStatus == 20 }"><spring:message code='sb.discontinue.use' /></c:if>
                                </td>
                            </tr>
                            <tr>
                                <td class="text-left"><spring:message code='sb.cooperation.mode' />：</td>
                                <td class="text-right">
                                    <c:if test="${deviceInfo.icooperationMode == 10 }"><spring:message code='sb.purchase' /></c:if>
                                    <c:if test="${deviceInfo.icooperationMode == 20 }"><spring:message code='sb.rent' /></c:if>
                                    <c:if test="${deviceInfo.icooperationMode == 30 }"><spring:message code='sb.autonomy' /></c:if>
                                </td>
                                <td class="text-left"><spring:message code='sb.whether.ai.devices.are.supported.or.not' />：</td>
                                <td class="text-right">
                                    <c:if test="${deviceInfo.isupportAi == 0 }"><spring:message code="main.no" /></c:if>
                                    <c:if test="${deviceInfo.isupportAi == 1 }"><spring:message code="main.yes" /></c:if>
                                </td>
                            </tr>
                            <tr>
                                <td class="text-left"><spring:message code='sb.put.in.the.detailed.address' />：</td>
                                <td class="text-right">${deviceInfo.sprovinceName}${deviceInfo.scityName}${deviceInfo.sareaName}${deviceInfo.sputAddress}</td>
                                <td class="text-left"><spring:message code='sb.container.type' />：</td>
                                <td class="text-right">
                                    <c:if test="${deviceInfo.icontainerType == 10 }"><spring:message code='sb.single.door' /></c:if>
                                    <c:if test="${deviceInfo.icontainerType == 20 }"><spring:message code='sb.double.door' /></c:if>
                                </td>
                            </tr>
                            <tr>
                                <td class="text-left"><spring:message code='sb.compressor.type' />：</td>
                                <td class="text-right">
                                    <c:if test="${deviceInfo.icompressorType == 10 }"><spring:message code='sb.refrigeration' /></c:if>
                                    <c:if test="${deviceInfo.icompressorType == 20 }"><spring:message code='sb.heating' /></c:if>
                                    <c:if test="${deviceInfo.icompressorType == 30 }"><spring:message code='sb.cooling.heat' /></c:if>
                                    <c:if test="${deviceInfo.icompressorType == 40 }"><spring:message code='sb.no.compressor' /></c:if>
                                </td>
                                <td class="text-left"><spring:message code='sb.whether.advertising.machine' />：</td>
                                <td class="text-right">
                                    <c:if test="${deviceInfo.iinstallAd == 0 }"><spring:message code="main.no" /></c:if>
                                    <c:if test="${deviceInfo.iinstallAd == 1 }"><spring:message code="main.yes" /></c:if>
                                </td>
                            </tr>
                            <tr>
                                <td class="text-left"><spring:message code='sb.do.you.support.weighing' />：</td>
                                <td class="text-right">
                                    <c:if test="${deviceInfo.isupportWeigh == 0 }"><spring:message code="main.no" /></c:if>
                                    <c:if test="${deviceInfo.isupportWeigh == 1 }"><spring:message code="main.yes" /></c:if>
                                </td>
                                <td class="text-left">485 Address：</td>
                                <td class="text-right">${deviceInfo.s485Address}
                                </td>
                            </tr>
                            <tr>
                                <td class="text-left">Weighing type：</td>
                                <td class="text-right">${deviceInfo.iweightType}</td>
                                <td class="text-left"><spring:message code='sb.eloating.value.of.electronic.scale' />：</td>
                                <td class="text-right">${deviceInfo.ielectronicFloat} </td>
                            </tr>
                             <tr>
                                <td class="text-left"><spring:message code='sb.whether.to.open.realtime.inventory' />：</td>
                                <td class="text-right">
                                    <c:if test="${deviceInfo.iisOpeningInventory == 0 }"><spring:message code="main.no" /></c:if>
                                    <c:if test="${deviceInfo.iisOpeningInventory == 1 }"><spring:message code="main.yes" /></c:if>
                                </td>
                                <td class="text-left"><spring:message code='sb.system.docking.type' />：</td>
                                <td class="text-right">
                                    <c:if test="${deviceInfo.idockingType == 10 }"><spring:message code='sb.independent.research.and.development' /></c:if>
                                    <c:if test="${deviceInfo.idockingType == 20 }"><spring:message code='sb.docking' /><spring:message code='sb.third.party' /></c:if>
                                </td>
                            </tr>
                            <tr>
                                <td class="text-left"><spring:message code='sb.screen.display.type'/>：</td>
                                <td class="text-right">
                                    <c:if test="${deviceInfo.iscreenDisplayType == 10 }"><spring:message code='sb.horizontal.screen' /></c:if>
                                    <c:if test="${deviceInfo.iscreenDisplayType == 20 }"><spring:message code='sb.vertical.screen' /></c:if>
                                </td>
                            </tr>
                            <tr>
                                <td class="text-left"><spring:message code='sb.number.of.layers' />:</td>
                                <td class="text-right">${deviceModel.ilayerNum}</td>
                                <td class="text-left"><spring:message code="main.remarks" />：</td>
                                <td class="text-right">${deviceInfo.sremark} </td>
                                <td class="text-left"></td>
                                <td class="text-right"></td>
                            </tr>
                        </table>
                    </div>
                </div>
                <script type="text/javascript">
                    $(".wflayui-form-item tr:even").addClass("odd");
                </script>
                <div class="layui-tab-item">
                    <div class="layui-form-item wflayui-form-item">
                        <table cellspacing="0" border="0">
                            <tr>
                                <td class="text-left" style="width:18%"><spring:message code='sb.registered.ip' />：</td>
                                <td class="text-right" style="width:32%">${deviceRegister.sregIp}</td>
                                <td class="text-left"><spring:message code='sb.security.key' />：</td>
                                <td class="text-right">${deviceRegister.ssecurityKey}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%"><spring:message code='sb.registration.status' />：</td>
                                <td class="text-right" style="width:32%">
                                    <c:if test="${deviceRegister.istatus == 10 }"><spring:message code="main.audited" /></c:if>
                                    <c:if test="${deviceRegister.istatus == 20 }"><spring:message code="main.approval" /></c:if>
                                    <c:if test="${deviceRegister.istatus == 30 }"><spring:message code="main.rejection" /></c:if>
                                    <c:if test="${deviceRegister.istatus == 40 }"><spring:message code="main.registered" /></c:if>
                                </td>
                                <td class="text-left"><spring:message code="main.reviewer" />：</td>
                                <td class="text-right">${deviceRegister.sauditUser}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%"><spring:message code="main.remarks" />：</td>
                                <td class="text-right" style="width:32%">${deviceRegister.sremark}</td>
                                <td class="text-left" style="width:18%">&nbsp;</td>
                                <td class="text-right" style="width:32%">&nbsp;</td>
                            </tr>
                        </table>
                    </div>
                </div>
                <script type="text/javascript">
                    $(".wflayui-form-item tr:even").addClass("odd");
                </script>

                <div class="layui-tab-item">
                    <div class="layui-form-item wflayui-form-item">
                        <table cellspacing="0" border="0">
                            <tr>
                                <td class="text-left" style="width:18%"><spring:message code='sb.area.number' />：</td>
                                <td class="text-right" style="width:32%">${deviceManage.sareaCode}</td>
                                <td class="text-left" style="width:18%">&nbsp;</td>
                                <td class="text-right" style="width:32%">&nbsp;</td>
                            </tr>
                            <tr>
                                <td class="text-left"><spring:message code='sb.regional.director'/>：</td>
                                <td class="text-right">${deviceManage.sareaPrincipal}</td>
                                <td class="text-left" style="width:18%"><spring:message code='sb.contact.information.of.regional.leaders' />：</td>
                                <td class="text-right" style="width:32%">${deviceManage.sareaPrincipalContact}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%"><spring:message code='sb.equipment.person.in.charge'/>：</td>
                                <td class="text-right" style="width:32%">${deviceManage.sdevicePrincipal}</td>
                                <td class="text-left" style="width:18%"><spring:message code='sb.contact.form.of.equipment.leader' />：</td>
                                <td class="text-right" style="width:32%">${deviceManage.sdevicePrincipalContact}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%"><spring:message code='sb.name.of.replenisher' />：</td>
                                <td class="text-right" style="width:32%">${deviceManage.sreplenishment}</td>
                                <td class="text-left" style="width:18%"><spring:message code='sb.contact.information.of.replenisher' />：</td>
                                <td class="text-right" style="width:32%">${deviceManage.sreplenishmentContact}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%"><spring:message code='sb.name.of.equipment.maintenance.person' />：</td>
                                <td class="text-right" style="width:32%">${deviceManage.smaintain}</td>
                                <td class="text-left" style="width:18%"><spring:message code='sb.maintenance.contact.information' />：</td>
                                <td class="text-right" style="width:32%">${deviceManage.smaintainContact}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%"><spring:message code="main.remarks" />：</td>
                                <td class="text-right" style="width:32%">${deviceManage.sremark}</td>
                                <td class="text-left" style="width:18%">&nbsp;</td>
                                <td class="text-right" style="width:32%">&nbsp;</td>
                            </tr>
                        </table>
                    </div>
                </div>
                <script type="text/javascript">
                    $(".wflayui-form-item tr:even").addClass("odd");
                </script>

                <div class="layui-tab-item">
                    <div class="layui-form-item wflayui-form-item">
                        <table cellspacing="0" border="0">
                            <tr>
                                <td class="text-left" style="width:18%"><spring:message code="sb.equipment.core.model" />：</td>
                                <td class="text-right" style="width:32%">${deviceModel.sdeviceModel}</td>
                                <td class="text-left" style="width:18%"><spring:message code='sb.core.manufacturers' />：</td>
                                <td class="text-right" style="width:32%">${deviceModel.smanufacturer}</td>
                            </tr>
                            <tr>
                                <td class="text-left"><spring:message code='sb.core.configuration.description' />：</td>
                                <td class="text-right">${deviceModel.scoreDesc}</td>
                                <td class="text-left" style="width:18%"><spring:message code='sb.overall.shape.size' />：</td>
                                <td class="text-right" style="width:32%">${deviceModel.sdimensions}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%"><spring:message code='sb.weight' />：</td>
                                <td class="text-right" style="width:32%">${deviceModel.sweight}</td>
                                <td class="text-left" style="width:18%"><spring:message code='sb.rated.power' />：</td>
                                <td class="text-right" style="width:32%">${deviceModel.sratedPower}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%"><spring:message code='sb.product.capacity' />：</td>
                                <td class="text-right" style="width:32%">${deviceModel.sproductCapacity}</td>
                                <td class="text-left" style="width:18%"><spring:message code='sb.product.types' />：</td>
                                <td class="text-right" style="width:32%">${deviceModel.sproductTypes}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%"><spring:message code='sb.daily.power.consumption' />：</td>
                                <td class="text-right" style="width:32%">${deviceModel.sdailyPower}</td>
                                <td class="text-left" style="width:18%"><spring:message code='sb.support.payment.method' />：</td>
                                <td class="text-right" style="width:32%">${deviceModel.spayWap}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%"><spring:message code='sb.Aanti.shock.type' />：</td>
                                <td class="text-right" style="width:32%">
                                    <c:if test="${deviceModel.ielectricShock == 10 }">0<spring:message code='sb.Class' /></c:if>
                                    <c:if test="${deviceModel.ielectricShock == 20 }">I<spring:message code='sb.Class' /></c:if>
                                    <c:if test="${deviceModel.ielectricShock == 30 }">II<spring:message code='sb.Class' /></c:if>
                                    <c:if test="${deviceModel.ielectricShock == 40 }">III<spring:message code='sb.Class' /></c:if>
                                </td>
                                <td class="text-left" style="width:18%"><spring:message code='sb.electronic.lock.model' />：</td>
                                <td class="text-right" style="width:32%">${deviceModel.slocksModel}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%"><spring:message code='sb.manufacturer.of.electronic.locksl' />：</td>
                                <td class="text-right" style="width:32%">${deviceModel.slocksManufacturer}</td>
                                <td class="text-left" style="width:18%"><spring:message code='sb.screen.dimension.instructions.for.advertising.machines' />：</td>
                                <td class="text-right" style="width:32%">${deviceModel.sadDimensions}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%"><spring:message code='sb.description.of.advertising.machine.configuration' />：</td>
                                <td class="text-right" style="width:32%">${deviceModel.sadConf}</td>
                                <td class="text-left" style="width:18%"><spring:message code='sb.number.of.compressors' />：</td>
                                <td class="text-right" style="width:32%">${deviceModel.icompressorNum}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%"><spring:message code='sb.compressor.position' />：</td>
                                <td class="text-right" style="width:32%">${deviceModel.scompressorPosition}</td>
                                <td class="text-left" style="width:18%"><spring:message code='sb.compressor.description' />：</td>
                                <td class="text-right" style="width:32%">${deviceModel.scompressorDesc}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%"><spring:message code='sb.number.of.goods' />：</td>
                                <td class="text-right" style="width:32%">${deviceModel.icargoRoadNum}</td>
                                <td class="text-left" style="width:18%"><spring:message code='sb.number.of.cameras' />：</td>
                                <td class="text-right" style="width:32%">${deviceModel.icameraNum}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%"><spring:message code='sb.number.of.motherboard.blocks' />：</td>
                                <td class="text-right" style="width:32%">${deviceModel.imotherboardNum}</td>
                                <td class="text-left" style="width:18%"></td>
                                <td class="text-right" style="width:32%"></td>
                            </tr>
                           <%-- <tr>
                                <td class="text-left" style="width:18%"><spring:message code='sb.whether.to.use.extended.gpto' />：</td>
                                <td class="text-right" style="width:32%">
                                    <c:if test="${deviceModel.iisUseExpandGpio == 0 }"><spring:message code="main.no" /></c:if>
                                    <c:if test="${deviceModel.iisUseExpandGpio == 1 }"><spring:message code="main.yes" /></c:if>
                                </td>
                                <td class="text-left" style="width:18%"><spring:message code='sb.whether.to.detect.hall.value' />：</td>
                                <td class="text-right" style="width:32%">
                                    <c:if test="${deviceModel.iisDetectHall == 0 }"><spring:message code="main.no" /></c:if>
                                    <c:if test="${deviceModel.iisDetectHall == 1 }"><spring:message code="main.yes" /></c:if>
                                </td>
                            </tr>--%>
                            <tr>
                                <td class="text-left" style="width:18%"><spring:message code='sb.camera.method'/>：</td>
                                <td class="text-right" style="width:32%">${deviceModel.scameraMethod}</td>
                                <td class="text-left" style="width:18%"><spring:message code='sb.camera.model' />：</td>
                                <td class="text-right" style="width:32%">${deviceModel.scameraModel}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%"><spring:message code='sb.camera.model' />：</td>
                                <td class="text-right" style="width:32%">${deviceModel.scameraType}</td>
                                <td class="text-left" style="width:18%"><spring:message code='sb.camera.brand' />：</td>
                                <td class="text-right" style="width:32%">${deviceModel.scameraBrand}</td>
                            </tr>
                           <%-- <tr>
                                <td class="text-left" style="width:18%"><spring:message code='sb.visual.service.provider' />：</td>
                                <td class="text-right" style="width:32%">${deviceModel.svisualServiceProvider}</td>
                                <td class="text-left" style="width:18%"><spring:message code='sb.pcb.board.model' />：</td>
                                <td class="text-right" style="width:32%">${deviceModel.spcbModel}</td>
                            </tr>--%>
                            <tr>
                                <td class="text-left" style="width:18%"><spring:message code='sb.visual.service.provider' />：</td>
                                <td class="text-right" style="width:32%">${deviceModel.svisualServiceProvider}</td>
                                <td class="text-left" style="width:18%"><spring:message code='sb.whether.to.detect.hall.value' />：</td>
                                <td class="text-right" style="width:32%">
                                    <c:if test="${deviceModel.iisDetectHall == 0 }"><spring:message code="main.no" /></c:if>
                                    <c:if test="${deviceModel.iisDetectHall == 1 }"><spring:message code="main.yes" /></c:if>
                                </td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%"><spring:message code='sb.open.pin.serial.number' />：</td>
                                <td class="text-right" style="width:32%">${deviceModel.iopendoorPinSn}</td>
                                <td class="text-left" style="width:18%"><spring:message code='sb.hall.pin.serial.number' />：</td>
                                <td class="text-right" style="width:32%">${deviceModel.ihallPinSn}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%"><spring:message code='sb.door.pin.serial.number' />：</td>
                                <td class="text-right" style="width:32%">${deviceModel.idoorPinSn}</td>
                                <td class="text-left" style="width:18%"><spring:message code='sb.cylinder.pin.number' />：</td>
                                <td class="text-right" style="width:32%">${deviceModel.ilockCylinder}</td>
                            </tr>

                        </table>
                    </div>
                </div>
                <script type="text/javascript">
                    $(".wflayui-form-item tr:even").addClass("odd");
                </script>

                <div class="layui-tab-item">
                    <div class="layui-form-item wflayui-form-item">
                        <table cellspacing="0" border="0">
                            <tr>
                                <td class="text-left" style="width:18%"><spring:message code='sb.number.of.inventories.per.time' />：</td>
                                <td class="text-right" style="width:32%">${monitorDataConf.iinventoryNum}</td>
                                <td class="text-left" style="width:18%"><spring:message code='sb.last.inventory.time' />：</td>
                                <td class="text-right" style="width:32%"><fmt:formatDate value="${monitorDataConf.tinventoryTime}"
                                                                                         pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            </tr>
                            <tr>
                                <td class="text-left"><spring:message code='sb.automated.inventory.starting.time' />：</td>
                                <td class="text-right">${monitorDataConf.tinventoryBeginTime}</td>
                                <td class="text-left" style="width:18%"><spring:message code='sb.automated.inventory.end.time' />：</td>
                                <td class="text-right" style="width:32%">${monitorDataConf.tinventoryEndTime}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%"><spring:message code='sb.realtime.temperature.1' />1：</td>
                                <td class="text-right" style="width:32%">${monitorDataConf.sltemperature}</td>
                                <td class="text-left" style="width:18%"><spring:message code='sb.realtime.temperature.2' />2：</td>
                                <td class="text-right" style="width:32%">${monitorDataConf.srtemperature}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%"><spring:message code='sb.control.temperature.1' />：</td>
                                <td class="text-right" style="width:32%">${monitorDataConf.slcontrolTemperature}</td>
                                <td class="text-left" style="width:18%"><spring:message code='sb.control.temperature.2' />：</td>
                                <td class="text-right" style="width:32%">${monitorDataConf.srcontrolTemperature}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%"><spring:message code='sb.temperature.control.start.time.1' />：</td>
                                <td class="text-right" style="width:32%">${monitorDataConf.tlcontrolBeginTime}</td>
                                <td class="text-left" style="width:18%"><spring:message code='sb.end.time.of.temperature.control.1' />：</td>
                                <td class="text-right" style="width:32%">${monitorDataConf.tlcontrolEndTime}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%"><spring:message code='sb.temperature.control.start.time.2' />：</td>
                                <td class="text-right" style="width:32%">${monitorDataConf.trcontrolBeginTime}</td>
                                <td class="text-left" style="width:18%"><spring:message code='sb.end.time.of.temperature.control.2' />：</td>
                                <td class="text-right" style="width:32%">${monitorDataConf.trcontrolEndTime}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%"><spring:message code='sb.timing.switch.status' />：</td>
                                <td class="text-right" style="width:32%">
                                    <c:if test="${monitorDataConf.iswitchStatus == 0 }"><spring:message code="main.no" /></c:if>
                                    <c:if test="${monitorDataConf.iswitchStatus == 1 }"><spring:message code="main.yes" /></c:if>
                                </td>
                                <td class="text-left" style="width:18%"><spring:message code='sb.realtime.volume' />：</td>
                                <td class="text-right" style="width:32%">${monitorDataConf.iactualVolume}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%"><spring:message code='sb.Bootup.time' />：</td>
                                <td class="text-right" style="width:32%">${monitorDataConf.tbootTime}</td>
                                <td class="text-left" style="width:18%"><spring:message code='sb.shutdown.time' />：</td>
                                <td class="text-right" style="width:32%">${monitorDataConf.tshutDownTime}</td>
                            </tr>
                        </table>
                    </div>
                </div>
                <script type="text/javascript">
                    $(".wflayui-form-item tr:even").addClass("odd");
                </script>
            </div>
        </div>
    </form>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script>
    layui.use('element', function () {
        var $ = layui.jquery, element = layui.element;//Tab的切换功能，切换事件监听等，需要依赖element模块
    });
</script>
</body>
</html>


