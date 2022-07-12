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
                <td class="text-left" style="width:18%">用户名称：</td>
                <td class="text-right"  style="width:32%">${faceRegInfo.srealName}</td>
                <td class="text-left"  style="width:18%">用户照片：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${!empty  faceRegInfo.sregisterFaceAddress}">
                        <p style="text-align: left;">
                            <img src="${dynamicSource}${faceRegInfo.sregisterFaceAddress}" style='width: 100px; height: 100px;'>
                        </p>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">审核状态：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${faceRegInfo.iauditStatus == 10 }">待审核</c:if>
                    <c:if test="${faceRegInfo.iauditStatus == 20 }">审核通过</c:if>
                    <c:if test="${faceRegInfo.iauditStatus == 30 }">审核拒绝</c:if>
                </td>
                <td class="text-left"  style="width:18%">审核意见：</td>
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
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    $(".wflayui-form-item tr:even").addClass("odd");
</script>
</body>
</html>

