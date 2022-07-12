<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
<title>消息/协议模板</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code='menu.message.protocol.template' /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <input type="hidden" id="menuId" name="menuId" value="${menuId}" />
                        <div class="layui-form-item">
                            <shiro:hasPermission name="ADD_MSGTEMPLATE">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" style="margin-left: 0px;" data-type="add"><i class="layui-icon"></i><spring:message code="main.add" /></button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="DELETE_MSGTEMPLATE">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i><spring:message code="main.delete" /></button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="BETCHUPDATE_MSGTEMPLATE">
                                <button class="layui-btn layui-btn-sm" data-type="update"><i class="layui-icon">&#xe642;</i><spring:message code='xx.batch.modify.suppliers' />
</button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="REFRESHCACHE_MSGTEMPLATE">
                                <button class="layui-btn layui-btn-sm layui-btn-primary" data-type="refreshCache"><i class="layui-icon layui-icon-refresh"></i><spring:message code="main.refresh.cache" />
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

<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    /**
     * 重新加载表格
     */
    function reloadData(menuId) {
        if (!isEmpty(menuId)) {
            $("#menuId").val(menuId);
        }
        resetReloadGrid();
    }
    /**
     * 选择日期范围
     */
    layui.use('laydate', function(){
        var laydate = layui.laydate;
        laydate.render({
            elem: '#toperateStartDate' //指定元素
            ,type: 'datetime'
        });
        laydate.render({
            elem: '#toperateEndDate' //指定元素
            ,type: 'datetime'
        });
    });
    var colModel= [{name: 'id', index: 'id',hidden:true},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'shCode',label : '<spring:message code="main.merchant.number" />',index: 'SMERCHANT_CODE',width:200},
        {name: 'merchantName',label : '<spring:message code="main.merchant.name" />',index: 'MERCHANTNAME',width:260,sortable : false},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'SMERCHANT_CODE', index: 'SMERCHANT_CODE', hidden: true},
        </c:if>
        {name: 'stemplateTitle',index: 'STEMPLATE_TITLE',label : "<spring:message code='xx.template.title' />",sortable : false,width:260},
        {name: 'stemplateName',index: 'STEMPLATE_NAME',label : "<spring:message code='tpinfo.template.name' />",sortable : false,width:260},
        {name: 'iusage',index: 'IUSAGE',label : "<spring:message code='xx.use' />", editable: true,
            formatter:"select",editoptions:{value:'1:<spring:message code='main.verify.code' />;2:<spring:message code='xx.ordinary' />'},sortable : false,width:80},
        {name: 'ssupplierCode',index: 'SSUPPLIER_CODE',label : "<spring:message code='xx.supplier.code' />",sortable : false,width:150},
        {name: 'sname',index: 'SNAME',label : "<spring:message code='xx.supplier.name' />",sortable : false,width:150},
        {name: 'iisEnable',index: 'IIS_ENABLE',label : '<spring:message code="main.whether.enable" />', editable: true,
            formatter:"select",editoptions:{value:'0:<spring:message code="main.no" />;1:<spring:message code="main.yes" />'},sortable : false,width:180},
        {name: 'iisRealtime',index: 'IIS_REALTIME',label : "<spring:message code='xx.whether.to.send.in.real.time' />", editable: true,
            formatter:"select",editoptions:{value:'1:<spring:message code='xx.real.time' />;2:<spring:message code='xx.not.real.time' />'},sortable : false,width:250},
        {name: 'sstarttime',index: 'SSTARTTIME',label : "<spring:message code='xx.send.start.time' />",sortable : false,width:200},
        {name: 'sendtime',index: 'SENDTIME',label : "<spring:message code='xx.send.end.time' />",sortable : false,width:200},
        {name: 'saddOperator',index: 'SADD_OPERATOR',label : "<spring:message code='wz.add.a.person' />",sortable : false,width:180},
        {name: 'daddDate',index: 'DADD_DATE',label : '<spring:message code="main.add.time" />',formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:150},
        {name : 'process',title:false,index : 'process',label : '<spring:message code="main.operation" />',sortable : false,width:200}
    ];
    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
            var data = $("#gridTable").jqGrid('getRowData');

            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = "";
                <shiro:hasPermission name="EDIT_MSGTEMPLATE">
                str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                    + cl + "')\" title='<spring:message code='xx.edit.message.protocol.template' />' alt='<spring:message code='xx.edit.message.protocol.template' />'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="DELETE_MSGTEMPLATE">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='<spring:message code='xx.delete.a.message.protocol.template' />' alt='<spring:message code='xx.delete.a.message.protocol.template' />'  /> | ";
                </shiro:hasPermission>
                if (data[i].iisEnable == 0) {
                    <shiro:hasPermission name="ENABLED_MSGTEMPLATE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/enabled.png' class=\"oper_icon\" onclick=\"enableMethod('"
                        + cl + "',1)\" title='<spring:message code="main.enable" />' alt='<spring:message code="main.enable" />'  /> | ";
                    </shiro:hasPermission>
                }else{
                    <shiro:hasPermission name="ENABLED_MSGTEMPLATE">
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
    $(function () {
        var config = {
            sortname: "DADD_DATE",
            sortorder: "desc",
            shrinkToFit: false,
            autoScroll: true,
        };
        initTable(ctx + "/msgTemplate/getBySmainId", colModel, tableCallBack, config);
    });
    //初始化form中按钮
    var initBtnConfig = {
        addUrl: ctx + '/msgTemplate/edit',
        deleteUrl: ctx + "/msgTemplate/delete",
        updatecacheUrl: ctx+'/zooSyn/cacheMsgTemplate?t='+new Date(),
        addTitle: '<spring:message code='xx.add.message.protocol.template.information' />',
        addFn: addTemplate
    };
    initBtnByForm5(initBtnConfig);


    function addTemplate() {
        var menuId = $("#menuId").val();
        if (isEmpty(menuId)) {
            alertDel("<spring:message code='wz.please.select.a.data.first' />!");
            return;
        }
        showLayerMedium("<spring:message code='xx.add.message.protocol.template.information' />",ctx+'/msgTemplate/edit?smainId='+menuId);
    }

    layui.use('form', function(){
        var $ = layui.$, active = {
            update: function(){//批量修改
                var sid = $("#gridTable").jqGrid('getGridParam','selarrrow');
                if(isEmpty(sid)) {
                    alertDel("<spring:message code='wz.please.select.a.data.first' />");
                    return;
                }
                showLayerMedium("<spring:message code='xx.batch.modify.suppliers' />", ctx+"/supplierInfo/selectList?isBatchUpdate=1&sid="+sid);
            }
        };
        $('.layui-form .layui-btn').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });
    function editMethod(sid) {
        showLayerMedium("<spring:message code='xx.edit.message.protocol.template.information' />", 'edit?sid='+sid);
    }
    function delMethod(sid) {
        confirmDelTip('<spring:message code="main.delete.confirm" />',ctx+"/msgTemplate/delete",{checkboxId:sid});
    }

    function enableMethod(sid, type) {
        var alertStr = "<spring:message code='xx.confirm.the.sms.template.is.disabled' />?";
        if (type == 0) {
            alertStr = "<spring:message code='xx.confirm.the.sms.template.is.disabled' />?";
        }
        confirmOperTip(alertStr, ctx + "/msgTemplate/enable", {checkboxId: sid, type: type});
    }

    function selectSupp(suppId,suppCode,imsgType) {
        $("#ssupplierId").val(suppId);
        $("#imsgType").val(imsgType);
        $("#ssupplierCode").val(suppCode);
    }
</script>
</body>
</html>

