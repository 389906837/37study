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
                    <h4 class="page-header">编辑相册</h4>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <form id="defaultForm" class="form-horizontal" action="${ctx}/mgr/album/save?backUri=${ctx}/mgr/album/list" method="post" role="form"  enctype="multipart/form-data">
              <input type="hidden" name="id" value="${album.id}">
               <div class="panel panel-default">
	           	  <div class="panel-body">
	           	   <div class="form-group">
	           	        <div class="group">
                          <label class="col-sm-2 control-label" for="snameId">相册名称</label>
                          <div class="col-sm-4">
                              <input type="text" class="form-control" id="snameId" name="sname" value="${album.sname}" placeholder="请输入相册名称" required data-bv-trigger="blur" data-bv-notempty-message="相册名称不能为空"/>
                          </div>
                     </div>
                    <div class="group">
                          <label class="col-sm-2 control-label" for="isortId">排序号</label>
                          <div class="col-sm-4">
                             <input class="form-control" id="isortId" name="isort" value="${album.isort}" type="text" placeholder="请输入排序号" required data-bv-trigger="blur" 
                             data-bv-notempty-message="排序号不能为空"  pattern="^[0-9]*$" data-bv-regexp-message="排序号格式不正确"/>
                          </div>
                     </div>
                       
                     </div>
                   
                      <div class="form-group">
	           	        <div class="group">
                          <label class="col-sm-2 control-label" for="imgFileId">图片内容</label>
                          <div class="col-sm-4">
                              <input type="file" class="file" id="imgFileId" name="imgFile" />
                          </div>
                     </div>
                       <div class="group">
                          <label class="col-sm-2 control-label" for="isdispalyId">是否显示</label>
                          <div class="col-sm-4">
                            <hurbao:select type="list" list="{1:是,0:否}" id="isdispalyId" name="isdispaly" cssClass="form-control" value="${album.isdispaly }"></hurbao:select>
                          </div>
                         </div>
                     </div>
  					    <div class="form-group">
	           	         <div class="group">
                          <label class="col-sm-2 control-label">图片内容预览</label>
                          <div class="col-sm-4">
                          	<c:if test="${not empty album.coverimg }">
                          		<img class="tip" src="${album.coverimg}" style="width:50px;height:50px">
                          	</c:if>
                             
                          </div>
                     </div>
                     
                     </div>
  					    <div class="form-group">
	  					      <div class="group">
							    <label class="col-sm-2 control-label" for="sremarkInpt">备注</label>
							    <div class="col-sm-10">
							    <textarea class="form-control" name="remark" rows="3" id="sremarkInpt">${album.remark}</textarea>
							 </div>
	  					    </div>
  					    </div>
  					   <div class="form-group" style="margin-top:10px">
	  					   <div class="col-sm-12 text-center">
		  					   	<button type="submit" class="btn btn-success">保存</button>
		  					   	<button type="button" class="btn btn-info" onclick="window.location.href='${ctx}/mgr/album/list'">返回列表</button>
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
    });
</script>
<jsp:include page="/common/include/mgrEditBottom.jsp"></jsp:include>
</body>

</html>
