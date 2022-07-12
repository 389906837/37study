<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>运营参数编辑</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/businessPara/save" method="post" id="myForm">

        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sys.business.para.name" /></label>
                <div class="layui-input-inline">
                    <c:if test="${empty businessParameter.id}">
                        <input type="text" name="name" datatype="*" nullmsg='<spring:message code="sys.business.para.name.empty" />' id="name"
                               value="${businessParameter.name}" class="layui-input"/>
                    </c:if>
                    <c:if test="${not empty businessParameter.id}">
                        <input type="text" readonly="readonly" name="name" datatype="*" nullmsg='<spring:message code="sys.business.para.name.empty" />' id="name"
                               value="${businessParameter.name}" class="layui-input"/>
                    </c:if>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sys.business.parameter.values" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="value" id="value" value="${businessParameter.value}" datatype="*"
                           nullmsg='<spring:message code="sys.business.parameter.values.empty" />' class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sys.business.sort" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sort" datatype="*" nullmsg='<spring:message code="sys.business.sort.empty" />' id="sort"
                           value="${businessParameter.sort}" class="layui-input"/>
                </div>
            </div>

        </div>
        <div class="layui-form-item">
            <label class="layui-form-label"><spring:message code="sys.business.description.parameters" /></label>
            <div class="layui-input-block">
                <textarea id="remark" name="remark" class="layui-textarea layui-form-textarea350"
                          placeholder='<spring:message code="main.remarks" />（<spring:message code="main.not.required" />）'>${businessParameter.remark}</textarea>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
                <button class="layui-btn" id="myFormSub"><spring:message code="main.save" /></button>
            </div>
        </div>
        <input type="hidden" id="id" name="id" value="${businessParameter.id}"/>
    </form>
</div>


<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js" type="text/javascript"></script>

<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate'], function () {
        var form = layui.form;
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
                               /* cancel: function () {
                                    parent.closeLayerAndRefresh(index);
                                }*/
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

