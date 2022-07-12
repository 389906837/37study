<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>补货成功</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="${staticSource}/static/css/comm.css">
    <link rel="stylesheet" href="${staticSource}/static/css/style.css?21.0">
</head>
<body>
<%@ include file="/common/include/commonHeader.jsp" %>
<div class="container">
    <div class="container-c">
        <div class="buy-success md">
            <img class="buy-successicon" src="${staticSource}/static/images/buy-successicon.png"/>
            <h4>已关门！请确认补货信息!</h4>
        </div>
        <img class="round-head m-t0" src="${staticSource}/static/images/round-head.png">
        <div class="kaimen-bg md" style="padding-bottom:10.5rem;">
            <p class="opensuccess">补货单编号：${recordCode}</p>
            <p class="opensuccess">商户名称：${merchantName}</p>
            <p class="opensuccess">设备编号：${deviceCode}</p>
            <p class="opensuccess">设备名称：${deviceName}</p>
            <p class="opensuccess">设备地址：${deviceAddress}</p>
            <p class="opensuccess">补货员姓名：${srealName} <a href="javascript:void(0);" onclick="myReplenishmentOrder();">我的历史补货单</a>
            </p>
            <p class="opensuccess">本次实际上架数量：${empty addSize ? 0 : addSize } <c:if
                    test="${(empty addSize ? 0 : addSize)>0 }"><a id="add-shelf-details"
                                                                  href="javascript:void(0);">详细信息</a></c:if></p>
            <p class="opensuccess">本次实际下架数量：${empty subSize ? 0 : subSize } <c:if
                    test="${(empty subSize ? 0 : subSize)>0 }"><a id="sub-shelf-details"
                                                                  href="javascript:void(0);">详细信息</a></c:if></p>
            <p class="opensuccess markingDone">是否完成计划补货单 <a class="btn" href="javascript:void(0);"
                                                            onclick="markingDone('${deviceCode}');">标记完成</a></p>
            <p class="opensuccess opensuccess-c1">补货开始时间：${openTime}</p>
            <p class="opensuccess opensuccess-c1">补货结束时间：${closeTime}</p>
        </div>
    </div>
    <%@ include file="/common/include/foot.jsp" %>
</div>
<!-- 本次上架明细 -->
<div class="eject"></div>
<c:if test="${fn:length(addList) > 0}">
    <div class="popup-bg popup-bg-add" style="width: 100%;">
        <div class="shelf-details md">
            <p class="kaimen-tip" style="padding-top: 1.5rem">本次上架明细</p>
            <div class="tab-top">
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
            <div class="tab">
                <table class="table">
                    <tbody class="table-bordered">
                    <c:forEach var="item" items="${addList}" varStatus="L">
                        <tr>
                            <td>${item.scommodityCode}</td>
                            <td>${item.scommodityName}</td>
                            <td>${item.forderCount}</td>
                            <td>${item.istock}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</c:if>
<c:if test="${fn:length(subList) > 0}">
    <div class="popup-bg popup-bg-sub" style="width: 100%;">
        <div class="shelf-details md">
            <p class="kaimen-tip" style="padding-top: 1.5rem">本次下架明细</p>
            <div class="tab-top">
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
            <div class="tab">
                <table class="table">
                    <tbody class="table-bordered">
                    <c:forEach var="item" items="${subList}" varStatus="L">
                        <tr>
                            <td>${item.scommodityCode}</td>
                            <td>${item.scommodityName}</td>
                            <td>${item.forderCount}</td>
                            <td>${item.istock}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</c:if>

<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/comm.js?236"></script>
<script type="text/javascript" src="${staticSource}/static/js/util.js"></script>
<c:if test="${fn:length(addList) > 0}">
    <script type="text/javascript">
        $(function () {
            $('#add-shelf-details').on('click', function () {
                $('.eject,.popup-bg-add').show();
            });
        });
    </script>
</c:if>
<c:if test="${fn:length(subList) > 0}">
    <script type="text/javascript">
        $(function () {
            $('#sub-shelf-details').on('click', function () {
                $('.eject,.popup-bg-sub').show();
            });
        });
    </script>
</c:if>
<script type="text/javascript">
    $(function () {
        $('.eject').on('click', function () {
            $('.eject,.popup-bg').hide();
        });
        setTitle("补货成功");
        $('.container-c').css('height', $(window.top).height());
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
                    Util.Alert(msg.msg);
                    if (msg.success) {
                        $(".markingDone").hide();
                        jumpReplenishmentIndex();
                    }
                }, 3000);
            }
        });
    }
    <%@ include file="/common/include/baidu.jsp"%>
</script>
</body>
</html>