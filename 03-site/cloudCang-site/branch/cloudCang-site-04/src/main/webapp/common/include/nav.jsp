<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
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
                        <span class="fa fa-user fa-fw"></span>${sessionScope.SESSION_KEY_USER_DETAIL.userName}
                    </a>
                </li>
                 <li class="dropdown" onclick="window.location.href='${ctx}/mgr/logout'">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="${ctx}/mgr/logout">
                        <span class="fa fa-sign-out fa-fw"></span>退出
                    </a>
                </li>
                <!-- /.dropdown -->
            </ul>
            <!-- /.navbar-top-links -->

            <div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
                    <ul class="nav" id="side-menu">
                        <li>
                            <a href="${ctx}/mgr/index"><i class="fa fa-dashboard fa-fw"></i>系统首页</a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-wrench fa-fw"></i>系统设置<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                             <shiro:hasPermission name="list_menu">
                                <li>
                                    <a href="${ctx}/mgr/menu/list">菜单设置</a>
                                </li>
                              </shiro:hasPermission>
                               <shiro:hasPermission name="list_pur">
                                <li>
                                    <a href="${ctx}/mgr/purview/list">权限设置</a>
                                </li>
                                 </shiro:hasPermission>
                            </ul>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-user fa-fw"></i>用户管理<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                               <shiro:hasPermission name="list_opt">
                                <li>
                                    <a href="${ctx}/mgr/operator/list">用户管理</a>
                                </li>
                                </shiro:hasPermission>
                                  <shiro:hasPermission name="list_role">
                                 <li>
                                    <a href="${ctx}/mgr/role/list">角色管理</a>
                                </li> 
                                </shiro:hasPermission>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-files-o fa-fw"></i>网站管理<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                              <shiro:hasPermission name="list_nav">
                                <li>
                                    <a href="${ctx}/mgr/nav/list">栏目管理</a>
                                </li>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="list_art">
                                <li>
                                    <a href="${ctx}/mgr/article/list">新闻管理</a>
                                </li>
                                </shiro:hasPermission>
                                <li>
                                    <a href="${ctx}/applicants/list">测试用户管理</a>
                                </li>
                               <%-- <shiro:hasPermission name="list_applicants">

                                </shiro:hasPermission>--%>

                                <shiro:hasPermission name="list_region">
                                <li>
                                    <a href="${ctx}/mgr/region/list">运营位管理</a>
                                </li>
                                 </shiro:hasPermission>
                                  <shiro:hasPermission name="list_ad">
                                <li>
                                    <a href="${ctx}/mgr/ad/list">广告管理</a>
                                </li>
                                 </shiro:hasPermission>
                                  <shiro:hasPermission name="list_album">
                                <li>
                                    <a href="${ctx}/mgr/album/list">相册管理</a>
                                </li>
                                 </shiro:hasPermission>
                                  <shiro:hasPermission name="list_albumImage">
                                <li>
                                    <a href="${ctx}/mgr/albumImage/list">相片管理</a>
                                </li>
                                 </shiro:hasPermission>
                                <shiro:hasPermission name="list_recommend">
                                    <li>
                                        <a href="${ctx}/mgr/recommend/list">推荐管理</a>
                                    </li>
                                </shiro:hasPermission>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                    </ul>
                </div>
                <!-- /.sidebar-collapse -->
            </div>
            <!-- /.navbar-static-side -->
        </nav>