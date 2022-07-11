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
                    <h4 class="page-header">相册片管理</h4>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <form action="${ctx}/mgr/albumImage/list" method="post" class="form-inline" role="form">
               <div class="panel panel-default">
                       <div class="panel-heading">
                                                                                 查询条件
                       </div>
            	  <div class="panel-body">
            	  	<div class="form-group">
            	  	  <div class="input-group">
            	  			<div class="input-group-addon">相册名称</div>
						    <hurbao:select type="col" cssClass="form-control" id="albumNameInputId" name="albumId" objs="${albums}" listKey="id" listValue="sname" value="${albumImage.albumId}" entire="true"></hurbao:select>
					    </div>
  					</div>
  					<div class="form-group">
            	  	  <div class="input-group">
            	  			<div class="input-group-addon">描述</div>
						    <input type="text" class="form-control" id="descId" name="desc" value="${albumImage.desc}" placeholder="请输入描述">
					    </div>
  					</div>
					  <button type="button" class="btn btn-success" onclick="query();">查询</button>
					   <button type="button" class="btn btn-default" onclick="doclear();">清空</button>
					     <shiro:hasPermission name="edit_albumImage">
					      <button type="button" class="btn btn-primary" onclick="javascript:window.location.href='${ctx}/mgr/albumImage/edit'">新增</button>
            	 	  </shiro:hasPermission>
            	  </div>
            	  
            </div>
			<div class="row">
				<table class="table table-bordered table-striped table-condensed">
	  				 <thead>
                           <tr>
                               <th>相册名称</th>
                               <th>相片</th>
                               <th>排序</th>
                               <th>描叙</th>
                               <th>上传人</th>
                               <th>上传日期</th>
                               <th>操作</th>
                           </tr>
                       </thead>
                      <tbody>
                      	  <c:if test="${not empty page.results}">
                      	  	<c:forEach items="${page.results}" var="op">
                      	  		<tr>
	                               <td>${op.albumName}</td>
	                               <td><c:if test="${not empty op.contenturl}"><a class="tip" href="${op.contenturl}" target="_blank" >查看</a></c:if>  </td>
	                               <td>${op.isort}</td>
	                                <td>${op.desc}</td>
	                               <td>${op.uloadUser}</td>
	                               <td><f:formatDate value="${op.uploadDate}" pattern="yyyy-MM-dd"/></td>
	                               <td>
	                               <shiro:hasPermission name="edit_albumImage">
	                               	<a href="${ctx}/mgr/albumImage/edit?id=${op.id}">编辑</a> &nbsp;&nbsp;&nbsp;&nbsp;
	                               	</shiro:hasPermission>
	                               	 <shiro:hasPermission name="del_albumImage">
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
	    	  window.location.href='${ctx}/mgr/albumImage/delete?id='+id+'&backUri=${ctx}/mgr/albumImage/list';
	    },
	    cancel:function(){
	        
	    },
	    buttons:{ok:'是',cancel:'否'}
	});

}

function doclear(){
	$('#albumNameInputId').val('');
	$('#descId').val('');
	
}


</script>
</body>

</html>
