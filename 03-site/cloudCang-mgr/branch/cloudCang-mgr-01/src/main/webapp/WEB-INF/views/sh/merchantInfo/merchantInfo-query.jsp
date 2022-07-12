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
            <li>微信、支付宝配置</li>
        </ul>
        <div class="layui-tab-content" style="height: 100px;">
            <div class="layui-tab-item layui-show" name="merchantInfoDiv" id="merchantInfoDiv">
                <div class="layui-form-item wflayui-form-item">
                    <div class="wfsplitt">
                        商户信息
                    </div>
                    <table cellspacing="0" border="0">
                        <tr>
                            <td class="text-left" style="width:18%">商户编号：</td>
                            <td class="text-right" style="width:32%">${merchantInfo.scode}</td>
                            <td class="text-left" style="width:18%">商户名称：</td>
                            <td class="text-right" style="width:32%">${merchantInfo.sname}</td>
                        </tr>
                        <tr>
                            <td class="text-left" style="width:18%">商户类型：</td>
                            <td class="text-right" style="width:32%">
                                <c:if test="${merchantInfo.itype == 10 }">个人</c:if>
                                <c:if test="${merchantInfo.itype == 20 }">企业</c:if>
                            </td>
                            <td class="text-left">公司类型：</td>
                            <td class="text-right">
                                <c:if test="${merchantInfo.itype == 10 }">国企</c:if>
                                <c:if test="${merchantInfo.itype == 20 }">民营</c:if>
                            </td>
                        </tr>
                        <tr>
                            <td class="text-left">商户状态：</td>
                            <td class="text-right">
                                <c:if test="${merchantInfo.istatus == 10 }">洽谈中</c:if>
                                <c:if test="${merchantInfo.istatus == 20 }">已签约</c:if>
                                <c:if test="${merchantInfo.istatus == 30 }">已解约</c:if>
                                <c:if test="${merchantInfo.istatus == 40 }">合约到期</c:if>
                            </td>
                            <td class="text-left">SKU使用范围：</td>
                            <td class="text-right">
                                <c:if test="${merchantInfo.ivmSkuType == 1 }">全部</c:if>
                                <c:if test="${merchantInfo.ivmSkuType == 2 }">部分</c:if>
                            </td>
                        </tr>
                        <tr>
                            <td class="text-left">签约日期：</td>
                            <td class="text-right"><fmt:formatDate value="${merchantInfo.dsignDate}"
                                                                   pattern="yyyy-MM-dd"/></td>
                            <td class="text-left">合约到期日：</td>
                            <td class="text-right"><fmt:formatDate value="${merchantInfo.dexpireDate}"
                                                                   pattern="yyyy-MM-dd"/></td>
                        </tr>
                        <tr>
                            <td class="text-left">联系人：</td>
                            <td class="text-right">
                                ${merchantInfo.scontactName}
                            </td>
                            <td class="text-left">联系人电话：</td>
                            <td class="text-right">
                                ${merchantInfo.scontactMobile}
                            </td>
                        </tr>
                        <tr>
                            <td class="text-left">联系人邮箱：</td>
                            <td class="text-right">
                                ${merchantInfo.scontactEmail}
                            </td>
                            <td class="text-left">联系人地址：</td>
                            <td class="text-right">
                                ${merchantInfo.scontactAddress}
                            </td>
                        </tr>
                        <tr>
                            <td class="text-left">合作模式：</td>
                            <td class="text-right">
                                <c:if test="${merchantInfo.icooperationMode == 10 }">采购</c:if>
                                <c:if test="${merchantInfo.icooperationMode == 20 }">租用</c:if>
                                <c:if test="${merchantInfo.icooperationMode == 30 }">自主</c:if>
                            </td>
                            <td class="text-left">二级分销开关：</td>
                            <td class="text-right">
                                <c:if test="${merchantInfo.idistributionSwitch == 0 }">关闭</c:if>
                                <c:if test="${merchantInfo.idistributionSwitch == 1 }">开启</c:if>
                            </td>
                        </tr>
                    </table>
                    <div class="wfsplitt">
                        客户端配置
                    </div>
                    <table cellspacing="0" border="0">
                        <tr>
                            <td class="text-left">支持支付方式：</td>
                            <td class="text-right">
                                <c:if test="${merchantClientConf.isupportPayWay == 10 }">全部</c:if>
                                <c:if test="${merchantClientConf.isupportPayWay == 20 }">仅代扣</c:if>
                                <c:if test="${merchantClientConf.isupportPayWay == 30 }">仅手动</c:if>
                            </td>
                            <td class="text-left">退款是否需要审核：</td>
                            <td class="text-right">
                                <c:if test="${merchantClientConf.iisRefundAudit == 0 }">否</c:if>
                                <c:if test="${merchantClientConf.iisRefundAudit == 1 }">是</c:if>
                            </td>
                        </tr>
                        <tr>
                            <td class="text-left">客服电话：</td>
                            <td class="text-right">
                                ${merchantClientConf.scontactMobile}
                            </td>
                            <td class="text-left">商户简称：</td>
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
                            <td class="text-left" style="width:18%">登录logo：</td>
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
                        详细信息
                    </div>
                    <table cellspacing="0" border="0">
                        <tr>
                            <td class="text-left" style="width:18%">公司对公税号：</td>
                            <td class="text-right" style="width:32%">  ${merchantDetail.staxId}</td>
                            <td class="text-left" style="width:18%">公司注册地址：</td>
                            <td class="text-right" style="width:32%">  ${merchantDetail.sregisterAddress}</td>
                        </tr>
                        <tr>
                            <td class="text-left">公司电话：</td>
                            <td class="text-right">
                                    ${merchantDetail.sphone}
                            </td>
                            <td class="text-left">公司传真：</td>
                            <td class="text-right">
                                    ${merchantDetail.sfax}
                            </td>
                        </tr>
                        <tr>
                            <td class="text-left">公司网址：</td>
                            <td class="text-right">
                                    ${merchantDetail.swebsiteUrl}
                            </td>
                            <td class="text-left">公司法人：</td>
                            <td class="text-right">
                                    ${merchantDetail.slegalPerson}
                            </td>
                        </tr>
                        <tr>
                            <td class="text-left">公司法人身份证：</td>
                            <td class="text-right">
                                    ${merchantDetail.sidCard}
                            </td>
                            <td class="text-left">公司对公账号：</td>
                            <td class="text-right">
                                    ${merchantDetail.saccountNumber}
                            </td>
                        </tr>
                        <tr>
                            <td class="text-left">公司对公账号名称：</td>
                            <td class="text-right">
                                    ${merchantDetail.saccountName}
                            </td>
                            <td class="text-left">公司对公账号开户行：</td>
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
                        微信配置
                    </div>
                    <table cellspacing="0" border="0">
                        <tr>
                            <td class="text-left" style="width:18%">授权回调URL：</td>
                            <td class="text-right" style="width:32%">${wCmerchantConf.scallBackUrl}</td>
                            <td class="text-left">APPID：</td>
                            <td class="text-right">${wCmerchantConf.sappId}</td>
                        </tr>
                        <tr>
                            <td class="text-left" style="width:18%">单笔金额短信通知：</td>
                            <td class="text-right" style="width:32%">
                                ${wCmerchantConf.fpayLimitMoney}
                            </td>
                            <td class="text-left">单笔支付限额：</td>
                            <td class="text-right">${wCmerchantConf.fpayLimitSingle}</td>
                        </tr>
                        <tr>
                            <td class="text-left" style="width:18%">单日支付限额：</td>
                            <td class="text-right" style="width:32%">
                                ${wCmerchantConf.fpayLimitDay}
                            </td>
                            <td class="text-left">单月支付限额：</td>
                            <td class="text-right">${wCmerchantConf.fpayLimitMonth}</td>
                        </tr>
                        <tr>
                            <td class="text-left" style="width:18%">MD5秘钥：</td>
                            <td class="text-right" style="width:32%">
                                ${wCmerchantConf.sappSecret}
                            </td>
                            <td class="text-left" style="width:18%">代扣模板ID：</td>
                            <td class="text-right" style="width:32%">
                                ${wCmerchantConf.splanId}
                            </td>
                        </tr>
                        <tr>
                            <td class="text-left" style="width:18%">微信商户号：</td>
                            <td class="text-right" style="width:32%">
                                ${wCmerchantConf.spid}
                            </td>
                            <td class="text-left" style="width:18%">微信签名KEY：</td>
                            <td class="text-right" style="width:32%">
                                ${wCmerchantConf.spayWechatKey}
                            </td>
                        </tr>
                        <tr>
                            <td class="text-left">证书地址：</td>
                            <td class="text-right">
                                ${wCmerchantConf.swechatCertUrl}
                            </td>
                            <td class="text-left">证书密码：</td>
                            <td class="text-right">
                                ${wCmerchantConf.swechatCertPwd}
                            </td>
                        </tr>
                        <tr>
                            <td class="text-left">微信账号：</td>
                            <td class="text-right">
                                ${wCmerchantConf.swechatAccount}
                            </td>
                            <td class="text-left" style="width:18%">微信支付回调地址：</td>
                            <td class="text-right" style="width:32%">${wCmerchantConf.spayCallBackUrl}</td>
                        </tr>
                    </table>
                    <div class="wfsplitt">
                        支付宝配置
                    </div>
                    <table cellspacing="0" border="0">
                        <tr>
                            <td class="text-left" style="width:18%">授权回调URL：</td>
                            <td class="text-right" style="width:32%">${aPmerchantConf.scallBackUrl}</td>
                            <td class="text-left">APPID：</td>
                            <td class="text-right">${aPmerchantConf.sappId}</td>
                        </tr>
                        <tr>
                            <td class="text-left" style="width:18%">MD5秘钥：</td>
                            <td class="text-right" style="width:32%">
                                ${aPmerchantConf.sappSecret}
                            </td>
                            <td class="text-left">单笔支付限额：</td>
                            <td class="text-right">${aPmerchantConf.fpayLimitSingle}</td>
                        </tr>
                        <tr>
                            <td class="text-left" style="width:18%">单日支付限额：</td>
                            <td class="text-right" style="width:32%">
                                ${aPmerchantConf.fpayLimitDay}
                            </td>
                            <td class="text-left" style="width:18%">单月支付限额：</td>
                            <td class="text-right" style="width:32%">
                                ${aPmerchantConf.fpayLimitMonth}
                            </td>
                        </tr>
                        <tr>
                            <td class="text-left">单笔金额短信通知：</td>
                            <td class="text-right">${aPmerchantConf.fpayLimitMoney}</td>

                            <td class="text-left" style="width:18%">支付宝合作伙伴ID：</td>
                            <td class="text-right" style="width:32%">
                                ${aPmerchantConf.spid}
                            </td>
                        </tr>
                        <tr>
                            <td class="text-left" style="width:18%">支付宝账号：</td>
                            <td class="text-right" style="width:32%">
                                ${aPmerchantConf.salipayAccount}
                            </td>
                            <td class="text-left" style="width:18%">支付宝支付回调地址：</td>
                            <td class="text-right" style="width:32%">
                                ${aPmerchantConf.spayCallBackUrl}
                            </td>
                        </tr>
                        <tr>
                         <%--    <td class="text-left" style="width:18%">支付宝公钥：</td>
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
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js"></script>
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

