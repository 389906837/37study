<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>动态补货单</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
</head>

<body>
<div class="ibox-content">
    <div id="printBody">
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label layui-form-label100p" style="text-align: left;padding-left: 0px;"><spring:message code='sm.document.number' />：${replenishment.sreplenishmentCode}</label></div>
            <div class="layui-col-md6">
                <label class="layui-form-label layui-form-label100p" style="text-align: right;padding-left: 0px;"><spring:message code='rm.generation.time' />：<fmt:formatDate value="${replenishment.tgenerateTime}" pattern="yyyy-MM-dd" /></label></div>
        </div>
        <div class="layui-form-item">
            <table class="layui-table">
                <colgroup>
                    <col width="40%">
                    <col width="60%">
                </colgroup>
                <tr style="text-align: center;height: 60px;line-height: 60px;">
                    <td colspan="2"><span style="text-align: center;font-size: 24px;font-weight: bold;"><spring:message code='rm.replenishment.order' /></span></td>
                </tr>
                <tr style="height: 40px;line-height: 40px;">
                    <td><spring:message code="main.device.id" />：${replenishment.sdeviceCode}</td>
                    <td><spring:message code="main.device.name" />：${replenishment.sdeviceName}</td>
                </tr>
                <tr style="height: 40px;line-height: 40px;">
                    <td colspan="2"><spring:message code="main.device.address" />：${replenishment.sdeviceAddress}</td>
                </tr>
                <tr style="height: 40px;line-height: 40px;">
                    <td><spring:message code='tpmanage.replenisher' />：${replenishment.srenewalName}</td>
                    <td><spring:message code='rm.contact.number' />：${replenishment.srenewalMobile}</td>
                </tr>
            </table>
            <table class="layui-table" style="margin-top:10px;">
                <colgroup>
                    <col width="15%">
                    <col width="25%">
                    <col width="14%">
                    <col width="14%">
                    <col width="14%">
                    <col width="18%">
                </colgroup>
                <thead>
                <tr>
                    <th><spring:message code="main.product.number" /></th>
                    <th><spring:message code="main.product.name" /></th>
                    <th><spring:message code='rm.replenishment.quantity' /></th>
                    <th><spring:message code='rm.selling.price' /></th>
                    <th><spring:message code='rm.total.merchandise' /></th>
                    <th><spring:message code="main.remarks" />（<spring:message code='sb.outbound.batch' />）</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach items="${replenishment.commodityResults}" var="item" varStatus="L">
                        <tr>
                            <td>${item.scommodityCode}</td>
                            <td>${item.scommodityName}</td>
                            <td>${item.forderCount}</td>
                            <td>${item.fcommodityPrice}</td>
                            <td>${item.fcommodityAmount}</td>
                            <td>${item.sremark}</td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td><spring:message code="main.remarks" /></td>
                        <td colspan="6">${replenishment.sremark}</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
    <div class="layui-form-item fixed-bottom">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
            <button class="layui-btn" id="printBtn"><spring:message code="rm.print" /></button>
        </div>
    </div>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
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

