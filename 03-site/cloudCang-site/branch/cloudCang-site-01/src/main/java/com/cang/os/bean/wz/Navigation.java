package com.cang.os.bean.wz;

import java.util.Date;

import com.cang.os.bean.BaseBean;

/** 文章栏目表(NAVIGATION) **/
public class Navigation extends BaseBean {

	private static final long serialVersionUID = 1L;
	
	//-----private-----
	private String id;   //主键ID
	private String sparentId;   //父栏目ID
	private String sparentName;//父栏目名称
	private String scode;   //编码
	private String sname;   //栏目名称
	private String sremark;   //备注
	private Integer isort;   //排序号
	private Integer iisDefault;   //是否默认栏目
	private Integer idefaultView;   //默认显示条数
	private Integer idelFlag;   //是否可修改:是否可供用户修改
	private Date taddTime;   //添加日期
	private String saddUser;   //添加人
	private Date tupdateTime;   //修改日期
	private String supateUser;   //修改人
	
	//-----get set-----
	 /**主键ID*/
	 public String getId(){
		 return id;
	 }
	 /**主键ID*/
	 public void setId(String id){
		 this.id=id;
	 }
	 /**父栏目ID*/
	 public String getSparentId(){
		 return sparentId;
	 }
	 /**父栏目ID*/
	 public void setSparentId(String sparentId){
		 this.sparentId=sparentId;
	 }
	 /**编码*/
	 public String getScode(){
		 return scode;
	 }
	 /**编码*/
	 public void setScode(String scode){
		 this.scode=scode;
	 }
	 /**栏目名称*/
	 public String getSname(){
		 return sname;
	 }
	 /**栏目名称*/
	 public void setSname(String sname){
		 this.sname=sname;
	 }
	 /**备注*/
	 public String getSremark(){
		 return sremark;
	 }
	 /**备注*/
	 public void setSremark(String sremark){
		 this.sremark=sremark;
	 }
	 /**排序号*/
	 public Integer getIsort(){
		 return isort;
	 }
	 /**排序号*/
	 public void setIsort(Integer isort){
		 this.isort=isort;
	 }
	 /**是否默认栏目*/
	 public Integer getIisDefault(){
		 return iisDefault;
	 }
	 /**是否默认栏目*/
	 public void setIisDefault(Integer iisDefault){
		 this.iisDefault=iisDefault;
	 }
	 /**默认显示条数*/
	 public Integer getIdefaultView(){
		 return idefaultView;
	 }
	 /**默认显示条数*/
	 public void setIdefaultView(Integer idefaultView){
		 this.idefaultView=idefaultView;
	 }
	 /**是否可修改:是否可供用户修改*/
	 public Integer getIdelFlag(){
		 return idelFlag;
	 }
	 /**是否可修改:是否可供用户修改*/
	 public void setIdelFlag(Integer idelFlag){
		 this.idelFlag=idelFlag;
	 }
	 /**添加日期*/
	 public Date getTaddTime(){
		 return taddTime;
	 }
	 /**添加日期*/
	 public void setTaddTime(Date taddTime){
		 this.taddTime=taddTime;
	 }
	 /**添加人*/
	 public String getSaddUser(){
		 return saddUser;
	 }
	 /**添加人*/
	 public void setSaddUser(String saddUser){
		 this.saddUser=saddUser;
	 }
	 /**修改日期*/
	 public Date getTupdateTime(){
		 return tupdateTime;
	 }
	 /**修改日期*/
	 public void setTupdateTime(Date tupdateTime){
		 this.tupdateTime=tupdateTime;
	 }
	 /**修改人*/
	 public String getSupateUser(){
		 return supateUser;
	 }
	 /**修改人*/
	 public void setSupateUser(String supateUser){
		 this.supateUser=supateUser;
	 }
	public String getSparentName() {
		return sparentName;
	}
	public void setSparentName(String sparentName) {
		this.sparentName = sparentName;
	}
	
} 