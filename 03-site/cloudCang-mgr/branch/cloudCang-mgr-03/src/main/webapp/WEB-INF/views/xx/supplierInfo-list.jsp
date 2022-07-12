<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title><spring:message code='xx.sms.provider.management' /></title>
    <link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
    <link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
<style>
    .ui-jqgrid tr.jqgrow td {
        white-space: normal !important;
        height: auto;
    }
</style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code='xx.sms.provider.management' /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-inline">
                                    <input type="text" name="smerchantCode" id="smerchantCode" value="" placeholder='<spring:message code="main.merchant.number" />...' class="layui-input">
                                </div>
                                <div class="layui-inline">
                                    <input type="text" name="merchantName" id="merchantName" value="" placeholder='<spring:message code="main.merchant.name" />...' class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder="<spring:message code='xx.supplier.code' />..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sname" id="sname" value="" placeholder="<spring:message code='xx.supplier.name' />..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <select class="form-control" name="itype" id="itype">
                                    <option value=""><spring:message code='xx.please.select.a.supplier.type' /></option>
                                    <option value="1"><spring:message code='xx.sms' /></option>
                                    <option value="2"><spring:message code="main.mailbox" /></option>
                                </select>
                            </div>
                            <div class="layui-inline">
                                <select class="form-control" name="iintention" id="iintention">
                                    <option value=""><spring:message code='xx.please.choose.for.channel.function' /></option>
                                    <option value="1"><spring:message code='xx.marketing' /></option>
                                    <option value="2"><spring:message code='xx.non.marketing' /></option>
                                </select>
                            </div>
                            <div class="layui-inline">
                                <select class="form-control" name="iisDisable" id="iisDisable">
                                    <option value=""><spring:message code='xx.please.choose.whether.to.enable' /></option>
                                    <option value="0"><spring:message code="main.enable" /></option>
                                    <option value="1"><spring:message code='sh.prohibit' /></option>
                                </select>
                            </div>
                            <div class="layui-inline" style="width: 180px">
                                <input type="text" name="taddTimeStr" id="taddTimeStr" placeholder='<spring:message code="main.add.time" />' class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i class="layui-icon">&#xe615;</i><spring:message code="main.query" /></button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i class="layui-icon">&#x1006;</i><spring:message code="main.clear" /></button>
                            </div>
                        </div>
                        <div class="layui-form-item">
                        <shiro:hasPermission name="ADD_SUPPLIERINFO">
                            <button class="layui-btn layui-btn-sm layui-btn-warm" style="margin-left: 0px;" data-type="add"><i class="layui-icon"></i><spring:message code="main.add" /></button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="REFRESHCACHE_SUPPLIERINFO">
                            <button class="layui-btn layui-btn-sm layui-btn-primary" data-type="refreshCache"><i class="layui-icon layui-icon-refresh"></i><spring:message code="main.refresh.cache" />
                            </button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="DELETE_SUPPLIERINFO">
                            <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i><spring:message code="main.delete" /></button>
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
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    /**
     * 选择日期范围
     */
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //执行一个laydate实例(添加时间)
        laydate.render({
            elem: '#taddTimeStr', //指定元素
            range:true,
            type: 'date'          //指定时间类型
        });
    });

    // 渲染数据
    var colModel = [{name: 'id', index: 'id',hidden:true},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode',label : '<spring:message code="main.merchant.number" />',index: 'SMERCHANT_CODE',width:180},
        {name: 'merchantName',label : '<spring:message code="main.merchant.name" />',index: 'MERCHANTNAME',width:320,sortable : false},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'SMERCHANT_CODE', index: 'SMERCHANT_CODE', hidden: true},
        </c:if>
        {name: 'scode',index: 'SCODE',label : "<spring:message code='xx.supplier.code' />",width:140},
        {name: 'sname',index: 'sname',label : "<spring:message code='xx.supplier.name' />",sortable : false,width:140},
        {name: 'itype',index: 'itype',label : "<spring:message code='xx.supplier.type' />", editable: true,
            formatter:"select",editoptions:{value:'1:<spring:message code='xx.sms' />;2:<spring:message code="main.mailbox" />'},sortable : false,width:140},
        {name: 'iintention',index: 'iintention',label : "<spring:message code='xx.channel.role' />", editable: true,
            formatter:"select",editoptions:{value:'1:<spring:message code='xx.marketing' />;2:<spring:message code='xx.non.marketing' />'},sortable : false,width:140},
        {name: 'iisDisable',index: 'iisDisable',label : '<spring:message code="main.whether.enable" />', editable: true,
            formatter:"select",editoptions:{value:'0:<spring:message code="main.no" />;1:<spring:message code="main.yes" />'},width:140},
        {name: 'sextInfo',index: 'sextInfo',label : "<spring:message code='xx.vendor.extension.information' />",sortable : false,width:320},
        {name: 'sdesc',index: 'sdesc',label : "<spring:message code='xx.supplier.description' />",sortable : false,width:200},
        {name: 'taddTime',index: 'TADD_TIME',label : '<spring:message code="main.add.time" />',formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:150},
        {name : 'process',title:false,index : 'process',label : '<spring:message code="main.operation" />',sortable : false,width:180}
    ];
    // 表格回调
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "scroll" });
            var data = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = "";
                <shiro:hasPermission name="EDIT_SUPPLIERINFO">
                str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                    + cl + "')\" title='<spring:message code='xx.edit.sms.provider' />' alt='<spring:message code='xx.edit.sms.provider' />'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="DELETE_SUPPLIERINFO">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='<spring:message code='xxcon.delete.sms.provider' />' alt='<spring:message code='xxcon.delete.sms.provider' />'  /> | ";
                </shiro:hasPermission>
                if (data[i].iisDisable == 0) {
                <shiro:hasPermission name="ENABLED_SUPPLIERINFO">
                str += "<img src='${staticSource}/resources/images/oper_icon/enabled.png' class=\"oper_icon\" onclick=\"enableMethod('"
                    + cl + "',1)\" title='<spring:message code="main.enable" />' alt='<spring:message code="main.enable" />'  /> | ";
                </shiro:hasPermission>
                }else{
                <shiro:hasPermission name="ENABLED_SUPPLIERINFO">
                    str += "<img src='${staticSource}/resources/images/oper_icon/sb_disable.png' class=\"oper_icon\" onclick=\"enableMethod('"
                        + cl + "',0)\" title='<spring:message code='main.disable' />' alt='<spring:message code='main.disable' />'  /> | ";
                </shiro:hasPermission>
                }

                $("#gridTable").jqGrid('setRowData',
                    cl, {
                        process: str.substr(0, str.lastIndexOf(" | "))
                    });
            }
        }, 0);
    }
    // 初始化表格,传入页面所需参数
    $(function () {
        var config = {
            sortname: "TADD_TIME",
            sortorder: "desc"
        };
        initTable(ctx + "/supplierInfo/queryData", colModel, tableCallBack, config);
    });
    //初始化form中按钮
    var initBtnConfig = {
        addUrl: ctx + '/supplierInfo/edit',
        deleteUrl: ctx + "/supplierInfo/delete",
        updatecacheUrl: ctx+'/zooSyn/cacheSupplier?t='+new Date(),
        addTitle: '<spring:message code='xx.add.sms.provider.information' />',
        addFn:showLayerMin
    };
    initBtnByForm5(initBtnConfig);

    function editMethod(sid) {
        showLayerMin("<spring:message code='xx.edit.sms.provider.information' />", 'edit?sid='+sid);
    }

    function delMethod(sid) {
        confirmDelTip('<spring:message code="main.delete.confirm" />',ctx+"/supplierInfo/delete",{checkboxId:sid});
    }

    function enableMethod(sid, type) {
        var alertStr = "<spring:message code='xx.whether.to.enable.the.supplier' />?";
        if (type == 0) {
            alertStr = "<spring:message code='xx.whether.to.disable.the.supplier' />?";
        }
        confirmOperTip(alertStr, ctx + "/supplierInfo/enable", {checkboxId: sid, type: type});
    }

</script>
</body>
</html>

