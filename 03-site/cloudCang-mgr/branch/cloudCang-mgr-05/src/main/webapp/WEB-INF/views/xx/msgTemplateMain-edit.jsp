<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title>消息分类编辑</title>
    <link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
    <link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
    <div class="ibox-content">
        <form class="layui-form" action="${ctx}/msgTemplateMain/save" method="post" id="myForm">
            <input type="hidden" id="smerchantId" name="smerchantId" value="${msgTemplateMain.smerchantId}"/><%--商户ID--%>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="main.merchant.name" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="smerchantName" placeholder='<spring:message code="main.required" />...' datatype="*" nullmsg='<spring:message code="main.merchant.name.select" />'
                               id="smerchantName" value="${merchantInfo.sname}" class="layui-input"/>
                    </div>
                    <button class="layui-btn" id="btn1" style="display: inline-block;float: left;"
                            type="button" data-type="sel"><spring:message code='xx.select' />
                    </button>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="main.merchant.number" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="smerchantCode" id="smerchantCode" value="${msgTemplateMain.smerchantCode}" style="width: 210px"
                               class="layui-input" disabled/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code='main.code' /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="scode" id="scode" value="${msgTemplateMain.scode}" class="layui-input" disabled/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code='main.name' /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="smsgName" id="smsgName" value="${msgTemplateMain.smsgName}" datatype="*" nullmsg="<spring:message code='xx.can.enter.any.character' />" placeholder='<spring:message code="wz.please.enter.a.name" />...' class="layui-input" />
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code='xx.function.number' /></label>
                    <div class="layui-input-inline">
                        <cang:select type="dic" groupNo="SP000150" name="sfunctionCode" exp="springMessageCode=xx.please.select.the.function.number"
                                     laySearch="1"     id="sfunctionCode" entire="true" value="${msgTemplateMain.sfunctionCode}" />
                    </div>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='xx.describe' /></label>
                <div class="layui-input-inline">
                    <textarea class="layui-textarea" name="sremark" id="sremark" style="width: 400px" placeholder="<spring:message code='xx.please.enter.a.description' />...">${msgTemplateMain.sremark}</textarea>
                </div>
            </div>
            <div class="layui-form-item fixed-bottom" style="z-index: 55">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
                    <button class="layui-btn" id="myFormSub"><spring:message code="main.save" /></button>
                </div>
            </div>
            <input type="hidden" id="id" name="id" value="${msgTemplateMain.id}" />
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

        //选择商户
        var $ = layui.$, active = {
            sel: function(){
                showLayerMax("<spring:message code='wz.select.a.merchant' />", ctx+"/merchantInfo/radioSelectList");
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

    function selectMerchant(merchantId,merchantCode,merchantName) {
        $("#smerchantId").val(merchantId);//商户ID
        $("#smerchantCode").val(merchantCode);//商户编号
        $("#smerchantName").val(merchantName);//商户名称
    }
</script>
</body>
</html>

