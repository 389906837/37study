<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>批量导入视觉商品</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?20180408" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<form class="layui-form" action="${ctx}/commoditySku/batchImportVrCommondityInfo" method="post" id="myForm" enctype="multipart/form-data">
    <div class="col-sm-12">
        <div class="ibox-content m-t">
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <%--<label class="layui-form-label">excel文件名</label>--%>
                    <div class="layui-input-inline">
                        <button type="button" class="layui-btn" id="commonImgButton">选择上传文件</button>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"></label>
                    <div class="layui-input-inline">
                        <a href="${staticSource}/resources/images/vrBatchImport.xlsx" download="批量导入视觉商品模板Excel07">批量导入视觉商品模板Excel2007下载</a>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-col-md6">
                    <label class="layui-form-label"></label>
                    <div class="layui-input-inline">
                        <a href="${staticSource}/resources/images/vrBatchImport.xls" download="批量导入视觉商品模板Excel03">批量导入视觉商品模板Excel2003下载</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="layui-form-item fixed-bottom">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
            <button class="layui-btn" lay-submit="" lay-filter="myFormSub">确认</button>
        </div>
    </div>
</form>


<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<script src="${staticSource}/resources/js/jsrender.min.js"  type="text/javascript"></script>
<script src="${staticSource}/resources/js/jsrender.converters.js"  type="text/javascript"></script>
<script src="${staticSource}/resources/js/validform_min.js"  type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var form;
    layui.use(['form', 'layedit', 'laydate', 'upload','element'], function () {
        form = layui.form;
        // Excel文件上传
        var $ = layui.jquery, upload = layui.upload;
        upload.render({
            auto: false
            , elem: '#commonImgButton'
            , multiple: false
            , field: "file"
            , size: 2000000
            , accept: 'file'
            , exts: 'xls|xlsx'
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
                            var params = {
                                icon: 2,
                                time: 300000 //3s后自动关闭
                            };
                            alertDel(msg.msg,params);
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



