<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title>网站关键字编辑</title>
    <link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
    <link href="${staticSource}/resources/css/override.css?1" rel="stylesheet">

</head>

<body>
    <div class="ibox-content">
        <form class="layui-form" action="${ctx}/keyWords/save" method="post" id="myForm">
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code='wz.page.number' /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="spageNo" id="spageNo" value="${keyWords.spageNo}" class="layui-input" disabled/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code='wz.page.name' /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="spageName" id="spageName" value="${keyWords.spageName}" datatype="*" nullmsg="<spring:message code='wz.please.enter.the.supplier.name' />" class="layui-input" />
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='wz.page.url' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="spageUrl" id="spageUrl" value="${keyWords.spageUrl}" datatype="*" nullmsg="<spring:message code='wz.please.enter.url' />" class="layui-input" />
                </div>
            </div>
            </div>
            <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='main.title.msg' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="stitle" id="stitle" value="${keyWords.stitle}" datatype="*"  nullmsg="<spring:message code='wz.please.enter.a.title' />" class="layui-input" />
                </div>
            </div>
            </div>
            <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='wz.keyword' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="skeyWord" id="skeyWord" value="${keyWords.skeyWord}" datatype="*"  nullmsg="<spring:message code='wz.please.enter.key.words' />" class="layui-input" />
                </div>
            </div>
            </div>
            <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label" ><spring:message code='xx.describe' /></label>
                <div class="layui-input-inline">
                    <textarea class="layui-textarea" style="width: 720px" id="sdescription" name="sdescription">${keyWords.sdescription}</textarea>
                </div>
            </div>
            </div>
            <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label" ><spring:message code="main.remarks" /></label>
                <div class="layui-input-inline">
                    <textarea class="layui-textarea" style="width: 720px" id="sremark" name="sremark">${keyWords.sremark}</textarea>
                </div>
            </div>
            </div>
            <div class="layui-form-item">
            <div class="layui-form-item fixed-bottom">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
                    <button class="layui-btn" id="myFormSub"><spring:message code="main.save" /></button>
                </div>
            </div>
            </div>
            <input type="hidden" id="id" name="id" value="${keyWords.id}" />
        </form>
    </div>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js"  type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate'], function(){
        var form = layui.form;
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
    });
    $(function() {
       $("#cancelBtn").click(function() {
           parent.layer.close(index);
           return false;
       });
    });
</script>
</body>
</html>

