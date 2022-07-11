<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>菜单编辑</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
<style type="text/css">
    .layui-form-label{width: 100px;}
</style>
</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/purview/save" method="post" id="myForm">
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">权限码</label>
                <div class="layui-input-inline">
                    <input type="text" name="spurCode" datatype="*" nullmsg="请输入权限码" id="spurCode"
                           value="${purview.spurCode}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">权限名称</label>
                <div class="layui-input-inline">
                    <input type="text" name="spurName" datatype="*" nullmsg="请输入权限名称" id="spurName"
                           value="${purview.spurName}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">商户可用</label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="iisMerchantUsed" id="iisMerchantUsed"
                                 exp="datatype=\"*\" nullmsg=\"请选择商户是否可用\""
                                 value="${purview.iisMerchantUsed}" list="{0:否,1:是}"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">路径</label>
                <div class="layui-input-inline">
                    <input type="text" name="surlPath" datatype="*" nullmsg="请输入路径" id="surlPath"
                           value="${purview.surlPath}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">备注</label>
            <div class="layui-input-block">
                <textarea class="layui-textarea layui-form-textarea80p" name="sdescription"
                          id="sdescription">${purview.sdescription}</textarea>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                <button class="layui-btn" <%--lay-submit="" lay-filter="myFormSub"--%>id="myFormSub">保存</button>
            </div>
        </div>
        <input type="hidden" id="id" name="id" value="${purview.id}"/>
        <input type="hidden" id="sparentId" name="sparentId" value="${purview.sparentId}"/>
    </form>
</div>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js" type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate'], function () {
        //监听提交
        $("#myForm").Validform({
            btnSubmit: "#myFormSub",  //根据id触发
            tiptype: 3,                  //第三种方式
            showAllError: true,
            beforeSubmit: function () { //验证过后执行save方法
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

