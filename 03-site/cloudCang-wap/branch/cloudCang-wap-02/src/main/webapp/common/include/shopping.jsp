<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="index_container" id="shoppingDiv">
    <div class="ww">
        <div class="banner slide">
            <c:if test="${fn:length(advertisList) <= 0}">
                <img src="${staticSource}/static/images/banner.png"/>
            </c:if>
            <c:if test="${fn:length(advertisList) > 0}">
                <c:forEach items="${advertisList}" var="item" varStatus="L">
                    <img src="${dynamicSource}${item.scontentUrl}"/>
                </c:forEach>
            </c:if>
        </div>
        <div class="wrapper">
            <div class="view">
                <div class="view_t">
                    <div class="login">
                        <c:if test="${empty indexData.slogo}"><img
                                src="${staticSource}/static/images/logo.png"/></c:if>
                        <c:if test="${!empty indexData.slogo}"><img src="${dynamicSource}${indexData.slogo}"/></c:if>
                        <c:if test="${isReplenishment eq 1}">
                            <span class="split">|</span>
                            <span class="bhy" onclick="jumpReplenishmentIndex();">补货员</span>
                        </c:if>
                    </div>
                    <p class="md kaimen-tip">${indexData.indexHint}</p>
                </div>
                <div class="view_b">
                    <div class="opendoor_back"></div>
                    <div class="opendoor"><span>购</span><span>物</span><span>开</span><span>门</span></div>
                </div>
            </div>
        </div>
        <jsp:include page="/common/include/foot.jsp">
            <jsp:param name="selected" value="1"/>
        </jsp:include>
    </div>

    <!-- 开通免密支付弹框 可关闭-->
    <div class="popup_bg close">
        <div class="popup_contant md popup_pd relative popup_contant_h">
            <img class="indexclose_icon" src="${staticSource}/static/images/indexclose_icon.png"/>
            <h4 class="popup_h4">立即开通免密支付功能 </h4>
            <p style="font-size: 1rem;">享受无感购物新体验</p>
            <div class="popup_mt">
                <a class="btn btn_gray" href="javascript:void(0);" onclick="noRemind();" style="width: 40%">不再提醒</a>
                <a class="btn btn_bluefill" href="javascript:void(0);" onclick="openFree('${signPage}');"
                   style="width: 40%">立即开通</a>
            </div>
        </div>
    </div>

    <!-- 开通免密支付弹框 不可关闭-->
    <div class="popup_bg close">
        <div class="popup_contant md popup_pd relative popup_contant_h">
            <%-- <img class="indexclose_icon" src="${staticSource}/static/images/indexclose_icon.png" />--%>
            <p class="popup_h4">立即开通免密支付功能</p>
            <p class="popup_h4">享受无感购物新体验</p>
            <div class="popup_mt">
                <a class="reg_btn" href="javascript:void(0);" onclick="openFree('${signPage}');"
                   style="margin-top:2.5rem;">立即开通</a>
            </div>
        </div>
    </div>
    <!-- 非正常开门情况 -->
    <div class="popup_bg close">
        <div class="popup_contant md popup_pd relative popup_contant_h">
            <%-- <img class="indexclose_icon" src="${staticSource}/static/images/indexclose_icon.png" />--%>
            <h4 class="popup_h4" id="alert_text"></h4>
            <p style="font-size: 1rem;">如有需要，请联系客服</p>
            <div class="popup_mt">
                <a class="reg_btn" href="javascript:void(0);" onclick="closeTip();">返 回</a>
            </div>
        </div>
    </div>
    <!-- 异常订单 -->
    <div class="popup_bg close">
        <div class="popup_contant md popup_pd relative popup_contant_h">
            <img class="indexclose_icon" src="${staticSource}/static/images/indexclose_icon.png"/>
            <h4 class="popup_h4">有未支付订单请支付 </h4>
            <div class="popup_mt">
                <a class="reg_btn" href="javascript:void(0);" onclick="myOrderFail();">去支付</a>
            </div>
        </div>
    </div>
    <!-- 开通微信支付分 可关闭-->
    <div class="popup_bg close">
        <div class="popup_contant md popup_pd relative popup_contant_h">
            <img class="indexclose_icon" src="${staticSource}/static/images/indexclose_icon.png"/>
            <h4 class="popup_h4">立即开通微信支付分 </h4>
            <p style="font-size: 1rem;">享受无感购物新体验</p>
            <div class="popup_mt">
                <a class="btn btn_gray" href="javascript:void(0);" onclick="noRemind();" style="width: 40%">不再提醒</a>
                <a class="btn btn_bluefill" href="javascript:void(0);" onclick="wechatPoint('${signPage}');"
                   style="width: 40%">立即开通</a>
            </div>
        </div>
    </div>
    <!-- 开通微信支付分 不可关闭-->
    <div class="popup_bg close">
        <div class="popup_contant md popup_pd relative popup_contant_h">
            <p class="popup_h4">立即开通微信支付分</p>
            <p class="popup_h4">享受无感购物新体验</p>
            <div class="popup_mt">
                <a class="reg_btn" href="javascript:void(0);" onclick="wechatPoint('${signPage}');"
                   style="margin-top:2.5rem;">立即开通</a>
            </div>
        </div>
    </div>
    <!-- 授权智能垃圾箱 可关闭-->
    <div class="popup_bg close">
        <div class="popup_contant md popup_pd relative popup_contant_h">
            <img class="indexclose_icon" src="${staticSource}/static/images/indexclose_icon.png"/>
            <h4 class="popup_h4">立即授权智能垃圾箱 </h4>
            <p style="font-size: 1rem;">立即授权智能垃圾箱</p>
            <div class="popup_mt">
                <a class="btn btn_gray" href="javascript:void(0);" onclick="authNoRemind(${strashBrand});"
                   <%--onclick="noRemind();"--%>
                   style="width: 40%">不再提醒</a>
                <a class="btn btn_bluefill" href="javascript:void(0);" onclick="openFree('${authoriseTrashPage}');"
                   style="width: 40%">立即授权</a>
            </div>
        </div>
    </div>
    <c:choose>
        <c:when test="${(empty isOpenFreeData or isOpenFreeData eq 0) and isRemindOpenAndClose eq 1 and (empty iwechatWithholdType or iwechatWithholdType eq 10)}">
            <script type="text/javascript">
                $(".popup_bg").eq(1).removeClass('close');
            </script>
        </c:when>
        <c:when test="${isExceptionOrder eq 1}">
            <script type="text/javascript">
                $(".popup_bg").eq(3).removeClass('close');
                $('.popup_bg,.indexclose_icon').on('click', function () {
                    closeTip();
                });
            </script>
        </c:when>
        <c:when test="${(empty isOpenFreeData or isOpenFreeData eq 0) and isRemindOpen eq 1 and (empty iwechatWithholdType or iwechatWithholdType eq 10)}">
            <script type="text/javascript">
                $(".popup_bg").eq(0).removeClass('close');
                $('.popup_bg,.indexclose_icon').on('click', function () {
                    closeTip();
                });
            </script>
        </c:when>

        <c:when test="${(empty isOpenFreeData or isOpenFreeData eq 0) and isRemindOpen eq 1 and (not empty iwechatWithholdType or iwechatWithholdType eq 20)}">
            <script type="text/javascript">
                $(".popup_bg").eq(4).removeClass('close');
                $('.popup_bg,.indexclose_icon').on('click', function () {
                    closeTip();
                });
            </script>
        </c:when>

        <c:when test="${(empty isOpenFreeData or isOpenFreeData eq 0) and isRemindOpenAndClose eq 1 and (not empty iwechatWithholdType or iwechatWithholdType eq 20)}">
            <script type="text/javascript">
                $(".popup_bg").eq(5).removeClass('close');
            </script>
        </c:when>
        <c:when test="${not empty authoriseTrash and authoriseTrash eq 1 and isRemindAuth eq 1}">
            <script type="text/javascript">
                $(".popup_bg").eq(6).removeClass('close');
                $('.popup_bg,.indexclose_icon').on('click', function () {
                    closeTip();
                });
            </script>
            <%--   <script type="text/javascript">
                   $(".popup_bg").eq(6).removeClass('close');
               </script>--%>
        </c:when>

        <c:otherwise>
            <script type="text/javascript">
                $('.popup_bg,.indexclose_icon').on('click', function () {
                    closeTip();
                });
            </script>
        </c:otherwise>
    </c:choose>
    <%-- 在线客服 --%>
    <%@ include file="/common/include/service-tell.jsp" %>
</div>