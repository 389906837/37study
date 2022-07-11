<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/include/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="keywords" content="${article.skeys}"/>
    <meta name="description" content="${article.snewsAbstract}"/>
    <title>${article.stitle}-37号仓-计算机视觉识别核心算法供应商-江苏叁拾柒号仓智能科技有限公司</title>
    <link rel="stylesheet" href="${staticSource}/static/css/index.css">
    <link rel="shortcut icon" href="${staticSource}/static/favicon.ico" type="image/x-icon">
</head>
<style type="text/css">
    body {
        height: fit-content;
    }
</style>
<body>
<!--立即申请-->
<div class="mask"></div>
<div class="applyaccount">
    <a class="support-circle"><img src="${staticSource}/static/images/support/circle.png"></a>
    <div class="apply-top">申请测试账号</div>
    <form class="form-horizontal" id="myform" method="post">
        <div class="form-group p-t-45 m-b-24">
            <label class="col-sm-3 t-r">测试账号平台：</label>
            <div class="col-sm-8 t-l" id="accountname"> 智能云服务平台</div>
            <input type="hidden" id="accountType" name="accountType" value="">
        </div>

        <div class="form-group">
            <label class="col-sm-3 control-label">公司名称/个人：</label>
            <div class="col-sm-8 control-div">
                <input class="form-control" id="name" name="userName"><span class="help-block m-b-none"> &nbsp;</span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">联系人：</label>

            <div class="col-sm-8 control-div">
                <input class="form-control" id="thecontact" name="linkMan"><span
                    class="help-block m-b-none">&nbsp;</span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">联系电话：</label>

            <div class="col-sm-8 control-div">
                <input class="form-control" id="contact" name="tel"><span class="help-block m-b-none">&nbsp; </span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">需求描述：</label>

            <div class="col-sm-8 control-div">
                <textarea class="form-control" id="require_text" name="require"></textarea>
            </div>
        </div>
        <div class="form-group m-t-20">
            <label class="col-sm-3 control-label">验证码：</label>
            <div class="col-sm-8 control-div">
                <input class="form-control" id="code" placeholder="请输入验证码" name="captcha" type="text" maxlength="5"
                       autocomplete="off" style="width:50%"><img class="code" id="captchaImg" src="${ctx}/Kaptcha"
                                                                 onclick="refreshCaptcha();"><span
                    class="help-block m-b-none">&nbsp;</span>
            </div>
        </div>
        <div class="form-group m-t-20 m-b-30">
            <a class=" col-sm-offset-3 submit-btn">提交</a>
        </div>
    </form>
</div>

<!--提交成功-->
<div class="success">
    <a class="support-circle"><img src="${staticSource}/static/images/support/success-circle.png"></a>
    <p style="padding-top: 67px"><img src="${staticSource}/static/images/success.png"></p>
    <p style="font-size: 26px;padding-top: 24px;">提交成功</p>
    <p style="color: #8b8b8b;padding: 55px 0px 75px 0px">您的申请已提交，稍后我们会有专人与您联系，感谢您的支持。</p>
</div>
<script src="${staticSource}/static/js/jquery.min.js"></script>

<script type="text/javascript">
    $(function () {
        /*点击申请*/
        $('.btn-red').on('click', function () {
            $('.mask').css('display', 'block');
            $('.applyaccount').css('display', 'block');
            $('#accountname').empty();
            $('#accountname').append($(this).parent().prevAll('h1').text());
            setAccountType($(this).parent().prevAll('h1').text());
        });
        $('.mask,.support-circle').on('click', function () {
            $('.mask').css('display', 'none');
            $('.applyaccount').css('display', 'none');
            $('.success').css('display', 'none');
        });
        /*验证*/

        $('.submit-btn').on('click', function () {
            var validate = true;
            if ($('#name').val() == "" || $('#name').val() == null) {
                $('#name').nextAll('.help-block').text('请输入申请公司名称');
                validate = false;
            }
            if ($('#name').val().length < 2) {
                $('#name').nextAll('.help-block').text('请输入正确的申请公司名称');
                validate = false;
            }
            if ($('#thecontact').val() == "" || $('#thecontact').val() == null) {
                $('#thecontact').nextAll('.help-block').text('请输入联系人姓名，2-7个汉字');
                validate = false;
            }
            if ($('#contact').val() == "" || $('#contact').val() == null) {
                $('#contact').nextAll('.help-block').text('请输入正确的联系方式');
                validate = false;
            }
            if ($('#code').val() == "" || $('#code').val() == null || $('#code').val().length != 4) {
                $('#code').nextAll('.help-block').text('请输入正确验证码');
                validate = false;
            }
            if (validate) {
                yzCaptcha();
            } else {
                return false;
            }
        });
        $('#name').on('input blur', function () {
            if ($(this).val() == "" || $(this).val() == null) {
                $(this).nextAll('.help-block').text('请输入申请公司名称');
            } else {
                $(this).nextAll('.help-block').empty();
            }
        });
        $('#thecontact').on('input blur', function () {
            if ($(this).val() == "" || $(this).val() == null) {
                $(this).nextAll('.help-block').text('请输入联系人');
            } else {
                $(this).nextAll('.help-block').empty();
            }
        });
        $('#contact').on('input blur', function () {

            if ($(this).val() == "" || $(this).val() == null || !(/^(13[0-9]|14[0-9]|15[0-9]|16[0-9]|17[0-9]|18[0-9]|19[0-9])\d{8}$/.test($(this).val()))) {
                $(this).nextAll('.help-block').text('请输入正确的联系方式');
            } else {
                $(this).nextAll('.help-block').empty();
            }
        });
        $('#code').on('input blur', function () {
            if ($(this).val() == "" || $(this).val() == null) {
                $(this).nextAll('.help-block').text('请输入验证码');
            } else {
                $(this).nextAll('.help-block').empty();
            }
        });
    });

    function tj() {
        var userName = $("#name").val();
        var linkMan = $("#thecontact").val();
        var tel = $("#contact").val();
        var require = $("#require_text").val();
        var accountType = $("#accountType").val();
        document.getElementById("myform").reset();
        if (tel == "" || tel == null || !(/^(13[0-9]|14[0-9]|15[0-9]|16[0-9]|17[0-9]|18[0-9]|19[0-9])\d{8}$/.test(tel))) {
            $(this).nextAll('.help-block').text('请输入正确的联系方式');
            return;
        }
  /*      $.post(
            "/applicants/save"
            , {"userName": userName, "linkMan": linkMan, "tel": tel, "require": require, "accountType": accountType}
            , function (result) {
                if (result.isSuccess) {
                    // 请求处理
                    $('.applyaccount').css('display', 'none');
                    $('.success').css('display', 'block');
                }
            }, "json");*/
        $.ajax({
            type: "POST",
            url: "${ctx}/applicants/save",
            data: {"userName": userName, "linkMan": linkMan, "tel": tel, "require": require, "accountType": accountType},
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    // 请求处理
                    $('.applyaccount').css('display', 'none');
                    $('.success').css('display', 'block');
                }
            }
        });
    }
    function yzCaptcha() {
        var captcha = $("#code").val();
        $.post(
            "${ctx}/applicants/captcha"
            , {captcha: captcha}
            , function (data) {
                if ($.trim(data) == 'true') {
                    tj();
                    /* $('.applyaccount').css('display', 'none');
                     $('.success').css('display', 'block');*/
                } else {
                    alert("验证码错误");
                }
                // 请求处理
            }, "json");
    }
    function refreshCaptcha() {
        document.getElementById('captchaImg').src = '${ctx}/Kaptcha?' + Math.floor(Math.random() * 100);
    }
</script>
<%--------------------------------------------------------------------------------往上-23行----支撑体系--%>

<div class="main mediareportslist-bg">
    <jsp:include page="/common/include/noIndexFrontTop.jsp"/>
    <c:if test="${article.ssubjoinTitle eq 'product'}">
        <div class="products-img large-header" id="large-header">
            <canvas id="Mycanvas"></canvas>
        </div>
    </c:if>
    ${content2.scontent}
    <jsp:include page="/common/include/indexFrontBottom.jsp"/>
</div>
</body>
<script type="text/javascript">

    $(function () {
        $('.product-list').find(".product-each").each(function (num, element) {
            console.log(num);
            if (num % 2 != 0) {
                $(this).addClass('product-bg-odd');
            }
        });
        $('.product-list').find(".product-eachimg").each(function (num, element) {
            console.log(num);
            if (num % 2 == 0) {
                $(this).addClass('product-bg-odd');
            }
        });
        $("#${article.id}").addClass("active");
    });
    function setAccountType(type) {
        $("#accountType").val(type.trim());
    }

    <%@ include file="/common/include/baidu.jsp"%>
</script>
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/jquery.lazyload.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/jquery.three-slide.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/demo-2.js"></script>
</html>
