<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title><spring:message code="menu.replenishment.center" /></title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?20190731" rel="stylesheet">
</head>
<body>
<div class="ibox-content">
    <div id="printBody">
        <div class="layui-form">
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label layui-form-label100p" style="text-align: left;padding-left: 0px;"><spring:message code="rm.planned.replenishment.order.number" />：${replenishmentPlan.scode}</label>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label layui-form-label100p" style="text-align: right;padding-right: 0px;"><spring:message code="rm.generation.time" />：<fmt:formatDate
                            value="${replenishmentPlan.tgenerateTime}" pattern="yyyy-MM-dd"/></label>
                </div>
            </div>
            <div class="layui-form-item">
                <table class="layui-table">
                    <colgroup>
                        <col width="40%">
                        <col width="60%">
                    </colgroup>
                    <tr style="text-align: center;height: 60px;line-height: 60px;">
                        <td colspan="2"><span style="text-align: center;font-size: 24px;font-weight: bold;"><spring:message code="rm.planned.replenishment.list" /></span>
                        </td>
                    </tr>
                    <tr style="height: 40px;line-height: 40px;">
                        <td><spring:message code="main.device.id" />：${replenishmentPlan.sdeviceCode}</td>
                        <td><spring:message code="main.device.name" />：${deviceInfo.sname}</td>
                    </tr>
                    <tr style="height: 40px;line-height: 40px;">
                        <td colspan="2"><spring:message code="main.device.address" />：${replenishmentPlan.sdeviceAddress}</td>
                    </tr>
                    <tr style="height: 40px;line-height: 40px;">
                        <td><spring:message code="rm.replenisher" />：${replenishmentPlan.srenewalName}</td>
                        <td><spring:message code="rm.contact.number" />：${replenishmentPlan.srenewalMobile}</td>
                    </tr>
                    <tr>
                        <td><spring:message code="rm.replenishment.status" />：
                            <c:if test="${replenishmentPlan.istatus == 10 }"><spring:message code="rm.to.be.delivered" /></c:if>
                            <c:if test="${replenishmentPlan.istatus == 20 }"><spring:message code="rm.distribution" /></c:if>
                            <c:if test="${replenishmentPlan.istatus == 30 }"><spring:message code="rm.completed" /></c:if>
                            <c:if test="${replenishmentPlan.istatus == 40 }"><spring:message code="rm.cancellation.of.distribution" /></c:if>
                        </td>
                        <td><spring:message code="main.remarks" />：${replenishmentPlan.sremark}</td>
                    </tr>
                </table>
            </div>
            <div class="wfsplitt">
                <p><spring:message code="rm.details.of.replenishment.commodities" /></p>
            </div>
            <table class="layui-table">
                <colgroup>
                    <col width="100">
                    <col width="150">
                    <col width="100">
                    <col width="100">
                    <col width="100">
                    <col width="100">
                    <col width="100">
                </colgroup>
                <thead>
                <tr>
                    <th><spring:message code="main.product.number" /></th>
                    <th><spring:message code="main.product.name" /></th>
                    <th><spring:message code="rm.unit.price.of.commodity.sale" /></th>
                    <th><spring:message code="rm.replenishment.quantity" /></th>
                    <th><spring:message code="rm.total.merchandise" /></th>
                    <th><spring:message code="main.add.time" /></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${list}" var="item" varStatus="L">
                    <tr>
                        <td>${item.scommodityCode}</td>
                        <td>${item.spsname}</td>
                        <td>${item.fcommodityPrice}</td>
                        <td>${item.forderCount}</td>
                        <td>${item.fcommodityAmount}</td>
                        <td>
                            <fmt:formatDate value="${item.taddTime}" pattern="yyyy/MM/dd  HH:mm:ss"/>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="layui-form-item fixed-bottom">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.close" /></button>
                    <button class="layui-btn" id="printBtn"><spring:message code="rm.print" /></button>
                </div>
            </div>
            <input type="hidden" id="id" name="id" value="${replenishmentPlan.id}"/>
            </form>
        </div>
    </div>
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

        $("#printBtn").click(function () {
            printdiv('printBody');
            window.location.reload();
        });
    });

    /**
     * 打印局部div
     * @param printpage 局部div的ID
     */
    function printdiv(printpage) {
        var headhtml = "<html><head><title></title></head><body>";
        var foothtml = "</body>";
        // 获取div中的html内容
        var newhtml = document.all.item(printpage).innerHTML;
        // 获取div中的html内容，jquery写法如下
        // var newhtml= $("#" + printpage).html();

        // 获取原来的窗口界面body的html内容，并保存起来
        var oldhtml = document.body.innerHTML;

        // 给窗口界面重新赋值，赋自己拼接起来的html内容
        document.body.innerHTML = headhtml + newhtml + foothtml;
        // 调用window.print方法打印新窗口
        window.print();

        // 将原来窗口body的html值回填展示
        document.body.innerHTML = oldhtml;
        return false;
    }
</script>
</body>
</html>

