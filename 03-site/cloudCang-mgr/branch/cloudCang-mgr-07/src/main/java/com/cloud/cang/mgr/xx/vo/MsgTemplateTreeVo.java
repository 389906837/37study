package com.cloud.cang.mgr.xx.vo;

import java.util.ArrayList;
import java.util.List;

public class MsgTemplateTreeVo {

	private List<MsgTemplateTreeVo> nodes = new ArrayList<MsgTemplateTreeVo>();
	private String href;//链接
	private String text;//名称
	private String smainId;//模板主表ID
	private String id;

	public List<MsgTemplateTreeVo> getNodes() {
		return nodes;
	}

	public void setNodes(List<MsgTemplateTreeVo> nodes) {
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSmainId() {
		return smainId;
	}

	public void setSmainId(String smainId) {
		this.smainId = smainId;
	}

	@Override
	public String toString() {
		return "MsgTemplateTreeVo{" +
				"href='" + href + '\'' +
				", text='" + text + '\'' +
				", smainId='" + smainId + '\'' +
				", id='" + id + '\'' +
				'}';
	}
}
