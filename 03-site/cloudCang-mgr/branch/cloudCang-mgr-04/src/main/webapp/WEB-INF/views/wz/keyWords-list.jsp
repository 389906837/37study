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
                                <input type="text" name="spageNo" id="spageNo" value="" placeholder="页面编号..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="spageName" id="spageName" value="" placeholder="页面名称..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="spageUrl" id="spageUrl" value="" placeholder="页面Url..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="stitle" id="stitle" value="" placeholder="标题..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="skeyWord" id="skeyWord" value="" placeholder="关键字..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i class="layui-icon">&#xe615;</i>查询</button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i class="layui-icon">&#x1006;</i>清除</button>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <shiro:hasPermission name="KEYWORDS_ADD">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" style="margin-left: 0px;" data-type="add"><i class="layui-icon"></i>添加</button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="KEYWORDS_DELETE">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i>删除</button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="KEYWORDS_REFRESHCACHE">
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
    // 渲染数据
    var colModel = [{name: 'id', index: 'id',hidden:true},
        {name: 'spageNo',index: 'SPAGE_NO',label : "页面编号",width:150},
        {name: 'spageName',index: 'spageName',label : "页面名称",width:150,sortable : false},
        {name: 'spageUrl',index: 'spageUrl',label : "页面Url",width:160,sortable : false},
        {name: 'stitle',index: 'stitle',label : "标题",width:150,sortable : false},
        {name: 'skeyWord',index: 'skeyWord',label : "关键字",width:150,sortable : false},
        {name: 'taddTime',index: 'TADD_TIME',label : "添加时间",formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:200},
        {name : 'process',index : 'process',label : "操作",sortable : false,width:150}
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
                    + cl + "')\" title='编辑网站关键字' alt='编辑网站关键字'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="KEYWORDS_DELETE">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='删除网站关键字' alt='删除网站关键字'  /> | ";
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
        addTitle: '添加网站关键字信息',
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
        showLayerMedium("编辑网站关键字信息", 'edit?kid='+kid);
    }
    function delMethod(kid) {
        confirmDelTip("确定要删除数据?",ctx+"/keyWords/delete",{checkboxId:kid});
    }
</script>
</body>
</html>

