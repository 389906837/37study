<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>开放平台识别服务器更新模型</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/recognitionServer/saveUpModel" method="post" id="myForm">

        <div class="layui-form-item">
            <div class="layui-col-md6" name="modelCode" id="modelCode">
                <label class="layui-form-label">识别模型编号</label>
                <div class="layui-input-inline" style="width: 115px">
                    <input type="text" name="smodelCode" readonly="true" id="smodelCode"
                           value="${recognitionServer.smodelCode}"
                           class="layui-input"/>
                </div>
                <button class="layui-btn" id="btn1" style="float: left;" type="button"
                        data-type="sel">选择
                </button>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">模型识别置信度</label>
                <div class="layui-input-inline">
                    <input type="text" name="fvisThresh" id="fvisThresh" value="${serverModel.fvisThresh}" class="layui-input" />
                </div>
            </div>
        </div>
   <%--     <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">是否立即重启</label>
                <div class="layui-input-inline">
                    <select class="form-control" name="iisRestartImmediately" id="iisRestartImmediately">
                        <option value="0">否</option>
                        <option value="1">是</option>
                    </select>
                </div>
            </div>
        </div>--%>

        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                <button class="layui-btn" lay-submit="" lay-filter="myFormSub">保存</button>
            </div>
        </div>
        <input type="hidden" id="id" name="id" value="${recognitionServer.id}"/>
        <input type="hidden" id="scode" name="scode" value="${recognitionServer.scode}"/>
        <input type="hidden" id="smodelId" name="smodelId" value="${recognitionServer.smodelId}"/>

    </form>
</div>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate'], function () {
        var form = layui.form;
        //监听提交
        form.on('submit(myFormSub)', function () {
            var iauditStatus = $("#iauditStatus option:selected").val();
            var sauditReason = $("#sauditReason").val();
            if (30 == iauditStatus && isEmpty(sauditReason)) {
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
        $(function () {
            $("#btn1").click(function () {
                showLayerMax("选择识别模型", ctx + "/serverModel/selectServerModel");
            });
            $("#cancelBtn").click(function () {
                parent.layer.close(index);
                return false;
            });
        });
    });
    $(function () {
        $("#cancelBtn").click(function () {
            parent.layer.close(index);
            return false;
        });
    });
    function selectSupp(smodelCode, smodelId) {
        $("#smodelCode").val(smodelCode);
        $("#smodelId").val(smodelId);
    }
</script>
</body>
</html>

