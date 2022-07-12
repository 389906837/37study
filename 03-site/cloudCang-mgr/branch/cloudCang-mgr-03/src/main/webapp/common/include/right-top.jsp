<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<link href="${staticSource}/resources/layui/css/modules/layer/default/layer.css" rel="stylesheet">
<%@ include file="/common/include/taglibs.jsp" %>
<div class="row border-bottom headfix">
    <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
        <div class="notice">
            <div style="float: left;width: 200px;">
                <a class="navbar-minimalize"   href="javascript:void(0);" style="color: #b0bfcd"><i
                        class="fa fa-bars"></i>
                </a>
                &nbsp;&nbsp;&nbsp;
                <a class="addMenu"  href="javascript:void(0);" style="color: #b0bfcd" onclick="addFastMenu();"><i
                      ></i> <spring:message code="main.config" /> <spring:message code="main.shortcut.menu" />
                </a>
            </div>
            <c:if test="${fn:length(announcementList) > 0}">
                <div class="notice-left overflow" >
                    <span style="float: left;" ><i class="icon noticeicon"></i><span><spring:message code="main.new.notification" />：</span></span>
                    <%--<div id="scrollDiv">
                        <ul>
                            <c:forEach var="item" items="${announcementList}" varStatus="L">
                                <li><a href="javascript:void(0);" style="color: #b0bfcd;">${item.scontentUrl}</a></li>
                            </c:forEach>
                        </ul>
                    </div>--%>
                    <div style="width: 505px; float:left;">
                        <div style="width: 505px; overflow:hidden">
                            <ul class="marquee">
                                <c:forEach var="item" items="${announcementList}" varStatus="L">
                                    <li><a href="javascript:void(0);" style="color: #b0bfcd;">${item.scontentUrl}</a></li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>


                </div>
            </c:if>
            <%--<div classs="notice-middle">
                <input type="submit" value="" class="icon submiticon"/>
                <input type="text" placeholder="请输入您需要查找的内容" class="notice-input"/>
            </div>--%>
            <div class="notice-right">
                <b><spring:message code="main.welcome" />，${sessionScope.SESSION_KEY_USER.userName}</b>
                <em onclick="window.location.href='${ctx}/mgr/logout'"><i class="icon"></i><spring:message code="main.logout" /></em>
            </div>
        </div>
        <c:if test="${fn:length(fastMenus) > 0}">
            <div class="index-nav">
                <ul>
                    <c:forEach items="${fastMenus}" varStatus="L" var="item">
                        <li onclick="selectMenu('${item.smenuPath}');">
                            <div><img src="${dynamicSource}${item.smenuIcon}"
                                      style="width: 26px;height: 26px;"/>&nbsp;&nbsp;${item.smenuName}</div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>
        <%--<div class="index-nav">
            <ul>
                <li class="hover">
                    <div><i class="icon index-add"></i>添加商品</div>
                </li>
                <li>
                    <div><i class="icon index-order"></i>订单列表</div>
                </li>
                <li>
                    <div><i class="icon index-user"></i>用户管理</div>
                </li>
                <li>
                    <div><i class="icon index-transaction"></i>交易管理</div>
                </li>
                <li>
                    <div><i class="icon index-advertisement"></i>广告管理</div>
                </li>
                <li>
                    <div><i class="icon index-activity"></i>活动管理</div>
                </li>
                <li>
                    <div><i class="icon index-activity2"></i>活动管理</div>
                </li>
            </ul>
        </div>--%>
    </nav>
</div>
<div class="row content-tabs index-btn" style="border-bottom:1px solid #92c449">
    <div class="index-btnleft index-btnlefthover"><i></i></div>
    <div class="J_menuTabs">
        <div class="index-btnmid">
            <ul class="page-tabs-content">
                <li class="J_menuTab"><a href="javascript:;" class="active" data-id="${ctx}/mgr/index"><spring:message code="main.index" /></a></li>
            </ul>
        </div>
    </div>
    <div class="index-btnmid-right">
        <div class="index-btnright1">
            <div class="index-btnright1" data-toggle="dropdown"><spring:message code="main.close.operation" /><i></i>
            </div>
            <ul role="menu" class="dropdown-menu dropdown-menu-right">
                <li class="J_tabShowActive"><a><spring:message code="main.current.tab" /></a></li>
                <li class="J_tabCloseAll"><a><spring:message code="main.close.all.tab" /></a></li>
                <li class="J_tabCloseOther"><a><spring:message code="main.close.other.tab" /></a></li>
            </ul>
        </div>
        <div class="index-btnright">
            <i></i>
        </div>
    </div>

    <%--<a href="${ctx}/mgr/logout" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>--%>
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
        showLayerMin("<spring:message code='main.add' /> <spring:message code='main.shortcut.menu' />", ctx + '/fastMenu/toAddFastMenu');
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

