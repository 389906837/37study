<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title>广告区域管理</title>
    <link href="${staticSource}/resources/layui/css/layui.css?201" rel="stylesheet">
    <link href="${staticSource}/resources/css/override.css" rel="stylesheet">
    <link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code="menu.advertising.area.management"></spring:message></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <input type="hidden" id="aid" name="aid" value="${aid}" />
                        <%--<div class="layui-form-item">--%>
                            <%--<div class="layui-input-inline">--%>
                                <%--<input type="text" name="stitle" id="stitle" value="" placeholder="标题..." class="layui-input">--%>
                            <%--</div>--%>
                            <%--<div class="layui-input-inline">--%>
                                <%--<cang:select type="list" name="istatus" id="istatus" entire="true" value=""--%>
                                             <%--list="{0:已过期,1:投放中,2:待投放,3:暂停}" entireName='springMessageCode=main.state'/>--%>
                            <%--</div>--%>
                            <%--<div class="layui-input-inline">--%>
                                <%--<cang:select type="list" name="ilinkType" id="ilinkType" entire="true" value=""--%>
                                             <%--list="{1:普通超链接,2:内链活动,3:内链项目,4:内链资讯}" entireName="链接类型"/>--%>
                            <%--</div>--%>
                            <%--<div class="layui-input-inline">--%>
                                <%--<cang:select type="list" name="iisDefault" id="iisDefault" entire="true" value=""--%>
                                             <%--list='{0:springMessageCode=main.no,1:springMessageCode=main.yes}' entireName="默认"/>--%>
                            <%--</div>--%>
                            <%--<div class="layui-inline">--%>
                                <%--<label class="layui-form-label" style="width: 100px"><spring:message code="main.end.date" />:</label>--%>
                                <%--<div class="layui-input-inline" style="width: 160px;">--%>
                                    <%--<input type="text" name="tendDateStart" id="tendDateStart" class="layui-input">--%>
                                <%--</div>--%>
                                <%--<div class="layui-form-mid">-</div>--%>
                                <%--<div class="layui-input-inline" style="width: 160px;">--%>
                                    <%--<input type="text" name="tendDateEnd" id="tendDateEnd" class="layui-input">--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="layui-inline">--%>
                                <%--<button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i class="layui-icon">&#xe615;</i><spring:message code="main.query" /></button>--%>
                                <%--<button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i class="layui-icon">&#x1006;</i><spring:message code="main.clear" /></button>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <div class="layui-form-item">
                            <%--<button class="layui-btn layui-btn" data-type="query"><i class="layui-icon">&#xe615;</i><spring:message code="main.query" /></button>--%>
                            <shiro:hasPermission name="ADVERTIS_ADD">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" style="margin-left: 0px;" data-type="add"><i class="layui-icon"></i><spring:message code="main.add" /></button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="ADVERTIS_DELETE">
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
        laydate.render({
            elem: '#tendDateStart' //指定元素
        });
        laydate.render({
            elem: '#tendDateEnd' //指定元素
        });
    });
    //渲染数据
    var colModel = [{name: 'id', index: 'id',hidden:true},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode',label : '<spring:message code="main.merchant.number" />',index: 'smerchant_code',width:180},
        {name: 'merchantName',label : '<spring:message code="main.merchant.name" />',index: 'MERCHANTNAME',width:340,sortable : false},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'smerchantCode',index: 'smerchant_code',hidden:true},
        </c:if>
        {name: 'stitle',index: 'stitle',label : "<spring:message code='wz.title' />",width:180,sortable : false},
        {name: 'istatus',index: 'istatus',label : '<spring:message code="main.state" />', editable: true,
            formatter:function (value) {return '<span class="'+ (({"0":"istatus-radius","1":"istatus-normal","2":"istatus-warm","3":"istatus-danger"})[value])+'"> '+(({"0":"● <spring:message code='sm.expired' />","1":"● <spring:message code='wz.in.delivery' />","2":"● <spring:message code='wz.pending' />","3":"● <spring:message code='wz.time.out' />"})[value])+'</span>'},
            sortable : false,width:120},
        {name: 'isort',index: 'isort',label : '<spring:message code="main.sort" />',width:100,sortable : false},
        {name: 'iadvType',index: 'iadvType',label : "<spring:message code='wz.advertisement.type' />", editable: true,
            formatter:"select",editoptions:{value:'10:<spring:message code='sh.image' />;20:<spring:message code='wz.audio' />;30:<spring:message code='wz.video' />'},sortable : false,width:100},
        {name: 'iscreenDisplayType',index: 'iscreenDisplayType',label : "<spring:message code='tpinfotoadd.screen.display.type' />", editable: true,
            formatter:"select",editoptions:{value:'10:<spring:message code='tpinfo.horizontal.screen' />;20:<spring:message code='tpinfo.vertical.screen' />'},sortable : false,width:170},
        {name: 'sremark',index: 'sremark',label : "<spring:message code='wz.description' />",width:200,sortable : false},
        {name: 'tstartDate',index: 'TSTART_DATE',label : '<spring:message code="main.start.date" />',formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:210},
        {name: 'tendDate',index: 'TEND_DATE',label : '<spring:message code="main.end.date" />',formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        },width:240},
        {name : 'process',title:false,index : 'process',label : '<spring:message code="main.operation" />',sortable : false,width:380}
    ];
    // 表格回调
    function tableCallBack() {
        setTimeout(function(){
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
            var ids = $("#gridTable").jqGrid('getDataIDs');
            for ( var i = 0; i < ids.length; i++) {
                var cl = ids[i];
                var str = "";
                <shiro:hasPermission name="ADVERTIS_EDIT">
                str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                    + cl + "')\" title='<spring:message code="wz.edit.ad.area" />' alt='<spring:message code="wz.edit.ad.area" />'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="ADVERTIS_DELETE">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='<spring:message code="wz.delete.ad.area" />' alt='<spring:message code="wz.delete.ad.area" />'  /> | ";
                </shiro:hasPermission>
                $("#gridTable").jqGrid('setRowData',
                ids[i], {
                    process : str.length > 0 ? str.substr(0,str.lastIndexOf(" | ")) : ""
                });
            }
        }, 0);
    }

    $(function () {
        var config = {
            sortname: "A.ISTATUS=2",
            sortorder: "desc"
        };
        initTable(ctx + "/advertis/getAdvertisByAid", colModel, tableCallBack, config);
    });

    //<spring:message code="wz.initialize.the.form.button" />
    var initBtnConfig = {
        addUrl: ctx + '/advertis/edit',
        deleteUrl: ctx + "/advertis/delete",
        addTitle: '<spring:message code="wz.add.ad.area.information" />',
        addFn:addTemplate
    };
    initBtnByForm4(initBtnConfig);

    function addTemplate() {
        var aid = $("#aid").val();
        if (isEmpty(aid)) {
            alertDel('<spring:message code="wz.please.select.a.data.first" />！');
            return;
        }
        showLayerMedium('<spring:message code="wz.add.ad.area.information" />',ctx+'/advertis/edit?sregionId='+aid);
    }

    function editMethod(aid) {
        showLayerMedium('<spring:message code="wz.edit.ad.area.information" />', ctx+"/advertis/edit?aid="+aid);
    }
    function delMethod(aid) {
        confirmDelTip('<spring:message code="main.delete.confirm" />',ctx+"/advertis/delete",{checkboxId:aid});
    }

</script>
</body>
</html>

