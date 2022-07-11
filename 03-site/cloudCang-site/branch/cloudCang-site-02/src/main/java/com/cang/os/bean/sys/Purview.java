package com.cang.os.bean.sys;

import java.util.Date;

import com.cang.os.bean.BaseBean;
import org.springframework.data.mongodb.core.mapping.Document;

/** 后台权限码表(PURVIEW) **/
@Document
public class Purview extends BaseBean {

	private static final long serialVersionUID = 1L;
	
	//-----private-----
	private String id;   //主键
	private String ssystemCode;   //系统代码
	private String spurNo;   //权限编号
	private String spurCode;   //权限码:根据用户登录时判断页面的操作时否显示
	private String spurName;   //权限名称
	private String sparentName;   //父菜单名称
	private String sparentId;   //父菜单ID
	private String surlPath;   //访问路径
	private String sdescription;   //权限说明
	private String sjpName;   //简拼名
	private String spyName;   //全拼名
	private Date daddDate;   //添加日期
	
	//-----get set-----
	 /**主键*/
	 public String getId(){
		 return id;
	 }
	 /**主键*/
	 public void setId(String id){
		 this.id=id;
	 }
	 /**系统代码*/
	 public String getSsystemCode(){
		 return ssystemCode;
	 }
	 /**系统代码*/
	 public void setSsystemCode(String ssystemCode){
		 this.ssystemCode=ssystemCode;
	 }
	 /**权限编号*/
	 public String getSpurNo(){
		 return spurNo;
	 }
	 /**权限编号*/
	 public void setSpurNo(String spurNo){
		 this.spurNo=spurNo;
	 }
	 /**权限码:根据用户登录时判断页面的操作时否显示*/
	 public String getSpurCode(){
		 return spurCode;
	 }
	 /**权限码:根据用户登录时判断页面的操作时否显示*/
	 public void setSpurCode(String spurCode){
		 this.spurCode=spurCode;
	 }
	 /**权限名称*/
	 public String getSpurName(){
		 return spurName;
	 }
	 /**权限名称*/
	 public void setSpurName(String spurName){
		 this.spurName=spurName;
	 }
	 /**父菜单名称*/
	 public String getSparentName(){
		 return sparentName;
	 }
	 /**父菜单名称*/
	 public void setSparentName(String sparentName){
		 this.sparentName=sparentName;
	 }
	 /**父菜单ID*/
	 public String getSparentId(){
		 return sparentId;
	 }
	 /**父菜单ID*/
	 public void setSparentId(String sparentId){
		 this.sparentId=sparentId;
	 }
	 /**访问路径*/
	 public String getSurlPath(){
		 return surlPath;
	 }
	 /**访问路径*/
	 public void setSurlPath(String surlPath){
		 this.surlPath=surlPath;
	 }
	 /**权限说明*/
	 public String getSdescription(){
		 return sdescription;
	 }
	 /**权限说明*/
	 public void setSdescription(String sdescription){
		 this.sdescription=sdescription;
	 }
	 /**简拼名*/
	 public String getSjpName(){
		 return sjpName;
	 }
	 /**简拼名*/
	 public void setSjpName(String sjpName){
		 this.sjpName=sjpName;
	 }
	 /**全拼名*/
	 public String getSpyName(){
		 return spyName;
	 }
	 /**全拼名*/
	 public void setSpyName(String spyName){
		 this.spyName=spyName;
	 }
	 /**添加日期*/
	 public Date getDaddDate(){
		 return daddDate;
	 }
	 /**添加日期*/
	 public void setDaddDate(Date daddDate){
		 this.daddDate=daddDate;
	 }
	

		
} 