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
                    <h4 class="page-header">角色管理</h4>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <form action="${ctx}/mgr/role/list" method="post" class="form-inline" role="form">
               <div class="panel panel-default">
                       <div class="panel-heading">
                                                                                 查询条件
                       </div>
            	  <div class="panel-body">
            	   <div class="row">
            	  	<div class="form-group">
            	  	  <div class="input-group">
            	  			<div class="input-group-addon">名称</div>
						    <input type="text" class="form-control" id="sroleNameId" name="sroleName" value="${role.sroleName}" placeholder="请输入名称">
					    </div>
  					</div>
  					  <button type="button" class="btn btn-success" onclick="query();">查询</button>
					  <button type="button" class="btn btn-default" onclick="doclear();">清空</button>
					  <shiro:hasPermission name="edit_role">
					  <button type="button" class="btn btn-primary" onclick="javascript:window.location.href='${ctx}/mgr/role/edit'">新增</button>
  					  </shiro:hasPermission>
  					</div>
  					
            	  </div>
            	  
            </div>
			<div class="row">
				<table class="table table-bordered table-striped table-condensed">
	  				 <thead>
                           <tr>
                               <th>角色名称</th>
                               <th>备注</th>
                               <th>操作</th>
                           </tr>
                       </thead>
                      <tbody>
                      	  <c:if test="${not empty page.results}">
                      	  	<c:forEach items="${page.results}" var="op">
                      	  		<tr>
	                               <td>${op.sroleName}</td>
	                               <td>${op.sremark}</td>
	                               <td>	
	                               		<shiro:hasPermission name="edit_role">
	                               		<a href="${ctx}/mgr/role/edit?id=${op.id}">编辑</a> &nbsp;&nbsp;&nbsp;&nbsp;
	                               		</shiro:hasPermission>
	                               		<shiro:hasPermission name="pur_role">
	                               		<a href="${ctx}/mgr/role/setPurview?id=${op.id}">分配权限</a> &nbsp;&nbsp;&nbsp;&nbsp;
	                               		</shiro:hasPermission>
	                               		 <shiro:hasPermission name="del_role">
	                               		<a href="javascript:void(0);" onclick="del('${op.id}')">删除</a>  &nbsp;&nbsp;&nbsp;&nbsp;
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

function del(id){
	
	$.confirm({
		confirmButtonClass: 'btn-danger',
		cancelButtonClass: 'btn-info',
	    title: '提示',
	    content: '是否确认删除？',
	    confirm: function(){
	    	  window.location.href='${ctx}/mgr/role/delete?id='+id+'&backUri=${ctx}/mgr/role/list';
	    },
	    cancel:function(){
	        
	    }
	});

}

function doclear(){
	$('#sroleNameId').val('');
}
</script>
</body>

</html>
