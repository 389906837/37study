<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备详细信息模板查看</title>
<link href="${staticSource}/resources/layui/css/layui.css?4" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?1" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <div class="layui-form-item wflayui-form-item">
        <table cellspacing="0" border="0">
            <tr>
                <td class="text-left" style="width:18%">模板编号：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.scode}</td>
                <td class="text-left" style="width:18%">模板名称：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.sname}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">设备核心型号：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.sdeviceModel}</td>
                <td class="text-left" style="width:18%">核心生产厂商：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.smanufacturer}</td>
            </tr>
            <tr>
                <td class="text-left">核心配置描述：</td>
                <td class="text-right">${deviceModelTemplate.scoreDesc}</td>
                <td class="text-left" style="width:18%">整体外形尺寸：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.sdimensions}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">重量：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.sweight}</td>
                <td class="text-left" style="width:18%">额定功率：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.sratedPower}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">商品容量：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.sproductCapacity}</td>
                <td class="text-left" style="width:18%">商品类型：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.sproductTypes}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">日耗电量：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.sdailyPower}</td>
                <td class="text-left" style="width:18%">支持支付方式：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.spayWap}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">防触电类型：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${deviceModelTemplate.ielectricShock == 10 }">0类</c:if>
                    <c:if test="${deviceModelTemplate.ielectricShock == 20 }">I类</c:if>
                    <c:if test="${deviceModelTemplate.ielectricShock == 30 }">II类</c:if>
                    <c:if test="${deviceModelTemplate.ielectricShock == 40 }">III类</c:if>
                </td>
                <td class="text-left" style="width:18%">电子锁型号：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.slocksModel}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">电子锁生产厂商：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.slocksManufacturer}</td>
                <td class="text-left" style="width:18%">广告机屏幕尺寸说明：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.sadDimensions}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">广告机配置描述：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.sadConf}</td>
                <td class="text-left" style="width:18%">压缩机个数：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.icompressorNum}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">压缩机位置：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.scompressorPosition}</td>
                <td class="text-left" style="width:18%">压缩机描述：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.scompressorDesc}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">货道数：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.icargoRoadNum}</td>
                <td class="text-left" style="width:18%">摄像头个数：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.icameraNum}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">主板块数：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.imotherboardNum}</td>
                <td class="text-left" style="width:18%"></td>
                <td class="text-right" style="width:32%"></td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">是否使用扩展GPIO：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${deviceModelTemplate.iisUseExpandGpio == 0 }">否</c:if>
                    <c:if test="${deviceModelTemplate.iisUseExpandGpio == 1 }">是</c:if>
                </td>
                <td class="text-left" style="width:18%">是否检测霍尔值：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${deviceModelTemplate.iisDetectHall == 0 }">否</c:if>
                    <c:if test="${deviceModelTemplate.iisDetectHall == 1 }">是</c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">摄像头方法：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.scameraMethod}</td>
                <td class="text-left" style="width:18%">摄像头型号：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.scameraModel}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">摄像头类型：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.scameraType}</td>
                <td class="text-left" style="width:18%">摄像头品牌：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.scameraBrand}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">视觉服务提供商：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.svisualServiceProvider}</td>
                <td class="text-left" style="width:18%">PCB板型号：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.spcbModel}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">开门引脚序号：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.iopendoorPinSn}</td>
                <td class="text-left" style="width:18%">霍尔引脚序号：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.ihallPinSn}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">门引脚序号：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.idoorPinSn}</td>
                <td class="text-left" style="width:18%">锁芯引脚序号：</td>
                <td class="text-right" style="width:32%">${deviceModelTemplate.ilockCylinder}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">盘货对比方式：</td>
                <td class="text-right" style="width:32%"><c:if test="${deviceModelTemplate.scontrastMode eq 'rawstock' }">原始库存</c:if>
                    <c:if test="${deviceModelTemplate.scontrastMode eq 'openDoor' }">开关门盘货</c:if></td>
                <td class="text-left" style="width:18%"></td>
                <td class="text-right" style="width:32%"></td>
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


