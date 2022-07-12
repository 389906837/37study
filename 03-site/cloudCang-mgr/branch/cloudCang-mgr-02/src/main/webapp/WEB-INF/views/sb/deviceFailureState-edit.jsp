<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
<title>修改故障状态</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/deviceMal/deviceFailure" method="post" id="myForm">
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">处理时间</label>
                <div class="layui-input-inline">
                    <input type="text" name="sdealwithTime" datatype="*" nullmsg="请选择申报时间" id="sdealwithTime"
                           value="<fmt:formatDate value='${deviceMalfunctionRecord.sdealwithTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"
                           class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">处理人</label>
                <div class="layui-input-inline">
                    <input type="text" name="sdealwithMan" id="sdealwithMan"
                           value="${deviceMalfunctionRecord.sdealwithMan}" datatype="*" errormsg="请填写处理人"
                           class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">处理备注</label>
                <div class="layui-input-inline">
                    <textarea class="layui-textarea" id="sremark" name="sremark" style="width: 500px">${deviceMalfunctionRecord.sremark}</textarea>
                </div>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn" id="myFormSub">提交</button>
            </div>
        </div>
        <input type="hidden" id="id" name="id" value="${deviceMalfunctionRecord.id}" />
        <input type="hidden" id="smalfunctionDesc" name="smalfunctionDesc" value="${deviceMalfunctionRecord.smalfunctionDesc}" />
        <input type="hidden" id="istatus" name="istatus" value="${deviceMalfunctionRecord.istatus}" />
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
            elem: '#tdeclareTime',
            type: 'datetime',
            min: nowTime,
            done: function(value, date){
                endMax = end.config.max;
                end.config.min = date;
                end.config.min.month = date.month -1;
                if(!isEmpty(value)) { //监听日期被切换
                    $("#tdeclareTime").removeClass("Validform_error");
                    $("#tdeclareTime").parent().find("span").hide();
                } else {
                    $("#tdeclareTime").addClass("Validform_error");
                    $("#tdeclareTime").parent().find("span").html($("#tdeclareTime").attr("nullmsg"));
                    $("#tdeclareTime").parent().find("span").removeClass("Validform_right");
                    $("#tdeclareTime").parent().find("span").addClass("Validform_wrong");
                    $("#tdeclareTime").parent().find("span").show();
                }
            }
        });
        var end = laydate.render({
            elem: '#sdealwithTime',
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
                    $("#sdealwithTime").removeClass("Validform_error");
                    $("#sdealwithTime").parent().find("span").hide();
                } else {
                    $("#sdealwithTime").addClass("Validform_error");
                    $("#sdealwithTime").parent().find("span").html($("#sdealwithTime").attr("nullmsg"));
                    $("#sdealwithTime").parent().find("span").removeClass("Validform_right");
                    $("#sdealwithTime").parent().find("span").addClass("Validform_wrong");
                    $("#sdealwithTime").parent().find("span").show();
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
</script>
</body>
</html>

