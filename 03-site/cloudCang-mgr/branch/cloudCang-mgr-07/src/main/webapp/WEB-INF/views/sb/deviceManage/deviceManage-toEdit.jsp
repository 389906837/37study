<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>编辑设备责任人信息</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/device/manage/edit" method="post" id="myForm">
        <div class="layui-form-item">
            <%--<div class="layui-col-md6">
                <label class="layui-form-label">设备编号</label>
                <label class="layui-form-label" style="text-align: left;">${deviceManageDomain.scode}</label>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">设备名称</label>
                <label class="layui-form-label"style="text-align: left;">${deviceManageDomain.sname}</label>
            </div>
            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                <div class="layui-col-md6">
                    <label class="layui-form-label">商户编号</label>
                    <label class="layui-form-label" style="text-align: left;">${deviceManageDomain.merchantName}</label>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label">商户名称</label>
                    <label class="layui-form-label" style="text-align: left;">${deviceManageDomain.merchantCode}</label>
                </div>
            </c:if>--%>
            <div class="layui-col-md6">
                <label class="layui-form-label">所属区域</label>
                <div class="layui-input-inline">
                    <cang:select type="dic" name="sareaCode" id="sareaCode" entire="true" exp="datatype=\"*\" nullmsg=\"请选择所属区域\""
                                 value="${deviceManageDomain.sareaCode}" groupNo="SP000145"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">区域负责人</label>
                <div class="layui-input-inline">
                    <input type="text" name="sareaPrincipal"  id="sareaPrincipal" value="${deviceManageDomain.sareaPrincipal}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">区域负责人联系方式</label>
                <div class="layui-input-inline">
                    <input type="text" name="sareaPrincipalContact"  id="sareaPrincipalContact" value="${deviceManageDomain.sareaPrincipalContact}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">设备负责人</label>
                <div class="layui-input-inline">
                    <input type="text" name="sdevicePrincipal"  id="sdevicePrincipal" value="${deviceManageDomain.sdevicePrincipal}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">设备负责人联系方式</label>
                <div class="layui-input-inline">
                    <input type="text" name="sdevicePrincipalContact"  id="sdevicePrincipalContact" value="${deviceManageDomain.sdevicePrincipalContact}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">设备维护人姓名</label>
                <div class="layui-input-inline">
                    <input type="text" name="smaintain"  id="smaintain" value="${deviceManageDomain.smaintain}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">维护人联系方式</label>
                <div class="layui-input-inline">
                    <input type="text" name="smaintainContact"  id="smaintainContact" value="${deviceManageDomain.smaintainContact}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">补货员姓名</label>
                <div class="layui-input-inline">
                    <input type="text" name="sreplenishment"  id="sreplenishment" value="${deviceManageDomain.sreplenishment}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">补货员联系方式</label>
                <div class="layui-input-inline">
                    <input type="text" name="sreplenishmentContact"  id="sreplenishmentContact" value="${deviceManageDomain.sreplenishmentContact}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">备注</label>
                <div class="layui-input-inline">
                    <textarea id="sremark" name="sremark" class="layui-textarea" placeholder="非必填项" style="width: 735px">${deviceManageDomain.sremark}</textarea>
                </div>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                <button class="layui-btn" id="myFormSub">保存</button>
            </div>
        </div>
        <input type="hidden"  name="id" id="mdcId" value="${deviceManageDomain.id}" class="layui-input" />
        <input type="hidden"  name="sdeviceId" id="sdeviceId" value="${deviceManageDomain.sdeviceId}" class="layui-input" />
    </form>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js"  type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var form;
    layui.use(['form', 'layedit', 'laydate'], function () {
        form = layui.form;


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

