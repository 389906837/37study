package com.cang.os.bean.sys;

import com.cang.os.bean.BaseBean;
import org.springframework.data.mongodb.core.mapping.Document;

/** 角色表(ROLE) **/
@Document
public class Role extends BaseBean {

	private static final long serialVersionUID = 1L;
	
	//-----private-----
	private String id;   //主键
	private String sroleName;   //角色名称
	private String sremark;   //备注
	
	//-----get set-----
	 /**主键*/
	 public String getId(){
		 return id;
	 }
	 /**主键*/
	 public void setId(String id){
		 this.id=id;
	 }
	 /**角色名称*/
	 public String getSroleName(){
		 return sroleName;
	 }
	 /**角色名称*/
	 public void setSroleName(String sroleName){
		 this.sroleName=sroleName;
	 }
	 /**备注*/
	 public String getSremark(){
		 return sremark;
	 }
	 /**备注*/
	 public void setSremark(String sremark){
		 this.sremark=sremark;
	 }
	

		
} 