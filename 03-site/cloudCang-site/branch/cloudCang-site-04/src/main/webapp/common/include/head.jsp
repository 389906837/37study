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
                    <c:choose>
                        <c:when test="${art.ssubjoinTitle eq 'productCenter'}">
                            <li>
                                <a href="#">产品中心</a>
                                <div class="menu-down">
                                    <c:forEach items="${ttt_artsss}" var="artT">
                                        <a href="${ctx}/m/${artT.ssubjoinTitle}_${artT.sresourceName}.html">${artT.stitle}</a>
                                    </c:forEach>
                                    <%--<a href="${ctx}/m/${art.ssubjoinTitle}_vending.html">智能自取售货柜</a>
                                    <a href="${ctx}/m/${art.ssubjoinTitle}_species.html">珍稀物种智能分析平台</a>
                                    <a href="${ctx}/m/${art.ssubjoinTitle}_display.html">商品智能展示系统</a>
                                    <a href="${ctx}/m/${art.ssubjoinTitle}_classification.html">垃圾分类智能识别系统</a>
                                    <a href="${ctx}/m/${art.ssubjoinTitle}_commodity.html">售货机出口商品检测</a>--%>
                                </div>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li>
                                <a href="${ctx}/m/${art.ssubjoinTitle}.html">${art.stitle}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </c:if>
            <li><a href="${ctx}/m/aboutUs.html">关于我们</a></li>
            <li><a href="${ctx}/m/dynamic.html">报道动态</a></li>
            <li><a href="javascript:void(0); " onclick="dibu();">联系方式</a></li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    /* 头部菜单 */
   $(".index-menu").click(function(){
        $(".menu-more").toggle();
    })
    /* 二级菜单下拉*/
    $(".menu-more li").click(function(){
        $(this).children(".menu-down").toggle();
    });
    function dibu() {
        $('html,body').animate({scrollTop:$('.foot').offset().top}, 800);
    }
</script>