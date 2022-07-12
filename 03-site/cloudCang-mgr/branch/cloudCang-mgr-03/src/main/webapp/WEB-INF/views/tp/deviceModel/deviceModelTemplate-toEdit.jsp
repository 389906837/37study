<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>增加设备详细信息模板</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
</head>
<body>
<div class="ibox-content">
    <div class="layui-tab-content">
        <div class="layui-tab-item layui-show">
            <form class="layui-form" action="${ctx}/deviceModelTemplate/edit" method="post" id="myForm" >
                <input type="hidden" name="id" id="id" value="${deviceModelTemplate.id}" class="layui-input"/>
                <input type="hidden" id="scameraMethod1" name="scameraMethod1" value="${deviceModelTemplate.scameraMethod}"/><%--<spring:message code="tpmodel.camera.method" />--%>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmodel.template.name" /></label>
                        <div class="layui-input-inline">
                            <input type="text" name="sname" id="sname" datatype="*" nullmsg='<spring:message code="tpmanage.please.enter.a.template.name" />'
                                   value="${deviceModelTemplate.sname}" class="layui-input" placeholder='<spring:message code="main.required" />...'/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmodel.template.number" /></label>
                        <div class="layui-input-inline">
                            <input type="text" name="scode" id="scode" value="${deviceModelTemplate.scode}"
                                   class="layui-input" disabled/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmodel.equipment.core.model" /></label>
                        <div class="layui-input-inline">
                            <input type="text" name="sdeviceModel" id="sdeviceModel" value="${deviceModelTemplate.sdeviceModel}" class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmodel.core.manufacturer" /></label>
                        <div class="layui-input-inline">
                            <input type="text" name="smanufacturer" id="smanufacturer" value="${deviceModelTemplate.smanufacturer}" class="layui-input"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmodel.core.configuration.description" /></label>
                        <div class="layui-input-inline">
                            <input type="text" name="scoreDesc" id="scoreDesc" value="${deviceModelTemplate.scoreDesc}" class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmodel.overall.shape.size" /></label>
                        <div class="layui-input-inline">
                            <input type="text" name="sdimensions" id="sdimensions" value="${deviceModelTemplate.sdimensions}" class="layui-input"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmodel.weight" /></label>
                        <div class="layui-input-inline">
                            <input type="text" name="sweight" id="sweight" value="${deviceModelTemplate.sweight}" class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmodel.rated.power" /></label>
                        <div class="layui-input-inline">
                            <input type="text" name="sratedPower" id="sratedPower" value="${deviceModelTemplate.sratedPower}" class="layui-input"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmodel.commodity.capacity" /></label>
                        <div class="layui-input-inline">
                            <input type="text" name="sproductCapacity" id="sproductCapacity" value="${deviceModelTemplate.sproductCapacity}"
                                   class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmodel.commodity.types" /></label>
                        <div class="layui-input-inline">
                            <input type="text" name="sproductTypes" id="sproductTypes" value="${deviceModelTemplate.sproductTypes}" class="layui-input"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmodel.daily.power.consumption" /></label>
                        <div class="layui-input-inline">
                            <input type="text" name="sdailyPower" id="sdailyPower" value="${deviceModelTemplate.sdailyPower}" class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmodel.support.payment.methods.weChat.alipay.cash.unionpay.card" /></label>
                        <div class="layui-input-inline">
                            <input type="text" name="spayWap" id="spayWap" value="${deviceModelTemplate.spayWap}" class="layui-input"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmodel.anti-shock.type" /></label>
                        <div class="layui-input-inline">
                            <cang:select type="list" name="ielectricShock" id="ielectricShock" entire="true"
                                         value="${deviceModelTemplate.ielectricShock}" list="{10:springMessageCode=tp.class,20:springMessageCode=tp.class.i,30:springMessageCode=tp.class.ii,40:springMessageCode=tp.class.iii}"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmodel.number.of.motherboard.blocks" /></label>
                        <div class="layui-input-inline">
                            <input type="number" name="imotherboardNum" id="imotherboardNum" value="${deviceModelTemplate.imotherboardNum}"
                                   class="layui-input"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmodel.electronic.lock.model" /></label>
                        <div class="layui-input-inline">
                            <input type="text" name="slocksModel" id="slocksModel" value="${deviceModelTemplate.slocksModel}" class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmodel.manufacturer.of.electronic.locks" /></label>
                        <div class="layui-input-inline">
                            <input type="text" name="slocksManufacturer" id="slocksManufacturer" value="${deviceModelTemplate.slocksManufacturer}"
                                   class="layui-input"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmodel.ad-level.screen.size" /></label>
                        <div class="layui-input-inline">
                            <input type="text" name="sadDimensions" id="sadDimensions" value="${deviceModelTemplate.sadDimensions}" class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmodel.description.of.advertising.machine.configuration" /></label>
                        <div class="layui-input-inline">
                            <input type="text" name="sadConf" id="sadConf" value="${deviceModelTemplate.sadConf}" class="layui-input"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmodel.number.of.compressors" /></label>
                        <div class="layui-input-inline">
                            <input type="number" name="icompressorNum" id="icompressorNum" value="${deviceModelTemplate.icompressorNum}" class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmodel.compressor.position" /></label>
                        <div class="layui-input-inline">
                            <input type="text" name="scompressorPosition" id="scompressorPosition" value="${deviceModelTemplate.scompressorPosition}"
                                   class="layui-input"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmodel.compressor.description" /></label>
                        <div class="layui-input-inline">
                            <input type="text" name="scompressorDesc" id="scompressorDesc" value="${deviceModelTemplate.scompressorDesc}"
                                   class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmodel.number.of.layers" /></label>
                        <div class="layui-input-inline">
                            <input type="number" name="ilayerNum" id="ilayerNum" value="${deviceModelTemplate.ilayerNum}" class="layui-input"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmodel.number.of.cargo.paths" /></label>
                        <div class="layui-input-inline">
                            <input type="number" name="icargoRoadNum" id="icargoRoadNum" value="${deviceModelTemplate.icargoRoadNum}" class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmodel.number.of.cameras" /></label>
                        <div class="layui-input-inline">
                            <input type="number" name="icameraNum" id="icameraNum" value="${deviceModelTemplate.icameraNum}" class="layui-input"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmodel.whether.to.use.extended.gpio" /></label>
                        <div class="layui-input-inline">
                            <cang:select type="list" name="iisUseExpandGpio" id="iisUseExpandGpio" entire="true"
                                         value="${deviceModelTemplate.iisUseExpandGpio}" list='{0:springMessageCode=main.no,1:springMessageCode=main.yes}'/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmodel.whether.to.detect.the.hall.value" /></label>
                        <div class="layui-input-inline">
                            <cang:select type="list" name="iisDetectHall" id="iisDetectHall" entire="true"
                                         value="${deviceModelTemplate.iisDetectHall}" list='{0:springMessageCode=main.no,1:springMessageCode=main.yes}'/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmodel.camera.method" /></label>
                        <div class="layui-input-inline">
                            <cang:select type="dic" name="scameraMethod" id="scameraMethod" entire="true"
                                         layFilter="scameraMethod" value="${deviceModelTemplate.scameraMethod}" groupNo="SP000171"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmodel.camera.model" /></label>
                        <div class="layui-input-inline">
                            <input type="text" name="scameraModel" id="scameraModel" value="${deviceModelTemplate.scameraModel}"
                                   class="layui-input" readonly/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmodel.camera.type" /></label>
                        <div class="layui-input-inline">
                            <input type="text" name="scameraType" id="scameraType" value="${deviceModelTemplate.scameraType}"
                                   class="layui-input" readonly/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmodel.camera.brand" /></label>
                        <div class="layui-input-inline">
                            <input type="text" name="scameraBrand" id="scameraBrand" value="${deviceModelTemplate.scameraBrand}"
                                   class="layui-input" readonly/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmodel.visual.service.provider" /></label>
                        <div class="layui-input-inline">
                            <cang:select type="dic" name="svisualServiceProvider" id="svisualServiceProvider"
                                         entire="true"
                                         value="${deviceModelTemplate.svisualServiceProvider}" groupNo="SP000172"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmodel.pcb.board.model" /></label>
                        <div class="layui-input-inline">
                            <cang:select type="dic" name="spcbModel" id="spcbModel" entire="true"
                                         layFilter="spcbModel" value="${deviceModelTemplate.spcbModel}"
                                         groupNo="SP000174"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmodel.open.pin.serial.number" /></label>
                        <div class="layui-input-inline">
                            <input type="number" name="iopendoorPinSn" id="iopendoorPinSn" value="${deviceModelTemplate.iopendoorPinSn}"
                                   class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmodel.hall.pin.serial.number" /></label>
                        <div class="layui-input-inline">
                            <input type="number" name="ihallPinSn" id="ihallPinSn" value="${deviceModelTemplate.ihallPinSn}"
                                   class="layui-input"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmodel.door.pin.serial.number" /></label>
                        <div class="layui-input-inline">
                            <input type="number" name="idoorPinSn" id="idoorPinSn" value="${deviceModelTemplate.idoorPinSn}"
                                   class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmodel.lock.pin.sequence.number" /></label>
                        <div class="layui-input-inline">
                            <input type="number" name="ilockCylinder" id="ilockCylinder" value="${deviceModelTemplate.ilockCylinder}"
                                   class="layui-input"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmodel.comparison.method" /></label>
                        <div class="layui-input-inline">
                            <cang:select type="list" name="scontrastMode" id="scontrastMode" entire="true"
                                         value="${deviceModelTemplate.scontrastMode}" list="{rawstock:springMessageCode=tpmodel.original.stock,openDoor:springMessageCode=tpmodel.switch.door}"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><spring:message code="main.remarks" /></label>
                    <div class="layui-input-block">
                        <textarea id="sremark" name="sremark" class="layui-textarea layui-form-textarea80p"
                                  placeholder='<spring:message code="main.remarks" />（<spring:message code="main.optional" />）'>${deviceModelTemplate.sremark}</textarea>
                    </div>
                </div>
                <div class="layui-form-item fixed-bottom">
                    <div class="layui-input-block">
                        <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
                        <button class="layui-btn" id="myFormSub"><spring:message code="main.save" /></button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js" type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var form;
    layui.use(['form', 'layedit', 'element'], function () {

        $('.layui-form .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

        form = layui.form;

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

        //监听摄像头方法select选择
        form.on('select(scameraMethod)',function (data) {
            var method = data.elem[data.elem.selectedIndex].text;   // 文本内容
            $.ajax({
                type:'post',
                url:'${ctx}/device/deviceModel/getCaremaInfo',
                data:{method:method},
                dataType:'json',
                success:function(resp){
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
        });

        $('.chosen-select').chosen();
    });

    $(function () {
        $("#cancelBtn").click(function () {
            parent.layer.close(index);
            return false;
        });
    });


</script>
</body>
</html>

