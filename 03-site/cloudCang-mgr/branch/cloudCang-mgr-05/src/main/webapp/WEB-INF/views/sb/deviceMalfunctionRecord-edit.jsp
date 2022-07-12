<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title>设备故障编辑</title>
    <link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
    <link href="${staticSource}/resources/css/override.css?1" rel="stylesheet">

</head>

<body>
    <div class="ibox-content">
        <form class="layui-form" action="${ctx}/deviceMal/save" method="post" id="myForm">
            <input type="hidden" id="sdeviceId" name="sdeviceId" value="${deviceMalfunctionRecord.sdeviceId}"/><%--<spring:message code='main.device' />ID--%>
            <input type="hidden" id="sdeviceCode" name="sdeviceCode" value="${deviceMalfunctionRecord.sdeviceCode}"/><%--<spring:message code='main.device.id' />--%>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="main.device.name" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="sname" id="sname" <c:if test="${deviceMalfunctionRecord.itype ne 30}"> disabled="disabled"</c:if> value="${deviceInfo.sname}" class="layui-input"/>
                        <input type="hidden" name="id" id="ids" value="${deviceInfo.id}" class="layui-input" />
                    </div>
                    <button class="layui-btn" id="btn1" style="display: inline-block;float: left;"
                            type="button" <c:if test="${deviceMalfunctionRecord.itype ne 30}"> <%--disabled="disabled"--%></c:if> data-type="sel"><spring:message code='main.choice' />
                    </button>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="main.device.id" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="sdeviceCodes" id="sdeviceCodes" value="${deviceMalfunctionRecord.sdeviceCode}" class="layui-input" disabled/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code='sb.fault.description' /></label>
                    <div class="layui-input-inline">
                        <textarea class="layui-textarea" id="smalfunctionDesc" name="smalfunctionDesc" style="width: 415px">${deviceMalfunctionRecord.smalfunctionDesc}</textarea>
                    </div>
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
            <input type="hidden" id="id" name="id" value="${deviceMalfunctionRecord.id}" />
        </form>
    </div>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js"  type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate','element'], function(){

        var form = layui.form;

        //选择商户
        var $ = layui.$, active = {
            sel: function(){
                showLayerMax('<spring:message code="activity.select.equipment" />', ctx+"/common/selectDeviceOne?selectType=10");
            }
        };

        $('.layui-form .layui-btn').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });


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

    function selectDeviceOne(sdeviceId,sdeviceCode,sname) {
        $("#sdeviceId").val(sdeviceId);//<spring:message code='main.device' />ID
        $("#sdeviceCodes").val(sdeviceCode);//设备编号
        $("#sname").val(sname);//设备名称
    }
</script>
</body>
</html>

