<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>会员中心</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
</head>
<body>
<div class="ibox-content">
    <div class="layui-form-item wflayui-form-item">
        <table  cellspacing="0" border="0">
            <div class="wfsplitt">
            <spring:message code='sh.essential.information' />
            </div>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="main.member.number" />：</td>
                <td class="text-right" style="width:32%">${memberInfo.scode}</td>
                <td class="text-left" style="width:18%"><spring:message code="main.member.username" />：</td>
                <td class="text-right" style="width:32%">${memberInfo.smemberName}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="main.gender" />：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${memberInfo.isex == 1 }"><spring:message code="main.male" /></c:if>
                    <c:if test="${memberInfo.isex == 2 }"><spring:message code="main.female" /></c:if>
                </td>
                <td class="text-left"  style="width:18%"><spring:message code='hy.member.birthday' />：</td>
                <td class="text-right"  style="width:32%"><fmt:formatDate value='${memberInfo.dbirthdate}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="main.mobile" />：</td>
                <td class="text-right"  style="width:32%">${memberInfo.smobile}</td>
                <td class="text-left"  style="width:18%"><spring:message code="main.mailbox" />：</td>
                <td class="text-right"  style="width:32%">${memberInfo.semail}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='hy.nickname' />：</td>
                <td class="text-right"  style="width:32%">${memberInfo.snickName}</td>
                <td class="text-left"  style="width:18%"><spring:message code='hy.actual.name' />：</td>
                <td class="text-right"  style="width:32%">${memberInfo.srealName}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code='hy.type.of.certificate' />：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${memberInfo.icardType == 10 }"><spring:message code='hy.id.card' /></c:if>
                    <c:if test="${memberInfo.icardType == 20 }"><spring:message code='sh.business.license' /></c:if>
                </td>
                <td class="text-left" style="width:18%"><spring:message code='hy.id.number' />：</td>
                <td class="text-right"  style="width:32%">${memberInfo.sidCard}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="sb.registration.time" />：</td>
                <td class="text-right"  style="width:32%"><fmt:formatDate value="${memberInfo.tregisterTime}" pattern="yyyy-MM-dd"/></td>
                <td class="text-left" style="width:18%"><spring:message code='hy.real-name.authentication.time' />：</td>
                <td class="text-right"  style="width:32%"><fmt:formatDate value="${memberInfo.trealNameTime}" pattern="yyyy-MM-dd"/></td>
            </tr>
        </table>
    </div>


    <div class="layui-form-item wflayui-form-item">
        <table  cellspacing="0" border="0">
            <div class="wfsplitt" style="margin-top: 20px">
                <spring:message code='hy.status.information' />
            </div>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="hy.member.status" />：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${memberInfo.istatus == 1}"><spring:message code="main.normal" /></c:if>
                    <c:if test="${memberInfo.istatus == 2}"><spring:message code='hy.logout.disabled' /></c:if>
                    <c:if test="${memberInfo.istatus == 3}"><spring:message code='hy.system.blacklist' /></c:if>
                    <c:if test="${memberInfo.istatus == 4}"><spring:message code='ac.freeze' /></c:if>
                </td>
                <td class="text-left" style="width:18%"><spring:message code='hy.type.of.membership' />：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${memberInfo.imemberType == 10 }"><spring:message code='sl.shopping.member' /></c:if>
                    <c:if test="${memberInfo.imemberType == 20 }"><spring:message code='sl.replenishment.member' /></c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='hy.membership.level' />：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${memberInfo.imemberLevel == 10 }"><spring:message code='hy.public.member' /></c:if>
                    <c:if test="${memberInfo.imemberLevel == 20 }"><spring:message code='hy.gold.member' /></c:if>
                    <c:if test="${memberInfo.imemberLevel == 30 }"><spring:message code='hy.platinum.member' /></c:if>
                    <c:if test="${memberInfo.imemberLevel == 40 }"><spring:message code='hy.diamond.membership' /></c:if>
                </td>
                <td class="text-left"  style="width:18%"><spring:message code='hy.is.it.the.first' />：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${memberInfo.iisOrder == 0 }"><spring:message code="main.no" /></c:if>
                    <c:if test="${memberInfo.iisOrder == 1 }"><spring:message code="main.yes" /></c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='hy.first.platform' />：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${memberInfo.ifirstOrderPlatform == 10 }"><spring:message code="main.wechat" /></c:if>
                    <c:if test="${memberInfo.ifirstOrderPlatform == 20 }"><spring:message code="main.alipay" /></c:if>
                    <c:if test="${memberInfo.ifirstOrderPlatform == 30 }">APP</c:if>
                </td>
                <td class="text-left"  style="width:18%"><spring:message code='ac.first.time' />：</td>
                <td class="text-right"  style="width:32%"><fmt:formatDate value="${memberInfo.tfirstOrderTime}" pattern="yyyy-MM-dd"/></td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='hy.is.the.recommendation.reward.enabled' />：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${memberInfo.iisEnableRecoaward == 0 }"><spring:message code="main.no" /></c:if>
                    <c:if test="${memberInfo.iisEnableRecoaward == 1 }"><spring:message code="main.yes" /></c:if>
                </td>
                <td class="text-left"  style="width:18%"><spring:message code='hy.real.name.certification' />：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${memberInfo.iisVerified == 0 }"><spring:message code="main.no" /></c:if>
                    <c:if test="${memberInfo.iisVerified == 1 }"><spring:message code="main.yes" /></c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code='hy.is.the.first.time.tied.to.the.card' />：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${memberInfo.iisFirstTieCard == 0 }"><spring:message code="main.no" /></c:if>
                    <c:if test="${memberInfo.iisFirstTieCard == 1 }"><spring:message code="main.yes" /></c:if>
                </td>
                <td class="text-left" style="width:18%"><spring:message code='hy.registered.device.number' />：</td>
                <td class="text-right"  style="width:32%">${memberInfo.sregDeviceCode}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code='hy.registration.platform' />：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${memberInfo.imemberLevel == 10 }"><spring:message code="main.wechat" /></c:if>
                    <c:if test="${memberInfo.imemberLevel == 20 }"><spring:message code="main.alipay" /></c:if>
                    <c:if test="${memberInfo.imemberLevel == 30 }"><spring:message code="main.wechat.public.number" /></c:if>
                    <c:if test="${memberInfo.imemberLevel == 40 }"><spring:message code="main.alipay.life" /></c:if>
                    <c:if test="${memberInfo.imemberLevel == 50 }">APP android</c:if>
                    <c:if test="${memberInfo.imemberLevel == 60 }">APP ios</c:if>
                </td>
                <td class="text-left" style="width:18%"><spring:message code='hy.member.registration.ip' />：</td>
                <td class="text-right"  style="width:32%">${memberInfo.smemberRegip}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code='hy.recent.shopping.platform' />：</td>
                <td class="text-right"  style="width:32%">
                <c:if test="${memberInfo.ilastShopPlatform == 10 }"><spring:message code="main.wechat" /></c:if>
                <c:if test="${memberInfo.ilastShopPlatform == 20 }"><spring:message code="main.alipay" /></c:if>
                <c:if test="${memberInfo.ilastShopPlatform == 30 }">APP</c:if>
                </td>
                <td class="text-left" style="width:18%"><spring:message code='hy.recent.shopping.time' />：</td>
                <td class="text-right"  style="width:32%"><fmt:formatDate value="${memberInfo.tlastShopTime}" pattern="yyyy-MM-dd"/></td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code="main.alipay.free" />：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${memberInfo.iaipayOpen == 0 }"><spring:message code="main.no" /></c:if>
                    <c:if test="${memberInfo.iaipayOpen == 1 }"><spring:message code="main.yes" /></c:if>
                </td>
                <td class="text-left" style="width:18%"><spring:message code='hy.is.wechat.confidentiality.open' />：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${memberInfo.iwechatOpen == 0 }"><spring:message code="main.no" /></c:if>
                    <c:if test="${memberInfo.iwechatOpen == 1 }"><spring:message code="main.yes" /></c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code='hy.recent.shopping.platform' />：</td>
                <td class="text-right"  style="width:32%">
                <c:if test="${memberInfo.ilastShopPlatform == 10 }"><spring:message code="main.wechat" /></c:if>
                <c:if test="${memberInfo.ilastShopPlatform == 20 }"><spring:message code="main.alipay" /></c:if>
                <c:if test="${memberInfo.ilastShopPlatform == 30 }">APP</c:if>
                </td>
                <td class="text-left" style="width:18%"><spring:message code='hy.recent.shopping.time' />：</td>
                <td class="text-right"  style="width:32%"><fmt:formatDate value="${memberInfo.tlastShopTime}" pattern="yyyy-MM-dd"/></td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code="sys.is.replenisher" />：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${memberInfo.iisReplenishment == 0 }"><spring:message code="main.no" /></c:if>
                    <c:if test="${memberInfo.iisReplenishment == 1 }"><spring:message code="main.yes" /></c:if>
                </td>
                <td class="text-left" style="width:18%">&nbsp;</td>
                <td class="text-right" style="width:32%">&nbsp;</td>
            </tr>
        </table>
    </div>
</div>
<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    $(".wflayui-form-item tr:even").addClass("odd");

</script>
</body>
</html>

