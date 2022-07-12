<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备编辑</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/device/info/add" method="post" id="myForm">
        <div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
            <ul class="layui-tab-title">
                <li class="layui-this" id="jc"><spring:message code="menu.device.basic.info" /></li>
                <li id="info"><spring:message code='sb.equipment.details' /></li>
                <li id="gl"><spring:message code='sb.equipment.management.information' /></li>
                <li id="jk"><spring:message code='sb.equipment.monitoring.information' /></li>
            </ul>
            <div class="layui-tab-content">
                <div class="layui-tab-item layui-show">
                    <div class="layui-form-item">
                        <%--<spring:message code='sb.hidden.attribute' />--%>
                        <input type="hidden" id="smerchantId" name="smerchantId" value=""/><%--<spring:message code='sb.merchant.id' />--%>
                        <input type="hidden" id="scameraMethod1" name="scameraMethod1" value=""/><%--<spring:message code='sb.camera.method'/>--%>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.whether.to.use.templates.or.not' /></label>
                                <div class="layui-input-inline">
                                    <select name="infoTemplate" id="infoTemplate" class="state"
                                            lay-filter="infoTemplate">
                                        <option value=""><spring:message code='sb.no.use' /></option>
                                        <c:forEach items="${deviceInfoTemplateList}" var="vo">
                                            <option value="${vo.id}">${vo.sname}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code="main.merchant.name" /></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="smerchantName" placeholder='<spring:message code="main.required" />...' datatype="*"
                                           nullmsg='<spring:message code="main.merchant.name.select" />'
                                           id="smerchantName" class="layui-input" readonly/>
                                </div>
                                <button class="layui-btn" id="btn1" style="display: inline-block;float: left;"
                                        type="button" data-type="selectMerchant"><spring:message code='sb.choice'/>
                                </button>
                            </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code="main.merchant.number" /></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="smerchantCode" id="smerchantCode"
                                           class="layui-input" disabled/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code="main.device.name" /></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="sname" placeholder='<spring:message code="main.required" />...' datatype="*" nullmsg='<spring:message code="main.device.name.empty" />'
                                           id="sname"
                                           class="layui-input"/>
                                </div>
                            </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.equipment.type'/></label>
                                <div class="layui-input-inline">
                                    <cang:select type="dic" name="itype" id="itype" entire="true"
                                                 exp="springMessageCode=sb.device type.select"
                                                 layFilter="itype" value="" groupNo="SP000144"/>
                                </div>
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.reader.serial.number' /></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="sreaderSerialNumber" id="sreaderSerialNumber"
                                           placeholder='<spring:message code="main.required" />...' datatype="*" nullmsg="<spring:message code='sb.please.enter.the.serial.number.of.the.reader' />" class="layui-input"/>
                                </div>
                            </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code="tpinfotoadd.equipment.model" /></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="sdeviceModel" id="sdeviceModel" placeholder='<spring:message code="main.required" />...'
                                           datatype="*" nullmsg='<spring:message code="sb.device.model.empty" />' class="layui-input"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.delivery.scenario' /></label>
                                <div class="layui-input-inline">
                                    <cang:select type="dic" name="sputScenes" id="sputScenes" entire="true"  laySearch="1"
                                                 value="" groupNo="SP000143"/>
                                </div>
                            </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.cooperation.mode' /></label>
                                <div class="layui-input-inline">
                                    <%--<cang:select name="irebateRule" value="0" type="dic" groupNo="A0001"/>--%>
                                    <cang:select type="dic" name="icooperationMode" id="icooperationMode" entire="true"
                                                 layFilter="icooperationMode" exp="springMessageCode=sb.please.choose.cooperation.mode"
                                                 value="" groupNo="SP000104"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.container.type' /></label>
                                <div class="layui-input-inline">
                                    <cang:select type="list" name="icontainerType" id="icontainerType" entire="true"
                                                 value="" list="{10:springMessageCode=sb.single.door,20:springMessageCode=sb.double.door}"/>
                                </div>
                            </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.compressor.type' /></label>
                                <div class="layui-input-inline">
                                    <cang:select type="list" name="icompressorType" id="icompressorType" entire="true"
                                                 value="" list="{10:springMessageCode=sb.refrigeration,20:springMessageCode=sb.heating,30:springMessageCode=sb.cooling.heat,40:springMessageCode=sb.no.compressor}"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.whether.advertising.machine' /></label>
                                <div class="layui-input-inline">
                                    <cang:select type="list" name="iinstallAd" id="iinstallAd" entire="true" value=""
                                                 list='{0:springMessageCode=main.no,1:springMessageCode=main.yes}'/>
                                </div>
                            </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.number.of.layers' /></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="ilayerNum" id="ilayerNum" value="" placeholder="<spring:message code='sb.defaults' />:5"
                                           class="layui-input"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.whether.ai.devices.are.supported.or.not' /></label>
                                <div class="layui-input-inline">
                                    <cang:select type="list" name="isupportAi" id="isupportAi" entire="true"
                                                 exp="springMessageCode=sb.please.choose.whether.to.support"
                                                 layFilter="isupportAi" value="" list='{0:springMessageCode=main.no,1:springMessageCode=main.yes}'/>
                                </div>
                            </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.whether.to.open.realtime.inventory' /></label>
                                <div class="layui-input-inline">
                                    <cang:select type="list" name="iisOpeningInventory" id="iisOpeningInventory"
                                                 entire="true"
                                                 exp="springMessageCode=sb.please.choose.whether.to.support"
                                                 layFilter="iisOpeningInventory" value="1" list='{0:springMessageCode=main.no,1:springMessageCode=main.yes}'/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='wz.screen.display.type' /></label>
                                <div class="layui-input-inline">
                                    <cang:select type="dic" groupNo="SP000175" entire="true"
                                                 exp="springMessageCode=sb.please.select.the.screen.display.type"
                                                 value="" entireName='springMessageCode=sb.please.select.the.screen.display.type'
                                                 name="iscreenDisplayType" id="iscreenDisplayType"/>
                                </div>
                            </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.system.docking.type' /></label>
                                <div class="layui-input-inline">
                                    <cang:select type="list" name="idockingType" id="idockingType"
                                                 entire="true"
                                                 exp="springMessageCode=sb.please.select.the.system.docking.type"
                                                 layFilter="idockingType" value="1" list="{10:springMessageCode=sb.independent.research.and.development,20:springMessageCode=sb.third.party}"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.do.you.support.weighing' /></label>
                                <div class="layui-input-inline">
                                    <cang:select type="list" name="isupportWeigh" id="isupportWeigh" entire="true"
                                                 exp="springMessageCode=sb.please.choose.whether.to.support.weighing"
                                                 layFilter="isupportWeigh" value="" list='{0:springMessageCode=main.no,1:springMessageCode=main.yes}'/>
                                </div>
                            </div>
                            <div class="layui-col-md6" style="display: none;" id="com_elec_Float">
                                <label class="layui-form-label"><spring:message code='sb.eloating.value.of.electronic.scale' /></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="ielectronicFloat" id="ielectronicFloat" value=""
                                           nullmsg="<spring:message code='sb.please.enter.a.positive.number.with.decimal.places' />" datatype="/^[0-9]+(\.[0-9]{1,2})?$/" ignore="ignore"
                                           placeholder="<spring:message code='sb.defaults' />：0.00" class="layui-input"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.provinces.put.into.operation'/></label>
                                <div class="layui-input-inline">
                                    <select name="sprovinceName" id="sprovinceName" class="state" lay-verify="required" lay-search=""
                                            datatype="*" nullmsg="<spring:message code='sb.please.select.a.province.to.serve'/>" lay-filter="province">
                                        <option value=""><spring:message code="main.please.choose" /></option>
                                        <c:forEach items="${provinceSet}" var="vo">
                                            <option value="${vo.id}_${vo.sname}">${vo.sname}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.put.it.into.the.city'/></label>
                                <div class="layui-input-inline">
                                    <select name="scityName" id="scityName" class="state" lay-verify="required" lay-search=""
                                            datatype="*" nullmsg="<spring:message code='sb.please.select.a.city.to.launch'/>" lay-filter="city">
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.delivery.to.districts.and.counties'/></label>
                                <div class="layui-input-inline">
                                    <select name="sareaName" id="sareaName" class="state" lay-verify="required" lay-search=""
                                            datatype="*" nullmsg="<spring:message code='sb.please.select.the.county.to.be.placed'/>" lay-filter="town">
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.put.in.the.detailed.address' /></label>
                                <div class="layui-input-inline">
                                    <textarea class="layui-textarea" name="sputAddress" placeholder='<spring:message code="main.required" />...' datatype="*"
                                              nullmsg="<spring:message code='sb.please.enter.the.address' />" id="sputAddress" value=""
                                              style="width: 720px;height: 100px">${deviceInfo.sputAddress}</textarea>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code="main.remarks" /></label>
                                <div class="layui-input-inline">
                                    <textarea class="layui-textarea" name="sremark" id="sremark" value=""
                                              style="width: 720px;height: 100px"></textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="layui-tab-item">
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.whether.to.use.templates.or.not' /></label>
                            <div class="layui-input-inline">
                                <select name="modelTemplate" id="modelTemplate" class="state"
                                        lay-filter="modelTemplate">
                                    <option value=""><spring:message code='sb.no.use' /></option>
                                    <c:forEach items="${deviceModelTemplateList}" var="model">
                                        <option value="${model.id}">${model.sname}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code="sb.equipment.core.model" /></label>
                            <div class="layui-input-inline">
                                <%--因为与sdeviceModel字段重复，改为sdeviceCoreModel--%>
                                <input type="text" name="sdeviceCoreModel" id="sdeviceCoreModel" value=""
                                       class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.core.manufacturers' /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="smanufacturer" id="smanufacturer" value=""
                                       class="layui-input"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.core.configuration.description' /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="scoreDesc" id="scoreDesc" value="" class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.overall.shape.size' /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="sdimensions" id="sdimensions" value=""
                                       class="layui-input"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.weight' /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="sweight" id="sweight" value="" class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.rated.power' /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="sratedPower" id="sratedPower" value=""
                                       class="layui-input"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.product.capacity' /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="sproductCapacity" id="sproductCapacity" value=""
                                       class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.product.types' /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="sproductTypes" id="sproductTypes" value=""
                                       class="layui-input"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.daily.power.consumption' /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="sdailyPower" id="sdailyPower" value=""
                                       class="layui-input"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.support.payment.method' />:(<spring:message code="main.wechat" />,<spring:message code="main.alipay" />,<spring:message code='sb.cash' />,<spring:message code='sb.unionpay.cards' />)</label>
                            <div class="layui-input-inline">
                                <input type="text" name="spayWap" id="spayWap" value="" class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.Aanti.shock.type' /></label>
                            <div class="layui-input-inline">
                                <cang:select type="list" name="ielectricShock" id="ielectricShock" entire="true"
                                             value="" list="{10:0<spring:message c ='sb.Class,20:I<spring:message code='sb.Class,30:II<spring:message code='sb.Class,40:III<spring:message code='sb.Class}"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.electronic.lock.model' /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="slocksModel" id="slocksModel" value=""
                                       class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.manufacturer.of.electronic.locksl' /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="slocksManufacturer" id="slocksManufacturer" value=""
                                       class="layui-input"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.screen.dimension.instructions.for.advertising.machines' /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="sadDimensions" id="sadDimensions" value=""
                                       class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.advertising.machine.configuration.description' /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="sadConf" id="sadConf" value="" class="layui-input"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.number.of.compressors' /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="icompressorNum" id="icompressorNum" value=""
                                       class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.compressor.position' /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="scompressorPosition" id="scompressorPosition" value=""
                                       class="layui-input"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.compressor.description' /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="scompressorDesc" id="scompressorDesc" value=""
                                       class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.number.of.goods' /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="icargoRoadNum" id="icargoRoadNum" value=""
                                       class="layui-input"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.number.of.cameras' /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="icameraNum" id="icameraNum" value=""
                                       class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.number.of.motherboard.blocks' /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="imotherboardNum" id="imotherboardNum" value=""
                                       class="layui-input"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.unlocking.method' /></label>
                            <div class="layui-input-inline">
                                <cang:select type="dic" name="iisUseExpandGpio" id="iisUseExpandGpio" entire="true"
                                             exp="springMessageCode=sb.please.choose.the.unlock.method"
                                             layFilter="iunlockType" value="" groupNo="SP000173"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.whether.to.detect.hall.value' /></label>
                            <div class="layui-input-inline">
                                <cang:select type="list" name="iisDetectHall" id="iisDetectHall" entire="true"
                                             layFilter="iisDetectHall"
                                             exp="springMessageCode=sb.please.choose.whether.to.detect.the.hall.value" value="" list='{0:springMessageCode=main.no,1:springMessageCode=main.yes}'/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.camera.method'/></label>
                            <div class="layui-input-inline">
                                <cang:select type="dic" name="scameraMethod" id="scameraMethod" entire="true"
                                             exp="springMessageCode=sb.please.select.the.camera.method"
                                             layFilter="scameraMethod" value="" groupNo="SP000171"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.camera.model' /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="scameraModel" id="scameraModel" value=""
                                       class="layui-input" readonly/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.camera.model' /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="scameraType" id="scameraType" value=""
                                       class="layui-input" readonly/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.camera.brand' /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="scameraBrand" id="scameraBrand" value=""
                                       class="layui-input" readonly/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.visual.service.provider' /></label>
                            <div class="layui-input-inline">
                                <cang:select type="dic" name="svisualServiceProvider" id="svisualServiceProvider"
                                             entire="true"
                                             value="" groupNo="SP000172"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.pcb.board.model' /></label>
                            <div class="layui-input-inline">
                                <cang:select type="dic" name="spcbModel" id="spcbModel" entire="true"
                                             exp="springMessageCode=sb.please.select.the.pcb.board.model"
                                             layFilter="spcbModel" value="${deviceModel.spcbModel}" groupNo="SP000174"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.open.pin.serial.number' /></label>
                            <div class="layui-input-inline">
                                <input type="number" name="iopendoorPinSn" id="iopendoorPinSn" value=""
                                       placeholder='<spring:message code="main.required" />...' datatype="*" nullmsg="<spring:message code='sb.please.enter.the.opening.pin.number' />" class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.hall.pin.serial.number' /></label>
                            <div class="layui-input-inline">
                                <input type="number" name="ihallPinSn" id="ihallPinSn" value=""
                                       placeholder='<spring:message code="main.required" />...' datatype="*" nullmsg="<spring:message code='sb.please.enter.the.hall.pin.number' />" class="layui-input"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.door.pin.serial.number' /></label>
                            <div class="layui-input-inline">
                                <input type="number" name="idoorPinSn" id="idoorPinSn" value=""
                                       placeholder='<spring:message code="main.required" />...' datatype="*" nullmsg="<spring:message code='sb.please.enter.the.gate.pin.number' />" class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.cylinder.pin.number' /></label>
                            <div class="layui-input-inline">
                                <input type="number" name="ilockCylinder" id="ilockCylinder" value=""
                                       placeholder='<spring:message code="main.required" />...' datatype="*" nullmsg="><spring:message code='sb.please.enter.the.lock.pin.number' />" class="layui-input"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label">><spring:message code='sb.comparison.method' /></label>
                            <div class="layui-input-inline">
                                <cang:select type="list" name="scontrastMode" id="scontrastMode" entire="true"
                                             value="" list="{rawstock:><spring:message code='sb.original.stock,openDoor:><spring:message code='sb.switch.door}"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="layui-tab-item">
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.whether.to.use.templates.or.not' /></label>
                            <div class="layui-input-inline">
                                <select name="manageTemplate" id="manageTemplate" class="state"
                                        lay-filter="manageTemplate">
                                    <option value=""><spring:message code='sb.no.use' /></option>
                                    <c:forEach items="${deviceManageTemplateList}" var="manage">
                                        <option value="${manage.id}">${manage.sname}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.areas.to.which.they.belong'/></label>
                                <div class="layui-input-inline">
                                    <cang:select type="dic" name="sareaCode" id="sareaCode" entire="true"
                                                 value="" groupNo="SP000145"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.regional.director'/></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="sareaPrincipal" id="sareaPrincipal" value=""
                                           class="layui-input"/>
                                </div>
                            </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.contact.information.of.regional.leaders' /></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="sareaPrincipalContact" id="sareaPrincipalContact" value=""
                                           class="layui-input"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.equipment.person.in.charge'/></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="sdevicePrincipal" id="sdevicePrincipal" value=""
                                           class="layui-input"/>
                                </div>
                            </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.contact.form.of.equipment.leader' /></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="sdevicePrincipalContact" id="sdevicePrincipalContact"
                                           value="" class="layui-input"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.name.of.replenisher' /></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="sreplenishment" id="sreplenishment" value=""
                                           class="layui-input"/>
                                </div>
                            </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.contact.information.of.replenisher' /></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="sreplenishmentContact" id="sreplenishmentContact" value=""
                                           class="layui-input"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.name.of.equipment.maintenance.person' /></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="smaintain" id="smaintain" value="" class="layui-input"/>
                                </div>
                            </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.maintenance.contact.information' /></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="smaintainContact" id="smaintainContact" value=""
                                           class="layui-input"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="layui-tab-item">
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.number.of.times.per.inventory' /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="iinventoryNum" id="iinventoryNum" value=""
                                       class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.automated.inventory.starting.time' /></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="tinventoryBeginTime" id="tinventoryBeginTime" value=""
                                           class="layui-input"/>
                                </div>
                            </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.automated.inventory.end.time' /></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="tinventoryEndTime" id="tinventoryEndTime" value=""
                                           class="layui-input"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.control.temperature.1' /></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="slcontrolTemperature" id="slcontrolTemperature" value=""
                                           class="layui-input"/>
                                </div>
                            </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.control.temperature.2' /></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="srcontrolTemperature" id="srcontrolTemperature" value=""
                                           class="layui-input"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.temperature.control.start.time.1' /></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="tlcontrolBeginTime" id="tlcontrolBeginTime" value=""
                                           class="layui-input"/>
                                </div>
                            </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.end.time.of.temperature.control.1' /></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="tlcontrolEndTime" id="tlcontrolEndTime" value=""
                                           class="layui-input"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <%--<div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.realtime.temperature.1' />1</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="sltemperature" id="sltemperature" value=""
                                           class="layui-input"/>
                                </div>
                            </div>--%>
                            <%--<div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.realtime.temperature.2' />2</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="srtemperature" id="srtemperature" value=""
                                           class="layui-input"/>
                                </div>
                            </div>--%>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.temperature.control.start.time.2' /></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="trcontrolBeginTime" id="trcontrolBeginTime" value=""
                                           class="layui-input"/>
                                </div>
                            </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.end.time.of.temperature.control.2' /></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="trcontrolEndTime" id="trcontrolEndTime" value=""
                                           class="layui-input"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.timing.switch.status' /></label>
                                <div class="layui-input-inline">
                                    <cang:select type="list" name="iswitchStatus" id="iswitchStatus" entire="true"
                                                 value="" list='{0:springMessageCode=main.no,1:springMessageCode=main.yes}'/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.Bootup.time' /></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="tbootTime" id="tbootTime" value="" class="layui-input"/>
                                </div>
                            </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.shutdown.time' /></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="tshutDownTime" id="tshutDownTime" value=""
                                           class="layui-input"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <%--<div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code='sb.realtime.volume' /></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="iactualVolume" id="iactualVolume" value=""
                                           class="layui-input"/>
                                </div>
                            </div>--%>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
                <button class="layui-btn" <%--lay-submit="" lay-filter="myFormSub" --%>id="myFormSub"><spring:message code="main.save" /></button>
            </div>
        </div>
    </form>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<%--<script>
    layui.use('element', function(){
        var $ = layui.jquery,element = layui.element;//Tab的切换功能，切换事件监听等，需要依赖element模块
    });
</script>--%>
<script src="${staticSource}/resources/js/validform_min.js" type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var form;
    layui.use(['form', 'layedit', 'laydate', 'element'], function () {
        //Tab的切换功能
        var $ = layui.jquery, element = layui.element;//Tab的切换功能，切换事件监听等，需要依赖element模块

        form = layui.form;

        //选择商户
        var $ = layui.$, active = {
            selectMerchant: function () {
                showLayer("<spring:message code='sb.select.a.merchant' />", ctx + "/merchantInfo/radioSelectList", {area: ['90%', '80%']});
            }
        };
        $('.layui-form .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

        $("#myForm").Validform({
            btnSubmit: "#myFormSub",  //根据id触发
            tiptype: 3,                  //第三种方式
            showAllError: true,
            beforeSubmit: function () { //验证过后执行save方法
                var loadIndex = loading();
                $('#myForm').ajaxSubmit({
                    type: "post",
                    dataType: "json",
                    async: true,
                    error: function () {
                        alertDelAndReload("<spring:message code='main.error.try.again' />");
                    },
                    success: function (msg) {
                        //成功返回
                        closeLayer(loadIndex);//关闭加载框
                        if (msg.success) {
                            alertSuccess("<spring:message code='main.success' />", {
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


        layui.use('laydate', function () {
            var laydate = layui.laydate;

            //上次盘货时间
            /*laydate.render({
             elem: '#tinventoryTime',
             type: 'time'
             });*/
            //<spring:message code='sb.automated.inventory.starting.time' />
            laydate.render({
                elem: '#tinventoryBeginTime',
                type: 'time'
            });
            //<spring:message code='sb.automated.inventory.end.time' />
            laydate.render({
                elem: '#tinventoryEndTime',
                type: 'time'
            });
            //<spring:message code='sb.temperature.control.start.time.1' />
            laydate.render({
                elem: '#tlcontrolBeginTime',
                type: 'datetime'
            });
            //<spring:message code='sb.end.time.of.temperature.control.1' />
            laydate.render({
                elem: '#tlcontrolEndTime',
                type: 'datetime'
            });
            //<spring:message code='sb.temperature.control.start.time.2' />
            laydate.render({
                elem: '#trcontrolBeginTime',
                type: 'datetime'
            });
            //<spring:message code='sb.end.time.of.temperature.control.2' />
            laydate.render({
                elem: '#trcontrolEndTime',
                type: 'datetime'
            });
            //<spring:message code='sb.Bootup.time' />
            laydate.render({
                elem: '#tbootTime',
                type: 'time'
            });
            //<spring:message code='sb.shutdown.time' />
            laydate.render({
                elem: '#tshutDownTime',
                type: 'time'
            });
        });

        //监听select选择
        form.on('select(city)', function (data) {
            var areaId = data.value.split("_")[0];
            $.ajax({
                type: 'post',
                url: '${ctx}/device/info/getTown',
                data: {cityId: areaId},
                dataType: 'json',
                success: function (data) {
                    $("#sareaName").html("");
                    var option1 = $("<option>").val("").text('<spring:message code="main.please.choose" />');
                    $("#sareaName").append(option1);
                    $.each(data.data, function (key, val) {
                        var option1 = $("<option>").val(val.id + "_" + val.sname).text(val.sname);
                        $("#sareaName").append(option1);
                        form.render('select');
                    });
                }
            });

            if (!isEmpty(data.value)) {
                $("#scityName").parent().find("span").hide();
            } else {
                $("#scityName").parent().find("span").show();
                if (!$("#scityName").parent().find("span").hasClass("Validform_wrong")) {
                    $("#scityName").parent().find("span").html($("#scityName").attr("nullmsg"));
                    $("#scityName").parent().find("span").removeClass("Validform_right");
                    $("#scityName").parent().find("span").addClass("Validform_wrong");
                }
            }
        });


        //监听select选择
        form.on('select(province)', function (data) {
            var areaId = data.value.split("_")[0];
            $.ajax({
                type: 'post',
                url: '${ctx}/device/info/getCity',
                data: {pid: areaId},
                dataType: 'json',
                success: function (resp) {
                    $("#scityName").html("");
                    var option1 = $("<option>").val("").text('<spring:message code="main.please.choose" />');
                    $("#scityName").append(option1);
                    $("#sareaName").html("");
                    var option1 = $("<option>").val("").text('<spring:message code="main.please.choose" />');
                    $("#sareaName").append(option1);
                    $.each(resp.data, function (key, val) {
                        var option1 = $("<option>").val(val.id + "_" + val.sname).text(val.sname);
                        $("#scityName").append(option1);
                        form.render('select');
                    });
                    //$('#scityName').trigger('change');
                }
            });

            if (!isEmpty(data.value)) {
                $("#sprovinceName").parent().find("span").hide();
            } else {
                $("#sprovinceName").parent().find("span").show();
                if (!$("#sprovinceName").parent().find("span").hasClass("Validform_wrong")) {
                    $("#sprovinceName").parent().find("span").html($("#sprovinceName").attr("nullmsg"));
                    $("#sprovinceName").parent().find("span").removeClass("Validform_right");
                    $("#sprovinceName").parent().find("span").addClass("Validform_wrong");
                }
            }
        });


        form.on('select(infoTemplate)', function (data) {
            if (isEmpty(data.value)) {
                infoTemplate(0, null);
                return;
            }
            var areaId = data.value;
            $.ajax({
                type: 'post',
                url: '${ctx}/deviceInfoTemplate/getInfoTemplate',
                data: {pid: areaId},
                dataType: 'json',
                success: function (resp) {
                    infoTemplate(1, resp.data);
                }
            });
        });

        form.on('select(manageTemplate)', function (data) {
            if (isEmpty(data.value)) {
                manageTemplate(0, null);
                return;
            }
            var areaId = data.value;
            $.ajax({
                type: 'post',
                url: '${ctx}/deviceManageTemplate/getManageTemplate',
                data: {pid: areaId},
                dataType: 'json',
                success: function (resp) {
                    manageTemplate(1, resp.data);
                }
            });
        });

        form.on('select(modelTemplate)', function (data) {
            if (isEmpty(data.value)) {
                modelTemplate(0, null);
                return;
            }
            var areaId = data.value;
            $.ajax({
                type: 'post',
                url: '${ctx}/deviceModelTemplate/getModelTemplate',
                data: {pid: areaId},
                dataType: 'json',
                success: function (resp) {
                    modelTemplate(1, resp.data);
                }
            });
        });


        //监听区县选择
        form.on('select(town)', function (data) {
            if (!isEmpty(data.value)) {
                $("#sareaName").parent().find("span").hide();
            } else {
                $("#sareaName").parent().find("span").show();
                if (!$("#sareaName").parent().find("span").hasClass("Validform_wrong")) {
                    $("#sareaName").parent().find("span").html($("#sareaName").attr("nullmsg"));
                    $("#sareaName").parent().find("span").removeClass("Validform_right");
                    $("#sareaName").parent().find("span").addClass("Validform_wrong");
                }
            }
        });

        //监听设备类型选择
        form.on('select(itype)', function (data) {
            if (!isEmpty(data.value)) {
                $("#itype").parent().find("span").hide();
            } else {
                $("#itype").parent().find("span").show();
                if (!$("#itype").parent().find("span").hasClass("Validform_wrong")) {
                    $("#itype").parent().find("span").html($("#itype").attr("nullmsg"));
                    $("#itype").parent().find("span").removeClass("Validform_right");
                    $("#itype").parent().find("span").addClass("Validform_wrong");
                }
            }
        });

        //监听合作模式选择
        form.on('select(icooperationMode)', function (data) {
            if (!isEmpty(data.value)) {
                $("#icooperationMode").parent().find("span").hide();
            } else {
                $("#icooperationMode").parent().find("span").show();
                if (!$("#icooperationMode").parent().find("span").hasClass("Validform_wrong")) {
                    $("#icooperationMode").parent().find("span").html($("#icooperationMode").attr("nullmsg"));
                    $("#icooperationMode").parent().find("span").removeClass("Validform_right");
                    $("#icooperationMode").parent().find("span").addClass("Validform_wrong");
                }
            }
        });

        //监听是否支持称重选择
        form.on('select(isupportWeigh)', function (data) {
            var isSupportAiDevice = data.value;
            if (!isEmpty(data.value)) {
                $("#isupportWeigh").parent().find("span").hide();
                if (isSupportAiDevice == 1) {
                    $("#com_elec_Float").show();
                } else {
                    $("#com_elec_Float").hide();
                }
                $("#icommodityFloat").val("");
                $("#ielectronicFloat").val("");
            } else {
                $("#isupportWeigh").parent().find("span").show();
                if (!$("#isupportWeigh").parent().find("span").hasClass("Validform_wrong")) {
                    $("#isupportWeigh").parent().find("span").html($("#isupportWeigh").attr("nullmsg"));
                    $("#isupportWeigh").parent().find("span").removeClass("Validform_right");
                    $("#isupportWeigh").parent().find("span").addClass("Validform_wrong");
                }
                $("#com_elec_Float").hide();
                $("#icommodityFloat").val("");
                $("#ielectronicFloat").val("");
            }
        });


        //监听是否支持AI设备
        form.on('select(isupportAi)', function (data) {
            var isUpport = data.value;
            if (!isEmpty(isUpport)) {
                $("#isupportAi").parent().find("span").hide();
            } else {
                $("#isupportAi").parent().find("span").show();
                if (!$("#isupportAi").parent().find("span").hasClass("Validform_wrong")) {
                    $("#isupportAi").parent().find("span").html($("#isupportAi").attr("nullmsg"));
                    $("#isupportAi").parent().find("span").removeClass("Validform_right");
                    $("#isupportAi").parent().find("span").addClass("Validform_wrong");
                }
            }
        });

        //监听系统对接类型
        form.on('select(idockingType)', function (data) {
            if (!isEmpty(data.value)) {
                $("#idockingType").parent().find("span").hide();
            } else {
                $("#idockingType").parent().find("span").show();
                if (!$("#idockingType").parent().find("span").hasClass("Validform_wrong")) {
                    $("#idockingType").parent().find("span").html($("#idockingType").attr("nullmsg"));
                    $("#idockingType").parent().find("span").removeClass("Validform_right");
                    $("#idockingType").parent().find("span").addClass("Validform_wrong");
                }
            }
        });

        //监听摄像头方法select选择
        form.on('select(scameraMethod)', function (data) {
            var method = data.elem[data.elem.selectedIndex].text;   // 文本内容
            $.ajax({
                type: 'post',
                url: '${ctx}/device/deviceModel/getCaremaInfo',
                data: {method: method},
                dataType: 'json',
                success: function (resp) {
                    var str = resp.data;
                    if (!isEmpty(str)) {
                        var strs = new Array(); //定义一数组
                        strs = str.split("-//-"); //字符分割
                        $("#scameraMethod1").val(method);
                        $("#scameraModel").val(strs[0]);
                        $("#scameraType").val(strs[1]);
                        $("#scameraBrand").val(strs[2]);
                    } else {
                        $("#scameraMethod1").val("");
                        $("#scameraModel").val("");
                        $("#scameraType").val("");
                        $("#scameraBrand").val("");
                    }
                }
            });

            if (!isEmpty(data.value)) {
                $("#scameraMethod").parent().find("span").hide();
            } else {
                $("#scameraMethod").parent().find("span").show();
                if (!$("#scameraMethod").parent().find("span").hasClass("Validform_wrong")) {
                    $("#scameraMethod").parent().find("span").html($("#scameraMethod").attr("nullmsg"));
                    $("#scameraMethod").parent().find("span").removeClass("Validform_right");
                    $("#scameraMethod").parent().find("span").addClass("Validform_wrong");
                }
            }
        });

        //监听是否使用扩展GPIO
        form.on('select(iisUseExpandGpio)', function (data) {
            if (!isEmpty(data.value)) {
                $("#iisUseExpandGpio").parent().find("span").hide();
            } else {
                $("#iisUseExpandGpio").parent().find("span").show();
                if (!$("#iisUseExpandGpio").parent().find("span").hasClass("Validform_wrong")) {
                    $("#iisUseExpandGpio").parent().find("span").html($("#iisUseExpandGpio").attr("nullmsg"));
                    $("#iisUseExpandGpio").parent().find("span").removeClass("Validform_right");
                    $("#iisUseExpandGpio").parent().find("span").addClass("Validform_wrong");
                }
            }
        });

        //监听是否检测霍尔值
        form.on('select(iisDetectHall)', function (data) {
            if (!isEmpty(data.value)) {
                $("#iisDetectHall").parent().find("span").hide();
            } else {
                $("#iisDetectHall").parent().find("span").show();
                if (!$("#iisDetectHall").parent().find("span").hasClass("Validform_wrong")) {
                    $("#iisDetectHall").parent().find("span").html($("#iisDetectHall").attr("nullmsg"));
                    $("#iisDetectHall").parent().find("span").removeClass("Validform_right");
                    $("#iisDetectHall").parent().find("span").addClass("Validform_wrong");
                }
            }
        });



    });

    $(function () {
        $("#cancelBtn").click(function () {
            parent.layer.close(index);
            return false;
        });
    });

    function selectMerchant(merchantId, merchantCode, merchantName) {
        $("#smerchantId").val(merchantId);//<spring:message code='sb.merchant.id' />
        $("#smerchantCode").val(merchantCode);//商户编号
        $("#smerchantName").val(merchantName);//商户名称
        $("#smerchantName").parent().find("span").hide();
        if ($("#smerchantName").parent().find("span").hasClass("Validform_wrong")) {
            $("#smerchantName").parent().find("span").removeClass("Validform_wrong");
            $("#smerchantName").removeClass("Validform_error");
            $("#smerchantName").parent().find("span").addClass("Validform_right");
        }
    }

    function infoTemplate(isUseTemplate, date) {
        if (isUseTemplate == 1) {
            $("#itype").val(date.itype);
            $("#sdeviceModel").val(date.sdeviceModel);
            $("#sreaderSerialNumber").val(date.sreaderSerialNumber);
            $("#icooperationMode").val(date.icooperationMode);
            $("#icontainerType").val(date.icontainerType);
            $("#icompressorType").val(date.icompressorType);
            $("#iinstallAd").val(date.iinstallAd);
            $("#sputScenes").val(date.sputScenes);
            $("#isupportAi").val(date.isupportAi);
            $("#isupportWeigh").val(date.isupportWeigh);
            $("#ielectronicFloat").val(date.ielectronicFloat);
            $("#iisOpeningInventory").val(date.iisOpeningInventory);
            $("#idockingType").val(date.idockingType);
            $("#iscreenDisplayType").val(date.iscreenDisplayType);
            if (date.isupportWeigh == 1) {
                $("#com_elec_Float").show();
            } else {
                $("#com_elec_Float").hide();
            }
            form.render('select');
        } else if (isUseTemplate == 0) {
            $("#itype").val("");
            $("#sdeviceModel").val("");
            $("#sreaderSerialNumber").val("");
            $("#icooperationMode").val("");
            $("#icontainerType").val("");
            $("#icompressorType").val("");
            $("#iinstallAd").val("");
            $("#sputScenes").val("");
            $("#isupportAi").val("");
            $("#isupportWeigh").val("");
            $("#ielectronicFloat").val("");
            $("#iisOpeningInventory").val("");
            $("#idockingType").val("");
            $("#iscreenDisplayType").val("");

            form.render('select');
        }
    }

    function manageTemplate(isUseTemplate, date) {
        if (isUseTemplate == 1) {
            $("#sareaPrincipal").val(date.sareaPrincipal);
            $("#sareaPrincipalContact").val(date.sareaPrincipalContact);
            $("#sdevicePrincipal").val(date.sdevicePrincipal);
            $("#sdevicePrincipalContact").val(date.sdevicePrincipalContact);
            $("#sreplenishment").val(date.sreplenishment);
            $("#sreplenishmentContact").val(date.sreplenishmentContact);
            $("#smaintain").val(date.smaintain);
            $("#smaintainContact").val(date.smaintainContact);
            form.render('select');
        } else if (isUseTemplate == 0) {
            $("#sareaPrincipal").val("");
            $("#sareaPrincipalContact").val("");
            $("#sdevicePrincipal").val("");
            $("#sdevicePrincipalContact").val("");
            $("#sreplenishment").val("");
            $("#sreplenishmentContact").val("");
            $("#smaintain").val("");
            $("#smaintainContact").val("");
            form.render('select');
        }
    }

    function modelTemplate(isUseTemplate, date) {
        if (isUseTemplate == 1) {
            $("#sdeviceCoreModel").val(date.sdeviceModel);
            $("#smanufacturer").val(date.smanufacturer);
            $("#scoreDesc").val(date.scoreDesc);
            $("#sdimensions").val(date.sdimensions);
            $("#sweight").val(date.sweight);
            $("#sratedPower").val(date.sratedPower);
            $("#sproductCapacity").val(date.sproductCapacity);
            $("#sproductTypes").val(date.sproductTypes);
            $("#sdailyPower").val(date.sdailyPower);
            $("#spayWap").val(date.spayWap);
            $("#ielectricShock").val(date.ielectricShock);
            $("#imotherboardNum").val(date.imotherboardNum);
            $("#slocksModel").val(date.slocksModel);
            $("#slocksManufacturer").val(date.slocksManufacturer);
            $("#sadDimensions").val(date.sadDimensions);
            $("#sadConf").val(date.sadConf);
            $("#icompressorNum").val(date.icompressorNum);
            $("#scompressorPosition").val(date.scompressorPosition);
            $("#scompressorDesc").val(date.scompressorDesc);
            $("#ilayerNum").val(date.ilayerNum);
            $("#icargoRoadNum").val(date.icargoRoadNum);
            $("#icameraNum").val(date.icameraNum);
            $("#iisUseExpandGpio").val(date.iisUseExpandGpio);
            $("#iisDetectHall").val(date.iisDetectHall);
            $("#scameraMethod").val(date.scameraMethod);
            $("#scameraMethod1").val(date.scameraMethod1);
            $("#scameraModel").val(date.scameraModel);
            $("#scameraType").val(date.scameraType);
            $("#scameraBrand").val(date.scameraBrand);
            $("#svisualServiceProvider").val(date.svisualServiceProvider);
            $("#spcbModel").val(date.spcbModel);
            $("#iopendoorPinSn").val(date.iopendoorPinSn);
            $("#ihallPinSn").val(date.ihallPinSn);
            $("#idoorPinSn").val(date.idoorPinSn);
            $("#ilockCylinder").val(date.ilockCylinder);
            $("#scontrastMode").val(date.scontrastMode);
            form.render('select');
        } else if (isUseTemplate == 0) {
            $("#sdeviceCoreModel").val("");
            $("#smanufacturer").val("");
            $("#scoreDesc").val("");
            $("#sdimensions").val("");
            $("#sweight").val("");
            $("#sratedPower").val("");
            $("#sproductCapacity").val("");
            $("#sproductTypes").val("");
            $("#sdailyPower").val("");
            $("#spayWap").val("");
            $("#ielectricShock").val("");
            $("#imotherboardNum").val("");
            $("#slocksModel").val("");
            $("#slocksManufacturer").val("");
            $("#sadDimensions").val("");
            $("#sadConf").val("");
            $("#icompressorNum").val("");
            $("#scompressorPosition").val("");
            $("#scompressorDesc").val("");
            $("#ilayerNum").val("");
            $("#icargoRoadNum").val("");
            $("#icameraNum").val("");
            $("#iisUseExpandGpio").val("");
            $("#iisDetectHall").val("");
            $("#scameraMethod").val("");
            $("#scameraMethod1").val("");
            $("#scameraModel").val("");
            $("#scameraType").val("");
            $("#scameraBrand").val("");
            $("#svisualServiceProvider").val("");
            $("#spcbModel").val("");
            $("#iopendoorPinSn").val("");
            $("#ihallPinSn").val("");
            $("#idoorPinSn").val("");
            $("#ilockCylinder").val("");
            $("#scontrastMode").val("");
            form.render('select');
        }
    }

</script>
</body>
</html>

