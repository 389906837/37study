<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<jsp:include page="/common/include/mgrHeader.jsp"></jsp:include>
<body>
    <div id="wrapper">
	<jsp:include page="/common/include/nav.jsp"></jsp:include>
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h4 class="page-header">编辑角色</h4>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <form id="defaultForm" class="form-horizontal" action="${ctx}/mgr/role/save?backUri=${ctx}/mgr/role/list" method="post" role="form">
              <input type="hidden" name="id" value="${role.id}">
               <div class="panel panel-default">
	           	  <div class="panel-body">
	           	  <div class="form-group">
                       <div class="group">
                          <label class="col-sm-2 control-label" for="sroleNameId">角色名称</label>
                          <div class="col-sm-4">
                              <input type="text" class="form-control" id="sroleNameId" name="sroleName" value="${role.sroleName}" placeholder="请输入角色名称" required data-bv-trigger="blur" data-bv-notempty-message="角色名称不能为空"/>
                          </div>
                     </div>
                     </div>
  					    <div class="form-group">
  					      <div class="group">
						    <label class="col-sm-2 control-label" for="sremarkId">备注</label>
						    <div class="col-sm-10">
						    <textarea class="form-control" name="sremark" rows="3" id="sremarkId" placeholder="请输入备注" required data-bv-trigger="blur" data-bv-notempty-message="备注不能为空">${role.sremark}</textarea>
						 </div>
  					    </div>
  					    </div>
  					   <div class="form-group" style="margin-top:10px">
	  					   <div class="col-sm-12 text-center">
		  					   	<button type="submit" class="btn btn-success">保存</button>
		  					   	<button type="button" class="btn btn-info" onclick="window.location.href='${ctx}/mgr/role/list'">返回列表</button>
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
    $(document).ready(function() {
        $('#defaultForm').bootstrapValidator(
        		{group: '.group'});
    });
</script>
<jsp:include page="/common/include/mgrEditBottom.jsp"></jsp:include>
</body>

</html>
