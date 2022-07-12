<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>增加设备详细信息模板</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
</head>
<body>
<div class="ibox-content">
    <div class="layui-tab-content">
        <div class="layui-tab-item layui-show">
            <form class="layui-form" action="${ctx}/deviceModelTemplate/add" method="post" id="myForm">
                <input type="hidden" id="scameraMethod1" name="scameraMethod1"
                       value="${deviceModelTemplate.scameraMethod}"/><%--摄像头方法--%>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label">模板名称</label>
                        <div class="layui-input-inline">
                            <input type="text" name="sname" id="sname" datatype="*" nullmsg="请输入模板名称"
                                   value="" class="layui-input" placeholder="必填..."/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label">模板编号</label>
                        <div class="layui-input-inline">
                            <input type="text" name="scode" id="scode" value=""
                                   class="layui-input" disabled/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label">设备核心型号</label>
                        <div class="layui-input-inline">
                            <input type="text" name="sdeviceModel" id="sdeviceModel" value="" class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label">核心生产厂商</label>
                        <div class="layui-input-inline">
                            <input type="text" name="smanufacturer" id="smanufacturer" value="" class="layui-input"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label">核心配置描述</label>
                        <div class="layui-input-inline">
                            <input type="text" name="scoreDesc" id="scoreDesc" value="" class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label">整体外形尺寸</label>
                        <div class="layui-input-inline">
                            <input type="text" name="sdimensions" id="sdimensions" value="" class="layui-input"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label">重量</label>
                        <div class="layui-input-inline">
                            <input type="text" name="sweight" id="sweight" value="" class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label">额定功率</label>
                        <div class="layui-input-inline">
                            <input type="text" name="sratedPower" id="sratedPower" value="" class="layui-input"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label">商品容量</label>
                        <div class="layui-input-inline">
                            <input type="text" name="sproductCapacity" id="sproductCapacity" value=""
                                   class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label">商品类型</label>
                        <div class="layui-input-inline">
                            <input type="text" name="sproductTypes" id="sproductTypes" value="" class="layui-input"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label">日耗电量</label>
                        <div class="layui-input-inline">
                            <input type="text" name="sdailyPower" id="sdailyPower" value="" class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label">支持支付方式（微信支、支付宝、现金、银联卡 ）</label>
                        <div class="layui-input-inline">
                            <input type="text" name="spayWap" id="spayWap" value="" class="layui-input"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label">防触电类型</label>
                        <div class="layui-input-inline">
                            <cang:select type="list" name="ielectricShock" id="ielectricShock" entire="true"
                                         value="" list="{10:0类,20:I类,30:II类,40:III类}"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label">主板块数</label>
                        <div class="layui-input-inline">
                            <input type="number" name="imotherboardNum" id="imotherboardNum" value=""
                                   class="layui-input"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label">电子锁型号</label>
                        <div class="layui-input-inline">
                            <input type="text" name="slocksModel" id="slocksModel" value="" class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label">电子锁生产厂商</label>
                        <div class="layui-input-inline">
                            <input type="text" name="slocksManufacturer" id="slocksManufacturer" value=""
                                   class="layui-input"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label">广告级屏幕尺寸</label>
                        <div class="layui-input-inline">
                            <input type="text" name="sadDimensions" id="sadDimensions" value="" class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label">广告机配置描述</label>
                        <div class="layui-input-inline">
                            <input type="text" name="sadConf" id="sadConf" value="" class="layui-input"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label">压缩机个数</label>
                        <div class="layui-input-inline">
                            <input type="number" name="icompressorNum" id="icompressorNum" value=""
                                   class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label">压缩机位置</label>
                        <div class="layui-input-inline">
                            <input type="text" name="scompressorPosition" id="scompressorPosition" value=""
                                   class="layui-input"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label">压缩机描述</label>
                        <div class="layui-input-inline">
                            <input type="text" name="scompressorDesc" id="scompressorDesc" value=""
                                   class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label">设备层数</label>
                        <div class="layui-input-inline">
                            <input type="number" name="ilayerNum" id="ilayerNum" value="" class="layui-input"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label">货道数</label>
                        <div class="layui-input-inline">
                            <input type="number" name="icargoRoadNum" id="icargoRoadNum" value="" class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label">摄像头个数</label>
                        <div class="layui-input-inline">
                            <input type="number" name="icameraNum" id="icameraNum" value="" class="layui-input"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label">是否使用扩展GPIO</label>
                        <div class="layui-input-inline">
                            <cang:select type="list" name="iisUseExpandGpio" id="iisUseExpandGpio" entire="true"
                                         value="" list="{0:否,1:是}"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label">是否检测霍尔值</label>
                        <div class="layui-input-inline">
                            <cang:select type="list" name="iisDetectHall" id="iisDetectHall" entire="true"
                                         value="" list="{0:否,1:是}"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label">摄像头方法</label>
                        <div class="layui-input-inline">
                            <cang:select type="dic" name="scameraMethod" id="scameraMethod" entire="true"
                                         layFilter="scameraMethod" value="" groupNo="SP000171"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label">摄像头型号</label>
                        <div class="layui-input-inline">
                            <input type="text" name="scameraModel" id="scameraModel" value=""
                                   class="layui-input" readonly/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label">摄像头类型</label>
                        <div class="layui-input-inline">
                            <input type="text" name="scameraType" id="scameraType" value=""
                                   class="layui-input" readonly/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label">摄像头品牌</label>
                        <div class="layui-input-inline">
                            <input type="text" name="scameraBrand" id="scameraBrand" value=""
                                   class="layui-input" readonly/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label">视觉服务提供商</label>
                        <div class="layui-input-inline">
                            <cang:select type="dic" name="svisualServiceProvider" id="svisualServiceProvider"
                                         entire="true"
                                         value="" groupNo="SP000172"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label">PCB板型号</label>
                        <div class="layui-input-inline">
                            <cang:select type="dic" name="spcbModel" id="spcbModel" entire="true" value=""
                                         groupNo="SP000174"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label">开门引脚序号</label>
                        <div class="layui-input-inline">
                            <input type="number" name="iopendoorPinSn" id="iopendoorPinSn" value=""
                                   class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label">霍尔引脚序号</label>
                        <div class="layui-input-inline">
                            <input type="number" name="ihallPinSn" id="ihallPinSn" value=""
                                   class="layui-input"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label">门引脚序号</label>
                        <div class="layui-input-inline">
                            <input type="number" name="idoorPinSn" id="idoorPinSn" value=""
                                   class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label">锁芯引脚序号</label>
                        <div class="layui-input-inline">
                            <input type="number" name="ilockCylinder" id="ilockCylinder" value=""
                                   class="layui-input"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label">盘货对比方式</label>
                        <div class="layui-input-inline">
                            <cang:select type="list" name="scontrastMode" id="scontrastMode" entire="true"
                                         value="" list="{rawstock:原始库存,openDoor:开关门盘货}"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">备注</label>
                    <div class="layui-input-block">
                        <textarea id="sremark" name="sremark" class="layui-textarea layui-form-textarea80p"
                                  placeholder="备注（选填项）"></textarea>
                    </div>
                </div>
                <div class="layui-form-item fixed-bottom">
                    <div class="layui-input-block">
                        <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                        <button class="layui-btn" id="myFormSub">保存</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js" type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var form;
    layui.use(['form', 'layedit', 'element'], function () {

        $('.layui-form .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

        form = layui.form;

        $("#myForm").Validform({
            btnSubmit: "#myFormSub",  //根据id触发
            tiptype: 3,                  //第三种方式
            showAllError: true,
            beforeSubmit: function () { //验证过后执行save方法
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
        });

        $('.chosen-select').chosen();
    });

    $(function () {
        $("#cancelBtn").click(function () {
            parent.layer.close(index);
            return false;
        });
    });


</script>
</body>
</html>

