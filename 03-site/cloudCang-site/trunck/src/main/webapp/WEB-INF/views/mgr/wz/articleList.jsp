<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<jsp:include page="/common/include/mgrHeader.jsp"></jsp:include>
<script src="${ctx}/static/resource/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<body>
    <div id="wrapper">
	<jsp:include page="/common/include/nav.jsp"></jsp:include>


        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h4 class="page-header">新闻管理</h4>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <form action="${ctx}/mgr/article/list" method="post" class="form-inline" role="form">
               <div class="panel panel-default">
                       <div class="panel-heading">
                                                                                 查询条件
                       </div>
            	  <div class="panel-body">
            	   <div class="row">
            	  	<div class="form-group">
            	  	  <div class="input-group">
            	  			<div class="input-group-addon">文章标题</div>
						    <input type="text" class="form-control" id="stitleId" name="stitle" value="${articleVo.stitle}" placeholder="请输入文章标题">
					    </div>
  					</div>
					 <div class="form-group">
            	  	  <div class="input-group">
            	  			<div class="input-group-addon">发布时间</div>
            	  			 <div class="group">
            	  			 	<div class="col-sm-6" style="margin-left:-15px">
            	  			 		<input type="text" class="form-control" id="startDateId" name="startDate" value="<f:formatDate value="${articleVo.startDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="请输发布开始时间">
            	  			 	</div>
            	  			 	<div class="col-sm-6">
            	  			 	 <input type="text" class="form-control" id="endDateId" name="endDate" value="<f:formatDate value="${articleVo.endDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="请输发布结束时间">
            	  			 	</div>
            	  			 
            	  			 </div>
						    
						   
					    </div>
  					</div>
  					<div class="form-group">
            	  	  <div class="input-group">
            	  			<div class="input-group-addon">状态</div>
						   <select id="istatusId" name="istatus" class="form-control" value="${articleVo.istatus }">
						            <option value="">全部</option>
									<c:choose>
										<c:when test="${articleVo.istatus eq 20}">
											 <option value=20 selected>已发布</option>
										</c:when>
										<c:otherwise>
										 <option value=20>已发布</option>
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${articleVo.istatus eq 10}">
											 <option value=10 selected>待发布</option>
										</c:when>
										<c:otherwise>
										 <option value=10>待发布</option>
										</c:otherwise>
									</c:choose>
								</select>
					    </div>
  					</div>
  					</div>
  					<div class="row" style="margin-top:20px;">
  					<div class="form-group">
            	  	  <div class="input-group">
            	  			<div class="input-group-addon">新闻栏目</div>
						  	  <hurbao:select type="col" cssClass="form-control" id="snavigationIdInput" name="snavigationId" objs="${navs}" listKey="id" listValue="sname" entire="true" value="${articleVo.snavigationId}"></hurbao:select>
					    </div>
  					</div>
  					<div class="form-group">
            	  	  <div class="input-group">
            	  			<div class="input-group-addon">是否推荐</div>
						  	  <hurbao:select type="list" cssClass="form-control" id="iisRecommendId" name="iisRecommend"  value="${articleVo.iisRecommend }" entire="true" list="{0:否,1:是}"></hurbao:select>
					    </div>
  					</div>
  					<div class="form-group">
            	  	  <div class="input-group">
            	  			<div class="input-group-addon">是否置顶</div>
						  	  <hurbao:select type="list" cssClass="form-control" id="iistopicId" name="iistopic"  value="${articleVo.iistopic }" entire="true" list="{0:否,1:是}"></hurbao:select>
					    </div>
  					</div>
					  <button type="button" class="btn btn-success" onclick="query();">查询</button>
					  <button type="button" class="btn btn-default" onclick="doclear();">清空</button>
					   <shiro:hasPermission name="edit_art">
					  <button type="button" class="btn btn-primary" onclick="javascript:window.location.href='${ctx}/mgr/article/edit'">新增</button>
            	  	  </shiro:hasPermission>
            	  </div>
            	  </div>
            	  
            </div>
			<div class="row">
				<table class="table table-bordered table-striped table-condensed">
	  				 <thead>
                           <tr>
                               <th>文章标题</th>
                               <th>栏目名称</th>
                               <th>首页图片</th>
							   <th>列表图片</th>
                               <th>开始时间</th>
                               <th>发布作者</th>
                               <th>排序号</th>
                               <th>结束时间</th>
                               <th>是否推荐</th>
                               <th>是否紧急公告</th>
                               <th>是否置顶</th>
                               <th>状态</th>
                               <th>添加日期</th>
                               <th>添加人</th>
                               <th>操作</th>
                           </tr>
                       </thead>
                      <tbody>
                      	  <c:if test="${not empty page.results}">
                      	  	<c:forEach items="${page.results}" var="op">
                      	  		 <c:choose>
	                               			<c:when test="${op.istatus eq 20 }">
	                               			<tr>
	                               			</c:when>
	                               			<c:otherwise>
	                               				<tr class="warning">
	                               			</c:otherwise>
	                               		
	                               		</c:choose>
	                               <td>${op.stitle}</td>
	                               <td>${op.snavigationName}</td>
									<td><c:if test="${not empty op.stitleImageIndex}"><a class="tip" href="${op.stitleImageIndex}" target="_blank">查看</a></c:if>  </td>
									<td><c:if test="${not empty op.stitleImage}"><a class="tip" href="${op.stitleImage}" target="_blank">查看</a></c:if>  </td>
	                               <td><f:formatDate value="${op.tpublishDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	                               <td>${op.sauthor}</td>
	                               <td>${op.isort}</td>
	                               <td><f:formatDate value="${op.tvalidDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	                               <td>
	                               		<c:choose>
	                               			<c:when test="${op.iisRecommend eq 1 }">
	                               				是
	                               			</c:when>
	                               			<c:otherwise>
	                               				否
	                               			</c:otherwise>
	                               		
	                               		</c:choose>
	                               </td>
	                                <td>
	                                	<c:choose>
	                               			<c:when test="${op.iisUrgentNotice eq 1 }">
	                               				是
	                               			</c:when>
	                               			<c:otherwise>
	                               				否
	                               			</c:otherwise>
	                               		
	                               		</c:choose>
	                                </td>
	                                <td>
	                                	<c:choose>
	                               			<c:when test="${op.iistopic eq 1 }">
	                               				是
	                               			</c:when>
	                               			<c:otherwise>
	                               				否
	                               			</c:otherwise>
	                               		
	                               		</c:choose>
	                                </td>
	                                <td>
	                                <c:choose>
	                               			<c:when test="${op.istatus eq 20 }">
	                               				已发布
	                               			</c:when>
	                               			<c:otherwise>
	                               				待发布
	                               			</c:otherwise>
	                               		
	                               		</c:choose>
	                                </td>
	                                <td><f:formatDate value="${op.taddTime}" pattern="yyyy-MM-dd"/></td>
	                                <td>${op.saddUser}</td>
	                             
	                              
	                               <td>

										   <a href="${ctx}/mgr/article/preview?id=${op.id}" target="_blank">预览</a>&nbsp;&nbsp;



	                               <shiro:hasPermission name="edit_art">
	                               	<a href="${ctx}/mgr/article/edit?id=${op.id}">编辑</a> &nbsp;&nbsp;
	                               	</shiro:hasPermission>
	                               	<shiro:hasPermission name="del_art">
	                               	<a href="javascript:void(0);" onclick="del('${op.id}')">删除</a>  &nbsp;&nbsp;
	                               	</shiro:hasPermission>
	                               	<shiro:hasPermission name="publish_art">
	                               	<c:if test="${op.istatus eq 10 }">
	                               		<a href="javascript:void(0);" onclick="publish('${op.id}')">发布</a>
	                               	</c:if>



										<c:if test="${op.istatus eq 20 }">
											<a href="javascript:void(0);" onclick="repealPublish('${op.id}')">撤销发布</a>
										</c:if>


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

            window.location.href='${ctx}/mgr/article/delete?id='+id+'&backUri=${ctx}/mgr/article/list';



	    },
	    cancel:function(){
	        
	    },
	    buttons:{ok:'是',cancel:'否'}
	});

}

function publish(id){
	
	$.confirm({
		confirmButtonClass: 'btn-danger',
		cancelButtonClass: 'btn-info',
	    title: '提示',
	    content: '是否确认发布？',
	    confirm: function(){
	    	  window.location.href='${ctx}/mgr/article/publish?id='+id+'&backUri=${ctx}/mgr/article/list';
	    },
	    cancel:function(){
	        
	    },
	    buttons:{ok:'是',cancel:'否'}
	});

}
function repealPublish(id){

    $.confirm({
        confirmButtonClass: 'btn-danger',
        cancelButtonClass: 'btn-info',
        title: '提示',
        content: '是否确认撤销发布？',
        confirm: function(){
            window.location.href='${ctx}/mgr/article/repealPublish?id='+id+'&backUri=${ctx}/mgr/article/list';
        },
        cancel:function(){

        },
        buttons:{ok:'是',cancel:'否'}
    });

}
function doclear(){
	$('#stitleId').val('');
	$('#startDateId').val('');
	$('#endDateId').val('');
	$('#istatusId').val('');
	$('#snavigationIdInput').val('');
	$('#iisRecommendId').val('');
	$('#iistopicId').val('');
	
}

</script>
</body>

</html>
