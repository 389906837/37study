<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>编辑平台应用</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?23" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/appManage/save" method="post" id="myForm" enctype="multipart/form-data">

        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">应用名称</label>
                <div class="layui-input-inline">
                    <input type="text" name="sname" id="sname" datatype="*" nullmsg="请输入应用名称"
                           value="${appManage.sname}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6" name="nickName" id="nickName">
                <label class="layui-form-label">应用用户</label>
                <div class="layui-input-inline" style="width: 115px">
                    <input type="text" name="snickName" readonly="true" id="snickName"
                           value="${appManage.snickName}"
                           class="layui-input"/>
                </div>
                <button class="layui-btn" style="display: inline-block;float: left;" type="button" id="sel"
                        data-type="sel">选择
                </button>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">应用类型</label>
                <div class="layui-input-inline">
                    <cang:select type="dic" groupNo="SP000159" name="stype"
                                 layFilter="stype" value="${appManage.stype}"
                                 exp="datatype=\"*\" nullmsg=\"请选择应用类型\""
                                 id="stype"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">应用系统分属</label>
                <div class="layui-input-inline">
                    <cang:select type="dic" groupNo="SP000160" name="ssystemType"
                                 layFilter="ssystemType" value="${appManage.ssystemType}"
                                 exp="datatype=\"*\" nullmsg=\"请选择应用系统分属\""
                                 id="ssystemType"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">数据加密类型</label>
                <div class="layui-input-inline">
                    <cang:select type="dic" groupNo="SP000161" name="sencrypType"
                                 layFilter="sencrypType" value="${appManage.sencrypType}"
                                 exp="datatype=\"*\" nullmsg=\"请选择数据加密类型\""
                                 id="sencrypType"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">应用收费类型</label>
                <div class="layui-input-inline">
                    <cang:select type="dic" groupNo="SP000162" name="stollType"
                                 layFilter="stollType" value="${appManage.stollType}"
                                 exp="datatype=\"*\" nullmsg=\"请选择应用收费类型\""
                                 id="stollType"/>
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">应用回调通知地址</label>
            <div class="layui-input-block">
                    <textarea id="snoticeAddress" name="snoticeAddress" class="layui-textarea layui-form-textarea80p"
                              placeholder="（请输入应用回调通知地址）">${appManage.snoticeAddress}</textarea>
            </div>
        </div>
        <div class="layui-form-item mt13">
            <label class="layui-form-label">备注</label>
            <div class="layui-input-block">
                    <textarea id="sremark" name="sremark" class="layui-textarea layui-form-textarea80p"
                              placeholder="（非必填项）">${appManage.sremark}</textarea>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                <button class="layui-btn" id="myFormSub">保存</button>
            </div>
        </div>
        <input type="hidden" id="id" name="id" value="${appManage.id}"/>
        <input type="hidden" id="scode" name="scode" value="${appManage.scode}"/>
        <input type="hidden" id="suserId" name="suserId" value="${appManage.suserId}"/>
        <input type="hidden" id="suserCode" name="suserCode" value="${appManage.suserCode}"/>
    </form>
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
                var snickName = $("#snickName").val();
                if (isEmpty(snickName)) {
                    alertDel("应用用户不能为空！");
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
    });
    $(function () {
        $("#cancelBtn").click(function () {
            parent.layer.close(index);
            return false;
        });
        $("#sel").click(function () {
            showLayerMax("选择开放平台用户", ctx + "/userInfo/selectUser");
        });
    });

    function selectSupp(userId, userCode, snickName) {
        $("#snickName").val(snickName);
        $("#suserCode").val(userCode);
        $("#suserId").val(userId);
    }
</script>
</body>
</html>

