<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>我的</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="format-detection" content="telephone=no" />
<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet" href="${staticSource}/static/css/comm.css" />
<link rel="stylesheet" href="${staticSource}/static/css/style.css?7.0" />
</head>
<body>
<%@ include file="/common/include/commonHeader.jsp"%>
<div class="container">
    <div class="container-c">
        <div class="my-contant">
            <div class="my-infor">
                <ul>
                    <li>
                        <img src="${staticSource}/static/images/iphone-nameicon.png" style="width: 2rem;height: 2rem;" >
                        <span>账户名称</span>
                        <em>${user.snickName }</em>
                    </li>
                    <li>
                        <img src="${staticSource}/static/images/iphone-numbericon.png" style="width: 1.5rem;height: 2rem;padding-left: 0.3rem" />
                        <span>注册手机号</span>
                        <em>${user.smobile }</em>
                    </li>
                </ul>
            </div>
            <div class="my-infor">
                <ul>
                    <c:if test="${openFree eq 1}">
                        <li>
                            <a href="javascript:void(0);" onclick="openFree('${freeOpenPath}');">
                                <img src="${staticSource}/static/images/openfree.png" />
                                <span>开通免密</span>
                                <em style="top:2.3rem;"><img class="my-arrowicon" src="${staticSource}/static/images/my-arrowicon.png" ></em>
                            </a>
                        </li>
                    </c:if>
                    <li>
                        <a href="javascript:void(0);" onclick="myOrder();">
                            <img src="${staticSource}/static/images/my_order.png" />
                            <span>我的订单</span>
                            <em style="top:2.3rem;"><img class="my-arrowicon" src="${staticSource}/static/images/my-arrowicon.png" ></em>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:void(0);" onclick="myCoupon();">
                            <img src="${staticSource}/static/images/my_coupon.png" />
                            <span>我的券包</span>
                            <em style="top:2.3rem;"><img class="my-arrowicon" src="${staticSource}/static/images/my-arrowicon.png" ></em>
                        </a>
                    </li>

                    <c:if test="${isReplenishment eq 1}">
                        <li>
                            <a href="javascript:void(0);" onclick="myReplenishmentOrder();">
                                <img src="${staticSource}/static/images/my_bhd.png" />
                                <span>我的补货单</span>
                                <em style="top:2.3rem;"><img class="my-arrowicon" src="${staticSource}/static/images/my-arrowicon.png" ></em>
                            </a>
                        </li>
                    </c:if>
                </ul>
            </div>
            <a href="javascript:void(0);" onclick="exitApp();" class="button-icon check-btn" style="width: 100%;margin: 0 0;">退出</a>
        </div>
    </div>
    <jsp:include page="/common/include/foot.jsp">
        <jsp:param  name="selected" value="4" />
    </jsp:include>
</div>
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/comm.js?3333"></script>
<script type="text/javascript">
    $(function () {
        setTitle("个人中心");
        $('.container-c').css('height', $(window.top).height());
    });
    <%@ include file="/common/include/baidu.jsp"%>
</script>
</body>
</html>