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
                    <h4 class="page-header">广告位管理</h4>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <form action="${ctx}/mgr/ad/list" method="post" class="form-inline" role="form">
               <div class="panel panel-default">
                       <div class="panel-heading">
                                                                                 查询条件
                       </div>
            	  <div class="panel-body">
            	   <div class="row">
            	  	<div class="form-group">
            	  	  <div class="input-group">
            	  			<div class="input-group-addon">标题</div>
						    <input type="text" class="form-control" id="stitleId" name="stitle" value="${advertisVo.stitle}" placeholder="请输入标题">
					    </div>
  					</div>
					 <div class="form-group">
            	  	  <div class="input-group">
            	  			<div class="input-group-addon">结束时间</div>
            	  			 <div class="group">
            	  			 	<div class="col-sm-6" style="margin-left:-15px">
            	  			 		<input type="text" class="form-control" id="startDateId" name="startDate" value="<f:formatDate value="${advertisVo.startDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="请输开始时间">
            	  			 	</div>
            	  			 	<div class="col-sm-6">
            	  			 	 <input type="text" class="form-control" id="endDateId" name="endDate" value="<f:formatDate value="${advertisVo.endDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="请输结束时间">
            	  			 	</div>
            	  			 
            	  			 </div>
						    
						   
					    </div>
  					</div>
  					<div class="form-group">
            	  	  <div class="input-group">
            	  			<div class="input-group-addon">状态</div>
						  	 <hurbao:select type="list" list="{0:已过期,1:投放中,2:待投放,3:暂停}" id="istatusId" name="istatus" cssClass="form-control" entire="true" value="${advertisVo.istatus }"></hurbao:select>
					    </div>
  					</div>
  					</div>
  					<div class="row" style="margin-top:20px;">
  					<div class="form-group">
            	  	  <div class="input-group">
            	  			<div class="input-group-addon">链接类型</div>
						  	 <hurbao:select type="list" list="{1:普通超链接,2:内链活动,3:内链资讯}" id="ilinkTypeId" name="ilinkType" cssClass="form-control"  entire="true" value="${advertisVo.ilinkType }"></hurbao:select>
					    </div>
  					</div>
  					<div class="form-group">
            	  	  <div class="input-group">
            	  			<div class="input-group-addon">默认</div>
						  	 <hurbao:select type="list" list="{0:否,1:是}" id="iisDefaultId" name="iisDefault" cssClass="form-control"  entire="true" value="${advertisVo.iisDefault }"></hurbao:select>
					    </div>
  					</div>
  					<div class="form-group">
            	  	  <div class="input-group">
            	  			<div class="input-group-addon">运营区域</div>
						  	  <hurbao:select type="col" cssClass="form-control" id="sregionIdInpt" name="sregionId" objs="${regions}" listKey="id" listValue="sregionName" entire="true" value="${advertisVo.sregionId}"></hurbao:select>
					    </div>
  					</div>
					  <button type="button" class="btn btn-success" onclick="query();">查询</button>
					  <button type="button" class="btn btn-default" onclick="doclear();">清空</button>
					  <shiro:hasPermission name="edit_ad">
					  <button type="button" class="btn btn-primary" onclick="javascript:window.location.href='${ctx}/mgr/ad/edit'">新增</button>
					  </shiro:hasPermission>
            	  </div>
            	  </div>
            	  
            </div>
			<div class="row">
				<table class="table table-bordered table-striped table-condensed">
	  				 <thead>
                           <tr>
                               <th>标题</th>
                               <th>状态</th>
                               <th>图片</th>
                               <th>开始日期</th>
                               <th>结束时间</th>
                               <th>链接类型</th>
                              <th>排序号</th>
                               <th>是否默认</th>
                               <th>添加日期</th>
                               <th>添加人</th>
                               <th>操作</th>
                           </tr>
                       </thead>
                      <tbody>
                      	  <c:if test="${not empty page.results}">
                      	  	<c:forEach items="${page.results}" var="op">
                      	  		<tr>
	                               <td>${op.stitle}</td>
	                               <td>
	                                <c:choose>
	                               			<c:when test="${op.istatus eq 0 }">
	                               				已过期
	                               			</c:when>
	                               			<c:when test="${op.istatus eq 1 }">
	                               				投放中
	                               			</c:when>
	                               			<c:when test="${op.istatus eq 2 }">
	                               				待投放
	                               			</c:when>
	                               			<c:otherwise>
	                               				暂停
	                               			</c:otherwise>
	                               		
	                               		</c:choose>
	                                </td>
	                               <td><c:if test="${not empty op.scontentUrl}"><a class="tip" href="${op.scontentUrl}" target="_blank">查看</a></c:if>  </td>
	                               <td><f:formatDate value="${op.tstartDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	                               <td><f:formatDate value="${op.tendDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	                               <td>
	                               		<c:choose>
	                               			<c:when test="${op.ilinkType eq 1 }">
	                               				普通超链接
	                               			</c:when>
	                               			<c:when test="${op.ilinkType eq 2 }">
	                               				内链活动
	                               			</c:when>
	                               			<c:when test="${op.ilinkType eq 3 }">
	                               				内链资讯
	                               			</c:when>
	                               	
	                               		</c:choose>
	                               </td>
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
	                                
	                                <td><f:formatDate value="${op.taddTime}" pattern="yyyy-MM-dd"/></td>
	                                <td>${op.saddUser}</td>
	                               <td>
	                                <shiro:hasPermission name="edit_ad">
	                               	<a href="${ctx}/mgr/ad/edit?id=${op.id}">编辑</a> &nbsp;&nbsp;&nbsp;&nbsp;
	                               	</shiro:hasPermission>
	                               	<shiro:hasPermission name="del_ad">
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
	    	  window.location.href='${ctx}/mgr/ad/delete?id='+id+'&backUri=${ctx}/mgr/ad/list';
	    },
	    cancel:function(){
	        
	    },
	    buttons:{ok:'是',cancel:'否'}
	});

}

function doclear(){
	$('#stitleId').val('');
	$('#endDateId').val('');
	$('#startDateId').val('');
	 $('#istatusId').val('');
	$('#ilinkTypeId').val('');
	$('#iisDefaultId').val('');
	$('#sregionIdInpt').val(''); 
	
}
</script>
</body>

</html>
