package com.cang.os.bean.sys;

import com.cang.os.bean.BaseBean;
import org.springframework.data.mongodb.core.mapping.Document;

/** 后台角色权限分配(ROLEPURVIEW) **/
@Document
public class Rolepurview extends BaseBean {

	private static final long serialVersionUID = 1L;
	
	//-----private-----
	private String id;   //主键
	private String sroleId;   //角色ID
	private String spurviewId;   //权限码ID
	
	//-----get set-----
	 /**主键*/
	 public String getId(){
		 return id;
	 }
	 /**主键*/
	 public void setId(String id){
		 this.id=id;
	 }
	 /**角色ID*/
	 public String getSroleId(){
		 return sroleId;
	 }
	 /**角色ID*/
	 public void setSroleId(String sroleId){
		 this.sroleId=sroleId;
	 }
	 /**权限码ID*/
	 public String getSpurviewId(){
		 return spurviewId;
	 }
	 /**权限码ID*/
	 public void setSpurviewId(String spurviewId){
		 this.spurviewId=spurviewId;
	 }
	

		
} 