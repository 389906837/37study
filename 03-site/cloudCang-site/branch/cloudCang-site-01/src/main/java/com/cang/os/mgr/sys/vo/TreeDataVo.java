/**
 * 
 */
package com.cang.os.mgr.sys.vo;

/**
 * @author zhouhong
 *
 */
public class TreeDataVo {
	
	private String id;
	
	private String pId;
	
	private String name;
	
	private boolean open = true;
	
	private boolean checked = false;
	
	private Integer isLeaf = 0;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Integer getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}
	
}
