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
                    <h4 class="page-header">编辑相片</h4>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <form id="defaultForm" class="form-horizontal" action="${ctx}/mgr/albumImage/save?backUri=${ctx}/mgr/albumImage/list?albumId=${albumImage.albumId}" method="post" role="form" onsubmit="return checkSubmit()" enctype="multipart/form-data">
              <input type="hidden" name="id" value="${albumImage.id}">
                <input type="hidden" id="albumNameId" name="albumName" value="${albumImage.albumName}">
               <div class="panel panel-default">
	           	  <div class="panel-body">
	           	   
                      <div class="form-group">
	           	        <div class="group">
                          <label class="col-sm-2 control-label" for="imgFileId">相片</label>
                          <div class="col-sm-4">
                              <input type="file" class="file" id="imgFileId" name="imgFile" />
                          </div>
                     </div>
                        <div class="group">
                          <label class="col-sm-2 control-label" for="albumNameInputId">相册</label>
                          <div class="col-sm-4">
                             <hurbao:select type="col" cssClass="form-control" id="albumNameInputId" name="albumId" objs="${albums}" listKey="id" listValue="sname" value="${albumImage.albumId}" exp="required data-bv-trigger=\"blur\" data-bv-notempty-message=\"相册不能为空\""></hurbao:select>
                          </div>
                         </div>
                     </div>
  					    <div class="form-group">
	           	         <div class="group">
                          <label class="col-sm-2 control-label">相片内容预览</label>
                          <div class="col-sm-4">
                          	<c:if test="${not empty albumImage.contenturl }">
                          		<img class="tip" src="${albumImage.contenturl}" style="width:50px;height:50px">
                          	</c:if>
                             
                          </div>
                        </div>
                         <div class="group">
                          <label class="col-sm-2 control-label" for="isortId">排序号</label>
                          <div class="col-sm-4">
                             <input class="form-control" id="isortId" name="isort" value="${albumImage.isort}" type="text" placeholder="请输入排序号" required data-bv-trigger="blur" 
                             data-bv-notempty-message="排序号不能为空"  pattern="^[0-9]*$" data-bv-regexp-message="排序号格式不正确"/>
                          </div>
                     </div>
                     
                     </div>
  					    <div class="form-group">
	  					      <div class="group">
							    <label class="col-sm-2 control-label" for="sremarkInpt">描述</label>
							    <div class="col-sm-10">
							    <textarea class="form-control" name="desc" rows="3" id="sremarkInpt">${albumImage.desc}</textarea>
							 </div>
	  					    </div>
  					    </div>
  					   <div class="form-group" style="margin-top:10px">
	  					   <div class="col-sm-12 text-center">
		  					   	<button type="submit" class="btn btn-success">保存</button>
		  					   	<button type="button" class="btn btn-info" onclick="window.location.href='${ctx}/mgr/albumImage/list?albumId=${albumImage.albumId}'">返回列表</button>
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
    
	function  checkSubmit(){
		$('#albumNameId').val($("#albumNameInputId").find("option:selected").text());
		  
	  }
</script>
<jsp:include page="/common/include/mgrEditBottom.jsp"></jsp:include>
</body>

</html>
