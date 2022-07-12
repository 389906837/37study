<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>用户人脸信息</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <div class="layui-form-item wflayui-form-item">
        <table  cellspacing="0" border="0">
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='fr.username' /><：</td>
                <td class="text-right"  style="width:32%">${faceRegInfo.srealName}</td>
                <td class="text-left"  style="width:18%"><spring:message code='fr.user.photo' />：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${!empty  faceRegInfo.sregisterFaceAddress}">
                        <p style="text-align: left;">
                            <img src="${dynamicSource}${faceRegInfo.sregisterFaceAddress}" style='width: 100px; height: 100px;'>
                        </p>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='sb.audit.status' />：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${faceRegInfo.iauditStatus == 10 }"><spring:message code="main.audited" /></c:if>
                    <c:if test="${faceRegInfo.iauditStatus == 20 }"><spring:message code="main.approval" /></c:if>
                    <c:if test="${faceRegInfo.iauditStatus == 30 }"><spring:message code="main.rejection" /></c:if>
                </td>
                <td class="text-left"  style="width:18%"><spring:message code='sh.audit.opinion' />：</td>
                <td class="text-right"  style="width:32%">${faceRegInfo.sauditRemark}</td>
            </tr>
            <tr>

                <td class="text-left" style="width:18%">&nbsp;</td>
                <td class="text-right" style="width:32%">&nbsp;</td>
                <td class="text-left" style="width:18%">&nbsp;</td>
                <td class="text-right" style="width:32%">&nbsp;</td>
            </tr>
        </table>
    </div>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    $(".wflayui-form-item tr:even").addClass("odd");
</script>
</body>
</html>

