<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备列表</title>
<link href="${staticSource}/resources/layui/css/layui.css?1" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?3" rel="stylesheet">
<style>
    .ui-jqgrid tr.jqgrow td {
        white-space: normal !important;
        height: auto;
    }
    .layui-btn-hui{background:#f2f2f2;border:1px solid #e6e6e6;color:#323232;}
    .layui-btn-hui:hover{color:#323232;}
</style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>设备管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm" style="margin-bottom:13px;">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder="设备编号..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sname" id="sname" value="" placeholder="设备名称..." class="layui-input">
                            </div>
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-input-inline">
                                    <input type="text" name="smerchantCode" id="smerchantCode" value="" placeholder="商户编号..." class="layui-input">
                                </div>
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantName" id="merchantName" value="" placeholder="商户名称..." class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-input-inline">
                                <input type="text" name="sputAddress" id="sputAddress" value="" placeholder="投放地址..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sgroupName" id="sgroupName" value="" placeholder="设备分组..." class="layui-input">
                            </div>

                            <div class="layui-input-inline">
                                <select class="form-control"  name="istatus" id="istatus" >
                                    <option value="">请选择设备状态</option>
                                    <option value="10">未注册</option>
                                    <option value="20">正常</option>
                                    <option value="30">故障</option>
                                    <option value="40">报废</option>
                                    <option value="50">离线</option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control"  name="ioperateStatus" id="ioperateStatus" >
                                    <option value="">请选择运营状态</option>
                                    <option value="10">启用</option>
                                    <option value="20">停用</option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control"  name="icooperationMode" id="icooperationMode" >
                                    <option value="">请选择合作模式</option>
                                    <option value="10">采购</option>
                                    <option value="20">租用</option>
                                    <option value="30">自主</option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control"  name="iscreenDisplayType" id="iscreenDisplayType" >
                                    <option value="">请选择屏幕显示类型</option>
                                    <option value="10">横屏</option>
                                    <option value="20">竖屏</option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query" style="float:left">
                                    <i class="layui-icon">&#xe615;</i>查询
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset" style="margin-bottom:0">
                                    <i class="layui-icon">&#x1006;</i>清除
                                </button>
                            </div>
                        </div>
                        <div class="layui-form-item" style="margin-bottom: 10px;">
                            <%--批量操作按钮--%>
                                <shiro:hasPermission name="DEVICE_INFO_ADD">
                            <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add" style="float:left"><i class="layui-icon"></i>添加</button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="DEVICE_INFO_DELETE">
                            <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i>删除</button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="DEVICE_INFO_VOLUME">
                            <button class="layui-btn layui-btn-sm layui-btn-hui" data-type="volume"><i class="layui-icon">&#xe645;</i>音量</button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="DEVICE_INFO_INVENTORY">
                            <button class="layui-btn layui-btn-sm layui-btn-hui" data-type="inventory"><i class="layui-icon">&#xe63c;</i>盘货</button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="DEVICE_INFO_UPGRADEVOICE">
                            <button class="layui-btn layui-btn-sm layui-btn-hui" data-type="upgradeVoice"><i class="layui-icon">&#xe645;</i>升级语音</button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="DEVICE_INFO_SYSTEMUPGRADE">
                            <button class="layui-btn layui-btn-sm layui-btn-hui" data-type="systemUpgrade"><i class="layui-icon">&#xe631;</i>APK升级</button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="DEVICE_INFO_REBOOT">
                            <button class="layui-btn layui-btn-sm layui-btn-hui" data-type="reboot"><i class="layui-icon">&#x1002;</i>重启</button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="DEVICE_INFO_SHUTDOWN">
                            <button class="layui-btn layui-btn-sm layui-btn-hui" data-type="shutdown"><i class="layui-icon">&#xe631;</i>关机</button>
                                </shiro:hasPermission>
                                <%--<shiro:hasPermission name="DEVICE_INFO_TIMINGBOOT">--%>
                            <%--<button class="layui-btn layui-btn-sm layui-btn-hui" data-type="timingBoot"><i class="layui-icon">&#xe620;</i>定时开关机</button>--%>
                                <%--</shiro:hasPermission>--%>
                                <shiro:hasPermission name="DEVICE_INFO_DEVICEGROUP">
                             <button class="layui-btn layui-btn-sm layui-btn-hui" data-type="deviceGroup"><i class="layui-icon">&#xe63c;</i>分组</button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="DEVICE_INFO_UNREGISTER">
                             <button class="layui-btn layui-btn-sm layui-btn-hui" data-type="unRegistered"><i class="layui-icon">&#xe60b;</i>未注册</button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="DEVICE_INFO_REGISTER">
                             <button class="layui-btn layui-btn-sm layui-btn-hui" data-type="registered"><i class="layui-icon">&#xe62c;</i>已注册</button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="DEVICE_INFO_VR_SERVER_UPGRADE">
                             <button class="layui-btn layui-btn-sm layui-btn-hui" data-type="vrServerUpgrade"><i class="layui-icon">&#xe628;</i>视觉服务器更新</button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="DEVICE_INFO_VR_LIB_UPGRADE">
                             <button class="layui-btn layui-btn-sm layui-btn-hui" data-type="vrLibUpgrade"><i class="layui-icon">&#xe615;</i>视觉库更新</button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="DEVICE_INFO_SEND_AD">
                             <button class="layui-btn layui-btn-sm layui-btn-hui" data-type="sendAdToDevice"><i class="layui-icon">&#xe609;</i>推送广告</button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="DEVICE_INFO_BATCH_REALTIME_SWITCH">
                             <button class="layui-btn layui-btn-sm layui-btn-hui" data-type="sendRealTimeReplentshment"><i class="layui-icon">&#xe60e;</i>实时购物车</button>
                                </shiro:hasPermission>

                        </div>
                    </div>
                    <div class="jqGrid_wrapper">
                        <table id="gridTable"></table>
                        <div id="gridPager"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<script src="${staticSource}/resources/js/xlsx.core.min.js"></script>
<script src="${staticSource}/resources/js/tableExport.min.js"></script>
<script src="${staticSource}/resources/js/tableExportComm.js?2"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    var colModel =[
        {name: 'id', index: 'id', hidden: true},
        {name: 'scode', index: 'SCODE', label: "设备编号",width:100},
        {name: 'sname', index: 'SNAME', label: "设备名称",width:120},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode',label : "商户编号",index: 'smerchant_code',width:120,sortable : false},
        {name: 'merchantName',label : "商户名称",index: 'merchantName',width:120,sortable : false},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'smerchantCode', index: 'smerchant_code', hidden: true},
        </c:if>
        {name: 'address', label: "投放地址",width:160,sortable : false},
        {name: 'sgroupName', index: 'SGROUP_NAME', label: "设备分组",width:70,sortable : false},
        {name: 'istatus',index: 'ISTATUS',label: "设备状态",
            formatter: function(value, options, rowObject) { return '<span id="istatus_'+rowObject.id+'" data="'+value+'" class="' + (({"10":"istatus-normal","20":"","30":"istatus-warm","40":"istatus-radius","50":"istatus-danger"})[value]) + '">' +  (({"10":"● 未注册","20":"● 正常","30":"● 故障","40":"● 报废","50":"● 离线"})[value])+ '</span>' },
            width:80
        },
        {name: 'ioperateStatus',index: 'IOPERATE_STATUS',label: "运营状态", formatter: "select",editoptions: {value: '10:启用;20:停用'},width:80},
        {name: 'istandardStockStatus', label: "标准库存状态", formatter: "select", editoptions: {value: '10:启用;20:关闭'}, width:100,sortable : false},
        {name: 'icooperationMode',index: 'ICOOPERATION_MODE',label: "合作模式",formatter: "select",editoptions: {value: '10:采购;20:租用;30:自主'},width: 80},
        {name: 'idockingType',index: 'IDOCKING_TYPE',label: "系统对接类型",formatter: "select",editoptions: {value: '10:自主研发;20:第三方'},width: 100},
        {name: 'iscreenDisplayType',index: 'ISCREEN_DISPLAY_TYPE',label: "屏幕显示类型",formatter: "select",editoptions: {value: '10:横屏;20:竖屏'},width: 100},
        /*{
         name: 'icontainerType',
         index: 'ICONTAINER_TYPE',
         label: "货柜类型",
         formatter: "select",
         editoptions: {value: '10:单开门;20:双开门'},
         width:120         },*/

        /*{
         name: 'icompressorType',
         index: 'ICOMPRESSOR_TYPE',
         label: "压缩机类型",
         formatter: "select",
         editoptions: {value: '10:制冷;20:制热;30:制冷热'},
         width:120
         },*/
        {
            name: 'taddTime', index: 'TADD_TIME', label: "添加日期", width: 80,formatter: function (value) {
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');}
        },
        {name: 'itype', index: 'itype', hidden: true}, /*设备类型*/
        {name: "process",title:false, index: "process", label: "操作", sortable: false, frozen: true, width: 240}
    ];
    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            var data = $("#gridTable").jqGrid('getRowData');
            var merchantCode = "${SESSION_KEY_SMERCHANT_CODE}";
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = '';
                <shiro:hasPermission name="DEVICE_INFO_VIEW">
                    str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"viewMethod('"
                        + cl + "')\" title='查看' alt='查看'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="DEVICE_INFO_EDIT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                        + cl + "')\" title='编辑' alt='编辑'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="DEVICE_INFO_DELETE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                        + cl + "')\" title='删除' alt='删除'  /> | ";
                </shiro:hasPermission>
                if (data[i].ioperateStatus == 20) {
                    <shiro:hasPermission name="DEVICE_INFO_OPERATING_ENABLED">
                    str += "<img src='${staticSource}/resources/images/oper_icon/enabled.png' class=\"oper_icon\" onclick=\"deviceEnabledMethod('"
                        + cl + "')\" title='启用' alt='启用'  /> | ";
                    </shiro:hasPermission>
                }else if(data[i].ioperateStatus == 10){
                    <shiro:hasPermission name="DEVICE_INFO_OPERATING_DISABLE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/sb_disable.png' class=\"oper_icon\" onclick=\"deviceDisabledMethod('"
                        + cl + "')\" title='停用' alt='停用'  /> | ";
                    </shiro:hasPermission>
                }
                if ($("#istatus_"+cl).attr("data") == 20) {
                    <shiro:hasPermission name="DEVICE_INFO_STATUS">
                    str += "<img src='${staticSource}/resources/images/oper_icon/status.png' class=\"oper_icon\" onclick=\"offlineMethod('"
                        + cl + "')\" title='离线' alt='离线'  /> | ";
                    </shiro:hasPermission>
                }
                if (merchantCode == data[i].smerchantCode) {
                    <shiro:hasPermission name="DEVICE_INFO_STANDARDSTOCK">
                    str += "<img src='${staticSource}/resources/images/oper_icon/stock.png' class=\"oper_icon\" onclick=\"standardStockMethod('"
                        + cl + "')\" title='标准库存' alt='标准库存'  /> | ";
                    </shiro:hasPermission>
                    if (data[i].istandardStockStatus == 10 && $("#istatus_"+cl).attr("data") == 20) {
                        <shiro:hasPermission name="DEVICE_INFO_DYNAMIC_REPLENISHMENT">
                        str += "<img src='${staticSource}/resources/images/oper_icon/replenishment.png' class=\"oper_icon\" onclick=\"generateReplenishment('"
                            + cl + "')\" title='动态补货单' alt='动态补货单'  /> | ";
                        </shiro:hasPermission>
                    }
                }
                if (data[i].idockingType != 20) {
                    <shiro:hasPermission name="DEVICE_INFO_QRCODE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/code.png' class=\"oper_icon\" onclick=\"qrcodeMethod('"
                        + cl + "')\" title='查看设备二维码' alt='查看设备二维码'  /> | ";
                    </shiro:hasPermission>
                }
                if (data[i].itype == 30 || data[i].itype == 40 || data[i].itype == 50 || data[i].itype == 60) {
                    <shiro:hasPermission name="DEVICE_NET_CAMERA_LIST">
                    str += "<img src='${staticSource}/resources/images/oper_icon/camera.png' class=\"oper_icon\" onclick=\"deviceCameraConfigMethod('"
                        + cl + "')\" title='摄像头配置' alt='摄像头配置'  /> | ";
                    </shiro:hasPermission>
                }
                $("#gridTable").jqGrid('setRowData',
                    cl, {
                        process: str.substr(0, str.lastIndexOf(" | "))
                    });
            }
            if(isExport) {//判断是否导出
                exportFileCallback();
            }
        }, 0);
    }

    $(function () {
        var config = {
            sortname: "ISTATUS=20",
            sortorder: "desc"
        };
        initTable(ctx+"/device/info/queryData",colModel,tableCallBack,config);
    });
    var initBtnConfig = {
        addUrl: ctx+'/device/info/toAdd',
        deleteUrl: ctx+"/device/info/delete",
        addTitle: '添加设备信息',
        addFn:showLayerMedium
    };
    initBtnByForm4(initBtnConfig);

    layui.use('form', function () {
        var $ = layui.$, active = {
            volume: function () {//音量
                showLayerMedium("选择调整音量设备", ctx + '/device/info/deviceAdjustVolume');
            },
            inventory: function () {//盘货
                var sid = $("#gridTable").jqGrid('getGridParam', 'selarrrow');
                if (isEmpty(sid)) {
                    alertDel("请先选择要操作的设备");
                    return;
                }
                if(sid.length != 1) {
                    alertDel("只能选择一台设备");
                    return;
                }
                showLayerMedium("主动盘货", ctx + '/device/info/inventory?deviceId=' + sid);
            },
            upgradeVoice: function () {//升级语音
                showLayerMedium("选择升级设备", ctx + '/device/info/deviceUpgradeVoice');
            },
            systemUpgrade: function () {//系统升级
                showLayerMedium("选择升级设备", ctx + '/device/info/deviceUpgradeAPK');
            },
            reboot: function () {//重启
                showLayerMedium("选择需要重启的设备", ctx + '/device/info/deviceReboot');
            },
            shutdown: function () {//关机
                showLayerMedium("选择需要关机的设备", ctx + '/device/info/deviceShutdown');
            },
            // timingBoot: function () {//定时关机
            //     showLayerMedium("选择需要定时关机的设备", ctx + '/device/info/timingSwitchDevice');
            // },
            disabled: function () {//停用
                var sid = $("#gridTable").jqGrid('getGridParam', 'selarrrow');
                if (isEmpty(sid)) {
                    alertDel("请先选择要停用设备");
                    return;
                }
                confirmOperTip("确定要操作停用设备?", ctx + "/device/info/operate/disabled", {checkboxId: sid.join(","),method:"disabled"});
            },
            unRegistered : function () { //未注册
                showLayerOne("未注册连接详情", ctx + "/device/info/unRegistered");
            },
            registered : function () { //已注册
                showLayerOne("已注册连接详情", ctx + "/device/info/registered");
            },
            vrServerUpgrade : function () { // 视觉服务更新
                showLayerMedium("视觉服务更新", ctx + "/device/info/vrServerUpgrade");
            },
            vrLibUpgrade : function () { // 视觉库更新
                showLayerMedium("视觉库更新", ctx + "/device/info/vrLibUpgrade");
            },
            sendAdToDevice : function () { // 视觉库更新
                showLayerMedium("给设备推送广告", ctx + "/device/info/sendAdToDevice");
            },
            sendRealTimeReplentshment : function () { // 实时盘货开关
                showLayerMedium("设置实时盘货开关", ctx + "/device/info/sendRealTimeReplentshment");
            },
            deviceGroup:function () { //设备分组
                var sid = $("#gridTable").jqGrid('getGridParam', 'selarrrow');
                if (isEmpty(sid)) {
                    alertDel("请先选择要分组的设备");
                    return;
                } else {
                    $.ajax({
                        type:'post',
                        url:'${ctx}/device/info/checkDevice',
                        data:{pid:sid.join(",")},
                        dataType:'json',
                        success:function(msg){
                            if(msg.success){
                                showLayer("选择设备分组", ctx + '/device/info/deviceAddToGroup?sid=' + sid, {area: ['60%', '70%']});
                            }else {
                                alertDel(msg.msg);
                            }
                        }
                    });
                }

            }
        };
        $('.layui-form .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });


        <c:if test="${!empty method}">
            <c:if test="${method eq 'add'}">
                if($.isFunction(initBtnConfig.addFn)) {
                    initBtnConfig.addFn(initBtnConfig.addTitle, initBtnConfig.addUrl, initBtnConfig.addModelConfig);
                } else {
                    showLayer(initBtnConfig.addTitle, initBtnConfig.addUrl, initBtnConfig.addModelConfig)
                }
            </c:if>
        </c:if>
    });
    function viewMethod(sid) {
        showLayerMedium("设备详情", ctx + '/device/info/view?sid=' + sid);
    }
    function editMethod(sid) {
        showLayerMedium("编辑设备信息", ctx + '/device/info/toEdit?sid=' + sid);
    }
    function delMethod(sid) {
        confirmDelTip("确定要删除数据?", ctx + "/device/info/delete", {checkboxId: sid});
    }
    function offlineMethod(sid) {
        confirmOperTip("确定将该设备离线?", ctx + "/device/info/offline", {checkboxId: sid});
    }
    function deviceEnabledMethod(sid) {
        confirmOperTip("确定要启用该设备?", ctx + "/device/info/operateingEanbled", {checkboxId: sid});
    }
    function deviceDisabledMethod(sid) {
        confirmOperTip("确定要停用该设备?", ctx + "/device/info/operateingDisabled", {checkboxId: sid});
    }
   function standardStockMethod(sid) {
       showLayerMax("设备标准库存", ctx + "/device/info/standardStock?sid=" + sid);
    }
    function qrcodeMethod(sid) {
        showLayerOne("设备二维码", ctx + "/device/info/qrCode?sid=" + sid, {area: ['30%', '75%']});
    }
    function deviceCameraConfigMethod(sid) {
        showLayerMax("配置设备摄像头信息", ctx + '/cameraConfig/list?sdeviceId=' + sid);
    }
    function generateReplenishment(sid) {
        showLayerMedium("动态生成补货单", ctx + "/device/info/generateDynamicReplenishment?sid=" + sid);
    }
</script>
</body>
</html>


