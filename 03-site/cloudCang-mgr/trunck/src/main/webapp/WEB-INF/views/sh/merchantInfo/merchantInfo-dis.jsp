<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>二级分销配置</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?22" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/merchantInfo/saveDis" method="post" id="myForm">


        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">返佣规则</label>

                <div class="layui-input-inline">
                    <cang:select type="list" name="irebateRule" id="irebateRule" entire="true"
                                 value="${merchantInfo.irebateRule}"
                                 exp="datatype=\"*\" nullmsg=\"请选择返佣规则\""
                                 list="{10:月结,20:季度,30:半年,40:一年}"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">返佣方式</label>

                <div class="layui-input-inline">
                    <cang:select type="list" name="irebateWay" id="irebateWay" entire="true"
                                 exp="datatype=\"*\" nullmsg=\"请选择返佣方式\""
                                 value="${merchantInfo.irebateWay}" list="{10:固定金额,20:按毛利,30:按纯利}"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">返佣比例</label>
                <div class="layui-input-inline">
                    <input type="text" name="irebateRate" id="irebateRate" value="${merchantInfo.irebateRate}"
                           class="layui-input" datatype="*" nullmsg="请输入返佣比例"/>
                </div>
            </div>
        </div>

        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                <button class="layui-btn" <%--lay-submit="" lay-filter="myFormSub"--%>id="myFormSub">保存</button>
            </div>
        </div>
        <input type="hidden" id="id" name="id" value="${merchantInfo.id}"/>
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
        /*     form.on('submit(myFormSub)', function () {
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

