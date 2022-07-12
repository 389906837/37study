<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>菜单编辑</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?1" rel="stylesheet">
</head>

<body>
<div class="ibox-content">
    <c:if test="${isAdd eq 1}">
    <form class="layui-form" action="${ctx}/codeGenerator/insert" method="post" id="myForm">
        </c:if>
        <c:if test="${isAdd ne 1}">
        <form class="layui-form" action="${ctx}/codeGenerator/update" method="post" id="myForm">
            </c:if>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">编号类型</label>
                    <div class="layui-input-inline">
                        <input type="text" name="scodeType" datatype="*" nullmsg="请输入编号类型" id="scodeType"
                               value="${codeGenerator.scodeType}" class="layui-input"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label">起始编号</label>
                    <div class="layui-input-inline">
                        <input type="text" name="icodeStart" datatype="*" nullmsg="请输入起始编号" id="icodeStart"
                               value="${codeGenerator.icodeStart}" class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">编号长度</label>
                    <div class="layui-input-inline">
                        <input type="text" name="icodeSize" id="icodeSize" value="${codeGenerator.icodeSize}"
                               class="layui-input"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label">递增大小</label>
                    <div class="layui-input-inline">
                        <input type="text" name="icodeStep" id="icodeStep" value="${codeGenerator.icodeStep}"
                               class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">编号前缀</label>
                    <div class="layui-input-inline">
                        <input type="text" name="scodePrefix" id="scodePrefix" value="${codeGenerator.scodePrefix}"
                               class="layui-input"/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label">每日是否重置</label>
                    <div class="layui-input-inline">
                        <select name="banewBegin" id="banewBegin">
                            <option value="0" <c:if test="${codeGenerator.banewBegin eq 0}">selected="true"</c:if>>否
                            </option>
                            <option value="1" <c:if test="${codeGenerator.banewBegin eq 1}">selected="true"</c:if>>是
                            </option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">日期格式</label>
                    <div class="layui-input-inline">
                        <input type="text" name="scodeDateformat" id="scodeDateformat"
                               value="${codeGenerator.scodeDateformat}" class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item fixed-bottom">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                    <button class="layui-btn" <%--lay-submit="" lay-filter="myFormSub"--%>id="myFormSub">保存</button>
                </div>
            </div>
            <input type="hidden" id="id" name="id" value="${codeGenerator.id}"/>
        </form>
</div>
</div>

<!-- layerUI-->
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
                               /* cancel: function () {
                                    parent.closeLayerAndRefresh(index);
                                }*/
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
</script>
</body>
</html>

