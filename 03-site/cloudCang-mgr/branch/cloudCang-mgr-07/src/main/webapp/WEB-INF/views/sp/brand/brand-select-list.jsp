<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
<title>选择商品品牌</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/js/plugins/tree-multiselect/jquery.tree-multiselect.min.css" rel="stylesheet" />
</head>

<body>
<div class="ibox-content">
    <select id="brandSelect" multiple="multiple">
        <c:forEach items="${brandList}" var="item" varStatus="L">
            <option value="${item.id}#//#${item.text}" data-section="商品品牌" <c:if test="${fn:indexOf(brandIds,item.id) != -1}"> selected</c:if>>${item.text}</option>
        </c:forEach>
    </select>
    <div class="layui-form-item fixed-bottom">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-primary" type="button" id="cancelBtn">返回</button>
            <button class="layui-btn" type="button" id="confirmBtn">确认选择</button>
        </div>
    </div>
</div>
<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jquery-ui/jquery-ui.min.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/tree-multiselect/jquery.tree-multiselect.min.js"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    $(function() {
        $("#brandSelect").treeMultiselect();
        $("#cancelBtn").click(function() {//返回
            parent.layer.close(index);
            return false;
        });
        $("#confirmBtn").click(function() {//确认选择
            var brandIds = "";
            var brandNames = "";
            if(!isEmpty($("#brandSelect").val())) {
                $.each($("#brandSelect").val(), function (index, value) {
                    brandIds += value.split("#//#")[0] + ",";
                    brandNames += value.split("#//#")[1] + ",";
                });
            }
            parent.selectCommodityBrand(brandIds.length>0?brandIds.substr(0,brandIds.length-1):brandIds,
                brandNames.length>0?brandNames.substr(0,brandNames.length-1):brandNames);
            parent.layer.close(index);
            return false;
        });
    });
</script>
</body>
</html>