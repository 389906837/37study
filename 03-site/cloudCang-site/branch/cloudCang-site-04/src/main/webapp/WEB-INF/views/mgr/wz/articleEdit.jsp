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
                <h4 class="page-header">编辑新闻</h4>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <form id="defaultForm" class="form-horizontal" action="${ctx}/mgr/article/save?backUri=${ctx}/mgr/article/list?snavigationId=${article.snavigationId}" method="post" role="form" onsubmit="return checkSubmit()" enctype="multipart/form-data">
            <input type="hidden" name="id" value="${article.id}">
            <input type="hidden" id="snavigationName" name="snavigationName" value="${article.snavigationName}">
            <div class="panel panel-default">
                <div class="panel-body">
                    <div class="form-group">
                        <div class="group">
                            <label class="col-sm-2 control-label" for="stitleInput">文章标题</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="stitleInput" name="stitle" value="${article.stitle}" placeholder="请输入文章标题" required data-bv-trigger="blur" data-bv-notempty-message="文章标题不能为空"/>
                            </div>
                        </div>
                        <div class="group">
                            <label class="col-sm-2 control-label" for="idefaultViewinput">副标题</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="ssubjoinTitleIput" name="ssubjoinTitle" value="${article.ssubjoinTitle}" placeholder="请输入副标题" required data-bv-trigger="blur" data-bv-notempty-message="副标题不能为空"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="group">
                            <label class="col-sm-2 control-label" for="snewsAbstractInput">摘要</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="snewsAbstractInput" name="snewsAbstract" value="${article.snewsAbstract}" placeholder="请输入文章标题"/>
                            </div>
                        </div>
                        <div class="group">
                            <label class="col-sm-2 control-label" for="sresourceNameinput">来源名称</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="sresourceNameinput" name="sresourceName" value="${article.sresourceName}" placeholder="请输入来源名称"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="group">
                            <label class="col-sm-2 control-label" for="sresourceUrlInput">来源地址</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="sresourceUrlInput" name="sresourceUrl" value="${article.sresourceUrl}" placeholder="请输入来源地址"/>
                            </div>
                        </div>
                        <div class="group">
                            <label class="col-sm-2 control-label" for="sauthorinput">发布作者</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="sauthorinput" name="sauthor" value="${article.sauthor}" placeholder="请输入发布作者"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="group">
                            <label class="col-sm-2 control-label" for="isortInpt">排序号</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="isortInpt" name="isort" value="${article.isort}"  placeholder="请输入排序号"  required data-bv-trigger="blur"
                                       data-bv-notempty-message="排序号不能为空"  pattern="^[0-9]*$" data-bv-regexp-message="排序号格式不正确">
                            </div>
                        </div>
                        <div class="group">
                            <label class="col-sm-2 control-label" for="stopicUrlinput">专题Url</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="stopicUrlinput" name="stopicUrl" value="${article.stopicUrl}" placeholder="请输入专题Url"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="group">
                            <label class="col-sm-2 control-label" for="tpublishDateInpt">开始时间</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="tpublishDateInpt" name="tpublishDate" value="<f:formatDate value="${article.tpublishDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"  placeholder="请输入开始时间"  required data-bv-trigger="blur"
                                       data-bv-notempty-message="开始时间不能为空" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                            </div>
                        </div>
                        <div class="group">
                            <label class="col-sm-2 control-label" for="tvalidDateInpt">结束时间</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="tvalidDateInpt" name="tvalidDate" value="<f:formatDate value="${article.tvalidDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"  placeholder="请输入结束时间"  required data-bv-trigger="blur"
                                       data-bv-notempty-message="结束时间不能为空" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="group">
                            <label class="col-sm-2 control-label" for="skeysInput">关键字</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="skeysInput" name="skeys" value="${article.skeys}" placeholder="请输入关键字"/>
                            </div>
                        </div>
                        <div class="group">
                            <label class="col-sm-2 control-label" for="iisUrgentNoticeinput">新闻栏目</label>
                            <div class="col-sm-4">
                                <hurbao:select type="col" cssClass="form-control" id="snavigationIdInput" name="snavigationId" objs="${navs}" listKey="id" listValue="sname" value="${article.snavigationId}" exp="required data-bv-trigger=\"blur\" data-bv-notempty-message=\"新闻栏目不能为空\""></hurbao:select>
                            </div>
                        </div>

                    </div>
                    <div class="form-group">
                        <div class="group">
                            <label class="col-sm-2 control-label" for="imgFileId">标题缩略图</label>
                            <div class="col-sm-4">
                                <input type="file" class="file" id="imgFileId" name="imgFile" />
                            </div>
                        </div>
                        <div class="group">
                            <label class="col-sm-2 control-label" for="imgFileIdIndex">标题缩略图Index</label>
                            <div class="col-sm-4">
                                <input type="file" class="file" id="imgFileIdIndex" name="imgFileIndex" />
                            </div>
                           <%-- <label class="col-sm-2 control-label" for="iisUrgentNoticeinput">是否紧急公告</label>
                            <div class="col-sm-4">
                                <hurbao:select type="list" cssClass="form-control" name="iisUrgentNotice" id="iisUrgentNoticeinput" value="${article.iisUrgentNotice }" list="{0:否,1:是}"></hurbao:select>
                            </div>--%>
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
                            <label class="col-sm-2 control-label">缩略图预览</label>
                            <div class="col-sm-4">
                                <c:if test="${not empty article.stitleImageIndex }">
                                    <img class="tip"  src="${article.stitleImageIndex}" style="width:50px;height:50px">

                                       </c:if>

                            </div>
                            <%--<label class="col-sm-2 control-label" for="iisRecommendinput">是否推荐</label>
                            <div class="col-sm-4">
                                <hurbao:select type="list" cssClass="form-control" name="iisRecommend" id="iisRecommendinput" value="${article.iisRecommend }" list="{0:否,1:是}"></hurbao:select>
                            </div>--%>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="group">
                            <label class="col-sm-2 control-label" for="iistopicinput">是否置顶</label>
                            <div class="col-sm-4">
                                <hurbao:select type="list" cssClass="form-control" name="iistopic" id="iistopicinput" value="${article.iistopic }" list="{0:否,1:是}"></hurbao:select>
                            </div>
                        </div>
                        <div class="group">
                            <label class="col-sm-2 control-label" for="iisRecommendinput">是否显示推荐</label>
                            <div class="col-sm-4">
                                <hurbao:select type="list" cssClass="form-control" name="iisRecommend" id="iisRecommendinput" value="${article.iisRecommend }" list="{0:否,1:是}"></hurbao:select>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="group">
                            <label class="col-sm-2 control-label" for="contentInpt">内容</label>
                            <div class="col-sm-10">
						   	<textarea id="content1" name="scontent"
                                      cols="88" rows="5"
                                      style="width: 600px; height: 100px; visibility: hidden;"><%=htmlData%></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12 text-center">
                            <button type="submit" class="btn btn-success">保存</button>
                            <button type="button" class="btn btn-info" onclick="window.location.href='${ctx}/mgr/article/list?snavigationId=${article.snavigationId}'">返回列表</button>
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

    function  checkSubmit(){
        $('#snavigationName').val($("#snavigationIdInput").find("option:selected").text());
        //同步数据
        editor1.sync();

    }
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
