<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>菜单编辑</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?926" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/menu/save" method="post" id="myForm">
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='sh.parent.menu' /></label>
                <div class="layui-input-inline">
                    <select name="sparentId" id="sparentId">
                        <option value="0"><spring:message code='sh.please.select.the.parent.menu' /></option>
                        <c:forEach items="${parentMenu}" varStatus="L" var="item">
                            <option value="${item.id}"
                                    <c:if test="${item.id eq menu.sparentId}">selected="true"</c:if>>${item.sname}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='sh.menu.name' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sname" datatype="*" nullmsg="<spring:message code='sh.please.enter.a.menu.name' />" id="sname" value="${menu.sname}"
                           class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='sh.menu.icon' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="simagePath" id="simagePath" value="${menu.simagePath}"
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='sh.menu.path' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="smenuPath" datatype="*" nullmsg="<spring:message code='sh.please.enter.the.menu.path' />" id="smenuPath"
                           value="${menu.smenuPath}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="main.sort.name" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="isort" datatype="*" nullmsg='<spring:message code="sys.business.sort.empty" />' id="isort" value="${menu.isort}"
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='sh.subordinate.system' /></label>
                <div class="layui-input-inline">
                    <cang:select type="dic" groupNo="SP000157" entire="true" exp="springMessageCode=sh.please.select.a.sub-system"
                                 value="${menu.ssystemSource}" entireName='springMessageCode=sh.please.select.a.sub-system'
                                 name="ssystemSource" id="ssystemSource"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
                <label class="layui-form-label"><spring:message code="main.remarks" /></label>
                <div class="layui-input-block">
                    <textarea class="layui-textarea layui-form-textarea80p" name="sremark"
                              id="sremark">${menu.sremark}</textarea>
                </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
                <button class="layui-btn" <%--lay-submit="" lay-filter="myFormSub"--%>id="myFormSub"><spring:message code="main.save" /></button>
            </div>
        </div>
        <input type="hidden" id="id" name="id" value="${menu.id}"/>
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
                                /*cancel: function () {
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
        /*        form.on('submit(myFormSub)', function(){
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
         alertSuccess("<spring:message code='main.success' />",{cancel:function() {
         parent.closeLayerAndRefresh(index);
         }},function () {
         parent.closeLayerAndRefresh(index);
         });
         } else {
         alertDel(msg.msg);
         }
         }
         });
         return false;
         });*/


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

