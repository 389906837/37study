<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>菜单编辑</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?2" rel="stylesheet">
<style type="text/css">
    .layui-form-label{width: 100px;}
</style>
</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/purview/save" method="post" id="myForm">
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='hy.authority.code' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="spurCode" datatype="*" nullmsg="<spring:message code='hy.please.enter.the.permission.code' />" id="spurCode"
                           value="${purview.spurCode}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='hy.privilege.name' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="spurName" datatype="*" nullmsg="<spring:message code='hy.please.enter.a.permission.name' />" id="spurName"
                           value="${purview.spurName}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='sh.merchant.available' /></label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="iisMerchantUsed" id="iisMerchantUsed"
                                 exp="springMessageCode=sh.please.select.if.the.merchant.is.available"
                                 value="${purview.iisMerchantUsed}" list='{0:springMessageCode=main.no,1:springMessageCode=main.yes}'/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='sh.path' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="surlPath" datatype="*" nullmsg="<spring:message code='sh.please.enter.the.path' />" id="surlPath"
                           value="${purview.surlPath}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label"><spring:message code="main.remarks" /></label>
            <div class="layui-input-block">
                <textarea class="layui-textarea layui-form-textarea80p" name="sdescription"
                          id="sdescription">${purview.sdescription}</textarea>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
                <button class="layui-btn" <%--lay-submit="" lay-filter="myFormSub"--%>id="myFormSub"><spring:message code="main.save" /></button>
            </div>
        </div>
        <input type="hidden" id="id" name="id" value="${purview.id}"/>
        <input type="hidden" id="sparentId" name="sparentId" value="${purview.sparentId}"/>
    </form>
</div>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js" type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate'], function () {
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

