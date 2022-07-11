<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>补货成功</title>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="${staticSource}/static/css/comm.css"/>
    <link rel="stylesheet" href="${staticSource}/static/css/index.css?1.1"/>
</head>
<body>
<div class="open_container">
    <div class="door_success">
        <div class="success">
            <div></div>
        </div>
        <p class="door_title">已关门！请确认补货信息!</p>
    </div>
    <div class="door_main">
        <ul>
            <li>补货单编号：${recordCode}</li>
            <li>商户名称：${merchantName}</li>
            <li>设备名称：${deviceName}</li>
            <li>设备地址：${deviceAddress}</li>
            <li>补货员名称：${srealName} <a href="javascript:void(0);" onclick="myReplenishmentOrder();">我的历史补货单</a></li>
            <li>补货开始时间：${openTime}</li>
            <li>补货结束时间：${closeTime}</li>
            <li>本次实际上架数量：${empty addSize ? 0 : addSize } <c:if test="${(empty addSize ? 0 : addSize)>0 }"><a
                    class="sjtk" id="add-shelf-details" href="javascript:void(0);">详细信息</a></c:if></li>
            <li>本次实际下架数量：${empty subSize ? 0 : subSize } <c:if test="${(empty subSize ? 0 : subSize)>0 }"><a
                    class="xjtk" id="sub-shelf-details" href="javascript:void(0);">详细信息</a></c:if></li>
            <li>是否完成计划补货单：<a class="doorbtn" href="javascript:void(0);" onclick="markingDone('${deviceCode}');">确定</a>
            </li>
        </ul>
    </div>
</div>

<!-- 本次上架明细弹框 -->
<div class="popup_bg close" id="addShelf">
    <div class="shelfdetails md">
        <p class="shelfdetails_name">本次上架明细</p>
        <div class="shelfdetails_title">
            <table class="table">
                <thead>
                <tr>
                    <th>商品编号</th>
                    <th>名称</th>
                    <th>上架数量</th>
                    <th>当前库存</th>
                </tr>
                </thead>
            </table>
        </div>
        <div class="shelfdetails_list">
            <table class="table">
                <tbody class="table-bordered">
                <c:forEach var="item" items="${addList}" varStatus="L">
                    <tr>
                        <td>${item.scommodityCode}</td>
                        <td>
                            <div>${item.scommodityName}</div>
                        </td>
                        <td>${item.forderCount}</td>
                        <td>${item.istock}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<div class="popup_bg close" id="subShelf">
    <div class="shelfdetails md">
        <p class="shelfdetails_name">本次下架明细</p>
        <div class="shelfdetails_title">
            <table class="table">
                <thead>
                <tr>
                    <th>商品编号</th>
                    <th>名称</th>
                    <th>下架数量</th>
                    <th>当前库存</th>
                </tr>
                </thead>
            </table>
        </div>
        <div class="subdetails_list">
            <table class="table">
                <tbody class="table-bordered">
                <c:forEach var="item" items="${subList}" varStatus="L">
                    <tr>
                        <td>${item.scommodityCode}</td>
                        <td>
                            <div>${item.scommodityName}</div>
                        </td>
                        <td>${item.forderCount}</td>
                        <td>${item.istock}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/comm.js?236"></script>
<script type="text/javascript" src="${staticSource}/static/js/util.js"></script>
<script type="text/javascript">
    $(function () {
        $('#sub-shelf-details').on('click', function () {
            $('#subShelf').removeClass('close');
            $('.subdetails_list').scrollTop(0);
        });
        $('#add-shelf-details').on('click', function () {
            $('#addShelf').removeClass('close');
            $('.shelfdetails_list').scrollTop(0);
        });
        $('.popup_bg').on('click', function () {
            $('.popup_bg').addClass('close');
        });
    });
    function markingDone(deviceCode) {
        Util.Waiting();
        $.ajax({
            type: "post",
            data: {'deviceCode': deviceCode},
            url: ctxRoot + "/replenishment/markingDone",
            dataType: "json",
            success: function (msg) {
                Util.waitingClose($("#wait_popup"), function () {
                    if (msg.success) {
                        Util.Alert(msg.msg, {"url": ctxRoot + '/replenishment/index.html'});
                        /* $(".markingDone").hide();*/
                        // jumpReplenishmentIndex();
                    } else {
                        Util.Alert(msg.msg);
                    }
                }, 1000);
            }
        });
    }
    <%@ include file="/common/include/baidu.jsp"%>
</script>
</body>
</html>