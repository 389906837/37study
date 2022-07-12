<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>增加设备基础信息模板</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
</head>
<body>
<div class="ibox-content">
    <div class="layui-tab-content">
        <div class="layui-tab-item layui-show">
            <form class="layui-form" action="${ctx}/deviceInfoTemplate/edit" method="post" id="myForm">
                <input type="hidden" name="id" id="id" value="${deviceInfoTemplate.id}" class="layui-input"/>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label">模板名称</label>
                        <div class="layui-input-inline">
                            <input type="text" name="sname" id="sname" datatype="*" nullmsg="请输入模板名称"
                                   value="${deviceInfoTemplate.sname}" class="layui-input" placeholder="必填..."/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label">模板编号</label>
                        <div class="layui-input-inline">
                            <input type="text" name="scode" id="scode" value="${deviceInfoTemplate.scode}"
                                   class="layui-input" disabled/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label">设备类型</label>
                        <div class="layui-input-inline">
                            <cang:select type="dic" name="itype" id="itype" entire="true"
                                         value="${deviceInfoTemplate.itype}" groupNo="SP000144"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label">合作模式</label>
                        <div class="layui-input-inline">
                            <cang:select type="dic" name="icooperationMode" id="icooperationMode" entire="true"
                                         value="${deviceInfoTemplate.icooperationMode}"
                                         groupNo="SP000104"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label">读写器序列号</label>
                        <div class="layui-input-inline">
                            <input type="text" name="sreaderSerialNumber" id="sreaderSerialNumber"
                                   value="${deviceInfoTemplate.sreaderSerialNumber}"
                                   class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label">设备型号</label>
                        <div class="layui-input-inline">
                            <input type="text" name="sdeviceModel" id="sdeviceModel"
                                   value="${deviceInfoTemplate.sdeviceModel}" class="layui-input"/>
                        </div>
                    </div>
                </div>
                <%--<div class="layui-form-item">--%>
                    <%--<div class="layui-col-md6">--%>
                        <%--<label class="layui-form-label">投放省份</label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<select name="sprovinceName" id="sprovinceName" class="state">--%>
                                <%--<option value="${deviceInfoTemplate.sprovinceName}">请选择</option>--%>
                                <%--<c:forEach items="${provinceSet}" var="vo">--%>
                                    <%--<option value="${vo.id}_${vo.sname}">${vo.sname}</option>--%>
                                <%--</c:forEach>--%>
                            <%--</select>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="layui-col-md6">--%>
                        <%--<label class="layui-form-label">投放城市</label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<select name="scityName" id="scityName" class="state">--%>
                            <%--</select>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <div class="layui-form-item">
                    <%--<div class="layui-col-md6">--%>
                        <%--<label class="layui-form-label">投放区县</label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<select name="sareaName" id="sareaName" class="state">--%>
                            <%--</select>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <div class="layui-col-md6">
                        <label class="layui-form-label">货柜类型</label>
                        <div class="layui-input-inline">
                            <cang:select type="list" name="icontainerType" id="icontainerType" entire="true"
                                         value="${deviceInfoTemplate.icontainerType}" list="{10:单开门,20:双开门}"/>
                        </div>
                    </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label">屏幕显示类型</label>
                            <div class="layui-input-inline">
                                <cang:select type="dic" groupNo="SP000175" entire="true"
                                             exp="datatype=\"*\" nullmsg=\"请选择屏幕显示类型\""
                                             value="${deviceInfoTemplate.iscreenDisplayType}" entireName="请选择屏幕显示类型"
                                             name="iscreenDisplayType" id="iscreenDisplayType"/>
                            </div>
                        </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label">压缩机类型</label>
                        <div class="layui-input-inline">
                            <cang:select type="list" name="icompressorType" id="icompressorType" entire="true"
                                         value="${deviceInfoTemplate.icompressorType}"
                                         list="{10:制冷,20:制热,30:制冷热,40:无压缩机}"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label">是否广告机</label>
                        <div class="layui-input-inline">
                            <cang:select type="list" name="iinstallAd" id="iinstallAd" entire="true"
                                         value="${deviceInfoTemplate.iinstallAd}"
                                         list="{0:否,1:是}"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label">设备投放场景</label>
                        <div class="layui-input-inline">
                            <cang:select type="dic" name="sputScenes" id="sputScenes" entire="true" laySearch="1"
                                         value="${deviceInfoTemplate.sputScenes}" groupNo="SP000143"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label">是否支持小屏幕AI设备</label>
                        <div class="layui-input-inline">
                            <cang:select type="list" name="isupportAi" id="isupportAi" entire="true"
                                         value="${deviceInfoTemplate.isupportAi}" list="{0:否,1:是}"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label">是否支持称重</label>
                        <div class="layui-input-inline">
                            <cang:select type="list" name="isupportWeigh" id="isupportWeigh" entire="true"
                                         value="${deviceInfoTemplate.isupportWeigh}" list="{0:否,1:是}"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label">电子秤允许浮动值</label>
                        <div class="layui-input-inline">
                            <input type="text" name="ielectronicFloat" id="ielectronicFloat"
                                   value="${deviceInfoTemplate.ielectronicFloat}"
                                   class="layui-input"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label">系统对接类型</label>
                        <div class="layui-input-inline">
                            <cang:select type="list" name="idockingType" id="idockingType"
                                         entire="true" value="${deviceInfoTemplate.idockingType}"
                                         list="{10:自主研发,20:第三方}"/>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label">是否开门购物中实时盘货</label>
                        <div class="layui-input-inline">
                            <cang:select type="list" name="iisOpeningInventory" id="iisOpeningInventory"
                                         entire="true" value="${deviceInfoTemplate.iisOpeningInventory}"
                                         list="{0:否,1:是}"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">备注</label>
                    <div class="layui-input-block">
                        <textarea id="sremark" name="sremark" class="layui-textarea layui-form-textarea80p"
                                  placeholder="备注（选填项）">${deviceInfoTemplate.sremark}</textarea>
                    </div>
                </div>
                <div class="layui-form-item fixed-bottom">
                    <div class="layui-input-block">
                        <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                        <button class="layui-btn" id="myFormSub">保存</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js" type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var form;
    layui.use(['form', 'layedit', 'element'], function () {

        $('.layui-form .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

        form = layui.form;

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

        $('.chosen-select').chosen();
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

