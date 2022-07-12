<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>编辑商品标签信息</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/commodity/labelInfo/Edit" method="post" id="myForm">
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">标签名称</label>
                <label class="layui-form-label" style="width:200px;text-align: left;">${labelInfo.sname}</label>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">是否父标签</label>
                <div class="layui-input-inline">
                    <select name="iisParent" id="iisParent" class="state" lay-filter="iisParent">
                        <option value="">请选择</option>
                        <option value="0" <c:if test="${0 == labelInfo.iisParent}">selected</c:if>>否</option>
                        <option value="1" <c:if test="${1 == labelInfo.iisParent}">selected</c:if>>是</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">父标签名称</label>
                <div class="layui-input-inline">
                    <select name="sparentId" id="parentName" class="state" lay-filter="parentName">
                        <option value="">请选择</option>
                        <c:if test="${0 == labelInfo.iisParent}">
                            <c:forEach items="${labelInfoList}" var="vo">
                                <option value="${vo.id}"
                                        <c:if test="${vo.id eq labelInfo.sparentId }">selected</c:if>>${vo.sname}</option>
                            </c:forEach>
                        </c:if>
                    </select>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">排序号</label>
                <div class="layui-input-inline">
                    <input type="text" name="isort"  id="isort" value="${labelInfo.isort}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                <button class="layui-btn" lay-submit="" lay-filter="myFormSub">保存</button>
            </div>
        </div>
        <input type="hidden"  name="id" id="id" value="${labelInfo.id}" class="layui-input" />
    </form>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var form;
    layui.use(['form', 'layedit', 'laydate'], function () {
        form = layui.form;
        //监听提交
        form.on('submit(myFormSub)', function () {
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
        });

        //监听select选择
        form.on('select(iisParent)', function (data) {
            var isParent = data.value;
            var labelId = '${labelInfo.id}';//标签ID
            if("0" == isParent) {//不是父标签
                $.ajax({
                    type: 'post',
                    url: '${ctx}/commodity/labelInfo/getParentLabel',
                    data: {pid: labelId},
                    dataType: 'json',
                    success: function (resp) {
                        $("#parentName").html("");
                        var option1 = $("<option>").val("").text("请选择");
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

