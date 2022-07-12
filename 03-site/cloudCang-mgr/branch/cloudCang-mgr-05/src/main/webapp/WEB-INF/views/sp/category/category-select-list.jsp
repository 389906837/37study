<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
<title>选择商品分类</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/js/plugins/tree-multiselect/jquery.tree-multiselect.min.css" rel="stylesheet" />
</head>

<body>
<div class="ibox-content">
    <select id="categorySelect" multiple="multiple">
        <c:forEach items="${categoryList}" var="item" varStatus="L">
            <option value="${item.id}#//#${item.text}" data-section="${item.parentName}" <c:if test="${fn:indexOf(categoryIds,item.id) != -1}"> selected</c:if>>${item.text}</option>
        </c:forEach>
    </select>
    <div class="layui-form-item fixed-bottom">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-primary" type="button" id="cancelBtn"><spring:message code="sp.brand.return" /></button>
            <button class="layui-btn" type="button" id="confirmBtn"><spring:message code="sp.brand.confirm.selection" /></button>
        </div>
    </div>
</div>
<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jquery-ui/jquery-ui.min.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/tree-multiselect/jquery.tree-multiselect.min.js"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    $(function() {
        $("#categorySelect").treeMultiselect();
        $("#cancelBtn").click(function() {//返回
            parent.layer.close(index);
            return false;
        });
        $("#confirmBtn").click(function() {//确认选择
            var categoryIds = "";
            var categoryNames = "";
            if(!isEmpty($("#categorySelect").val())) {
                $.each($("#categorySelect").val(), function (index, value) {
                    categoryIds += value.split("#//#")[0] + ",";
                    categoryNames += value.split("#//#")[1] + ",";
                });
            }
            parent.selectCommodityCategory(categoryIds.length>0?categoryIds.substr(0,categoryIds.length-1):categoryIds,
                categoryNames.length>0?categoryNames.substr(0,categoryNames.length-1):categoryNames);
            parent.layer.close(index);
            return false;
        });
    });
</script>
</body>
</html>