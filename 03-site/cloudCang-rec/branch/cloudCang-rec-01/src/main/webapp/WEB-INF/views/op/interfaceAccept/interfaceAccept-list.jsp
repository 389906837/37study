<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>接口业务受理信息管理</title>
<link href="${staticSource}/resources/layui/css/layui.css?1" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>接口业务受理信息管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder="业务受理编号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="suserCode" id="suserCode" value="" placeholder="用户编号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="userName" id="userName" value="" placeholder="用户名..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sappCode" id="sappCode" value="" placeholder="应用编号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="appName" id="appName" value="" placeholder="应用名称..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sinterfaceCode" id="sinterfaceCode" value=""
                                       placeholder="接口编号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="interfaceName" id="interfaceName" value=""
                                       placeholder="接口名称..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="stpSerialNumber" id="stpSerialNumber" value=""
                                       placeholder="第三方业务编号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" id="iisDealwith" name="iisDealwith" list="{0:否,1:是}"
                                             entire="true" entireName="业务是否受理"/>
                            </div>
                            <div class="layui-input-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i
                                        class="layui-icon">&#xe615;</i>查询
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i
                                        class="layui-icon">&#x1006;</i>清除
                                </button>
                            </div>
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
    layui.use('laydate', function () {
        var laydate = layui.laydate;

//执行一个laydate实例
        laydate.render({
            elem: '#toperateDateStr', //指定元素
            range: true,
            type: 'datetime'
        });
    });

    // 渲染数据
    var colModel = [{name: 'id', index: 'id', hidden: true},
        {name: 'scode', index: 'scode', label: "编号", width: 200},
        {name: 'suserCode', index: 'SUSER_CODE', label: "用户编号", width: 100},
        {name: 'userName', index: 'username', label: "用户名", width: 200},
        {name: 'sappCode', index: 'SAPP_CODE', label: "应用编号", width: 100},
        {name: 'appName', index: 'appName', label: "应用名", width: 200},
        {name: 'sinterfaceCode', index: 'SINTERFACE_CODE', label: "接口编号", width: 100},
        {name: 'interfaceName', index: 'interfaceName', label: "接口名", width: 200},
        {name: 'stpSerialNumber', index: 'STP_SERIAL_NUMBER', label: "第三方业务编号", width: 260},
        {
            name: 'srequestTime', index: 'SREQUEST_TIME', label: "请求时间", width: 150, formatter: function (value) {
            if (isEmpty(value)) {
                return "";
            }
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }
        },
        {
            name: 'iisDealwith', index: 'IIS_DEALWITH', label: "业务是否处理", width: 100, formatter: "select",
            editoptions: {value: '0:否;1:是'}
        },
        {
            name: 'iisNeedCallback', index: 'IIS_NEED_CALLBACK', label: "是否需要回调", width: 100, formatter: "select",
            editoptions: {value: '0:否;1:是'}
        },
        {
            name: 'iisCallback', index: 'IIS_CALLBACK', label: "是否回调", width: 100, formatter: "select",
            editoptions: {value: '0:否;1:是'}
        },
        {
            name: 'icallbackNum', index: 'ICALLBACK_NUM', label: "已回调次数", width: 100
        },
        {
            name: 'iisCallbackSuccess', index: 'IIS_CALLBACK_SUCCESS', label: "回调是否成功", width: 100, formatter: "select",
            editoptions: {value: '0:否;1:是'}
        },
        {
            name: 'trequestFinishTime',
            index: 'TREQUEST_FINISH_TIME',
            label: "请求完成时间",
            width: 200,
            formatter: function (value) {
                if (isEmpty(value)) {
                    return "";
                }
                return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
            }
        },
        {
            name: 'tcallbackSuccessTime',
            index: 'TCALLBACK_SUCCESS_TIME',
            label: "回调成功时间",

            width: 200,
            formatter: function (value) {
                if (isEmpty(value)) {
                    return "";
                }
                return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
            }
        },
        {
            name: 'iisRequestSuccess', index: 'IIS_REQUEST_SUCCESS', label: "请求是否成功", width: 100, formatter: "select",
            editoptions: {value: '0:否;1:是'}
        },
        {
            name: 'itollNum', index: 'ITOLL_NUM', label: "请求收费次数", width: 100
        },
        {
            name: 'iactualTollNum', index: 'IACTUAL_TOLL_NUM', label: "请求实收收费次数", width: 140
        },
        {
            name: 'soperIp', index: 'SOPER_IP', label: "操作IP", width: 100
        },
        {
            name: "process", index: "process", title: false, label: "操作", sortable: false, frozen: true, width: 100
        }
    ]
    // 表格回调
    function tableCallBack() {
        $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
        setTimeout(function () {
            var ids = $("#gridTable").jqGrid('getRowData');
            var merchantCode = "${SESSION_KEY_SMERCHANT_CODE}";
            for (var i = 0; i < ids.length; i++) {
                var cl = ids[i].id;
                var str = '';
                <shiro:hasPermission name="INTERFACE_ACCEPT_VIEW">
                str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"viewMethod('"
                    + cl + "')\" title='查看详情' alt='查看详情'  /> | ";
                </shiro:hasPermission>
                if (1 == ids[i].iisNeedCallback) {
                    <shiro:hasPermission name="INTERFACE_ACCEPT_ACTIVE_NOTIFY">
                    str += "<img src='${staticSource}/resources/images/rec/activeNotify.png' class=\"oper_icon\" onclick=\"activeNotifyMethod('"
                        + cl + "')\" title='主动通知' alt='主动通知' /> | ";
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
            sortname: "",
            sortorder: "",
            shrinkToFit: false,
            autoScroll: true
        };
        initTable(ctx + "/interfaceAccept/queryData", colModel, tableCallBack, config);
    });
    // 调用Button按钮方法(查询,清除)
    //初始化form中按钮
    var initBtnConfig = {
        addUrl: ctx + '/interfaceAccept/edit',
        deleteUrl: ctx + "/interfaceAccept/delete",
        addTitle: '添加平台接口业务受理信息',
        addFn: showLayerMedium
    };
    initBtnByForm4(initBtnConfig);
    function viewMethod(sid) {
        showLayerMedium("查看平台接口业务受理信息详情", ctx + '/interfaceAccept/view?sid=' + sid);
    }
    function activeNotifyMethod(sid) {
        confirmDelTip("确定要主动通知?", ctx + "/interfaceAccept/activeNotify", {checkboxId: sid});
    }
</script>
</body>
</html>

