<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="index_container" id="replenishmentDiv">
    <div class="ww">
        <div class="banner slide">
            <c:if test="${fn:length(advertisList) <= 0}">
                <img src="${staticSource}/static/images/banner.png" />
            </c:if>
            <c:if test="${fn:length(advertisList) > 0}">
                <c:forEach items="${advertisList}" var="item" varStatus="L">
                    <img src="${dynamicSource}${item.scontentUrl}" />
                </c:forEach>
            </c:if>
        </div>
        <div class="wrapper">
            <div class="view">
                <div class="view_t">
                    <div class="login">
                        <c:if test="${empty indexData.slogo}"><img src="${indexData.slogo}/static/images/logo.png" /></c:if>
                        <c:if test="${!empty indexData.slogo}"><img src="${dynamicSource}${indexData.slogo}" /></c:if>
                        <span class="split">|</span>
                        <span class="bhy" onclick="jumpIndex();">我要购物</span>
                    </div>
                    <p class="kaimen-tip" id="reName">补货员姓名：${srealName}</p>
                </div>
                <div class="view_b">
                    <div class="opendoor_back"></div>
                    <div class="opendoor"><span>补</span><span>货</span><span>开</span><span>门</span></div>
                </div>
            </div>
        </div>
        <jsp:include page="/common/include/foot.jsp">
            <jsp:param name="selected" value="1"/>
        </jsp:include>
    </div>
    <!-- 非正常开门情况 -->
    <div class="popup_bg close">
        <div class="popup_contant md popup_pd relative popup_contant_h">
            <img class="indexclose_icon" src="${staticSource}/static/images/indexclose_icon.png" />
            <h4 class="popup_h4" id="alert_text"></h4>
            <p style="font-size: 1rem;">如有需要，请联系客服</p>
            <div class="popup_mt">
                <a class="reg_btn" href="javascript:void(0);" onclick="closeTip();">返 回</a>
            </div>
        </div>
    </div>
    <%-- 在线客服 --%>
    <%@ include file="/common/include/service-tell.jsp" %>
</div>

