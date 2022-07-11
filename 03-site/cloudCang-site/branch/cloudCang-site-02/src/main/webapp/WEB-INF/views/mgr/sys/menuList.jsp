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
                    <h4 class="page-header">菜单管理</h4>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <form action="${ctx}/mgr/menu/list" method="post" class="form-inline" role="form">
               <div class="panel panel-default">
                       <div class="panel-heading">
                                                                                 查询条件
                       </div>
            	  <div class="panel-body">
            	   <div class="row">
            	  	<div class="form-group">
            	  	  <div class="input-group">
            	  			<div class="input-group-addon">名称</div>
						    <input type="text" class="form-control" id="snameId" name="sname" value="${menu.sname}" placeholder="请输入名称">
					    </div>
  					</div>
					 
  					<div class="form-group">
            	  	  <div class="input-group"><div class="input-group-addon">父菜单</div><input type="hidden" id="sparentId" name="sparentId" value="${menu.sparentId}"><input type="text" class="form-control" placeholder="请选择父菜单" id="sparentNameId" name="sparentName" value="${menu.sparentName}"><span class="input-group-btn"><button class="btn btn-default" onClick="getCommonTree('${ctx}/mgr/menu/getMenuTree',new Array(this.parentNode.parentNode.childNodes[1],this.parentNode.parentNode.childNodes[2]));" type="button">选择..</button></span></div>
  					</div>
  					  <button type="button" class="btn btn-success" onclick="query();">查询</button>
					  <button type="button" class="btn btn-default" onclick="doclear();">清空</button>
					   <shiro:hasPermission name="edit_menu">
					  <button type="button" class="btn btn-primary" onclick="javascript:window.location.href='${ctx}/mgr/menu/edit'">新增</button>
  						</shiro:hasPermission>
  					</div>
  					
            	  </div>
            	  
            </div>
			<div class="row">
				<table class="table table-bordered table-striped table-condensed">
	  				 <thead>
                           <tr>
                               <th>菜单编号</th>
                               <th>菜单名称</th>
                               <th>是否父菜单</th>
                               <th>父菜单名称</th>
                               <th>菜单阶层</th>
                               <th>菜单路径</th>
                                <th>排序号</th>
                               <th>添加日期</th>
                               <th>添加人</th>
                               <th>操作</th>
                           </tr>
                       </thead>
                      <tbody>
                      	  <c:if test="${not empty page.results}">
                      	  	<c:forEach items="${page.results}" var="op">
                      	  		<tr>
	                               <td>${op.smenuNo}</td>
	                               <td>${op.sname}</td>
	                               <td>
	                                <c:choose>
	                               			<c:when test="${op.bisRoot eq 1 }">
	                               				是
	                               			</c:when>
	                               			<c:otherwise>
	                               				否
	                               			</c:otherwise>
	                               		
	                               		</c:choose>
	                                </td>
	                               <td>${op.sparentName}</td>
	                               <td>${op.imenuLevel}</td>
	                               <td>${op.smenuPath}</td>
	                               <td>${op.isort}</td>
	                                <td><f:formatDate value="${op.daddDate}" pattern="yyyy-MM-dd"/></td>
	                                <td>${op.saddOperator}</td>
	                               <td>	
	                               <c:if test="${op.smenuNo ne '1611011622455540'}">
	                                   <shiro:hasPermission name="edit_menu">
	                               		<a href="${ctx}/mgr/menu/edit?id=${op.id}">编辑</a> &nbsp;&nbsp;&nbsp;&nbsp;
	                               		</shiro:hasPermission>
	                               		<shiro:hasPermission name="del_menu">
	                               		<a href="javascript:void(0);" onclick="del('${op.id}')">删除</a>  &nbsp;&nbsp;&nbsp;&nbsp;
	                               		</shiro:hasPermission>
	                               	</c:if>
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
	    	  window.location.href='${ctx}/mgr/menu/delete?id='+id+'&backUri=${ctx}/mgr/menu/list';
	    },
	    cancel:function(){
	        
	    }
	});

}

function doclear(){
	$('#snameId').val('');
	$('#sparentId').val('');
	$('#sparentNameId').val('');
	
	
}
</script>
</body>

</html>
