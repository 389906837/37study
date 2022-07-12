<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>编辑设备网络摄像头信息</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/cameraConfig/edit"
          method="post" id="myForm" enctype="multipart/form-data">
        <input type="hidden" id="sdeviceId" name="sdeviceId" value="${sdeviceId}" /><%--设备ID--%>
        <input type="hidden" id="id" name="id" value="${cameraConfig.id}" /><%--设备ID--%>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">IP</label>
                <div class="layui-input-inline">
                    <input type="text" name="sip" datatype="*" nullmsg="<spring:message code="sb.please.enter" />IP" id="sip" value="${cameraConfig.sip}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sb.the.port.number" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sport" datatype="*" nullmsg='<spring:message code="sb.please.enter" /><spring:message code="sb.the.port.number" />' id="sport" value="${cameraConfig.sport}"
                           class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item m-t">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sb.serial.number" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sserialNumber" datatype="*" nullmsg='<spring:message code="sb.please.enter" /><spring:message code="sb.serial.number" />' id="sserialNumber" value="${cameraConfig.sserialNumber}"
                           class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="main.remarks" /></label>
                <div class="layui-block">
                    <textarea style="width: 470px" id="sremark" name="sremark"
                              class="layui-textarea layui-form-textarea80p" placeholder='<spring:message code="sb.not.required" />'>${cameraConfig.sremark}</textarea>
                </div>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
                <button class="layui-btn"  id="myFormSub"><spring:message code="main.save" /></button>
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
    layui.use(['form', 'layedit', 'laydate', 'upload'], function () {
        var laydate = layui.laydate;
        laydate.render({
            elem: '#dproduceDate',//指定元素
            type: 'datetime',
            done: function(value){ //监听日期被切换
                if(!isEmpty(value)) {
                    $("#dproduceDate").removeClass("Validform_error");
                    $("#dproduceDate").parent().find("span").hide();
                } else {
                    $("#dproduceDate").addClass("Validform_error");
                    $("#dproduceDate").parent().find("span").html($("#dproduceDate").attr("nullmsg"));
                    $("#dproduceDate").parent().find("span").removeClass("Validform_right");
                    $("#dproduceDate").parent().find("span").addClass("Validform_wrong");
                    $("#dproduceDate").parent().find("span").show();
                }
            }
        });

        form = layui.form;

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

    $(function () {
        $("#cancelBtn").click(function () {
            parent.layer.close(index);
            return false;
        });
    });



</script>
</body>
</html>

