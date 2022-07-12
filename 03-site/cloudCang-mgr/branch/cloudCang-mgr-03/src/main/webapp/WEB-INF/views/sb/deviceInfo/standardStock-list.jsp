<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title></title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?20180408" rel="stylesheet">
<style type="text/css">
    .layui-form-label {width: 180px;}
    fieldset {
        padding: .35em .625em .75em;
        margin: 0 2px;
        border: 1px solid #5FB878;
    }
    legend {
        font-size: 22px;
        line-height: inherit;
        color: #333;
        border: 0;
        width: auto;
    }
</style>
</head>
<body>
<div class="ibox-content">
    <div class="layui-tab layui-tab-brief" style="margin: 0px;">
        <div class="layui-tab-item layui-show">
            <form class="layui-form" action="${ctx}/device/standardStock/operCommodity" method="post" id="myForm">

                <%--<fieldset>--%>
                    <%--<legend><spring:message code='sb.device.information' />:</legend>--%>
                <%--</fieldset>--%>
                <div class="wfsplitt">
                    <spring:message code='sb.device.information' />
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="main.device.name" />:</label>
                        <label class="layui-form-label" style="text-align: left;">${deviceInfoDomain.sname}</label>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="main.device.id" />:</label>
                        <label class="layui-form-label" style="text-align: left;">${deviceInfoDomain.scode}</label>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code="tpinfotoadd.equipment.model" />:</label>
                        <label class="layui-form-label" style="text-align: left;">${deviceInfoDomain.sdeviceModel}</label>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code='sb.address'/>:</label>
                        <label class="layui-form-label" style="text-align: left;width: auto">${deviceInfoDomain.address}</label>
                    </div>
                </div>
                <div class="wfsplitt">
                    <spring:message code='sb.standard.inventory.function' />
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label"><spring:message code='sb.standard.inventory.function' /></label>
                        <div class="layui-input-inline">
                            <input type="radio" name="istatus" lay-filter="standardStockStatus" value="20" <c:if
                                    test="${(empty standardStock.istatus ? 20 : standardStock.istatus) eq 20}"> checked</c:if>
                                   title='<spring:message code="main.close" />'/>
                            <input type="radio" name="istatus" lay-filter="standardStockStatus" value="10" <c:if
                                    test="${standardStock.istatus eq 10}"> checked</c:if>
                                   title="<spring:message code='sh.open' />"/>
                        </div>
                    </div>
                </div>

                <div id="standardStockDetail" <c:if test="${empty standardStock.istatus || standardStock.istatus eq 20}">style="display:none;"</c:if>>
                    <div class="wfsplitt">
                        <spring:message code='sb.commodity.standard.stock.information' />
                    </div>
                    <c:forEach begin="1" end="${ilayerNum}" var="layerNum">
                        <div class="layui-input-block mt15">
                           <div class="clearfix layui-input-title"><span><spring:message code='sb.device.stock.floor' />:${layerNum}</span> <input class="choose-btn" type="button" onclick="selectCommodity(${layerNum});" value="<spring:message code='sb.select.product' />" /></div>
                            <table class="layui-table" id="commodityTable_layer${layerNum}">
                                <thead>
                                <tr>
                                    <th><spring:message code="main.product.number" /></th>
                                    <th><spring:message code="main.product.name" /></th>
                                    <th><spring:message code='sb.standard.stock.quantity' /></th>
                                    <th><spring:message code='sm.warning.value.of.inventory' /></th>
                                    <th><spring:message code="main.operation" /></th>
                                </tr>
                                </thead>
                                <tbody id="tbody_${layerNum}">
                                    <c:set var="commodityIds" value="" />
                                    <c:forEach var="item" items="${detailList[layerNum-1]}" varStatus="L">
                                        <c:set var="commodityIds" value="${commodityIds}${item.scommodityId},"/>
                                        <tr id="${item.scommodityId}_${layerNum}">
                                            <td>${item.scommodityCode}</td>
                                            <td>${item.commodityFullName}</td>
                                            <td><input type="text" value="${item.istandardStock}" name="standardStock_${item.scommodityId}_${layerNum}" onkeyup="changeValue('${item.scommodityId}',${layerNum});" id="standardStock_${item.scommodityId}_${layerNum}" /></td>
                                            <td><input type="text" value="${item.iminSillValue}" name="minSillValue_${item.scommodityId}_${layerNum}" onkeyup="changeValue1('${item.scommodityId}',${layerNum});" id="minSillValue_${item.scommodityId}_${layerNum}" /></td>
                                            <td><a href="javascript:void(0);" onclick="removeCommodity('${item.scommodityId}',${layerNum});"><spring:message code='sb.remove' /></a></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            <input type="hidden" value="${commodityIds}" id="commodityIds_${layerNum}" name="commodityIds_${layerNum}"  />
                        </div>
                    </c:forEach>
                </div>

                <div class="layui-form-item fixed-bottom">
                    <div class="layui-input-block">
                        <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
                        <button class="layui-btn" lay-submit="" lay-filter="myFormSub"><spring:message code="main.save" /></button>
                    </div>
                </div>
                <input type="hidden" id="sdeviceId" name="sdeviceId" value="${deviceInfoDomain.id}"/>
                <input type="hidden" id="sdeviceCode" name="sdeviceCode" value="${deviceInfoDomain.scode}"/>
                <input type="hidden" id="id" name="id" value="${standardStock.id}"/>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'element'], function () {
        var form = layui.form;
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
                            'index':index
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
        //标准库存开启关闭
        form.on('radio(standardStockStatus)', function(data){
            if (data.value == 20) {//关闭标准库存
                $("#standardStockDetail").hide();
            }else {
                $("#standardStockDetail").show();
            }
        });
    });
    $(function () {
        //返回
        $("#cancelBtn").click(function () {
            parent.layer.close(index);
            return false;
        });
    });

    function removeCommodity(commodityId, layerNum) {
        confirmTip('<spring:message code="main.delete.product" />',function () {
            $("#" + commodityId + "_" + layerNum).remove();
            var commodityIds = $("#commodityIds_" + layerNum).val();//获取当前商品集合
            $("#commodityIds_" + layerNum).val(commodityIds.replace(commodityId + ",", ""));//删除当前商品
        });
    }

    function deleteCommodity(commodityId, layerNum) {
        $("#"+commodityId+"_"+layerNum).remove();
        var commodityIds = $("#commodityIds_"+layerNum).val();//获取当前商品集合
        $("#commodityIds_"+layerNum).val(commodityIds.replace(commodityId+",",""));//删除当前商品
    }
    var currLayerNum;
    function selectCommodity(layerNum) {
        currLayerNum = layerNum;
        var commodityIds = $("#commodityIds_"+layerNum).val();
        if (!isEmpty(commodityIds)) {
            showLayer("<spring:message code='sb.select.product' />",ctx+"/common/selectCommodityByMap?commodityIds="+commodityIds,{ area: ['90%', '80%']});
        } else {
            showLayer("<spring:message code='sb.select.product' />",ctx+"/common/selectCommodityByMap",{ area: ['90%', '80%']});
        }
    }
    function changeValue(commodityId, currLayerNum) {
        var val = $("#standardStock_"+commodityId+"_"+currLayerNum).val();
        val = val.replace(/\D/g,'');
        if(isEmpty(val)) {
            val = 0;
        }
        $("#standardStock_"+commodityId+"_"+currLayerNum).val(val);
        var val1 = $("#minSillValue_"+commodityId+"_"+currLayerNum).val();//库存预警
        if(Number(val1) > Number(val) && Number(val) >= 0) {//如果库存预警大于标准库存 库存预警=标准库存-1
            if (Number(val) == 0) {
                val1 = 0;
            } else {
                val1 = val-1;
            }
            $("#minSillValue_"+commodityId+"_"+currLayerNum).val(val1);
        }
    }
    function changeValue1(commodityId, currLayerNum) {
        var val = $("#minSillValue_"+commodityId+"_"+currLayerNum).val();
        val = val.replace(/\D/g,'');
        if(isEmpty(val)) {
            val = 0;
        }
        var val1 = $("#standardStock_"+commodityId+"_"+currLayerNum).val();//标准库存
        if(Number(val) > Number(val1) && Number(val1) >= 0) {//如果库存预警大于标准库存 库存预警=标准库存-1
            if (Number(val1) == 0) {
                val = 0;
            } else {
                val = val1-1;
            }
        }
        $("#minSillValue_"+commodityId+"_"+currLayerNum).val(val);

    }
    function confirmSelectCommodity(commodityIds, commodityMap,deleteCommodityIds) {
        $("#commodityIds_"+currLayerNum).val(commodityIds);

        if(!isEmpty(commodityIds)) {
           var tempIds = commodityIds.substr(0,commodityIds.length-1);
           var arr = tempIds.split(",");
           var html = "";
           for (var i=0;i<arr.length;i++) {
               if (isEmpty($("#"+arr[i]+"_"+currLayerNum).attr("id"))) {
                   var tempData = commodityMap[arr[i]];
                   html += '<tr id="'+arr[i]+'_'+currLayerNum+'">'+
                       '<td>'+tempData.scode+'</td>'+
                       '<td>'+tempData.commodityFullName+'</td>'+
                       '<td><input type="text" name="standardStock_'+arr[i]+'_'+currLayerNum+'" onkeyup="changeValue(\''+arr[i]+'\',currLayerNum);" id="standardStock_'+arr[i]+'_'+currLayerNum+'" value="0" /></td>'+
                       '<td><input type="text" name="minSillValue_'+arr[i]+'_'+currLayerNum+'" onkeyup="changeValue1(\''+arr[i]+'\',currLayerNum);" id="minSillValue_'+arr[i]+'_'+currLayerNum+'" value="0" /></td>'+
                       '<td><a href="javascript:void(0);" onclick="removeCommodity(\''+arr[i]+'\','+currLayerNum+');"><spring:message code='sb.remove' /></a></td>'+
                   '</tr>';
               }
           }
           if (!isEmpty(html)) {
                $("#tbody_"+currLayerNum).append(html);
           }
           //删除原有没选择的
           if(!isEmpty(deleteCommodityIds)) {
               tempIds = commodityIds.substr(0,commodityIds.length-1);
               arr = tempIds.split(",");
               for (var i=0;i<arr.length;i++) {
                   deleteCommodity(arr[i],currLayerNum);
               }
           }
        } else {
            $("#tbody_"+currLayerNum).html("");//清楚表格body
        }
    }

</script>
</body>
</html>

