<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备搬迁记录</title>
<link href="${staticSource}/resources/layui/css/layui.css?1" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
<style>
</style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>设备搬迁记录</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm" style="margin-bottom:13px;">
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <input type="text" name="sdeviceCode" id="sdeviceCode" value="" placeholder="设备编号..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sbName" id="sbName" value="" placeholder="设备名称..." class="layui-input">
                            </div>
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-inline">
                                    <input type="text" name="smerchantCode" id="smerchantCode" value="" placeholder="商户编号..." class="layui-input">
                                </div>
                                <div class="layui-inline">
                                    <input type="text" name="merchantName" id="merchantName" value="" placeholder="商户名称..." class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-inline">
                                <input type="text" name="sdeviceOldAddress" id="SDEVICE_OLD_ADDRESS" value="" placeholder="设备原地址..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sdeviceNewAddress" id="SDEVICE_NEW_ADDRESS" value="" placeholder="设备新地址..." class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <select class="form-control"  name="istatus" id="istatus" >
                                    <option value="">请选择搬迁状态</option>
                                    <option value="10">待审核</option>
                                    <option value="20">审核通过</option>
                                    <option value="30">审核拒绝</option>
                                    <option value="40">已完成</option>
                                    <option value="50">已取消</option>
                                </select>
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="tplanMoveTimeStr" id="tplanMoveTimeStr" placeholder="计划搬迁时间" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="tmoveTimeStr" id="tmoveTimeStr" placeholder="搬迁时间" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="tauditTimeStr" id="tauditTimeStr" placeholder="审核时间" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="tapplyTimeStr" id="tapplyTimeStr" placeholder="申请时间" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sprincipal" id="sprincipal" value="" placeholder="搬迁负责人..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query" style="float:left">
                                    <i class="layui-icon">&#xe615;</i>查询
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset" style="margin-bottom:0">
                                    <i class="layui-icon">&#x1006;</i>清除
                                </button>
                            </div>
                        </div>
                        <div class="layui-form-item" style="margin-bottom: 10px;">
                            <shiro:hasPermission name="DEVICE_MOVE_ADD">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"
                                        style="float:left"><i class="layui-icon"></i>添加
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="DEVICE_MOVE_DELETE">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i>删除
                                </button>
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
    /**
     * 选择日期范围
     */
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //执行一个laydate实例(计划搬迁时间)
        laydate.render({
            elem: '#tplanMoveTimeStr', //指定元素
            range:true,
            type:'date'
        });
        //搬迁时间
        laydate.render({
            elem: '#tmoveTimeStr', //指定元素
            range:true,
            type:'date'
        });
        // 审核时间
        laydate.render({
            elem: '#tauditTimeStr', //指定元素
            range:true,
            type:'date'
        });
        // 申请时间
        laydate.render({
            elem: '#tapplyTimeStr', //指定元素
            range:true,
            type:'date'
        });
    });


    // 渲染数据
    var colModel =[
        {name: 'id', index: 'id', hidden: true},
        {name: 'sdeviceCode', index: 'SDEVICE_CODE', label: "设备编号",width:100,sortable : false},
        {name: 'sbName', index: 'sbName', label: "设备名称",width:130,sortable : false},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode',label : "商户编号",index: 'smerchant_code',width:190,sortable : false},
        {name: 'merchantName',label : "商户名称",index: 'merchantName',width:330,sortable : false},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'smerchantCode', index: 'smerchant_code', hidden: true},
        </c:if>
        {name: 'sdeviceOldAddress', index: 'SDEVICE_OLD_ADDRESS', label: "设备原地址",width:180,sortable : false},
        {name: 'sdeviceNewAddress', index: 'SDEVICE_NEW_ADDRESS', label: "设备新地址",width:280,sortable : false},
        {name: 'istatus',index: 'ISTATUS',label: "审核状态",formatter: "select", editoptions: {value: '10:待审核;20:审核通过;30:审核拒绝;40:已完成;50:已取消'},width:100},
        {
            name: 'tplanMoveTime', index: 'TPLAN_MOVE_TIME', label: "计划搬迁日期", width: 240,formatter: function (value) {
            if (isEmpty(value)) {
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd');},sortable : false
        },
        {
            name: 'tmoveTime', index: 'TMOVE_TIME', label: "搬迁日期", width: 240,formatter: function (value) {
            if (isEmpty(value)) {
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd');}
        },
        {name: 'sprincipal',label : "搬迁负责人",index: 'SPRINCIPAL',width:150,sortable : false},
        {
            name: 'tauditTime', index: 'TAUDIT_TIME', label: "审核日期", width: 240,formatter: function (value) {
            if (isEmpty(value)) {
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd');},sortable : false
        },
        {name: 'sauditUser',label : "审核人",index: 'sauditUser',width:150,sortable : false},
        {
            name: 'tapplyTime', index: 'TAPPLY_TIME', label: "申请日期", width: 240,formatter: function (value) {
            if (isEmpty(value)) {
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd');},sortable : false
        },
        {name: "process",title:false, index: "process", label: "操作", sortable: false, frozen: true, width: 150}
    ];
    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
            var data = $("#gridTable").jqGrid('getRowData');
            var merchantCode = "${SESSION_KEY_SMERCHANT_CODE}";
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = '';
                if (data[i].istatus == 10 || data[i].istatus == 30 ){
                <shiro:hasPermission name="DEVICE_MOVE_EDIT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                        + cl + "')\" title='编辑' alt='编辑'  /> | ";
                </shiro:hasPermission>
                }
                if (data[i].istatus == 10 || data[i].istatus == 30 ) {
                    <shiro:hasPermission name="DEVICE_REVIEW_ISTATUS">
                    str += "<img src='${staticSource}/resources/images/oper_icon/review.png' class=\"oper_icon\" onclick=\"deviceMoveState('"
                        + cl + "')\" title='审核' alt='审核'  /> | ";
                    </shiro:hasPermission>
                }
                if (data[i].istatus == 20) {
                <shiro:hasPermission name="DEVICET_COMPLETED">
                str += "<img src='${staticSource}/resources/images/oper_icon/completed.png' class=\"oper_icon\" onclick=\"comMethod('"
                    + cl + "')\" title='已完成' alt='已完成'  /> | ";
                </shiro:hasPermission>
                }
                if (data[i].istatus == 20) {
                <shiro:hasPermission name="DEVICET_CLOSE">
                str += "<img src='${staticSource}/resources/images/oper_icon/close.png' class=\"oper_icon\" onclick=\"closeMethod('"
                    + cl + "',50)\" title='已取消' alt='已取消'  /> | ";
                </shiro:hasPermission>
                }
                if (data[i].istatus == 10 || data[i].istatus == 30 || data[i].istatus == 40 || data[i].istatus == 50){
                <shiro:hasPermission name="DEVICE_MOVE_DELETE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                        + cl + "')\" title='删除' alt='删除'  /> | ";
                </shiro:hasPermission>
                }
                $("#gridTable").jqGrid('setRowData',
                    cl, {
                        process: str.substr(0, str.lastIndexOf(" | "))
                    });
            }
        }, 0);
    }

    $(function () {
        var config = {
            sortname: "ISTATUS=10",
            sortorder: "desc",
            shrinkToFit: false,
            autoScroll: true,
        };
        initTable(ctx+"/device/queryData",colModel,tableCallBack,config);
    });
    var initBtnConfig = {
        addUrl: ctx+'/device/edit',
        deleteUrl: ctx+"/device/delete",
        addTitle: '添加设备搬迁信息',
        addFn:showLayerMedium
    };
    initBtnByForm4(initBtnConfig);

    function editMethod(sid) {
        showLayerMedium("编辑设备信息", ctx + '/device/edit?sid=' + sid);
    }
    function delMethod(sid) {
        confirmDelTip("确定要删除数据?", ctx + "/device/delete", {checkboxId: sid});
    }

    function deviceMoveState(sid) {
        showLayerMin("审核", 'deviceMoveState?sid='+sid);
    }
    function comMethod(sid) {
        showLayerMin("搬迁状态", 'deviceMoveCom?sid='+sid);
    }

//    function comMethod(sid,type) {
//        operDealwith(ctx+"/device/deviceComplete",{checkboxId:sid, type:type});
//    }
    function closeMethod(sid,type) {
        confirmDelTip("确定要已取消?",ctx+"/device/deviceClose",{checkboxId:sid, type:type});
    }
</script>
</body>
</html>


