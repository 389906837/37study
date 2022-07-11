function connect(params) {
    var url = apiPath;
    url += "?deviceId="+params.deviceId;
    url += "&userId="+params.userId;
    url += "&userCode="+params.userCode;
    url += "&userName="+params.userName;
    url += "&isourceClientType="+params.isourceClientType;
    url += "&deviceCode="+params.deviceCode;
    url += "&clientIp="+params.clientIp;
    var socketObj = io.connect(url, {'reconnect': true,'transports':['websocket']});
    //var socketObj = io.connect(url, {'reconnect': true,'transports':['websocket','polling']});
    //var socketObj = io.connect(url, {'transports':['websocket']});
    socketObj.on('connect', function() {
        console.log("连接成功");
        connectFlag = 1;
        monitorSocket(socketObj);
    });
    socketObj.on('connect_failed', function() {
        console.log("连接异常，正在尝试重连");
        connectFlag = 3;
    });
    socketObj.on('error', function() {
        //Util.localHref(global.errorUrl);
        connectFlag = 2;
    });
    socketObj.on('reconnecting', function () {
        global.reconnectFailCount++;
        if (global.reconnectFailCount >= global.maxFailCount) {
            //Util.localHref(global.networkUrl);
            connectFlag = 3;
        }
    });
    socketObj.on('reconnect', function () {
        console.log("重新连接正常");
        connectFlag = 1;
    });
    socketObj.on('disconnect', function () {
        console.log("连接关闭");
        connectFlag = 0;
    });
    return socketObj;
}
var openTimeOut = 5;//开门超时时间
var openTimer;
//var closeTimeOut = 10;//关门超时时间
//var closeTimer;
//连接成功 监听服务发送消息
function monitorSocket(socketObj) {
    socketObj.on('socketEvent', function (data) {
        if(data == "success") {
            Util.Waiting({wid: 'wait_popup'});
            //开启开门定时器
            openTimer = setInterval(function () {
                openTimeOut--;
                if(openTimeOut <= 0) {
                    clearInterval(openTimer);
                    openTimeOut = 5;
                    Util.waitingClose($("#wait_popup"));
                    jumpUrl(ctxRoot + "/uc/error?errorCode=10018");
                }
            },1000);
        } else if(data.success) {//消息是否成功
            if(data.types == 10 || data.types == 20) {
                Util.waitingClose($("#wait_popup"), function () {
                    //关闭开门定时器
                    clearInterval(openTimer);
                    openTimeOut = 5;
                   /* //开始关门定时器
                    closeTimer = setInterval(function () {
                        closeTimeOut--;
                        if(closeTimeOut <= 0) {
                            clearInterval(closeTimeOut);
                            closeTimeOut = 15;
                            jumpUrl(ctxRoot + "/uc/error?errorCode=10019");
                        }
                    },1000);*/
                    if(data.types == 10) {//购物开门跳转开门成功页面
                        jumpUrl(ctxRoot + "/index/openSuccess?deviceId="+data.data);
                    } else if(data.types == 20) {//补货开门跳转开门成功页面
                        jumpUrl(ctxRoot + "/replenishment/openSuccess?deviceId="+data.data);
                    }
                });
            } else if(data.types == 30) {//购物关门
                //关闭关门定时器
                /*clearInterval(closeTimer);
                closeTimeOut = 15;*/
                if(Util.isEmpty(data.data)) {
                    jumpUrl(ctxRoot + "/order/buySuccess");
                } else {
                    jumpUrl(ctxRoot + "/order/buySuccess?orderCodes="+data.data+"&isFirstOrder="+data.msg);
                }
            } else if(data.types == 40) {//补货员关门关门
                //关闭关门定时器
                /*clearInterval(closeTimer);
                closeTimeOut = 15;*/
                if(Util.isEmpty(data.data)) {
                    jumpUrl(ctxRoot + "/replenishment/closeDoorSuccess");
                } else {
                    jumpUrl(ctxRoot + "/replenishment/closeDoorSuccess?recordCode="+data.data);
                }
            }
        } else {
            if(data.types == 10 || data.types == 20) {
                //关闭开门定时器
                clearInterval(openTimer);
                openTimeOut = 5;
                if (data.types == 10) {//购物开门失败
                    childWindow.openFail(data.msg);
                } else if (data.types == 20) {//补货员开门失败
                    childWindow.openFail(data.msg);
                }
                Util.waitingClose($("#wait_popup"));
            } else if(data.types == 50) {//购物异常
                //关闭关门定时器
               /* clearInterval(closeTimer);
                closeTimeOut = 15;*/
                if(!Util.isEmpty($("#wait_popup").attr("id"))) {
                    Util.waitingClose($("#wait_popup"));
                }
                jumpUrl(ctxRoot + "/uc/error?errorCode=10015&code="+data.errorCode);
            } else if(data.types == 60) {//补货异常
                //关闭关门定时器
                /*clearInterval(closeTimer);
                closeTimeOut = 15;*/
                if(!Util.isEmpty($("#wait_popup").attr("id"))) {
                    Util.waitingClose($("#wait_popup"));
                }
                jumpUrl(ctxRoot + "/uc/error?errorCode=10016&code="+data.errorCode);
            }
        }
    });
}