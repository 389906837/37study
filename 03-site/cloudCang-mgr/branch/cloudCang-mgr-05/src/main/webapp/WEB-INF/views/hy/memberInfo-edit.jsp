<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>会员管理</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?20186" rel="stylesheet">
</head>
<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/memberInfo/save" method="post" id="myForm" enctype="multipart/form-data">
        <div class="wfsplitt">
            <spring:message code='hy.basic.information' />
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="main.member.number" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="scode" id="scode" value="${memberInfo.scode}" class="layui-input"disabled/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="main.member.username" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="smemberName" id="smemberName" value="${memberInfo.smemberName}" class="layui-input" disabled/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='hy.member.gender' /></label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="isex" id="isex" entire="true" value="${memberInfo.isex}" list='{1:springMessageCode=main.male,2:springMessageCode=main.female}' />
                    <%--<select id="isex" name="isex" disabled>--%>
                        <%--<option value="${memberInfo.isex}"<c:if test="${1 == memberInfo.isex}">selected</c:if>><spring:message code="main.male" /></option>--%>
                        <%--<option value="${memberInfo.isex}"<c:if test="${2 == memberInfo.isex}">selected</c:if>><spring:message code="main.female" /></option>--%>
                    <%--</select>--%>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='hy.member.birthday' /></label>
                <div class="layui-input-inline">
                    <input type="text" class="layui-input" id="dbirthdate" name="dbirthdate" placeholder="<fmt:formatDate value="${memberInfo.dbirthdate}" pattern="yyyy-MM-dd"/>">
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="main.mobile" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="smobile" id="smobile" value="${memberInfo.smobile}" datatype="m" nullmsg="<spring:message code='hy.must.enter.11.mobile.phone.number' />" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="main.mailbox" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="semail" id="semail" value="${memberInfo.semail}" datatype="e" nullmsg="<spring:message code='hy.please.enter.the.correct.format.of.the.mailbox.number' />" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='hy.type.of.certificate' /></label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="icardType" id="icardType" entire="true" value="${memberInfo.icardType}" list="{10:springMessageCode=hy.id.card,20:springMessageCode=sh.business.license}"/>
                        <%--<select id="icardType" name="icardType" disabled>--%>
                            <%--<option value="${memberInfo.icardType}"<c:if test="${10 == memberInfo.icardType}">selected</c:if>><spring:message code='hy.id.card' /></option>--%>
                            <%--<option value="${memberInfo.icardType}"<c:if test="${20 == memberInfo.icardType}">selected</c:if>><spring:message code='sh.business.license' /></option>--%>
                        <%--</select>--%>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='hy.id.number' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sidCard" id="sidCard" value="${memberInfo.sidCard}" class="layui-input">
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='hy.actual.name' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="srealName" id="srealName" value="${memberInfo.srealName}" class="layui-input" >
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='hy.membership.level' /></label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="imemberLevel" id="imemberLevel" entire="true" value="${memberInfo.imemberLevel}" list="{10:springMessageCode=hy.public.member,20:springMessageCode=hy.gold.member,30:springMessageCode=hy.platinum.member,40:springMessageCode=hy.diamond.membership}"/>
                </div>
            </div>
            <%--<div class="layui-col-md6">--%>
                <%--<label class="layui-form-label"><spring:message code='sb.registration.time' /></label>--%>
                <%--<div class="layui-input-inline">--%>
                    <%--<input type="text" class="layui-input" id="tregisterTime" name="tregisterTime" placeholder="<fmt:formatDate value="${memberInfo.tregisterTime}" pattern="yyyy-MM-dd"/>" disabled>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
        <%--<div class="layui-form-item">--%>
            <%--<div class="layui-col-md6">--%>
                <%--<label class="layui-form-label"><spring:message code='hy.login.password' /></label>--%>
                <%--<div class="layui-input-inline">--%>
                    <%--<input type="text" name="sloginPwd" id="sloginPwd" value="${memberInfo.sloginPwd}" class="layui-input" lay-verify="required">--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<div class="layui-col-md6">--%>
                <%--<label class="layui-form-label"><spring:message code='hy.transaction.password' /></label>--%>
                <%--<div class="layui-input-inline">--%>
                    <%--<input type="text" name="stradePwd" id="stradePwd" value="${memberInfo.stradePwd}" class="layui-input" lay-verify="required">--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
        <%--<div class="layui-form-item">--%>
            <%--<div class="layui-col-md6">--%>
                <%--<label class="layui-form-label"><spring:message code='hy.real-name.authentication.time' /></label>--%>
                <%--<div class="layui-input-inline" >--%>
                    <%--<input type="text" name="trealNameTime" id="trealNameTime" placeholder="<fmt:formatDate value="${memberInfo.trealNameTime}" pattern="yyyy-MM-dd"/>"--%>
                           <%--class="layui-input" disabled/>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--&lt;%&ndash;<div class="layui-col-md6">&ndash;%&gt;--%>
                <%--&lt;%&ndash;<label class="layui-form-label"><spring:message code='sp.commodityBatch.merchant.id' /></label>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<div class="layui-input-inline">&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<input type="text" lay-verify="required" name="smerchantId"  id="smerchantId" class="layui-input"/>&ndash;%&gt;--%>
                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
        <%--</div>--%>
        <%--<c:if test="${ empty  memberInfo.id || 20 == memberInfo.itype}">--%>
            <%--<div name="typediv1" id="typediv1">--%>
                <%--<div class="wfsplitt">--%>
                    <%--<spring:message code='hy.status.information' />--%>
                <%--</div>--%>
                <%--<div class="layui-form-item">--%>
                    <%--<div class="layui-col-md6">--%>
                        <%--<label class="layui-form-label"><spring:message code="hy.member.status" /></label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<cang:select disabled="true" type="list" name="istatus" id="istatus" entire="true" value="${memberInfo.istatus}" list="{1:springMessageCode=sm.normal,2:springMessageCode=hy.logout.disabled,3:springMessageCode=hy.system.blacklist,4:springMessageCode=ac.freeze}"/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="layui-col-md6">--%>
                        <%--<label class="layui-form-label"><spring:message code='hy.type.of.membership' /></label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<cang:select disabled="true" type="list" name="imemberType" id="imemberType" entire="true" value="${memberInfo.imemberType}" list="{10:springMessageCode=sl.shopping.member,20:springMessageCode=sl.replenishment.member}"/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="layui-form-item">--%>
                    <%--<div class="layui-col-md6">--%>
                        <%--<label class="layui-form-label"><spring:message code='hy.membership.level' /></label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<cang:select type="list" name="imemberLevel" id="imemberLevel" entire="true" value="${memberInfo.imemberLevel}" list="{10:springMessageCode=hy.public.member,20:springMessageCode=hy.gold.member,30:springMessageCode=hy.platinum.member,40:springMessageCode=hy.diamond.membership}"/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="layui-col-md6">--%>
                        <%--<label class="layui-form-label"><spring:message code='hy.whether.the.first.order' /></label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<cang:select disabled="true" type="list" name="iisOrder" id="iisOrder" entire="true" value="${memberInfo.iisOrder}" list='{0:springMessageCode=main.no,1:springMessageCode=main.yes}'/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="layui-form-item">--%>
                    <%--<div class="layui-col-md6">--%>
                        <%--<label class="layui-form-label"><spring:message code='hy.first.platform' /></label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<cang:select disabled="true" type="list" name="ifirstOrderPlatform" id="ifirstOrderPlatform" entire="true" value="${memberInfo.ifirstOrderPlatform}" list="{10:springMessageCode=main.wechat,20:springMessageCode=main.alipay,30:APP}"/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="layui-col-md6">--%>
                        <%--<label class="layui-form-label"><spring:message code='ac.first.time' /></label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<input type="text" name="tfirstOrderTime" id="tfirstOrderTime" placeholder="<fmt:formatDate value="${memberInfo.tfirstOrderTime}" pattern="yyyy-MM-dd"/>"--%>
                                   <%--class="layui-input" disabled="true"/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="layui-form-item">--%>
                    <%--<div class="layui-col-md6">--%>
                        <%--<label class="layui-form-label"><spring:message code='hy.is.the.recommendation.reward.enabled' /></label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<cang:select disabled="true" type="list" name="iisEnableRecoaward" id="iisEnableRecoaward" entire="true" value="${memberInfo.iisEnableRecoaward}" list='{0:springMessageCode=main.no,1:springMessageCode=main.yes}'/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="layui-col-md6">--%>
                        <%--<label class="layui-form-label"><spring:message code='hy.real.name.certification' /></label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<cang:select disabled="true" type="list" name="iisVerified" id="iisVerified" entire="true" value="${memberInfo.iisVerified}" list='{0:springMessageCode=main.no,1:springMessageCode=main.yes}'/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="layui-form-item">--%>
                    <%--<div class="layui-col-md6">--%>
                        <%--<label class="layui-form-label"><spring:message code='hy.is.the.first.time.tied.to.the.card' /></label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<cang:select disabled="true" type="list" name="iisFirstTieCard" id="iisFirstTieCard" entire="true" value="${memberInfo.iisFirstTieCard}" list='{0:springMessageCode=main.no,1:springMessageCode=main.yes}'/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="layui-col-md6">--%>
                        <%--<label class="layui-form-label"><spring:message code='hy.member.registration.ip' /></label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<input disabled="true" type="text" name="smemberRegip" id="smemberRegip" value="${memberInfo.smemberRegip}" class="layui-input">--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="layui-form-item">--%>
                    <%--<div class="layui-col-md6">--%>
                        <%--<label class="layui-form-label"><spring:message code='hy.recent.shopping.platform' /></label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<cang:select disabled="true" type="list" name="ilastShopPlatform" id="ilastShopPlatform" entire="true" value="${memberInfo.ilastShopPlatform}" list="{10:springMessageCode=main.wechat,20:springMessageCode=main.alipay,30:APP}"/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="layui-col-md6">--%>
                        <%--<label class="layui-form-label"><spring:message code='hy.recent.shopping.time' /></label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<input disabled="true" type="text" name="tlastShopTime"  id="tlastShopTime" placeholder="<fmt:formatDate value="${memberInfo.tlastShopTime}" pattern="yyyy-MM-dd"/>"--%>
                                   <%--class="layui-input"/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="layui-form-item">--%>
                    <%--<div class="layui-col-md6">--%>
                        <%--<label class="layui-form-label"><spring:message code="main.alipay.free" /></label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<cang:select disabled="true" type="list" name="iaipayOpen" id="iaipayOpen" entire="true" value="${memberInfo.iaipayOpen}" list='{0:springMessageCode=main.no,1:springMessageCode=main.yes}'/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="layui-col-md6">--%>
                        <%--<label class="layui-form-label"><spring:message code='hy.is.wechat.confidentiality.open' /></label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<cang:select disabled="true" type="list" name="iwechatOpen" id="iwechatOpen" entire="true" value="${memberInfo.iwechatOpen}" list='{0:springMessageCode=main.no,1:springMessageCode=main.yes}'/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="layui-form-item">--%>
                    <%--<div class="layui-col-md6">--%>
                        <%--<label class="layui-form-label"><spring:message code="sys.is.replenisher" /></label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<cang:select disabled="true" type="list" name="iisReplenishment" id="iisReplenishment" entire="true" value="${memberInfo.iisReplenishment}" list='{0:springMessageCode=main.no,1:springMessageCode=main.yes}'/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
            <%--</div>--%>
        <%--</c:if>--%>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
                <button class="layui-btn" id="myFormSub"><spring:message code="main.save" /></button>
                <%--<button class="layui-btn" lay-submit="" lay-filter="myFormSub"><spring:message code="main.save" /></button>--%>
            </div>
        </div>
        <%--<input type="hidden" id="smerchantId" name="smerchantId" value="${memberInfo.smerchantId}">--%>
        <input type="hidden" id="merchantId" name="id" value="${memberInfo.id}"/>
    </form>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js"  type="text/javascript"></script>
<script type="text/javascript">

     layui.use('laydate', function () {
     var laydate = layui.laydate;
     //执行一个laydate实例
     laydate.render({
     elem: '#dbirthdate',//指定元素
     type: 'date'
     });
});

    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate','upload'], function () {
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
        $('.layui-form .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
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

