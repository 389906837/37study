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
                    <h4 class="page-header">编辑权限</h4>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <form id="defaultForm" class="form-horizontal" action="${ctx}/mgr/purview/save?backUri=${ctx}/mgr/purview/list" method="post" role="form">
              <input type="hidden" name="id" value="${purview.id}">
               <div class="panel panel-default">
	           	  <div class="panel-body">
	           	  <div class="form-group">
	           	        <div class="group">
                          <label class="col-sm-2 control-label" for="spurCodeId">权限编码</label>
                          <div class="col-sm-4">
                              <input type="text" class="form-control" id="spurCodeId" name="spurCode" value="${purview.spurCode}" placeholder="请输入权限编码" required data-bv-trigger="blur" data-bv-notempty-message="权限编码不能为空"/>
                          </div>
                     </div>
                       <div class="group">
                          <label class="col-sm-2 control-label" for="spurNameId">权限名称</label>
                          <div class="col-sm-4">
                              <input type="text" class="form-control" id="spurNameId" name="spurName" value="${purview.spurName}" placeholder="请输入权限名称" required data-bv-trigger="blur" data-bv-notempty-message="权限名称不能为空"/>
                          </div>
                     </div>
                     </div>
  					    <div class="form-group">
  					      <div class="group">
                          <label class="col-sm-2 control-label" for="surlPathId">路径</label>
                          <div class="col-sm-4">
                             <input class="form-control" id="surlPathId" name="surlPath" value="${purview.surlPath}" type="text" placeholder="请输入路径" required data-bv-trigger="blur" 
                             data-bv-notempty-message="路径不能为空"/>
                          </div>
                         </div>
						  <div class="group">
                          <label class="col-sm-2 control-label" for="sparentNameId">父菜单</label>
                          <div class="col-sm-4">
	                           <div class="input-group"><input type="hidden" name="sparentId" value="${purview.sparentId}" required data-bv-trigger="blur" data-bv-notempty-message="父菜单不能为空"><input type="text" class="form-control" placeholder="请选择父菜单" id="sparentNameId" name="sparentName" value="${purview.sparentName}" required data-bv-trigger="blur" data-bv-notempty-message="父菜单不能为空"><span class="input-group-btn"><button class="btn btn-default" onClick="getCommonTree('${ctx}/mgr/menu/getMenuTree',new Array(this.parentNode.parentNode.childNodes[0],this.parentNode.parentNode.childNodes[1]));" type="button">选择..</button></span></div>
                          </div>
                         </div>
  					    </div>
  					    <div class="form-group">
  					      <div class="group">
						    <label class="col-sm-2 control-label" for="sdescriptionId">说明</label>
						    <div class="col-sm-10">
						    <textarea class="form-control" name="sdescription" rows="3" id="sdescriptionId">${purview.sdescription}</textarea>
						 </div>
  					    </div>
  					    </div>
  					   <div class="form-group" style="margin-top:10px">
	  					   <div class="col-sm-12 text-center">
		  					   	<button type="submit" class="btn btn-success">保存</button>
		  					   	<button type="button" class="btn btn-info" onclick="window.location.href='${ctx}/mgr/purview/list'">返回列表</button>
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
