<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>审核</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/domainconf/saveExamine" method="post" id="myForm">
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">状态</label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="istatus" id="istatus"  value="${domainConf.istatus}"
                                 list="{20:审核通过,30:审核驳回}"/>
                </div>
            </div>

        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">审核意见</label>
            <div class="layui-input-block">
                    <textarea id="sauditOpinion" name="sauditOpinion" class="layui-textarea layui-form-textarea80p"
                              placeholder="审核意见(驳回必填)">${domainConf.sauditOpinion}</textarea>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                <button class="layui-btn" lay-submit="" lay-filter="myFormSub">保存</button>
            </div>
        </div>
        <input type="hidden" id="id" name="id" value="${domainConf.id}"/>
        <input type="hidden" id="smerchantId" name="smerchantId" value="${domainConf.smerchantId}"/>
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
            var iauditStatus = $("#istatus option:selected").val();
            var sauditRefuseReason = $("#sauditOpinion").val();
            if (30 == iauditStatus && isEmpty(sauditRefuseReason)) {
                alertDel("审核驳回原因不能为空！");
                return false;
            }

            var loadIndex = loading();
            $('#myForm').ajaxSubmit({
                type: "post",
                url: ctx + "/domainconf/selectDomain",
                dataType: "json",
                async: true,
                error: function () {
                    alertDelAndReload("操作异常，请重新操作");
                },
                success: function (msg) {
                    if (msg.data != null) {/*  msg.data !=null  表示已经该商户有域名通过审核*/
                        layer.confirm("商户已有域名通过审核,确定要替换已通过审核的域名？", {
                            btn: ['取消', '确定'], //按钮
                            cancel: function (index) {
                                closeLayer(index);//关闭加载框
                                closeLayer(loadIndex);
                            }
                        }, function (index) {
                            closeLayer(index);//关闭加载框
                            closeLayer(loadIndex);
                        }, function () {
                            $('#myForm').ajaxSubmit({
                                type: "post",
                                data: {"up": '1'},
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
                        });
                    } else {
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

