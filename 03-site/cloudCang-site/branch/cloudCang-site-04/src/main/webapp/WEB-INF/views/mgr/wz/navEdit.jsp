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
                    <h4 class="page-header">编辑新闻栏目</h4>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <form id="defaultForm" class="form-horizontal" action="${ctx}/mgr/nav/save?backUri=${ctx}/mgr/nav/list" method="post" role="form">
              <input type="hidden" name="id" value="${nav.id}">
               <div class="panel panel-default">
	           	  <div class="panel-body">
	           	   <div class="form-group">
	           	        <div class="group">
                          <label class="col-sm-2 control-label" for="snameInput">栏目名称</label>
                          <div class="col-sm-4">
                              <input type="text" class="form-control" id="snameInput" name="sname" value="${nav.sname}" placeholder="请输入栏目名称" required data-bv-trigger="blur" data-bv-notempty-message="栏目名称不能为空"/>
                          </div>
                     </div>
                       <div class="group">
                          <label class="col-sm-2 control-label" for="idefaultViewinput">默认显示条</label>
                          <div class="col-sm-4">
                             <input class="form-control" id="idefaultViewinput" name="idefaultView" value="${nav.idefaultView}" type="text" placeholder="请输入默认显示条" required data-bv-trigger="blur" 
                             data-bv-notempty-message="默认显示条数不能为空"  pattern="^[0-9]*$" data-bv-regexp-message="默认显示条数格式不正确"/>
                          </div>
                         </div>
                     </div>
	          	    <div class="form-group">
  					      <div class="group">
						    <label class="col-sm-2 control-label" for="isortInpt">排序号</label>
						    <div class="col-sm-4">
						    <input type="text" class="form-control" id="isortInpt" name="isort" value="${nav.isort}"  placeholder="请输入排序号"  required data-bv-trigger="blur" 
                             data-bv-notempty-message="排序号不能为空"  pattern="^[0-9]*$" data-bv-regexp-message="排序号格式不正确">
						    </div>
						 </div>
							<div class="group">
							    <label class="col-sm-2 control-label" for="iisDefaultInpt">是否默认</label>
							     <div class="col-sm-4">
								<select id="iisDefaultInpt" name="iisDefault" class="form-control" data-bv-trigger="keyup" required data-bv-notempty-message="是否默认不能为空">
									<c:choose>
										<c:when test="${nav.iisDefault eq 1}">
											 <option value=1 selected>是</option>
										</c:when>
										<c:otherwise>
										 <option value=1>是</option>
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${nav.iisDefault eq 0}">
											 <option value=0 selected>否</option>
										</c:when>
										<c:otherwise>
										 <option value=0>否</option>
										</c:otherwise>
									</c:choose>
								</select>
								</div>
	  					   </div>
  					    </div>
  					    <div class="form-group">
  					      <div class="group">
						    <label class="col-sm-2 control-label" for="sremarkInpt">备注</label>
						    <div class="col-sm-10">
						    <textarea class="form-control" name="sremark" rows="3" id="sremarkInpt">${nav.sremark}</textarea>
						 </div>
  					    </div>
  					    </div>
  					   <div class="form-group" style="margin-top:10px">
	  					   <div class="col-sm-12 text-center">
		  					   	<button type="submit" class="btn btn-success">保存</button>
		  					   	<button type="button" class="btn btn-info" onclick="window.location.href='${ctx}/mgr/nav/list'">返回列表</button>
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
