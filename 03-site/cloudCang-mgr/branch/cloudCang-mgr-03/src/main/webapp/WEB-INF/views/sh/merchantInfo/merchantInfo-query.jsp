<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title><spring:message code="sh.business.details" /></title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?20185256" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css" rel="stylesheet"/>
</head>
<body>
<div class="ibox-content">

    <div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
        <ul class="layui-tab-title">
            <li class="layui-this" id="merchantInfoLi"><spring:message code="sh.essential.information" /></li>
            <%--<li><spring:message code="sh.wechat.alipay.configuration" /></li>--%>
        </ul>
        <div class="layui-tab-content" style="height: 100px;">
            <div class="layui-tab-item layui-show" name="merchantInfoDiv" id="merchantInfoDiv">
                <div class="layui-form-item wflayui-form-item">
                    <div class="wfsplitt">
                        <spring:message code="sh.business.information" />
                    </div>
                    <table cellspacing="0" border="0">
                        <tr>
                            <td class="text-left" style="width:18%"><spring:message code="main.merchant.number" />：</td>
                            <td class="text-right" style="width:32%">${merchantInfo.scode}</td>
                            <td class="text-left" style="width:18%"><spring:message code="main.merchant.name" />：</td>
                            <td class="text-right" style="width:32%">${merchantInfo.sname}</td>
                        </tr>
                        <tr>
                            <td class="text-left" style="width:18%"><spring:message code="sh.business.type" />：</td>
                            <td class="text-right" style="width:32%">
                                <c:if test="${merchantInfo.itype == 10 }"><spring:message code="sh.personal" /></c:if>
                                <c:if test="${merchantInfo.itype == 20 }"><spring:message code="sh.enterprise" /></c:if>
                            </td>
                            <td class="text-left"><spring:message code="sh.type.of.company" />：</td>
                            <td class="text-right">
                                <c:if test="${merchantInfo.itype == 10 }"><spring:message code="sh.state.owned.enterprise" /></c:if>
                                <c:if test="${merchantInfo.itype == 20 }"><spring:message code="sh.private" /></c:if>
                            </td>
                        </tr>
                        <tr>
                            <td class="text-left"><spring:message code="sh.merchant.status" />：</td>
                            <td class="text-right">
                                <c:if test="${merchantInfo.istatus == 10 }"><spring:message code="sh.negotiating" /></c:if>
                                <c:if test="${merchantInfo.istatus == 20 }"><spring:message code="sh.signed" /></c:if>
                                <c:if test="${merchantInfo.istatus == 30 }"><spring:message code="sh.terminated" /></c:if>
                                <c:if test="${merchantInfo.istatus == 40 }"><spring:message code="sh.contract.expired" /></c:if>
                            </td>
                            <td class="text-left"><spring:message code="sh.scope.of.sku.use"/>：</td>
                            <td class="text-right">
                                <c:if test="${merchantInfo.ivmSkuType == 1 }"><spring:message code="sh.aii" /></c:if>
                                <c:if test="${merchantInfo.ivmSkuType == 2 }"><spring:message code="sh.part" /></c:if>
                            </td>
                        </tr>
                        <tr>
                            <td class="text-left"><spring:message code="sh.date.of.signing" />：</td>
                            <td class="text-right"><fmt:formatDate value="${merchantInfo.dsignDate}"
                                                                   pattern="yyyy-MM-dd"/></td>
                            <td class="text-left"><spring:message code="sh.contract.expiration.date" />：</td>
                            <td class="text-right"><fmt:formatDate value="${merchantInfo.dexpireDate}"
                                                                   pattern="yyyy-MM-dd"/></td>
                        </tr>
                        <tr>
                            <td class="text-left"><spring:message code="sh.contacts" />：</td>
                            <td class="text-right">
                                ${merchantInfo.scontactName}
                            </td>
                            <td class="text-left"><spring:message code="sh.contact.telephone" />：</td>
                            <td class="text-right">
                                ${merchantInfo.scontactMobile}
                            </td>
                        </tr>
                        <tr>
                            <td class="text-left"><spring:message code="sh.contact.email" />：</td>
                            <td class="text-right">
                                ${merchantInfo.scontactEmail}
                            </td>
                            <td class="text-left"><spring:message code="sh.contact.address" />：</td>
                            <td class="text-right">
                                ${merchantInfo.scontactAddress}
                            </td>
                        </tr>
                        <tr>
                            <td class="text-left"><spring:message code="sh.cooperation.model" />：</td>
                            <td class="text-right">
                                <c:if test="${merchantInfo.icooperationMode == 10 }"><spring:message code="sh.purchase" /></c:if>
                                <c:if test="${merchantInfo.icooperationMode == 20 }"><spring:message code="sh.rent" /></c:if>
                                <c:if test="${merchantInfo.icooperationMode == 30 }"><spring:message code="sh.independence" /></c:if>
                            </td>
                            <td class="text-left"><spring:message code="sh.secondary.distribution.switch" />：</td>
                            <td class="text-right">
                                <c:if test="${merchantInfo.idistributionSwitch == 0 }"><spring:message code="sh.close" /></c:if>
                                <c:if test="${merchantInfo.idistributionSwitch == 1 }"><spring:message code="sh.open" /></c:if>
                            </td>
                        </tr>
                    </table>
                    <div class="wfsplitt">
                        <spring:message code="sh.client.configuration" />
                    </div>
                    <table cellspacing="0" border="0">
                        <tr>
                            <td class="text-left"><spring:message code='sh.support.payment.method'/>：</td>
                            <td class="text-right">
                                <c:if test="${merchantClientConf.isupportPayWay == 10 }"><spring:message code="sh.aii" /></c:if>
                                <c:if test="${merchantClientConf.isupportPayWay == 20 }"><spring:message code="sh.only.withholding" /></c:if>
                                <c:if test="${merchantClientConf.isupportPayWay == 30 }"><spring:message code="sh.manual.only" /></c:if>
                            </td>
                            <td class="text-left"><spring:message code="sh.whether.the.refund.needs.to.be.audited" />：</td>
                            <td class="text-right">
                                <c:if test="${merchantClientConf.iisRefundAudit == 0 }"><spring:message code="main.no" /></c:if>
                                <c:if test="${merchantClientConf.iisRefundAudit == 1 }"><spring:message code="main.yes" /></c:if>
                            </td>
                        </tr>
                        <tr>
                            <td class="text-left"><spring:message code="sh.customer.service.telephone.numbers" />：</td>
                            <td class="text-right">
                                ${merchantClientConf.scontactMobile}
                            </td>
                            <td class="text-left"><spring:message code="sh.merchant.short.name" />：</td>
                            <td class="text-right">
                                ${merchantClientConf.sshortName}
                            </td>
                        </tr>
                        <tr>
                            <td class="text-left" style="width:18%">logo：</td>
                            <td class="text-right" style="width:32%">
                                <c:if test="${!empty  merchantClientConf.slogo}">
                                    <p style="text-align: left;">
                                        <img src="${dynamicSource}${merchantClientConf.slogo}"
                                             style='width: 50px; height: 45px;'>
                                    </p>
                                </c:if>
                            </td>
                            <td class="text-left" style="width:18%"><spring:message code="sh.sign.in.logo" />：</td>
                            <td class="text-right" style="width:32%">
                                <c:if test="${!empty  merchantClientConf.sloginLogo}">
                                    <p style="text-align: left;">
                                        <img src="${dynamicSource}${merchantClientConf.sloginLogo}"
                                             style='width: 50px; height: 45px;'>
                                    </p>
                                </c:if>
                            </td>
                        </tr>
                    </table>
                    <c:if test="${20 == merchantInfo.itype}">
                    <div class="wfsplitt">
                        <spring:message code="sh.detailed.information" />
                    </div>
                    <table cellspacing="0" border="0">
                        <tr>
                            <td class="text-left" style="width:18%"><spring:message code="sh.company.tax.number" />：</td>
                            <td class="text-right" style="width:32%">  ${merchantDetail.staxId}</td>
                            <td class="text-left" style="width:18%"><spring:message code="sh.company.registration.address" />：</td>
                            <td class="text-right" style="width:32%">  ${merchantDetail.sregisterAddress}</td>
                        </tr>
                        <tr>
                            <td class="text-left"><spring:message code="sh.company.telephone" />：</td>
                            <td class="text-right">
                                    ${merchantDetail.sphone}
                            </td>
                            <td class="text-left"><spring:message code="sh.company.fax" />：</td>
                            <td class="text-right">
                                    ${merchantDetail.sfax}
                            </td>
                        </tr>
                        <tr>
                            <td class="text-left"><spring:message code="sh.company.web.site" />：</td>
                            <td class="text-right">
                                    ${merchantDetail.swebsiteUrl}
                            </td>
                            <td class="text-left"><spring:message code="sh.corporate.legal.person" />：</td>
                            <td class="text-right">
                                    ${merchantDetail.slegalPerson}
                            </td>
                        </tr>
                        <tr>
                            <td class="text-left"><spring:message code="sh.corporate.identity.card" />：</td>
                            <td class="text-right">
                                    ${merchantDetail.sidCard}
                            </td>
                            <td class="text-left"><spring:message code="sh.company.account" />：</td>
                            <td class="text-right">
                                    ${merchantDetail.saccountNumber}
                            </td>
                        </tr>
                        <tr>
                            <td class="text-left"><spring:message code="sh.name.of.company's.public.account" />：</td>
                            <td class="text-right">
                                    ${merchantDetail.saccountName}
                            </td>
                            <td class="text-left"><spring:message code="sh.the.company.opens.an.account.with.a.public.account" />：</td>
                            <td class="text-right">
                                    ${merchantDetail.saccountBank}
                            </td>
                        </tr>
                    </table>
                    </c:if>
                </div>
            </div>
            <script type="text/javascript">
                $(".wflayui-form-item tr:even").addClass("odd");
            </script>
            <div class="layui-tab-item">

                <div class="layui-form-item wflayui-form-item">
                    <div class="wfsplitt">
                       <spring:message code="sh.wechat.configuration" />
                    </div>
                    <table cellspacing="0" border="0">
                        <tr>
                            <td class="text-left" style="width:18%"><spring:message code="sh.authorized.callback.url" />：</td>
                            <td class="text-right" style="width:32%">${wCmerchantConf.scallBackUrl}</td>
                            <td class="text-left">APPID：</td>
                            <td class="text-right">${wCmerchantConf.sappId}</td>
                        </tr>
                        <tr>
                            <td class="text-left" style="width:18%"><spring:message code="sh.short.message.notification.of.single.amount" />：</td>
                            <td class="text-right" style="width:32%">
                                ${wCmerchantConf.fpayLimitMoney}
                            </td>
                            <td class="text-left"><spring:message code="sh.single.payment.limit" />：</td>
                            <td class="text-right">${wCmerchantConf.fpayLimitSingle}</td>
                        </tr>
                        <tr>
                            <td class="text-left" style="width:18%"><spring:message code="sh.one.day.payment.limit" />：</td>
                            <td class="text-right" style="width:32%">
                                ${wCmerchantConf.fpayLimitDay}
                            </td>
                            <td class="text-left"><spring:message code="sh.monthly.payment.limit" />：</td>
                            <td class="text-right">${wCmerchantConf.fpayLimitMonth}</td>
                        </tr>
                        <tr>
                            <td class="text-left" style="width:18%"><spring:message code="sh.md5.secret.key" />：</td>
                            <td class="text-right" style="width:32%">
                                ${wCmerchantConf.sappSecret}
                            </td>
                            <td class="text-left" style="width:18%"><spring:message code="sh.substitution.template.id" />：</td>
                            <td class="text-right" style="width:32%">
                                ${wCmerchantConf.splanId}
                            </td>
                        </tr>
                        <tr>
                            <td class="text-left" style="width:18%"><spring:message code="sh.wechat.business.number" />：</td>
                            <td class="text-right" style="width:32%">
                                ${wCmerchantConf.spid}
                            </td>
                            <td class="text-left" style="width:18%"><spring:message code="sh.wechat.signature.key" />：</td>
                            <td class="text-right" style="width:32%">
                                ${wCmerchantConf.spayWechatKey}
                            </td>
                        </tr>
                        <tr>
                            <td class="text-left"><spring:message code="sh.certificate.address" />：</td>
                            <td class="text-right">
                                ${wCmerchantConf.swechatCertUrl}
                            </td>
                            <td class="text-left"><spring:message code="sh.certificate.password" />：</td>
                            <td class="text-right">
                                ${wCmerchantConf.swechatCertPwd}
                            </td>
                        </tr>
                        <tr>
                            <td class="text-left"><spring:message code="sh.wechat.account" />：</td>
                            <td class="text-right">
                                ${wCmerchantConf.swechatAccount}
                            </td>
                            <td class="text-left" style="width:18%"><spring:message code="sh.wechat.payment.callback.address" />：</td>
                            <td class="text-right" style="width:32%">${wCmerchantConf.spayCallBackUrl}</td>
                            <td class="text-left"></td>
                            <td class="text-right">
                            </td>
                        </tr>
                    </table>
                    <div class="wfsplitt">
                        <spring:message code="sh.alipay.configuration" />
                    </div>
                    <table cellspacing="0" border="0">
                        <tr>
                            <td class="text-left" style="width:18%"><spring:message code="sh.authorized.callback.url" />：</td>
                            <td class="text-right" style="width:32%">${aPmerchantConf.scallBackUrl}</td>
                            <td class="text-left">APPID：</td>
                            <td class="text-right">${aPmerchantConf.sappId}</td>
                        </tr>
                        <tr>
                            <td class="text-left" style="width:18%"><spring:message code="sh.md5.secret.key" />：</td>
                            <td class="text-right" style="width:32%">
                                ${aPmerchantConf.sappSecret}
                            </td>
                            <td class="text-left"><spring:message code="sh.single.payment.limit" />：</td>
                            <td class="text-right">${aPmerchantConf.fpayLimitSingle}</td>
                        </tr>
                        <tr>
                            <td class="text-left" style="width:18%"><spring:message code="sh.one.day.payment.limit" />：</td>
                            <td class="text-right" style="width:32%">
                                ${aPmerchantConf.fpayLimitDay}
                            </td>
                            <td class="text-left" style="width:18%"><spring:message code="sh.monthly.payment.limit" />：</td>
                            <td class="text-right" style="width:32%">
                                ${aPmerchantConf.fpayLimitMonth}
                            </td>
                        </tr>
                        <tr>
                            <td class="text-left"><spring:message code="sh.short.message.notification.of.single.amount" />：</td>
                            <td class="text-right">${aPmerchantConf.fpayLimitMoney}</td>

                            <td class="text-left" style="width:18%"><spring:message code="sh.alipay.partners.id" />ID：</td>
                            <td class="text-right" style="width:32%">
                                ${aPmerchantConf.spid}
                            </td>
                        </tr>
                        <tr>
                            <td class="text-left" style="width:18%"><spring:message code="sh.alipay.account.number" />：</td>
                            <td class="text-right" style="width:32%">
                                ${aPmerchantConf.salipayAccount}
                            </td>
                            <td class="text-left" style="width:18%"><spring:message code="sh.alipay.payment.callback.address" />：</td>
                            <td class="text-right" style="width:32%">
                                ${aPmerchantConf.spayCallBackUrl}
                            </td>
                        </tr>
                        <tr>
                         <%--    <td class="text-left" style="width:18%"><spring:message code="sh.alipay.public.key" />：</td>
                             <td class="text-right" colspan="3" style="width:82%">${aPmerchantConf.spublicKey}</td>--%>

                        </tr>
                    </table>
                </div>
            </div>
            <script type="text/javascript">
                $(".wflayui-form-item tr:even").addClass("odd");
            </script>
        </div>
    </div>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js"></script>
<script src="${staticSource}/resources/js/validform_min.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        $("input").attr("disabled", true)
        $("textarea").attr("disabled", true)
        $("select").attr("disabled", true);

    });
    layui.use(['form', 'element'], function () {
        //Table Start
        var $ = layui.jquery
            , element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块
        //Table End
        var form = layui.form;
    });
</script>
</body>

