<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title><spring:message code='om.order.details' /></title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" id="myForm">
        <c:forEach items="${orderCommodityList}" var="orderCommodity">
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="main.order.number" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="sorderCode" id="sorderCode" value="${orderCommodity.sorderCode}"
                               class="layui-input"/>

                    </div>
                </div>

            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="main.product.number" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="scommodityCode" id="scommodityCode"
                               value="${orderCommodity.scommodityCode}"
                               class="layui-input"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="main.product.name" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="scommodityName" id="scommodityName"
                               value="${orderCommodity.scommodityName}"
                               class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code='sm.item.pricing' /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="fcommodityPrice" id="fcommodityPrice"
                               value="${orderCommodity.fcommodityPrice}"
                               class="layui-input"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code='om.quantity.of.order' /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="forderCount" id="forderCount" value="${orderCommodity.forderCount}"
                               class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code='rm.total.merchandise' /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="fcommodityAmount" id="fcommodityAmount"
                               value="${orderCommodity.fcommodityAmount}"
                               class="layui-input"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code='om.total.order.amount' /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="fdiscountAmount" id="fdiscountAmount"
                               value="${orderCommodity.fdiscountAmount}"
                               class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code='om.order.first.amount' /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="ffirstDiscountAmount" id="ffirstDiscountAmount"
                               value="${orderCommodity.ffirstDiscountAmount}" class="layui-input"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code='om.order.promotion.amount' /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="fpromotionDiscountAmount" id="fpromotionDiscountAmount"
                               value="${orderCommodity.fpromotionDiscountAmount}" class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code='om.credit.limit' /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="ipoint" id="ipoint" value="${orderCommodity.ipoint}"
                               class="layui-input"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code='om.the.amount.actually.paid' /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="factualAmount" id="factualAmount"
                               value="${orderCommodity.factualAmount}"
                               class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code='spcon.commodity.status' /></label>
                    <div class="layui-input-inline">
                        <cang:select type="list" name="icommodityStatus" id="icommodityStatus" entire="true"
                                     value="${orderCommodity.icommodityStatus}"
                                     list="{10:springMessageCode=om.invalid.sale.of.goods,20:springMessageCode=om.normally.sold,30:springMessageCode=sm.expired.and.sold}"/>
                    </div>
                </div>
            </div>
            <span class="splitLine">

                 <hr>
        </span>
        </c:forEach>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.close" /></button>
            </div>
        </div>
        <%--
                    <input type="hidden" id="id" name="id" value="${menu.id}" />
        --%>
    </form>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    $(function () {
        $("input").attr("disabled", true),
            $("select").attr("disabled", true);

    });

    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate'], function () {
        var form = layui.form;
        //监听提交
        form.on('submit(myFormSub)', function () {
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

