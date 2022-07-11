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
                    <h4 class="page-header">编辑运营位</h4>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <form id="defaultForm" class="form-horizontal" action="${ctx}/mgr/region/save?backUri=${ctx}/mgr/region/list" method="post" role="form">
              <input type="hidden" name="id" value="${region.id}">
               <div class="panel panel-default">
	           	  <div class="panel-body">
	           	   <div class="form-group">
	           	        <div class="group">
                          <label class="col-sm-2 control-label" for="snameInput">名称</label>
                          <div class="col-sm-4">
                              <input type="text" class="form-control" id="snameInput" name="sregionName" value="${region.sregionName}" placeholder="请输入名称" required data-bv-trigger="blur" data-bv-notempty-message="名称不能为空"/>
                          </div>
                     </div>
                       <div class="group">
                          <label class="col-sm-2 control-label" for="icountId">数量</label>
                          <div class="col-sm-4">
                             <input class="form-control" id="icountId" name="icount" value="${region.icount}" type="text" placeholder="请输入数量" required data-bv-trigger="blur" 
                             data-bv-notempty-message="数量不能为空"  pattern="^[0-9]*$" data-bv-regexp-message="数量格式不正确"/>
                          </div>
                         </div>
                     </div>
	          	    <div class="form-group">
  					      <div class="group">
						    <label class="col-sm-2 control-label" for="iregionLengthId">长(px)</label>
						    <div class="col-sm-4">
						    <input type="text" class="form-control" id="iregionLengthId" name="iregionLength" value="${region.iregionLength}"  placeholder="请输入长"  required data-bv-trigger="blur" 
                             data-bv-notempty-message="长不能为空"  pattern="^[0-9]*$" data-bv-regexp-message="长格式不正确">
						    </div>
						 </div>
						 <div class="group">
						    <label class="col-sm-2 control-label" for="iregionWidthId">宽(px)</label>
						    <div class="col-sm-4">
						    <input type="text" class="form-control" id="iregionWidthId" name="iregionWidth" value="${region.iregionWidth}"  placeholder="请输入宽"  required data-bv-trigger="blur" 
                             data-bv-notempty-message="宽不能为空"  pattern="^[0-9]*$" data-bv-regexp-message="宽格式不正确">
						    </div>
						 </div>
							
  					    </div>
  					     <div class="form-group">
  					      <div class="group">
						    <label class="col-sm-2 control-label" for="spositionId">页面位置</label>
						    <div class="col-sm-4">
						    	<hurbao:select type="list" list="{10:顶部,20:中部,30:底部}" cssClass="form-control" name="sposition" id="spositionId" value="${region.sposition}" exp="required data-bv-trigger=\"blur\" data-bv-notempty-message=\"页面位置不能为空\""></hurbao:select>
						   	</div>
						 </div>
						
							
  					    </div>
  					    <div class="form-group">
	  					      <div class="group">
							    <label class="col-sm-2 control-label" for="sremarkInpt">备注</label>
							    <div class="col-sm-10">
							    <textarea class="form-control" name="sremark" rows="3" id="sremarkInpt">${region.sremark}</textarea>
							 </div>
	  					    </div>
  					    </div>
  					   <div class="form-group" style="margin-top:10px">
	  					   <div class="col-sm-12 text-center">
		  					   	<button type="submit" class="btn btn-success">保存</button>
		  					   	<button type="button" class="btn btn-info" onclick="window.location.href='${ctx}/mgr/region/list'">返回列表</button>
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
