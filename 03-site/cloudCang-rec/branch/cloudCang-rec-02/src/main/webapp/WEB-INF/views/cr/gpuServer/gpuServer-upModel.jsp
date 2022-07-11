<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备升级APK列表</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?20180408" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<form class="layui-form" action="${ctx}/gpuServer/saveUpModel" method="post" id="myForm">
    <div class="col-sm-12">
        <div class="ibox-content m-t">
             <div class="layui-form-item">
                 <div class="layui-col-md6" name="modelCode" id="modelCode" >
                     <label class="layui-form-label">识别模型编号</label>
                     <div class="layui-input-inline" style="width: 115px">
                         <input type="text" name="smodelCode" readonly="true" id="smodelCode"
                                value=""
                                class="layui-input"/>
                     </div>
                     <button class="layui-btn" id="btn1" style="float: left;" type="button"
                             data-type="sel">选择
                     </button>
                 </div>
             </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">升级开始时间</label>
                    <div class="layui-input-inline">
                        <input type="radio" name="timeType" lay-filter="timeType" value="10"  checked title="立即"/>
                        <input type="radio" name="timeType" lay-filter="timeType" value="20"  title="定时"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label">定时时间</label>
                    <div class="layui-input-inline">
                        <input type="text" name="dproduceDate" datatype="*" nullmsg="请选择定时时间" id="dproduceDate"
                               value="" class="layui-input"  ignore="ignore" disabled/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">升级gpu服务器</label>
                    <div class="layui-input-inline">
                        <input type="radio" name="irangeDevice" lay-filter="rangeDevice" value="10"  checked title="全部"/>
                        <input type="radio" name="irangeDevice" lay-filter="rangeDevice" value="20"  title="部分"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <input type="hidden" id="deviceIds" name="deviceIds" value="" />
                    <input type="hidden" id="deviceCodes" name="deviceCodes" value="" />
                    <button class="layui-btn layui-btn-primary" id="selectDevice" style="margin-left: 200px;margin-top: 10px;" type="button"  disabled="disabled">选择gpu服务器</button>
                </div>
            </div>
        </div>
        <div class="layui-form-item ">
            <table class="layui-table" id="deviceTable" style="display:none;">
                <colgroup>
                    <col width="150">
                    <col width="150">
                    <col>
                </colgroup>
                <thead>
                <tr>
                    <th>gpu服务器名</th>
                    <th>gpu服务器Ip</th>
                    <th>gpu服务器端口</th>
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
      <td>{{:sname}}</td>
      <td>{{:sip}}</td>
      <td>{{:sport}}</td>
    </tr>
{{/for}}
</script>
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<script src="${staticSource}/resources/js/jsrender.min.js"  type="text/javascript"></script>
<script src="${staticSource}/resources/js/jsrender.converters.js"  type="text/javascript"></script>
<script src="${staticSource}/resources/js/validform_min.js"  type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var form;
    layui.use(['form', 'layedit', 'laydate'], function () {

        var laydate = layui.laydate;
        laydate.render({
            elem: '#dproduceDate',//指定元素
            type: 'datetime',
            done: function(value){ //监听日期被切换
                if(!isEmpty(value)) {
                    $("#dproduceDate").removeClass("Validform_error");
                    $("#dproduceDate").parent().find("span").hide();
                } else {
                    $("#dproduceDate").addClass("Validform_error");
                    $("#dproduceDate").parent().find("span").html($("#dproduceDate").attr("nullmsg"));
                    $("#dproduceDate").parent().find("span").removeClass("Validform_right");
                    $("#dproduceDate").parent().find("span").addClass("Validform_wrong");
                    $("#dproduceDate").parent().find("span").show();
                }
            }
        });

        form = layui.form;
        $("#myForm").Validform({
            btnSubmit:"#myFormSub",  //根据id触发
            tiptype:3,                  //第三种方式
            showAllError:true,
            beforeSubmit:function(){ //验证过后执行save方法
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


        // 定时升级，立即升级
        form.on('radio(timeType)', function(data){
            if (data.value == 20) {
                //定时
                $("#dproduceDate").removeAttr("disabled");
                $("#dproduceDate").removeAttr("ignore");
            } else {
                //立即
                $("#dproduceDate").attr("disabled", "disabled");
                $("#dproduceDate").attr("ignore", "ignore");
                $("#dproduceDate").val("");
                $("#dproduceDate").removeClass("Validform_error");
                $("#dproduceDate").parent().find("span").hide();
            }
            $(this).blur();
        });
        $(function () {
            $("#btn1").click(function () {
                showLayerMax("选择识别模型", ctx + "/serverModel/selectServerModel");
            });
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
                showLayerMax("选择gpu服务器",ctx+"/gpuServer/selectDevice?deviceIds="+deviceIds+"&deviceCodes="+deviceCodes);
            } else {
                showLayerMax("选择gpu服务器",ctx+"/gpuServer/selectDevice");
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
            url:ctx+'/gpuServer/getDeviceAndGroupByIds',
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
    function selectSupp(smodelCode) {
        $("#smodelCode").val(smodelCode);
    }

</script>
</body>
</html>




