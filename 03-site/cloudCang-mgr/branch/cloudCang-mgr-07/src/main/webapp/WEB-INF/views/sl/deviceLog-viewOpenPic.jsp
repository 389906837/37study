<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>开关门图片(左开门右关门)</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
</head>
<body>
<div class="ibox-content">
    <form class="layui-form" id="myForm">

        <c:if test="${fn:length(openPics) > 0}">
            <c:forEach items="${openPics}" var="item" varStatus="L">
                ${item.cameraCode}
                <div class="layui-form-item">
                    <div class="layui-col-md6">
                        <label class="layui-form-label"></label>
                        <div class="layui-input-inline">
                            <c:if test="${not empty item.imageUrl}">
                                <img src="${dynamicSource}${item.imageUrl}"
                                     style="width: 100%;height: 100%"/>
                            </c:if>
                            <c:if test="${empty item.imageUrl}">
                                <img src="${staticSource}/resources/images/37cang.png"
                                     style="width: 100%;height: 100%"/>
                            </c:if>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <label class="layui-form-label"></label>
                        <div class="layui-input-inline">
                            <c:if test="${not empty item.closeImgUrl}">
                                <img src="${dynamicSource}${item.closeImgUrl}"
                                     style="width: 100%;height: 100%"/>
                            </c:if>
                            <c:if test="${empty item.closeImgUrl}">
                                <img src="${staticSource}/resources/images/37cang.png"
                                     style="width: 100%;height: 100%"/>
                            </c:if>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:if>
        <%--    <div class="layui-form-item wflayui-form-item">
                <table cellspacing="0" border="0">
                    <c:forEach items="${openPics}" var="item" varStatus="L">
                        <tr>
                            <td class="text-left" style="width:18%">摄像头：</td>
                            <td class="text-right" style="width:32%">${item.cameraCode}</td>
                            <td class="text-left" style="width:18%"></td>
                            <td class="text-right" style="width:32%"></td>
                        </tr>
                        <tr>
                            <td class="text-left" style="width:18%">开门图片：</td>
                            <td class="text-right" style="width:32%"><img src="${dynamicSource}${item.imageUrl}"
                                                                          style="width: 100%;height: 100%"/></td>
                            <td class="text-left" style="width:18%">关门图片：</td>
                            <td class="text-right" style="width:32%"><img src="${dynamicSource}${item.imageUrl}"
                                                                          style="width: 100%;height: 100%"/></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>--%>
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

    layui.use('flow', function () {
        var flow = layui.flow;
        //按屏加载图片
        flow.lazyimg({
            elem: '#LAY_demo3 img'
            , scrollElem: '#LAY_demo3' //一般不用设置，此处只是演示需要。
        });
    });
    $(function () {
        $("#cancelBtn").click(function () {
            parent.layer.close(index);
            return false;
        });
    });
</script>
</body>
</html>

