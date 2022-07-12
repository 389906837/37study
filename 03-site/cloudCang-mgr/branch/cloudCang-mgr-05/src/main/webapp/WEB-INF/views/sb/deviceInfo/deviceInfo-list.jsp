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
                    <h5><spring:message code='menu.device.management' /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm" style="margin-bottom:13px;">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder='<spring:message code="main.device.id" />...' class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sname" id="sname" value="" placeholder='<spring:message code="main.device.name" />...' class="layui-input">
                            </div>
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-input-inline">
                                    <input type="text" name="smerchantCode" id="smerchantCode" value="" placeholder='<spring:message code="main.merchant.number" />...' class="layui-input">
                                </div>
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantName" id="merchantName" value="" placeholder='<spring:message code="main.merchant.name" />...' class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-input-inline">
                                <input type="text" name="sputAddress" id="sputAddress" value="" placeholder="<spring:message code='sb.delivery.address' />..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sgroupName" id="sgroupName" value="" placeholder="<spring:message code='sh.device.grouping' />..." class="layui-input">
                            </div>

                            <div class="layui-input-inline">
                                <select class="form-control"  name="istatus" id="istatus" >
                                    <option value=""><spring:message code="sb.equipment.status.select" /></option>
                                    <option value="10"><spring:message code='sb.unregistered' /></option>
                                    <option value="20"><spring:message code="main.normal" /></option>
                                    <option value="30"><spring:message code='sb.fault' /></option>
                                    <option value="40"><spring:message code='sb.scrap' /></option>
                                    <option value="50"><spring:message code='sb.offline' /></option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control"  name="ioperateStatus" id="ioperateStatus" >
                                    <option value=""><spring:message code='sb.please.select.the.operating.status' /></option>
                                    <option value="10"><spring:message code="main.enable" /></option>
                                    <option value="20"><spring:message code='tpmanage.disable' /></option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control"  name="icooperationMode" id="icooperationMode" >
                                    <option value=""><spring:message code='sb.please.choose.cooperation.mode' /></option>
                                    <option value="10"><spring:message code='sh.purchase' /></option>
                                    <option value="20"><spring:message code='sb.rent' /></option>
                                    <option value="30"><spring:message code='tpinfo.independence' /></option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control"  name="iscreenDisplayType" id="iscreenDisplayType" >
                                    <option value=""><spring:message code='tpinfotoadd.please.select.the.screen.display.type' /></option>
                                    <option value="10"><spring:message code='tpinfo.horizontal.screen' /></option>
                                    <option value="20"><spring:message code='sb.vertical.screen' /></option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query" style="float:left">
                                    <i class="layui-icon">&#xe615;</i><spring:message code="main.query" />
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset" style="margin-bottom:0">
                                    <i class="layui-icon">&#x1006;</i><spring:message code="main.clear" />
                                </button>
                            </div>
                        </div>
                        <div class="layui-form-item" style="margin-bottom: 10px;">
                            <%--批量操作按钮--%>
                                <shiro:hasPermission name="DEVICE_INFO_ADD">
                            <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add" style="float:left"><i class="layui-icon"></i><spring:message code="main.add" /></button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="DEVICE_INFO_DELETE">
                            <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i><spring:message code="main.delete" /></button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="DEVICE_INFO_VOLUME">
                            <button class="layui-btn layui-btn-sm layui-btn-hui" data-type="volume"><i class="layui-icon">&#xe645;</i><spring:message code='sb.volume' /></button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="DEVICE_INFO_INVENTORY">
                            <button class="layui-btn layui-btn-sm layui-btn-hui" data-type="inventory"><i class="layui-icon">&#xe63c;</i><spring:message code='sb.dish' /></button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="DEVICE_INFO_UPGRADEVOICE">
                            <button class="layui-btn layui-btn-sm layui-btn-hui" data-type="upgradeVoice"><i class="layui-icon">&#xe645;</i><spring:message code='sb.upgrade.voice' /></button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="DEVICE_INFO_SYSTEMUPGRADE">
                            <button class="layui-btn layui-btn-sm layui-btn-hui" data-type="systemUpgrade"><i class="layui-icon">&#xe631;</i><spring:message code='sb.apk.upgrade' /></button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="DEVICE_INFO_REBOOT">
                            <button class="layui-btn layui-btn-sm layui-btn-hui" data-type="reboot"><i class="layui-icon">&#x1002;</i><spring:message code='sb.restart' /></button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="DEVICE_INFO_SHUTDOWN">
                            <button class="layui-btn layui-btn-sm layui-btn-hui" data-type="shutdown"><i class="layui-icon">&#xe631;</i><spring:message code='sb.shutdown' /></button>
                                </shiro:hasPermission>
                                <%--<shiro:hasPermission name="DEVICE_INFO_TIMINGBOOT">--%>
                            <%--<button class="layui-btn layui-btn-sm layui-btn-hui" data-type="timingBoot"><i class="layui-icon">&#xe620;</i><spring:message code='sb.timing.switch' /></button>--%>
                                <%--</shiro:hasPermission>--%>
                                <shiro:hasPermission name="DEVICE_INFO_DEVICEGROUP">
                             <button class="layui-btn layui-btn-sm layui-btn-hui" data-type="deviceGroup"><i class="layui-icon">&#xe63c;</i><spring:message code='sb.grouping' /></button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="DEVICE_INFO_UNREGISTER">
                             <button class="layui-btn layui-btn-sm layui-btn-hui" data-type="unRegistered"><i class="layui-icon">&#xe60b;</i><spring:message code='sb.unregistered' /></button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="DEVICE_INFO_REGISTER">
                             <button class="layui-btn layui-btn-sm layui-btn-hui" data-type="registered"><i class="layui-icon">&#xe62c;</i><spring:message code="main.registered" /></button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="DEVICE_INFO_VR_SERVER_UPGRADE">
                             <button class="layui-btn layui-btn-sm layui-btn-hui" data-type="vrServerUpgrade"><i class="layui-icon">&#xe628;</i><spring:message code='sb.visual.server.update' /></button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="DEVICE_INFO_VR_LIB_UPGRADE">
                             <button class="layui-btn layui-btn-sm layui-btn-hui" data-type="vrLibUpgrade"><i class="layui-icon">&#xe615;</i><spring:message code='sb.visual.library.update' /></button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="DEVICE_INFO_SEND_AD">
                             <button class="layui-btn layui-btn-sm layui-btn-hui" data-type="sendAdToDevice"><i class="layui-icon">&#xe609;</i><spring:message code='sb.push.ad' /></button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="DEVICE_INFO_BATCH_REALTIME_SWITCH">
                             <button class="layui-btn layui-btn-sm layui-btn-hui" data-type="sendRealTimeReplentshment"><i class="layui-icon">&#xe60e;</i><spring:message code='sb.real.time.shopping.cart' /></button>
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
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<script src="${staticSource}/resources/js/xlsx.core.min.js"></script>
<script src="${staticSource}/resources/js/tableExport.min.js"></script>
<script src="${staticSource}/resources/js/tableExportComm.js?2"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    var colModel =[
        {name: 'id', index: 'id', hidden: true},
        {name: 'scode', index: 'SCODE', label: '<spring:message code="main.device.id" />',width:120},
        {name: 'sname', index: 'SNAME', label: '<spring:message code="main.device.name" />',width:140},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode',label : '<spring:message code="main.merchant.number" />',index: 'smerchant_code',width:160,sortable : false},
        {name: 'merchantName',label : '<spring:message code="main.merchant.name" />',index: 'merchantName',width:200,sortable : false},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'smerchantCode', index: 'smerchant_code', hidden: true},
        </c:if>
        {name: 'address', label: "<spring:message code='sb.delivery.address' />",width:200,sortable : false},
        {name: 'sgroupName', index: 'SGROUP_NAME', label: "<spring:message code='sh.device.grouping' />",width:160,sortable : false},
        {name: 'istatus',index: 'ISTATUS',label: '<spring:message code="sb.equipment.status" />',
            formatter: function(value, options, rowObject) { return '<span id="istatus_'+rowObject.id+'" data="'+value+'" class="' + (({"10":"istatus-normal","20":"","30":"istatus-warm","40":"istatus-radius","50":"istatus-danger"})[value]) + '">' +  (({"10":'● <spring:message code="sb.unregistered" />',"20":'● <spring:message code="main.normal" />',"30":'● <spring:message code="sb.fault" />',"40":'● <spring:message code="sb.scrap" />',"50":'● <spring:message code="sb.offline" />'})[value])+ '</span>' },
            width:160
        },
        {name: 'ioperateStatus',index: 'IOPERATE_STATUS',label: "<spring:message code='sb.operation.state' />", formatter: "select",editoptions: {value: '10:<spring:message code="main.enable" />;20:<spring:message code="main.disable" />'},width:140},
        {name: 'istandardStockStatus', label: "<spring:message code='sb.standard.stock.status' />", formatter: "select", editoptions: {value: '10:<spring:message code='sh.enable' />;20:<spring:message code="main.close" />'}, width:180,sortable : false},
        {name: 'icooperationMode',index: 'ICOOPERATION_MODE',label: "<spring:message code='sb.cooperation.mode' />",formatter: "select",editoptions: {value: '10:<spring:message code='sh.purchase' />;20:<spring:message code='sb.rent' />;30:<spring:message code='sb.autonomy' />'},width: 160},
        {name: 'idockingType',index: 'IDOCKING_TYPE',label: "<spring:message code='sb.system.docking.type' />",formatter: "select",editoptions: {value: '10:<spring:message code='sb.independent.research.and.development' />;20:<spring:message code='sb.third.party' />'},width: 200},
        {name: 'iscreenDisplayType',index: 'ISCREEN_DISPLAY_TYPE',label: "<spring:message code='wz.screen.display.type' />",formatter: "select",editoptions: {value: '10:<spring:message code='tpinfo.horizontal.screen' />;20:<spring:message code='sb.vertical.screen' />'},width: 180},
        /*{
         name: 'icontainerType',
         index: 'ICONTAINER_TYPE',
         label: "<spring:message code='tpinfotoadd.container.type' />",
         formatter: "select",
         editoptions: {value: '10:<spring:message code="sb.single.door" />;20:<spring:message code="sb.double.door" />'},
         width:120         },*/

        /*{
         name: 'icompressorType',
         index: 'ICOMPRESSOR_TYPE',
         label: "<spring:message code='sb.compressor.type' />",
         formatter: "select",
         editoptions: {value: '10:<spring:message code="sb.refrigeration" />;20:<spring:message code="sb.heating" />;30:<spring:message code="sb.cooling.heat" />'},
         width:120
         },*/
        {
            name: 'taddTime', index: 'TADD_TIME', label: '<spring:message code="sp.brand.add.date" />', width: 160,formatter: function (value) {
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');}
        },
        {name: 'itype', index: 'itype', hidden: true},
        {name: "process",title:false, index: "process", label: '<spring:message code="main.operation" />', sortable: false, frozen: true, width: 300}
    ];
    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "scroll" });
            var data = $("#gridTable").jqGrid('getRowData');
            var merchantCode = "${SESSION_KEY_SMERCHANT_CODE}";
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = '';
                <shiro:hasPermission name="DEVICE_INFO_VIEW">
                    str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"viewMethod('"
                        + cl + "')\" title=\"<spring:message code='sh.view' />\" alt=\"<spring:message code='sh.view' />\" /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="DEVICE_INFO_EDIT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                        + cl + "')\" title=\"<spring:message code='main.edit' />\" alt=\"<spring:message code='main.edit' />\" /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="DEVICE_INFO_DELETE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                        + cl + "')\" title='<spring:message code="main.delete" />' alt='<spring:message code="main.delete" />'  /> | ";
                </shiro:hasPermission>
                if (data[i].ioperateStatus == 20) {
                    <shiro:hasPermission name="DEVICE_INFO_OPERATING_ENABLED">
                    str += "<img src='${staticSource}/resources/images/oper_icon/enabled.png' class=\"oper_icon\" onclick=\"deviceEnabledMethod('"
                        + cl + "')\" title='<spring:message code="main.enable" />' alt='<spring:message code="main.enable" />'  /> | ";
                    </shiro:hasPermission>
                }else if(data[i].ioperateStatus == 10){
                    <shiro:hasPermission name="DEVICE_INFO_OPERATING_DISABLE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/sb_disable.png' class=\"oper_icon\" onclick=\"deviceDisabledMethod('"
                        + cl + "')\" title='<spring:message code="sh.disable" />' alt='<spring:message code="sh.disable" />'  /> | ";
                    </shiro:hasPermission>
                }
                if ($("#istatus_"+cl).attr("data") == 20) {
                    <shiro:hasPermission name="DEVICE_INFO_STATUS">
                    str += "<img src='${staticSource}/resources/images/oper_icon/status.png' class=\"oper_icon\" onclick=\"offlineMethod('"
                        + cl + "')\" title='<spring:message code="sb.offline"/>' alt='<spring:message code="sb.offline" />'  /> | ";
                    </shiro:hasPermission>
                }
                if (merchantCode == data[i].smerchantCode) {
                    <shiro:hasPermission name="DEVICE_INFO_STANDARDSTOCK">
                    str += "<img src='${staticSource}/resources/images/oper_icon/stock.png' class=\"oper_icon\" onclick=\"standardStockMethod('"
                        + cl + "')\" title='<spring:message code="sm.label.stock" />' alt='<spring:message code="sm.label.stock" />'  /> | ";
                    </shiro:hasPermission>
                    if (data[i].istandardStockStatus == 10 && $("#istatus_"+cl).attr("data") == 20) {
                        <shiro:hasPermission name="DEVICE_INFO_DYNAMIC_REPLENISHMENT">
                        str += "<img src='${staticSource}/resources/images/oper_icon/replenishment.png' class=\"oper_icon\" onclick=\"generateReplenishment('"
                            + cl + "')\" title='<spring:message code="rm.dynamic.replenishment.order" />' alt='<spring:message code="rm.dynamic.replenishment.order" />'  /> | ";
                        </shiro:hasPermission>
                    }
                }
                if (data[i].idockingType != 20) {
                    <shiro:hasPermission name="DEVICE_INFO_QRCODE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/code.png' class=\"oper_icon\" onclick=\"qrcodeMethod('"
                        + cl + "')\" title='<spring:message code="sb.view.device.qr.code" />' alt='<spring:message code="sb.view.device.qr.code" />'  /> | ";
                    </shiro:hasPermission>
                }
                if (data[i].itype == 30 || data[i].itype == 40 || data[i].itype == 50 || data[i].itype == 60) {
                    <shiro:hasPermission name="DEVICE_NET_CAMERA_LIST">
                    str += "<img src='${staticSource}/resources/images/oper_icon/camera.png' class=\"oper_icon\" onclick=\"deviceCameraConfigMethod('"
                        + cl + "')\" title='<spring:message code="sb.camera.configuration" />' alt='<spring:message code="sb.camera.configuration" />'  /> | ";
                    </shiro:hasPermission>
                }
                str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"viewAdvMethod('"
                    + cl + "')\" title='<spring:message code="sb.view.device.ad.info"/>' alt='<spring:message code="sb.view.device.ad.info"/>'  /> | ";
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
        addTitle: '<spring:message code="sb.add.device.information" />',
        addFn:showLayerMedium
    };
    initBtnByForm4(initBtnConfig);

    layui.use('form', function () {
        var $ = layui.$, active = {
            volume: function () {//音量
                showLayerMedium("<spring:message code='sb.select.to.adjust.the.volume.device'/>", ctx + '/device/info/deviceAdjustVolume');
            },
            inventory: function () {//盘货
                var sid = $("#gridTable").jqGrid('getGridParam', 'selarrrow');
                if (isEmpty(sid)) {
                    alertDel("<spring:message code='sb.please.select.the.device.you.want.to.operate.first'/>");
                    return;
                }
                if(sid.length != 1) {
                    alertDel("<spring:message code='sb.can.only.select.one.device'/>");
                    return;
                }
                showLayerMedium("<spring:message code='sb.active.inventory'/>", ctx + '/device/info/inventory?deviceId=' + sid);
            },
            upgradeVoice: function () {//升级语音
                showLayerMedium('<spring:message code="sb.upgrade.device.select" />', ctx + '/device/info/deviceUpgradeVoice');
            },
            systemUpgrade: function () {//系统升级
                showLayerMedium('<spring:message code="sb.upgrade.device.select" />', ctx + '/device/info/deviceUpgradeAPK');
            },
            reboot: function () {//重启
                showLayerMedium("<spring:message code='sb.select.the.device.that.needs.to.be.restarted'/>", ctx + '/device/info/deviceReboot');
            },
            shutdown: function () {//关机
                showLayerMedium("<spring:message code='sb.select.the.device.that.needs.to.be.turned.off'/>", ctx + '/device/info/deviceShutdown');
            },
            // timingBoot: function () {//定时关机
            //     showLayerMedium("<spring:message code='sb.select.a.device.that.requires.a.timed.shutdown'/>", ctx + '/device/info/timingSwitchDevice');
            // },
            disabled: function () {//停用
                var sid = $("#gridTable").jqGrid('getGridParam', 'selarrrow');
                if (isEmpty(sid)) {
                    alertDel("<spring:message code='sb.please.choose.to.deactivate.the.device.first'/>");
                    return;
                }
                confirmOperTip("<spring:message code='sb.make.sure.you.want.to.disable.the.device'/>?", ctx + "/device/info/operate/disabled", {checkboxId: sid.join(","),method:"disabled"});
            },
            unRegistered : function () { //未注册
                showLayerOne("<spring:message code='sb.unregistered.connection.details'/>", ctx + "/device/info/unRegistered");
            },
            registered : function () { //已注册
                showLayerOne("<spring:message code='sb.registered.connection.details'/>", ctx + "/device/info/registered");
            },
            vrServerUpgrade : function () { // 视觉服务更新
                showLayerMedium("<spring:message code='sb.visual.service.update'/>", ctx + "/device/info/vrServerUpgrade");
            },
            vrLibUpgrade : function () { // 视觉库更新
                showLayerMedium("<spring:message code='sb.visual.library.update'/>", ctx + "/device/info/vrLibUpgrade");
            },
            sendAdToDevice : function () { // 视觉库更新
                showLayerMedium("<spring:message code='sb.push.ads.to.devices'/>", ctx + "/device/info/sendAdToDevice");
            },
            sendRealTimeReplentshment : function () { // 实时盘货开关
                showLayerMedium("<spring:message code='sb.set.real.time.inventory.switch'/>", ctx + "/device/info/sendRealTimeReplentshment");
            },
            deviceGroup:function () { //设备分组
                var sid = $("#gridTable").jqGrid('getGridParam', 'selarrrow');
                if (isEmpty(sid)) {
                    alertDel("<spring:message code='sb.please.select.the.device.to.be.grouped.first'/>");
                    return;
                } else {
                    $.ajax({
                        type:'post',
                        url:'${ctx}/device/info/checkDevice',
                        data:{pid:sid.join(",")},
                        dataType:'json',
                        success:function(msg){
                            if(msg.success){
                                showLayer('<spring:message code="sb.select.device.grouping" />', ctx + '/device/info/deviceAddToGroup?sid=' + sid, {area: ['60%', '70%']});
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
        showLayerMedium("<spring:message code='sb.device.details'/>", ctx + '/device/info/view?sid=' + sid);
    }
    function editMethod(sid) {
        showLayerMedium("<spring:message code='sb.edit.device.information'/>", ctx + '/device/info/toEdit?sid=' + sid);
    }
    function delMethod(sid) {
        confirmDelTip('<spring:message code="main.delete.confirm" />', ctx + "/device/info/delete", {checkboxId: sid});
    }
    function offlineMethod(sid) {
        confirmOperTip("<spring:message code='sb.make.sure.to.take.the.device.offline'/>?", ctx + "/device/info/offline", {checkboxId: sid});
    }
    function deviceEnabledMethod(sid) {
        confirmOperTip("<spring:message code='sb.make.sure.you.want.to.enable.the.device'/>?", ctx + "/device/info/operateingEanbled", {checkboxId: sid});
    }
    function deviceDisabledMethod(sid) {
        confirmOperTip("<spring:message code='sb.make.sure.you.want.to.deactivate.the.device'/>?", ctx + "/device/info/operateingDisabled", {checkboxId: sid});
    }
   function standardStockMethod(sid) {
       showLayerMax("<spring:message code='sb.equipment.standard.stock'/>", ctx + "/device/info/standardStock?sid=" + sid);
    }
    function qrcodeMethod(sid) {
        showLayerOne("<spring:message code='sb.device.qr.code'/>", ctx + "/device/info/qrCode?sid=" + sid, {area: ['30%', '75%']});
    }
    function deviceCameraConfigMethod(sid) {
        showLayerMax("<spring:message code='sb.configure.device.camera.information'/>", ctx + '/cameraConfig/list?sdeviceId=' + sid);
    }
    function generateReplenishment(sid) {
        showLayerMedium("<spring:message code='sb.dynamically.generate.replenishment.orders'/>", ctx + "/device/info/generateDynamicReplenishment?sid=" + sid);
    }
    function viewAdvMethod(sid) {
        showLayerMax("<spring:message code="wz.device.ad.mgr"/>", ctx + '/device/info/toAdvertis?sid=' + sid);
    }
</script>
</body>
</html>


