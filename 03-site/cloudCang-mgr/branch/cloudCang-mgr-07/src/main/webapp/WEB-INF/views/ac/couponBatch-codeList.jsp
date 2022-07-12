<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>优惠券券码生成列表</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>券码生成</h5>
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
                                <input type="text" name="sbatchCode" id="sbatchCode" value="" placeholder="发放批次编号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <cang:select type="list" list="{10:现金券,20:满减券,30:抵扣券,40:商品券}" id="icouponType"
                                             name="icouponType" entire="true" entireName="请选择券类型"/>
                            </div>
                            <div class="layui-inline">
                                <label class="layui-form-label" style="width: 80px;margin-top: 10px">券面值</label>
                                <div class="layui-inline" style="width: 70px;margin-top: 10px">
                                    <input type="text" name="fcouponValueFrom" id="fcouponValueFrom" autocomplete="off"
                                           class="layui-input">
                                </div>
                                <div class="layui-form-mid" style="margin-top: 10px">~</div>
                                <div class="layui-inline" style="width: 70px;margin-top: 10px">
                                    <input type="text" name="fcouponValueTo" id="fcouponValueTo" autocomplete="off"
                                           class="layui-input">
                                </div>
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <cang:select type="list" list="{10:草稿,11:待审核,20:审核通过,21:审核拒绝}" id="istatus"
                                             name="istatus" entire="true" entireName="请选择状态"/>
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <cang:select type="list" list="{0:优惠券,1:优惠券码,2:营销活动券}" id="itype" name="itype"
                                             entire="true" entireName="请选择下发类型"/>
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="ibatchNumber" id="ibatchNumber" value="" placeholder="发放数量..."
                                       class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <cang:select type="list" list="{10:只有一次,20:每月一次}" id="icouponCodeUseType" name="icouponCodeUseType"
                                             entire="true" entireName="请选择券码使用类型"/>
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="dcouponEffectiveDateStr" id="dcouponEffectiveDateStr"
                                       placeholder="券生效时间" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="dcouponExpiryDateStr" id="dcouponExpiryDateStr"
                                       placeholder="券失效时间" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="texStarttimeStr" id="texStarttimeStr"
                                       placeholder="兑券有效开始时间" class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="texEndtimeStr" id="texEndtimeStr"
                                       placeholder="兑券有效截止时间" class="layui-input">
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

                        <div class="layui-form-item">
                            <shiro:hasPermission name="COUPONBATCH_ADD">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" style="margin-left: 0px;"
                                        data-type="add"><i class="layui-icon"></i>添加
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="COUPONBATCH_DELETE">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i>删除
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

    layui.use('laydate', function () {
        var laydate = layui.laydate;

        //执行一个laydate实例
        laydate.render({
            elem: '#dcouponEffectiveDateStr', //指定元素,券生效日期
            range: true,
            type: 'date'
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#dcouponExpiryDateStr', //指定元素,券失效日期
            range: true,
            type: 'date'
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#texStarttimeStr', //指定元素,兑券有效开始日期
            range: true,
            type: 'date'
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#texEndtimeStr', //指定元素,兑券有效截止日期
            range: true,
            type: 'date'
        });
    });

    // 渲染数据
    var colModel = [{name: 'id', index: 'id', hidden: true},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode', label: "商户编号", index: 'smerchant_code'},
        {name: 'merchantName', label: "商户名称", index: 'merchantName'},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'smerchantCode', index: 'smerchant_code', hidden: true},
        </c:if>
        {name: 'sbatchCode', index: 'sbatchCode', label: "发放批次编号", sortable: true, width: 120},
        {
            name: 'icouponType', index: 'icouponType', label: "券类型", editable: true,
            formatter: "select", editoptions: {value: '10:现金券;20:满减券;30:抵扣券;40:商品券'}, sortable: false, width: 70
        },
        {name: 'fcouponValue', index: 'fcouponValue', label: "券面值(元)", sortable: false, width: 90},
        {
            name: 'istatus', index: 'istatus', label: "状态", editable: true,
            formatter: "select", editoptions: {value: '10:草稿;11:待审核;20:审核通过;21:审核拒绝'}, sortable: false, width: 70
        },
        {
            name: 'itype', index: 'itype', label: "下发类型", editable: true,
            formatter: "select", editoptions: {value: '0:优惠券;1:优惠券码;2:营销活动券'}, sortable: false, width: 90
        },
        {name: 'ibatchNumber', index: 'ibatchNumber', label: "发放数量(张)", sortable: true, width: 130},
        {
            name: 'icouponCodeUseType', index: 'icouponCodeUseType', label: "券码使用类型", editable: true,
            formatter: "select", editoptions: {value: '10:只有一次;20:每月一次'}, sortable: false
        },
        {
            name: 'dcouponEffectiveDate', index: 'dcouponEffectiveDate', label: "券生效日期", formatter: function (value) {
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }, width: 170
        },
        {
            name: 'dcouponExpiryDate', index: 'dcouponExpiryDate', label: "券失效日期", formatter: function (value) {
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }, width: 170
        },

        {
            name: 'texStarttime', index: 'TEX_STARTTIME', label: "兑券有效开始时间", formatter: function (value) {
            if (isEmpty(value)) {
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }, width: 170
        },
        {
            name: 'texEndtime', index: 'TEX_ENDTIME', label: "兑券有效截止时间", formatter: function (value) {
            if (isEmpty(value)) {
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }, width: 170
        },

        {name: 'scouponValidityValue', index: 'scouponValidityValue', label: "有效天数", sortable: true, width: 100},

        {name: 'scouponInstruction', index: 'scouponInstruction', label: "券说明", sortable: false, width: 100},
        {name: 'sbriefDesc', index: 'sbriefDesc', label: "券简述", sortable: false, width: 100},
        {name: 'ssubmitOperatorId', index: 'ssubmitOperatorId', label: "提交人", sortable: false, width: 70},
        {
            name: 'tsubmitDatetime', index: 'tsubmitDatetime', label: "提交时间", formatter: function (value) {
            if (isEmpty(value)) {
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }, width: 170
        },
        {name: 'sauditOperatorName', index: 'sauditOperatorName', label: "审核人", sortable: false, width: 70},
        {
            name: 'tauditDatetime', index: 'tauditDatetime', label: "审核时间", formatter: function (value) {
            if (isEmpty(value)) {
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }, width: 170
        },
        {name: 'process', index: 'process', label: "操作", sortable: false, width: 190}
    ];
    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            var data = $("#gridTable").jqGrid('getRowData');
            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = '';
                <shiro:hasPermission name="COUPONBATCH_LOOK">
                str += "<img src='${staticSource}/resources/images/oper_icon/view.png' class=\"oper_icon\" onclick=\"queryDetailsMethod('"
                    + cl + "')\" title='批量发券详情' alt='批量发券详情'  /> | ";
                </shiro:hasPermission>
                if (data[i].istatus != 11) {
                    <shiro:hasPermission name="COUPONBATCH_EDIT">
                    str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                        + cl + "')\" title='编辑' alt='编辑'  /> | ";
                    </shiro:hasPermission>
                }
                <shiro:hasPermission name="COUPONBATCH_DELETE">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='删除' alt='删除'  /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="COUPONBATCH_AUDIT">
                str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"arraignMethod('"
                    + cl + "')\">提审</a> |  ";
                </shiro:hasPermission>
                <shiro:hasPermission name="COUPONBATCH_REVIEW">
                str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"reviewMethod('"
                    + cl + "')\">审核</a> |  ";
                </shiro:hasPermission>
                $("#gridTable").jqGrid('setRowData',
                    cl, {
                        process: str.substr(0, str.lastIndexOf(" | "))
                    });
            }
        }, 0);
    }
    $(function () {
        var config = {
            sortname: "TEX_STARTTIME",
            sortorder: "desc"
        };
        initTable(ctx + "/couponBatch/queryCodeData", colModel, tableCallBack, config);
    });
    //初始化form中按钮
    var initBtnConfig = {
            addUrl: ctx + '/couponBatch/toEdit',
            deleteUrl: ctx + "/couponBatch/delete",
            addTitle: '添加批量发券信息',
            addFn: showLayerMax
        }
    ;
    initBtnByForm4(initBtnConfig);

    function editMethod(sid) {
        showLayerMax("编辑批量发券信息", ctx + '/couponBatch/toEdit?sid=' + sid);
    }
    function delMethod(checkboxId) {
        confirmDelTip("确定要删除数据?", ctx + "/couponBatch/delete", {checkboxId: checkboxId});
    }
    function queryDetailsMethod(sid) {
        showLayer("优惠券批量下发详情", ctx + '/couponBatch/queryCouponDetails?sid=' + sid, {area: ['70%', '100%']});
    }
    function arraignMethod(couponBatchId) {
        confirmOperTip("提审操作后将不可编辑,您确认要提审吗?", ctx + "/couponBatch/arraign", {couponBatchId: couponBatchId});
    }
    function reviewMethod(rid) {
        showLayer("审核", ctx + '/couponBatch/reviewEdit?rid=' + rid, {area: ['30%', '60%']});
    }

</script>
</body>
</html>

