<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备实时盘货开关</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?20180408" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<form class="layui-form" action="${ctx}/device/info/batchRealtimeSwitch" method="post" id="myForm">
    <div class="col-sm-12">
        <div class="ibox-content m-t">
            <div id="deviceDiv" class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">操作设备</label>
                    <div class="layui-input-inline">
                        <input type="radio" name="irangeDevice" lay-filter="rangeDevice" value="10" checked title="全部"/>
                        <input type="radio" name="irangeDevice" lay-filter="rangeDevice" value="20" title="部分"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <input type="hidden" id="deviceIds" name="deviceIds" value=""/>
                    <input type="hidden" id="deviceCodes" name="deviceCodes" value=""/>
                    <button class="layui-btn layui-btn-primary" id="selectDevice"
                            style="margin-left: 200px;margin-top: 10px;" type="button" disabled="disabled">选择设备
                    </button>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">顾客实时购物车</label>
                    <div class="layui-input-inline">
                        <cang:select type="list" name="shoppingInventory" id="shoppingInventory" entire="true"
                                     layFilter="shoppingInventory"
                                     value="" exp="datatype=\"*\" nullmsg=\"请选择是否开启\"" list="{0:关闭,1:开启}"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label">补货员实时购物车</label>
                    <div class="layui-input-inline">
                        <cang:select type="list" name="replenishmentInventory" id="replenishmentInventory" entire="true"
                                     layFilter="replenishmentInventory"
                                     value="" exp="datatype=\"*\" nullmsg=\"请选择是否开启\"" list="{0:关闭,1:开启}"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="layui-form-item ">
            <table class="layui-table" id="deviceTable" style="display:none;">
                <colgroup>
                    <col width="100">
                    <col width="150">
                    <col>
                </colgroup>
                <thead>
                <tr>
                    <th>设备编号</th>
                    <th>设备名称</th>
                    <th>设备地址</th>
                </tr>
                </thead>
                <tbody id="deviceBody">
                </tbody>
            </table>
        </div>
    </div>
    <div class="layui-form-item fixed-bottom">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
            <button class="layui-btn" lay-submit="" lay-filter="myFormSub">确认</button>
        </div>
    </div>
</form>

<script id="device_table_tmpl" type="text/x-jsrender">
{{for data}}
    <tr>
      <td>{{:deviceCode}}</td>
      <td>{{:deviceName}}</td>
      <td>{{:address}}</td>
    </tr>
{{/for}}
</script>
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<script src="${staticSource}/resources/js/jsrender.min.js" type="text/javascript"></script>
<script src="${staticSource}/resources/js/jsrender.converters.js" type="text/javascript"></script>
<script src="${staticSource}/resources/js/validform_min.js" type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var form;
    layui.use(['form', 'layedit'], function () {
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


        //监听设备类型选择
        form.on('select(shoppingInventory)', function (data) {
            if (!isEmpty(data.value)) {
                $("#shoppingInventory").parent().find("span").hide();
            } else {
                $("#shoppingInventory").parent().find("span").show();
                if (!$("#shoppingInventory").parent().find("span").hasClass("Validform_wrong")) {
                    $("#shoppingInventory").parent().find("span").html($("#shoppingInventory").attr("nullmsg"));
                    $("#shoppingInventory").parent().find("span").removeClass("Validform_right");
                    $("#shoppingInventory").parent().find("span").addClass("Validform_wrong");
                }
            }
        });

        //监听设备类型选择
        form.on('select(replenishmentInventory)', function (data) {
            if (!isEmpty(data.value)) {
                $("#replenishmentInventory").parent().find("span").hide();
            } else {
                $("#replenishmentInventory").parent().find("span").show();
                if (!$("#replenishmentInventory").parent().find("span").hasClass("Validform_wrong")) {
                    $("#replenishmentInventory").parent().find("span").html($("#replenishmentInventory").attr("nullmsg"));
                    $("#replenishmentInventory").parent().find("span").removeClass("Validform_right");
                    $("#replenishmentInventory").parent().find("span").addClass("Validform_wrong");
                }
            }
        });

        //设备单选
        form.on('radio(rangeDevice)', function(data){
            if (data.value == 20) {
                //部分设备
                $("#selectDevice").removeAttr("disabled");
            } else {
                //全部设备
                $("#selectDevice").attr("disabled", "disabled");
                $("#deviceIds").val("");
                $("#deviceCodes").val("");
                hideDeviceTable();
            }
            $(this).blur();
        });


    });

    $(function () {
        $("#cancelBtn").click(function () {
            parent.layer.close(index);
            return false;
        });

        //选择设备
        $("#selectDevice").click(function() {
            var deviceIds = $("#deviceIds").val();
            var deviceCodes = $("#deviceCodes").val();
            if (!isEmpty(deviceIds)) {
                showLayerMax("选择设备信息",ctx+"/common/rootSelectDevice?queryDeviceType=50&deviceIds="+deviceIds+"&deviceCodes="+deviceCodes);
            } else {
                showLayerMax("选择设备信息",ctx+"/common/rootSelectDevice?queryDeviceType=50");
            }
        });

        initDeviceTable();
    });


    //确认选择设备
    function selectDevice(deviceIds, deviceCodes) {
        $("#deviceIds").val(deviceIds);
        $("#deviceCodes").val(deviceCodes);
        initDeviceTable();
    }

    //隐藏表格 设备
    function hideDeviceTable() {
        $("#deviceBody").html("");
        $("#deviceTable").hide();
    }

    //初始化设备表格
    function initDeviceTable() {
        var deviceIds = $("#deviceIds").val();
        if(isEmpty(deviceIds)) {//没有选择设备
            hideDeviceTable();
            return;
        }
        $.ajax({
            url:ctx+'/common/getDeviceByIds',
            type:'POST', //GET
            async:false,    //或false,是否异步
            data:{deviceIds:deviceIds},
            dataType:'json',
            error:function(){
                hideDeviceTable();
            },
            success:function(msg){
                if(msg.success) {//成功返回
                    var deviceList = msg.data;
                    if(!isEmpty(deviceList)) {
                        var html = $("#device_table_tmpl").render(msg);
                        $("#deviceBody").html(html);
                        $("#deviceTable").show();
                    } else {
                        hideDeviceTable();
                    }
                } else {
                    hideDeviceTable();
                }
            }
        });
    }
</script>
</body>
</html>



