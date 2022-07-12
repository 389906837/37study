<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>积分规则</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?20188" rel="stylesheet">
</head>
<body>
<div class="ibox-content">
    <div class="layui-form-item wflayui-form-item">
        <table  cellspacing="0" border="0">
            <tr>
                <td class="text-left" style="width:18%">是否有效：</td>
                <td class="text-right" style="width:32%"><c:if test="${integralRule.iisValid eq 1}">启用</c:if><c:if test="${(empty integralRule.iisValid ? 0 : integralRule.iisValid) eq 0}">禁用</c:if></td>
                <td class="text-left" style="width:18%">&nbsp;</td>
                <td class="text-right" style="width:32%">&nbsp;</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">送券方式：</td>
                <td class="text-right"  style="width:32%"><c:if test="${integralRule.igiveMethod eq 10}">比例</c:if>
                    <c:if test="${integralRule.igiveMethod eq 20}">固定额度</c:if></td>
                <td class="text-left" style="width:18%">积分(分)/比例(%)：</td>
                <td class="text-right"  style="width:32%"><fmt:formatNumber value="${integralRule.fbaseIntegral}" pattern="#0.##"/></td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">赠送下限(分)：</td>
                <td class="text-right"  style="width:32%">${integralRule.fminValue}</td>
                <td class="text-left"  style="width:18%">赠送上限(分)：</td>
                <td class="text-right"  style="width:32%">${integralRule.fmaxValue}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">奖励说明：</td>
                <td class="text-right"  style="width:32%">${integralRule.sactivityInstruction}</td>
                <td class="text-left"  style="width:18%">备注：</td>
                <td class="text-right"  style="width:32%">${integralRule.sremark}</td>
            </tr>
        </table>
    </div>
</div>
<script type="text/javascript">
    $(".wflayui-form-item tr:even").addClass("odd");
</script>
</body>
</html>