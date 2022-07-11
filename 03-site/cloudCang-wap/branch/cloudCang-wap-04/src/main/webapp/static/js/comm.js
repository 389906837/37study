/*常量定义JS*/
var djstime = 3;
var msgtime = 90;
var timer;
var validCode = true;
var regPhone = /^(13[0-9]|14[0-9]|15[0-9]|16[0-9]|17[0-9]|18[0-9]|19[0-9])\d{8}$/;//手机号码
var regPwd = /^(?![A-Z]+$)(?![a-z]+$)(?!\d+$)(?![\W_]+$)\S{6,20}$/;//登录密码

var global = {
    reconnectFailCount: 0,
    maxFailCount: 6,
    errorUrl: ctxRoot + '/uc/error?errorCode=10008',
    networkUrl: ctxRoot + '/uc/error?errorCode=10012'
};
function jumpUrl(url) {//业务路径跳转
    window.location.href = url;
}
var mineInfo = function () {
    window.location.href = ctxRoot + '/personal/index.html';
};
var myOrder = function () {
    window.location.href = ctxRoot + '/personal/orderAll.html';
};
var myOrderFail = function () {
    window.location.href = ctxRoot + '/personal/orderFail.html';
};
var myCoupon = function () {
    window.location.href = ctxRoot + '/personal/couponList.html';

};
var jumpIndex = function () {
    window.location.href = ctxRoot + '/index/main.html';

};
var jumpIndexMain = function () {
    window.location.href = ctxRoot + '/index/main.html';
};
var jumpOrderDetail = function (orderCode) {
    window.location.href = ctxRoot + '/personal/orderDetail/' + orderCode + '.html';
};

var jumpRefundOrderDetail = function (refundCode) {
    window.location.href = ctxRoot + '/personal/refundOrderDetail/' + refundCode + '.html';

};
var jumpApplyRefund = function (orderCode) {
    window.location.href = ctxRoot + '/order/applyRefund/' + orderCode + '.html';

};
var jumpReplenishmentIndex = function () {
    window.location.href = ctxRoot + '/replenishment/index.html';

};
var myReplenishmentOrder = function () {
    window.location.href = ctxRoot + '/personal/replenishmentList.html';

};
var myDeviceStock = function () {
    window.location.href = ctxRoot + '/replenishment/deviceStock.html';
};
//开通免密
function openFree(path) {
    window.location.href = ctxRoot + path;
}
//退出
function exitApp() {
    if (isWechatPay()) {
        wechatExitApp();
    } else if (isAliPay()) {
        alipayExitApp();
    }
}

//判断是否是微信浏览器
function isWechatPay() {
    var ua = window.navigator.userAgent.toLowerCase();
    if (ua.match(/MicroMessenger/i) == 'micromessenger') {
        return true;
    } else {
        return false;
    }
}
//判断是否是支付宝浏览器
function isAliPay() {
    var ua = window.navigator.userAgent.toLowerCase();
    if (ua.match(/Alipay/i) == 'alipay') {
        return true;
    } else {
        return false;
    }
}
function setTitle(title) {
    if (isWechatPay()) {
        wechatSetTitle(title)
    } else if (isAliPay()) {
        alipaySetTitle(title);
    }
}
function pushHistory(state) {
    window.history.pushState(state, state.title, state.url);
}