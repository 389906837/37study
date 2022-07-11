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
                    <h4 class="page-header">编辑菜单</h4>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <form id="defaultForm" class="form-horizontal" action="${ctx}/mgr/menu/save?backUri=${ctx}/mgr/menu/list" method="post" role="form">
              <input type="hidden" name="id" value="${menu.id}">
               <div class="panel panel-default">
	           	  <div class="panel-body">
	           	   <div class="form-group">
	           	        <div class="group">
                          <label class="col-sm-2 control-label" for="snameInput">名称</label>
                          <div class="col-sm-4">
                              <input type="text" class="form-control" id="snameInput" name="sname" value="${menu.sname}" placeholder="请输入名称" required data-bv-trigger="blur" data-bv-notempty-message="名称不能为空"/>
                          </div>
                     </div>
                       <div class="group">
                          <label class="col-sm-2 control-label" for="smenuPathId">路径</label>
                          <div class="col-sm-4">
                             <input class="form-control" id="smenuPathId" name="smenuPath" value="${menu.smenuPath}" type="text" placeholder="请输入路径" required data-bv-trigger="blur" 
                             data-bv-notempty-message="路径不能为空"/>
                          </div>
                         </div>
                     </div>
	          	    <div class="form-group">
  					      <div class="group">
						    <label class="col-sm-2 control-label" for="isortInpt">排序号</label>
						    <div class="col-sm-4">
						    <input type="text" class="form-control" id="isortInpt" name="isort" value="${menu.isort}"  placeholder="请输入排序号"  required data-bv-trigger="blur" 
                             data-bv-notempty-message="排序号不能为空"  pattern="^[0-9]*$" data-bv-regexp-message="排序号格式不正确">
						    </div>
						 </div>
						  <div class="group">
                          <label class="col-sm-2 control-label" for="simagePathId">图标</label>
                          <div class="col-sm-4">
                             <input class="form-control" id="smenuPathId" name="simagePath" value="${menu.simagePath}" type="text" placeholder="请输入图标" />
                          </div>
                         </div>
  					    </div>
  					    <div class="form-group">
  					      <div class="group">
						    <label class="col-sm-2 control-label" for="imenuLevelId">菜单层级</label>
						    <div class="col-sm-4">
						    <input type="text" class="form-control" id="imenuLevelId" name="imenuLevel" value="${menu.imenuLevel}"  placeholder="请输入菜单层级"  required data-bv-trigger="blur" 
                             data-bv-notempty-message="菜单层级不能为空"  pattern="^[0-9]*$" data-bv-regexp-message="菜单层级格式不正确">
						    </div>
						 </div>
						  <div class="group">
                          <label class="col-sm-2 control-label" for="sparentNameId">父菜单</label>
                          <div class="col-sm-4">
	                           <div class="input-group"><input type="hidden" name="sparentId" value="${menu.sparentId}"><input type="text" class="form-control" placeholder="请选择父菜单" id="sparentNameId" name="sparentName" value="${menu.sparentName}"><span class="input-group-btn"><button class="btn btn-default" onClick="getCommonTree('${ctx}/mgr/menu/getMenuTree',new Array(this.parentNode.parentNode.childNodes[0],this.parentNode.parentNode.childNodes[1]));" type="button">选择..</button></span></div>
                          </div>
                         </div>
  					    </div>
  					    <div class="form-group">
  					      <div class="group">
						    <label class="col-sm-2 control-label" for="sremarkInpt">备注</label>
						    <div class="col-sm-10">
						    <textarea class="form-control" name="sremark" rows="3" id="sremarkInpt">${menu.sremark}</textarea>
						 </div>
  					    </div>
  					    </div>
  					   <div class="form-group" style="margin-top:10px">
	  					   <div class="col-sm-12 text-center">
		  					   	<button type="submit" class="btn btn-success">保存</button>
		  					   	<button type="button" class="btn btn-info" onclick="window.location.href='${ctx}/mgr/menu/list'">返回列表</button>
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
