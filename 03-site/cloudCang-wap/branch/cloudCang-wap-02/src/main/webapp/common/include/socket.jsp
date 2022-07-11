<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%-- 建立长连接 --%>
<c:if test="${!empty connectParam}">
    <script type="text/javascript">
        $(function () {
            initConnect();
        });
    </script>
</c:if>
<script type="text/javascript">
    function initConnect() {
        socketObj = connect(JSON.parse('${connectParam}'));
    }
    function sendMsg(msg) {//发送消息
        if (connectFlag != 1) {
            if (connectFlag == 2) {
                openFail("连接服务器异常，请重新扫码");
                return;
            }
            if ($.isFunction(initConnect)) {
                initConnect();
                var timer = setInterval(function () {//定时器1秒执行
                    clearInterval(timer);
                    if (connectFlag != 1) {
                        openFail("连接服务器异常，请重新扫码");
                    } else {
                        socketObj.emit('socketEvent', msg);
                    }
                }, 1000);
                return;
            }
            openFail("连接服务器异常，请重新扫码");
            return;
        }
        socketObj.emit('socketEvent', msg);
    }
</script>