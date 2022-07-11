<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<jsp:include page="/common/include/mgrHeader.jsp"></jsp:include>
<script src="${staticSource}/static/js/multiselect.min.js"></script>
<body>
    <div id="wrapper">
	<jsp:include page="/common/include/nav.jsp"></jsp:include>
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h4 class="page-header">分配角色</h4>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <form id="defaultForm" class="form-horizontal" action="${ctx}/mgr/operator/saveRolePurview?backUri=${ctx}/mgr/operator/list" method="post" role="form">
              <input type="hidden" name="operatorId" value="${operator.id}">
               <div class="panel panel-default">
	           	  <div class="panel-body">
	           	  <div class="form-group">
                       <div class="group">
                          <label class="col-sm-2 control-label" for="sroleNameId">用户名</label>
                          <div class="col-sm-4" style="padding-top:7px;">${operator.suserName} </div>
                     </div>
                     <div class="group">
                          <label class="col-sm-2 control-label" for="sroleNameId">姓名</label>
                          <div class="col-sm-4" style="padding-top:7px;">${operator.srealName} </div>
                     </div>
                     </div>
                      <div class="form-group">
				             <div class="row">
							    <div class="col-xs-5">
							       <label class="control-label" for="sroleNameId">未选中角色</label>
							        <select name="snotselectrole" class="js-multiselect form-control" size="8" multiple="multiple">
							        	<c:forEach items="${notHasRoles}" var="item">
							        		 <option value="${item.id }">${item.sroleName}</option>
							        	</c:forEach>
							        </select>
							    </div>
							    
							    <div class="col-xs-2">
							        <label class="control-label" for="sroleNameId">&nbsp;&nbsp;&nbsp;&nbsp;</label>
							        <button type="button" id="js_right_All_1" class="btn btn-block"><i class="glyphicon glyphicon-forward"></i></button>
							        <button type="button" id="js_right_Selected_1" class="btn btn-block"><i class="glyphicon glyphicon-chevron-right"></i></button>
							        <button type="button" id="js_left_Selected_1" class="btn btn-block"><i class="glyphicon glyphicon-chevron-left"></i></button>
							        <button type="button" id="js_left_All_1" class="btn btn-block"><i class="glyphicon glyphicon-backward"></i></button>
							    </div>
							    
							    <div class="col-xs-5">
							       <label class="control-label" for="sroleNameId">已选中角色</label>
							        <select name="sselectrole" id="js_multiselect_to_1" class="form-control" size="8" multiple="multiple">
							        	<c:forEach items="${hasRoles}" var="item">
							        		 <option value="${item.id }">${item.sroleName}</option>
							        	</c:forEach>
							        </select>
							    </div>
							</div>
                       </div>
  					
  					   <div class="form-group" style="margin-top:10px">
	  					   <div class="col-sm-12 text-center">
		  					   	<button type="submit" class="btn btn-success">保存</button>
		  					   	<button type="button" class="btn btn-info" onclick="window.location.href='${ctx}/mgr/operator/list'">返回列表</button>
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
        
        $('.js-multiselect').multiselect({
            right: '#js_multiselect_to_1',
            rightAll: '#js_right_All_1',
            rightSelected: '#js_right_Selected_1',
            leftSelected: '#js_left_Selected_1',
            leftAll: '#js_left_All_1'
        });

    });
</script>


<jsp:include page="/common/include/mgrEditBottom.jsp"></jsp:include>
</body>

</html>
