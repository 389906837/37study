<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html>
<jsp:include page="/common/include/mgrHeader.jsp"></jsp:include>
 <script src="${staticSource}/static/js/treeView.js"></script>
<body >
<script language="JavaScript">
	
	/* 关闭 */
	function closePage(itemStr) {
	   parent.divTreeViewRetVal(itemStr);
	}

	function closeDiv(){
		parent.doCloseTreeViewDiv();
	}
	
	function setCenter(w, h) {
		if(w)
			window.moveTo((window.screen.width-w)/2,(window.screen.height-h)/2);  
		else
			window.moveTo((window.screen.width-document.body.clientWidth)/2,(window.screen.height-document.body.clientHeight)/2); 
	}
	//setCenter(300,1000);
	
</script>
  <div class="popupBg" style="width:300px;height:400px;">
	  <div class="puTl"><div class="puTr"><div class="puTc"><span>请选择菜单</span><i onClick="closeDiv();" style="margin-left:200px"><img src="${ctx}/static/images/close.png" /></i></div></div></div>
	  	  <div class="puMl"><div class="puMr" style="height:100%;"><div class="puMc p10" style="height:100%;">
			<div class="clearfix" style="height:300px;overflow:auto;">
			<script language="javascript">
			var moduleTree = new TreeView("moduleTree");	
			moduleTree.add('_${rootInfo.id}','','${rootInfo.sname}','closePage(\"${rootInfo.id}:${rootInfo.sname}\")');
			<c:forEach items="${treeInfo}"  var="item" varStatus="st">
			
				moduleTree.add('_${item.id}','_${item.sparentId}','${item.sname}','closePage(\"${item.id}:${item.sname}\")');
			
			</c:forEach>
			document.write(moduleTree);
		</script>
			</div>
  </div>
 </div>
 </div>
 </div>
</body>
</html>
