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
                    <h4 class="page-header">相册管理</h4>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <form action="${ctx}/mgr/album/list" method="post" class="form-inline" role="form">
               <div class="panel panel-default">
                       <div class="panel-heading">
                                                                                 查询条件
                       </div>
            	  <div class="panel-body">
            	  	<div class="form-group">
            	  	  <div class="input-group">
            	  			<div class="input-group-addon">相册名称</div>
						    <input type="text" class="form-control" id="snameId" name="sname" value="${album.sname}" placeholder="请输入相册名称">
					    </div>
  					</div>
					 <div class="form-group">
            	  	  <div class="input-group">
            	  			<div class="input-group-addon">是否显示</div>
            	  			<hurbao:select type="list" list="{1:是,0:否}" id="isdispalyId" name="isdispaly" cssClass="form-control" entire="true" value="${album.isdispaly }"></hurbao:select>
					    </div>
  					</div>
					  <button type="button" class="btn btn-success" onclick="query();">查询</button>
					   <button type="button" class="btn btn-default" onclick="doclear();">清空</button>
					     <shiro:hasPermission name="edit_album">
					      <button type="button" class="btn btn-primary" onclick="javascript:window.location.href='${ctx}/mgr/album/edit'">新增</button>
            	 	  </shiro:hasPermission>
            	  </div>
            	  
            </div>
			<div class="row">
				<table class="table table-bordered table-striped table-condensed">
	  				 <thead>
                           <tr>
                               <th>编号</th>
                               <th>相册名称</th>
                               <th>封面</th>
                               <th>相片数量</th>
                               <th>排序</th>
                               <th>是否显示</th>
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
	                                  <td><c:if test="${not empty op.coverimg}"><a class="tip" href="${op.coverimg}" target="_blank">查看</a></c:if>  </td>
	                               <td>${op.icount}</td>
	                               <td>${op.isort}</td>
	                               <td>
	                               		<c:choose>
	                               			<c:when test="${op.isdispaly eq 1 }">
	                               				是
	                               			</c:when>
	                               			<c:otherwise>
	                               				否
	                               			</c:otherwise>
	                               		
	                               		</c:choose>
	                               </td>
	                               <td>${op.saddUser}</td>
	                               <td><f:formatDate value="${op.tupdateTime}" pattern="yyyy-MM-dd"/></td>
	                              
	                               <td>
	                               <shiro:hasPermission name="edit_album">
	                               	<a href="${ctx}/mgr/album/edit?id=${op.id}">编辑</a> &nbsp;&nbsp;&nbsp;&nbsp;
	                               	</shiro:hasPermission>
	                               	 <shiro:hasPermission name="del_album">
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
	    	  window.location.href='${ctx}/mgr/album/delete?id='+id+'&backUri=${ctx}/mgr/album/list';
	    },
	    cancel:function(){
	        
	    },
	    buttons:{ok:'是',cancel:'否'}
	});

}

function doclear(){
	$('#snameId').val('');
	$('#isdispalyId').val('');
	
}

</script>
</body>

</html>
