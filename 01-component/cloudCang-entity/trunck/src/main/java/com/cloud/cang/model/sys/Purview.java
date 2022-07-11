package com.cloud.cang.model.sys;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 后台权限码表(SYS_PURVIEW) **/
public class Purview extends GenericEntity  {

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
	
	/* 添加日期 */
	private Date daddDate;
	
	public Date getDaddDate(){
		return daddDate;
	}
	
	public void setDaddDate(Date daddDate){
		this.daddDate= daddDate;
	}
	/* 是否商户可用 0否 1是 */
	private Integer iisMerchantUsed;
	
	public Integer getIisMerchantUsed(){
		return iisMerchantUsed;
	}
	
	public void setIisMerchantUsed(Integer iisMerchantUsed){
		this.iisMerchantUsed= iisMerchantUsed;
	}
	/* 权限说明 */
	private String sdescription;
	
	public String getSdescription(){
		return sdescription;
	}
	
	public void setSdescription(String sdescription){
		this.sdescription= sdescription;
	}
	/* 简拼名 */
	private String sjpName;
	
	public String getSjpName(){
		return sjpName;
	}
	
	public void setSjpName(String sjpName){
		this.sjpName= sjpName;
	}
	/* 父菜单ID */
	private String sparentId;
	
	public String getSparentId(){
		return sparentId;
	}
	
	public void setSparentId(String sparentId){
		this.sparentId= sparentId;
	}
	/* 父菜单名称 */
	private String sparentName;
	
	public String getSparentName(){
		return sparentName;
	}
	
	public void setSparentName(String sparentName){
		this.sparentName= sparentName;
	}
	/* 权限码:根据用户登录时判断页面的操作时否显示 */
	private String spurCode;
	
	public String getSpurCode(){
		return spurCode;
	}
	
	public void setSpurCode(String spurCode){
		this.spurCode= spurCode;
	}
	/* 权限名称 */
	private String spurName;
	
	public String getSpurName(){
		return spurName;
	}
	
	public void setSpurName(String spurName){
		this.spurName= spurName;
	}
	/* 权限编号 */
	private String spurNo;
	
	public String getSpurNo(){
		return spurNo;
	}
	
	public void setSpurNo(String spurNo){
		this.spurNo= spurNo;
	}
	/* 全拼名 */
	private String spyName;
	
	public String getSpyName(){
		return spyName;
	}
	
	public void setSpyName(String spyName){
		this.spyName= spyName;
	}
	/* 系统代码 */
	private String ssystemCode;
	
	public String getSsystemCode(){
		return ssystemCode;
	}
	
	public void setSsystemCode(String ssystemCode){
		this.ssystemCode= ssystemCode;
	}
	/* 访问路径 */
	private String surlPath;
	
	public String getSurlPath(){
		return surlPath;
	}
	
	public void setSurlPath(String surlPath){
		this.surlPath= surlPath;
	}

		
}