<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>

<div class="head">
    <img class="index-menu" src="${staticSource}/static/wap/image/menu-icon.png">
    江苏叁拾柒号仓智能科技有限公司
    <div class="menu-more">
        <ul>
            <li><a href="${ctx}/m/index.html">首页</a></li>
            <c:if test="${not empty arts }">
                <c:forEach items="${arts}" var="art">
                    <li>
                        <a href="${ctx}/m/${art.ssubjoinTitle}.html">${art.stitle}</a>
                    </li>
                </c:forEach>
            </c:if>
            <li><a href="${ctx}/m/aboutUs.html">关于我们</a></li>
            <li><a href="${ctx}/m/dynamic.html">报道/动态</a></li>
            <li><a href="javascript:void(0); " onclick="dibu();">联系方式</a></li>
        </ul>
    </div>
</div>
<script type="text/javascript">
    function dibu() {
        $('html,body').animate({scrollTop:$('.foot').offset().top}, 800);
    }
</script>