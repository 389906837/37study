<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>增加商品品牌信息</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?223" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/commodity/brandInfo/Edit" method="post" id="myForm" enctype="multipart/form-data">
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.brand.brand.name" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sname" datatype="*" nullmsg='<spring:message code="sp.brand.please.enter.a.brand.name" />' id="sname" value="${brandInfo.sname}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="main.sort" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="isort" id="isort" value="${brandInfo.isort}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item m-t">
            <div class="layui-col-md6 ">
                <label class="layui-form-label"></label>
                <div class="layui-inline">
                    <button type="button" class="layui-btn" id="commonImgButton"><spring:message code="sp.brand.choose.a.brand.image" /></button>
                    <blockquote class="layui-elem-quote layui-quote-nm layui-quote-nm-1">
                        <spring:message code="sp.brand.preview" />
                        <div class="layui-upload-list" id="commonLogo">
                            <c:if test="${!empty brandInfo.slogo}">
                                <img src="${dynamicSource}${brandInfo.slogo}" style="width: 100%;height: 100%" />
                            </c:if>
                            <c:if test="${empty brandInfo.slogo}">
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
                <button class="layui-btn" lay-submit="" lay-filter="myFormSub"><spring:message code="main.save" /></button>
            </div>
        </div>
        <input type="hidden"  name="id" id="id" value="${brandInfo.id}" class="layui-input" />
    </form>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
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
        //监听提交
        form.on('submit(myFormSub)', function () {
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

