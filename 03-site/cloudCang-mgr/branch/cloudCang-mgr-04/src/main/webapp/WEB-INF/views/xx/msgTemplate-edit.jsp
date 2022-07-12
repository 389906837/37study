<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>消息协议/模板编辑</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?1" rel="stylesheet">

</head>
<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/msgTemplate/save" method="post" id="myForm">
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">模板标题</label>
                <div class="layui-input-inline">
                    <input type="text" name="stemplateTitle" id="stemplateTitle" value="${msgTemplate.stemplateTitle}"
                           datatype="*" nullmsg="请输入模板" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">模板名称</label>
                <div class="layui-input-inline">
                    <input type="text" name="stemplateName" id="stemplateName" value="${msgTemplate.stemplateName}"
                           datatype="*" nullmsg="请输入模板名称" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">模板分类</label>
                <div class="layui-input-inline">
                    <select id="smainId" name="smainId" datatype="*" nullmsg="请选择模板分类" lay-filter="smain" lay-search="" >
                        <option value="">请选择模板分类</option>
                        <c:forEach items="${msgTemplateList}" varStatus="L" var="item">
                            <option value="${item.id}"
                                    <c:if test="${item.id eq msgTemplate.smainId}">selected="true"</c:if>>(${item.scode})${item.smsgName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">供应商编号</label>
                <div class="layui-input-inline" style="width: 132px">
                    <input type="text" name="ssupplierCode" id="ssupplierCode" value="${msgTemplate.ssupplierCode}"
                           readonly="readonly" class="layui-input"/>
                </div>
                <button class="layui-btn" id="btn1" style="display: inline-block;float: left;" type="button"
                        data-type="sel">选择
                </button>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">每日发送(次)</label>
                <div class="layui-input-inline">
                    <input type="text" name="sendCountLimit" id="sendCountLimit" value="${msgTemplate.sendCountLimit}"
                           datatype="n" nullmsg="请输入发送次数" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">每日单用户数</label>
                <div class="layui-input-inline">
                    <input type="text" name="sendUserCountLimit" datatype="*" nullmsg="请输入每日单用户" id="sendUserCountLimit"
                           value="${msgTemplate.sendUserCountLimit}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">

                <label class="layui-form-label">实时发送</label>
                <div class="layui-input-inline">
                    <select id="iisRealtime" name="iisRealtime" lay-filter="time" datatype="*" nullmsg="请选择实时发送">
                        <option value="">请选择</option>
                        <option value="1" <c:if test="${1 == msgTemplate.iisRealtime}">selected</c:if>>实时</option>
                        <option value="2" <c:if test="${2 == msgTemplate.iisRealtime}">selected</c:if>>非实时</option>
                    </select>
                </div>
            </div>

            <div class="layui-col-md6">
                <label class="layui-form-label">超时(分钟)</label>
                <div class="layui-input-inline">
                    <input type="text" name="itimeout" id="itimeout" value="${msgTemplate.itimeout}"
                           class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">用途</label>
                <div class="layui-input-inline">
                <%--    <select id="iusage" name="iusage" lay-filter="iusage" datatype="*" nullmsg="请选择用途">
                        <option value="">请选择</option>
                        <option value="1" <c:if test="${1 == msgTemplate.iusage}"></c:if>>验证码</option>
                        <option value="2" <c:if test="${2 == msgTemplate.iusage}"></c:if>>普通</option>
                    </select>--%>
                    <cang:select type="list" id="iusage" name="iusage" value="${msgTemplate.iusage}" list="{1:验证码,2:普通}" entire="true" exp="datatype=\"*\" nullmsg=\"请选择用途\"" />
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6" id="startTimeId">
                <label class="layui-form-label">发送开始时间</label>
                <div class="layui-input-inline">
                    <input type="text" name="sstarttime" id="sstarttime" value="${msgTemplate.sstarttime}"
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6" id="endTimeId">
                <label class="layui-form-label">发送结束时间</label>
                <div class="layui-input-inline">
                    <input type="text" name="sendtime" id="sendtime" value="${msgTemplate.sendtime}"
                           class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">模板内容</label>
                <div class="layui-input-inline">
                    <textarea class="layui-textarea" name="stemplateContent" id="stemplateContent"  placeholder="模板内容（必填项）"
                              style="width: 600px">${msgTemplate.stemplateContent}</textarea>
                </div>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                <button class="layui-btn" id="myFormSub">保存</button>
            </div>
        </div>
        <input type="hidden" id="id" name="id" value="${msgTemplate.id}"/>
        <input type="hidden" id="ssupplierId" name="ssupplierId" value="${msgTemplate.ssupplierId}"/>
        <input type="hidden" id="imsgType" name="imsgType" value="${msgTemplate.imsgType}"/>
    </form>
</div>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js" type="text/javascript"></script>

<c:if test="${1 == msgTemplate.iisRealtime}">
    <script type="text/javascript">
        $("#sstarttime").attr("disabled", "disabled");
        $("#sendtime").attr("disabled", "disabled");
        $("#sstarttime").val("");
        $("#sendtime").val("");
    </script>
</c:if>

<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate'], function () {
        /**
         * 选择日期范围
         */
        var laydate = layui.laydate;
        //执行一个laydate实例
        laydate.render({
            elem: '#sstarttime', //指定元素
            type: 'time',   //指定时间类型
            done: function (data) { //监听日期被切换
                if (!isEmpty(data)) {
                    $("#sstarttime").removeClass("Validform_error");
                    $("#sstarttime").parent().find("span").hide();
                } else {
                    $("#sstarttime").addClass("Validform_error");
                    $("#sstarttime").parent().find("span").html($("#sstarttime").attr("nullmsg"));
                    $("#sstarttime").parent().find("span").removeClass("Validform_right");
                    $("#sstarttime").parent().find("span").addClass("Validform_wrong");
                    $("#sstarttime").parent().find("span").show();
                }
            }
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#sendtime', //指定元素
            type: 'time',  //指定时间类型
            done: function (data) { //监听日期被切换
                if (!isEmpty(data)) {
                    $("#sendtime").removeClass("Validform_error");
                    $("#sendtime").parent().find("span").hide();
                } else {
                    $("#sendtime").addClass("Validform_error");
                    $("#sendtime").parent().find("span").html($("#sendtime").attr("nullmsg"));
                    $("#sendtime").parent().find("span").removeClass("Validform_right");
                    $("#sendtime").parent().find("span").addClass("Validform_wrong");
                    $("#sendtime").parent().find("span").show();
                }
            }
        });


        //选择商户
//        var $ = layui.$, active = {
//            sel1: function(){
//                showLayerMax("选择商户", ctx+"/merchantInfo/radioSelectList");
//            }
//        };
//        $('.layui-form .layui-btn').on('click', function(){
//            var type = $(this).data('type');
//            active[type] ? active[type].call(this) : '';
//        });

        var form = layui.form;
        $("#myForm").Validform({
            btnSubmit: "#myFormSub",  //根据id触发
            tiptype: 3,                  //第三种方式
            showAllError: true,
            beforeSubmit: function () { //验证过后执行save方法
                var stemplateContent = $("#stemplateContent").val();
                if (isEmpty(stemplateContent)) {
                    alertDel("请填写模板内容！");
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

        // 实时/非实时触发事件(选择实时发送开始结束时间置灰,非实时可选择时间,选非实时后更改为实时,清空发送时间值)
        form.on('select(time)', function (data) {
            if (data.value == 1) {
                $("#sstarttime").attr("disabled", "disabled");
                $("#sendtime").attr("disabled", "disabled");
                $("#sstarttime").val("");
                $("#sendtime").val("");
            } else {
                $("#sstarttime").removeAttr("disabled");
                $("#sendtime").removeAttr("disabled");
            }
            if (!isEmpty(data.value)) {
                $("#iisRealtime").parent().find("span").hide();
            } else {
                $("#iisRealtime").parent().find("span").show();
                if (!$("#iisRealtime").parent().find("span").hasClass("Validform_wrong")) {
                    $("#iisRealtime").parent().find("span").html($("#iisRealtime").attr("nullmsg"));
                    $("#iisRealtime").parent().find("span").removeClass("Validform_right");
                    $("#iisRealtime").parent().find("span").addClass("Validform_wrong");
                }
            }
        });


        form.on('select(smain)', function (data) {
            if (!isEmpty(data.value)) {
                $("#smainId").parent().find("span").hide();
            } else {
                $("#smainId").parent().find("span").show();
                if (!$("#smainId").parent().find("span").hasClass("Validform_wrong")) {
                    $("#smainId").parent().find("span").html($("#smainId").attr("nullmsg"));
                    $("#smainId").parent().find("span").removeClass("Validform_right");
                    $("#smainId").parent().find("span").addClass("Validform_wrong");
                }
            }
        });

        form.on('select(iusage)', function (data) {
            if (!isEmpty(data.value)) {
                $("#iusage").parent().find("span").hide();
            } else {
                $("#iusage").parent().find("span").show();
                if (!$("#iusage").parent().find("span").hasClass("Validform_wrong")) {
                    $("#iusage").parent().find("span").html($("#iusage").attr("nullmsg"));
                    $("#iusage").parent().find("span").removeClass("Validform_right");
                    $("#iusage").parent().find("span").addClass("Validform_wrong");
                }
            }
        });


    });

    var $ = layui.$, active = {
        //选择供应商
        sel: function () {
            showLayer("选择供应商", ctx + "/supplierInfo/selectMsgTemplateList", {area: ['100%', '100%']});
        }
    };
    $('.layui-form .layui-btn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    $(function () {
        $("#cancelBtn").click(function () {
            parent.layer.close(index);
            return false;
        });
    });

    function selectSupp(suppId, suppCode, imsgType) {
        $("#ssupplierId").val(suppId);
        $("#imsgType").val(imsgType);
        $("#ssupplierCode").val(suppCode).parent().find("span").hide();
    }

    //    function selectMerchant(merchantId,merchantCode,merchantName) {
    //        $("#smerchantId").val(merchantId);//商户ID
    //        $("#smerchantCode").val(merchantCode);//商户编号
    //        $("#smerchantName").val(merchantName);//商户名称
    //    }


</script>

</body>
</html>

