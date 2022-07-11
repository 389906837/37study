<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<script src="${staticSource}/resources/js/jsrender.min.js" type="text/javascript"></script>
<script src="${staticSource}/resources/js/jsrender.converters.js"  type="text/javascript"></script>
<script  src="${staticSource}/resources/js/jquery.form.js"  type="text/javascript"></script>
<script type="text/javascript">
    $(function() {
        //加载菜单
        $('#myForm').ajaxSubmit({
            type : "POST",
            dataType : "json",
            async : true,
            error : function(msg) {
                $("body").html(msg.responseText);
            },
            success : function(msg) {
                var html = $("#menu_tmpl").render(msg);
                $("#menuList").html(html);
            }
        });

    });
</script>
<form id="myForm" action="${ctx}/operator/getMenuTree" method="post">
    <script id="menu_tmpl" type="text/x-jsrender">
        {{for #data}}
            <li>
                {{if href == '/'}}
                    <a href="#">
                        <i class="fa {{:iconCls}}"></i>
                        <span class="nav-label">{{:text}}</span>
                        <span class="fa arrow"></span>
                    </a>
                    <ul class="nav nav-second-level">
                        {{for children}}
                            <li>
                                <a class="J_menuItem" href="${ctx}/{{:href}}" data-index="{{: #index}}">{{:text}}</a>
                            </li>
                        {{/for}}
                    </ul>
                {{/if}}
                 {{if href != '/'}}
                    <a class="J_menuItem" href="${ctx}/{{:href}}">
                        <i class="fa {{:iconCls}}"></i>
                        <span class="nav-label">{{:text}}</span>
                    </a>
                {{/if}}
        {{/for}}
    </script>
</form>
<li id="menuList">

</li>


