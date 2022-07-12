<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备编辑</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
</head>
<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/device/info/childEdit" method="post" id="myForm">
        <div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
            <div class="layui-tab-content">
                <div class="layui-tab-item layui-show">
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code="main.device.name" /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="sname" id="sname" datatype="*" nullmsg='<spring:message code="main.device.name.empty" />'
                                       value="${deviceInfo.sname}"
                                       class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.device.grouping'/></label>
                            <div class="layui-input-inline">
                                <select name="sgroup_id" id="sgroup_id" datatype="*" nullmsg='<spring:message code="sb.device.grouping.select" />' class="state" lay-filter="sgroup_id">
                                    <option value=""><spring:message code="main.please.choose" /></option>
                                    <c:forEach items="${groupManageLists}" var="vo">
                                        <option value="${vo.id}" <c:if test="${vo.id eq groupRelationship.sgroupId}">selected</c:if>>${vo.sgroupName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.provinces.put.into.operation'/></label>
                            <div class="layui-input-inline">
                                <select name="sprovinceName" id="sprovinceName" class="state"  lay-search=""
                                        datatype="*"  nullmsg="<spring:message code='sb.please.select.a.province.to.serve'/>" lay-filter="province">
                                    <option value=""><spring:message code="main.please.choose" /></option>
                                    <c:forEach items="${provinceSet}" var="vo">
                                        <option value="${vo.id}_${vo.sname}"
                                                <c:if test="${vo.id eq deviceInfo.sprovinceId}">selected</c:if>>${vo.sname}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.put.it.into.the.city'/>
</label>
                            <div class="layui-input-inline">
                                <select name="scityName" id="scityName" class="state"  lay-search=""
                                        datatype="*"  nullmsg="<spring:message code='sb.please.select.a.city.to.launch'/>
" lay-filter="city">
                                    <option value=""><spring:message code="main.please.choose" /></option>
                                    <c:forEach items="${citySet}" var="vo">
                                        <option value="${vo.id}_${vo.sname}"
                                                <c:if test="${vo.id eq deviceInfo.scityId}">selected</c:if>>${vo.sname}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.delivery.to.districts.and.counties'/>
</label>
                            <div class="layui-input-inline">
                                <select name="sareaName" id="sareaName" class="state" lay-search=""
                                        datatype="*"  nullmsg="<spring:message code='sb.please.select.the.county.to.be.placed'/>
" lay-filter="town">
                                    <option value=""><spring:message code="main.please.choose" /></option>
                                    <c:forEach items="${townsSet}" var="vo">
                                        <option value="${vo.id}_${vo.sname}"
                                                <c:if test="${vo.id eq deviceInfo.sareaId}">selected</c:if>>${vo.sname}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.put.in.the.detailed.address' /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="sputAddress" id="sputAddress"
                                       value="${deviceInfo.sputAddress}" class="layui-input"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.screen.display.type'/>
</label>
                            <div class="layui-input-inline">
                                <cang:select type="dic" groupNo="SP000175" entire="true"
                                             exp="springMessageCode=sb.please.select.the.screen.display.type"
                                             value="${deviceInfo.iscreenDisplayType}" entireName='springMessageCode=sb.please.select.the.screen.display.type'
                                             name="iscreenDisplayType" id="iscreenDisplayType"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.regional.director'/>
</label>
                            <div class="layui-input-inline">
                                <input type="text" name="sareaPrincipal" id="sareaPrincipal"
                                       value="${deviceManage.sareaPrincipal}"
                                       class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.contact.information.of.regional.leaders' /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="sareaPrincipalContact" id="sareaPrincipalContact"
                                       value="${deviceManage.sareaPrincipalContact}" class="layui-input"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.equipment.person.in.charge'/>
</label>
                            <div class="layui-input-inline">
                                <input type="text" name="sdevicePrincipal" id="sdevicePrincipal"
                                       value="${deviceManage.sdevicePrincipal}" class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.contact.form.of.equipment.leader' /></label>
                            <div class="layui-input-inline">
                                <input type="text" <%--lay-verify="unCheckPhone|phone"--%>
                                       name="sdevicePrincipalContact"
                                       id="sdevicePrincipalContact" value="${deviceManage.sdevicePrincipalContact}"
                                       class="layui-input"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.name.of.replenisher' /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="sreplenishment" id="sreplenishment"
                                       value="${deviceManage.sreplenishment}"
                                       class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.contact.information.of.replenisher' /></label>
                            <div class="layui-input-inline">
                                <input type="text" <%--lay-verify="number"--%> name="sreplenishmentContact"
                                       id="sreplenishmentContact" value="${deviceManage.sreplenishmentContact}"
                                       class="layui-input"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.name.of.equipment.maintenance.person' /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="smaintain" id="smaintain" value="${deviceManage.smaintain}"
                                       class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.maintenance.contact.information' /></label>
                            <div class="layui-input-inline">
                                <input type="text" <%--lay-verify="number"--%> name="smaintainContact"
                                       id="smaintainContact"
                                       value="${deviceManage.smaintainContact}" class="layui-input"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='sb.areas.to.which.they.belong'/>
</label>
                            <div class="layui-input-inline">
                                <%--<cang:select type="dic" name="sareaCode" id="sareaCode" entire="true" exp="springMessageCode=sb.please.select.your.region'/>
\""--%>
                                <%--value="${deviceManage.sareaCode}" groupNo="SP000145"/>--%>
                                <cang:select type="dic" name="sareaCode" id="sareaCode" entire="true"
                                             value="${deviceManage.sareaCode}" groupNo="SP000145"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
                <button class="layui-btn" id="myFormSub"><spring:message code="main.save" /></button>
            </div>
        </div>
        <input type="hidden" id="smerchantId" name="smerchantId" value="${deviceInfo.smerchantId}"/><%--<spring:message code='sb.merchant.id' />--%>
        <input type="hidden" id="smerchantCode" name="smerchantCode" value="${deviceInfo.smerchantCode}"/><%--<spring:message code='sb.merchant.id' />--%>
        <input type="hidden" id="deviceInfoId" name="deviceInfoId" value="${deviceInfo.id}"/>
        <input type="hidden" id="scode" name="scode" value="${deviceInfo.scode}"/>
        <input type="hidden" id="deviceManageId" name="deviceManageId" value="${deviceManage.id}"/><%--<spring:message code='sb.equipment.management.id' />--%>
        <input type="hidden" id="oldGroupId" name="oldGroupId" value="${groupRelationship.id}"/><%--<spring:message code='sb.device.grouping.relationship.id' />--%>
    </form>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js"  type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit'], function () {
        var form = layui.form;

        $('.layui-form .layui-btn').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

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

