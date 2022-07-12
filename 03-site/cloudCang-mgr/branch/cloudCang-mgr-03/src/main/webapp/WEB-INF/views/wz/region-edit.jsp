<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>运营区域编辑</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?1" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/region/save" method="post" id="myForm">
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="wz.coding" /></label>
                <div class="layui-inline">
                    <input type="text" name="scode" id="scode" value="${region.scode}" class="layui-input" disabled/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="wz.name" /></label>
                <div class="layui-inline">
                    <input type="text" name="sregionName" id="sregionName" value="${region.sregionName}" datatype="*"
                           nullmsg='<spring:message code="wz.please.enter.a.name" />' class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="wz.quantity" /></label>
                <div class="layui-inline">
                    <input type="text" name="icount" id="icount" value="${region.icount}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="wz.qperation.area.type" /></label>
                <div class="layui-inline" style="width: 182px;">
                    <cang:select type="list" name="itype" id="itype" layFilter="discountSelect"
                                 value="${region.itype}" list="{10:springMessageCode=wz.ad,20:springMessageCode=menu.system.notification}" exp="springMessageCode=wz.please.select.the.type.of.operation.area"
                                 entireName='springMessageCode=wz.please.select.the.type.of.operation.area' entire="true"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" ><spring:message code="wz.description" /></label>
            <div class="layui-input-block">
                <textarea class="layui-textarea" style="width: 470px" id="sremark"
                          name="sremark">${region.sremark}</textarea>
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
        <input type="hidden" id="id" name="id" value="${region.id}"/>
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

