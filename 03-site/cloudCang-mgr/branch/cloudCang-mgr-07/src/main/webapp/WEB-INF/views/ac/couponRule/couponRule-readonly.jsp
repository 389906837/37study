<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>送券规则</title>
<link href="${staticSource}/resources/layui/css/layui.css?4" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?20189" rel="stylesheet">
</head>
<body>
<div class="ibox-content">
    <div class="layui-form-item wflayui-form-item">
    <table  cellspacing="0" border="0">
        <tr>
            <td class="text-left" style="width:18%">是否有效：</td>
            <td class="text-right" style="width:32%"><c:if test="${couponRule.iisValid eq 1}">启用</c:if><c:if test="${(empty couponRule.iisValid ? 0 : couponRule.iisValid) eq 0}">禁用</c:if></td>
            <td class="text-left" style="width:18%">&nbsp;</td>
            <td class="text-right" style="width:32%">&nbsp;</td>
        </tr>
        <tr>
            <td class="text-left" style="width:18%">券类型：</td>
            <td class="text-right"  style="width:32%"><c:if test="${couponRule.icouponType eq 10}">现金券</c:if>
                <c:if test="${couponRule.icouponType eq 20}">满减券</c:if>
                <c:if test="${couponRule.icouponType eq 30}">抵扣券</c:if>
                <c:if test="${couponRule.icouponType eq 40}">商品券</c:if></td>
            <td class="text-left"  style="width:18%">送券方式：</td>
            <td class="text-right"  style="width:32%"><c:if test="${couponRule.igiveMethod eq 10}">比例</c:if>
                <c:if test="${couponRule.igiveMethod eq 20}">固定额度</c:if></td>
        </tr>
        <c:if test="${couponRule.igiveMethod eq 10}">
                <tr>
                    <td class="text-left" style="width:18%">最小券值(元)：</td>
                    <td class="text-right"  style="width:32%"><fmt:formatNumber value="${couponRule.fminValue}" pattern="#0.##元"/></td>
                    <td class="text-left"  style="width:18%">最大券值(元)：</td>
                    <td class="text-right"  style="width:32%"><fmt:formatNumber value="${couponRule.fmaxValue}" pattern="#0.##元"/></td>
                </tr>
        </c:if>
        <tr>
            <c:if test="${couponRule.icouponType eq 40}">
                <td class="text-left"  style="width:18%">商品信息：</td>
                <td class="text-right"  style="width:32%">${couponRule.scommodityCode}</td>
            </c:if>
            <c:if test="${couponRule.icouponType ne 40}">
                <td class="text-left" style="width:18%">送劵面值(元或%)：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${couponRule.igiveMethod eq 10}"><fmt:formatNumber value="${couponRule.fcouponValue/100}" pattern="#0.##%"/></c:if>
                    <c:if test="${couponRule.igiveMethod eq 20}"><fmt:formatNumber value="${couponRule.fcouponValue}" pattern="#0.##元"/></c:if></td>
            </c:if>
            <td class="text-left"  style="width:18%">送券数量(张)：</td>
            <td class="text-right"  style="width:32%">${couponRule.isendNumber}张</td>
        </tr>
        <tr>
            <td class="text-left" style="width:18%">券生效日期：</td>
            <td class="text-right"  style="width:32%"><fmt:formatDate value='${couponRule.dcouponEffectiveDate}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
            <td class="text-left" style="width:18%">券有效期天数：</td>
            <td class="text-right"  style="width:32%">${couponRule.icouponValidityValue}天</td>
        </tr>
        <tr>
            <td class="text-left"  style="width:18%">券失效日期：</td>
            <td class="text-right"  style="width:32%"><fmt:formatDate value='${couponRule.dcouponExpiryDate}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
            <td class="text-left" style="width:18%">券简述：</td>
            <td class="text-right" style="width:32%">${couponRule.sbriefDesc}</td>
        </tr>

        <tr>
            <td class="text-left" style="width:18%">劵说明：</td>
            <td class="text-right"  style="width:32%">${couponRule.sactivityInstruction}</td>
            <td class="text-left"  style="width:18%">赠送说明：</td>
            <td class="text-right"  style="width:32%">${couponRule.sremark}</td>
        </tr>
        <c:if test="${iscenesType eq 51 or iscenesType eq 60 or iscenesType eq 70 or itype eq 20}">
            <tr>
                <td class="text-left" style="width:18%">赠送订单实付金额(元)：</td>
                <td class="text-right" style="width:32%"><fmt:formatNumber value="${empty couponRule.fgiveLimitAmount ? 0 : couponRule.fgiveLimitAmount }" pattern="#0.##元"/></td>
                <td class="text-left"  style="width:18%">赠送商品限制：</td>
                <td class="text-right"  style="width:32%">${(empty couponRule.sgiveLimitCommodity) ? "不限制" : couponRule.sgiveLimitCommodity}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">赠送商品分类限制：</td>
                <td class="text-right" style="width:32%">${(empty giveLimitCategory) ? "不限制" : giveLimitCategory}</td>
                <td class="text-left"  style="width:18%">赠送商品品牌限制：</td>
                <td class="text-right"  style="width:32%">${(empty giveLimitBrand) ? "不限制" : giveLimitBrand}</td>
            </tr>
        </c:if>
        <c:if test="${couponRule.icouponType eq 40}">
            <tr>
                <td class="text-left" style="width:18%">使用设备限制：</td>
                <td class="text-right" style="width:32%">${(empty couponRule.suseLimitDevice) ? "不限制" : couponRule.suseLimitDevice}</td>
                <td class="text-left" style="width:18%">&nbsp;</td>
                <td class="text-right" style="width:32%">&nbsp;</td>
            </tr>
        </c:if>
        <c:if test="${couponRule.icouponType ne 40}">
            <c:if test="${couponRule.icouponType ne 30}">
                <tr>
                    <td class="text-left" style="width:18%">使用订单实付金额(元)：</td>
                    <td class="text-right" style="width:32%"><fmt:formatNumber value="${empty couponRule.fuseLimitAmount ? 0 : couponRule.fuseLimitAmount }" pattern="#0.##元"/></td>
                    <td class="text-left" style="width:18%">&nbsp;</td>
                    <td class="text-right" style="width:32%">&nbsp;</td>
                </tr>
            </c:if>
            <tr>
                <td class="text-left"  style="width:18%">使用商品限制：</td>
                <td class="text-right"  style="width:32%">${(empty couponRule.suseLimitCommodity) ? "不限制" : couponRule.suseLimitCommodity}</td>
                <td class="text-left" style="width:18%">使用设备限制：</td>
                <td class="text-right" style="width:32%">${(empty couponRule.suseLimitDevice) ? "不限制" : couponRule.suseLimitDevice}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">使用商品分类限制：</td>
                <td class="text-right" style="width:32%">${(empty useLimitCategory) ? "不限制" : useLimitCategory}</td>
                <td class="text-left"  style="width:18%">使用商品品牌限制：</td>
                <td class="text-right"  style="width:32%">${(empty useLimitBrand) ? "不限制" : useLimitBrand}</td>
            </tr>
        </c:if>
    </table>
    </div>
</div>
<script type="text/javascript">
    $(".wflayui-form-item tr:even").addClass("odd");
</script>
</body>
</html>