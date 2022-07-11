<%--
  Created by IntelliJ IDEA.
  User: 37cang-1
  Date: 2018/12/27
  Time: 11:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/include/taglibs.jsp"%>
<html>
<head>
    <title>核心算法</title>
    <link rel="stylesheet" href="${staticSource}/static/css/index.css">
    <link rel="shortcut icon" href="${staticSource}/static/favicon.ico" type="image/x-icon">
    <script type="text/javascript" src="${staticSource}/static/front/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="${staticSource}/static/front/js/common.js"></script>
    <script>
        $(document).ready(function(){
            $.ajax({
                type:"GET",
                url:"news/mediaList",
                success:function(data){
                    alert(data)
                },
                error : function(e) {
                    alert('Failed!: ' + e);
                }
            });
        });

    </script>
</head>
<body>
<jsp:include page="/common/include/noIndexFrontTop.jsp" /><hr>
<div class="core-img"></div>



<jsp:include page="/common/include/noIndexFrontBottom.jsp" /><hr>
</body>
</html>
