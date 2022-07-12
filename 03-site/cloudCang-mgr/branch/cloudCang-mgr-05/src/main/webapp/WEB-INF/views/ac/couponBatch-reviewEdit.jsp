<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>审核</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/couponBatch/review" method="post" id="myForm" onsubmit="return false;">
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="activity.issuance.batch.number" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sbatchCode" id="sbatchCode" value="${couponBatch.sbatchCode}"
                           class="layui-input" disabled/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label"><spring:message code='main.sms.code' /></label>
                <div class="layui-input-inline" style="width: 95px;">
                    <input type="text" name="mobileCode" id="mobileCode"
                           class="layui-input"/>
                </div>
                <input type="button" class="layui-btn" style="display: inline-block;float: left;" data-type="sel" id="messageBtn"
                       value="<spring:message code='main.get.sms.code' />" onclick="sendMessage(this)"/>
            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label"><spring:message code='sh.audit.opinion' /></label>
            <div class="layui-input-block" >
                <textarea class="layui-textarea layui-form-textarea80p"<%--datatype="*" nullmsg="<spring:message code='ac.please.enter.an.audit.opinion' />"--%> id="sactivityInstruction"
                          name="sactivityInstruction"></textarea>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn  layui-btn-danger" id="myFormSubNo"><spring:message code="main.rejection"/></button>
                <button class="layui-btn" id="myFormSub" ><spring:message code="main.approval"/></button>
            </div>
        </div>
        <input type="hidden" name="istatus" id="istatus" value="${couponBatch.istatus}"/>
        <input type="hidden" name="ssubmitOperatorId" id="ssubmitOperatorId" value="${couponBatch.ssubmitOperatorId}"/>
        <input type="hidden" id="id" name="id" value="${couponBatch.id}"/>
    </form>
</div>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form'], function () {
        var form = layui.form;
    });
    $(function () {
        $("#myFormSub").click(function () {
            if(!checkMsg()) {
                return false;
            }
            layer.confirm('<spring:message code="main.confirm.audit" />', {
                btn: ['<spring:message code="main.no" />', '<spring:message code="main.yes" />'], //按钮
                cancel: function (index) {
                    closeLayer(index);//关闭加载框
                }
            }, function (index) {
                closeLayer(index);//关闭加载框
            }, function () {
                var loadIndex = loading();
                $('#myForm').ajaxSubmit({
                    type: "post",
                    dataType: "json",
                    data: {
                        "couponBatchId": $('#id').val(),
                        "msgCode": $('#mobileCode').val(),
                        "stype": $('#istatus').val(),
                        "saduitMsg": $('#sactivityInstruction').val()
                    },
                    async: true,
                    error: function () {
                        alertDelAndReload('<spring:message code="main.error.try.again"/>');
                    },
                    success: function (msg) {
                        closeLayer(loadIndex);//关闭加载框
                        if (msg.success) {
                            alertSuccess('<spring:message code="main.success"/>', {
                                'index': index
                            }, function () {
                                parent.closeLayerAndRefresh(index);
                            });
                        } else {
                            alertDel(msg.msg);
                            resteTime($("#messageBtn"));
                        }
                    }
                });
            });
            return false;
        });
        $("#myFormSubNo").click(function () {
            if(!checkMsg()) {
                return false;
            }
            var instruction = $("#sactivityInstruction").val();
            if (isEmpty(instruction)) {
                alertDel('<spring:message code="sb.audit.rejection.reason.cannot.be.empty" />');
                return false;
            }
            layer.confirm("<spring:message code='ac.are.you.sure.you.want.to.decline.the.review' />？", {
                btn: ['<spring:message code="main.no" />', '<spring:message code="main.yes" />'], //按钮
                cancel: function (index) {
                    closeLayer(index);//关闭加载框
                }
            }, function (index) {
                closeLayer(index);//关闭加载框
            }, function () {
                var loadIndex = loading();
                $('#myForm').ajaxSubmit({
                    type: "post",
                    dataType: "json",
                    data: {
                        "couponBatchId": $('#id').val(),
                        "msgCode": $('#mobileCode').val(),
                        "stype": "21",
                        "saduitMsg": $('#sactivityInstruction').val()
                    },
                    async: true,
                    error: function () {
                        alertDelAndReload('<spring:message code="main.error.try.again"/>');
                    },
                    success: function (msg) {
                        closeLayer(loadIndex);//关闭加载框
                        if (msg.success) {
                            alertSuccess('<spring:message code="main.success"/>', {
                                'index': index
                            }, function () {
                                parent.closeLayerAndRefresh(index);
                            });
                        } else {
                            alertDel(msg.msg);
                            resteTime($("#messageBtn"));
                        }
                    }
                });
            });
            return false;
        });
    });


    function checkMsg() {
        if(isEmpty($("#mobileCode").val())) {
            alertDel("<spring:message code='ac.sms.verification.code.cannot.be.empty' />");
            return false;
        }
        return true;
    }

    /*获取验证码*/
    var clock;
    var nums = 90;
    var btn;
    var flag = true;
    function sendMessage(thisBtn) {
        if(flag) {
            flag = false;
            $.ajax({
                type: "POST",
                dataType: "json",
                url: "${ctx}/mgr/getCode",
                data: {
                    sauditOperatorName: $('#ssubmitOperatorId').val()
                },
                success: function (msg) {
                    if (msg.success) {
                        btn = thisBtn;
                        btn.disabled = true; //将按钮置为不可点击
                        btn.value = nums + '<spring:message code='ac.regain.after.seconds' />';
                        clock = setInterval(doLoop, 1000); //一秒执行一次
                    } else {
                        alertDel(msg.msg);
                        resteTime(btn);
                    }
                }
            });
        }

    }

    function doLoop() {
        nums--;
        if (nums > 0) {
            btn.value = nums + '<spring:message code='ac.regain.after.seconds' />';
        } else {
            clearInterval(clock); //清除js定时器
            resteTime(btn);
        }
    }

    function resteTime(btn) {
        btn.disabled = false;
        btn.value = "<spring:message code='main.get.sms.code' />";
        nums = 90; //重置时间
        flag = true;
    }
    $(function(){
        $("input:button").click(function() {
            $("input[name='mobileCode']").val("").focus(); // 清空并获得焦点
        });
    })
</script>
</body>
</html>

