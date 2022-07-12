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
                <label class="layui-form-label" style="width: 160px">每次盘货次数</label>
                <div class="layui-input-inline">
                    <input type="text" name="iinventoryNum"  id="iinventoryNum" value="${monitorDataConfDomain.iinventoryNum}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label" style="width: 160px">自动盘货开始时间</label>
                <div class="layui-input-inline" >
                    <input type="text" name="tinventoryBeginTime"  id="tinventoryBeginTime" value="${monitorDataConfDomain.tinventoryBeginTime}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label" style="width: 160px">自动盘货结束时间</label>
                <div class="layui-input-inline">
                    <input type="text" name="tinventoryEndTime"  id="tinventoryEndTime" value="${monitorDataConfDomain.tinventoryEndTime}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label" style="width: 160px">控制温度1</label>
                <div class="layui-input-inline">
                    <input type="text" name="slcontrolTemperature"  id="slcontrolTemperature" value="${monitorDataConfDomain.slcontrolTemperature}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label" style="width: 160px">温度控制开始时间1</label>
                <div class="layui-input-inline">
                    <input type="text" name="tlcontrolBeginTime"  id="tlcontrolBeginTime"  value="${monitorDataConfDomain.tlcontrolBeginTime}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label" style="width: 160px">温度控制结束时间1</label>
                <div class="layui-input-inline">
                    <input type="text" name="tlcontrolEndTime"  id="tlcontrolEndTime"  value="${monitorDataConfDomain.tlcontrolEndTime}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label" style="width: 160px">控制温度2</label>
                <div class="layui-input-inline">
                    <input type="text" name="srcontrolTemperature" id="srcontrolTemperature" value="${monitorDataConfDomain.srcontrolTemperature}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label" style="width: 160px">温度控制开始时间2</label>
                <div class="layui-input-inline">
                    <input type="text" name="trcontrolBeginTime" id="trcontrolBeginTime"  value="${monitorDataConfDomain.trcontrolBeginTime}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label" style="width: 160px">温度控制结束时间2</label>
                <div class="layui-input-inline">
                    <input type="text" name="trcontrolEndTime" id="trcontrolEndTime"  value="${monitorDataConfDomain.trcontrolEndTime}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label" style="width: 160px">定时开关机</label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="iswitchStatus" id="iswitchStatus" entire="true" value="${monitorDataConfDomain.iswitchStatus}"  list="{0:否,1:是}"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label" style="width: 160px">开机时间</label>
                <div class="layui-input-inline">
                    <input type="text" name="tbootTime" id="tbootTime" placeholder="${monitorDataConfDomain.tbootTime}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label" style="width: 160px">关机时间</label>
                <div class="layui-input-inline">
                    <input type="text"  name="tshutDownTime" id="tshutDownTime"class="layui-input" placeholder="${monitorDataConfDomain.tshutDownTime}" >
                </div>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                <button class="layui-btn" lay-submit="" lay-filter="myFormSub">保存</button>
            </div>
        </div>
        <input type="hidden"  name="id" id="mdcId" value="${monitorDataConfDomain.id}" class="layui-input" />
        <input type="hidden"  name="sdeviceId" id="sdeviceId" value="${monitorDataConfDomain.sdeviceId}" class="layui-input" />
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


        layui.use('laydate', function(){
            var laydate = layui.laydate;


            //上次盘货时间
            laydate.render({
                elem: '#tinventoryTime',
                type: 'time'
            });
            //自动盘货开始时间
            laydate.render({
                elem: '#tinventoryBeginTime',
                type: 'time'
            });
            //自动盘货结束时间
            laydate.render({
                elem: '#tinventoryEndTime',
                type: 'time'
            });
            //温度控制开始时间1
            laydate.render({
                elem: '#tlcontrolBeginTime',
                type: 'time'
            });
            //温度控制结束时间1
            laydate.render({
                elem: '#tlcontrolEndTime',
                type: 'time'
            });
            //温度控制开始时间2
            laydate.render({
                elem: '#trcontrolBeginTime',
                type: 'time'
            });
            //温度控制结束时间2
            laydate.render({
                elem: '#trcontrolEndTime',
                type: 'time'
            });
            //开机时间
            laydate.render({
                elem: '#tbootTime',
                type: 'time'
            });
            //关机时间
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

