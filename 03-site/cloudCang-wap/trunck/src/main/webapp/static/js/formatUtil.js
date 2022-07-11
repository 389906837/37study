/**
 * 将数值四舍五入(保留2位小数)后格式化成金额形式
 *
 * @param num 数值(Number或者String)
 * @return 金额格式的字符串,如'1,234,567.45'
 * @type String
 */
function formatCurrency(num) {
	if (num==undefined)num="0.00";
    num = num.toString().replace(/\$|\,/g,'');
    if(isNaN(num))
    num = "0.00";
    sign = (num == (num = Math.abs(num)));
    num = Math.floor(num*100+0.50000000001);
    cents = num%100;
    num = Math.floor(num/100).toString();
    if(cents<10)
    cents = "0" + cents;
    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
    num = num.substring(0,num.length-(4*i+3))+','+
    num.substring(num.length-(4*i+3));
    if (cents.toString()=='00'){
    	return (((sign)?'':'-') + num );
    }else if(cents.toString().substring(2,1)=='0'){
    	return (((sign)?'':'-') + num + '.' + cents.toString().substring(0,1));
    }else{
    	return (((sign)?'':'-') + num + '.' + cents);
    }
}
function formateMoney(v) {
    if(isNaN(v)){
        return v;
    }
    v = (Math.round((v - 0) * 100)) / 100;
    v = (v == Math.floor(v)) ? v + ".00" : ((v * 10 == Math.floor(v * 10)) ? v
        + "0" : v);
    v = String(v);
    var ps = v.split('.');
    var whole = ps[0];
    var sub = ps[1] ? '.' + ps[1] : '.00';
    var r = /(\d+)(\d{3})/;
    while (r.test(whole)) {
        whole = whole.replace(r, '$1' + ',' + '$2');
    }
    v = whole + sub;

    return v;
}

function formateMoneyNo(v) {
    if(isNaN(v)){
        return v;
    }
    v = (Math.round((v - 0) * 100)) / 100;
    v = (v == Math.floor(v)) ? v + ".00" : ((v * 10 == Math.floor(v * 10)) ? v
        + "0" : v);
    v = String(v);
    var ps = v.split('.');
    var whole = ps[0];
    var sub = ps[1] ? '.' + ps[1] : '.00';
   /* var r = /(\d+)(\d{3})/;
    while (r.test(whole)) {
        whole = whole.replace(r, '$1' + ',' + '$2');
    }*/
    v = whole + sub;

    return v;
}
function clearNoNum(event,obj){
    //响应鼠标事件，允许左右方向键移动 
    event = window.event||event; 
    if(event.keyCode == 37 | event.keyCode == 39){ 
        return; 
    } 
    //先把非数字的都替换掉，除了数字和. 
    obj.value = obj.value.replace(/[^\d.]/g,""); 
    //必须保证第一个为数字而不是. 
    obj.value = obj.value.replace(/^\./g,""); 
    //保证只有出现一个.而没有多个. 
    obj.value = obj.value.replace(/\.{2,}/g,"."); 
    //保证.只出现一次，而不能出现两次以上 
    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$","."); 
}

function checkNum(obj){ 
    //为了去除最后一个. 
    obj.value = obj.value.replace(/\.$/g,""); 
} 


function validateMoney(money){
	var flag = (/^(([1-9]\d*)|\d)(\.\d{1,2})?$/).test(money.toString());
	return flag;
}
function validateDecimalMoney(money){
	var flag = (/^\d+(?=\.{0,1}\d+$|$)/).test(money.toString());
	return flag;
}

//去空格
String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
}


/**
 * 精确乘法
 * @param arg1
 * @param arg2
 * @returns {Number}
 */
function accMul(arg1, arg2) {
	var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
	try {
		m += s1.split(".")[1].length
	} catch (e) {}
	try {
		m += s2.split(".")[1].length
	} catch (e) {}
	return Number(s1.replace(".", "")) * Number(s2.replace(".", ""))
			/ Math.pow(10, m)
}

//说明：javascript的除法结果会有误差，在两个浮点数相除的时候会比较明显。这个函数返回较为精确的除法结果。  
//调用：accDiv(arg1,arg2)  
//返回值：arg1除以arg2的精确结果  
function accDiv(arg1, arg2) {
	var t1 = 0, t2 = 0, r1, r2;
	try {
		t1 = arg1.toString().split(".")[1].length
	} catch (e) {}
	try {
		t2 = arg2.toString().split(".")[1].length
	} catch (e) {}
	with (Math) {
		r1 = Number(arg1.toString().replace(".", ""))
		r2 = Number(arg2.toString().replace(".", ""))
		return (r1 / r2) * pow(10, t2 - t1);
	}
}

//说明：javascript的减法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的减法结果。  
//调用：accSub(arg1,arg2)  
//返回值：arg1减上arg2的精确结果  
function accSub(arg1, arg2) {
	return accAdd(arg1, -arg2);
}

//说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。  
//调用：accAdd(arg1,arg2)  
//返回值：arg1加上arg2的精确结果  
function accAdd(arg1, arg2) {
	var r1, r2, m;
	try {
		r1 = arg1.toString().split(".")[1].length
	} catch (e) {r1 = 0}
	try {
		r2 = arg2.toString().split(".")[1].length
	} catch (e) {r2 = 0}
	m = Math.pow(10, Math.max(r1, r2))
	return (arg1 * m + arg2 * m) / m
}