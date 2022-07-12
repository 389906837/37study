<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title>会员权限管理</title>
    <link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
    <link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
    <div class="ibox-content">
        <form class="layui-form" action="${ctx}/mbrPurview/save" method="post" id="myForm">
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="main.code" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="spurNo" id="spurNo" value="${mbrPurview.spurNo}" class="layui-input" disabled/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code='hy.authority.code' /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="spurCode" datatype="*" nullmsg="<spring:message code='hy.can.enter.any.character' />" id="spurCode" value="${mbrPurview.spurCode}" placeholder="<spring:message code='hy.please.enter.the.permission.code' />..." class="layui-input" />
                    </div>
                </div>
            </div>
                <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code='hy.privilege.name' /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="spurName" datatype="*" nullmsg="<spring:message code='hy.can.enter.any.character' />" id="spurName" value="${mbrPurview.spurName}" placeholder="<spring:message code='hy.please.enter.a.permission.name' />..." class="layui-input" />
                    </div>
                </div>
                </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code='hy.access.path' /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="surlPath" datatype="*" nullmsg="<spring:message code='hy.can.enter.any.character' />" id="surlPath" value="${mbrPurview.surlPath}" placeholder="<spring:message code='hy.please.enter.the.access.path' />..." class="layui-input" />
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code='hy.system.code' /></label>
                    <div class="layui-input-inline">
                        <cang:select type="list" id="ssysCode" name="ssysCode" value="${mbrPurview.ssysCode}" list="{10:SYS-WEB,20:SYS-WAP}" entire="true" exp="springMessageCode=hy.please.select.the.system.code" />
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code='hy.taxonomy' /></label>
                    <div class="layui-input-inline">
                       <cang:select type="list" id="ssysType" name="ssysType" value="${mbrPurview.ssysType}" list="{10:PC,20:WAP}" entire="true" exp="springMessageCode=hy.please.select.system.classification" />
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code='hy.permission.description' /></label>
                    <div class="layui-input-inline">
                        <textarea class="layui-textarea" name="sdescription" id="sdescription" style="width: 500px" placeholder="<spring:message code='hy.please.enter.a.permission.description' />...">${mbrPurview.sdescription}</textarea>
                    </div>
                </div>
            </div>
            <div class="layui-form-item fixed-bottom">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
                    <button class="layui-btn" id="myFormSub"><spring:message code="main.save" /></button>
                </div>
            </div>
            <input type="hidden" id="id" name="id" value="${mbrPurview.id}" />
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

