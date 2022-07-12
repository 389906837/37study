<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>用户角色分配</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/js/plugins/tree-multiselect/jquery.tree-multiselect.min.css" rel="stylesheet" />

</head>

<body>
<div class="ibox-content">
    <select id="roleSelect" multiple="multiple">
        <c:forEach items="${roleVoLsit}" var="item" varStatus="L">
         <%--   <c:if test="${item.bisroot eq '1'}">--%>
               <%-- <c:forEach varStatus="K" var="purItem" items="${item.nodes}">--%>
                    <%-- <option value="${item.id}#//#${purItem.id}" data-section="<spring:message code='sh.rights.profile' />/${item.menuName}/${purItem.menuName}" <c:if test="${purItem.isSelect eq 1}"> selected</c:if>>${purItem.text}</option>--%>
                <%--</c:forEach>--%>
         <%--   <option value="<${item.id}#//#${item.id}" data-section="<spring:message code='sh.rights.profile' />/${item.id}/${item.id}> 11" <c:if test="${item.isSelect eq 1}"> selected</c:if>>${item.sroleName}</option>--%>
          <%--  </c:if>--%>
<%--
            <option value="${item.id}"&lt;%&ndash;data-section="${item.sroleName}&ndash;%&gt;"<c:if test="${item.isSelect eq 1}"> selected</c:if>>${item.sroleName}</option>
--%>
            <%-- <c:if test="${item.bisroot ne '1'}">
                 <option value="${item.id}" data-section="${item.sparentName}" <c:if test="${item.isSelect eq 1}"> selected</c:if>>${item.text}</option>
             </c:if>--%>
          <%--  <option value="${item.id}" data-section="${item.sparentName}" <c:if test="${item.isSelect eq 1}"> selected</c:if>>${item.text}</option>--%>
            <option value="${item.id}"<c:if test="${item.isSelect eq 1}"> selected</c:if>>${item.sroleName}</option>
        </c:forEach>
    </select>

    <form class="layui-form" action="${ctx}/operator/saveOperatorRole" method="post" id="myForm">
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
                <button class="layui-btn" lay-submit="" lay-filter="myFormSub"><spring:message code="main.save" /></button>
            </div>
        </div>
        <input type="hidden" id="operatorId" name="operatorId" value="${operator.id}" />
        <input type="hidden" id="operatorRoleIds" name="operatorRoleIds" value="" />
    </form>

</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jquery-ui/jquery-ui.min.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/tree-multiselect/jquery.tree-multiselect.min.js"></script>

<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate'], function(){
        var form = layui.form;
        //监听提交
        form.on('submit(myFormSub)', function(){
            $("#operatorRoleIds").val($("#roleSelect").val());
            var loadIndex = loading();
            $('#myForm').ajaxSubmit({
                type : "post",
                dataType : "json",
                async : true,
                error:function() {
                    alertDelAndReload("<spring:message code='main.error.try.again' />");
                },
                success : function(msg) {
                    //成功返回
                    closeLayer(loadIndex);//关闭加载框
                    if(msg.success) {
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
    $(function() {
        $("#roleSelect").treeMultiselect();
        $("#cancelBtn").click(function() {
            parent.layer.close(index);
            return false;
        });
    });
</script>
</body>
</html>

