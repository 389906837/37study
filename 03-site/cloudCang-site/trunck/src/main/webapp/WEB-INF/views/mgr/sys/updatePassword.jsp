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
                    <h4 class="page-header">修改用户密码</h4>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <form id="defaultForm" class="form-horizontal" action="${ctx}/mgr/operator/confirmUpdatePassword?backUri=${ctx}/mgr/operator/list" method="post" role="form">
              <input type="hidden" name="id" value="${opt.id}">
               <div class="panel panel-default">
	           	  <div class="panel-body">
	           	  
	           	  
  					   <div class="form-group">
	  					   <div class="group">
						    <label  class="col-sm-2 control-label" for="passwordInput">密码</label>
						      <div class="col-sm-4">
						    	<input type="password" class="form-control" id="passwordInput" name="spassword" value="" placeholder="请输入密码" data-bv-trigger="blur"
	                                   data-bv-message="密码不正确"
	                                   required data-bv-notempty-message="密码不能为空"
	                                   pattern="^(?![A-Z]+$)(?![a-z]+$)(?!\d+$)(?![\W_]+$)\S{6,20}$" data-bv-regexp-message="密码格式不正确"/>
						     </div>
						     </div>
						     
						 </div>
						 <div class="form-group">
						      <div class="group">
						      <label class="col-sm-2 control-label" for="passwordInput2">确认密码</label>
						       <div class="col-sm-4">
							    <input type="password" class="form-control" id="passwordInput2" name="spassword2" value="" placeholder="请输入确认密码" >
							   </div>
	  					     </div>
  					    </div>
  					   
  					   <div class="form-group">
	  					   <div class="col-sm-8 text-center">
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
        		{group: '.group',
        			fields:{
        				spassword2:{
        					validators:{
        						notEmpty: {
        	                        message: '确认密码不能为空'
        	                    },
        	                    regexp: {
        	                        regexp: /^(?![A-Z]+$)(?![a-z]+$)(?!\d+$)(?![\W_]+$)\S{6,20}$/,
        	                        message: '输入确认密码格式不正确或密码不匹配'
        	                    }, identical: {
        	                        field: 'spassword',
        	                        message: '密码与确认密码不匹配'
        	                    }
        					}
        					
        					
        				}	
        				
        				
        			}});
    });
</script>
<jsp:include page="/common/include/mgrEditBottom.jsp"></jsp:include>
</body>

</html>
