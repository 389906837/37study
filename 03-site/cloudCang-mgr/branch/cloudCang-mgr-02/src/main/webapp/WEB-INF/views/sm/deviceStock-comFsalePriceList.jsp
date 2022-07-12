<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
<title>修改商品单价</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/devicestock/updateComFsalePrice" method="post" id="myForm">
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">设备编号</label>
                <div class="layui-inline">
                    <input type="text" name="sdeviceCode" id="sdeviceCode" value="${deviceStock.sdeviceCode}" class="layui-input" disabled/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">设备名称</label>
                <div class="layui-inline">
                    <input type="text" name="sname" id="sname" value="${deviceInfo.sname}" class="layui-input" disabled/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">商品编号</label>
                <div class="layui-inline">
                    <input type="text" name="scommodityCode" id="scommodityCode" value="${deviceStock.scommodityCode}" class="layui-input" disabled/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">商品名称</label>
                <div class="layui-inline">
                    <input type="text" name="sname" id="spname" value="${scommodityFullName}" class="layui-input" disabled/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">修改单价</label>
                    <div class="layui-inline">
                        <input type="text" name="fsalePrice" id="fsalePrice" value="${deviceStock.fsalePrice}" class="layui-input" datatype="*" nullmsg="请输入金额#0.00"/>
                    </div>
            </div>
        </div>
        <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">修改库存</label>
                    <div class="layui-inline">
                        <input type="text" name="istock" id="istock" value="${deviceStock.istock}" class="layui-input" datatype="*" nullmsg="请输入库存"/>
                    </div>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn">关闭</button>
                <button class="layui-btn" id="myFormSub">提交</button>
            </div>
        </div>
        <input type="hidden" id="id" name="id" value="${deviceStock.id}" />
    </form>
</div>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js"  type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate'], function(){
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
                        alertDelAndReload("操作异常，请重新操作");
                    },
                    success: function (msg) {
                        //成功返回
                        closeLayer(loadIndex);//关闭加载框
                        if (msg.success) {
                            alertSuccess("操作成功", {
                                cancel: function () {
                                    parent.closeLayerAndRefresh(index);
                                }
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
    $(function() {
        $("#cancelBtn").click(function() {
            parent.layer.close(index);
            return false;
        });
    });
</script>
</body>
</html>

