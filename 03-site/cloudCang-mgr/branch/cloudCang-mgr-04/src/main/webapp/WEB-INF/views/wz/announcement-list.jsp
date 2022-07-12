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
                                <button class="layui-btn layui-btn-sm layui-btn-warm" style="margin-left: 0px;" data-type="add"><i class="layui-icon"></i>添加</button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="ANNOUNCEMENT_DELETE">
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
        {name: 'smerchantCode',label : "商户编号",index: 'smerchant_code',width:180},
        {name: 'merchantName',label : "商户名称",index: 'MERCHANTNAME',width:360,sortable : false},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'smerchantCode',index: 'smerchant_code',hidden:true},
        </c:if>
        {name: 'stitle',index: 'stitle',label : "标题",width:200,sortable : false},
        {name: 'istatus',index: 'istatus',label : "状态", editable: true,
            formatter:function (value, options, rowObject) {return '<span id="istatus_'+rowObject.id+'" data="'+value+'" class="'+ (({"10":"istatus-radius","20":"istatus-normal","30":"istatus-warm"})[value])+'"> '+(({"10":"● 待发布","20":"● 已发布","30":"● 已过期"})[value])+'</span>'},
            sortable : false,width:120},
        {name: 'scontentUrl',index: 'scontentUrl',label : "公告内容",width:150,sortable : false},
        {name: 'isort',index: 'isort',label : "排序号",width:80,sortable : false},
        {name: 'tpublishDate',index: 'TPUBLISH_DATE',label : "发布日期",formatter:function(value){
            if (isEmpty(value)) {
                return '';
            }
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:210},
        {name: 'tvalidDate',index: 'TVALID_DATE',label : "截止日期",formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:210},
        {name: 'sremark',index: 'sremark',label : "备注",width:110,sortable : false},
        {name : 'process',title:false,index : 'process',label : "操作",sortable : false,width:200}
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
                            + cl + "',20)\" title='发布' alt='发布'  /> | ";
                        </shiro:hasPermission>
                    }
                    if ($('#istatus_'+cl).attr('data') == 10) {
                        <shiro:hasPermission name="ANNOUNCEMENT_EDIT">
                        str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                            + cl + "')\" title='编辑系统公告' alt='编辑系统公告'  /> | ";
                        </shiro:hasPermission>
                    }
                    <shiro:hasPermission name="ANNOUNCEMENT_DELETE">
                    str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                        + cl + "')\" title='删除系统公告' alt='删除系统公告'  /> | ";
                    </shiro:hasPermission>
                    if ($('#istatus_'+cl).attr('data') != 30) {
                    <shiro:hasPermission name="ANNOUNCEMENT_EXPIRED">
                    str += "<img src='${staticSource}/resources/images/oper_icon/expired.png' class=\"oper_icon\" onclick=\"expireMethod('"
                        + cl + "',30)\" title='已失效' alt='已失效'  /> | ";
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
    //初始化form中按钮
    var initBtnConfig = {
        addUrl: ctx + '/announcement/edit',
        deleteUrl: ctx + "/announcement/delete",
        addTitle: '添加系统公告信息',
        addFn:addAnnouncement
    };
    initBtnByForm4(initBtnConfig);

    function addAnnouncement() {
        var aid = $("#aid").val();
        if (isEmpty(aid)) {
            alertDel("请先选择一条数据!");
            return;
        }
        showLayerMin("添加系统公告信息",ctx+'/announcement/edit?sregionId='+aid);
    }

    function publishMethod(sid,type) {
        var alertStr = "是否确认发布系统公告?";
        confirmOperTip(alertStr , ctx+"/announcement/publish",{checkboxId:sid, type:type});
    }

    function editMethod(aid) {
        showLayerMin("编辑系统公告信息", 'edit?aid='+aid);
    }
    function delMethod(aid) {
        confirmDelTip("确定要删除数据?",ctx+"/announcement/delete",{checkboxId:aid});
    }

    function expireMethod(sid,type) {
        operDealwith(ctx+"/announcement/expire",{checkboxId:sid, type:type});
    }

</script>
</body>
</html>

