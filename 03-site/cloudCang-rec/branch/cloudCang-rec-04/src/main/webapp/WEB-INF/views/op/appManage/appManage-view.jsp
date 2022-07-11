<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>应用详情</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <%--  <div class="layui-form-item wflayui-form-item">
          <table cellspacing="0" border="0">
              <tr>
                  <td class="text-left" style="width:18%">商户编号：</td>
                  <td class="text-right" style="width:32%">${appManage.splatformKey}</td>
                  <td class="text-left" style="width:18%">商户名称：</td>
                  <td class="text-right" style="width:32%">${appManage.splatformPublicKey}</td>
              </tr>
          </table>
      </div>--%>
    <%--<div class="layui-form-item ">
        <label class="layui-form-label">应用ID</label>
        <div class="layui-input-inline">
            <input type="text" name="sappId" id="sappId" value="${appManage.sappId}"
                   class="layui-input"/>
        </div>
    </div>
    <div class="layui-form-item ">
        <label class="layui-form-label">应用秘钥</label>
        <div class="layui-input-block">
                    <textarea id="sappSecretKey" name="sappSecretKey" class="layui-textarea layui-form-textarea80p"
                              disabled
                    >${appManage.sappSecretKey}</textarea>
        </div>
    </div>--%>
    <div class="layui-form-item mt13">
        <label class="layui-form-label">平台回调地址</label>
        <div class="layui-input-block">
                    <textarea id="snoticeAddress" name="snoticeAddress" class="layui-textarea layui-form-textarea80p"
                              disabled
                    >${appManage.snoticeAddress}</textarea>
        </div>
    </div>
    <div class="layui-form-item mt13">
        <label class="layui-form-label">平台秘钥</label>
        <div class="layui-input-block">
                    <textarea id="splatformKey" name="splatformKey" class="layui-textarea layui-form-textarea80p"
                              disabled
                    >${appManage.splatformKey}</textarea>
        </div>
    </div>
    <div class="layui-form-item mt13">
        <label class="layui-form-label">平台公钥</label>
        <div class="layui-input-block">
                    <textarea id="splatformPublicKey" name="splatformPublicKey" disabled
                              class="layui-textarea layui-form-textarea80p"
                    >${appManage.splatformPublicKey}</textarea>
        </div>
    </div>
    <div class="layui-form-item mt13">
        <label class="layui-form-label">应用私钥</label>
        <div class="layui-input-block">
                    <textarea id="sappPrivateKey" name="sappPrivateKey" class="layui-textarea layui-form-textarea80p"
                              disabled
                    >${appManage.sappPrivateKey}</textarea>
        </div>
    </div>
    <div class="layui-form-item mt13">
        <label class="layui-form-label">应用公钥</label>
        <div class="layui-input-block">
                    <textarea id="sappPublicKey" name="sappPublicKey" class="layui-textarea layui-form-textarea80p"
                              disabled
                    >${appManage.sappPublicKey}</textarea>
        </div>
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

