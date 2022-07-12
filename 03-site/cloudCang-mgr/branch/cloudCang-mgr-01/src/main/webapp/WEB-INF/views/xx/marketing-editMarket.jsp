<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>

<title>营销短信管理</title>
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
                        <ul class="nav nav-tabs" >
                            <li><a href="#taskObject" data-toggle="tab">任务对象</a></li>
                            <li><a href="#sendTestMsg" data-toggle="tab">发送测试信息</a></li>
                            <li><a href="#save" data-toggle="tab">任务确认</a></li>
                        </ul>
                        <div class="tab-content m-t">
                            <!-- 任务对象 -->
                            <div class="tab-pane" id="taskObject">
                                <form class="form-inline" id="searchForm">
                                    <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                        <input type="text" class="form-control m-b" name="smerchantCode" id="smerchantCode" value="" placeholder="商户编号..." />
                                        <input type="text" class="form-control m-b" name="merchantName" id="merchantName" value="" placeholder="商户名称..." />
                                    </c:if>
                                    <input type="text" class="form-control m-b" name="scode" id="scode" value="" placeholder="会员编号..." />
                                    <input type="text" class="form-control m-b" name="smemberName" id="smemberName" value="" placeholder="会员用户名..." />
                                    <select class="form-control m-b" name="isex" id="isex">
                                        <option value="">请选择性别</option>
                                        <option value="1">男</option>
                                        <option value="0">女</option>
                                    </select>&nbsp;&nbsp;
                                    <select class="form-control m-b" name="istatus" id="istatus">
                                        <option value="">请选择会员状态</option>
                                        <option value="1">正常</option>
                                        <option value="2">注销停用</option>
                                        <option value="3">系统黑名单</option>
                                        <option value="4">冻结</option>
                                    </select>
                                    <cang:select cssClass="form-control m-b" type="list" id="iregClientType" name="iregClientType" groupNo="SP000120"
                                                 list="{10:微信支付,20:支付宝,30:微信公众号,40:支付宝生活号,50:APP android,60:APP ios }"
                                                 entire="true" entireName="请选择注册平台"/>
                                    <select class="form-control m-b" name="iisOrder" id="iisOrder">
                                        <option value="">请选择是否首单</option>
                                        <option value="0">否</option>
                                        <option value="1">是</option>
                                    </select>
                                    <select class="form-control m-b" name="iisReplenishment" id="iisReplenishment">
                                        <option value="">请选择是否补货员</option>
                                        <option value="0">否</option>
                                        <option value="1">是</option>
                                    </select>
                                    <select class="form-control m-b" name="iaipayOpen" id="iaipayOpen">
                                        <option value="">请选择支付宝免密是否开通</option>
                                        <option value="0">否</option>
                                        <option value="1">是</option>
                                    </select>
                                    <select class="form-control m-b" name="iwechatOpen" id="iwechatOpen">
                                        <option value="">请选择微信免密是否开通</option>
                                        <option value="0">否</option>
                                        <option value="1">是</option>
                                    </select>
                                    <input type="text" class="form-control m-b" name="sregDeviceCode" id="sregDeviceCode" value="" placeholder="注册设备编号..." />
                                    <input type="text" class="form-control m-b" name="tregisterTimeStr" id="tregisterTimeStr" value="" placeholder="注册时间..." />
                                    <button class="btn btn-warning m-b" type="button"id="search_form">
                                        查询
                                    </button>
                                    <button class="btn btn-danger m-b" type="button"id="reset">
                                        清除
                                    </button>
                                </form>
                                <table id="gridTable"></table>
                                <div id="gridPager"></div>
                            </div>


                            <!-- 发送测试信息 -->
                            <div class="tab-pane" id="sendTestMsg">
                                <form class="form-horizontal">
                                    <div class="form-group">
                                        <div class="col-sm-offset-3 col-sm-4">
                                            <select class="form-control" id="ssupplierCode" name="ssupplierCode" datatype="*" nullmsg="请选择短信供应商" >
                                                <option value="">请选择短信供应商</option>
                                                <%--<c:forEach items="${list}" varStatus="L" var="item">--%>
                                                <%--<option value="${item.id}"--%>
                                                <%--(${item.scode})${item.sname}</option>--%>
                                                <%--</c:forEach>--%>
                                                <option value="SUPP0007">玄武短信</option>
                                                <option value="SUPP0008">玄武营销短信</option>
                                            </select>
                                        </div>

                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label" for="mobilestr">手机号码：</label>
                                        <div class="col-sm-4">
                                            <input class="form-control" id="mobilestr" name="mobilestr">
                                            <span style="color:#ff0000">多个用,隔开</span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label" for="msgcontent">消息内容：</label>
                                        <div class="col-sm-4">
                                        <textarea class="form-control" style="width:450px;height:60px;resize: none;" id="msgcontent"
                                                  name="msgcontent"></textarea>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-sm-offset-3 col-sm-4">
                                            <button class="btn btn-warning" type="button" id="sendmsg_btn">
                                                发送短信
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
                                        <label class="col-sm-3 control-label" for="mobilestr">任务对象:</label>
                                        <div class="col-sm-4">
                                            <span id="taskObj"></span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label" for="mobilestr">发送内容:</label>
                                        <div class="col-sm-4">
                                            <span id="sendContent"></span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label" for="mobilestr">测试对象:</label>
                                        <div class="col-sm-4">
                                            <span id="testMobile"></span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-sm-offset-3 col-sm-4">
                                            <button class="btn btn-warning" type="button" id="generated_task_btn">
                                                生成任务
                                            </button>
                                        </div>
                                    </div>

                                </form>
                            </div>


                            <ul class="pager wizard">
                                <li class="previous first" style="display:none;"><a href="#">First</a></li>
                                <li class="previous"><a href="#">返回</a></li>
                                <li class="next last" style="display:none;"><a href="#">Last</a></li>
                                <li class="next"><a href="#">下一步</a></li>
                            </ul>
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

<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
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
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode', label: "商户编号", index: 'SMERCHANT_CODE', sortable: false, width: 150},
        {name: 'merchantName', label: "商户名称", index: 'MERCHANTNAME', sortable: false, width: 240},
        </c:if>
        {name: 'scode', index: 'SCODE', label: "会员编号", sortable: false, width: 140},
        {name: 'smemberName', index: 'SMEMBER_NAME', label: "会员用户名", sortable: false, width: 120},
        {name: 'snickName', index: 'SNICKNAME', label: "会员昵称", sortable: false, width: 120},
        {
            name: 'isex', index: 'ISEX', label: "性别", editable: true,
            formatter: "select", editoptions: {value: '1:男;0:女'}, sortable: false, width: 70
        },
        {
            name: 'istatus', index: 'ISTATUS', label: "会员状态", editable: true,
            formatter: "select", editoptions: {value: '1:正常;2:注销停用;3:系统黑名单;4:冻结'}, sortable: false, width: 80
        },
        {
            name: 'iregClientType',
            index: 'IREG_CLIENT_TYPE',
            label: "注册平台",
            editable: true,
            formatter: "select",
            editoptions: {value: '10:微信支付;20:支付宝;30:微信公众号;40:支付宝生活号;50:APP android;60:APP ios '},
            sortable: false, width: 80
        },
        {
            name: 'iisOrder', index: 'IIS_ORDER', label: "是否已首单", editable: true,
            formatter: "select", editoptions: {value: '0:否;1:是'}, sortable: false, width: 80
        },
        {
            name: 'iisReplenishment', index: 'IIS_REPLENISHMENT', label: "是否补货员", editable: true,
            formatter: "select", editoptions: {value: '0:否;1:是'}, sortable: false, width: 80
        },
        {
            name: 'iaipayOpen', index: 'IAIPAY_OPEN', label: "支付宝免密是否开通", editable: true,
            formatter: "select", editoptions: {value: '0:否;1:是'}, sortable: false, width: 120
        },
        {
            name: 'iwechatOpen', index: 'IWECHAT_OPEN', label: "微信支付免密是否开通", editable: true,
            formatter: "select", editoptions: {value: '0:否;1:是'}, sortable: false, width: 120
        },
        {name: 'sregDeviceCode', index: 'SREG_DEVICE_CODE', label: "注册设备编号", sortable: false, width: 100},
        {
            name: 'tregisterTime', index: 'TREGISTER_TIME', label: "注册时间", formatter: function (value) {
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }
        },
        {name: 'process', title: false, index: 'process', label: "操作", sortable: false, width: 80}
    ];
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
            var data = $("#gridTable").jqGrid('getRowData');

            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = "";
                <shiro:hasPermission name="MERCHANT_DELETE">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='删除会员' alt='删除会员'  /> | ";
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
            sortname: "A.ISTATUS=1",
            sortorder: "desc",
            shrinkToFit: false,
            autoScroll: true,
        };
        initTable(ctx + "/memberInfo/queryData", colModel, tableCallBack, config);
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

    function delMethod(sid) {
        confirmDelTip("确定要删除数据?", ctx + "/memberInfo/delete", {checkboxId: sid});
    }

</script>
<script>
    $(function () {
        $('.btn-cx').on('click',function () {
            resetReloadGrid();
        })

    })


    // 清除查询样式
    $(function () {
        $('.btn-qc').on('click',function () {
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
        $('#sendmsg_btn').on('click',function () {
            alert("111");
            //发送短信
            if(isEmpty($("#ssupplierCode").val())) {
                alertDel("请选择短信供应商");
                return false;
            }
            if(isEmpty($("#mobilestr").val())) {
                alertDel("手机号码不能为空");
                return false;
            }
            return true;
            if(isEmpty($("#msgcontent").val())) {
                alertDel("消息内容不能为空");
                return false;
            }
            return true;
            $('#mobilestr').focus();
            $.ajax({
                type: "post",
                url: ctx + "/salesmsgmain/sendMsg",
                data:{
                    ssupplierCode:$("#ssupplierCode").val(),
                    testMobile:$("#mobilestr").val(),
                    msgContent:$("#msgcontent").val()
                },
                async: true,
                dataType: "json",
                success: function(msg){
                }
            });
        })


        //生成任务
        $("#generated_task_btn").click(function() {
            $.ajax({
                type: "post",
                url: ctx + "/salesmsgmain/save",
                data:{
                    ssupplierCode:$("#ssupplierCode").val(),
                    testMobile:$("#mobilestr").val(),
                    msgContent:$("#msgcontent").val()
                },
                async: true,
                dataType: "json",
                success: function (msg) {
                }
            });
        });

    })
</script>
</body>
</html>



