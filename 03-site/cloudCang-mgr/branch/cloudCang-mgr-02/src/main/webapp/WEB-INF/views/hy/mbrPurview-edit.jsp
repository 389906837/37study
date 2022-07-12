<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title>会员权限管理</title>
    <link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
    <link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
    <div class="ibox-content">
        <form class="layui-form" action="${ctx}/mbrPurview/save" method="post" id="myForm">
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">编号</label>
                    <div class="layui-input-inline">
                        <input type="text" name="spurNo" id="spurNo" value="${mbrPurview.spurNo}" class="layui-input" disabled/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">权限码</label>
                    <div class="layui-input-inline">
                        <input type="text" name="spurCode" datatype="*" nullmsg="可输入任何字符" id="spurCode" value="${mbrPurview.spurCode}" placeholder="请输入权限码..." class="layui-input" />
                    </div>
                </div>
            </div>
                <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">权限名称</label>
                    <div class="layui-input-inline">
                        <input type="text" name="spurName" datatype="*" nullmsg="可输入任何字符" id="spurName" value="${mbrPurview.spurName}" placeholder="请输入权限名称..." class="layui-input" />
                    </div>
                </div>
                </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">访问路径</label>
                    <div class="layui-input-inline">
                        <input type="text" name="surlPath" datatype="*" nullmsg="可输入任何字符" id="surlPath" value="${mbrPurview.surlPath}" placeholder="请输入访问路径..." class="layui-input" />
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">系统代码</label>
                    <div class="layui-input-inline">
                        <cang:select type="list" id="ssysCode" name="ssysCode" value="${mbrPurview.ssysCode}" list="{10:SYS-WEB,20:SYS-WAP}" entire="true" exp="datatype=\"*\" nullmsg=\"请选择系统代码\"" />
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">系统分类</label>
                    <div class="layui-input-inline">
                       <cang:select type="list" id="ssysType" name="ssysType" value="${mbrPurview.ssysType}" list="{10:PC,20:WAP}" entire="true" exp="datatype=\"*\" nullmsg=\"请选择系统分类\"" />
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">权限说明</label>
                    <div class="layui-input-inline">
                        <textarea class="layui-textarea" name="sdescription" id="sdescription" style="width: 500px" placeholder="请输入权限说明...">${mbrPurview.sdescription}</textarea>
                    </div>
                </div>
            </div>
            <div class="layui-form-item fixed-bottom">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                    <button class="layui-btn" id="myFormSub">保存</button>
                </div>
            </div>
            <input type="hidden" id="id" name="id" value="${mbrPurview.id}" />
        </form>
    </div>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js"  type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate'], function(){
        var form = layui.form;
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
    $(function() {
       $("#cancelBtn").click(function() {
           parent.layer.close(index);
           return false;
       });
    });
</script>
</body>
</html>

