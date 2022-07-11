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
                <h4 class="page-header">推荐管理</h4>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <form action="${ctx}/mgr/recommend/list" method="post" class="form-inline" role="form">
            <div class="panel panel-default">
                <div class="panel-heading">
                    查询条件
                </div>
                <div class="panel-body">
                    <div class="form-group">
                        <div class="input-group">
                            <div class="input-group-addon">排序号</div>
                            <input type="text" class="form-control" id="sortId" name="sort" value="${region.sort}"
                                   placeholder="请输入排序号">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <div class="input-group-addon">简述</div>
                            <input type="text" class="form-control" id="sketchId" name="sketch" value="${region.sketch}"
                                   placeholder="请输入简述">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <div class="input-group-addon">状态</div>
                            <hurbao:select type="list" list="{1:是,0:否}" id="iisExhibitionId" name="iisExhibition" cssClass="form-control" entire="true" value="${region.iisExhibition }"></hurbao:select>
                        </div>
                    </div>
                    <button type="button" class="btn btn-success" onclick="query();">查询</button>
                    <button type="button" class="btn btn-default" onclick="doclear();">清空</button>
                    <button type="button" class="btn btn-primary"
                            onclick="javascript:window.location.href='${ctx}/mgr/recommend/edit'">新增
                    </button>
                </div>

            </div>
            <div class="row">
                <table class="table table-bordered table-striped table-condensed">
                    <thead>
                    <tr>
                        <th>排序</th>
                        <th>简述</th>
                        <th>是否展示</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:if test="${not empty page.results}">
                        <c:forEach items="${page.results}" var="op">
                            <tr>
                                <td>${op.sort}</td>
                                <td>${op.sketch}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${op.iisExhibition eq 1 }">
                                            是
                                        </c:when>
                                        <c:otherwise>
                                            否
                                        </c:otherwise>

                                    </c:choose>
                                </td>
                                <td>
                                    <a href="${ctx}/mgr/recommend/preview?advertisId=${op.advertisId}" target="_blank">预览</a>&nbsp;&nbsp;
                                    <a href="${ctx}/mgr/recommend/edit?id=${op.id}">编辑</a> &nbsp;&nbsp;&nbsp;&nbsp;
                                    <a href="javascript:void(0);" onclick="del('${op.id}')">删除</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    </tbody>
                </table>
                <jsp:include page="/common/include/pagenation.jsp"></jsp:include>
            </div>

        </form>

    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->

<script type="text/javascript">

    function del(id) {

        $.confirm({
            confirmButtonClass: 'btn-danger',
            cancelButtonClass: 'btn-info',
            title: '提示',
            content: '是否确认删除？',
            confirm: function () {
                window.location.href = '${ctx}/mgr/recommend/delete?id=' + id + '&backUri=${ctx}/mgr/recommend/list';
            },
            cancel: function () {

            },
            buttons: {ok: '是', cancel: '否'}
        });

    }

    function doclear() {
        $('#scodeId').val('');
        $('#sregionNameId').val('');
    }

</script>
</body>

</html>
