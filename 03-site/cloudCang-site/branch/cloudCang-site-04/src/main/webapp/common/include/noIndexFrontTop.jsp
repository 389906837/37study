<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<div class="head head-bg">
    <div class="container">
        <div class="nav">
            <div class="logo">
                <img src="${staticSource}/static/images/logoNew.png">
            </div>
            <div class="menu">
                <ul>
                    <li><a href="/" id="home">首页</a></li>
                    <c:if test="${not empty arts }">
                        <c:forEach items="${arts}" var="art">
                            <li id="${art.id}"<c:if test="${param.selected eq art.ssubjoinTitle }"> class="active"</c:if>>
                                <a href="${ctx}/${art.ssubjoinTitle}.html">${art.stitle}</a>
                            </li>
                        </c:forEach>
                    </c:if>
                    <li<c:if test="${param.selected eq 1 }"> class="active"</c:if>><a href="${ctx}/aboutus.html">关于我们</a></li>
                    <li<c:if test="${param.selected eq 2 }"> class="active"</c:if>><a href="${ctx}/report.html">报道/动态</a></li>
                    <li><a href="${ctx}/#contactus" id="lxfs">联系方式</a></li>
                </ul>
            </div>
        </div>
    </div>
</div>


