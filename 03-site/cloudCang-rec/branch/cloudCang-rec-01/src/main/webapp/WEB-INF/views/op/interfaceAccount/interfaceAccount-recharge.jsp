<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>编辑开放平台接口</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?23" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/interfaceAccount/saveRecharge" method="post" id="myForm"
          enctype="multipart/form-data">

        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">账户类型</label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="iaccountType" id="iaccountType" disabled="disabled"
                                 value="${interfaceAccount.iaccountType}"
                                 layFilter="accountType"
                                 list="{10:按次计费,20:按单位次数计费(识别图片张数),30:按时间计费,40:通用时间按次收费,50:通用时间按单位次数计费}"/>
                </div>
            </div>
            <div class="layui-col-md6" style="display:none" id="stype">
                <label class="layui-form-label">账户充值</label>
                <div class="layui-input-inline">
                    <select class="form-control" name="type" id="type" lay-filter="stype">
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
                           value="<fmt:formatDate value='${interfaceAccount.tstartTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"
                           class="layui-input">
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">结束时间</label>
                <div class="layui-input-inline">
                    <input type="text" name="tendTime" id="tendTime"
                           value="<fmt:formatDate value='${interfaceAccount.tendTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"
                           class="layui-input">
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">支付方式</label>
                <div class="layui-input-inline">
                    <cang:select type="dic" groupNo="SP000163" name="ipayWay" layFilter="ipayWay"
                                 exp="datatype=\"*\" nullmsg=\"请选择支付方式\""
                                 id="ipayWay" entire="true" value=""/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">支付金额</label>
                <div class="layui-input-inline">
                    <input type="text" name="fpayAmount" id="fpayAmount" value=""
                           class="layui-input">
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">支付流水号</label>
                <div class="layui-input-inline">
                    <input type="text" name="spayNumber" id="spayNumber" value=""
                           class="layui-input">
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">订单状态</label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="orderIstatus" id="orderIstatus" entire="true"
                                 exp="datatype=\"*\" nullmsg=\"请选择订单状态\""
                                 value="" list="{10:待付款,20:已付款}"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">购买时间</label>
                <div class="layui-input-inline">
                    <input type="text" name="tbuyTime" id="tbuyTime"
                           value="" class="layui-input">
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">支付成功时间</label>
                <div class="layui-input-inline">
                    <input type="text" name="tpayFinishTime" id="tpayFinishTime"
                           value="" class="layui-input">
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">购买方式</label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="sbuyWay" id="sbuyWay" entire="true"
                                 exp="datatype=\"*\" nullmsg=\"请选择购买方式\""
                                 value="" list="{10:按次购买,20:资源包购买,30:按时间购买}"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">购买内容</label>
            <div class="layui-input-block">
                    <textarea id="sbuyContent" name="sbuyContent" class="layui-textarea layui-form-textarea80p"
                              placeholder="（必填,请填写购买内容）"></textarea>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                <button class="layui-btn" id="myFormSub">保存</button>
            </div>
        </div>
        <input type="hidden" id="id" name="id" value="${interfaceAccount.id}"/>
    </form>
</div>


<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js" type="text/javascript"></script>
<script type="text/javascript">
    $(document).ready(function () {
        var iaccountType = $("#iaccountType").val();
        /*   if (30 == iaccountType) {
         document.getElementById("balance").style.display = "none";
         } else if (10 == iaccountType || 20 == iaccountType) {
         document.getElementById("stime").style.display = "none";
         }*/
        if (10 == iaccountType || 20 == iaccountType) {
            document.getElementById("balance").style.display = "block";
        } else if (30 == iaccountType) {
            /*document.getElementById("stime").style.display = "block";*/
            document.getElementById("validityType").style.display = "block";
        } else if (40 == iaccountType || 50 == iaccountType) {
            document.getElementById("stype").style.display = "block";
        }

    });
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate'], function () {
        var laydate = layui.laydate;

        laydate.render({
            elem: '#tstartTime' //指定元素
            , type: 'datetime'
        });
        laydate.render({
            elem: '#tendTime' //指定元素
            , type: 'datetime'
        });
        laydate.render({
            elem: '#tbuyTime' //指定元素
            , type: 'datetime'
        });
        laydate.render({
            elem: '#tpayFinishTime' //指定元素
            , type: 'datetime'
        });
        var form = layui.form;
        //监听提交
        $("#myForm").Validform({
            btnSubmit: "#myFormSub",  //根据id触发
            tiptype: 3,                  //第三种方式
            showAllError: true,
            beforeSubmit: function () { //验证过后执行save方法
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
        form.on('select(stype)', function (data) {
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
    });
</script>
</body>
</html>

