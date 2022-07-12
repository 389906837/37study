<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>增加设备管理信息模板</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
</head>
<body>
<div class="ibox-content">
    <div class="layui-tab-content">
        <div class="layui-tab-item layui-show">
            <form class="layui-form" action="${ctx}/deviceManageTemplate/add" method="post" id="myForm">
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmanage.template.name" /></label>
                        <div class="layui-input-inline">
                            <input type="text" name="sname" id="sname" datatype="*" nullmsg='<spring:message code="tpmanage.please.enter.a.template.name" />'
                                   value="" class="layui-input" placeholder='<spring:message code="main.required" />...'/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmanage.template.number" /></label>
                        <div class="layui-input-inline">
                            <input type="text" name="scode" id="scode" value=""
                                   class="layui-input" disabled/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmanage.manufacturer" /></label>
                        <div class="layui-input-inline">
                            <input type="text" name="sareaPrincipal" id="sareaPrincipal" value="" class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmanage.contact.information" /></label>
                        <div class="layui-input-inline">
                            <input type="text" name="sareaPrincipalContact" id="sareaPrincipalContact" value=""
                                   class="layui-input"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmanage.equipment.manager" /></label>
                        <div class="layui-input-inline">
                            <input type="text" name="sdevicePrincipal" id="sdevicePrincipal" value=""
                                   class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmanage.contact.information" /></label>
                        <div class="layui-input-inline">
                            <input type="text" name="sdevicePrincipalContact" id="sdevicePrincipalContact" value=""
                                   class="layui-input"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmanage.replenisher" /></label>
                        <div class="layui-input-inline">
                            <input type="text" name="sreplenishment" id="sreplenishment" value=""
                                   class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmanage.contact.information" /></label>
                        <div class="layui-input-inline">
                            <input type="text" name="sreplenishmentContact" id="sreplenishmentContact" value=""
                                   class="layui-input"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmanage.equipment.maintenance.personnel" /></label>
                        <div class="layui-input-inline">
                            <input type="text" name="smaintain" id="smaintain" value="" class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpmanage.contact.information" /></label>
                        <div class="layui-input-inline">
                            <input type="text" name="smaintainContact" id="smaintainContact" value=""
                                   class="layui-input"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><spring:message code="main.remarks" /></label>
                    <div class="layui-input-block">
                        <textarea id="sremark" name="sremark" class="layui-textarea layui-form-textarea80p"
                                  placeholder='<spring:message code="main.remarks" />（<spring:message code="main.optional" />）'></textarea>
                    </div>
                </div>
                <div class="layui-form-item fixed-bottom">
                    <div class="layui-input-block">
                        <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
                        <button class="layui-btn" id="myFormSub"><spring:message code="main.save" /></button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js" type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var form;
    layui.use(['form', 'layedit', 'element'], function () {

        $('.layui-form .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

        form = layui.form;

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

        $('.chosen-select').chosen();
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

