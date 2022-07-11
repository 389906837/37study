<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="hurbao" uri="/WEB-INF/tld/hrb.tld"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
	request.setAttribute("staticSource", request.getContextPath());
%>
<script type="text/javascript">
    var ctxRoot = "${ctx}";
</script>


