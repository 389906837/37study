<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备信息查看</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <div class="layui-form-item wflayui-form-item">
        <table cellspacing="0" border="0">
            <tr>
                <td class="text-left" style="width:18%">每次盘活次数：</td>
                <td class="text-right" style="width:32%">${monitorDataConfDomain.iinventoryNum}</td>
                <td class="text-left" style="width:18%">上次盘货时间：</td>
                <td class="text-right" style="width:32%"><fmt:formatDate value="${monitorDataConfDomain.tinventoryTime}"
                                                                         pattern="yyyy-MM-dd HH:mm:ss"/></td>
            </tr>
            <tr>
                <td class="text-left">自动盘货开始时间：</td>
                <td class="text-right">${monitorDataConfDomain.tinventoryBeginTime}</td>
                <td class="text-left" style="width:18%">自动盘货结束时间：</td>
                <td class="text-right" style="width:32%">${monitorDataConfDomain.tinventoryEndTime}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">实时温度1：</td>
                <td class="text-right" style="width:32%">${monitorDataConfDomain.sltemperature}</td>
                <td class="text-left" style="width:18%">实时温度2：</td>
                <td class="text-right" style="width:32%">${monitorDataConfDomain.srtemperature}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">控制温度1：</td>
                <td class="text-right" style="width:32%">${monitorDataConfDomain.slcontrolTemperature}</td>
                <td class="text-left" style="width:18%">控制温度2：</td>
                <td class="text-right" style="width:32%">${monitorDataConfDomain.srcontrolTemperature}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">温度控制开始时间1：</td>
                <td class="text-right" style="width:32%">${monitorDataConfDomain.tlcontrolBeginTime}</td>
                <td class="text-left" style="width:18%">温度控制结束时间1：</td>
                <td class="text-right" style="width:32%">${monitorDataConfDomain.tlcontrolEndTime}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">温度控制开始时间2：</td>
                <td class="text-right" style="width:32%">${monitorDataConfDomain.trcontrolBeginTime}</td>
                <td class="text-left" style="width:18%">温度控制结束时间2：</td>
                <td class="text-right" style="width:32%">${monitorDataConfDomain.trcontrolEndTime}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">定时开关机状态：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${monitorDataConfDomain.iswitchStatus == 0 }">否</c:if>
                    <c:if test="${monitorDataConfDomain.iswitchStatus == 1 }">是</c:if>
                </td>
                <td class="text-left" style="width:18%">实时音量：</td>
                <td class="text-right" style="width:32%">${monitorDataConfDomain.iactualVolume}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">开机时间：</td>
                <td class="text-right" style="width:32%">${monitorDataConfDomain.tbootTime}</td>
                <td class="text-left" style="width:18%">关机时间：</td>
                <td class="text-right" style="width:32%">${monitorDataConfDomain.tshutDownTime}</td>
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

