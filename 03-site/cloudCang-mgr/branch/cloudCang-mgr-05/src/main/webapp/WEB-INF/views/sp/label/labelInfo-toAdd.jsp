<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>增加商品标签信息</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/commodity/labelInfo/add" method="post" id="myForm">
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label" ><spring:message code="sp.commodityInfo.label.name" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="sname" datatype="*" nullmsg='<spring:message code="sp.label.please.enter.a.tag.name" />'  id="sname" value="" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.label.whether.the.parent.tag" /></label>
                <div class="layui-input-inline">
                    <select name="iisParent" id="iisParent" class="state" lay-filter="iisParent">
                        <option value=""><spring:message code="main.please.choose" /></option>
                        <option value="0"><spring:message code="main.no" /></option>
                        <option value="1"><spring:message code="main.yes" /></option>
                    </select>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.label.parent.tag.name" /></label>
                <div class="layui-input-inline">
                    <select name="sparentId" id="parentName" class="state" lay-filter="parentName">
                    </select>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="main.sort" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="isort"  id="isort" value="" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
                <button class="layui-btn"  id="myFormSub"><spring:message code="main.save" /></button>
            </div>
        </div>
    </form>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js"  type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var form;
    layui.use(['form', 'layedit', 'laydate'], function () {
        form = layui.form;
        $("#myForm").Validform({
            btnSubmit:"#myFormSub",  //根据id触发
            tiptype:3,                  //第三种方式
            showAllError:true,
            beforeSubmit:function(){ //验证过后执行save方法
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
        /*//监听提交
        form.on('submit(myFormSub)', function () {
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
                            cancel: function () {
                                parent.closeLayerAndRefresh(index);
                            }
                        }, function () {
                            parent.closeLayerAndRefresh(index);
                        });
                    } else {
                        alertDel(msg.msg);
                    }
                }
            });
            return false;
        });*/

        //监听select选择
        form.on('select(iisParent)', function (data) {
            var isParent = data.value;
            var labelId = "add";
            if("0" == isParent) {//不是父标签
                $.ajax({
                    type: 'post',
                    url: '${ctx}/commodity/labelInfo/getParentLabel',
                    data: {pid: labelId},
                    dataType: 'json',
                    success: function (resp) {
                        $("#parentName").removeAttr("disabled");
                        $("#parentName").html("");
                        var option1 = $("<option>").val("").text('<spring:message code="main.please.choose" />');
                        $("#parentName").append(option1);
                        $.each(resp.data, function (key, val) {
                            var option1 = $("<option>").val(val.id).text(val.sname);
                            $("#parentName").append(option1);
                            form.render('select');
                        });
                    }
                });
            } else {
                $("#parentName").html("");
                $("#parentName").attr("disabled", "disabled");
                form.render('select');
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

