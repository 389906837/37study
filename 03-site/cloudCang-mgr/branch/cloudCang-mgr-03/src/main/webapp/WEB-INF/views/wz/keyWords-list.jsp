<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title>网页关键字</title>
    <link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
    <link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>网页关键字</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="spageNo" id="spageNo" value="" placeholder="<spring:message code='wz.page.number' />..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="spageName" id="spageName" value="" placeholder="<spring:message code='wz.page.name' />..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="spageUrl" id="spageUrl" value="" placeholder="<spring:message code='wz.page.url' />..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="stitle" id="stitle" value="" placeholder="<spring:message code='main.title.msg' />..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="skeyWord" id="skeyWord" value="" placeholder="<spring:message code='wz.keyword' />..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i class="layui-icon">&#xe615;</i><spring:message code="main.query" /></button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i class="layui-icon">&#x1006;</i><spring:message code="main.clear" /></button>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <shiro:hasPermission name="KEYWORDS_ADD">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" style="margin-left: 0px;" data-type="add"><i class="layui-icon"></i><spring:message code="main.add" /></button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="KEYWORDS_DELETE">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i><spring:message code="main.delete" /></button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="KEYWORDS_REFRESHCACHE">
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
    // 渲染数据
    var colModel = [{name: 'id', index: 'id',hidden:true},
        {name: 'spageNo',index: 'SPAGE_NO',label : "<spring:message code='wz.page.number' />",width:150},
        {name: 'spageName',index: 'spageName',label : "<spring:message code='wz.page.name' />",width:150,sortable : false},
        {name: 'spageUrl',index: 'spageUrl',label : "<spring:message code='wz.page.url' />",width:160,sortable : false},
        {name: 'stitle',index: 'stitle',label : "<spring:message code='main.title.msg' />",width:150,sortable : false},
        {name: 'skeyWord',index: 'skeyWord',label : "<spring:message code='wz.keyword' />",width:150,sortable : false},
        {name: 'taddTime',index: 'TADD_TIME',label : '<spring:message code="main.add.time" />',formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:200},
        {name : 'process',index : 'process',label : '<spring:message code="main.operation" />',sortable : false,width:150}
    ];
    // 表格回调
    function tableCallBack() {
        setTimeout(function () {
            var data = $("#gridTable").jqGrid('getRowData');

            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = "";
                <shiro:hasPermission name="KEYWORDS_EDIT">
                str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                    + cl + "')\" title='<spring:message code='wz.edit.website.keywords' />' alt='<spring:message code='wz.edit.website.keywords' />'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="KEYWORDS_DELETE">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='<spring:message code='wzcon.delete.site.keywords' />' alt='<spring:message code='wzcon.delete.site.keywords' />'  /> | ";
                </shiro:hasPermission>

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
        initTable(ctx + "/keyWords/queryData", colModel, tableCallBack, config);
    });
    //初始化form中按钮
    var initBtnConfig = {
        addUrl: ctx + '/keyWords/edit',
        deleteUrl: ctx + "/keyWords/delete",
        addTitle: '<spring:message code='wz.add.site.keyword.information' />',
        addFn:showLayerMedium
    };
    initBtnByForm4(initBtnConfig);
    layui.use('form', function(){
        var $ = layui.$, active = {
            refreshCache: function(){//刷新缓存
                var loadIndex = loading();
                $.ajax({
                    type : "POST",
                    dataType : "json",
                    url : ctx+'/zooSyn/cachePageKeys?t='+new Date(),
                    success : function(msg) {
                        closeLayer(loadIndex);//关闭加载框
                        if (msg.success) {
                            alertInfo(msg.data)
                        } else {
                            alertDel(msg.data);
                        }
                    }
                });
            },
        };
        $('.layui-form .layui-btn').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });
    function editMethod(kid) {
        showLayerMedium("<spring:message code='wz.edit.website.keyword.information' />", 'edit?kid='+kid);
    }
    function delMethod(kid) {
        confirmDelTip('<spring:message code="main.delete.confirm" />',ctx+"/keyWords/delete",{checkboxId:kid});
    }
</script>
</body>
</html>

