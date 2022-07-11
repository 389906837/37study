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
                    <h4 class="page-header">用户管理</h4>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <form action="${ctx}/mgr/operator/list" method="post" class="form-inline" role="form">
               <div class="panel panel-default">
                       <div class="panel-heading">
                                                                                 查询条件
                       </div>
            	  <div class="panel-body">
            	  	<div class="form-group">
            	  	  <div class="input-group">
            	  			<div class="input-group-addon">用户名</div>
						    <input type="text" class="form-control" id="suserNameId" name="suserName" value="${operatorVo.suserName}" placeholder="请输入用户名">
					    </div>
  					</div>
					 <div class="form-group">
            	  	  <div class="input-group">
            	  			<div class="input-group-addon">手机号</div>
						    <input type="text" class="form-control" id="smobileId" name="smobile" value="${operatorVo.smobile }" placeholder="请输入手机号">
					    </div>
  					</div>
					 <div class="form-group">
            	  	  <div class="input-group">
            	  			<div class="input-group-addon">用户姓名</div>
						    <input type="text" class="form-control" id="srealNameId" name="srealName" value="${operatorVo.srealName }" placeholder="请输入用户姓名">
					    </div>
  					</div>
					  <button type="button" class="btn btn-success" onclick="query();">查询</button>
					  <button type="button" class="btn btn-default" onclick="doclear();">清空</button>
					  <shiro:hasPermission name="edit_opt">
					  	<button type="button" class="btn btn-primary" onclick="javascript:window.location.href='${ctx}/mgr/operator/edit'">新增</button>
            	  	 </shiro:hasPermission>
            	  </div>
            	  
            </div>
			<div class="row">
				<table class="table table-bordered table-striped table-condensed">
	  				 <thead>
                           <tr>
                               <th>用户名</th>
                               <th>真实姓名</th>
                               <th>手机</th>
                               <th>邮箱</th>
                               <th>状态</th>
                               <th>操作</th>
                           </tr>
                       </thead>
                      <tbody>
                      	  <c:if test="${not empty page.results}">
                      	  	<c:forEach items="${page.results}" var="op">
                      	  		<tr>
	                               <td>${op.suserName}</td>
	                               <td>${op.srealName}</td>
	                               <td>${op.smobile}</td>
	                               <td>${op.smail}</td>
	                               <td>
	                               	 <c:choose>
	                               	 	<c:when test="${op.bisFreeze eq 1}">
	                               	 		启用
	                               	 	</c:when>
	                               	 	<c:otherwise>
	                               	 		冻结
	                               	 	</c:otherwise>
	                               	 </c:choose>
	                               </td>
	                               <td>
	                               <shiro:hasPermission name="edit_opt">
	                               	<a href="${ctx}/mgr/operator/edit?id=${op.id}">编辑</a> &nbsp;&nbsp;&nbsp;&nbsp;
	                               	</shiro:hasPermission>
	                               	<shiro:hasPermission name="asign_opt">
	                               	<a href="${ctx}/mgr/operator/setOperatorRole?id=${op.id}">分派角色</a> &nbsp;&nbsp;&nbsp;&nbsp;
	                               	</shiro:hasPermission>
	                               	<shiro:hasPermission name="pass_opt">
	                               	<a href="${ctx}/mgr/operator/updatePassword?id=${op.id}">修改密码</a> &nbsp;&nbsp;&nbsp;&nbsp;
	                               	</shiro:hasPermission>
	                               	 <shiro:hasPermission name="del_opt">
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
function doclear(){
	$('#suserNameId').val('');
	$('#smobileId').val('');
	$('#srealNameId').val('');
	
}

function del(id){
	
	$.confirm({
		confirmButtonClass: 'btn-danger',
		cancelButtonClass: 'btn-info',
	    title: '提示',
	    content: '是否确认删除？',
	    confirm: function(){
	    	  window.location.href='${ctx}/mgr/operator/delete?id='+id+'&backUri=${ctx}/mgr/operator/list';
	    },
	    cancel:function(){
	        
	    }
	});

}

</script>
</body>

</html>
