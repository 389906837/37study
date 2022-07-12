<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>用户持有券明细</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
</head>
<body>
<div class="ibox-content">
    <div class="layui-form-item wflayui-form-item">
        <table  cellspacing="0" border="0">
            <tr>
                <td class="text-left" style="width:18%">券编号：</td>
                <td class="text-right" style="width:32%">${couponUser.scouponCode}</td>
                <td class="text-left" style="width:18%">&nbsp;</td>
                <td class="text-right" style="width:32%">&nbsp;</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">券类型：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${couponUser.icouponType == 10}">现金券</c:if>
                    <c:if test="${couponUser.icouponType == 20}">满减券</c:if>
                    <c:if test="${couponUser.icouponType == 30}">抵扣券</c:if>
                    <c:if test="${couponUser.icouponType == 40}">商品券</c:if>
                </td>
                <td class="text-left"  style="width:18%">获券时间：</td>
                <td class="text-right"  style="width:32%"><fmt:formatDate value='${couponUser.tgetDatetime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">券面值(元)：</td>
                <td class="text-right"  style="width:32%"><fmt:formatNumber value="${couponUser.fcouponValue}" pattern="#0.##元"/></td>
                <td class="text-left"  style="width:18%">券状态：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${couponUser.istatus == 1 }">未使用</c:if>
                    <c:if test="${couponUser.istatus == 2 }">已使用</c:if>
                    <c:if test="${couponUser.istatus == 3 }">已过期</c:if>
                    <c:if test="${couponUser.istatus == 4 }">冻结</c:if>
                    <c:if test="${couponUser.istatus == 5 }">删除</c:if>
                </td>
            </tr>
                <tr>
                    <td class="text-left" style="width:18%">券生效日期：</td>
                    <td class="text-right"  style="width:32%"><fmt:formatDate value='${couponUser.dcouponEffectiveDate}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                    <td class="text-left"  style="width:18%">券失效日期：</td>
                    <td class="text-right"  style="width:32%"><fmt:formatDate value='${couponUser.dcouponExpiryDate}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                </tr>
            <tr>
                    <td class="text-left"  style="width:18%">券有效期天数：</td>
                    <td class="text-right"  style="width:32%">${couponUser.scouponValidityValue}天</td>
                    <td class="text-left" style="width:18%">&nbsp;</td>
                    <td class="text-right" style="width:32%">&nbsp;</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">券说明：</td>
                <td class="text-right"  style="width:32%">${couponUser.scouponInstruction}</td>
                <td class="text-left" style="width:18%">券简述：</td>
                <td class="text-right"  style="width:32%">${couponUser.sbriefDesc}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">会员编号：</td>
                <td class="text-right"  style="width:32%">${couponUser.smemberCode}</td>
                <td class="text-left" style="width:18%">会员用户名：</td>
                <td class="text-right"  style="width:32%">${couponUser.smemberName}</td>
            </tr>
            <c:if test="${couponUser.isourceType ne 10 }">
            <tr>
                <td class="text-left"  style="width:18%">来源活动编号：</td>
                <td class="text-right"  style="width:32%">${couponUser.ssourceAcCode}</td>
                <td class="text-left" style="width:18%">来源活动名称：</td>
                <td class="text-right" style="width:32%">${couponUser.ssourceAcName}</td>
            </tr>
            </c:if>
            <tr>
                <td class="text-left"  style="width:18%">来源编号：</td>
                <td class="text-right"  style="width:32%">${couponUser.ssourceCode}</td>
                <td class="text-left" style="width:18%">来源类型：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${couponUser.isourceType == 10 }">平台赠送</c:if>
                    <c:if test="${couponUser.isourceType == 20 }">会员注册</c:if>
                    <c:if test="${couponUser.isourceType == 30 }">首次开通支付宝免密</c:if>
                    <c:if test="${couponUser.isourceType == 40 }">首次开通微信免密</c:if>
                    <c:if test="${couponUser.isourceType == 50 }">推荐注册</c:if>
                    <c:if test="${couponUser.isourceType == 60 }">首次下单</c:if>
                    <c:if test="${couponUser.isourceType == 70 }">下单赠送</c:if>
                    <c:if test="${couponUser.isourceType == 80 }">促销活动</c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">来源说明：</td>
                <td class="text-right"  style="width:32%">${couponUser.ssourceInstruction}</td>
                <td class="text-left"  style="width:18%">使用订单编号：</td>
                <td class="text-right"  style="width:32%">${couponUser.suseTargetCode}</td>
            </tr>
                    <tr>
                        <td class="text-left" style="width:18%">实际使用金额(元)：</td>
                        <td class="text-right" style="width:32%">${couponUser.factualExchangeAmount}</td>
                        <td class="text-left" style="width:18%">实际使用时间：</td>
                        <td class="text-right" style="width:32%"><fmt:formatDate value='${couponUser.tactualUseDatetime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                    </tr>
            <c:if test="${couponUser.icouponType ne 30}">
                <tr>
                    <td class="text-left" style="width:18%">使用订单实付金额(元)：</td>
                    <td class="text-right" style="width:32%">${couponUser.fuseLimitAmount}</td>
                    <td class="text-left" style="width:18%">&nbsp;</td>
                    <td class="text-right" style="width:32%">&nbsp;</td>
                </tr>
            </c:if>
                <tr>
                    <td class="text-left"  style="width:18%">使用商品限制：</td>
                    <td class="text-right"  style="width:32%">${(empty couponUser.suseLimitCommodity) ? "不限制" : couponUser.suseLimitCommodity}</td>
                    <td class="text-left" style="width:18%">使用设备限制：</td>
                    <td class="text-right" style="width:32%">${(empty couponUser.suseLimitDevice) ? "不限制" : couponUser.suseLimitDevice}</td>
                </tr>
                <tr>
                    <td class="text-left" style="width:18%">使用商品分类限制：</td>
                    <td class="text-right" style="width:32%">${(empty useLimitCategory) ? "不限制" : useLimitCategory}</td>
                    <td class="text-left"  style="width:18%">使用商品品牌限制：</td>
                    <td class="text-right"  style="width:32%">${(empty useLimitBrand) ? "不限制" : useLimitBrand}</td>
                </tr>
        </table>
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

