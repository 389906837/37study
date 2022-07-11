var Util = Util || {};
Util.Alert = function(text,inparams) {
	var params = {
		aclass : 'tip_popup',
		aid : 'tip_popup',
		time : 3000,
		url : '',
		type : 1
	};
	if (inparams && typeof (inparams) == 'object') {
		for ( var key in inparams) {
			if (key.match(/^_/)) {
				continue;
			}
			params[key] = inparams[key];
		}
	};
	var temp = document.getElementById(params.aid);
	if(temp) {
		return;
	}
	var html = '<div id="'+params.aid+'" class="'+params.aclass+'"><span class="tip_txt">'+text+'</span></div>';
	$(html).appendTo('body').fadeIn("slow");
	var alertDiv = $('.' + params.aclass);
	if (typeof params.time === 'number') {
		setTimeout(function() {
			Util.alertClose(alertDiv);
			if(params.url != '') {
				if(params.url == 'reload') {
					window.location.reload();
				} else if(params.url == 'history') {
					Util.goHistry();
				} else {
					if (params.type == 1) {
                        Util.href(params.url);
					} else {
                        Util.localHref(params.url);
					}
				}
			}
		}, params.time);
	}
};
Util.alertClose = function(alertDiv) {
	alertDiv.fadeOut(function() {
		alertDiv.remove();
	});
};
//等待
Util.Waiting = function(inparams) {
	var params = {
		aclass : 'wait_popup',
		wid : 'wait_popup',
		text : '加载中...',
		imageUrl : staticPathRoot+'/static/images/loading.gif?100'
	};
	if (inparams && typeof (inparams) == 'object') {
		for ( var key in inparams) {
			if (key.match(/^_/)) {
				continue;
			}
			params[key] = inparams[key];
		}
	};
	var temp = document.getElementById(params.wid);
	if(temp) {
		return;
	}
	var html = '<div id="'+params.wid+'" class="'+params.aclass+'"><div class="wait_pic"><img src="'+params.imageUrl+'" /></div>'
			+ '<div class="wait_txt">'+params.text+'</div></div>';
	$(html).appendTo('body').fadeIn("slow");
};
Util.waitingClose = function(waitingDiv,fn) {
	if(waitingDiv) {
		waitingDiv.fadeOut(function() {
			waitingDiv.remove();
			if($.isFunction(fn)) {
				fn();
			}
		});
	}
};
Util.waitingClose = function(waitingDiv,fn, speed) {
	if(waitingDiv) {
		waitingDiv.fadeOut(speed, function() {
			waitingDiv.remove();
			if($.isFunction(fn)) {
				fn();
			}
		});
	}
};
//加载中
Util.Loading = function(text,inparams) {
	var params = {
		aclass : 'loading',
		wid : 'loading',
		wrapId : 'dataList',
		subheight : 0,
		imageUrl : staticPathRoot+'/static/images/loading.gif'
	};
	if (inparams && typeof (inparams) == 'object') {
		for ( var key in inparams) {
			if (key.match(/^_/)) {
				continue;
			}
			params[key] = inparams[key];
		}
	};
	var temp = document.getElementById(params.wid);
	if(temp) {
		return;
	}
	var html = '<div id="'+params.wid+'" class="'+params.aclass+'"><div class="loading_pic"><img src="'+params.imageUrl+'" /></div>'
			+ '<div class="loading_txt">'+text+'</div></div>';
	$(html).appendTo($("#"+params.wrapId)).fadeIn("slow");
	var iheight=$(window).height();
	$("#"+params.wid).css("height",(iheight-params.subheight)-((iheight-params.subheight)/2-10)+"px");
	$("#"+params.wid).css("padding-top",((iheight-params.subheight)/2-10)+"px");
};

//部分加载中
Util.PartLoading = function(text,inparams) {
	var params = {
		aclass : 'loading_wrap',
		wid : 'loading_wrap',
		wrapId : 'dataList',
		imageUrl : staticPathRoot+'/static/images/loading.gif'
	};
	if (inparams && typeof (inparams) == 'object') {
		for ( var key in inparams) {
			if (key.match(/^_/)) {
				continue;
			}
			params[key] = inparams[key];
		}
	};
	var temp = document.getElementById(params.wid);
	if(temp) {
		return;
	}
	var html = '<div id="'+params.wid+'" class="'+params.aclass+'"><div class="loading_wrapimg"><img src="'+params.imageUrl+'" /></div>'
			+ '<div class="loading_wraptxt">'+text+'</div></div>';
	//$(html).appendTo($("#"+params.wrapId)).fadeIn("slow");
	$("#"+params.wrapId).html(html);
};

// 加载失败
Util.Loadfailure = function(text,inparams) {
	var params = {
		aclass : 'loading_failure_all',
		wid : 'loading_failure',
		wrapId : 'dataList',
		subheight : 0,
		imageUrl : staticPathRoot+'/static/images/loadingfailure.png'
	};
	if (inparams && typeof (inparams) == 'object') {
		for ( var key in inparams) {
			if (key.match(/^_/)) {
				continue;
			}
			params[key] = inparams[key];
		}
	};
	var temp = document.getElementById(params.wid);
	if(temp) {
		return;
	}
	var html = '<div id="'+params.wid+'" class="'+params.aclass+'"><div class="loading_failureimg_all"><img src="'+params.imageUrl+'" /></div>'
	+ '<div class="loading_failuretxt_all">'+text+'</div></div>';
	$("#"+params.wrapId).html(html);
	var iheight=$(window).height();
	$("#"+params.wid).css("height",(iheight-params.subheight)-((iheight-params.subheight)/2-10)+"px");
	$("#"+params.wid).css("padding-top",((iheight-params.subheight)/2-10)+"px");
};


// 暂无记录
Util.EmptyData = function(text,inparams) {
    var params = {
        aclass : 'error-main',
        wid : 'loading_empty',
        wrapId : 'dataList',
        imageUrl : staticPathRoot+'/static/images/norecords.png'
    };
    if (inparams && typeof (inparams) == 'object') {
        for ( var key in inparams) {
            if (key.match(/^_/)) {
                continue;
            }
            params[key] = inparams[key];
        }
    };
    var temp = document.getElementById(params.wid);
    if(temp) {
        return;
    }
    var html = '<div id="'+params.wid+'" class="md '+params.aclass+'"><img class="emptyData" src="'+params.imageUrl+'"><p>'+text+'</p></div>';
    $("#"+params.wrapId).html(html);
    var allHg=$(document).height();
    $(".error-main").css("padding-top",allHg/4.5);
};

//加载失败
Util.PartLoadfailure = function(text,inparams) {
	var params = {
		aclass : 'loading_failure',
		wid : 'loading_failure',
		wrapId : 'dataList',
		imageUrl : staticPathRoot+'/static/images/loadingfailure.png'
	};
	if (inparams && typeof (inparams) == 'object') {
		for ( var key in inparams) {
			if (key.match(/^_/)) {
				continue;
			}
			params[key] = inparams[key];
		}
	};
	var temp = document.getElementById(params.wid);
	if(temp) {
		return;
	}
	var html = '<div id="'+params.wid+'" class="'+params.aclass+'"><div class="loading_failureimg"><img src="'+params.imageUrl+'" /></div>'
			+ '<div class="loading_failuretxt">'+text+'</div></div>';
	$("#"+params.wrapId).html(html);
};

// 底部加载中
Util.Loadfoot = function(text,inparams) {
	var params = {
		aclass : 'loading_foot',
		wid : 'loading_foot',
		wrapId : 'foot_div',
		text:'',
		onClick:'',
		imageUrl : ''
	};
	if (inparams && typeof (inparams) == 'object') {
		for ( var key in inparams) {
			if (key.match(/^_/)) {
				continue;
			}
			params[key] = inparams[key];
		}
	};
	if(params.imageUrl!=''){
		var html = '<div id="'+params.wid+'" class="'+params.aclass+'"><div><img src="'+params.imageUrl+'" class="loading_ft_img" /><span class="loadft_txt">'+text+'</span></div></div>';
	} else{
		var html = '<div id="'+params.wid+'" class="'+params.aclass+'"><div><span class="loadft_txt">'+text+'</span></div></div>';
	}
	$("#"+params.wrapId).html(html);
	if (params.onClick != '' && $.isFunction(params.onClick)) {
		$("#"+params.wid).click(function() {
			params.onClick();
		});
	}
};
Util.href = function(url) {
    Util.Waiting();
    window.location.href = url;
};
Util.localHref = function(url) {
    parent.jumpUrl(url);
};
Util.goHistry = function() {
	history.go(-1);
};
//验证是否为空
Util.isEmpty = function(val) {
	if(typeof(val) == "undefined" || val == "") {
		return true;
	}
	return false;
}
//设置cookie
Util.setCookie = function(cname, cvalue, extime) {
	var d = new Date();
    d.setTime(d.getTime() + extime);
    expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires+";path=/";
}
//获取cookie
Util.getCookie = function(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) != -1) return c.substring(name.length, c.length);
    }
    return "";
}
//清除cookie  
Util.clearCookie = function(name) {  
	Util.setCookie(name, "", -(24*60*60*1000));  
}  