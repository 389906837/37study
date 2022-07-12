<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>补货中心</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
</head>
<body>
<div class="ibox-content">
        <div class="layui-form-item wflayui-form-item">
        <div class="wfsplitt">
            补货单编号：${replenishmentRecord.scode}
        </div>

        <table  cellspacing="0" border="0">
            <tr>
                <td class="text-left" style="width:18%">商户编号：</td>
                <td class="text-right" style="width:32%">${merchantInfo.scode}</td>
                <td class="text-left" style="width:18%">商户名称:</td>
                <td class="text-right" style="width:32%">${merchantInfo.sname}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">设备编号：</td>
                <td class="text-right" style="width:32%">${replenishmentRecord.sdeviceCode}</td>
                <td class="text-left" style="width:18%">设备名称:</td>
                <td class="text-right" style="width:32%">${deviceInfo.sname}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">设备地址：</td>
                <td class="text-right" style="width:32%">${replenishmentRecord.sdeviceAddress}</td>
                <td class="text-left" style="width:18%">&nbsp;</td>
                <td class="text-right" style="width:32%">&nbsp;</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">补货状态：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${replenishmentRecord.istatus == 10 }">待配货</c:if>
                    <c:if test="${replenishmentRecord.istatus == 20 }">配送中</c:if>
                    <c:if test="${replenishmentRecord.istatus == 30 }">已完成</c:if>
                    <c:if test="${replenishmentRecord.istatus == 40 }">取消配货</c:if>
                </td>
                <td class="text-left"  style="width:18%">补货类型：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${replenishmentRecord.itype == 10 }">动态补货</c:if>
                    <c:if test="${replenishmentRecord.itype == 20 }">普通补货</c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">补货员姓名：</td>
                <td class="text-right"  style="width:32%">${replenishmentRecord.srenewalName}</td>
                <td class="text-left"  style="width:18%">补货员手机号：</td>
                <td class="text-right"  style="width:32%">${replenishmentRecord.srenewalMobile}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">实际上架数量：</td>
                <td class="text-right"  style="width:32%">${replenishmentRecord.iactualShelves}</td>
                <td class="text-left"  style="width:18%">实际下架数量：</td>
                <td class="text-right"  style="width:32%">${replenishmentRecord.iactualUnder}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">实际上架总额：</td>
                <td class="text-right"  style="width:32%">${replenishmentRecord.iactualShelvesAmount}</td>
                <td class="text-left" style="width:18%">实际下架总额：</td>
                <td class="text-right"  style="width:32%">${replenishmentRecord.iactualUnderAmount}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">补货时间：</td>
                <td class="text-right"  style="width:32%"><fmt:formatDate value="${replenishmentRecord.treplenishmentTime}" pattern="yyyy-MM-dd"/></td>
                <td class="text-left" style="width:18%">备注：</td>
                <td class="text-right"  style="width:32%">${replenishmentRecord.sremark}</td>
            </tr>
        </table>
</div>
        <div class="wfsplitt" style="margin-top: 20px">
            <p>补货商品明细</p>
        </div>
        <table class="layui-table">
            <colgroup>
                <col width="100">
                <col width="150">
                <col width="100">
                <col width="100">
                <col width="100">
                <col width="100">
                <col width="100">
            </colgroup>
            <thead>
            <tr>
                <th>商品编号</th>
                <th>商品名称</th>
                <th>商品销售单价</th>
                <th>补货数量</th>
                <th>商品总额</th>
                <th>补货类型</th>
                <th>商品状态</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${list}" var="item" varStatus="L">
                <tr>
                   <td>${item.scommodityCode}</td>
                    <td>${item.spsname}</td>
                    <td>${item.fcommodityPrice}</td>
                    <td>${item.forderCount}</td>
                    <td>${item.fcommodityAmount}</td>
                    <td>
                        <c:if test="${item.itype == 10}">上架</c:if>
                        <c:if test="${item.itype == 20}">下架</c:if>
                    </td>
                    <td>
                        <c:if test="${item.icommodityStatus == 10}">正常</c:if>
                        <c:if test="${item.icommodityStatus == 20}">失效</c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
<input type="hidden" id="id" name="id" value="${replenishmentRecord.id}"/>
    </div>
</div>
<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    $(".wflayui-form-item tr:even").addClass("odd");

</script>
</body>
</html>

