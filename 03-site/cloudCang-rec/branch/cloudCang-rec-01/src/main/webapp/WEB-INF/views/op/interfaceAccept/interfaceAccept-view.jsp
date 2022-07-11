<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>平台接口业务受理信息详情</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <%--  <div class="layui-form-item wflayui-form-item">
          <table cellspacing="0" border="0">
              <tr>
                  <td class="text-left" style="width:18%">请求数据：</td>
                  <td class="text-right" style="width:32%">${interfaceAccept.srequestData}</td>
                  <td class="text-left" style="width:18%">回调数据：</td>
                  <td class="text-right" style="width:32%">${appManage.scallbackData}</td>
              </tr>
          </table>--%>
    <div class="layui-form-item ">
        <label class="layui-form-label">请求数据</label>
        <div class="layui-input-block">
                    <textarea id="srequestData" name="srequestData" class="layui-textarea layui-form-textarea80p"
                              disabled
                    >${interfaceAccept.srequestData}</textarea>
        </div>
    </div>
    <div class="layui-form-item mt13">
        <label class="layui-form-label">回调数据</label>
        <div class="layui-input-block">
                    <textarea id="scallbackData" name="scallbackData" class="layui-textarea layui-form-textarea80p"
                              disabled
                    >${interfaceAccept.scallbackData}</textarea>
        </div>
    </div>
    <div class="layui-form-item mt13">
        <label class="layui-form-label">返回数据</label>
        <div class="layui-input-block">
                    <textarea id="sresponseData" name="sresponseData" class="layui-textarea layui-form-textarea80p"
                              disabled
                    >${interfaceAccept.sresponseData}</textarea>
        </div>
    </div>
    <div class="layui-form-item mt13">
        <label class="layui-form-label">回调地址</label>
        <div class="layui-input-block">
                    <textarea id="scallbackAddress" name="scallbackAddress" disabled
                              class="layui-textarea layui-form-textarea80p"
                    >${interfaceAccept.scallbackAddress}</textarea>
        </div>
    </div>
    <%-- </div>--%>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    $(".wflayui-form-item tr:even").addClass("odd");
</script>
</body>
</html>

