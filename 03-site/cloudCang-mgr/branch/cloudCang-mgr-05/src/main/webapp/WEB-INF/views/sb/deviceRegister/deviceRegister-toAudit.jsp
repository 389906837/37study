<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>审核</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/device/register/audit" method="post" id="myForm">

        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='sb.please.select.the.audit.status' /></label>
                <div class="layui-input-inline">
                    <%--<cang:select type="list" name="istatus" id="istatus" entire="true" value="${deviceRegisterDomain.istatus}"  list="{10:springMessageCode=main.audited,20:springMessageCode=main.approval,30:springMessageCode=main.rejection}"/>--%>
                    <cang:select type="list" name="istatus" exp="springMessageCode=sb.please.select.the.audit.status" layFilter="istatus"
                                 id="istatus" entire="true" value="${deviceRegisterDomain.istatus}"  list='{20:springMessageCode=main.approval,30:springMessageCode=main.rejection}'/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label"><spring:message code='sb.audit.remarks' /></label>
            <div class="layui-input-block">
                <textarea id="sremark" name="sremark" class="layui-textarea layui-form-textarea80p"
                          placeholder='<spring:message code="sb.required.reviewing.rejection" />'>${deviceRegisterDomain.sremark}</textarea>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <div class="layui-input-inline">
                    <input type="hidden"  name="id" id="id" value="${deviceRegisterDomain.id}" class="layui-input" />
                    <input type="hidden"  name="sdeviceId" id="sdeviceId" value="${deviceRegisterDomain.sdeviceId}" class="layui-input" />
                </div>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
                <button class="layui-btn" id="myFormSub" ><spring:message code="main.save" /></button>
            </div>
        </div>
    </form>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js"  type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var form;
    layui.use(['form', 'layedit', 'laydate'], function () {
        form = layui.form;

        $("#myForm").Validform({
            btnSubmit:"#myFormSub",  //根据id触发
            tiptype:3,                  //第三种方式
            showAllError:true,
            beforeSubmit:function(){ //验证过后执行save方法
                var iauditStatus = $("#istatus option:selected").val();
                var sauditRefuseReason = $("#sremark").val();
                if (30 == iauditStatus && isEmpty(sauditRefuseReason)) {
                    alertDel('<spring:message code="sb.audit.rejection.reason.cannot.be.empty" />');
                    return false;
                }
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


        //监听审核select选择
        form.on('select(istatus)', function (data) {
            if (!isEmpty(data.value)) {
                $("#istatus").parent().find("span").hide();
            } else {
                $("#istatus").parent().find("span").show();
                if (!$("#istatus").parent().find("span").hasClass("Validform_wrong")) {
                    $("#istatus").parent().find("span").html($("#istatus").attr("nullmsg"));
                    $("#istatus").parent().find("span").removeClass("Validform_right");
                    $("#istatus").parent().find("span").addClass("Validform_wrong");
                }
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


