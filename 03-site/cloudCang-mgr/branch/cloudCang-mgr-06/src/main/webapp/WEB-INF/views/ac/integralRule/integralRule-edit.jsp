<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>积分规则</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?20188" rel="stylesheet">
<style type="text/css">
    .layui-form-label {width: 150px;}
</style>
</head>
<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/integralRule/save" method="post" id="myForm">

        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">是否有效</label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="iisValid" id="iisValid"
                                 value="${integralRule.iisValid}" list="{1:启用,0:禁用}"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">赠送方式</label>
                <div class="layui-input-inline">
                    <cang:select type="dic" groupNo="SP000006" name="igiveMethod" layFilter="igiveMethod"
                                 id="igiveMethod" value="${empty integralRule.igiveMethod ? 10 : integralRule.igiveMethod}" />
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">积分(分)/比例(%)</label>
                <div class="layui-input-inline">
                    <input type="text" name="fbaseIntegral" id="fbaseIntegral"
                           value='<fmt:formatNumber value="${integralRule.fbaseIntegral}" pattern="#0.##"/>' class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">赠送下限(分)</label>
                <div class="layui-input-inline">
                    <input type="text" name="fminValue" id="fminValue"
                           value="${integralRule.fminValue}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">赠送上限(分)</label>
                <div class="layui-input-inline">
                    <input type="text" name="fmaxValue" id="fmaxValue"
                           value="${integralRule.fmaxValue}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">奖励说明</label>
                <div class="layui-input-inline" style="width: 212px">
                    <textarea class="layui-textarea" datatype="*" nullmsg="请输入奖励说明" id="sactivityInstruction"
                              name="sactivityInstruction">${integralRule.sactivityInstruction}</textarea>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">备注</label>
                <div class="layui-input-inline" style="width: 212px">
                    <textarea class="layui-textarea" id="sremark" name="sremark">${integralRule.sremark}</textarea>
                </div>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                <button class="layui-btn" id="myFormSub">保存</button>
            </div>
        </div>
        <input type="hidden" id="id" name="id" value="${integralRule.id}"/>
        <input type="hidden" id="sacId" name="sacId" value="${integralRule.sacId}"/>
        <input type="hidden" id="sacCode" name="sacCode" value="${integralRule.sacCode}"/>
        <input type="hidden" id="scode" name="scode" value="${integralRule.scode}"/>
    </form>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js"  type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit'], function () {

        var form = layui.form;
        //监听提交
        $("#myForm").Validform({
            btnSubmit:"#myFormSub",  //根据id触发
            tiptype:3,                  //第三种方式
            showAllError:true,
            beforeSubmit:function() { //验证过后执行save方法
                if (isEmpty($("#fbaseIntegral").val())) {
                    alertDel("积分值或比例不能为空！");
                    return false;
                }
                var igiveMethod = $("#igiveMethod").val();
                if (igiveMethod == 10) {
                    if (isEmpty($("#fminValue").val())) {
                        alertDel("赠送下限不能为空！");
                        return false;
                    }
                    if (isEmpty($("#fmaxValue").val())) {
                        alertDel("赠送上限不能为空！");
                        return false;
                    }
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
                                'index':index
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

        //赠送方式
        form.on('select(igiveMethod)', function(data){
            if (data.value == 10) {//比例
                $("#fminValue").removeAttr("disabled");
                $("#fmaxValue").removeAttr("disabled");
                $("#fmaxValue").val("");
                $("#fminValue").val("");
            } else {
                $("#fminValue").attr("disabled", "disabled");
                $("#fmaxValue").attr("disabled", "disabled");
            }
        });
    });
    $(function () {
        //返回
        $("#cancelBtn").click(function () {
            parent.layer.close(index);
            return false;
        });
    });

</script>
</body>
</html>