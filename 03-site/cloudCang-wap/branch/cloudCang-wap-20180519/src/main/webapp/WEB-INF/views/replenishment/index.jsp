<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>${index.indexTitle}</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no" />
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="${staticSource}/static/css/comm.css" />
    <link rel="stylesheet" href="${staticSource}/static/css/style.css?1" />
</head>
<body>
    <div class="container">
        <div class="container-c">
            <%@ include file="/common/include/commonHeader.jsp"%>
            <div class="md">
                <div class="logo">
                    <img src="${staticSource}/static/images/logo.png" class="logo-pic" style="float:none;" />
                </div>
                <p class="md kaimen-tip">补货员：${srealName}</p>
            </div>
            <img class="round-head" src="${staticSource}/static/images/round-head.png" >
            <div class="kaimen-bg">
                <div class="kaimen-round md" onclick="openDoor(1);">补货开门</div>
            </div>
        </div>
        <%@ include file="/common/include/foot.jsp"%>
    </div>
    <div class="eject"></div>
    <!-- 非正常开门的情况 -->
    <div class="popup-bg">
        <div class="popup-contant md relative">
            <img class="indexclose-icon" src="${staticSource}/static/images/indexclose-icon.png">
            <img class="noopen-icon" src="${staticSource}/static/images/index-noopen-icon.png">
            <p class="popup-txt" id="alert_text"></p>
            <p class="popup-txt" style="font-size:1.4rem;padding-top:0.5rem;">如有需要，请联系客服</p>
            <a class="button-icon check-btn" href="javascript:void(0);" onclick="closeTip();" style="margin-top:2.5rem;">关闭</a>
        </div>
    </div>
    <script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="${staticSource}/static/js/comm.js"></script>
    <script type="text/javascript" src="${staticSource}/static/js/socket.io.js"></script>
    <script type="text/javascript" src="${staticSource}/static/js/soketUtil.js"></script>
    <script type="text/javascript" src="${staticSource}/static/js/util.js"></script>
    <script type="text/javascript">
        $(function (){
            openDoor(0);//系统主动开门
            $(".indexclose-icon").click(function(){
                $(".eject,.popup-bg").hide();
            });
            $('.container-c').css('height', $(window.top).height());
        });
        function closeTip() {
            $(".eject,.popup-bg").hide();
        }
        //发送开门操作
        function operOpenDoor() {
            global.reconnectFailCount = 0;
            parent.sendMsg('${openParam}');
        }
        function openDoor(operType){
            $.ajax({
               type : "post",
               data : {'operType':operType,'memberType':1},
               url : ctxRoot + "/index/isAutoOpen",
               dataType : "json",
               success: function (msg) {
                   if(msg.success) {
                       //成功返回
                       if(msg.data == '1'){
                           $(".kaimen-round").removeAttr("onclick");
                           operOpenDoor();
                       } else {
                           if(operType != 0) {
                               if (msg.msg == "1") {
                                   parent.wechatScan();
                               } else if (msg.msg == "2") {
                                   parent.alipayScan();
                               }
                           }
                       }
                   } else {
                       $("#alert_text").html(msg.msg);
                       $(".eject,.popup-bg").show();
                   }
                }
            });
        }
        function openFail(msg) {
            $(".kaimen-round").attr("onclick", "openDoor(1);");
            Util.Alert("开门失败(" + msg + ")");
        }
        <%@ include file="/common/include/baidu.jsp"%>
    </script>
</body>
</html>