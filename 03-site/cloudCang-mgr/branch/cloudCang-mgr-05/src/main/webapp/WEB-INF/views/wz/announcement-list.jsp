<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title>系统公告区域管理</title>
    <link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
    <link href="${staticSource}/resources/css/override.css" rel="stylesheet">
    <link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>系统公告区域管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <input type="hidden" id="aid" name="aid" value="${aid}" />
                        <div class="layui-form-item">
                            <shiro:hasPermission name="ANNOUNCEMENT_ADD">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" style="margin-left: 0px;" data-type="add"><i class="layui-icon"></i><spring:message code="main.add" /></button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="ANNOUNCEMENT_DELETE">
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
     * 重新加载表格
     */
    function reloadData(aid) {
        if (!isEmpty(aid)) {
            $("#aid").val(aid);
        }
        resetReloadGrid();
    }
    /**
     * 选择日期范围
     */
    layui.use('laydate', function(){
        var laydate = layui.laydate;
        //执行一个laydate实例
        laydate.render({
            elem: '#tpublishDateStart' //指定元素
        });
        //执行一个laydate实例
        laydate.render({
            elem: '#tpublishDateEnd' //指定元素
        });
    });

    //渲染数据
    var colModel = [{name: 'id', index: 'id',hidden:true},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode',label : '<spring:message code="main.merchant.number" />',index: 'smerchant_code',width:180},
        {name: 'merchantName',label : '<spring:message code="main.merchant.name" />',index: 'MERCHANTNAME',width:360,sortable : false},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'smerchantCode',index: 'smerchant_code',hidden:true},
        </c:if>
        {name: 'stitle',index: 'stitle',label : '<spring:message code="wz.title" />',width:200,sortable : false},
        {name: 'istatus',index: 'istatus',label : '<spring:message code="main.state" />', editable: true,
            formatter:function (value, options, rowObject) {return '<span id="istatus_'+rowObject.id+'" data="'+value+'" class="'+ (({"10":"istatus-radius","20":"istatus-normal","30":"istatus-warm"})[value])+'"> '+(({"10":"● 待发布","20":"● 已发布","30":"● 已过期"})[value])+'</span>'},
            sortable : false,width:120},
        {name: 'scontentUrl',index: 'scontentUrl',label : '<spring:message code="wz.announcement.content" />',width:150,sortable : false},
        {name: 'isort',index: 'isort',label : '<spring:message code="main.sort" />',width:80,sortable : false},
        {name: 'tpublishDate',index: 'TPUBLISH_DATE',label : '<spring:message code="wz.release.datet" />',formatter:function(value){
            if (isEmpty(value)) {
                return '';
            }
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:210},
        {name: 'tvalidDate',index: 'TVALID_DATE',label : '<spring:message code="wz.deadline" />',formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:210},
        {name: 'sremark',index: 'sremark',label : '<spring:message code="main.remarks" />',width:110,sortable : false},
        {name : 'process',title:false,index : 'process',label : '<spring:message code="main.operation" />',sortable : false,width:200}
    ];
    // 表格回调
    function tableCallBack() {
        setTimeout(function () {
            var data = $("#gridTable").jqGrid('getRowData');
            var merchantCode = "${SESSION_KEY_SMERCHANT_CODE}";
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = "";
                if (merchantCode == data[i].smerchantCode) {
                    if ($('#istatus_'+cl).attr('data') == 10) {
                        <shiro:hasPermission name="ANNOUNCEMENT_PUBLISH">
                        str += "<img src='${staticSource}/resources/images/oper_icon/publish.png' class=\"oper_icon\" onclick=\"publishMethod('"
                            + cl + "',20)\" title='<spring:message code="wz.release" />' alt='<spring:message code="wz.release" />'  /> | ";
                        </shiro:hasPermission>
                    }
                    if ($('#istatus_'+cl).attr('data') == 10) {
                        <shiro:hasPermission name="ANNOUNCEMENT_EDIT">
                        str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                            + cl + "')\" title='<spring:message code="wz.editing.system.announcement" />' alt='<spring:message code="wz.editing.system.announcement" />'  /> | ";
                        </shiro:hasPermission>
                    }
                    <shiro:hasPermission name="ANNOUNCEMENT_DELETE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                        + cl + "')\" title='<spring:message code="wz.delete.system.announcement" />' alt='<spring:message code="wz.delete.system.announcement" />'  /> | ";
                    </shiro:hasPermission>
                    if ($('#istatus_'+cl).attr('data') != 30) {
                    <shiro:hasPermission name="ANNOUNCEMENT_EXPIRED">
                    str += "<img src='${staticSource}/resources/images/oper_icon/expired.png' class=\"oper_icon\" onclick=\"expireMethod('"
                        + cl + "',30)\" title='<spring:message code="wz.expired" />' alt='<spring:message code="wz.expired" />'  /> | ";
                    </shiro:hasPermission>
                    }
                    $("#gridTable").jqGrid('setRowData',
                        cl, {
                            process: str.substr(0, str.lastIndexOf(" | "))
                        });
                }
            }
        }, 0);
    }

    $(function () {
        var config = {
            sortname: "A.ISTATUS=10",
            sortorder: "desc"
        };
        initTable(ctx + "/announcement/getAnnounceMentByAid", colModel, tableCallBack, config);
    });
    //<spring:message code="wz.initialize.the.form.button" />
    var initBtnConfig = {
        addUrl: ctx + '/announcement/edit',
        deleteUrl: ctx + "/announcement/delete",
        addTitle: '<spring:message code="wz.add.system.announcement.information" />',
        addFn:addAnnouncement
    };
    initBtnByForm4(initBtnConfig);

    function addAnnouncement() {
        var aid = $("#aid").val();
        if (isEmpty(aid)) {
            alertDel('<spring:message code="wz.please.select.a.data.first" />！');
            return;
        }
        showLayerMin('<spring:message code="wz.add.system.announcement.information" />',ctx+'/announcement/edit?sregionId='+aid);
    }

    function publishMethod(sid,type) {
        var alertStr = '<spring:message code="wz.whether.to.confirm.the.release.system.announcement" />?';
        confirmOperTip(alertStr , ctx+"/announcement/publish",{checkboxId:sid, type:type});
    }

    function editMethod(aid) {
        showLayerMin('<spring:message code="wz.edit.system.announcement.information" />', 'edit?aid='+aid);
    }
    function delMethod(aid) {
        confirmDelTip('<spring:message code="main.delete.confirm" />',ctx+"/announcement/delete",{checkboxId:aid});
    }

    function expireMethod(sid,type) {
        operDealwith(ctx+"/announcement/expire",{checkboxId:sid, type:type});
    }

</script>
</body>
</html>

