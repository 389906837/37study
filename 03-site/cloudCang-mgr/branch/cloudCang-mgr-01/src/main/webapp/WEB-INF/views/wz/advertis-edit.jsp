<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>广告区域编辑</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?1" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/advertis/save" method="post" id="myForm" enctype="multipart/form-data">
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">标题</label>
                <div class="layui-input-inline">
                    <input type="text" name="stitle" id="stitle" value="${advertis.stitle}" datatype="*"
                           nullmsg="请输入标题"
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">投放状态</label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="istatus" id="istatus" layFilter="discountSelect"
                                 value="${advertis.istatus}" list="{2:待投放,1:投放中,0:已过期,3:暂停}"
                                 exp="datatype=\"*\" nullmsg=\"请选择投放状态\""
                                 entireName="请选择投放状态" entire="true"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">开始日期</label>
                <div class="layui-input-inline">
                    <input type="text" name="tstartDate" datatype="*" nullmsg="请选择开始日期" id="tstartDate"
                           value="<fmt:formatDate value='${advertis.tstartDate}' pattern='yyyy-MM-dd HH:mm:ss'/>"
                           class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">结束日期</label>
                <div class="layui-input-inline">
                    <input type="text" name="tendDate" datatype="*" nullmsg="请选择结束日期" id="tendDate"
                           value="<fmt:formatDate value='${advertis.tendDate}' pattern='yyyy-MM-dd HH:mm:ss'/>"
                           class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">广告类型</label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="iadvType" id="iadvType" layFilter="discountSelect"
                                 value="${advertis.iadvType}" list="{10:图片,20:音频,30:视频}"
                                 exp="datatype=\"*\" nullmsg=\"请选择广告类型\""
                                 entireName="请选择广告类型" entire="true"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">排序号</label>
                <div class="layui-input-inline">
                    <input type="text" name="isort" id="isort" value="${advertis.isort}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-col-md6">
            <label class="layui-form-label">说明</label>
            <div class="layui-input-inline">
                    <textarea style="width: 593px" class="layui-textarea" id="sremark"
                              name="sremark">${advertis.sremark}</textarea>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6 ">
                        <label class="layui-form-label"></label>
                        <div class="layui-inline">
                            <button type="button" class="layui-btn" id="advertisBtn">广告图片上传</button>
                            <blockquote class="layui-elem-quote layui-quote-nm layui-quote-nm-1">
                                预览图
                                <div class="layui-upload-list" id="slogoYl">
                                    <c:if test="${!empty  advertis.scontentUrl}">
                                        <img src="${dynamicSource}${advertis.scontentUrl}" style="width: 100%;height: 100%" />
                                    </c:if>
                                    <c:if test="${empty advertis.scontentUrl}">
                                        <img src="${staticSource}/resources/images/37cang.png" style="width: 100%;height: 100%" />
                                    </c:if>
                                </div>
                            </blockquote>
                            <span class="require">(1920*1080像素,低于2000kb的jpg或者png图片)</span>
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
        <input type="hidden" id="id" name="id" value="${advertis.id}"/>
        <input type="hidden" id="sregionId" name="sregionId" value="${advertis.sregionId}"/>
    </form>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js" type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate', 'upload'], function () {
        //执行一个laydate实例
        var laydate = layui.laydate;
        var nowTime = new Date().valueOf();
        var min = null;
        var max = null;

        var start = laydate.render({
            elem: '#tstartDate',
            type: 'datetime',
            min: nowTime,
//            btns: ['clear','now','confirm'],
            done: function(value, date){
                endMax = end.config.max;
                end.config.min = date;
                end.config.min.month = date.month -1;
                if(!isEmpty(value)) { //监听日期被切换
                    $("#tstartDate").removeClass("Validform_error");
                    $("#tstartDate").parent().find("span").hide();
                } else {
                    $("#tstartDate").addClass("Validform_error");
                    $("#tstartDate").parent().find("span").html($("#tstartDate").attr("nullmsg"));
                    $("#tstartDate").parent().find("span").removeClass("Validform_right");
                    $("#tstartDate").parent().find("span").addClass("Validform_wrong");
                    $("#tstartDate").parent().find("span").show();
                }
            }
        });
        var end = laydate.render({
            elem: '#tendDate',
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
                    $("#tendDate").removeClass("Validform_error");
                    $("#tendDate").parent().find("span").hide();
                } else {
                    $("#tendDate").addClass("Validform_error");
                    $("#tendDate").parent().find("span").html($("#tendDate").attr("nullmsg"));
                    $("#tendDate").parent().find("span").removeClass("Validform_right");
                    $("#tendDate").parent().find("span").addClass("Validform_wrong");
                    $("#tendDate").parent().find("span").show();
                }
            }
        });

        //图片上传start
        var $ = layui.jquery, upload = layui.upload;
        upload.render({
            elem:'#advertisBtn',
            auto: false,
            field: "photoFile",
            multiple: false,
            size: 2000,
            accept: 'images',
            exts: 'jpg|png',
            choose: function(obj){
                obj.preview(function(index, file, result){
                    $("#slogoYl").find("img").attr("src", result);
                });
            }
        });
        //图片上传end


        //选择商户
        var $ = layui.$, active = {
            sel: function () {
                showLayer("选择商户", ctx + "/merchantInfo/radioSelectList", {area: ['90%', '80%']});
            }
        };
        $('.layui-form .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

        var form = layui.form;
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
    });
    $(function () {
        $("#cancelBtn").click(function () {
            parent.layer.close(index);
            return false;
        });
    });


    function selectMerchant(merchantId, merchantCode, merchantName) {
        $("#smerchantId").val(merchantId);//商户ID
        $("#smerchantCode").val(merchantCode);//商户编号
        $("#smerchantName").val(merchantName);//商户名称
    }
</script>
</body>
</html>

