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
                    <h5>商户管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder="商户编号"
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sname" id="sname" value="" placeholder="商户名称"
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control" name="itype" id="itype">
                                    <option value="">请选择商户类型</option>
                                    <option value="10">个人</option>
                                    <option value="20">企业</option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <cang:select type="list" name="istatus" id="istatus" entire="true" value=""
                                             list="{20:已签约,30:已解约}" entireName="请选择签约状态"/>
                            </div>

                            <div class="layui-input-inline">
                                <cang:select type="dic" name="icooperationMode" id="icooperationMode" entire="true"
                                             entireName="请选择合作模式" groupNo="SP000104"/>
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="scontactName" id="scontactName" value="" placeholder="联系人"
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="scontactMobile" id="scontactMobile" value=""
                                       placeholder="联系人手机号"
                                       class="layui-input">
                            </div>
                            <%--   <div class="layui-input-inline">
                                   <input type="text" name="queryTimeCondition" id="queryTimeCondition" placeholder="合约到期日"
                                          class="layui-input">
                               </div>--%>
                            <%-- <div class="layui-input-inline">
                                 <select class="form-control" name="idistributionSwitch" id="idistributionSwitch">
                                     <option value="">请选择二级分销状态</option>
                                     <option value="0">禁用</option>
                                     <option value="1">启用</option>
                                 </select>
                             </div>--%>
                            <div class="layui-input-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i
                                        class="layui-icon">&#xe615;</i>查询
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i
                                        class="layui-icon">&#x1006;</i>清除
                                </button>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <shiro:hasPermission name="MERCHANT_ADD">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"><i
                                        class="layui-icon"></i>添加
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="MERCHANT_DELETE">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i>删除
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="MERCHANT_CACHE">
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
    var colModel = [{name: 'id', index: 'id', hidden: true},
        {name: 'idistributionSwitch', index: 'idistribution_switch', hidden: true},
        {name: 'scode', label: "商户编号", index: 'scode'},
        {name: 'sname', label: "商户名", index: 'sname'},
        {
            name: 'itype',
            index: 'itype',
            label: "商户类型",
            formatter: "select",
            width: 100,
            editoptions: {value: '10:个人;20:企业'}
        },
        {
            name: 'istatus',
            index: 'istatus',
            label: "签约状态",
            formatter: "select",
            editoptions: {value: '10:洽谈中;20:已签约;30:已解约;40:合约到期'}
        },
        {name: 'scontactName', label: "联系人", index: 'SCONTACT_NAME'},
        {name: 'scontactMobile', label: "联系人电话", index: 'SCONTACT_MOBILE'},
        /* {
         name: 'dexpireDate', label: "合约到期日", index: 'DEXPIRE_DATE', formatter: function (value) {
         if (isEmpty(value)) {
         return "";
         }
         return formatDate(new Date(value), 'yyyy-MM-dd');
         }
         },*/
        {
            name: 'icooperationMode',
            index: 'ICOOPERATION_MODE',
            label: "合作模式",
            formatter: "select",
            width: 100,
            editoptions: {value: '10:采购;20:租用;30:自主'}
        },
        /* {
         name: 'idistributionSwitch',
         label: "二级分销状态",
         index: 'idistribution_switch',
         formatter: "select",
         editoptions: {value: '0:关闭;1:启用'}
         },*/
        {name: "process", title: false, index: "process", label: "操作", sortable: false, frozen: true, width: 410},
        {name: 'iisConfWechatGzh', index: 'IIS_CONF_WECHAT_GZH', hidden: true}, /*微信公众号是否配置*/
        {name: 'iisConfAlipayShh', index: 'IIS_CONF_ALIPAY_SHH', hidden: true}, /*支付宝生活号是否配置*/
        {name: 'iisConfWechat', index: 'IIS_CONF_WECHAT', hidden: true}, /*微信支付是否配置*/
        {name: 'ivmSkuType', index: 'IVM_SKU_TYPE', hidden: true}, /*配置视觉商品*/
        {name: 'iisConfAlipay', index: 'IIS_CONF_ALIPAY', hidden: true}/*支付宝支付是否配置*/
    ];
    function tableCallBack() {
        setTimeout(function () {

            var data = $("#gridTable").jqGrid('getRowData');

            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = "";
                <shiro:hasPermission name="MERCHANT_QUERYAll">
                str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"queryAllMethod('"
                    + cl + "')\" title='商户详情' alt='商户详情'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="MERCHANT_EDIT">
                str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                    + cl + "')\" title='编辑商户' alt='编辑商户'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="MERCHANT_DELETE">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='删除商户' alt='删除商户'  /> | ";
                </shiro:hasPermission>

                if (data[i].istatus == 30 || data[i].istatus == 10) {
                    <shiro:hasPermission name="MERCHANT_CONTRACT">
                    /*       str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"contractMethod('"
                     + cl + "',20)\">签约</a> |  ";*/
                    str += "<img src='${staticSource}/resources/images/merchantinfo/qianyue.png' class=\"merchantinfo\" onclick=\"contractMethod('"
                        + cl + "',20)\" title='签约' alt='签约'  /> | ";
                    </shiro:hasPermission>
                } else if (data[i].istatus == 20) {
                    <shiro:hasPermission name="MERCHANT_CONTRACT">
                    /* str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"contractMethod('"
                     + cl + "',30)\">解约</a> |  ";*/
                    str += "<img src='${staticSource}/resources/images/merchantinfo/jieyue.png' class=\"merchantinfo\" onclick=\"contractMethod('"
                        + cl + "',30)\" title='解约' alt='解约'  /> | ";
                    </shiro:hasPermission>
                }

                if (data[i].istatus == 20) {
                    <shiro:hasPermission name="MERCHANT_MENU">
                    /* str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"dealingAuthorization('"
                     + cl + "')\">菜单权限</a> |  ";*/
                    str += "<img src='${staticSource}/resources/images/merchantinfo/caidanquanxian.png' class=\"merchantinfo\" onclick=\"dealingAuthorization('"
                        + cl + "')\" title='菜单权限' alt='菜单权限'  /> | ";
                    </shiro:hasPermission>
                }
                if (data[i].ivmSkuType == 2) {
                    <shiro:hasPermission name="ALLOT_VR_COMMODITY">
                    /*                   str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"allotVrCommodity('"
                     + cl + "')\">配置视觉商品</a> |  ";*/
                    str += "<img src='${staticSource}/resources/images/merchantinfo/peizhishijueshangpin.png' class=\"merchantinfo\" onclick=\"allotVrCommodity('"
                        + cl + "')\" title='配置视觉商品' alt='配置视觉商品'  /> | ";
                    </shiro:hasPermission>
                }
                if (data[i].idistributionSwitch == 0) {
                    <shiro:hasPermission name="MERCHANT_ENABLE">
                    /*        str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"enableMethod('"
                     + cl + "',1)\">分销启用</a> |  ";*/
                    str += "<img src='${staticSource}/resources/images/merchantinfo/fenxiaoqiyong.png' class=\"merchantinfo\" onclick=\"enableMethod('"
                        + cl + "',1)\" title='分销启用' alt='分销启用'/> | ";
                    </shiro:hasPermission>
                } else {
                    <shiro:hasPermission name="MERCHANT_ENABLE">
                    /*      str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"enableMethod('"
                     + cl + "',0)\">分销禁用</a> |  ";*/
                    str += "<img src='${staticSource}/resources/images/merchantinfo/fenxiaojinyong.png' class=\"merchantinfo\" onclick=\"enableMethod('"
                        + cl + "',0)\" title='分销禁用' alt='分销禁用'/> | ";
                    </shiro:hasPermission>
                    <shiro:hasPermission name="MERCHANT_DIS">
                    /* str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"disMethod('"
                     + cl + "')\">二级分销配置</a> |  ";*/
                    str += "<img src='${staticSource}/resources/images/merchantinfo/erjifenxiaopeizhi.png' class=\"merchantinfo\" onclick=\"disMethod('"
                        + cl + "',0)\" title='二级分销配置' alt='二级分销配置'/> | ";
                    </shiro:hasPermission>
                }
                if (data[i].iisConfWechatGzh == 0) {
                    <shiro:hasPermission name="MERCHANT_WECHATGZH">
                    /*             str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"OpenGzhMethod('"
                     + cl + "',1,'微信公众号','iisConfWechatGzh')\">微信公众号启用</a> |  ";*/
                    str += "<img src='${staticSource}/resources/images/merchantinfo/wxgzhqiyong.png' class=\"merchantinfo\" onclick=\"OpenGzhMethod('"
                        + cl + "',1,'微信公众号','iisConfWechatGzh')\" title='微信公众号启用' alt='微信公众号启用'/> | ";
                    </shiro:hasPermission>
                } else {
                    <shiro:hasPermission name="MERCHANT_WECHATGZH">
                    /*       str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"OpenGzhMethod('"
                     + cl + "',0,'微信公众号','iisConfWechatGzh')\">微信公众号禁用</a> |  ";*/
                    str += "<img src='${staticSource}/resources/images/merchantinfo/wxgzhjinyong.png' class=\"merchantinfo\" onclick=\"OpenGzhMethod('"
                        + cl + "',0,'微信公众号','iisConfWechatGzh')\" title='微信公众号禁用' alt='微信公众号禁用'/> | ";
                    </shiro:hasPermission>
                }
                if (data[i].iisConfAlipayShh == 0) {
                    <shiro:hasPermission name="MERCHANT_WECHATSHH">
                    /*    str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"OpenGzhMethod('"
                     + cl + "',1,'支付宝生活号','iisConfAlipayShh')\">支付宝生活号启用</a> |  ";*/
                    str += "<img src='${staticSource}/resources/images/merchantinfo/zfbshhqiyong.png' class=\"merchantinfo\" onclick=\"OpenGzhMethod('"
                        + cl + "',1,'支付宝生活号','iisConfAlipayShh')\" title='支付宝生活号启用' alt='支付宝生活号启用'/> | ";
                    </shiro:hasPermission>
                } else {
                    <shiro:hasPermission name="MERCHANT_WECHATSHH">
                    /*str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"OpenGzhMethod('"
                     + cl + "',0,'支付宝生活号','iisConfAlipayShh')\">支付宝生活号禁用</a> |  ";*/
                    str += "<img src='${staticSource}/resources/images/merchantinfo/zfbshhjinyong.png' class=\"merchantinfo\" onclick=\"OpenGzhMethod('"
                        + cl + "',0,'支付宝生活号','iisConfAlipayShh')\" title='支付宝生活号禁用' alt='支付宝生活号禁用'/> | ";
                    </shiro:hasPermission>
                }
                /*  }*/
                if (data[i].iisConfWechat == 0) {
                    <shiro:hasPermission name="MERCHANT_WECHANT">
                    /*       str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"OpenGzhMethod('"
                     + cl + "',1,'微信支付','iisConfWechat')\">微信支付启用</a> |  ";*/
                    str += "<img src='${staticSource}/resources/images/merchantinfo/wxzfqiyong.png' class=\"merchantinfo\" onclick=\"OpenGzhMethod('"
                        + cl + "',1,'微信支付','iisConfWechat')\" title='微信支付启用' alt='微信支付启用'/> | ";
                    </shiro:hasPermission>
                } else {
                    <shiro:hasPermission name="MERCHANT_WECHANT">
                    /*   str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"OpenGzhMethod('"
                     + cl + "',0,'微信支付','iisConfWechat')\">微信支付禁用</a> |  ";*/
                    str += "<img src='${staticSource}/resources/images/merchantinfo/wxzfjinyong.png' class=\"merchantinfo\" onclick=\"OpenGzhMethod('"
                        + cl + "',0,'微信支付','iisConfWechat')\" title='微信支付禁用' alt='微信支付禁用'/> | ";
                    </shiro:hasPermission>
                }
                if (data[i].iisConfAlipay == 0) {
                    <shiro:hasPermission name="MERCHANT_ALIPAY">
                    /*           str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"OpenGzhMethod('"
                     + cl + "',1,'支付宝支付','iisConfAlipay')\">支付宝支付启用</a> |  ";*/
                    str += "<img src='${staticSource}/resources/images/merchantinfo/zfbzfqiyong.png' class=\"merchantinfo\" onclick=\"OpenGzhMethod('"
                        + cl + "',1,'支付宝支付','iisConfAlipay')\" title='支付宝支付启用' alt='支付宝支付启用'/> | ";
                    </shiro:hasPermission>
                } else {
                    <shiro:hasPermission name="MERCHANT_ALIPAY">
                    /*         str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"OpenGzhMethod('"
                     + cl + "',0,'支付宝支付','iisConfAlipay')\">支付宝支付禁用</a> |  ";*/
                    str += "<img src='${staticSource}/resources/images/merchantinfo/zfbzfjinyong.png' class=\"merchantinfo\" onclick=\"OpenGzhMethod('"
                        + cl + "',0,'支付宝支付','iisConfAlipay')\" title='支付宝支付禁用' alt='支付宝支付禁用'/> | ";
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
            addTitle: '添加商户信息',
            addFn: showLayerMax
        }
    ;
    initBtnByForm4(initBtnConfig);


    layui.use(['form', 'laydate'], function () {
        /**
         * 时间组键
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
        showLayerMax("编辑商户", ctx + '/merchantInfo/edit?sid=' + sid);
    }
    function delMethod(sid) {

        confirmDelTip("确定要删除数据?", ctx + "/merchantInfo/delete", {checkboxId: sid});
    }
    function queryAllMethod(sid) {
        showLayerMax("商户详情", ctx + '/merchantInfo/queryAll?sid=' + sid);

    }
    function disMethod(sid) {
        showLayerMin("二级分销配置", ctx + '/merchantInfo/toDisEdit?sid=' + sid);
    }

    function enableMethod(sid, type) {
        var alertStr = "确定要开启二级分销?";
        if (type == 0) {
            alertStr = "确定要禁用二级分销?";
        }
        confirmOperTip(alertStr, ctx + "/merchantInfo/enable", {checkboxId: sid, type: type});
    }
    function contractMethod(sid, type) {
        var alertStr = "确定要签约商户?";
        if (type == 30) {
            alertStr = "确定要和商户解约?";
        }
        confirmOperTip(alertStr, ctx + "/merchantInfo/contract", {checkboxId: sid, type: type});
    }
    function OpenGzhMethod(sid, type, str, name) {
        var alertStr = "确定要启用";
        if (type == 0) {
            alertStr = "确定要禁用";
        }
        confirmOperTip(alertStr + str, ctx + "/merchantInfo/configure", {
            checkboxId: sid,
            type: type,
            str: str,
            name: name
        });
    }
    /**
     * 分配菜单权限
     */
    function dealingAuthorization(sid) {
        showLayerMedium("菜单权限配置", ctx + '/merchantInfo/menuAuthConfig?sid=' + sid);
    }
    function allotVrCommodity(sid) {
        showLayerMedium("配置视觉商品", ctx + '/commoditySku/merchantSelectList?sid=' + sid);
    }
</script>
</body>
</html>

