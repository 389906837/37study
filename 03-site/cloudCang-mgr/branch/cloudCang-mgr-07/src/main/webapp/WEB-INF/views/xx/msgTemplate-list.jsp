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
                    <h5>消息/协议模板</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <input type="hidden" id="menuId" name="menuId" value="${menuId}" />
                        <div class="layui-form-item">
                            <shiro:hasPermission name="ADD_MSGTEMPLATE">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" style="margin-left: 0px;" data-type="add"><i class="layui-icon"></i>添加</button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="DELETE_MSGTEMPLATE">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i>删除</button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="BETCHUPDATE_MSGTEMPLATE">
                                <button class="layui-btn layui-btn-sm" data-type="update"><i class="layui-icon">&#xe642;</i>批量修改供应商</button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="REFRESHCACHE_MSGTEMPLATE">
                                <button class="layui-btn layui-btn-sm layui-btn-primary" data-type="refreshCache"><i class="layui-icon layui-icon-refresh"></i>更新缓存
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
        {name: 'shCode',label : "商户编号",index: 'SMERCHANT_CODE',width:160},
        {name: 'merchantName',label : "商户名称",index: 'MERCHANTNAME',width:260,sortable : false},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'SMERCHANT_CODE', index: 'SMERCHANT_CODE', hidden: true},
        </c:if>
        {name: 'stemplateTitle',index: 'STEMPLATE_TITLE',label : "模板标题",sortable : false,width:260},
        {name: 'stemplateName',index: 'STEMPLATE_NAME',label : "模板名称",sortable : false,width:260},
        {name: 'iusage',index: 'IUSAGE',label : "用途", editable: true,
            formatter:"select",editoptions:{value:'1:验证码;2:普通'},sortable : false,width:80},
        {name: 'ssupplierCode',index: 'SSUPPLIER_CODE',label : "供应商编号",sortable : false,width:150},
        {name: 'sname',index: 'SNAME',label : "供应商名称",sortable : false,width:150},
        {name: 'iisEnable',index: 'IIS_ENABLE',label : "是否启用", editable: true,
            formatter:"select",editoptions:{value:'0:否;1:是'},sortable : false,width:90},
        {name: 'iisRealtime',index: 'IIS_REALTIME',label : "是否实时发送", editable: true,
            formatter:"select",editoptions:{value:'1:实时;2:非实时'},sortable : false,width:120},
        {name: 'sstarttime',index: 'SSTARTTIME',label : "发送开始时间",sortable : false,width:120},
        {name: 'sendtime',index: 'SENDTIME',label : "发送结束时间",sortable : false,width:120},
        {name: 'saddOperator',index: 'SADD_OPERATOR',label : "添加人",sortable : false,width:100},
        {name: 'daddDate',index: 'DADD_DATE',label : "添加时间",formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:150},
        {name : 'process',title:false,index : 'process',label : "操作",sortable : false,width:160}
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
                    + cl + "')\" title='编辑消息协议模板' alt='编辑消息协议模板'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="DELETE_MSGTEMPLATE">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='删除消息协议模板' alt='删除消息协议模板'  /> | ";
                </shiro:hasPermission>
                if (data[i].iisEnable == 0) {
                    <shiro:hasPermission name="ENABLED_MSGTEMPLATE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/enabled.png' class=\"oper_icon\" onclick=\"enableMethod('"
                        + cl + "',1)\" title='启用' alt='启用'  /> | ";
                    </shiro:hasPermission>
                }else{
                    <shiro:hasPermission name="ENABLED_MSGTEMPLATE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/sb_disable.png' class=\"oper_icon\" onclick=\"enableMethod('"
                        + cl + "',0)\" title='禁用' alt='禁用'  /> | ";
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
        addTitle: '添加消息协议模板信息',
        addFn: addTemplate
    };
    initBtnByForm5(initBtnConfig);


    function addTemplate() {
        var menuId = $("#menuId").val();
        if (isEmpty(menuId)) {
            alertDel("请先选择一条数据!");
            return;
        }
        showLayerMedium("添加消息协议模板信息",ctx+'/msgTemplate/edit?smainId='+menuId);
    }

    layui.use('form', function(){
        var $ = layui.$, active = {
            update: function(){//批量修改
                var sid = $("#gridTable").jqGrid('getGridParam','selarrrow');
                if(isEmpty(sid)) {
                    alertDel("请先选择一条数据");
                    return;
                }
                showLayerMedium("批量修改供应商", ctx+"/supplierInfo/selectList?isBatchUpdate=1&sid="+sid);
            }
        };
        $('.layui-form .layui-btn').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });
    function editMethod(sid) {
        showLayerMedium("编辑消息协议模板信息", 'edit?sid='+sid);
    }
    function delMethod(sid) {
        confirmDelTip("确定要删除数据?",ctx+"/msgTemplate/delete",{checkboxId:sid});
    }

    function enableMethod(sid, type) {
        var alertStr = "确认将该短信模板启用吗?";
        if (type == 0) {
            alertStr = "确认将该短信模板禁用吗?";
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

