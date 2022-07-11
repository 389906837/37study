<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.37cang.com/tags" prefix="cang"%>
<%response.setHeader("Pragma","No-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires", 0); 
response.flushBuffer();%>
 <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=urf-8">
        <title>调度任务列表</title>
        <style type="text/css">@import url('${pageContext.request.contextPath}/static/styles/home.css');</style>
		 <script type="text/javascript" src="${pageContext.request.contextPath}/static/components/jquery/jquery-1.3.2.min.js"></script>
		<script type="text/javascript">
		function doCmd(state,triggerName,group,triggerState){
			if(state == 'pause' && triggerState=='PAUSED'){
				alert("该Trigger己经暂停！");
				return;
			}
		    if(state == 'resume' && triggerState != 'PAUSED'){
				alert("该Trigger正在运行中！");
				return;
			}
			//客户端两次编码，服务端再解码，否测中文乱码 
			triggerName = encodeURIComponent(encodeURIComponent(triggerName));
			group = encodeURIComponent(encodeURIComponent(group));
            $.ajax({
                url: '${pageContext.request.contextPath}/JobProcessServlet?jobtype=200&action='+state+'&triggerName='+triggerName+'&group='+group,
                type: 'post',
                //dataType: 'xml',
               // timeout: 3000,
                error: function(){
                   alert("执行失败！");		
                },
                success: function(xml){
					if (xml == 0) {
						alert("执行成功！");
						window.location.reload();
					}else{
						alert("执行失败！");	
					}		   
                }
            });
		}
		</script>
		<style type="text/css">
		body, html{height: 100%;width: 100%;margin: 0; padding: 0;}
        P{width: 80%;margin: 0px auto;}
        table {height: 80%;width: 80%;margin: 10px auto;}
		</style>
    </head>
    <body>
    	<br><br>
        <p style="display: none"><a href="${pageContext.request.contextPath}/index.jsp">添加调度任务</a></p>
         <p  ><font color="red"><% request.setAttribute("current", new Date());%>服务器当前时间：<fmt:formatDate value="${current}" pattern="yyyy-MM-dd HH:mm:ss"/></font></p>
        <table  border="1">
            <tr>
            	<th nowrap>编号</th>
            	<th nowrap>Trigger 名称</th>
                <th nowrap>Trigger 名称</th>
                <th nowrap> Trigger 分组</th>
                <th nowrap>时间表达式</th>
                <th nowrap>优先级</th>
                <th nowrap>Trigger 状态</th>
                <th nowrap>Trigger 类型</th>
                <th nowrap>开始时间</th>
                <th nowrap>上次执行时间</th>
                <th nowrap> 下次执行时间</th>
                <th nowrap>结束时间</th>
                <th nowrap> 动作命令</th>
            </tr>
            <c:forEach var="map" items="${list}" varStatus="status">
                <tr>
                	<td nowrap>${status.index+1}</td>
                	<td nowrap>${map.moduleName}</td>
                    <td nowrap>${map.triggerName}</td>
                    <td nowrap>${map.triggerGroup}</td>
                    <td nowrap>${map.cron}</td>
                    <td nowrap>${map.priority}</td>
                    <td nowrap>
                    <c:if test='${map.triggerState eq "PAUSED" }'><font color="red"> ${map.triggerState}</font></c:if>
                    <c:if test='${map.triggerState ne "PAUSED" }'> ${map.triggerState}</c:if></td>
                    <td nowrap>${map.triggerType}</td>
                    <td nowrap><cang:date value="${map.startTime}"></cang:date></td>
                    <td nowrap><cang:date value="${map.prevFireTime}"></cang:date></td>
                    <td nowrap><cang:date value="${map.nextFireTime}"></cang:date></td>
                    <td nowrap><cang:date value="${map.endTime}"></cang:date></td>
                    <td nowrap>
                    	<input type="button" id="pause" value="暂停" onclick="doCmd('pause','${map.triggerName}','${map.triggerGroup}','${map.triggerState}')">
						<input type="button" id="resume" value="恢复" onclick="doCmd('resume','${map.triggerName}','${map.triggerGroup}','${map.triggerState}')">
						<!-- <input type="button" id="remove" value="删除" onclick="doCmd('remove','${map.triggerName}','${map.triggerGroup}','${map.triggerState}')"> -->	
						<input type="button" id="run" value="立即执行" onclick="doCmd('run','${map.triggerName}','${map.triggerGroup}','${map.triggerState}')">						
                    </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
