<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ include file="/common/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no"/>
    <title>我的补货单</title>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="${staticSource}/static/css/comm.css"/>
    <link rel="stylesheet" href="${staticSource}/static/css/style.css?21"/>
    <style type="text/css">
        .m-t {margin: 0rem 1.5rem 1rem 1.5rem;}
    </style>
</head>
<body>
<%@ include file="/common/include/commonHeader.jsp"%>
<div class="container">
    <div class="container-c">
        <div class="m-h-r-o">
            <div id="replenishmentBody"></div>
            <div id="foot_div"></div>
            <form id="myForm" action="${ctx}/personal/myReplenishmentList" method="post">
                <input type="hidden" name="limit" id="limit"/>
                <input type="hidden" name="start" id="start"/>
                <input type="hidden" name="page_index" id="page_index" value="0" autocomplete="off"/>
                <script id="myReplenishmentList_tmpl" type="text/x-jsrender">
                        {{for #data}}
                        <div class="m-h-r-o-main">
                                <p>补货单编号：{{:scode}}</p>
                                <p>补货日期：{{formatDate:treplenishmentTime 'yyyy年MM月dd日 HH:mm'}}</p>
                                <p>设备名称：{{:deviceName}}</p>
                                <p>设备地址：{{:sdeviceAddress}}</p>
                                <p>实际上架数量：{{:iactualShelves}} {{if iactualShelves > 0}}<a id="add-shelf-details" onclick="addShelf('{{: id}}','{{:deviceId}}',10);">上架明细</a>{{/if}}</p>
                                <p>实际下架数量：{{:iactualUnder}} {{if iactualUnder > 0}}<a id="sub-shelf-details" onclick="subShelf('{{: id}}','{{:deviceId}}',20);">下架明细</a>{{/if}}</p>
                          </div>
                        {{/for}}
                </script>
            </form>
        </div>
    </div>
</div> <!-- container -->

<!-- 本次上架明细 -->
<div class="eject"></div>
<div class="popup-bg popup-bg-add" style="width: 100%;" id="addShelfDetail">
    <div class="shelf-details md">
        <p class="kaimen-tip" style="padding-top: 1.5rem">上架明细</p>
        <div class="tab-top">
            <table class="table">
                <thead>
                <tr>
                    <th>商品编号</th>
                    <th>名称</th>
                    <th>上架数量</th>
                    <%--  <th>当前库存</th>--%>
                </tr>
                </thead>
            </table>
        </div>
        <div class="tab">
            <table class="table">
                <tbody class="table-bordered" id="addShelfBody">
                </tbody>
            </table>
        </div>
    </div>
</div>

<div class="popup-bg popup-bg-sub" style="width: 100%;" id="subShelfDetail">
    <div class="shelf-details md">
        <p class="kaimen-tip" style="padding-top: 1.5rem">下架明细</p>
        <div class="tab-top">
            <table class="table">
                <thead>
                <tr>
                    <th>商品编号</th>
                    <th>名称</th>
                    <th>下架数量</th>
                    <%--  <th>当前库存</th>--%>
                </tr>
                </thead>
            </table>
        </div>
        <div class="tab">
            <table class="table">
                <tbody class="table-bordered" id="subShelfBody">

                </tbody>
            </table>
        </div>
    </div>
</div>




<script id="addShelf_tmpl" type="text/x-jsrender">
           {{for  data}}
                 <tr>
                        <td>{{:scommodityCode}}</td>
                        <td>{{:scommodityName}}</td>
                        <td>{{:forderCount}}</td>
                      <%--  <td>{{:istock}}</td>--%>
                  </tr>
          {{/for}}

</script>
<script id="subShelf_tmpl" type="text/x-jsrender">
           {{for  data}}
                 <tr>
                        <td>{{:scommodityCode}}</td>
                        <td>{{:scommodityName}}</td>
                        <td>{{:forderCount}}</td>
                     <%--   <td>{{:istock}}</td>--%>
                  </tr>
          {{/for}}

</script>
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/comm.js?2"></script>
<script src="${staticSource}/static/js/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript" src="${staticSource}/static/js/util.js"></script>
<script src="${staticSource}/static/js/jsrender.min.js" type="text/javascript"></script>
<script src="${staticSource}/static/js/jsrender.converters.js" type="text/javascript"></script>
<script type="text/javascript" src="${staticSource}/static/js/jquery.touchSlider.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/order-pay.js?4"></script>
<script>
    function addShelf(id, deviceId, type) {
        $.ajax({
            type: "POST",
            url: "${ctx}/personal/queryDetail",
            data: {"id": id, "deviceId": deviceId, "type": type},
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    var html = $("#addShelf_tmpl").render(data);
                    $("#addShelfBody").html(html);
                    $('.eject,#addShelfDetail').css('display', 'block');
                    $('.tab').scrollTop(0);
                }
            }
        });
    }
    function subShelf(id, deviceId, type) {
        $.ajax({
            type: "POST",
            url: "${ctx}/personal/queryDetail",
            data: {"id": id, "deviceId": deviceId, "type": type},
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    var html = $("#subShelf_tmpl").render(data);
                    $("#subShelfBody").html(html);
                    $('.eject,#subShelfDetail').css('display', 'block');
                    $('.tab').scrollTop(0);
                }
            }
        });
    }


    $(function () {
        setTitle("我的补货单");
        $('.container-c').css('height', $(window.top).height());
        Util.Loading('加载中...');
        loadQueryDate($("#page_index").val(), true);

        $('.eject').on('click', function () {
            $('.eject,#addShelfDetail,#subShelfDetail').css('display', 'none');
        });
    });


    //数据请求
    var params = {};
    params.limit = "${pageSize}";
    params.start = 0;
    var count = 0;
    var totalProperty = 0;
    function loadQueryDate(page_index, async) {
        params.start = page_index * params.limit;
        page_index = parseInt(page_index) + 1;
        if (false != async) {
            async = true;
        }
        $('#myForm').ajaxSubmit({
            type: "POST",
            dataType: "json",
            async: async,
            beforeSerialize: function () {
                $('#start').val(params.start);
                $('#limit').val(params.limit);
            },
            error: function (msg) {
                $("body").html(msg.responseText);
            },
            success: function (msg) {
                //成功返回
                if (msg.success) {
                    totalProperty = msg.totalProperty;
                    count = Math.ceil(msg.totalProperty / params.limit);
                    if (true == async) {
                        var data = msg.data;
                        if ("" != data) {
                            //渲染数据
                            var html = $("#myReplenishmentList_tmpl").render(data);
                            if (data.length == 0) {
                                if (parseInt(page_index) == 1) {
                                    Util.EmptyData("暂无补货单记录!",{
                                        wrapId: "replenishmentBody"
                                    });
                                    //page_index = parseInt(page_index)-1;
                                    $("#page_index").val(page_index);
                                    return;
                                } else {
                                    Util.Loadfoot("数据加载失败，点击重新加载", {
                                        imageUrl: staticPathRoot + '/static/images/loading.gif',
                                        onClick: reloadData
                                    });
                                    page_index = parseInt(page_index) - 1;
                                }
                            }
                            if (parseInt(page_index) == 1) {
                                $("#replenishmentBody").html(html);
                            } else {
                                $("#replenishmentBody").append(html);
                            }
                            $("#page_index").val(page_index);
                            if (parseInt(page_index) >= parseInt(count)) {
                                //Util.Loadfoot("已加载全部");
                                var html = "<div class=\"footer-tips\"><span>已经到底啦</span></div>";
                                $("#foot_div").html(html);
                            } else {
                                Util.Loadfoot("点击加载更多", {onClick: reloadData})
                            }
                            return;
                        }
                    }
                    if (parseInt(page_index) == 1) {
                        Util.EmptyData("暂无补货单记录!",{
                            wrapId: "replenishmentBody"
                        });
                    } else {
                        Util.Loadfoot("数据加载失败，点击重新加载", {
                            imageUrl: staticPathRoot + '/static/images/loading.gif',
                            onClick: reloadData
                        });
                        page_index = parseInt(page_index) - 1;
                    }
                    $("#page_index").val(page_index);
                } else {
                    //加载失败
                    if (parseInt(page_index) == 1) {
                        Util.Loadfailure("数据加载失败!");
                    } else {
                        Util.Loadfoot("数据加载失败，点击重新加载", {
                            imageUrl: staticPathRoot + '/static/images/loading.gif',
                            onClick: reloadData
                        });
                        page_index = parseInt(page_index) - 1;
                    }
                    $("#page_index").val(page_index);
                }
            }
        });
        return count;
    }
    function reloadData() {
        Util.Loadfoot("加载中...", {imageUrl: staticPathRoot + '/static/images/loading.gif'})
        loadQueryDate($("#page_index").val(), true);
    }

    $(window).scroll(function () {
        if ($(window).scrollTop() == $(document).height() - $(window).height()) {
            $("#loading_foot").click();
        }
    });

    <%@ include file="/common/include/baidu.jsp"%>
</script>
</body>
</html>