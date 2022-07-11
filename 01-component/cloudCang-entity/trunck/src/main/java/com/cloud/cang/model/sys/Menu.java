package com.cloud.cang.model.sys;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 后台菜单管理表(SYS_MENU) **/
public class Menu extends GenericEntity  {

	private static final long serialVersionUID = 1L;
	
	/*主键*/
	private String id;
	/*主键*/
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		 this.id= id;
	}
	
	/* 是否显示菜单 */
	private Integer bisDisplay;
	
	public Integer getBisDisplay(){
		return bisDisplay;
	}
	
	public void setBisDisplay(Integer bisDisplay){
		this.bisDisplay= bisDisplay;
	}
	/* 是否叶子节点 */
	private Integer bisLeaf;
	
	public Integer getBisLeaf(){
		return bisLeaf;
	}
	
	public void setBisLeaf(Integer bisLeaf){
		this.bisLeaf= bisLeaf;
	}
	/* 是否父节点 */
	private Integer bisRoot;
	
	public Integer getBisRoot(){
		return bisRoot;
	}
	
	public void setBisRoot(Integer bisRoot){
		this.bisRoot= bisRoot;
	}
	/* 添加日期 */
	private Date daddDate;
	
	public Date getDaddDate(){
		return daddDate;
	}
	
	public void setDaddDate(Date daddDate){
		this.daddDate= daddDate;
	}
	/* 修改日期 */
	private Date dmodifyDate;
	
	public Date getDmodifyDate(){
		return dmodifyDate;
	}
	
	public void setDmodifyDate(Date dmodifyDate){
		this.dmodifyDate= dmodifyDate;
	}
	/* 菜单阶层 */
	private Integer imenuLevel;
	
	public Integer getImenuLevel(){
		return imenuLevel;
	}
	
	public void setImenuLevel(Integer imenuLevel){
		this.imenuLevel= imenuLevel;
	}
	/* 排序编号 */
	private Integer isort;
	
	public Integer getIsort(){
		return isort;
	}
	
	public void setIsort(Integer isort){
		this.isort= isort;
	}
	/* 添加人(用户名) */
	private String saddOperator;
	
	public String getSaddOperator(){
		return saddOperator;
	}
	
	public void setSaddOperator(String saddOperator){
		this.saddOperator= saddOperator;
	}
	/* 菜单图标 */
	private String simagePath;
	
	public String getSimagePath(){
		return simagePath;
	}
	
	public void setSimagePath(String simagePath){
		this.simagePath= simagePath;
	}
	/* 编号 */
	private String smenuNo;
	
	public String getSmenuNo(){
		return smenuNo;
	}
	
	public void setSmenuNo(String smenuNo){
		this.smenuNo= smenuNo;
	}
	/* 菜单路径 */
	private String smenuPath;
	
	public String getSmenuPath(){
		return smenuPath;
	}
	
	public void setSmenuPath(String smenuPath){
		this.smenuPath= smenuPath;
	}
	/* 修改人(用户名) */
	private String smodifyOperator;
	
	public String getSmodifyOperator(){
		return smodifyOperator;
	}
	
	public void setSmodifyOperator(String smodifyOperator){
		this.smodifyOperator= smodifyOperator;
	}
	/* 菜单名称 */
	private String sname;
	
	public String getSname(){
		return sname;
	}
	
	public void setSname(String sname){
		this.sname= sname;
	}
	/* 父节点ID */
	private String sparentId;
	
	public String getSparentId(){
		return sparentId;
	}
	
	public void setSparentId(String sparentId){
		this.sparentId= sparentId;
	}
	/* 权限ID */
	private String spurId;
	
	public String getSpurId(){
		return spurId;
	}
	
	public void setSpurId(String spurId){
		this.spurId= spurId;
	}
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 分属系统 */
	private String ssystemSource;
	
	public String getSsystemSource(){
		return ssystemSource;
	}
	
	public void setSsystemSource(String ssystemSource){
		this.ssystemSource= ssystemSource;
	}
	/* 菜单标题 */
	private String stitle;
	
	public String getStitle(){
		return stitle;
	}
	
	public void setStitle(String stitle){
		this.stitle= stitle;
	}

		
}