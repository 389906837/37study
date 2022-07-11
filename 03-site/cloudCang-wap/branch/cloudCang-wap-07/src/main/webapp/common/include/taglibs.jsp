<%@page import="com.cloud.cang.zookeeper.utils.EvnUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="cang" uri="http://www.37cang.com/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	//request.setAttribute("staticSource", request.getContextPath());
	request.setAttribute("staticSource", EvnUtils.getValue("static.http.domain"));
	request.setAttribute("dynamicSource", EvnUtils.getValue("dynamic.http.domain"));
	request.setAttribute("wapPath", EvnUtils.getValue("wap.http.domain"));
	request.setAttribute("apiPath", EvnUtils.getValue("api.http.domain"));
%>
<script type="text/javascript">
    var staticPathRoot = "${staticSource }";
    var ctxRoot = "${ctx }";
    var socketObj;//全局soket变量
    var apiPath = "${apiPath}";
</script>