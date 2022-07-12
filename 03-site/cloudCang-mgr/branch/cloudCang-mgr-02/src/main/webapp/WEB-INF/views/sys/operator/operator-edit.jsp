<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>用户编辑</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?1323" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/operator/save" method="post" id="myForm">
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">用户名</label>
                <div class="layui-input-inline">
                    <input type="text" name="suserName" id="suserName" value="${operator.suserName}"
                           datatype="*" nullmsg="请输入用户名"
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">真实姓名</label>
                <div class="layui-input-inline">
                    <input type="text" name="srealName" id="srealName" value="${operator.srealName}"
                           datatype="*" nullmsg="请输入真实姓名"
                           class="layui-input"/>
                </div>
            </div>
        </div>
        <c:if test="${ empty operator.id}">
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">密码</label>
                    <div class="layui-input-inline">
                        <input type="password" name="spassword" id="spassword" value="" datatype="*" nullmsg="请输入密码"
                               class="layui-input"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label">确认密码</label>
                    <div class="layui-input-inline">
                        <input type="password" name="spasswordl" id="spasswordl" value="" datatype="*" nullmsg="请输入确认密码"
                               class="layui-input"/>
                    </div>
                </div>
            </div>
        </c:if>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">手机号</label>
                <div class="layui-input-inline">
                    <input type="text" name="smobile" datatype="*" nullmsg="请输入手机号" id="smobile"
                           value="${operator.smobile}"
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">邮箱</label>
                <div class="layui-input-inline">
                    <input type="text" name="smail" id="smail" value="${operator.smail}" class="layui-input"
                           datatype="*" nullmsg="请输入邮箱"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">是否启用</label>
                <div class="layui-input-inline">
                    <cang:select type="list" layFilter="scenesType"  name="bisFreeze" id="bisFreeze"
                                 exp="datatype=\"*\" nullmsg=\"请选择启禁用状态\""
                                 value="${operator.bisFreeze}"
                                 list="{1:启用,0:禁用}"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">是否BD</label>
                <div class="layui-input-inline">
                    <%--  <select id="iisBd" name="iisBd" class="iisBd" onchange="selectOnchang(this)">
                          <option value="0" <c:if test="${codeGenerator.banewBegin eq 0}">selected="true"</c:if>>否</option>
                          <option value="1" <c:if test="${codeGenerator.banewBegin eq 1}">selected="true"</c:if>>是</option>
                      </select>--%>
                    <cang:select type="list" name="iisBd" id="iisBd" value="${operator.iisBd}"
                                 layFilter="iisBd"
                                 list="{0:否,1:是}" exp="datatype=\"*\" nullmsg=\"请选择BD状态\""/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">管理活动设备</label>
                <div class="layui-input-inline">
                    <input type="radio" name="idevType" lay-filter="rangeDevice" value="0" <c:if
                            test="${(empty operator.idevType ? 0 : operator.idevType) eq 0}"> checked</c:if> title="全部"/>
                    <%--   <input type="radio" name="idevType" lay-filter="rangeDevice" value="1" <c:if
                               test="${operator.idevType eq 1}"> checked</c:if> title="指定分组"/>--%>
                    <input type="radio" name="idevType" lay-filter="rangeDevice" value="2" <c:if
                            test="${operator.idevType eq 2}"> checked</c:if> title="指定设备"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">管理商户</label>
                <div class="layui-input-inline">
                    <div>
                        <input type="radio" name="imerType" lay-filter="rangeCommodity" value="0" <c:if
                                test="${(empty operator.imerType?0:operator.imerType) eq 0}"> checked</c:if> title="全部" id="imerType1"/>
                    </div>
                    <div id="selectIsBd">
                        <input type="radio" name="imerType" lay-filter="rangeCommodity" value="1" <c:if
                                test="${operator.imerType eq 1}"> checked</c:if> title="对应bd管理商户" id="imerType2"/>
                    </div>
                    <div>
                        <input type="radio" name="imerType" lay-filter="rangeCommodity" value="2" <c:if
                                test="${operator.imerType eq 2}"> checked</c:if> title="自己商户"/>
                    </div>
                    <div>
                        <input type="radio" name="imerType" lay-filter="rangeCommodity" value="3" <c:if
                                test="${operator.imerType eq 3}"> checked</c:if> title="指定商户"/>
                    </div>
                    <div>
                        <input type="radio" name="imerType" lay-filter="rangeCommodity" value="4" <c:if
                                test="${operator.imerType eq 4}"> checked</c:if> title="对应bd管理商户及以下"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
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
                        <th>地址</th>
                        <th>设备分组</th>
                    </tr>
                    </thead>
                    <tbody id="deviceBody">
                    </tbody>
                </table>
                <c:choose>
                    <c:when test="${2 == operator.idevType}">
                        <input type="hidden" id="sgroupDecList" name="sgroupDecList"
                               value="${operator.sgroupDecList}"/>
                    </c:when>
                    <c:otherwise>
                        <input type="hidden" id="sgroupDecList" name="sgroupDecList" value=""/>
                    </c:otherwise>
                </c:choose>
                <input type="hidden" id="deviceCodes" name="deviceCodes" value=""/>
                <button class="layui-btn layui-btn-primary" id="selectDevice"
                        style="margin-left: 180px;margin-top: 10px;" type="button" <c:if
                        test="${operator.idevType ne 2}"> disabled="disabled"</c:if>>选择设备
                </button>
            </div>

            <%--         <div class="layui-col-md6">
                         <table class="layui-table" id="deviceGroupTable" style="display:none;">
                             <colgroup>
                                 <col width="100">
                                 <col width="150">
                                 <col>
                             </colgroup>
                             <thead>
                             <tr>
                                 <th>分组名称</th>
                                 <th>排序号</th>
                                 <th>备注</th>
                             </tr>
                             </thead>
                             <tbody id="deviceGroupBody">
                             </tbody>
                         </table>
                         <c:choose>
                             <c:when test="${1 == operator.idevType}">
                             <input type="hidden" id="deviceIdGroups" name="deviceIdGroups" value="${operator.sgroupDecList}"/>
                             </c:when>
                             <c:otherwise>
                                 <input type="hidden" id="deviceIdGroups" name="deviceIdGroups" value=""/>
                             </c:otherwise>
                         </c:choose>
                         <button class="layui-btn layui-btn-primary" id="selectDeviceGroup"
                                 style="margin-left: 180px;margin-top: 10px;" type="button" <c:if
                                 test="${operator.idevType ne 1}"> disabled="disabled"</c:if>>选择分组
                         </button>
                     </div>--%>


            <div class="layui-col-md6">
                <table class="layui-table" id="commodityTable" style="display:none;">
                    <colgroup>
                        <col width="100">
                        <col width="150">
                        <col>
                    </colgroup>
                    <thead>
                    <tr>
                        <th>商户编号</th>
                        <th>商户名称</th>
                    </tr>
                    </thead>
                    <tbody id="commodityBody">
                    </tbody>
                </table>
                <input type="hidden" id="smerList" name="smerList" value="${operator.smerList}"/>
                <input type="hidden" id="commodityCodes" name="commodityCodes" value="${operator.sgroupDecList}"/>
                <button class="layui-btn layui-btn-primary" id="selectCommodity"
                        style="margin-left: 180px;margin-top: 10px;" type="button" <c:if
                        test="${operator.imerType ne 3}"> disabled="disabled"</c:if>>选择商户
                </button>
            </div>
        </div>

        <div class="layui-form-item mt13">
            <label class="layui-form-label">备注</label>
            <div class="layui-input-block">
                    <textarea id="sremark" name="sremark" class="layui-textarea layui-form-textarea80p"
                              placeholder="备注（非必填项）">${operator.sremark}</textarea>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                <button class="layui-btn" id="myFormSub">保存</button>
            </div>
        </div>
        <input type="hidden" id="id" name="id" value="${operator.id}"/>
    </form>
</div>
<script id="commodity_table_tmpl" type="text/x-jsrender">
{{for data}}
    <tr>
        <td>{{:merchantCode}}</td>
        <td>{{:merchantName}}</td>
    </tr>
{{/for}}
</script>
<script id="device_table_tmpl" type="text/x-jsrender">
{{for data}}
    <tr>
      <td>{{:deviceCode}}</td>
      <td>{{:deviceName}}</td>
      <td>{{:address}}</td>
      <td>{{:groupName}}</td>
    </tr>
{{/for}}
</script>
<script src="${staticSource}/resources/js/jsrender.min.js" type="text/javascript"></script>
<script src="${staticSource}/resources/js/jsrender.converters.js" type="text/javascript"></script>
<script src="${staticSource}/resources/js/validform_min.js" type="text/javascript"></script>
<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        if (${not  empty operator.id}) {
            $("#suserName").attr("disabled", true);
        }
    });
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引

    layui.use(['form', 'layedit', 'laydate'], function () {

        var form = layui.form;
        //select监听是否Bd
        form.on('select(iisBd)', function (data) {
            if (0 == data.value) {
                //商户隐藏
                $("#selectIsBd").css("display", "none");
                var val = $('input:radio[name="imerType"]:checked').val();
                if (val == 1) {
                    $("input[name='imerType']").get(0).checked = true;
                    form.render('radio');
                }
            } else if (1 == data.value) {
                $("#selectIsBd").css("display", "block");
            }
        });
        //设备单选
        form.on('radio(rangeDevice)', function (data) {
            //设备分组
            /* if (1 == data.value) {
             $("#selectDeviceGroup").removeAttr("disabled");
             $("#selectDevice").attr("disabled", "disabled");
             $("#sgroupDecList").val("");
             $("#deviceCodes").val("");
             hideDeviceTable();
             } else*/
            if (data.value == 2) {
                //部分设备
                $("#selectDevice").removeAttr("disabled");
                /* $("#selectDeviceGroup").attr("disabled", "disabled");
                 $("#deviceIdGroups").val("");*/
                hideDeviceGroupTable();
            } else {
                //全部设备
                $("#selectDevice").attr("disabled", "disabled");
                /*     $("#selectDeviceGroup").attr("disabled", "disabled");*/
                $("#sgroupDecList").val("");
                $("#deviceCodes").val("");
                /*  $("#deviceIdGroups").val("");*/
                hideDeviceTable();
                /*   hideDeviceGroupTable();*/
            }
        });
        //
        form.on('radio(rangeCommodity)', function (data) {
            if (data.value == 3) {
                //指定商户
                $("#selectCommodity").removeAttr("disabled");
            } else {
                //其他商户类型
                $("#selectCommodity").attr("disabled", "disabled");
                $("#smerList").val("");
                $("#commodityCodes").val("");
                hideCommodityTable();
            }
        });
        //监听提交
        $("#myForm").Validform({
            btnSubmit: "#myFormSub",  //根据id触发
            tiptype: 3,                  //第三种方式
            showAllError: true,
            beforeSubmit: function () { //验证过后执行save方法

                var rangeDevice = $('input[name="idevType"]:checked').val();
                if (null == rangeDevice) {
                    alertDel("请选择管理活动设备类型");
                    return false;
                }
                if (2 == rangeDevice) {
                    if (isEmpty($("#sgroupDecList").val())) {
                        alertDel("请选择指定设备");
                        return false;
                    }
                }
                var merType = $('input[name="imerType"]:checked').val();
                if (null == merType) {
                    alertDel("请选择管理商户类型");
                    return false;
                }
                if (3 == merType) {
                    if (isEmpty($("#smerList").val())) {
                        alertDel("请选择指定商户");
                        return false;
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
                               /* cancel: function () {
                                    parent.closeLayerAndRefresh(index);
                                }*/
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
        form.on('select(scenesType)', function(data){
            if(!isEmpty(data.value)) {
                $("#bisFreeze").parent().find("span").hide();
            } else {
                $("#bisFreeze").parent().find("span").show();
                if (!$("#bisFreeze").parent().find("span").hasClass("Validform_wrong")) {
                    $("#bisFreeze").parent().find("span").html($("#bisFreeze").attr("nullmsg"));
                    $("#bisFreeze").parent().find("span").removeClass("Validform_right");
                    $("#bisFreeze").parent().find("span").addClass("Validform_wrong");
                }
            }
        });


    });

    $(function () {

        $("#cancelBtn").click(function () {
            parent.layer.close(index);
            return false;
        });
        //选择设备
        $("#selectDevice").click(function () {

            var deviceIds = $("#sgroupDecList").val();
            var deviceCodes = $("#deviceCodes").val();
            if (!isEmpty(deviceIds)) {
                showLayer("选择设备", ctx + "/common/selectDevice?deviceIds=" + deviceIds + "&deviceCodes=" + deviceCodes+"&allIstatus=10", {area: ['90%', '80%']});
            } else {
                showLayer("选择设备", ctx + "/common/selectDevice?allIstatus=10", {area: ['90%', '80%']});
            }
        });
        //选择设备分组
        /*   $("#selectDeviceGroup").click(function () {
         var deviceIdGroups = $("#deviceIdGroups").val();
         if (!isEmpty(deviceIdGroups)) {
         showLayer("选择分组", ctx + "/common/selectDeviceGroup?deviceIdGroups="+deviceIdGroups,{area: ['100%', '80%']});
         } else {
         showLayer("选择分组", ctx + "/common/selectDeviceGroup", {area: ['100%', '80%']});
         }
         });*/
        //选择商户
        $("#selectCommodity").click(function () {
            var commodityIds = $("#smerList").val();
            var commodityCodes = $("#commodityCodes").val();
            if (!isEmpty(smerList)) {
                showLayer("选择商户", ctx + "/merchantInfo/selectMerchant?commodityIds=" + commodityIds + "&commodityCodes=" + commodityCodes, {area: ['90%', '80%']});
            } else {
                showLayer("选择商户", ctx + "/merchantInfo/selectMerchant", {area: ['90%', '80%']});
            }
        });

        initDeviceTable();
        initCommodityTable();

    });


    //初始化设备表格
    function initDeviceTable() {

        var deviceIds = $("#sgroupDecList").val();

        if (isEmpty(deviceIds)) {//没有选择设备
            hideDeviceTable();
            return;
        }
        $.ajax({
            url: ctx + '/common/getDeviceAndGroupByIds',
            type: 'POST', //GET
            async: false,    //或false,是否异步
            data: {deviceIds: deviceIds},
            dataType: 'json',
            error: function () {
                hideDeviceTable();
            },
            success: function (msg) {
                if (msg.success) {//成功返回0
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
    //隐藏表格 设备
    function hideDeviceTable() {

        $("#deviceBody").html("");
        $("#deviceTable").hide();
    }
    //隐藏表格 设备分组
    /* function hideDeviceGroupTable() {
     $("#deviceGroupBody").html("");
     $("#deviceGroupTable").hide();
     }*/
    //隐藏表格 商品
    function hideCommodityTable() {
        $("#commodityBody").html("");
        $("#commodityTable").hide();
    }
    //初始化商品表格
    function initCommodityTable() {
        var commodityIds = $("#smerList").val();
        if (isEmpty(commodityIds)) {//没有选择商户
            hideCommodityTable();
            return;
        }
        $.ajax({
            url: ctx + '/merchantInfo/getMerchantByIds',
            type: 'POST', //GET
            async: false,    //或false,是否异步
            data: {commodityIds: commodityIds},
            dataType: 'json',
            error: function () {
                hideCommodityTable();
            },
            success: function (msg) {
                if (msg.success) {//成功返回
                    var commodityList = msg.data;
                    if (!isEmpty(commodityList)) {
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
    /*    function initDeviceGroupTable() {

     var deviceIdGroups = $("#deviceIdGroups").val();

     if (isEmpty(deviceIdGroups)) {//没有选择设备
     hideDeviceGroupTable();
     return;
     }
     //查询分组
     $.ajax({
     url: ctx + "/common/getDeviceGroupByIds",
     type: 'POST', //GET
     async: false,    //或false,是否异步
     data: {deviceGroupIds: deviceIdGroups},
     dataType: 'json',
     error: function () {
     hideDeviceGroupTable();
     },
     success: function (msg) {
     if (msg.success) {//成功返回

     var deviceGroupList = msg.data;
     if (!isEmpty(deviceGroupList)) {
     var html = $("#commodityGroup_table_tmpl").render(msg);
     $("#deviceGroupBody").html(html);
     $("#deviceGroupTable").show();
     } else {
     hideDeviceGroupTable();
     }
     } else {
     hideDeviceGroupTable();
     }
     }
     });
     }*/
    //确认选择设备
    function selectDevice(sgroupDecList, deviceCodes) {
        $("#sgroupDecList").val(sgroupDecList);
        $("#deviceCodes").val(deviceCodes);
        initDeviceTable();
    }
    //确认选择商户
    function selectCommodity(smerList, commodityCodes) {
        $("#smerList").val(smerList);
        $("#commodityCodes").val(commodityCodes);
        initCommodityTable();
    }
    //确认选择设备分组
    /* function selectDeviceGroup(deviceIdGroups) {
     $("#deviceIdGroups").val(deviceIdGroups);
     initDeviceGroupTable();
     }*/
</script>
</body>

