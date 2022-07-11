<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<link href="${staticSource}/resources/layui/css/modules/layer/default/layer.css" rel="stylesheet">
<%@ include file="/common/include/taglibs.jsp" %>
<div class="row border-bottom headfix">
    <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
        <div class="notice">
            <div class="notice-right">
                欢迎您，${sessionScope.SESSION_KEY_USER.userName}-${sessionScope.SESSION_KEY_USER.realName}
                <em onclick="window.location.href='${ctx}/rec/logout'"><i class="icon"></i>退出</em>
            </div>
        </div>
    </nav>
</div>
<div class="row content-tabs index-btn">
    <div class="index-btnleft index-btnlefthover"><i></i></div>
    <div class="J_menuTabs">
        <div class="index-btnmid">
            <ul class="page-tabs-content">
                <li class="J_menuTab"><a href="javascript:;" class="active" data-id="${ctx}/rec/index">首页</a></li>
            </ul>
        </div>
    </div>
    <div class="index-btnmid-right">
        <div class="index-btnright1">
            <div class="index-btnright1" data-toggle="dropdown">关闭操作<i></i>
            </div>
            <ul role="menu" class="dropdown-menu dropdown-menu-right">
                <li class="J_tabShowActive"><a>定位当前选项卡</a></li>
                <li class="J_tabCloseAll"><a>关闭全部选项卡</a></li>
                <li class="J_tabCloseOther"><a>关闭其他选项卡</a></li>
            </ul>
        </div>
        <div class="index-btnright">
            <i></i>
        </div>
    </div>

    <%--<a href="${ctx}/label/logout" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>--%>
</div>
<script type="text/javascript" src="${staticSource}/resources/js/scroll.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/marquee.js"></script>
<c:if test="${fn:length(announcementList) > 0}">
    <script type="text/javascript">
        $("#scrollDiv").Scroll({line: 1, speed: 500, timer: 3000, up: "but_up", down: "but_down"});
    </script>
</c:if>
<script type="text/javascript">
    $(".index-nav ul li div:last").css("border", "none");
    /*$(".index-nav ul li").click(function () {
        $(this).addClass("hover");
        $(this).siblings().removeClass("hover");
    });*/
    $(window).load(function () {
        //var winWidth = document.documentElement.clientWidth;
        //var nuvleft=$(".slimScrollDiv").width();
        //$(".wrapper-pisition").css("width",winWidth-nuvleft);
        var nuvleft = $(".navbar-default").width();
        $(".wrapper-pisition").css("padding-left", nuvleft);
    });
    function selectMenu(path) {
        var tempPath = path;
        var flag = false;
        if (path.indexOf("?") != -1) {
            tempPath = path.split("?")[0];
            flag = true;
        }
        tempPath = tempPath.replace(new RegExp("/", "gm"), "");
        var obj = $("#" + tempPath);
        //获取标识数据
        var dataUrl = $(obj).attr('href'),
            dataIndex = $(obj).data('index'),
            menuName = $.trim($(obj).text()),
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
                            if (flag) {
                                $(this).attr("src", path);
                            }
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
            var str = '<li class="J_menuTab"><a href="javascript:;" class="active" data-id="' + dataUrl + '">' + menuName + ' <i class="fa fa-times-circle"></i></a></li>';
            $('.J_menuTab').find("a").removeClass('active');

            // 添加选项卡对应的iframe
            var str1 = '<iframe class="J_iframe" name="iframe' + (dataIndex + 1) + '" width="100%" height="100%" src="' + path + '" frameborder="0" data-id="' + dataUrl + '" seamless></iframe>';
            $('.J_mainContent').find('iframe.J_iframe').hide().parents('.J_mainContent').append(str1);

            // 添加选项卡
            $('.J_menuTabs .page-tabs-content').append(str);
            scrollToTab($('.J_menuTab .active'));
        }
        return false;
    }

    function addFastMenu() {
        showLayerMin("添加快捷菜单", ctx + '/fastMenu/toAddFastMenu');
    }
    /**
     * 关闭并加载更新表格
     */
    function closeLayerAndRefresh(index) {
        layer.close(index);
        location.reload();
    }

    $('.marquee').marquee({
        duration: 6000,
        gap: 50,
        delayBeforeStart: 5000,
        direction: 'left',
        startVisible: true,
        duplicated: false
    });


</script>

