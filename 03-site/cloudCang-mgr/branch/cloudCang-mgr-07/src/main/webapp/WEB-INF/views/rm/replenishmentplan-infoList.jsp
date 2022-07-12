<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>补货中心</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
</head>
<body>
<div class="ibox-content">
    <div id="printBody">
        <div class="layui-form">
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label layui-form-label100p" style="text-align: left;padding-left: 0px;">计划补货单编号：${replenishmentPlan.scode}</label>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label layui-form-label100p" style="text-align: right;padding-right: 0px;">生成时间：<fmt:formatDate
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
                        <td colspan="2"><span style="text-align: center;font-size: 24px;font-weight: bold;">计划补货单</span>
                        </td>
                    </tr>
                    <tr style="height: 40px;line-height: 40px;">
                        <td>设备编号：${replenishmentPlan.sdeviceCode}</td>
                        <td>设备名称：${deviceInfo.sname}</td>
                    </tr>
                    <tr style="height: 40px;line-height: 40px;">
                        <td colspan="2">设备地址：${replenishmentPlan.sdeviceAddress}</td>
                    </tr>
                    <tr style="height: 40px;line-height: 40px;">
                        <td>补货员：${replenishmentPlan.srenewalName}</td>
                        <td>联系电话：${replenishmentPlan.srenewalMobile}</td>
                    </tr>
                    <tr>
                        <td>补货状态：
                            <c:if test="${replenishmentPlan.istatus == 10 }">待配货</c:if>
                            <c:if test="${replenishmentPlan.istatus == 20 }">配送中</c:if>
                            <c:if test="${replenishmentPlan.istatus == 30 }">已完成</c:if>
                            <c:if test="${replenishmentPlan.istatus == 40 }">取消配货</c:if>
                        </td>
                        <td>备注：${replenishmentPlan.sremark}</td>
                    </tr>
                </table>
            </div>
            <div class="wfsplitt">
                <p>补货商品明细</p>
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
                    <th>商品编号</th>
                    <th>商品名称</th>
                    <th>商品销售单价</th>
                    <th>补货数量</th>
                    <th>商品总额</th>
                    <th>添加时间</th>
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
                    <button class="layui-btn layui-btn-primary" id="cancelBtn">关闭</button>
                    <button class="layui-btn" id="printBtn">打印</button>
                </div>
            </div>
            <input type="hidden" id="id" name="id" value="${replenishmentPlan.id}"/>
            </form>
        </div>
    </div>
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

