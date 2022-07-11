<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ include file="/common/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>授权</title>
    <link rel="stylesheet" href="${staticSource}/static/css/index.css">
    <link rel="stylesheet" href="${staticSource}/static/css/comm.css"/>

    <style>
        /* 2019-11-18 新增 */
        .form_content{position:relative;}
        .reg_form .form_content{margin-bottom: 20px;}
        .code-gray{background: #c5c5c5!important;}
        .form_content em{background: #3080e1;color: #fff;text-align:center;width:6rem;padding:0.4rem 0rem;border-radius:4px;font-size:1rem;position:absolute;right:5px;top:0.7rem;}

    </style>

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
                <label>手机号</label>
                <span id="mobileNumber"></span>
            </div>
            <div class="form_content">
                <label>验证码</label>
                <input type="number" id="usercode" name="usercode" placeholder="请输入验证码" maxlength="6" value="">
                <em class="accredit-code">获取验证码</em>
            </div>
          <%--  <div style="margin-bottom: 2rem;font-size: 1rem;display:none;" id="daojishi">
                <a class="time" href="javascript:void(0);" style="color: #3080e1;"> 90 </a>
                <span class="times" style="color:#6d6d6d;"> (s) </span>
            </div>--%>

            <div>
                <a class="reg_btn">授权</a>
                <div class="m-t-1" style="font-size: .9rem;">
                    授权即表示已阅读并同意<a href="${ctx}/protocol/agreement/trash" style="color:#3080e1;">《智能垃圾箱授权协议》</a>
                </div>
            </div>
        </div>
        <input type="hidden" id="mobile" name="mobile" value="${mobile}"/>
    </div>
</div>

<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/comm.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/jquery.form.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/util.js?1000"></script>
<script>
    $(document).ready(function () {
        var mobile = $("#mobile").val();
        var pat = /(\d{3})\d*(\d{4})/;
        $("#mobileNumber").text(mobile.replace(pat, '$1****$2'));
    });
    /* 验证码 */
    var validCode = true;
    $(".accredit-code").click(function () {
        var time = 90;
        if (validCode) {
            validCode = false;
            sendCodeByBind();
            $(".accredit-code").addClass("code-gray");
            var timer = setInterval(function () {
                time--;
                $(".accredit-code").html(time+'(s)');
                if (time == 0) {
                    clearInterval(timer);
                    validCode = true;
                    $(".accredit-code").removeClass("code-gray");
                    $(".accredit-code").html('获取验证码');
                }
            }, 1000)
        }
    });
    /*    var handler = function (captchaObj) {
     tempObj = captchaObj;*/
    /*倒计时*/
    /*  var timerer = null;
     function timer() {
     $("#daojishi").show();
     $('.time').removeAttr("onclick");
     if (null != timerer) {
     clearInterval(timerer);
     }
     $('.time').html("90");
     $('.times').show();
     var second = $('.time').html();
     timerer = setInterval(function () {
     second -= 1;
     if (second > 0) {
     $('.time').html(second);
     } else {
     clearInterval(timerer);
     $('.time').html("重新发送");
     $('.time').attr("onclick", "sendCodeByBind();");
     $('.times').hide();
     }
     }, 1000);
     }
     */
    /* $(".accredit-code").click(function () {
     //发送验证码
     sendCodeByBind();
     });*/
    $(".reg_btn").click(function () {
        //绑定
        submitForm();
    });
    /* };*/
    //发送短信
    function sendCodeByBind() {
        Util.Waiting({wid: "wait_popup"});
        var mobile = $("#mobile").val();
        $.ajax({
            type: "post",
            data: {
                mobileNumber: mobile,
                memId: '${memId}',
                types: 120
            },
            url: ctxRoot + "/base/sendMessageByTypes",
            dataType: "json",
            success: function (data) {
                if (data.success) {
                  /*  timer();*/
                    //  Util.waitingClose($("#wait_popup"));
                    Util.waitingClose($("#wait_popup"), function () {
                        Util.Alert(data.msg);
                    }, 200);
                } else {
                    Util.waitingClose($("#wait_popup"), function () {
                        Util.Alert(data.msg);
                    }, 200);
                }
            }
        });
    }
    function submitForm() {
        Util.Waiting({wid: 'wait_popup'});
        var mobile = $("#mobile").val();
        $.ajax({
            type: "post",
            data: {
                'mobileNumber': mobile,
                'msgCode': $("#usercode").val()
            },
            url: ctxRoot + "/uc/authTrash",
            dataType: "json",
            success: function (msg) {
                //成功返回
                if (msg.success) {
                    Util.waitingClose($("#wait_popup"));
                    jumpIndex();
                } else {
                    Util.waitingClose($("#wait_popup"), function () {
                        Util.Alert(msg.msg);
                    });
                }
            }
        });
    }
    <%@ include file="/common/include/baidu.jsp"%>
</script>

</body>
</html>
