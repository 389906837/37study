function connect(params) {
    var url = apiPath;
    url += "?deviceId="+params.deviceId;
    url += "&userId="+params.userId;
    url += "&userCode="+params.userCode;
    url += "&userName="+params.userName;
    url += "&isourceClientType="+params.isourceClientType;
    url += "&deviceCode="+params.deviceCode;
    var socketObj = io.connect(url, {'reconnect': true,'transports':['websocket']});
    socketObj.on('connect', function() {
        console.log("连接成功");
        monitorSocket(socketObj);
    });
    socketObj.on('connect_failed', function() {
        console.log("连接异常，正在尝试重连");
    });
    socketObj.on('error', function() {
        Util.localHref(global.errorUrl);
    });
    socketObj.on('reconnecting', function () {
        global.reconnectFailCount++;
        if (global.reconnectFailCount >= global.maxFailCount) {
            Util.localHref(global.networkUrl);
        }
    });
    socketObj.on('reconnect', function () {
        console.log("重新连接正常");
    });
    socketObj.on('disconnect', function () {
        console.log("连接关闭");
    });
    return socketObj;
}
//连接成功 监听服务发送消息
function monitorSocket(socketObj) {
    socketObj.on('socketEvent', function (data) {
        if(data == "success") {
            Util.Waiting({wid: 'wait_popup'});
        } else if(data.success) {//消息是否成功
            if(data.types == 10 || data.types == 20) {
                Util.waitingClose($("#wait_popup"), function () {
                    if(data.types == 10) {//购物开门跳转开门成功页面
                        jumpUrl(ctxRoot + "/index/openSuccess?deviceId="+data.data);
                    } else if(data.types == 20) {//补货开门跳转开门成功页面
                        jumpUrl(ctxRoot + "/replenishment/openSuccess?deviceId="+data.data);
                    }
                });
            } else if(data.types == 30) {//购物关门
                if(Util.isEmpty(data.data)) {
                    jumpUrl(ctxRoot + "/order/buySuccess");
                } else {
                    jumpUrl(ctxRoot + "/order/buySuccess?orderCodes="+data.data+"&isFirstOrder="+data.msg);
                }
            } else if(data.types == 40) {//补货员关门关门
                if(Util.isEmpty(data.data)) {
                    jumpUrl(ctxRoot + "/replenishment/closeDoorSuccess");
                } else {
                    jumpUrl(ctxRoot + "/replenishment/closeDoorSuccess?recordCode="+data.data);
                }
            } else if(data.types == 50) {//购物异常
                Util.localHref(ctxRoot + "/uc/error?errorCode=10015");
            } else if(data.types == 60) {//补货异常
                Util.localHref(ctxRoot + "/uc/error?errorCode=10016");
            }
        } else {
            data = eval('(' + data + ')');
            if(data.types == 10 || data.types == 20) {
                Util.waitingClose($("#wait_popup"), function () {
                    if (data.types == 10) {//购物开门失败
                        childWindow.openFail(data.msg);
                    } else if (data.types == 20) {//补货员开门失败
                        childWindow.openFail(data.msg);
                    }
                });
            }
        }
    });
}