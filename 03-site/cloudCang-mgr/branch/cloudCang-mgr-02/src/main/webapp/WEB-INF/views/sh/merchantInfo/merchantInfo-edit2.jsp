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
                                             layFilter="companyType"
                                             value="${merchantInfo.icompanyType}" entireName="请选择公司类型"
                                             exp="datatype=\"*\" nullmsg=\"请选择公司类型\""
                                             list="{10:国企,20:民营}"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label">二级分销状态</label>
                            <div class="layui-input-inline">
                                <cang:select type="list" name="idistributionSwitch" id="idistributionSwitch"
                                             exp="datatype=\"*\" nullmsg=\"请选择二级分销状态\""
                                             value="${merchantInfo.idistributionSwitch}"
                                             list="{0:关闭,1:开启}"/>
                            </div>
                        </div>

                    </div>

                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label">SKU使用范围</label>
                            <div class="layui-input-inline">
                                <cang:select type="list" name="ivmSkuType" id="ivmSkuType"
                                             entire="true" layFilter="vmSkuType"
                                             exp="datatype=\"*\" nullmsg=\"请选择SKU使用范围\""
                                             value="${merchantInfo.ivmSkuType}" entireName="请选择SKU使用范围"
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
                        <c:if test="${not empty merchantInfo.id}">
                            <div class="layui-col-md6">
                                <label class="layui-form-label"> 签约日期</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="dsignDate" datatype="*" nullmsg="请选择签约日期" id="dsignDate2"
                                           value="<fmt:formatDate value='${merchantInfo.dsignDate}' pattern='yyyy-MM-dd'/>"
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
                    <c:if test="${ empty merchantInfo.id}">
                        <div class="layui-form-item">
                            <div class="layui-col-md6">
                                <label class="layui-form-label"> 签约日期</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="dsignDate" datatype="*" nullmsg="请选择签约日期" id="dsignDate"
                                        <%--   value="<fmt:formatDate value='${merchantInfo.dsignDate}' pattern='yyyy-MM-dd'/>"--%>
                                           class="layui-input"/>
                                </div>
                            </div>
                                <%--    <div class="layui-col-md6">
                                        <label class="layui-form-label"> 合约到期日</label>
                                        <div class="layui-input-inline">
                                            <input type="text" name="dexpireDate" datatype="*" nullmsg="请选择合约到期日" id="dexpireDate"
                                                   value="<fmt:formatDate value='${merchantInfo.dexpireDate}' pattern='yyyy-MM-dd'/>"
                                                   class="layui-input"/>

                                        </div>
                                    </div>--%>
                        </div>
                    </c:if>
                    <div class="layui-form-item">
                        <label class="layui-form-label"> 联系人地址</label>
                        <div class="layui-input-block">
                    <textarea id="scontactAddress" name="scontactAddress" class="layui-textarea layui-form-textarea80p"
                              placeholder="请输入联系人地址">${merchantInfo.scontactAddress}</textarea>
                        </div>
                    </div>

                    <div class="layui-form-item mt13">
                        <div class="layui-col-md6">
                            <label class="layui-form-label">联系人邮箱</label>
                            <div class="layui-input-inline">
                                <input type="text" name="scontactEmail" id="scontactEmail"
                                       value="${merchantInfo.scontactEmail}" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"> 合作模式</label>
                            <div class="layui-input-inline">
                                <cang:select type="dic" groupNo="SP000104" entire="true"
                                             layFilter="cooperationMode"
                                             value="${merchantInfo.icooperationMode}" entireName="请选择合作模式"
                                             name="icooperationMode" id="icooperationMode"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label">业务拓展方式</label>
                            <div class="layui-input-inline">
                                <cang:select type="list" entire="true" value="${merchantInfo.idbWap}"
                                             entireName="请选择业务拓展方式"
                                             layFilter="dbWap"
                                             name="idbWap" id="idbWap" list="{10:公司,20:个人}"/>
                            </div>
                        </div>
                        <div class="layui-col-md6" name="dbName" id="dbName" style="display:none;">
                            <label class="layui-form-label">对接业务员</label>
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
                    <textarea id="sremark2" name="sremark" class="layui-textarea layui-form-textarea80p"
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
                                             exp="datatype=\"*\" nullmsg=\"请选择支持支付方式\"" layFilter="supportPayWay"
                                             value="${merchantClientConf.isupportPayWay}" entireName="请选择支持支付方式"
                                             list="{10:全部,20:仅代扣,30:仅手动}" entire="true"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label">退款是否需要审核</label>
                            <div class="layui-input-inline">
                                <cang:select type="list" id="iisRefundAudit" name="iisRefundAudit"
                                             exp="datatype=\"*\" nullmsg=\"请选择类型\""
                                             value="${merchantClientConf.iisRefundAudit}"
                                             list="{0:否,1:是}"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label">代扣方式</label>
                            <div class="layui-input-inline">
                                <cang:select type="list" id="iwithholdingWay" name="iwithholdingWay"
                                             exp="datatype=\"*\" nullmsg=\"请选择代扣方式\"" layFilter="withholdingWay"
                                             value="${merchantClientConf.iwithholdingWay}"
                                             list="{10:商户代扣,20:单次代扣}" />
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label">称重稳定盘货次数</label>
                            <div class="layui-input-inline">
                                <input type="text" name="iweightStockNum" id="iweightStockNum"
                                       datatype="*"
                                       nullmsg="请输入称重稳定盘货次数！"
                                       value="${merchantClientConf.iweightStockNum}"
                                       class="layui-input"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label">客服电话</label>
                            <div class="layui-input-inline">
                                <input type="text" name="KFscontactMobile" id="KFscontactMobile"
                                       datatype="*"
                                       nullmsg="请输入客服电话！"
                                       value="${merchantClientConf.scontactMobile}"
                                       class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label">商户简称</label>
                            <div class="layui-input-inline">
                                <input type="text" name="sshortName" id="sshortName"
                                       datatype="*"
                                       nullmsg="请输入商户简称！"
                                       value="${merchantClientConf.sshortName}"
                                       class="layui-input"/>
                            </div>
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"></label>
                            <div class="layui-inline">
                                <button type="button" class="layui-btn" id="slogoBut">商户logo上传</button>
                                <blockquote class="layui-elem-quote layui-quote-nm layui-quote-nm-1">
                                    预览图
                                    <div class="layui-upload-list" id="slogo">
                                        <c:if test="${!empty  merchantClientConf.slogo}">
                                            <img src="${dynamicSource}${merchantClientConf.slogo}"
                                                 style="width: 100%;height: 100%"/>
                                        </c:if>
                                        <c:if test="${empty merchantClientConf.slogo}">
                                            <img src="${staticSource}/resources/images/37cang.png"
                                                 style="width: 100%;height: 100%"/>
                                        </c:if>
                                    </div>
                                </blockquote>
                                <span class="require">(180*90像素,白色背景，低于200kb的jpg或png图片)</span>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"></label>
                            <div class="layui-inline">
                                <button type="button" class="layui-btn" id="loginLogoBut">客户端Logo上传</button>
                                <blockquote class="layui-elem-quote layui-quote-nm layui-quote-nm-1">
                                    预览图
                                    <div class="layui-upload-list" id="loginLogo">
                                        <c:if test="${!empty  merchantClientConf.sloginLogo}">
                                            <img src="${dynamicSource}${merchantClientConf.sloginLogo}"
                                                 style="width: 100%;height: 100%"/>
                                        </c:if>
                                        <c:if test="${empty  merchantClientConf.sloginLogo}">
                                            <img src="${staticSource}/resources/images/37cang.png"
                                                 style="width: 100%;height: 100%"/>
                                        </c:if>
                                    </div>
                                </blockquote>
                                <span class="require">(180*90像素,白色背景，低于200kb的jpg或png图片)</span>
                            </div>
                        </div>
                    </div>

                    <div 　 style="display:none;" name="typediv" id="typediv">
                        <div class="wfsplitt">
                            基础详细信息
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
                        <div class="layui-col-md6" id="clearSelect">
                            <label class="layui-form-label"> 资质附件类型</label>
                            <div class="layui-input-inline">
                                <cang:select type="dic" groupNo="SP000105" entire="true" value=""
                                             exp="datatype=\"*\" nullmsg=\"请选择资质附件类型\"" layFilter="code"
                                             name="scode" id="merchantConfCode"/>
                            </div>
                        </div>
                        <div class="layui-col-md6" id="clearSort">
                            <label class="layui-form-label">显示顺序</label>
                            <div class="layui-input-inline">
                                <input type="text" name="isort" id="isort" datatype="*" nullmsg="请输入显示顺序"
                                       value=""
                                       class="layui-input"/>
                            </div>
                        </div>
                    </div>


                    <div class="layui-form-item">

                        <div class="layui-col-md6" id="upImg">
                            <label class="layui-form-label"></label>
                            <div class="layui-inline">
                                <button type="button" class="layui-btn" id="attachUpBut">资质附件上传</button>
                                <blockquote class="layui-elem-quote layui-quote-nm layui-quote-nm-1">
                                    预览图
                                    <div class="layui-upload-list" id="attachImg">
                                        <img src="${staticSource}/resources/images/37cang.png"
                                             style="width: 100%;height: 100%"/>
                                    </div>
                                </blockquote>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">备注</label>
                        <div class="layui-input-block">
                    <textarea id="sremark" name="sremark" class="layui-textarea layui-form-textarea80p"
                              placeholder="备注（非必填项）"></textarea>
                        </div>
                    </div>
                    <div class="layui-form-item text-center m-t">
                        <div class="layui-input-block">
                            <button class="layui-btn layui-btn-primary" id="cancelBtnImg">清空</button>
                            <button class="layui-btn" id="myImgFormSub">保存</button>
                        </div>
                        <input type="hidden" id="smerchantId" name="smerchantId" value="${merchantInfo.id}"/>
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
                            <label class="layui-form-label">单笔金额短信通知</label>
                            <div class="layui-input-inline">
                                <input type="text" name="WCfpayLimitMoney" value="${wCmerchantConf.fpayLimitMoney}"
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
                            <label class="layui-form-label">MD5秘钥</label>
                            <div class="layui-input-inline">
                                <input type="text" name="WCsappSecret" value="${wCmerchantConf.sappSecret}"

                                       class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label">代扣模板ID</label>
                            <div class="layui-input-inline">
                                <input type="text" name="WCsplanId" value="${wCmerchantConf.splanId}"
                                       class="layui-input"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label">微信商户号</label>
                            <div class="layui-input-inline">
                                <input type="text" name="WCspId" value="${wCmerchantConf.spid}"
                                       class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label">微信签名KEY</label>
                            <div class="layui-input-inline">
                                <input type="text" name="spayWechatKey" value="${wCmerchantConf.spayWechatKey}"
                                       class="layui-input"/>
                            </div>
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label">证书地址</label>
                            <div class="layui-input-inline">
                                <input type="text" name="swechatCertUrl" value="${wCmerchantConf.swechatCertUrl}"
                                       class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label">证书密码</label>
                            <div class="layui-input-inline">
                                <input type="text" name="swechatCertPwd" value="${wCmerchantConf.swechatCertPwd}"
                                       class="layui-input"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label">微信账号</label>
                            <div class="layui-input-inline">
                                <input type="text" name="swechatAccount" value="${wCmerchantConf.swechatAccount}"
                                       class="layui-input"/>
                            </div>
                        </div>
                    </div>

                    <div class="layui-form-item mt13">
                        <label class="layui-form-label">微信支付回调地址</label>
                        <div class="layui-input-block">
                                  <textarea id="WCspayCallBackUrl" name="WCspayCallBackUrl"
                                            class="layui-textarea layui-form-textarea80p"
                                            placeholder="请输入微信支付回调地址">${wCmerchantConf.spayCallBackUrl}</textarea>
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
                            <label class="layui-form-label">支付宝账号</label>
                            <div class="layui-input-inline">
                                <input type="text" name="salipayAccount" value="${aPmerchantConf.salipayAccount}"
                                       class="layui-input"/>
                            </div>
                        </div>
                        <%--      <div class="layui-col-md6">
                                  <label class="layui-form-label">支付宝第三方授权TOKEN</label>
                                  <div class="layui-input-inline">
                                      <input type="text" name="sauthToken" value="${aPmerchantConf.sauthToken}"
                                             class="layui-input"/>
                                  </div>
                              </div>--%>
                    </div>
                    <div class="layui-form-item  ">
                        <label class="layui-form-label">支付宝第三方授权TOKEN</label>
                        <div class="layui-input-block">
                    <textarea id="sauthToken" name="sauthToken" class="layui-textarea layui-form-textarea80p"
                              placeholder="请输入支付宝第三方授权TOKEN">${aPmerchantConf.sauthToken}</textarea>
                        </div>
                    </div>
                    <div class="layui-form-item  mt13">
                        <label class="layui-form-label">支付宝公钥</label>
                        <div class="layui-input-block">
                    <textarea id="spublicKey" name="spublicKey" class="layui-textarea layui-form-textarea80p"
                              placeholder="请输入支付宝公钥">${aPmerchantConf.spublicKey}</textarea>
                        </div>
                    </div>
                    <div class="layui-form-item  mt13">
                        <label class="layui-form-label">支付宝应用私钥</label>
                        <div class="layui-input-block">
                    <textarea id="sprivateKey" name="sprivateKey" class="layui-textarea layui-form-textarea80p"
                              placeholder="请输入支付宝应用私钥">${aPmerchantConf.sprivateKey}</textarea>
                        </div>
                    </div>
                    <div class="layui-form-item mt13">
                        <label class="layui-form-label">支付宝应用公钥</label>
                        <div class="layui-input-block">
                    <textarea id="sappPublicKey" name="sappPublicKey" class="layui-textarea layui-form-textarea80p"
                              placeholder="请输入支付宝应用公钥">${aPmerchantConf.sappPublicKey}</textarea>
                        </div>
                    </div>
                    <div class="layui-form-item mt13">
                        <label class="layui-form-label">支付宝支付回调地址</label>
                        <div class="layui-input-block">
                    <textarea id="spayCallBackUrl" name="spayCallBackUrl" class="layui-textarea layui-form-textarea80p"
                              placeholder="请输入支付宝支付回调地址">${aPmerchantConf.spayCallBackUrl}</textarea>
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
            min: 0, //n天前
            elem: '#dexpireDate',//指定元素
            type: 'date',
            done: function (value) { //监听日期被切换
                if (!isEmpty(value)) {
                    $("#dexpireDate").removeClass("Validform_error");
                    $("#dexpireDate").parent().find("span").hide();
                } else {
                    $("#dexpireDate").addClass("Validform_error");
                    $("#dexpireDate").parent().find("span").html($("#dexpireDate").attr("nullmsg"));
                    $("#dexpireDate").parent().find("span").removeClass("Validform_right");
                    $("#dexpireDate").parent().find("span").addClass("Validform_wrong");
                    $("#dexpireDate").parent().find("span").show();

                }
            }
        });
        laydate.render({
            min: 0, //n天前
            elem: '#dsignDate',
            type: 'date',
            done: function (value) { //监听日期被切换
                if (!isEmpty(value)) {
                    $("#dsignDate").removeClass("Validform_error");
                    $("#dsignDate").parent().find("span").hide();
                } else {
                    $("#dsignDate").addClass("Validform_error");
                    $("#dsignDate").parent().find("span").html($("#dsignDate").attr("nullmsg"));
                    $("#dsignDate").parent().find("span").removeClass("Validform_right");
                    $("#dsignDate").parent().find("span").addClass("Validform_wrong");
                    $("#dsignDate").parent().find("span").show();
                }
            }
        });
        laydate.render({
             //n天前
            elem: '#dsignDate2',
            type: 'date',
            done: function (value) { //监听日期被切换
                if (!isEmpty(value)) {
                    $("#dsignDate2").removeClass("Validform_error");
                    $("#dsignDate2").parent().find("span").hide();
                } else {
                    $("#dsignDate2").addClass("Validform_error");
                    $("#dsignDate2").parent().find("span").html($("#dsignDate2").attr("nullmsg"));
                    $("#dsignDate2").parent().find("span").removeClass("Validform_right");
                    $("#dsignDate2").parent().find("span").addClass("Validform_wrong");
                    $("#dsignDate2").parent().find("span").show();
                }
            }
        });
        var $ = layui.jquery
            , upload = layui.upload;
        $("#cancelBtnImg").click(function () {

            $(upImg).children("span").eq(0).text(" ");
            AttachImg.config.elem.next()[0].value = '';
            $("#sremark").val('');
            $("#attachImg").find("img").attr("src", "${staticSource}/resources/images/37cang.png");
            /*   setResetFormValues();*/

            $("#clearSort :input").each(function () {
                $(this).val("");
            });
            /* 商户资质证书上传select框选择清空后 有问题*/
            /*  $("#clearSelect :input").each(function () {
             $(this).val("");
             });*/
            document.getElementById("merchantConfCode").value = "";
            /* document.getElementById("merchantConfCode").options.length = 0;*/

            /*    $(".layui-form select").each(function(){
             this.value = "";
             });*/
            //无法清除
            form.render('select');
            return false;
        });
        //资质证书图片上传
        var AttachImg = upload.render({
            auto: false
            , elem: '#attachUpBut'
            , multiple: false
            , field: "attachFile"
            , size: 20000
            , accept: 'images'
            , exts: 'jpg|png'
            , choose: function (obj) {
                obj.preview(function (index, file, result) {
                    $("#attachImg").find("img").attr("src", result);
                });
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
            , choose: function (obj) {
                obj.preview(function (index, file, result) {
                    $("#slogo").find("img").attr("src", result);
                });
            }
        });
        upload.render({
            auto: false
            , elem: '#loginLogoBut'
            , multiple: false
            , field: "loginLogofile"
            , size: 200
            , accept: 'images'
            , exts: 'jpg|png'
            , choose: function (obj) {
                obj.preview(function (index, file, result) {
                    $("#loginLogo").find("img").attr("src", result);
                });
            }
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
                    alertDel("请选择对接业务员");
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
                                        /*  cancel: function () {
                                         parent.closeLayerAndRefresh(index);
                                         }*/
                                    'index': index
                                },
                                function () {
                                    parent.closeLayerAndRefresh(index);
                                }
                            )
                            ;
                        } else {
                            alertDel(msg.msg);
                        }
                    }
                });
                return false;
            }
        });
        form.on('select(vmSkuType)', function (data) {
            if (!isEmpty(data.value)) {
                $("#ivmSkuType").parent().find("span").hide();
            } else {
                $("#ivmSkuType").parent().find("span").show();
                if (!$("#ivmSkuType").parent().find("span").hasClass("Validform_wrong")) {
                    $("#ivmSkuType").parent().find("span").html($("#ivmSkuType").attr("nullmsg"));
                    $("#ivmSkuType").parent().find("span").removeClass("Validform_right");
                    $("#ivmSkuType").parent().find("span").addClass("Validform_wrong");
                }
            }
        });
        form.on('select(cooperationMode)', function (data) {
            if (!isEmpty(data.value)) {
                $("#icooperationMode").parent().find("span").hide();
            } else {
                $("#icooperationMode").parent().find("span").show();
                if (!$("#icooperationMode").parent().find("span").hasClass("Validform_wrong")) {
                    $("#icooperationMode").parent().find("span").html($("#icooperationMode").attr("nullmsg"));
                    $("#icooperationMode").parent().find("span").removeClass("Validform_right");
                    $("#icooperationMode").parent().find("span").addClass("Validform_wrong");
                }
            }
        });
        form.on('select(companyType)', function (data) {
            if (!isEmpty(data.value)) {
                $("#icompanyType").parent().find("span").hide();
            } else {
                $("#icompanyType").parent().find("span").show();
                if (!$("#icompanyType").parent().find("span").hasClass("Validform_wrong")) {
                    $("#icompanyType").parent().find("span").html($("#icompanyType").attr("nullmsg"));
                    $("#icompanyType").parent().find("span").removeClass("Validform_right");
                    $("#icompanyType").parent().find("span").addClass("Validform_wrong");
                }
            }
        });
        form.on('select(supportPayWay)', function (data) {
            if (!isEmpty(data.value)) {
                $("#isupportPayWay").parent().find("span").hide();
            } else {
                $("#isupportPayWay").parent().find("span").show();
                if (!$("#isupportPayWay").parent().find("span").hasClass("Validform_wrong")) {
                    $("#isupportPayWay").parent().find("span").html($("#isupportPayWay").attr("nullmsg"));
                    $("#isupportPayWay").parent().find("span").removeClass("Validform_right");
                    $("#isupportPayWay").parent().find("span").addClass("Validform_wrong");
                }
            }
        });
        form.on('select(withholdingWay)', function (data) {
            if (!isEmpty(data.value)) {
                $("#iwithholdingWay").parent().find("span").hide();
            } else {
                $("#iwithholdingWay").parent().find("span").show();
                if (!$("#iwithholdingWay").parent().find("span").hasClass("Validform_wrong")) {
                    $("#iwithholdingWay").parent().find("span").html($("#iwithholdingWay").attr("nullmsg"));
                    $("#iwithholdingWay").parent().find("span").removeClass("Validform_right");
                    $("#iwithholdingWay").parent().find("span").addClass("Validform_wrong");
                }
            }
        });
        form.on('select(dbWap)', function (data) {
            if (!isEmpty(data.value)) {
                $("#idbWap").parent().find("span").hide();
            } else {
                $("#idbWap").parent().find("span").show();
                if (!$("#idbWap").parent().find("span").hasClass("Validform_wrong")) {
                    $("#idbWap").parent().find("span").html($("#idbWap").attr("nullmsg"));
                    $("#idbWap").parent().find("span").removeClass("Validform_right");
                    $("#idbWap").parent().find("span").addClass("Validform_wrong");
                }
            }
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
            if (!isEmpty(data.value)) {
                $("#itype").parent().find("span").hide();
            } else {
                $("#itype").parent().find("span").show();
                if (!$("#itype").parent().find("span").hasClass("Validform_wrong")) {
                    $("#itype").parent().find("span").html($("#itype").attr("nullmsg"));
                    $("#itype").parent().find("span").removeClass("Validform_right");
                    $("#itype").parent().find("span").addClass("Validform_wrong");
                }
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

                            $("#clearSort :input").each(function () {
                                $(this).val("");
                            });

                            document.getElementById("merchantConfCode").value = "";
                            //无法清除
                            form.render('select');

                            /* $(upImg).children("span").eq(0).text(" ");
                             AttachImg.config.elem.next()[0].value = '';
                             $("#sremark").val('');
                             $("#clearSort :input").each(function () {
                             $(this).val("");
                             });
                             document.getElementById("merchantConfCode").value = "";
                             //无法清除
                             form.render('select');*/
                            alertMsgAndReload("操作成功", "");


                        } else {
                            alertDel(msg.msg);
                        }
                    }
                });
                return false;

            }
        });
        form.on('select(code)', function (data) {
            if (!isEmpty(data.value)) {
                $("#merchantConfCode").parent().find("span").hide();
            } else {
                $("#merchantConfCode").parent().find("span").show();
                if (!$("#merchantConfCode").parent().find("span").hasClass("Validform_wrong")) {
                    $("#merchantConfCode").parent().find("span").html($("#merchantConfCode").attr("nullmsg"));
                    $("#merchantConfCode").parent().find("span").removeClass("Validform_right");
                    $("#merchantConfCode").parent().find("span").addClass("Validform_wrong");
                }
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
                                /*  cancel: function () {
                                 parent.closeLayerAndRefresh(index);
                                 }*/
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

        //BD添加
        var $ = layui.$, active = {
            //选择Bd
            sel: function () {
                showLayerMedium("选择对接业务员", ctx + "/operator/selectBdList");
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
            colModel: [
                {name: 'id', index: 'id', hidden: true},
                {
                    name: 'scode', index: 'scode', label: "资质附件类型", width: 150, sortable: false, formatter: "select",
                    editoptions: {value: '10:营业执照;20:扫描件;30:一般纳税人证明;40:法人身份证复印件;50:食品流通许可证'}
                },
                {
                    name: 'sattachPath',
                    index: 'SATTACH_PATH',
                    label: "资质附件图片",
                    sortable: false,
                    formatter: function (value) {
                        return "<a href='javascript:void(0);' data-container='body' data-toggle='popover' data-placement='right' data-content='<img style=\"width: auto; height:auto; max-width:240px; max-height:240px;\" src=\"${dynamicSource}" + value + "\"/>'>查看</a>";
                    }
                },
                {name: 'isort', index: 'isort', label: "排序号", width: 150, sortable: false},
                {name: 'sremark', index: 'sremark', label: "说明", width: 150, sortable: false},
                {name: 'process', index: 'process', label: "操作", sortable: false, width: 150}
            ],
            rownumbers: true,
            viewrecords: true,
            ondblClickRow: function (rowId) {//双击编辑
                showLayerMin("编辑", ctx + '/merchantAttach/editDetail?sid=' + rowId);
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
        $(document.body).popover({
            selector: '[data-toggle="popover"]',
            html: true,
            trigger: 'hover',
        });
        $(window).bind('resize', function () {
            var width = $('.jqGrid_wrapper').width();
            $('#gridTable').setGridWidth(width);
        });
    });
    function editMethod(sid) {
        showLayerMin("编辑", ctx + '/merchantAttach/editDetail?sid=' + sid);
    }
    function delMethod(sid) {
        confirmDelTip("确定要删除图片?", ctx + "/merchantAttach/deleteDetail", {checkboxId: sid});
    }
    //查看图片
    /*    function viewImg(sid) {
     showLayer("资质证书", ctx + '/merchantAttach/viewImg?sid=' + sid, {area: ['60%', '40%']});
     }*/
</script>
</body>

