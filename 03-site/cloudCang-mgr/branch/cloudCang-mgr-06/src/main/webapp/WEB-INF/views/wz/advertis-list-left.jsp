<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>运营区域管理</title>


<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?222" rel="stylesheet">
</head>

<body class="fixed-sidebar full-height-layout" style="overflow:hidden;">
<div id="wrapper" class="nobg p-l" style="background:#f3f3f4">
    <!--左侧导航开始-->
    <nav class="navbar-default navbar-static-side navbar-defaultbg" role="navigation" style="height: 100%;overflow: auto;margin: 20px 0px 0px 3px;padding-bottom: 20px">
        <div class="layui-form-item">
            <div class="layui-col-md12">

                <div class="wfregion">
                    <div class="layui-form-item text-center m-t ">
                        <div class="layui-input-inline">
                            <input type="text" name="smsgName" id="smsgName" value="" placeholder="编号/名称" style="width:200px;display:inline-block" class="layui-input">
                        </div>
                    </div>
                    <ul id="list">

                    </ul>
                </div>
            </div>
        </div>
    </nav>
    <!--左侧导航结束-->
    <!--右侧部分开始-->
    <div id="page-wrapper" style="padding:0 !important">
        <iframe class="J_iframe" name="iframe0" src="${ctx}/advertis/page" width="100%" height="100%" frameborder="0"
                scrolling="yes"></iframe>
    </div>
    <!--右侧部分结束-->
</div>
<script src="${staticSource}/resources/hplus/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>

<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script src="${staticSource}/resources/js/validform_min.js" type="text/javascript"></script>
<!-- 自定义js -->

<script src="${staticSource}/resources/js/jsrender.min.js" type="text/javascript"></script>
<script src="${staticSource}/resources/js/jsrender.converters.js" type="text/javascript"></script>

<script id="testTmpl" type="text/x-jsrender">


{{for data}}
        <li value={{:id}} title="{{:sregionName}}">
           ({{:scode}}){{:sregionName}}
        </li>
{{/for}}
</script>

<script type="text/javascript">
    $(function(){

        $.ajax({
            type : "POST",
            dataType : "json",
            url : ctx + "/advertis/getRerionList",
            success : function(msg) {
                if (msg.success) {
                    var html = "";
                    var  data = msg.data;
                    if (null != data && data.length > 0) {
                        //渲染数据
                        var html = $("#testTmpl").render(msg);
                        $("#list").html(html);
                        //点击事件
                        $(".wfregion ul li").click(function(){
                            var val=$(this).html();
                            $(this).addClass("hover");
                            $(this).siblings().removeClass("hover");
                            var aid = $(this).attr("value");
                            selectPur(aid);
                        });
                    }
                }
            }
        });

   });

    function selectPur(aid) {
        iframe0.window.reloadData(aid);
    }

    // 模糊查询
    $(function(){
        $('#smsgName').bind('keypress',function(event){
            if(event.keyCode == "13")
            {
                $.ajax({
                    type: "post",
                    url: ctx + "/advertis/getRerionList",
                    data: {sregionName:$("#smsgName").val(),scode:$("#smsgName").val()},
                    async: true,
                    dataType: "json",
                    success: function(msg){
                        if (msg.success) {
                            var html = "";
                            var  data = msg.data;
                            if (null != data && data.length > 0) {
                                //渲染数据
                                var html = $("#testTmpl").render(msg);
                                $("#list").html(html);
                                //点击事件
                                $(".wfregion ul li").click(function(){
                                    var val=$(this).html();
                                    $(this).addClass("hover");
                                    $(this).siblings().removeClass("hover");
                                    aid = $(this).attr("value");
                                    selectPur(aid);
                                });
                            }
                        }
                    }
                });
            }
        });
    });


</script>
</body>
</html>