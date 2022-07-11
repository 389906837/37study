$.views.converters({
	
	/**
	 * 数值转换
	 * 小于1W 保留2位小数；大于1W 保留4位小数。
	 * @param values 数值
	 * @returns {String} 返回数值
	 */
	quantityConversion : function(values){
		var val = parseFloat(values);
		var company = "";
		var fixed = 4;
		if (val >= 10000 && val < 100000000) {
			val = val / 10000;
			company = "万";
		} else if (val >= 100000000) {
			val = val / 100000000;
			company = "亿";
		} else {
			fixed = 2;
		}
			
		return parseFloat(val.toFixed(fixed)) + company;
	},
	
	/**
	 * 数值转换
	 * 小于1W 保留2位小数；大于1W 保留4位小数。
	 * @param values 数值
	 * @returns {String} 返回数值
	 */
    quantityConversion : function(values, fixed){
		var val = parseFloat(values);
		var company = "";
		if (val >= 10000 && val < 100000000) {
			val = val / 10000;
			company = "万";
		} else if (val >= 100000000) {
			val = val / 100000000;
			company = "亿";
		}
			
		return parseFloat(val.toFixed(fixed)) + company;
	},
	
	/**
	 * 姓名转换  -- > update
	 * 取第一个汉字，后面追加...
	 * @param sname 文字
	 * @param size  .数量
	 * @param showMsg  显示的数据
	 * @returns 修改后的文字
	 */
	quantityNames : function(sname, size, showMsg){
		var str = sname.substring(0, 1);
		var reg = /^([\u4E00-\u9FA5]+，?)+$/;
		var yesorno = str.match(reg) != null;
		
		var htmls = "";
		for(var index = 0; index < size; index++){
			htmls += ".";
		}
	 
		if (yesorno)
			return str + htmls;
		else
			return sname;
	},

	//是否存在字段
    isExistStr : function(sid, targetStr){
		if(targetStr.indexOf(sid) != -1) {
			return "hover";
		}
		return "";
    },
	
	/**
	 * 姓名转换
	 * 取第一个汉字，后面追加...
	 * @param sname 文字
	 * @param size  .数量
	 * @param showMsg  显示的数据
	 * @returns 修改后的文字
	 */
	showMessage : function(value, valueSize, msgSize, showMsg){
		if(value.length <= valueSize){
			return value;
		}
		var str = value.substring(0, valueSize);
		
		var htmls = "";
		for(var index = 0; index < msgSize; index++){
			htmls += ".";
		}
	 
		return str + htmls;
	},
	formateMoney : function (v) {
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
	},
	formateMoneyNotZero : function (v) {
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
	      
	    return v.replace(".00","");  
	},
	formatCurrencyNoformat : function (num) {
		if (num==undefined)num="0";
	    num = num.toString().replace(/\$|\,/g,'');
	    if(isNaN(num))
	    num = "0";
	    sign = (num == (num = Math.abs(num)));
	    num = Math.floor(num*100+0.50000000001);
	    cents = num%100;
	    num = Math.floor(num/100).toString();
	    if(cents<10)
	    cents = "0" + cents;
	    if (cents.toString()=='00'){
	    	return (((sign)?'':'-') + num );
	    }else if(cents.toString().substring(2,1)=='0'){
	    	return (((sign)?'':'-') + num + '.' + cents.toString().substring(0,1));
	    }else{
	    	return (((sign)?'':'-') + num + '.' + cents);
	    }
	},
	formateTest:function(num){
		if (num == undefined) {
			num = "0";
		}
		num = num.toString().replace(/\$|\,/g, '');
		if (isNaN(num)) {
			num = "0";
		}
		return parseFloat(num).toFixed(1);  
	},
	formatCurrency : function (num) {
		if (num == undefined)
			num = "0.00";
		num = num.toString().replace(/\$|\,/g, '');
		if (isNaN(num))
			num = "0.00";
		sign = (num == (num = Math.abs(num)));
		num = Math.floor(num * 100 + 0.50000000001);
		cents = num % 100;
		num = Math.floor(num / 100).toString();
		if (cents < 10)
			cents = "0" + cents;
		for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
			num = num.substring(0, num.length - (4 * i + 3)) + ','
					+ num.substring(num.length - (4 * i + 3));
		if (cents.toString() == '00') {
			return (((sign) ? '' : '-') + num);
		} else if (cents.toString().substring(2, 1) == '0') {
			return (((sign) ? '' : '-') + num + '.' + cents.toString()
					.substring(0, 1));
		} else {
			return (((sign) ? '' : '-') + num + '.' + cents);
		}
	},
	formatPercent : function (num) {
		if (num == undefined)
			num = "0%";
		return parseFloat((num*100).toFixed(2)) + "%";
	},
	formatPercent1 : function (num) {
		if (num == undefined)
			num = "0%";
		return parseFloat((num*100).toFixed(2));
	},
	getStr : function (istatus,types) {
		if(types == '1') {
			if(istatus == 50) {
				return "融资结束";
			} else if(istatus == 60) {
				return "已放款";
			} else if(istatus == 70) {
				return "已结清";
			}
		}
	},
	addAndFormatDate : function(date, pattern, day) {
		if ($.type(date) === "string") {
			if (date == "") {
				date = new Date();
			} else {
				date = new Date(new Date(date).replace(/-/g, "/"));
			}
		} else {
			date = new Date(date);
		}
		date = date.setDate(date.getDate() + day);  
		return $.views.converters.formatDate(date, pattern);
	},
	/**
	 * 格式化日期时间
	 * @param date 时间数
	 * @param pattern 格式
	 * @returns
	 */
	formatDate : function(date, pattern) {
		var toFixedWidth = function(value, length) {
			var result = "00" + value.toString();
			return result.substr(result.length - length);
		};
		
		var patternValue = {
			y : function(date) {
				return date.getFullYear().toString().length > 1 ? toFixedWidth(date.getFullYear(), 2) : toFixedWidth(date.getFullYear(), 1);
			},
			yy : function(date) {
				return toFixedWidth(date.getFullYear(), 2);
			},
			yyy : function(date) {
				return toFixedWidth(date.getFullYear(), 3);
			},
			yyyy : function(date) {
				return date.getFullYear().toString();
			},
			M : function(date) {
				return date.getMonth() + 1;
			},
			MM : function(date) {
				return toFixedWidth(date.getMonth() + 1, 2);
			},
			d : function(date) {
				return date.getDate();
			},
			dd : function(date) {
				return toFixedWidth(date.getDate(), 2);
			},
			H : function(date) {
				return date.getHours();
			},
			HH : function(date) {
				return toFixedWidth(date.getHours(), 2);
			},
			h : function(date) {
				return date.getHours() % 12;
			},
			hh : function(date) {
				return toFixedWidth(
						date.getHours() > 12 ? date.getHours() - 12 : date
								.getHours(), 2);
			},
			m : function(date) {
				return date.getMinutes();
			},
			mm : function(date) {
				return toFixedWidth(date.getMinutes(), 2);
			},
			s : function(date) {
				return date.getSeconds();
			},
			ss : function(date) {
				return toFixedWidth(date.getSeconds(), 2);
			},
			S : function(date) {
				return toFixedWidth(date.getMilliseconds(), 3);
			}
		};
		
		//pattern = 'HH:mm';
		var RegExpObject = /^(y+|M+|d+|H+|h+|m+|s+|E+|S|a)/;
		if (date == undefined) {
			date = new Date();
		}
			
		/* 传入时间为字符串 */
		if ($.type(date) === "string") {
			if (date == "") {
				date = new Date();
			} else {
				date = new Date(new Date(date).replace(/-/g, "/"));
			}
		} else {
			date = new Date(date);
		}

		var result = [];
		while (pattern.length > 0) {
			RegExpObject.lastIndex = 0;
			var matched = RegExpObject.exec(pattern);
			if (matched) {
				result.push(patternValue[matched[0]](date));
				pattern = pattern.slice(matched[0].length);
			} else {
				result.push(pattern.charAt(0));
				pattern = pattern.slice(1);
			}
		}
		return result.join('');
	},
	


	/**
	 * 截取字符串，并追加...
	 * 
	 * @param data
	 * @param leg
	 * @returns {String}
	 */
	substring : function(data, leg) {
		if (data != "" && data != undefined) {
			if (data.length > leg) {
				return data.substr(0, leg) + "...";
			}
			return data;
		}
		return "";
	},

	
	/**
	 *(10.01) 利率数据，i= 0,1 返回数组第一条，或第二条
	 * @param data
	 * @param i
	 */

	rateArray : function(data,i){
		if(data === null || data === ""){
			return "";
		}
		data +="";
		var temp  = data.split('.');
		if(temp.length == 1 ){
			if(i == 0 )
			return $.views.converters.formatCurrency(data);
			if(i == 1 )
				return "00";
		}
		if(i == 0){
			return $.views.converters.formatCurrency(temp[0]);
		}
		if(i == 1){
			 var value = temp[1].substring(0,temp[1].length);
			 if(value.length <=1){
				 value +="0"; 
			 }
			return value;
		}
	},

	getIPhone : function(phoneNum){
		if(phoneNum == null || phoneNum == ""){
			return "";
		}
		var length = phoneNum.length;
		return phoneNum.substr(0, 3)+"****"+phoneNum.substr(length-4, length);
	
	}

});

/**
 *(10.01) 利率数据，i= 0,1 返回数组第一条，或第二条
 * @param data
 * @param i
 */






function getToday(date) {
	var toFixedWidth = function(value, length) {
		var result = "00" + value.toString();
		return result.substr(result.length - length);
	};
	
	var patternValue = {
		y : function(date) {
			return date.getFullYear().toString().length > 1 ? toFixedWidth(date.getFullYear(), 2) : toFixedWidth(date.getFullYear(), 1);
		},
		yy : function(date) {
			return toFixedWidth(date.getFullYear(), 2);
		},
		yyy : function(date) {
			return toFixedWidth(date.getFullYear(), 3);
		},
		yyyy : function(date) {
			return date.getFullYear().toString();
		},
		M : function(date) {
			return date.getMonth() + 1;
		},
		MM : function(date) {
			return toFixedWidth(date.getMonth() + 1, 2);
		},
		d : function(date) {
			return date.getDate();
		},
		dd : function(date) {
			return toFixedWidth(date.getDate(), 2);
		},
		H : function(date) {
			return date.getHours();
		},
		HH : function(date) {
			return toFixedWidth(date.getHours(), 2);
		},
		h : function(date) {
			return date.getHours() % 12;
		},
		hh : function(date) {
			return toFixedWidth(
					date.getHours() > 12 ? date.getHours() - 12 : date
							.getHours(), 2);
		},
		m : function(date) {
			return date.getMinutes();
		},
		mm : function(date) {
			return toFixedWidth(date.getMinutes(), 2);
		},
		s : function(date) {
			return date.getSeconds();
		},
		ss : function(date) {
			return toFixedWidth(date.getSeconds(), 2);
		},
		S : function(date) {
			return toFixedWidth(date.getMilliseconds(), 3);
		}
	};
	
	pattern = 'yyyyMMdd';
	var RegExpObject = /^(y+|M+|d+|H+|h+|m+|s+|E+|S|a)/;
	if (date == undefined) {
		date = new Date();
	}
		
	/* 传入时间为字符串 */
	if ($.type(date) === "string") {
		if (date == "") {
			date = new Date();
		} else {
			date = new Date(new Date(date).replace(/-/g, "/"));
		}
	} else {
		date = new Date(date);
	}

	var result = [];
	while (pattern.length > 0) {
		RegExpObject.lastIndex = 0;
		var matched = RegExpObject.exec(pattern);
		if (matched) {
			result.push(patternValue[matched[0]](date));
			pattern = pattern.slice(matched[0].length);
		} else {
			result.push(pattern.charAt(0));
			pattern = pattern.slice(1);
		}
	}
	return result.join('');
}

function getStringByLength(data, leg) {
	if (data != "" && data != undefined) {
		if (data.length > leg) {
			return data.substr(0, leg) + "...";
		}
		return data;
	}
	return "";
}