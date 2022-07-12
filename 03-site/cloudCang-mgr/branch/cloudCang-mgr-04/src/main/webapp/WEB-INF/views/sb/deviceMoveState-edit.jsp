<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
<title>修改审核状态</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/device/deviceMove" method="post" id="myForm">
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">审核状态</label>
                <div class="layui-input-inline">
                    <select id="istatus" name="istatus" datatype="*" nullmsg="请选择审核状态">
                        <option value="">请选择审核状态</option>
                        <option value="20" <c:if test="${20 == deviceMoveRecord.istatus}">selected</c:if>>审核通过</option>
                        <option value="30" <c:if test="${30 == deviceMoveRecord.istatus}">selected</c:if>>审核拒绝</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">审核意见</label>
                <div class="layui-input-inline">
                    <textarea class="layui-textarea" id="tauditOpinion" name="tauditOpinion" datatype="*" nullmsg="请填写审核意见" style="width: 500px">${deviceMoveRecord.tauditOpinion}</textarea>
                </div>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                <button class="layui-btn" id="myFormSub">提交</button>
            </div>
        </div>
        <input type="hidden" id="id" name="id" value="${deviceMoveRecord.id}" />
    </form>
</div>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js"  type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate','element'], function(){
        var element = layui.element;
        var laydate = layui.laydate;

        var nowTime = new Date().valueOf();
        var min = null;
        var max = null;

        var start = laydate.render({
            elem: '#tplanMoveTime',
            type: 'datetime',
            min: nowTime,
            done: function(value, date){
                endMax = end.config.max;
                end.config.min = date;
                end.config.min.month = date.month -1;
                if(!isEmpty(value)) { //监听日期被切换
                    $("#tplanMoveTime").removeClass("Validform_error");
                    $("#tplanMoveTime").parent().find("span").hide();
                } else {
                    $("#tplanMoveTime").addClass("Validform_error");
                    $("#tplanMoveTime").parent().find("span").html($("#tplanMoveTime").attr("nullmsg"));
                    $("#tplanMoveTime").parent().find("span").removeClass("Validform_right");
                    $("#tplanMoveTime").parent().find("span").addClass("Validform_wrong");
                    $("#tplanMoveTime").parent().find("span").show();
                }
            }
        });
        var end = laydate.render({
            elem: '#tmoveTime',
            type: 'datetime',
            min: nowTime,
            done: function(value, date){
                if($.trim(value) == ''){
                    var curDate = new Date();
                    date = {'date': curDate.getDate(), 'month': curDate.getMonth()+1, 'year': curDate.getFullYear()};
                }
                start.config.max = date;
                start.config.max.month = date.month -1;
                if(!isEmpty(value)) {
                    $("#tmoveTime").removeClass("Validform_error");
                    $("#tmoveTime").parent().find("span").hide();
                } else {
                    $("#tmoveTime").addClass("Validform_error");
                    $("#tmoveTime").parent().find("span").html($("#tmoveTime").attr("nullmsg"));
                    $("#tmoveTime").parent().find("span").removeClass("Validform_right");
                    $("#tmoveTime").parent().find("span").addClass("Validform_wrong");
                    $("#tmoveTime").parent().find("span").show();
                }
            }
        });

        var end = laydate.render({
            elem: '#tauditTime',
            type: 'datetime',
            min: nowTime,
            done: function(value, date){
                if($.trim(value) == ''){
                    var curDate = new Date();
                    date = {'date': curDate.getDate(), 'month': curDate.getMonth()+1, 'year': curDate.getFullYear()};
                }
                start.config.max = date;
                start.config.max.month = date.month -1;
                if(!isEmpty(value)) {
                    $("#tauditTime").removeClass("Validform_error");
                    $("#tauditTime").parent().find("span").hide();
                } else {
                    $("#tauditTime").addClass("Validform_error");
                    $("#tauditTime").parent().find("span").html($("#tauditTime").attr("nullmsg"));
                    $("#tauditTime").parent().find("span").removeClass("Validform_right");
                    $("#tauditTime").parent().find("span").addClass("Validform_wrong");
                    $("#tauditTime").parent().find("span").show();
                }
            }
        });




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

    $(function () {
        $("#cancelBtn").click(function () {
            parent.layer.close(index);
            return false;
        });
    });
</script>
</body>
</html>

