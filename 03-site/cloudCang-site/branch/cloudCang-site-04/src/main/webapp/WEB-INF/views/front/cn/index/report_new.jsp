<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/include/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="keywords" content="公司动态，新闻中心，37号仓动态，37号仓新闻，37号仓动态，媒体报道，媒体新闻，37号仓媒体报道，37号仓新闻报道" />
    <meta name="description" content="37号仓报道动态为用户提供37号仓资讯，37号仓平台新闻等最新消息介绍，让用户知晓37号仓的最新动态！37号仓-计算机视觉识别核心算法供应商，可广泛应用于新零售、环保、安防等领域！" />
    <title>报道动态-37号仓-计算机视觉识别核心算法供应商-江苏叁拾柒号仓智能科技有限公司</title>
    <link rel="stylesheet" href="${staticSource}/static/css/index_new.css">
    <link rel="shortcut icon" href="${staticSource}/static/favicon.ico" type="image/x-icon">
</head>
<body>
<div class="main">
    <!--头-->
    <jsp:include page="/common/include/indexFrontTop_new.jsp"/>
    <div class="headHeight"></div>
    <div class="product-headimg">
        <img src="/static/images/report-headimg.jpg">
    </div>
    <div class="report-titlte-bg">
        <div class="report-title container">
            <a id="a_companyNews" href="${ctx}/report?type=companyNews" onclick="selectMenu('companyNews');"
               <c:if test="${type eq 'companyNews'}">class="active"</c:if>>公司动态</a>
            <a id="a_mediaReport" href="${ctx}/report?type=mediaReport" onclick="selectMenu('mediaReport');"
               <c:if test="${type eq 'mediaReport'}">class="active"</c:if>>媒体报道</a>
            <a id="a_industryNews" href="${ctx}/report?type=industryNews" onclick="selectMenu('industryNews');"
               <c:if test="${type eq 'industryNews'}">class="active"</c:if>>行业新闻</a>
        </div>
    </div>
    <div class="report-container">
        <div class="listall">
            <div id="aaaa"></div>
            <div class="setPageDiv">
                <div class="Pagination" id="pagination"></div>
            </div>
            <div class="recommend-news">
                <c:if test="${not empty articles}">
                    <div class="recommend-news-title">相关推荐</div>
                    <div class="recommend-news-item">
                            <c:forEach items="${articles}" var="item">
                                <div class="recommend-news-list">
                                    <a href="${item.advertisUrl}">
                                        <img src="${item.stitleImage}">
                                        <p>${item.sketch}</p>
                                    </a>
                                </div>
                            </c:forEach>
                    </div>
                </c:if>
            </div>
        </div>
        <input type="hidden" name="type" value="${type}" id="type">
    </div>
    <!--尾-->
    <jsp:include page="/common/include/indexFrontBottom_new.jsp"/>
</div>

<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/jquery.SuperSlide.2.1.3.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/swiper.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/jquery.pagination.js"></script>
<script src="${staticSource}/static/js/jsrender.min.js" type="text/javascript"></script>
<script src="${staticSource}/static/js/jsrender.converters.js?111" type="text/javascript"></script>
<script id="device_top" type="text/x-jsrender">
   {{for  #data}}
         <div class="listeach fadeIn">
                <div class="date">
                    <h1>{{formatDate:taddTime 'dd'}}</h1>
                    <p>{{formatDate:taddTime 'yyyy/MM'}}</p>
                </div>
                <div class="pic">
                    {{if isEmpty:stitleImage}}
                        <img src="{{:stitleImage}}">
                    {{else}}
                        <img src="${staticSource}/static/images/mediareports/defaultThumbnail.jpg" />
                    {{/if}}
                </div>
                <div class="details">
                    <a href="${ctx}/news/${newType}/{{:isort}}.html"><h3>{{:stitle}}</h3></a>
                    <p>{{:snewsAbstract}}</p>
                </div>
            </div>
  {{/for}}




</script>
<script type="text/javascript">
    function selectMenu(type) {
        $("#type").val(type);
    }

    /* 报道动态 */
    $(".report-title a").click(function () {
        var ind = $(".report-title a").index(this); // 找到当前序列号
        $(this).addClass("active").siblings().removeClass("active");
        $(".listall").eq(ind).show().siblings().hide();
    });
</script>
<script>
    $(function () {
        var pageSize = 5; // 每页显示多少条记录
        var total = ${pageSize}; // 总共多少记录
        getMsg(1); // 注意参数，初始页面默认传到后台的参数，第一页是0;
        $(".Pagination").pagination(total, {
            callback: PageCallback,
            prev_text: '上一页',
            next_text: '下一页',
            first_text:"",
            last_text:"",
            items_per_page: pageSize,
            num_display_entries: 4, // 连续分页主体部分显示的分页条目数
            num_edge_entries: 1 // 两侧显示的首尾分页的条目数
        });
        //点击上一页、下一页、页码的时候触发的事件
        function PageCallback(index, jq) { // 前一个参数表示当前点击的那个分页的页数索引值，后一个参数表示装载容器。
            getMsg(index+1);
        }

        function getMsg(num) {
            var type = $("#type").val();
            $.ajax({
                url: '/getBB',
                data: {"type": type, "pageNum": num},
                type: 'POST',
                dataType: 'json',
                async: true,
                success: function (msg) {
                    var html = $("#device_top").render(msg.data.content);
                    $("#aaaa").html(html);
                }
            })
        }
    });
    <%@ include file="/common/include/baidu.jsp"%>
</script>
</body>
</html>
