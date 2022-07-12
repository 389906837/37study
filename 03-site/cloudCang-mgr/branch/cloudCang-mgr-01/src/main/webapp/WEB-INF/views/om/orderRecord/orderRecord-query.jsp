<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>订单详情</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" id="myForm">
        <c:forEach items="${orderCommodityList}" var="orderCommodity">
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">订单编号</label>
                    <div class="layui-input-inline">
                        <input type="text" name="sorderCode" id="sorderCode" value="${orderCommodity.sorderCode}"
                               class="layui-input"/>

                    </div>
                </div>

            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">商品编号</label>
                    <div class="layui-input-inline">
                        <input type="text" name="scommodityCode" id="scommodityCode"
                               value="${orderCommodity.scommodityCode}"
                               class="layui-input"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label">商品名称</label>
                    <div class="layui-input-inline">
                        <input type="text" name="scommodityName" id="scommodityName"
                               value="${orderCommodity.scommodityName}"
                               class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">商品单价</label>
                    <div class="layui-input-inline">
                        <input type="text" name="fcommodityPrice" id="fcommodityPrice"
                               value="${orderCommodity.fcommodityPrice}"
                               class="layui-input"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label">订单数量</label>
                    <div class="layui-input-inline">
                        <input type="text" name="forderCount" id="forderCount" value="${orderCommodity.forderCount}"
                               class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">商品总额</label>
                    <div class="layui-input-inline">
                        <input type="text" name="fcommodityAmount" id="fcommodityAmount"
                               value="${orderCommodity.fcommodityAmount}"
                               class="layui-input"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label">订单优惠总额</label>
                    <div class="layui-input-inline">
                        <input type="text" name="fdiscountAmount" id="fdiscountAmount"
                               value="${orderCommodity.fdiscountAmount}"
                               class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">订单首单优惠金额</label>
                    <div class="layui-input-inline">
                        <input type="text" name="ffirstDiscountAmount" id="ffirstDiscountAmount"
                               value="${orderCommodity.ffirstDiscountAmount}" class="layui-input"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label">订单促销优惠金额</label>
                    <div class="layui-input-inline">
                        <input type="text" name="fpromotionDiscountAmount" id="fpromotionDiscountAmount"
                               value="${orderCommodity.fpromotionDiscountAmount}" class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">抵扣积分</label>
                    <div class="layui-input-inline">
                        <input type="text" name="ipoint" id="ipoint" value="${orderCommodity.ipoint}"
                               class="layui-input"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label">实付金额</label>
                    <div class="layui-input-inline">
                        <input type="text" name="factualAmount" id="factualAmount"
                               value="${orderCommodity.factualAmount}"
                               class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">商品状态</label>
                    <div class="layui-input-inline">
                        <cang:select type="list" name="icommodityStatus" id="icommodityStatus" entire="true"
                                     value="${orderCommodity.icommodityStatus}"
                                     list="{10:商品无效售出,20:正常售出,30:已过期且售出}"/>
                    </div>
                </div>
            </div>
            <span class="splitLine">

                 <hr>
        </span>
        </c:forEach>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn">关闭</button>
            </div>
        </div>
        <%--
                    <input type="hidden" id="id" name="id" value="${menu.id}" />
        --%>
    </form>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
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

