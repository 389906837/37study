<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<jsp:include page="/common/include/mgrHeader.jsp"></jsp:include>
<script src="${ctx}/static/resource/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
 <link rel="stylesheet" href="${ctx}/static/resource/kindeditor/themes/default/default.css" />
<link rel="stylesheet" href="${ctx}/static/resource/kindeditor/plugins/code/prettify.css" />
<script charset="utf-8" src="${ctx}/static/resource/kindeditor/kindeditor.js"></script>
<script charset="utf-8" src="${ctx}/static/resource/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="${ctx}/static/resource/kindeditor/plugins/code/prettify.js"></script>
<%
request.setCharacterEncoding("UTF-8");
String htmlData = request.getAttribute("scontent") != null ? request.getAttribute("scontent").toString() : "";
%>
<body>
    <div id="wrapper">
	<jsp:include page="/common/include/nav.jsp"></jsp:include>


        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h4 class="page-header">修改测试用户信息</h4>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row --><%--${ctx}/mgr/article/save?backUri=${ctx}/mgr/article/list?snavigationId=${article.snavigationId}--%>
            <form id="defaultForm" class="form-horizontal" action="${ctx}/applicants/update?backUri=${ctx}/applicants/list" method="post" role="form"  enctype="multipart/form-data">


               <div class="panel panel-default">
	           	  <div class="panel-body">
	           	   <div class="form-group">
	           	        <div class="group">
                          <label class="col-sm-2 control-label" for="stitleInput">公司/个人名称</label>
                          <div class="col-sm-4">
                              <input type="hidden" name="_id" value="${applicant._id}">
                              <input type="text" class="form-control" id="stitleInput" name="userName" value="${applicant.userName}" placeholder="请输入公司/个人名称" required data-bv-trigger="blur" data-bv-notempty-message="公司/个人名称不能为空"/>

                          </div>
                     </div>
                       <div class="group">
                          <label class="col-sm-2 control-label" >联系人</label>
                          <div class="col-sm-4">
                              <input type="text" class="form-control" id="ssubjoinTitleIput" name="linkMan" value="${applicant.linkMan}" placeholder="请输入联系人" required data-bv-trigger="blur" data-bv-notempty-message="联系人不能为空"/>
                          </div>
                         </div>
                     </div>
                     <div class="form-group">
	           	        <div class="group">
                          <label class="col-sm-2 control-label" for="snewsAbstractInput">联系电话</label>
                          <div class="col-sm-4">
                              <input type="text" class="form-control" id="snewsAbstractInput" name="tel" value="${applicant.tel}" placeholder="请输入联系电话"  required data-bv-trigger="blur" data-bv-notempty-message="联系电话不能为空"/>
                          </div>
                     </div>
                       <div class="group">
                          <label class="col-sm-2 control-label" for="sresourceNameinput">用户需求</label>
                          <div class="col-sm-4">
                              <input type="text" class="form-control" id="sresourceNameinput" name="require" value="${applicant.require}" placeholder="请输入用户需求" required data-bv-trigger="blur" data-bv-notempty-message="用户需求不能为空"/>
                          </div>
                         </div>
                     </div>
                     <div class="form-group">
	           	        <div class="group">
                          <label class="col-sm-2 control-label" >账号状态</label>
                          <div class="col-sm-4">
<%--
                              <input type="text" class="form-control" id="sresourceUrlInput" name="status" value="${applicant.status }" placeholder="请输入账号状态" required data-bv-trigger="blur" data-bv-notempty-message="账号状态不能为空"/>
--%>
                          <hurbao:select type="list" cssClass="form-control" name="status"  value="${applicant.status }" list="{未处理:未处理,已处理:已处理}"></hurbao:select>

                          </div>
                     </div>
                       <div class="group">
                          <label class="col-sm-2 control-label" for="sauthorinput">账号类别</label>
                          <div class="col-sm-4">
                              <input type="text" class="form-control" id="sauthorinput" name="accountType" value="${applicant.accountType }" placeholder="请输入账号类别"required data-bv-trigger="blur" data-bv-notempty-message="账号类别不能为空"/>
                          </div>
                         </div>
                     </div><input type="hidden"  name="operator" value="${applicant.operator }"    >
                      <div class="form-group">
                         <%-- <div class="group">
                              <label class="col-sm-2 control-label" for="isortInpt">操作人</label>
                              <div class="col-sm-4">

                              </div>
                         </div>--%>
                     <%--  <div class="group">
                          <label class="col-sm-2 control-label" for="stopicUrlinput">操作时间</label>
                          <div class="col-sm-4">
                              <input type="text" class="form-control" id="stopicUrlinput" name="stopicUrl" value="${applicant.operationTime}" placeholder="请输入操作时间"/>
                          </div>
                         </div>
                     </div>--%>
                         <div class="form-group">
                          <label class="col-sm-2 control-label">备注</label>
                          <div class="col-sm-4">
                            <%-- <input type="texta" Class="form-control" id="snavigationIdInput" name="remarks"   value="${applicant.remarks}">--%>
                              <input type="textarea" Class="form-control"  name=" remarks" id="remarks" cols="30" rows="10" value="${applicant.remarks }"></textarea>
                          </div>
                         </div>
                     </div>
                      <div class="form-group">
                         <%-- <div class="group">
                              <label class="col-sm-2 control-label" for="tpublishDateInpt">操作时间</label>
                              <div class="col-sm-4">
                                  <input type="hidden" class="form-control" id="stopicUrlinput" name="operationTime" value="<f:formatDate value="${applicant.operationTime }" pattern="yyyy-MM-dd HH:mm:ss"/>"  placeholder="请输入修改时间"  required data-bv-trigger="blur"
                                         data-bv-notempty-message="开始时间不能为空" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                              </div>
                          </div>--%>

                          <div class="group">
                              <label class="col-sm-2 control-label" for="tvalidDateInpt">修改时间</label>
                              <div class="col-sm-4">
                                  <input type="text" class="form-control" id="tvalidDateInpt" name="updateTime" value="<f:formatDate value="${applicant.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"  placeholder="请输入创建ip"  required data-bv-trigger="blur"
                                         data-bv-notempty-message="结束时间不能为空" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                              </div>
                          </div>
                      </div>
                      <div class="form-group">
                          <div class="group">
                              <label class="col-sm-2 control-label" >账号创建时间</label>
                              <div class="col-sm-4">
                                  <input type="text" class="form-control"  name="creatTime" value="<f:formatDate value="${applicant.creatTime }" pattern="yyyy-MM-dd HH:mm:ss"/>"  placeholder="请输入账号创建时间"  required data-bv-trigger="blur"
                                         data-bv-notempty-message="开始时间不能为空" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                              </div>
                          </div>


                          <div class="group">
                              <label class="col-sm-2 control-label" >账号创建ip</label>
                              <div class="col-sm-4">
                                  <input type="text" class="form-control" id="sto1" name="ip" value="${applicant.ip}" placeholder="请输入账号创建ip"/>
                              </div>
                          </div>
                      </div>
                     <%-- <div class="form-group">
	           	        <div class="group">
                          <label class="col-sm-2 control-label" for="imgFileId">标题缩略图</label>
                          <div class="col-sm-4">
                              <input type="file" class="file" id="imgFileId" name="imgFile" />
                          </div>
                     </div>
                       <div class="group">
                          <label class="col-sm-2 control-label" for="iisUrgentNoticeinput">是否紧急公告</label>
                          <div class="col-sm-4">
                             <hurbao:select type="list" cssClass="form-control" name="iisUrgentNotice" id="iisUrgentNoticeinput" value="${article.iisUrgentNotice }" list="{0:否,1:是}"></hurbao:select>
                          </div>
                         </div>
                     </div>
	          	    
  					  <div class="form-group">
	           	        <div class="group">
                          <label class="col-sm-2 control-label">缩略图预览</label>
                          <div class="col-sm-4">
                          	<c:if test="${not empty article.stitleImage }">
                          		<img class="tip"  src="${article.stitleImage}" style="width:50px;height:50px">
                          	</c:if>
                             
                          </div>
                     </div>
                       <div class="group">
                          <label class="col-sm-2 control-label" for="iisRecommendinput">是否推荐</label>
                          <div class="col-sm-4">
                             <hurbao:select type="list" cssClass="form-control" name="iisRecommend" id="iisRecommendinput" value="${article.iisRecommend }" list="{0:否,1:是}"></hurbao:select>
                          </div>
                         </div>
                     </div>
                      <div class="form-group">
                       <div class="group">
                          <label class="col-sm-2 control-label" for="iistopicinput">是否置顶</label>
                          <div class="col-sm-4">
                             <hurbao:select type="list" cssClass="form-control" name="iistopic" id="iistopicinput" value="${article.iistopic }" list="{0:否,1:是}"></hurbao:select>
                          </div>
                         </div>
                     </div>--%>
  					    <%-- <div class="form-group">
  					      <div class="group">
						    <label class="col-sm-2 control-label" for="contentInpt">备注</label>
						    <div class="col-sm-10">
						   	<textarea id="content1" name="scontent"
							cols="88" rows="5"
							style="width: 600px; height: 100px; visibility: hidden;"><%=htmlData%></textarea>
						 </div>
  					    </div>
  					    </div>--%>
  					   <div class="form-group">
	  					   <div class="col-sm-12 text-center">
		  					   	<button type="submit" class="btn btn-success">保存</button>
		  					   	<button type="button" class="btn btn-info" onclick="window.location.href='${ctx}/applicants/list'">返回列表</button>
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
	var editor1 = null;
	  if (KindEditor) {
      	editor1 = KindEditor.create('#content1', {
      		cssPath : '${ctx}/static/resource/kindeditor/plugins/code/prettify.css',
 				uploadJson : '${ctx}/mgr/article/upload?navicationId='+$("#snavigationIdInput").find("option:selected").val(),
 				width : "100%", 
 				resizeType:0,
 				height:'415px',
              allowFileManager: false,
          	formatUploadUrl:false,
              items:[
                      'fullscreen', '|','source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste',
                     'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
                     'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
                     'superscript', 'clearhtml', 'quickformat', 'selectall', '|',
                     'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
                     'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 
                     'image','flash', 'media', 'insertfile', 'table', 'hr', 'baidumap', 'pagebreak',
                     'anchor', 'link', 'unlink'
             ]
          });
      	prettyPrint();
 		}
	  
/*	function  checkSubmit(){
		$('#snavigationName').val($("#snavigationIdInput").find("option:selected").text());
		//同步数据
		editor1.sync();
		  
	  }*/
    $(document).ready(function() {
        $('#defaultForm').bootstrapValidator(
        		{group: '.group'});
    });
</script>
<jsp:include page="/common/include/mgrEditBottom.jsp"></jsp:include>
</body>
<%!
private String htmlspecialchars(String str) {
	str = str.replaceAll("&", "&amp;");
	str = str.replaceAll("<", "&lt;");
	str = str.replaceAll(">", "&gt;");
	str = str.replaceAll("\"", "&quot;");
	return str;
}
%>
</html>
