package com.cloud.cang.model.wz;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 网站关键字(MYSQL)(WZ_KEY_WORDS) **/
public class KeyWords extends GenericEntity  {

	private static final long serialVersionUID = 1L;
	
	/*主键ID*/
	private String id;
	/*主键ID*/
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		 this.id= id;
	}
	
	/* 是否可修改:是否可供用户修改 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 添加人 */
	private String saddUser;
	
	public String getSaddUser(){
		return saddUser;
	}
	
	public void setSaddUser(String saddUser){
		this.saddUser= saddUser;
	}
	/* 描述 */
	private String sdescription;
	
	public String getSdescription(){
		return sdescription;
	}
	
	public void setSdescription(String sdescription){
		this.sdescription= sdescription;
	}
	/* 关键字 */
	private String skeyWord;
	
	public String getSkeyWord(){
		return skeyWord;
	}
	
	public void setSkeyWord(String skeyWord){
		this.skeyWord= skeyWord;
	}
	/* 页面名称 */
	private String spageName;
	
	public String getSpageName(){
		return spageName;
	}
	
	public void setSpageName(String spageName){
		this.spageName= spageName;
	}
	/* 页面编号 */
	private String spageNo;
	
	public String getSpageNo(){
		return spageNo;
	}
	
	public void setSpageNo(String spageNo){
		this.spageNo= spageNo;
	}
	/* 页面Url */
	private String spageUrl;
	
	public String getSpageUrl(){
		return spageUrl;
	}
	
	public void setSpageUrl(String spageUrl){
		this.spageUrl= spageUrl;
	}
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 预留字段 */
	private String sreserve;
	
	public String getSreserve(){
		return sreserve;
	}
	
	public void setSreserve(String sreserve){
		this.sreserve= sreserve;
	}
	/* 标题 */
	private String stitle;
	
	public String getStitle(){
		return stitle;
	}
	
	public void setStitle(String stitle){
		this.stitle= stitle;
	}
	/* 修改人 */
	private String supdateUser;
	
	public String getSupdateUser(){
		return supdateUser;
	}
	
	public void setSupdateUser(String supdateUser){
		this.supdateUser= supdateUser;
	}
	/* 添加日期 */
	private Date taddTime;
	
	public Date getTaddTime(){
		return taddTime;
	}
	
	public void setTaddTime(Date taddTime){
		this.taddTime= taddTime;
	}
	/* 修改日期 */
	private Date tupdateTime;
	
	public Date getTupdateTime(){
		return tupdateTime;
	}
	
	public void setTupdateTime(Date tupdateTime){
		this.tupdateTime= tupdateTime;
	}

		
}