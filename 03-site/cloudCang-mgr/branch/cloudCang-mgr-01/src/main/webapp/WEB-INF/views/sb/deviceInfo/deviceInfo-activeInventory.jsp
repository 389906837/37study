<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备库存编辑</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?323" rel="stylesheet">
<style type="text/css">
    td input {
        width: 50px;
    }
    #error{padding: 50px 0;text-align: center;}
    .p1{
        font-size: 1.75rem;
    }
</style>
</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/device/info/saveActiveInventoryStock" method="post" id="myForm" onsubmit="return false;">
        <div class="wfsplitt">
            商品详情
        </div>
        <div class="layui-input-block mt15">
            <div class="clearfix layui-input-title">
                <button class="layui-btn" id="zdph">主动盘货</button>
                <input class="choose-btn" type="button" onclick="selectCommodity();" value="选择商品"/>
            </div>
            <div class="layui-row layui-col-space15">
                <div class="layui-card">
                    <div class="layui-card-body" id="searchResult">
                        <table class="layui-table layuiadmin-page-table " id="stock_table">
                            <thead>
                            <tr>
                                <th>商品编号</th>
                                <th>商品名称</th>
                                <th>商品单价(元)</th>
                                <th>商品数量</th>
                                <th style="width: 100px;">操作</th>
                            </tr>
                            </thead>
                            <tbody id="tbody">
                            <c:forEach var="item" items="${orderAuditCommodityVos}" varStatus="L">
                                <c:set var="commodityIds" value="${commodityIds}${item.scommodityId},"/>
                                <tr id="${item.scommodityId}">
                                    <td>${item.scommodityCode}</td>
                                    <td>${item.commodityFullName}</td>
                                    <td><input type="text" value="${item.fcommodityPrice}"
                                               name="fcommodityPrice1_${item.scommodityId}" disabled
                                               id="fcommodityPrice1_${item.id}"/></td>
                                    <td><input type="text" value="${item.forderCount}"
                                               name="fcommodityAmount_${item.scommodityId}"
                                               id="fcommodityAmount_${item.id}"/></td>
                                    <td><a href="javascript:void(0);"
                                           onclick="removeCommodity('${item.scommodityId}');">移除</a></td>
                                    <input type="hidden" value="${item.scommodityCode}"
                                           name="scommodityCode_${item.scommodityId}"
                                           id="scommodityCode_${item.scommodityId}"/>
                                    <input type="hidden" value="${item.scommodityId}"
                                           name="scommodityId_${item.scommodityId}"
                                           id="scommodityId_${item.scommodityId}"/>
                                    <input type="hidden" value="${item.fcommodityPrice}"
                                           name="fcommodityPrice_${item.scommodityId}"
                                           id="fcommodityPrice_${item.id}"/>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <div id="error">
                            <img src="${staticSource}/resources/images/error-express.png">
                            <p class="p1">页面出错了，请检查后重试...</p>
                        </div>
                    </div>

                </div>
            </div>

            <input type="hidden" value="${commodityIds}" id="commodityIds" name="commodityIds"/>
        </div>

        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <input type="hidden" value="${deviceId}" name="deviceId" id="deviceId"/>
                <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                <button class="layui-btn" lay-submit="" lay-filter="myFormSub">保存</button>
            </div>
        </div>

    </form>
</div>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js" type="text/javascript"></script>
<script type="text/javascript">
    $('#error').hide();
    function loadStock() {

        //加载中
        var loadIndex = loading();
        var isError = false;
        //定时两分钟执行
        var timer = setTimeout(function() {
            isError = true;
            $('#error').show();
            $('#stock_table').hide();
        }, 20000);
        $.ajax({
            type: "post",
            url:"${ctx}/device/info/sendDeviceStockMsg?deviceId="+ $('#deviceId').val(),
            dataType: "json",
            success: function(data){
                if (isError) return;
                if(data.success){
                    $.ajax({
                        type: "GET",
                        url: "${ctx}/device/info/getDeviceStock?deviceId="+ $('#deviceId').val(),
                        dataType: "json",
                        success: function(data) {
                            if (isError) return;
                            if(data.success) {
                                var ids = "";
                                $("#tbody").html("");//清楚表格body
                                for (var i = 0; i < data.data.length; i++) {
                                    var stock = data.data[i];
                                    var id = stock.commodityId;
                                    ids += id + ",";
                                    $('#stock_table').append(
                                        '<tr id=' + stock.commodityId + '>' +
                                        '<td>' + stock.commodityCode + '</td>' +
                                        '<td>' + stock.commodityFullName + '</td>' +
                                        '<td>' + stock.fcommodityPrice + '</td>' +
                                        '<td><input type="text" name="fcommodityAmount_' + id + '" id="fcommodityAmount' + id + '"  value="'+stock.num+'" /></td>' +
                                        '<input type="hidden" name="fcommodityPrice_' + id + '" id="fcommodityPrice_' + id + '"  value="' + stock.fcommodityPrice + '"  />' +
                                        '<input type="hidden" value="' + stock.commodityCode + '" name = "scommodityCode_' + id + '" / > ' +
                                        '<input type="hidden" value="' + id + '"  name = "scommodityId_' + id + '" / > ' +
                                        '<td><a href="javascript:void(0);"\n' +
                                        'onclick="removeCommodity(\'' + stock.commodityId + '\');">移除</a></td>' +
                                        '</tr>')
                                }
                                $('#error').hide();
                                $('#stock_table').show();
                                if (!isEmpty(ids)) {
                                    $("#commodityIds").val(ids);
                                }
                            } else {
                                $('#error').show();
                                $('#stock_table').hide();
                            }
                        },
                        error: function() {
                            $('#error').html("出现错误").show();
                            $('#stock_table').hide();
                        },
                        complete: function() {
                            //关闭加载中
                            closeLayer(loadIndex);
                            clearTimeout(timer);
                        }
                    })
                }else{
                    $('#error').html("出现错误：" + data.msg).show();
                    $('#stock_table').hide();
                    closeLayer(loadIndex);
                    clearTimeout(timer);
                }
            },
            error:function () {
                //关闭加载中
                closeLayer(loadIndex);
                $('#error').show();
                $('#stock_table').hide();
                clearTimeout(timer);
            }
        });
    }

    $('#zdph').on('click',function () {
        loadStock();
    })



    var index = parent.layer.getFrameIndex(window.name); //获取窗口索

    layui.use(['form', 'layedit', 'laydate'], function () {
        var form = layui.form;
        //监听提交
        form.on('submit(myFormSub)', function () {
            var commodityIds = $("#commodityIds").val();
            if (isEmpty(commodityIds)) {
                alertDel("请选择商品！");
                return false;
            }
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
        });
    });
    $(function () {
        //返回
        $("#cancelBtn").click(function () {
            parent.layer.close(index);
            return false;
        });
    });


    function selectCommodity() {
        var commodityIds = $("#commodityIds").val();
        if (!isEmpty(commodityIds)) {
            showLayer("选择商品", ctx + "/common/selectCommodityByMap?commodityIds=" + commodityIds + "&deviceId=${orderRecord.sdeviceId}", {area: ['90%', '80%']});
        } else {
            showLayer("选择商品", ctx + "/common/selectCommodityByMap?deviceId=${orderRecord.sdeviceId}", {area: ['90%', '80%']});
        }
    }


    function confirmSelectCommodity(commodityIds, commodityMap, deleteCommodityIds) {
        $("#commodityIds").val(commodityIds);
        if (!isEmpty(commodityIds)) {
            var tempIds = commodityIds.substr(0, commodityIds.length - 1);
            var arr = tempIds.split(",");
            var html = "";
            for (var i = 0; i < arr.length; i++) {
                if (isEmpty($("#" + arr[i]).attr("id"))) {
                    var tempData = commodityMap[arr[i]];
                    html += '<tr id="' + arr[i] + '">' +
                        '<td>' + tempData.scode + '</td>' +
                        '<td>' + tempData.commodityFullName + '</td>' +
                        '<td><input type="text" name="fcommodityPrice1_' + arr[i] + '" id="fcommodityPrice1_' + arr[i] + '"  value="' + tempData.fsalePrice + '"  disabled /></td>' +
                        '<td><input type="text" name="fcommodityAmount_' + arr[i] + '" id="fcommodityAmount' + arr[i] + '"  value="0" /></td>' +
                        '<input type="hidden" name="fcommodityPrice_' + arr[i] + '" id="fcommodityPrice_' + arr[i] + '"  value="' + tempData.fsalePrice + '"  />' +
                        '<input type="hidden" value="' + tempData.scode + '" name = "scommodityCode_' + arr[i] + '" / > ' +
                        '<input type="hidden" value="' + arr[i] + '"  name = "scommodityId_' + arr[i] + '" / > ' +
                        '<td><a href="javascript:void(0);" onclick="removeCommodity(\'' + arr[i] + '\')">移除</a></td>' +
                        '</tr>';
                }
            }
            if (!isEmpty(html)) {
                $("#tbody").append(html);
            }
            //删除原有没选择的
            if (!isEmpty(deleteCommodityIds)) {
                tempIds = deleteCommodityIds.substr(0, deleteCommodityIds.length - 1);
                arr = tempIds.split(",");
                for (var i = 0; i < arr.length; i++) {
                    deleteCommodity(arr[i]);
                }
            }
        } else {
            $("#tbody").html("");//清楚表格body
        }
    }

    function removeCommodity(commodityId) {
        confirmTip("确定删除此商品?", function () {
            $("#" + commodityId).remove();
            var commodityIds = $("#commodityIds").val();//获取当前商品集合
            $("#commodityIds").val(commodityIds.replace(commodityId + ",", ""));//删除当前商品
        });
    }
    function deleteCommodity(commodityId) {
        $("#" + commodityId).remove();
        var commodityIds = $("#commodityIds").val();//获取当前商品集合
        $("#commodityIds").val(commodityIds.replace(commodityId + ",", ""));//删除当前商品
    }
</script>

</body>
</html>





