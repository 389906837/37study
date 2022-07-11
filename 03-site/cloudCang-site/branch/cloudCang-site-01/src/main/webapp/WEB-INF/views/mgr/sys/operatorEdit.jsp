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
                    <h4 class="page-header">编辑用户</h4>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <form id="defaultForm" class="form-horizontal" action="${ctx}/mgr/operator/save?backUri=${ctx}/mgr/operator/list" method="post" role="form">
              <input type="hidden" name="id" value="${opt.id}">
               <div class="panel panel-default">
	           	  <div class="panel-body">
	           	   <div class="form-group">
	           	        <div class="group">
                          <label class="col-sm-2 control-label" for="userNameInput">用户名</label>
                          <div class="col-sm-4">
                              <input type="text" class="form-control" id="userNameInput" name="suserName" value="${opt.suserName}" placeholder="请输入用户名" />
                          </div>
                     </div>
                       <div class="group">
                          <label class="col-sm-2 control-label" for="srealNameinput">用户姓名</label>
                          <div class="col-sm-4">
                             <input class="form-control" id="srealNameinput" name="srealName" value="${opt.srealName}" type="text" placeholder="请输入用户姓名" required data-bv-trigger="blur" data-bv-notempty-message="用户姓名不能为空"/>
                          </div>
                         </div>
                     </div>
	           	  	 <c:if test="${ empty opt or empty opt.id}">
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
						      <div class="group">
						      <label class="col-sm-2 control-label" for="passwordInput2">确认密码</label>
						       <div class="col-sm-4">
							    <input type="password" class="form-control" id="passwordInput2" name="spassword2" value="" placeholder="请输入确认密码" >
							   </div>
	  					     </div>
  					    </div>
  					  </c:if>
  					   <div class="form-group">
	  					     <div class="group">
							    <label  class="col-sm-2 control-label" for="smobileInpt">手机</label>
							    <div class="col-sm-4">
							    	<input type="text" class="form-control" id="smobileInpt" name="smobile" value="${opt.smobile}" placeholder="请输入手机号"  data-bv-trigger="blur"
	                                   data-bv-message="手机号不正确"
	                                   required data-bv-notempty-message="手机号不能为空"
	                                   pattern="^(13[0-9]|15[0-9]|14[7]|18[0-9]|17[0-9])\d{8}$" data-bv-regexp-message="手机号格式不正确"/>
	  					   		 </div>
	  					     </div>
  					  		<div class="group">
	  					   		 <label class="col-sm-2 control-label" for="smailInpt">邮箱</label>
							     <div class="col-sm-4">
							    	<input type="text" class="form-control" id="smailInpt" name="smail" value="${opt.smail}" placeholder="请输入邮箱">
							     </div>
						    </div>
  					   </div>
  					  
  					    <div class="form-group">
  					      <div class="group">
						    <label class="col-sm-2 control-label" for="sphoneInpt">电话</label>
						    <div class="col-sm-4">
						    <input type="text" class="form-control" id="sphoneInpt" name="sphone" value="${opt.sphone}" placeholder="请输入电话">
						    </div>
						 </div>
							<div class="group">
							    <label class="col-sm-2 control-label" for="bisFreezeInpt">是否启用</label>
							     <div class="col-sm-4">
								<select id="bisFreezeInpt" name="bisFreeze" class="form-control" data-bv-trigger="keyup" required data-bv-notempty-message="是否启用不能为空">
									<c:choose>
										<c:when test="${opt.bisFreeze eq 1}">
											 <option value=1 selected>启用</option>
										</c:when>
										<c:otherwise>
										 <option value=1>启用</option>
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${opt.bisFreeze eq 0}">
											 <option value=0 selected>禁用</option>
										</c:when>
										<c:otherwise>
										 <option value=0>禁用</option>
										</c:otherwise>
									</c:choose>
								</select>
								</div>
	  					   </div>
  					    </div>
  					   <div class="form-group">
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
        		{group: '.group',
        			fields:{
        				suserName: {//验证input项：验证规则
        		                 message: '用户名格式不正确',
        		                
        		                 validators: {
        		                     notEmpty: {//非空验证：提示消息
        		                         message: '用户名不能为空'
        		                     },
        		                     stringLength: {
        		                         min: 4,
        		                         max: 30,
        		                         message: '用户名长度必须在4到30之间'
        		                     },
        		                     threshold:6 , //有6字符以上才发送ajax请求，（input中输入一个字符，插件会向服务器发送一次，设置限制，6字符以上才开始）
        		                     //<c:if test="${ empty opt or empty opt.id}">
        		                     remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
        		                         url: '${ctx}/mgr/operator/checkNameExists',//验证地址
        		                         message: '该用户用户已存在',//提示消息
        		                         delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
        		                         type: 'POST'//请求方式
        		                     },
        		                    // </c:if>
        		                     regexp: {
        		                         regexp: /^[a-zA-Z0-9_\.]+$/,
        		                         message: '用户名由数字字母下划线和.组成'
        		                     }
        		                 }
        		             },
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
