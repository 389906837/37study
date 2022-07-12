<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>促销活动详情</title>
<link href="${staticSource}/resources/layui/css/layui.css?2" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?1" rel="stylesheet">
</head>
<body>
<div class="ibox-content">
    <div class="layui-form-item wflayui-form-item">
        <table  cellspacing="0" border="0">
            <tr>
                <td class="text-left" style="width:18%">编号：</td>
                <td class="text-right" style="width:32%">${conf.sconfCode}</td>
                <td class="text-left"  style="width:18%">名称：</td>
                <td class="text-right"  style="width:32%">${conf.sconfName}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">活动类型：</td>
                <td class="text-right"  style="width:32%">
                <c:if test="${conf.idiscountWay eq 10}">首单优惠</c:if>
                <c:if test="${conf.idiscountWay eq 20}">商品打折</c:if>
                <c:if test="${conf.idiscountWay eq 30}">商品满减</c:if>
                <c:if test="${conf.idiscountWay eq 40}">下单返券</c:if>
                <c:if test="${conf.idiscountWay eq 50}">下单返现</c:if>
                </td>
                <c:if test="${conf.idiscountWay eq 10}">
                    <td class="text-left"  style="width:18%">优惠是否叠加：</td>
                    <td class="text-right"  style="width:32%">
                    <c:if test="${conf.iisSuperposition eq 0}">否</c:if>
                    <c:if test="${conf.iisSuperposition eq 1}">是</c:if></td>
                </c:if>
                <c:if test="${conf.idiscountWay eq 20}">
                <td class="text-left" style="width:18%">折扣类型：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${confDetail.idiscountType eq 20}">满X元</c:if>
                    <c:if test="${confDetail.idiscountType eq 30}">满X件</c:if>
                    <c:if test="${confDetail.idiscountType eq 40}">满X元且满X件</c:if>
                </td>
                </c:if>
                <c:if test="${conf.idiscountWay eq 30}">
                <td class="text-left" style="width:18%">满减类型：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${confDetail.idiscountType eq 50}">满X元</c:if>
                    <c:if test="${confDetail.idiscountType eq 60}">每满X元</c:if>
                    <c:if test="${confDetail.idiscountType eq 70}">满X件</c:if>
                    <c:if test="${confDetail.idiscountType eq 80}">满X元且满Y件</c:if>
                </td>
                </c:if>
                <c:if test="${conf.idiscountWay eq 40}">
                    <td class="text-left" style="width:18%">&nbsp;</td>
                    <td class="text-right" style="width:32%">&nbsp;</td>
                </c:if>
                <c:if test="${conf.idiscountWay eq 50}">
                    <td class="text-left" style="width:18%">&nbsp;</td>
                    <td class="text-right" style="width:32%">&nbsp;</td>
                </c:if>
            </tr>
            <c:if test="${conf.idiscountWay eq 10}">
            <tr>
                    <td class="text-left" style="width:18%">目标金额：</td>
                    <td class="text-right"  style="width:32%">${confDetail.ftargetMoney}元</td>
                    <td class="text-left" style="width:18%">优惠金额：</td>
                    <td class="text-right" style="width:18%">${confDetail.fdiscountMoney}元</td>
            </tr>
            </c:if>
            <c:if test="${confDetail.idiscountType eq 20}">
                <tr>
                    <td class="text-left" style="width:18%">目标金额：</td>
                    <td class="text-right"  style="width:32%">${confDetail.ftargetMoney}元</td>
                    <td class="text-left"  style="width:18%">优惠折扣(0-10)：</td>
                    <td class="text-right"  style="width:32%"><fmt:formatNumber pattern="#0.00##" value="${confDetail.fdiscount}" />折</td>
                </tr>
            </c:if>
            <c:if test="${confDetail.idiscountType eq 30}">
            <tr>
                <td class="text-left"  style="width:18%">目标件数：</td>
                <td class="text-right"  style="width:32%">${confDetail.ftargetNum}件</td>
                <td class="text-left"  style="width:18%">优惠折扣(0-10)：</td>
                <td class="text-right"  style="width:32%"><fmt:formatNumber pattern="#0.00##" value="${confDetail.fdiscount}" />折</td>
            </tr>
            </c:if>
            <c:if test="${confDetail.idiscountType eq 40}">
                <tr>
                    <td class="text-left"  style="width:18%">目标件数：</td>
                    <td class="text-right"  style="width:32%">${confDetail.ftargetNum}件</td>
                    <td class="text-left" style="width:18%">目标金额：</td>
                    <td class="text-right"  style="width:32%">${confDetail.ftargetMoney}元</td>
                </tr>
                <tr>
                    <td class="text-left"  style="width:18%">优惠折扣(0-10)：</td>
                    <td class="text-right"  style="width:32%"><fmt:formatNumber pattern="#0.00##" value="${confDetail.fdiscount}"/>折</td>
                    <td class="text-left" style="width:18%">&nbsp;</td>
                    <td class="text-right" style="width:32%">&nbsp;</td>
                </tr>
            </c:if>
            <c:if test="${confDetail.idiscountType eq 50}">
                <tr>
                    <td class="text-left" style="width:18%">目标金额：</td>
                    <td class="text-right"  style="width:32%">${confDetail.ftargetMoney}元</td>
                    <td class="text-left"  style="width:18%">优惠金额：</td>
                    <td class="text-right"  style="width:32%">${confDetail.fdiscountMoney}元</td>
                </tr>
            </c:if>
                <c:if test="${confDetail1.idiscountType eq 50}">
                <tr>
                    <td class="text-left" style="width:18%">目标金额：</td>
                    <td class="text-right"  style="width:32%">${confDetail1.ftargetMoney}元</td>
                    <td class="text-left"  style="width:18%">优惠金额：</td>
                    <td class="text-right"  style="width:32%">${confDetail1.fdiscountMoney}元</td>
                </tr>
                </c:if>
            <c:if test="${confDetail2.idiscountType eq 50}">
                <tr>
                    <td class="text-left" style="width:18%">目标金额：</td>
                    <td class="text-right"  style="width:32%">${confDetail2.ftargetMoney}元</td>
                    <td class="text-left"  style="width:18%">优惠金额：</td>
                    <td class="text-right"  style="width:32%">${confDetail2.fdiscountMoney}元</td>
                </tr>
            </c:if>
            <c:if test="${confDetail.idiscountType eq 60}">
                <tr>
                    <td class="text-left" style="width:18%">目标金额：</td>
                    <td class="text-right"  style="width:32%">${confDetail.ftargetMoney}元</td>
                    <td class="text-left" style="width:18%">&nbsp;</td>
                    <td class="text-right" style="width:32%">&nbsp;</td>
                </tr>
                <tr>
                    <td class="text-left"  style="width:18%">优惠金额：</td>
                    <td class="text-right"  style="width:32%">${confDetail.fdiscountMoney}元</td>
                    <td class="text-left"  style="width:18%">优惠上限：</td>
                    <td class="text-right"  style="width:32%">${confDetail.fdiscountLimit}元</td>
                </tr>
            </c:if>
            <c:if test="${confDetail.idiscountType eq 70}">
                <tr>
                    <td class="text-left"  style="width:18%">目标件数：</td>
                    <td class="text-right"  style="width:32%">${confDetail.ftargetNum}件</td>
                    <td class="text-left" style="width:18%">&nbsp;</td>
                    <td class="text-right" style="width:32%">&nbsp;</td>
                </tr>
            </c:if>
            <c:if test="${confDetail.idiscountType eq 80}">
                <tr>
                    <td class="text-left"  style="width:18%">目标件数：</td>
                    <td class="text-right"  style="width:32%">${confDetail.ftargetNum}件</td>
                    <td class="text-left" style="width:18%">&nbsp;</td>
                    <td class="text-right" style="width:32%">&nbsp;</td>
                </tr>
                <tr>
                    <td class="text-left" style="width:18%">目标金额：</td>
                    <td class="text-right"  style="width:32%">${confDetail.ftargetMoney}元</td>
                    <td class="text-left"  style="width:18%">优惠金额：</td>
                    <td class="text-right"  style="width:32%">${confDetail.fdiscountMoney}元</td>
                </tr>
                <tr>
                    <td class="text-left" style="width:18%">目标金额：</td>
                    <td class="text-right"  style="width:32%">${confDetail1.ftargetMoney}元</td>
                    <td class="text-left"  style="width:18%">优惠金额：</td>
                    <td class="text-right"  style="width:32%">${confDetail1.fdiscountMoney}元</td>
                </tr>
                <tr>
                    <td class="text-left" style="width:18%">目标金额：</td>
                    <td class="text-right"  style="width:32%">${confDetail2.ftargetMoney}元</td>
                    <td class="text-left"  style="width:18%">优惠金额：</td>
                    <td class="text-right"  style="width:32%">${confDetail2.fdiscountMoney}元</td>
                </tr>
            </c:if>

            <c:if test="${conf.idiscountWay eq 50}">
                <tr>
                    <td class="text-left" style="width:18%">目标金额：</td>
                    <td class="text-right"  style="width:32%">${confDetail.ftargetMoney}元</td>
                    <td class="text-left"  style="width:18%">返现金额：</td>
                    <td class="text-right"  style="width:32%">${confDetail.fcashBackMoney}元</td>
                </tr>
            </c:if>
            <tr>
                <td class="text-left" style="width:19%">活动期间总参与次数：</td>
                <td class="text-right"  style="width:32%">${(conf.iallCount eq 0) ? '不限制':conf.iallCount}<c:if test="${conf.iallCount>0}">次</c:if></td>
                <td class="text-left"  style="width:18%">参与次数限制类型：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${conf.icountType eq 10}">活动期间用户</c:if>
                    <c:if test="${conf.icountType eq 20}">活动期间设备</c:if></td>
                </td>
            </tr>
            <c:if test="${conf.icountType eq 10}">
                <tr>
                    <td class="text-left" style="width:18%">(用户)总参与次数：</td>
                    <td class="text-right"  style="width:32%">${(conf.iuserAllCount eq 0) ? '不限制':conf.iuserAllCount}<c:if test="${conf.iuserAllCount>0}">次</c:if></td>
                    <td class="text-left"  style="width:19%">(用户)单日参与次数：</td>
                    <td class="text-right"  style="width:32%">${(conf.iuserDayCount eq 0) ? '不限制':conf.iuserDayCount}<c:if test="${conf.iuserDayCount>0}">次</c:if></td>
                </tr>
            </c:if>
            <c:if test="${conf.icountType eq 20}">
                <tr>
                    <td class="text-left" style="width:18%">(设备)总参与次数：</td>
                    <td class="text-right"  style="width:32%">${conf.iuserAllCount}次</td>
                    <td class="text-left"  style="width:18%">(设备)单日参与次数：</td>
                    <td class="text-right"  style="width:32%">${conf.iuserDayCount}次</td>
                </tr>
            </c:if>
            <tr>
                <td class="text-left" style="width:18%">活动开始时间：</td>
                <td class="text-right" style="width:32%"><fmt:formatDate value='${conf.tactiveStartTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                <td class="text-left" style="width:18%">活动结束时间：</td>
                <td class="text-right" style="width:32%"><fmt:formatDate value='${conf.tactiveEndTime}' pattern='yyyy-MM-dd HH:mm:ss'/> </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">活动叙述：</td>
                <td class="text-right"  style="width:32%">${conf.sdescription}</td>
                <td class="text-left" style="width:18%">&nbsp;</td>
                <td class="text-right" style="width:32%">&nbsp;</td>
            </tr>
        </table>
        <input type="hidden" id="id" name="id" value="${conf.id}"/>
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

