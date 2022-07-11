package com.cloud.cang.model.xx;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 站内信表(XX_SITEMESSAGE) **/
public class Sitemessage extends GenericEntity  {

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
	
	/* 显示终端(app/pc/wap)
            app:10
            pc:20
            wap:30
            pc/wap:40
            all:50
             */
	private Integer idisplayType;
	
	public Integer getIdisplayType(){
		return idisplayType;
	}
	
	public void setIdisplayType(Integer idisplayType){
		this.idisplayType= idisplayType;
	}
	/* 信息类型:
            1、普通信息
            2、意见反馈 */
	private Integer iinforType;
	
	public Integer getIinforType(){
		return iinforType;
	}
	
	public void setIinforType(Integer iinforType){
		this.iinforType= iinforType;
	}
	/* 是否删除 */
	private Integer iisdelete;
	
	public Integer getIisdelete(){
		return iisdelete;
	}
	
	public void setIisdelete(Integer iisdelete){
		this.iisdelete= iisdelete;
	}
	/* 是否已发送 */
	private Integer iisSendok;
	
	public Integer getIisSendok(){
		return iisSendok;
	}
	
	public void setIisSendok(Integer iisSendok){
		this.iisSendok= iisSendok;
	}
	/* 接收人类型
            1:指定用户
            2：所用用户 */
	private Integer ireceiveType;
	
	public Integer getIreceiveType(){
		return ireceiveType;
	}
	
	public void setIreceiveType(Integer ireceiveType){
		this.ireceiveType= ireceiveType;
	}
	/* 发送人类型:
            10：会员
            20：系统消息 */
	private Integer isenderType;
	
	public Integer getIsenderType(){
		return isenderType;
	}
	
	public void setIsenderType(Integer isenderType){
		this.isenderType= isenderType;
	}
	/* 内容 */
	private String scontext;
	
	public String getScontext(){
		return scontext;
	}
	
	public void setScontext(String scontext){
		this.scontext= scontext;
	}
	/* 发送人用户名 */
	private String senderUsername;
	
	public String getSenderUsername(){
		return senderUsername;
	}
	
	public void setSenderUsername(String senderUsername){
		this.senderUsername= senderUsername;
	}
	/* 商户编号 */
	private String smerchantCode;
	
	public String getSmerchantCode(){
		return smerchantCode;
	}
	
	public void setSmerchantCode(String smerchantCode){
		this.smerchantCode= smerchantCode;
	}
	/* 商户ID */
	private String smerchantId;
	
	public String getSmerchantId(){
		return smerchantId;
	}
	
	public void setSmerchantId(String smerchantId){
		this.smerchantId= smerchantId;
	}
	/* 接收人姓名 */
	private String sname;
	
	public String getSname(){
		return sname;
	}
	
	public void setSname(String sname){
		this.sname= sname;
	}
	/* 接收人ID */
	private String sreceiveId;
	
	public String getSreceiveId(){
		return sreceiveId;
	}
	
	public void setSreceiveId(String sreceiveId){
		this.sreceiveId= sreceiveId;
	}
	/* 发送人ID */
	private String ssenderId;
	
	public String getSsenderId(){
		return ssenderId;
	}
	
	public void setSsenderId(String ssenderId){
		this.ssenderId= ssenderId;
	}
	/* 标题 */
	private String stitle;
	
	public String getStitle(){
		return stitle;
	}
	
	public void setStitle(String stitle){
		this.stitle= stitle;
	}
	/* 接收人用户名 */
	private String suserName;
	
	public String getSuserName(){
		return suserName;
	}
	
	public void setSuserName(String suserName){
		this.suserName= suserName;
	}
	/* 有效止期 */
	private Date tendTime;
	
	public Date getTendTime(){
		return tendTime;
	}
	
	public void setTendTime(Date tendTime){
		this.tendTime= tendTime;
	}
	/* 发送时间 */
	private Date tsendDateTime;
	
	public Date getTsendDateTime(){
		return tsendDateTime;
	}
	
	public void setTsendDateTime(Date tsendDateTime){
		this.tsendDateTime= tsendDateTime;
	}
	/* 有效开始时间 */
	private Date tstartTime;
	
	public Date getTstartTime(){
		return tstartTime;
	}
	
	public void setTstartTime(Date tstartTime){
		this.tstartTime= tstartTime;
	}

		
}