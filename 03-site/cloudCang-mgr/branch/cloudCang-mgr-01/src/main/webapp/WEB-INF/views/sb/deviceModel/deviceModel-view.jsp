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
        <table  cellspacing="0" border="0">
                <tr>
                    <td class="text-left" style="width:18%">设备核心型号：</td>
                    <td class="text-right" style="width:32%">${deviceModel.sdeviceModel}</td>
                    <td class="text-left" style="width:18%">核心生产厂商：</td>
                    <td class="text-right" style="width:32%">${deviceModel.smanufacturer}</td>
                </tr>
            <tr>
                <td class="text-left" style="width:18%">核心配置描述：</td>
                <td class="text-right"  style="width:32%">${deviceModel.scoreDesc}</td>
                <td class="text-left" style="width:18%">整体外形尺寸：</td>
                <td class="text-right"  style="width:32%">${deviceModel.sdimensions}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">重量：</td>
                <td class="text-right"  style="width:32%">${deviceModel.sweight}</td>
                <td class="text-left"  style="width:18%">额定功率：</td>
                <td class="text-right"  style="width:32%">${deviceModel.sratedPower}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">商品容量：</td>
                <td class="text-right"  style="width:32%">${deviceModel.sproductCapacity}</td>
                <td class="text-left"  style="width:18%">商品类型：</td>
                <td class="text-right"  style="width:32%">${deviceModel.sproductTypes}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">日耗电量：</td>
                <td class="text-right"  style="width:32%">${deviceModel.sdailyPower}</td>
                <td class="text-left"  style="width:18%">支付方式：</td>
                <td class="text-right"  style="width:32%">${deviceModel.spayWap}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">防触电类型：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${deviceModel.ielectricShock == 10 }">0类</c:if>
                    <c:if test="${deviceModel.ielectricShock == 20 }">I类</c:if>
                    <c:if test="${deviceModel.ielectricShock == 30 }">II类</c:if>
                    <c:if test="${deviceModel.ielectricShock == 40 }">III类</c:if>
                </td>
                <td class="text-left" style="width:18%">电子锁型号：</td>
                <td class="text-right" style="width:32%">${deviceModel.slocksModel}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">电子锁生产厂商：</td>
                <td class="text-right" style="width:32%">${deviceModel.slocksManufacturer}</td>
                <td class="text-left" style="width:18%">广告机屏幕尺寸说明：</td>
                <td class="text-right" style="width:32%">${deviceModel.sadDimensions}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">广告机配置描述：</td>
                <td class="text-right" style="width:32%">${deviceModel.sadConf}</td>
                <td class="text-left" style="width:18%">压缩机个数：</td>
                <td class="text-right" style="width:32%">${deviceModel.icompressorNum}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">压缩机位置：</td>
                <td class="text-right" style="width:32%">${deviceModel.scompressorPosition}</td>
                <td class="text-left" style="width:18%">压缩机描述：</td>
                <td class="text-right" style="width:32%">${deviceModel.scompressorDesc}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">货道数：</td>
                <td class="text-right" style="width:32%">${deviceModel.icargoRoadNum}</td>
                <td class="text-left" style="width:18%">摄像头个数：</td>
                <td class="text-right" style="width:32%">${deviceModel.icameraNum}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">货道数：</td>
                <td class="text-right" style="width:32%">${deviceModel.icargoRoadNum}</td>
                <td class="text-left" style="width:18%">主板块数：</td>
                <td class="text-right" style="width:32%">${deviceModel.imotherboardNum}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">是否使用扩展GPIO：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${deviceModel.iisUseExpandGpio == 0 }">否</c:if>
                    <c:if test="${deviceModel.iisUseExpandGpio == 1 }">是</c:if>
                </td>
                <td class="text-left" style="width:18%">是否检测霍尔值：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${deviceModel.iisDetectHall == 0 }">否</c:if>
                    <c:if test="${deviceModel.iisDetectHall == 1 }">是</c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">摄像头方法：</td>
                <td class="text-right" style="width:32%">${deviceModel.scameraMethod}</td>
                <td class="text-left" style="width:18%">摄像头型号：</td>
                <td class="text-right" style="width:32%">${deviceModel.scameraModel}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">摄像头类型：</td>
                <td class="text-right" style="width:32%">${deviceModel.scameraType}</td>
                <td class="text-left" style="width:18%">摄像头品牌：</td>
                <td class="text-right" style="width:32%">${deviceModel.scameraBrand}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">视觉服务提供商：</td>
                <td class="text-right" style="width:32%">${deviceModel.svisualServiceProvider}</td>
                <td class="text-left" style="width:18%">PCB板型号：</td>
                <td class="text-right" style="width:32%">${deviceModel.spcbModel}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">开门引脚序号：</td>
                <td class="text-right" style="width:32%">${deviceModel.iopendoorPinSn}</td>
                <td class="text-left" style="width:18%">霍尔引脚序号：</td>
                <td class="text-right" style="width:32%">${deviceModel.ihallPinSn}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">门引脚序号：</td>
                <td class="text-right" style="width:32%">${deviceModel.idoorPinSn}</td>
                <td class="text-left" style="width:18%">锁芯引脚序号：</td>
                <td class="text-right" style="width:32%">${deviceModel.ilockCylinder}</td>
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

