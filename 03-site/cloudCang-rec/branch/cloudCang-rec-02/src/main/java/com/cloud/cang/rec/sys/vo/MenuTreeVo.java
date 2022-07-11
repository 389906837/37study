package com.cloud.cang.rec.sys.vo;
import java.util.ArrayList;
import java.util.List;

public class MenuTreeVo {

	private List<MenuTreeVo> nodes = new ArrayList<MenuTreeVo>();
	private String href;//链接
	private String icon;//图标
	private String text;//名称
	private String bisroot;//是否父节点
	private String sparentId;//父节点ID
	private String id;

	public List<MenuTreeVo> getNodes() {
		return nodes;
	}

	public void setNodes(List<MenuTreeVo> nodes) {
		this.nodes = nodes;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
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

	@Override
	public String toString() {
		return "MenuTreeVo{" +
				"nodes=" + nodes +
				", href='" + href + '\'' +
				", icon='" + icon + '\'' +
				", text='" + text + '\'' +
				", bisroot='" + bisroot + '\'' +
				", sparentId='" + sparentId + '\'' +
				", id='" + id + '\'' +
				'}';
	}
}
