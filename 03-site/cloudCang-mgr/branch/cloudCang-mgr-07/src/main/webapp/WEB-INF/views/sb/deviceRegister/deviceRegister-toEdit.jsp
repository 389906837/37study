<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备编辑</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?203" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/device/register/edit" method="post" id="myForm">

        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">原始注册码</label>
                <div class="layui-input-inline">
                    <span class="form-span">${deviceRegisterDomain.oldReqIp}</span>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">新注册码</label>
                <div class="layui-input-inline">
                    <span class="form-span">${deviceRegisterDomain.sregIp}</span>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">上次审核拒绝原因</label>
                <div class="layui-input-inline">
                    <span class="form-span">${deviceRegisterDomain.sremark}</span>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">审核备注</label>
            <div class="layui-input-block">
                <textarea id="sremark" name="sremark" class="layui-textarea layui-form-textarea80p"
                          placeholder="审核意见（非必填项）"></textarea>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                <button class="layui-btn" lay-submit="" lay-filter="myFormSub">保存</button>
            </div>
        </div>
        <input type="hidden" name="sregIp" id="sregIp" value="${deviceRegisterDomain.sregIp}" class="layui-input" />
        <input type="hidden" name="id" id="id" value="${deviceRegisterDomain.id}" class="layui-input" />
        <input type="hidden" name="sdeviceId" id="sdeviceId" value="${deviceRegisterDomain.sdeviceId}" class="layui-input" />
    </form>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var form;
    layui.use(['form', 'layedit', 'laydate'], function () {
        form = layui.form;
        //监听提交
        form.on('submit(myFormSub)', function () {
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


