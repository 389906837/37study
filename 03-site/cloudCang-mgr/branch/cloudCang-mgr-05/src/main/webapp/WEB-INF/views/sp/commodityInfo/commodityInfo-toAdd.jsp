<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>增加商品信息</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="#" method="post" id="" enctype="multipart/form-data">
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityInfo.applicable.equipment.type" /></label>
                <div class="layui-input-block">
                    <div class="layui-input-inline">
                        <select name="istoreDevice" id="istoreDevice" datatype="*" nullmsg='<spring:message code="sp.commodityInfo.please.select.a.product.type" />'
                                lay-filter="istoreDevice">
                            <option value=""><spring:message code="main.please.choose" /></option>
                            <option value="10"><spring:message code="sp.commodityInfo.vision" /></option>
                            <option value="20">rfid<spring:message code="main.device" /></option>
                            <option value="30"><spring:message code="sp.commodityInfo.other" /></option>
                        </select>
                    </div>
                    <div nama="typediv1" id="typediv1" class="layui-input-inline" style="display:none;">
                        <button class="layui-btn" id="btn1" style="display: inline-block;float: left;"
                                type="button" onclick="selectVrComodity();"><spring:message code="sp.commodityInfo.choose.visual.goods" />
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </form>
    <div nama="typediv3" id="typediv3" style="display:none;">
        <form class="layui-form" action="${ctx}/commodity/commodityInfo/add" method="post" id="myFormVr"
              enctype="multipart/form-data">
            <input type="hidden" name="skuId" id="skuId" value="" class="layui-input"/>
            <input type="hidden" name="istoreDevice" id="vrType" value="10" class="layui-input"/>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="sp.brand.brand.name" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="sbrandName1" id="sbrandName1" value="" class="layui-input" disabled/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="main.product.name" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="commodityName" id="commodityName" value="" class="layui-input"
                               disabled/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="sp.commodityInfo.visual.product.number" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="vrCommodityCode" id="vrCommodityCode" value="" class="layui-input"
                               disabled/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="sp.commodityInfo.visual.product.identification.number" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="vrCode" id="vrCode" value="" class="layui-input" disabled/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="sp.commodityInfo.large.class.name" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="sbigCategoryName1" id="sbigCategoryName1" value="" class="layui-input"
                               disabled/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="sp.commodityInfo.small.class.name" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="ssmallCategoryName1" id="ssmallCategoryName1" value=""
                               class="layui-input" disabled/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="sp.commodityInfo.shelf.life.type" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="ilifeType1" id="ilifeType1" value="" class="layui-input" disabled/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="sm.shelf.life" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="ishelfLife1" id="ishelfLife1" value="" class="layui-input" disabled/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="sp.commodityInfo.specification.unit" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="sspecUnit1" id="sspecUnit1" value="" class="layui-input" disabled/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="sp.commodityInfo.specification/weight" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="ispecWeight1" id="ispecWeight1" value="" class="layui-input" disabled/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="sp.commodityInfo.commodity.weight" />（g）</label>
                    <div class="layui-input-inline">
                        <input type="text" name="iweigth1" id="iweigth1" value="" class="layui-input" disabled/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="sp.commodityInfo.product.weight.floating.value" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="icommodityFloat1" id="icommodityFloat1" value="" class="layui-input"
                               disabled/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="sp.commodityInfo.minimum.sales.packaging.unit" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="spackageUnit1" id="spackageUnit1" value="" class="layui-input"
                               disabled/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="sp.commodityInfo.Identification.type" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="iidentificationType1" id="iidentificationType1" value=""
                               class="layui-input" disabled/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="sp.commodityInfo.taste" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="staste1" id="staste1" value="" class="layui-input" disabled/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="sp.commodityInfo.packaging.material" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="spackagingMaterial1" id="spackagingMaterial1" value=""
                               class="layui-input" disabled/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="sp.commodityInfo.place.of.origin" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="sorigin1" id="sorigin1" value="" class="layui-input" disabled/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="sp.commodityInfo.product.barcode" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="sproductBarcode1" id="sproductBarcode1" value="" class="layui-input"
                               disabled/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="sp.commodityInfo.label.name" /></label>
                    <div class="layui-input-inline">
                        <select lay-verify="required" lay-search="" name="slabelName" id="slabelName1" class="state"
                                lay-filter="label">
                            <option value=""><spring:message code="main.please.choose" /></option>
                            <c:forEach items="${labelInfoList}" var="label">
                                <option value="${label.id}_${label.sname}">${label.sname}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label" ><spring:message code="sp.commodityInfo.sale.price" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="fsalePrice" id="fsalePrice1" value="" datatype="*" nullmsg='<spring:message code="sp.commodityInfo.please.enter.the.sales.price.(number)" />'
                               class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"> ><spring:message code="sp.commodityBatch.cost.price" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="fcostPrice" id="fcostPrice1" value="" datatype="*" nullmsg='<spring:message code="sp.commodityInfo.please.enter.the.cost.price.(number)" />'
                               class="layui-input"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label" ><spring:message code="sp.commodityInfo.market.price" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="fmarketPrice" id="fmarketPrice1" value="" class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label" ><spring:message code="sp.commodityInfo.product.short.name" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="sshortName" id="sshortName1" value=""
                               class="layui-input"/>
                    </div>
                </div>
            </div>


            <div class="layui-form-item fixed-bottom">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-primary" id="cancelBtnVr"><spring:message code="main.cancel" /></button>
                    <button class="layui-btn" id="myFormSubVr"><spring:message code="main.save" /></button>
                </div>
            </div>
        </form>
    </div>
    <div nama="typediv2" id="typediv2" style="display:none;">
        <form class="layui-form" action="${ctx}/commodity/commodityInfo/add" method="post" id="myFormCommon"
              enctype="multipart/form-data">
            <%--<spring:message code="sp.commodityBatch.hidden.attribute" />--%>
            <input type="hidden" id="sbrandId" name="sbrandId" value=""/><%--<spring:message code="sp.commodityInfo.brand.id" />--%>
            <input type="hidden" name="istoreDevice" id="comType" value="" class="layui-input"/>
            <div class="layui-form-item">
                <div class="layui-col-md6" id="scommdityName">
                    <label class="layui-form-label"><spring:message code="main.product.name" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="sname" id="sname" datatype="*" nullmsg='<spring:message code="sp.commodityInfo.please.enter.the.product.name" />' value=""
                               class="layui-input" placeholder='<spring:message code="main.required" />...'/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label" ><spring:message code="sp.brand.brand.name" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="sbrandName" placeholder='<spring:message code="main.required" />...' datatype="*" nullmsg='<spring:message code="sp.commodityInfo.please.select.a.brand.name" />'
                               id="sbrandName" class="layui-input" readonly/>
                    </div>
                    <button class="layui-btn" id="btn2" style="display: inline-block;float: left;"
                            type="button" data-type="selBrand"><spring:message code="main.choice" />
                    </button>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label" ><spring:message code="sp.commodityInfo.large.class.name" /></label>
                    <div class="layui-input-inline">
                        <select lay-verify="required" lay-search="" name="sbigCategoryName" id="sbigCategoryName"
                                class="state"
                        <%--datatype="*"  nullmsg='<spring:message code="sp.commodityInfo.please.select.a.large.class.name" />'  --%>
                                lay-filter="bigCategoryName">
                            <option value=""><spring:message code="main.please.choose" /></option>
                            <c:forEach items="${bigCategoryList}" var="bc">
                                <option value="${bc.id}_${bc.sname}">${bc.sname}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="sp.commodityInfo.small.class.name" /></label>
                    <div class="layui-input-inline">
                        <select lay-verify="required" lay-search="" name="ssmallCategoryName" id="ssmallCategoryName"
                                class="state"
                        <%--datatype="*"  nullmsg='<spring:message code="sp.commodityInfo.please.select.a.small.class.name" />'   --%>
                                lay-filter="ssmallCategoryName">
                        </select>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label" ><spring:message code="sp.commodityInfo.shelf.life.type" /></label>
                    <div class="layui-input-inline">
                        <cang:select type="list" name="ilifeType" id="ilifeType" entire="true" value=""
                                     layFilter="ilifeType" exp="springMessageCode=sp.commodityInfo.please.select.the.shelf.life.type"
                                     list='{10:springMessageCode=sp.commodityInfo.day,20:springMessageCode=sp.commodityInfo.month}'/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label" ><spring:message code="sm.shelf.life" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="ishelfLife" datatype="*" nullmsg='<spring:message code="sp.commodityInfo.please.enter.the.expiration.date" />' placeholder='<spring:message code="main.required" />...'
                               id="ishelfLife" value="" class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="sp.commodityInfo.specification.unit" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="sspecUnit" datatype="*" nullmsg='<spring:message code="sp.commodityInfo.please.enter.the.specification.unit" />' placeholder='<spring:message code="main.required" />...'
                               id="sspecUnit" value="" class="layui-input"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="sp.commodityInfo.specification/weight" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="ispecWeight" datatype="*" nullmsg='<spring:message code="sp.commodityInfo.please.enter.the.specification/weight" />' placeholder='<spring:message code="main.required" />...'
                               id="ispecWeight" value="" class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="sp.commodityInfo.commodity.weight" />(g)</label>
                    <div class="layui-input-inline">
                        <input type="text" name="iweigth" datatype="*" nullmsg='<spring:message code="sp.commodityInfo.please.enter.the.weight.of.the.item" />' placeholder='<spring:message code="main.required" />...'
                               id="iweigth" value="" class="layui-input"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="sp.commodityInfo.product.weight.floating.value" />(g)</label>
                    <div class="layui-input-inline">
                        <input type="text" name="icommodityFloat" id="icommodityFloat" value="" class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="sp.commodityInfo.minimum.sales.packaging.unit" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="spackageUnit" datatype="*" nullmsg='<spring:message code="sp.commodityInfo.please.enter.the.minimum.sales.package.unit" />' placeholder='<spring:message code="main.required" />...'
                               id="spackageUnit" value="" class="layui-input"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="sp.commodityInfo.Identification.type" /></label>
                    <div class="layui-input-inline">
                        <cang:select type="dic" name="iidentificationType" id="iidentificationType" entire="true"
                                     value="${commoditySku.iidentificationType}"
                                     groupNo="SP000153"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="sp.commodityInfo.taste" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="staste" id="staste" value="" class="layui-input"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="sp.commodityInfo.packaging.material" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="spackagingMaterial" id="spackagingMaterial" value=""
                               class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="sp.commodityInfo.place.of.origin" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="sorigin" id="sorigin" value="" class="layui-input"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="sp.commodityInfo.product.barcode" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="sproductBarcode" id="sproductBarcode" value="" class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="sp.commodityInfo.label.name" /></label>
                    <div class="layui-input-inline">
                        <select lay-verify="required" lay-search="" name="slabelName" id="slabelName" class="state" lay-filter="label">
                            <option value=""><spring:message code="main.please.choose" /></option>
                            <c:forEach items="${labelInfoList}" var="label">
                                <option value="${label.id}_${label.sname}">${label.sname}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label" ><spring:message code="sp.commodityInfo.sale.price" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="fsalePrice" id="fsalePrice" value="" datatype="*" nullmsg='<spring:message code="sp.commodityInfo.please.enter.the.sales.price.(number)" />'
                               class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="sp.commodityBatch.cost.price" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="fcostPrice" id="fcostPrice" value="" datatype="*" nullmsg='<spring:message code="sp.commodityInfo.please.enter.the.cost.price.(number)" />'
                               class="layui-input"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label" ><spring:message code="sp.commodityInfo.market.price" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="fmarketPrice" id="fmarketPrice" value="" class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label" ><spring:message code="sp.commodityInfo.product.short.name" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="sshortName" id="sshortName" value=""
                               class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item m-t">
                <div class="layui-col-md6 ">
                    <label class="layui-form-label"></label>
                    <div class="layui-inline">
                        <button type="button" class="layui-btn" id="commonImgButton"><spring:message code="sp.commodityInfo.select.product.image" /></button>
                        <blockquote class="layui-elem-quote layui-quote-nm layui-quote-nm-1">
                            <spring:message code="sp.brand.preview" />
                            <div class="layui-upload-list" id="commonLogo">
                                <img src="${staticSource}/resources/images/37cang.png"
                                     style="width: 100%;height: 100%"/>
                            </div>
                        </blockquote>
                        <span class="require">(<spring:message code="sp.commodityInfo.pixels.white.background" />)</span>
                    </div>
                </div>
            </div>

            <div class="layui-form-item fixed-bottom">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-primary" id="cancelBtnCommon"><spring:message code="main.cancel" /></button>
                    <button class="layui-btn" id="myFormSubCommon"><spring:message code="main.save" /></button>
                </div>
            </div>
        </form>
    </div>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js" type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate', 'upload', 'layer'], function () {
        var form = layui.form;

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

        var upload = layui.upload, layer = layui.layer, $ = layui.jquery;

        upload.render({
            auto: false
            , elem: '#commonImgButton'
            , multiple: false
            , field: "file"
            , size: 200
            , accept: 'images'
            , exts: 'jpg'
            , choose: function (obj) {
                obj.preview(function (index, file, result) {
                    $("#commonLogo").find("img").attr("src", result);
                });
            }
        });
        //监听商品类型
        form.on('select(istoreDevice)', function (data) {
            if (!isEmpty(data.value)) {
                $("#istoreDevice").removeClass("Validform_error");
                $("#istoreDevice").parent().find("span").hide();
            } else {
                $("#istoreDevice").addClass("Validform_error");
                $("#istoreDevice").parent().find("span").html($("#istoreDevice").attr("nullmsg"));
                $("#istoreDevice").parent().find("span").removeClass("Validform_right");
                $("#istoreDevice").parent().find("span").addClass("Validform_wrong");
                $("#istoreDevice").parent().find("span").show();
            }
            if ("10" == data.value) {//视觉商品
                $("#typediv2").css('display', 'none');
                $("#typediv1").css('display', 'contents');
                $("#typediv3").css('display', '');
                $("#scommdityName").css('display', 'none');
                document.getElementById("myFormCommon").reset();
            } else if ("20" == data.value) {//RFID
                $("#typediv2").css('display', '');
                $("#typediv1").css('display', 'none');
                $("#typediv3").css('display', 'none');
                $("#scommdityName").css('display', '');
                $("#comType").val("20");
                document.getElementById("myFormVr").reset();
            } else if ("30" == data.value) {//其他商品
                $("#typediv2").css('display', '');
                $("#typediv1").css('display', 'none');
                $("#typediv3").css('display', 'none');
                $("#scommdityName").css('display', '');
                $("#comType").val("30");
                document.getElementById("myFormVr").reset();
            } else {//请选择
                $("#typediv2").css('display', 'none');
                $("#typediv1").css('display', 'none');
                $("#typediv3").css('display', 'none');
                $("#scommdityName").css('display', '');
                $("#comType").val("");
                document.getElementById("myFormCommon").reset();
                document.getElementById("myFormVr").reset();
            }
        });

        // 视觉商品提交
        $("#myFormVr").Validform({
            btnSubmit: "#myFormSubVr",  //根据id触发
            tiptype: 3,                  //第三种方式
            showAllError: true,
            beforeSubmit: function () { //验证过后执行save方法
                var loadIndex = loading();
                $('#myFormVr').ajaxSubmit({
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

        // 普通商品提交
        $("#myFormCommon").Validform({
            btnSubmit: "#myFormSubCommon",  //根据id触发
            tiptype: 3,                  //第三种方式
            showAllError: true,
            beforeSubmit: function () { //验证过后执行save方法
                var loadIndex = loading();
                $('#myFormCommon').ajaxSubmit({
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
        form.on('select(brand)', function (data) {
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


    });


    function selectVrComodity() {
        showLayer('<spring:message code="sp.commodityInfo.choose.visual.goods" />', ctx + "/commoditySku/selectList", {area: ['100%', '80%']});
    }

    $(function () {
        $("#cancelBtnVr").click(function () {
            parent.layer.close(index);
            return false;
        });
    });

    $(function () {
        $("#cancelBtnCommon").click(function () {
            parent.layer.close(index);
            return false;
        });
    });


    //选择视觉设备call( )
    function selectSKU(Id, brandName, BigCategoryName, SmallCategoryName, IlifeType, IshelfLife, commodityName, sspecUnit, ispecWeight, spackageUnit, vrCommodityCode, vrCode, iidentificationType, staste, spackagingMaterial, sorigin, sproductBarcode, iweigth, icommodityFloat) {
        $("#skuId").val("");
        $("#sbrandName1").val("");
        $("#sbigCategoryName1").val("");
        $("#ssmallCategoryName1").val("");
        $("#ilifeType1").val("");
        $("#ishelfLife1").val("");
        $("#sspecUnit1").val("");
        $("#ispecWeight1").val("");
        $("#spackageUnit1").val("");
        $("#commodityName").val("");
        $("#sname").val("");
        $("#slabelName").val("");
        $("#vrCommodityCode").val("");
        $("#vrCode").val("");
        $("#iidentificationType1").val("");
        $("#staste1").val("");
        $("#spackagingMaterial1").val("");
        $("#sorigin1").val("");
        $("#sproductBarcode1").val("");
        $("#iweigth1").val("");
        $("#icommodityFloat1").val("");

        $("#skuId").val(Id);//商品SKU-Id
        $("#sbrandName1").val(brandName);//品牌
        $("#sbigCategoryName1").val(BigCategoryName);//大类
        $("#ishelfLife1").val(IshelfLife);//保质期
        $("#commodityName").val(commodityName);//商品名称
        // $("#sname").val(commodityName);//商品名称
        $("#ssmallCategoryName1").val(SmallCategoryName);//小类
        $("#sspecUnit1").val(sspecUnit);//规格单位
        $("#ispecWeight1").val(ispecWeight);//规格/重量
        $("#spackageUnit1").val(spackageUnit);//最小销售包装单位
        $("#vrCommodityCode").val(vrCommodityCode);//视觉商品编号
        $("#vrCode").val(vrCode);//视觉商品识别码
        $("#staste1").val(staste);//口味
        $("#spackagingMaterial1").val(spackagingMaterial);//包装材质
        $("#sorigin1").val(sorigin);//产地
        $("#sproductBarcode1").val(sproductBarcode);//条形码
        $("#iweigth1").val(iweigth);//商品重量
        $("#icommodityFloat1").val(icommodityFloat);// 商品误差值
        if ("10" == IlifeType) {//保质期类型
            $("#ilifeType1").val('<spring:message code="sp.commodityInfo.day" />');
        } else if ("20" == IlifeType){
            $("#ilifeType1").val('<spring:message code="sp.commodityInfo.month" />');
        }
        if ("10" == iidentificationType) {//保质期类型
            $("#iidentificationType1").val('<spring:message code="sp.commodityInfo.specialty" />');
        } else if ("20" == iidentificationType) {
            $("#iidentificationType1").val('<spring:message code="sp.commodityInfo.import" />');
        }
    }

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

