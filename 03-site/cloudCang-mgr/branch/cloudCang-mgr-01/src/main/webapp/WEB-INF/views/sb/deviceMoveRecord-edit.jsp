<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title>设备搬迁编辑</title>
    <link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
    <link href="${staticSource}/resources/css/override.css?1" rel="stylesheet">

</head>

<body>
    <div class="ibox-content">
        <form class="layui-form" action="${ctx}/device/save" method="post" id="myForm">
            <input type="hidden" id="sdeviceId" name="sdeviceId" value="${deviceMoveRecord.sdeviceId}"/><%--设备ID--%>
            <input type="hidden" id="sdeviceCode" name="sdeviceCode" value="${deviceMoveRecord.sdeviceCode}"/><%--设备编号--%>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">设备名称</label>
                    <div class="layui-input-inline">
                        <input type="text" name="sname" id="sname" value="${deviceInfo.sname}" class="layui-input"/>
                    </div>
                    <button class="layui-btn" id="btn1" style="display: inline-block;float: left;"
                            type="button" data-type="sel">选择
                    </button>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label">设备编号</label>
                    <div class="layui-input-inline">
                        <input type="text" name="sdeviceCodes" id="sdeviceCodes" value="${deviceMoveRecord.sdeviceCode}" class="layui-input" disabled/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">投放省份</label>
                    <div class="layui-input-inline">
                        <select name="sprovinceName" id="sprovinceName" class="state"
                                datatype="*"  nullmsg="请选择投放省份"  lay-filter="province">
                            <option value="">请选择</option>
                            <c:forEach items="${provinceSet}" var="vo">
                                <option value="${vo.id}_${vo.sname}"
                                        <c:if test="${vo.id eq deviceMoveRecord.sprovinceId}">selected</c:if>>${vo.sname}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label">投放城市</label>
                    <div class="layui-input-inline">
                        <select name="scityName" id="scityName" class="state"
                                datatype="*"  nullmsg="请选择投放城市" lay-filter="city">
                            <option value="">请选择</option>
                            <c:forEach items="${citySet}" var="vo">
                                <option value="${vo.id}_${vo.sname}"
                                        <c:if test="${vo.id eq deviceMoveRecord.scityId}">selected</c:if>>${vo.sname}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">投放区县</label>
                    <div class="layui-input-inline">
                        <select name="sareaName" id="sareaName" class="state"
                                datatype="*"  nullmsg="请选择投放区县" lay-filter="town">
                            <option value="">请选择</option>
                            <c:forEach items="${townsSet}" var="vo">
                                <option value="${vo.id}_${vo.sname}"
                                        <c:if test="${vo.id eq deviceMoveRecord.sareaId}">selected</c:if>>${vo.sname}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">投放详细地址</label>
                    <div class="layui-input-inline">
                        <textarea class="layui-textarea" name="sputAddress" placeholder="必填..." datatype="*" nullmsg="请输入地址" id="sputAddress" value="" style="width: 750px;height: 100px">${deviceInfo.sputAddress}</textarea>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">计划搬迁日期</label>
                    <div class="layui-input-inline">
                        <input type="text" name="tplanMoveTime" datatype="*" nullmsg="请选择计划搬迁日期" id="tplanMoveTime"
                               value="<fmt:formatDate value='${deviceMoveRecord.tplanMoveTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"
                               class="layui-input"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label">搬迁负责人</label>
                    <div class="layui-input-inline">
                        <input type="text" name="sprincipal" id="sprincipal"
                               value="${deviceMoveRecord.sprincipal}" datatype="*" nullmsg="请填写搬迁负责人"
                               class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">搬迁原因</label>
                    <div class="layui-input-inline">
                        <textarea class="layui-textarea" id="smoveReason" name="smoveReason" style="width: 750px;height: 100px">${deviceMoveRecord.smoveReason}</textarea>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">备注</label>
                    <div class="layui-input-inline">
                        <textarea class="layui-textarea" id="sremark" name="sremark" style="width: 750px">${deviceMoveRecord.sremark}</textarea>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
            <div class="layui-form-item fixed-bottom">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                    <button class="layui-btn" id="myFormSub">保存</button>
                </div>
            </div>
            </div>
            <input type="hidden" id="id" name="id" value="${deviceMoveRecord.id}" />
        </form>
    </div>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js"  type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate','element'], function(){
        var form = layui.form;

        var element = layui.element;
        var laydate = layui.laydate;

        var nowTime = new Date().valueOf();
        var min = null;
        var max = null;

        var start = laydate.render({
            elem: '#tplanMoveTime',
            type: 'date',
            min: nowTime,
            done: function(value, date){
                endMax = end.config.max;
                end.config.min = date;
                end.config.min.month = date.month -1;
                if(!isEmpty(value)) { //监听日期被切换
                    $("#tplanMoveTime").removeClass("Validform_error");
                    $("#tplanMoveTime").parent().find("span").hide();
                } else {
                    $("#tplanMoveTime").addClass("Validform_error");
                    $("#tplanMoveTime").parent().find("span").html($("#tplanMoveTime").attr("nullmsg"));
                    $("#tplanMoveTime").parent().find("span").removeClass("Validform_right");
                    $("#tplanMoveTime").parent().find("span").addClass("Validform_wrong");
                    $("#tplanMoveTime").parent().find("span").show();
                }
            }
        });
        var end = laydate.render({
            elem: '#tmoveTime',
            type: 'date',
            min: nowTime,
            done: function(value, date){
                if($.trim(value) == ''){
                    var curDate = new Date();
                    date = {'date': curDate.getDate(), 'month': curDate.getMonth()+1, 'year': curDate.getFullYear()};
                }
                start.config.max = date;
                start.config.max.month = date.month -1;
                if(!isEmpty(value)) {
                    $("#tmoveTime").removeClass("Validform_error");
                    $("#tmoveTime").parent().find("span").hide();
                } else {
                    $("#tmoveTime").addClass("Validform_error");
                    $("#tmoveTime").parent().find("span").html($("#tmoveTime").attr("nullmsg"));
                    $("#tmoveTime").parent().find("span").removeClass("Validform_right");
                    $("#tmoveTime").parent().find("span").addClass("Validform_wrong");
                    $("#tmoveTime").parent().find("span").show();
                }
            }
        });

        //选择商户
        var $ = layui.$, active = {
            sel: function(){
                showLayerMax("选择设备", ctx+"/common/selectDeviceOne");
            }
        };

        $('.layui-form .layui-btn').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });


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

        //监听select选择
        form.on('select(city)',function (data) {
            var areaId = data.value.split("_")[0];
            $.ajax({
                type:'post',
                url:'${ctx}/device/info/getTown',
                data:{cityId:areaId},
                dataType:'json',
                success:function(data){
                    $("#sareaName").html("");
                    var option1 = $("<option>").val("").text("请选择");
                    $("#sareaName").append(option1);
                    $.each(data.data,function(key,val){
                        var option1 = $("<option>").val(val.id+"_"+val.sname).text(val.sname);
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
        form.on('select(province)',function (data) {
            var areaId = data.value.split("_")[0];
            $.ajax({
                type:'post',
                url:'${ctx}/device/info/getCity',
                data:{pid:areaId},
                dataType:'json',
                success:function(resp){
                    $("#scityName").html("");
                    var option1 = $("<option>").val("").text("请选择");
                    $("#scityName").append(option1);
                    $("#sareaName").html("");
                    var option1 = $("<option>").val("").text("请选择");
                    $("#sareaName").append(option1);
                    $.each(resp.data,function(key,val){
                        var option1 = $("<option>").val(val.id+"_"+val.sname).text(val.sname);
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


    });
    $(function() {
       $("#cancelBtn").click(function() {
           parent.layer.close(index);
           return false;
       });
    });

    function selectDeviceOne(sdeviceId,sdeviceCode,sname) {
        $("#sdeviceId").val(sdeviceId);//设备ID
        $("#sdeviceCodes").val(sdeviceCode);//设备编号
        $("#sname").val(sname);//设备名称
    }
</script>
</body>
</html>

