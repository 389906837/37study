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
                    <h4 class="page-header">权限管理</h4>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <form action="${ctx}/mgr/purview/list" method="post" class="form-inline" role="form">
               <div class="panel panel-default">
                       <div class="panel-heading">
                                                                                 查询条件
                       </div>
            	  <div class="panel-body">
            	   <div class="row">
            	  	<div class="form-group">
            	  	  <div class="input-group">
            	  			<div class="input-group-addon">权限名称</div>
						    <input type="text" class="form-control" id="spurNameId" name="spurName" value="${purview.spurName}" placeholder="请输入权限名称">
					    </div>
  					</div>
					 
  					<div class="form-group">
            	  	  <div class="input-group"><div class="input-group-addon">父菜单</div><input type="hidden" id="sparentId" name="sparentId" value="${purview.sparentId}"><input type="text" class="form-control" placeholder="请选择父菜单" id="sparentNameId" name="sparentName" value="${purview.sparentName}"><span class="input-group-btn"><button class="btn btn-default" onClick="getCommonTree('${ctx}/mgr/menu/getMenuTree',new Array(this.parentNode.parentNode.childNodes[1],this.parentNode.parentNode.childNodes[2]));" type="button">选择..</button></span></div>
  					</div>
  					  <button type="button" class="btn btn-success" onclick="query();">查询</button>
					  <button type="button" class="btn btn-default" onclick="doclear();">清空</button>
					   <shiro:hasPermission name="edit_pur">
					  <button type="button" class="btn btn-primary" onclick="javascript:window.location.href='${ctx}/mgr/purview/edit'">新增</button>
  					 </shiro:hasPermission>
  					</div>
  					
            	  </div>
            	  
            </div>
			<div class="row">
				<table class="table table-bordered table-striped table-condensed">
	  				 <thead>
                           <tr>
                               <th>权限编号</th>
                               <th>权限码</th>
                               <th>权限名称</th>
                               <th>父菜单名称</th>
                               <th>权限路径</th>
                               <th>说明</th>
                               <th>添加日期</th>
                               <th>操作</th>
                           </tr>
                       </thead>
                      <tbody>
                      	  <c:if test="${not empty page.results}">
                      	  	<c:forEach items="${page.results}" var="op">
                      	  		<tr>
	                               <td>${op.spurNo}</td>
	                               <td>${op.spurCode}</td>
	                               <td>${op.spurName}</td>
	                               <td>${op.sparentName}</td>
	                               <td>${op.surlPath}</td>
	                               <td>${op.sdescription}</td>
	                                <td><f:formatDate value="${op.daddDate}" pattern="yyyy-MM-dd"/></td>
	                               <td>	
	                                <shiro:hasPermission name="edit_pur">
                               		 <a href="${ctx}/mgr/purview/edit?id=${op.id}">编辑</a> &nbsp;&nbsp;&nbsp;&nbsp;
                               		 </shiro:hasPermission>
                               		 <shiro:hasPermission name="del_pur">
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
	    	  window.location.href='${ctx}/mgr/purview/delete?id='+id+'&backUri=${ctx}/mgr/purview/list';
	    },
	    cancel:function(){
	        
	    }
	});

}

function doclear(){
	$('#spurNameId').val('');
	$('#sparentId').val('');
	$('#sparentNameId').val('');
	
	
}
</script>
</body>

</html>
