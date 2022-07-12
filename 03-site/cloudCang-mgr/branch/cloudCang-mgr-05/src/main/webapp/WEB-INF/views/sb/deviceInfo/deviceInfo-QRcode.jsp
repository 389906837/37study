<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备二维码展示</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="#" method="post" id="myForm">
        <div class="layui-form-item">
            <div class="layui-col-md6">
                    <spring:message code='sb.show.pictures'/>：
                    <div class="layui-upload-list" id="slogoYl">
                        <c:if test="${!empty  deviceInfoVo.sqrUrl}">
                            <p style="text-align: center;">
                                <img id="qrCodeImg" src="${dynamicSource}${deviceInfoVo.sqrUrl}" >
                            </p>
                        </c:if>
                    </div>
            </div>

        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <div class="layui-input-inline">
                    <input type="hidden"  name="id" id="id" value="${deviceInfoVo.id}" class="layui-input" />
                </div>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <c:if test="${nowMerchantCode eq deviceInfoVo.smerchantCode}">
                    <shiro:hasPermission name="DEVICE_INFO_RESETQRCODE">
                        <button class="layui-btn" id="resetQrCode" ><spring:message code='sb.reset.twodimensional.code'/></button>
                    </shiro:hasPermission>
                </c:if>
            </div>
        </div>
    </form>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var form;
    layui.use(['form', 'layedit', 'laydate'], function () {
        form = layui.form;
        //监听提交
        form.on('submit(myFormSub)', function () {
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
        });

    });

    $(function () {
        $("#resetQrCode").click(function () {
            var deviceId = $("#id").val();
            var prePath = "${dynamicSource}";

            layer.confirm('<spring:message code='sb.make.sure.to.reset.the.qr.code'/>？', {
                btn: ['<spring:message code="main.cancel" />','<spring:message code="main.determine" />'], //按钮
                cancel:function(index) {
                    closeLayer(index);
                }
            }, function(index){
                layer.close(index);
            }, function(){
//                operDealwith(url, param);
                $.ajax({
                    type:'post',
                    url:'${ctx}/device/info/reSetQRcode',
                    data:{id:deviceId},
                    dataType:'json',
                    success:function(resp){
                        var newQrCodeUrl = prePath + resp.data.sqrUrl;
                        $("#qrCodeImg").attr('src',newQrCodeUrl);
                    }
                });

            });



            return false;
        });
    });


</script>
</body>
</html>


