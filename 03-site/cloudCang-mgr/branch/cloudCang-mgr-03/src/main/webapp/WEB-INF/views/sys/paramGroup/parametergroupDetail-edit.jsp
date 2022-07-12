<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>数据字典明细编辑</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet"/>
<link href="${staticSource}/resources/css/override.css" rel="stylesheet"/>
</head>

<body>
<div class="ibox-content" style="padding: 5px 0px;">
    <c:if test="${isAdd eq 1}">
    <form class="layui-form" action="${ctx}/paramGroup/saveDetail" method="post" id="myForm"
          enctype="multipart/form-data">
        </c:if>
        <c:if test="${isAdd ne 1}">
        <form class="layui-form" action="${ctx}/paramGroup/updateDetail" method="post" id="myForm"
              enctype="multipart/form-data">
            </c:if>
            <div class="layui-form-item">
                <label class="layui-form-label"><spring:message code='sys.business.para.name' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sname" datatype="*" nullmsg="<spring:message code='sys.business.para.name.empty' />" id="sname" value="${pgd.sname}"
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"><spring:message code='sys.business.parameter.values' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="svalue" datatype="*" nullmsg="<spring:message code='sys.business.parameter.values.empty' />" id="svalue" value="${pgd.svalue}"
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"><spring:message code="main.sort" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="isort" datatype="*" nullmsg='<spring:message code="sys.business.sort.empty" />' id="isort"
                           value="${pgd.isort}" class="layui-input"/>
                </div>
            </div>
            <c:choose>
                <c:when test="${'SP000134' eq groupNo}">
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"></label>
                            <div class="layui-inline" style="margin-bottom:50px;">
                                <button type="button" class="layui-btn" id="loginLogoBut"><spring:message code='sh.quick.menu.logo.upload' /></button>
                                <blockquote class="layui-elem-quote layui-quote-nm layui-quote-nm-1">
                                    <spring:message code='vrsku.preview' />
                                    <div class="layui-upload-list" id="slogo">
                                        <c:if test="${! empty  pgd.id}">
                                            <img src="${dynamicSource}${pgd.sremark}"
                                                 style="width: 100%;height: 100%"/>
                                        </c:if>
                                        <c:if test="${ empty  pgd.id}">
                                            <img src="${staticSource}/resources/images/37cang.png"
                                                 style="width: 100%;height: 100%"/>
                                        </c:if>
                                    </div>
                                </blockquote>
                                <span class="require">(32*37<spring:message code='sh.pixels.white.background.png.images.below' />)</span>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="layui-form-item">
                        <label class="layui-form-label"><spring:message code='wz.description' /></label>
                        <div class="layui-input-block">
                            <textarea class="layui-textarea layui-form-textarea260" name="sremark"
                                      id="sremark">${pgd.sremark}</textarea>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
            <div class="layui-form-item fixed-bottom">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
                    <button class="layui-btn" id="myFormSub"><spring:message code="main.save" /></button>
                </div>
            </div>
            <input type="hidden" id="id" name="id" value="${pgd.id}"/>
            <input type="hidden" id="sgroupid" name="sgroupid" value="${pgd.sgroupid}"/>
            <input type="hidden" id="groupNo" name="groupNo" value="${groupNo}"/>
        </form>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js" type="text/javascript"></script>

<script type="text/javascript">

    $(document).ready(function () {
        if (${not  empty pgd.id}) {
            $('#sname').attr("disabled", "disabled");
        }
    });
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate', 'upload'], function () {
        var form = layui.form;
        //上传图片
        var $ = layui.jquery
            , upload = layui.upload;
        upload.render({
            auto: false
            , elem: '#loginLogoBut'
            , multiple: false
            , field: "loginLogofile"
            , size: 100
            , accept: 'images'
            , exts: 'png'
            , choose: function (obj) {
                obj.preview(function (index, file, result) {
                    $("#slogo").find("img").attr("src", result);
                });
            }
        });
        //监听提交
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

