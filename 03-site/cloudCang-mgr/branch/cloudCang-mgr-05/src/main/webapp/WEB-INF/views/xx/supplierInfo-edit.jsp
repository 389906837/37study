<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title>短信供应商编辑</title>
    <link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
    <link href="${staticSource}/resources/css/override.css?1" rel="stylesheet">

</head>

<body>
    <div class="ibox-content">
        <form class="layui-form" action="${ctx}/supplierInfo/save" method="post" id="myForm">
            <input type="hidden" id="smerchantId" name="smerchantId" value="${supplierInfo.smerchantId}"/><%--商户ID--%>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="main.merchant.name" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="smerchantName" placeholder='<spring:message code="main.required" />...' datatype="*" nullmsg='<spring:message code="main.merchant.name.select" />'
                               id="smerchantName" value="${merchantInfo.sname}" class="layui-input"/>
                    </div>
                    <button class="layui-btn" id="btn1" style="display: inline-block;float: left;"
                            type="button" data-type="sel"><spring:message code='vrsku.select' />
                    </button>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label" style="width: 100px"><spring:message code="main.merchant.number" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="smerchantCode" id="smerchantCode" value="${merchantInfo.scode}" style="width: 206px"
                               class="layui-input" disabled/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code='main.code' /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="scode" id="scode" value="${supplierInfo.scode}" class="layui-input" disabled/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code='main.name' /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="sname" id="sname" value="${supplierInfo.sname}" datatype="*" nullmsg="<spring:message code='wz.please.enter.the.supplier.name' />" class="layui-input" />
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='xx.supplier.type' /></label>
                <div class="layui-input-inline">
                    <cang:select type="list" id="itype" name="itype" value="${supplierInfo.itype}" list='{1:springMessageCode=xx.sms,2:<spring:message code="main.mailbox}' entire="true" exp="springMessageCode=xx.please.select.a.supplier.type" />
                </div>
            </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code='xx.channel.role' /></label>
                    <div class="layui-input-inline">
                        <cang:select type="list" id="iintention" name="iintention" value="${supplierInfo.iintention}" list="{1:springMessageCode=xx.marketing,2:springMessageCode=xx.non.marketing}" entire="true" exp="springMessageCode=xx.please.select.the.channel.role" />
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='xx.account.name' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="saccName" id="saccName" value="${supplierInfo.saccName}" datatype="*6-16" errormsg="<spring:message code='xx.account.name.must.consist.of.characters' />" class="layui-input" />
                </div>
            </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code='main.password' /></label>
                    <div class="layui-input-inline">
                        <input type="password" name="saccPassword" id="saccPassword" value="${supplierInfo.saccPassword}" datatype="/^[A-Za-z0-9]{6,16}$/"  errormsg="<spring:message code='xx.password.must.consist.of.letters.and.numbers' />" class="layui-input" />
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code='xx.extended.information' /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="sextInfo" id="sextInfo" value='${supplierInfo.sextInfo}' datatype="*" nullmsg="<spring:message code='index.order.statistics' />xx.please.enter.extension.information" class="layui-input" />
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label" ><spring:message code='xx.describe' /></label>
                <div class="layui-input-inline">
                    <textarea class="layui-textarea" id="sdesc" name="sdesc" style="width: 600px">${supplierInfo.sdesc}</textarea>
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
            <input type="hidden" id="id" name="id" value="${supplierInfo.id}" />
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

