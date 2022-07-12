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
                    <h5><spring:message code='hy.member.user.list' /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-inline">
                                    <input type="text" name="smerchantCode" id="smerchantCode" value=""
                                           placeholder='<spring:message code="main.merchant.number" />...' class="layui-input">
                                </div>
                                <div class="layui-inline">
                                    <input type="text" name="merchantName" id="merchantName" value=""
                                           placeholder='<spring:message code="main.merchant.name" />...' class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder='<spring:message code="main.member.number" />...'
                                       class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="smemberName" id="smemberName" value="" placeholder='<spring:message code="main.member.username" />...'
                                       class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <select class="form-control" name="isex" id="isex">
                                    <option value=""><spring:message code="main.gender" /></option>
                                    <option value="1"><spring:message code="main.male" /></option>
                                    <option value="0"><spring:message code="main.female" /></option>
                                </select>
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <select class="form-control" name="istatus" id="istatus">
                                    <option value=""><spring:message code="hy.member.status" /></option>
                                    <option value="1"><spring:message code="main.normal" /></option>
                                    <option value="2"><spring:message code='hy.logout.disabled' /></option>
                                    <option value="3"><spring:message code='hy.system.blacklist' /></option>
                                    <option value="4"><spring:message code='ac.freeze' /></option>
                                </select>
                            </div>
                            <%--<div class="layui-inline" style="width: 182px">
                                <cang:select type="list" id="iregClientType" name="iregClientType" groupNo="SP000120"
                                             list='{10:springMessageCode=main.wechat.pay,20:springMessageCode=main.alipay,30:springMessageCode=main.wechat.public.number,40:springMessageCode=main.alipay.life,50:APP android,60:APP ios }'
                                             entire="true" entireName='springMessageCode=hy.registration.platform' />
                            </div>--%>
                            <div class="layui-inline" style="width: 182px">
                                <select class="form-control" name="iisOrder" id="iisOrder">
                                    <option value=""><spring:message code='hy.whether.the.first.order' /></option>
                                    <option value="0"><spring:message code="main.no" /></option>
                                    <option value="1"><spring:message code="main.yes" /></option>
                                </select>
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <select class="form-control" name="iisReplenishment" id="iisReplenishment">
                                    <option value=""><spring:message code="sys.is.replenisher" /></option>
                                    <option value="0"><spring:message code="main.no" /></option>
                                    <option value="1"><spring:message code="main.yes" /></option>
                                </select>
                            </div>
                            <%--<div class="layui-inline" style="width: 182px">
                                <select class="form-control" name="iaipayOpen" id="iaipayOpen">
                                    <option value=""><spring:message code="main.alipay.free" /></option>
                                    <option value="0"><spring:message code="main.no" /></option>
                                    <option value="1"><spring:message code="main.yes" /></option>
                                </select>
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <select class="form-control" name="iwechatOpen" id="iwechatOpen">
                                    <option value=""><spring:message code='hy.is.wechat.confidentiality.open' /></option>
                                    <option value="0"><spring:message code="main.no" /></option>
                                    <option value="1"><spring:message code="main.yes" /></option>
                                </select>
                            </div>--%>
                            <%--<div class="layui-inline" style="width: 182px">--%>
                            <%--<select class="form-control" name="iisEnableRecoaward" id="iisEnableRecoaward">--%>
                            <%--<option value=""><spring:message code='hy.recommended.to.enable' /></option>--%>
                            <%--<option value="0"><spring:message code="main.no" /></option>--%>
                            <%--<option value="1"><spring:message code="main.yes" /></option>--%>
                            <%--</select>--%>
                            <%--</div>--%>
                            <div class="layui-inline" style="width: 182px">
                                <input type="text" name="sregDeviceCode" id="sregDeviceCode" value=""
                                       placeholder='<spring:message code='hy.registered.device.number' />...' class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <input type="text" name="tregisterTimeStr" id="tregisterTimeStr" placeholder='<spring:message code="sb.registration.time" />'
                                       class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn layui-btn" id="searchBtn" data-type="query"><i
                                        class="layui-icon">&#xe615;</i><spring:message code="main.query" />
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i
                                        class="layui-icon">&#x1006;</i><spring:message code="main.clear" />
                                </button>
                            </div>
                        </div>
                        <%--<div class="layui-form-item">--%>
                        <%--<shiro:hasPermission name="ADD_MEMBERINFO">--%>
                        <%--<button class="layui-btn layui-btn-sm layui-btn-warm" style="margin-left: 0px;" data-type="add"><i class="layui-icon"></i><spring:message code="main.add" /></button>--%>
                        <%--</shiro:hasPermission>--%>
                        <%--<shiro:hasPermission name="MEMBERINFO_DELETE">--%>
                        <%--<button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i><spring:message code="main.delete" /></button>--%>
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
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<script src="${staticSource}/resources/js/tableExportComm-${currentLanguage}.js"></script>
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
        {name: 'smerchantCode', label: '<spring:message code="main.merchant.number" />', index: 'SMERCHANT_CODE', sortable: false},
        {name: 'merchantName', label: '<spring:message code="main.merchant.name" />', index: 'MERCHANTNAME', sortable: false, width: 320},
        </c:if>
        {name: 'scode', index: 'SCODE', label: '<spring:message code="main.member.number" />', sortable: false},
        {name: 'smemberName', index: 'SMEMBER_NAME', label: '<spring:message code="main.member.username" />', sortable: false},
        {name: 'snickName', index: 'SNICKNAME', label: "<spring:message code='hy.member.nickname' />", sortable: false},
        {
            name: 'isex', index: 'ISEX', label: '<spring:message code="main.gender" />', editable: true,
            formatter: "select", editoptions: {value: '1:<spring:message code="main.male" />;0:<spring:message code="main.female" />'}, sortable: false, width: 100
        },
        {
            name: 'istatus', index: 'ISTATUS', label: '<spring:message code="hy.member.status" />', editable: true,
            formatter: "select", editoptions: {value: '1:<spring:message code="main.normal" />;2:<spring:message code='hy.logout.disabled' />;3:<spring:message code='hy.system.blacklist' />;4:<spring:message code='ac.freeze' />'}, sortable: false, width: 130
        },
        {
            name: 'iregClientType',
            index: 'IREG_CLIENT_TYPE',
            label: "<spring:message code='hy.registration.platform' />",
            editable: true,
            formatter: "select",
            editoptions: {value: '10:<spring:message code="main.wechat.pay" />;20:<spring:message code="main.alipay" />;30:<spring:message code="main.wechat.public.number" />;40:<spring:message code="main.alipay.life" />;50:APP android;60:APP ios;70:Vendstop Wallet'},
            sortable: false, width: 220
        },
        {
            name: 'iisOrder', index: 'IIS_ORDER', label: "<spring:message code='hy.is.it.the.first' />", editable: true,
            formatter: "select", editoptions: {value: '0:<spring:message code="main.no" />;1:<spring:message code="main.yes" />'}, sortable: false, width: 200
        },
        {
            name: 'iisReplenishment', index: 'IIS_REPLENISHMENT', label: '<spring:message code="sys.is.replenisher" />', editable: true,
            formatter: "select", editoptions: {value: '0:<spring:message code="main.no" />;1:<spring:message code="main.yes" />'}, sortable: false, width: 170
        }/*,
        {
            name: 'iaipayOpen', index: 'IAIPAY_OPEN', label: '<spring:message code="main.alipay.free" />', editable: true,
            formatter: "select", editoptions: {value: '0:<spring:message code="main.no" />;1:<spring:message code="main.yes" />'}, sortable: false, width: 240
        },
        {
            name: 'iwechatOpen', index: 'IWECHAT_OPEN', label: '<spring:message code="main.wechat.free" />', editable: true,
            formatter: "select", editoptions: {value: '0:<spring:message code="main.no" />;1:<spring:message code="main.yes" />'}, sortable: false, width: 320
        }*/,
        {
            name: 'iwechatPointOpen', index: 'IWECHAT_POINT_OPEN', label: "微信支付分是否开通", editable: true,
            formatter: "select", editoptions: {value: '0:否;1:是'}, sortable: false, width: 180
        },
//        {
//            name: 'iisEnableRecoaward', index: 'IIS_ENABLE_RECOAWARD', label: "<spring:message code='hy.recommended.to.enable' />", editable: true,
//            formatter: "select", editoptions: {value: '0:<spring:message code="main.no" />;1:<spring:message code="main.yes" />'}, sortable: false, width: 100
//        },
        {name: 'sregDeviceCode', index: 'SREG_DEVICE_CODE', label: '<spring:message code="sb.registered.device.number" />', sortable: false, width: 200},
        {
            name: 'tregisterTime', index: 'TREGISTER_TIME', label: '<spring:message code="sb.registration.time" />', formatter: function (value) {
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }
        },
        {name: 'process', title: false, index: 'process', label: '<spring:message code="main.operation" />', sortable: false, width: 320}
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
                    + cl + "')\" title='<spring:message code="hy.member.details" />' alt='<spring:message code="hy.member.details" />' /> | ";
                </shiro:hasPermission>
                <%--<shiro:hasPermission name="MEMBERINFO_EDIT">--%>
                <%--str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"--%>
                <%--+ cl + "')\" title='<spring:message code='hy.edit.member' />' alt='<spring:message code='hy.edit.member' />'  /> | ";--%>
                <%--</shiro:hasPermission>--%>
                <shiro:hasPermission name="MERCHANT_DELETE">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='<spring:message code='hy.delete.member' />' alt='<spring:message code='hy.delete.member' />'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="MEMBER_ALIPAY_MAKEUP">
                str += "<img src='${staticSource}/resources/images/oper_icon/alipay_makeup.png' class=\"oper_icon\" onclick=\"rechargeAgainMethod('"
                    + cl + "',0)\" title='<spring:message code='hy.alipay.exemption.payment.processing' />' alt='<spring:message code='hy.alipay.exemption.payment.processing' />'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="MEMBER_WECHANT_MAKEUP">
                str += "<img src='${staticSource}/resources/images/oper_icon/wechant_makeup.png' class=\"oper_icon\" onclick=\"rechargeAgainMethod('"
                    + cl + "',1)\" title='<spring:message code='hy.wechat.exemption.payment.processing' />' alt='<spring:message code='hy.wechat.exemption.payment.processing' />'  /> | ";
                </shiro:hasPermission>
                str += "<img src='${staticSource}/resources/images/oper_icon/wechant_makeup.png' class=\"oper_icon\" onclick=\"rechargeAgainMethod('"
                    + cl + "',2)\" title='微信支付分支付补处理' alt='微信支付分支付补处理'  /> | ";
                <%--<shiro:hasPermission name="MEMBERINFO_RESET">--%>
                <%--str += "<img src='${staticSource}/resources/images/oper_icon/reset_pwd.png' class=\"oper_icon\" onclick=\"restPasswordMthod('"--%>
                <%--+ cl + "')\" title='<spring:message code='hy.reset.password' />' alt='<spring:message code='hy.reset.password' />'  /> | ";--%>
                <%--</shiro:hasPermission>--%>
                <%--if (data[i].iisEnableRecoaward == 0) {--%>
                <%--<shiro:hasPermission name="MEMBERINFO_ENABLE">--%>
                <%--str += "<img src='${staticSource}/resources/images/oper_icon/enabled.png' class=\"oper_icon\" onclick=\"enableMethod('"--%>
                <%--+ cl + "',1)\" title='<spring:message code='hy.recommended.to.enable' />' alt='<spring:message code='hy.recommended.to.enable' />'  /> | ";--%>
                <%--</shiro:hasPermission>--%>
                <%--} else {--%>
                <%--<shiro:hasPermission name="MEMBERINFO_ENABLE">--%>
                <%--str += "<img src='${staticSource}/resources/images/oper_icon/sb_disable.png' class=\"oper_icon\" onclick=\"enableMethod('"--%>
                <%--+ cl + "',0)\" title='<spring:message code='hy.recommended.to.disable' />' alt='<spring:message code='hy.recommended.to.disable' />'  /> | ";--%>
                <%--</shiro:hasPermission>--%>
                <%--}--%>
                if (data[i].istatus == 1 || data[i].istatus == 2 || data[i].istatus == 4) {
                    <shiro:hasPermission name="MEMBERINFO_DIS">
                    str += "<img src='${staticSource}/resources/images/oper_icon/join_blacklist.png' class=\"oper_icon\" onclick=\"freezeMethod('"
                        + cl + "',1)\" title='<spring:message code='hy.add.to.blacklist' />' alt='<spring:message code='hy.add.to.blacklist' />'  /> | ";
                    </shiro:hasPermission>
                } else {
                    <shiro:hasPermission name="MEMBERINFO_DIS">
                    str += "<img src='${staticSource}/resources/images/oper_icon/release_blacklist.png' class=\"oper_icon\" onclick=\"freezeMethod('"
                        + cl + "',0)\" title='<spring:message code='hy.blacklist' />' alt='<spring:message code='hy.blacklist' />'  /> | ";
                    </shiro:hasPermission>
                }
                if (data[i].iaipayOpen == 1) {
                    <shiro:hasPermission name="MEMBERINFO_ALIPAY_MANUAL_TERMINATION">
                    str += "<img src='${staticSource}/resources/images/oper_icon/wechant_makeup.png' class=\"oper_icon\" onclick=\"manualTerminationMethod('"
                        + cl + "',1)\" title='<spring:message code='hy.manual.cancellation' />' alt='<spring:message code='hy.manual.cancellation' />'  /> | ";
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
            addTitle: '<spring:message code='hy.add.member.information' />',
            addFn: showLayerMax
        }
    ;
    initBtnByForm4(initBtnConfig);
    function editMethod(sid) {
        showLayerMax("<spring:message code='hy.edit.member.information' />", 'edit?sid=' + sid);
    }
    function rechargeAgainMethod(sid, type) {
        confirmOperTip("<spring:message code='hy.are.you.sure.you.want.to.process.it' />?", ctx + "/memberInfo/rechargeAgain", {checkboxId: sid, type: type});
    }
    function delMethod(sid) {
        confirmDelTip('<spring:message code="main.delete.confirm" />', ctx + "/memberInfo/delete", {checkboxId: sid});
    }
    function queryAllMethod(sid) {
        showLayerMax('<spring:message code="hy.member.details" />', ctx + '/memberInfo/queryAll?sid=' + sid);
    }
    function enableMethod(sid, type) {
        var alertStr = "<spring:message code='hy.whether.to.confirm.that.the.user.recommendation.is.enabled' />?";
        if (type == 0) {
            alertStr = "<spring:message code='hy.whether.to.confirm.that.the.user.recommendation.is.disabled' />?";
        }
        confirmOperTip(alertStr, ctx + "/memberInfo/enableRecoaward", {checkboxId: sid, type: type});
    }
    function restPasswordMthod(sid) {
        confirmRestTip("<spring:message code='hy.are.you.sure.you.want.to.reset.your.password' />?", ctx + "/memberInfo/resetPassword", {sid: sid});
    }
    function freezeMethod(sid, type) {
        var alertStr = "<spring:message code='hy.whether.to.confirm.join.the.blacklist.of.this.user' />?";
        if (type == 0) {
            alertStr = "<spring:message code='hy.whether.to.confirm.release.the.blacklist.of.this.user' />?";
        }
        confirmOperTip(alertStr, ctx + "/memberInfo/blackList", {checkboxId: sid, type: type});
    }
    function manualTerminationMethod(sid) {
        confirmDelTip("<spring:message code='hy.are.you.sure.you.want.to.cancel.the.contract.manually' />?", ctx + "/memberInfo/manualTermination", {checkboxId: sid});
    }
</script>
</body>
</html>

