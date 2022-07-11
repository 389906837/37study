package com.cang.os.bean.sys;

import java.util.Date;

import com.cang.os.bean.BaseBean;
import org.springframework.data.mongodb.core.mapping.Document;

/** 后台菜单管理表(MENU) **/
@Document
public class Menu extends BaseBean {

	private static final long serialVersionUID = 1L;
	
	//-----private-----
	private String id;   //主键
	private String smenuNo;   //编号
	private String sname;   //菜单名称
	private String stitle;   //菜单标题
	private Integer bisRoot;   //是否父节点
	private Integer bisLeaf;   //是否叶子节点
	private String sparentId;   //父节点ID
	private String sparentName;//父节点名称
	private String spurId;   //权限ID
	private Integer imenuLevel;   //菜单阶层
	private String simagePath;   //菜单图标
	private String smenuPath;   //菜单路径
	private Integer bisDisplay;   //是否显示菜单
	private String sremark;   //备注
	private Integer isort;   //排序编号
	private Date daddDate;   //添加日期
	private String saddOperator;   //添加人(用户名)
	private Date dmodifyDate;   //修改日期
	private String smodifyOperator;   //修改人(用户名)
	private String iconCls;
	
	
	
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	//-----get set-----
	 /**主键*/
	 public String getId(){
		 return id;
	 }
	 /**主键*/
	 public void setId(String id){
		 this.id=id;
	 }
	 /**编号*/
	 public String getSmenuNo(){
		 return smenuNo;
	 }
	 /**编号*/
	 public void setSmenuNo(String smenuNo){
		 this.smenuNo=smenuNo;
	 }
	 /**菜单名称*/
	 public String getSname(){
		 return sname;
	 }
	 /**菜单名称*/
	 public void setSname(String sname){
		 this.sname=sname;
	 }
	 /**菜单标题*/
	 public String getStitle(){
		 return stitle;
	 }
	 /**菜单标题*/
	 public void setStitle(String stitle){
		 this.stitle=stitle;
	 }
	 /**是否父节点*/
	 public Integer getBisRoot(){
		 return bisRoot;
	 }
	 /**是否父节点*/
	 public void setBisRoot(Integer bisRoot){
		 this.bisRoot=bisRoot;
	 }
	 /**是否叶子节点*/
	 public Integer getBisLeaf(){
		 return bisLeaf;
	 }
	 /**是否叶子节点*/
	 public void setBisLeaf(Integer bisLeaf){
		 this.bisLeaf=bisLeaf;
	 }
	 /**父节点ID*/
	 public String getSparentId(){
		 return sparentId;
	 }
	 /**父节点ID*/
	 public void setSparentId(String sparentId){
		 this.sparentId=sparentId;
	 }
	 /**权限ID*/
	 public String getSpurId(){
		 return spurId;
	 }
	 /**权限ID*/
	 public void setSpurId(String spurId){
		 this.spurId=spurId;
	 }
	 /**菜单阶层*/
	 public Integer getImenuLevel(){
		 return imenuLevel;
	 }
	 /**菜单阶层*/
	 public void setImenuLevel(Integer imenuLevel){
		 this.imenuLevel=imenuLevel;
	 }
	 /**菜单图标*/
	 public String getSimagePath(){
		 return simagePath;
	 }
	 /**菜单图标*/
	 public void setSimagePath(String simagePath){
		 this.simagePath=simagePath;
	 }
	 /**菜单路径*/
	 public String getSmenuPath(){
		 return smenuPath;
	 }
	 /**菜单路径*/
	 public void setSmenuPath(String smenuPath){
		 this.smenuPath=smenuPath;
	 }
	 /**是否显示菜单*/
	 public Integer getBisDisplay(){
		 return bisDisplay;
	 }
	 /**是否显示菜单*/
	 public void setBisDisplay(Integer bisDisplay){
		 this.bisDisplay=bisDisplay;
	 }
	 /**备注*/
	 public String getSremark(){
		 return sremark;
	 }
	 /**备注*/
	 public void setSremark(String sremark){
		 this.sremark=sremark;
	 }
	 /**排序编号*/
	 public Integer getIsort(){
		 return isort;
	 }
	 /**排序编号*/
	 public void setIsort(Integer isort){
		 this.isort=isort;
	 }
	 /**添加日期*/
	 public Date getDaddDate(){
		 return daddDate;
	 }
	 /**添加日期*/
	 public void setDaddDate(Date daddDate){
		 this.daddDate=daddDate;
	 }
	 /**添加人(用户名)*/
	 public String getSaddOperator(){
		 return saddOperator;
	 }
	 /**添加人(用户名)*/
	 public void setSaddOperator(String saddOperator){
		 this.saddOperator=saddOperator;
	 }
	 /**修改日期*/
	 public Date getDmodifyDate(){
		 return dmodifyDate;
	 }
	 /**修改日期*/
	 public void setDmodifyDate(Date dmodifyDate){
		 this.dmodifyDate=dmodifyDate;
	 }
	 /**修改人(用户名)*/
	 public String getSmodifyOperator(){
		 return smodifyOperator;
	 }
	 /**修改人(用户名)*/
	 public void setSmodifyOperator(String smodifyOperator){
		 this.smodifyOperator=smodifyOperator;
	 }
	public String getSparentName() {
		return sparentName;
	}
	public void setSparentName(String sparentName) {
		this.sparentName = sparentName;
	}
	 
	 
	

		
} 