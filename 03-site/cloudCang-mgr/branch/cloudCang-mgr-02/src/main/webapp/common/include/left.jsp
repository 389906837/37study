<%@ page import="java.util.Date" %>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<nav class="navbar-default navbar-static-side" role="navigation">
    <div class="nav-close"><i class="fa fa-times-circle"></i>
    </div>
    <div class="sidebar-collapse">
        <ul class="nav" id="side-menu">
            <%@ include file="/common/include/operatorInfo.jsp" %>
        </ul>
        <div class="sidebar-copyright">
            2017-<fmt:formatDate value="<%=new Date()%>" pattern="yyyy"/> </br>
            <c:if test="${SESSION_KEY_ROOT_MERCHANT_PARENT eq 1}"><a href="http://www.37cang.com" target="_blank" style="color:#b0bfcd;">www.37cang.com</a></br></c:if>
            All Rights Reserved</br>
            ${sessionScope.SESSION_KEY_USER.smerchantName}
        </div>
    </div>
    <script src="${staticSource}/resources/js/jsrender.min.js" type="text/javascript"></script>
    <script src="${staticSource}/resources/js/jsrender.converters.js?2" type="text/javascript"></script>
    <script src="${staticSource}/resources/js/jquery.form.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function () {
            //加载菜单
            $('#myForm').ajaxSubmit({
                type: "POST",
                dataType: "json",
                async: true,
                error: function (msg) {
                    $("body").html(msg.responseText);
                },
                success: function (msg) {
                    var html = $("#menu_tmpl").render(msg);
                    $("#side-menu").append(html);
                    $('#side-menu').metisMenu();
                    fix_height();
                    //通过遍历给菜单项加上data-index属性
                    $(".J_menuItem").each(function (index) {
                        if (!$(this).attr('data-index')) {
                            $(this).attr('data-index', index);
                        }
                    });
                    $('.J_menuItem').on('click', menuItem);

                    $('#side-menu>li').click(function () {
                        if ($('body').hasClass('mini-navbar')) {
                            NavToggle();
                        }
                    });
                    $('#side-menu>li li a').click(function () {
                        if ($(window).width() < 769) {
                            NavToggle();
                        }
                    });
                }
            });
        });

        // 侧边栏高度
        function fix_height() {
            var heightWithoutNavbar = $("body > #wrapper").height() - 61;
            $(".sidebard-panel").css("min-height", heightWithoutNavbar + "px");
        }

        function menuItem() {
            // 获取标识数据
            var dataUrl = $(this).attr('href'),
                dataIndex = $(this).data('index'),
                menuName = $.trim($(this).text()),
                flag = true;
            if (dataUrl == undefined || $.trim(dataUrl).length == 0)return false;
            // 选项卡菜单已存在
            $('.J_menuTab').each(function () {
                if ($(this).find("a").data('id') == dataUrl) {
                    if (!$(this).find("a").hasClass('active')) {
                        $(this).find("a").addClass('active');
                        $(this).siblings('.J_menuTab').find("a").removeClass('active');
                        scrollToTab($(this).find("a"));
                        // 显示tab对应的内容区
                        $('.J_mainContent .J_iframe').each(function () {
                            if ($(this).data('id') == dataUrl) {
                                $(this).show().siblings('.J_iframe').hide();
                                return false;
                            }
                        });
                    }
                    flag = false;
                    return false;
                }
            });

            // 选项卡菜单不存在
            if (flag) {
                //var str = '<li><a href="javascript:;" class="active J_menuTab" data-id="${ctx}/mgr/index">首页</a></li>';
                var str = '<li class="J_menuTab"><a href="javascript:;" class="active" data-id="' + dataUrl + '">' + menuName + ' <i class="fa fa-times-circle"></i></a></li>';
                $('.J_menuTab').find("a").removeClass('active');

                // 添加选项卡对应的iframe
                var str1 = '<iframe class="J_iframe" name="iframe' + (dataIndex + 1) + '" width="100%" height="100%" src="' + dataUrl + '" frameborder="0" data-id="' + dataUrl + '" seamless></iframe>';
                $('.J_mainContent').find('iframe.J_iframe').hide().parents('.J_mainContent').append(str1);


                //显示loading提示
                /*var loading = layer.load();
                 $('.J_mainContent iframe:visible').load(function () {
                 //iframe加载完成后隐藏loading提示
                 layer.close(loading);
                 });*/
                // 添加选项卡
                $('.J_menuTabs .page-tabs-content').append(str);
                scrollToTab($('.J_menuTab .active'));
            }
            return false;
        }

        //计算元素集合的总宽度
        function calSumWidth(elements) {
            var width = 0;
            $(elements).each(function () {
                width += $(this).outerWidth(true);
            });
            return width;
        }
        //滚动到指定选项卡
        function scrollToTab(element) {
            var marginLeftVal = calSumWidth($(element).parents(".J_menuTab").prevAll()),
                marginRightVal = calSumWidth($(element).parents(".J_menuTab").nextAll());
            // 可视区域非tab宽度
            //var tabOuterWidth = calSumWidth($(".content-tabs").find().not(".J_menuTabs"));
            var tabOuterWidth = $(".index-btnleft").outerWidth(true) + $(".index-btnmid-right").outerWidth(true);
            //可视区域tab宽度
            var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
            //实际滚动宽度
            var scrollVal = 0;
            if ($(".page-tabs-content").outerWidth() < visibleWidth) {
                scrollVal = 0;
            } else if (marginRightVal <= (visibleWidth - $(element).parents(".J_menuTab").outerWidth(true) - $(element).parents(".J_menuTab").next().outerWidth(true))) {
                if ((visibleWidth - $(element).parents(".J_menuTab").next().outerWidth(true)) > marginRightVal) {
                    scrollVal = marginLeftVal;
                    var tabElement = element;
                    while ((scrollVal - $(tabElement).parents(".J_menuTab").outerWidth()) > ($(".page-tabs-content").outerWidth() - visibleWidth)) {
                        scrollVal -= $(tabElement).parents(".J_menuTab").prev().outerWidth();
                        tabElement = $(tabElement).parents(".J_menuTab").prev().find("a");
                    }
                }
                if (scrollVal > 0) {
                    $(".index-btnleft").addClass("wfindex-btnleft");
                    $(".index-btnright").removeClass("wfindex-btnright");
                } else {
                    $(".index-btnright").addClass("wfindex-btnright");
                    $(".index-btnleft").removeClass("wfindex-btnleft");
                }
            } else if (marginLeftVal > (visibleWidth - $(element).parents(".J_menuTab").outerWidth(true) - $(element).parents(".J_menuTab").prev().outerWidth(true))) {
                scrollVal = marginLeftVal - $(element).parents(".J_menuTab").prev().outerWidth(true);
                if (scrollVal > 0) {
                    $(".index-btnleft").addClass("wfindex-btnleft");
                    $(".index-btnright").removeClass("wfindex-btnright");
                } else {
                    $(".index-btnright").addClass("wfindex-btnright");
                    $(".index-btnleft").removeClass("wfindex-btnleft");
                }
            }
            $('.page-tabs-content').animate({
                marginLeft: 0 - scrollVal + 'px'
            }, "fast");
        }
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
                               {{if href != '/'}}
                                    <li>
                                        <a id="{{replaceStr:href}}" class="J_menuItem" href="{{:href}}" data-index="{{: #index}}">{{:text}}</a>
                                    </li>
                                {{/if}}
                            {{/for}}
                        </ul>
                    {{/if}}
                     {{if href != '/'}}
                        <a id="{{replaceStr:href}}" class="J_menuItem" href="{{:href}}">
                            <i class="fa {{:iconCls}}"></i>
                            <span class="nav-label">{{:text}}</span>
                        </a>
                    {{/if}}
            {{/for}}

        </script>
    </form>
</nav>

