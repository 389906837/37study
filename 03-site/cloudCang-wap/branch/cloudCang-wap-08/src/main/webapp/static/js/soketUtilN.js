var connectFlag = -1;
function connect(params) {
    var url = apiPath;
    url += "?deviceId=" + params.deviceId;
    url += "&userId=" + params.userId;
    url += "&userCode=" + params.userCode;
    url += "&userName=" + params.userName;
    url += "&isourceClientType=" + params.isourceClientType;
    url += "&deviceCode=" + params.deviceCode;
    url += "&clientIp=" + params.clientIp;
    var socketObj = io.connect(url, {'reconnect': true, 'transports': ['websocket']});
    //var socketObj = io.connect(url, {'reconnect': true,'transports':['websocket','polling']});
    //var socketObj = io.connect(url, {'transports':['websocket']});
    socketObj.on('connect', function () {
        console.log("连接成功");
        connectFlag = 1;
        monitorSocket(socketObj);
    });
    socketObj.on('connect_failed', function () {
        console.log("连接异常，正在尝试重连");
        connectFlag = 3;
    });
    socketObj.on('error', function () {
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

var flag_shopping = false;
var openTimeOut = 10;//开门超时时间
var openTimer;
//var closeTimeOut = 10;//关门超时时间
//var closeTimer;
//连接成功 监听服务发送消息
function monitorSocket(socketObj) {
    socketObj.on('socketEvent', function (data) {
        if (data == "success") {
            Util.Waiting({wid: 'wait_popup1'});
            //开启开门定时器
            openTimer = setInterval(function () {
                openTimeOut--;
                if (openTimeOut <= 0) {
                    clearInterval(openTimer);
                    openTimeOut = 5;
                    Util.waitingClose($("#wait_popup1"));
                    jumpUrl(ctxRoot + "/uc/error?errorCode=10018");
                }
            }, 1000);
        } else if (data.success) {//消息是否成功
            if (data.types == 10 || data.types == 20) {
                Util.waitingClose($("#wait_popup1"), function () {
                    //关闭开门定时器
                    clearInterval(openTimer);
                    openTimeOut = 5;
                    setTitle("开门成功");
                    if (data.types == 10) {//购物开门跳转开门成功页面
                        $.ajax({
                            type: "POST",
                            dataType: "json",
                            url: ctxRoot + "/index/openSuccess",
                            success: function (msg) {
                                $("#openSuccess").removeClass("close");
                                if (!$("#shoppingDiv").hasClass("close")) {
                                    $("#shoppingDiv").addClass("close");
                                }
                                $("#openHint").html(msg.data["open"].openHint);
                                if (msg.data.preferentialInfos.length > 0) {//渲染数据
                                    $(".buy_havecoupon").removeClass("close");
                                    $(".open_main").removeClass("close");
                                    var html = $("#openSucess_table_tmpl").render(msg.data.preferentialInfos);
                                    $("#discountDta").html(html);
                                }
                            }
                        });
                    } else if (data.types == 20) {//补货开门跳转开门成功页面
                        $.ajax({
                            type: "POST",
                            dataType: "json",
                            url: ctxRoot + "/replenishment/openSuccess",
                            success: function (msg) {
                                $("#reOpenSuccess").removeClass("close");
                                if (!$("#replenishmentDiv").hasClass("close")) {
                                    $("#replenishmentDiv").addClass("close");
                                }
                                //渲染数据
                                var html = $("#reOpenSucess_table_tmpl").render(msg.data);
                                $("#reOpenSuccess").html(html);
                            }
                        });
                    }
                }, 50);
            } else if (data.types == 30) {//购物关门
                //关闭关门定时器
                /*clearInterval(closeTimer);
                 closeTimeOut = 15;*/
                if (Util.isEmpty(data.data)) {
                    jumpUrl(ctxRoot + "/order/buySuccess");
                } else {
                    jumpUrl(ctxRoot + "/order/buySuccess?orderCodes=" + data.data + "&isFirstOrder=" + data.msg);
                }
            } else if (data.types == 40) {//补货员关门关门
                //关闭关门定时器
                /*clearInterval(closeTimer);
                 closeTimeOut = 15;*/
                if (Util.isEmpty(data.data)) {
                    jumpUrl(ctxRoot + "/replenishment/closeDoorSuccess");
                } else {
                    jumpUrl(ctxRoot + "/replenishment/closeDoorSuccess?recordCode=" + data.data);
                }
            } else if (data.types == 70) { // 购物--云端识别实时盘货
                if (!flag_shopping) {
                    flag_shopping = true;
                    $("#shoppingRealtime").removeClass("close");
                    if (!$("#openSuccess").hasClass("close")) {
                        $("#openSuccess").addClass("close");
                    }
                    if (!$("#shoppingDiv").hasClass("close")) {
                        $("#shoppingDiv").addClass("close");
                    }
                    $("body").addClass("cart_bg");
                    //关闭开门定时器
                    clearInterval(openTimer);
                    openTimeOut = 5;
                } else {
                    if (!Util.isEmpty(data.data)) {
                        $.ajax({
                            type: "post",
                            url: ctxRoot + "/order/shoppingCloudRealTimeOrder?userIdAndDeviceId=" + data.data,
                            dataType: "json",
                            success: function (msg) {
                                showCart(msg);
                            }
                        });
                    } else {
                        hideCart();
                    }
                }
            } else if (data.types == 80) {

                if (!flag_shopping) {
                    flag_shopping = true;
                    $("#replenishmentRealtime").removeClass("close");
                    if (!$("#reOpenSuccess").hasClass("close")) {
                        $("#reOpenSuccess").addClass("close");
                    }
                    if (!$("#replenishmentDiv").hasClass("close")) {
                        $("#replenishmentDiv").addClass("close");
                    }
                    //关闭开门定时器
                    clearInterval(openTimer);
                    openTimeOut = 5;
                } else {
                    if (!Util.isEmpty(data.data)) {
                        $.ajax({
                            type: "post",
                            url: ctxRoot + "/order/shoppingCloudRealTimeOrder?userIdAndDeviceId=" + data.data,
                            dataType: "json",
                            success: function (msg) {
                                showAddCart(msg);
                                showSubCart(msg);
                            }
                        });
                    } else {
                        hideRepCart(0);
                    }
                }
            } else if (data.types == 90) {//补货员关门确认补货单
                flag_shopping = false;
                //关闭关门定时器
                jumpUrl(ctxRoot + "/replenishment/closeDoorConfirm?confirmData=" + data.data);
            }
        } else {
            if (data.types == 10 || data.types == 20) {
                //关闭开门定时器
                clearInterval(openTimer);
                openTimeOut = 5;
                if (data.types == 10) {//购物开门失败
                    openFail(data.msg);
                } else if (data.types == 20) {//补货员开门失败
                    openFail(data.msg);
                }
                if (!Util.isEmpty($("#wait_popup").attr("id"))) {
                    Util.waitingClose($("#wait_popup"));
                }
            } else if (data.types == 50) {//购物异常
                //关闭关门定时器
                /* clearInterval(closeTimer);
                 closeTimeOut = 15;*/
                if (!Util.isEmpty($("#wait_popup").attr("id"))) {
                    Util.waitingClose($("#wait_popup"));
                }
                jumpUrl(ctxRoot + "/uc/error?errorCode=10020&code=" + data.errorCode);
            } else if (data.types == 60) {//补货异常
                //关闭关门定时器
                /*clearInterval(closeTimer);
                 closeTimeOut = 15;*/
                if (!Util.isEmpty($("#wait_popup").attr("id"))) {
                    Util.waitingClose($("#wait_popup"));
                }
                jumpUrl(ctxRoot + "/uc/error?errorCode=10016&code=" + data.errorCode);
            }
        }
    });
}

function openTimeStr() {



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

    if (data.types == 10) {//购物开门跳转开门成功页面
        /*$.ajax({
         type: "POST",
         dataType: "json",
         url: ctxRoot + "/index/openSuccess",
         /!*data: {"deviceId": data.data},*!/
         success: function (msg) {
         alert("购物开门跳转开门成功页面###" + JSON.stringify(msg));
         if (msg.success) {
         /!*document.getElementById("errorDiv").style.display = "none";
         $('#msgCodeError').html("");
         getMsgSuccess();*!/
         //渲染数据
         document.getElementById("openSuccess").style.display = "block";//显示
         document.getElementById("isReplenishment").style.display = "none";//显示
         document.getElementById("gouwu").style.display = "none";//显示
         var temp = msg.data["preferentialInfos"];
         alert(JSON.stringify(temp));
         if (!isEmpty(temp)) {
         var html = $("#openSucess_table_tmpl").render(temp);
         $("#discountDta").html(html);
         }
         } else {
         /!* $("#captchaImg").trigger("click");
         $('#msgCodeError').html(msg.msg);
         $("#captcha").focus();
         $("#captcha").select();*!/
         }
         }
         });*/
        /*  jumpUrl(ctxRoot + "/index/openSuccess?deviceId="+data.data);*/
    } else if (data.types == 20) {//补货开门跳转开门成功页面

        jumpUrl(ctxRoot + "/replenishment/openSuccess_new?deviceId=" + data.data);
    }
}