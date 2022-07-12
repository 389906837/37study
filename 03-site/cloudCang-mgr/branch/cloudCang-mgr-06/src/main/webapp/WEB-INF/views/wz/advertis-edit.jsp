<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>广告区域编辑</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?1" rel="stylesheet">
<link rel="stylesheet" href="${staticSource}/resources/css/webuploader.css">


</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/advertis/save" method="post" id="myForm" enctype="multipart/form-data">
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">运营区域</label>
                <div class="layui-input-inline">
                    <select lay-verify="required" lay-search="" name="sregionCode" datatype="*" nullmsg="请选择运营区域"
                            id="sregionCode" class="state" lay-filter="sregionCode">
                        <option value="">请选择运营区域</option>
                        <c:forEach items="${regions}" var="item">
                            <option
                                    <c:if test="${item.scode eq advertis.sregionCode}">selected</c:if>
                                    value="${item.scode}">${item.sregionName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">广告是否通用</label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="iuseType" id="iuseType"
                                 value="${advertis.iuseType}" list="{0:否,1:是}"
                                 exp="datatype=\"*\" nullmsg=\"请选择广告是否通用\""
                                 entireName="请选择广告是否通用" entire="true"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">标题</label>
                <div class="layui-input-inline">
                    <input type="text" name="stitle" id="stitle" value="${advertis.stitle}" datatype="*"
                           nullmsg="请输入标题"
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">投放状态</label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="istatus" id="istatus" layFilter="discountSelect"
                                 value="${advertis.istatus}" list="{2:待投放,1:投放中,0:已过期,3:暂停}"
                                 exp="datatype=\"*\" nullmsg=\"请选择投放状态\""
                                 entireName="请选择投放状态" entire="true"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">开始日期</label>
                <div class="layui-input-inline">
                    <input type="text" name="tstartDate" datatype="*" nullmsg="请选择开始日期" id="tstartDate"
                           value="<fmt:formatDate value='${advertis.tstartDate}' pattern='yyyy-MM-dd HH:mm:ss'/>"
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">结束日期</label>
                <div class="layui-input-inline">
                    <input type="text" name="tendDate" datatype="*" nullmsg="请选择结束日期" id="tendDate"
                           value="<fmt:formatDate value='${advertis.tendDate}' pattern='yyyy-MM-dd HH:mm:ss'/>"
                           class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">广告类型</label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="iadvType" id="iadvType" layFilter="advType"
                                 value="${advertis.iadvType}" list="{20:视频,10:图片}"
                                 exp="datatype=\"*\" nullmsg=\"请选择广告类型\""/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">排序号</label>
                <div class="layui-input-inline">
                    <input type="text" name="isort" id="isort" value="${advertis.isort}" datatype="*"
                           nullmsg="请输入排序号"
                           class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">屏幕显示类型</label>
                <div class="layui-input-inline">
                    <cang:select type="dic" groupNo="SP000175" entire="true"
                                 exp="datatype=\"*\" nullmsg=\"请选择屏幕显示类型\""
                                 value="${advertis.iscreenDisplayType}" entireName="请选择屏幕显示类型"
                                 name="iscreenDisplayType" id="iscreenDisplayType"/>
                </div>
            </div>
        </div>
        <div class="layui-col-md6">
            <label class="layui-form-label">说明</label>
            <div class="layui-input-inline" style="width:50%;">
                    <textarea style="width: 81%" class="layui-textarea" id="sremark"
                              name="sremark">${advertis.sremark}</textarea>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <div id="uploader" class="wu-example">
                    <!--用来存放文件信息-->
                    <div class="btns btn-mg">
                        <div id="picker">选择上传视频文件</div>
                        <span class="require" style="color:red">(请选择mp4格式视频上传,上传成功后才能保存!)</span>
                        <%-- <div id="thelist" class="uploader-list"></div>--%>
                        <%--   <button id="ctlBtn" class="btn btn-default">开始上传</button>--%>
                        <div id="thelist" class="uploader-list">
                            <table class="table" border="1" cellpadding="0" cellspacing="0" width="100%">
                                <tr class="filelist-head">
                                    <th class="file-name">文件名称</th>
                                    <th class="file-size">大小</th>
                                    <th width="20%" class="file-pro">进度</th>
                                    <%--  <th width="20%" class="file-manage">操作</th>--%>
                                </tr>
                            </table>
                        </div>

                    </div>
                </div>
            </div>
            <div class="layui-col-md6 " id="upImg" name="upImg" style="display:none;">
                <label class="layui-form-label"></label>
                <div class="layui-inline">
                    <button type="button" class="layui-btn" id="advertisBtn">广告图片上传</button>
                    <blockquote class="layui-elem-quote layui-quote-nm layui-quote-nm-1">
                        预览图
                        <div class="layui-upload-list" id="slogoYl">
                            <c:if test="${!empty  advertis.scontentUrl and advertis.iadvType == 10 }">
                                <img src="${dynamicSource}${advertis.scontentUrl}" style="width: 100%;height: 100%"/>
                            </c:if>
                            <c:if test="${empty advertis.scontentUrl or advertis.iadvType != 10 }">
                                <img src="${staticSource}/resources/images/37cang.png"
                                     style="width: 100%;height: 100%"/>
                            </c:if>
                        </div>
                    </blockquote>
                    <span class="require">(1920*1080像素,低于2000kb的jpg或者png图片)</span>
                </div>
            </div>
        </div>


        <div class="layui-form-item">
            <div class="layui-form-item fixed-bottom">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                    <button class="layui-btn" id="myFormSub" <c:if test="${empty advertis.id }">disabled</c:if>>保存
                    </button>
                    <%--  <span class="require" style="color:red">(视频上传成功后才能保存！)</span>--%>
                </div>
            </div>
        </div>
        <input type="hidden" id="id" name="id" value="${advertis.id}"/>
        <input type="hidden" id="tempFile" name="tempFile" value=""/>
        <input type="hidden" id="sregionId" name="sregionId" value="${advertis.sregionId}"/>
        <input type="hidden" id="guid" name="guid" value=""/>
    </form>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js" type="text/javascript"></script>
<!--webuploader -->
<script type="text/javascript" src="${staticSource}/resources/js/webuploader.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        if (${not empty advertis.id}) {
            var iadvType = $("#iadvType").val();//商户ID
            if (!isEmpty(iadvType)) {
                if (iadvType == 10) {
                    $("#upImg").css("display", "block");
                    $("#uploader").css("display", "none");
                }
            }
        }
    });
    var uploader;
    $(function () {
        var GUID = WebUploader.Base.guid();//一个GUID
        $("#guid").val(GUID);
        //mp4上传
        var $list = $('#thelist .table');
        uploader = WebUploader.create({
            // swf文件路径
            swf: '${staticSource}/resources/swf/Uploader.swf',
            // 文件接收服务端。
            auto: false, // 选完文件后，是否自动上传
            server: ctx + '/advertis/upVideoToFtp',
            title: '更新视频',
            accept: {
                title: 'intoTypes',
                extensions: 'mp4',
                mimeTypes: '.mp4'
            },
            formData: {guid: GUID},
            // 选择文件的按钮。可选。
            pick: '#picker',
            auto: true,
            chunked: true, // 分片处理
            chunkSize: 10 * 1024 * 1024, // 每片2M,
            chunkRetry: false,// 如果失败，则不重试
            threads: 5,// 上传并发数。允许同时最大上传进程数。
            duplicate: false, //是否支持重复上传
            // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
            resize: false,
            fileNumLimit: 1//上传数量限制
            // fileSizeLimit: 500 * 1024 * 1024 //验证文件总大小是否超出限制, 超出则不允许加入队列
            //fileSingleSizeLimit: 50 * 1024 * 1024
        });
        uploader.on("error", function (type, handler) {
            if (type == "Q_TYPE_DENIED") {
                alertDel("请上传MP4格式的视频");
            } else if (type == "Q_EXCEED_SIZE_LIMIT") {
                alertDel("视频大小不能超过500M哦");
            }
        });
        uploader.on('fileQueued', function (file) {
            $list.append('<tr id="' + file.id + '" class="file-item">' + /*'<td width="5%" class="file-num">111</td>' +*/ '<td class="file-name">' + file.name + '</td>' + '<td width="20%" class="file-size">' + (file.size / 1024 / 1024).toFixed(1) + 'M' + '</td>' + '<td width="20%" class="file-pro">0%</td>' + '</tr>');
        });
        //重复添加文件
        var timer1;
        uploader.onError = function (code) {
            clearTimeout(timer1);
            timer1 = setTimeout(function () {
                alertDel("已添加文件,请不要重复操作!");
            }, 250);
        };
        uploader.on('uploadProgress', function (file, percentage) {
            $("td.file-pro").text("");
            var $li = $('#' + file.id).find('.file-pro'),
                $percent = $li.find('.file-progress .progress-bar');

            // 避免重复创建
            if (!$percent.length) {
                $percent = $('<div class="file-progress progress-striped active">' +
                    '<div class="progress-bar" role="progressbar" style="width: 0%">' +
                    '</div>' +
                    '</div>' + '<br/><div class="per">0%</div>').appendTo($li).find('.progress-bar');
            }
            $li.find('.per').text((percentage * 100).toFixed(2) + '%');
            $percent.css('width', percentage * 100 + '%');
        });
        // 文件上传成功，给item添加成功class, 用样式标记上传成功。
        uploader.on('uploadSuccess', function (file, response) {
            // 具体逻辑...
            $.post(ctx + '/advertis/mergeFile', {guid: GUID, fileName: file.name}, function (data) {
                var msg = $.parseJSON(data);
                if (msg.success) {
                    $("#tempFile").val(msg.data);
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
                url: ctx + "/advertis/deleTempFile",
                data: {"guid": GUID},
                dataType: "json",
                async: true
            });
        });
    });
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate', 'upload'], function () {
        var form = layui.form;
        //执行一个laydate实例
        var laydate = layui.laydate;
        var nowTime = new Date().valueOf();
        var min = null;
        var max = null;
        var start = laydate.render({
            elem: '#tstartDate',
            type: 'datetime',
            min: nowTime,
//            btns: ['clear','now','confirm'],
            done: function (value, date) {
                endMax = end.config.max;
                end.config.min = date;
                end.config.min.month = date.month - 1;
                if (!isEmpty(value)) { //监听日期被切换
                    $("#tstartDate").removeClass("Validform_error");
                    $("#tstartDate").parent().find("span").hide();
                } else {
                    $("#tstartDate").addClass("Validform_error");
                    $("#tstartDate").parent().find("span").html($("#tstartDate").attr("nullmsg"));
                    $("#tstartDate").parent().find("span").removeClass("Validform_right");
                    $("#tstartDate").parent().find("span").addClass("Validform_wrong");
                    $("#tstartDate").parent().find("span").show();
                }
            }
        });
        form.on('select(advType)', function (data) {
            if (!isEmpty(data.value)) {
                if (data.value == 10) {
                    //图片
                    $("#upImg").css("display", "block");
                    $("#uploader").css("display", "none");
                    document.getElementById("myFormSub").disabled = false;
                    var tempFile = $("#tempFile").val();
                    if (!isEmpty(tempFile)) {
                        $.ajax({
                            type: "POST",
                            dataType: "json",
                            url: "${ctx}/advertis/deleTempFile",
                            data: {
                                tempFile: $("#tempFile").val()
                            },
                            success: function (msg) {
                                $("#tempFile").val("");
                            }
                        });
                    }
                    $("#tempFile").val("");
                    $.each(uploader.getFiles(), function (index, file) {
                        uploader.removeFile(file);
                    });
                    $(".file-item").remove();
                } else if (data.value == 30) {
                    //音频
                    $("#upImg").css("display", "none");
                    $("#uploader").css("display", "none");
                    document.getElementById("myFormSub").disabled = false;
                } else {
                    //视频
                    document.getElementById("myFormSub").disabled = true;
                    $("#upImg").css("display", "none");
                    $("#uploader").css("display", "block");
                }
                $("#iadvType").parent().find("span").hide();
            } else {
                $("#iadvType").parent().find("span").show();
                if (!$("#iadvType").parent().find("span").hasClass("Validform_wrong")) {
                    $("#iadvType").parent().find("span").html($("#iadvType").attr("nullmsg"));
                    $("#iadvType").parent().find("span").removeClass("Validform_right");
                    $("#iadvType").parent().find("span").addClass("Validform_wrong");
                }
            }
        });
        var end = laydate.render({
            elem: '#tendDate',
            type: 'datetime',
            min: nowTime,
            done: function (value, date) {
                if ($.trim(value) == '') {
                    var curDate = new Date();
                    date = {'date': curDate.getDate(), 'month': curDate.getMonth() + 1, 'year': curDate.getFullYear()};
                }
                start.config.max = date;
                start.config.max.month = date.month - 1;
                if (!isEmpty(value)) {
                    $("#tendDate").removeClass("Validform_error");
                    $("#tendDate").parent().find("span").hide();
                } else {
                    $("#tendDate").addClass("Validform_error");
                    $("#tendDate").parent().find("span").html($("#tendDate").attr("nullmsg"));
                    $("#tendDate").parent().find("span").removeClass("Validform_right");
                    $("#tendDate").parent().find("span").addClass("Validform_wrong");
                    $("#tendDate").parent().find("span").show();
                }
            }
        });

        //图片上传start
        var $ = layui.jquery, upload = layui.upload;
        upload.render({
            elem: '#advertisBtn',
            auto: false,
            field: "photoFile",
            multiple: false,
            size: 2000,
            accept: 'images',
            exts: 'jpg|png',
            choose: function (obj) {
                obj.preview(function (index, file, result) {
                    $("#slogoYl").find("img").attr("src", result);
                });
            }
        });
        //图片上传end


        //选择商户
        var $ = layui.$, active = {
            sel: function () {
                showLayer("选择商户", ctx + "/merchantInfo/radioSelectList", {area: ['90%', '80%']});
            }
        };
        $('.layui-form .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

        var form = layui.form;
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
    });
    $(function () {
        /*    $("#cancelBtn").click(function () {
         parent.layer.close(index);
         return false;
         });*/
        $("#cancelBtn").click(function () {
            var tempFile = $("#tempFile").val();
            var guid = $("#guid").val();
            if (isEmpty(tempFile) && !isEmpty(guid)) {
                tempFile = guid;
            }
            $.ajax({
                type: "POST",
                dataType: "json",
                url: "${ctx}/advertis/deleTempFile",
                data: {
                    tempFile: tempFile
                },
                success: function (msg) {
                }
            });
            parent.layer.close(index);
            return false;
        });
    });


    function selectMerchant(merchantId, merchantCode, merchantName) {
        $("#smerchantId").val(merchantId);//商户ID
        $("#smerchantCode").val(merchantCode);//商户编号
        $("#smerchantName").val(merchantName);//商户名称
    }
</script>
</body>
</html>

