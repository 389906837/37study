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
            基本信息
            </div>
            <tr>
                <td class="text-left" style="width:18%">会员编号：</td>
                <td class="text-right" style="width:32%">${memberInfo.scode}</td>
                <td class="text-left" style="width:18%">会员用户名：</td>
                <td class="text-right" style="width:32%">${memberInfo.smemberName}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">会员性别：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${memberInfo.isex == 1 }">男</c:if>
                    <c:if test="${memberInfo.isex == 2 }">女</c:if>
                </td>
                <td class="text-left"  style="width:18%">会员生日：</td>
                <td class="text-right"  style="width:32%"><fmt:formatDate value='${memberInfo.dbirthdate}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">手机号码：</td>
                <td class="text-right"  style="width:32%">${memberInfo.smobile}</td>
                <td class="text-left"  style="width:18%">邮箱：</td>
                <td class="text-right"  style="width:32%">${memberInfo.semail}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">昵称：</td>
                <td class="text-right"  style="width:32%">${memberInfo.snickName}</td>
                <td class="text-left"  style="width:18%">真实姓名：</td>
                <td class="text-right"  style="width:32%">${memberInfo.srealName}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">证件类型：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${memberInfo.icardType == 10 }">身份证</c:if>
                    <c:if test="${memberInfo.icardType == 20 }">营业执照</c:if>
                </td>
                <td class="text-left" style="width:18%">证件号码：</td>
                <td class="text-right"  style="width:32%">${memberInfo.sidCard}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">注册时间：</td>
                <td class="text-right"  style="width:32%"><fmt:formatDate value="${memberInfo.tregisterTime}" pattern="yyyy-MM-dd"/></td>
                <td class="text-left" style="width:18%">实名认证时间：</td>
                <td class="text-right"  style="width:32%"><fmt:formatDate value="${memberInfo.trealNameTime}" pattern="yyyy-MM-dd"/></td>
            </tr>
        </table>
    </div>


    <div class="layui-form-item wflayui-form-item">
        <table  cellspacing="0" border="0">
            <div class="wfsplitt" style="margin-top: 20px">
                状态信息
            </div>
            <tr>
                <td class="text-left" style="width:18%">会员状态：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${memberInfo.istatus == 1}">正常</c:if>
                    <c:if test="${memberInfo.istatus == 2}">注销停用</c:if>
                    <c:if test="${memberInfo.istatus == 3}">系统黑名单</c:if>
                    <c:if test="${memberInfo.istatus == 4}">冻结</c:if>
                </td>
                <td class="text-left" style="width:18%">会员类型：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${memberInfo.imemberType == 10 }">购物会员</c:if>
                    <c:if test="${memberInfo.imemberType == 20 }">补货会员</c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">会员等级：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${memberInfo.imemberLevel == 10 }">大众会员</c:if>
                    <c:if test="${memberInfo.imemberLevel == 20 }">黄金会员</c:if>
                    <c:if test="${memberInfo.imemberLevel == 30 }">铂金会员</c:if>
                    <c:if test="${memberInfo.imemberLevel == 40 }">钻石会员</c:if>
                </td>
                <td class="text-left"  style="width:18%">是否已首单：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${memberInfo.iisOrder == 0 }">否</c:if>
                    <c:if test="${memberInfo.iisOrder == 1 }">是</c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">首单平台：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${memberInfo.ifirstOrderPlatform == 10 }">微信</c:if>
                    <c:if test="${memberInfo.ifirstOrderPlatform == 20 }">支付宝</c:if>
                    <c:if test="${memberInfo.ifirstOrderPlatform == 30 }">APP</c:if>
                </td>
                <td class="text-left"  style="width:18%">首单时间：</td>
                <td class="text-right"  style="width:32%"><fmt:formatDate value="${memberInfo.tfirstOrderTime}" pattern="yyyy-MM-dd"/></td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">推荐奖励是否启用：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${memberInfo.iisEnableRecoaward == 0 }">否</c:if>
                    <c:if test="${memberInfo.iisEnableRecoaward == 1 }">是</c:if>
                </td>
                <td class="text-left"  style="width:18%">是否实名认证：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${memberInfo.iisVerified == 0 }">否</c:if>
                    <c:if test="${memberInfo.iisVerified == 1 }">是</c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">是否首次绑卡：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${memberInfo.iisFirstTieCard == 0 }">否</c:if>
                    <c:if test="${memberInfo.iisFirstTieCard == 1 }">是</c:if>
                </td>
                <td class="text-left" style="width:18%">注册设备编号：</td>
                <td class="text-right"  style="width:32%">${memberInfo.sregDeviceCode}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">注册平台：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${memberInfo.imemberLevel == 10 }">微信</c:if>
                    <c:if test="${memberInfo.imemberLevel == 20 }">支付宝</c:if>
                    <c:if test="${memberInfo.imemberLevel == 30 }">微信公众号</c:if>
                    <c:if test="${memberInfo.imemberLevel == 40 }">支付宝生活号</c:if>
                    <c:if test="${memberInfo.imemberLevel == 50 }">APP android</c:if>
                    <c:if test="${memberInfo.imemberLevel == 60 }">APP ios</c:if>
                </td>
                <td class="text-left" style="width:18%">会员注册IP：</td>
                <td class="text-right"  style="width:32%">${memberInfo.smemberRegip}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">最近购物平台：</td>
                <td class="text-right"  style="width:32%">
                <c:if test="${memberInfo.ilastShopPlatform == 10 }">微信</c:if>
                <c:if test="${memberInfo.ilastShopPlatform == 20 }">支付宝</c:if>
                <c:if test="${memberInfo.ilastShopPlatform == 30 }">APP</c:if>
                </td>
                <td class="text-left" style="width:18%">最近购物时间：</td>
                <td class="text-right"  style="width:32%"><fmt:formatDate value="${memberInfo.tlastShopTime}" pattern="yyyy-MM-dd"/></td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">支付宝免密是否开通：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${memberInfo.iaipayOpen == 0 }">否</c:if>
                    <c:if test="${memberInfo.iaipayOpen == 1 }">是</c:if>
                </td>
                <td class="text-left" style="width:18%">微信免密是否开通：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${memberInfo.iwechatOpen == 0 }">否</c:if>
                    <c:if test="${memberInfo.iwechatOpen == 1 }">是</c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">最近购物平台：</td>
                <td class="text-right"  style="width:32%">
                <c:if test="${memberInfo.ilastShopPlatform == 10 }">微信</c:if>
                <c:if test="${memberInfo.ilastShopPlatform == 20 }">支付宝</c:if>
                <c:if test="${memberInfo.ilastShopPlatform == 30 }">APP</c:if>
                </td>
                <td class="text-left" style="width:18%">最近购物时间：</td>
                <td class="text-right"  style="width:32%"><fmt:formatDate value="${memberInfo.tlastShopTime}" pattern="yyyy-MM-dd"/></td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">是否补货员：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${memberInfo.iisReplenishment == 0 }">否</c:if>
                    <c:if test="${memberInfo.iisReplenishment == 1 }">是</c:if>
                </td>
                <td class="text-left" style="width:18%">&nbsp;</td>
                <td class="text-right" style="width:32%">&nbsp;</td>
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

