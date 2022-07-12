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
            <%--<spring:message code="sp.commodityBatch.hidden.attribute" />--%>
            <input type="hidden" id="id" name="id" value="${commodityBatch.id}" /><%--<spring:message code="sp.commodityBatch.batch.id" />--%>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityBatch.inventory.status" /></label>
                <div class="layui-inline" style="width: 182px">
                    <select name="istockStatus" id="istockStatus" class="state" >
                        <%--<option value=""><spring:message code="main.please.choose" /></option>--%>
                        <option value="10" <c:if test="${10 == commodityBatch.istockStatus}">selected</c:if>><spring:message code="sp.commodityBatch.waiting.to.be.put.on.the.shelves" /></option>
                        <option value="20" <c:if test="${20 == commodityBatch.istockStatus}">selected</c:if>><spring:message code="sp.commodityBatch.partly.on.the.shelf" /></option>
                        <option value="30" <c:if test="${30 == commodityBatch.istockStatus}">selected</c:if>><spring:message code="sp.commodityBatch.all.shelves" /></option>
                    </select>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityBatch.sales.status" /></label>
                <div class="layui-inline" style="width: 182px">
                    <select name="isaleStatus" id="isaleStatus" class="state" >
                        <option value="30" <c:if test="${30 == commodityBatch.isaleStatus}">selected</c:if>><spring:message code="sp.commodityBatch.pending.sales" /></option>
                        <option value="10" <c:if test="${10 == commodityBatch.isaleStatus}">selected</c:if>><spring:message code="sp.commodityBatch.sale" /></option>
                        <option value="20" <c:if test="${20 == commodityBatch.isaleStatus}">selected</c:if>><spring:message code="sp.commodityBatch.sold.out" /></option>
                    </select>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityBatch.operation.state" /></label>
                <div class="layui-inline" style="width: 182px">
                    <select name="istatus" id="istatus" class="state" >
                        <option value="10" <c:if test="${10 == commodityBatch.istatus}">selected</c:if>><spring:message code="main.enable" /></option>
                        <option value="20" <c:if test="${20 == commodityBatch.istatus}">selected</c:if>><spring:message code='main.disable' /></option>
                    </select>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.commodityBatch.number.of.damage" /></label>
                <div class="layui-inline" style="width: 183px">
                    <input type="text" name="ilossGoodsNum" id="ilossGoodsNum" value="${commodityBatch.ilossGoodsNum}" datatype="n" nullmsg='<spring:message code="sp.commodityBatch.must.enter.the.integer.number" />' class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="main.remarks" /></label>
                <div class="layui-block">
                    <textarea style="width: 470px" id="sremark" name="sremark" class="layui-textarea layui-form-textarea80p" placeholder='<spring:message code="sp.category.not.required" />'>${commodityBatch.sremark}</textarea>
                </div>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
                <button class="layui-btn"  id="myFormSub"><spring:message code="main.save" /></button>
            </div>
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

