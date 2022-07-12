<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>动态补货单</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?1" rel="stylesheet">
</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/device/replenishment/generate" method="post" id="myForm">
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label layui-form-label100p" style="text-align: left;padding-left: 0px;">单据编号（仅供参考）：${replenishment.sreplenishmentCode}</label>
            </div>
        </div>
        <c:set var="commodityIds" value="" />
        <div class="layui-form-item">
            <table class="layui-table">
                <colgroup>
                    <col width="40%">
                    <col width="60%">
                </colgroup>
                <tr style="text-align: center;height: 60px;line-height: 60px;">
                    <td colspan="2"><span style="text-align: center;font-size: 24px;font-weight: bold;">计划补货单</span></td>
                </tr>
                <tr style="height: 40px;line-height: 40px;">
                    <td>设备编号：${replenishment.sdeviceCode}</td>
                    <td>设备名称：${replenishment.sdeviceName}</td>
                </tr>
                <tr style="height: 40px;line-height: 40px;">
                    <td colspan="2">设备地址：${replenishment.sdeviceAddress}</td>
                </tr>
                <tr style="height: 40px;line-height: 40px;">
                    <td>补货员：<input value="${replenishment.srenewalName}" name="srenewalName" id="srenewalName"/></td>
                    <td>联系电话：<input value="${replenishment.srenewalMobile}" name="srenewalMobile" id="srenewalMobile"/></td>
                </tr>
            </table>
            <table class="layui-table" style="margin-top:10px;">
                <colgroup>
                    <col width="15%">
                    <col width="25%">
                    <col width="8%">
                    <col width="12%">
                    <col width="12%">
                    <col width="20%">
                    <col width="8%">
                </colgroup>
                <thead>
                <tr>
                    <th>商品编号</th>
                    <th>商品名称</th>
                    <th>补货数量</th>
                    <th>销售价格</th>
                    <th>商品总额</th>
                    <th>备注(出库批次，仅供参考)</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="tbody_commodity">
                    <c:forEach items="${replenishment.commodityResults}" var="item" varStatus="L">
                        <c:set var="commodityIds" value="${commodityIds}${item.scommodityId}," />
                        <tr id="${item.scommodityId}">
                            <td>${item.scommodityCode}</td>
                            <td>${item.scommodityName}</td>
                            <td><input value="${item.forderCount}" name="count_${item.scommodityId}" onkeyup="changeValue('${item.scommodityId}');" id="count_${item.scommodityId}" /></td>
                            <td id="price_${item.scommodityId}">${item.fcommodityPrice}</td>
                            <td id="amount_${item.scommodityId}"><fmt:formatNumber value="${item.fcommodityAmount}" pattern=",##0.00" /> </td>
                            <td><input value="${item.sremark}" name="sremark_${item.scommodityId}" id="sremark_${item.scommodityId}" /></td>
                            <td><a href="javascript:void(0);" onclick="deleteTr('${item.scommodityId}')">移除</a></td>
                        </tr>
                    </c:forEach>
                </tbody>
                <tr>
                    <td colspan="7"><input class="choose-btn" style="float:left;margin-bottom: 0px;" type="button" onclick="selectCommodity();" value="添加商品" /></td>
                </tr>
                <tr>
                    <td>备注</td>
                    <td colspan="6"><textarea class="layui-textarea" id="sremark" name="sremark"></textarea></td>
                </tr>
            </table>
        </div>
        <input id="commodityIds" name="commodityIds" value="${commodityIds}" type="hidden" />
        <input id="deviceId" name="deviceId" value="${replenishment.sdeviceId}" type="hidden" />
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                <button class="layui-btn" lay-submit="" lay-filter="myFormSub">保存</button>
            </div>
        </div>
    </form>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/formatUtil.js?1"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    $(function () {
        $("#cancelBtn").click(function () {
            parent.layer.close(index);
            return false;
        });
    });
    function deleteTr(commodityId) {
        confirmTip("确定删除此商品?",function () {
            $("#"+commodityId).remove();
            var commodityIds = $("#commodityIds").val();//获取当前商品集合
            $("#commodityIds").val(commodityIds.replace(commodityId+",",""));//删除当前商品
        });
    }
    function changeValue(commodityId) {
        var count = $("#count_"+commodityId).val();
        count = count.replace(/\D/g,'');
        $("#count_"+commodityId).val(count);
        var price = $("#price_"+commodityId).html();
        $("#amount_"+commodityId).html(formateMoney(accMul(Number(count),Number(price))));
    }
    layui.use('form', function () {
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
                            'index':index
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
    function selectCommodity() {
        var commodityIds = $("#commodityIds").val();
        if (!isEmpty(commodityIds)) {
            showLayerMax("选择商品信息",ctx+"/common/selectCommodityByMap?commodityIds="+commodityIds);
        } else {
            showLayerMax("选择商品信息",ctx+"/common/selectCommodityByMap");
        }
    }

    function confirmSelectCommodity(commodityIds, commodityMap,deleteCommodityIds) {
        $("#commodityIds").val(commodityIds);

        if(!isEmpty(commodityIds)) {
            var tempIds = commodityIds.substr(0,commodityIds.length-1);
            var arr = tempIds.split(",");
            var html = "";
            for (var i=0;i<arr.length;i++) {
                if (isEmpty($("#"+arr[i]).attr("id"))) {
                    var tempData = commodityMap[arr[i]];
                    var tempAmount = formateMoney(accMul(Number(tempData.fsalePrice),Number(1)));
                    html += '<tr id="'+arr[i]+'">'+
                        '<td>'+tempData.scode+'</td>'+
                        '<td>'+tempData.commodityFullName+'</td>'+
                        '<td><input type="text" name="count_'+arr[i]+'" id="count_'+arr[i]+'" onkeyup="changeValue(\''+arr[i]+'\');" value="1" /></td>'+
                        '<td id="price_'+arr[i]+'">'+tempData.fsalePrice+'</td>'+
                        '<td id="amount_'+arr[i]+'">'+tempAmount+'</td>'+
                        '<td><input value="" name="sremark_'+arr[i]+'" id="sremark_'+arr[i]+'" /></td>'+
                        '<td><a href="javascript:void(0);" onclick="deleteTr(\''+arr[i]+'\');">移除</a></td>'+
                        '</tr>';
                }
            }
            if (!isEmpty(html)) {
                $("#tbody_commodity").append(html);
            }
            //删除原有没选择的
            if(!isEmpty(deleteCommodityIds)) {
                tempIds = commodityIds.substr(0,commodityIds.length-1);
                arr = tempIds.split(",");
                for (var i=0;i<arr.length;i++) {
                    $("#"+arr[i]).remove();
                    var commodityIds = $("#commodityIds").val();//获取当前商品集合
                    $("#commodityIds").val(commodityIds.replace(arr[i]+",",""));//删除当前商品
                }
            }
        } else {
            $("#tbody_commodity").html("");//清楚表格body
        }
    }
</script>
</body>
</html>

