<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>编辑商品分类信息</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
<style type="text/css">
    .layui-form-label{width:115px;}
</style>
</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/commodity/category/Edit" method="post" id="myForm" enctype="multipart/form-data">
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.category.category.name" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sname" datatype="*" nullmsg='<spring:message code="sp.category.please.enter.a.category.name" />' id="sname" value="${category.sname}"
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="main.sort" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="isort"  id="isort" value="${category.isort}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.category.whether.to.display.externally" /></label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="iisDisplay" id="iisDisplay" entire="true" value="${category.iisDisplay}"  list='{0:springMessageCode=main.no,1:springMessageCode=main.yes}'/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.category.whether.it.is.popular" /></label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="iisHot" id="iisHot" entire="true" value="${category.iisHot}"  list='{0:springMessageCode=main.no,1:springMessageCode=main.yes}'/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label"><spring:message code="main.remarks" /></label>
            <div class="layui-input-block">
                <textarea id="sremark" name="sremark" class="layui-textarea layui-form-textarea80p"  placeholder='<spring:message code="sp.category.not.required" />'>${category.sremark}</textarea>
            </div>
        </div>
        <div class="layui-form-item m-t">
            <div class="layui-col-md6 ">
                <label class="layui-form-label"></label>
                <div class="layui-inline">
                    <button type="button" class="layui-btn" id="commonImgButton"><spring:message code="sp.category.select.category.icon" /></button>
                    <blockquote class="layui-elem-quote layui-quote-nm layui-quote-nm-1">
                        <spring:message code="sp.brand.preview" />
                        <div class="layui-upload-list" id="commonLogo">
                            <c:if test="${!empty category.sicon}">
                                <img src="${dynamicSource}${category.sicon}" style="width: 100%;height: 100%" />
                            </c:if>
                            <c:if test="${empty category.sicon}">
                                <img src="${staticSource}/resources/images/37cang.png" style="width: 100%;height: 100%" />
                            </c:if>
                        </div>
                    </blockquote>
                    <span class="require">(<spring:message code="sp.brand.pixels.jpg.or.png.images.below" />)</span>
                </div>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
                <button class="layui-btn" <%--lay-submit="" lay-filter="myFormSub"--%> id="myFormSub"><spring:message code="main.save" /></button>
            </div>
        </div>
        <input type="hidden"  name="id" id="mdcId" value="${category.id}" class="layui-input" />
    </form>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js"  type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var form;
    layui.use(['form', 'layedit', 'laydate', 'upload'], function () {
        form = layui.form;
        //图片上传start
        var $ = layui.jquery, upload = layui.upload;
        upload.render({
            auto: false
            , elem: '#commonImgButton'
            , multiple: false
            , field: "file"
            , size: 200
            , accept: 'images'
            , exts: 'jpg|png'
            , choose: function (obj) {
                obj.preview(function (index, file, result) {
                    $("#commonLogo").find("img").attr("src", result);
                });
            }
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
        form.on('select(iisParent)', function (data) {
            var isParent = data.value;
            var categoryId = '${category.id}';//分类ID
            if("0" == isParent) {//不是父类
                $.ajax({
                    type: 'post',
                    url: '${ctx}/commodity/category/getParentCategory',
                    data: {pid: categoryId},
                    dataType: 'json',
                    success: function (resp) {
                        $("#parentName").html("");
                        var option1 = $("<option>").val("").text('<spring:message code="main.please.choose" />');
                        $("#parentName").append(option1);
                        $.each(resp.data, function (key, val) {
                            var option1 = $("<option>").val(val.id).text(val.sname);
                            $("#parentName").append(option1);
                            form.render('select');
                        });
                    }
                });
            } else {
                $("#parentName").html("");
                form.render('select');
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

