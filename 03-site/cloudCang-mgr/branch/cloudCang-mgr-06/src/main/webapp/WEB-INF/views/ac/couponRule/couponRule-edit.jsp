<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>送券规则</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?20188" rel="stylesheet">
<style type="text/css">
    .layui-form-label {width: 150px;}
</style>
</head>
<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/couponRule/save" method="post" id="myForm">
        <div class="wfsplitt">
            基础规则
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">是否有效</label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="iisValid" id="iisValid"
                                 value="${couponRule.iisValid}" list="{1:启用,0:禁用}"/>
                </div>
            </div>
            <%--<div class="layui-col-md6">
                <label class="layui-form-label">规则编号</label>
                <div class="layui-input-inline">
                    <input type="text" name="scode" id="scode" disabled="disabled" value="${couponRule.scode}" class="layui-input"/>
                </div>
            </div>--%>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">券类型</label>
                <div class="layui-input-inline">
                    <cang:select type="dic" groupNo="SP000004" name="icouponType" layFilter="icouponType"
                                 id="icouponType" value="${empty couponRule.icouponType ? 20:couponRule.icouponType}" />
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">送券方式</label>
                <div class="layui-input-inline">
                    <cang:select type="dic" groupNo="SP000006" name="igiveMethod" layFilter="igiveMethod"

                                 id="igiveMethod" value="${empty couponRule.igiveMethod ? 20 : couponRule.igiveMethod}" />
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">最小券值(元)</label>
                <div class="layui-input-inline">
                    <input type="text" name="fminValue" id="fminValue"
                           value="${couponRule.fminValue}" <c:if test="${couponRule.igiveMethod ne 10}">disabled="disabled"</c:if> class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">最大券值(元)</label>
                <div class="layui-input-inline">
                    <input type="text" name="fmaxValue" id="fmaxValue"
                           value="${couponRule.fmaxValue}" <c:if test="${couponRule.igiveMethod ne 10}">disabled="disabled"</c:if> class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">送劵面值(元或%)</label>
                <div class="layui-input-inline">
                    <input type="text" name="fcouponValue" id="fcouponValue"
                           value='<fmt:formatNumber value="${couponRule.fcouponValue}" pattern="#0.##"/>' class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">商品信息</label>
                <div class="layui-input-inline" style="width: 115px">
                    <input type="text" name="scommodityCode" id="scommodityCode"<c:if test="${couponRule.icouponType ne 40}"> disabled="disabled"</c:if> value="${couponRule.scommodityCode}" class="layui-input" />
                    <input type="hidden" name="scommodityId" id="scommodityId" value="${couponRule.scommodityId}" class="layui-input" />
                </div>
                <button class="layui-btn" id="selectSp" style="display: inline-block;float: left;" type="button"<c:if test="${couponRule.icouponType ne 40}"> disabled="disabled"</c:if> data-type="sel">选择</button>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">券有效期天数</label>
                <div class="layui-input-inline">
                    <input type="text" name="icouponValidityValue" id="icouponValidityValue" datatype="n" nullmsg="请输入券有效期天数" errormsg="券有效期天数必须是正整数"
                           value="${couponRule.icouponValidityValue}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">送券数量(张)</label>
                <div class="layui-input-inline">
                    <input type="text" name="isendNumber" id="isendNumber" datatype="n" nullmsg="请输入送券数量" errormsg="送券数量必须是数字"
                           value="${empty couponRule.isendNumber ? 1 : couponRule.isendNumber}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">券生效日期</label>
                <div class="layui-input-inline">
                    <input type="text" name="dcouponEffectiveDate" datatype="*" nullmsg="请选择券生效日期" id="dcouponEffectiveDate"
                           value="<fmt:formatDate value='${couponRule.dcouponEffectiveDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">券失效日期</label>
                <div class="layui-input-inline">
                    <input type="text" name="dcouponExpiryDate" id="dcouponExpiryDate" disabled="disabled"
                           value="<fmt:formatDate value='${couponRule.dcouponExpiryDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="wfsplitt">
            说明
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">券简述</label>
                <div class="layui-input-inline">
                    <input type="text" name="sbriefDesc" datatype="*" nullmsg="请输入券简述" id="sbriefDesc"
                           value="${couponRule.sbriefDesc}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">劵说明</label>
                <div class="layui-input-inline" style="width: 350px">
                    <textarea class="layui-textarea" datatype="*" nullmsg="请输入劵说明" id="sactivityInstruction"
                              name="sactivityInstruction">${couponRule.sactivityInstruction}</textarea>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">赠送说明</label>
                <div class="layui-input-inline" style="width: 350px">
                    <textarea class="layui-textarea" datatype="*" nullmsg="请输入赠送说明" id="sremark" name="sremark">${couponRule.sremark}</textarea>
                </div>
            </div>
        </div>
        <c:if test="${iscenesType eq 51 or iscenesType eq 60 or iscenesType eq 70 or itype eq 20}">
            <div class="wfsplitt">
                赠送规则
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">订单实付金额(元):</label>
                    <div class="layui-input-inline">
                        <input type="text" name="fgiveLimitAmount" id="fgiveLimitAmount" placeholder="0不限制"
                               value="<c:if test="${!empty couponRule.fgiveLimitAmount}"><fmt:formatNumber value="${couponRule.fgiveLimitAmount }" pattern="#0.##"/></c:if>" class="layui-input"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label">商品限制</label>
                    <div class="layui-input-inline" style="width: 260px">
                        <textarea class="layui-textarea" id="sgiveLimitCommodity" name="sgiveLimitCommodity" readonly="readonly" placeholder="空不限制">${couponRule.sgiveLimitCommodity}</textarea>
                    </div>
                        <button class="layui-btn" id="giveCommodity" style="display: inline-block;float: left;" type="button" data-type="sel">选择</button>
                </div>
            </div>
            <%--<div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">设备限制</label>
                    <div class="layui-input-inline" style="width: 260px">
                        <textarea class="layui-textarea" id="sgiveLimitDevice" name="sgiveLimitDevice" readonly="readonly" placeholder="空不限制">${couponRule.sgiveLimitDevice}</textarea>
                    </div>
                    <button class="layui-btn" id="giveDevice" style="display: inline-block;float: left;" type="button" data-type="sel">选择</button>
                </div>
            </div>--%>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">商品分类限制</label>
                    <div class="layui-input-inline" style="width: 260px">
                        <textarea class="layui-textarea" id="giveLimitCategory" name="giveLimitCategory" readonly="readonly" placeholder="空不限制">${giveLimitCategory}</textarea>
                    </div>
                    <button class="layui-btn layui-btn-primary" id="giveCategory" style="display: inline-block;float: left;" type="button" data-type="sel">选择</button>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label">商品品牌限制</label>
                    <div class="layui-input-inline" style="width: 260px">
                        <textarea class="layui-textarea" id="giveLimitBrand" name="giveLimitBrand" readonly="readonly" placeholder="空不限制">${giveLimitBrand}</textarea>
                    </div>
                    <button class="layui-btn layui-btn-primary" id="giveBrand" style="display: inline-block;float: left;" type="button" data-type="sel">选择</button>
                </div>
            </div>
        </c:if>
        <div class="wfsplitt">
            使用规则
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">订单实付金额(元):</label>
                <div class="layui-input-inline">
                    <input type="text" name="fuseLimitAmount" id="fuseLimitAmount" placeholder="0不限制"
                           value="<c:if test="${!empty couponRule.fuseLimitAmount}"><fmt:formatNumber value="${couponRule.fuseLimitAmount }" pattern="#0.##"/></c:if>" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">商品限制</label>
                <div class="layui-input-inline" style="width: 260px">
                    <textarea class="layui-textarea" id="suseLimitCommodity" name="suseLimitCommodity" readonly="readonly" placeholder="空不限制">${couponRule.suseLimitCommodity}</textarea>
                </div>
                <button class="layui-btn" id="useCommodity" style="display: inline-block;float: left;" type="button" data-type="sel">选择</button>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">设备限制</label>
                <div class="layui-input-inline" style="width: 260px">
                    <textarea class="layui-textarea" id="suseLimitDevice" name="suseLimitDevice" readonly="readonly" placeholder="空不限制">${couponRule.suseLimitDevice}</textarea>
                </div>
                <button class="layui-btn" id="useDevice" style="display: inline-block;float: left;" type="button" data-type="sel">选择</button>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">商品分类限制</label>
                <div class="layui-input-inline" style="width: 260px">
                    <textarea class="layui-textarea" id="useLimitCategory" name="useLimitCategory" readonly="readonly" placeholder="空不限制">${useLimitCategory}</textarea>
                </div>
                <button class="layui-btn layui-btn-primary" id="useCategory" style="display: inline-block;float: left;" type="button" data-type="sel">选择</button>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">商品品牌限制</label>
                <div class="layui-input-inline" style="width: 260px">
                    <textarea class="layui-textarea" id="useLimitBrand" name="useLimitBrand" readonly="readonly" placeholder="空不限制">${useLimitBrand}</textarea>
                </div>
                <button class="layui-btn layui-btn-primary" id="useBrand" style="display: inline-block;float: left;" type="button" data-type="sel">选择</button>
            </div>
        </div>

        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                <button class="layui-btn" id="myFormSub">保存</button>
            </div>
        </div>

        <c:if test="${iscenesType eq 51 or iscenesType eq 60 or iscenesType eq 70 or itype eq 20}">
            <%--<input type="hidden" id="giveDeviceIds" name="giveDeviceIds" value="${giveDeviceIds}"/>--%>
            <input type="hidden" id="giveCommodityIds" name="giveCommodityIds" value="${giveCommodityIds}"/>
            <input type="hidden" id="sgiveLimitCategory" name="sgiveLimitCategory" value="${couponRule.sgiveLimitCategory}"/>
            <input type="hidden" id="sgiveLimitBrand" name="sgiveLimitBrand" value="${couponRule.sgiveLimitBrand}"/>
        </c:if>

        <input type="hidden" id="useDeviceIds" name="useDeviceIds" value="${useDeviceIds}"/>
        <input type="hidden" id="useCommodityIds" name="useCommodityIds" value="${useCommodityIds}"/>
        <input type="hidden" id="suseLimitBrand" name="suseLimitBrand" value="${couponRule.suseLimitBrand}"/>
        <input type="hidden" id="suseLimitCategory" name="suseLimitCategory" value="${couponRule.suseLimitCategory}"/>

        <input type="hidden" id="id" name="id" value="${couponRule.id}"/>
        <input type="hidden" id="sacId" name="sacId" value="${couponRule.sacId}"/>
        <input type="hidden" id="sacCode" name="sacCode" value="${couponRule.sacCode}"/>
        <input type="hidden" id="scode" name="scode" value="${couponRule.scode}"/>
    </form>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js"  type="text/javascript"></script>


<script type="text/javascript">
    function initForm() {
        $("#igiveMethod").attr("disabled", "disabled");
        $("#scommodityCode").removeAttr("disabled");
        $("#selectSp").removeAttr("disabled");
        $("#fcouponValue").val("");
        $("#fcouponValue").attr("disabled", "disabled");

        //取消商品使用限制条件
        $("#fuseLimitAmount").attr("disabled", "disabled");
        $("#fuseLimitAmount").val("");

        $("#useCommodity").attr("disabled", "disabled");
        //$("#useDevice").attr("disabled", "disabled");
        $("#useCategory").attr("disabled", "disabled");
        $("#useBrand").attr("disabled", "disabled");

        $("#suseLimitCommodity").val("");
        //$("#suseLimitDevice").val("");
        $("#useLimitCategory").val("");
        $("#useLimitBrand").val("");

        $("#useCommodityIds").val("");
        //$("#useDeviceIds").val("");
        $("#suseLimitCategory").val("");
        $("#suseLimitBrand").val("");


        $("#fminValue").attr("disabled", "disabled");
        $("#fmaxValue").attr("disabled", "disabled");
        $("#fmaxValue").val("");
        $("#fminValue").val("");
    }
</script>


<c:if test="${(iscenesType ne 51 and iscenesType ne 60 and iscenesType ne 70 and itype ne 20)}">
<script type="text/javascript">
    $("#igiveMethod").attr("disabled", "disabled");
</script>
</c:if>
<c:if test="${couponRule.icouponType eq 40}">
    <script type="text/javascript">
        initForm();
        //form.render('select');
    </script>
</c:if>
<c:if test="${couponRule.icouponType eq 30}">
    <script type="text/javascript">
        $("#fuseLimitAmount").attr("disabled", "disabled");
        $("#fuseLimitAmount").val("");
    </script>
</c:if>
<c:if test="${couponRule.igiveMethod ne 10}">
    <script type="text/javascript">
        $("#fmaxValue").val("");
        $("#fminValue").val("");
    </script>
</c:if>

<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate'], function () {
        var laydate = layui.laydate;
        laydate.render({
            elem: '#dcouponEffectiveDate',//指定元素
            type: 'date',
            done: function(value){ //监听日期被切换
                if(!isEmpty(value)) {
                    $("#dcouponEffectiveDate").removeClass("Validform_error");
                    $("#dcouponEffectiveDate").parent().find("span").hide();
                    if(!isEmpty($("#icouponValidityValue").val())) {
                        $("#dcouponExpiryDate").val(dateAndTimeAdd(new Date(value), parseInt($("#icouponValidityValue").val())));
                    } else {
                        $("#dcouponExpiryDate").val("");
                    }
                } else {
                    $("#dcouponEffectiveDate").addClass("Validform_error");
                    $("#dcouponEffectiveDate").parent().find("span").html($("#dcouponEffectiveDate").attr("nullmsg"));
                    $("#dcouponEffectiveDate").parent().find("span").removeClass("Validform_right");
                    $("#dcouponEffectiveDate").parent().find("span").addClass("Validform_wrong");
                    $("#dcouponEffectiveDate").parent().find("span").show();
                }
            }
        });

        var form = layui.form;
        //监听提交
        $("#myForm").Validform({
            btnSubmit:"#myFormSub",  //根据id触发
            tiptype:3,                  //第三种方式
            showAllError:true,
            beforeSubmit:function() { //验证过后执行save方法
                var icouponType = $("#icouponType").val();
                if (icouponType == 40) {
                    if (isEmpty($("#scommodityId").val())) {
                        alertDel("请选择商品信息！");
                        return false;
                    }
                } else {
                    if (isEmpty($("#fcouponValue").val())) {
                        alertDel("劵面值不能为空！");
                        return false;
                    }
                    if(icouponType != 30) {
                        var fuseLimitAmount = $("#fuseLimitAmount").val();
                        if (isEmpty(fuseLimitAmount) || Number(fuseLimitAmount) <= 0) {
                            alertDel("劵使用规则订单实付金额必须大于0！");
                            return false;
                        }
                    }
                }
                if (icouponType != 40) {
                    var igiveMethod = $("#igiveMethod").val();
                    if (igiveMethod == 10) {
                        if (isEmpty($("#fminValue").val())) {
                            alertDel("最小券值不能为空！");
                            return false;
                        }
                        if (Number($("#fminValue").val()) < 0) {
                            alertDel("最小券值必须是正整数！");
                            return false;
                        }
                        if (isEmpty($("#fmaxValue").val())) {
                            alertDel("最大券值不能为空！");
                            return false;
                        }
                        if (Number($("#fminValue").val()) > Number($("#fmaxValue").val())) {
                            alertDel("最大券值必须大于最小券值！");
                            return false;
                        }
                    }
                }
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
                                'index':index
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
        //券类型
        form.on('select(icouponType)', function(data){
            if (data.value == 40) {//商品券
                initForm();
            } else {

                var iscenesType = "${iscenesType}";
                var itype = "${itype}";
                if(!(iscenesType != "51" && iscenesType != "60" && iscenesType != "70" && itype != "20")) {
                    $("#igiveMethod").removeAttr("disabled");
                    if($("#igiveMethod").val() == 10) {
                        $("#fminValue").removeAttr("disabled");
                        $("#fmaxValue").removeAttr("disabled");
                        $("#fmaxValue").val("");
                        $("#fminValue").val("");
                    } else {
                        $("#fminValue").attr("disabled", "disabled");
                        $("#fmaxValue").attr("disabled", "disabled");
                        $("#fmaxValue").val("");
                        $("#fminValue").val("");
                    }
                } else {
                    $("#igiveMethod").attr("disabled", "disabled");
                    $("#fminValue").attr("disabled", "disabled");
                    $("#fmaxValue").attr("disabled", "disabled");
                    $("#fmaxValue").val("");
                    $("#fminValue").val("");
                }

                $("#fcouponValue").removeAttr("disabled");
                $("#scommodityCode").attr("disabled", "disabled");
                $("#selectSp").attr("disabled", "disabled");
                $("#scommodityCode").val("");
                $("#scommodityId").val("");

                if(data.value == 30) {
                    $("#fuseLimitAmount").attr("disabled", "disabled");
                    $("#fuseLimitAmount").val("");
                } else {
                    $("#fuseLimitAmount").removeAttr("disabled");
                }

                $("#useCommodity").removeAttr("disabled");
                //$("#useDevice").removeAttr("disabled");
                $("#useCategory").removeAttr("disabled");
                $("#useBrand").removeAttr("disabled");

            }
            $(this).blur();
            form.render('select');
        });
        //送券方式
        form.on('select(igiveMethod)', function(data){
            if (data.value == 10) {//比例
                $("#fminValue").removeAttr("disabled");
                $("#fmaxValue").removeAttr("disabled");
                $("#fmaxValue").val("");
                $("#fminValue").val("");
            } else {
                $("#fminValue").attr("disabled", "disabled");
                $("#fmaxValue").attr("disabled", "disabled");
                $("#fmaxValue").val("");
                $("#fminValue").val("");
            }
            $(this).blur();
        });
    });
    var deviceFlag = true;
    var commodityFlag = true;
    var categoryFlag = true;
    var brandFlag = true;
    $(function () {
        $("#icouponValidityValue").keyup(function(){
            var val = $(this).val();
            if(!isEmpty(val) && !isEmpty($("#dcouponEffectiveDate").val())) {
                $("#dcouponExpiryDate").val(dateAndTimeAdd(new Date($("#dcouponEffectiveDate").val()), parseInt(val)));
            } else {
                $("#dcouponExpiryDate").val("");
            }
        });
        //返回
        $("#cancelBtn").click(function () {
            parent.layer.close(index);
            return false;
        });
        //选择使用设备
        $("#useDevice").click(function() {
            deviceFlag = false;
            var suseLimitDevice = $("#suseLimitDevice").val();
            var useDeviceIds = $("#useDeviceIds").val();
            if (!isEmpty(useDeviceIds)) {
                showLayerMax("选择设备信息",ctx+"/common/selectDevice?deviceIds="+useDeviceIds+"&deviceCodes="+suseLimitDevice);
            } else {
                showLayerMax("选择设备信息",ctx+"/common/selectDevice");
            }
        });
        //选择使用商品
        $("#useCommodity").click(function() {
            commodityFlag = false;
            var suseLimitCommodity = $("#suseLimitCommodity").val();
            var useCommodityIds = $("#useCommodityIds").val();
            if (!isEmpty(useCommodityIds)) {
                showLayerMax("选择商品信息",ctx+"/common/selectCommodity?commodityIds="+useCommodityIds+"&commodityCodes="+suseLimitCommodity);
            } else {
                showLayerMax("选择商品信息",ctx+"/common/selectCommodity");
            }
        });
        //选择使用商品分类
        $("#useCategory").click(function() {
            categoryFlag = false;
            var suseLimitCategory = $("#suseLimitCategory").val();
            if (!isEmpty(suseLimitCategory)) {
                showLayerMedium("选择商品分类信息",ctx+"/common/selectCommodityCategory?categoryIds="+suseLimitCategory);
            } else {
                showLayerMedium("选择商品分类信息",ctx+"/common/selectCommodityCategory");
            }
        });
        //选择使用商品分类
        $("#useBrand").click(function() {
            brandFlag = false;
            var suseLimitBrand = $("#suseLimitBrand").val();
            if (!isEmpty(suseLimitBrand)) {
                showLayerMedium("选择商品品牌信息",ctx+"/common/selectCommodityBrand?brandIds="+suseLimitBrand);
            } else {
                showLayerMedium("选择商品品牌信息",ctx+"/common/selectCommodityBrand");
            }
        });
        //选择商品信息
        $("#selectSp").click(function() {
            showLayerMax("选择商品信息",ctx+"/common/selectSingleCommodity?types=10");
        });
    });
    //确认选择设备
     function selectDevice(deviceIds, deviceCodes) {
         if (deviceFlag) {
             $("#sgiveLimitDevice").val(deviceCodes);
             $("#giveDeviceIds").val(deviceIds);
         } else {
             $("#suseLimitDevice").val(deviceCodes);
             $("#useDeviceIds").val(deviceIds);
         }
     }
    //确认选择商品
    function selectCommodity(commodityIds, commodityCodes) {
        if (commodityFlag) {
            $("#sgiveLimitCommodity").val(commodityCodes);
            $("#giveCommodityIds").val(commodityIds);
        } else {
            $("#suseLimitCommodity").val(commodityCodes);
            $("#useCommodityIds").val(commodityIds);
        }
    }
    //确认选择商品分类
    function selectCommodityCategory(categoryIds, categoryNames) {
        if (categoryFlag) {
            $("#sgiveLimitCategory").val(categoryIds);
            $("#giveLimitCategory").val(categoryNames);
        } else {
            $("#suseLimitCategory").val(categoryIds);
            $("#useLimitCategory").val(categoryNames);
        }
    }
    //确认选择商品品牌
    function selectCommodityBrand(brandIds, brandNames) {
        if (brandFlag) {
            $("#sgiveLimitBrand").val(brandIds);
            $("#giveLimitBrand").val(brandNames);
        } else {
            $("#suseLimitBrand").val(brandIds);
            $("#useLimitBrand").val(brandNames);
        }
    }
    //确认选择商品信息
    function selectSingleCommodity(scommodityId, scommodityCode) {
        $("#scommodityCode").val(scommodityCode);
        $("#scommodityId").val(scommodityId);
    }
    <c:if test="${iscenesType eq 51 or iscenesType eq 60 or iscenesType eq 70 or itype eq 20}">
    $(function () {
        /*//选择限制设备
         $("#giveDevice").click(function() {
             deviceFlag = true;
             var sgiveLimitDevice = $("#sgiveLimitDevice").val();
             var giveDeviceIds = $("#giveDeviceIds").val();
             if (!isEmpty(giveDeviceIds)) {
                showLayer("选择设备",ctx+"/common/selectDevice?deviceIds="+giveDeviceIds+"&deviceCodes="+sgiveLimitDevice,{ area: ['90%', '80%']});
             } else {
                showLayer("选择设备",ctx+"/common/selectDevice",{ area: ['90%', '80%']});
             }
         });*/
        //选择限制商品
        $("#giveCommodity").click(function() {
            commodityFlag = true;
            var sgiveLimitCommodity = $("#sgiveLimitCommodity").val();
            var giveCommodityIds = $("#giveCommodityIds").val();
            if (!isEmpty(giveCommodityIds)) {
                showLayerMedium("选择商品信息",ctx+"/common/selectCommodity?commodityIds="+giveCommodityIds+"&commodityCodes="+sgiveLimitCommodity);
            } else {
                showLayerMedium("选择商品信息",ctx+"/common/selectCommodity");
            }
        });
        //选择限制商品分类
        $("#giveCategory").click(function() {
            categoryFlag = true;
            var sgiveLimitCategory = $("#sgiveLimitCategory").val();
            if (!isEmpty(sgiveLimitCategory)) {
                showLayerMedium("选择商品分类信息",ctx+"/common/selectCommodityCategory?categoryIds="+sgiveLimitCategory);
            } else {
                showLayerMedium("选择商品分类信息",ctx+"/common/selectCommodityCategory");
            }
        });
        //选择限制品牌
        $("#giveBrand").click(function() {
            brandFlag = true;
            var sgiveLimitBrand = $("#sgiveLimitBrand").val();
            if (!isEmpty(sgiveLimitBrand)) {
                showLayerMedium("选择商品品牌信息",ctx+"/common/selectCommodityBrand?brandIds="+sgiveLimitBrand,{ area: ['50%', '80%']});
            } else {
                showLayerMedium("选择商品品牌信息",ctx+"/common/selectCommodityBrand",{ area: ['50%', '80%']});
            }
        });
    });
    </c:if>
</script>
</body>
</html>