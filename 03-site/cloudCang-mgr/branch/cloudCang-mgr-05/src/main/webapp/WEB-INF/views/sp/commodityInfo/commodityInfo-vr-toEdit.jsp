<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>编辑视觉商品信息</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/commodity/commodityInfo/Edit" method="post" id="myForm"
          enctype="multipart/form-data">
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
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label" ><spring:message code="sp.brand.brand.name" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sbrandName1" id="sbrandName1" value="${commodityInfo.sbrandName}"
                           class="layui-input" disabled/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="main.product.name" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="commodityName1" id="commodityName1" value="${commodityInfo.sname}"
                           class="layui-input" disabled/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityInfo.visual.product.name" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="svrCommodityCode1" id="svrCommodityCode1"
                           value="${commodityInfo.svrCommodityCode}" class="layui-input" disabled/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityInfo.visual.product.identification.number" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="svrCode1" id="svrCode1" value="${commodityInfo.svrCode}"
                           class="layui-input" disabled/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label" ><spring:message code="sp.commodityInfo.large.class.name" /></label>
                <div class="layui-input-inline">
                    <input lay-verify="required" lay-search="" type="text" name="sbigCategoryName1"
                           id="sbigCategoryName1"
                           value="${commodityInfo.sbigCategoryName}" class="layui-input" disabled/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityInfo.small.class.name" /></label>
                <div class="layui-input-inline">
                    <input lay-verify="required" lay-search="" type="text" name="ssmallCategoryName1"
                           id="ssmallCategoryName1"
                           value="${commodityInfo.ssmallCategoryName}" class="layui-input" disabled/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label" ><spring:message code="sp.commodityInfo.shelf.life.type" /></label>
                <div class="layui-input-inline">
                    <c:if test="${commodityInfo.ilifeType == 10 }">
                        <input type="text" name="ilifeType1" id="ilifeType1" value='<spring:message code="sp.commodityInfo.day" />' class="layui-input" disabled/>
                    </c:if>
                    <c:if test="${commodityInfo.ilifeType == 20 }">
                        <input type="text" name="ilifeType2" id="ilifeType2" value='<spring:message code="sp.commodityInfo.month" />' class="layui-input" disabled/>
                    </c:if>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label" ><spring:message code="sm.shelf.life" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="ishelfLife1" id="ishelfLife1" value="${commodityInfo.ishelfLife}"
                           class="layui-input" disabled/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityInfo.specification.unit" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sspecUnit" id="sspecUnit1" value="${commodityInfo.sspecUnit}" disabled
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityInfo.specification/weight" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="ispecWeight" id="ispecWeight1" value="${commodityInfo.ispecWeight}"
                           disabled
                           class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityInfo.commodity.weight" />（g）</label>
                <div class="layui-input-inline">
                    <input type="text" name="iweigth" id="iweigth" value="${commodityInfo.iweigth}" disabled
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityInfo.product.weight.floating.value" />（g）</label>
                <div class="layui-input-inline">
                    <input type="text" name="icommodityFloat" id="icommodityFloat"
                           value="${commodityInfo.icommodityFloat}" disabled class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityInfo.minimum.sales.packaging.unit" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="spackageUnit" id="spackageUnit1" value="${commodityInfo.spackageUnit}"
                           disabled
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityInfo.Identification.type" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="iidentificationType1" id="iidentificationType1"
                           value='<c:if test="${commodityInfo.iidentificationType == 10 }"><spring:message code="sp.commodityInfo.specialty" /></c:if><c:if test="${commodityInfo.iidentificationType == 20 }"><spring:message code="sp.commodityInfo.import" /></c:if>'
                           class="layui-input" disabled/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityInfo.taste" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="staste1" id="staste1" value="${commodityInfo.staste}" class="layui-input"
                           disabled/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityInfo.packaging.material" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="spackagingMaterial1" id="spackagingMaterial1"
                           value="${commodityInfo.spackagingMaterial}" class="layui-input" disabled/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityInfo.place.of.origin" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sorigin1" id="sorigin1" value="${commodityInfo.sorigin}"
                           class="layui-input" disabled/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityInfo.product.barcode" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sproductBarcode1" id="sproductBarcode1"
                           value="${commodityInfo.sproductBarcode}" class="layui-input" disabled/>
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
                <label class="layui-form-label" ><spring:message code="sp.commodityInfo.market.price" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="fmarketPrice" id="fmarketPrice" value="${commodityInfo.fmarketPrice}"
                           class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label" ><spring:message code="sp.commodityInfo.sale.price" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="fsalePrice" id="fsalePrice" value="${commodityInfo.fsalePrice}"
                           datatype="*" nullmsg='<spring:message code="sp.commodityInfo.please.enter.the.sales.price" />'
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label" ><spring:message code="sp.commodityInfo.product.short.name" /></label>
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
                    <%--<button type="button" class="layui-btn" id="scommodityImgBut"><spring:message code="sp.commodityInfo.show.pictures" /></button>--%>
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
                    <span class="require">(<spring:message code="sp.commodityInfo.pixels.white.background" />)</span>
                </div>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
                <button class="layui-btn" <%--lay-submit="" lay-filter="myFormSub"--%> id="myFormSub"><spring:message code="main.save" /></button>
            </div>
        </div>
        <input type="hidden" name="iidentificationType" id="iidentificationType"
               value="${commodityInfo.iidentificationType}" class="layui-input"/>
        <input type="hidden" name="staste" id="staste" value="${commodityInfo.staste}" class="layui-input"/>
        <input type="hidden" name="spackagingMaterial" id="spackagingMaterial"
               value="${commodityInfo.spackagingMaterial}" class="layui-input"/>
        <input type="hidden" name="sorigin" id="sorigin" value="${commodityInfo.sorigin}" class="layui-input"/>
        <input type="hidden" name="sproductBarcode" id="sproductBarcode" value="${commodityInfo.sproductBarcode}"
               class="layui-input"/>
        <input type="hidden" name="id" id="spId" value="${commodityInfo.id}" class="layui-input"/>
        <input type="hidden" name="istoreDevice" id="istoreDevice" value="${commodityInfo.istoreDevice}"
               class="layui-input"/>
        <%--<input type="hidden" name="sbrandName" id="sbrandName" value="${commodityInfo.sbrandId}_${commodityInfo.sbrandName}" class="layui-input"/>--%>
        <%--<input type="hidden" name="sbigCategoryName" id="sbigCategoryName" value="${commodityInfo.sbigCategoryId}_${commodityInfo.sbigCategoryName}" class="layui-input"/>--%>
        <%--<input type="hidden" name="ssmallCategoryName" id="ssmallCategoryName" value="${commodityInfo.ssmallCategoryId}_${commodityInfo.ssmallCategoryName}" class="layui-input"/>--%>
        <%--<input type="hidden" name="ishelfLife" id="ishelfLife" value="${commodityInfo.ishelfLife}}" class="layui-input"/>--%>
        <%--<input type="hidden" name="ilifeType" id="ilifeType" value="${commodityInfo.ilifeType}" class="layui-input"/>--%>
        <%--<input type="hidden" name="sspecUnit" id="sspecUnit" value="${commodityInfo.sspecUnit}" class="layui-input"/>--%>
        <%--<input type="hidden" name="ispecWeight" id="ispecWeight" value="${commodityInfo.ispecWeight}" class="layui-input"/>--%>
        <%--<input type="hidden" name="spackageUnit" id="spackageUnit" value="${commodityInfo.spackageUnit}" class="layui-input"/>--%>
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

        //图片上传start
        var $ = layui.jquery, upload = layui.upload;
//        upload.render({
//            elem:uploadConfig.elem,
//            auto: uploadConfig.auto,
//            field: uploadConfig.field,
//            multiple: uploadConfig.multiple,
//            size: uploadConfig.fileSize,
//            accept: uploadConfig.accept,
//            exts: uploadConfig.exts,
//            choose: function(obj){
//                obj.preview(function(index, file, result){
//                    $("#slogoYl").find("img").attr("src", result);
//                });
//            }
//        });
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

