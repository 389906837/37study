<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>编辑平台应用</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?23" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/userInfo/save" method="post" id="myForm" enctype="multipart/form-data">

        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">会员昵称</label>
                <div class="layui-input-inline">
                    <input type="text" name="snickName" id="snickName" datatype="*" nullmsg="请输入会员昵称"
                           value="${userInfoVo.snickName}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"> 会员生日</label>
                <div class="layui-input-inline">
                    <input type="text" name="dbirthdate" datatype="*" nullmsg="请选择签约日期" id="dbirthdate"
                       value="<fmt:formatDate value='${userInfoVo.dbirthdate}' pattern='yyyy-MM-dd'/>"
                           class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">手机号</label>
                <div class="layui-input-inline">
                    <input type="text" name="smobile" id="smobile" datatype="*" nullmsg="请输入手机号"
                           value="${userInfoVo.smobile}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">邮箱</label>
                <div class="layui-input-inline">
                    <input type="text" name="semail" id="semail" datatype="*" nullmsg="请输入邮箱"
                           value="${userInfoVo.semail}" class="layui-input"/>
                </div>
            </div>
        </div>
        <c:if test="${empty userInfoVo.id}">
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">登录密码</label>
                    <div class="layui-input-inline">
                        <input type="text" name="sloginPwd" id="sloginPwd" datatype="*" nullmsg="请输入登录密码"
                               value="${userInfoVo.sloginPwd}" class="layui-input"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label">确认登录密码</label>
                    <div class="layui-input-inline">
                        <input type="text" name="loginPwdConfirm" id="loginPwdConfirm" datatype="*" nullmsg="请确认登录密码"
                               value="" class="layui-input"/>
                    </div>
                </div>
            </div>
        </c:if>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">会员性别</label>
                <div class="layui-input-inline">
                    <cang:select type="list" list="{0:女,1:男}" id="isex" value="${userInfoVo.isex}"
                                 name="isex" entire="true" entireName="请选择会员性别"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">应用会员类型</label>
                <div class="layui-input-inline">
                    <cang:select type="list" list="{10:个人会员,20:企业会员}" id="imemberType" value="${userInfoVo.imemberType}"
                                 name="imemberType" entire="true" entireName="请选择会员类型"/>
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">是否挂靠商户</label>
                <div class="layui-input-inline">
                    <cang:select type="list" entire="true" value="${userInfoVo.iisMerchaant}"
                                 entireName="请选择是否挂靠商户"
                                 layFilter="isMerchaant"
                                 name="iisMerchaant" id="iisMerchaant" list="{0:否,1:是}"/>
                </div>
            </div>
            <div class="layui-col-md6" name="merchantName" id="merchantName" style="display:none;">
                <label class="layui-form-label">挂靠商户</label>
                <div class="layui-input-inline" style="width: 115px">
                    <input type="text" name="smerchantName" readonly="true" id="smerchantName"
                           value="${userInfoVo.merchantName}"
                           class="layui-input"/>
                </div>
                <button class="layui-btn" id="btn1" style="display: inline-block;float: left;" type="button"
                        data-type="sel">选择
                </button>
            </div>
        </div>


        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                <button class="layui-btn" id="myFormSub">保存</button>
            </div>
        </div>
        <input type="hidden" id="id" name="id" value="${userInfoVo.id}"/>
        <input type="hidden" id="scode" name="scode" value="${userInfoVo.scode}"/>
        <input type="hidden" id="smerchantId" name="smerchantId" value="${userInfoVo.smerchantId}"/>
        <input type="hidden" id="smerchantCode" name="smerchantCode" value="${userInfoVo.smerchantCode}"/>
    </form>
</div>


<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js" type="text/javascript"></script>
<script type="text/javascript">
    $(document).ready(function () {
        if (${not  empty userInfoVo.id}) {
            if (${1 == userInfoVo.iisMerchaant }) {
                $("#merchantName").css("display", "block");
            } else {
                $("#merchantName").css("display", "none");
            }
        }
    });

    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate'], function () {
        var form = layui.form;
        //执行一个laydate实例
        var laydate = layui.laydate;
        //监听提交
        $("#myForm").Validform({
            btnSubmit: "#myFormSub",  //根据id触发
            tiptype: 3,                  //第三种方式
            showAllError: true,
            beforeSubmit: function () { //验证过后执行save方法
                var iisMerchaant = $("#iisMerchaant option:selected").val();
                var smerchantName = $("#smerchantName").val();

                if (1 == iisMerchaant && isEmpty(smerchantName)) {
                    alertDel("请选择挂靠商户");
                    return false;
                }
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

        form.on('select(isMerchaant)', function (data) {
            if (!isEmpty(data.value)) {
                $("#iisMerchaant").parent().find("span").hide();
            } else {
                $("#iisMerchaant").parent().find("span").show();
                if (!$("#iisMerchaant").parent().find("span").hasClass("Validform_wrong")) {
                    $("#iisMerchaant").parent().find("span").html($("#iisMerchaant").attr("nullmsg"));
                    $("#iisMerchaant").parent().find("span").removeClass("Validform_right");
                    $("#iisMerchaant").parent().find("span").addClass("Validform_wrong");
                }
            }
            if (1 == data.value) {
                $("#merchantName").css("display", "block");
            } else {
                $("#merchantName").css("display", "none");
                $("#smerchantName").val("");
                $("#smerchantId").val("");
                $("#smerchantCode").val("");
            }
        });
        laydate.render({
            //n天前  min: 0,
            elem: '#dbirthdate',
            type: 'date',
            done: function (value) { //监听日期被切换
                if (!isEmpty(value)) {
                    $("#dbirthdate").removeClass("Validform_error");
                    $("#dbirthdate").parent().find("span").hide();
                } else {
                    $("#dbirthdate").addClass("Validform_error");
                    $("#dbirthdate").parent().find("span").html($("#dbirthdate").attr("nullmsg"));
                    $("#dbirthdate").parent().find("span").removeClass("Validform_right");
                    $("#dbirthdate").parent().find("span").addClass("Validform_wrong");
                    $("#dbirthdate").parent().find("span").show();
                }
            }
        });

        $(function () {
            $("#btn1").click(function () {
                showLayerMax("选择挂靠商户", ctx + "/userInfo/selectMerchant");
            });
            $("#cancelBtn").click(function () {
                parent.layer.close(index);
                return false;
            });
        });
    });
    function selectSupp(merchantId, merchantCode, merchantName) {
        $("#smerchantId").val(merchantId);
        $("#smerchantCode").val(merchantCode);
        $("#smerchantName").val(merchantName);
    }

</script>
</body>
</html>

