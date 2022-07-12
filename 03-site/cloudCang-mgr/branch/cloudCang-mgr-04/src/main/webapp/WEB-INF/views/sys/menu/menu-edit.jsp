<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>菜单编辑</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?926" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/menu/save" method="post" id="myForm">
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">父菜单</label>
                <div class="layui-input-inline">
                    <select name="sparentId" id="sparentId">
                        <option value="0">请选择父菜单</option>
                        <c:forEach items="${parentMenu}" varStatus="L" var="item">
                            <option value="${item.id}"
                                    <c:if test="${item.id eq menu.sparentId}">selected="true"</c:if>>${item.sname}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">菜单名称</label>
                <div class="layui-input-inline">
                    <input type="text" name="sname" datatype="*" nullmsg="请输入菜单名称" id="sname" value="${menu.sname}"
                           class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">菜单图标</label>
                <div class="layui-input-inline">
                    <input type="text" name="simagePath" id="simagePath" value="${menu.simagePath}"
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">菜单路径</label>
                <div class="layui-input-inline">
                    <input type="text" name="smenuPath" datatype="*" nullmsg="请输入菜单路径" id="smenuPath"
                           value="${menu.smenuPath}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">排序</label>
                <div class="layui-input-inline">
                    <input type="text" name="isort" datatype="*" nullmsg="请输入排序号" id="isort" value="${menu.isort}"
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">分属系统</label>
                <div class="layui-input-inline">
                    <cang:select type="dic" groupNo="SP000157" entire="true" exp="datatype=\"*\" nullmsg=\"请选择分属系统\""
                                 value="${menu.ssystemSource}" entireName="请选择分属系统"
                                 name="ssystemSource" id="ssystemSource"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
                <label class="layui-form-label">备注</label>
                <div class="layui-input-block">
                    <textarea class="layui-textarea layui-form-textarea80p" name="sremark"
                              id="sremark">${menu.sremark}</textarea>
                </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                <button class="layui-btn" <%--lay-submit="" lay-filter="myFormSub"--%>id="myFormSub">保存</button>
            </div>
        </div>
        <input type="hidden" id="id" name="id" value="${menu.id}"/>
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
                                /*cancel: function () {
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
        /*        form.on('submit(myFormSub)', function(){
         var loadIndex = loading();
         $('#myForm').ajaxSubmit({
         type : "post",
         dataType : "json",
         async : true,
         error:function() {
         alertDelAndReload("操作异常，请重新操作");
         },
         success : function(msg) {
         //成功返回
         closeLayer(loadIndex);//关闭加载框
         if(msg.success) {
         alertSuccess("操作成功",{cancel:function() {
         parent.closeLayerAndRefresh(index);
         }},function () {
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

