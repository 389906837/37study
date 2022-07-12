$(function(){
	//全局查询 enter事件
	$('#searchForm').keydown(function(e){
		if(e.keyCode==13){
			//处理事件
		   $("#searchBtn").click();
		}
	});
});
//弹出框info样式
function alertInfo(msg,inparams) {
    var params = {
        icon: 1,
        time: 3000 //3s后自动关闭
    };
    if (inparams && typeof (inparams) == 'object') {
        for ( var key in inparams) {
            if (key.match(/^_/)) {
                continue;
            }
            params[key] = inparams[key];
        }
    }
    layer.alert(msg, params);
}
//成功提示框
function alertSuccess(msg, inparams,success) {
    var params = {
        icon: 1,
        time: 3000, //3s后自动关闭,
        end : function () {
            parent.closeLayerAndRefresh(inparams.index);
        }
    };
    if (inparams && typeof (inparams) == 'object') {
        for ( var key in inparams) {
            if (key.match(/^_/)) {
                continue;
            }
            params[key] = inparams[key];
        }
    }
    layer.alert(msg, params,success);
}
//弹出框删除样式
function alertDelAndReload(msg, inparams) {
    var params = {
        icon: 2,
        time: 3000, //3s后自动关闭
        cancel:function() {
            window.location.reload();
        }
    };
    if (inparams && typeof (inparams) == 'object') {
        for ( var key in inparams) {
            if (key.match(/^_/)) {
                continue;
            }
            params[key] = inparams[key];
        }
    }
    layer.alert(msg, params,function(index){
        layer.close(index);
        window.location.reload();
    });
}
//load框
function loading(inparams) {
    var params = {
        shade: [0.1,'#fff'] //0.1透明度的白色背景
    };
    if (inparams && typeof (inparams) == 'object') {
        for ( var key in inparams) {
            if (key.match(/^_/)) {
                continue;
            }
            params[key] = inparams[key];
        }
    }
    return layer.load(1, params);
}
//弹出框删除样式
function alertDel(msg, inparams) {
    var params = {
        icon: 2,
        time: 3000 //3s后自动关闭
    };
    if (inparams && typeof (inparams) == 'object') {
        for ( var key in inparams) {
            if (key.match(/^_/)) {
                continue;
            }
            params[key] = inparams[key];
        }
    }
    layer.alert(msg, params);
}
//弹出框成功并刷新
function alertMsgAndReload(msg, inparams) {
    if(isEmpty(msg)) {
        msg = "操作成功";
    }
    var params = {
        icon: 1,
        time: 3000,//3s后自动关闭
        cancel:function() {
            reloadGrid(true);
        }
    };
    if (inparams && typeof (inparams) == 'object') {
        for ( var key in inparams) {
            if (key.match(/^_/)) {
                continue;
            }
            params[key] = inparams[key];
        }
    }
    layer.alert(msg, params,function(index){
        layer.close(index);
        reloadGrid(true);
    });
}
//msg方式弹出框
function alertMsg(msg, inparams) {
    var params = {
        time: 3000, //3s后自动关闭
        btn: ['确定']
    };
    if (inparams && typeof (inparams) == 'object') {
        for ( var key in inparams) {
            if (key.match(/^_/)) {
                continue;
            }
            params[key] = inparams[key];
        }
    }
    layer.msg(msg, params);
}
//
function upCache(url) {
    $.ajax({
        type: "post",
        url: url,
        dataType: "json",
        success: function(data){
            alertMsgAndReload(data.msg);
        }
    });
}
//删除确认提示框
function confirmDelTip(msg, url, param) {
    layer.confirm(msg, {
        btn: ['取消','确定'], //按钮
        cancel:function(index) {
            closeLayer(index);
		}
    }, function(index){
        layer.close(index);
    }, function(){
        operDel(url, param);
    });
}
//操作确认提示框
function confirmTip(msg, fn) {
    layer.confirm(msg, {
        btn: ['取消','确定'], //按钮
        cancel:function(index) {
            closeLayer(index);
        }
    }, function(index){
        layer.close(index);
    }, function(){
        if($.isFunction(fn)) {
            fn();
        }
    });
}
//操作确认提示框
function confirmOperTip(msg, url, param) {
    layer.confirm(msg, {
        btn: ['取消','确定'], //按钮
        cancel:function(index) {
            closeLayer(index);
        }
    }, function(index){
        layer.close(index);
    }, function(){
        operDealwith(url, param);
    });
}

//处理操作事件
function operDealwith(url,param) {
    $.ajax({
        type: "post",
        url: url,
        data: param,
        dataType: "json",
        success: function(data){
            dealwithSuccess(data);
        }
    });
}
//操作完回调处理
dealwithSuccess = function(data) {
    if(data.success) {
        alertMsgAndReload(data.msg);
    } else {
        alertDel(data.msg);
    }
};
//处理删除事件
function operDel(url,param) {
    $.ajax({
        type: "post",
        url: url,
        data: param,
        dataType: "json",
        success: function(data){
            delSuccess(data);
        }
    });
}
//重置密码确认框
function confirmRestTip(msg, url, param) {
    layer.confirm(msg, {
        btn: ['取消','确定'], //按钮
        cancel:function(index) {
            closeLayer(index);
        }
    }, function(index){
        layer.close(index);
    }, function(){
        operRest(url, param);
    });
}
//处理重置密码事件
function operRest(url,param) {
    $.ajax({
        type: "post",
        url: url,
        data: param,
        dataType: "json",
        success: function(data){
            RestSuccess(data);
        }
    });
}
//重置密码后callback
RestSuccess = function(data) {
    if(data.success) {
        alertMsgAndReload(data.msg);
    } else {
        alertDel("重置密码失败!");
    }
};
//成功操作
/*operSuccess = function(data) {
    if(data.success) {
        alertMsgAndReload(data.msg);
    } else {
        alertDel(data.msg);
    }
};*/
var layerIndex;//层变量
//展示弹出框
function showLayer(title, url, inparams) {
    var params = {
        type: 2,
        title: title,
        shadeClose: false,
        shade: 0.8,
        maxmin: true, //开启最大化最小化按钮
        area: ['55%', '60%'],
        content:url,
        scrollbar:true
    };
    if (inparams && typeof (inparams) == 'object') {
        for ( var key in inparams) {
            if (key.match(/^_/)) {
                continue;
            }
            params[key] = inparams[key];
        }
    }
    layerIndex = layer.open(params);
}
//展示弹出框 40% 60%
function showLayerOne(title, url, inparams) {
    var params = {
        type: 2,
        title: title,
        shadeClose: false,
        shade: 0.8,
        maxmin: true, //开启最大化最小化按钮
        area: ['40%', '60%'],
        content:url,
        scrollbar:true
    };
    if (inparams && typeof (inparams) == 'object') {
        for ( var key in inparams) {
            if (key.match(/^_/)) {
                continue;
            }
            params[key] = inparams[key];
        }
    }
    layerIndex = layer.open(params);
}
//展示弹出框 50% 40%
function showLayerMin(title, url, inparams) {
    var params = {
        type: 2,
        title: title,
        shadeClose: false,
        shade: 0.8,
        maxmin: true, //开启最大化最小化按钮
        area: ['50%', '55%'],
        content:url,
        scrollbar:true
    };
    if (inparams && typeof (inparams) == 'object') {
        for ( var key in inparams) {
            if (key.match(/^_/)) {
                continue;
            }
            params[key] = inparams[key];
        }
    }
    layerIndex = layer.open(params);
}
//展示弹出框 70% 60%
function showLayerMedium(title, url, inparams) {
    var params = {
        type: 2,
        title: title,
        shadeClose: false,
        shade: 0.8,
        maxmin: true, //开启最大化最小化按钮
        area: ['65%', '75%'],
        content:url,
        scrollbar:true
    };
    if (inparams && typeof (inparams) == 'object') {
        for ( var key in inparams) {
            if (key.match(/^_/)) {
                continue;
            }
            params[key] = inparams[key];
        }
    }
    layerIndex = layer.open(params);
}

//展示弹出框 85% 75%
function showLayerMax(title, url, inparams) {
    var params = {
        type: 2,
        title: title,
        shadeClose: false,
        shade: 0.8,
        maxmin: true, //开启最大化最小化按钮
        area: ['80%', '90%'],
        content:url,
        scrollbar:true
    };
    if (inparams && typeof (inparams) == 'object') {
        for ( var key in inparams) {
            if (key.match(/^_/)) {
                continue;
            }
            params[key] = inparams[key];
        }
    }
    layerIndex = layer.open(params);
}
/**
 * 关闭layer
 */
function closeLayer() {
    layer.close(layerIndex);
}
/**
 * 关闭弹出框
 * @param index 层ID
 */
function closeLayer(index) {
    layer.close(index);
}

//表格刷新
function reloadGrid(flag, rowNum) {
    var sdata;
    if(!flag) {
        sdata = { // 查询条件
            "newdate" : new Date().getTime()
        };
    } else {
        sdata = getFormValues();
    }

    if (isEmpty(rowNum)) {
        rowNum = $("#gridTable").jqGrid('getGridParam', 'rowNum');
    }
    var postData = $("#gridTable").jqGrid("getGridParam", "postData");
    $.extend(postData,sdata);
    $("#gridTable").jqGrid("setGridParam", {
        datatype : "json",
        rowNum : rowNum,
        search : true,
        mtype : "post"
    }).trigger("reloadGrid");
}
//重新加载表格并回到第一页
function resetReloadGrid(rowNum) {
    var sdata = getFormValues();
    if (isEmpty(rowNum)) {
        rowNum = $("#gridTable").jqGrid('getGridParam', 'rowNum');
    }
    var postData = $("#gridTable").jqGrid("getGridParam", "postData");
    $.extend(postData,sdata);
    $("#gridTable").jqGrid("setGridParam", {
        datatype : "json",
        rowNum : rowNum,
        page : 1,
        search : true,
        mtype : "post"
    }).trigger("reloadGrid");
}

//表格删除后callback
delSuccess = function(data) {
	if(data.success) {
        alertMsgAndReload(data.msg);
	} else {
	    if(!isEmpty(data.msg)) {
            alertDel(data.msg);
        } else {
            alertDel("删除失败!");
        }
	}
};
//成功操作
operSuccess = function(data) {
    if(data.success) {
        alertMsgAndReload(data.msg);
    } else {
        alertDel(data.msg);
    }
};

//获取form的值
function getFormValues() {
	var sdata = {};
    $("#searchForm input[type=text]").each(function(){
        if (!isEmpty(this.name)) {
            sdata[this.name] = this.value;
        }
	});
    $("#searchForm input[type=hidden]").each(function(){
        if (!isEmpty(this.name)) {
            sdata[this.name] = this.value;
        }
    });
    $("#searchForm select").each(function(){
		if(!isEmpty(this.name)) {
			sdata[this.name] = this.value;
		}
	});
	return sdata;
}

//获取form的值
function getFormValue() {
    var parameter = "";
    $("#searchForm input[type=text]").each(function(){
        if(!isEmpty(this.value)) {
            parameter += "&"+this.name+"="+this.value;
        }
    });
    $("#searchForm input[type=hidden]").each(function(){
        if(!isEmpty(this.value)) {
            parameter += "&"+this.name+"="+this.value;
        }
    });
    $("#searchForm select").each(function(){
        if(!isEmpty(this.value)) {
            parameter += "&"+this.name+"="+this.value;
        }
    });
    return parameter;
}


function setResetFormValues() {
    $(".layui-form input[type=text]").each(function(){
        this.value = "";
    });
    $(".layui-form select").each(function(){
        this.value = "";
    });
}
/**
 * 日期格式化
 * @param date
 * @param format
 * @returns
 */
function formatDate(date, format) {  
	if (date=="\"null\"") return "";
    if (!date) return;   
    if (!format) format = "yyyy-MM-dd";   
    switch(typeof date) {   
        case "string":   
        	var dt = date.replace(/-/g,"/");
			dt = dt.substring(0,dt.indexOf("."));
            date = new Date(dt); 
            break;   
        case "number":   
            date = new Date(date);   
            break;   
    }    
    if (!date instanceof Date) return;   
    var dict = {   
        "yyyy": date.getFullYear(),   
        "M": date.getMonth() + 1,   
        "d": date.getDate(),   
        "H": date.getHours(),   
        "m": date.getMinutes(),   
        "s": date.getSeconds(),   
        "MM": ("" + (date.getMonth() + 101)).substr(1),   
        "dd": ("" + (date.getDate() + 100)).substr(1),   
        "HH": ("" + (date.getHours() + 100)).substr(1),   
        "mm": ("" + (date.getMinutes() + 100)).substr(1),   
        "ss": ("" + (date.getSeconds() + 100)).substr(1)   
    };      
    return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g, function() {   
        return dict[arguments[0]];   
    });                   
}

//时间比较
function dataDuibi(a, b) {
    var arr = a.split("-");
    var starttime = new Date(arr[0], arr[1], arr[2]);
    var starttimes = starttime.getTime();
    var arrs = b.split("-");
    var lktime = new Date(arrs[0], arrs[1], arrs[2]);
    var lktimes = lktime.getTime();
    if (starttimes >= lktimes) {
        return true;
    } else {
        return false;
    }
}
//时间相减 得到天数
function dataSub(a, b) {
    var arr = a.split("-");
    var starttime = new Date(arr[0], arr[1], arr[2]);
    var starttimes = starttime.getTime();
    var arrs = b.split("-");
    var lktime = new Date(arrs[0], arrs[1], arrs[2]);
    var lktimes = lktime.getTime();
    var date3=starttimes-lktimes;  //时间差的毫秒数
    //计算出相差天数
    var days=Math.floor(date3/(24*3600*1000));
    return days;
}
/**
 * 判断值是都为空 
 * @param val
 * @returns {Boolean} true 为空
 */
function isEmpty(val) {
	if(typeof(val) == "undefined" || val == null || val == 'null' || val == "") {
		return true;
	}
	return false;
}
/**
 * 判断对象是否为空
 * @param obj
 * @returns {boolean}
 */
function isEmptyObject(obj) {
    for (var key in obj) {
        return false;
    }
    return true;
}

function createDatePicker(element,callback,inparams) {
    var params = {
        startDate: moment().subtract(6, 'days'),
        endDate: moment().subtract(0, 'days'),
        minDate: '2018-01-01',
        maxDate: new Date(),
        alwaysShowCalendars: true,
        showDropdowns: true,
        locale: {
            applyLabel: '确认',
            cancelLabel: '取消',
            fromLabel: '起始时间',
            format: 'YYYY-MM-DD',
            toLabel: '结束时间',
            weekLabel: 'W',
            customRangeLabel: '自定义',
            daysOfWeek: ["日", "一", "二", "三", "四", "五", "六"],
            monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        },
        ranges: {
            '昨日': [moment().subtract('days', 1).startOf('day'), moment().subtract('days', 1).endOf('day')],
            '今日': [moment().startOf('day'), moment()],
            '本周': [moment().startOf('week'), moment()],
            '最近7日': [moment().subtract('days', 6), moment()],
            '本月': [moment().startOf('month'), moment()],
            '最近30日': [moment().subtract('days', 29), moment()]
        }
    };
    if (inparams && typeof (inparams) == 'object') {
        for ( var key in inparams) {
            if (key.match(/^_/)) {
                continue;
            }
            params[key] = inparams[key];
        }
    };
    $(element).daterangepicker(params, function (start, end, label) {
        var flag = true;
        var condition = "";
        if ("今日" == label) {
            flag = false;
            condition = "today";
        } else if ('昨日' == label) {
            flag = false;
            condition = "yesterday";
        }
        if($.isFunction(callback)) {
            callback(condition, flag, start, end);
        };
    });
}
/**
 * 日期时间 相加
 * @param date 日期
 * @param days 天数
 * @returns {string} 日期+ 时分秒
 */
function dateAndTimeAdd(date, days) {
    date.setDate(date.getDate() + days);
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    var hours = date.getHours();       // 获取当前小时数(0-23)
    var minutes = date.getMinutes();     // 获取当前分钟数(0-59)
    var seconds = date.getSeconds();// 获取当前秒数(0-59)
    if (month < 10) {
        month = '0' + month;
    }
    if (day < 10) {
        day = '0' + day;
    }
    if (hours < 10) {
        hours = '0' + hours;
    }
    if (minutes < 10) {
        minutes = '0' + minutes;
    }
    if (seconds < 10) {
        seconds = '0' + seconds;
    }

    return year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds;
};
/**
 * 日期相加
 * @param date 日期
 * @param days 天数
 * @returns {string} 日期
 */
function dateAdd(date, days) {
    date.setDate(date.getDate() + days);
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    if (month < 10) {
        month = '0' + month;
    }
    if (day < 10) {
        day = '0' + day;
    }
    return year + '-' + month + '-' + day;
};