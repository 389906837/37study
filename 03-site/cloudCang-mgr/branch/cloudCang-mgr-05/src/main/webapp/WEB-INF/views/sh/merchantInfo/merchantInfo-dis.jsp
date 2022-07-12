<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title><spring:message code="sh.secondary.distribution.configuration" /></title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?22" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/merchantInfo/saveDis" method="post" id="myForm">


        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sh.rebate.rules" /></label>

                <div class="layui-input-inline">
                    <cang:select type="list" name="irebateRule" id="irebateRule" entire="true"
                                 value="${merchantInfo.irebateRule}"
                                 exp="springMessageCode=sh.please.select.the.rebate.rule"
                                 list='{10:springMessageCode=sh.monthly,20:springMessageCode=sh.quarter,30:springMessageCode=sh.half.a.year,40:springMessageCode=sh.one.year}' />
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sh.rebate.method" /></label>

                <div class="layui-input-inline">
                    <cang:select type="list" name="irebateWay" id="irebateWay" entire="true"
                                 exp="springMessageCode=sh.please.choose.the.rebate.method"
                                 value="${merchantInfo.irebateWay}" list='{10:springMessageCode=sh.fixed.amount,20:springMessageCode=sh.by.gross.profit,30:springMessageCode=sh.according.to.net.profit}' />
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sh.rebate.ratio" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="irebateRate" id="irebateRate" value="${merchantInfo.irebateRate}"
                           class="layui-input" datatype="*" nullmsg='<spring:message code="sh.Please.enter.the.rebate.ratio" />'/>
                </div>
            </div>
        </div>

        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
                <button class="layui-btn" <%--lay-submit="" lay-filter="myFormSub"--%>id="myFormSub"><spring:message code="main.save" /></button>
            </div>
        </div>
        <input type="hidden" id="id" name="id" value="${merchantInfo.id}"/>
    </form>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
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
        /*     form.on('submit(myFormSub)', function () {
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

