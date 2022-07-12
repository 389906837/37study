package com.cloud.cang.mgr.sys.vo;
import java.util.ArrayList;
import java.util.List;

public class TreeVo{
	private String attributes;	
	//是否父节点
	private String bisroot;
	private List<TreeVo> children = new ArrayList<TreeVo>();
	private boolean expanded;
	private String href;
	private String iconCls;
	private String id;
	private boolean leaf;  
	//父节点ID
	private String sparentid;

	private String text;
	public String getAttributes() {
		return attributes;
	}
	public String getBisroot() {
		return this.bisroot;
	}
	public List<TreeVo> getChildren() {
		return this.children;
	}
	public String getHref() {
		return this.href;
		
	}
	public String getIconCls() {
		return this.iconCls;
	}
	public String getId() {
		return this.id;
	}
	public String getSparentid() {
		return this.sparentid;
	}
	public String getText() {
		return this.text;
	}
	public boolean isExpanded() {
		return this.expanded;
	}
	public boolean isLeaf() {
		return this.leaf;
	}
	public void removeNoRight(){
    	int size=children.size();
    	TreeVo node;
    	for(int i=size-1;i>=0;i--){
    		node=children.get(i);
    		node.removeNoRight();
    		if((!node.isLeaf())&&node.getChildren().size()==0)
    		{	 
    			 children.remove(i);
    		}
    	}
    }
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
	public void setBisroot(String bisroot) {
		this.bisroot = bisroot;
	}
	public void setChildren(List<TreeVo> children) {
		this.children = children;
	}
	
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	
	public void setHref(String href) {
		this.href = href;
	}
	
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public void setSparentid(String sparentid) {
		this.sparentid = sparentid;
	}
    public void setText(String text) {
		this.text = text;
	}
	
	
}
