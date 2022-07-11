<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>编辑接口购买记录</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?23" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/buyRecord/save" method="post" id="myForm" enctype="multipart/form-data">

        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">应用用户</label>
                <div class="layui-input-inline" style="width: 115px">
                    <input type="text" name="snickName" readonly="true" id="snickName"
                           value="${buyRecordDomain.snickName}"
                           class="layui-input"/>
                </div>
                <button class="layui-btn" style="display: inline-block;float: left;" type="button" id="sel"
                        data-type="sel">选择
                </button>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">接口名称</label>
                <div class="layui-input-inline" style="width: 115px">
                    <input type="text" name="interfaceName" readonly="true" id="interfaceName"
                           value="${buyRecordDomain.interfaceName}"
                           class="layui-input"/>
                </div>
                <button class="layui-btn" style="display: inline-block;float: left;" type="button" id="selInterface"
                        data-type="sel">选择
                </button>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-col-md6" style="display:none;" id="payType">
                <label class="layui-form-label">接口收费类型</label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="interfacePayType" id="interfacePayType" disabled="disabled"
                                 value="" list="{10:按次计费,20:按单位次数计费 （识别图片张数）,30:按时间计费,40:通用时间按次收费,50:通用时间按单位次数计费}"/>

                </div>
            </div>
            <div class="layui-col-md6" style="display:none" id="iaccountType">
                <label class="layui-form-label">账户充值</label>
                <div class="layui-input-inline">
                    <select class="form-control" name="accountType" id="accountType" lay-filter="lfAccountType">
                        <option value="">请选择充值类型</option>
                        <option value="2">按次计费</option>
                        <option value="3">按时间计费</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="layui-form-item" style="display:none" id="validityType">
            <div class="layui-col-md6">
                <label class="layui-form-label">有效期类型</label>
                <div class="layui-input-inline">
                    <select class="form-control" name="ivalidityType" id="ivalidityType" lay-filter="ivalidityType">
                        <option value="">请选择有效期类型</option>
                        <option value="10">固定日期</option>
                        <option value="20">长期有效</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="layui-form-item" style="display:none" id="balance">
            <div class="layui-col-md6">
                <label class="layui-form-label">余额次数</label>
                <div class="layui-input-inline">
                    <input type="text" name="fbalance" id="fbalance" value=""
                           placeholder="余额次数..."
                           class="layui-input">
                </div>
            </div>
        </div>
        <div class="layui-form-item" id="stime" style="display:none">
            <div class="layui-col-md6">
                <label class="layui-form-label">开始时间</label>
                <div class="layui-input-inline">
                    <input type="text" name="tstartTime" id="tstartTime" disabled
                           value=""
                           class="layui-input">
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">结束时间</label>
                <div class="layui-input-inline">
                    <input type="text" name="tendTime" id="tendTime"
                           value=""
                           class="layui-input">
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">购买方式</label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="sbuyWay" id="sbuyWay"
                                 exp="datatype=\"*\" nullmsg=\"请选择购买方式\""
                                 value="${buyRecordDomain.sbuyWay}" list="{10:按次购买,20:资源包购买,30:按时间购买}"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">订单状态</label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="istatus" id="istatus"
                                 exp="datatype=\"*\" nullmsg=\"请选择订单状态\""
                                 value="${buyRecordDomain.istatus}" list="{10:待付款,20:已付款}"/>
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">购买时间</label>
                <div class="layui-input-inline">
                    <input type="text" name="tbuyTime" id="tbuyTime" <%--datatype="*" nullmsg="请输入购买时间"--%>
                           value="<fmt:formatDate value="${buyRecordDomain.tbuyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                           class="layui-input">
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">支付成功时间</label>
                <div class="layui-input-inline">
                    <input type="text" name="tpayFinishTime"
                           id="tpayFinishTime" <%--datatype="*" nullmsg="请输入支付成功时间"--%>
                           value="<fmt:formatDate value="${buyRecordDomain.tpayFinishTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                           class="layui-input">
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">购买内容</label>
            <div class="layui-input-block">
                    <textarea id="sbuyContent" name="sbuyContent" class="layui-textarea layui-form-textarea80p"
                              placeholder="（必填,请填写购买内容）">${buyRecordDomain.sbuyContent}</textarea>
            </div>
        </div>
        <div class="layui-form-item mt13">
            <div class="layui-col-md6">
                <label class="layui-form-label">支付方式</label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="ipayWay" id="ipayWay"
                                 exp="datatype=\"*\" nullmsg=\"请选择支付方式\""
                                 value="${buyRecordDomain.sbuyWay}" list="{10:微信支付,20:支付宝,30:银行卡支付}"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">支付金额</label>
                <div class="layui-input-inline">
                    <input type="text" name="fpayAmount" id="fpayAmount"
                           value="${buyRecordDomain.fpayAmount}" <%--datatype="*" nullmsg="请输入支付金额"--%>
                           class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item mt13">
            <div class="layui-col-md6">
                <label class="layui-form-label">支付流水号</label>
                <div class="layui-input-inline">
                    <input type="text" name="spayNumber" id="spayNumber"
                           value="${buyRecordDomain.spayNumber}" <%--datatype="*" nullmsg="请输入支付流水号"--%>
                           class="layui-input"/>
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">备注</label>
            <div class="layui-input-block">
                    <textarea id="sremark" name="sremark" class="layui-textarea layui-form-textarea80p"
                              placeholder="（非必填项）">${buyRecordDomain.sremark}</textarea>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                <button class="layui-btn" id="myFormSub">保存</button>
            </div>
        </div>
        <input type="hidden" id="id" name="id" value="${buyRecordDomain.id}"/>
        <input type="hidden" id="scode" name="scode" value="${buyRecordDomain.scode}"/>
        <input type="hidden" id="suserId" name="suserId" value="${buyRecordDomain.suserId}"/>
        <input type="hidden" id="suserCode" name="suserCode" value="${buyRecordDomain.suserCode}"/>
        <input type="hidden" id="sinterfaceCode" name="sinterfaceCode" value="${buyRecordDomain.sinterfaceCode}"/>
        <input type="hidden" id="interfacePayTypeTemp" name="interfacePayTypeTemp" value=""/>
    </form>
</div>


<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js" type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate'], function () {
        var laydate = layui.laydate;
        laydate.render({
            elem: '#tbuyTime' //指定元素
            , type: 'datetime'
        });
        laydate.render({
            elem: '#tpayFinishTime' //指定元素
            , type: 'datetime'
        });
        laydate.render({
            elem: '#tendTime' //指定元素
            , type: 'datetime'
        });
        var form = layui.form;
        //监听提交
        $("#myForm").Validform({
            btnSubmit: "#myFormSub",  //根据id触发
            tiptype: 3,                  //第三种方式
            showAllError: true,
            beforeSubmit: function () { //验证过后执行save方法
                var sinterfaceCode = $("#sinterfaceCode").val();
                var suserId = $("#suserId").val();
                if (isEmpty(suserId)) {
                    alertDel("请选择用户！");
                    return false;
                }
                if (isEmpty(sinterfaceCode)) {
                    alertDel("请选择接口！");
                    return false;
                }
                var sbuyContent = $("#sbuyContent").val();
                if (isEmpty(sbuyContent)) {
                    alertDel("请填写购买内容！");
                    return false;
                }
                var loadIndex = loading();
                $('#myForm').ajaxSubmit({
                    type: "post",
                    dataType: "json",
                    async: true,
                    error: function () {
                        alertDelAndReload("操作异常，请重新操作");
                    },
                    success: function (msg) {
                        //成功返回
                        closeLayer(loadIndex);//关闭加载框
                        if (msg.success) {
                            alertSuccess("操作成功", {
                                'index': index
                            }, function () {
                                parent.closeLayerAndRefresh(index);
                            });
                        } else {
                            alertDel(msg.msg);
                        }
                    }
                });
                return false;
            }
        });

        //监听审核select选择
        form.on('select(lfAccountType)', function (data) {
            //按次计费
            if (2 == data.value) {
                document.getElementById("balance").style.display = "block";
                document.getElementById("stime").style.display = "none";
                document.getElementById("validityType").style.display = "none";
                document.getElementById("stime").value = "";
            } else if (3 == data.value) {
                //按时间计费
                /* document.getElementById("stime").style.display = "block";*/
                document.getElementById("validityType").style.display = "block";
                document.getElementById("balance").style.display = "none";
                document.getElementById("fbalance").value = "";
            }
        });
        form.on('select(ivalidityType)', function (data) {
            //固定日期
            if (10 == data.value) {
                document.getElementById("stime").style.display = "block";
                document.getElementById("balance").value = "";
            } else if (20 == data.value) {
                //长期有效
                document.getElementById("balance").style.display = "none";
                document.getElementById("stime").style.display = "none";
                document.getElementById("fbalance").value = "";
                document.getElementById("tendTime").value = "";
            }
        });

    });
    $(function () {
        $("#cancelBtn").click(function () {
            parent.layer.close(index);
            return false;
        });
        $("#sel").click(function () {
            showLayerMax("选择开放平台用户", ctx + "/userInfo/selectUser");
        });
        $("#selInterface").click(function () {
            showLayerMax("选择开放平台接口", ctx + "/interfaceInfo/selectInterface");
        });
    });
    function selectSupp(userId, userCode, snickName) {
        $("#snickName").val(snickName);
        $("#suserCode").val(userCode);
        $("#suserId").val(userId);
    }
    function selectInterface(interfaceCode, interfaceName, interfacePayType) {
        $("#interfaceName").val(interfaceName);
        $("#sinterfaceCode").val(interfaceCode);
        if (!isEmpty(interfacePayType)) {
            $("#interfacePayType").val(interfacePayType);
            $("#interfacePayTypeTemp").val(interfacePayType);
            a(interfacePayType);
        }
    }
    function a(interfacePayType) {

        $("#payType").css("display", "block");
        if (10 == interfacePayType || 20 == interfacePayType) {
            document.getElementById("balance").style.display = "block";
        } else if (30 == interfacePayType) {
            /*document.getElementById("stime").style.display = "block";*/
            document.getElementById("validityType").style.display = "block";
        } else if (40 == interfacePayType || 50 == interfacePayType) {
            document.getElementById("iaccountType").style.display = "block";
        }
        var form = layui.form;
        form.render('select');
    }
</script>
</body>
</html>

