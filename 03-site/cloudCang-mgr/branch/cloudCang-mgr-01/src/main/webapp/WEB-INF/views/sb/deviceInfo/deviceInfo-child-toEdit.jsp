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
                            <label class="layui-form-label">设备名称</label>
                            <div class="layui-input-inline">
                                <input type="text" name="sname" id="sname" datatype="*" nullmsg="请输入设备名称"
                                       value="${deviceInfo.sname}"
                                       class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label">设备分组</label>
                            <div class="layui-input-inline">
                                <select name="sgroup_id" id="sgroup_id" datatype="*" nullmsg="请选择设备分组" class="state" lay-filter="sgroup_id">
                                    <option value="">请选择</option>
                                    <c:forEach items="${groupManageLists}" var="vo">
                                        <option value="${vo.id}" <c:if test="${vo.id eq groupRelationship.sgroupId}">selected</c:if>>${vo.sgroupName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label">投放省份</label>
                            <div class="layui-input-inline">
                                <select name="sprovinceName" id="sprovinceName" class="state"
                                        datatype="*"  nullmsg="请选择投放省份" lay-filter="province">
                                    <option value="">请选择</option>
                                    <c:forEach items="${provinceSet}" var="vo">
                                        <option value="${vo.id}_${vo.sname}"
                                                <c:if test="${vo.id eq deviceInfo.sprovinceId}">selected</c:if>>${vo.sname}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label">投放城市</label>
                            <div class="layui-input-inline">
                                <select name="scityName" id="scityName" class="state"
                                        datatype="*"  nullmsg="请选择投放城市" lay-filter="city">
                                    <option value="">请选择</option>
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
                            <label class="layui-form-label">投放区县</label>
                            <div class="layui-input-inline">
                                <select name="sareaName" id="sareaName" class="state"
                                        datatype="*"  nullmsg="请选择投放区县" lay-filter="town">
                                    <option value="">请选择</option>
                                    <c:forEach items="${townsSet}" var="vo">
                                        <option value="${vo.id}_${vo.sname}"
                                                <c:if test="${vo.id eq deviceInfo.sareaId}">selected</c:if>>${vo.sname}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label">投放详细地址</label>
                            <div class="layui-input-inline">
                                <input type="text" name="sputAddress" id="sputAddress"
                                       value="${deviceInfo.sputAddress}" class="layui-input"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label">区域负责人</label>
                            <div class="layui-input-inline">
                                <input type="text" name="sareaPrincipal" id="sareaPrincipal"
                                       value="${deviceManage.sareaPrincipal}"
                                       class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label">区域负责人联系方式</label>
                            <div class="layui-input-inline">
                                <input type="text" name="sareaPrincipalContact" id="sareaPrincipalContact"
                                       value="${deviceManage.sareaPrincipalContact}" class="layui-input"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label">设备负责人</label>
                            <div class="layui-input-inline">
                                <input type="text" name="sdevicePrincipal" id="sdevicePrincipal"
                                       value="${deviceManage.sdevicePrincipal}" class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label">设备负责人联系方式</label>
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
                            <label class="layui-form-label">补货员姓名</label>
                            <div class="layui-input-inline">
                                <input type="text" name="sreplenishment" id="sreplenishment"
                                       value="${deviceManage.sreplenishment}"
                                       class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label">补货员联系方式</label>
                            <div class="layui-input-inline">
                                <input type="text" <%--lay-verify="number"--%> name="sreplenishmentContact"
                                       id="sreplenishmentContact" value="${deviceManage.sreplenishmentContact}"
                                       class="layui-input"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label">设备维护人姓名</label>
                            <div class="layui-input-inline">
                                <input type="text" name="smaintain" id="smaintain" value="${deviceManage.smaintain}"
                                       class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label">维护人联系方式</label>
                            <div class="layui-input-inline">
                                <input type="text" <%--lay-verify="number"--%> name="smaintainContact"
                                       id="smaintainContact"
                                       value="${deviceManage.smaintainContact}" class="layui-input"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label">所属区域</label>
                            <div class="layui-input-inline">
                                <%--<cang:select type="dic" name="sareaCode" id="sareaCode" entire="true" exp="datatype=\"*\" nullmsg=\"请选择所属区域\""--%>
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
                <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                <button class="layui-btn" id="myFormSub">保存</button>
            </div>
        </div>
        <input type="hidden" id="smerchantId" name="smerchantId" value="${deviceInfo.smerchantId}"/><%--商户ID--%>
        <input type="hidden" id="smerchantCode" name="smerchantCode" value="${deviceInfo.smerchantCode}"/><%--商户ID--%>
        <input type="hidden" id="deviceInfoId" name="deviceInfoId" value="${deviceInfo.id}"/>
        <input type="hidden" id="scode" name="scode" value="${deviceInfo.scode}"/>
        <input type="hidden" id="deviceManageId" name="deviceManageId" value="${deviceManage.id}"/><%--设备管理ID--%>
        <input type="hidden" id="oldGroupId" name="oldGroupId" value="${groupRelationship.id}"/><%--设备分组关系ID--%>
    </form>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
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
                        alertDelAndReload("操作异常，请重新操作");
                    },
                    success: function (msg) {
                        //成功返回
                        closeLayer(loadIndex);//关闭加载框
                        if (msg.success) {
                            alertSuccess("操作成功", {
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
                    var option1 = $("<option>").val("").text("请选择");
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
                    var option1 = $("<option>").val("").text("请选择");
                    $("#scityName").append(option1);
                    $("#sareaName").html("");
                    var option1 = $("<option>").val("").text("请选择");
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

