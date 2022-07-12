<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>系统公区域编辑</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?1" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/announcement/save" method="post" id="myForm">
        <div class="layui-form-item">
            <%--商户ID--%>
            <%--<input type="hidden" id="smerchantId" name="smerchantId" value="${announcement.smerchantId}"/>--%>
            <%--<div class="layui-form-item">--%>
                <%--<div class="layui-col-md6">--%>
                    <%--<label class="layui-form-label">商户名称</label>--%>
                    <%--<div class="layui-input-inline">--%>
                        <%--<input type="text" name="smerchantName" value="${merchantInfo.sname}" placeholder="必填..."--%>
                               <%--datatype="*" nullmsg="请输入商户名称"--%>
                               <%--id="smerchantName" class="layui-input"/>--%>
                    <%--</div>--%>
                    <%--<button class="layui-btn" id="btn1" style="display: inline-block;float: left;"--%>
                            <%--type="button" data-type="sel">选择--%>
                    <%--</button>--%>
                <%--</div>--%>
                <%--<div class="layui-col-md6">--%>
                    <%--<label class="layui-form-label">商户编号</label>--%>
                    <%--<div class="layui-input-inline">--%>
                        <%--<input type="text" name="smerchantCode" id="smerchantCode" value="${announcement.smerchantCode}"--%>
                               <%--class="layui-input" disabled/>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">标题</label>
                    <div class="layui-input-inline">
                        <input type="text" name="stitle" id="stitle" value="${announcement.stitle}" datatype="*"
                               nullmsg="请输入标题"
                               class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">排序号</label>
                    <div class="layui-input-inline">
                        <input type="text" name="isort" id="isort" value="${announcement.isort}" datatype="n"
                               nullmsg="请输入数字"
                               class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">截止日期</label>
                    <div class="layui-input-inline">
                        <input type="text" name="tvalidDate" datatype="*" nullmsg="请选择有效日期" id="tvalidDate"
                               value="<fmt:formatDate value='${announcement.tvalidDate}' pattern='yyyy-MM-dd HH:mm:ss'/>"
                               class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">公告内容</label>
                    <div class="layui-input-inline">
                        <input type="text" name="scontentUrl" id="scontentUrl" value="${announcement.scontentUrl}" datatype="*"
                               nullmsg="请输入标题"
                               class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">说明</label>
                    <div class="layui-input-block">
                    <textarea style="width: 360px;" class="layui-textarea" id="sremark"
                              name="sremark">${announcement.sremark}</textarea>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-form-item fixed-bottom">
                    <div class="layui-input-block">
                        <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                        <button class="layui-btn" id="myFormSub">保存</button>
                    </div>
                </div>
            </div>
            <input type="hidden" id="id" name="id" value="${announcement.id}"/>
            <input type="hidden" id="sregionId" name="sregionId" value="${announcement.sregionId}"/>
    </form>
</div>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js" type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate', 'upload'], function () {
        //执行一个laydate实例
        var laydate = layui.laydate;
        laydate.render({
            elem: '#tvalidDate',//指定元素
            type: 'datetime',
            min: 0,
            done: function(value){ //监听日期被切换
                if(!isEmpty(value)) {
                    $("#tvalidDate").removeClass("Validform_error");
                    $("#tvalidDate").parent().find("span").hide();
                } else {
                    $("#tvalidDate").addClass("Validform_error");
                    $("#tvalidDate").parent().find("span").html($("#tvalidDate").attr("nullmsg"));
                    $("#tvalidDate").parent().find("span").removeClass("Validform_right");
                    $("#tvalidDate").parent().find("span").addClass("Validform_wrong");
                    $("#tvalidDate").parent().find("span").show();
                }
            },
        });

        laydate.render({
            elem: '#tpublishDate',
            type: 'datetime',
            done: function(value){ //监听日期被切换
                if(!isEmpty(value)) {
                    $("#tpublishDate").removeClass("Validform_error");
                    $("#tpublishDate").parent().find("span").hide();
                } else {
                    $("#tpublishDate").addClass("Validform_error");
                    $("#tpublishDate").parent().find("span").html($("#tpublishDate").attr("nullmsg"));
                    $("#tpublishDate").parent().find("span").removeClass("Validform_right");
                    $("#tpublishDate").parent().find("span").addClass("Validform_wrong");
                    $("#tpublishDate").parent().find("span").show();
                }
            }
        });

        //选择商户
        var $ = layui.$, active = {
            sel: function () {
                showLayer("选择商户", ctx + "/merchantInfo/radioSelectList", {area: ['90%', '80%']});
            }
        };
        $('.layui-form .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

        var form = layui.form;
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
    });
    $(function () {
        $("#cancelBtn").click(function () {
            parent.layer.close(index);
            return false;
        });
    });


    function selectMerchant(merchantId, merchantCode, merchantName) {
        $("#smerchantId").val(merchantId);//商户ID
        $("#smerchantCode").val(merchantCode);//商户编号
        $("#smerchantName").val(merchantName);//商户名称
    }
</script>
</body>
</html>

