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
                    <td class="text-left" style="width:18%">人脸编号：</td>
                    <td class="text-right" style="width:32%">${faceRegisterInfo.sfaceCode}</td>
                    <td class="text-left" style="width:18%">AI设备编号：</td>
                    <td class="text-right" style="width:32%">${faceRegisterInfo.sregAiCode}</td>
                </tr>
            </c:if>
            <tr>
                <td class="text-left" style="width:18%">会员编号：</td>
                <td class="text-right"  style="width:32%">${faceRegisterInfo.smemberCode}</td>
                <td class="text-left"  style="width:18%">会员用户名：</td>
                <td class="text-right"  style="width:32%">${faceRegisterInfo.smemberName}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">状态：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${faceRegisterInfo.itype == 10 }">注册</c:if>
                    <c:if test="${faceRegisterInfo.itype == 20 }">绑定</c:if>
                    <c:if test="${faceRegisterInfo.itype == 30 }">解绑</c:if>
                </td>
                <td class="text-left" style="width:18%">绑定支付方式：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${faceRegisterInfo.ibindPayType == 10 }">微信</c:if>
                    <c:if test="${faceRegisterInfo.ibindPayType == 20 }">支付宝</c:if>
                    <c:if test="${faceRegisterInfo.ibindPayType == 30 }">其他</c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">注册时间：</td>
                <td class="text-right"  style="width:32%">
                    <fmt:formatDate value="${faceRegisterInfo.tregisterTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
                <td class="text-left"  style="width:18%">绑定时间：</td>
                <td class="text-right"  style="width:32%">
                    <fmt:formatDate value="${faceRegisterInfo.tbindTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">解绑时间：</td>
                <td class="text-right"  style="width:32%">
                    <fmt:formatDate value="${faceRegisterInfo.tunbindTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
                <td class="text-left"  style="width:18%">用户组：</td>
                <td class="text-right"  style="width:32%">${faceRegisterInfo.sgroupId}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">注册设备地址：</td>
                <td class="text-right"  style="width:32%">${faceRegisterInfo.sdeviceAddress}</td>
                <td class="text-left"  style="width:18%"></td>
                <td class="text-right"  style="width:32%"></td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">注册人脸图片：</td>
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
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    $(".wflayui-form-item tr:even").addClass("odd");
</script>
</body>
</html>

