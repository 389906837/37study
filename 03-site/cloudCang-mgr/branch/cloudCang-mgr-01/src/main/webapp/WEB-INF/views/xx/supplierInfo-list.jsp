<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title>短信供应商管理</title>
    <link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
    <link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>短信供应商管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-inline">
                                    <input type="text" name="smerchantCode" id="smerchantCode" value="" placeholder="商户编号..." class="layui-input">
                                </div>
                                <div class="layui-inline">
                                    <input type="text" name="merchantName" id="merchantName" value="" placeholder="商户名称..." class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder="供应商编号..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sname" id="sname" value="" placeholder="供应商名称..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <select class="form-control" name="itype" id="itype">
                                    <option value="">请选择供应商类型</option>
                                    <option value="1">短信</option>
                                    <option value="2">邮箱</option>
                                </select>
                            </div>
                            <div class="layui-inline">
                                <select class="form-control" name="iintention" id="iintention">
                                    <option value="">请选择供渠道作用</option>
                                    <option value="1">营销</option>
                                    <option value="2">非营销</option>
                                </select>
                            </div>
                            <div class="layui-inline">
                                <select class="form-control" name="iisDisable" id="iisDisable">
                                    <option value="">请选择是否启用</option>
                                    <option value="0">启用</option>
                                    <option value="1">禁用</option>
                                </select>
                            </div>
                            <div class="layui-inline" style="width: 180px">
                                <input type="text" name="taddTimeStr" id="taddTimeStr" placeholder="添加时间" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i class="layui-icon">&#xe615;</i>查询</button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i class="layui-icon">&#x1006;</i>清除</button>
                            </div>
                        </div>
                        <div class="layui-form-item">
                        <shiro:hasPermission name="ADD_SUPPLIERINFO">
                            <button class="layui-btn layui-btn-sm layui-btn-warm" style="margin-left: 0px;" data-type="add"><i class="layui-icon"></i>添加</button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="REFRESHCACHE_SUPPLIERINFO">
                            <button class="layui-btn layui-btn-sm layui-btn-primary" data-type="refreshCache"><i class="layui-icon">&#x1006;</i>更新缓存</button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="DELETE_SUPPLIERINFO">
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
        {name: 'smerchantCode',label : "商户编号",index: 'SMERCHANT_CODE',width:150},
        {name: 'merchantName',label : "商户名称",index: 'MERCHANTNAME',width:260,sortable : false},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'SMERCHANT_CODE', index: 'SMERCHANT_CODE', hidden: true},
        </c:if>
        {name: 'scode',index: 'SCODE',label : "供应商编号",width:150},
        {name: 'sname',index: 'sname',label : "供应商名称",sortable : false,width:150},
        {name: 'itype',index: 'itype',label : "供应商类型", editable: true,
            formatter:"select",editoptions:{value:'1:短信;2:邮箱'},sortable : false,width:90},
        {name: 'iintention',index: 'iintention',label : "渠道作用", editable: true,
            formatter:"select",editoptions:{value:'1:营销;2:非营销'},sortable : false,width:90},
        {name: 'iisDisable',index: 'iisDisable',label : "是否启用", editable: true,
            formatter:"select",editoptions:{value:'0:否;1:是'},width:90},
        {name: 'sextInfo',index: 'sextInfo',label : "供应商扩展信息",sortable : false,width:260},
        {name: 'sdesc',index: 'sdesc',label : "供应商描述",sortable : false,width:150},
        {name: 'taddTime',index: 'TADD_TIME',label : "添加时间",formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:150},
        {name : 'process',title:false,index : 'process',label : "操作",sortable : false,width:150}
    ];
    // 表格回调
    function tableCallBack() {
        setTimeout(function () {
            var data = $("#gridTable").jqGrid('getRowData');

            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = "";
                <shiro:hasPermission name="EDIT_SUPPLIERINFO">
                str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                    + cl + "')\" title='编辑短信供应商' alt='编辑短信供应商'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="DELETE_SUPPLIERINFO">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='删除短信供应商' alt='删除短信供应商'  /> | ";
                </shiro:hasPermission>
                if (data[i].iisDisable == 0) {
                <shiro:hasPermission name="ENABLED_SUPPLIERINFO">
                str += "<img src='${staticSource}/resources/images/oper_icon/enabled.png' class=\"oper_icon\" onclick=\"enableMethod('"
                    + cl + "',1)\" title='启用' alt='启用'  /> | ";
                </shiro:hasPermission>
                }else{
                <shiro:hasPermission name="ENABLED_SUPPLIERINFO">
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
        addTitle: '添加短信供应商信息',
        addFn:showLayerMin
    };
    initBtnByForm5(initBtnConfig);

    function editMethod(sid) {
        showLayerMin("编辑短信供应商信息", 'edit?sid='+sid);
    }

    function delMethod(sid) {
        confirmDelTip("确定要删除数据?",ctx+"/supplierInfo/delete",{checkboxId:sid});
    }

    function enableMethod(sid, type) {
        var alertStr = "是否启用该供应商?";
        if (type == 0) {
            alertStr = "是否禁用该供应商?";
        }
        confirmOperTip(alertStr, ctx + "/supplierInfo/enable", {checkboxId: sid, type: type});
    }

</script>
</body>
</html>

