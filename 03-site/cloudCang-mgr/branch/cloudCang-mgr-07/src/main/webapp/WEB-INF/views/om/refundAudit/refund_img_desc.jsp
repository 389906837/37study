<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>退款图片说明</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
</head>
<body>
<div class="ibox-content">
    <form class="layui-form" id="myForm">

        <%--   <c:choose>
               <c:when test="${fn:length(refundImgDescList) > 0}">--%>
        <%--  <div class="layui-form-item">
              <label class="layui-form-label"></label>
              <div class="layui-input-inline">
                  <div class="layui-col-md6">
                      <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 5px;">
                          <div class="layui-upload-list" id="demo2">
                              <c:forEach items="${refundImgDescList}" var="item" varStatus="L">
                                  <p style="text-align: left;">
                                      <img src="${dynamicSource}${item.simgPath}"
                                           style='width: 450px; height: 450px;'>
                                  </p>
                              </c:forEach>
                          </div>
                      </blockquote>
                  </div>
              </div>
          </div>--%>

        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"></label>
                <div class="layui-input-inline">
                    <%-- <blockquote class="layui-elem-quote layui-quote-nm &lt;%&ndash;layui-quote-nm-1&ndash;%&gt;"
                                 style="margin-top: 5px;">--%>
                    <c:choose>
                    <c:when test="${fn:length(refundImgDescList) > 0}">
                    <div class="layui-upload-list" id="slogo">
                        <c:forEach items="${refundImgDescList}" var="item" varStatus="L">

                            <img src="${dynamicSource}${item.simgPath}"
                                 style="width: 100%;height: 100%"/>
                            <%--   style='width: 200px; height: 200px;'>--%>

                        </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <img src="${staticSource}/resources/images/37cang.png"
                                 style="width: 100%;height: 100%"/>
                        </c:otherwise>
                        </c:choose>
                    </div>
                    </blockquote>
                </div>
            </div>
        </div>
        <%--   </c:when>
           <c:otherwise>--%>
        <%--<div class="layui-form-item m-t-xl">
            <label class="layui-form-label"></label>
            <div class="layui-input-inline">
                <div class="layui-col-md6">
                    <img src="${staticSource}/resources/images/37cang.png">
                </div>
            </div>
        </div>--%>
        <%--  </c:otherwise>
          </c:choose>--%>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn">关闭</button>
            </div>
        </div>
    </form>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    $(function () {
        $("#cancelBtn").click(function () {
            parent.layer.close(index);
            return false;
        });
    });
</script>
</body>
</html>

