<%@ taglib prefix="srping" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
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
                    <h5><spring:message code="menu.advertising.area.management"/></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="sregionCode" id="sregionCode" value="" placeholder="<spring:message code='wz.operation.area.number'/>"
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sregionName" id="sregionName" value="" placeholder="<spring:message code='wz.operation.area.name'/>"
                                       class="layui-input">
                            </div>
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-input-inline">
                                    <input type="text" name="smerchantCode" id="smerchantCode" value=""
                                           placeholder="<spring:message code='sm.merchant.code'/>" class="layui-input">
                                </div>
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantName" id="merchantName" value=""
                                           placeholder="<spring:message code='sm.merchant.name'/>" class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-input-inline">
                                <input type="text" name="stitle" id="stitle" value="" placeholder="<spring:message code='sl.title'/>"
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" list="{0:springMessageCode=sm.expired,1:springMessageCode=wz.in.delivery,2:springMessageCode=wz.pending,3:springMessageCode=wz.time.out}" id="istatus"
                                             name="istatus" entire="true" entireName="springMessageCode=main.state.select"/>
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" list="{10:springMessageCode=sh.image,20:springMessageCode=wz.video,30:springMessageCode=wz.audio}" id="iadvType"
                                             name="iadvType" entire="true" entireName="springMessageCode=wz.please.select.an.ad.type"/>
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" list="{10:springMessageCode=sb.horizontal.screen,20:springMessageCode=sb.vertical.screen}" id="iscreenDisplayType"
                                             name="iscreenDisplayType" entire="true" entireName="springMessageCode=sb.please.select.the.screen.display.type"/>
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" list="{0:springMessageCode=main.no,1:springMessageCode=main.yes}" id="iuseType"
                                             name="iuseType" entire="true" entireName="springMessageCode=wz.ad.is.inCommonUse"/>
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i
                                        class="layui-icon">&#xe615;</i><spring:message code="main.query"/>
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i
                                        class="layui-icon">&#x1006;</i><spring:message code="main.clear"/>
                                </button>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <shiro:hasPermission name="ADVERTIS_ADD">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" style="margin-left: 0px;"
                                        data-type="add"><i class="layui-icon"></i><spring:message code="tpinfo.add.to"/>
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="ADVERTIS_DELETE">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i><spring:message code="tpinfo.delete"/>
                                </button>
                            </shiro:hasPermission>
                            <button class="layui-btn layui-btn-sm layui-btn-hui" data-type="advertisDeviceBind">
                                <i class="layui-icon">&#xe60b;</i><spring:message code="wz.device.ad.mgr"/>
                            </button>

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
    layui.use(['form', 'laydate'], function () {
        var laydate = layui.laydate;
        laydate.render({
            elem: '#tendDateStart' //指定元素
        });
        laydate.render({
            elem: '#tendDateEnd' //指定元素
        });
        var $ = layui.$, active = {
            advertisDeviceBind: function () {//设备广告绑定
                var sid = $("#gridTable").jqGrid('getGridParam', 'selarrrow');
                if (isEmpty(sid)) {
                    alertDel("<spring:message code='wz.bound.information.empty'/>");
                    return;
                }
                showLayerMax("<spring:message code='wz.device.bound.ad'/>", ctx + "/advertis/toBinding?checkboxId=" + sid.join(","));
            }
        };
        $('.layui-form .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });
    //渲染数据
    var colModel = [{name: 'id', index: 'id', hidden: true},
        {name: 'sregionCode', label: "<spring:message code='wz.operation.area.number'/>", index: 'sregion_code', width: 185, sortable: false},
        {name: 'sregionName', label: "<spring:message code='wz.operation.area.name'/>", index: 'sregionName', width: 320, sortable: false},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode', label: "<spring:message code='sm.merchant.code'/>", index: 'smerchant_code', width: 140},
        {name: 'merchantName', label: "<spring:message code='sm.merchant.name'/>", index: 'MERCHANTNAME', width: 300, sortable: false},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'smerchantCode', index: 'smerchant_code', hidden: true},
        </c:if>
        {name: 'stitle', index: 'stitle', label: "<spring:message code='wz.title'/>", width: 100, sortable: false},
        {
            name: 'istatus', index: 'istatus', label: "<spring:message code='main.state'/>", editable: true,
            formatter: function (value) {
                return '<span class="' + (({
                        "0": "istatus-radius",
                        "1": "istatus-normal",
                        "2": "istatus-warm",
                        "3": "istatus-danger"
                    })[value]) + '"> ' + (({"0": "● <spring:message code='sm.expired'/>", "1": "● <spring:message code='wz.in.delivery'/>", "2": "● <spring:message code='wz.pending'/>", "3": "● <spring:message code='wz.time.out'/>"})[value]) + '</span>'
            },
            sortable: false, width:110
        },
        {name: 'isort', index: 'isort', label: "<spring:message code='sys.business.sort'/>", width: 80, sortable: false},
        {
            name: 'iadvType', index: 'iadvType', label: "<spring:message code='wz.advertisement.type'/>", editable: true,
            formatter: "select", editoptions: {value: '10:<spring:message code='sh.image' />;20:<spring:message code='wz.video' />;30:<spring:message code='wz.audio' />'}, sortable: false, width: 90
        },
        {
            name: 'iuseType', index: 'iuseType', label: "<spring:message code='wz.ad.is.inCommonUse'/>", editable: true,
            formatter: "select", editoptions: {value: '0:<spring:message code='main.no' />;1:<spring:message code='main.yes' />'}, sortable: false, width: 300
        },
        {
            name: 'iscreenDisplayType', index: 'iscreenDisplayType', label: "<spring:message code='tpinfotoadd.screen.display.type'/>", editable: true,
            formatter: "select", editoptions: {value: '10:<spring:message code='sb.horizontal.screen' />;20:<spring:message code='tpinfo.vertical.screen' />'}, sortable: false, width: 170
        },
        {
            name: 'tstartDate', index: 'TSTART_DATE', label: "<spring:message code='main.start.date'/>", formatter: function (value) {
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }, width: 160
        },
        {
            name: 'tendDate', index: 'TEND_DATE', label: "<spring:message code='main.end.date'/>", formatter: function (value) {
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }, width: 160
        },
        {name: 'process', title: false, index: 'process', label: "<spring:message code='main.operation'/>", sortable: false, width: 130}
    ];
    // 表格回调
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
            var ids = $("#gridTable").jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
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
                        process: str.length > 0 ? str.substr(0, str.lastIndexOf(" | ")) : ""
                    });
            }
        }, 0);
    }

    $(function () {
        var config = {
            sortname: "A.ISTATUS=2",
            sortorder: "desc"
        };
        initTable(ctx + "/advertis/queryData", colModel, tableCallBack, config);
    });

    //初始化form中按钮
    var initBtnConfig = {
        addUrl: ctx + '/advertis/edit',
        deleteUrl: ctx + "/advertis/delete",
        addTitle: '<spring:message code="wz.add.ad.area.information" />',
        addFn: showLayerMedium
    };
    initBtnByForm4(initBtnConfig);

    function editMethod(aid) {
        showLayerMedium("<spring:message code="wz.edit.ad.area.information" />", ctx + "/advertis/edit?aid=" + aid);
    }
    function delMethod(aid) {
        confirmDelTip("<spring:message code="sp.brand.are.you.sure.you.want.to.delete.all.data" />?", ctx + "/advertis/delete", {checkboxId: aid});
    }

</script>
</body>
</html>

