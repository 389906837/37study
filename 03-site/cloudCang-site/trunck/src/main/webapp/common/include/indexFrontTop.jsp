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

<div class="top">
    <div class="head">
        <div class="container">
            <div class="nav">
                <div class="logo">
                    <img  src="${staticSource}/static/images/logoNew.png">
                </div>
                <div class="menu menu-bg">
                    <ul>
                        <li class="active"><a href="/" id="home">首页</a></li>

                        <c:if test="${not empty arts }">
                            <c:forEach items="${arts}" var="art">
                                <li>
                                    <a  href="${ctx}/${art.ssubjoinTitle}.html" onclick="a('${art.ssubjoinTitle}')">${art.stitle}</a>
                                </li>
                            </c:forEach>
                        </c:if>
                        <li><a href="#" id="gywm">关于我们</a></li>
                        <li><a href="#" id="mtbd">媒体报道</a></li>
                        <li><a href="#" id="lxfs">联系方式</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="title"><img  src="/static/images/title.png" /></div>
        <div class="round">
            <img class=" round-background" src="/static/images/round.png">
            <div class="reddot">
                <div></div>
                <p>江苏叁拾柒号仓智能科技有限公司</p>
            </div>
        </div>
    </div>
</div>
</body>
<script>
function a(taitle) {
    if(taitle=='product')
    {
        $("#large-header").css('display','block');
    }


}
</script>
</html>
