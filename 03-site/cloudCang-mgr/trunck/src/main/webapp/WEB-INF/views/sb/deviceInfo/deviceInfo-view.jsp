<%--
  Created by IntelliJ IDEA.
  User: Alex
  Date: 2018/2/5
  Time: 16:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备信息查看</title>
<link href="${staticSource}/resources/layui/css/layui.css?4" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?1" rel="stylesheet">

</head>

<body>
<div class="ibox-content noibox-content">
    <form class="layui-form" action="#" method="" id="myForm">
        <div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
            <ul class="layui-tab-title">
                <li class="layui-this">设备基础信息</li>
                <li>设备注册信息</li>
                <li>设备管理信息</li>
                <li>设备详细信息</li>
                <li>设备监控信息</li>
            </ul>
            <div class="layui-tab-content">
                <div class="layui-tab-item layui-show">
                    <div class="layui-form-item wflayui-form-item">
                        <table cellspacing="0" border="0">
                            <tr>
                                <td class="text-left" style="width:18%">商户编号：</td>
                                <td class="text-right" style="width:32%">${deviceInfo.smerchantCode}</td>
                                <td class="text-left" style="width:18%">商户名称：</td>
                                <td class="text-right" style="width:32%">${merchantInfo.sname}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%">设备编号：</td>
                                <td class="text-right" style="width:32%">${deviceInfo.scode}</td>
                                <td class="text-left">设备名称：</td>
                                <td class="text-right">${deviceInfo.sname}</td>
                            </tr>
                            <tr>
                                <td class="text-left">设备类型：</td>
                                <td class="text-right">
                                    <c:if test="${deviceInfo.itype == 10 }">传统</c:if>
                                    <c:if test="${deviceInfo.itype == 20 }">RFID射频</c:if>
                                    <c:if test="${deviceInfo.itype == 30 }">视觉</c:if>
                                    <c:if test="${deviceInfo.itype == 40 }">前端视觉+重力</c:if>
                                    <c:if test="${deviceInfo.itype == 50 }">云端识别</c:if>
                                    <c:if test="${deviceInfo.itype == 60 }">云端识别+重力</c:if>
                                </td>
                                <td class="text-left">读写器序列号：</td>
                                <td class="text-right">${deviceInfo.sreaderSerialNumber}</td>
                            </tr>
                            <tr>
                                <td class="text-left">设备型号：</td>
                                <td class="text-right">${deviceInfo.sdeviceModel}</td>
                                <td class="text-left">设备状态：</td>
                                <td class="text-right">
                                    <c:if test="${deviceInfo.istatus == 10 }">未注册</c:if>
                                    <c:if test="${deviceInfo.istatus == 20 }">正常</c:if>
                                    <c:if test="${deviceInfo.istatus == 30 }">故障</c:if>
                                    <c:if test="${deviceInfo.istatus == 40 }">报废</c:if>
                                    <c:if test="${deviceInfo.istatus == 50 }">离线</c:if>
                                </td>
                            </tr>
                            <tr>
                                <td class="text-left">设备分组：</td>
                                <td class="text-right">
                                    ${groupManage.sgroupName}
                                </td>
                                <td class="text-left">运营状态：</td>
                                <td class="text-right">
                                    <c:if test="${deviceInfo.ioperateStatus == 10 }">启用</c:if>
                                    <c:if test="${deviceInfo.ioperateStatus == 20 }">停用</c:if>
                                </td>
                            </tr>
                            <tr>
                                <td class="text-left">合作模式：</td>
                                <td class="text-right">
                                    <c:if test="${deviceInfo.icooperationMode == 10 }">采购</c:if>
                                    <c:if test="${deviceInfo.icooperationMode == 20 }">租用</c:if>
                                    <c:if test="${deviceInfo.icooperationMode == 30 }">自主</c:if>
                                </td>
                                <td class="text-left">是否支持AI设备：</td>
                                <td class="text-right">
                                    <c:if test="${deviceInfo.isupportAi == 0 }">否</c:if>
                                    <c:if test="${deviceInfo.isupportAi == 1 }">是</c:if>
                                </td>
                            </tr>
                            <tr>
                                <td class="text-left">投放详细地址：</td>
                                <td class="text-right">${deviceInfo.sprovinceName}${deviceInfo.scityName}${deviceInfo.sareaName}${deviceInfo.sputAddress}</td>
                                <td class="text-left">货柜类型：</td>
                                <td class="text-right">
                                    <c:if test="${deviceInfo.icontainerType == 10 }">单开门</c:if>
                                    <c:if test="${deviceInfo.icontainerType == 20 }">双开门</c:if>
                                </td>
                            </tr>
                            <tr>
                                <td class="text-left">压缩机类型：</td>
                                <td class="text-right">
                                    <c:if test="${deviceInfo.icompressorType == 10 }">制冷</c:if>
                                    <c:if test="${deviceInfo.icompressorType == 20 }">制热</c:if>
                                    <c:if test="${deviceInfo.icompressorType == 30 }">制冷热</c:if>
                                    <c:if test="${deviceInfo.icompressorType == 40 }">无压缩机</c:if>
                                </td>
                                <td class="text-left">是否广告机：</td>
                                <td class="text-right">
                                    <c:if test="${deviceInfo.iinstallAd == 0 }">否</c:if>
                                    <c:if test="${deviceInfo.iinstallAd == 1 }">是</c:if>
                                </td>
                            </tr>
                            <tr>
                                <td class="text-left">是否支持称重：</td>
                                <td class="text-right">
                                    <c:if test="${deviceInfo.isupportWeigh == 0 }">否</c:if>
                                    <c:if test="${deviceInfo.isupportWeigh == 1 }">是</c:if>
                                </td>
                                <td class="text-left">485地址：</td>
                                <td class="text-right">${deviceInfo.s485Address}
                                </td>
                            </tr>
                            <tr>
                                <td class="text-left">称重类型：</td>
                                <td class="text-right">${deviceInfo.iweightType}</td>
                                <td class="text-left">电子秤浮动值：</td>
                                <td class="text-right">${deviceInfo.ielectronicFloat} </td>
                            </tr>
                             <tr>
                                <td class="text-left">是否开启实时盘货：</td>
                                <td class="text-right">
                                    <c:if test="${deviceInfo.iisOpeningInventory == 0 }">否</c:if>
                                    <c:if test="${deviceInfo.iisOpeningInventory == 1 }">是</c:if>
                                </td>
                                <td class="text-left">系统对接类型：</td>
                                <td class="text-right">
                                    <c:if test="${deviceInfo.idockingType == 10 }">自主研发</c:if>
                                    <c:if test="${deviceInfo.idockingType == 20 }">对接第三方</c:if>
                                </td>
                            </tr>
                            <tr>
                                <td class="text-left">设备层数:</td>
                                <td class="text-right">${deviceModel.ilayerNum}</td>
                                <td class="text-left">屏幕显示类型：</td>
                                <td class="text-right">
                                    <c:if test="${deviceInfo.iscreenDisplayType == 10 }">横屏</c:if>
                                    <c:if test="${deviceInfo.iscreenDisplayType == 20 }">竖屏</c:if>
                                </td>
                            </tr>
                            <tr>
                                <td class="text-left">备注：</td>
                                <td class="text-right">${deviceInfo.sremark} </td>
                                <td class="text-left"></td>
                                <td class="text-right"></td>
                            </tr>
                        </table>
                    </div>
                </div>
                <script type="text/javascript">
                    $(".wflayui-form-item tr:even").addClass("odd");
                </script>
                <div class="layui-tab-item">
                    <div class="layui-form-item wflayui-form-item">
                        <table cellspacing="0" border="0">
                            <tr>
                                <td class="text-left" style="width:18%">注册IP：</td>
                                <td class="text-right" style="width:32%">${deviceRegister.sregIp}</td>
                                <td class="text-left">安全密钥：</td>
                                <td class="text-right">${deviceRegister.ssecurityKey}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%">注册状态：</td>
                                <td class="text-right" style="width:32%">
                                    <c:if test="${deviceRegister.istatus == 10 }">待审核</c:if>
                                    <c:if test="${deviceRegister.istatus == 20 }">审核通过</c:if>
                                    <c:if test="${deviceRegister.istatus == 30 }">审核拒绝</c:if>
                                    <c:if test="${deviceRegister.istatus == 40 }">已注册</c:if>
                                </td>
                                <td class="text-left">审核人：</td>
                                <td class="text-right">${deviceRegister.sauditUser}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%">备注：</td>
                                <td class="text-right" style="width:32%">${deviceRegister.sremark}</td>
                                <td class="text-left" style="width:18%">&nbsp;</td>
                                <td class="text-right" style="width:32%">&nbsp;</td>
                            </tr>
                        </table>
                    </div>
                </div>
                <script type="text/javascript">
                    $(".wflayui-form-item tr:even").addClass("odd");
                </script>

                <div class="layui-tab-item">
                    <div class="layui-form-item wflayui-form-item">
                        <table cellspacing="0" border="0">
                            <tr>
                                <td class="text-left" style="width:18%">所属区域编号：</td>
                                <td class="text-right" style="width:32%">${deviceManage.sareaCode}</td>
                                <td class="text-left" style="width:18%">&nbsp;</td>
                                <td class="text-right" style="width:32%">&nbsp;</td>
                            </tr>
                            <tr>
                                <td class="text-left">区域负责人：</td>
                                <td class="text-right">${deviceManage.sareaPrincipal}</td>
                                <td class="text-left" style="width:18%">区域负责人联系方式：</td>
                                <td class="text-right" style="width:32%">${deviceManage.sareaPrincipalContact}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%">设备负责人：</td>
                                <td class="text-right" style="width:32%">${deviceManage.sdevicePrincipal}</td>
                                <td class="text-left" style="width:18%">设备负责人联系方式：</td>
                                <td class="text-right" style="width:32%">${deviceManage.sdevicePrincipalContact}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%">补货员姓名：</td>
                                <td class="text-right" style="width:32%">${deviceManage.sreplenishment}</td>
                                <td class="text-left" style="width:18%">补货员联系方式：</td>
                                <td class="text-right" style="width:32%">${deviceManage.sreplenishmentContact}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%">设备维护人姓名：</td>
                                <td class="text-right" style="width:32%">${deviceManage.smaintain}</td>
                                <td class="text-left" style="width:18%">维护人联系方式：</td>
                                <td class="text-right" style="width:32%">${deviceManage.smaintainContact}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%">备注：</td>
                                <td class="text-right" style="width:32%">${deviceManage.sremark}</td>
                                <td class="text-left" style="width:18%">&nbsp;</td>
                                <td class="text-right" style="width:32%">&nbsp;</td>
                            </tr>
                        </table>
                    </div>
                </div>
                <script type="text/javascript">
                    $(".wflayui-form-item tr:even").addClass("odd");
                </script>

                <div class="layui-tab-item">
                    <div class="layui-form-item wflayui-form-item">
                        <table cellspacing="0" border="0">
                            <tr>
                                <td class="text-left" style="width:18%">设备核心型号：</td>
                                <td class="text-right" style="width:32%">${deviceModel.sdeviceModel}</td>
                                <td class="text-left" style="width:18%">核心生产厂商：</td>
                                <td class="text-right" style="width:32%">${deviceModel.smanufacturer}</td>
                            </tr>
                            <tr>
                                <td class="text-left">核心配置描述：</td>
                                <td class="text-right">${deviceModel.scoreDesc}</td>
                                <td class="text-left" style="width:18%">整体外形尺寸：</td>
                                <td class="text-right" style="width:32%">${deviceModel.sdimensions}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%">重量：</td>
                                <td class="text-right" style="width:32%">${deviceModel.sweight}</td>
                                <td class="text-left" style="width:18%">额定功率：</td>
                                <td class="text-right" style="width:32%">${deviceModel.sratedPower}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%">商品容量：</td>
                                <td class="text-right" style="width:32%">${deviceModel.sproductCapacity}</td>
                                <td class="text-left" style="width:18%">商品类型：</td>
                                <td class="text-right" style="width:32%">${deviceModel.sproductTypes}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%">日耗电量：</td>
                                <td class="text-right" style="width:32%">${deviceModel.sdailyPower}</td>
                                <td class="text-left" style="width:18%">支持支付方式：</td>
                                <td class="text-right" style="width:32%">${deviceModel.spayWap}</td>
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
                                <td class="text-left" style="width:18%">主板块数：</td>
                                <td class="text-right" style="width:32%">${deviceModel.imotherboardNum}</td>
                                <td class="text-left" style="width:18%"></td>
                                <td class="text-right" style="width:32%"></td>
                            </tr>
                           <%-- <tr>
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
                            </tr>--%>
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
                           <%-- <tr>
                                <td class="text-left" style="width:18%">视觉服务提供商：</td>
                                <td class="text-right" style="width:32%">${deviceModel.svisualServiceProvider}</td>
                                <td class="text-left" style="width:18%">PCB板型号：</td>
                                <td class="text-right" style="width:32%">${deviceModel.spcbModel}</td>
                            </tr>--%>
                            <tr>
                                <td class="text-left" style="width:18%">视觉服务提供商：</td>
                                <td class="text-right" style="width:32%">${deviceModel.svisualServiceProvider}</td>
                                <td class="text-left" style="width:18%">是否检测霍尔值：</td>
                                <td class="text-right" style="width:32%">
                                    <c:if test="${deviceModel.iisDetectHall == 0 }">否</c:if>
                                    <c:if test="${deviceModel.iisDetectHall == 1 }">是</c:if>
                                </td>
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
                <script type="text/javascript">
                    $(".wflayui-form-item tr:even").addClass("odd");
                </script>

                <div class="layui-tab-item">
                    <div class="layui-form-item wflayui-form-item">
                        <table cellspacing="0" border="0">
                            <tr>
                                <td class="text-left" style="width:18%">每次盘活次数：</td>
                                <td class="text-right" style="width:32%">${monitorDataConf.iinventoryNum}</td>
                                <td class="text-left" style="width:18%">上次盘货时间：</td>
                                <td class="text-right" style="width:32%"><fmt:formatDate value="${monitorDataConf.tinventoryTime}"
                                                                                         pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            </tr>
                            <tr>
                                <td class="text-left">自动盘货开始时间：</td>
                                <td class="text-right">${monitorDataConf.tinventoryBeginTime}</td>
                                <td class="text-left" style="width:18%">自动盘货结束时间：</td>
                                <td class="text-right" style="width:32%">${monitorDataConf.tinventoryEndTime}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%">实时温度1：</td>
                                <td class="text-right" style="width:32%">${monitorDataConf.sltemperature}</td>
                                <td class="text-left" style="width:18%">实时温度2：</td>
                                <td class="text-right" style="width:32%">${monitorDataConf.srtemperature}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%">控制温度1：</td>
                                <td class="text-right" style="width:32%">${monitorDataConf.slcontrolTemperature}</td>
                                <td class="text-left" style="width:18%">控制温度2：</td>
                                <td class="text-right" style="width:32%">${monitorDataConf.srcontrolTemperature}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%">温度控制开始时间1：</td>
                                <td class="text-right" style="width:32%">${monitorDataConf.tlcontrolBeginTime}</td>
                                <td class="text-left" style="width:18%">温度控制结束时间1：</td>
                                <td class="text-right" style="width:32%">${monitorDataConf.tlcontrolEndTime}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%">温度控制开始时间2：</td>
                                <td class="text-right" style="width:32%">${monitorDataConf.trcontrolBeginTime}</td>
                                <td class="text-left" style="width:18%">温度控制结束时间2：</td>
                                <td class="text-right" style="width:32%">${monitorDataConf.trcontrolEndTime}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%">定时开关机状态：</td>
                                <td class="text-right" style="width:32%">
                                    <c:if test="${monitorDataConf.iswitchStatus == 0 }">否</c:if>
                                    <c:if test="${monitorDataConf.iswitchStatus == 1 }">是</c:if>
                                </td>
                                <td class="text-left" style="width:18%">实时音量：</td>
                                <td class="text-right" style="width:32%">${monitorDataConf.iactualVolume}</td>
                            </tr>
                            <tr>
                                <td class="text-left" style="width:18%">开机时间：</td>
                                <td class="text-right" style="width:32%">${monitorDataConf.tbootTime}</td>
                                <td class="text-left" style="width:18%">关机时间：</td>
                                <td class="text-right" style="width:32%">${monitorDataConf.tshutDownTime}</td>
                            </tr>
                        </table>
                    </div>
                </div>
                <script type="text/javascript">
                    $(".wflayui-form-item tr:even").addClass("odd");
                </script>
            </div>
        </div>
    </form>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script>
    layui.use('element', function () {
        var $ = layui.jquery, element = layui.element;//Tab的切换功能，切换事件监听等，需要依赖element模块
    });
</script>
</body>
</html>


