<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
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
    <title>我的</title>
    <link rel="stylesheet" href="${staticSource}/static/css/index.css?1.1">
</head>
<body class="my_bg">
<%@ include file="/common/include/commonHeader.jsp" %>
<div class="my_container">
    <div class="my_contant">
        <div class="my_infor">
            <ul>
                <li>
                    <img src="${staticSource}/static/images/my_zhmc.png">
                    <span>账户名称</span>
                    <em>${user.snickName }</em>
                </li>
                <li>
                    <img src="${staticSource}/static/images/my_phone.png">
                    <span>注册手机号</span>
                    <em>${user.smobile }</em>
                </li>
            </ul>
        </div>
        <div class="my_infor">
            <ul>
                <c:if test="${openFree eq 1}">
                    <li>
                        <a href="javascript:void(0);" onclick="openFree('${freeOpenPath}');">
                            <img src="${staticSource}/static/images/my_ktmm.png">
                            <span>开通免密</span>
                            <em><img class="my_arrowicon" src="${staticSource}/static/images/my_arrowicon.png"></em>
                        </a>
                    </li>
                </c:if>
                <c:if test="${openFree eq 2}">
                    <li>
                        <a href="javascript:void(0);" onclick="wechatPoint('${freeOpenPath}');">
                            <img src="${staticSource}/static/images/my_ktmm.png">
                            <span>开通微信支付分</span>
                            <em><img class="my_arrowicon" src="${staticSource}/static/images/my_arrowicon.png"></em>
                        </a>
                    </li>
                </c:if>
                <li>
                    <a href="javascript:void(0);" onclick="myOrder();">
                        <img src="${staticSource}/static/images/my_dd.png">
                        <span>我的订单</span>
                        <em><img class="my_arrowicon" src="${staticSource}/static/images/my_arrowicon.png"></em>
                    </a>
                </li>
                <li>
                    <a href="javascript:void(0);" onclick="myCoupon();">
                        <img src="${staticSource}/static/images/my_qb.png">
                        <span>我的券包</span>
                        <em><img class="my_arrowicon" src="${staticSource}/static/images/my_arrowicon.png"></em>
                    </a>
                </li>
                <c:if test="${isReplenishment eq 1}">
                    <li>
                        <a href="javascript:void(0);" onclick="myReplenishmentOrder();">
                            <img src="${staticSource}/static/images/my_bhd.png">
                            <span>我的补货单</span>
                            <em><img class="my_arrowicon" src="${staticSource}/static/images/my_arrowicon.png"></em>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:void(0);" <%--onclick="myDeviceStock();"--%> onclick="selectDevice()">
                            <img src="${staticSource}/static/images/my_hgkc.png">
                            <span>查看货柜库存</span>
                            <em><img class="my_arrowicon" src="${staticSource}/static/images/my_arrowicon.png"></em>
                        </a>
                    </li>
                </c:if>
            </ul>
        </div>
        <div class="my_mlr">
            <c:if test="${alipay eq 1}">
                <a class="reg_btn" href="javascript:void(0);" onclick="exitApp();">退 出</a>
            </c:if>
            <c:if test="${wechat eq 1}">
                <a class="reg_btn hui-display" href="javascript:void(0);">退 出</a>
            </c:if>
        </div>
    </div>
</div>
<jsp:include page="/common/include/foot.jsp">
    <jsp:param name="selected" value="4"/>
</jsp:include>
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/comm.js?1.0"></script>
<script type="text/javascript" src="${staticSource}/static/js/util.js?1.0"></script>
<c:if test="${alipay eq 1}">
    <%@ include file="/common/include/alipayInit.jsp" %>
</c:if>
<c:if test="${wechat eq 1}">
    <script type="text/javascript" src="${staticSource}/static/js/jweixin-1.5.0.js"></script>
    <script type="text/javascript">
        wx.config(${jsonStr});
        wx.ready(function () {
            $(".reg_btn").attr("onclick", "exitMineApp()");
            $(".reg_btn").removeClass("hui-display");
        });
        wx.error(function (res) {
            Util.Alert(res.errMsg);
        });
        function exitMineApp() {
            window.location.href = ctxRoot + "/logout";
            wx.closeWindow();
        }
    </script>
    <%@ include file="/common/include/wechatInit.jsp" %>
</c:if>
<script type="text/javascript">
    function selectDevice() {

        $.ajax({
            type: "post",
            url: ctxRoot + "/replenishment/selectDevice",
            dataType: "json",
            success: function (msg) {
                if (msg.success) {
                    myDeviceStock();
                } else {
                    //重新扫码
                    if (msg.msg == "1") {
                        wechatScan(2);
                    } else if (msg.msg == "2") {
                        alipayScan(2);
                    }
                }
            }
        });
    }
    <%@ include file="/common/include/baidu.jsp" %>
</script>
</body>
</html>