package com.cloud.cang.rec.common.vo;

import com.cloud.cang.generic.GenericEntity;


/**
 * select2 日期选择Vo
 * @author zhouhong
 */
public class SelectVo extends GenericEntity {


	private String id;
	private String text;
	private String parentName;//父菜单名称

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	@Override
	public String toString() {
		return "SelectVo{" +
				"id='" + id + '\'' +
				", text='" + text + '\'' +
				", parentName='" + parentName + '\'' +
				'}';
	}
}
