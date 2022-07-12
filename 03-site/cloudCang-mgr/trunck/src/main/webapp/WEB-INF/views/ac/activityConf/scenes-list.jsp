<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title>场景活动列表</title>
    <link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
    <link href="${staticSource}/resources/css/override.css" rel="stylesheet">
    <link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox">
                <div class="ibox-title">
                    <h5>场景活动</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="sconfCode" id="sconfCode" value="" placeholder="活动编号..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sconfName" id="sconfName" value="" placeholder="活动名称..." class="layui-input">
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
                                <cang:select type="list" list="{0:草稿,1:开启,-1:关闭}" id="iisAvailable" name="iisAvailable" entire="true" entireName="请选择活动状态" />
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="activeStartTimeStr" id="activeStartTimeStr" placeholder="活动开始时间" class="layui-input" />
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="activeEndTimeStr" id="activeEndTimeStr" placeholder="活动结束时间" class="layui-input" />
                            </div>
                            <div class="layui-input-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i class="layui-icon">&#xe615;</i>查询</button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i class="layui-icon">&#x1006;</i>清除</button>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <shiro:hasPermission name="ACT_SCENES_ADD">
                            <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"><i class="layui-icon"></i>添加</button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="ACT_SCENES_DELETE">
                            <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i>删除</button>
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
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    var colModel = [{name: 'id', index: 'id',hidden:true},
        {name: 'sconfCode',label : "活动编号",index: 'sconf_code'},
        {name: 'sconfName',index: 'sconf_name',label : "活动名称"},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode',label : "商户编号",index: 'smerchant_code'},
        {name: 'merchantName',label : "商户名称",index: 'sname',width:260},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'smerchantCode',index: 'smerchant_code',hidden:true},
        </c:if>
        {name: 'iisAvailable',index: 'iis_available',label : "活动状态"  ,
            formatter:function (value, options, rowObject) {return '<span id="iisAvailable_'+rowObject.id+'" data="'+value+'" class="'+ (({"1":"istatus-normal","0":"istatus-warm","-1":"istatus-danger"})[value])+'"> '+(({"1":"● 启用","0":"● 草稿","-1":"● 关闭"})[value])+'</span>'}
        },
        {name: 'scenesType',index: 'iscenes_type',label : "场景类型"},
        {name: 'irangeType',index: 'irange_type',label : "参与活动设备",sortable : false,formatter:function(value,rowObject){
            return "<a href='javascript:void(0);' onclick=\"viewDevice('"+rowObject.rowId+"')\">查看设备</a>";}},
        {name: 'tactiveStartTime',label : "活动开始时间",index: 'tactive_start_time',formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');}},
        {name: 'tactiveEndTime',label : "活动结束时间",index: 'tactive_end_time',formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');}},
        {name: 'ijoinNum',index: 'ijoin_num',label : "参与人数"},
        {name : "process", title:false,index : "process",label : "操作",sortable : false,frozen:true,width:350}
    ];
    function tableCallBack() {
        setTimeout(function(){
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "scroll" });
            var data = $("#gridTable").jqGrid('getRowData');
            var merchantCode = "${SESSION_KEY_SMERCHANT_CODE}";
            for ( var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = "";
                <shiro:hasPermission name="ACT_SCENES_VIEW">
                str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"viewMethod('"
                    + cl + "')\" title='活动详情' alt='活动详情'  /> | ";
                </shiro:hasPermission>
                if(merchantCode == data[i].smerchantCode) {
                    if ($("#iisAvailable_"+cl).attr("data") == 0) {
                        <shiro:hasPermission name="ACT_SCENES_ENABLED">
                            str += "<img src='${staticSource}/resources/images/oper_icon/open.png' class=\"oper_icon\" onclick=\"enabledMethod('"
                                + cl + "')\" title='活动开启' alt='活动开启'  /> | ";
                        </shiro:hasPermission>
                        <shiro:hasPermission name="ACT_SCENES_EDIT">
                            str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                                + cl + "')\" title='活动编辑' alt='活动编辑'  /> | ";
                        </shiro:hasPermission>
                    } else if ($("#iisAvailable_"+cl).attr("data") == 1) {
                        <shiro:hasPermission name="ACT_SCENES_CLOSED">
                            str += "<img src='${staticSource}/resources/images/oper_icon/close.png' class=\"oper_icon\" onclick=\"closedMethod('"
                                + cl + "')\" title='活动关闭' alt='活动关闭'  /> | ";
                        </shiro:hasPermission>
                    }
                    if ($("#iisAvailable_"+cl).attr("data") != 1) {
                        <shiro:hasPermission name="ACT_SCENES_DELETE">
                            str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                                + cl + "')\" title='活动删除' alt='活动删除'  /> | ";
                        </shiro:hasPermission>
                    }
                }
                <shiro:hasPermission name="ACT_SCENES_COUPON_RULE">
                str += "<img src='${staticSource}/resources/images/oper_icon/coupon_rule.png' class=\"oper_icon\" onclick=\"couponRule('"
                    + cl + "',"+(merchantCode == data[i].smerchantCode)+","+$("#iisAvailable_"+cl).attr("data")+")\" title='送券规则' alt='送券规则'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="ACT_SCENES_INTEGRAL_RULE">
                str += "<img src='${staticSource}/resources/images/oper_icon/integral_rule.png' class=\"oper_icon\" onclick=\"integralRule('"
                    + cl + "',"+(merchantCode == data[i].smerchantCode)+","+$("#iisAvailable_"+cl).attr("data")+")\" title='积分规则' alt='积分规则'  /> | ";
                </shiro:hasPermission>

                $("#gridTable").jqGrid('setRowData',
                cl, {
                    process : str.length > 0 ? str.substr(0, str.lastIndexOf(" | ")) : ""
                });
            }
        }, 0);
    }
    $(function () {
        var config = {
            sortname: "A.IIS_AVAILABLE=1",
            sortorder: "desc",
            shrinkToFit: false,
            autoScroll: true,
        };
        initTable(ctx+"/activityConf/queryScenesData",colModel,tableCallBack,config);
    });
    //初始化form中按钮
    var initBtnConfig = {
        addUrl: ctx+'/activityConf/editScenes',
        deleteUrl: ctx+"/activityConf/delete",
        addTitle: '添加场景活动信息',
        addFn:showLayerMedium
    };
    initBtnByForm4(initBtnConfig);
    layui.use('laydate', function(){
        var laydate = layui.laydate;
        laydate.render({
            elem: '#activeStartTimeStr',
            range:true,
            type: 'date'
        });
        laydate.render({
            elem: '#activeEndTimeStr',
            range:true,
            type: 'date'
        });
    });
    layui.use('form', function () {
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
    //促销活动详情
    function viewMethod(sid) {
        showLayerMin("查看场景活动详情", ctx+'/activityConf/scenesView?sid='+sid);
    }
    //查看活动设备
    function viewDevice(sid) {
        showLayerMedium("查看场景活动设备信息", ctx+'/activityConf/viewDevice?sid='+sid);
    }
    function editMethod(sid) {
        showLayerMedium("编辑场景活动信息", ctx+'/activityConf/editScenes?sid='+sid);
    }
    //删除数据
    function delMethod(sid) {
        confirmDelTip("确定要删除数据?",ctx+"/activityConf/delete",{checkboxId:sid});
    }
    //关闭活动
    function closedMethod(sid) {
        confirmOperTip("确定要关闭此活动？" , ctx+"/activityConf/closed",{sid:sid});
    }
    //启用活动
    function enabledMethod(sid) {
        confirmOperTip("确定要启用此活动？" , ctx+"/activityConf/enabled",{sid:sid});
    }
    //送券规则
    function couponRule(sid,flag,istatus) {
        var title = "编辑送券规则信息";
        if(!flag || istatus == 1) {
            title = "送券规则详情信息";
        }
        showLayerMedium(title, ctx+'/couponRule/edit?sid='+sid);
    }
    //积分规则
    function integralRule(sid,flag,istatus) {
        var title = "编辑积分规则信息";
        if(!flag || istatus == 1) {
            title = "积分规则详情信息";
        }
        showLayerMin(title, ctx+'/integralRule/edit?sid='+sid);
    }
</script>
</body>
</html>

