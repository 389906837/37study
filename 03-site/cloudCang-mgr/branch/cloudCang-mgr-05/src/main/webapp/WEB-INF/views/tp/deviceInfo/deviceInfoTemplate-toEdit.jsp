<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>增加设备基础信息模板</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
</head>
<body>
<div class="ibox-content">
    <div class="layui-tab-content">
        <div class="layui-tab-item layui-show">
            <form class="layui-form" action="${ctx}/deviceInfoTemplate/edit" method="post" id="myForm">
                <input type="hidden" name="id" id="id" value="${deviceInfoTemplate.id}" class="layui-input"/>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpinfo.template.name" /></label>
                        <div class="layui-input-inline">
                            <input type="text" name="sname" id="sname" datatype="*" nullmsg='<spring:message code="tpinfotoadd.please.enter.a.template.name" />'
                                   value="${deviceInfoTemplate.sname}" class="layui-input" placeholder='<spring:message code="tpinfotoadd.template.number" />...' />
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpinfotoadd.template.number" /></label>
                        <div class="layui-input-inline">
                            <input type="text" name="scode" id="scode" value="${deviceInfoTemplate.scode}"
                                   class="layui-input" disabled/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpinfotoadd.equipment.type" /></label>
                        <div class="layui-input-inline">
                            <cang:select type="dic" name="itype" id="itype" entire="true"
                                         value="${deviceInfoTemplate.itype}" groupNo="SP000144"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpinfotoadd.cooperation.mode" /></label>
                        <div class="layui-input-inline">
                            <cang:select type="dic" name="icooperationMode" id="icooperationMode" entire="true"
                                         value="${deviceInfoTemplate.icooperationMode}"
                                         groupNo="SP000104"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpinfotoadd.reader.serial.number" /></label>
                        <div class="layui-input-inline">
                            <input type="text" name="sreaderSerialNumber" id="sreaderSerialNumber"
                                   value="${deviceInfoTemplate.sreaderSerialNumber}"
                                   class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpinfotoadd.equipment.model" /></label>
                        <div class="layui-input-inline">
                            <input type="text" name="sdeviceModel" id="sdeviceModel"
                                   value="${deviceInfoTemplate.sdeviceModel}" class="layui-input"/>
                        </div>
                    </div>
                </div>
                <%--<div class="layui-form-item">--%>
                    <%--<div class="layui-col-md6">--%>
                        <%--<label class="layui-form-label"><spring:message code="tpinfotoadd.provinces.put.into.operation" /></label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<select name="sprovinceName" id="sprovinceName" class="state">--%>
                                <%--<option value="${deviceInfoTemplate.sprovinceName}">请选择</option>--%>
                                <%--<c:forEach items="${provinceSet}" var="vo">--%>
                                    <%--<option value="${vo.id}_${vo.sname}">${vo.sname}</option>--%>
                                <%--</c:forEach>--%>
                            <%--</select>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="layui-col-md6">--%>
                        <%--<label class="layui-form-label">投放城市</label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<select name="scityName" id="scityName" class="state">--%>
                            <%--</select>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <div class="layui-form-item">
                    <%--<div class="layui-col-md6">--%>
                        <%--<label class="layui-form-label">投放区县</label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<select name="sareaName" id="sareaName" class="state">--%>
                            <%--</select>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpinfotoadd.container.type" /></label>
                        <div class="layui-input-inline">
                            <cang:select type="list" name="icontainerType" id="icontainerType" entire="true"
                                         value="${deviceInfoTemplate.icontainerType}" list="{10:springMessageCode=tpinfotoadd.single.door,20:springMessageCode=tpinfotoadd.double.door}"/>
                        </div>
                    </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code="tpinfotoadd.screen.display.type" /></label>
                            <div class="layui-input-inline">
                                <cang:select type="dic" groupNo="SP000175" entire="true"
                                             exp="springMessageCode=tpinfotoadd.please.select.the.screen.display.type"
                                             value="${deviceInfoTemplate.iscreenDisplayType}" entireName='springMessageCode=tpinfotoadd.please.select.the.screen.display.type'
                                             name="iscreenDisplayType" id="iscreenDisplayType"/>
                            </div>
                        </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpinfotoadd.compressor.type" /></label>
                        <div class="layui-input-inline">
                            <cang:select type="list" name="icompressorType" id="icompressorType" entire="true"
                                         value="${deviceInfoTemplate.icompressorType}"
                                         list="{10:springMessageCode=tpinfotoadd.refrigeration,20:springMessageCode=tpinfotoadd.heating,30:springMessageCode=tpinfotoadd.cooling.heat,40:springMessageCode=tpinfotoadd.no.compressor}"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpinfotoadd.whether.advertising.machine" /></label>
                        <div class="layui-input-inline">
                            <cang:select type="list" name="iinstallAd" id="iinstallAd" entire="true"
                                         value="${deviceInfoTemplate.iinstallAd}"
                                         list='{0:springMessageCode=main.no,1:springMessageCode=main.yes}'/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpinfotoadd.device.delivery.scenario" /></label>
                        <div class="layui-input-inline">
                            <cang:select type="dic" name="sputScenes" id="sputScenes" entire="true" laySearch="1"
                                         value="${deviceInfoTemplate.sputScenes}" groupNo="SP000143"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpinfotoadd.whether.to.support.small.screen.ai.devices" /></label>
                        <div class="layui-input-inline">
                            <cang:select type="list" name="isupportAi" id="isupportAi" entire="true"
                                         value="${deviceInfoTemplate.isupportAi}" list='{0:springMessageCode=main.no,1:springMessageCode=main.yes}'/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpinfotoadd.whether.to.support.weighing" /></label>
                        <div class="layui-input-inline">
                            <cang:select type="list" name="isupportWeigh" id="isupportWeigh" entire="true"
                                         value="${deviceInfoTemplate.isupportWeigh}" list='{0:springMessageCode=main.no,1:springMessageCode=main.yes}'/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpinfotoadd.electronic.scales.allow.floating.values" /></label>
                        <div class="layui-input-inline">
                            <input type="text" name="ielectronicFloat" id="ielectronicFloat"
                                   value="${deviceInfoTemplate.ielectronicFloat}"
                                   class="layui-input"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpinfotoadd.system.docking.type" /></label>
                        <div class="layui-input-inline">
                            <cang:select type="list" name="idockingType" id="idockingType"
                                         entire="true" value="${deviceInfoTemplate.idockingType}"
                                         list="{10:springMessageCode=tpinfotoadd.independent.research.and.development,20:springMessageCode=tpinfotoadd.third.party}"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpinfotoadd.whether.to.open.the.door.to.shop.in.real.time" /></label>
                        <div class="layui-input-inline">
                            <cang:select type="list" name="iisOpeningInventory" id="iisOpeningInventory"
                                         entire="true" value="${deviceInfoTemplate.iisOpeningInventory}"
                                         list='{0:springMessageCode=main.no,1:springMessageCode=main.yes}'/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><spring:message code="sm.remarks" /></label>
                    <div class="layui-input-block">
                        <textarea id="sremark" name="sremark" class="layui-textarea layui-form-textarea80p"
                                  placeholder='<spring:message code="sh.remarks" />（<spring:message code="main.optional" />）'>${deviceInfoTemplate.sremark}</textarea>
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

