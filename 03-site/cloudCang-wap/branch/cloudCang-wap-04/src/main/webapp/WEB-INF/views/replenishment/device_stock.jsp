<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>设备库存</title>
    <link rel="stylesheet" href="${staticSource}/static/css/index.css?1.0.1">
</head>
<body>
<%@ include file="/common/include/commonHeader.jsp" %>
<div class="inventory_container">
    <div class="inventory_head md">
        <p>商户名称：${merchantInfo.sname}</p>
        <p>设备编号：${deviceInfo.scode}</p>
        <p>设备地址：${address}</p>
    </div>
    <div class="inventory_list">
        <table class="table">
            <thead>
            <tr>
                <th>商品名称</th>
                <th>单价（元）</th>
                <th>当前库存</th>
            </tr>
            </thead>
            <tbody class="table-bordered" id="deviceStockData">
            </tbody>
        </table>
    </div>
    <script id="deviceStock_tmpl" type="text/x-jsrender">
            {{for #data}}
                <tr>
                    <td><div><p>{{:scommodityFullName}}</p></div></td>
                    <td>{{:fsalePrice}}</td>
                    <td>{{:istock}}</td>
                </tr>
            {{/for}}



    </script>
</div>
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/comm.js?236"></script>
<script src="${staticSource}/static/js/jquery.form.js" type="text/javascript"></script>
<script src="${staticSource}/static/js/jsrender.min.js" type="text/javascript"></script>
<script src="${staticSource}/static/js/jsrender.converters.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        $.ajax({
            type: "POST",
            dataType: "json",
            url: ctxRoot + '/personal/myDeviceStock',
            async: true,
            data: {'deviceCode': "${deviceInfo.scode}"},
            success: function (msg) {
                //成功返回
                if (msg.success) {
                    var data = msg.data.deviceStockVos;
                    if (null != data && "" != data && data.length > 0) {
                        //渲染数据
                        var html = $("#deviceStock_tmpl").render(data);
                        $("#deviceStockData").html(html);
                        $("#deviceStockData").append('<tr><td colspan="3"><div class="inventory_sum" id="inventorySum">当前库存总计：' + msg.data.deviceStockAmount + '</div></td></tr>');
                        /*   if (data.length > 0) {
                               $("#deviceStockData").append('<tr><td colspan="3"><div class="inventory_sum" id="inventorySum">当前库存总计：' + msg.data.deviceStockAmount + '</div></td></tr>');
                           } else {
                               $("#deviceStockData").append(' <tr> <td colspan="3"><div class="norecord">暂无记录</div></td></tr>');
                               $("#deviceStockData").append('<tr><td colspan="3"><div class="inventory_sum" id="inventorySum">当前库存总计：0</div></td></tr>');
                           }*/
                    } else {
                        $("#deviceStockData").append(' <tr> <td colspan="3"><div class="norecord">暂无记录</div></td></tr>');
                        $("#deviceStockData").append('<tr><td colspan="3"><div class="inventory_sum" id="inventorySum">当前库存总计：0</div></td></tr>');
                    }
                }
            }
        });
    });
    <%@ include file="/common/include/baidu.jsp"%>
</script>
</body>
</html>