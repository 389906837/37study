<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>菜单编辑</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?20185256" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css" rel="stylesheet"/>
</head>
<body>
<div class="ibox-content">

    <div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
        <ul class="layui-tab-title">
            <li class="layui-this" id="merchantInfoLi"><spring:message code="sh.essential.information" /></li>
            <li id="attachLi"><spring:message code="sh.qualification.attachment.information" /></li>
            <li><spring:message code="sh.wechat.alipay.configuration" /></li>
        </ul>
        <div class="layui-tab-content" style="height: 100px;">
            <div class="layui-tab-item layui-show" name="merchantInfoDiv" id="merchantInfoDiv">
                <form class="layui-form" action="${ctx}/merchantInfo/save" method="post" id="myForm"
                      enctype="multipart/form-data">
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code="main.merchant.name" /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="sname" id="sname"
                                       value="${merchantInfo.sname}" datatype="*" nullmsg='<spring:message code="main.merchant.name.empty" />'
                                       class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code="sh.business.type" /></label>
                            <div class="layui-input-inline">
                                <cang:select type="list" name="itype" id="itype" entire="true"
                                             exp="springMessageCode=sh.please.select.business.type"
                                             layFilter="merchantType"
                                             value="${merchantInfo.itype}" entireName='springMessageCode=sh.please.select.business.type'
                                             list='{10:springMessageCode=sh.personal,20:springMessageCode=sh.enterprise}'/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">

                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code="sh.type.of.company" /></label>
                            <div class="layui-input-inline">
                                <cang:select type="list" name="icompanyType" id="icompanyType" entire="true"
                                             value="${merchantInfo.icompanyType}" entireName='springMessageCode=sh.please.select.a.company.type'
                                             exp="springMessageCode=sh.please.select.a.company.type"
                                             list='{10:springMessageCode=sh.state.owned.enterprise,20:springMessageCode=sh.private}'/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code="sh.secondary.distribution.status" /></label>
                            <div class="layui-input-inline">
                                <cang:select type="list" name="idistributionSwitch" id="idistributionSwitch"
                                             entire="true"
                                             exp="springMessageCode=sh.please.select.secondary.distribution.status"
                                             value="${merchantInfo.idistributionSwitch}" entireName='springMessageCode=sh.please.select.secondary.distribution.status'
                                             list='{0:springMessageCode=sh.close,1:springMessageCode=sh.open}'/>
                            </div>
                        </div>

                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code="sh.date.of.signing" /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="dsignDate" datatype="*" nullmsg='<spring:message code="sh.please.select.the.contract.date" />' id="dsignDate"
                                       value="<fmt:formatDate value='${merchantInfo.dsignDate}' pattern='yyyy-MM-dd'/>"
                                       class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"> ><spring:message code="sh.contract.expiration.date" /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="dexpireDate" datatype="*" nullmsg='<spring:message code="sh.please.select.the.contract.due.date" />' id="dexpireDate"
                                       value="<fmt:formatDate value='${merchantInfo.dexpireDate}' pattern='yyyy-MM-dd'/>"
                                       class="layui-input"/>

                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code="sh.sku.type" /></label>
                            <div class="layui-input-inline">
                                <cang:select type="list" name="ivmSkuType" id="ivmSkuType"
                                             entire="true"
                                             exp="springMessageCode=sh.please.select.the.sku.type"
                                             value="${merchantInfo.ivmSkuType}" entireName='springMessageCode=sh.please.select.the.sku.type'
                                             list='{1:springMessageCode=sh.aii,2:springMessageCode=sh.part}'/>
                            </div>
                        </div>
                        <c:if test="${empty merchantInfo.id}">
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code="sh.contact.username" /></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantUserName"
                                           id="merchantUserName"
                                           datatype="*" nullmsg='<spring:message code="sh.please.enter.a.contact.username" />'
                                           class="layui-input"/>
                                </div>
                            </div>
                        </c:if>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code="sh.contacts" /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="scontactName" id="scontactName" datatype="*" nullmsg='<spring:message code="sh.please.enter.a.contact" />'
                                       value="${merchantInfo.scontactName}" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"> <spring:message code="sh.contact.telephone" /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="scontactMobile" id="scontactMobile" datatype="*"
                                       nullmsg='<spring:message code="sh.please.type.your.phone.number" />'
                                       value="${merchantInfo.scontactMobile}" class="layui-input">
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code="sh.contact.email" /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="scontactEmail" id="scontactEmail" datatype="*"
                                       nullmsg='<spring:message code="sh.please.enter.a.contact.email" />'
                                       value="${merchantInfo.scontactEmail}" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"> <spring:message code="sh.cooperation.model" /></label>
                            <div class="layui-input-inline">
                                <cang:select type="dic" groupNo="SP000104" entire="true"
                                             exp="springMessageCode=sh.please.choose.cooperation.mode"
                                             value="${merchantInfo.icooperationMode}" entireName='springMessageCode=sh.please.choose.cooperation.mode'
                                             name="icooperationMode" id="icooperationMode"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code="sh.bd.mode" /></label>
                            <div class="layui-input-inline">
                                <cang:select type="list" entire="true" value="${merchantInfo.idbWap}"
                                             entireName='springMessageCode=sh.please.select.bd.mode'
                                             exp="springMessageCode=sh.please.select.bd.mode" layFilter="dbWap"
                                             name="idbWap" id="idbWap" list='{10:springMessageCode=sh.the.company,20:springMessageCode=sh.personal}'/>
                            </div>
                        </div>
                        <div class="layui-col-md6" name="dbName" id="dbName" style="display:none;">
                            <label class="layui-form-label"><spring:message code="sh.docking.bd.name" /></label>
                            <div class="layui-input-inline" style="width: 115px">
                                <input type="text" name="sdbName" readonly="true" id="sdbName"
                                       value="${merchantInfo.sdbName}"
                                       class="layui-input"/>
                            </div>
                            <button class="layui-btn" id="btn1" style="display: inline-block;float: left;" type="button"
                                    data-type="sel"><spring:message code="sh.choice" />
                            </button>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label"><spring:message code="main.remarks" /></label>
                        <div class="layui-input-block">
                    <textarea id="sremark2" name="sremark" class="layui-textarea"
                              placeholder='<spring:message code="main.remarks" />（<spring:message code="main.not.required" />）'>${merchantInfo.sremark}</textarea>
                        </div>
                    </div>

                    <div class="wfsplitt">
                        <spring:message code="sh.client.configuration" />
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code="sh.support.payment.method" /></label>
                            <div class="layui-input-inline">
                                <cang:select type="list" id="isupportPayWay" name="isupportPayWay"
                                             exp="springMessageCode=sh.please.choose.to.support.payment.method"
                                             value="${merchantClientConf.isupportPayWay}" entireName='springMessageCode=sh.please.choose.to.support.payment.method'
                                             list='{10:springMessageCode=sh.aii,20:springMessageCode=sh.only.withholding,30:springMessageCode=sh.manual.only}' entire="true"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <div class="layui-inline">
                            <button type="button" class="layui-btn" id="slogoBut"><spring:message code="sh.merchant.logo.upload" /></button>
                            <input type="hidden" id="slogo" name="slogo" value=""/>
                            <c:if test="${not empty  merchantClientConf.slogo}">
                                <blockquote class="layui-elem-quote layui-quote-nm layui-quote-nm-1">
                                    <spring:message code="sh.preview" />：
                                    <div class="layui-upload-list" id="slogoYl">
                                        <p style="text-align: left;">
                                            <img src="${dynamicSource}${merchantClientConf.slogo}"
                                                 style='width: 100px; height: 100px;'>
                                        </p>
                                    </div>
                                </blockquote>
                                <span class="require">(<spring:message code="sh.pixels.white.background.jpg.or.png.images" />)</span>
                            </c:if>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <div class="layui-inline">
                            <button type="button" class="layui-btn" id="loginLogoBut"><spring:message code="sh.pixels.white.background.jpg.or.png.images" /></button>
                            <input type="hidden" id="loginLogo" name="loginLogo" value=""/>
                            <c:if test="${ not empty  merchantClientConf.sloginLogo}">
                                <blockquote class="layui-elem-quote layui-quote-nm layui-quote-nm-1">
                                    <spring:message code="sh.preview" />：
                                    <div class="layui-upload-list" id="loginslogoYl">
                                        <p style="text-align: left;">
                                            <img src="${dynamicSource}${merchantClientConf.sloginLogo}"
                                                 style='width: 100px; height: 100px;'>
                                        </p>
                                    </div>
                                </blockquote>
                                <span class="require">(<spring:message code="sh.pixels.white.background.jpg.or.png.images" />)</span>
                            </c:if>
                            </div>
                        </div>
                    </div>

                    <div 　 style="display:none;" name="typediv" id="typediv">
                        <div class="wfsplitt">
                            <spring:message code="sh.basic.details" />
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code="sh.business.address" /></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="sopenAddress" id="sopenAddress"
                                           value="${merchantDetail.sopenAddress}"
                                           class="layui-input"/>
                                </div>
                            </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code="sh.company.registration.address" /></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="sregisterAddress" id="sregisterAddress"
                                           value="${merchantDetail.sregisterAddress}"
                                           class="layui-input"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code="sh.company.telephone" /></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="sphone" id="sphone"
                                           value="${merchantDetail.sphone}"
                                           class="layui-input"/>
                                </div>
                            </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code="sh.company.fax" /></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="sfax" id="sfax" value="${merchantDetail.sfax}"
                                           class="layui-input"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code="sh.company.web.site" /></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="swebsiteUrl" id="swebsiteUrl"
                                           value="${merchantDetail.swebsiteUrl}"
                                           class="layui-input"/>
                                </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code="sh.corporate.legal.person" /></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="slegalPerson" id="slegalPerson"
                                           value="${merchantDetail.slegalPerson}"
                                           class="layui-input"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code="sh.corporate.identity.card" /></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="sidCard" id="sidCard"
                                           value="${merchantDetail.sidCard}"
                                           class="layui-input"/>
                                </div>
                            </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code="sh.company.account" /></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="saccountNumber" id="saccountNumber"
                                           value="${merchantDetail.saccountNumber}"
                                           class="layui-input"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code="sh.name.of.company's.public.account" /></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="saccountName" id="saccountName"
                                           value="${merchantDetail.saccountName}"
                                           class="layui-input"/>
                                </div>
                            </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code="sh.the.company.opens.an.account.with.a.public.account" /></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="saccountBank" id="saccountBank"
                                           value="${merchantDetail.saccountBank}"
                                           class="layui-input"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label"><spring:message code="sh.company.tax.number" /></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="staxId" id="staxId"
                                           value="${merchantDetail.staxId}"
                                           class="layui-input"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="list-group-item">
                        <div class="layui-input-block">
                            <input type="hidden" id="id" name="id" value="${merchantInfo.id}"/>
                            <input type="hidden" id="merchantDetailId" name="merchantDetailId"
                                   value="${merchantDetail.id}"/>
                            <input type="hidden" id="sdbId" name="sdbId" value="${merchantInfo.sdbId}">
                            <input type="hidden" id="istatus" name="istatus" value="${merchantInfo.istatus}">
                            <input type="hidden" id="scode" name="scode" value="${merchantInfo.scode}">
                        </div>
                    </div>
                    <div class="layui-form-item fixed-bottom">
                        <div class="layui-input-block">
                            <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
                            <button class="layui-btn" id="myFormSub"><spring:message code="main.save" /></button>
                        </div>
                    </div>
                </form>
            </div>

            <div class="layui-tab-item" name="attachDiv" id="attachDiv">
                <form class="layui-form" action="${ctx}/merchantAttach/save" method="post" id="myImgForm"
                      enctype="multipart/form-data">
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code="sh.qualification.attachment.type" /></label>
                            <div class="layui-input-inline">
                                <cang:select type="dic" groupNo="SP000105" entire="true" value=""
                                             exp="springMessageCode=sh.please.select.a.qualified.attachment.type"
                                             name="scode" id="scode"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code="sh.display.order" /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="isort" id="isort" datatype="*" nullmsg='<spring:message code="sh.please.enter.the.display.order" />'
                                       value=""
                                       class="layui-input"/>
                            </div>
                        </div>
                    </div>
                    <%--<div class="layui-form-item">
                        <div class="layui-col-md6" id="upImg">
                            <button type="button" class="layui-btn" id="attachUpBut"><spring:message code="sh.qualification.attachment.upload" /></button>
                        </div>
                    </div>--%>
                    <div class="layui-form-item">
                        <div class="layui-col-md6" id="upImg">
                            <div class="layui-inline">
                            <button type="button" class="layui-btn" id="attachUpBut"><spring:message code="sh.multiple.image.upload" /></button>
                            <blockquote class="layui-elem-quote layui-quote-nm layui-quote-nm-1">
                                <spring:message code="sh.preview" />：
                                <div class="layui-upload-list" id="demo2"></div>
                            </blockquote>
                                <span class="require">(<spring:message code="sh.pixels.white.background.jpg.or.png.images" />)</span>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label"><spring:message code="main.remarks" /></label>
                        <div class="layui-input-block">
                    <textarea id="sremark" name="sremark" class="layui-textarea"
                              placeholder='<spring:message code="main.remarks" />（<spring:message code="main.not.required" />）'></textarea>
                        </div>
                    </div>
                    <div class="layui-form-item text-center m-t">
                        <div class="layui-input-block">
                            <div class="layui-input-block">
                                <button class="layui-btn layui-btn-primary" id="cancelBtnImg"><spring:message code="sh.empty" /></button>
                                <button class="layui-btn" id="myImgFormSub"><spring:message code="main.save" /></button>
                            </div>
                            <input type="hidden" id="smerchantId" name="smerchantId" value="${merchantInfo.id}"/>
                        </div>
                    </div>
                    <div class="jqGrid_wrapper">
                        <table id="gridTable"></table>
                    </div>
                </form>
            </div>
            <div class="layui-tab-item">
                <form class="layui-form" action="${ctx}/merchantConf/save" method="post" id="myConfForm">
                    <div class="wfsplitt">
                       <spring:message code="sh.wechat.configuration" />
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code="sh.authorized.callback.url" /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="WCscallBackUrl" value="${wCmerchantConf.scallBackUrl}"

                                       class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label">APPID</label>
                            <div class="layui-input-inline">
                                <input type="text" name="WCsappId" value="${wCmerchantConf.sappId}"
                                       class="layui-input"/>

                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code="sh.md5.secret.key" /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="WCsappSecret" value="${wCmerchantConf.sappSecret}"

                                       class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code="sh.single.payment.limit" /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="WCfpayLimitSingle" value="${wCmerchantConf.fpayLimitSingle}"


                                       class="layui-input"/>
                            </div>
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code="sh.one.day.payment.limit" /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="WCfpayLimitDay" value="${wCmerchantConf.fpayLimitDay}"


                                       class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code="sh.monthly.payment.limit" /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="WCfpayLimitMonth" value="${wCmerchantConf.fpayLimitMonth}"

                                       class="layui-input"/>
                            </div>
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code="sh.short.message.notification.of.single.amount" /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="WCfpayLimitMoney" value="${wCmerchantConf.fpayLimitMoney}"
                                       class="layui-input"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code="sh.wechat.public.key" /></label>
                            <div class="layui-input-block">
                                  <textarea id="WCspublicKey" name="WCspublicKey" class="layui-textarea"
                                            placeholder='<spring:message code="sh.please.enter.the.wechat.public.key" />'>${wCmerchantConf.spublicKey}</textarea>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code="sh.wechat.application.private.key" /></label>
                            <div class="layui-input-block">
                                  <textarea id="WCsprivateKey" name="WCsprivateKey" class="layui-textarea"
                                            placeholder='<spring:message code="sh.please.enter.the.wechat.application.private.key" />'>${wCmerchantConf.sprivateKey}</textarea>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code="sh.wechat.application.private.key" /></label>
                            <div class="layui-input-block">
                                  <textarea id="WCsappPublicKey" name="WCsappPublicKey" class="layui-textarea"
                                            placeholder='<spring:message code="sh.please.enter.the.wechat.application.private.key" />'>${wCmerchantConf.sappPublicKey}</textarea>
                            </div>
                        </div>
                    </div>
                    <div class="wfsplitt">
                        <spring:message code="sh.alipay.configuration" />
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code="sh.authorized.callback.url" /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="scallBackUrl" value="${aPmerchantConf.scallBackUrl}"

                                       class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label">APPID</label>
                            <div class="layui-input-inline">
                                <input type="text" name="sappId" value="${aPmerchantConf.sappId}"
                                       class="layui-input"/>

                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code="sh.md5.secret.key" /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="sappSecret" value="${aPmerchantConf.sappSecret}"

                                       class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code="sh.single.payment.limit" /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="fpayLimitSingle" value="${aPmerchantConf.fpayLimitSingle}"

                                       class="layui-input"/>
                            </div>
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code="sh.one.day.payment.limit" /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="fpayLimitDay" value="${aPmerchantConf.fpayLimitDay}"

                                       class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code="sh.monthly.payment.limit" /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="fpayLimitMonth" value="${aPmerchantConf.fpayLimitMonth}"

                                       class="layui-input"/>
                            </div>
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code="sh.short.message.notification.of.single.amount" /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="fpayLimitMoney" value="${aPmerchantConf.fpayLimitMoney}"
                                       class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code="sh.alipay.partners.id" />ID</label>
                            <div class="layui-input-inline">
                                <input type="text" name="spid" value="${aPmerchantConf.spid}"
                                       class="layui-input"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code="sh.alipay.public.key" /></label>
                            <div class="layui-input-block">
                    <textarea id="spublicKey" name="spublicKey" class="layui-textarea"
                              placeholder='<spring:message code="sh.please.enter.the.alipay.public.key" />'>${aPmerchantConf.spublicKey}</textarea>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code="sh.alipay.application.private.key" /></label>
                            <div class="layui-input-block">
                    <textarea id="sprivateKey" name="sprivateKey" class="layui-textarea"
                              placeholder='<spring:message code="sh.please.enter.the.alipay.application.private.key" />'>${aPmerchantConf.sprivateKey}</textarea>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code="sh.alipay.application.private.key" /></label>
                            <div class="layui-input-block">
                    <textarea id="sappPublicKey" name="sappPublicKey" class="layui-textarea"
                              placeholder='<spring:message code="sh.please.enter.the.alipay.application.private.key" />'>${aPmerchantConf.sappPublicKey}</textarea>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-input-block">
                            <input type="hidden" name="smerchantId" value="${merchantInfo.id}"/>
                            <input type="hidden" name="id" value="${aPmerchantConf.id}"/>
                            <input type="hidden" name="WCId" value="${wCmerchantConf.id}"/>
                        </div>
                    </div>
                    <div class="layui-form-item fixed-bottom">
                        <div class="layui-input-block">
                            <button class="layui-btn layui-btn-primary" id="cancelBtnConf"><spring:message code="main.cancel" /></button>
                            <button class="layui-btn" id="myConfFormSub"><spring:message code="sh.save.configuration" />
                            </button>
                        </div>
                    </div>
                </form>
            </div>
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
    $(document).ready(function () {
        if (${not  empty merchantInfo.id}) {
            if (${20 == merchantInfo.idbWap }) {
                $("#dbName").css("display", "block");
            } else {
                $("#dbName").css("display", "none");
            }
        }
    });
    $(document).ready(function () {
        if (${not  empty merchantInfo.id}) {
            if (${20 == merchantInfo.itype }) {
                $("#typediv").css("display", "block");
            } else {
                $("#typediv").css("display", "none");
            }
        }
    });
    /**
     * 关闭并加载更新表格
     */
    function closeLayerAndRefresh(index) {
        layer.close(index);
        reloadGrid(true);
    }


    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate', 'upload', 'element'], function () {
        //Table Start
        var $ = layui.jquery
            , element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块
        //Table End
        var form = layui.form;


        //执行一个laydate实例
        var laydate = layui.laydate;
        laydate.render({
            elem: '#dexpireDate',//指定元素
            type: 'date'
        });
        laydate.render({
            elem: '#dsignDate',
            type: 'date'
        });
        var $ = layui.jquery
            , upload = layui.upload;
        $("#cancelBtnImg").click(function () {
            $(upImg).children("span").eq(0).text(" ");
            AttachImg.config.elem.next()[0].value = '';
            $("#sremark").val('');
            setResetFormValues();
            return false;
        });
        //资质证书图片上传
        /*   var AttachImg = upload.render({
         auto: false
         , elem: '#attachUpBut'
         , multiple: false
         , field: "attachFile"
         , size: 200
         , accept: 'images'
         , exts: 'jpg'
         , number: 5
         });*/
        //<spring:message code="sh.multiple.image.upload" />
        var AttachImg = upload.render({
            auto: false
            , elem: '#attachUpBut'
            , multiple: false
            , field: "attachFile"
            , size: 200
            , accept: 'images'
            , exts: 'jpg|png'
            , number: 5
            , before: function (obj) {
                //预读本地文件示例，不支持ie8
                obj.preview(function (index, file, result) {
                    $('#demo2').append('<img src="' + result + '" alt="' + file.name + '" class="layui-upload-img">')
                });
            }
            , done: function (res) {
                //上传完毕
            }
        });
        upload.render({
            auto: false
            , elem: '#slogoBut'
            , multiple: false
            , field: "slogoFile"
            , size: 200
            , accept: 'images'
            , exts: 'jpg|png'

        });
        upload.render({
            auto: false
            , elem: '#loginLogoBut'
            , multiple: false
            , field: "loginLogofile"
            , size: 200
            , accept: 'images'
            , exts: 'jpg|png'
        });
        //监听提交
        $("#myForm").Validform({
            btnSubmit: "#myFormSub",  //根据id触发
            tiptype: 3,                  //第三种方式
            showAllError: true,
            beforeSubmit: function () { //验证过后执行save方法
                var idbWap = $("#idbWap option:selected").val();
                var sdbName = $("#sdbName").val();

                if (20 == idbWap && isEmpty(sdbName)) {
                    alertDel('<spring:message code="sh.please.select.docking.bd" />');
                    return false;
                }
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

        $("#myImgForm").Validform({
            btnSubmit: "#myImgFormSub",  //根据id触发
            tiptype: 3,                  //第三种方式
            showAllError: true,
            beforeSubmit: function () { //验证过后执行save方法
                var attach = AttachImg.config.elem.next()[0].value;
                if (attach.replace(/(^\s*)|(\s*$)/g, "").length == 0) {
                    alertDel('<spring:message code="sh.please.select.a.qualification.attachment" />！');
                    return false;
                }
                var loadIndex = loading();
                $('#myImgForm').ajaxSubmit({
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
                            $(upImg).children("span").eq(0).text(" ");
                            AttachImg.config.elem.next()[0].value = '';
                            $("#sremark").val('');
                            setResetFormValues();
                            alertMsgAndReload("<spring:message code='main.success' />", "");
                        } else {
                            alertDel(msg.msg);
                        }
                    }
                });
                return false;

            }
        });

        $("#myConfForm").Validform({
            btnSubmit: "#myConfFormSub",  //根据id触发
            tiptype: 3,                  //第三种方式
            showAllError: true,
            beforeSubmit: function () { //验证过后执行save方法
                var loadIndex = loading();

                $('#myConfForm').ajaxSubmit({
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
        form.on('select(dbWap)', function (data) {
            if (20 == data.value) {
                $("#dbName").css("display", "block");
            } else {
                $("#dbName").css("display", "none");
                $("#sdbName").val("");
                $("#sdbId").val("");
            }
        });
        form.on('select(merchantType)', function (data) {
            if (20 == data.value) {
                $("#typediv").css("display", "block");
            } else {
                $("#typediv").css("display", "none");
                $("#typediv").val("");
            }
        });

        //BD添加
        var $ = layui.$, active = {
            //选择Bd
            sel: function () {
                showLayer('<spring:message code="sh.please.select.docking.bd" />', ctx + "/operator/selectBdList", {area: ['100%', '80%']});
            }
        };
        $('.layui-form .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

    });
    $(function () {
        $("#cancelBtnConf").click(function () {
            parent.layer.close(index);
            return false;
        });
    });
    $(function () {
        $("#cancelBtn").click(function () {
            parent.layer.close(index);
            return false;
        });
    });
    //接收子页面传来的数据
    function selectSupp(suppId, srealName, suserName) {
        $("#sdbName").val(srealName);
        $("#sdbId").val(suppId);
    }
    function refreshTable() {
        reloadGrid(true);
    }
    //上传图片table展示
    $(function () {

        $.jgrid.defaults.styleUI = 'Bootstrap';
        $("#gridTable").jqGrid({
            url: ctx + '/merchantAttach/queryAttachDetail',
            datatype: "json",
            height: 180,
            postData: {smerchantId: '${merchantInfo.id}'},
            width: $(".ibox-content").width(),
            shrinkToFit: true,
            /*     multiselect:true,*/
            colModel: [
                {name: 'id', index: 'id', hidden: true},
                {
                    name: 'scode', index: 'scode', label: '<spring:message code="sh.name" />', width: 150, sortable: false, formatter: "select",
                    editoptions: {value: '10:<spring:message code="sh.business.license" />;20:<spring:message code="sh.scanned.piece" />;30:<spring:message code="sh.general.taxpayer.certificate" />;40:<spring:message code="sh.copy.of.corporate.id.card" />;50:<spring:message code="sh.food.distribution.license" />'}
                },
                {
                    name: 'irangeType',
                    index: 'irange_type',
                    label: '<spring:message code="sh.image" />',
                    sortable: false,
                    formatter: function (value, rowObject) {
                        return "<a href='javascript:void(0);' onclick=\"viewImg('" + rowObject.rowId + "')\"><spring:message code='sh.view' /></a>";
                    }
                },
                {name: 'isort', index: 'isort', label: '<spring:message code="main.sort" />', width: 150, sortable: false},
                {name: 'sremark', index: 'sremark', label: '<spring:message code="sh.explain" />', width: 150, sortable: false},
                {name: 'process', index: 'process', label: '<spring:message code="main.operation" />', sortable: false, width: 150}
            ],
            rownumbers: true,
            viewrecords: true,
            ondblClickRow: function (rowId) {//双击编辑
                showLayer('<spring:message code="sh.edit" />', ctx + '/merchantAttach/editDetail?sid=' + rowId, {area: ['70%', '50%']});
            },
            gridComplete: function () {
                setTimeout(function () {
                    var ids = $("#gridTable").jqGrid('getDataIDs');
                    for (var i = 0; i < ids.length; i++) {
                        var cl = ids[i];
                        var str = '';
                        str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"editMethod('"
                            + cl + "')\"><spring:message code='sh.edit' /></a> | ";
                        str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"delMethod('"
                            + cl + "')\"><spring:message code='main.delete' /></a> | ";
                        $("#gridTable").jqGrid('setRowData',
                            ids[i], {
                                process: str.substr(0, str.lastIndexOf(" | "))
                            });
                    }
                }, 0);
            }
        });
        $(window).bind('resize', function () {
            var width = $('.jqGrid_wrapper').width();
            $('#gridTable').setGridWidth(width);
        });
    });
    function editMethod(sid) {
        showLayer('<spring:message code="sh.edit" />', ctx + '/merchantAttach/editDetail?sid=' + sid, {area: ['70%', '50%']});
    }
    function delMethod(sid) {
        confirmDelTip('<spring:message code="sh.are.you.sure.you.want.to.delete.the.picture" />', ctx + "/merchantAttach/deleteDetail", {checkboxId: sid});
    }
    //查看图片
    function viewImg(sid) {
        showLayer("<spring:message code='sh.refund.picture' />", ctx + '/merchantAttach/viewImg?sid=' + sid, {area: ['60%', '40%']});
    }

    $(function () {

        $.jgrid.defaults.styleUI = 'Bootstrap';
        $("#ConfgridTable").jqGrid({
            url: ctx + '/merchantConf/queryDetail',
            datatype: "json",
            height: 180,
            postData: {smerchantId: '${merchantInfo.id}'},
            width: $(".ibox-content").width(),
            shrinkToFit: true,
            /*     multiselect:true,*/
            colModel: [
                {name: 'id', index: 'id', hidden: true},
                {
                    name: 'itype', index: 'itype', label: "<spring:message code='sp.category.types.of' />", width: 150, sortable: false, formatter: "select",
                    editoptions: {value: '10:<spring:message code="sh.alipay.configuration" />;20:<spring:message code="sh.wechat.configuration" />'}
                },
                {name: 'scallBackUrl', index: 'scallBackUrl', label: '<spring:message code="sh.authorized.callback.url" />', width: 150, sortable: false},
                {name: 'sappId', index: 'sappId', label: "APPID", width: 150, sortable: false},
                {name: 'sappSecret', index: 'sappSecret', label: '<spring:message code="sh.md5.secret.key" />', width: 150, sortable: false},
                {
                    name: 'scode', index: 'scode', label: '<spring:message code="sh.name" />', width: 150, sortable: false, formatter: "select",
                    editoptions: {value: '10:<spring:message code="sh.business.license" />;20:<spring:message code="sh.scanned.piece" />;30:<spring:message code="sh.general.taxpayer.certificate" />;40:<spring:message code="sh.copy.of.corporate.id.card" />;50:<spring:message code="sh.food.distribution.license" />'}
                },
                {
                    name: 'irangeType',
                    index: 'irange_type',
                    label: "<spring:message code='sh.image' />",
                    sortable: false,
                    formatter: function (value, rowObject) {
                        return "<a href='javascript:void(0);' onclick=\"viewImg('" + rowObject.rowId + "')\"><spring:message code="sh.view" /></a>";
                    }
                },
                {name: 'isort', index: 'isort', label: '<spring:message code="main.sort" />', width: 150, sortable: false},
                {name: 'sremark', index: 'sremark', label: '<spring:message code="sh.explain" />', width: 150, sortable: false},
                {name: 'process', index: 'process', label: '<spring:message code="main.operation" />', sortable: false, width: 150}
            ],
            rownumbers: true,
            viewrecords: true,
            ondblClickRow: function (rowId) {//双击编辑
                showLayer('<spring:message code="sh.edit" />', ctx + '/merchantAttach/editDetail?sid=' + rowId, {area: ['70%', '40%']});
            },
            gridComplete: function () {
                setTimeout(function () {
                    var ids = $("#ConfgridTable").jqGrid('getDataIDs');
                    for (var i = 0; i < ids.length; i++) {
                        var cl = ids[i];
                        var str = '';
                        str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"editMethod('"
                            + cl + "')\"><spring:message code='sh.edit' /></a> | ";
                        str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"delMethod('"
                            + cl + "')\"><spring:message code='main.delete' /></a> | ";
                        $("#ConfgridTable").jqGrid('setRowData',
                            ids[i], {
                                process: str.substr(0, str.lastIndexOf(" | "))
                            });
                    }
                }, 0);
            }
        });
        $(window).bind('resize', function () {
            var width = $('.jqGrid_wrapper').width();
            $('#ConfgridTable').setGridWidth(width);
        });
    });
</script>
</body>

