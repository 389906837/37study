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
                <label class="layui-form-label"><spring:message code="sm.date.of.production" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="dproduceDate" datatype="*" nullmsg='<spring:message code="sp.commodityBatch.please.select.the.date.of.manufacture" />' id="dproduceDate"
                           value="<fmt:formatDate value='${commodityBatch.dproduceDate}' pattern="yyyy-MM-dd"/>" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityBatch.product.number" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="icommodityNum" datatype="n" nullmsg='<spring:message code="sp.commodityBatch.must.enter.the.integer.number" />' id="icommodityNum"
                           value="${commodityBatch.icommodityNum}"
                           class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityBatch.number.of.damage" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="ilossGoodsNum" datatype="n" nullmsg='<spring:message code="sp.commodityBatch.please.enter.the.amount.of.damage" />' id="ilossGoodsNum"
                           value="" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityBatch.cost.price" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="fcostAmount" datatype="*" nullmsg='<spring:message code="sp.commodityBatch.please.enter.the.cost.price" />' id="fcostAmount"
                           value="" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityBatch.tax.point" />（%）</label>
                <div class="layui-input-inline">
                    <input type="text" name="ftaxPoint" datatype="n1-2" nullmsg='<spring:message code="sp.commodityBatch.please.enter.a.tax.point" />' id="ftaxPoint" placeholder='<spring:message code="sp.commodityBatch.integer" />...'
                           value="" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
                <c:if test="${empty commodityBatch.id }">
                    <button class="layui-btn"  id="myFormSub"><spring:message code="main.save" /></button>
                </c:if>
            </div>
        </div>
        <div class="layui-form-item">
            <%--<spring:message code="sp.commodityBatch.hidden.attribute" />--%>
            <input type="hidden" id="id" name="id" value="${commodityBatch.id}" /><%--<spring:message code="sp.commodityBatch.batch.id" />--%>
            <input type="hidden" id="smerchantId" name="smerchantId" value="${merchantInfo.id}" /><%--<spring:message code="sp.commodityBatch.merchant.id" />--%>
            <input type="hidden" id="smerchantCode" name="smerchantCode" value="${merchantInfo.scode}" /><%--<spring:message code='main.merchant.number' />--%>
            <input type="hidden" id="scommodityId" name="scommodityId" value="${commodityInfo.id}" /><%--<spring:message code="sp.commodityBatch.product.id" />--%>
            <input type="hidden" id="scommodityCode" name="scommodityCode" value="${commodityInfo.scode}" /><%--<spring:message code='main.product.number' />--%>
        </div>
    </form>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
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
                        alertDelAndReload("<spring:message code='main.error.try.again' />");
                    },
                    success: function (msg) {
                        //成功返回
                        closeLayer(loadIndex);//关闭加载框
                        if (msg.success) {
                            alertSuccess("<spring:message code='main.success' />", {
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

