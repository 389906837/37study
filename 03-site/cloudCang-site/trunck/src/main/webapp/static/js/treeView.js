/* ------------------------------------------------------------------------------------------------
	功能：树控件
	作者：Peter
	日期：2006-10-12
	版本：version1.0
	文件：treeView.js
	Copyright 2010 MySteelSoft All Rights Reserved.
   ------------------------------------------------------------------------------------------------ */
   
/***********************************************************************
*功能：	设置相对路径或者绝对路径
*参数：	prefix-它的值可以是相对路径"../"或绝对路径
***********************************************************************/
var PathPrefix = "../"
function setPathPrefix(prefix){
	if(prefix=="" || prefix == null)
	    PathPrefix = "../";
        else
            PathPrefix = prefix;
}

function getPathPrefix(){
	return getContextPath()+'/';
}

/**
 * 获取上下文路径
 */
function getContextPath() 
{ 
		var contextPath = document.location.pathname; 
		var index =contextPath.substr(1).indexOf("/"); 
		contextPath = contextPath.substr(0,index+1); 
		delete index; 
		contextPath = contextPath.replace(/\/mgr/,'');
		return contextPath; 
} 

document.oncontextmenu=new Function("event.returnValue=false;");
var Icon={
		open		:'static/images/tree/folderopen.gif',
		close		:'static/images/tree/folder.gif',
		file		:'static/images/tree/file.gif',
		join		:'static/images/tree/T.gif',
		joinbottom	:'static/images/tree/L.gif',
		plus		:'static/images/tree/Tplus.gif',
		plusbottom	:'static/images/tree/Lplus.gif',
		minus		:'static/images/tree/Tminus.gif',
		minusbottom	:'static/images/tree/Lminus.gif',
		blank		:'static/images/tree/blank.gif',
		line		:'static/images/tree/I.gif'
		
};


function PreLoad(){
	for(i in Icon){
	var tem=Icon[i]
	Icon[i]=new Image()
	Icon[i].src=getPathPrefix()+tem;  //getPathPrefix() is defined in commons.js
	}
}
PreLoad()

function TreeView(obj,target,ExpandOne){
	this.obj=obj;
	this.Tree=new Node(0)
	this.Root=null
	this.Nodes=new Array()
	this.target=target?target:"_blank";
	this.ie=document.all?1:0;
	this.ExpandOne=ExpandOne?1:0
}

function Node(id,pid,txt,ondbclk,link,title,target,xicon){
	this.Index=null;
	this.id=id;
	this.pid=pid
	this.parent=null
	this.txt=txt?txt:"New Item"
	this.link=link
	this.title=title?title:this.txt
	this.target=target
	this.xicon=xicon
	this.indent=""
	this.hasChild=false;
	this.lastNode=false;
	this.open=false
	this.ondbclk = ondbclk;
}

TreeView.prototype.add=function(id,pid,txt,ondbclk,link,title,target,xicon){
	target=target?target:this.target
	var nNode=new Node(id,pid,txt,ondbclk,link,title,target,xicon);
	if(pid==this.Tree.id)nNode.open=true;
	nNode.Index=this.Nodes.length
	this.Nodes[this.Nodes.length]=nNode
}

TreeView.prototype.InitNode=function(oNode){
	var last;
	for(i=0;i<this.Nodes.length;i++){
		if(this.Nodes[i].pid==oNode.id){this.Nodes[i].parent=oNode;oNode.hasChild=true}
		if(this.Nodes[i].pid==oNode.pid)last=this.Nodes[i].id
	}
	if(last==oNode.id)oNode.lastNode=true
}

TreeView.prototype.DrawLine=function(pNode,oNode){
		oNode.indent=pNode.indent+(oNode.pid!=this.Tree.id&&oNode.pid!=this.Root.id?("<img align='absmiddle' src='"+(pNode.lastNode?Icon.blank.src:Icon.line.src)+"'>"):'')
}

TreeView.prototype.DrawNode=function(nNode,pNode){
	var str=""
	var indents=""
	var nid=nNode.id
	var nIndex=nNode.Index;
	this.DrawLine(pNode,nNode)
	if(nNode.hasChild)
	indents=nNode.indent+(this.Tree.id!=nNode.pid?("<img align='absmiddle'  onclick='"+this.obj+".Toggle("+nIndex+",this);event.cancelBubble = true' src='"+(nNode.lastNode?(nNode.open?Icon.minusbottom.src:Icon.plusbottom.src):(nNode.open?Icon.minus.src:Icon.plus.src))+"' id='icon"+nid+"' border=0>"):"")
	else
	indents=nNode.indent+(nNode.pid==this.Tree.id?'':("<img align='absmiddle' src='"+(nNode.lastNode?Icon.joinbottom.src:Icon.join.src)+"'>"))
	indents+="<img id='folder"+nid+"' align='absmiddle' src='"+(nNode.xicon?nNode.xicon:(nNode.hasChild?(nNode.open?Icon.open.src:Icon.close.src):Icon.file.src))+"'>"
	
	//str+="<div class='node' onclick='"+this.obj+".Toggle("+nIndex+",this);' ><nobr>"+indents+this.DrawLink(nNode)+"</nobr></div>"
	if(nNode.hasChild){
	str+="<div class='node' onclick='"+this.obj+".Toggle("+nIndex+",this);event.cancelBubble = true' ><nobr>"+indents+this.DrawLink(nNode)+"</nobr></div>"
	str+="<div id='Child"+nid+"' style='display:"+(nNode.open?"":"none")+"'>"
	str+=this.DrawTree(nNode)
	str+="</div>"
        }
        else
	str+="<div class='node'><nobr>"+indents+this.DrawLink(nNode)+"</nobr></div>"
	return str;
}

TreeView.prototype.DrawTree=function(pNode){
	var str=""
	for(var i=0;i<this.Nodes.length;i++)
		if(this.Nodes[i].pid==pNode.id)
		str+=this.DrawNode(this.Nodes[i],pNode)
	return str
}

TreeView.prototype.DrawLink=function(oNode){
	var str=""	
	str+=" <span id='NodeItem"+oNode.id+"' tabindex='1' title='"+oNode.title+"'onDblClick='"+oNode.ondbclk+"' onclick='"+this.obj+".Select(this)'>"+(oNode.link?("<a href='"+oNode.link+"'>"+oNode.txt+"</a>"):oNode.txt)+"</span>"
	return str
}


TreeView.prototype.toString=function(){
	var str=""
	for(var i=0;i<this.Nodes.length;i++){
	if(!this.Root)
	if(this.Nodes[i].pid==this.Tree.id)this.Root=this.Nodes[i]
	this.InitNode(this.Nodes[i])
	}
	str+=this.DrawTree(this.Tree)
	return str
}

TreeView.prototype.Toggle=function(nIndex,o){
	var nNode=this.Nodes[nIndex]
	o.blur();
	if(!nNode.hasChild)return;
	if(nNode.open)this.Collapse(nNode)
	else this.Expand(nNode)
}

TreeView.prototype.Expand=function(nNode){
	var nid=nNode.id
	var node=this.GetElm('Child'+nid)
	var oicon=this.GetElm('icon'+nid)
	node.style.display=''
	var img1=new Image()
	img1.src=(nNode.lastNode?Icon.minusbottom.src:Icon.minus.src)
	if(oicon)oicon.src=img1.src
	if(!nNode.xicon){
		var img2=new Image()
		img2.src=Icon.open.src
		this.GetElm("folder"+nid).src=img2.src
	}
	if(this.ExpandOne)this.CloseOtherItem(nNode);
	nNode.open=true
}

TreeView.prototype.Collapse=function(nNode){
	var nid=nNode.id
	var node=this.GetElm('Child'+nid)
	var oicon=this.GetElm('icon'+nid)
	node.style.display='none'
	var img1=new Image()
	img1.src=(nNode.lastNode?Icon.plusbottom.src:Icon.plus.src)
	if(oicon)oicon.src=img1.src
	if(!nNode.xicon){
		var img2=new Image()
		img2.src=Icon.close.src
		this.GetElm("folder"+nid).src=img2.src
	}
	nNode.open=false
}

TreeView.prototype.ExpandAll=function(){
	for(i=0;i<this.Nodes.length;i++)
		if(this.Nodes[i].hasChild)this.Expand(this.Nodes[i])
}

TreeView.prototype.CollapseAll=function(){
	for(i=0;i<this.Nodes.length;i++)
		if(this.Nodes[i].hasChild&&this.Nodes[i].pid!=this.Tree.id&&this.Nodes[i]!=this.Root)this.Collapse(this.Nodes[i])
}

TreeView.prototype.CloseOtherItem=function(nNode){
	for(var i=0;i<this.Nodes.length;i++)
		if(this.Nodes[i].pid==nNode.pid&&this.Nodes[i].open){this.Collapse(this.Nodes[i]);break}
}
TreeView.prototype.Select=function(objNode,flag){
	if(!this.current)this.current=objNode
	this.current.className=""
	objNode.className="highlight"
	this.current=objNode
	var a=objNode.getElementsByTagName("A")
	if(a.length!=0&&flag)window.open(a[0].href,a[0].target);
	if(this.ie)objNode.focus()
}

TreeView.prototype.openTo=function(nIndex){
	if(nIndex<0||nIndex>=this.Nodes.length)return;
	var nNode=this.Nodes[nIndex]
	while(nNode){
		if(!nNode.open&&nNode.hasChild)this.Expand(nNode)
		nNode=nNode.parent
	}
	this.Select(this.GetElm("NodeItem"+this.Nodes[nIndex].id),true)
}

TreeView.prototype.GetElm=function(uid){
	return document.getElementById(uid)
}
