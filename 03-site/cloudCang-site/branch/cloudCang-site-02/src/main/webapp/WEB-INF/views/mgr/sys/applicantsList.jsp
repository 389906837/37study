<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<jsp:include page="/common/include/mgrHeader.jsp"></jsp:include>
<script src="${ctx}/static/resource/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<body>
<div id="wrapper">
    <jsp:include page="/common/include/nav.jsp"></jsp:include>


    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h4 class="page-header">测试用户管理</h4>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <form action="${ctx}/applicants/list" method="post" class="form-inline" role="form">
            <div class="panel panel-default">
                <div class="panel-heading">
                    查询条件
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="form-group">
                            <div class="input-group">
                                <div class="input-group-addon">集团/个人名称</div>
                                <input type="text" class="form-control" id="stitleId" name="userName"
                                       placeholder="请输入集团/个人名称">
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="input-group">

                            </div>
                        </div>
                    </div>
                    <div class="row" style="margin-top:20px;">
                        <div class="form-group">

                        </div>

                        <button type="button" class="btn btn-success" onclick="query();">查询</button>
                        <button type="button" class="btn btn-default" onclick="doclear();">清空</button>

                    </div>
                </div>

            </div>
            <div class="row">
                <table class="table table-bordered table-striped table-condensed">
                    <thead>
                    <tr>
                        <th>集团/个人名称</th>
                        <th>联系人</th>
                        <th>联系电话</th>
                        <th>用户需求</th>
                        <th>账号状态</th>
                        <th>账号所属类型</th>
                        <th>操作人</th>
                        <th>操作时间</th>
                        <th>账号创建时间</th>
                        <th>账号上次修改时间</th>
                        <th>注册IP</th>
                        <th>备注</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:if test="${not empty page.results}">
                        <c:forEach items="${page.results}" var="op">

                            <td>${op.userName}</td>
                            <td>${op.linkMan}</td>
                            <td>${op.tel}</td>
                            <td>${op.require}</td>
                            <td>${op.status}</td>
                            <td>${op.accountType}</td>
                            <td>${op.operator}</td>
                            <td><f:formatDate value="${op.operationTime}" pattern="yyyy-MM-dd"/></td>
                            <td><f:formatDate value="${op.creatTime}" pattern="yyyy-MM-dd"/></td>
                            <td><f:formatDate value="${op.updateTime}" pattern="yyyy-MM-dd"/></td>
                            <td>${op.ip}</td>
                            <td>${op.remarks}</td>
                            <td>
                                <shiro:hasPermission name="edit_art">
                                    <a href="${ctx}/applicants/selece/${op._id}">编辑</a> &nbsp;&nbsp;&nbsp;&nbsp;
                                </shiro:hasPermission>
                                <shiro:hasPermission name="del_art">
                                    <a href="javascript:void(0);"
                                       onclick="del('${op._id}')">删除</a> &nbsp;&nbsp;&nbsp;&nbsp;
                                </shiro:hasPermission>
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
    function query() {
        alert($("#stitleId").val());
        $(".form-inline").submit();
    }

    function del(_id) {

        $.confirm({
            confirmButtonClass: 'btn-danger',
            cancelButtonClass: 'btn-info',
            title: '提示',
            content: '是否确认删除？',
            confirm: function () {
                window.location.href='${ctx}/applicants/delete?_id=' + _id + '&backUri=${ctx}/applicants/list';
               // location.reload();
            },
            cancel: function () {

            },
            buttons: {ok: '是', cancel: '否'}
        });

    }


    function doclear() {
        $('#stitleId').val('');
        $('#startDateId').val('');
        $('#endDateId').val('');
        $('#istatusId').val('');
        $('#snavigationIdInput').val('');
        $('#iisRecommendId').val('');
        $('#iistopicId').val('');

    }

</script>
</body>

</html>
