<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/include/taglibs.jsp"%>
<div class="top">
    <div class="head">
        <div class="container">
            <div class="nav">
                <div class="logo">
                    <img src="${staticSource}/static/images/logoNew.png" />
                </div>
                <div class="menu menu-bg">
                    <ul>
                        <li class="active"><a href="/" id="home">首页</a></li>
                        <c:if test="${not empty arts }">
                            <c:forEach items="${arts}" var="art">
                                <li>
                                    <a  href="${ctx}/${art.ssubjoinTitle}.html">${art.stitle}</a>
                                </li>
                            </c:forEach>
                        </c:if>
                        <li><a href="${ctx}/aboutus.html">关于我们</a></li>
                        <li><a href="${ctx}/report.html">报道/动态</a></li>
                        <li><a href="#" id="lxfs" onclick="dibu();">联系方式</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="title"><img src="${staticSource}/static/images/title.png" /></div>
        <div class="round">
            <img class=" round-background" src="${staticSource}/static/images/round.png">
            <div class="reddot">
                <div></div>
                <p>江苏叁拾柒号仓智能科技有限公司</p>
            </div>
        </div>
    </div>
</div>

