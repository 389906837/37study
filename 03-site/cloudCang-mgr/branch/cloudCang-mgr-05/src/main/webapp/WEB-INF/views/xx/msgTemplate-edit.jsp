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
                <label class="layui-form-label"><spring:message code='xx.template.title' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="stemplateTitle" id="stemplateTitle" value="${msgTemplate.stemplateTitle}"
                           datatype="*" nullmsg="<spring:message code='xx.please.enter.a.template' />" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='tpinfo.template.name' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="stemplateName" id="stemplateName" value="${msgTemplate.stemplateName}"
                           datatype="*" nullmsg="<spring:message code='tpinfotoadd.please.enter.a.template.name' />" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='xx.template.classification' /></label>
                <div class="layui-input-inline">
                    <select id="smainId" name="smainId" datatype="*" nullmsg="<spring:message code='xx.please.select.a.template.category' />" lay-filter="smain" lay-search="" >
                        <option value=""><spring:message code='xx.please.select.a.template.category' /></option>
                        <c:forEach items="${msgTemplateList}" varStatus="L" var="item">
                            <option value="${item.id}"
                                    <c:if test="${item.id eq msgTemplate.smainId}">selected="true"</c:if>>(${item.scode})${item.smsgName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='xx.supplier.code' /></label>
                <div class="layui-input-inline" style="width: 132px">
                    <input type="text" name="ssupplierCode" id="ssupplierCode" value="${msgTemplate.ssupplierCode}"
                           readonly="readonly" class="layui-input"/>
                </div>
                <button class="layui-btn" id="btn1" style="display: inline-block;float: left;" type="button"
                        data-type="sel"><spring:message code='main.choice' />
                </button>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='xx.send.daily.times' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sendCountLimit" id="sendCountLimit" value="${msgTemplate.sendCountLimit}"
                           datatype="n" nullmsg="<spring:message code='xx.please.enter.the.number.of.times.sent' />" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='xx.daily.single.user.count' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sendUserCountLimit" datatype="*" nullmsg="<spring:message code='xx.please.enter.a.daily.single.user' />" id="sendUserCountLimit"
                           value="${msgTemplate.sendUserCountLimit}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">

                <label class="layui-form-label"><spring:message code='xx.send.in.real.time' /></label>
                <div class="layui-input-inline">
                    <select id="iisRealtime" name="iisRealtime" lay-filter="time" datatype="*" nullmsg="<spring:message code='xx.please.select.send.in.real.time' />">
                        <option value=""><spring:message code="main.please.choose" /></option>
                        <option value="1" <c:if test="${1 == msgTemplate.iisRealtime}">selected</c:if>><spring:message code='xx.real.time' /></option>
                        <option value="2" <c:if test="${2 == msgTemplate.iisRealtime}">selected</c:if>><spring:message code='xx.not.real.time' /></option>
                    </select>
                </div>
            </div>

            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='xx.timeout.minutes' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="itimeout" id="itimeout" value="${msgTemplate.itimeout}"
                           class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='xx.use' /></label>
                <div class="layui-input-inline">
                <%--    <select id="iusage" name="iusage" lay-filter="iusage" datatype="*" nullmsg="请选择用途">
                        <option value=""><spring:message code="main.please.choose" /></option>
                        <option value="1" <c:if test="${1 == msgTemplate.iusage}"></c:if>>验证码</option>
                        <option value="2" <c:if test="${2 == msgTemplate.iusage}"></c:if>>普通</option>
                    </select>--%>
                    <cang:select type="list" id="iusage" name="iusage" value="${msgTemplate.iusage}" list="{1:springMessageCode=main.verify.code,2:springMessageCode=xx.ordinary}" entire="true" exp="springMessageCode=xx.please.select.a.purpose" />
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6" id="startTimeId">
                <label class="layui-form-label"><spring:message code='xx.send.start.time' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sstarttime" id="sstarttime" value="${msgTemplate.sstarttime}"
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6" id="endTimeId">
                <label class="layui-form-label"><spring:message code='xx.send.end.time' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sendtime" id="sendtime" value="${msgTemplate.sendtime}"
                           class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='xx.template.content' /></label>
                <div class="layui-input-inline">
                    <textarea class="layui-textarea" name="stemplateContent" id="stemplateContent"  placeholder="<spring:message code='xx.template.content.required' />"
                              style="width: 600px">${msgTemplate.stemplateContent}</textarea>
                </div>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
                <button class="layui-btn" id="myFormSub"><spring:message code="main.save" /></button>
            </div>
        </div>
        <input type="hidden" id="id" name="id" value="${msgTemplate.id}"/>
        <input type="hidden" id="ssupplierId" name="ssupplierId" value="${msgTemplate.ssupplierId}"/>
        <input type="hidden" id="imsgType" name="imsgType" value="${msgTemplate.imsgType}"/>
    </form>
</div>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
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
                    alertDel("<spring:message code='xx.please.fill.in.the.template.content' />");
                    return false;
                }
                var loadIndex = loading();
                $('#myForm').ajaxSubmit({
                    type: "post",
                    dataType: "json",
                    async: true,
                    error: function () {
                        alertDelAndReload("<spring:message code='main.error.try.again' />");
                    },
                    success: function (msg) {
                        //成功返回
                        closeLayer(loadIndex);//关闭加载框
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
            showLayer("<spring:message code='xxcon.select.supplier' />", ctx + "/supplierInfo/selectMsgTemplateList", {area: ['100%', '100%']});
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

