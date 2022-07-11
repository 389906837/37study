/*常量定义JS*/
var djstime=3;
var msgtime=90;
var timer;
var validCode = true;
var regPhone=/^(13[0-9]|14[0-9]|15[0-9]|16[0-9]|17[0-9]|18[0-9]|19[0-9])\d{8}$/;//手机号码
var regPwd=/^(?![A-Z]+$)(?![a-z]+$)(?!\d+$)(?![\W_]+$)\S{6,20}$/;//登录密码

var global = {
    reconnectFailCount:0,
    maxFailCount:6,
    errorUrl:ctxRoot+'/uc/error?errorCode=10008',
    networkUrl:ctxRoot+'/uc/error?errorCode=10012'
};
var mineInfo = function(){
    parent.jumpUrl(ctxRoot + '/personal/index.html');
};
var myOrder = function(){
    parent.jumpUrl(ctxRoot + '/personal/orderList.html');
};
var myCoupon = function(){
    parent.jumpUrl(ctxRoot + '/personal/couponList.html');
};
var jumpIndex = function() {
    parent.jumpUrl(ctxRoot + '/index/page.html');
};
var jumpIndexMain = function() {
    parent.window.location.href = ctxRoot + '/index/main.html';
};
var jumpOrderDetail = function(orderCode) {
    parent.jumpUrl(ctxRoot + '/personal/orderDetail/'+orderCode+'.html');
};
var jumpRefundOrderDetail = function(refundCode) {
    parent.jumpUrl(ctxRoot + '/personal/refundOrderDetail/'+refundCode+'.html');
};
var jumpApplyRefund = function(orderCode) {
    parent.jumpUrl(ctxRoot + '/order/applyRefund/'+orderCode+'.html');
};
var jumpReplenishmentIndex = function() {
    parent.jumpUrl(ctxRoot + '/replenishment/index.html');
};
var myReplenishmentOrder = function() {
    parent.jumpUrl(ctxRoot + '/personal/replenishmentList.html');
};
//开通免密
function openFree(path) {
    parent.window.location.href=ctxRoot+path;
}
//退出
function exitApp() {
    if(isWechatPay()) {
        parent.wechatExitApp();
    } else if(isAliPay()) {
        parent.alipayExitApp();
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

//退出
function testBtn(path) {
    parent.window.location.href=ctxRoot+path;
}