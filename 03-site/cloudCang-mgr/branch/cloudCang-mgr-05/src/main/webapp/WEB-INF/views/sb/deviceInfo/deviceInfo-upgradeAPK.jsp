<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备升级APK列表</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?20180408" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
<link rel="stylesheet" href="${staticSource}/resources/css/webuploader.css?20190715">

</head>

<body class="gray-bg">
<form class="layui-form" action="${ctx}/device/info/updateApk" method="post" id="myForm" enctype="multipart/form-data">
    <div class="col-sm-12">
        <div class="ibox-content m-t">
            <div class="layui-form-item ">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code='sb.upload.file.mode' /></label>
                    <div class="layui-input-inline">
                        <cang:select type="list" name="upFileType" id="upFileType"
                                     exp="springMessageCode=sb.please.choose.how.to.upload.files"
                                     layFilter="upFileType" value=""
                                     list="{10:springMessageCode=sb.ftp.upload,20:springMessageCode=sb.local.file.upload}"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <%--<div class="layui-col-md6" id="ftpUp" name="ftpUp">
                    <button type="button" class="layui-btn" id="apkButton"><spring:message code='sb.choose.to.upload.apk.file' /></button>
                </div>--%>
                <div id="uploader" class="wu-example">
                    <!--用来存放文件信息-->
                    <div class="btns">
                        <div id="picker" style="margin-left:162px;margin-bottom:20px;"><spring:message code='sb.choose.to.upload.apk.file' /></div>
                        <div id="thelist" class="uploader-list"></div>
                        <%--   <button id="ctlBtn" class="btn btn-default"><spring:message code='sb.start.upload' /></button>--%>
                    </div>
                </div>
                <div class="layui-col-md6" id="localUp" name="localUp" style="display:none;">
                    <label class="layui-form-label"><spring:message code='sb.file.address' /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="localAddress" id="localAddress"
                               value="" placeholder="<spring:message code='sb.please.fill.in.the.server.local.file.path' />！"
                               class="layui-input"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code='vrsku.version.number' /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="version" id="version" placeholder="<spring:message code='sb.only.integers.are.supported' />..." datatype="n1-10"
                               nullmsg="<spring:message code='sb.please.enter.the.version.number' />" value="" class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code='sb.upgrade.start.time' /></label>
                    <div class="layui-input-inline">
                        <input type="radio" name="timeType" lay-filter="timeType" value="0" checked title="<spring:message code='main.immediately' />"/>
                        <input type="radio" name="timeType" lay-filter="timeType" value="1" title="<spring:message code='main.timing' />"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code='sb.timing' /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="dproduceDate" datatype="*" nullmsg="<spring:message code='sb.please.select.the.time' />" id="dproduceDate"
                               value="" class="layui-input" ignore="ignore" disabled/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="sb.upgrade.device" /></label>
                    <div class="layui-input-inline">
                        <input type="radio" name="irangeDevice" lay-filter="rangeDevice" value="0" checked title='<spring:message code="main.whole" />'/>
                        <input type="radio" name="irangeDevice" lay-filter="rangeDevice" value="1" title='<spring:message code="main.part" />'/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <input type="hidden" id="deviceIds" name="deviceIds" value=""/>
                    <input type="hidden" id="deviceCodes" name="deviceCodes" value=""/>
                    <input type="hidden" id="apkTempFile" name="apkTempFile" value=""/>
                    <button class="layui-btn layui-btn-primary" id="selectDevice"
                            style="margin-left: 200px;margin-top: 10px;" type="button" disabled="disabled"><spring:message code="activity.select.equipment" />
                    </button>
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
                    <th><spring:message code="main.device.id" /></th>
                    <th><spring:message code="main.device.name" /></th>
                    <th><spring:message code="main.device.address" /></th>
                </tr>
                </thead>
                <tbody id="deviceBody">
                </tbody>
            </table>
        </div>
    </div>
    <div class="layui-form-item fixed-bottom">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
            <button class="layui-btn" lay-submit="" lay-filter="myFormSub"><spring:message code="main.confirm" /></button>
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
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<script src="${staticSource}/resources/js/jsrender.min.js" type="text/javascript"></script>
<script src="${staticSource}/resources/js/jsrender.converters.js" type="text/javascript"></script>
<script src="${staticSource}/resources/js/validform_min.js" type="text/javascript"></script>
<!--webuploader -->
<script type="text/javascript" src="${staticSource}/resources/js/webuploader.js"></script>
<script type="text/javascript">
    var uploader;
    $(function () {
        var GUID = WebUploader.Base.guid();//一个GUID
        //apk上传
        var $list = $('#thelist .table')/*,
         $btn = $('#myFormSub')*/;
        uploader = WebUploader.create({
            // swf文件路径
            swf: '${staticSource}/resources/swf/Uploader.swf',
            // 文件接收服务端。
            auto: false, // 选完文件后，是否自动上传
            server: ctx + '/device/info/upApkToFtp',
            title: '<spring:message code='sb.update.apk' />',
            accept: {
                title: 'intoTypes',
                extensions: 'zip',
                mimeTypes: '.zip'
            },
            formData: {
                guid: GUID,
                folderType: 20
            },
            // 选择文件的按钮。可选。
            pick: '#picker',
            auto: true,
            chunked: true, // 分片处理
            chunkSize: 10 * 1024 * 1024, // 每片2M,
            chunkRetry: false,// 如果失败，则不重试
            threads: 5,// 上传并发数。允许同时最大上传进程数。
            duplicate: false, //是否支持重复上传
            fileNumLimit: 1,
            // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
            resize: false
        });
        //当有文件添加进来的时候
        uploader.on('fileQueued', function (file) {
            //具体逻辑根据项目需求来写  这里只是简单的举个例子写下
            $one = $("<div >" + file.name + "</div>");
            $("#thelist").append($one);
        });
        //重复添加文件
        var timer1;
        uploader.onError = function (code) {
            clearTimeout(timer1);
            timer1 = setTimeout(function () {
                alertDel("<spring:message code='sb.the.file.already.exists' />");
            }, 250);
        };
        // 文件上传成功处理。
        uploader.on('uploadSuccess', function (file) {
            // 具体逻辑...
            $.post(ctx + '/device/info/mergeFile', {guid: GUID, fileName: file.name, folderType: 20}, function (data) {
                var msg = $.parseJSON(data);
                if (msg.success) {
                    $("#apkTempFile").val(msg.data);
                    document.getElementById("myFormSub").disabled = false;
                }
            });
        });
        // 文件上传失败处理。
        uploader.on('uploadError', function (file) {
            alertDel("上传失败");
            //删除原来数据
            $.ajax({
                type: 'post',
                url: ctx + "/device/info/deleTempFile",
                data: {
                    apkTempFile: $("#apkTempFile").val()
                },
                dataType: "json",
                async: true
            })
        });
    });

    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var form;
    layui.use(['form', 'layedit', 'laydate'], function () {

        var laydate = layui.laydate;
        laydate.render({
            elem: '#dproduceDate',//指定元素
            type: 'datetime',
            done: function (value) { //监听日期被切换
                if (!isEmpty(value)) {
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
                        alertDelAndReload("<spring:message code='main.error.try.again' />");
                    },
                    success: function (msg) {
                        //成功返回
                        closeLayer(loadIndex);//关闭加载框
                        if (msg.success) {
                            alertSuccess("<spring:message code='main.success' />", {
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
        form.on('radio(rangeDevice)', function (data) {
            if (data.value == 1) {
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
        form.on('select(upFileType)', function (data) {
            if (10 == data.value) { //FTP
                $("#uploader").css("display", "block");
                $("#localUp").css("display", "none");
                $("#localAddress").val("");
                document.getElementById("myFormSub").disabled = true;
            } else {//本地
                $("#uploader").css("display", "none");
                $("#localUp").css("display", "block");
                document.getElementById("myFormSub").disabled = false;
                var apkTempFile = $("#apkTempFile").val();
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: "${ctx}/device/info/deleTempFile",
                    data: {
                        apkTempFile: apkTempFile
                    },
                    success: function (msg) {
                    }
                });
                $("#tempFile").val("");
                $("#thelist").empty().append("");
                $.each(uploader.getFiles(), function (index, file) {
                    uploader.removeFile(file);
                });
                $(".file-item").remove();
            }
        });

        // 定时升级，立即升级
        form.on('radio(timeType)', function (data) {
            if (data.value == 1) {
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


    });

    $(function () {
        $("#cancelBtn").click(function () {
            $.ajax({
                type: "POST",
                dataType: "json",
                url: "${ctx}/device/info/deleTempFile",
                data: {
                    apkTempFile: $("#apkTempFile").val()
                },
                success: function (msg) {
                }
            });
            parent.layer.close(index);
            return false;
        });

        //选择设备
        $("#selectDevice").click(function () {
            var deviceIds = $("#deviceIds").val();
            var deviceCodes = $("#deviceCodes").val();
            if (!isEmpty(deviceIds)) {
                showLayerMax('<spring:message code="sb.select.device.information" />', ctx + "/common/rootSelectDevice?queryDeviceType=60&deviceIds=" + deviceIds + "&deviceCodes=" + deviceCodes);
            } else {
                showLayerMax('<spring:message code="sb.select.device.information" />', ctx + "/common/rootSelectDevice?queryDeviceType=60");
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
        if (isEmpty(deviceIds)) {//没有选择设备
            hideDeviceTable();
            return;
        }
        $.ajax({
            url: ctx + '/common/getDeviceByIds',
            type: 'POST', //GET
            async: false,    //或false,是否异步
            data: {deviceIds: deviceIds},
            dataType: 'json',
            error: function () {
                hideDeviceTable();
            },
            success: function (msg) {
                if (msg.success) {//成功返回
                    var deviceList = msg.data;
                    if (!isEmpty(deviceList)) {
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



