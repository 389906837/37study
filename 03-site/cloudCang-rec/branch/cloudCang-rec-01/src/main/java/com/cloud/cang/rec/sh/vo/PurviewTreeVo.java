package com.cloud.cang.rec.sh.vo;

import java.util.ArrayList;
import java.util.List;

public class PurviewTreeVo {

	private List<PurviewTreeVo> nodes = new ArrayList<PurviewTreeVo>();
	private String text;//权限名称
	private String bisroot;//是否父节点
	private String sparentId;//父节点ID
	private String menuName;//菜单名称
	private String id;//权限ID
	private Integer isSelect;//是否选择


	public List<PurviewTreeVo> getNodes() {
		return nodes;
	}

	public void setNodes(List<PurviewTreeVo> nodes) {
		this.nodes = nodes;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getBisroot() {
		return bisroot;
	}

	public void setBisroot(String bisroot) {
		this.bisroot = bisroot;
	}

	public String getSparentId() {
		return sparentId;
	}

	public void setSparentId(String sparentId) {
		this.sparentId = sparentId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getIsSelect() {
		return isSelect;
	}

	public void setIsSelect(Integer isSelect) {
		this.isSelect = isSelect;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	@Override
	public String toString() {
		return "PurviewTreeVo{" +
				"nodes=" + nodes +
				", text='" + text + '\'' +
				", bisroot='" + bisroot + '\'' +
				", sparentId='" + sparentId + '\'' +
				", menuName='" + menuName + '\'' +
				", id='" + id + '\'' +
				", isSelect=" + isSelect +
				'}';
	}
}
