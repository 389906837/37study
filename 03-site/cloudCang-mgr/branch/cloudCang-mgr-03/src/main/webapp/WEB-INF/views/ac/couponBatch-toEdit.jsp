<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>优惠券批量下发编辑</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?201810" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css" rel="stylesheet"/>
<style type="text/css">
    .layui-form-label {
        width: 180px;
    }
</style>
</head>
<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/couponBatch/save" method="post" id="myForm">
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="activity.issuance.batch.number" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sbatchCode" id="sbatchCode" disabled value="${couponBatch.sbatchCode}"
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='ac.couponrule.voucher.type' /></label>
                <div class="layui-input-inline">
                    <cang:select type="dic" groupNo="SP000004" name="icouponType" layFilter="icouponType"
                                 exp="springMessageCode=ac.please.select.the.coupon.type"
                                 id="icouponType" entire="true"
                                 value="${couponBatch.icouponType}"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <div class="layui-inline">
                    <label class="layui-form-label"><spring:message code='ac.couponrule.day.of.validity.of.the.voucher' /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="scouponValidityValue" id="scouponValidityValue"
                               lay-verify="required|number" value="${couponBatch.scouponValidityValue}"
                               class="layui-input" datatype="n" nullmsg="<spring:message code='wz.please.enter.the.number' />"/>
                    </div>
                </div>
            </div>
            <div class="layui-col-md6">
                <div class="layui-inline">
                    <label class="layui-form-label"><spring:message code='ac.voucher.value.yuan' /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="fcouponValue" <%--datatype="*" nullmsg="<spring:message code='ac.please.enter.the.voucher.value' />" errormsg="<spring:message code='ac.voucher.value.must.be.a.number' />"--%>
                               id="fcouponValue"
                               value='<fmt:formatNumber value="${couponBatch.fcouponValue}" pattern="#0.##"/>'
                               class="layui-input"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='ac.voucher.effective.time' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="dcouponEffectiveDate" datatype="*" nullmsg="<spring:message code='ac.please.select.the.coupon.effective.time' />"
                           id="dcouponEffectiveDate"
                           value="<fmt:formatDate value='${couponBatch.dcouponEffectiveDate}' pattern='yyyy-MM-dd '/>"
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="main.voucher.expiration.time" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="dcouponExpiryDate"
                           disabled="disabled" <%--datatype="*" nullmsg="<spring:message code='ac.please.select.the.voucher.expiration.time' />"--%>
                           id="dcouponExpiryDate"
                           value="<fmt:formatDate value='${couponBatch.dcouponExpiryDate}' pattern='yyyy-MM-dd '/>"
                           class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='ac.delivery.type' /></label>
                <div class="layui-input-inline">
                    <cang:select type="dic" groupNo="SP000146" name="itype" layFilter="iType"
                                 exp="springMessageCode=ac.please.select.the.delivery.type"
                                 id="itype" entire="true"
                                 value="${couponBatch.itype}"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='ac.couponrule.order.payment.amount.yuan' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="fuseLimitAmount" id="fuseLimitAmount" placeholder='0<spring:message code="main.unlimited"/>'
                           value="<fmt:formatNumber value="${couponBatch.fuseLimitAmount}" pattern="#0.##"/>"
                           class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='ac.couponrule.product.information' /></label>
                <div class="layui-input-inline" style="width: 115px">
                    <input type="text" name="scommodityCode" id="scommodityCode" disabled="disabled"
                           value="${couponBatch.suseLimitCommodity}" class="layui-input"/>
                    <input type="hidden" name="scommodityId" id="scommodityId" value="${useCommodityIds}"
                           class="layui-input"/>
                </div>
                <button class="layui-btn" id="selectSp" style="display: inline-block;float: left;" type="button"
                        disabled="disabled" data-type="sel"><spring:message code='main.choice' />
                </button>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='ac.couponrule.commodity.restrictions' /></label>
                <div class="layui-input-inline" style="width: 260px">
                    <textarea class="layui-textarea" id="suseLimitCommodity" name="suseLimitCommodity"
                              readonly="readonly" placeholder="<spring:message code='main.null.unlimited' />">${couponBatch.suseLimitCommodity}</textarea>
                </div>
                <button class="layui-btn" id="useCommodity" style="display: inline-block;float: left;" type="button"
                        data-type="sel"><spring:message code='main.choice' />
                </button>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='ac.couponrule.device.restriction' /></label>
                <div class="layui-input-inline" style="width: 260px">
                    <textarea class="layui-textarea" id="suseLimitDevice" name="suseLimitDevice" readonly="readonly"
                              placeholder='<spring:message code="main.null.unlimited"/>'>${couponBatch.suseLimitDevice}</textarea>
                </div>
                <button class="layui-btn" id="useDevice" style="display: inline-block;float: left;" type="button"
                        data-type="sel"><spring:message code='main.choice' />
                </button>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='ac.couponrule.commodity.classification.restrictions' /></label>
                <div class="layui-input-inline" style="width: 260px">
                    <textarea class="layui-textarea" id="useLimitCategory" name="useLimitCategory" readonly="readonly"
                              placeholder='<spring:message code="main.null.unlimited"/>'>${useLimitCategory}</textarea>
                </div>
                <button class="layui-btn layui-btn-primary" id="useCategory" style="display: inline-block;float: left;"
                        type="button" data-type="sel"><spring:message code='main.choice' />
                </button>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='ac.couponrule.commodity.brand.restrictions' /></label>
                <div class="layui-input-inline" style="width: 260px">
                    <textarea class="layui-textarea" id="useLimitBrand" name="useLimitBrand" readonly="readonly"
                              placeholder='<spring:message code="main.null.unlimited"/>'>${useLimitBrand}</textarea>
                </div>
                <button class="layui-btn layui-btn-primary" id="useBrand" style="display: inline-block;float: left;"
                        type="button" data-type="sel"><spring:message code='main.choice' />
                </button>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label" style="width: 180px;"><spring:message code='ac.couponrule.voucher.description' /></label>
                <div class="layui-input-block">
                <textarea class="layui-textarea" style="width: 260px" id="scouponInstruction"
                          name="scouponInstruction">${couponBatch.scouponInstruction}</textarea>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='ac.couponrule.voucher.brief' /></label>
                <div class="layui-input-inline">
                    <textarea class="layui-textarea" style="width: 260px" id="sbriefDesc"
                              name="sbriefDesc">${couponBatch.sbriefDesc}</textarea>
                </div>
            </div>
        </div>
        <%--<div class="layui-form-item">--%>
        <%--<div class="layui-col-md6">--%>
        <%--<label class="layui-form-label"><spring:message code='ac.coupon.delivery.user' /></label>--%>
        <%--<button class="layui-btn" id="selectMember" style="display: inline-block;float: left;" type="button">--%>
        <%--<spring:message code='syscon.select.operator' />--%>
        <%--</button>--%>
        <%--</div>--%>
        <%--</div>--%>


        <div class="layui-form-item" id="selectMember">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='ac.coupon.delivery.user' /></label>
                <div class="layui-input-inline">
                    <input type="radio" name="member" lay-filter="smember" value="0" title='<spring:message code="main.whole" />'/>
                    <input type="radio" name="member" lay-filter="smember" value="1" checked="checked" title='<spring:message code="main.part" />'/>
                    <input type="radio" name="member" lay-filter="smember" value="2" title="<spring:message code='ac.import.from.excel' />"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='ac.number.of.deliveries' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="couponNum" id="couponNum" value="" disabled="disabled"
                           class="layui-input"/>
                </div>
            </div>
        </div>


        <div class="layui-form-item">
            <div class="layui-col-md6">
                <table class="layui-table" id="memberTable" style="display:none;margin-left: 180px">
                    <colgroup>
                        <col width="60">
                        <col>
                        <col>
                        <col width="60">
                    </colgroup>
                    <thead>
                    <tr>
                        <%--<th><input type="checkbox" id="checkbox_{{:smemberCode}}" name="checkbox_{{:smemberCode}}"--%>
                        <%--value="{{:smemberCode}}"/></th>--%>
                        <th><spring:message code='main.serial.number' /></th>
                        <th><spring:message code="main.member.number" /></th>
                        <th><spring:message code='main.member.username' /></th>
                        <th><spring:message code='ac.number.sent' /></th>
                    </tr>
                    </thead>
                    <tbody id="memberBody">
                    </tbody>
                </table>
                <input type="hidden" id="smemberId" name="smemberId" value="${smemberId}"/>
                <button class="layui-btn layui-btn-primary" id="selectSmember"
                        style="margin-left: 180px;margin-top: 10px;" type="button"><spring:message code='syscon.select.operator' />
                </button>
                <button class="layui-btn layui-btn-primary" id="selectExcel"
                        style="margin-left: 180px;margin-top: 10px;" type="button" disabled><spring:message code='ac.select.the.file' />
                </button>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
                <button class="layui-btn" id="myFormSub"><spring:message code="main.save" /></button>
            </div>
        </div>
        <input type="hidden" id="id" name="id" value="${couponBatch.id}"/>

        <input type="hidden" id="useDeviceIds" name="useDeviceIds" value="${useDeviceIds}"/>
        <input type="hidden" id="useCommodityIds" name="useCommodityIds" value="${useCommodityIds}"/>
        <input type="hidden" id="suseLimitBrand" name="suseLimitBrand" value="${couponBatch.suseLimitBrand}"/>
        <input type="hidden" id="suseLimitCategory" name="suseLimitCategory" value="${couponBatch.suseLimitCategory}"/>

    </form>
</div>
<script id="member_table_tmpl" type="text/x-jsrender">
{{for data}}
    <tr id="tr_{{:smemberId}}">
      <%--<td><input type="checkbox" id="checkbox_{{:smemberId}}" name="checkbox_{{:smemberId}}" value="{{:smemberId}}"/></td>--%>
      <td>{{:#index + 1}}</td>
      <td>{{:smemberCode}}</td>
      <td>{{:smemberName}}</td>
      <td><input type="text" id="inumber_{{:smemberId}}" name="inumber_{{:smemberId}}" value="{{:inumber}}"/></td>
    </tr>
{{/for}}



</script>
<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/jsrender.min.js" type="text/javascript"></script>
<script src="${staticSource}/resources/js/jsrender.converters.js" type="text/javascript"></script>
<script src="${staticSource}/resources/js/validform_min.js" type="text/javascript"></script>


<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var form;
    layui.use(['form', 'layedit', 'upload', 'laydate'], function () {

        //文件上传start
        var $ = layui.jquery, upload = layui.upload;
        upload.render({
            elem: '#selectExcel'
            , url: ctx + '/couponBatch/batchUploadExcel'
//            , multiple: false
            , field: "file"
            , size: 200
            , accept: 'file'
            , exts: 'csv'
            , done: function (res) {
                // 成功后的回调
                var memberList = res.data;
                if (!isEmpty(memberList)) {
                    var html = $("#member_table_tmpl").render(res);
                    $("#memberBody").html(html);
                    $("#memberTable").show();
                    var buffer = "";
                    for (var i = 0, len = memberList.length; i < len; i++) {
                        buffer = buffer + memberList[i].smemberId + ",";
                    }
                    $("#smemberId").val(buffer);
                } else {
                    hideMemberTable();
                }
            }
            , error: function () {
                // 当上传失败时的回调
                hideMemberTable();
            }
        });

        // 指定时间
        var laydate = layui.laydate;
        laydate.render({
            elem: '#dcouponEffectiveDate',//指定元素
            type: 'date',
            min: 0,
            done: function (value) { //监听日期被切换
                if (!isEmpty(value)) {
                    $("#dcouponEffectiveDate").removeClass("Validform_error");
                    $("#dcouponEffectiveDate").parent().find("span").hide();
                    if (!isEmpty($("#scouponValidityValue").val())) {
                        $("#dcouponExpiryDate").val(dateAdd(new Date(value), parseInt($("#scouponValidityValue").val())));
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

        form = layui.form;
        $(function () {
            $("#myForm").Validform({
                btnSubmit: "#myFormSub",  //根据id触发
                tiptype: 3,                  //第三种方式
                showAllError: true,
                beforeSubmit: function () { //验证过后执行save方法
                    var smember = $('input[name="member"]:checked').val();
                    if (smember == 1) {
                        if (isEmpty($("#smemberId").val())) {
                            alertDel("<spring:message code='ac.please.select.the.user.to.be.sent' />");
                            return false;
                        }
                    }
                    var icouponType = $("#icouponType").val();
                    if (icouponType == 40) {
                        if (isEmpty($("#scommodityId").val()) || isEmpty($("#scommodityCode").val())) {
                            alertDel("<spring:message code='ac.couponrule.please.select.product.information' />！");
                            return false;
                        }
                    } else {
                        if (isEmpty($("#fcouponValue").val())) {
                            alertDel("<spring:message code='ac.couponrule.voucher.value.cannot.be.empty' />！");
                            return false;
                        }
                        if (icouponType != 30) {
                            var fuseLimitAmount = $("#fuseLimitAmount").val();
                            if (isEmpty(fuseLimitAmount) || Number(fuseLimitAmount) < 0) {
                                alertDel("<spring:message code='ac.couponrule.the.voucher.usage.rule.order.payment.amount.must.be.greater.than.0' />！");
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
                            alertDelAndReload('<spring:message code="main.error.try.again"/>');
                        },
                        success: function (msg) {
                            //成功返回
                            closeLayer(loadIndex);//关闭加载框
                            if (msg.success) {
                                alertSuccess('<spring:message code="main.success"/>', {
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
        });

        //券类型
        form.on('select(icouponType)', function (data) {
            if (data.value == 40) {//<spring:message code='ac.couponrule.commodity.ticket' />
                initForm();
            } else {
                $("#fuseLimitAmount").removeAttr("disabled");
                $("#fuseLimitAmount").val("");
                if (data.value == 30) {
                    $("#fuseLimitAmount").attr("disabled", "disabled");
                    $("#fuseLimitAmount").val("");
                }
                $("#fcouponValue").removeAttr("disabled");

                $("#scommodityCode").attr("disabled", "disabled");
                $("#scommodityCode").val("");
                $("#selectSp").attr("disabled", "disabled");
                $("#scommodityId").val("");

                $("#useCommodity").removeAttr("disabled");
                $("#useDevice").removeAttr("disabled");
                $("#useCategory").removeAttr("disabled");
                $("#useBrand").removeAttr("disabled");
            }
            $(this).blur();
            form.render('select');
        });
        //用户单选
        form.on('radio(smember)', function (data) {
            if (data.value == 1) {
                //部分用户
                $("#selectSmember").removeAttr("disabled");
                $("#selectExcel").attr("disabled", "disabled");
                $("#couponNum").attr("disabled", "disabled");
                $("#couponNum").val("");
            } else if (data.value == 2) {
                // 选择Excel导入
                $("#selectSmember").attr("disabled", "disabled");
                $("#selectExcel").removeAttr("disabled");
                $("#couponNum").attr("disabled", "disabled");
                $("#couponNum").val("");
            } else {
                //全部用户
                $("#selectSmember").attr("disabled", "disabled");
                $("#selectExcel").attr("disabled", "disabled");
                $("#couponNum").removeAttr("disabled");
                $("#couponNum").val("1");
            }
            hideMemberTable();
        });
    });

    //选择用户
    $("#selectSmember").click(function () {
        var memberIds = $("#smemberId").val();
        var memberCodes = $("#memberCodes").val();
        if (!isEmpty(memberIds)) {
            showLayerMax("<spring:message code='syscon.select.operator' />", ctx + "/memberInfo/toCouponBatchList?memberIds=" + memberIds + "&memberCodes=" + memberCodes);
        } else {
            showLayerMax("<spring:message code='syscon.select.operator' />", ctx + "/memberInfo/toCouponBatchList");
        }
    });

    $(function () {
        $("#scouponValidityValue").keyup(function () {
            var val = $(this).val();
            if (!isEmpty(val) && !isEmpty($("#dcouponEffectiveDate").val())) {
                $("#dcouponExpiryDate").val(dateAdd(new Date($("#dcouponEffectiveDate").val()), parseInt(val)));
            } else {
                $("#dcouponExpiryDate").val("");
            }
        });
        //返回
        $("#cancelBtn").click(function () {
            parent.layer.close(index);
            return false;
        });
        initMemberTable();

        //选择使用设备
        $("#useDevice").click(function () {
            var suseLimitDevice = $("#suseLimitDevice").val();
            var useDeviceIds = $("#useDeviceIds").val();
            if (!isEmpty(useDeviceIds)) {
                showLayerMax('<spring:message code="sb.select.device.information" />', ctx + "/common/selectDevice?deviceIds=" + useDeviceIds + "&deviceCodes=" + suseLimitDevice + "&allIstatus=20");
            } else {
                showLayerMax('<spring:message code="sb.select.device.information" />', ctx + "/common/selectDevice?allIstatus=20");
            }
        });
        //选择使用商品
        $("#useCommodity").click(function () {
            var suseLimitCommodity = $("#suseLimitCommodity").val();
            var useCommodityIds = $("#useCommodityIds").val();
            if (!isEmpty(useCommodityIds)) {
                showLayerMax("<spring:message code='ac.couponrule.select.product.information' />", ctx + "/common/selectCommodity?commodityIds=" + useCommodityIds + "&commodityCodes=" + suseLimitCommodity);
            } else {
                showLayerMax("<spring:message code='ac.couponrule.select.product.information' />", ctx + "/common/selectCommodity");
            }
        });
        //选择使用商品分类
        $("#useCategory").click(function () {
            var suseLimitCategory = $("#suseLimitCategory").val();
            if (!isEmpty(suseLimitCategory)) {
                showLayerMedium("<spring:message code='ac.couponrule.select.product.classification.information' />", ctx + "/common/selectCommodityCategory?categoryIds=" + suseLimitCategory);
            } else {
                showLayerMedium("<spring:message code='ac.couponrule.select.product.classification.information' />", ctx + "/common/selectCommodityCategory");
            }
        });
        //选择使用商品分类
        $("#useBrand").click(function () {
            var suseLimitBrand = $("#suseLimitBrand").val();
            if (!isEmpty(suseLimitBrand)) {
                showLayerMedium("<spring:message code='ac.couponrule.select.product.brand.information' />", ctx + "/common/selectCommodityBrand?brandIds=" + suseLimitBrand);
            } else {
                showLayerMedium("<spring:message code='ac.couponrule.select.product.brand.information' />", ctx + "/common/selectCommodityBrand");
            }
        });
        //选择商品信息
        $("#selectSp").click(function () {
            showLayerMax("<spring:message code='ac.couponrule.select.product.information' />", ctx + "/common/selectSingleCommodity?types=10");
        });
    });
    //确认选择设备
    function selectDevice(deviceIds, deviceCodes) {
        $("#suseLimitDevice").val(deviceCodes);
        $("#useDeviceIds").val(deviceIds);
    }
    //确认选择商品
    function selectCommodity(commodityIds, commodityCodes) {
        $("#suseLimitCommodity").val(commodityCodes);
        $("#useCommodityIds").val(commodityIds);
    }
    //确认选择商品分类
    function selectCommodityCategory(categoryIds, categoryNames) {
        $("#suseLimitCategory").val(categoryIds);
        $("#useLimitCategory").val(categoryNames);
    }
    //确认选择商品品牌
    function selectCommodityBrand(brandIds, brandNames) {
        $("#suseLimitBrand").val(brandIds);
        $("#useLimitBrand").val(brandNames);
    }
    //确认选择商品信息
    function selectSingleCommodity(scommodityId, scommodityCode) {
        $("#scommodityCode").val(scommodityCode);
        $("#scommodityId").val(scommodityId);
    }


    //初始化用户表格
    function initMemberTable() {
        var smemberId = $("#smemberId").val();
        if (isEmpty(smemberId)) {//没有选择用户
            hideMemberTable();
            return;
        }
        $.ajax({
            url: ctx + '/couponBatch/batch',
            type: 'POST', //GET
            async: false,    //或false,是否异步
            data: {memberId: smemberId, batchId: '${couponBatch.id}'},
            dataType: 'json',
            error: function () {
                hideMemberTable();
            },
            success: function (msg) {
                if (msg.success) {//成功返回
                    var memberList = msg.data;
                    if (!isEmpty(memberList)) {
                        var html = $("#member_table_tmpl").render(msg);
                        $("#memberBody").html(html);
                        $("#memberTable").show();
                    } else {
                        hideMemberTable();
                    }
                } else {
                    hideMemberTable();
                }
            }
        });
    }

    //隐藏表格 用户
    function hideMemberTable() {
        $("#memberBody").html("");
        $("#memberTable").hide();
        $("#smemberId").val("");
    }

    //确认选择用户
    function selectMember(memberId, memberCodes) {
        $("#smemberId").val(memberId);
        $("#memberCodes").val(memberCodes);
        initMemberTable();
    }
    function initForm() {
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
    }
</script>
<%--<spring:message code='ac.couponrule.discount.coupon,<spring:message code='ac.the.actual.amount.paid.is.grayed.out.and.the.coupon.value.can.be.filled.in' />--%>
<c:if test="${couponBatch.icouponType eq 30 }">
    <script type="text/javascript">
        $("#fuseLimitAmount").attr("disabled", "disabled");
        $("#fuseLimitAmount").val("");
    </script>
</c:if>
<c:if test="${couponBatch.icouponType eq 40}">
    <script type="text/javascript">
        initForm();
        //form.render('select');
    </script>
</c:if>
<c:if test="${couponBatch.icouponType ne 40}">
    <script type="text/javascript">
        $("#scommodityCode").val("");
        $("#scommodityId").val("");
    </script>
</c:if>
</body>
</html>