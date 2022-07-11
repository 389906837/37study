package com.cang.os.bean.sys;
import com.cang.os.bean.BaseBean;
import org.springframework.data.mongodb.core.mapping.Document;

/** 用户角色表(OPERATORROLE) **/
@Document
public class Operatorrole extends BaseBean {

	private static final long serialVersionUID = 1L;
	
	//-----private-----
	private String id;   //主键
	private String soperatorId;   //操作员ID
	private String sroleId;   //角色ID
	
	//-----get set-----
	 /**主键*/
	 public String getId(){
		 return id;
	 }
	 /**主键*/
	 public void setId(String id){
		 this.id=id;
	 }
	 /**操作员ID*/
	 public String getSoperatorId(){
		 return soperatorId;
	 }
	 /**操作员ID*/
	 public void setSoperatorId(String soperatorId){
		 this.soperatorId=soperatorId;
	 }
	 /**角色ID*/
	 public String getSroleId(){
		 return sroleId;
	 }
	 /**角色ID*/
	 public void setSroleId(String sroleId){
		 this.sroleId=sroleId;
	 }
	

		
} 