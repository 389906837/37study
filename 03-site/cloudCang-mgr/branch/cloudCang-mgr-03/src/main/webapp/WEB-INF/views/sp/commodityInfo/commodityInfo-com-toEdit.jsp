<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>编辑商品信息</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?1.0" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/commodity/commodityInfo/Edit" method="post" id="myForm"
          enctype="multipart/form-data">
        <input type="hidden" id="sbrandId" name="sbrandId" value="${commodityInfo.sbrandId}"/><%--<spring:message code="sp.commodityInfo.brand.id" />--%>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityInfo.applicable.equipment.type" /></label>
                <div class="layui-input-inline">
                    <select name="istoreDevice1" id="istoreDevice1" datatype="*" nullmsg='<spring:message code="sp.commodityInfo.please.select.a.product.type" />' class="state"
                            lay-filter="istoreDevice" disabled>
                        <option value=""><spring:message code="main.please.choose" /></option>
                        <option value="10"
                                <c:if test="${10 == commodityInfo.istoreDevice}">selected</c:if> ><spring:message code="sp.commodityInfo.vision" />
                        </option>
                        <option value="20"
                                <c:if test="${20 == commodityInfo.istoreDevice}">selected</c:if> >rfid<spring:message code="main.device" />
                        </option>
                        <option value="30"
                                <c:if test="${30 == commodityInfo.istoreDevice}">selected</c:if> ><spring:message code="sp.commodityInfo.other" />
                        </option>
                    </select>
                </div>
            </div>
            <div class="layui-col-md6" id="nameDiv">
                <label class="layui-form-label"><spring:message code="main.product.name" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sname" datatype="*" nullmsg='<spring:message code="sp.commodityInfo.please.enter.the.product.name" />' id="sname"
                           value="${commodityInfo.sname}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.brand.brand.name" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sbrandName" placeholder='<spring:message code="main.required" />...' datatype="*" nullmsg='<spring:message code="sp.commodityInfo.please.select.a.brand.name" />'
                           id="sbrandName" class="layui-input" value="${commodityInfo.sbrandName}" readonly/>
                </div>
                <button class="layui-btn" id="btn1" style="display: inline-block;float: left;"
                        type="button" data-type="selBrand"><spring:message code="main.choice" />
                </button>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityInfo.large.class.name" /></label>
                <div class="layui-input-inline">
                    <select lay-verify="required" lay-search="" name="sbigCategoryName" id="sbigCategoryName"
                            class="state"
                    <%--datatype="*" nullmsg='<spring:message code="sp.commodityInfo.please.select.a.large.class.name" />' --%>
                            lay-filter="bigCategoryName">
                        <option value=""><spring:message code="main.please.choose" /></option>
                        <c:forEach items="${bigCategoryList}" var="bc">
                            <option value="${bc.id}_${bc.sname}"
                                    <c:if test="${bc.id eq commodityInfo.sbigCategoryId}">selected</c:if>>${bc.sname}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityInfo.small.class.name" /></label>
                <div class="layui-input-inline">
                    <select lay-verify="required" lay-search="" name="ssmallCategoryName" id="ssmallCategoryName"
                            class="state"
                    <%--datatype="*" nullmsg='<spring:message code="sp.commodityInfo.please.select.a.small.class.name" />' --%>
                            lay-filter="ssmallCategoryName">
                        <option value=""><spring:message code="main.please.choose" /></option>
                        <c:forEach items="${smallCategoryList}" var="sc">
                            <option value="${sc.id}_${sc.sname}"
                                    <c:if test="${sc.id eq commodityInfo.ssmallCategoryId}">selected</c:if>>${sc.sname}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sm.shelf.life" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="ishelfLife" id="ishelfLife" value="${commodityInfo.ishelfLife}"
                           datatype="*" nullmsg='<spring:message code="sp.commodityInfo.please.enter.the.expiration.date" />' placeholder='<spring:message code="main.required" />...' class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityInfo.shelf.life.type" /></label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="ilifeType" id="ilifeType" entire="true"
                                 layFilter="ilifeType" exp="springMessageCode=sp.commodityInfo.please.select.the.shelf.life.type"
                                 value="${commodityInfo.ilifeType }" list='{10:springMessageCode=sp.commodityInfo.day,20:springMessageCode=sp.commodityInfo.month}' />
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityInfo.specification.unit" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sspecUnit" id="sspecUnit" value="${commodityInfo.sspecUnit}"
                           datatype="*" nullmsg='<spring:message code="sp.commodityInfo.please.enter.the.specification.unit" />' class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityInfo.specification/weight" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="ispecWeight" id="ispecWeight" value="${commodityInfo.ispecWeight}"
                           datatype="*" nullmsg='<spring:message code="sp.commodityInfo.please.enter.the.specification/weight" />' class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityInfo.commodity.weight" />(g)</label>
                <div class="layui-input-inline">
                    <input type="text" name="iweigth" datatype="*" nullmsg='<spring:message code="sp.commodityInfo.please.enter.the.weight.of.the.item" />' placeholder='<spring:message code="main.required" />...' id="iweigth"
                           value="${commodityInfo.iweigth}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityInfo.product.weight.floating.value" />(g)</label>
                <div class="layui-input-inline">
                    <input type="text" name="icommodityFloat" id="icommodityFloat"
                           value="${commodityInfo.icommodityFloat}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityInfo.minimum.sales.packaging.unit" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="spackageUnit" id="spackageUnit" value="${commodityInfo.spackageUnit}"
                           datatype="*" nullmsg='<spring:message code="sp.commodityInfo.please.enter.the.minimum.sales.package.unit" />' class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityInfo.Identification.type" /></label>
                <div class="layui-input-inline">
                    <cang:select type="dic" name="iidentificationType" id="iidentificationType" entire="true"
                                 value="${commodityInfo.iidentificationType}"
                                 groupNo="SP000153"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityInfo.taste" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="staste" id="staste" value="${commodityInfo.staste}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityInfo.packaging.material" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="spackagingMaterial" id="spackagingMaterial"
                           value="${commodityInfo.spackagingMaterial}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityInfo.place.of.origin" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sorigin" id="sorigin" value="${commodityInfo.sorigin}"
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityInfo.product.barcode" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sproductBarcode" id="sproductBarcode"
                           value="${commodityInfo.sproductBarcode}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityInfo.label.name" /></label>
                <div class="layui-input-inline">
                    <select lay-verify="required" lay-search="" name="slabelName" id="slabelName" class="state"
                            lay-filter="slabelName">
                        <option value=""><spring:message code="main.please.choose" /></option>
                        <c:forEach items="${labelInfoList}" var="label">
                            <option value="${label.id}_${label.sname}"
                                    <c:if test="${label.id eq commodityInfo.slabelId}">selected</c:if>>${label.sname}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityInfo.shelf.status" /></label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="istatus" id="istatus" entire="true" value="${commodityInfo.istatus }"
                                 layFilter="istatus" exp="springMessageCode=sp.commodityInfo.please.select.the.shelf.status" list='{10:springMessageCode=index.shelf,20:springMessageCode=index.obtained}'/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityBatch.cost.price" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="fcostPrice" id="fcostPrice" value="${commodityInfo.fcostPrice}"
                           datatype="*" nullmsg='<spring:message code="sp.commodityBatch.please.enter.the.cost.price" />'
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityInfo.market.price" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="fmarketPrice" id="fmarketPrice" value="${commodityInfo.fmarketPrice}"
                           class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityInfo.sale.price" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="fsalePrice" id="fsalePrice" value="${commodityInfo.fsalePrice}"
                           datatype="*" nullmsg='<spring:message code="sp.commodityInfo.please.enter.the.sales.price" />'
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityInfo.product.short.name" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sshortName" id="sshortName" value="${commodityInfo.sshortName}"
                           class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <div class="layui-col-md12">
                <label class="layui-form-label"><spring:message code="main.remarks" /></label>
                <div class="layui-input-block">
                    <textarea id="sremark" name="sremark" class="layui-textarea" style="width: 860px"
                              placeholder='（<spring:message code="sp.commodityInfo.optional" />）'>${commodityInfo.sremark}</textarea>
                </div>
            </div>
        </div>
        <div class="layui-form-item m-t">
            <div class="layui-col-md6 ">
                <label class="layui-form-label"></label>
                <div class="layui-inline">
                    <button type="button" class="layui-btn" id="scommodityImgBut"><spring:message code="sp.commodityInfo.show.pictures" /></button>
                    <blockquote class="layui-elem-quote layui-quote-nm layui-quote-nm-1">
                        <spring:message code="sp.brand.preview" />
                        <div class="layui-upload-list" id="slogoYl">
                            <c:if test="${!empty  commodityInfo.scommodityImg}">
                                <img src="${dynamicSource}${commodityInfo.scommodityImg}"
                                     style="width: 100%;height: 100%"/>
                            </c:if>
                            <c:if test="${empty commodityInfo.scommodityImg}">
                                <img src="${staticSource}/resources/images/37cang.png"
                                     style="width: 100%;height: 100%"/>
                            </c:if>
                        </div>
                    </blockquote>
                    <span class="require">(<spring:message code="sp.commodityInfo.pixels.white.background" />600*600)</span>
                </div>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
                <button class="layui-btn" id="myFormSub"><spring:message code="main.save" /></button>
            </div>
        </div>
        <input type="hidden" name="id" id="spId" value="${commodityInfo.id}" class="layui-input"/>
        <input type="hidden" name="istoreDevice" id="istoreDevice" value="${commodityInfo.istoreDevice}"
               class="layui-input"/>
    </form>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js" type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var form;
    layui.use(['form', 'layedit', 'laydate', 'upload'], function () {
        form = layui.form;

        //选择品牌
        var $ = layui.$, active = {
            selBrand: function () {
                showLayer('<spring:message code="sp.commodityInfo.choose.a.brand" />', ctx + "/commoditySku/radioSelect", {area: ['90%', '80%']});
            }
        };
        $('.layui-form .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

        //图片上传start
        var $ = layui.jquery, upload = layui.upload;
        upload.render({
            auto: false
            , elem: '#scommodityImgBut'
            , multiple: false
            , field: "file"
            , size: 200
            , accept: 'images'
            , exts: 'jpg'
            , choose: function (obj) {
                obj.preview(function (index, file, result) {
                    $("#slogoYl").find("img").attr("src", result);
                });
            }
        });
        //图片上传end

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


        //监听select选择
        form.on('select(bigCategoryName)', function (data) {
            var bigCategoryId = data.value.split("_")[0];
            $.ajax({
                type: 'post',
                url: '${ctx}/commodity/commodityInfo/getSmallCategory',
                data: {pid: bigCategoryId},
                dataType: 'json',
                success: function (resp) {
                    var respSmall = resp.data;
                    if (respSmall !== null && respSmall !== undefined) {
                        $("#ssmallCategoryName").html("");
                        var option1 = $("<option>").val("").text('<spring:message code="main.please.choose" />');
                        $("#ssmallCategoryName").append(option1);
                        $.each(resp.data, function (key, val) {
                            var option1 = $("<option>").val(val.id + "_" + val.sname).text(val.sname);
                            $("#ssmallCategoryName").append(option1);
                            form.render('select');
                        });
                    } else {
                        $("#ssmallCategoryName").html("");
                        var option2 = $("<option>").val("").text('<spring:message code="main.please.choose" />');
                        $("#ssmallCategoryName").append(option2);
                        form.render('select');
                    }
                }
            });

//            if (!isEmpty(data.value)) {
//                $("#sbigCategoryName").parent().find("span").hide();
//            } else {
//                $("#sbigCategoryName").parent().find("span").show();
//                if (!$("#sbigCategoryName").parent().find("span").hasClass("Validform_wrong")) {
//                    $("#sbigCategoryName").parent().find("span").html($("#sbigCategoryName").attr("nullmsg"));
//                    $("#sbigCategoryName").parent().find("span").removeClass("Validform_right");
//                    $("#sbigCategoryName").parent().find("span").addClass("Validform_wrong");
//                }
//            }
        });


        //监听品牌select选择
        form.on('select(sbrandName)', function (data) {
            if (!isEmpty(data.value)) {
                $("#sbrandName").parent().find("span").hide();
            } else {
                $("#sbrandName").parent().find("span").show();
                if (!$("#sbrandName").parent().find("span").hasClass("Validform_wrong")) {
                    $("#sbrandName").parent().find("span").html($("#sbrandName").attr("nullmsg"));
                    $("#sbrandName").parent().find("span").removeClass("Validform_right");
                    $("#sbrandName").parent().find("span").addClass("Validform_wrong");
                }
            }
        });


        //监听小类select选择
        form.on('select(ssmallCategoryName)', function (data) {
//            if (!isEmpty(data.value)) {
//                $("#ssmallCategoryName").parent().find("span").hide();
//            } else {
//                $("#ssmallCategoryName").parent().find("span").show();
//                if (!$("#ssmallCategoryName").parent().find("span").hasClass("Validform_wrong")) {
//                    $("#ssmallCategoryName").parent().find("span").html($("#ssmallCategoryName").attr("nullmsg"));
//                    $("#ssmallCategoryName").parent().find("span").removeClass("Validform_right");
//                    $("#ssmallCategoryName").parent().find("span").addClass("Validform_wrong");
//                }
//            }
        });


        // 监听日期select选择
        form.on('select(ilifeType)', function (data) {
            if (!isEmpty(data.value)) {
                $("#ilifeType").parent().find("span").hide();
            } else {
                $("#ilifeType").parent().find("span").show();
                if (!$("#ilifeType").parent().find("span").hasClass("Validform_wrong")) {
                    $("#ilifeType").parent().find("span").html($("#ilifeType").attr("nullmsg"));
                    $("#ilifeType").parent().find("span").removeClass("Validform_right");
                    $("#ilifeType").parent().find("span").addClass("Validform_wrong");
                }
            }
        });

        // 监听上架下架select选择
        form.on('select(istatus)', function (data) {
            if (!isEmpty(data.value)) {
                $("#istatus").parent().find("span").hide();
            } else {
                $("#istatus").parent().find("span").show();
                if (!$("#istatus").parent().find("span").hasClass("Validform_wrong")) {
                    $("#istatus").parent().find("span").html($("#istatus").attr("nullmsg"));
                    $("#istatus").parent().find("span").removeClass("Validform_right");
                    $("#istatus").parent().find("span").addClass("Validform_wrong");
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


    function selectBrand(sbrandId, sbrandName) {
        $("#sbrandId").val(sbrandId);//商户ID
        $("#sbrandName").val(sbrandName);//商户名称
        $("#sbrandName").parent().find("span").hide();
        if ($("#sbrandName").parent().find("span").hasClass("Validform_wrong")) {
            $("#sbrandName").parent().find("span").removeClass("Validform_wrong");
            $("#sbrandName").removeClass("Validform_error");
            $("#sbrandName").parent().find("span").addClass("Validform_right");
        }
    }


</script>
</body>
</html>

