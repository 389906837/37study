<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>后台首页报表</title>
<link href="${staticSource}/resources/layui/css/layui.css?135" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?1.2" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${staticSource}/resources/css/comm.css?3"/>
<link rel="stylesheet" type="text/css" href="${staticSource}/resources/css/index.css?1.1"/>
<!--Bootstrap双日历 -->
<link rel="stylesheet" type="text/css" href="${staticSource}/resources/css/daterangepicker.css"/>
</head>

<body>
<%--<div class="ibox-content">
    首页
</div>--%>
<div class="content">
    <shiro:hasPermission name="INDEX_STATIC_DATA">
        <div class="index-fourul">
            <ul>
                <li>
                    <div class="index-fourulblock">
                        <i class="icon index-dingd"></i>
                        <p>今日订单总数</p>
                        <h2>${index['indexCacheDataVo'].tdOrderTotal}</h2>
                    </div>
                </li>
                <li>
                    <div class="index-fourulblock">
                        <i class="icon index-xiaos"></i>
                        <p>今日销售总额</p>
                        <h2>￥${index['indexCacheDataVo'].tdSaleTotal}</h2>
                    </div>
                </li>
                <li>
                    <div class="index-fourulblock">
                        <i class="icon index-xiaos1"></i>
                        <p>近7天销售总额</p>
                        <h2>￥${index['indexCacheDataVo'].sevenDSaleTotal}</h2>
                    </div>
                </li>
                <li>
                    <div class="index-fourulblock">
                        <i class="icon index-zuot"></i>
                        <p>昨日订单总数</p>
                        <h2>${index['indexCacheDataVo'].ydOrderTotal}</h2>
                    </div>
                </li>
                <li>
                    <div class="index-fourulblock">
                        <i class="icon index-xiaos2"></i>
                        <p>昨日销售总额</p>
                        <h2>￥${index['indexCacheDataVo'].ydSaleTotal}</h2>
                    </div>
                </li>
            </ul>
        </div>
    </shiro:hasPermission>
    <div class="index-equipment">
        <div class="index-equipment-left">
            <div class="overview">
                <shiro:hasPermission name="INDEX_COMMODITY_OVERVIEW">

                    <div class="overview-left">
                        <div class="overview-title">
                            商品总览
                        </div>
                        <ul>
                            <li>
                                <h2>${index['indexCommodityVo'].upShelfCommodity}</h2>
                                <p>已上架</p>
                            </li>
                            <li>
                                <h2>${index['indexCommodityVo'].downShelfCommodity}</h2>
                                <p>已下架</p>
                            </li>
                            <li>
                                <h2>${index['indexCommodityVo'].commodityStockWarn}</h2>
                                <p>库存预警</p>
                            </li>
                            <li>
                                <h2>${index['indexCommodityVo'].commodityExpireWarn}</h2>
                                <p>过期预警</p>
                            </li>
                        </ul>
                    </div>
                </shiro:hasPermission>
                <shiro:hasPermission name="INDEX_OPERATOR_OVERVIEW">
                    <div class="overview-right">
                        <div class="overview-title" style="background:#f1da71;">
                            用户总览
                        </div>
                        <ul>
                            <li>
                                <h2>${index['indexMemberVo'].tdAdd}</h2>
                                <p>今日新增</p>
                            </li>
                            <li>
                                <h2>${index['indexMemberVo'].ydAdd}</h2>
                                <p>昨日新增</p>
                            </li>
                            <li>
                                <h2>${index['indexMemberVo'].mAdd}</h2>
                                <p>本月新增</p>
                            </li>
                            <li>
                                <h2>${index['indexMemberVo'].memberTotal}</h2>
                                <p>会员总数</p>
                            </li>
                        </ul>
                    </div>
                </shiro:hasPermission>
            </div>
            <shiro:hasPermission name="INDEX_ORDER_ECHARTS">
                <div class="index-echarts">
                    <div class="index-echarts-left">
                        <div class="index-echarts-title">
                            订单统计
                            <div>
                                <button class="layui-btn layui-btn-primary layui-btn-radius ddtj-btn"
                                        onclick="orderBtn('today',false,'','');">
                                    今日
                                </button>
                                <button class="layui-btn layui-btn-primary layui-btn-radius ddtj-btn"
                                        onclick="orderBtn('thisWeek',false,'','');"
                                >本周
                                </button>
                                <button class="layui-btn layui-btn-primary layui-btn-radius ddtj-btn active"
                                        onclick="orderBtn('lastSevenDay',false,'','');"
                                        id="initOrderStatistics">
                                    近七天
                                </button>
                                <button class="layui-btn layui-btn-primary layui-btn-radius ddtj-btn"
                                        onclick="orderBtn('thisMonth',false,'','');">本月
                                </button>
                                <button class="layui-btn layui-btn-primary layui-btn-radius ddtj-btn"
                                        onclick="orderBtn('nearlyThirtyDays',false,'','');">
                                    近30天
                                </button>
                                <div style="display:inline-block" class="orderClass">
                                    <input type="text" id="orderEchartDatetime" class="form-control"
                                           name="orderEchartDatetime">
                                    <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                                </div>
                            </div>
                        </div>
                        <div id="orderEchart" style="width:100%; height:230px;">
                        </div>

                    </div>
                    <div class="index-echarts-right">
                        <p>本月订单总数：<em>${index['orderStatisticsVo'].thisMonthOrderNum}</em></p>
                        <c:if test="${0 < index['orderStatisticsVo'].thisMonthRateOfChange['plus']}">
                            <p class="f15-icon">
                                <span>${index['orderStatisticsVo'].thisMonthRateOfChange['result']} </span>同比上月
                            </p>
                        </c:if>
                        <c:if test="${0 > index['orderStatisticsVo'].thisMonthRateOfChange['plus']}">
                            <p class="f15-icon  f15-xia">
                                <span>${index['orderStatisticsVo'].thisMonthRateOfChange['result']} </span>同比上月</p>
                        </c:if>
                        <c:if test="${0 == index['orderStatisticsVo'].thisMonthRateOfChange['plus']}">
                            <p class="f15-icon f15-chiping">
                                <span>持平 </span>同比上月
                            </p>
                        </c:if>
                        <p style="padding-top:45px;">本周订单总数：<em>${index['orderStatisticsVo'].thisWeekOrderNum}</em></p>

                        <c:if test="${0 < index['orderStatisticsVo'].thisWeekRateOfChange['plus']}">
                            <p class="f15-icon">
                                <span>${index['orderStatisticsVo'].thisWeekRateOfChange['result']} </span>同比上周
                            </p>
                        </c:if>
                        <c:if test="${0 > index['orderStatisticsVo'].thisWeekRateOfChange['plus']}">
                            <p class="f15-icon f15-xia">
                                <span>${index['orderStatisticsVo'].thisWeekRateOfChange['result']} </span>同比上周</p>
                        </c:if>
                        <c:if test="${0 == index['orderStatisticsVo'].thisWeekRateOfChange['plus']}">
                            <p class="f15-icon f15-chiping">
                                <span>持平 </span>同比上周
                            </p>
                        </c:if>
                    </div>
                </div>
            </shiro:hasPermission>
            <shiro:hasPermission name="INDEX_SALES_ECHARTS">
                <div class="index-echarts">
                    <div class="index-echarts-left">
                        <div class="index-echarts-title">
                            销售统计
                            <div>
                                <button class="layui-btn layui-btn-primary layui-btn-radius xstj-btn"
                                        onclick="salesBtn('today',false,'','');">
                                    今日
                                </button>
                                <button class="layui-btn layui-btn-primary layui-btn-radius xstj-btn"
                                        onclick="salesBtn('thisWeek',false,'','');">本周
                                </button>
                                <button class="layui-btn layui-btn-primary layui-btn-radius xstj-btn active"
                                        onclick="salesBtn('lastSevenDay',false,'','');"
                                        id="initSalesStatistics">
                                    近七天
                                </button>
                                <button class="layui-btn layui-btn-primary layui-btn-radius xstj-btn"
                                        onclick="salesBtn('thisMonth',false,'','');">本月
                                </button>
                                <button class="layui-btn layui-btn-primary layui-btn-radius xstj-btn"
                                        onclick="salesBtn('nearlyThirtyDays',false,'','');">
                                    近30天
                                </button>
                                <div style="display:inline-block" class="salesClass">
                                    <input type="text" id="salesEchartDatetime" class="form-control"
                                           name="salesEchartDatetime">
                                    <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                                </div>
                            </div>
                        </div>
                        <div id="salesEchart" style="width:100%; height:230px;">
                        </div>

                    </div>
                    <div class="index-echarts-right">
                        <p>本月销售总额：<em>${index['salesAmountStatisticsVo'].thisMonthSalesAmount}</em></p>
                        <c:if test="${0 < index['salesAmountStatisticsVo'].thisMonthRateOfChange['plus']}">
                            <p class="f15-icon">
                                <span>${index['salesAmountStatisticsVo'].thisMonthRateOfChange['result']} </span>同比上月
                            </p>
                        </c:if>
                        <c:if test="${0 > index['salesAmountStatisticsVo'].thisMonthRateOfChange['plus']}">
                            <p class="f15-icon f15-xia">
                                <span>${index['salesAmountStatisticsVo'].thisMonthRateOfChange['result']} </span>同比上月
                            </p>
                        </c:if>
                        <c:if test="${0 == index['salesAmountStatisticsVo'].thisMonthRateOfChange['plus']}">
                            <p class="f15-icon f15-chiping">
                                <span>持平 </span>同比上月
                            </p>
                        </c:if>
                        <p style="padding-top:45px;">
                            本周销售总额：<em>${index['salesAmountStatisticsVo'].thisWeekMemberSalesAmount}</em></p>

                        <c:if test="${0 < index['salesAmountStatisticsVo'].thisWeekRateOfChange['plus']}">
                            <p class="f15-icon">
                                <span>${index['salesAmountStatisticsVo'].thisWeekRateOfChange['result']} </span>同比上周
                            </p>
                        </c:if>
                        <c:if test="${0 > index['salesAmountStatisticsVo'].thisWeekRateOfChange['plus']}">
                            <p class="f15-icon f15-xia">
                                <span>${index['salesAmountStatisticsVo'].thisWeekRateOfChange['result']} </span>同比上周</p>
                        </c:if>
                        <c:if test="${0 == index['salesAmountStatisticsVo'].thisWeekRateOfChange['plus']}">
                            <p class="f15-icon f15-chiping">
                                <span>持平 </span>同比上周
                            </p>
                        </c:if>
                    </div>
                </div>
            </shiro:hasPermission>
            <shiro:hasPermission name="INDEX_MEMBER_ECHARTS">
                <div class="index-echarts" style="margin-bottom:20px;">
                    <div class="index-echarts-left">
                        <div class="index-echarts-title">
                            会员统计
                            <div>
                                <button class="layui-btn layui-btn-primary layui-btn-radius hytj-btn"
                                        onclick="memberBtn('today',false,'','');">
                                    今日
                                </button>
                                <button class="layui-btn layui-btn-primary layui-btn-radius hytj-btn"
                                        onclick="memberBtn('thisWeek',false,'','');">本周
                                </button>
                                <button class="layui-btn layui-btn-primary layui-btn-radius hytj-btn active"
                                        onclick="memberBtn('lastSevenDay',false,'','');"
                                        id="initMemberStatistics">
                                    近七天
                                </button>
                                <button class="layui-btn layui-btn-primary layui-btn-radius hytj-btn"
                                        onclick="memberBtn('thisMonth',false,'','');">本月
                                </button>
                                <button class="layui-btn layui-btn-primary layui-btn-radius hytj-btn"
                                        onclick="memberBtn('nearlyThirtyDays',false,'','');">
                                    近30天
                                </button>
                                <div style="display:inline-block" class="memberClass">
                                    <input type="text" id="memberEchartDatetime" class="form-control"
                                           name="memberEchartDatetime">
                                    <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                                </div>
                            </div>
                        </div>

                        <div id="memberEchart" style="width:100%; height:230px;">
                        </div>

                    </div>
                    <div class="index-echarts-right">
                        <p>本月会员人数：<em>${index['memberStatisticsVo'].thisMonthMemberNum}</em></p>
                        <c:if test="${0 < index['memberStatisticsVo'].thisMonthRateOfChange['plus']}">
                            <p class="f15-icon">
                                <span>${index['memberStatisticsVo'].thisMonthRateOfChange['result']} </span>同比上月
                            </p>
                        </c:if>
                        <c:if test="${0 > index['memberStatisticsVo'].thisMonthRateOfChange['plus']}">
                            <p class="f15-icon  f15-xia">
                                <span>${index['memberStatisticsVo'].thisMonthRateOfChange['result']} </span>同比上月</p>
                        </c:if>
                        <c:if test="${0 == index['memberStatisticsVo'].thisMonthRateOfChange['plus']}">
                            <p class="f15-icon  f15-chiping">
                                <span>持平 </span>同比上月
                            </p>
                        </c:if>
                        <p>本周会员人数：<em>${index['memberStatisticsVo'].thisWeekMemberNum}</em></p>
                        <c:if test="${0 < index['memberStatisticsVo'].thisWeekRateOfChange['plus']}">
                            <p class="f15-icon ">
                                <span>${index['memberStatisticsVo'].thisWeekRateOfChange['result']} </span>同比上周
                            </p>
                        </c:if>
                        <c:if test="${0 > index['memberStatisticsVo'].thisWeekRateOfChange['plus']}">
                            <p class="f15-icon f15-xia">
                                <span>${index['memberStatisticsVo'].thisWeekRateOfChange['result']} </span>同比上周</p>
                        </c:if>
                        <c:if test="${0 == index['memberStatisticsVo'].thisWeekRateOfChange['plus']}">
                            <p class="f15-icon  f15-chiping">
                                <span>持平 </span>同比上周
                            </p>
                        </c:if>
                        </p>
                    </div>
                </div>
            </shiro:hasPermission>
        </div>
        <div class="index-equipment-right">

            <shiro:hasPermission name="INDEX_TRANSACTION_PROCESSED">
                <div class="treated">
                    <div class="treated-title">
                        待处理事物
                    </div>
                    <ul>
                        <li>待付款订单
                            <span>(<em>${index['indexWaitProcess'].pPaymentOrderNum}</em>)</span>
                        </li>
                        <li>待审核退款
                            <span>(<em>${index['indexWaitProcess'].pRefundNum}</em>)</span>
                        </li>
                        <li>待补货单
                            <span>(<em>${index['indexWaitProcess'].pReplenishmentOrderNum}</em>)</span>
                        </li>
                        <li>设备故障
                            <span>(<em>${index['indexWaitProcess'].deviceFaultNum}</em>)</span>
                        </li>
                        <li>商品异常
                            <span>(<em>${index['indexWaitProcess'].commodityErrorNum}</em>)</span>
                        </li>
                        <li>盘货异常
                            <span>(<em>${index['indexWaitProcess'].stocktakingError}</em>)</span>
                        </li>
                    </ul>
                </div>
            </shiro:hasPermission>
            <div class="ranking">
                <div class="ranking-title">
                    <shiro:hasPermission name="INDEX_COM_SALES_TOP"><h2 class="hover" id="commodityH2">
                        商品销售TOP排行</h2></shiro:hasPermission>
                    <shiro:hasPermission name="INDEX_DEV_SALES_TOP"><h2 id="deviceH2">
                        设备销售TOP排行</h2></shiro:hasPermission>
                </div>
                <shiro:hasPermission name="INDEX_COM_SALES_TOP">
                    <div id="commodityTopTen">
                        <div class="ranking-today">
                            <button class="layui-btn layui-btn-primary layui-btn-radius"
                                    onclick="commodityTopBtn('today',false,'','');">今日
                            </button>
                            <button class="layui-btn layui-btn-primary layui-btn-radius"
                                    onclick="commodityTopBtn('lastSevenDay',false,'','');"
                                    id="initCommodityTop">
                                近七天
                            </button>
                            <button class="layui-btn layui-btn-primary layui-btn-radius"
                                    onclick="commodityTopBtn('nearlyThirtyDays',false,'','');">
                                近30天
                            </button>
                            <div style="display:inline-block" class="ranking-todayinput">
                                <input type="text" id="commodityTopDatetime" class="form-control"
                                       name="commodityTopDatetime">
                                <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                            </div>
                        </div>

                        <ul class="ranking-ul">
                            <li style="width:16%">排名</li>
                            <li style="width:40%">商品名称</li>
                            <li style="width:24%">销售总额</li>
                            <li style="width:20%">数量</li>
                        </ul>
                        <div id="commodityBody">
                        </div>
                    </div>
                </shiro:hasPermission>
                <shiro:hasPermission name="INDEX_DEV_SALES_TOP">
                    <div id="deviceTopTen" style="display: none;">
                        <div class="ranking-today">
                            <button class="layui-btn layui-btn-primary layui-btn-radius"
                                    onclick="deviceTopBtn('today',false,'','');">今日
                            </button>
                            <button class="layui-btn layui-btn-primary layui-btn-radius"
                                    onclick="deviceTopBtn('lastSevenDay',false,'','');"
                                    id="initDeviceTop">
                                近七天
                            </button>
                            <button class="layui-btn layui-btn-primary layui-btn-radius"
                                    onclick="deviceTopBtn('nearlyThirtyDays',false,'','');">
                                近30天
                            </button>
                            <div style="display:inline-block" class="ranking-todayinput">
                                <input type="text" id="deviceTopDatetime" class="form-control" name="deviceTopDatetime">
                                <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                            </div>
                        </div>

                        <ul class="ranking-ul">
                            <li style="width:16%">排名</li>
                            <li style="width:32%">设备名称</li>
                            <li style="width:18%">销售总额</li>
                            <li style="width:17%">订单数</li>
                            <li style="width:17%">访客数</li>
                        </ul>
                        <div id="deviceBody">
                        </div>
                    </div>
                </shiro:hasPermission>
            </div>
        </div>
    </div>
</div>
<script id="device_top" type="text/x-jsrender">
{{for  data}}
     <ul class="ranking-ulsun">
            {{if #index ==0}}
                    <li style="width:16%"><span class="first">1</span></li>

             {{else #index ==1}}
                    <li style="width:16%"><span class="two">2</span></li>

             {{else #index ==2}}
                    <li style="width:16%"><span class="three">3</span></li>

             {{else}}
             <li style="width:16%"> {{:#getIndex() + 1}}</li>
            {{/if}}
                    <li style="width:32%" title="{{:deviceName}}">{{:deviceName}}</li>
                    <li style="width:18%">{{:deviceSaleAmountStr}}</li>
                    <li style="width:17%">{{:deviceSaleNum}}</li>
                    <li style="width:17%">{{:visitorNum}}</li>
      </ul>
{{/for}}





















</script>
<script id="commodity_top" type="text/x-jsrender">
{{for  data}}
     <ul class="ranking-ulsun">
            {{if #index ==0}}
                    <li style="width:16%"><span class="first">1</span></li>

             {{else #index ==1}}
                    <li style="width:16%"><span class="two">2</span></li>

             {{else #index ==2}}
                    <li style="width:16%"><span class="three">3</span></li>

             {{else}}
             <li style="width:16%"> {{:#getIndex() + 1}}</li>
            {{/if}}
                    <li style="width:40%" title="{{:commodityFullName}}">{{:commodityFullName}}</li>
                    <li style="width:24%">{{:commodityAmountStr}}</li>
                    <li style="width:20%">{{:commodityCount}}</li>
      </ul>
{{/for}}





















</script>
<!--bootstrap双日历插件 -->
<script type="text/javascript" src="${staticSource}/resources/js/moment.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/daterangepicker.js"></script>

<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/jsrender.min.js" type="text/javascript"></script>
<script src="${staticSource}/resources/js/jsrender.converters.js" type="text/javascript"></script>

<!--echarts-->
<script src="${staticSource}/resources/js/echarts/esl.js" type="text/javascript"></script>
<script src="${staticSource}/resources/js/echarts/echarts.js" type="text/javascript"></script>
<script src="${staticSource}/resources/js/WapCharts.js" type="text/javascript"></script>
<script src="${staticSource}/resources/js/MyEcharts.js?2" type="text/javascript"></script>
<script src="${staticSource}/resources/js/base.js" type="text/javascript"></script>

<script type="text/javascript">
    function memberBtn(condition, flag, start, end) {
        $.ajax({
                type: "POST",
                url: ctx + "/mgr/memberStatistics",
                data: 'start=' + start + '&end=' + end + '&isSearch=' + flag + '&condition=' + condition,
                async: true,
                dataType: "json",
                success: function (resp) {
                    var seriesTemp = {
                        symbol: 'circle',
                        itemStyle: {
                            normal: {
                                color: '#68b6de',
                                lineStyle: {color: '#68b6de'},
                                areaStyle: {color: '#87c4e4', type: 'default'}
                            }
                        },
                        center: ['1%', '0%'],
                        smooth: true
                    };
                    DrawLines(resp, "memberEchart", seriesTemp);
                }
            }
        );
    }
    function salesBtn(condition, flag, start, end) {
        $.ajax({
                type: "POST",
                url: ctx + "/mgr/salesAmountStatistics",
                data: 'start=' + start + '&end=' + end + '&isSearch=' + flag + '&condition=' + condition,
                async: true,
                dataType: "json",
                success: function (resp) {
                    var seriesTemp = {
                        symbol: 'circle',
                        itemStyle: {
                            normal: {
                                color: '#68b6de',
                                lineStyle: {color: '#68b6de'},
                                areaStyle: {color: '#87c4e4', type: 'default'}
                            }
                        },
                        center: ['1%', '0%'],
                        smooth: true
                    };
                    DrawLines(resp, "salesEchart", seriesTemp);
                }
            }
        );
    }
    function orderBtn(condition, flag, start, end) {
        $.ajax({
                type: "POST",
                url: ctx + "/mgr/orderStatistics",
                data: 'start=' + start + '&end=' + end + '&isSearch=' + flag + '&condition=' + condition,
                async: true,
                dataType: "json",
                success: function (resp) {
                    var seriesTemp = {
                        symbol: 'emptyCircle',
                        itemStyle: {
                            normal: {
                                color: '#43ccce',
                                lineStyle: {color: '#43ccce'},
                                areaStyle: {color: '#93e0e1', type: 'default'}
                            }
                        },
                        center: ['1%', '0%'],
                        smooth: true
                    };
                    DrawLines(resp, "orderEchart", seriesTemp);
                }
            }
        );
    }

    function commodityTopBtn(condition, isSearch, start, end) {
        $.ajax({
            type: "POST",
            url: ctx + "/mgr/commoditySaleRanking",
            data: 'start=' + start + '&end=' + end + '&isSearch=' + isSearch + '&condition=' + condition,
            async: true,
            dataType: "json",
            success: function (resp) {
                var html = $("#commodity_top").render(resp);
                $("#commodityBody").html(html);
            }
        });
    }
    function deviceTopBtn(condition, isSearch, start, end) {
        $.ajax({
            type: "POST",
            url: ctx + "/mgr/deviceSaleRanking",
            data: 'start=' + start + '&end=' + end + '&isSearch=' + isSearch + '&condition=' + condition,
            async: true,
            dataType: "json",
            success: function (resp) {
                var html = $("#device_top").render(resp);
                $("#deviceBody").html(html);
            }
        });
    }
    $(document).ready(function () {
        <!--     销售排行榜    -->
        $(".ranking-title h2").click(function () {
            if (!$(this).hasClass("hover")) {
                $(this).addClass("hover");
                $(this).siblings().removeClass("hover");
                if ($(this).attr("id") == "commodityH2") {
                    $("#commodityTopTen").show();
                    $("#deviceTopTen").hide();
                } else {
                    if ($(this).attr("id") == "deviceH2") {
                        $("#deviceTopTen").show();
                        $("#commodityTopTen").hide();
                    }
                }
            }
        });

        $(".ranking-today button").click(function () {
            $(this).addClass("hover");
            $(this).siblings().removeClass("hover");
        });
        $('#initOrderStatistics').trigger("click");
        $('#initSalesStatistics').trigger("click");
        $('#initMemberStatistics').trigger("click");
        $('#initCommodityTop').trigger("click");
        $('#initDeviceTop').trigger("click");

        $('.memberClass i').click(function () {
            $(this).parent().find('input').click();
        });
        $('.orderClass i').click(function () {
            $(this).parent().find('input').click();
        });
        $('.salesClass i').click(function () {
            $(this).parent().find('input').click();
        });
        $('.ranking-todayinput i').click(function () {
            $(this).parent().find('input').click();
        });
        createDatePicker($('input[name=memberEchartDatetime]'), memberBtn);
        createDatePicker($('input[name=orderEchartDatetime]'), orderBtn);
        createDatePicker($('input[name=salesEchartDatetime]'), salesBtn);
        createDatePicker($('input[name=deviceTopDatetime]'), deviceTopBtn);
        createDatePicker($('input[name=commodityTopDatetime]'), commodityTopBtn);

        $('.ddtj-btn').on('click', function () {
            $('.ddtj-btn').removeClass('active');
            $(this).addClass('active');
        })
        $('.xstj-btn').on('click', function () {
            $('.xstj-btn').removeClass('active');
            $(this).addClass('active');
        })
        $('.hytj-btn').on('click', function () {
            $('.hytj-btn').removeClass('active');
            $(this).addClass('active');
        })

    });

</script>
</body>
</html>