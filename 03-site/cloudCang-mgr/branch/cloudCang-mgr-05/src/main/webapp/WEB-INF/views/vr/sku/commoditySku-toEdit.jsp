<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>编辑视觉商品信息</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/commoditySku/Edit" method="post" id="myForm" enctype="multipart/form-data">
        <%--隐藏属性--%>
        <input type="hidden" id="sbrandId" name="sbrandId" value="${commoditySku.sbrandId}"/><%--品牌ID--%>
        <input type="hidden" id="scode" name="scode" value="${commoditySku.scode}"/><%--品牌ID--%>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="vrsku.visual.product.name" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="scommodityName" datatype="*" nullmsg='<spring:message code="vrsku.please.enter.the.product.name" />' id="scommodityName"
                           value="${commoditySku.scommodityName}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="vrsku.barcode" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sproductBarcode" id="sproductBarcode"
                           value="${commoditySku.sproductBarcode}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="vrsku.barcode" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="svrCode" id="svrCode" datatype="*" nullmsg='<spring:message code="vrsku.barcode" />'
                           value="${commoditySku.svrCode}"
                           class="layui-input" placeholder='<spring:message code="main.required" />...'/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="vrsku.brand.name" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sbrandName" placeholder='<spring:message code="main.required" />...' datatype="*" nullmsg='<spring:message code="vrsku.please.select.a.brand.name" />'
                           id="sbrandName" class="layui-input" value="${commoditySku.sbrandName}" readonly/>
                </div>
                <button class="layui-btn" id="btn1" style="display: inline-block;float: left;"
                        type="button" data-type="selBrand"><spring:message code="vrsku.select" />
                </button>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="vrsku.large.class.name" /></label>
                <div class="layui-input-inline">
                    <select lay-verify="required" lay-search="" name="sbigCategoryName" id="sbigCategoryName"
                            class="state"
                    <%--datatype="*"  nullmsg="请选择大类名称" --%>
                            lay-filter="bigCategoryName">
                        <option value=""><spring:message code="main.please.choose" /></option>
                        <c:forEach items="${bigCategoryList}" var="bc">
                            <option value="${bc.id}_${bc.sname}"
                                    <c:if test="${bc.id eq commoditySku.sbigCategoryId}">selected</c:if>>${bc.sname}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="vrsku.small.class.name" /></label>
                <div class="layui-input-inline">
                    <select lay-verify="required" lay-search="" name="ssmallCategoryName" id="ssmallCategoryName"
                            class="state"
                    <%--datatype="*"  nullmsg="请选择小类名称" --%>
                            lay-filter="ssmallCategoryName">
                        <option value=""><spring:message code="main.please.choose" /></option>
                        <c:forEach items="${smallCategoryList}" var="sc">
                            <option value="${sc.id}_${sc.sname}"
                                    <c:if test="${sc.id eq commoditySku.ssmallCategoryId}">selected</c:if>>${sc.sname}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="vrsku.shelf.life" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="ishelfLife" datatype="*" nullmsg='<spring:message code="vrsku.please.enter.the.expiration.date" />' id="ishelfLife"
                           value="${commoditySku.ishelfLife}"
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="vrsku.type.of.shelf.life" /></label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="ilifeType" id="ilifeType" entire="true"
                                 layFilter="ilifeType" exp="springMessageCode=sp.commodityInfo.please.select.the.shelf.life.type"
                                 value="${commoditySku.ilifeType }" list="{10:springMessageCode=vrsku.day,20:springMessageCode=vrsku.month}"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="vrsku.minimum.sales.packaging.unit" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="spackageUnit" datatype="*" nullmsg='<spring:message code="vrsku.please.enter.the.minimum.sales.package.unit" />' id="spackageUnit"
                           value="${commoditySku.spackageUnit}"
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="vrsku.manufacturer" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="smanufacturer" id="smanufacturer" value="${commoditySku.smanufacturer}"
                           class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="vrsku.specification.unit" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sspecUnit" datatype="*" nullmsg='<spring:message code="vrsku.please.enter.the.specification.unit" />' id="sspecUnit"
                           value="${commoditySku.sspecUnit}"
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="vrsku.specification.weight" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="ispecWeight" datatype="*" nullmsg='<spring:message code="vrsku.please.enter.the.specification.weight" />' id="ispecWeight"
                           value="${commoditySku.ispecWeight}"
                           class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="vrsku.product.weight" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="iweigth" datatype="*" nullmsg='<spring:message code="vrsku.please.enter.the.weight.of.the.item" />' placeholder='<spring:message code="main.required" />...' id="iweigth"
                           value="${commoditySku.iweigth}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="vrsku.floating.value.of.commodity.weight" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="icommodityFloat" id="icommodityFloat"
                           value="${commoditySku.icommodityFloat}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="vrsku.taste" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="staste" id="staste" value="${commoditySku.staste}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="vrsku.packing.material" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="spackagingMaterial" id="spackagingMaterial"
                           value="${commoditySku.spackagingMaterial}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="vrsku.origin" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sorigin" id="sorigin" value="${commoditySku.sorigin}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="vrsku.identification.type" /></label>
                <div class="layui-input-inline">
                    <cang:select type="dic" name="iidentificationType" id="iidentificationType" entire="true"
                                 value="${commoditySku.iidentificationType}"
                                 groupNo="SP000153"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label"><spring:message code="main.remarks" /></label>
            <div class="layui-input-block">
                <textarea id="sremark" name="sremark" class="layui-textarea layui-form-textarea80p"
                          placeholder='<spring:message code="main.remarks" />（<spring:message code="main.optional" />）'>${commoditySku.sremark}</textarea>
            </div>
        </div>
        <div class="layui-form-item m-t">
            <div class="layui-col-md6 ">
                <label class="layui-form-label"></label>
                <div class="layui-inline">
                    <button type="button" class="layui-btn" id="commonImgButton"><spring:message code="vrsku.choose.visual.commodity.pictures" /></button>
                    <blockquote class="layui-elem-quote layui-quote-nm layui-quote-nm-1">
                        <spring:message code="vrsku.preview" />
                        <div class="layui-upload-list" id="commonLogo">
                            <c:if test="${!empty commoditySku.scommodityImg}">
                                <img src="${dynamicSource}${commoditySku.scommodityImg}"
                                     style="width: 100%;height: 100%"/>
                            </c:if>
                            <c:if test="${empty commoditySku.scommodityImg}">
                                <img src="${staticSource}/resources/images/37cang.png"
                                     style="width: 100%;height: 100%"/>
                            </c:if>
                        </div>
                    </blockquote>
                    <span class="require"><spring:message code="vrsku.pixels.white.background.jpg.image.below" /></span>
                </div>
            </div>
        </div>

        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
                <button class="layui-btn" id="myFormSub"><spring:message code="main.save" /></button>
            </div>
        </div>
        <input type="hidden" name="id" id="spId" value="${commoditySku.id}" class="layui-input"/>
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
                showLayer("<spring:message code='sp.commodityInfo.choose.a.brand' />", ctx + "/commoditySku/radioSelect", {area: ['90%', '80%']});
            }
        };
        $('.layui-form .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

        //图片上传start
        var $ = layui.jquery, upload = layui.upload;
        upload.render({
            auto: false,
            elem: '#commonImgButton',
            multiple: false,
            field: "file",
            size: 200,
            accept: 'images',
            exts: 'jpg',
            choose: function (obj) {
                obj.preview(function (index, file, result) {
                    $("#commonLogo").find("img").attr("src", result);
                });
            }
        });

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
                        var id = msg.data;
                        if (!isEmpty(id)) { // 表示修改视觉商品信息成功
                            if (typeof(showConfirm) != 'undefined') {
                                showConfirm(id, loadIndex);
                                return;
                            }
                        }
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


    });
    <shiro:hasPermission name="SP_SKU_SYNCHRONIZE">
    function showConfirm(id, loadIndex) {
        layer.confirm("<spring:message code='vr.whether.to.sync.to.the.corresponding.product' />", {
            btn: ['<spring:message code='main.no' />', <shiro:hasPermission name="SP_SKU_SYNCHRONIZE">"<spring:message code='main.yes' />"</shiro:hasPermission>], //按钮
            cancel: function (indexC) {
                closeLayer(indexC);//关闭加载框
                closeLayer(loadIndex);
                parent.closeLayerAndRefresh(index);
            }
        }, function (indexC) {
            closeLayer(indexC);//关闭加载框
            closeLayer(loadIndex);
            parent.closeLayerAndRefresh(index);
        }, function () {
            $.ajax({
                type: "post",
                url: '${ctx}/commoditySku/synchronize',
                data: {checkboxId: id},
                dataType: "json",
                async: true,
                error: function () {
                    alertDelAndReload("<spring:message code='vr.sync.failed.please.use.the.sync.button.to.sync' />");
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
        });
    }
    </shiro:hasPermission>
    $(function () {
        $("#cancelBtn").click(function () {
            parent.layer.close(index);
            return false;
        });
    });

    function selectBrand(sbrandId, sbrandName) {
        $("#sbrandId").val(sbrandId);//<spring:message code='sp.commodityBatch.merchant.id' />
        $("#sbrandName").val(sbrandName);//<spring:message code='sh.business.name' />
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

