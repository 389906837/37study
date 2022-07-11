<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ include file="/common/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>会员注册</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no" />
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="${staticSource}/static/css/comm.css" />
    <link rel="stylesheet" href="${staticSource}/static/css/index.css" />
</head>
<body>
<%@ include file="/common/include/commonHeader.jsp" %>

<div class="reg_container">
    <div class="logo">
        <img src="${staticSource}/static/images/register_logo.png">
    </div>
    <div class="reg_main">
        <div class="reg_form">
            <div class="form_content">
                <span>手机号</span>
                <div class="form_input"><input type="number" id="mobileNumber" name="mobileNumber" placeholder="请输入您注册的手机号" maxlength="11" oninput="if(value.length>11)value=value.slice(0,11)"></div>
            </div>
            <div style="margin: 30px 0px;">
                <div id="captcha">
                    <p id="wait" class="show">正在加载验证码......</p>
                </div>
            </div>

            <div>
                <a class="reg_btn">提交绑定</a>
                <div class="m-t-1" style="font-size: .9rem;">
                    注册即表示已阅读并同意<a href="${ctx}/protocol/agreement/reg" style="color:#3080e1;">《注册协议》</a>和<a
                        href="${ctx}/protocol/agreement/privacy" style="color:#d03638;">《隐私协议》</a>
                </div>
            </div>
        </div>

    </div>
</div>

<!-- 输入验证码弹出框 -->
<div class="eject"></div>
<!-- 短信验证码输入弹出框 -->
<div class="popup_bg close">
    <div class="popup_contant md">
        <h4 class="iphonecode_tip">请输入手机接收到的验证码</h4>
        <div class="iphonecode_input">
            <input type="number" id="yzm_1" name="yzm_1" onkeyup="changeVal(this);" oninput="if(value.length>1)value=value.slice(0,1);" maxlength="1" />
            <input type="number" id="yzm_2" name="yzm_2" onkeyup="changeVal(this);" oninput="if(value.length>1)value=value.slice(0,1);" maxlength="1" />
            <input type="number" id="yzm_3" name="yzm_3" onkeyup="changeVal(this);" oninput="if(value.length>1)value=value.slice(0,1);" maxlength="1" />
            <input type="number" id="yzm_4" name="yzm_4" onkeyup="changeVal(this);" oninput="if(value.length>1)value=value.slice(0,1);" maxlength="1" />
        </div>
    </div>
</div>

<!-- 注意，验证码本身是不需要 jquery 库，此处使用 jquery 仅为了在 demo 中使用，减少代码量 -->
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/comm.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/jquery.form.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/util.js?1000"></script>
<!-- 引入 gt.js-->
<script src="${staticSource}/static/js/gt.js"></script>
<script>
    <!-- -->
    $(function () {
        /*阻止冒泡*/
        $(".popup_contant").click(function(event){
            event.stopPropagation();
        });
        $("input[type='number']").keydown(function (event) {
            if (event.keyCode == 8) {//keycode为8表示退格键
                if (Util.isEmpty($(this).val())) {
                    if ($(this).attr("id") == 'yzm_2') {
                        $("#yzm_1").focus();
                    } else if ($(this).attr("id") == 'yzm_3') {
                        $("#yzm_2").focus();
                    } else if ($(this).attr("id") == 'yzm_4') {
                        $("#yzm_3").focus();
                    }
                }
            }
        });

        initYzm();
        $('.popup_bg').on('click',function () {
            $('.popup_bg').addClass('close');
            $(".eject").hide();
            //$(".popup_bg").hide();
            flag = true;
            submitflag = true;
            tempObj.reset();
        });
        $(".eject").click(function () {
           /* $(".eject").hide();
            //$(".popup_bg").hide();
            $('.popup_bg').addClass('close');
            flag = true;
            submitflag = true;
            tempObj.reset();*/
        });
    });

    function initYzm() {
        $.ajax({
            url: "${ctx}/uc/getGtCode?t=" + (new Date()).getTime(), // 加随机数防止缓存
            type: "post",
            dataType: "json",
            success: function (msg) {
                if (msg.success) {
                    var data = JSON.parse(msg.data);
                    initGeetest({
                        gt: data.gt,
                        challenge: data.challenge,
                        new_captcha: data.new_captcha, // 用于宕机时表示是新验证码的宕机
                        offline: !data.success, // 表示用户后台检测极验服务器是否宕机，一般不需要关注
                        product: "popup", // 产品形式，包括：float，popup
                        width: "100%"
                    }, handler);
                } else {
                    Util.Alert(msg.msg);
                }
            }
        });
    }
    var flag = true;
    var tempObj;
    var handler = function (captchaObj) {
        tempObj = captchaObj;
        $(".reg_btn").click(function () {
            if (flag) {
                flag = false;
                if (!verifyPhone()) {
                    flag = true;
                    return false;
                }
                var result = captchaObj.getValidate();
                if (!result) {
                    Util.Alert("请先完成图片验证码");
                    flag = true;
                    return false;
                }
                //发送验证码
                sendCodeByBind(result);

            }
        });
        // 将验证码加到id为captcha的元素里，同时会有三个input的值用于表单提交
        captchaObj.appendTo("#captcha");
        captchaObj.onReady(function () {
            $("#wait").hide();
        });
    };
    //绑定
    var submitflag = true;
    function submitForm() {
        if (submitflag) {
            submitflag = false;
            if (!verifyPhone()) {
                submitflag = true;
                return false;
            }
            var mobileNumber = $("#mobileNumber").val().trim();
            var yzm1 = $("#yzm_1").val().trim();
            var yzm2 = $("#yzm_2").val().trim();
            var yzm3 = $("#yzm_3").val().trim();
            var yzm4 = $("#yzm_4").val().trim();
            if (Util.isEmpty(yzm1) || Util.isEmpty(yzm2) || Util.isEmpty(yzm3) || Util.isEmpty(yzm4)) {
                Util.Alert("请输入手机短信验证码");
                submitflag = true;
                return false;
            }
            Util.Waiting({wid: 'wait_popup'});
            $.ajax({
                type: "post",
                data: {
                    'mobileNumber': mobileNumber,
                    'msgCode': yzm1 + yzm2 + yzm3 + yzm4
                },
                url: ctxRoot + "/uc/register",
                dataType: "json",
                success: function (msg) {
                    //成功返回
                    if (msg.success) {
                        Util.waitingClose($("#wait_popup"), function () {
                            Util.href(msg.data);
                        });
                    } else {
                        Util.waitingClose($("#wait_popup"), function () {
                            Util.Alert(msg.msg, function () {
                                window.location.reload();
                                submitflag = true;
                            });
                        });
                    }
                }
            });
        }
    }
    //发送短信
    function sendCodeByBind(result) {
        Util.Waiting({wid: "wait_popup"});
        $.ajax({
            type: "post",
            data: {
                mobileNumber: $("#mobileNumber").val(),
                challenge: result.geetest_challenge,
                validate: result.geetest_validate,
                seccode: result.geetest_seccode,
                types: 100
            },
            url: ctxRoot + "/base/sendMessageByTypes",
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    Util.waitingClose($("#wait_popup"));
                    $(".eject").show();
                    //$(".popup_bg").show();
                    $('.popup_bg').removeClass('close');
                    $("#yzm_1").val("");
                    $("#yzm_2").val("");
                    $("#yzm_3").val("");
                    $("#yzm_4").val("");
                    $("#yzm_1").focus();
                } else {
                    Util.waitingClose($("#wait_popup"), function () {
                        Util.Alert(data.msg);
                        flag = true;
                        tempObj.reset();
                    }, 200);
                }
            }
        });
    }
    //
    function changeVal(obj) {
        if (Util.isEmpty($(obj).val())) {
            return;
        }
        if ($(obj).attr("id") == 'yzm_1') {
            $("#yzm_2").focus();
        } else if ($(obj).attr("id") == 'yzm_2') {
            $("#yzm_3").focus();
        } else if ($(obj).attr("id") == 'yzm_3') {
            $("#yzm_4").focus();
        } else if ($(obj).attr("id") == 'yzm_4') {
            submitForm();
        }
    }
    //验证手机号
    function verifyPhone() {
        var mobileNumber = $("#mobileNumber").val().trim();
        if (Util.isEmpty(mobileNumber)) {
            Util.Alert("请输入绑定手机号码");
            return false;
        }
        if (!regPhone.test(mobileNumber)) {
            Util.Alert("绑定手机号码格式不正确");
            return false;
        }
        return true;
    }
    <%@ include file="/common/include/baidu.jsp"%>

</script>

</body>
</html>
