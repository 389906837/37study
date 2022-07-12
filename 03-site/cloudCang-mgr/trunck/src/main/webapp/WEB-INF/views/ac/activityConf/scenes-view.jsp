<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>场景活动详情</title>
<link href="${staticSource}/resources/layui/css/layui.css?2" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?1" rel="stylesheet">
</head>
<body>
<div class="ibox-content">
    <div class="layui-form-item wflayui-form-item">
        <table  cellspacing="0" border="0">
            <tr>
                <td class="text-left" style="width:18%">编号：</td>
                <td class="text-right" style="width:32%">${conf.sconfCode}</td>
                <td class="text-left" style="width:18%">&nbsp;</td>
                <td class="text-right" style="width:32%">&nbsp;</td>

            </tr>
            <tr>
                <td class="text-left"  style="width:18%">名称：</td>
                <td class="text-right"  style="width:32%">${conf.sconfName}</td>
                <td class="text-left"  style="width:18%">场景类型：</td>
                <td class="text-right"  style="width:32%">
                <c:if test="${conf.iscenesType eq 10}">平台赠送</c:if>
                <c:if test="${conf.iscenesType eq 20}">会员注册</c:if>
                <c:if test="${conf.iscenesType eq 30}">首次开通支付宝免密</c:if>
                <c:if test="${conf.iscenesType eq 40}">首次开通微信免密</c:if>
                <c:if test="${conf.iscenesType eq 50}">推荐注册</c:if>
                <c:if test="${conf.iscenesType eq 60}">首次下单</c:if>
                <c:if test="${conf.iscenesType eq 70}">下单</c:if>
                    <c:if test="${conf.iscenesType eq 51}">推荐首单</c:if></td>

            </tr>
            <tr>
                <td class="text-left" style="width:19%">活动期间总参与次数：</td>
                <td class="text-right"  style="width:32%">${(conf.iallCount eq 0) ? '不限制':conf.iallCount}<c:if test="${conf.iallCount>0}">次</c:if></td>
                <td class="text-left"  style="width:18%">参与次数限制类型：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${conf.icountType eq 10}">活动期间用户</c:if>
                    <c:if test="${conf.icountType eq 20}">活动期间设备</c:if></td>
                </td>
            </tr>
            <c:if test="${conf.icountType eq 10}">
            <tr>
                <td class="text-left" style="width:18%">(用户)总参与次数：</td>
                <td class="text-right"  style="width:32%">${(conf.iuserAllCount eq 0) ? '不限制':conf.iuserAllCount}<c:if test="${conf.iuserAllCount>0}">次</c:if></td>
                <td class="text-left"  style="width:18%">(用户)单日参与次数：</td>
                <td class="text-right"  style="width:32%">${(conf.iuserDayCount eq 0) ? '不限制':conf.iuserDayCount}<c:if test="${conf.iuserDayCount>0}">次</c:if></td>
            </tr>
            </c:if>
            <c:if test="${conf.icountType eq 20}">
            <tr>
                <td class="text-left" style="width:18%">(设备)总参与次数：</td>
                <td class="text-right"  style="width:32%">${conf.iuserAllCount}次</td>
                <td class="text-left"  style="width:18%">(设备)单日参与次数：</td>
                <td class="text-right"  style="width:32%">${conf.iuserDayCount}次</td>
            </tr>
            </c:if>
            <tr>
                <td class="text-left" style="width:18%">活动开始时间：</td>
                <td class="text-right" style="width:32%"><fmt:formatDate value='${conf.tactiveStartTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                <td class="text-left" style="width:18%">活动结束时间：</td>
                <td class="text-right" style="width:32%"><fmt:formatDate value='${conf.tactiveEndTime}' pattern='yyyy-MM-dd HH:mm:ss'/> </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">活动叙述：</td>
                <td class="text-right"  style="width:32%">${conf.sdescription}</td>
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

