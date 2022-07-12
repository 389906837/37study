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
                <label class="layui-form-label layui-form-label100p" style="text-align: left;padding-left: 0px;">单据编号：${replenishment.sreplenishmentCode}</label></div>
            <div class="layui-col-md6">
                <label class="layui-form-label layui-form-label100p" style="text-align: right;padding-left: 0px;">生成时间：<fmt:formatDate value="${replenishment.tgenerateTime}" pattern="yyyy-MM-dd" /></label></div>
        </div>
        <div class="layui-form-item">
            <table class="layui-table">
                <colgroup>
                    <col width="40%">
                    <col width="60%">
                </colgroup>
                <tr style="text-align: center;height: 60px;line-height: 60px;">
                    <td colspan="2"><span style="text-align: center;font-size: 24px;font-weight: bold;">补货单</span></td>
                </tr>
                <tr style="height: 40px;line-height: 40px;">
                    <td>设备编号：${replenishment.sdeviceCode}</td>
                    <td>设备名称：${replenishment.sdeviceName}</td>
                </tr>
                <tr style="height: 40px;line-height: 40px;">
                    <td colspan="2">设备地址：${replenishment.sdeviceAddress}</td>
                </tr>
                <tr style="height: 40px;line-height: 40px;">
                    <td>补货员：${replenishment.srenewalName}</td>
                    <td>联系电话：${replenishment.srenewalMobile}</td>
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
                    <th>商品编号</th>
                    <th>商品名称</th>
                    <th>补货数量</th>
                    <th>销售价格</th>
                    <th>商品总额</th>
                    <th>备注（出库批次）</th>
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
                        <td>备注</td>
                        <td colspan="6">${replenishment.sremark}</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
    <div class="layui-form-item fixed-bottom">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
            <button class="layui-btn" id="printBtn">打印</button>
        </div>
    </div>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
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

