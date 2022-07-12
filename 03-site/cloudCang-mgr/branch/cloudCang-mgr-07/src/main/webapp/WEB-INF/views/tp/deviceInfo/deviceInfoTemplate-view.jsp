<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备基础信息模板查看</title>
<link href="${staticSource}/resources/layui/css/layui.css?4" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?1" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <div class="layui-form-item wflayui-form-item">
        <table cellspacing="0" border="0">
            <tr>
                <td class="text-left" style="width:18%">模板名称：</td>
                <td class="text-right" style="width:32%">${deviceInfoTemplate.sname}</td>
                <td class="text-left" style="width:18%">模板编号：</td>
                <td class="text-right" style="width:32%">${deviceInfoTemplate.scode}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">设备类型：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${deviceInfoTemplate.itype == 10 }">传统</c:if>
                    <c:if test="${deviceInfoTemplate.itype == 20 }">RFID射频</c:if>
                    <c:if test="${deviceInfoTemplate.itype == 30 }">视觉</c:if>
                    <c:if test="${deviceInfoTemplate.itype == 40 }">前端视觉+重力</c:if>
                    <c:if test="${deviceInfoTemplate.itype == 50 }">云端识别</c:if>
                    <c:if test="${deviceInfoTemplate.itype == 60 }">云端识别+重力</c:if>
                </td>
                <td class="text-left" style="width:18%">合作模式：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${deviceInfoTemplate.icooperationMode == 10 }">采购</c:if>
                    <c:if test="${deviceInfoTemplate.icooperationMode == 20 }">租用</c:if>
                    <c:if test="${deviceInfoTemplate.icooperationMode == 30 }">自主</c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">读写器序列号：</td>
                <td class="text-right" style="width:32%">${deviceInfoTemplate.sreaderSerialNumber}</td>
                <td class="text-left" style="width:18%">设备型号：</td>
                <td class="text-right" style="width:32%">${deviceInfoTemplate.sdeviceModel}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">货柜类型：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${deviceInfoTemplate.icontainerType == 10 }">单开门</c:if>
                    <c:if test="${deviceInfoTemplate.icontainerType == 20 }">双开门</c:if>
                </td>
                <td class="text-left">屏幕显示类型：</td>
                <td class="text-right">
                    <c:if test="${deviceInfoTemplate.iscreenDisplayType == 10 }">横屏</c:if>
                    <c:if test="${deviceInfoTemplate.iscreenDisplayType == 20 }">竖屏</c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">压缩机类型：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${deviceInfoTemplate.icompressorType == 10 }">制冷</c:if>
                    <c:if test="${deviceInfoTemplate.icompressorType == 20 }">制热</c:if>
                    <c:if test="${deviceInfoTemplate.icompressorType == 30 }">制冷热</c:if>
                    <c:if test="${deviceInfoTemplate.icompressorType == 40 }">无压缩机</c:if>
                </td>
                <td class="text-left" style="width:18%">是否广告机：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${deviceInfoTemplate.iinstallAd == 0 }">否</c:if>
                    <c:if test="${deviceInfoTemplate.iinstallAd == 1 }">是</c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">投放场景：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${deviceInfoTemplate.sputScenes == 10 }">公园</c:if>
                    <c:if test="${deviceInfoTemplate.sputScenes == 20 }">学校</c:if>
                    <c:if test="${deviceInfoTemplate.sputScenes == 30 }">医院</c:if>
                </td>
                <td class="text-left" style="width:18%">是否支持AI设备：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${deviceInfoTemplate.isupportAi == 0 }">否</c:if>
                    <c:if test="${deviceInfoTemplate.isupportAi == 1 }">是</c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">系统对接类型：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${deviceInfoTemplate.idockingType == 10 }">自主研发</c:if>
                    <c:if test="${deviceInfoTemplate.idockingType == 20 }">对接第三方</c:if>
                </td>
                <td class="text-left" style="width:18%">是否开启实时盘货：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${deviceInfoTemplate.iisOpeningInventory == 0 }">否</c:if>
                    <c:if test="${deviceInfoTemplate.iisOpeningInventory == 1 }">是</c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">是否支持称重：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${deviceInfoTemplate.isupportWeigh == 0 }">否</c:if>
                    <c:if test="${deviceInfoTemplate.isupportWeigh == 1 }">是</c:if>
                </td>
                <td class="text-left" style="width:18%">电子秤浮动值：</td>
                <td class="text-right" style="width:32%">${deviceInfoTemplate.ielectronicFloat} </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">称重类型：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${deviceInfoTemplate.iweightType == 10 }">232</c:if>
                    <c:if test="${deviceInfoTemplate.iweightType == 20 }">485</c:if>
                </td>
                <td class="text-left" style="width:18%">备注：</td>
                <td class="text-right" style="width:32%">${deviceInfoTemplate.sremark} </td>
            </tr>
        </table>
    </div>
</div>
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    $(".wflayui-form-item tr:even").addClass("odd");
</script>
</body>
</html>


