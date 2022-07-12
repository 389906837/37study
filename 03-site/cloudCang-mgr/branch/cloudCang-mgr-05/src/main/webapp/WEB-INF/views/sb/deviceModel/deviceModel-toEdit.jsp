<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>编辑设备详细信息</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/device/deviceModel/edit" method="post" id="myForm">
        <input type="hidden" id="scameraMethod1" name="scameraMethod1" value="${deviceModel.scameraMethod}"/><%--<spring:message code='tpmodel.camera.method' />--%>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sb.equipment.core.model" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sdeviceModel"  id="sdeviceModel" value="${deviceModel.sdeviceModel}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label" ><spring:message code='tpmodel.core.manufacturer' /></label>
                <div class="layui-input-inline" >
                    <input type="text" name="smanufacturer"  id="smanufacturer" value="${deviceModel.smanufacturer}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='tpmodel.core.configuration.description' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="scoreDesc"  id="scoreDesc" value="${deviceModel.scoreDesc}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='tpmodel.overall.shape.size' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sdimensions"  id="sdimensions" value="${deviceModel.sdimensions}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='tpmodel.weight' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sweight"  id="sweight" value="${deviceModel.sweight}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='tpmodel.rated.power' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sratedPower"  id="sratedPower" value="${deviceModel.sratedPower}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='tpmodel.commodity.capacity' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sproductCapacity"  id="sproductCapacity" value="${deviceModel.sproductCapacity}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='tpmodel.commodity.types' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sproductTypes"  id="sproductTypes" value="${deviceModel.sproductTypes}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='tpmodel.daily.power.consumption' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sdailyPower"  id="sdailyPower" value="${deviceModel.sdailyPower}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='sl.payment.method' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="spayWap"  id="spayWap" value="${deviceModel.spayWap}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='tpmodel.anti-shock.type' /></label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="ielectricShock" id="ielectricShock" entire="true" value="${deviceModel.ielectricShock}"  list="{10:0<spring:message code='sb.class,20:I<spring:message code='sb.class,30:II<spring:message code='sb.class,40:III<spring:message code='sb.class}"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='tpmodel.electronic.lock.model' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="slocksModel"  id="slocksModel" value="${deviceModel.slocksModel}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='tpmodel.manufacturer.of.electronic.locks' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="slocksManufacturer"  id="slocksManufacturer" value="${deviceModel.slocksManufacturer}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='tpmodel.screen.dimension.instructions.for.advertising.machines' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sadDimensions"  id="sadDimensions" value="${deviceModel.sadDimensions}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='sb.advertising.machine.configuration.description' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sadConf"  id="sadConf" value="${deviceModel.sadConf}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='tpmodel.number.of.compressors' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="icompressorNum"  id="icompressorNum" value="${deviceModel.icompressorNum}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='tpmodel.compressor.position' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="scompressorPosition"  id="scompressorPosition" value="${deviceModel.scompressorPosition}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='tpmodel.compressor.description' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="scompressorDesc"  id="scompressorDesc" value="${deviceModel.scompressorDesc}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='tpmodel.number.of.layers' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="ilayerNum"  id="ilayerNum" value="${deviceModel.ilayerNum}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='tpmodel.number.of.cargo.paths' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="icargoRoadNum"  id="icargoRoadNum" value="${deviceModel.icargoRoadNum}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code='tpmodel.number.of.cameras' /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="icameraNum" id="icameraNum"
                               value="${deviceModel.icameraNum}" class="layui-input"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code='tpmodel.number.of.motherboard.blocks' /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="imotherboardNum" id="imotherboardNum"
                               value="${deviceModel.imotherboardNum}"
                               class="layui-input"/>
                    </div>
                </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='tpmodel.whether.to.use.extended.gpio' /></label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="iisUseExpandGpio" id="iisUseExpandGpio" entire="true"
                                 value="${deviceModel.iisUseExpandGpio}" list='{0:springMessageCode=main.no,1:springMessageCode=main.yes}'/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='tpmodel.whether.to.detect.the.hall.value' /></label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="iisDetectHall" id="iisDetectHall" entire="true"
                                 value="${deviceModel.iisDetectHall}" list='{0:springMessageCode=main.no,1:springMessageCode=main.yes}'/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='tpmodel.camera.method' /></label>
                <div class="layui-input-inline">
                    <cang:select type="dic" name="scameraMethod" id="scameraMethod" entire="true"
                                 layFilter="scameraMethod" value="${deviceModel.scameraModel}" groupNo="SP000171"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='tpmodel.camera.model' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="scameraModel" id="scameraModel" value="${deviceModel.scameraModel}"
                           class="layui-input" readonly/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='tpmodel.camera.type' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="scameraType" id="scameraType" value="${deviceModel.scameraType}"
                           class="layui-input" readonly/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='tpmodel.camera.brand' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="scameraBrand" id="scameraBrand" value="${deviceModel.scameraBrand}"
                           class="layui-input" readonly/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='tpmodel.visual.service.provider' /></label>
                <div class="layui-input-inline">
                    <cang:select type="dic" name="svisualServiceProvider" id="svisualServiceProvider" entire="true"
                                 value="${deviceModel.svisualServiceProvider}" groupNo="SP000172"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='tpmodel.pcb.board.model' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="spcbModel" id="spcbModel" value="${deviceModel.spcbModel}"
                           class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='tpmodel.open.pin.serial.number' /></label>
                <div class="layui-input-inline">
                    <input type="number" name="iopendoorPinSn" id="iopendoorPinSn" value="${deviceModel.iopendoorPinSn}" min="0"
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='tpmodel.hall.pin.serial.number' /></label>
                <div class="layui-input-inline">
                    <input type="number" name="ihallPinSn" id="ihallPinSn" value="${deviceModel.ihallPinSn}" min="0"
                           class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='tpmodel.door.pin.serial.number' /></label>
                <div class="layui-input-inline">
                    <input type="number" name="idoorPinSn" id="idoorPinSn" value="${deviceModel.idoorPinSn}" min="0"
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='tpmodel.lock.pin.sequence.number' /></label>
                <div class="layui-input-inline">
                    <input type="number" name="ilockCylinder" id="ilockCylinder" value="${deviceModel.ilockCylinder}" min="0"
                           class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='tpmodel.comparison.method' /></label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="scontrastMode" id="scontrastMode" entire="true"
                                 value="${deviceModel.scontrastMode}" list="{rawstock:springMessageCode=tpmodel.original.stock,openDoor:springMessageCode=tpmodel.switch.door}"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="main.remarks" /></label>
                <div class="layui-input-inline">
                    <textarea id="sremark" name="sremark" style="width: 735px" class="layui-textarea" placeholder="<spring:message code='main.not.required' />">${deviceModel.sremark}</textarea>
                </div>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
                <button class="layui-btn" id="myFormSub"><spring:message code="main.save" /></button>
            </div>
        </div>
        <input type="hidden"  name="id" id="mdcId" value="${deviceModel.id}" class="layui-input" />
        <input type="hidden"  name="sdeviceId" id="sdeviceId" value="${deviceModel.sdeviceId}" class="layui-input" />
    </form>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js"  type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var form;
    layui.use(['form', 'layedit', 'laydate'], function () {
        form = layui.form;


        $("#myForm").Validform({
            btnSubmit:"#myFormSub",  //根据id触发
            tiptype:3,                  //第三种方式
            showAllError:true,
            beforeSubmit:function(){ //验证过后执行save方法
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

