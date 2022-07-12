<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>


<title>商品批次信息</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/commodity/commodityBatch/addBatch"
          method="post" id="myForm" enctype="multipart/form-data">
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">生产日期</label>
                <div class="layui-input-inline">
                    <input type="text" name="dproduceDate" datatype="*" nullmsg="请选择生产日期" id="dproduceDate"
                           value="<fmt:formatDate value='${commodityBatch.dproduceDate}' pattern="yyyy-MM-dd"/>" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">商品数量</label>
                <div class="layui-input-inline">
                    <input type="text" name="icommodityNum" datatype="n" nullmsg="必须输入整形数字" id="icommodityNum"
                           value="${commodityBatch.icommodityNum}"
                           class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">货损数量</label>
                <div class="layui-input-inline">
                    <input type="text" name="ilossGoodsNum" datatype="n" nullmsg="请输入货损数量" id="ilossGoodsNum"
                           value="" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">成本价</label>
                <div class="layui-input-inline">
                    <input type="text" name="fcostAmount" datatype="*" nullmsg="请输入成本价" id="fcostAmount"
                           value="" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">税点（%）</label>
                <div class="layui-input-inline">
                    <input type="text" name="ftaxPoint" datatype="n1-2" nullmsg="请输入税点" id="ftaxPoint" placeholder="0-99整数..."
                           value="" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                <c:if test="${empty commodityBatch.id }">
                    <button class="layui-btn"  id="myFormSub">保存</button>
                </c:if>
            </div>
        </div>
        <div class="layui-form-item">
            <%--隐藏属性--%>
            <input type="hidden" id="id" name="id" value="${commodityBatch.id}" /><%--批次ID--%>
            <input type="hidden" id="smerchantId" name="smerchantId" value="${merchantInfo.id}" /><%--商户ID--%>
            <input type="hidden" id="smerchantCode" name="smerchantCode" value="${merchantInfo.scode}" /><%--商户编号--%>
            <input type="hidden" id="scommodityId" name="scommodityId" value="${commodityInfo.id}" /><%--商品ID--%>
            <input type="hidden" id="scommodityCode" name="scommodityCode" value="${commodityInfo.scode}" /><%--商品编号--%>
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
        //执行一个laydate实例
        var laydate = layui.laydate;
        laydate.render({
            elem: '#dproduceDate',//指定元素
            type: 'date',
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

