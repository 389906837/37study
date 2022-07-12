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
            <li class="layui-this" id="merchantInfoLi">基本信息</li>
            <li id="attachLi">资质附件信息</li>
            <li>微信、支付宝配置</li>
        </ul>
        <div class="layui-tab-content" style="height: 100px;">
            <div class="layui-tab-item layui-show" name="merchantInfoDiv" id="merchantInfoDiv">
                <form class="layui-form" action="${ctx}/merchantInfo/save" method="post" id="myForm"
                      enctype="multipart/form-data">
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"> 商户名称</label>
                            <div class="layui-input-inline">
                                <input type="text" name="sname" id="sname"
                                       value="${merchantInfo.sname}" datatype="*" nullmsg="请输入商户名称"
                                       class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label">商户类型</label>
                            <div class="layui-input-inline">
                                <cang:select type="list" name="itype" id="itype" entire="true"
                                             exp="datatype=\"*\" nullmsg=\"请选择商户类型\""
                                             layFilter="merchantType"
                                             value="${merchantInfo.itype}" entireName="请选择商户类型"
                                             list="{10:个人,20:企业}"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">

                        <div class="layui-col-md6">
                            <label class="layui-form-label">公司类型</label>
                            <div class="layui-input-inline">
                                <cang:select type="list" name="icompanyType" id="icompanyType" entire="true"
                                             value="${merchantInfo.icompanyType}" entireName="请选择公司类型"
                                             exp="datatype=\"*\" nullmsg=\"请选择公司类型\""
                                             list="{10:国企,20:民营}"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label">二级分销状态</label>
                            <div class="layui-input-inline">
                                <cang:select type="list" name="idistributionSwitch" id="idistributionSwitch"
                                             entire="true"
                                             exp="datatype=\"*\" nullmsg=\"请选择二级分销状态\""
                                             value="${merchantInfo.idistributionSwitch}" entireName="请选择二级分销状态"
                                             list="{0:关闭,1:开启}"/>
                            </div>
                        </div>

                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"> 签约日期</label>
                            <div class="layui-input-inline">
                                <input type="text" name="dsignDate" datatype="*" nullmsg="请选择签约日期" id="dsignDate"
                                       value="<fmt:formatDate value='${merchantInfo.dsignDate}' pattern='yyyy-MM-dd'/>"
                                       class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"> 合约到期日</label>
                            <div class="layui-input-inline">
                                <input type="text" name="dexpireDate" datatype="*" nullmsg="请选择合约到期日" id="dexpireDate"
                                       value="<fmt:formatDate value='${merchantInfo.dexpireDate}' pattern='yyyy-MM-dd'/>"
                                       class="layui-input"/>

                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label">SKU类型</label>
                            <div class="layui-input-inline">
                                <cang:select type="list" name="ivmSkuType" id="ivmSkuType"
                                             entire="true"
                                             exp="datatype=\"*\" nullmsg=\"请选择SKU类型\""
                                             value="${merchantInfo.ivmSkuType}" entireName="请选择SKU类型"
                                             list="{1:全部,2:部分}"/>
                            </div>
                        </div>
                        <c:if test="${empty merchantInfo.id}">
                            <div class="layui-col-md6">
                                <label class="layui-form-label">联系人用户名</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantUserName"
                                           id="merchantUserName"
                                           datatype="*" nullmsg="请输入联系人用户名"
                                           class="layui-input"/>
                                </div>
                            </div>
                        </c:if>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label">联系人</label>
                            <div class="layui-input-inline">
                                <input type="text" name="scontactName" id="scontactName" datatype="*" nullmsg="请输入联系人"
                                       value="${merchantInfo.scontactName}" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"> 联系人电话</label>
                            <div class="layui-input-inline">
                                <input type="text" name="scontactMobile" id="scontactMobile" datatype="*"
                                       nullmsg="请输入联系电话"
                                       value="${merchantInfo.scontactMobile}" class="layui-input">
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label">联系人邮箱</label>
                            <div class="layui-input-inline">
                                <input type="text" name="scontactEmail" id="scontactEmail" datatype="*"
                                       nullmsg="请输入联系人邮箱"
                                       value="${merchantInfo.scontactEmail}" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"> 合作模式</label>
                            <div class="layui-input-inline">
                                <cang:select type="dic" groupNo="SP000104" entire="true"
                                             exp="datatype=\"*\" nullmsg=\"请选择合作模式\""
                                             value="${merchantInfo.icooperationMode}" entireName="请选择合作模式"
                                             name="icooperationMode" id="icooperationMode"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label">BD方式</label>
                            <div class="layui-input-inline">
                                <cang:select type="list" entire="true" value="${merchantInfo.idbWap}"
                                             entireName="请选择BD方式"
                                             exp="datatype=\"*\" nullmsg=\"请选择BD方式\"" layFilter="dbWap"
                                             name="idbWap" id="idbWap" list="{10:公司,20:个人}"/>
                            </div>
                        </div>
                        <div class="layui-col-md6" name="dbName" id="dbName" style="display:none;">
                            <label class="layui-form-label">对接BD姓名</label>
                            <div class="layui-input-inline" style="width: 115px">
                                <input type="text" name="sdbName" readonly="true" id="sdbName"
                                       value="${merchantInfo.sdbName}"
                                       class="layui-input"/>
                            </div>
                            <button class="layui-btn" id="btn1" style="display: inline-block;float: left;" type="button"
                                    data-type="sel">选择
                            </button>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">备注</label>
                        <div class="layui-input-block">
                    <textarea id="sremark2" name="sremark" class="layui-textarea"
                              placeholder="备注（非必填项）">${merchantInfo.sremark}</textarea>
                        </div>
                    </div>

                    <div class="wfsplitt">
                        客户端配置
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label">支持支付方式</label>
                            <div class="layui-input-inline">
                                <cang:select type="list" id="isupportPayWay" name="isupportPayWay"
                                             exp="datatype=\"*\" nullmsg=\"请选择支持支付方式\""
                                             value="${merchantClientConf.isupportPayWay}" entireName="请选择支持支付方式"
                                             list="{10:全部,20:仅代扣,30:仅手动}" entire="true"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <div class="layui-inline">
                            <button type="button" class="layui-btn" id="slogoBut">商户logo上传</button>
                            <input type="hidden" id="slogo" name="slogo" value=""/>
                            <c:if test="${not empty  merchantClientConf.slogo}">
                                <blockquote class="layui-elem-quote layui-quote-nm layui-quote-nm-1">
                                    预览图：
                                    <div class="layui-upload-list" id="slogoYl">
                                        <p style="text-align: left;">
                                            <img src="${dynamicSource}${merchantClientConf.slogo}"
                                                 style='width: 100px; height: 100px;'>
                                        </p>
                                    </div>
                                </blockquote>
                                <span class="require">(180*90像素,白色背景，低于200kb的jpg或png图片)</span>
                            </c:if>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <div class="layui-inline">
                            <button type="button" class="layui-btn" id="loginLogoBut">商户登录Logo上传</button>
                            <input type="hidden" id="loginLogo" name="loginLogo" value=""/>
                            <c:if test="${ not empty  merchantClientConf.sloginLogo}">
                                <blockquote class="layui-elem-quote layui-quote-nm layui-quote-nm-1">
                                    预览图：
                                    <div class="layui-upload-list" id="loginslogoYl">
                                        <p style="text-align: left;">
                                            <img src="${dynamicSource}${merchantClientConf.sloginLogo}"
                                                 style='width: 100px; height: 100px;'>
                                        </p>
                                    </div>
                                </blockquote>
                                <span class="require">(180*90像素,白色背景，低于200kb的jpg或png图片)</span>
                            </c:if>
                            </div>
                        </div>
                    </div>

                    <div 　 style="display:none;" name="typediv" id="typediv">
                        <div class="wfsplitt">
                            基础详细信息
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label">营业地址</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="sopenAddress" id="sopenAddress"
                                           value="${merchantDetail.sopenAddress}"
                                           class="layui-input"/>
                                </div>
                            </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label">公司注册地址</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="sregisterAddress" id="sregisterAddress"
                                           value="${merchantDetail.sregisterAddress}"
                                           class="layui-input"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label">公司电话</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="sphone" id="sphone"
                                           value="${merchantDetail.sphone}"
                                           class="layui-input"/>
                                </div>
                            </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label">公司传真</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="sfax" id="sfax" value="${merchantDetail.sfax}"
                                           class="layui-input"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label">公司网址</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="swebsiteUrl" id="swebsiteUrl"
                                           value="${merchantDetail.swebsiteUrl}"
                                           class="layui-input"/>
                                </div>
                            </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label">公司法人</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="slegalPerson" id="slegalPerson"
                                           value="${merchantDetail.slegalPerson}"
                                           class="layui-input"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label">公司法人身份证</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="sidCard" id="sidCard"
                                           value="${merchantDetail.sidCard}"
                                           class="layui-input"/>
                                </div>
                            </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label">公司对公账号</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="saccountNumber" id="saccountNumber"
                                           value="${merchantDetail.saccountNumber}"
                                           class="layui-input"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label">公司对公账号名称</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="saccountName" id="saccountName"
                                           value="${merchantDetail.saccountName}"
                                           class="layui-input"/>
                                </div>
                            </div>
                            <div class="layui-col-md6">
                                <label class="layui-form-label">公司对公账号开户行</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="saccountBank" id="saccountBank"
                                           value="${merchantDetail.saccountBank}"
                                           class="layui-input"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label">公司对公税号</label>
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
                            <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                            <button class="layui-btn" id="myFormSub">保存</button>
                        </div>
                    </div>
                </form>
            </div>

            <div class="layui-tab-item" name="attachDiv" id="attachDiv">
                <form class="layui-form" action="${ctx}/merchantAttach/save" method="post" id="myImgForm"
                      enctype="multipart/form-data">
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"> 资质附件类型</label>
                            <div class="layui-input-inline">
                                <cang:select type="dic" groupNo="SP000105" entire="true" value=""
                                             exp="datatype=\"*\" nullmsg=\"请选择资质附件类型\""
                                             name="scode" id="scode"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label">显示顺序</label>
                            <div class="layui-input-inline">
                                <input type="text" name="isort" id="isort" datatype="*" nullmsg="请输入显示顺序"
                                       value=""
                                       class="layui-input"/>
                            </div>
                        </div>
                    </div>
                    <%--<div class="layui-form-item">
                        <div class="layui-col-md6" id="upImg">
                            <button type="button" class="layui-btn" id="attachUpBut">资质附件上传</button>
                        </div>
                    </div>--%>
                    <div class="layui-form-item">
                        <div class="layui-col-md6" id="upImg">
                            <div class="layui-inline">
                            <button type="button" class="layui-btn" id="attachUpBut">多图片上传</button>
                            <blockquote class="layui-elem-quote layui-quote-nm layui-quote-nm-1">
                                预览图：
                                <div class="layui-upload-list" id="demo2"></div>
                            </blockquote>
                                <span class="require">(180*90像素,白色背景，低于200kb的jpg或png图片)</span>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">备注</label>
                        <div class="layui-input-block">
                    <textarea id="sremark" name="sremark" class="layui-textarea"
                              placeholder="备注（非必填项）"></textarea>
                        </div>
                    </div>
                    <div class="layui-form-item text-center m-t">
                        <div class="layui-input-block">
                            <div class="layui-input-block">
                                <button class="layui-btn layui-btn-primary" id="cancelBtnImg">清空</button>
                                <button class="layui-btn" id="myImgFormSub">保存</button>
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
                        微信配置
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"> 授权回调URL</label>
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
                            <label class="layui-form-label">MD5秘钥</label>
                            <div class="layui-input-inline">
                                <input type="text" name="WCsappSecret" value="${wCmerchantConf.sappSecret}"

                                       class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label">单笔支付限额</label>
                            <div class="layui-input-inline">
                                <input type="text" name="WCfpayLimitSingle" value="${wCmerchantConf.fpayLimitSingle}"


                                       class="layui-input"/>
                            </div>
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label">单日支付限额</label>
                            <div class="layui-input-inline">
                                <input type="text" name="WCfpayLimitDay" value="${wCmerchantConf.fpayLimitDay}"


                                       class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label">单月支付限额</label>
                            <div class="layui-input-inline">
                                <input type="text" name="WCfpayLimitMonth" value="${wCmerchantConf.fpayLimitMonth}"

                                       class="layui-input"/>
                            </div>
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label">单笔金额短信通知</label>
                            <div class="layui-input-inline">
                                <input type="text" name="WCfpayLimitMoney" value="${wCmerchantConf.fpayLimitMoney}"
                                       class="layui-input"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label">微信公钥</label>
                            <div class="layui-input-block">
                                  <textarea id="WCspublicKey" name="WCspublicKey" class="layui-textarea"
                                            placeholder="请输入微信公钥">${wCmerchantConf.spublicKey}</textarea>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label">微信应用私钥</label>
                            <div class="layui-input-block">
                                  <textarea id="WCsprivateKey" name="WCsprivateKey" class="layui-textarea"
                                            placeholder="请输入微信应用私钥">${wCmerchantConf.sprivateKey}</textarea>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label">微信应用公钥</label>
                            <div class="layui-input-block">
                                  <textarea id="WCsappPublicKey" name="WCsappPublicKey" class="layui-textarea"
                                            placeholder="请输入微信应用公钥">${wCmerchantConf.sappPublicKey}</textarea>
                            </div>
                        </div>
                    </div>
                    <div class="wfsplitt">
                        支付宝配置
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"> 授权回调URL</label>
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
                            <label class="layui-form-label">MD5秘钥</label>
                            <div class="layui-input-inline">
                                <input type="text" name="sappSecret" value="${aPmerchantConf.sappSecret}"

                                       class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label">单笔支付限额</label>
                            <div class="layui-input-inline">
                                <input type="text" name="fpayLimitSingle" value="${aPmerchantConf.fpayLimitSingle}"

                                       class="layui-input"/>
                            </div>
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label">单日支付限额</label>
                            <div class="layui-input-inline">
                                <input type="text" name="fpayLimitDay" value="${aPmerchantConf.fpayLimitDay}"

                                       class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label">单月支付限额</label>
                            <div class="layui-input-inline">
                                <input type="text" name="fpayLimitMonth" value="${aPmerchantConf.fpayLimitMonth}"

                                       class="layui-input"/>
                            </div>
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label">单笔金额短信通知</label>
                            <div class="layui-input-inline">
                                <input type="text" name="fpayLimitMoney" value="${aPmerchantConf.fpayLimitMoney}"
                                       class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label">支付宝合作伙伴ID</label>
                            <div class="layui-input-inline">
                                <input type="text" name="spid" value="${aPmerchantConf.spid}"
                                       class="layui-input"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label">支付宝公钥</label>
                            <div class="layui-input-block">
                    <textarea id="spublicKey" name="spublicKey" class="layui-textarea"
                              placeholder="请输入支付宝公钥">${aPmerchantConf.spublicKey}</textarea>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label">支付宝应用私钥</label>
                            <div class="layui-input-block">
                    <textarea id="sprivateKey" name="sprivateKey" class="layui-textarea"
                              placeholder="请输入支付宝应用私钥">${aPmerchantConf.sprivateKey}</textarea>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label">支付宝应用公钥</label>
                            <div class="layui-input-block">
                    <textarea id="sappPublicKey" name="sappPublicKey" class="layui-textarea"
                              placeholder="请输入支付宝应用公钥">${aPmerchantConf.sappPublicKey}</textarea>
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
                            <button class="layui-btn layui-btn-primary" id="cancelBtnConf">取消</button>
                            <button class="layui-btn" id="myConfFormSub">保存配置
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js"></script>
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
        //多图片上传
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
                    alertDel("请选择对接Bd");
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
                    alertDel("请选择资质附件!");
                    return false;
                }
                var loadIndex = loading();
                $('#myImgForm').ajaxSubmit({
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
                            $(upImg).children("span").eq(0).text(" ");
                            AttachImg.config.elem.next()[0].value = '';
                            $("#sremark").val('');
                            setResetFormValues();
                            alertMsgAndReload("操作成功", "");
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
                showLayer("选择对接BD", ctx + "/operator/selectBdList", {area: ['100%', '80%']});
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
                /* {name: 'sname', index: 'sname', label: "名称", width: 150, sortable: false},*/
                {
                    name: 'scode', index: 'scode', label: "名称", width: 150, sortable: false, formatter: "select",
                    editoptions: {value: '10:营业执照;20:扫描件;30:一般纳税人证明;40:法人身份证复印件;50:食品流通许可证'}
                },
                {
                    name: 'irangeType',
                    index: 'irange_type',
                    label: "图片",
                    sortable: false,
                    formatter: function (value, rowObject) {
                        return "<a href='javascript:void(0);' onclick=\"viewImg('" + rowObject.rowId + "')\">查看</a>";
                    }
                },
                {name: 'isort', index: 'isort', label: "排序号", width: 150, sortable: false},
                {name: 'sremark', index: 'sremark', label: "说明", width: 150, sortable: false},
                {name: 'process', index: 'process', label: "操作", sortable: false, width: 150}
            ],
            rownumbers: true,
            viewrecords: true,
            ondblClickRow: function (rowId) {//双击编辑
                showLayer("编辑", ctx + '/merchantAttach/editDetail?sid=' + rowId, {area: ['70%', '50%']});
            },
            gridComplete: function () {
                setTimeout(function () {
                    var ids = $("#gridTable").jqGrid('getDataIDs');
                    for (var i = 0; i < ids.length; i++) {
                        var cl = ids[i];
                        var str = '';
                        str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"editMethod('"
                            + cl + "')\">编辑</a> | ";
                        str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"delMethod('"
                            + cl + "')\">删除</a> | ";
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
        showLayer("编辑", ctx + '/merchantAttach/editDetail?sid=' + sid, {area: ['70%', '50%']});
    }
    function delMethod(sid) {
        confirmDelTip("确定要删除图片?", ctx + "/merchantAttach/deleteDetail", {checkboxId: sid});
    }
    //查看图片
    function viewImg(sid) {
        showLayer("退款图片", ctx + '/merchantAttach/viewImg?sid=' + sid, {area: ['60%', '40%']});
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
                    name: 'itype', index: 'itype', label: "类型", width: 150, sortable: false, formatter: "select",
                    editoptions: {value: '10:支付宝配置;20:微信配置'}
                },
                {name: 'scallBackUrl', index: 'scallBackUrl', label: "授权回调Url", width: 150, sortable: false},
                {name: 'sappId', index: 'sappId', label: "APPID", width: 150, sortable: false},
                {name: 'sappSecret', index: 'sappSecret', label: "MD5秘钥", width: 150, sortable: false},
                {
                    name: 'scode', index: 'scode', label: "名称", width: 150, sortable: false, formatter: "select",
                    editoptions: {value: '10:营业执照;20:扫描件;30:一般纳税人证明;40:法人身份证复印件;50:食品流通许可证'}
                },
                {
                    name: 'irangeType',
                    index: 'irange_type',
                    label: "图片",
                    sortable: false,
                    formatter: function (value, rowObject) {
                        return "<a href='javascript:void(0);' onclick=\"viewImg('" + rowObject.rowId + "')\">查看</a>";
                    }
                },
                {name: 'isort', index: 'isort', label: "排序号", width: 150, sortable: false},
                {name: 'sremark', index: 'sremark', label: "说明", width: 150, sortable: false},
                {name: 'process', index: 'process', label: "操作", sortable: false, width: 150}
            ],
            rownumbers: true,
            viewrecords: true,
            ondblClickRow: function (rowId) {//双击编辑
                showLayer("编辑", ctx + '/merchantAttach/editDetail?sid=' + rowId, {area: ['70%', '40%']});
            },
            gridComplete: function () {
                setTimeout(function () {
                    var ids = $("#ConfgridTable").jqGrid('getDataIDs');
                    for (var i = 0; i < ids.length; i++) {
                        var cl = ids[i];
                        var str = '';
                        str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"editMethod('"
                            + cl + "')\">编辑</a> | ";
                        str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"delMethod('"
                            + cl + "')\">删除</a> | ";
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

