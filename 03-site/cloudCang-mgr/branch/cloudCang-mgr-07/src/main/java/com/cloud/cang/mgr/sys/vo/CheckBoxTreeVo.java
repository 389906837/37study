package com.cloud.cang.mgr.sys.vo;
import java.util.ArrayList;
import java.util.List;

public class CheckBoxTreeVo {
	private String id;	
	private String text;
	private boolean leaf;
	private boolean expanded;
	public boolean isExpanded() {
		return this.expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	private List<CheckBoxTreeVo> children = new ArrayList<CheckBoxTreeVo>();
	public List<CheckBoxTreeVo> getChildren() {
		return this.children;
	}
	public void setChildren(List<CheckBoxTreeVo> children) {
		this.children = children;
	}
	private String href;  
	private boolean checked;
	public boolean isChecked() {
		return this.checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getHref() {
		return this.href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	//是否父节点
	private String bisroot;
	//父节点ID
	private String sparentid;
	private String iconCls;
    public String getIconCls() {
		return this.iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	
	public String getBisroot() {
        return bisroot;
    }
    public void setBisroot(String bisroot) {
        this.bisroot = bisroot;
    }
    public String getSparentid() {
		return this.sparentid;
	}
	public void setSparentid(String sparentid) {
		this.sparentid = sparentid;
	}
	
	
	public String getId() {
		return this.id;
	}
	
	public String getText() {
		return this.text;
	}
	
	public boolean isLeaf() {
		return this.leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 检查儿子有没有被选中，儿子被选中了，就选中自己，如果一个儿子也没有被选中，撤销自己
	 */
	public void checkall(){
		if(this.isLeaf())
			return;
		int size=children.size();
		CheckBoxTreeVo node;
		boolean ch=false;
		for(int i=size-1;i>=0;i--){
			node=children.get(i);
			node.checkall();
			if(node.checked)
				ch=true;
		}
		checked=ch;
		
	}
	
	public void removeNoRight(){
    	int size=children.size();
    	CheckBoxTreeVo node;
    	for(int i=size-1;i>=0;i--){
    		node=children.get(i);
    		node.removeNoRight();
    		if((!node.isLeaf())&&node.getChildren().size()==0)
    		{	 
    			 children.remove(i);
    		}
    	}
    }
}
