<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>积分规则</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?20188" rel="stylesheet">
<style type="text/css">
    .layui-form-label {width: 150px;}
</style>
</head>
<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/integralRule/save" method="post" id="myForm">

        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='ac.couponrule.is.it.effective' /></label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="iisValid" id="iisValid"
                                 value="${integralRule.iisValid}" list="{1:springMessageCode=main.enable,0:springMessageCode=main.disable}"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='ac.integralrule.gift.method' /></label>
                <div class="layui-input-inline">
                    <cang:select type="dic" groupNo="SP000006" name="igiveMethod" layFilter="igiveMethod"
                                 id="igiveMethod" value="${empty integralRule.igiveMethod ? 10 : integralRule.igiveMethod}" />
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='ac.integralrule.points.minutes.ratio.%' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="fbaseIntegral" id="fbaseIntegral"
                           value='<fmt:formatNumber value="${integralRule.fbaseIntegral}" pattern="#0.##"/>' class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='ac.integralrule.gift.lower.limit.minutes' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="fminValue" id="fminValue"
                           value="${integralRule.fminValue}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='ac.integralrule.gift.limit.minutes' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="fmaxValue" id="fmaxValue"
                           value="${integralRule.fmaxValue}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='ac.integralrule.reward.description' /></label>
                <div class="layui-input-inline" style="width: 212px">
                    <textarea class="layui-textarea" datatype="*" nullmsg="<spring:message code='ac.integralrule.please.enter.a.reward.description' />" id="sactivityInstruction"
                              name="sactivityInstruction">${integralRule.sactivityInstruction}</textarea>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sm.remarks" /></label>
                <div class="layui-input-inline" style="width: 212px">
                    <textarea class="layui-textarea" id="sremark" name="sremark">${integralRule.sremark}</textarea>
                </div>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
                <button class="layui-btn" id="myFormSub"><spring:message code="main.save" /></button>
            </div>
        </div>
        <input type="hidden" id="id" name="id" value="${integralRule.id}"/>
        <input type="hidden" id="sacId" name="sacId" value="${integralRule.sacId}"/>
        <input type="hidden" id="sacCode" name="sacCode" value="${integralRule.sacCode}"/>
        <input type="hidden" id="scode" name="scode" value="${integralRule.scode}"/>
    </form>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js"  type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit'], function () {

        var form = layui.form;
        //监听提交
        $("#myForm").Validform({
            btnSubmit:"#myFormSub",  //根据id触发
            tiptype:3,                  //第三种方式
            showAllError:true,
            beforeSubmit:function() { //验证过后执行save方法
                if (isEmpty($("#fbaseIntegral").val())) {
                    alertDel("<spring:message code='ac.integralrule.the.integral.value.or.ratio.cannot.be.empty' />！");
                    return false;
                }
                var igiveMethod = $("#igiveMethod").val();
                if (igiveMethod == 10) {
                    if (isEmpty($("#fminValue").val())) {
                        alertDel("<spring:message code='ac.integralrule.the.gift.lower.limit.cannot.be.empty' />！");
                        return false;
                    }
                    if (isEmpty($("#fmaxValue").val())) {
                        alertDel("<spring:message code='ac.integralrule.gift.cap.cannot.be.empty' />！");
                        return false;
                    }
                }
                var loadIndex = loading();
                $('#myForm').ajaxSubmit({
                    type: "post",
                    dataType: "json",
                    async: true,
                    error: function () {
                        alertDelAndReload('<spring:message code="main.error.try.again"/>');
                    },
                    success: function (msg) {
                        //成功返回
                        closeLayer(loadIndex);//关闭加载框
                        if (msg.success) {
                            alertSuccess('<spring:message code="main.success"/>', {
                                'index':index
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

        //赠送方式
        form.on('select(igiveMethod)', function(data){
            if (data.value == 10) {//比例
                $("#fminValue").removeAttr("disabled");
                $("#fmaxValue").removeAttr("disabled");
                $("#fmaxValue").val("");
                $("#fminValue").val("");
            } else {
                $("#fminValue").attr("disabled", "disabled");
                $("#fmaxValue").attr("disabled", "disabled");
            }
        });
    });
    $(function () {
        //返回
        $("#cancelBtn").click(function () {
            parent.layer.close(index);
            return false;
        });
    });

</script>
</body>
</html>