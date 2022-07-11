//全局上下文路径
var rootPath = getContextPath();
var lookupArry = null;//弹出框要赋值的对象数组
var isCatchDiv = false;
var clearLookupArray = null;//弹出框要清除值的对象数组
var lookupindex ='';

/**
 * 获取上下文路径
 */
function getContextPath() 
{ 
		var contextPath = document.location.pathname; 
		var index =contextPath.substr(1).indexOf("/"); 
		contextPath = contextPath.substr(0,index+1); 
		delete index; 
		return contextPath; 
} 

/*
 * 根据Id查找对象
 * @param id 对象Id
 * @return 返回该对象
 */
function getById(id){
	
	return document.getElementById(id);
}

//兼容IE/火狐的获取iframe 的document
function getIFrameDOM(id){  
	return document.getElementById(id).contentDocument || document.frames[id].document; 
}


/**************************通用DIV层的移动操作***********************************/
/* ---------------------------------------------------------------------------------------------
	功能：   需要移动的DIV层的onMouseDown事件
	返回：    
创建者：  Hunter
创建时间：2016-11-01
--------------------------------------------------------------------------------------------- */
function catchDiv(obj){
	isCatchDiv = true;
	obj.setCapture();
	var e = e||window.event;
	dragX = ( e.x || e.clientX) - obj.offsetLeft;
	dragY = ( e.y || e.clientY ) - obj.offsetTop;
}

/* ---------------------------------------------------------------------------------------------
	功能：   需要移动的DIV层的onMouseUp事件
	返回：    
创建者：  Hunter
创建时间：2016-11-01
--------------------------------------------------------------------------------------------- */
function releaseDiv(obj){
	isCatchDiv = false;
	obj.releaseCapture();
}
/* ---------------------------------------------------------------------------------------------
	功能：   需要移动的DIV层的onMouseMove事件
	返回：    
创建者：  Hunter
创建时间：2016-11-01
--------------------------------------------------------------------------------------------- */
function moveDiv(obj){
	var e = e||window.event;
	if(!isCatchDiv) return;
	obj.style.left =( e.x || e.clientX)-dragX+ "px";
	obj.style.top =( e.y || e.clientY )-dragY+ "px";
}

/**************************通用获取树形菜单的方法***********************************/
/* ---------------------------------------------------------------------------------------------
	功能：   打开通用树形目录的DIV层方法(注意：如果所传入的路径为空值，将默认打开菜单的树形目录)
	参数： _url 树目录数据装载的访问方法  oArray 返回值存放的对象数组
	返回：    
创建者：  Hunter
创建时间：2016-11-01
--------------------------------------------------------------------------------------------- */
function getCommonTree(_url,oArray) {
	//如果路径为空，则直接取菜单的树目录
	if(_url == null || _url == "")
		alert("路径错误");
	_url= _url.replace(rootPath, "");
	_url=rootPath+_url;
	var objfrm=document.getElementById("commonTreeViewDivFrm");
	lookupArry=oArray;
	if(objfrm == null || objfrm == "" || objfrm.src != _url )
		objfrm.src=_url;
	document.getElementById("commonTreeViewDiv").style.display="block";
	document.getElementById("commonRoofLayer").style.display="block";
}


/* ---------------------------------------------------------------------------------------------
	功能：   打开通用树形目录的DIV层方法(注意：如果所传入的路径为空值，将默认打开菜单的树形目录)
	参数： _url 树目录数据装载的访问方法  oArray 返回值存放的对象数组
	返回：    
创建者：  Hunter
创建时间：2016-11-01
--------------------------------------------------------------------------------------------- */
function getCommonTreeGrid(obj,_url,oArray) {
	//如果路径为空，则直接取菜单的树目录
	if(_url == null || _url == "")
		alert("路径错误");
	_url= _url.replace(rootPath, "");
	_url=rootPath+_url;
	var objfrm=document.getElementById("commonTreeViewDivFrm");
	var newArray=new Array();
	for(var i=0;i<oArray.length;i++)
	{
		if((obj.parentElement.parentElement.parentElement.rows.length-1)==1){
			newArray[i]=oArray[i];
		}else{
			index=obj.parentElement.parentElement.rowIndex-1;
			newArray[i]=oArray[i][index];
		}
	}
	lookupArry=newArray;
	if(objfrm == null || objfrm == "" || objfrm.src != _url )
		objfrm.src=_url;
	document.getElementById("commonTreeViewDiv").style.display="block";
	document.getElementById("commonRoofLayer").style.display="block";
}

/* ---------------------------------------------------------------------------------------------
	功能：    弹出树目录DIV层通用返回值调用函数
	参数：    retval——封装好的返回值
	返回：   （无）
创建者：  Hunter
创建时间：2016-11-1
--------------------------------------------------------------------------------------------- */
function divTreeViewRetVal(retval) {
	oArray=lookupArry;
	if(retval!=null && retval.length!=0 && retval != ""){	
		var backValue=retval.split(":");
		for (i = 0; i < oArray.length; i++) {
			if(oArray[i]!=null)
				oArray[i].value=backValue[i];
		}
	}
	doCloseTreeViewDiv();
}

/* ---------------------------------------------------------------------------------------------
	功能：    关闭弹出树目录DIV层
	返回：   （无）
创建者：  Hunter
创建时间：2016-11-1
--------------------------------------------------------------------------------------------- */	
function doCloseTreeViewDiv(){
	getById("commonTreeViewDiv").style.display="none";;
	getById("commonRoofLayer").style.display="none";
	
}

//删除左右两端的空格
function trim(str) { 
         return str.replace(/(^\s*)|(\s*$)/g, "").replace(/(^　*)|(　*$)/g, ""); //把空格替换为空 //把空格替换为空
    }
/* ---------------------------------------------------------------------------------------------
功能：    去掉所有空格和&nbsp;
参数：    str(原字符串)
返回：    去掉所有空格和&nbsp;后的str字符串
--------------------------------------------------------------------------------------------- */
function trimAll(str)
{
return str.replace(/[ ]/g,"");
}
//浮动图片
$(function () {
	$('.tip').mouseover(function () {
		var src = '';
		if (this.href == null || this.href ==''){
			src = this.src;
		}else{
			src=this.href;
		}
		var $tip = $('<div id="tip"><div class="t_box"><div><s><i></i></s><img src="' + src + '" /></div></div></div>');
		$('body').append($tip);       
		$('#tip').show('fast');     
		}).mouseout(function () {       $('#tip').remove();     }).mousemove(function (e) {       $('#tip').css({ "top": (e.pageY - 60) + "px", "left": (e.pageX + 30) + "px" })     })   
})