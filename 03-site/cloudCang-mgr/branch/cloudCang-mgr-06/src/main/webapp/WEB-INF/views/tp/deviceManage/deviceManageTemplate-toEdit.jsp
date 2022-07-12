<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>增加设备管理信息模板</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
</head>
<body>
<div class="ibox-content">
    <div class="layui-tab-content">
        <div class="layui-tab-item layui-show">
            <form class="layui-form" action="${ctx}/deviceManageTemplate/edit" method="post" id="myForm" >
                <input type="hidden" name="id" id="id" value="${deviceManageTemplate.id}" class="layui-input"/>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label">模板名称</label>
                        <div class="layui-input-inline">
                            <input type="text" name="sname" id="sname" datatype="*" nullmsg="请输入模板名称"
                                   value="${deviceManageTemplate.sname}" class="layui-input" placeholder="必填..."/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label">模板编号</label>
                        <div class="layui-input-inline">
                            <input type="text" name="scode" id="scode" value="${deviceManageTemplate.scode}"
                                   class="layui-input" readonly/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label">生产厂商</label>
                        <div class="layui-input-inline">
                            <input type="text" name="sareaPrincipal" id="sareaPrincipal" value="${deviceManageTemplate.sareaPrincipal}" class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label">联系方式</label>
                        <div class="layui-input-inline">
                            <input type="text" name="sareaPrincipalContact" id="sareaPrincipalContact" value="${deviceManageTemplate.sareaPrincipalContact}" class="layui-input"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label">设备负责人</label>
                        <div class="layui-input-inline">
                            <input type="text" name="sdevicePrincipal" id="sdevicePrincipal" value="${deviceManageTemplate.sdevicePrincipal}" class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label">联系方式</label>
                        <div class="layui-input-inline">
                            <input type="text" name="sdevicePrincipalContact" id="sdevicePrincipalContact" value="${deviceManageTemplate.sdevicePrincipalContact}" class="layui-input"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label">补货员</label>
                        <div class="layui-input-inline">
                            <input type="text" name="sreplenishment" id="sreplenishment" value="${deviceManageTemplate.sreplenishment}"
                                   class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label">联系方式</label>
                        <div class="layui-input-inline">
                            <input type="text" name="sreplenishmentContact" id="sreplenishmentContact" value="${deviceManageTemplate.sreplenishmentContact}" class="layui-input"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label">设备维护人员</label>
                        <div class="layui-input-inline">
                            <input type="text" name="smaintain" id="smaintain" value="${deviceManageTemplate.smaintain}" class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label">联系方式</label>
                        <div class="layui-input-inline">
                            <input type="text" name="smaintainContact" id="smaintainContact" value="${deviceManageTemplate.smaintainContact}" class="layui-input"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">备注</label>
                    <div class="layui-input-block">
                        <textarea id="sremark" name="sremark" class="layui-textarea layui-form-textarea80p"
                                  placeholder="备注（选填项）">${deviceManageTemplate.sremark}</textarea>
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
    </div>
</div>

<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js" type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var form;
    layui.use(['form', 'layedit', 'element'], function () {

        $('.layui-form .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

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

        $('.chosen-select').chosen();
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

