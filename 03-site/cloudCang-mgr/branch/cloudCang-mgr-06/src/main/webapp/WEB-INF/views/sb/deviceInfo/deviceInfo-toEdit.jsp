<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备编辑</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/device/info/edit" method="post" id="myForm">
        <div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
            <ul class="layui-tab-title">
                <li class="layui-this">设备基础信息</li>
                <li>设备详细信息</li>
                <li>设备管理信息</li>
                <li>设备监控信息</li>
            </ul>
            <div class="layui-tab-content">
                <div class="layui-tab-item layui-show">
                    <div class="layui-form-item">
                        <%--隐藏属性--%>
                        <input type="hidden" id="smerchantId" name="smerchantId" value="${deviceInfo.smerchantId}"/><%--商户ID--%>
                        <input type="hidden" id="smerchantCode" name="smerchantCode"
                               value="${deviceInfo.smerchantCode}"/><%--商户code--%>
                        <input type="hidden" id="scameraMethod1" name="scameraMethod1"
                               value="${deviceModel.scameraMethod}"/><%--摄像头方法--%>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label">是否使用模板</label>
                                <div class="layui-input-inline">
                                    <select name="infoTemplate" id="infoTemplate" class="state"
                                            lay-filter="infoTemplate">
                                        <option value="">不使用</option>
                                        <c:forEach items="${deviceInfoTemplateList}" var="vo">
                                            <option value="${vo.id}">${vo.sname}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label">商户名称</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="smerchantName" id="smerchantName" datatype="*"
                                           nullmsg="请选择商户"
                                           value="${merchantInfo.sname}" readonly
                                           class="layui-input"/>
                                </div>
                                <button class="layui-btn" id="btn1" style="display: inline-block;float: left;"
                                        type="button"
                                        data-type="sel">选择
                                </button>
                            </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label">商户编号</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="smerchantCodes" id="smerchantCodes"
                                           value="${deviceInfo.smerchantCode}" class="layui-input" disabled/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label">设备名称</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="sname" id="sname" datatype="*" nullmsg="请输入设备名称"
                                           value="${deviceInfo.sname}"
                                           class="layui-input"/>
                                </div>
                            </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label">设备类型</label>
                                <div class="layui-input-inline">
                                    <cang:select type="dic" name="itype" id="itype" entire="true"
                                                 exp="datatype=\"*\" nullmsg=\"请选择设备类型\""
                                                 layFilter="itype" value="${deviceInfo.itype}" groupNo="SP000144"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label">读写器序列号</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="sreaderSerialNumber" id="sreaderSerialNumber" datatype="*"
                                           nullmsg="请输入读写器序列号"
                                           value="${deviceInfo.sreaderSerialNumber}" class="layui-input"/>
                                </div>
                            </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label">设备型号</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="sdeviceModel" datatype="*" nullmsg="请输入设备型号"
                                           id="sdeviceModel"
                                           value="${deviceInfo.sdeviceModel}" class="layui-input"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label">投放场景</label>
                                <div class="layui-input-inline">
                                    <cang:select type="dic" name="sputScenes" id="sputScenes" entire="true"
                                                 laySearch="1"
                                                 value="${deviceInfo.sputScenes}" groupNo="SP000143"/>
                                </div>
                            </div>
                            <c:if test="${ !(deviceInfo.istatus == 10)}">
                                <div class="layui-col-md6">
                                    <label class="layui-form-label">设备状态</label>
                                    <div class="layui-input-inline">
                                        <cang:select type="list" name="istatus" id="istatus" entire="true"
                                                     exp="datatype=\"*\" nullmsg=\"请选择设备状态\""
                                                     value="${deviceInfo.istatus}" list="{20:正常,30:故障,40:报废,50:离线}"/>
                                    </div>
                                </div>
                            </c:if>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label">合作模式</label>
                                <div class="layui-input-inline">
                                    <cang:select type="dic" name="icooperationMode" id="icooperationMode" entire="true"
                                                 layFilter="icooperationMode"
                                                 exp="datatype=\"*\" nullmsg=\"请选择合作模式\""
                                                 value="${deviceInfo.icooperationMode}" groupNo="SP000104"/>
                                </div>
                            </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label">货柜类型</label>
                                <div class="layui-input-inline">
                                    <cang:select type="list" name="icontainerType" id="icontainerType" entire="true"
                                                 value="${deviceInfo.icontainerType}" list="{10:单开门,20:双开门}"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label">压缩机类型</label>
                                <div class="layui-input-inline">
                                    <cang:select type="list" name="icompressorType" id="icompressorType" entire="true"
                                                 value="${deviceInfo.icompressorType}"
                                                 list="{10:制冷,20:制热,30:制冷热,40:无压缩机}"/>
                                </div>
                            </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label">是否广告机</label>
                                <div class="layui-input-inline">
                                    <cang:select type="list" name="iinstallAd" id="iinstallAd" entire="true"
                                                 value="${deviceInfo.iinstallAd}" list="{0:否,1:是}"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label">设备层数</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="ilayerNum" id="ilayerNum" value="${deviceModel.ilayerNum}"
                                           class="layui-input"/>
                                </div>
                            </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label">系统对接类型</label>
                                <div class="layui-input-inline">
                                    <cang:select type="list" name="idockingType" id="idockingType"
                                                 entire="true"
                                                 exp="datatype=\"*\" nullmsg=\"请选择系统对接类型\""
                                                 layFilter="idockingType" value="${deviceInfo.idockingType}"
                                                 list="{10:自主研发,20:第三方}"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label">是否支持AI设备</label>
                                <div class="layui-input-inline">
                                    <cang:select type="list" name="isupportAi" id="isupportAi" entire="true"
                                                 exp="datatype=\"*\" nullmsg=\"请选择是否支持\""
                                                 layFilter="isupportAi" value="${deviceInfo.isupportAi}"
                                                 list="{0:否,1:是}"/>
                                </div>
                            </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label">是否开启实时盘货</label>
                                <div class="layui-input-inline">
                                    <cang:select type="list" name="iisOpeningInventory" id="iisOpeningInventory"
                                                 entire="true"
                                                 exp="datatype=\"*\" nullmsg=\"请选择是否支持\""
                                                 layFilter="iisOpeningInventory"
                                                 value="${deviceInfo.iisOpeningInventory}" list="{0:否,1:是}"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">

                            <div class="layui-col-md6">
                                <label class="layui-form-label">是否支持称重</label>
                                <div class="layui-input-inline">
                                    <cang:select type="list" name="isupportWeigh" id="isupportWeigh" entire="true"
                                                 exp="datatype=\"*\" nullmsg=\"请选择是否支持称重\""
                                                 layFilter="isupportWeigh" value="${deviceInfo.isupportWeigh}"
                                                 list="{0:否,1:是}"/>
                                </div>
                            </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label">485地址</label>
                                <div class="layui-input-inline">
                                    <%-- <input type="text" name="s485Address" id="s485Address" value="${deviceInfo.s485Address}"
                                            class="layui-input"/>--%>
                                    <cang:select type="dic" name="s485Address" id="s485Address"
                                                 entire="true"
                                                 value="${deviceInfo.s485Address}" groupNo="SP000178"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <c:if test="${ deviceInfo.isupportWeigh == 0}">
                            <div class="layui-col-md6" style="display: none;" id="com_elec_Float">
                                </c:if>
                                <c:if test="${ deviceInfo.isupportWeigh == 1}">
                                <div class="layui-col-md6" id="com_elec_Float">
                                    </c:if>
                                    <label class="layui-form-label">电子秤浮动值</label>
                                    <div class="layui-input-inline">
                                        <input type="text" name="ielectronicFloat" id="ielectronicFloat"
                                               value="${deviceInfo.ielectronicFloat}" nullmsg="请输入带1-2位小数的正数"
                                               datatype="/^[0-9]+(\.[0-9]{1,2})?$/"
                                               ignore="ignore" placeholder="默认值：0.00" class="layui-input"/>
                                    </div>
                                    <c:if test="${ deviceInfo.isupportWeigh == 0}">
                                </div>
                                </c:if>
                                <c:if test="${ deviceInfo.isupportWeigh == 1}">
                            </div>
                            </c:if>
                            <c:if test="${ deviceInfo.isupportWeigh == 0}">
                            <div class="layui-col-md6" style="display: none;" id="iweightTypeTe">
                                </c:if>
                                <c:if test="${ deviceInfo.isupportWeigh == 1}">
                                <div class="layui-col-md6" id="iweightTypeTe">
                                    </c:if>
                                    <label class="layui-form-label">称重类型</label>
                                    <div class="layui-input-inline">
                                        <cang:select type="dic" name="iweightType" id="iweightType"
                                                     layFilter="iweightType" entire="true"
                                                     value="${deviceInfo.iweightType}" groupNo="SP000177"/>
                                    </div>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <div class="layui-col-md6">
                                    <label class="layui-form-label">投放省份</label>
                                    <div class="layui-input-inline">
                                        <select name="sprovinceName" id="sprovinceName" class="state" lay-search=""
                                                datatype="*" nullmsg="请选择投放省份" lay-filter="province">
                                            <option value="">请选择</option>
                                            <c:forEach items="${provinceSet}" var="vo">
                                                <option value="${vo.id}_${vo.sname}"
                                                        <c:if test="${vo.id eq deviceInfo.sprovinceId}">selected</c:if>>${vo.sname}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="layui-col-md6">
                                    <label class="layui-form-label">投放城市</label>
                                    <div class="layui-input-inline">
                                        <select name="scityName" id="scityName" class="state" lay-search=""
                                                datatype="*" nullmsg="请选择投放城市" lay-filter="city">
                                            <option value="">请选择</option>
                                            <c:forEach items="${citySet}" var="vo">
                                                <option value="${vo.id}_${vo.sname}"
                                                        <c:if test="${vo.id eq deviceInfo.scityId}">selected</c:if>>${vo.sname}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <div class="layui-col-md6">
                                    <label class="layui-form-label">投放区县</label>
                                    <div class="layui-input-inline">
                                        <select name="sareaName" id="sareaName" class="state" lay-search=""
                                                datatype="*" nullmsg="请选择投放区县" lay-filter="town">
                                            <option value="">请选择</option>
                                            <c:forEach items="${townsSet}" var="vo">
                                                <option value="${vo.id}_${vo.sname}"
                                                        <c:if test="${vo.id eq deviceInfo.sareaId}">selected</c:if>>${vo.sname}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="layui-col-md6">
                                    <label class="layui-form-label">屏幕显示类型</label>
                                    <div class="layui-input-inline">
                                        <cang:select type="dic" groupNo="SP000175" entire="true"
                                                     exp="datatype=\"*\" nullmsg=\"请选择屏幕显示类型\""
                                                     value="${deviceInfo.iscreenDisplayType}" entireName="请选择屏幕显示类型"
                                                     name="iscreenDisplayType" id="iscreenDisplayType"/>
                                    </div>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <c:if test="${ deviceInfo.itype != 50 and deviceInfo.itype != 60}">
                                <div class="layui-col-md6" style="display: none;" id="recognitionModelCode">
                                    </c:if>
                                    <c:if test="${ deviceInfo.itype == 50 or deviceInfo.itype == 60}">
                                    <div class="layui-col-md6" id="recognitionModelCode">
                                        </c:if>
                                        <label class="layui-form-label">识别模型编号</label>
                                        <div class="layui-input-inline">
                                            <input type="text" name="srecognitionModelCode" readonly="true"
                                                   id="srecognitionModelCode"
                                                   value="${deviceInfo.srecognitionModelCode}"
                                                   class="layui-input"/>
                                        </div>
                                            <button class="layui-btn" id="btn2" style="display: inline-block;float: left;" type="button"
                                                data-type="sel2">选择
                                        </button>
                                    </div>
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">是否绑定垃圾箱</label>
                                        <div class="layui-input-inline">
                                            <cang:select type="list" name="iisBindTrash" id="iisBindTrash" entire="true"
                                                         exp="datatype=\"*\" nullmsg=\"请选择是否绑定垃圾箱\""
                                                         layFilter="isBindTrash"
                                                         value="${deviceInfo.iisBindTrash}"
                                                         list="{0:否,1:是}"/>
                                        </div>
                                    </div>
                                </div>
                                <c:if test="${empty deviceInfo.iisBindTrash  or deviceInfo.iisBindTrash eq 0 }">
                                <div class="layui-form-item" style="display: none;" id="trashModel">
                                    </c:if>
                                    <c:if test="${not empty deviceInfo.iisBindTrash  and  deviceInfo.iisBindTrash eq 1 }">
                                    <div class="layui-form-item" id="trashModel">
                                        </c:if>
                                        <div class="layui-col-md6">
                                            <label class="layui-form-label">智能垃圾箱编号</label>
                                            <div class="layui-input-inline">
                                                <input type="text" name="strashCode" id="strashCode"
                                                       value="${deviceInfo.strashCode}"
                                                       class="layui-input"/>
                                            </div>
                                        </div>
                                        <div class="layui-col-md6">
                                            <label class="layui-form-label">智能垃圾箱品牌</label>
                                            <div class="layui-input-inline">
                                                <input type="text" name="strashBrand" id="strashBrand"
                                                       value="${deviceInfo.strashBrand}"
                                                       class="layui-input"/>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="layui-form-item">
                                        <div class="layui-col-md6">
                                            <label class="layui-form-label">投放详细地址</label>
                                            <div class="layui-input-inline">
                                    <textarea class="layui-textarea" name="sputAddress" placeholder="必填..." datatype="*"
                                              nullmsg="请输入地址" id="sputAddress" value=""
                                              style="width: 720px;height: 100px">${deviceInfo.sputAddress}</textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="layui-form-item">
                                        <div class="layui-col-md6">
                                            <label class="layui-form-label">备注</label>
                                            <div class="layui-input-inline">
                                    <textarea class="layui-textarea" name="sremark" id="sremark" value=""
                                              style="width: 720px;height: 100px">${deviceInfo.sremark}</textarea>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="layui-tab-item">
                                <div class="layui-form-item">
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">是否使用模板</label>
                                        <div class="layui-input-inline">
                                            <select name="modelTemplate" id="modelTemplate" class="state"
                                                    lay-filter="modelTemplate">
                                                <option value="">不使用</option>
                                                <c:forEach items="${deviceModelTemplateList}" var="model">
                                                    <option value="${model.id}">${model.sname}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">设备核心型号</label>
                                        <div class="layui-input-inline">
                                            <input type="text" name="sdeviceCoreModel" id="sdeviceCoreModel"
                                                   value="${deviceModel.sdeviceModel}"
                                                   class="layui-input"/>
                                        </div>
                                    </div>
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">核心生产厂商</label>
                                        <div class="layui-input-inline">
                                            <input type="text" name="smanufacturer" id="smanufacturer"
                                                   value="${deviceModel.smanufacturer}"
                                                   class="layui-input"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">核心配置描述</label>
                                        <div class="layui-input-inline">
                                            <input type="text" name="scoreDesc" id="scoreDesc"
                                                   value="${deviceModel.scoreDesc}"
                                                   class="layui-input"/>
                                        </div>
                                    </div>
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">整体外形尺寸</label>
                                        <div class="layui-input-inline">
                                            <input type="text" name="sdimensions" id="sdimensions"
                                                   value="${deviceModel.sdimensions}"
                                                   class="layui-input"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">重量</label>
                                        <div class="layui-input-inline">
                                            <input type="text" name="sweight" id="sweight"
                                                   value="${deviceModel.sweight}"
                                                   class="layui-input"/>
                                        </div>
                                    </div>
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">额定功率</label>
                                        <div class="layui-input-inline">
                                            <input type="text" name="sratedPower" id="sratedPower"
                                                   value="${deviceModel.sratedPower}"
                                                   class="layui-input"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">商品容量</label>
                                        <div class="layui-input-inline">
                                            <input type="text" name="sproductCapacity" id="sproductCapacity"
                                                   value="${deviceModel.sproductCapacity}" class="layui-input"/>
                                        </div>
                                    </div>
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">商品类型</label>
                                        <div class="layui-input-inline">
                                            <input type="text" name="sproductTypes" id="sproductTypes"
                                                   value="${deviceModel.sproductTypes}" class="layui-input"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">日耗电量</label>
                                        <div class="layui-input-inline">
                                            <input type="text" name="sdailyPower" id="sdailyPower"
                                                   value="${deviceModel.sdailyPower}"
                                                   class="layui-input"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">支持支付方式</label>
                                        <div class="layui-input-inline">
                                            <input type="text" name="spayWap" id="spayWap"
                                                   value="${deviceModel.spayWap}"
                                                   class="layui-input"/>
                                        </div>
                                    </div>
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">防触电类型</label>
                                        <div class="layui-input-inline">
                                            <cang:select type="list" name="ielectricShock" id="ielectricShock"
                                                         entire="true"
                                                         value="${deviceModel.ielectricShock}"
                                                         list="{10:0类,20:I类,30:II类,40:III类}"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">电子锁型号</label>
                                        <div class="layui-input-inline">
                                            <%--<input type="text" name="slocksModel" id="slocksModel"
                                                   value="${deviceModel.slocksModel}"
                                                   class="layui-input"/>--%>
                                            <cang:select type="dic" name="slocksModel" id="slocksModel"
                                                         entire="true"
                                                         value="${deviceModel.slocksModel}" groupNo="SP000179"/>
                                        </div>
                                    </div>
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">电子锁生产厂商</label>
                                        <div class="layui-input-inline">
                                            <input type="text" name="slocksManufacturer" id="slocksManufacturer"
                                                   value="${deviceModel.slocksManufacturer}" class="layui-input"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">广告机屏幕尺寸说明</label>
                                        <div class="layui-input-inline">
                                            <input type="text" name="sadDimensions" id="sadDimensions"
                                                   value="${deviceModel.sadDimensions}"
                                                   class="layui-input"/>
                                        </div>
                                    </div>
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">广告机配置描叙</label>
                                        <div class="layui-input-inline">
                                            <input type="text" name="sadConf" id="sadConf"
                                                   value="${deviceModel.sadConf}"
                                                   class="layui-input"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">压缩机个数</label>
                                        <div class="layui-input-inline">
                                            <input type="text" name="icompressorNum" id="icompressorNum"
                                                   value="${deviceModel.icompressorNum}"
                                                   class="layui-input"/>
                                        </div>
                                    </div>
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">压缩机位置</label>
                                        <div class="layui-input-inline">
                                            <input type="text" name="scompressorPosition" id="scompressorPosition"
                                                   value="${deviceModel.scompressorPosition}" class="layui-input"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">压缩机描叙</label>
                                        <div class="layui-input-inline">
                                            <input type="text" name="scompressorDesc" id="scompressorDesc"
                                                   value="${deviceModel.scompressorDesc}" class="layui-input"/>
                                        </div>
                                    </div>
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">货道数</label>
                                        <div class="layui-input-inline">
                                            <input type="text" name="icargoRoadNum" id="icargoRoadNum"
                                                   value="${deviceModel.icargoRoadNum}"
                                                   class="layui-input"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">摄像头个数</label>
                                        <div class="layui-input-inline">
                                            <input type="text" name="icameraNum" id="icameraNum"
                                                   value="${deviceModel.icameraNum}" class="layui-input"/>
                                        </div>
                                    </div>
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">主板块数</label>
                                        <div class="layui-input-inline">
                                            <input type="text" name="imotherboardNum" id="imotherboardNum"
                                                   value="${deviceModel.imotherboardNum}"
                                                   class="layui-input"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">开锁方式</label>
                                        <div class="layui-input-inline">
                                            <cang:select type="dic" name="iisUseExpandGpio" id="iisUseExpandGpio"
                                                         entire="true"
                                                         exp="datatype=\"*\" nullmsg=\"请选择开锁方式\""
                                                         layFilter="iunlockType" value="${deviceModel.iisUseExpandGpio}"
                                                         groupNo="SP000173"/>
                                        </div>
                                    </div>
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">是否检测霍尔值</label>
                                        <div class="layui-input-inline">
                                            <cang:select type="list" name="iisDetectHall" id="iisDetectHall"
                                                         entire="true"
                                                         layFilter="iisDetectHall"
                                                         exp="datatype=\"*\" nullmsg=\"请选择是否检测霍尔值\""
                                                         value="${deviceModel.iisDetectHall}" list="{0:否,1:是}"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">摄像头方法</label>
                                        <div class="layui-input-inline">
                                            <cang:select type="dic" name="scameraMethod" id="scameraMethod"
                                                         entire="true"
                                                         exp="datatype=\"*\" nullmsg=\"请选择摄像头方法\""
                                                         layFilter="scameraMethod" value="${deviceModel.scameraModel}"
                                                         groupNo="SP000171"/>
                                        </div>
                                    </div>
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">摄像头型号</label>
                                        <div class="layui-input-inline">
                                            <input type="text" name="scameraModel" id="scameraModel"
                                                   value="${deviceModel.scameraModel}"
                                                   class="layui-input" readonly/>
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">摄像头类型</label>
                                        <div class="layui-input-inline">
                                            <input type="text" name="scameraType" id="scameraType"
                                                   value="${deviceModel.scameraType}"
                                                   class="layui-input" readonly/>
                                        </div>
                                    </div>
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">摄像头品牌</label>
                                        <div class="layui-input-inline">
                                            <input type="text" name="scameraBrand" id="scameraBrand"
                                                   value="${deviceModel.scameraBrand}"
                                                   class="layui-input" readonly/>
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">视觉服务提供商</label>
                                        <div class="layui-input-inline">
                                            <cang:select type="dic" name="svisualServiceProvider"
                                                         id="svisualServiceProvider"
                                                         entire="true"
                                                         layFilter="svisualServiceProvider"
                                                         value="${deviceModel.svisualServiceProvider}"
                                                         groupNo="SP000172"/>
                                        </div>
                                    </div>
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">PCB板型号</label>
                                        <div class="layui-input-inline">
                                            <cang:select type="dic" name="spcbModel" id="spcbModel" entire="true"
                                                         exp="datatype=\"*\" nullmsg=\"请选择PCB板型号\""
                                                         layFilter="spcbModel" value="${deviceModel.spcbModel}"
                                                         groupNo="SP000174"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">开门引脚序号</label>
                                        <div class="layui-input-inline">
                                            <input type="number" name="iopendoorPinSn" id="iopendoorPinSn"
                                                   value="${deviceModel.iopendoorPinSn}"
                                                   placeholder="必填..." datatype="*" nullmsg="请输入开门引脚序号"
                                                   class="layui-input"/>
                                        </div>
                                    </div>
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">霍尔引脚序号</label>
                                        <div class="layui-input-inline">
                                            <input type="number" name="ihallPinSn" id="ihallPinSn"
                                                   value="${deviceModel.ihallPinSn}"
                                                   placeholder="必填..." datatype="*" nullmsg="请输入霍尔引脚序号"
                                                   class="layui-input"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">门引脚序号</label>
                                        <div class="layui-input-inline">
                                            <input type="number" name="idoorPinSn" id="idoorPinSn"
                                                   value="${deviceModel.idoorPinSn}"
                                                   placeholder="必填..." datatype="*" nullmsg="请输入门引脚序号"
                                                   class="layui-input"/>
                                        </div>
                                    </div>
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">锁芯引脚序号</label>
                                        <div class="layui-input-inline">
                                            <input type="number" name="ilockCylinder" id="ilockCylinder"
                                                   value="${deviceModel.ilockCylinder}"
                                                   placeholder="必填..." datatype="*" nullmsg="请输入锁芯引脚序号"
                                                   class="layui-input"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">盘货对比方式</label>
                                        <div class="layui-input-inline">
                                            <cang:select type="list" name="scontrastMode" id="scontrastMode"
                                                         entire="true"
                                                         value="${deviceModel.scontrastMode}"
                                                         list="{rawstock:原始库存,openDoor:开关门盘货}"/>
                                        </div>
                                    </div>
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">是否支持分层盘货</label>
                                        <div class="layui-input-inline">
                                            <cang:select type="list" name="isupportSingleLayer" id="isupportSingleLayer"
                                                         entire="true"
                                                         value="${deviceModel.isupportSingleLayer}"
                                                         list="{0:否,1:是}"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="layui-tab-item">
                                <div class="layui-form-item">
                                    <div class="layui-col-md6">
                                        <label class="layui-form-label">是否使用模板</label>
                                        <div class="layui-input-inline">
                                            <select name="manageTemplate" id="manageTemplate" class="state"
                                                    lay-filter="manageTemplate">
                                                <option value="">不使用</option>
                                                <c:forEach items="${deviceManageTemplateList}" var="manage">
                                                    <option value="${manage.id}">${manage.sname}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="layui-form-item">
                                        <div class="layui-col-md6">
                                            <label class="layui-form-label">所属区域</label>
                                            <div class="layui-input-inline">
                                                <cang:select type="dic" name="sareaCode" id="sareaCode" entire="true"
                                                             value="${deviceManage.sareaCode}" groupNo="SP000145"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="layui-form-item">
                                        <div class="layui-col-md6">
                                            <label class="layui-form-label">区域负责人</label>
                                            <div class="layui-input-inline">
                                                <input type="text" name="sareaPrincipal" id="sareaPrincipal"
                                                       value="${deviceManage.sareaPrincipal}"
                                                       class="layui-input"/>
                                            </div>
                                        </div>
                                        <div class="layui-col-md6">
                                            <label class="layui-form-label">区域负责人联系方式</label>
                                            <div class="layui-input-inline">
                                                <input type="text" name="sareaPrincipalContact"
                                                       id="sareaPrincipalContact"
                                                       value="${deviceManage.sareaPrincipalContact}"
                                                       class="layui-input"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="layui-form-item">
                                        <div class="layui-col-md6">
                                            <label class="layui-form-label">设备负责人</label>
                                            <div class="layui-input-inline">
                                                <input type="text" name="sdevicePrincipal" id="sdevicePrincipal"
                                                       value="${deviceManage.sdevicePrincipal}" class="layui-input"/>
                                            </div>
                                        </div>
                                        <div class="layui-col-md6">
                                            <label class="layui-form-label">设备负责人联系方式</label>
                                            <div class="layui-input-inline">
                                                <input type="text" <%--lay-verify="unCheckPhone|phone"--%>
                                                       name="sdevicePrincipalContact"
                                                       id="sdevicePrincipalContact"
                                                       value="${deviceManage.sdevicePrincipalContact}"
                                                       class="layui-input"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="layui-form-item">
                                        <div class="layui-col-md6">
                                            <label class="layui-form-label">补货员姓名</label>
                                            <div class="layui-input-inline">
                                                <input type="text" name="sreplenishment" id="sreplenishment"
                                                       value="${deviceManage.sreplenishment}"
                                                       class="layui-input"/>
                                            </div>
                                        </div>
                                        <div class="layui-col-md6">
                                            <label class="layui-form-label">补货员联系方式</label>
                                            <div class="layui-input-inline">
                                                <input type="text" <%--lay-verify="number"--%>
                                                       name="sreplenishmentContact"
                                                       id="sreplenishmentContact"
                                                       value="${deviceManage.sreplenishmentContact}"
                                                       class="layui-input"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="layui-form-item">
                                        <div class="layui-col-md6">
                                            <label class="layui-form-label">设备维护人姓名</label>
                                            <div class="layui-input-inline">
                                                <input type="text" name="smaintain" id="smaintain"
                                                       value="${deviceManage.smaintain}"
                                                       class="layui-input"/>
                                            </div>
                                        </div>
                                        <div class="layui-col-md6">
                                            <label class="layui-form-label">维护人联系方式</label>
                                            <div class="layui-input-inline">
                                                <input type="text" <%--lay-verify="number"--%> name="smaintainContact"
                                                       id="smaintainContact"
                                                       value="${deviceManage.smaintainContact}" class="layui-input"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="layui-tab-item">
                                <div class="layui-form-item">
                                    <div class="layui-form-item">
                                    </div>
                                    <div class="layui-form-item">
                                        <div class="layui-col-md6">
                                            <label class="layui-form-label">自动盘货开始时间</label>
                                            <div class="layui-input-inline">
                                                <input type="text" name="tinventoryBeginTime" id="tinventoryBeginTime"
                                                       placeholder="${monitorDataConf.tinventoryBeginTime}"
                                                       class="layui-input"/>
                                            </div>
                                        </div>
                                        <div class="layui-col-md6">
                                            <label class="layui-form-label">自动盘货结束时间</label>
                                            <div class="layui-input-inline">
                                                <input type="text" name="tinventoryEndTime" id="tinventoryEndTime"
                                                       placeholder="${monitorDataConf.tinventoryEndTime}"
                                                       class="layui-input"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="layui-form-item">
                                        <div class="layui-col-md6">
                                            <label class="layui-form-label">控制温度1</label>
                                            <div class="layui-input-inline">
                                                <input type="text" name="slcontrolTemperature" id="slcontrolTemperature"
                                                       value="${monitorDataConf.slcontrolTemperature}"
                                                       class="layui-input"/>
                                            </div>
                                        </div>
                                        <div class="layui-col-md6">
                                            <label class="layui-form-label">控制温度2</label>
                                            <div class="layui-input-inline">
                                                <input type="text" name="srcontrolTemperature" id="srcontrolTemperature"
                                                       value="${monitorDataConf.srcontrolTemperature}"
                                                       class="layui-input"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="layui-form-item">
                                        <div class="layui-col-md6">
                                            <label class="layui-form-label">温度控制开始时间1</label>
                                            <div class="layui-input-inline">
                                                <input type="text" name="tlcontrolBeginTime" id="tlcontrolBeginTime"
                                                       placeholder="${monitorDataConf.tlcontrolBeginTime}"
                                                       class="layui-input"/>
                                            </div>
                                        </div>
                                        <div class="layui-col-md6">
                                            <label class="layui-form-label">温度控制结束时间1</label>
                                            <div class="layui-input-inline">
                                                <input type="text" name="tlcontrolEndTime" id="tlcontrolEndTime"
                                                       placeholder="${monitorDataConf.tlcontrolEndTime}"
                                                       class="layui-input"/>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="layui-form-item">
                                        <div class="layui-col-md6">
                                            <label class="layui-form-label">温度控制开始时间2</label>
                                            <div class="layui-input-inline">
                                                <input type="text" name="trcontrolBeginTime" id="trcontrolBeginTime"
                                                       placeholder="${monitorDataConf.trcontrolBeginTime}"
                                                       class="layui-input"/>
                                            </div>
                                        </div>
                                        <div class="layui-col-md6">
                                            <label class="layui-form-label">温度控制结束时间2</label>
                                            <div class="layui-input-inline">
                                                <input type="text" name="trcontrolEndTime" id="trcontrolEndTime"
                                                       placeholder="${monitorDataConf.trcontrolEndTime}"
                                                       class="layui-input"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="layui-form-item">
                                        <div class="layui-col-md6">
                                            <label class="layui-form-label">定时开关机状态</label>
                                            <div class="layui-input-inline">
                                                <cang:select type="list" name="iswitchStatus" id="iswitchStatus"
                                                             entire="true"
                                                             value="${monitorDataConf.iswitchStatus}" list="{0:否,1:是}"/>
                                            </div>
                                        </div>
                                        <div class="layui-col-md6">
                                            <label class="layui-form-label">每次盘货次数</label>
                                            <div class="layui-input-inline">
                                                <input type="text" name="iinventoryNum" id="iinventoryNum"
                                                       value="${monitorDataConf.iinventoryNum}"
                                                       class="layui-input"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="layui-form-item">
                                        <div class="layui-col-md6">
                                            <label class="layui-form-label">开机时间</label>
                                            <div class="layui-input-inline">
                                                <input type="text" name="tbootTime" id="tbootTime"
                                                       placeholder="${monitorDataConf.tbootTime}"
                                                       class="layui-input"/>
                                            </div>
                                        </div>
                                        <div class="layui-col-md6">
                                            <label class="layui-form-label">关机时间</label>
                                            <div class="layui-input-inline">
                                                <input type="text" name="tshutDownTime" id="tshutDownTime"
                                                       class="layui-input"
                                                       placeholder="${monitorDataConf.tshutDownTime}">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="layui-form-item">

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item fixed-bottom">
                        <div class="layui-input-block">
                            <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                            <button class="layui-btn" id="myFormSub">保存</button>
                        </div>
                    </div>
                    <input type="hidden" id="deviceInfoId" name="deviceInfoId" value="${deviceInfo.id}"/>
                    <input type="hidden" id="scode" name="scode" value="${deviceInfo.scode}"/>
                    <input type="hidden" id="oldGroupId" name="oldGroupId" value="${groupRelationship.id}"/><%--设备分组关系ID--%>
                    <input type="hidden" id="deviceManageId" name="deviceManageId" value="${deviceManage.id}"/><%--设备管理ID--%>
                    <input type="hidden" id="deviceModelId" name="deviceModelId" value="${deviceModel.id}"/><%--设备详细信息ID--%>
                    <input type="hidden" id="monitorDataConfId" name="monitorDataConfId" value="${monitorDataConf.id}"/><%--设备配置ID--%>
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
<script src="${staticSource}/resources/js/validform_min.js" type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var form
    layui.use(['form', 'layedit', 'laydate', 'element'], function () {
        form = layui.form;

        //选择商户
        var $ = layui.$, active = {
                sel: function () {
                    showLayer("选择商户", ctx + "/merchantInfo/radioSelectList", {area: ['90%', '80%']});
                },
                sel2: function () {
                    var smerchantCode = $("#smerchantCode").val();
                    var smerchantId = $("#smerchantId").val();
                    showLayerMax("选择识别模型", ctx + "/serverModel/selectServerModel?smerchantId=" + smerchantId);
                }
            }
        ;
        $('.layui-form .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
        $("#myForm").Validform({
            btnSubmit: "#myFormSub",  //根据id触发
            tiptype: 3,                  //第三种方式
            showAllError: true,
            beforeSubmit: function () { //验证过后执行save方法
                var isupportWeigh = $("#isupportWeigh").val();
                var iweightType = $("#iweightType").val();
                if (isupportWeigh == 1 && isEmpty(iweightType)) {
                    alertDel("请选择称重类型！");
                    return false;
                }
                var itype = $("#itype").val();
                var srecognitionModelCode = $("#srecognitionModelCode").val();
                if ((itype == 50 || itype == 60) && isEmpty(srecognitionModelCode)) {
                    alertDel("请选择视觉模型！");
                    return false;
                }
                var loadIndex = loading();
                $('#myForm').ajaxSubmit({
                    type: "post",
                    dataType: "json",
                    async: true,
                    error: function () {
                        alertDelAndReload("操作异常，请重新操作");
                    },
                    success: function (msg) {
                        //成功返回
                        closeLayer(loadIndex);//关闭加载框
                        if (msg.success) {
                            alertSuccess("操作成功", {
                                'index': index
                            }, function () {
                                parent.closeLayerAndRefresh(index);
                            });
                        } else {
                            alertDel(msg.msg);
                        }
                    }
                });
                return false;
            }
        });


        layui.use('laydate', function () {
            var laydate = layui.laydate;


            //自动盘货开始时间
            laydate.render({
                elem: '#tinventoryBeginTime',
                type: 'time'
            });
            //自动盘货结束时间
            laydate.render({
                elem: '#tinventoryEndTime',
                type: 'time'
            });
            //温度控制开始时间1
            laydate.render({
                elem: '#tlcontrolBeginTime',
                type: 'time'
            });
            //温度控制结束时间1
            laydate.render({
                elem: '#tlcontrolEndTime',
                type: 'time'
            });
            //温度控制开始时间2
            laydate.render({
                elem: '#trcontrolBeginTime',
                type: 'time'
            });
            //温度控制结束时间2
            laydate.render({
                elem: '#trcontrolEndTime',
                type: 'time'
            });
            //开机时间
            laydate.render({
                elem: '#tbootTime',
                type: 'time'
            });
            //关机时间
            laydate.render({
                elem: '#tshutDownTime',
                type: 'time'
            });
        });


        //监听select选择
        form.on('select(city)', function (data) {
            var areaId = data.value.split("_")[0];
            $.ajax({
                type: 'post',
                url: '${ctx}/device/info/getTown',
                data: {cityId: areaId},
                dataType: 'json',
                success: function (data) {
                    $("#sareaName").html("");
                    var option1 = $("<option>").val("").text("请选择");
                    $("#sareaName").append(option1);
                    $.each(data.data, function (key, val) {
                        var option1 = $("<option>").val(val.id + "_" + val.sname).text(val.sname);
                        $("#sareaName").append(option1);
                        form.render('select');
                    });
                }
            });

            if (!isEmpty(data.value)) {
                $("#scityName").parent().find("span").hide();
            } else {
                $("#scityName").parent().find("span").show();
                if (!$("#scityName").parent().find("span").hasClass("Validform_wrong")) {
                    $("#scityName").parent().find("span").html($("#scityName").attr("nullmsg"));
                    $("#scityName").parent().find("span").removeClass("Validform_right");
                    $("#scityName").parent().find("span").addClass("Validform_wrong");
                }
            }
        });


        //监听select选择
        form.on('select(province)', function (data) {
            var areaId = data.value.split("_")[0];
            $.ajax({
                type: 'post',
                url: '${ctx}/device/info/getCity',
                data: {pid: areaId},
                dataType: 'json',
                success: function (resp) {
                    $("#scityName").html("");
                    var option1 = $("<option>").val("").text("请选择");
                    $("#scityName").append(option1);
                    $("#sareaName").html("");
                    var option1 = $("<option>").val("").text("请选择");
                    $("#sareaName").append(option1);
                    $.each(resp.data, function (key, val) {
                        var option1 = $("<option>").val(val.id + "_" + val.sname).text(val.sname);
                        $("#scityName").append(option1);
                        form.render('select');
                    });
                    //$('#scityName').trigger('change');
                }
            });

            if (!isEmpty(data.value)) {
                $("#sprovinceName").parent().find("span").hide();
            } else {
                $("#sprovinceName").parent().find("span").show();
                if (!$("#sprovinceName").parent().find("span").hasClass("Validform_wrong")) {
                    $("#sprovinceName").parent().find("span").html($("#sprovinceName").attr("nullmsg"));
                    $("#sprovinceName").parent().find("span").removeClass("Validform_right");
                    $("#sprovinceName").parent().find("span").addClass("Validform_wrong");
                }
            }
        });

        //监听区县选择
        form.on('select(town)', function (data) {
            if (!isEmpty(data.value)) {
                $("#sareaName").parent().find("span").hide();
            } else {
                $("#sareaName").parent().find("span").show();
                if (!$("#sareaName").parent().find("span").hasClass("Validform_wrong")) {
                    $("#sareaName").parent().find("span").html($("#sareaName").attr("nullmsg"));
                    $("#sareaName").parent().find("span").removeClass("Validform_right");
                    $("#sareaName").parent().find("span").addClass("Validform_wrong");
                }
            }
        });

        //监听设备类型选择
        form.on('select(itype)', function (data) {
            var itype = data.value;
            if (!isEmpty(data.value)) {
                if (itype == 50 || itype == 60) {
                    $("#recognitionModelCode").show();
                } else {
                    $("#recognitionModelCode").hide();
                    $("#srecognitionModelCode").val("");
                }
                $("#itype").parent().find("span").hide();
            } else {
                $("#itype").parent().find("span").show();
                if (!$("#itype").parent().find("span").hasClass("Validform_wrong")) {
                    $("#itype").parent().find("span").html($("#itype").attr("nullmsg"));
                    $("#itype").parent().find("span").removeClass("Validform_right");
                    $("#itype").parent().find("span").addClass("Validform_wrong");
                }
                $("#recognitionModelCode").hide();
                $("#srecognitionModelCode").val("");

            }
        });

        //监听系统对接类型
        form.on('select(idockingType)', function (data) {
            if (!isEmpty(data.value)) {
                $("#idockingType").parent().find("span").hide();
            } else {
                $("#idockingType").parent().find("span").show();
                if (!$("#idockingType").parent().find("span").hasClass("Validform_wrong")) {
                    $("#idockingType").parent().find("span").html($("#idockingType").attr("nullmsg"));
                    $("#idockingType").parent().find("span").removeClass("Validform_right");
                    $("#idockingType").parent().find("span").addClass("Validform_wrong");
                }
            }
        });

        //监听是否支持称重选择
        form.on('select(isupportWeigh)', function (data) {
            var isSupportAiDevice = data.value;
            if (!isEmpty(data.value)) {
                $("#isupportWeigh").parent().find("span").hide();
                if (isSupportAiDevice == 1) {
                    $("#com_elec_Float").show();
                    $("#iweightTypeTe").show();
                } else {
                    $("#com_elec_Float").hide();
                    $("#iweightTypeTe").hide();
                }
                $("#icommodityFloat").val("");
                $("#ielectronicFloat").val("");
            } else {
                $("#isupportWeigh").parent().find("span").show();
                if (!$("#isupportWeigh").parent().find("span").hasClass("Validform_wrong")) {
                    $("#isupportWeigh").parent().find("span").html($("#isupportWeigh").attr("nullmsg"));
                    $("#isupportWeigh").parent().find("span").removeClass("Validform_right");
                    $("#isupportWeigh").parent().find("span").addClass("Validform_wrong");
                }
                $("#com_elec_Float").hide();
                $("#iweightTypeTe").hide();
                $("#icommodityFloat").val("");
                $("#ielectronicFloat").val("");
            }
        });
             //监听是是垃圾箱设备
                    form.on('select(isBindTrash)', function (data) {
                        var isBindTrash = data.value;
                        if (!isEmpty(data.value)) {
                            if (isBindTrash == 1) {
                                $("#trashModel").show();
                            } else {
                                $("#trashModel").hide();
                            }
                            $("#strashCode").val("");
                            $("#strashBrand").val("");
                        } else {
                            $("#trashModel").hide();
                            $("#strashCode").val("");
                            $("#strashBrand").val("");
                        }
                    });


        //监听合作模式选择
        form.on('select(icooperationMode)', function (data) {
            if (!isEmpty(data.value)) {
                $("#icooperationMode").parent().find("span").hide();
            } else {
                $("#icooperationMode").parent().find("span").show();
                if (!$("#icooperationMode").parent().find("span").hasClass("Validform_wrong")) {
                    $("#icooperationMode").parent().find("span").html($("#icooperationMode").attr("nullmsg"));
                    $("#icooperationMode").parent().find("span").removeClass("Validform_right");
                    $("#icooperationMode").parent().find("span").addClass("Validform_wrong");
                }
            }
        });

        //监听是否使用扩展GPIO
        form.on('select(iisUseExpandGpio)', function (data) {
            if (!isEmpty(data.value)) {
                $("#iisUseExpandGpio").parent().find("span").hide();
            } else {
                $("#iisUseExpandGpio").parent().find("span").show();
                if (!$("#iisUseExpandGpio").parent().find("span").hasClass("Validform_wrong")) {
                    $("#iisUseExpandGpio").parent().find("span").html($("#iisUseExpandGpio").attr("nullmsg"));
                    $("#iisUseExpandGpio").parent().find("span").removeClass("Validform_right");
                    $("#iisUseExpandGpio").parent().find("span").addClass("Validform_wrong");
                }
            }
        });

        //监听是否检测霍尔值
        form.on('select(iisDetectHall)', function (data) {
            if (!isEmpty(data.value)) {
                $("#iisDetectHall").parent().find("span").hide();
            } else {
                $("#iisDetectHall").parent().find("span").show();
                if (!$("#iisDetectHall").parent().find("span").hasClass("Validform_wrong")) {
                    $("#iisDetectHall").parent().find("span").html($("#iisDetectHall").attr("nullmsg"));
                    $("#iisDetectHall").parent().find("span").removeClass("Validform_right");
                    $("#iisDetectHall").parent().find("span").addClass("Validform_wrong");
                }
            }
        });


        //监听摄像头方法select选择
        form.on('select(scameraMethod)', function (data) {
            var method = data.elem[data.elem.selectedIndex].text;   // 文本内容
            $.ajax({
                type: 'post',
                url: '${ctx}/device/deviceModel/getCaremaInfo',
                data: {method: method},
                dataType: 'json',
                success: function (resp) {
                    var str = resp.data;
                    if (!isEmpty(str)) {
                        var strs = new Array(); //定义一数组
                        strs = str.split("-//-"); //字符分割
                        $("#scameraMethod1").val(method);
                        $("#scameraModel").val(strs[0]);
                        $("#scameraType").val(strs[1]);
                        $("#scameraBrand").val(strs[2]);
                    } else {
                        $("#scameraMethod1").val("");
                        $("#scameraModel").val("");
                        $("#scameraType").val("");
                        $("#scameraBrand").val("");
                    }
                }
            });

            if (!isEmpty(data.value)) {
                $("#scameraMethod").parent().find("span").hide();
            } else {
                $("#scameraMethod").parent().find("span").show();
                if (!$("#scameraMethod").parent().find("span").hasClass("Validform_wrong")) {
                    $("#scameraMethod").parent().find("span").html($("#scameraMethod").attr("nullmsg"));
                    $("#scameraMethod").parent().find("span").removeClass("Validform_right");
                    $("#scameraMethod").parent().find("span").addClass("Validform_wrong");
                }
            }
        });

        form.on('select(infoTemplate)', function (data) {
            if (isEmpty(data.value)) {
                infoTemplate(0, null);
                return;
            }
            var areaId = data.value;
            $.ajax({
                type: 'post',
                url: '${ctx}/deviceInfoTemplate/getInfoTemplate',
                data: {pid: areaId},
                dataType: 'json',
                success: function (resp) {
                    infoTemplate(1, resp.data);
                }
            });
        });

        form.on('select(manageTemplate)', function (data) {
            if (isEmpty(data.value)) {
                manageTemplate(0, null);
                return;
            }
            var areaId = data.value;
            $.ajax({
                type: 'post',
                url: '${ctx}/deviceManageTemplate/getManageTemplate',
                data: {pid: areaId},
                dataType: 'json',
                success: function (resp) {
                    manageTemplate(1, resp.data);
                }
            });
        });

        form.on('select(modelTemplate)', function (data) {
            if (isEmpty(data.value)) {
                modelTemplate(0, null);
                return;
            }
            var areaId = data.value;
            $.ajax({
                type: 'post',
                url: '${ctx}/deviceModelTemplate/getModelTemplate',
                data: {pid: areaId},
                dataType: 'json',
                success: function (resp) {
                    modelTemplate(1, resp.data);
                }
            });
        });

    })
    ;
    $(function () {
        $("#cancelBtn").click(function () {
            parent.layer.close(index);
            return false;
        });
    });

    function selectMerchant(merchantId, merchantCode, merchantName) {
        $("#smerchantId").val(merchantId);//商户ID
        $("#smerchantCodes").val(merchantCode);//商户编号
        $("#smerchantCode").val(merchantCode);//商户编号
        $("#smerchantName").val(merchantName);//商户名称
        $("#smerchantName").parent().find("span").hide();
        if ($("#smerchantName").parent().find("span").hasClass("Validform_wrong")) {
            $("#smerchantName").parent().find("span").removeClass("Validform_wrong");
            $("#smerchantName").removeClass("Validform_error");
            $("#smerchantName").parent().find("span").addClass("Validform_right");
        }
    }


    function infoTemplate(isUseTemplate, date) {
        if (isUseTemplate == 1) {
            $("#itype").val(date.itype);
            $("#sdeviceModel").val(date.sdeviceModel);
            $("#sreaderSerialNumber").val(date.sreaderSerialNumber);
            $("#icooperationMode").val(date.icooperationMode);
            $("#icontainerType").val(date.icontainerType);
            $("#icompressorType").val(date.icompressorType);
            $("#iinstallAd").val(date.iinstallAd);
            $("#sputScenes").val(date.sputScenes);
            $("#isupportAi").val(date.isupportAi);
            $("#isupportWeigh").val(date.isupportWeigh);
            // $("#ielectronicFloat").val(date.ielectronicFloat);
            $("#iisOpeningInventory").val(date.iisOpeningInventory);
            $("#idockingType").val(date.idockingType);
            $("#iscreenDisplayType").val(date.iscreenDisplayType);
            //  $("#iweightType").val(date.iweightType);
            $("#iisBindTrash").val(date.iisBindTrash);
            if (date.itype == 50 || date.itype == 60) {
                $("#recognitionModelCode").show();
                $("#srecognitionModelCode").val(date.srecognitionModelCode);
            } else {
                $("#recognitionModelCode").hide();
                $("#srecognitionModelCode").val("");
            }
            if (date.isupportWeigh == 1) {
                $("#com_elec_Float").show();
                $("#iweightTypeTe").show();
                $("#ielectronicFloat").val(date.ielectronicFloat);
                $("#iweightType").val(date.iweightType);
            } else {
                $("#com_elec_Float").hide();
                $("#iweightTypeTe").hide();
                $("#ielectronicFloat").val("");
                $("#iweightType").val("");
            }
            form.render('select');
        } else if (isUseTemplate == 0) {
            $("#itype").val("");
            $("#sdeviceModel").val("");
            $("#sreaderSerialNumber").val("");
            $("#icooperationMode").val("");
            $("#icontainerType").val("");
            $("#icompressorType").val("");
            $("#iinstallAd").val("");
            $("#sputScenes").val("");
            $("#isupportAi").val("");
            $("#isupportWeigh").val("");
            $("#ielectronicFloat").val("");
            $("#iisOpeningInventory").val("");
            $("#idockingType").val("");
            $("#iscreenDisplayType").val("");
            $("#iisBindTrash").val("");
            $("#recognitionModelCode").hide();
            $("#com_elec_Float").hide();
            $("#iweightTypeTe").hide();
            form.render('select');
        }
    }

    function manageTemplate(isUseTemplate, date) {
        if (isUseTemplate == 1) {
            $("#sareaPrincipal").val(date.sareaPrincipal);
            $("#sareaPrincipalContact").val(date.sareaPrincipalContact);
            $("#sdevicePrincipal").val(date.sdevicePrincipal);
            $("#sdevicePrincipalContact").val(date.sdevicePrincipalContact);
            $("#sreplenishment").val(date.sreplenishment);
            $("#sreplenishmentContact").val(date.sreplenishmentContact);
            $("#smaintain").val(date.smaintain);
            $("#smaintainContact").val(date.smaintainContact);
            form.render('select');
        } else if (isUseTemplate == 0) {
            $("#sareaPrincipal").val("");
            $("#sareaPrincipalContact").val("");
            $("#sdevicePrincipal").val("");
            $("#sdevicePrincipalContact").val("");
            $("#sreplenishment").val("");
            $("#sreplenishmentContact").val("");
            $("#smaintain").val("");
            $("#smaintainContact").val("");
            form.render('select');
        }
    }

    function modelTemplate(isUseTemplate, date) {
        if (isUseTemplate == 1) {
            $("#sdeviceCoreModel").val(date.sdeviceModel);
            $("#smanufacturer").val(date.smanufacturer);
            $("#scoreDesc").val(date.scoreDesc);
            $("#sdimensions").val(date.sdimensions);
            $("#sweight").val(date.sweight);
            $("#sratedPower").val(date.sratedPower);
            $("#sproductCapacity").val(date.sproductCapacity);
            $("#sproductTypes").val(date.sproductTypes);
            $("#sdailyPower").val(date.sdailyPower);
            $("#spayWap").val(date.spayWap);
            $("#ielectricShock").val(date.ielectricShock);
            $("#imotherboardNum").val(date.imotherboardNum);
            $("#slocksModel").val(date.slocksModel);
            $("#slocksManufacturer").val(date.slocksManufacturer);
            $("#sadDimensions").val(date.sadDimensions);
            $("#sadConf").val(date.sadConf);
            $("#icompressorNum").val(date.icompressorNum);
            $("#scompressorPosition").val(date.scompressorPosition);
            $("#scompressorDesc").val(date.scompressorDesc);
            $("#ilayerNum").val(date.ilayerNum);
            $("#icargoRoadNum").val(date.icargoRoadNum);
            $("#icameraNum").val(date.icameraNum);
            $("#iisUseExpandGpio").val(date.iisUseExpandGpio);
            $("#iisDetectHall").val(date.iisDetectHall);
            $("#scameraMethod").val(date.scameraMethod);
            $("#scameraMethod1").val(date.scameraMethod1);
            $("#scameraModel").val(date.scameraModel);
            $("#scameraType").val(date.scameraType);
            $("#scameraBrand").val(date.scameraBrand);
            $("#svisualServiceProvider").val(date.svisualServiceProvider);
            $("#spcbModel").val(date.spcbModel);
            $("#iopendoorPinSn").val(date.iopendoorPinSn);
            $("#ihallPinSn").val(date.ihallPinSn);
            $("#idoorPinSn").val(date.idoorPinSn);
            $("#ilockCylinder").val(date.ilockCylinder);
            $("#scontrastMode").val(date.scontrastMode);
            form.render('select');
        } else if (isUseTemplate == 0) {
            $("#sdeviceCoreModel").val("");
            $("#smanufacturer").val("");
            $("#scoreDesc").val("");
            $("#sdimensions").val("");
            $("#sweight").val("");
            $("#sratedPower").val("");
            $("#sproductCapacity").val("");
            $("#sproductTypes").val("");
            $("#sdailyPower").val("");
            $("#spayWap").val("");
            $("#ielectricShock").val("");
            $("#imotherboardNum").val("");
            $("#slocksModel").val("");
            $("#slocksManufacturer").val("");
            $("#sadDimensions").val("");
            $("#sadConf").val("");
            $("#icompressorNum").val("");
            $("#scompressorPosition").val("");
            $("#scompressorDesc").val("");
            $("#ilayerNum").val("");
            $("#icargoRoadNum").val("");
            $("#icameraNum").val("");
            $("#iisUseExpandGpio").val("");
            $("#iisDetectHall").val("");
            $("#scameraMethod").val("");
            $("#scameraMethod1").val("");
            $("#scameraModel").val("");
            $("#scameraType").val("");
            $("#scameraBrand").val("");
            $("#svisualServiceProvider").val("");
            $("#spcbModel").val("");
            $("#iopendoorPinSn").val("");
            $("#ihallPinSn").val("");
            $("#idoorPinSn").val("");
            $("#ilockCylinder").val("");
            $("#scontrastMode").val("");
            form.render('select');
        }
    }
    function selectSupp(smodelCode, smodelId) {
        $("#srecognitionModelCode").val(smodelCode);
    }

</script>
</body>
</html>

