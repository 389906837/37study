<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
<title>会员积分调整</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/integralAccount/integralAdjustment" method="post" id="myForm">
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='main.member.username' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="smemberName" id="smemberName" value="${integralAccount.smemberName}" class="layui-input" disabled/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label"><spring:message code='hy.adjusting.points' /></label>
                    <div class="layui-input-inline" style="width: 90px;">
                        <select id="addLess" name="addLess" datatype="*" nullmsg="<spring:message code='sh.please.choose.the.type' />">
                            <option value="" ></option>
                            <option value="0"><spring:message code='hy.increase' /></option>
                            <option value="1"><spring:message code='hy.cut.back' /></option>
                        </select>
                    </div>
                    <div class="layui-input-inline" style="width: 90px;">
                        <input type="text" name="itotalPoints" id="itotalPoints" value="" class="layui-input" datatype="n" nullmsg="<spring:message code='wz.please.enter.the.number' />"/>
                    </div>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.close" /></button>
                <button class="layui-btn" id="myFormSub"><spring:message code="main.submit" /></button>
            </div>
        </div>
        <input type="hidden" id="id" name="id" value="${integralAccount.id}" />
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

