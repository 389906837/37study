<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>

<title>营销短信管理</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/style.css?v=4.1.0" rel="stylesheet">

</head>


<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-xs-12">
            <div class="ibox">
                <div class="ibox-content">
                    <div id="inverse">
                        <ul class="nav nav-tabs">
                            <li><a href="#taskObject" data-toggle="tab"><spring:message code="xx.task.object" /></a></li>
                            <li><a href="#sendTestMsg" data-toggle="tab"><spring:message code="xx.send.test.information" /></a></li>
                            <li><a href="#save" data-toggle="tab" onclick="taskValidation();"><spring:message code="xx.task.confirmation" /></a></li>
                        </ul>
                        <div class="tab-content m-t">
                            <!-- 任务对象 -->
                            <%--    <div class="tab-pane" id="taskObject">
                                    <form class="form-inline">
                                        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                            <input type="text" class="form-control m-b" name="smerchantCode"
                                                   id="smerchantCode" value="" placeholder='<spring:message code="main.merchant.number" />...'/>
                                            <input type="text" class="form-control m-b" name="merchantName"
                                                   id="merchantName" value="" placeholder='<spring:message code="main.merchant.name" />...'/>
                                        </c:if>
                                        <input type="text" class="form-control m-b" name="scode" id="scode" value=""
                                               placeholder='<spring:message code="main.member.number" />...'/>
                                        <input type="text" class="form-control m-b" name="smemberName" id="smemberName"
                                               value="" placeholder='<spring:message code="main.member.username" />...'/>
                                        <select class="form-control m-b" name="isex" id="isex">
                                            <option value=""><spring:message code='xx.please.select.gender' /></option>
                                            <option value="1"><spring:message code="main.male" /></option>
                                            <option value="0"><spring:message code="main.female" /></option>
                                        </select>&nbsp;&nbsp;
                                        <select class="form-control m-b" name="istatus" id="istatus">
                                            <option value=""><spring:message code='xx.please.select.member.status' /></option>
                                            <option value="1"><spring:message code='main.normal' /></option>
                                            <option value="2"><spring:message code='xx.logout.disabled' /></option>
                                            <option value="3"><spring:message code='xx.system.blacklist' /></option>
                                            <option value="4"><spring:message code='xx.freeze' /></option>
                                        </select>
                                        <cang:select cssClass="form-control m-b" type="list" id="iregClientType"
                                                     name="iregClientType" groupNo="SP000120"
                                                     list="{10:springMessageCode=sh.wechat.pay,20:springMessageCode=report.alipay' />支付宝,30:<spring:message code="main.wechat.public.number,40:<spring:message code="main.alipay.life,50:APP android,60:APP ios }"
                                                     entire="true" entireName='springMessageCode=xx.please.select.a.registration.platform' />
                                        <select class="form-control m-b" name="iisOrder" id="iisOrder">
                                            <option value=""><spring:message code='xx.please.choose.whether.or.not.to.order' /></option>
                                            <option value="0"><spring:message code="main.no" /></option>
                                            <option value="1"><spring:message code="main.yes" /></option>
                                        </select>
                                        <select class="form-control m-b" name="iisReplenishment" id="iisReplenishment">
                                            <option value=""><spring:message code='xx.please.choose.whether.or.not.the.replenisher' /></option>
                                            <option value="0"><spring:message code="main.no" /></option>
                                            <option value="1"><spring:message code="main.yes" /></option>
                                        </select>
                                        <select class="form-control m-b" name="iaipayOpen" id="iaipayOpen">
                                            <option value=""><spring:message code='xx.please.choose.whether.alipay.is.secret.or.not' /></option>
                                            <option value="0"><spring:message code="main.no" /></option>
                                            <option value="1"><spring:message code="main.yes" /></option>
                                        </select>
                                        <select class="form-control m-b" name="iwechatOpen" id="iwechatOpen">
                                            <option value=""><spring:message code='xx.please.choose.whether.wechat.is.not.open' /></option>
                                            <option value="0"><spring:message code="main.no" /></option>
                                            <option value="1"><spring:message code="main.yes" /></option>
                                        </select>
                                        <input type="text" class="form-control m-b" name="sregDeviceCode"
                                               id="sregDeviceCode" value="" placeholder='<spring:message code="sb.registered.device.number" />...'/>
                                        <input type="text" class="form-control m-b" name="tregisterTimeStr"
                                               id="tregisterTimeStr" value="" placeholder="<spring:message code='sb.registration.time' />..."/>
                                        <div class="layui-inline">
                                            <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i class="layui-icon">&#xe615;</i><spring:message code="main.query" /></button>
                                            <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i class="layui-icon">&#x1006;</i><spring:message code="main.clear" /></button>
                                        </div>
                                    </form>
                                    <table id="gridTable"></table>
                                    <div id="gridPager"></div>
                                </div>--%>
                            <div class="tab-pane" id="taskObject">
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
                                            <input type="text" name="smemberName" id="smemberName" value=""
                                                   placeholder='<spring:message code="main.member.username" />...'
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
                                                <option value="2"><spring:message code='xx.logout.disabled' /></option>
                                                <option value="3"><spring:message code='xx.system.blacklist' /></option>
                                                <option value="4"><spring:message code='xx.freeze' /></option>
                                            </select>
                                        </div>
                                        <div class="layui-inline" style="width: 182px">
                                            <cang:select type="list" id="iregClientType" name="iregClientType"
                                                         groupNo="SP000120"
                                                         list='{10:springMessageCode=main.wechat.pay,20:springMessageCode=main.alipay,30:springMessageCode=main.wechat.public.number,40:springMessageCode=main.alipay.life,50:APP android,60:APP ios }'
                                                         entire="true" entireName='springMessageCode=xx.index.order.statistics.registration.platform' />
                                        </div>
                                        <div class="layui-inline" style="width: 182px">
                                            <select class="form-control" name="iisOrder" id="iisOrder">
                                                <option value=""><spring:message code='index.order.statistics' /><spring:message code='xx.whether.the.first.order' /></option>
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
                                        <div class="layui-inline" style="width: 182px">
                                            <select class="form-control" name="iaipayOpen" id="iaipayOpen">
                                                <option value=""><spring:message code="main.alipay.free" /></option>
                                                <option value="0"><spring:message code="main.no" /></option>
                                                <option value="1"><spring:message code="main.yes" /></option>
                                            </select>
                                        </div>
                                        <div class="layui-inline" style="width: 182px">
                                            <select class="form-control" name="iwechatOpen" id="iwechatOpen">
                                                <option value=""><spring:message code='xx.is.wechat.confidentiality.open' /></option>
                                                <option value="0"><spring:message code="main.no" /></option>
                                                <option value="1"><spring:message code="main.yes" /></option>
                                            </select>
                                        </div>
                                        <%--<div class="layui-inline" style="width: 182px">--%>
                                        <%--<select class="form-control" name="iisEnableRecoaward" id="iisEnableRecoaward">--%>
                                        <%--<option value="">是否推荐启用</option>--%>
                                        <%--<option value="0"><spring:message code="main.no" /></option>--%>
                                        <%--<option value="1"><spring:message code="main.yes" /></option>--%>
                                        <%--</select>--%>
                                        <%--</div>--%>
                                        <div class="layui-inline" style="width: 182px">
                                            <input type="text" name="sregDeviceCode" id="sregDeviceCode" value=""
                                                   placeholder='<spring:message code='sb.registered.device.number' />...' class="layui-input">
                                        </div>
                                        <div class="layui-inline" style="width: 182px">
                                            <input type="text" name="tregisterTimeStr" id="tregisterTimeStr"
                                                   placeholder='<spring:message code="sb.registration.time" />'
                                                   class="layui-input">
                                        </div>
                                        <div class="layui-inline">
                                            <button class="layui-btn layui-btn layui-btn" id="searchBtn"
                                                    data-type="query"><i
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


                            <!-- 发送测试信息 -->
                            <div class="tab-pane" id="sendTestMsg">
                                <form class="form-horizontal">
                                    <div class="form-group">
                                        <div class="col-sm-offset-3 col-sm-4">
                                            <select class="form-control" id="ssupplierCode" name="ssupplierCode"
                                                    datatype="*" nullmsg='<spring:message code="xx.please.select.a.sms.provider" />'>
                                                <option value=""><spring:message code="xx.please.select.a.sms.provider" /></option>
                                                <c:forEach items="${supplierInfos}" varStatus="L" var="item">
                                                    <option value="${item.id}_${item.scode}">
                                                            ${item.sname}</option>
                                                </c:forEach>
                                                <%-- <option value="SUPP0007">玄武短信</option>
                                                 <option value="SUPP0008"><spring:message code='xx.xuanwu.marketing.sms' /></option>--%>
                                            </select>
                                        </div>

                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label" for="mobilestr"><spring:message code="main.mobile" />：</label>
                                        <div class="col-sm-4">
                                            <input class="form-control" id="mobilestr" name="mobilestr">
                                            <span style="color:#ff0000"><spring:message code='xx.multiple.use.separated' /></span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label" for="msgcontent"><spring:message code="xx.message.content" />：</label>
                                        <div class="col-sm-4">
                                        <textarea class="form-control" style="width:450px;height:60px;resize: none;"
                                                  id="msgcontent"
                                                  name="msgcontent"></textarea>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-sm-offset-3 col-sm-4">
                                            <button class="btn btn-warning" type="button" id="sendmsg_btn">
                                                <spring:message code="xx.send.messages" />
                                            </button>
                                            <span id="sendMsg"></span>
                                        </div>

                                    </div>

                                </form>
                            </div>

                            <!-- 任务确认 -->
                            <div class="tab-pane" id="save">
                                <form class="form-horizontal">
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label" for="mobilestr"><spring:message code="xx.task.object" />:</label>
                                        <div class="col-sm-4">
                                            <span id="taskObj"></span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label" for="mobilestr"><spring:message code="xx.send.content" />:</label>
                                        <div class="col-sm-4">
                                            <span id="sendContent"></span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label" for="mobilestr"><spring:message code="xx.testing.object" />:</label>
                                        <div class="col-sm-4">
                                            <span id="testMobile"></span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-sm-offset-3 col-sm-4">
                                            <button class="btn btn-warning" type="button" id="generated_task_btn">
                                                <spring:message code="xx.build.task" />
                                            </button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <%-- <ul class="pager wizard">
                                 <li class="previous first" style="display:none;"><a href="#">First</a></li>
                                 <li class="previous"><a href="#"><spring:message code='sp.brand.return' /></a></li>
                                 <li class="next last" style="display:none;"><a href="#">Last</a></li>
                                 <li class="next"><a href="#"><spring:message code='xx.next.step' /></a></li>
                             </ul>--%>
                        </div>
                    </div>
                </div>
            </div>


        </div>

    </div>

    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="icon-double-angle-up icon-only bigger-110"></i>
    </a>
</div>


<script type="text/javascript" src="${staticSource}/resources/js/jquery.bootstrap.wizard.min.js"></script>

<script>
    $(document).ready(function () {
        $('#inverse').bootstrapWizard();
    });
</script>

<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
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
        {name: 'smobile', index: 'smobile', hidden: true},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode', label: '<spring:message code="main.merchant.number" />', index: 'SMERCHANT_CODE', sortable: false, width: 150},
        {name: 'merchantName', label: '<spring:message code="main.merchant.name" />', index: 'MERCHANTNAME', sortable: false, width: 240},
        </c:if>
        {name: 'scode', index: 'SCODE', label: '<spring:message code="main.member.number" />', sortable: false, width: 140},
        {name: 'smemberName', index: 'SMEMBER_NAME', label: '<spring:message code="main.member.username" />', sortable: false, width: 120},
        {name: 'snickName', index: 'SNICKNAME', label: "<spring:message code='xx.member.nickname' />", sortable: false, width: 120},
        {
            name: 'isex', index: 'ISEX', label: '<spring:message code="main.gender" />', editable: true,
            formatter: "select", editoptions: {value: '1:<spring:message code="main.male" />;0:<spring:message code="main.female" />'}, sortable: false, width: 70
        },
        {
            name: 'istatus', index: 'ISTATUS', label: '<spring:message code="hy.member.status" />', editable: true,
            formatter: "select", editoptions: {value: '1:<spring:message code="main.normal" />;2:<spring:message code='xx.logout.disabled' />;3:<spring:message code='xx.system.blacklist' />;4:<spring:message code='xx.freeze' />'}, sortable: false, width: 80
        },
        {
            name: 'iregClientType',
            index: 'IREG_CLIENT_TYPE',
            label: "<spring:message code='xx.registration.platform' />",
            editable: true,
            formatter: "select",
            editoptions: {value: '10:<spring:message code="main.wechat.pay" />;20:<spring:message code="main.alipay" />;30:<spring:message code="main.wechat.public.number" />;40:<spring:message code="main.alipay.life" />;50:APP android;60:APP ios '},
            sortable: false, width: 80
        },
        {
            name: 'iisOrder', index: 'IIS_ORDER', label: "<spring:message code='xx.is.it.the.first' />", editable: true,
            formatter: "select", editoptions: {value: '0:<spring:message code="main.no" />;1:<spring:message code="main.yes" />'}, sortable: false, width: 80
        },
        {
            name: 'iisReplenishment', index: 'IIS_REPLENISHMENT', label: '<spring:message code="sys.is.replenisher" />', editable: true,
            formatter: "select", editoptions: {value: '0:<spring:message code="main.no" />;1:<spring:message code="main.yes" />'}, sortable: false, width: 80
        },
        {
            name: 'iaipayOpen', index: 'IAIPAY_OPEN', label: '<spring:message code="main.alipay.free" />', editable: true,
            formatter: "select", editoptions: {value: '0:<spring:message code="main.no" />;1:<spring:message code="main.yes" />'}, sortable: false, width: 120
        },
        {
            name: 'iwechatOpen', index: 'IWECHAT_OPEN', label: '<spring:message code="main.wechat.free" />', editable: true,
            formatter: "select", editoptions: {value: '0:<spring:message code="main.no" />;1:<spring:message code="main.yes" />'}, sortable: false, width: 120
        },
        {name: 'sregDeviceCode', index: 'SREG_DEVICE_CODE', label: '<spring:message code="sb.registered.device.number" />', sortable: false, width: 100},
        {
            name: 'tregisterTime', index: 'TREGISTER_TIME', label: '<spring:message code="sb.registration.time" />', formatter: function (value) {
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }
        }
    ];
    $(function () {
        var config = {
            sortname: "A.ISTATUS=1",
            sortorder: "desc",
            shrinkToFit: false,
            multiselect: false,
            autoScroll: true
        };
        initTable(ctx + "/memberInfo/queryData", colModel, '', config);
    });
    initBtnByForm2();

</script>
<script>
    $(function () {
        $('.btn-cx').on('click', function () {
            resetReloadGrid();
        })

    });

    // 清除查询样式
    $(function () {
        $('.btn-qc').on('click', function () {
            $("#smerchantCode").val("").focus(); // 清空并获得焦点
            $("#merchantName").val("").focus();
            $("#scode").val("").focus();
            $("#smemberName").val("").focus();
            $("#isex").val("").focus();
            $("#istatus").val("").focus();
            $("#iregClientType").val("").focus();
            $("#iisOrder").val("").focus();
            $("#iisReplenishment").val("").focus();
            $("#iaipayOpen").val("").focus();
            $("#iwechatOpen").val("").focus();
            $("#sregDeviceCode").val("").focus();
            $("#tregisterTimeStr").val("").focus();
        })

    })
</script>


<script>
    $(function () {
        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        $('#sendmsg_btn').on('click', function () {
            //发送短信
            if (isEmpty($("#ssupplierCode").val())) {
                alertDel("<spring:message code='xx.please.select.a.sms.provider' />");
                return false;
            }
            if (isEmpty($("#mobilestr").val())) {
                alertDel("<spring:message code='xx.phone.number.can.not.be.blank' />");
                return false;
            }
            if (isEmpty($("#msgcontent").val())) {
                alertDel("<spring:message code='xx.message.content.cannot.be.empty' />");
                return false;
            }
            $('#mobilestr').focus();

            $.ajax({
                type: "post",
                url: ctx + "/salesmsgmain/sendMsg",
                data: {
                    ssupplierCode: $("#ssupplierCode").val(),
                    testMobile: $("#mobilestr").val(),
                    msgContent: $("#msgcontent").val()
                },
                async: true,
                dataType: "json",
                success: function (msg) {
                    if (msg.success) {
                        document.getElementById("testMobile").innerHTML = $("#mobilestr").val();
                        alertInfo("<spring:message code='main.success' />", {
                            'index': index
                        });
                    } else {
                        alertDel(msg.msg);
                    }
                }
            });
        });
        //生成任务
        $("#generated_task_btn").click(function () {
            var a = $("#testMobile").html();
            $.ajax({
                type: "post",
                url: ctx + "/salesmsgmain/save",
                data: {
                    ssupplierCode: $("#ssupplierCode").val(),
                    testMobile: $("#testMobile").html(),
                    msgContent: $("#sendContent").html(),
                    sendMsgIds: $("#taskObj").html()
                },
                async: true,
                dataType: "json",
                success: function (msg) {
                    if (msg.success) {
                        alertSuccess("<spring:message code='main.success' />", {
                            'index': index
                        }, function () {
                            parent.closeLayerAndRefresh(index);
                        });
                    } else {
                        alertDel(msg.msg);
                    }
                }
            });
        });

    });
    /**
     * 关闭并加载更新表格
     */
    function closeLayerAndRefresh(index) {
        layer.close(index);
        reloadGrid(true);
    }
    function taskValidation() {
        var params = getFormValue();
        $.ajax({
            type: "post",
            url: ctx + "/salesmsgmain/selectParam?t=" + new Date() + params,
            async: true,
            dataType: "json",
            success: function (msg) {
                if (msg.success) {
                    document.getElementById("taskObj").innerHTML = msg.data;
                } else {
                    document.getElementById("taskObj").innerHTML = "";
                }
            }
        });
        document.getElementById("sendContent").innerHTML = $("#msgcontent").val();
        /*    var data = $("#gridTable").jqGrid('getRowData');
         var cl = "";
         for (var i = 0; i < data.length; i++) {
         cl = cl + data[i].smobile + ",";
         }
         document.getElementById("taskObj").innerHTML = cl;*/
        /* var sid = $("#gridTable").jqGrid('getGridParam', 'selarrrow');
         document.getElementById("taskObj").innerHTML = sid.join(",");*/
    }
</script>
</body>
</html>



