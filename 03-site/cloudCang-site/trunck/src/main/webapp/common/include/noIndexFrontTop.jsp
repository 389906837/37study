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
<div class="main">
    <div class="head head-bg">
        <div class="container">
            <div class="nav">
                <div class="logo">
                    <img src="${staticSource}/static/images/logoNew.png">
                </div>
                <div class="menu">
                    <ul>
                        <li <%--class="active"--%>><a href="/" id="home">首页</a></li>

                        <c:if test="${not empty arts }">
                            <c:forEach items="${arts}" var="art">
                                <li id="${art.id}">
                                    <a href="${ctx}/${art.ssubjoinTitle}.html"  <%--onclick="changeCol('${art.id}')"--%>>${art.stitle}</a>
                                   <%-- <input type="hidden" id="aid" name="parameter" value="${art.id}">--%>
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

</div>
</body>
<script>
   /* function changeCol(id)
    {
        alert(id);
        $('.menu').find('li').removeClass('active');
       /!* $('#'+id).addClass('active');*!/
        $(this).addClass('active');
    }*/

  /* $(function(){

       $("li").click(function() {

           $(".menu").find("li").removeClass("active");  // 删除其他兄弟元素的样式

           $(this).addClass("active");   // 添加当前元素的样式

       });
   });*/


</script>

</html>
