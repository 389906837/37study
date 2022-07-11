<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<jsp:include page="/common/include/mgrHeader.jsp"></jsp:include>
<body>
<div id="wrapper">
    <jsp:include page="/common/include/nav.jsp"></jsp:include>


    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h4 class="page-header">编辑推荐新闻</h4>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <form id="defaultForm" class="form-horizontal"
              action="${ctx}/mgr/recommend/save?backUri=${ctx}/mgr/recommend/list" method="post" role="form">
            <input type="hidden" name="id" value="${recommend.id}">
            <div class="panel panel-default">
                <div class="panel-body">
                    <div class="form-group">
                        <div class="group">
                            <label class="col-sm-2 control-label" for="advertisIdInput">新闻id</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="advertisIdInput" name="advertisId"
                                       value="${recommend.advertisId}" placeholder="请输入新闻id" required
                                       data-bv-trigger="blur" data-bv-notempty-message="新闻id不能为空"/>
                            </div>
                        </div>
                        <div class="group">
                            <label class="col-sm-2 control-label" for="sortInput">排序号</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="sortInput" name="sort"
                                       value="${recommend.sort}" placeholder="请输入排序号" required data-bv-trigger="blur"
                                       data-bv-notempty-message="排序号不能为空"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="group">
                            <label class="col-sm-2 control-label" for="iisExhibitionInpt">是否展示</label>
                            <div class="col-sm-4">
                                <select id="iisExhibitionInpt" name="iisExhibition" class="form-control"
                                        data-bv-trigger="keyup" required data-bv-notempty-message="是否展示不能为空">
                                    <c:choose>
                                        <c:when test="${recommend.iisExhibition eq 1}">
                                            <option value=1 selected>是</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value=1>是</option>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${recommend.iisExhibition eq 0}">
                                            <option value=0 selected>否</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value=0>否</option>
                                        </c:otherwise>
                                    </c:choose>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="group">
                            <label class="col-sm-2 control-label" for="sketchInpt">简述</label>
                            <div class="col-sm-10">
                                <textarea class="form-control" rows="3" id="sketchInpt"
                                          name="sketch">${recommend.sketch}</textarea>
                            </div>
                        </div>
                    </div>
                    <div class="form-group" style="margin-top:10px">
                        <div class="col-sm-12 text-center">
                            <button type="submit" class="btn btn-success">保存</button>
                            <button type="button" class="btn btn-info"
                                    onclick="window.location.href='${ctx}/mgr/recommend/list'">返回列表
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </form>

    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->
<script type="text/javascript">
    $(document).ready(function () {
        $('#defaultForm').bootstrapValidator(
            {group: '.group'});
    });
</script>
<jsp:include page="/common/include/mgrEditBottom.jsp"></jsp:include>
</body>

</html>
