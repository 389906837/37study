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
                    <h4 class="page-header">新闻栏目管理</h4>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <form action="${ctx}/mgr/nav/list" method="post" class="form-inline" role="form">
               <div class="panel panel-default">
                       <div class="panel-heading">
                                                                                 查询条件
                       </div>
            	  <div class="panel-body">
            	  	<div class="form-group">
            	  	  <div class="input-group">
            	  			<div class="input-group-addon">编号</div>
						    <input type="text" class="form-control" id="scodeId" name="scode" value="${navigation.scode}" placeholder="请输入编号">
					    </div>
  					</div>
					 <div class="form-group">
            	  	  <div class="input-group">
            	  			<div class="input-group-addon">栏目名称</div>
						    <input type="text" class="form-control" id="snameId" name="sname" value="${navigation.sname}" placeholder="请输入栏目名称">
					    </div>
  					</div>
					  <button type="button" class="btn btn-success" onclick="query();">查询</button>
					  <button type="button" class="btn btn-default" onclick="doclear();">清空</button>
					   <shiro:hasPermission name="edit_nav">
					  <button type="button" class="btn btn-primary" onclick="javascript:window.location.href='${ctx}/mgr/nav/edit'">新增</button>
					  </shiro:hasPermission>
            	  </div>
            	  
            </div>
			<div class="row">
				<table class="table table-bordered table-striped table-condensed">
	  				 <thead>
                           <tr>
                               <th>编号</th>
                               <th>栏目名称</th>
                               <th>排序</th>
                               <th>是否默认栏目</th>
                               <th>默认显示条数</th>
                               <th>备注</th>
                               <th>添加人</th>
                               <th>添加日期</th>
                               <th>操作</th>
                           </tr>
                       </thead>
                      <tbody>
                      	  <c:if test="${not empty page.results}">
                      	  	<c:forEach items="${page.results}" var="op">
                      	  		<tr>
	                               <td>${op.scode}</td>
	                               <td>${op.sname}</td>
	                               <td>${op.isort}</td>
	                               <td>
	                               		<c:choose>
	                               			<c:when test="${op.iisDefault eq 1 }">
	                               				是
	                               			</c:when>
	                               			<c:otherwise>
	                               				否
	                               			</c:otherwise>
	                               		
	                               		</c:choose>
	                               </td>
	                                <td>${op.idefaultView}</td>
	                                <td>${op.sremark}</td>
	                               <td>${op.saddUser}</td>
	                               <td><f:formatDate value="${op.taddTime}" pattern="yyyy-MM-dd"/></td>
	                              
	                               <td>
	                               <shiro:hasPermission name="edit_nav">
	                               	<a href="${ctx}/mgr/nav/edit?id=${op.id}">编辑</a> &nbsp;&nbsp;&nbsp;&nbsp;
	                               	</shiro:hasPermission>
	                               	 <shiro:hasPermission name="del_nav">
	                               	<a href="javascript:void(0);" onclick="del('${op.id}')">删除</a> 
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
	    	  window.location.href='${ctx}/mgr/nav/delete?id='+id+'&backUri=${ctx}/mgr/nav/list';
	    },
	    cancel:function(){
	        
	    },
	    buttons:{ok:'是',cancel:'否'}
	});

}

function doclear(){
	$('#scodeId').val('');
	$('#snameId').val('');
	
}

</script>
</body>

</html>
