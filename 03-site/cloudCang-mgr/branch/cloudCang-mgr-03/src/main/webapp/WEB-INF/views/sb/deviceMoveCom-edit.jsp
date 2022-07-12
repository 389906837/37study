<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
<title>修改搬迁状态</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/device/deviceComplete" method="post" id="myForm">
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='sb.relocation.date' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="tmoveTime" datatype="*" nullmsg="<spring:message code='sb.please.select.the.relocation.date' />" id="tmoveTime"
                           value="<fmt:formatDate value='${deviceMoveRecord.tmoveTime}' pattern='yyyy-MM-dd'/>"
                           class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code='sb.relocation.person.in.charge' /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="sprincipal" id="sprincipal"
                               value="${deviceMoveRecord.sprincipal}" datatype="*" nullmsg="<spring:message code='sb.please.fill.in.the.person.in.charge.of.relocation' />"
                               class="layui-input"/>
                    </div>
                </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
                <button class="layui-btn" id="myFormSub"><spring:message code="main.submit" /></button>
            </div>
        </div>
        <input type="hidden" id="id" name="id" value="${deviceMoveRecord.id}" />
        <input type="hidden" id="istatus" name="istatus" value="${deviceMoveRecord.istatus}" />
    </form>
</div>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js"  type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate','element'], function(){
        var element = layui.element;
        var laydate = layui.laydate;


        laydate.render({
            elem: '#tmoveTime',//指定元素
            type: 'date',
            done: function(value, date){
                if(!isEmpty(value)) {
                    $("#tmoveTime").removeClass("Validform_error");
                    $("#tmoveTime").parent().find("span").hide();
                } else {
                    $("#tmoveTime").addClass("Validform_error");
                    $("#tmoveTime").parent().find("span").html($("#tmoveTime").attr("nullmsg"));
                    $("#tmoveTime").parent().find("span").removeClass("Validform_right");
                    $("#tmoveTime").parent().find("span").addClass("Validform_wrong");
                    $("#tmoveTime").parent().find("span").show();
                }
            }
        });




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

