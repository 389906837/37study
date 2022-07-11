<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>编辑开放平台API服务器</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?23" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/serverModel/save" method="post" id="myForm" enctype="multipart/form-data">

        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">模型地址</label>
                <div class="layui-input-inline">
                    <input type="text" name="smodelAddress" id="smodelAddress" datatype="*" nullmsg="请输入模型地址"
                           value="${serverModel.smodelAddress}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">模型类型</label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="sfileType" id="sfileType"
                                 exp="datatype=\"*\" nullmsg=\"请选择文件类型\""
                                 value="${serverModel.sfileType}" list="{10:图像识别服务器}"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">识别类别数量</label>
                <div class="layui-input-inline">
                    <input type="text" name="icategoryNum" id="icategoryNum" datatype="*" nullmsg="请输入识别类别数量"
                           value="${serverModel.icategoryNum}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">是否TINY模型</label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="istiny" id="istiny"
                                 exp="datatype=\"*\" nullmsg=\"请选择文件是否TINY模型\""
                                 value="${serverModel.istiny}" list="{0:否,1:是}"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">识别置信度</label>
                <div class="layui-input-inline">
                    <input type="text" name="fvisThresh" id="fvisThresh" datatype="*" nullmsg="请输入识别置信度"
                           value="${serverModel.fvisThresh}" class="layui-input"/>
                </div>
            </div>
        </div>


        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">适用范围类型</label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="irangeType" id="irangeType" layFilter="irangeType"
                                 exp="datatype=\"*\" nullmsg=\"请选择适用范围类型\"" entire="true" entireName="请选择适用范围类型"
                                 value="${serverModel.irangeType}" list="{10:通用,20:专用,30:多商户}"/>
                </div>
            </div>
            <c:if test="${serverModel.irangeType eq 20}">
            <div class="layui-col-md6" name="rangeList" id="rangeList" mt13>
                </c:if>
                <c:if test="${serverModel.irangeType  ne 20}">
                <div class="layui-col-md6" name="rangeList" id="rangeList" style="display: none;" mt13>
                    </c:if>
                    <label class="layui-form-label">专用商户</label>
                    <div class="layui-input-inline" style="width: 115px">
                        <input type="text" name="merchantName" readonly="true" id="merchantName"
                               value="${merchantName}"
                               class="layui-input"/>
                    </div>
                    <button class="layui-btn" id="btn1" style="float: left;" type="button"
                            data-type="sel">选择
                    </button>
                </div>
            </div>
            <%--  <div class="layui-form-item">

            <div class="layui-col-md6" name="rangeList" id="rangeList" style="display: none;" mt13>
                <label class="layui-form-label">专用商户</label>
                <div class="layui-input-inline" style="width: 115px">
                    <input type="text" name="merchantName" readonly="true" id="merchantName"
                           value=""
                           class="layui-input"/>
                </div>
                <button class="layui-btn" id="btn1" style="float: left;" type="button"
                        data-type="sel">选择
                </button>
            </div>
        </div>--%>
            <div class="layui-form-item">
                <div class="layui-col-md6" style="display: none;" id="selectMultipleMer">
                    <table class="layui-table" id="merchantTable" style="display:none;">
                        <colgroup>
                            <col width="100">
                            <col width="150">
                            <col>
                        </colgroup>
                        <thead>
                        <tr>
                            <th>商户编号</th>
                            <th>商户名称</th>
                        </tr>
                        </thead>
                        <tbody id="merchantBody">
                        </tbody>
                    </table>
                    <%--  <input type="hidden" id="smerList" name="smerList" value="${operator.smerList}"/>
                      <input type="hidden" id="commodityCodes" name="commodityCodes" value="${operator.sgroupDecList}"/>--%>
                    <button class="layui-btn layui-btn-primary" id="selectMerchant"
                            style="margin-left: 180px;margin-top: 10px;" type="button" <%--<c:if
                        test="${serverModel.irangeType ne 30}">  style="display: none;" </c:if>--%>>选择商户
                    </button>
                </div>
            </div>
            <div class="layui-form-item mt13">
                <label class="layui-form-label">备注</label>
                <div class="layui-input-block">
                    <textarea id="sremark" name="sremark" class="layui-textarea layui-form-textarea80p"
                              placeholder="（非必填项）">${serverModel.sremark}</textarea>
                </div>
            </div>
            <div class="layui-form-item fixed-bottom">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                    <button class="layui-btn" id="myFormSub">保存</button>
                </div>
            </div>
            <input type="hidden" id="id" name="id" value="${serverModel.id}"/>
            <input type="hidden" id="scode" name="scode" value="${serverModel.scode}"/>
            <input type="hidden" id="srangeList" name="srangeList" value="${serverModel.srangeList}"/>
            <input type="hidden" id="merchantIds" name="merchantIds" value=""/>
    </form>
</div>

<script id="merchant_table_tmpl" type="text/x-jsrender">
{{for data}}
    <tr>
        <td>{{:merchantCode}}</td>
        <td>{{:merchantName}}</td>
    </tr>
{{/for}}


</script>
<!-- layerUI-->
<script src="${staticSource}/resources/js/jsrender.min.js" type="text/javascript"></script>
<script src="${staticSource}/resources/js/jsrender.converters.js" type="text/javascript"></script>
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js" type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate'], function () {
        var form = layui.form;
        //监听提交
        $("#myForm").Validform({
            btnSubmit: "#myFormSub",  //根据id触发
            tiptype: 3,                  //第三种方式
            showAllError: true,
            beforeSubmit: function () { //验证过后执行save方法
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
        form.on('select(irangeType)', function (data) {

            if (20 == data.value) {
                $("#rangeList").css("display", "block");
                $("#selectMultipleMer").css("display", "none");
                $("#srangeList").val("");
                $("#merchantIds").val("");
                $("#merchantName").val("");
                hideMerchantTable();

            } else if (30 == data.value) {
                $("#selectMultipleMer").css("display", "block");
                $("#rangeList").css("display", "none");
                $("#srangeList").val("");
                $("#merchantName").val("");
            } else {
                $("#selectMultipleMer").css("display", "none");
                $("#rangeList").css("display", "none");
                $("#merchantName").val("");
                $("#srangeList").val("");
                $("#merchantIds").val("");
                hideMerchantTable();
            }
        });
        $(function () {
            $("#btn1").click(function () {
                showLayerMax("选择专用商户", ctx + "/merchantInfo/selectMerchant");
            });
            $("#cancelBtn").click(function () {
                parent.layer.close(index);
                return false;
            });
        });

    });

    $(function () {
        $("#cancelBtn").click(function () {
            parent.layer.close(index);
            return false;
        });
        //选择商户
        $("#selectMerchant").click(function () {
            var merchantIds = $("#srangeList").val();
            /*    var merchantCodes = $("#srangeList").val();*/
            if (!isEmpty(merchantIds)) {
                showLayer("选择商户", ctx + "/merchantInfo/selectMultipleMerchant?commodityIds=" + merchantIds, {area: ['90%', '80%']});
            } else {
                showLayer("选择商户", ctx + "/merchantInfo/selectMultipleMerchant", {area: ['90%', '80%']});
            }
        });
        initMerchantTable();
    });
    //隐藏表格 商品
    function hideMerchantTable() {
        $("#merchantBody").html("");
        $("#merchantTable").hide();
    }
    //初始化商品表格
    function initMerchantTable() {
        var merchantIds = $("#srangeList").val();
        var irangeType = $("#irangeType").val();

        if (isEmpty(merchantIds) || 30 != irangeType) {//没有选择商户
            hideMerchantTable();
            return;
        }
        $.ajax({
            url: ctx + '/merchantInfo/getMerchantByIds',
            type: 'POST', //GET
            async: false,    //或false,是否异步
            data: {merchantIds: merchantIds},
            dataType: 'json',
            error: function () {
                hideMerchantTable();
            },
            success: function (msg) {
                if (msg.success) {//成功返回
                    var merchantList = msg.data;
                    if (!isEmpty(merchantList)) {
                        var html = $("#merchant_table_tmpl").render(msg);
                        $("#merchantBody").html(html);
                        $("#merchantTable").show();
                        $("#selectMultipleMer").show();
                    } else {
                        hideMerchantTable();
                    }
                } else {
                    hideMerchantTable();
                }
            }
        });
    }
    function selectSupp(merchantId, merchantName, smodelCode) {
        $("#srangeList").val(merchantId);
        $("#merchantName").val(merchantName);
    }
    //确认选择商户
    function selectMultipleMerchant(merchantIds, merchantCodes) {
        $("#merchantIds").val(merchantIds);
        $("#srangeList").val(merchantIds);
        initMerchantTable();
    }
</script>
</body>
</html>

