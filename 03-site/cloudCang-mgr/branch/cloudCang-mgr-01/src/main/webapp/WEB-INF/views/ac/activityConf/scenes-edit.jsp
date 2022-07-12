<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>场景活动编辑</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?201822" rel="stylesheet">
<style type="text/css">
    .layui-form-label {width: 200px;}
</style>
</head>
<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/activityConf/saveScenes" method="post" id="myForm">
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">编号</label>
                <div class="layui-input-inline">
                    <input type="text" name="sconfCode" id="sconfCode" disabled value="${conf.sconfCode}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">名称</label>
                <div class="layui-input-inline">
                    <input type="text" name="sconfName" datatype="*" nullmsg="请输入活动名称" id="sconfName"
                           value="${conf.sconfName}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">场景类型</label>
                <div class="layui-input-inline">
                    <cang:select type="dic" groupNo="SP000118" layFilter="scenesType" name="iscenesType" exp="datatype=\"*\" nullmsg=\"请选择场景类型\""
                                 id="iscenesType" entire="true" value="${conf.iscenesType}" />
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <%--<div class="layui-col-md6">
                <label class="layui-form-label">优惠方式</label>
                <div class="layui-input-inline">
                    <cang:select type="dic" groupNo="SP000113" name="idiscountWay"
                                 id="idiscountWay" entire="true" value="${conf.idiscountWay}" />
                </div>
            </div>--%>
            <div class="layui-col-md6">
                <label class="layui-form-label">活动期间总参与次数</label>
                <div class="layui-input-inline">
                    <input type="text" name="iallCount" datatype="n" nullmsg="请输入活动期间总参与次数" errormsg="参与次数必须数字" id="iallCount"
                           value="${conf.iallCount}" placeholder="(0不限制)" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">参与次数限制类型</label>
                <div class="layui-input-inline">
                    <cang:select type="dic" groupNo="SP000115" name="icountType" layFilter="countType"
                                 id="icountType" entire="true" value="${conf.icountType}" />
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label" id="allLabel">(<c:choose><c:when test="${conf.icountType eq 10}">用户</c:when>
                    <c:when test="${conf.icountType eq 20}">设备</c:when><c:otherwise>限制类型</c:otherwise></c:choose>)总参与次数</label>
                <div class="layui-input-inline">
                    <input type="text" name="iuserAllCount" id="iuserAllCount"
                           value="${conf.iuserAllCount}" placeholder="(0不限制)" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label" id="dayLabel">(<c:choose><c:when test="${conf.icountType eq 10}">用户</c:when>
                    <c:when test="${conf.icountType eq 20}">设备</c:when><c:otherwise>限制类型</c:otherwise></c:choose>)单日参与次数</label>
                <div class="layui-input-inline">
                    <input type="text" name="iuserDayCount" id="iuserDayCount"
                           value="${conf.iuserDayCount}" placeholder="(0不限制)" class="layui-input"/>
                </div>
            </div>
        </div>
        <%--<div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">优惠是否叠加</label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="iisSuperposition" id="iisSuperposition" entire="true" disabled="disabled"
                                 entireName="请选择" value="${merchantInfo.iisSuperposition}" list="{0:否,1:是}"/>
                </div>
            </div>
        </div>--%>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">活动开始时间</label>
                <div class="layui-input-inline">
                    <input type="text" name="tactiveStartTime" datatype="*" nullmsg="请选择活动开始时间" id="tactiveStartTime"
                           value="<fmt:formatDate value='${conf.tactiveStartTime}' pattern='yyyy-MM-dd HH:mm:ss'/>" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">活动结束时间</label>
                <div class="layui-input-inline">
                    <input type="text" name="tactiveEndTime"  datatype="*" nullmsg="请选择活动结束时间" id="tactiveEndTime"
                           value="<fmt:formatDate value='${conf.tactiveEndTime}' pattern='yyyy-MM-dd HH:mm:ss'/>" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item" <c:if test="${conf.iscenesType eq 30 or conf.iscenesType eq 40}"> style="display: none;"</c:if><c:if test="${conf.iscenesType ne 30 and conf.iscenesType ne 40}"> style="display: block;"</c:if> id="deviceDiv1">
            <div class="layui-col-md6">
                <label class="layui-form-label">活动设备</label>
                <div class="layui-input-inline" >
                    <input type="radio" name="irangeDevice" lay-filter="rangeDevice" value="0" <c:if test="${conf.irangeType eq 10 || conf.irangeType == 30}"> checked</c:if> title="全部" />
                    <input type="radio" name="irangeDevice" lay-filter="rangeDevice" value="1" <c:if test="${conf.irangeType eq 20 || conf.irangeType == 40}"> checked</c:if> title="部分" />
                </div>
            </div>
            <%--<div class="layui-col-md6">
                <label class="layui-form-label">活动商品</label>
                <div class="layui-input-inline">
                    <input type="radio" name="irangeCommodity" lay-filter="rangeCommodity" value="0" <c:if test="${conf.irangeType eq 10 || conf.irangeType == 20}"> checked</c:if> title="全部" />
                    <input type="radio" name="irangeCommodity" lay-filter="rangeCommodity" value="1" <c:if test="${conf.irangeType eq 30 || conf.irangeType == 40}"> checked</c:if> title="部分" />
                </div>
            </div>--%>
        </div>
        <div class="layui-form-item" <c:if test="${conf.iscenesType eq 30 or conf.iscenesType eq 40}"> style="display: none;"</c:if><c:if test="${conf.iscenesType ne 30 and conf.iscenesType ne 40}"> style="display: block;"</c:if> id="deviceDiv2">
            <div class="layui-col-md6">
                <table class="layui-table" id="deviceTable" style="display:none;margin-left: 200px;">
                    <colgroup>
                        <col width="100">
                        <col width="150">
                        <col>
                    </colgroup>
                    <thead>
                    <tr>
                        <th>设备编号</th>
                        <th>设备名称</th>
                        <th>地址</th>
                    </tr>
                    </thead>
                    <tbody id="deviceBody">
                    </tbody>
                </table>
                <input type="hidden" id="deviceIds" name="deviceIds" value="${useRange.sdeviceId}" />
                <input type="hidden" id="deviceCodes" name="deviceCodes" value="${useRange.sdeviceCode}" />
                <button class="layui-btn layui-btn-primary" id="selectDevice" style="margin-left: 200px;margin-top: 10px;" type="button" <c:if test="${conf.irangeType eq 10 || conf.irangeType == 30}"> disabled="disabled"</c:if>>选择设备</button>
            </div>
            <%--<div class="layui-col-md6">
                <table class="layui-table" id="commodityTable" style="display:none;">
                    <colgroup>
                        <col width="100">
                        <col width="150">
                        <col>
                    </colgroup>
                    <thead>
                    <tr>
                        <th>商品编号</th>
                        <th>商品名称</th>
                        <th>销售价格</th>
                    </tr>
                    </thead>
                    <tbody id="commodityBody">
                    </tbody>
                </table>
                <input type="hidden" id="commodityIds" name="commodityIds" value="${useRange.scommodityId}" />
                <input type="hidden" id="commodityCodes" name="commodityCodes" value="${useRange.scommodityCode}" />
                <button class="layui-btn layui-btn-primary" id="selectCommodity" style="margin-left: 200px;margin-top: 10px;" type="button" <c:if test="${conf.irangeType eq 10 || conf.irangeType == 20}"> disabled="disabled"</c:if>>选择商品</button>
            </div>--%>
        </div>
        <div class="layui-form-item mt13">
            <label class="layui-form-label">活动描叙</label>
            <div class="layui-input-block block_textarea">
                <textarea class="layui-textarea layui-form-textarea80p" datatype="*" nullmsg="请输入活动描叙" id="sdescription" name="sdescription">${conf.sdescription}</textarea>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                <button class="layui-btn" <%--lay-submit="" lay-filter="myFormSub" --%>id="myFormSub">保存</button>
            </div>
        </div>
        <input type="hidden" id="id" name="id" value="${conf.id}"/>
    </form>
</div>
<%--<script id="commodity_table_tmpl" type="text/x-jsrender">
{{for data}}
    <tr>
      <td>{{:commodityCode}}</td>
      <td>{{:commodityName}}</td>
      <td>{{:salePrice}}</td>
    </tr>
{{/for}}
</script>--%>
<script id="device_table_tmpl" type="text/x-jsrender">
{{for data}}
    <tr>
      <td>{{:deviceCode}}</td>
      <td>{{:deviceName}}</td>
      <td>{{:address}}</td>
    </tr>
{{/for}}
</script>
<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/jsrender.min.js"  type="text/javascript"></script>
<script src="${staticSource}/resources/js/jsrender.converters.js"  type="text/javascript"></script>
<script src="${staticSource}/resources/js/validform_min.js"  type="text/javascript"></script>
<c:if test="${conf.iscenesType eq 20 or conf.iscenesType eq 30
or conf.iscenesType eq 40 or conf.iscenesType eq 50}">
<script type="text/javascript">
    $("#icountType").attr("disabled", "disabled");
    $("#iuserAllCount").attr("disabled", "disabled");
    $("#iuserDayCount").attr("disabled", "disabled");
    $("#iuserAllCount").val("");
    $("#iuserDayCount").val("");
</script>
</c:if>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate'], function () {
        var laydate = layui.laydate;

        var nowTime = new Date().valueOf();
        var min = null;
        var max = null;

        var start = laydate.render({
            elem: '#tactiveStartTime',
            type: 'datetime',
            min: nowTime,
            done: function(value, date){
                endMax = end.config.max;
                end.config.min = date;
                end.config.min.month = date.month -1;
                if(!isEmpty(value)) { //监听日期被切换
                    $("#tactiveStartTime").removeClass("Validform_error");
                    $("#tactiveStartTime").parent().find("span").hide();
                } else {
                    $("#tactiveStartTime").addClass("Validform_error");
                    $("#tactiveStartTime").parent().find("span").html($("#tactiveStartTime").attr("nullmsg"));
                    $("#tactiveStartTime").parent().find("span").removeClass("Validform_right");
                    $("#tactiveStartTime").parent().find("span").addClass("Validform_wrong");
                    $("#tactiveStartTime").parent().find("span").show();
                }
            }
        });
        var end = laydate.render({
            elem: '#tactiveEndTime',
            type: 'datetime',
            min: nowTime,
            done: function(value, date){
                if($.trim(value) == ''){
                    var curDate = new Date();
                    date = {'date': curDate.getDate(), 'month': curDate.getMonth()+1, 'year': curDate.getFullYear()};
                }
                start.config.max = date;
                start.config.max.month = date.month -1;
                if(!isEmpty(value)) {
                    $("#tactiveEndTime").removeClass("Validform_error");
                    $("#tactiveEndTime").parent().find("span").hide();
                } else {
                    $("#tactiveEndTime").addClass("Validform_error");
                    $("#tactiveEndTime").parent().find("span").html($("#tactiveEndTime").attr("nullmsg"));
                    $("#tactiveEndTime").parent().find("span").removeClass("Validform_right");
                    $("#tactiveEndTime").parent().find("span").addClass("Validform_wrong");
                    $("#tactiveEndTime").parent().find("span").show();
                }
            }
        });

        var form = layui.form;
        $("#myForm").Validform({
            btnSubmit:"#myFormSub",  //根据id触发
            tiptype:3,                  //第三种方式
            showAllError:true,
            beforeSubmit:function(){ //验证过后执行save方法
                var rangeDevice = $('input[name="irangeDevice"]:checked').val();
                if(rangeDevice == 1) {
                    if(isEmpty($("#deviceIds").val())) {
                        alertDel("请选择活动参与设备");
                        return false;
                    }
                }
                /*var rangeCommodity = $('input[name="irangeCommodity"]:checked').val();
                if(rangeCommodity == 1) {
                    if(isEmpty($("#commodityIds").val())) {
                        alertDel("请选择活动参与商品");
                        return false;
                    }
                }*/
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
                                'index':index
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

       /* //监听提交
        form.on('submit(myFormSub)', function () {
            var rangeDevice = $('input[name="irangeDevice"]:checked').val();
            if(rangeDevice == 1) {
                if(isEmpty($("#deviceIds").val())) {
                    alertDel("请选择活动参与设备");
                    return;
                }
            }
            var rangeCommodity = $('input[name="irangeCommodity"]:checked').val();
            if(rangeCommodity == 1) {
                if(isEmpty($("#commodityIds").val())) {
                    alertDel("请选择活动参与商品");
                    return;
                }
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
                            cancel: function () {
                                parent.closeLayerAndRefresh(index);
                            }
                        }, function () {
                            parent.closeLayerAndRefresh(index);
                        });
                    } else {
                        alertDel(msg.msg);
                    }
                }
            });
            return false;
        });*/
        form.on('select(scenesType)', function(data){

            if(!isEmpty(data.value)) {
                $("#iscenesType").parent().find("span").hide();
                if(data.value == 20 || data.value == 30 || data.value == 40 || data.value == 50) {
                    $("#icountType").attr("disabled", "disabled");
                    $("#iuserAllCount").attr("disabled", "disabled");
                    $("#iuserDayCount").attr("disabled", "disabled");
                } else {
                    $("#icountType").removeAttr("disabled");
                    $("#iuserAllCount").removeAttr("disabled");
                    $("#iuserDayCount").removeAttr("disabled");
                }

                if (data.value == 30 || data.value == 40) {
                    $("#deviceDiv1").hide();
                    $("#deviceDiv2").hide();
                } else {
                    $("#deviceDiv1").show();
                    $("#deviceDiv2").show();
                }
                form.render('select');
            } else {
                $("#deviceDiv1").show();
                $("#deviceDiv2").show();
                $("#iscenesType").parent().find("span").show();
                if (!$("#iscenesType").parent().find("span").hasClass("Validform_wrong")) {
                    $("#iscenesType").parent().find("span").html($("#iscenesType").attr("nullmsg"));
                    $("#iscenesType").parent().find("span").removeClass("Validform_right");
                    $("#iscenesType").parent().find("span").addClass("Validform_wrong");
                }
            }
        });
        form.on('select(countType)', function(data){
            if (data.value == 10) {
                //用户
                $("#allLabel").html("(用户)总参与次数");
                $("#dayLabel").html("(用户)单日参与次数");
            } else if (data.value == 20) {
                //设备
                $("#allLabel").html("(设备)总参与次数");
                $("#dayLabel").html("(设备)单日参与次数");
            } else {
                //限制类型
                $("#allLabel").html("(限制类型)总参与次数");
                $("#dayLabel").html("(限制类型)单日参与次数");
            }
            if(!isEmpty(data.value)) {
                $("#icountType").parent().find("span").hide();
            } else {
                $("#icountType").parent().find("span").show();
                if (!$("#icountType").parent().find("span").hasClass("Validform_wrong")) {
                    $("#icountType").parent().find("span").html($("#icountType").attr("nullmsg"));
                    $("#icountType").parent().find("span").removeClass("Validform_right");
                    $("#icountType").parent().find("span").addClass("Validform_wrong");
                }
            }
        });
       //设备单选
        form.on('radio(rangeDevice)', function(data){
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
        });
        /* //商品单选
        form.on('radio(rangeCommodity)', function(data){
            if (data.value == 1) {
                //部分商品
                $("#selectCommodity").removeAttr("disabled");
            } else {
                //全部商品
                $("#selectCommodity").attr("disabled", "disabled");
                $("#commodityIds").val("");
                $("#commodityCodes").val("");
                hideCommodityTable();
            }
        });*/
    });
    $(function () {

        //返回
        $("#cancelBtn").click(function () {
            parent.layer.close(index);
            return false;
        });
        //选择设备
        $("#selectDevice").click(function() {
            var deviceIds = $("#deviceIds").val();
            var deviceCodes = $("#deviceCodes").val();
            if (!isEmpty(deviceIds)) {
                showLayerMax("选择设备信息",ctx+"/common/selectDevice?deviceIds="+deviceIds+"&deviceCodes="+deviceCodes);
            } else {
                showLayerMax("选择设备信息",ctx+"/common/selectDevice");
            }
        });
        /*//选择商品
        $("#selectCommodity").click(function() {
            var commodityIds = $("#commodityIds").val();
            var commodityCodes = $("#commodityCodes").val();
            if (!isEmpty(commodityIds)) {
                showLayer("选择商品",ctx+"/common/selectCommodity?commodityIds="+commodityIds+"&commodityCodes="+commodityCodes,{ area: ['90%', '80%']});
            } else {
                showLayer("选择商品",ctx+"/common/selectCommodity",{ area: ['90%', '80%']});
            }
        });*/
        initDeviceTable();
        //initCommodityTable();
    });
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
    //隐藏表格 设备
    function hideDeviceTable() {
        $("#deviceBody").html("");
        $("#deviceTable").hide();
    }
    //确认选择设备
    function selectDevice(deviceIds, deviceCodes) {
        $("#deviceIds").val(deviceIds);
        $("#deviceCodes").val(deviceCodes);
        initDeviceTable();
    }
    /*//隐藏表格 商品
    function hideCommodityTable() {
        $("#commodityBody").html("");
        $("#commodityTable").hide();
    }
    //初始化商品表格
    function initCommodityTable() {
        var commodityIds = $("#commodityIds").val();
        if(isEmpty(commodityIds)) {//没有选择设备
            hideCommodityTable();
            return;
        }
        $.ajax({
            url:ctx+'/common/getCommodityByIds',
            type:'POST', //GET
            async:false,    //或false,是否异步
            data:{commodityIds:commodityIds},
            dataType:'json',
            error:function(){
                hideCommodityTable();
            },
            success:function(msg){
                if(msg.success) {//成功返回
                    var commodityList = msg.data;
                    if(!isEmpty(commodityList)) {
                        var html = $("#commodity_table_tmpl").render(msg);
                        $("#commodityBody").html(html);
                        $("#commodityTable").show();
                    } else {
                        hideCommodityTable();
                    }
                } else {
                    hideCommodityTable();
                }
            }
        });
    }
    //确认选择商品
    function selectCommodity(commodityIds, commodityCodes) {
        $("#commodityIds").val(commodityIds);
        $("#commodityCodes").val(commodityCodes);
        initCommodityTable();
    }*/
</script>
</body>
</html>

