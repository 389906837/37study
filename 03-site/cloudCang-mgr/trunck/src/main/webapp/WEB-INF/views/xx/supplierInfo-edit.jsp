<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title>短信供应商编辑</title>
    <link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
    <link href="${staticSource}/resources/css/override.css?1" rel="stylesheet">

</head>

<body>
    <div class="ibox-content">
        <form class="layui-form" action="${ctx}/supplierInfo/save" method="post" id="myForm">
            <input type="hidden" id="smerchantId" name="smerchantId" value="${supplierInfo.smerchantId}"/><%--商户ID--%>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">商户名称</label>
                    <div class="layui-input-inline">
                        <input type="text" name="smerchantName" placeholder="必填..." datatype="*" nullmsg="请选择商户名称"
                               id="smerchantName" value="${merchantInfo.sname}" class="layui-input"/>
                    </div>
                    <button class="layui-btn" id="btn1" style="display: inline-block;float: left;"
                            type="button" data-type="sel">选择
                    </button>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label" style="width: 100px">商户编号</label>
                    <div class="layui-input-inline">
                        <input type="text" name="smerchantCode" id="smerchantCode" value="${merchantInfo.scode}" style="width: 206px"
                               class="layui-input" disabled/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">编号</label>
                    <div class="layui-input-inline">
                        <input type="text" name="scode" id="scode" value="${supplierInfo.scode}" class="layui-input" disabled/>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label">名称</label>
                    <div class="layui-input-inline">
                        <input type="text" name="sname" id="sname" value="${supplierInfo.sname}" datatype="*" nullmsg="请输入供应商名称" class="layui-input" />
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">供应商类型</label>
                <div class="layui-input-inline">
                    <cang:select type="list" id="itype" name="itype" value="${supplierInfo.itype}" list="{1:短信,2:邮箱}" entire="true" exp="datatype=\"*\" nullmsg=\"请选择供应商类型\"" />
                </div>
            </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label">渠道作用</label>
                    <div class="layui-input-inline">
                        <cang:select type="list" id="iintention" name="iintention" value="${supplierInfo.iintention}" list="{1:营销,2:非营销}" entire="true" exp="datatype=\"*\" nullmsg=\"请选择渠道作用\"" />
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">账户名</label>
                <div class="layui-input-inline">
                    <input type="text" name="saccName" id="saccName" value="${supplierInfo.saccName}" datatype="*6-16" errormsg="账户名必须由6-16任意字符组成" class="layui-input" />
                </div>
            </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label">密码</label>
                    <div class="layui-input-inline">
                        <input type="password" name="saccPassword" id="saccPassword" value="${supplierInfo.saccPassword}" datatype="/^[A-Za-z0-9]{6,16}$/"  errormsg="密码必须由6-16位字母、数字组成！" class="layui-input" />
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label">扩展信息</label>
                    <div class="layui-input-inline">
                        <input type="text" name="sextInfo" id="sextInfo" value='${supplierInfo.sextInfo}' datatype="*" nullmsg="请输入扩展信息" class="layui-input" />
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label" >描述</label>
                <div class="layui-input-inline">
                    <textarea class="layui-textarea" id="sdesc" name="sdesc" style="width: 600px">${supplierInfo.sdesc}</textarea>
                </div>
            </div>
            </div>
            <div class="layui-form-item">
            <div class="layui-form-item fixed-bottom">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                    <button class="layui-btn" id="myFormSub">保存</button>
                </div>
            </div>
            </div>
            <input type="hidden" id="id" name="id" value="${supplierInfo.id}" />
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

        //选择商户
        var $ = layui.$, active = {
            sel: function(){
                showLayerMax("选择商户", ctx+"/merchantInfo/radioSelectList");
            }
        };
        $('.layui-form .layui-btn').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

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

    function selectMerchant(merchantId,merchantCode,merchantName) {
        $("#smerchantId").val(merchantId);//商户ID
        $("#smerchantCode").val(merchantCode);//商户编号
        $("#smerchantName").val(merchantName);//商户名称
    }
</script>
</body>
</html>

