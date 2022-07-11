<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<jsp:include page="/common/include/indexFrontTop.jsp" />
<body>
<div id="wrapper">
 <!-- Navigation -->
 <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
     <div class="navbar-header">
         <span>
          <img src="${staticSource}/static/images/logoNew.png" style="float:left;padding-left:10px;"/>
          <a class="navbar-brand" href="javascript:void(0);" style="padding-left:5px;">叁拾柒仓官网后台管理系统</a>
         </span>
     </div>
     <!-- /.navbar-header -->

     <ul class="nav navbar-top-links navbar-right">
     	  
         <li class="dropdown">
             <a class="dropdown-toggle" data-toggle="dropdown" href="javascript:void(0);">
                 <span class="fa fa-user fa-fw"></span>admin
             </a>
         </li>
          <li class="dropdown">
             <a class="dropdown-toggle" data-toggle="dropdown" href="javascript:void(0);">
                 <span class="fa fa-sign-out fa-fw"></span>退出
             </a>
         </li>
         <!-- /.dropdown -->
     </ul>
     <!-- /.navbar-top-links -->
</nav>
</div>
 <!-- /#wrapper -->

    <div class="row">
        <div class="col-lg-12 text-center">
           <div class="alert alert-danger" style="height:150px;line-height:130px" role="alert">操作失败，${sRes }</div>
        </div>
    </div>


</body>

</html>