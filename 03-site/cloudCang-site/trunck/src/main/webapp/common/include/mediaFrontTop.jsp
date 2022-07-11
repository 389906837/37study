<%--
  Created by IntelliJ IDEA.
  User: 37cang-1
  Date: 2018/12/27
  Time: 10:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html>
<head>
    <script type="text/javascript" src="${staticSource}/static/front/js/jquery-1.7.2.min.js"></script>
    <link rel="shortcut icon" href="${staticSource}/static/favicon.ico" type="image/x-icon">
    <title>Title</title>
</head>
<body>
<div class="main mediareportslist-bg">
    <div class="topinner">
        <div class="navinner">
            <div class="logoinner">
                    <img src="${staticSource}/static/images/37logoNew.png">
                </div>
                <div class="menuinner">
                    <ul>
                        <li ><a href="/" id="home">首页</a></li>
                        <c:if test="${not empty arts }">
                            <c:forEach items="${arts}" var="art">
                                <li>
                                    <a  href="${ctx}/${art.ssubjoinTitle}.html" >${art.stitle}</a>
                                </li>
                            </c:forEach>
                        </c:if>

                        <li><a href="/#aboutus" id="gywm">关于我们</a></li>
                        <li><a href="/#mediareports" id="mtbd">媒体报道</a></li>
                        <li><a href="/#contactus" id="lxfs">联系方式</a></li>

                    </ul>
                </div>
            </div>
        </div>
    </div>
    <%--<div class="container">
        <div class="title"><img class="lazy" data-original="${staticSource}/static/images/title.png" /></div>
        <div class="round">
            <img class="lazy round-background" data-original="${staticSource}/static/images/round.png">
            <div class="reddot">
                <div></div>
                <p>江苏叁拾柒号仓智能科技有限公司</p>
            </div>
        </div>
    </div>--%>
</div>
</body>
<script>
    $(document).ready(function() {
        $("#rs").click(function() {
            publishArticle();
        });
    });



</script>
</html>
