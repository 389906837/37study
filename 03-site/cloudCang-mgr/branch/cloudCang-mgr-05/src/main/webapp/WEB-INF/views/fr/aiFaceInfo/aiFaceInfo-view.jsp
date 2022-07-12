<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>人脸信息</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <div class="layui-form-item wflayui-form-item">
        <table  cellspacing="0" border="0">
            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                <tr>
                    <td class="text-left" style="width:18%"><spring:message code='fr.face.number' />：</td>
                    <td class="text-right" style="width:32%">${faceRegisterInfo.sfaceCode}</td>
                    <td class="text-left" style="width:18%"><spring:message code='sb.ai.equipment.number' />：</td>
                    <td class="text-right" style="width:32%">${faceRegisterInfo.sregAiCode}</td>
                </tr>
            </c:if>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="main.member.number" />：</td>
                <td class="text-right"  style="width:32%">${faceRegisterInfo.smemberCode}</td>
                <td class="text-left"  style="width:18%"><spring:message code="main.member.username" />：</td>
                <td class="text-right"  style="width:32%">${faceRegisterInfo.smemberName}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code="main.state" />：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${faceRegisterInfo.itype == 10 }"><spring:message code='fr.registered' /></c:if>
                    <c:if test="${faceRegisterInfo.itype == 20 }"><spring:message code='fr.binding' /></c:if>
                    <c:if test="${faceRegisterInfo.itype == 30 }"><spring:message code='fr.untied' /></c:if>
                </td>
                <td class="text-left" style="width:18%"><spring:message code='fr.binding.payment.method' />：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${faceRegisterInfo.ibindPayType == 10 }"><spring:message code="main.wechat" /></c:if>
                    <c:if test="${faceRegisterInfo.ibindPayType == 20 }"><spring:message code="main.alipay" /></c:if>
                    <c:if test="${faceRegisterInfo.ibindPayType == 30 }"><spring:message code="main.other" /></c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="sb.registration.time" />：</td>
                <td class="text-right"  style="width:32%">
                    <fmt:formatDate value="${faceRegisterInfo.tregisterTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
                <td class="text-left"  style="width:18%"><spring:message code='fr.binding.time' />：</td>
                <td class="text-right"  style="width:32%">
                    <fmt:formatDate value="${faceRegisterInfo.tbindTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='fr.unbinding.time' />：</td>
                <td class="text-right"  style="width:32%">
                    <fmt:formatDate value="${faceRegisterInfo.tunbindTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
                <td class="text-left"  style="width:18%"><spring:message code='fr.user.group' />：</td>
                <td class="text-right"  style="width:32%">${faceRegisterInfo.sgroupId}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='sb.registered.device.address' />：</td>
                <td class="text-right"  style="width:32%">${faceRegisterInfo.sdeviceAddress}</td>
                <td class="text-left"  style="width:18%"></td>
                <td class="text-right"  style="width:32%"></td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code='fr.register.face.image' />：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${!empty  faceRegisterInfo.sregisterFaceAddress}">
                        <p style="text-align: left;">
                            <img src="${dynamicSource}${faceRegisterInfo.sregisterFaceAddress}" style='width: 100px; height: 100px;'>
                        </p>
                    </c:if>
                </td>
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

