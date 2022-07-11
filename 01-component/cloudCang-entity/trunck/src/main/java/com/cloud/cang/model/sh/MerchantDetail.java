package com.cloud.cang.model.sh;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 商户基础详细信息表(SH_MERCHANT_DETAIL) **/
public class MerchantDetail extends GenericEntity  {

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
	
	/* 公司对公账号开户行 */
	private String saccountBank;
	
	public String getSaccountBank(){
		return saccountBank;
	}
	
	public void setSaccountBank(String saccountBank){
		this.saccountBank= saccountBank;
	}
	/* 公司对公账号名称 */
	private String saccountName;
	
	public String getSaccountName(){
		return saccountName;
	}
	
	public void setSaccountName(String saccountName){
		this.saccountName= saccountName;
	}
	/* 公司对公账号 */
	private String saccountNumber;
	
	public String getSaccountNumber(){
		return saccountNumber;
	}
	
	public void setSaccountNumber(String saccountNumber){
		this.saccountNumber= saccountNumber;
	}
	/* 添加人 */
	private String saddUser;
	
	public String getSaddUser(){
		return saddUser;
	}
	
	public void setSaddUser(String saddUser){
		this.saddUser= saddUser;
	}
	/* 公司传真 */
	private String sfax;
	
	public String getSfax(){
		return sfax;
	}
	
	public void setSfax(String sfax){
		this.sfax= sfax;
	}
	/* 公司法人身份证 */
	private String sidCard;
	
	public String getSidCard(){
		return sidCard;
	}
	
	public void setSidCard(String sidCard){
		this.sidCard= sidCard;
	}
	/* 公司法人 */
	private String slegalPerson;
	
	public String getSlegalPerson(){
		return slegalPerson;
	}
	
	public void setSlegalPerson(String slegalPerson){
		this.slegalPerson= slegalPerson;
	}
	/* 公司电话 */
	private String sphone;
	
	public String getSphone(){
		return sphone;
	}
	
	public void setSphone(String sphone){
		this.sphone= sphone;
	}
	/* 公司注册地址 */
	private String sregisterAddress;
	
	public String getSregisterAddress(){
		return sregisterAddress;
	}
	
	public void setSregisterAddress(String sregisterAddress){
		this.sregisterAddress= sregisterAddress;
	}
	/* 公司对公税号 */
	private String staxId;
	
	public String getStaxId(){
		return staxId;
	}
	
	public void setStaxId(String staxId){
		this.staxId= staxId;
	}
	/* 修改人 */
	private String supdateUser;
	
	public String getSupdateUser(){
		return supdateUser;
	}
	
	public void setSupdateUser(String supdateUser){
		this.supdateUser= supdateUser;
	}
	/* 公司网址 */
	private String swebsiteUrl;
	
	public String getSwebsiteUrl(){
		return swebsiteUrl;
	}
	
	public void setSwebsiteUrl(String swebsiteUrl){
		this.swebsiteUrl= swebsiteUrl;
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