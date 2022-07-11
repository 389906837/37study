<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>编辑开放平台API服务器</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?23" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/gpuServer/save" method="post" id="myForm" enctype="multipart/form-data">

        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">服务器名称</label>
                <div class="layui-input-inline">
                    <input type="text" name="sname" id="sname" datatype="*" nullmsg="请输入服务器名称"
                           value="${serverList.sname}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">服务器IP</label>
                <div class="layui-input-inline">
                    <input type="text" name="sip" id="sip" datatype="*" nullmsg="请输入服务器IP"
                           value="${serverList.sip}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">服务器端口</label>
                <div class="layui-input-inline">
                    <input type="text" name="sport" id="sport" datatype="*" nullmsg="请输入服务器端口"
                           value="${serverList.sport}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">服务器类型</label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="itype" id="itype"
                                 exp="datatype=\"*\" nullmsg=\"请选择设备类型\""
                                 value="${serverList.itype}" list="{10:图像识别服务器}"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6" name="modelCode" id="modelCode" >
                <label class="layui-form-label">识别模型编号</label>
                <div class="layui-input-inline" style="width: 115px">
                    <input type="text" name="smodelCode" readonly="true" id="smodelCode"
                           value="${serverList.smodelCode}"
                           class="layui-input"/>
                </div>
                <button class="layui-btn" id="btn1" style="float: left;" type="button"
                        data-type="sel">选择
                </button>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">服务器参数配置</label>
            <div class="layui-input-block">
                    <textarea id="sconfigDetail" name="sconfigDetail" class="layui-textarea layui-form-textarea80p"
                              placeholder="（请输入服务器参数配置）">${serverList.sconfigDetail}</textarea>
            </div>
        </div>
        <div class="layui-form-item mt13">
            <label class="layui-form-label">备注</label>
            <div class="layui-input-block">
                    <textarea id="sremark" name="sremark" class="layui-textarea layui-form-textarea80p"
                              placeholder="（非必填项）">${serverList.sremark}</textarea>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                <button class="layui-btn" id="myFormSub">保存</button>
            </div>
        </div>
        <input type="hidden" id="id" name="id" value="${serverList.id}"/>
        <input type="hidden" id="scode" name="scode" value="${serverList.scode}"/>
    </form>
</div>


<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js" type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate'], function () {
        var form = layui.form;
        //监听提交
        $("#myForm").Validform({
            btnSubmit: "#myFormSub",  //根据id触发
            tiptype: 3,                  //第三种方式
            showAllError: true,
            beforeSubmit: function () { //验证过后执行save方法
                var smodelCode = $("#smodelCode").val();
                if(isEmpty(smodelCode)){
                    alertDel("请选择识别模型！");
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
            }
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
    function selectSupp(smodelCode) {
        $("#smodelCode").val(smodelCode);
    }
</script>
</body>
</html>

