<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>增加设备网络摄像头信息</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/cameraConfig/add"
          method="post" id="myForm" enctype="multipart/form-data">
        <input type="hidden" id="sdeviceId" name="sdeviceId" value="${sdeviceId}"/><%--设备ID--%>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">IP</label>
                <div class="layui-input-inline">
                    <input type="text" name="sip" datatype="*" nullmsg="请输入IP" id="sip" value="" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">端口号</label>
                <div class="layui-input-inline">
                    <input type="text" name="sport" datatype="*" nullmsg="请输入端口号" id="sport" value=""
                           class="layui-input"/>
                </div>
            </div>
        </div>

        <div class="layui-form-item m-t">
            <div class="layui-col-md6">
                <label class="layui-form-label">序列号</label>
                <div class="layui-input-inline">
                    <input type="text" name="sserialNumber" datatype="*" nullmsg="请输入序列号" id="sserialNumber" value=""
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">称重编号</label>
                <div class="layui-input-inline">
                    <input type="text" name="sweightCode" id="sweightCode" value=""
                           class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item m-t">
            <div class="layui-col-md6">
                <label class="layui-form-label">从机地址</label>
                <div class="layui-input-inline">
                    <input type="text" name="slaveAddress" id="slaveAddress" value=""
                           class="layui-input"/>
                </div>
            </div>

        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">备注</label>
                <div class="layui-block">
                    <textarea style="width: 470px" id="sremark" name="sremark"
                              class="layui-textarea layui-form-textarea80p" placeholder="非必填项"></textarea>
                </div>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                <button class="layui-btn" id="myFormSub">保存</button>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js" type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var form;
    layui.use(['form', 'layedit', 'layer'], function () {
        form = layui.form;
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

