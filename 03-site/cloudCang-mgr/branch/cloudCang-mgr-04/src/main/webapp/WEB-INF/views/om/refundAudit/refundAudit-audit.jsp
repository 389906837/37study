<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>审核</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/refundAudit/saveAudit" method="post" id="myForm">
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">状态</label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="iauditStatus" id="iauditStatus"
                                 value="${refundAudit.iauditStatus}" list="{20:审核通过,30:审核驳回}" layFilter="auditStatus"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item" id="RefuseReason">
            <label class="layui-form-label">审核原因</label>
            <div class="layui-input-block">
                    <textarea id="sauditRefuseReason" name="sauditRefuseReason"
                              class="layui-textarea layui-form-textarea80p"
                              placeholder="审核原因">${refundAudit.sauditRefuseReason}</textarea>
            </div>
        </div>
        <c:if test="${not empty refundAudit.srefundReason}">
            <div class="layui-form-item  mt13">
                <label class="layui-form-label">退款原因</label>
                <div class="layui-input-block">
                    <textarea id="srefundReason" name="srefundReason" disabled
                              class="layui-textarea layui-form-textarea80p"
                              placeholder="退款原因">${refundAudit.srefundReason}</textarea>
                </div>
            </div>
        </c:if>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                <button class="layui-btn" lay-submit="" lay-filter="myFormSub">保存</button>
            </div>
        </div>
        <input type="hidden" id="id" name="id" value="${refundAudit.id}"/>
        <input type="hidden" id="sorderId" name="sorderId" value="${refundAudit.sorderId}"/>
        <input type="hidden" id="srefundCode" name="srefundCode" value="${refundAudit.srefundCode}"/>
    </form>
</div>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    /*    $(document).ready(function () {
     if () {//通过审核
     $("#RefuseReason").css("display", "none");
     } else {
     $("#RefuseReason").css("display", "block");
     }
     });*/
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate'], function () {
        var form = layui.form;
        //监听提交
        form.on('submit(myFormSub)', function () {
            var iauditStatus = $("#iauditStatus option:selected").val();
            var sauditRefuseReason = $("#sauditRefuseReason").val();
            if (30 == iauditStatus && isEmpty(sauditRefuseReason)) {
                alertDel("审核驳回原因不能为空！");
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

