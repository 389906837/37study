<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title>初始化</title>
    <link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
    <link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
    <div class="ibox-content">
        <form class="layui-form" action="${ctx}/currenttradeDate/initFestivalDay" method="post" id="myForm">
            <div class="layui-form-item">

                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code='cs.years' /></label>
                    <div class="layui-input-inline">
                       <%-- <input type="text" name="sname" lay-verify="required" id="sname" value="${menu.sname}" class="layui-input" />--%>
                           <select name="year" class="form-control" id="year">
                              <c:forEach items="${dataList}"  var="item">
                                  <option value="${item}">${item}</option>
                              </c:forEach>
                           </select>
                    </div>
                </div>
            </div>
            <div class="layui-form-item fixed-bottom">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
                    <button class="layui-btn" lay-submit="" lay-filter="myFormSub"><spring:message code="main.save" /></button>
                </div>
            </div>
<%--
            <input type="hidden" id="id" name="id" value="${menu.id}" />
--%>
        </form>
    </div>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate'], function(){
        var form = layui.form;
        //监听提交
        form.on('submit(myFormSub)', function(){
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
       $("#cancelBtn").click(function() {
           parent.layer.close(index);
           return false;
       });
    });
</script>
</body>
</html>

