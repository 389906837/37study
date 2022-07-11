<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<jsp:include page="/common/include/mgrHeader.jsp"></jsp:include>
<link href="${staticSource}/static/css/metroStyle/metroStyle.css" rel="stylesheet">
<script src="${staticSource}/static/js/jquery.ztree.core.min.js"></script>
<script src="${staticSource}/static/js/jquery.ztree.excheck.min.js"></script>
<body>
    <div id="wrapper">
	<jsp:include page="/common/include/nav.jsp"></jsp:include>
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h4 class="page-header">分配权限</h4>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <form id="defaultForm" class="form-horizontal" action="${ctx}/mgr/role/saveRolePurview?backUri=${ctx}/mgr/role/list" method="post" role="form" onsubmit="return checkSubmit()">
              <input type="hidden" name="roleId" value="${role.id}">
              <input type="hidden" id="checkPurviewId" name="checkPurviewId">
               <div class="panel panel-default">
	           	  <div class="panel-body">
	           	  <div class="form-group">
                       <div class="group">
                          <label class="col-sm-2 control-label" for="sroleNameId">角色名称</label>
                          <div class="col-sm-4">${role.sroleName} </div>
                     </div>
                     </div>
                      <div class="form-group">
                      	 <div class="group">
                      	 	<ul id="treeDemo" class="ztree"></ul>
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
        
        var zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
		var nodes = zTreeObj.getCheckedNodes();
		for (var i = 0, l = nodes.length; i < l; i++) {
		zTreeObj.checkNode(nodes[i], true, true);
		}
    });
</script>
	<SCRIPT type="text/javascript">
	
		var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};

		var zNodes =${trees}; /* [
			{ id:1, pId:0, name:"随意勾选 1", open:true,isLeaf:0},
			{ id:11, pId:1, name:"随意勾选 1-1", open:true},
			{ id:111, pId:11, name:"随意勾选 1-1-1",checked:true,isLeaf:1},
			{ id:112, pId:11, name:"随意勾选 1-1-2",checked:true},
			{ id:12, pId:1, name:"随意勾选 1-2", open:true},
			{ id:121, pId:12, name:"随意勾选 1-2-1"},
			{ id:122, pId:12, name:"随意勾选 1-2-2"},
			{ id:2, pId:0, name:"随意勾选 2", checked:true, open:true},
			{ id:21, pId:2, name:"随意勾选 2-1"},
			{ id:22, pId:2, name:"随意勾选 2-2", open:true},
			{ id:221, pId:22, name:"随意勾选 2-2-1", checked:true},
			{ id:222, pId:22, name:"随意勾选 2-2-2"},
			{ id:23111111, pId:2, name:"随意勾选 2-31111"}
		]; */
		
		function checkSubmit(){
			//选中
			getAllselected();
			return true;
		}
		function getAllselected(){
			var checkedPurs = '';
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			var nodes = zTree.getCheckedNodes();
			for (var i = 0, l = nodes.length; i < l; i++) {
			    if (nodes[i].isLeaf == 1){
			    	checkedPurs+= nodes[i].id+',';
				}
				
			}
			$('#checkPurviewId').val(checkedPurs);
		} 		
		
		
	</SCRIPT>
<jsp:include page="/common/include/mgrEditBottom.jsp"></jsp:include>
</body>

</html>
