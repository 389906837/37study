<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title></title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="layui-form-item">
                    <h4>已选商品</h4>
                    <table class="layui-table" id="deviceTable">
                        <colgroup>
                            <col width="300">
                            <col width="300">
                            <%--<col width="250">--%>
                        </colgroup>
                        <thead>
                        <tr>
                            <th>商品编号</th>
                            <th>商品名称</th>
                            <%--<th>设备地址</th>--%>
                        </tr>
                        </thead>
                        <tbody id="deviceBody">
                        <c:forEach items="${commodityInfoList}" var="vo">
                            <tr>
                                <td>${vo.scode}</td>
                                <td>${vo.sname}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="ibox-content">
                    <form class="layui-form" action="${ctx}/commodity/commodityBatch/addToCommodityBatch" method="post"
                          id="myForm">
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label">生产日期</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="dproduceDate" datatype="*" nullmsg="" id="dproduceDate"
                                           value="" class="layui-input"/>
                                </div>
                            </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label">商品数量</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="" datatype="*" nullmsg="" id="icommodityNum"
                                           value="" class="layui-input"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label">成本价</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="fcostAmount" datatype="*" nullmsg="" id="fcostAmount"
                                           value="" class="layui-input"/>
                                </div>
                            </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label">税点</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="ftaxPoint" datatype="*" nullmsg="" id="ftaxPoint"
                                           value="" class="layui-input"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item fixed-bottom">
                            <div class="layui-input-block">
                                <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                                <button class="layui-btn" id="myFormSub">保存</button>
                            </div>
                        </div>
                        <input id="commodityIds" name="commodityIds" value="${sid}" type="hidden"/>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js"  type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var form;
    layui.use(['form', 'layedit', 'laydate', 'upload'], function () {
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

        layui.use('laydate', function(){
            var laydate = layui.laydate;
            //生产日期
            laydate.render({
                elem: '#dproduceDate',
                type: 'datetime'
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



