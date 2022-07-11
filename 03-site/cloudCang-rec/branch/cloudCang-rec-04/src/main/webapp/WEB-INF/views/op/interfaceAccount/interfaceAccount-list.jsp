<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>接口账户记录</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>接口账户记录</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder="编号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="userName" id="userName" value="" placeholder="用户名称..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="interfaceName" id="interfaceName" value=""
                                       placeholder="接口名称..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="interfaceAction" id="interfaceAction" value=""
                                       placeholder="接口动作..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="appId" id="appId" value=""
                                       placeholder="AppId..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list"
                                             list="{10:按次计费,20:按单位次数计费 （识别图片张数）,30:按时间计费,40:通用时间按次收费,50:通用时间按单位次数计费}"
                                             id="iaccountType"
                                             name="iaccountType" entire="true" entireName="请选择账户类型"/>
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list"
                                             list="{10:固定日期,20:长期有效}"
                                             id="ivalidityType"
                                             name="ivalidityType" entire="true" entireName="请选择有效期类型"/>
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list"
                                             list="{10:已开通,20:已关闭}"
                                             id="istatus"
                                             name="istatus" entire="true" entireName="请选择状态"/>
                            </div>
                            <div class="layui-inline">
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
        {name: 'scode', index: 'scode', label: "编号", width: 100},
        {name: 'suserCode', index: 'SUSER_CODE', label: "用户编号", width: 100},
        {name: 'userName', index: 'userName', label: "用户名", width: 50, sortable: false},
        {name: 'interfaceName', index: 'interfaceName', label: "接口名", width: 100, sortable: false},
        {name: 'interfaceAction', index: 'interfaceAction', label: "接口动作", width: 150, sortable: false},
        {name: 'appId', index: 'appId', label: "AppId", width: 100, sortable: false},
        {
            name: 'iisUnlimitedNumber', index: 'IACCOUNT_TYPE', label: "是否限制次数", width: 80, formatter: "select",
            editoptions: {value: '0:不限次数;1:限制次数'},
            hidden: true
        },
        {name: 'fbalance', index: 'FBALANCE', label: "余额次数", width: 50},
        {name: 'ifreezeNum', index: 'IFREEZE_NUM', label: "冻结次数", width: 50},
        {name: 'ideductionNum', index: 'IDEDUCTION_NUM', label: "扣费调用次数", width: 50},
        {name: 'itransferNum', index: 'ITRANSFER_NUM', label: "调用次数", width: 50},
        {
            name: 'iaccountType', index: 'IACCOUNT_TYPE', label: "账户类型", width: 80, formatter: "select",
            editoptions: {value: '10:按次计费;20:按单位次数计费 （识别图片张数）;30:按时间计费;40:通用时间按次收费;50:通用时间按单位次数计费'}
        },
        {
            name: 'ivalidityType', index: 'IVALIDITY_TYPE', label: "有效期类型", width: 70, formatter: "select",
            editoptions: {value: '10:固定日期;20:长期有效'}
        },
        {
            name: 'istatus', index: 'ISTATUS', label: "状态", width: 50, formatter: "select",
            editoptions: {value: '10:已开通;20:已关闭'}
        },
        {name: "process", index: "process", title: false, label: "操作", sortable: false, frozen: true, width: 80}
    ]
    // 表格回调
    function tableCallBack() {
        setTimeout(function () {
            var ids = $("#gridTable").jqGrid('getRowData');
            var merchantCode = "${SESSION_KEY_SMERCHANT_CODE}";
            for (var i = 0; i < ids.length; i++) {
                var cl = ids[i].id;
                var str = '';
                if (10 == ids[i].istatus) {
                    <shiro:hasPermission name="INTERFACE_ACCOUNT_CLOSE">
                    str += "<img src='${staticSource}/resources/images/rec/closeAccount.png' class=\"oper_icon\" onclick=\"closeMethod('"
                        + cl + "',20)\" title='关闭账户' alt='关闭账户' /> | ";
                    </shiro:hasPermission>
                    if (30 != ids[i].iaccountType || (30 == ids[i].iaccountType && 10 == ids[i].ivalidityType)) {
                        <shiro:hasPermission name="INTERFACE_ACCOUNT_RECHARGE">
                        str += "<img src='${staticSource}/resources/images/rec/rechargeAccount.png' class=\"oper_icon\" onclick=\"rechargeMethod('"
                            + cl + "')\" title='账户充值' alt='账户充值' /> | ";
                        </shiro:hasPermission>
                    }
                    if (0 == ids[i].iisUnlimitedNumber) {

                        <shiro:hasPermission name="INTERFACE_ACCOUNT_UNLIMITED_NUMBER">
                        str += "<img src='${staticSource}/resources/images/rec/rechargeAccount.png' class=\"oper_icon\" onclick=\"unlimitedNumberMethod('"
                            + cl + "',1)\" title='限制使用次数' alt='限制使用次数' /> | ";
                        </shiro:hasPermission>
                    } else if (1 == ids[i].iisUnlimitedNumber) {
                        <shiro:hasPermission name="INTERFACE_ACCOUNT_UNLIMITED_NUMBER">
                        str += "<img src='${staticSource}/resources/images/rec/rechargeAccount.png' class=\"oper_icon\" onclick=\"unlimitedNumberMethod('"
                            + cl + "',0)\" title='不限制使用次数' alt='不限制使用次数' /> | ";
                        </shiro:hasPermission>
                    }
                }
                if (20 == ids[i].istatus) {
                    <shiro:hasPermission name="INTERFACE_ACCOUNT_CLOSE">
                    str += "<img src='${staticSource}/resources/images/rec/closeAccount.png' class=\"oper_icon\" onclick=\"closeMethod('"
                        + cl + "',10)\" title='开通账户' alt='开通账户' /> | ";
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
            sortorder: ""
        };
        initTable(ctx + "/interfaceAccount/queryData", colModel, tableCallBack, config);
    });
    // 调用Button按钮方法(查询,清除)
    //初始化form中按钮
    var initBtnConfig = {
        addUrl: ctx + '/interfaceAccount/edit',
        deleteUrl: ctx + "/interfaceAccount/delete",
        addTitle: '添加平台接口业务受理信息',
        addFn: showLayerMedium
    };
    initBtnByForm4(initBtnConfig);

    function closeMethod(sid, type) {
        var alertStr = "确定要开通接口账户?";
        if (type == 20) {
            alertStr = "确定要关闭接口账户?";
        }
        confirmDelTip(alertStr, ctx + "/interfaceAccount/close", {checkboxId: sid, type: type});
    }
    function rechargeMethod(sid) {
        showLayerMedium("接口账户充值", ctx + '/interfaceAccount/recharge?sid=' + sid);
    }
    function unlimitedNumberMethod(sid, type) {
        var alertStr = "确定不限制账户使用次数?";
        if (type == 1) {
            alertStr = "确定限制账户使用次数?";
        }
        confirmOperTip(alertStr, ctx + "/interfaceAccount/restrictionNum", {checkboxId: sid, type: type});
    }
</script>
</body>
</html>

