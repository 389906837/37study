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
                    <h4 class="page-header">编辑运营位</h4>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <form id="defaultForm" class="form-horizontal" action="${ctx}/mgr/ad/save?backUri=${ctx}/mgr/ad/list?sregionId=${advertis.sregionId}" method="post" role="form" onsubmit="return checkSubmit()" enctype="multipart/form-data">
              <input type="hidden" name="id" value="${advertis.id}">
                <input type="hidden" id="sregionNameId" name="sregionName" value="${advertis.sregionName}">
               <div class="panel panel-default">
	           	  <div class="panel-body">
	           	   <div class="form-group">
	           	        <div class="group">
                          <label class="col-sm-2 control-label" for="stitleId">名称</label>
                          <div class="col-sm-4">
                              <input type="text" class="form-control" id="stitleId" name="stitle" value="${advertis.stitle}" placeholder="请输入名称" required data-bv-trigger="blur" data-bv-notempty-message="名称不能为空"/>
                          </div>
                     </div>
                      <div class="group">
                          <label class="col-sm-2 control-label" for="ssourceTitleId">来源标题</label>
                          <div class="col-sm-4">
                              <input type="text" class="form-control" id="ssourceTitleId" name="ssourceTitle" value="${advertis.ssourceTitle}" placeholder="请输入来源标题" required data-bv-trigger="blur" data-bv-notempty-message="来源标题不能为空"/>
                          </div>
                     </div>
                       
                     </div>
                      <div class="form-group">
	           	        <div class="group">
                          <label class="col-sm-2 control-label" for="stitleId">链接类型</label>
                          <div class="col-sm-4">
                          	  <hurbao:select type="list" list="{1:普通超链接,2:内链活动,3:内链资讯}" name="ilinkType" cssClass="form-control"  value="${advertis.ilinkType }" exp="required data-bv-trigger=\"blur\" data-bv-notempty-message=\"链接类型不能为空\""></hurbao:select>
                          </div>
                     </div>
                      <div class="group">
                          <label class="col-sm-2 control-label" for="shrefId">超链接</label>
                          <div class="col-sm-4">
                              <input type="text" class="form-control" id="shrefId" name="shref" value="${advertis.shref}" placeholder="请输入超链接"/>
                          </div>
                     </div>
                       
                     </div>
	          	    <div class="form-group">
	           	        <div class="group">
                          <label class="col-sm-2 control-label" for="tstartDateId">开始时间</label>
                          <div class="col-sm-4">
                              <input type="text" class="form-control" id="tstartDateId" name="tstartDate" value="<f:formatDate value="${advertis.tstartDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"  placeholder="请输入开始时间"  required data-bv-trigger="blur" 
                             data-bv-notempty-message="开始时间不能为空" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                          </div>
                     </div>
                       <div class="group">
                          <label class="col-sm-2 control-label" for="tendDateId">结束时间</label>
                          <div class="col-sm-4">
                               <input type="text" class="form-control" id="tendDateId" name="tendDate" value="<f:formatDate value="${advertis.tendDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"  placeholder="请输入结束时间"  required data-bv-trigger="blur" 
                             data-bv-notempty-message="结束时间不能为空" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                          </div>
                         </div>
                     </div>
                     <div class="form-group">
	           	        <div class="group">
                          <label class="col-sm-2 control-label" for="iisDefaultId">默认</label>
                          <div class="col-sm-4">
                          	   <hurbao:select type="list" list="{0:否,1:是}" id="iisDefaultId" name="iisDefault" cssClass="form-control" value="${advertis.iisDefault }"></hurbao:select>
                          </div>
                     </div>
                     <div class="group">
                          <label class="col-sm-2 control-label" for="isortId">排序号</label>
                          <div class="col-sm-4">
                             <input class="form-control" id="isortId" name="isort" value="${advertis.isort}" type="text" placeholder="请输入排序号" required data-bv-trigger="blur" 
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
                          <label class="col-sm-2 control-label" for="iisUrgentNoticeinput">投放状态</label>
                          <div class="col-sm-4">
                            <hurbao:select type="list" list="{0:已过期,1:投放中,2:待投放,3:暂停}" name="istatus" cssClass="form-control" value="${advertis.istatus }"></hurbao:select>
                          </div>
                         </div>
                     </div>
  					    <div class="form-group">
	           	         <div class="group">
                          <label class="col-sm-2 control-label">图片内容预览</label>
                          <div class="col-sm-4">
                          	<c:if test="${not empty advertis.scontentUrl }">
                          		<img class="tip"  src="${advertis.scontentUrl}" style="width:50px;height:50px">
                          	</c:if>
                             
                          </div>
                     </div>
                      <div class="group">
                          <label class="col-sm-2 control-label" for="sregionIdInpt">运营区域</label>
                          <div class="col-sm-4">
                             <hurbao:select type="col" cssClass="form-control" id="sregionIdInpt" name="sregionId" objs="${regions}" listKey="id" listValue="sregionName" value="${advertis.sregionId}"></hurbao:select>
                          </div>
                         </div>
                     </div>
                      <div class="form-group">
	           	        <div class="group">
                          <label class="col-sm-2 control-label" for="icoFileId">缩略图</label>
                          <div class="col-sm-4">
                              <input type="file" class="file" id="icoFileId" name="icoFile" />
                          </div>
                     </div>
                       <div class="group">
                          <label class="col-sm-2 control-label">缩略图预览</label>
                          <div class="col-sm-4">
                          	<c:if test="${not empty advertis.sicoUrl }">
                          		<img class="tip" src="${advertis.sicoUrl}" style="width:50px;height:50px">
                          	</c:if>
                             
                          </div>
                         </div>
                     </div>
  					    <div class="form-group">
	  					      <div class="group">
							    <label class="col-sm-2 control-label" for="sremarkInpt">备注</label>
							    <div class="col-sm-10">
							    <textarea class="form-control" name="sremark" rows="3" id="sremarkInpt">${advertis.sremark}</textarea>
							 </div>
	  					    </div>
  					    </div>
  					   <div class="form-group" style="margin-top:10px">
	  					   <div class="col-sm-12 text-center">
		  					   	<button type="submit" class="btn btn-success">保存</button>
		  					   	<button type="button" class="btn btn-info" onclick="window.location.href='${ctx}/mgr/ad/list?sregionId=${advertis.sregionId}'">返回列表</button>
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
function  checkSubmit(){
	$('#sregionNameId').val($("#sregionIdInpt").find("option:selected").text());
		  
  }
    $(document).ready(function() {
        $('#defaultForm').bootstrapValidator(
        		{group: '.group'});
    });
</script>
<jsp:include page="/common/include/mgrEditBottom.jsp"></jsp:include>
</body>

</html>
