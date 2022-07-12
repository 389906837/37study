<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>编辑设备分组</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/device/monitorDataConf/edit" method="post" id="myForm">
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label" style="width: 160px"><spring:message code='sb.number.of.shipments.per.shipment' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="iinventoryNum"  id="iinventoryNum" value="${monitorDataConfDomain.iinventoryNum}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label" style="width: 160px"><spring:message code='sb.automatic.stock.start.time' /></label>
                <div class="layui-input-inline" >
                    <input type="text" name="tinventoryBeginTime"  id="tinventoryBeginTime" value="${monitorDataConfDomain.tinventoryBeginTime}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label" style="width: 160px"><spring:message code='sb.automatic.stock.end.time' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="tinventoryEndTime"  id="tinventoryEndTime" value="${monitorDataConfDomain.tinventoryEndTime}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label" style="width: 160px"><spring:message code='sb.temperature.control' />1</label>
                <div class="layui-input-inline">
                    <input type="text" name="slcontrolTemperature"  id="slcontrolTemperature" value="${monitorDataConfDomain.slcontrolTemperature}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label" style="width: 160px"><spring:message code='sb.temperature.control.start.time' />1</label>
                <div class="layui-input-inline">
                    <input type="text" name="tlcontrolBeginTime"  id="tlcontrolBeginTime"  value="${monitorDataConfDomain.tlcontrolBeginTime}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label" style="width: 160px"><spring:message code='sb.temperature.control.end.time' />1</label>
                <div class="layui-input-inline">
                    <input type="text" name="tlcontrolEndTime"  id="tlcontrolEndTime"  value="${monitorDataConfDomain.tlcontrolEndTime}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label" style="width: 160px"><spring:message code='sb.temperature.control' />2</label>
                <div class="layui-input-inline">
                    <input type="text" name="srcontrolTemperature" id="srcontrolTemperature" value="${monitorDataConfDomain.srcontrolTemperature}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label" style="width: 160px"><spring:message code='sb.temperature.control.start.time' />2</label>
                <div class="layui-input-inline">
                    <input type="text" name="trcontrolBeginTime" id="trcontrolBeginTime"  value="${monitorDataConfDomain.trcontrolBeginTime}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label" style="width: 160px"><spring:message code='sb.temperature.control.end.time' />2</label>
                <div class="layui-input-inline">
                    <input type="text" name="trcontrolEndTime" id="trcontrolEndTime"  value="${monitorDataConfDomain.trcontrolEndTime}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label" style="width: 160px"><spring:message code='sb.timing.switch' /></label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="iswitchStatus" id="iswitchStatus" entire="true" value="${monitorDataConfDomain.iswitchStatus}"  list='{0:springMessageCode=main.no,1:springMessageCode=main.yes}'/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label" style="width: 160px"><spring:message code='sb.boot.time' /></label>
                <div class="layui-input-inline">
                    <input type="text" name="tbootTime" id="tbootTime" placeholder="${monitorDataConfDomain.tbootTime}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label" style="width: 160px"><spring:message code='sb.shutdown.time' /></label>
                <div class="layui-input-inline">
                    <input type="text"  name="tshutDownTime" id="tshutDownTime"class="layui-input" placeholder="${monitorDataConfDomain.tshutDownTime}" >
                </div>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
                <button class="layui-btn" lay-submit="" lay-filter="myFormSub"><spring:message code="main.save" /></button>
            </div>
        </div>
        <input type="hidden"  name="id" id="mdcId" value="${monitorDataConfDomain.id}" class="layui-input" />
        <input type="hidden"  name="sdeviceId" id="sdeviceId" value="${monitorDataConfDomain.sdeviceId}" class="layui-input" />
    </form>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
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
        });


        layui.use('laydate', function(){
            var laydate = layui.laydate;


            //<spring:message code='sb.last.shipment.time' />
            laydate.render({
                elem: '#tinventoryTime',
                type: 'time'
            });
            //<spring:message code='sb.automatic.stock.start.time' />
            laydate.render({
                elem: '#tinventoryBeginTime',
                type: 'time'
            });
            //<spring:message code='sb.automatic.stock.end.time' />
            laydate.render({
                elem: '#tinventoryEndTime',
                type: 'time'
            });
            //<spring:message code='sb.temperature.control.start.time' />1
            laydate.render({
                elem: '#tlcontrolBeginTime',
                type: 'time'
            });
            //<spring:message code='sb.temperature.control.end.time' />1
            laydate.render({
                elem: '#tlcontrolEndTime',
                type: 'time'
            });
            //<spring:message code='sb.temperature.control.start.time' />2
            laydate.render({
                elem: '#trcontrolBeginTime',
                type: 'time'
            });
            //<spring:message code='sb.temperature.control.end.time' />2
            laydate.render({
                elem: '#trcontrolEndTime',
                type: 'time'
            });
            //<spring:message code='sb.boot.time' />
            laydate.render({
                elem: '#tbootTime',
                type: 'time'
            });
            //<spring:message code='sb.shutdown.time' />
            laydate.render({
                elem: '#tshutDownTime',
                type: 'time'
            });
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

