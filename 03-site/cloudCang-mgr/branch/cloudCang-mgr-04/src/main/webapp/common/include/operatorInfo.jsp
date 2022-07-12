<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<li class="nav-header">
    <div class="dropdown profile-element" style="text-align: left;">

        <c:if test="${!empty sessionScope.SESSION_KEY_USER.smerchantLogo}">
            <img src="${dynamicSource}${sessionScope.SESSION_KEY_USER.smerchantLogo}" class="newlogo"/>
        </c:if>
        <c:if test="${empty sessionScope.SESSION_KEY_USER.smerchantLogo}">
            <img src="${staticSource}/resources/hplus/css/patterns/newlogo.png" class="newlogo"/>
        <%--<span style="color:#fff;font-size:13px;padding-top:10px;display:block">${sessionScope.SESSION_KEY_USER.smerchantName}<img alt="image" class="img-circle" src="${dynamicSource}${sessionScope.SESSION_KEY_USER.smerchantLogo}" /></span>--%>
        </c:if>
        <span style="color:#b0bfcd;font-size:13px;padding-top:10px;display:block;line-height:22px;display:block;text-align:center;width:140px;margin:auto">${sessionScope.SESSION_KEY_USER.smerchantName}</span>
        <%--<div class="glyuan">管理员</div>--%>
        <%--<a data-toggle="dropdown" class="dropdown-toggle" href="javascript:void(0);">
            <span class="clear">
           &lt;%&ndash; <span class="block m-t-xs"><strong class="font-bold">${sessionScope.SESSION_KEY_USER.userName}</strong></span>&ndash;%&gt;
            <span class="text-muted text-xs block">${sessionScope.SESSION_KEY_USER.userName}-${sessionScope.SESSION_KEY_USER.realName}</span>
            </span>
        </a>--%>

    </div>
    <div class="logo-element">${sessionScope.SESSION_KEY_USER.sshortName}</div>
</li>

