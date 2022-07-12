<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>


<title>商品批次信息</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/commodity/commodityBatch/editBatch"
          method="post" id="myForm" enctype="multipart/form-data">
        <div class="layui-form-item">
            <%--隐藏属性--%>
            <input type="hidden" id="id" name="id" value="${commodityBatch.id}" /><%--批次ID--%>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">库存状态</label>
                <div class="layui-inline" style="width: 182px">
                    <select name="istockStatus" id="istockStatus" class="state" >
                        <%--<option value="">请选择</option>--%>
                        <option value="10" <c:if test="${10 == commodityBatch.istockStatus}">selected</c:if>>待上架</option>
                        <option value="20" <c:if test="${20 == commodityBatch.istockStatus}">selected</c:if>>部分上架</option>
                        <option value="30" <c:if test="${30 == commodityBatch.istockStatus}">selected</c:if>>全部上架</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">销售状态</label>
                <div class="layui-inline" style="width: 182px">
                    <select name="isaleStatus" id="isaleStatus" class="state" >
                        <option value="30" <c:if test="${30 == commodityBatch.isaleStatus}">selected</c:if>>待销售</option>
                        <option value="10" <c:if test="${10 == commodityBatch.isaleStatus}">selected</c:if>>销售中</option>
                        <option value="20" <c:if test="${20 == commodityBatch.isaleStatus}">selected</c:if>>售罄</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">运营状态</label>
                <div class="layui-inline" style="width: 182px">
                    <select name="istatus" id="istatus" class="state" >
                        <option value="10" <c:if test="${10 == commodityBatch.istatus}">selected</c:if>>启用</option>
                        <option value="20" <c:if test="${20 == commodityBatch.istatus}">selected</c:if>>禁用</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">货损数量</label>
                <div class="layui-inline" style="width: 183px">
                    <input type="text" name="ilossGoodsNum" id="ilossGoodsNum" value="${commodityBatch.ilossGoodsNum}" datatype="n" nullmsg="必须输入整形数字" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">备注</label>
                <div class="layui-block">
                    <textarea style="width: 470px" id="sremark" name="sremark" class="layui-textarea layui-form-textarea80p" placeholder="非必填项">${commodityBatch.sremark}</textarea>
                </div>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                <button class="layui-btn"  id="myFormSub">保存</button>
            </div>
        </div>
    </form>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js"  type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var form;
    layui.use(['form', 'layedit', 'laydate', 'upload'], function () {
        var laydate = layui.laydate;
        laydate.render({
            elem: '#dproduceDate',//指定元素
            type: 'datetime',
            done: function(value){ //监听日期被切换
                if(!isEmpty(value)) {
                    $("#dproduceDate").removeClass("Validform_error");
                    $("#dproduceDate").parent().find("span").hide();
                } else {
                    $("#dproduceDate").addClass("Validform_error");
                    $("#dproduceDate").parent().find("span").html($("#dproduceDate").attr("nullmsg"));
                    $("#dproduceDate").parent().find("span").removeClass("Validform_right");
                    $("#dproduceDate").parent().find("span").addClass("Validform_wrong");
                    $("#dproduceDate").parent().find("span").show();
                }
            }
        });

        form = layui.form;

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

