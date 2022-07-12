<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>信息配置</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?1.111" rel="stylesheet">

</head>

<body>
<div class="ibox-title">
    <h5>客户端配置</h5>
</div>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/merchantClientConf/update" method="post" id="myForm"
          enctype="multipart/form-data" onsubmit="return false;">
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label" style="width: 150px">商户简称</label>
                <div class="layui-inline">
                    <input type="text" name="sshortName" datatype="*" nullmsg="请输入商户简称" id="sshortName"
                           value="${merchantClientConf.sshortName}" class="layui-input"/>
                </div>
            </div>
            <%--</div>
            <div class="layui-form-item">--%>
            <div class="layui-col-md6">
                <label class="layui-form-label" style="width: 150px">客服联系电话</label>
                <div class="layui-inline">
                    <input type="text" name="scontactMobile" datatype="*" nullmsg="请输入商户联系电话" id="scontactMobile"
                           value="${merchantClientConf.scontactMobile}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label" style="width: 150px">客服服务时间</label>
                <div class="layui-inline">
                    <input type="text" name="scustomerServiceTime" datatype="*" nullmsg="请输入客服服务时间"
                           id="scustomerServiceTime"
                           value="${merchantClientConf.scustomerServiceTime}" class="layui-input"/>
                </div>
            </div>
            <%-- </div>
             <div class="layui-form-item">--%>
            <div class="layui-col-md6">
                <label class="layui-form-label" style="width: 150px">客户端标题</label>
                <div class="layui-inline">
                    <input type="text" name="stitle" datatype="*" nullmsg="请输入客户端标题" id="stitle"
                           value="${merchantClientConf.stitle}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label" style="width: 150px">首页提示语</label>
                <div class="layui-inline">
                    <input type="text" name="sindexHint" datatype="*" nullmsg="请输入首页提示语" id="sindexHint"
                           value="${merchantClientConf.sindexHint}" class="layui-input"/>
                </div>
            </div>
            <%-- </div>
             <div class="layui-form-item">--%>
            <div class="layui-col-md6">
                <label class="layui-form-label" style="width: 150px">开门成功提示语</label>
                <div class="layui-inline">
                    <input type="text" name="ssuccessHint" datatype="*" nullmsg="请输入开门成功提示语" id="ssuccessHint"
                           value="${merchantClientConf.ssuccessHint}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label" style="width: 150px">云端识别AppId</label>
                <div class="layui-inline">
                    <input type="text" name="scloudAppId" id="scloudAppId"
                           value="${merchantClientConf.scloudAppId}" class="layui-input"/>
                </div>
            </div>
            <%--   </div>
               <div class="layui-form-item ">--%>
            <div class="layui-col-md6">
                <label class="layui-form-label">云端补货是否开启实时盘货</label>
                <div class="layui-input-inline">
                    <cang:select type="list" list="{0:否,1:是}" id="iisOpeningInventory"
                                 value="${merchantClientConf.iisOpeningInventory}"
                                 name="iisOpeningInventory"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item ">
            <div class="layui-col-md6">
                <label class="layui-form-label">本地补货是否开启实时盘货</label>
                <div class="layui-input-inline">
                    <cang:select type="list" list="{0:否,1:是}" id="iisLocalInventory"
                                 value="${merchantClientConf.iisLocalInventory}"
                                 name="iisLocalInventory"/>
                </div>
            </div>
            <%--   </div>
               <div class="layui-form-item ">--%>
            <div class="layui-col-md6">
                <label class="layui-form-label">代扣方式</label>
                <div class="layui-input-inline">
                    <cang:select type="list" list="{10:商户代扣,20:单次代扣}" id="iwithholdingWay"
                                 value="${merchantClientConf.iwithholdingWay}"
                                 name="iwithholdingWay"/>
                </div>
            </div>
        </div>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT_PARENT ne 1}">
            <div class="layui-form-item ">
                <div class="layui-col-md6">
                    <label class="layui-form-label">分账功能是否开启</label>
                    <div class="layui-input-inline">
                        <cang:select type="list" list="{0:否,1:是}" id="iisSeparateAccounts"
                                     value="${merchantClientConf.iisSeparateAccounts}"
                                     name="iisSeparateAccounts"/>
                    </div>
                </div>
                    <%-- </div>
                     <div class="layui-form-item">--%>
                <div class="layui-col-md6">
                    <label class="layui-form-label" style="width: 150px">分账比例(0-100)%</label>
                    <div class="layui-inline">
                        <input type="text" name="separateAccountsPro" id="separateAccountsPro"
                               value="${merchantClientConf.separateAccountsPro}" class="layui-input"/>
                    </div>
                </div>
            </div>
        </c:if>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">补货是否需要确认补货单</label>
                <div class="layui-input-inline">
                    <cang:select type="list" list="{0:否,1:是}" id="iisReplenConfirm"
                                 value="${merchantClientConf.iisReplenConfirm}"
                                 name="iisReplenConfirm"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"> 云端识别访问地址</label>
                <div class="layui-input-block">
                    <textarea id="scloudHost" name="scloudHost" class="layui-textarea layui-form-textarea80p"
                              placeholder="请输入云端识别访问地址">${merchantClientConf.scloudHost}</textarea>
                </div>
            </div>
        </div>
        <div class="layui-form-item mt13">
            <div class="layui-col-md6 ">
                <label class="layui-form-label" style="width: 150px"></label>
                <%--<div class="layui-input-inline inline-150">--%>
                <div class="layui-inline">
                    <button type="button" class="layui-btn" id="slogoBut">商户logo</button>
                    <blockquote class="layui-elem-quote layui-quote-nm layui-quote-nm-1">
                        预览图
                        <div class="layui-upload-list" id="slogoYl">
                            <c:if test="${!empty  merchantClientConf.slogo}">
                                <img src="${dynamicSource}${merchantClientConf.slogo}"
                                     style="width: 100%;height: 100%"/>
                            </c:if>
                            <c:if test="${empty merchantClientConf.slogo}">
                                <img src="${staticSource}/resources/images/37cang.png"
                                     style="width: 100%;height: 100%"/>
                            </c:if>
                        </div>
                    </blockquote>
                    <span class="require">(180*90像素,白色背景，低于200kb的jpg或png图片)</span>
                </div>
            </div>
        <%--</div>
        <div class="layui-form-item">--%>
            <div class="layui-col-md6 ">
                <label class="layui-form-label" style="width: 150px"></label>
                <%--<div class="layui-input-inline inline-150">--%>
                <div class="layui-inline">
                    <button type="button" class="layui-btn" id="loginLogoBut">商户客户端Logo</button>
                    <blockquote class="layui-elem-quote layui-quote-nm layui-quote-nm-1">
                        预览图
                        <div class="layui-upload-list" id="loginslogoYl">
                            <c:if test="${!empty  merchantClientConf.sloginLogo}">
                                <img src="${dynamicSource}${merchantClientConf.sloginLogo}"
                                     style="width: 100%;height: 100%"/>
                            </c:if>
                            <c:if test="${empty merchantClientConf.sloginLogo}">
                                <img src="${staticSource}/resources/images/37cang.png"
                                     style="width: 100%;height: 100%"/>
                            </c:if>
                        </div>
                    </blockquote>
                    <span class="require">(180*90像素,白色背景，低于200kb的jpg或png图片)</span>
                </div>
            </div>
        </div>


        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <shiro:hasPermission name="MERCHANT_REFRESHCACHE">
                    <button class="layui-btn layui-btn-primary" data-type="refreshCache">更新缓存</button>
                </shiro:hasPermission>
                <button class="layui-btn" id="myFormSub">保存</button>
            </div>
        </div>
        <input type="hidden" id="id" name="id" value="${merchantClientConf.id}"/>
        <input type="hidden" id="scode" name="scode" value="${merchantClientConf.scode}"/>
    </form>
</div>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js" type="text/javascript"></script>
<script type="text/javascript">
    var form;
    layui.use(['form', 'layedit', 'laydate', 'upload'], function () {
        form = layui.form;

        //商户logo图片上传start
        var $ = layui.jquery, upload = layui.upload;
        upload.render({
            elem: '#slogoBut',
            fileSize: 200,
            auto: false,
            multiple: false,
            field: "file",
            accept: 'images',
            exts: 'jpg|png',
            choose: function (obj) {
                obj.preview(function (index, file, result) {
                    $("#slogoYl").find("img").attr("src", result);
                });
            }
        });
        //图片上传end

        //登录logo图片上传start
        var $ = layui.jquery, upload = layui.upload;
        upload.render({
            elem: '#loginLogoBut',
            fileSize: 200,
            auto: false,
            multiple: false,
            field: "loginLogofile",
            accept: 'images',
            exts: 'jpg|png',
            choose: function (obj) {
                obj.preview(function (index, file, result) {
                    $("#loginslogoYl").find("img").attr("src", result);
                });
            }
        });

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
                            alertInfo("操作成功");
                        } else {
                            alertDel(msg.msg);
                        }
                    }
                });
                return false;
            }
        });
    });


    layui.use(['form', 'laydate'], function () {
        var $ = layui.$, active = {
            refreshCache: function () {//刷新缓存
                var loadIndex = loading();
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: ctx + '/merchantInfo/cache',
                    success: function (msg) {
                        closeLayer(loadIndex);//关闭加载框
                        if (msg.success) {
                            alertInfo(msg.data);
                        } else {
                            alertDel(msg.data);
                        }
                    }
                });
                return false;
            }
        };
        $('.layui-form .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });

    //    $(function() {
    //        $("#cancelBtn").click(function() {
    //            layer.close(index);
    //            return false;
    //        });
    //    });
</script>
</body>
</html>



