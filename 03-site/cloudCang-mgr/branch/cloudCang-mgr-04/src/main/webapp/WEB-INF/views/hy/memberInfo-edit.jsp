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
            基础信息
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"> 会员编号</label>
                <div class="layui-input-inline">
                    <input type="text" name="scode" id="scode" value="${memberInfo.scode}" class="layui-input"disabled/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">会员用户名</label>
                <div class="layui-input-inline">
                    <input type="text" name="smemberName" id="smemberName" value="${memberInfo.smemberName}" class="layui-input" disabled/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">会员性別</label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="isex" id="isex" entire="true" value="${memberInfo.isex}" list="{1:男,2:女}" />
                    <%--<select id="isex" name="isex" disabled>--%>
                        <%--<option value="${memberInfo.isex}"<c:if test="${1 == memberInfo.isex}">selected</c:if>>男</option>--%>
                        <%--<option value="${memberInfo.isex}"<c:if test="${2 == memberInfo.isex}">selected</c:if>>女</option>--%>
                    <%--</select>--%>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">会员生日</label>
                <div class="layui-input-inline">
                    <input type="text" class="layui-input" id="dbirthdate" name="dbirthdate" placeholder=" <fmt:formatDate value="${memberInfo.dbirthdate}" pattern="yyyy-MM-dd"/>">
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"> 手机号码</label>
                <div class="layui-input-inline">
                    <input type="text" name="smobile" id="smobile" value="${memberInfo.smobile}" datatype="m" nullmsg="必须输入11位手机号" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">邮箱</label>
                <div class="layui-input-inline">
                    <input type="text" name="semail" id="semail" value="${memberInfo.semail}" datatype="e" nullmsg="请输入格式正确的邮箱号" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">证件类型</label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="icardType" id="icardType" entire="true" value="${memberInfo.icardType}" list="{10:身份证,20:营业执照}"/>
                        <%--<select id="icardType" name="icardType" disabled>--%>
                            <%--<option value="${memberInfo.icardType}"<c:if test="${10 == memberInfo.icardType}">selected</c:if>>身份证</option>--%>
                            <%--<option value="${memberInfo.icardType}"<c:if test="${20 == memberInfo.icardType}">selected</c:if>>营业执照</option>--%>
                        <%--</select>--%>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">证件号码</label>
                <div class="layui-input-inline">
                    <input type="text" name="sidCard" id="sidCard" value="${memberInfo.sidCard}" class="layui-input">
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">真实姓名</label>
                <div class="layui-input-inline">
                    <input type="text" name="srealName" id="srealName" value="${memberInfo.srealName}" class="layui-input" >
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">会员等级</label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="imemberLevel" id="imemberLevel" entire="true" value="${memberInfo.imemberLevel}" list="{10:大众会员,20:黄金会员,30:铂金会员,40:钻石会员}"/>
                </div>
            </div>
            <%--<div class="layui-col-md6">--%>
                <%--<label class="layui-form-label">注册时间</label>--%>
                <%--<div class="layui-input-inline">--%>
                    <%--<input type="text" class="layui-input" id="tregisterTime" name="tregisterTime" placeholder=" <fmt:formatDate value="${memberInfo.tregisterTime}" pattern="yyyy-MM-dd"/>" disabled>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
        <%--<div class="layui-form-item">--%>
            <%--<div class="layui-col-md6">--%>
                <%--<label class="layui-form-label">登录密码</label>--%>
                <%--<div class="layui-input-inline">--%>
                    <%--<input type="text" name="sloginPwd" id="sloginPwd" value="${memberInfo.sloginPwd}" class="layui-input" lay-verify="required">--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<div class="layui-col-md6">--%>
                <%--<label class="layui-form-label">交易密码</label>--%>
                <%--<div class="layui-input-inline">--%>
                    <%--<input type="text" name="stradePwd" id="stradePwd" value="${memberInfo.stradePwd}" class="layui-input" lay-verify="required">--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
        <%--<div class="layui-form-item">--%>
            <%--<div class="layui-col-md6">--%>
                <%--<label class="layui-form-label">实名认证时间</label>--%>
                <%--<div class="layui-input-inline" >--%>
                    <%--<input type="text" name="trealNameTime" id="trealNameTime" placeholder=" <fmt:formatDate value="${memberInfo.trealNameTime}" pattern="yyyy-MM-dd"/>"--%>
                           <%--class="layui-input" disabled/>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--&lt;%&ndash;<div class="layui-col-md6">&ndash;%&gt;--%>
                <%--&lt;%&ndash;<label class="layui-form-label">商户ID</label>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<div class="layui-input-inline">&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<input type="text" lay-verify="required" name="smerchantId"  id="smerchantId" class="layui-input"/>&ndash;%&gt;--%>
                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
        <%--</div>--%>
        <%--<c:if test="${ empty  memberInfo.id || 20 == memberInfo.itype}">--%>
            <%--<div name="typediv1" id="typediv1">--%>
                <%--<div class="wfsplitt">--%>
                    <%--状态信息--%>
                <%--</div>--%>
                <%--<div class="layui-form-item">--%>
                    <%--<div class="layui-col-md6">--%>
                        <%--<label class="layui-form-label">会员状态</label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<cang:select disabled="true" type="list" name="istatus" id="istatus" entire="true" value="${memberInfo.istatus}" list="{1:正常,2:注销停用,3:系统黑名单,4:冻结}"/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="layui-col-md6">--%>
                        <%--<label class="layui-form-label">会员类型</label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<cang:select disabled="true" type="list" name="imemberType" id="imemberType" entire="true" value="${memberInfo.imemberType}" list="{10:购物会员,20:补货会员}"/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="layui-form-item">--%>
                    <%--<div class="layui-col-md6">--%>
                        <%--<label class="layui-form-label">会员等级</label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<cang:select type="list" name="imemberLevel" id="imemberLevel" entire="true" value="${memberInfo.imemberLevel}" list="{10:大众会员,20:黄金会员,30:铂金会员,40:钻石会员}"/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="layui-col-md6">--%>
                        <%--<label class="layui-form-label">是否首单</label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<cang:select disabled="true" type="list" name="iisOrder" id="iisOrder" entire="true" value="${memberInfo.iisOrder}" list="{0:否,1:是}"/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="layui-form-item">--%>
                    <%--<div class="layui-col-md6">--%>
                        <%--<label class="layui-form-label">首单平台</label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<cang:select disabled="true" type="list" name="ifirstOrderPlatform" id="ifirstOrderPlatform" entire="true" value="${memberInfo.ifirstOrderPlatform}" list="{10:微信,20:支付宝,30:APP}"/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="layui-col-md6">--%>
                        <%--<label class="layui-form-label">首单时间</label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<input type="text" name="tfirstOrderTime" id="tfirstOrderTime" placeholder=" <fmt:formatDate value="${memberInfo.tfirstOrderTime}" pattern="yyyy-MM-dd"/>"--%>
                                   <%--class="layui-input" disabled="true"/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="layui-form-item">--%>
                    <%--<div class="layui-col-md6">--%>
                        <%--<label class="layui-form-label">推荐奖励是否启用</label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<cang:select disabled="true" type="list" name="iisEnableRecoaward" id="iisEnableRecoaward" entire="true" value="${memberInfo.iisEnableRecoaward}" list="{0:否,1:是}"/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="layui-col-md6">--%>
                        <%--<label class="layui-form-label">是否实名认证</label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<cang:select disabled="true" type="list" name="iisVerified" id="iisVerified" entire="true" value="${memberInfo.iisVerified}" list="{0:否,1:是}"/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="layui-form-item">--%>
                    <%--<div class="layui-col-md6">--%>
                        <%--<label class="layui-form-label">是否首次绑卡</label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<cang:select disabled="true" type="list" name="iisFirstTieCard" id="iisFirstTieCard" entire="true" value="${memberInfo.iisFirstTieCard}" list="{0:否,1:是}"/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="layui-col-md6">--%>
                        <%--<label class="layui-form-label">会员注册IP</label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<input disabled="true" type="text" name="smemberRegip" id="smemberRegip" value="${memberInfo.smemberRegip}" class="layui-input">--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="layui-form-item">--%>
                    <%--<div class="layui-col-md6">--%>
                        <%--<label class="layui-form-label">最近购物平台</label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<cang:select disabled="true" type="list" name="ilastShopPlatform" id="ilastShopPlatform" entire="true" value="${memberInfo.ilastShopPlatform}" list="{10:微信,20:支付宝,30:APP}"/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="layui-col-md6">--%>
                        <%--<label class="layui-form-label">最近购物时间</label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<input disabled="true" type="text" name="tlastShopTime"  id="tlastShopTime" placeholder=" <fmt:formatDate value="${memberInfo.tlastShopTime}" pattern="yyyy-MM-dd"/>"--%>
                                   <%--class="layui-input"/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="layui-form-item">--%>
                    <%--<div class="layui-col-md6">--%>
                        <%--<label class="layui-form-label">支付宝免密是否开通</label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<cang:select disabled="true" type="list" name="iaipayOpen" id="iaipayOpen" entire="true" value="${memberInfo.iaipayOpen}" list="{0:否,1:是}"/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="layui-col-md6">--%>
                        <%--<label class="layui-form-label">微信免密是否开通</label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<cang:select disabled="true" type="list" name="iwechatOpen" id="iwechatOpen" entire="true" value="${memberInfo.iwechatOpen}" list="{0:否,1:是}"/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="layui-form-item">--%>
                    <%--<div class="layui-col-md6">--%>
                        <%--<label class="layui-form-label">是否补货员</label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<cang:select disabled="true" type="list" name="iisReplenishment" id="iisReplenishment" entire="true" value="${memberInfo.iisReplenishment}" list="{0:否,1:是}"/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
            <%--</div>--%>
        <%--</c:if>--%>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                <button class="layui-btn" id="myFormSub">保存</button>
                <%--<button class="layui-btn" lay-submit="" lay-filter="myFormSub">保存</button>--%>
            </div>
        </div>
        <%--<input type="hidden" id="smerchantId" name="smerchantId" value="${memberInfo.smerchantId}">--%>
        <input type="hidden" id="merchantId" name="id" value="${memberInfo.id}"/>
    </form>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
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
                        alertDelAndReload("操作异常，请重新操作");
                    },
                    success: function (msg) {
                        //成功返回
                        closeLayer(loadIndex);//关闭加载框
                        if (msg.success) {
                            alertSuccess("操作成功", {
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

