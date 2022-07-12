<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>会员用户列表</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>会员用户列表</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-inline">
                                    <input type="text" name="smerchantCode" id="smerchantCode" value=""
                                           placeholder="商户编号..." class="layui-input">
                                </div>
                                <div class="layui-inline">
                                    <input type="text" name="merchantName" id="merchantName" value=""
                                           placeholder="商户名称..." class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder="会员编号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="smemberName" id="smemberName" value="" placeholder="会员用户名..."
                                       class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <select class="form-control" name="isex" id="isex">
                                    <option value="">性别</option>
                                    <option value="1">男</option>
                                    <option value="0">女</option>
                                </select>
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <select class="form-control" name="istatus" id="istatus">
                                    <option value="">会员状态</option>
                                    <option value="1">正常</option>
                                    <option value="2">注销停用</option>
                                    <option value="3">系统黑名单</option>
                                    <option value="4">冻结</option>
                                </select>
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <cang:select type="list" id="iregClientType" name="iregClientType" groupNo="SP000120"
                                             list="{10:微信支付,20:支付宝,30:微信公众号,40:支付宝生活号,50:APP android,60:APP ios }"
                                             entire="true" entireName="注册平台"/>
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <select class="form-control" name="iisOrder" id="iisOrder">
                                    <option value="">是否首单</option>
                                    <option value="0">否</option>
                                    <option value="1">是</option>
                                </select>
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <select class="form-control" name="iisReplenishment" id="iisReplenishment">
                                    <option value="">是否补货员</option>
                                    <option value="0">否</option>
                                    <option value="1">是</option>
                                </select>
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <select class="form-control" name="iaipayOpen" id="iaipayOpen">
                                    <option value="">支付宝免密是否开通</option>
                                    <option value="0">否</option>
                                    <option value="1">是</option>
                                </select>
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <select class="form-control" name="iwechatOpen" id="iwechatOpen">
                                    <option value="">微信免密是否开通</option>
                                    <option value="0">否</option>
                                    <option value="1">是</option>
                                </select>
                            </div>
                            <%--<div class="layui-inline" style="width: 182px">--%>
                            <%--<select class="form-control" name="iisEnableRecoaward" id="iisEnableRecoaward">--%>
                            <%--<option value="">是否推荐启用</option>--%>
                            <%--<option value="0">否</option>--%>
                            <%--<option value="1">是</option>--%>
                            <%--</select>--%>
                            <%--</div>--%>
                            <div class="layui-inline" style="width: 182px">
                                <input type="text" name="sregDeviceCode" id="sregDeviceCode" value=""
                                       placeholder="注册设备编号..." class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <input type="text" name="tregisterTimeStr" id="tregisterTimeStr" placeholder="注册时间"
                                       class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn layui-btn" id="searchBtn" data-type="query"><i
                                        class="layui-icon">&#xe615;</i>查询
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i
                                        class="layui-icon">&#x1006;</i>清除
                                </button>
                            </div>
                        </div>
                        <%--<div class="layui-form-item">--%>
                        <%--<shiro:hasPermission name="ADD_MEMBERINFO">--%>
                        <%--<button class="layui-btn layui-btn-sm layui-btn-warm" style="margin-left: 0px;" data-type="add"><i class="layui-icon"></i>添加</button>--%>
                        <%--</shiro:hasPermission>--%>
                        <%--<shiro:hasPermission name="MEMBERINFO_DELETE">--%>
                        <%--<button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i>删除</button>--%>
                        <%--</shiro:hasPermission>--%>
                        <%--</div>--%>
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
<script src="${staticSource}/resources/js/tableExportComm.js"></script>
<script src="${staticSource}/resources/js/xlsx.core.min.js"></script>
<script src="${staticSource}/resources/js/tableExport.min.js"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    /**
     * 选择生日范围
     */
    layui.use('laydate', function () {
        var laydate = layui.laydate;

        //执行一个laydate实例
        laydate.render({
            elem: '#tregisterTimeStr', //指定元素
            range: true,
            type: 'date'
        });
    });
    // 渲染数据
    var colModel = [{name: 'id', index: 'id', hidden: true},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode', label: "商户编号", index: 'SMERCHANT_CODE', sortable: false},
        {name: 'merchantName', label: "商户名称", index: 'MERCHANTNAME', sortable: false, width: 260},
        </c:if>
        {name: 'scode', index: 'SCODE', label: "会员编号", sortable: false},
        {name: 'smemberName', index: 'SMEMBER_NAME', label: "会员用户名", sortable: false},
        {name: 'snickName', index: 'SNICKNAME', label: "会员昵称", sortable: false},
        {
            name: 'isex', index: 'ISEX', label: "性别", editable: true,
            formatter: "select", editoptions: {value: '1:男;0:女'}, sortable: false, width: 90
        },
        {
            name: 'istatus', index: 'ISTATUS', label: "会员状态", editable: true,
            formatter: "select", editoptions: {value: '1:正常;2:注销停用;3:系统黑名单;4:冻结'}, sortable: false, width: 120
        },
        {
            name: 'iregClientType',
            index: 'IREG_CLIENT_TYPE',
            label: "注册平台",
            editable: true,
            formatter: "select",
            editoptions: {value: '10:微信支付;20:支付宝;30:微信公众号;40:支付宝生活号;50:APP android;60:APP ios '},
            sortable: false, width: 120
        },
        {
            name: 'iisOrder', index: 'IIS_ORDER', label: "是否已首单", editable: true,
            formatter: "select", editoptions: {value: '0:否;1:是'}, sortable: false, width: 100
        },
        {
            name: 'iisReplenishment', index: 'IIS_REPLENISHMENT', label: "是否补货员", editable: true,
            formatter: "select", editoptions: {value: '0:否;1:是'}, sortable: false, width: 100
        },
        {
            name: 'iaipayOpen', index: 'IAIPAY_OPEN', label: "支付宝免密是否开通", editable: true,
            formatter: "select", editoptions: {value: '0:否;1:是'}, sortable: false, width: 180
        },
        {
            name: 'iwechatOpen', index: 'IWECHAT_OPEN', label: "微信支付免密是否开通", editable: true,
            formatter: "select", editoptions: {value: '0:否;1:是'}, sortable: false, width: 180
        },
        {
            name: 'iwechatPointOpen', index: 'IWECHAT_POINT_OPEN', label: "微信支付分是否开通", editable: true,
            formatter: "select", editoptions: {value: '0:否;1:是'}, sortable: false, width: 180
        },
//        {
//            name: 'iisEnableRecoaward', index: 'IIS_ENABLE_RECOAWARD', label: "推荐是否启用", editable: true,
//            formatter: "select", editoptions: {value: '0:否;1:是'}, sortable: false, width: 100
//        },
        {name: 'sregDeviceCode', index: 'SREG_DEVICE_CODE', label: "注册设备编号", sortable: false, width: 140},
        {
            name: 'tregisterTime', index: 'TREGISTER_TIME', label: "注册时间", formatter: function (value) {
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }
        },
        {name: 'process', title: false, index: 'process', label: "操作", sortable: false, width: 320}
    ];
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
            var data = $("#gridTable").jqGrid('getRowData');

            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = "";
                <shiro:hasPermission name="MEMBERINFO_QUERYAll">
                str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"queryAllMethod('"
                    + cl + "')\" title='会员详情' alt='会员详情'  /> | ";
                </shiro:hasPermission>
                <%--<shiro:hasPermission name="MEMBERINFO_EDIT">--%>
                <%--str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"--%>
                <%--+ cl + "')\" title='编辑会员' alt='编辑会员'  /> | ";--%>
                <%--</shiro:hasPermission>--%>
                <shiro:hasPermission name="MERCHANT_DELETE">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='删除会员' alt='删除会员'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="MEMBER_ALIPAY_MAKEUP">
                str += "<img src='${staticSource}/resources/images/oper_icon/alipay_makeup.png' class=\"oper_icon\" onclick=\"rechargeAgainMethod('"
                    + cl + "',0)\" title='支付宝免密支付补处理' alt='支付宝免密支付补处理'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="MEMBER_WECHANT_MAKEUP">
                str += "<img src='${staticSource}/resources/images/oper_icon/wechant_makeup.png' class=\"oper_icon\" onclick=\"rechargeAgainMethod('"
                    + cl + "',1)\" title='微信免密支付补处理' alt='微信免密支付补处理'  /> | ";
                </shiro:hasPermission>
                str += "<img src='${staticSource}/resources/images/oper_icon/wechant_makeup.png' class=\"oper_icon\" onclick=\"rechargeAgainMethod('"
                    + cl + "',2)\" title='微信支付分支付补处理' alt='微信支付分支付补处理'  /> | ";
                <%--<shiro:hasPermission name="MEMBERINFO_RESET">--%>
                <%--str += "<img src='${staticSource}/resources/images/oper_icon/reset_pwd.png' class=\"oper_icon\" onclick=\"restPasswordMthod('"--%>
                <%--+ cl + "')\" title='重置密码' alt='重置密码'  /> | ";--%>
                <%--</shiro:hasPermission>--%>
                <%--if (data[i].iisEnableRecoaward == 0) {--%>
                <%--<shiro:hasPermission name="MEMBERINFO_ENABLE">--%>
                <%--str += "<img src='${staticSource}/resources/images/oper_icon/enabled.png' class=\"oper_icon\" onclick=\"enableMethod('"--%>
                <%--+ cl + "',1)\" title='推荐启用' alt='推荐启用'  /> | ";--%>
                <%--</shiro:hasPermission>--%>
                <%--} else {--%>
                <%--<shiro:hasPermission name="MEMBERINFO_ENABLE">--%>
                <%--str += "<img src='${staticSource}/resources/images/oper_icon/sb_disable.png' class=\"oper_icon\" onclick=\"enableMethod('"--%>
                <%--+ cl + "',0)\" title='推荐禁用' alt='推荐禁用'  /> | ";--%>
                <%--</shiro:hasPermission>--%>
                <%--}--%>
                if (data[i].istatus == 1 || data[i].istatus == 2 || data[i].istatus == 4) {
                    <shiro:hasPermission name="MEMBERINFO_DIS">
                    str += "<img src='${staticSource}/resources/images/oper_icon/join_blacklist.png' class=\"oper_icon\" onclick=\"freezeMethod('"
                        + cl + "',1)\" title='加入黑名单' alt='加入黑名单'  /> | ";
                    </shiro:hasPermission>
                } else {
                    <shiro:hasPermission name="MEMBERINFO_DIS">
                    str += "<img src='${staticSource}/resources/images/oper_icon/release_blacklist.png' class=\"oper_icon\" onclick=\"freezeMethod('"
                        + cl + "',0)\" title='解除黑名单' alt='解除黑名单'  /> | ";
                    </shiro:hasPermission>
                }
                if (data[i].iaipayOpen == 1) {
                    <shiro:hasPermission name="MEMBERINFO_ALIPAY_MANUAL_TERMINATION">
                    str += "<img src='${staticSource}/resources/images/oper_icon/wechant_makeup.png' class=\"oper_icon\" onclick=\"manualTerminationMethod('"
                        + cl + "',1)\" title='手动解约' alt='手动解约'  /> | ";
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
            sortname: "A.ISTATUS=1",
            sortorder: "desc",
            shrinkToFit: false,
            autoScroll: true,
            multiselect: false
        };
        initTable(ctx + "/memberInfo/queryData", colModel, tableCallBack, config);
        <shiro:hasPermission name="MEMBERINFO_DOWNLOADEXCEL">
        addExportBtnByUrl("${ctx}/memberInfo/downloadExcel");
        </shiro:hasPermission>
    });
    //初始化form中按钮
    var initBtnConfig = {
            addUrl: ctx + '/memberInfo/edit',
            deleteUrl: ctx + "/memberInfo/delete",
            addTitle: '添加会员信息',
            addFn: showLayerMax
        }
    ;
    initBtnByForm4(initBtnConfig);
    function editMethod(sid) {
        showLayerMax("编辑会员信息", 'edit?sid=' + sid);
    }
    function rechargeAgainMethod(sid, type) {
        confirmOperTip("确定要补处理?", ctx + "/memberInfo/rechargeAgain", {checkboxId: sid, type: type});
    }
    function delMethod(sid) {
        confirmDelTip("确定要删除数据?", ctx + "/memberInfo/delete", {checkboxId: sid});
    }
    function queryAllMethod(sid) {
        showLayerMax("会员详情", ctx + '/memberInfo/queryAll?sid=' + sid);
    }
    function enableMethod(sid, type) {
        var alertStr = "是否确认该用户推荐启用?";
        if (type == 0) {
            alertStr = "是否确认该用户推荐禁用?";
        }
        confirmOperTip(alertStr, ctx + "/memberInfo/enableRecoaward", {checkboxId: sid, type: type});
    }
    function restPasswordMthod(sid) {
        confirmRestTip("确定要重置密码?", ctx + "/memberInfo/resetPassword", {sid: sid});
    }
    function freezeMethod(sid, type) {
        var alertStr = "是否确认将该用户加入黑名单?";
        if (type == 0) {
            alertStr = "是否确认将该用户解除黑名单?";
        }
        confirmOperTip(alertStr, ctx + "/memberInfo/blackList", {checkboxId: sid, type: type});
    }
    function manualTerminationMethod(sid) {
        confirmDelTip("确定要手动解约?", ctx + "/memberInfo/manualTermination", {checkboxId: sid});
    }
</script>
</body>
</html>

