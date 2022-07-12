<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>商户管理</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
<style>
    .ui-jqgrid tr.jqgrow td {
        white-space: normal !important;
        height: auto;
    }
</style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code='menu.merchant.management' /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder='<spring:message code="main.merchant.number" />'
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sname" id="sname" value="" placeholder='<spring:message code="main.merchant.name" />'
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control" name="itype" id="itype">
                                    <option value=""><spring:message code="sh.please.select" /><spring:message code="sh.business.type" /></option>
                                    <option value="10"><spring:message code="sh.personal" /></option>
                                    <option value="20"><spring:message code="sh.enterprise" /></option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" name="istatus" id="istatus" entire="true" value=""
                                             list='{20:springMessageCode=sh.signed,30:springMessageCode=sh.terminated}' entireName='springMessageCode=sh.please.select.the.status.of.signing'/>
                            </div>

                            <div class="layui-input-inline">
                                <cang:select type="dic" name="icooperationMode" id="icooperationMode" entire="true"
                                             entireName='springMessageCode=sh.please.choose.cooperation.mode" />' groupNo="SP000104"/>
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="scontactName" id="scontactName" value="" placeholder='<spring:message code="sh.contacts" />'
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="scontactMobile" id="scontactMobile" value=""
                                       placeholder='<spring:message code="sh.contact.cell.phone.number" />'
                                       class="layui-input">
                            </div>
                            <%--   <div class="layui-input-inline">
                                   <input type="text" name="queryTimeCondition" id="queryTimeCondition" placeholder='<spring:message code="sh.contract.expiration.date" />'
                                          class="layui-input">
                               </div>--%>
                            <%-- <div class="layui-input-inline">
                                 <select class="form-control" name="idistributionSwitch" id="idistributionSwitch">
                                     <option value=""><spring:message code="sh.please.select.secondary.distribution.status" /></option>
                                     <option value="0"><spring:message code="sh.prohibit" /></option>
                                     <option value="1"><spring:message code="main.enable" /></option>
                                 </select>
                             </div>--%>
                            <div class="layui-input-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i
                                        class="layui-icon">&#xe615;</i><spring:message code="main.query" />
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i
                                        class="layui-icon">&#x1006;</i><spring:message code="main.clear" />
                                </button>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <shiro:hasPermission name="MERCHANT_ADD">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"><i
                                        class="layui-icon"></i><spring:message code="main.add" />
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="MERCHANT_DELETE">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i><spring:message code="main.delete" />
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="MERCHANT_CACHE">
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
    var colModel = [{name: 'id', index: 'id', hidden: true},
        {name: 'idistributionSwitch', index: 'idistribution_switch', hidden: true},
        {name: 'scode', label: '<spring:message code="main.merchant.number" />', index: 'scode'},
        {name: 'sname', label: '<spring:message code="sm.merchant.name" />', index: 'sname',width: 200,},
        {
            name: 'itype',
            index: 'itype',
            label: '<spring:message code="sh.business.type" />',
            formatter: "select",
            width: 140,
            editoptions: {value: '10:<spring:message code="sh.personal" />;20:<spring:message code="sh.enterprise" />'}
        },
        {
            name: 'istatus',
            index: 'istatus',
            label: '<spring:message code="sh.contractual.status" />',
            formatter: "select",
            width:180,
            editoptions: {value: '10:<spring:message code="sh.negotiating" />;20:<spring:message code="sh.signed" />;30:<spring:message code="sh.terminated" />;40:<spring:message code="sh.contract.expired" />'}
        },
        {name: 'scontactName', label: '<spring:message code="sh.contacts" />', index: 'SCONTACT_NAME'},
        {name: 'scontactMobile', label: '<spring:message code="sh.contact.telephone" />', width:180,index: 'SCONTACT_MOBILE'},
        {
            name: 'icooperationMode',
            index: 'ICOOPERATION_MODE',
            label: '<spring:message code="sh.cooperation.model" />',
            formatter: "select",
            width:240,
            editoptions: {value: '10:<spring:message code="sh.purchase" />;20:<spring:message code="sh.rent" />;30:<spring:message code="sh.independence" />'}
        },
        {name: "process", title: false, index: "process", label: '<spring:message code="main.operation" />', sortable: false, frozen: true, width: 330},
        {name: 'iisConfWechatGzh', index: 'IIS_CONF_WECHAT_GZH', hidden: true}, /*微信公众号是否配置*/
        {name: 'iisConfAlipayShh', index: 'IIS_CONF_ALIPAY_SHH', hidden: true}, /*支付宝生活号是否配置*/
        {name: 'iisConfWechat', index: 'IIS_CONF_WECHAT', hidden: true}, /*微信支付是否配置*/
        {name: 'ivmSkuType', index: 'IVM_SKU_TYPE', hidden: true}, /*配置视觉商品*/
        {name: 'iisConfAlipay', index: 'IIS_CONF_ALIPAY', hidden: true}/*支付宝支付是否配置*/
    ];
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
            var data = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = "";
                <shiro:hasPermission name="MERCHANT_QUERYAll">
                str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"queryAllMethod('"
                    + cl + "')\" title='<spring:message code="sh.business.details" />' alt='<spring:message code="sh.business.details" />'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="MERCHANT_EDIT">
                str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                    + cl + "')\" title='<spring:message code="sh.edit.merchant" />' alt='<spring:message code="sh.edit.merchant" />'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="MERCHANT_DELETE">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='<spring:message code="sh.delete.business" />' alt='<spring:message code="sh.delete.business" />'  /> | ";
                </shiro:hasPermission>

                if (data[i].istatus == 30 || data[i].istatus == 10) {
                    <shiro:hasPermission name="MERCHANT_CONTRACT">
                    str += "<img src='${staticSource}/resources/images/merchantinfo/qianyue.png' class=\"merchantinfo oper_icon\" onclick=\"contractMethod('"
                        + cl + "',20)\" title='<spring:message code="sh.signing" />' alt='<spring:message code="sh.signing" />'  /> | ";
                    </shiro:hasPermission>
                } else if (data[i].istatus == 20) {
                    <shiro:hasPermission name="MERCHANT_CONTRACT">
                    str += "<img src='${staticSource}/resources/images/merchantinfo/jieyue.png' class=\"merchantinfo oper_icon\" onclick=\"contractMethod('"
                        + cl + "',30)\" title='<spring:message code="sh.release" />' alt='<spring:message code="sh.release" />'  /> | ";
                    </shiro:hasPermission>
                }

                if (data[i].istatus == 20) {
                    <shiro:hasPermission name="MERCHANT_MENU">
                    str += "<img src='${staticSource}/resources/images/merchantinfo/caidanquanxian.png' class=\"merchantinfo oper_icon\" onclick=\"dealingAuthorization('"
                        + cl + "')\" title='<spring:message code="sh.menu.permission" />' alt='<spring:message code="sh.menu.permission" />'  /> | ";
                    </shiro:hasPermission>
                }
                if (data[i].ivmSkuType == 2) {
                    <shiro:hasPermission name="ALLOT_VR_COMMODITY">
                    str += "<img src='${staticSource}/resources/images/merchantinfo/peizhishijueshangpin.png' class=\"merchantinfo oper_icon\" onclick=\"allotVrCommodity('"
                        + cl + "')\" title='<spring:message code="sh.configuring.visual.goods" />' alt='<spring:message code="sh.configuring.visual.goods" />'  /> | ";
                    </shiro:hasPermission>
                }
                if (data[i].idistributionSwitch == 0) {
                    <shiro:hasPermission name="MERCHANT_ENABLE">
                    str += "<img src='${staticSource}/resources/images/merchantinfo/fenxiaoqiyong.png' class=\"merchantinfo oper_icon\" onclick=\"enableMethod('"
                        + cl + "',1)\" title='<spring:message code="sh.distribution.enabled" />' alt='<spring:message code="sh.distribution.enabled" />'/> | ";
                    </shiro:hasPermission>
                } else {
                    <shiro:hasPermission name="MERCHANT_ENABLE">
                    str += "<img src='${staticSource}/resources/images/merchantinfo/fenxiaojinyong.png' class=\"merchantinfo oper_icon\" onclick=\"enableMethod('"
                        + cl + "',0)\" title='<spring:message code="sh.distribution.disabled" />' alt='<spring:message code="sh.distribution.disabled" />'/> | ";
                    </shiro:hasPermission>
                    <shiro:hasPermission name="MERCHANT_DIS">
                    str += "<img src='${staticSource}/resources/images/merchantinfo/erjifenxiaopeizhi.png' class=\"merchantinfo oper_icon\" onclick=\"disMethod('"
                        + cl + "',0)\" title='<spring:message code="sh.secondary.distribution.configuration" />' alt='<spring:message code="sh.secondary.distribution.configuration" />'/> | ";
                    </shiro:hasPermission>
                }
                if (data[i].iisConfWechatGzh == 0) {
                    <shiro:hasPermission name="MERCHANT_WECHATGZH">
                    str += "<img src='${staticSource}/resources/images/merchantinfo/wxgzhqiyong.png' class=\"merchantinfo oper_icon\" onclick=\"OpenGzhMethod('"
                        + cl + "',1,'<spring:message code="sh.wechat.subscription" />','iisConfWechatGzh')\" title='<spring:message code="sh.wechat.public.number.is.enabled" />' alt='<spring:message code="sh.wechat.public.number.is.enabled" />'/> | ";
                    </shiro:hasPermission>
                } else {
                    <shiro:hasPermission name="MERCHANT_WECHATGZH">
                    str += "<img src='${staticSource}/resources/images/merchantinfo/wxgzhjinyong.png' class=\"merchantinfo\" onclick=\"OpenGzhMethod('"
                        + cl + "',0,'<spring:message code="sh.wechat.subscription" />','iisConfWechatGzh')\" title='<spring:message code="sh.wechat.public.number.is.disabled" />' alt='<spring:message code="sh.wechat.public.number.is.disabled" />'/> | ";
                    </shiro:hasPermission>
                }
                if (data[i].iisConfAlipayShh == 0) {
                    <shiro:hasPermission name="MERCHANT_WECHATSHH">
                    str += "<img src='${staticSource}/resources/images/merchantinfo/zfbshhqiyong.png' class=\"merchantinfo oper_icon\" onclick=\"OpenGzhMethod('"
                        + cl + "',1,'<spring:message code="sh.alipay.subscription" />','iisConfAlipayShh')\" title='<spring:message code="sh.alipay.life.number.is.enabled" />' alt='<spring:message code="sh.alipay.life.number.is.enabled" />'/> | ";
                    </shiro:hasPermission>
                } else {
                    <shiro:hasPermission name="MERCHANT_WECHATSHH">
                    str += "<img src='${staticSource}/resources/images/merchantinfo/zfbshhjinyong.png' class=\"merchantinfo oper_icon\" onclick=\"OpenGzhMethod('"
                        + cl + "',0,'<spring:message code="sh.alipay.subscription" />','iisConfAlipayShh')\" title='<spring:message code="sh.alipay.life.number.is.disabled" />' alt='<spring:message code="sh.alipay.life.number.is.disabled" />'/> | ";
                    </shiro:hasPermission>
                }
                /*  }*/
                if (data[i].iisConfWechat == 0) {
                    <shiro:hasPermission name="MERCHANT_WECHANT">
                    str += "<img src='${staticSource}/resources/images/merchantinfo/wxzfqiyong.png' class=\"merchantinfo oper_icon\" onclick=\"OpenGzhMethod('"
                        + cl + "',1,'<spring:message code="sh.wechat.pay" />','iisConfWechat')\" title='<spring:message code="sh.wechat.payment.enabled" />' alt='<spring:message code="sh.wechat.payment.enabled" />'/> | ";
                    </shiro:hasPermission>
                } else {
                    <shiro:hasPermission name="MERCHANT_WECHANT">
                    str += "<img src='${staticSource}/resources/images/merchantinfo/wxzfjinyong.png' class=\"merchantinfo oper_icon\" onclick=\"OpenGzhMethod('"
                        + cl + "',0,'<spring:message code="sh.wechat.pay" />','iisConfWechat')\" title='<spring:message code="sh.wechat.payment.is.disabled" />' alt='<spring:message code="sh.wechat.payment.is.disabled" />'/> | ";
                    </shiro:hasPermission>
                }
                if (data[i].iisConfAlipay == 0) {
                    <shiro:hasPermission name="MERCHANT_ALIPAY">
                    str += "<img src='${staticSource}/resources/images/merchantinfo/zfbzfqiyong.png' class=\"merchantinfo oper_icon\" onclick=\"OpenGzhMethod('"
                        + cl + "',1,'<spring:message code="sh.pay.with.ali.pay" />','iisConfAlipay')\" title='<spring:message code="sh.alipay.payment.enabled" />' alt='<spring:message code="sh.alipay.payment.enabled" />'/> | ";
                    </shiro:hasPermission>
                } else {
                    <shiro:hasPermission name="MERCHANT_ALIPAY">
                    str += "<img src='${staticSource}/resources/images/merchantinfo/zfbzfjinyong.png' class=\"merchantinfo oper_icon\" onclick=\"OpenGzhMethod('"
                        + cl + "',0,'<spring:message code="sh.pay.with.ali.pay" />','iisConfAlipay')\" title='<spring:message code="sh.alipay.payment.disabled" />' alt='<spring:message code="sh.alipay.payment.disabled" />'/> | ";
                    </shiro:hasPermission>
                }
                $("#gridTable").jqGrid('setRowData',
                    cl, {
                        process: str.length > 0 ? str.substr(0, str.lastIndexOf(" | ")) : ""
                    });
            }
        }, 0);
    }
    $(function () {
        var config = {
            sortname: "ISTATUS=10",
            sortorder: "desc"
        };
        initTable(ctx + "/merchantInfo/queryData", colModel, tableCallBack, config);
    });
    //初始化form中按钮
    var initBtnConfig = {
            addUrl: ctx + '/merchantInfo/edit',
            deleteUrl: ctx + "/merchantInfo/delete",
            addTitle: '<spring:message code="sh.add.business.information" />',
            addFn: showLayerMax
        }
    ;
    initBtnByForm4(initBtnConfig);


    layui.use(['form', 'laydate'], function () {
        /**
         * <spring:message code="sh.time.group.key" />
         */
        var laydate = layui.laydate;
        //执行一个laydate实例
        laydate.render({
            elem: '#dexpireDate' //指定元素
        });
        laydate.render({
            elem: '#queryTimeCondition', //指定元素,查询商户合约到期日
            range: true,
            type: 'date'
        });
        var $ = layui.$, active = {
            refreshCache: function () {//刷新缓存
                var loadIndex = loading();
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: ctx + '/merchantInfo/cache',
                    success: function (msg) {
                        closeLayer(loadIndex);//关闭加载框
                        if (msg.success) {
                            alertInfo(msg.data)
                        } else {
                            alertDel(msg.data);
                        }
                    }
                });
            }
        };
        $('.layui-form .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
        <c:if test="${!empty method}">
            <c:if test="${method eq 'add'}">
                if($.isFunction(initBtnConfig.addFn)) {
                    initBtnConfig.addFn(initBtnConfig.addTitle, initBtnConfig.addUrl, initBtnConfig.addModelConfig);
                } else {
                    showLayer(initBtnConfig.addTitle, initBtnConfig.addUrl, initBtnConfig.addModelConfig)
                }
            </c:if>
        </c:if>
    });
    function editMethod(sid) {
        showLayerMax('<spring:message code="sh.edit.merchant" />', ctx + '/merchantInfo/edit?sid=' + sid);
    }
    function delMethod(sid) {

        confirmDelTip('<spring:message code="sh.make.sure.you.want.to.delete.data" />', ctx + "/merchantInfo/delete", {checkboxId: sid});
    }
    function queryAllMethod(sid) {
        showLayerMax('<spring:message code="sh.business.details" />', ctx + '/merchantInfo/queryAll?sid=' + sid);

    }
    function disMethod(sid) {
        showLayerMin('<spring:message code="sh.secondary.distribution.configuration" />', ctx + '/merchantInfo/toDisEdit?sid=' + sid);
    }

    function enableMethod(sid, type) {
        var alertStr = '<spring:message code="sh.are.you.sure.you.want.to.turn.on.secondary.distribution" />';
        if (type == 0) {
            alertStr = '<spring:message code="sh.are.you.sure.you.want.to.disable.secondary.distribution" />';
        }
        confirmOperTip(alertStr, ctx + "/merchantInfo/enable", {checkboxId: sid, type: type});
    }
    function contractMethod(sid, type) {
        var alertStr = '<spring:message code="sh.determine.to.sign.up.for.a.merchant" />?';
        if (type == 30) {
            alertStr = '<spring:message code="sh.are.you.sure.you.want.to.cancel.your.contract.with.the.merchant" />';
        }
        confirmOperTip(alertStr, ctx + "/merchantInfo/contract", {checkboxId: sid, type: type});
    }
    function OpenGzhMethod(sid, type, str, name) {
        var alertStr = '<spring:message code="sh.ok.to.enable" />';
        if (type == 0) {
            alertStr = '<spring:message code="sh.ok.to.disable" />';
        }
        confirmOperTip(alertStr + str, ctx + "/merchantInfo/configure", {
            checkboxId: sid,
            type: type,
            str: str,
            name: name
        });
    }
    /**
     * <spring:message code="sh.assign.menu.permissions" />
     */
    function dealingAuthorization(sid) {
        showLayerMedium('<spring:message code="sh.menu.permission.configuration" />', ctx + '/merchantInfo/menuAuthConfig?sid=' + sid);
    }
    function allotVrCommodity(sid) {
        showLayerMedium('<spring:message code="sh.configuring.visual.goods" />', ctx + '/commoditySku/merchantSelectList?sid=' + sid);
    }
</script>
</body>
</html>

