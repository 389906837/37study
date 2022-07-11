<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>

<head>消息测试</head>

<body>
1.根据模板发送单条信息

<form action="${ctx}/test/testSend">

模板编号:<input name="templateCode" type="text"/><br/>
手机:<input name="mobile" type="text"/><br/>
邮箱:<input name="email" type="text"/><br/>
模板关键字内容(JSON 字符串):<textarea  rows="5" cols="20" name="contentStr"></textarea><br/>

<input type="submit" value="提交">

</form>

<hr/>

2.根据模板批量发送消息

<form action="${ctx}/test/testBatchSend">

模板编号:<input name="templateCode" type="text"/><br/>
手机(多个账号用英文逗号","分隔):<input name="mobile" type="text"/><br/>
邮箱(多个账号用英文逗号","分隔):<input name="email" type="text"/><br/>
模板关键字内容(JSON 字符串):<textarea  rows="5" cols="20" name="contentStr"></textarea><br/>

<input type="submit" value="提交">

</form>

<hr/>

2.根据模板和权限发送给有权限的内部用户

<form action="${ctx}/test/testPurviewSend">

模板编号:<input name="templateCode" type="text"/><br/>
权限编号:<input name="purviewCode" type="text"/><br/>
模板关键字内容(JSON 字符串):<textarea  rows="5" cols="20" name="contentStr"></textarea><br/>

<input type="submit" value="提交">

</form>

<hr/>

</body>


</html>




