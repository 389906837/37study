package com.cloud.cang.model.xx;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 消息/协议  模板从表(XX_MSG_TEMPLATE) **/
public class MsgTemplate extends GenericEntity  {

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
	
	/* 是否删除 */
	private Integer bisDelete;
	
	public Integer getBisDelete(){
		return bisDelete;
	}
	
	public void setBisDelete(Integer bisDelete){
		this.bisDelete= bisDelete;
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
	/*  */
	private Integer iisEnable;
	
	public Integer getIisEnable(){
		return iisEnable;
	}
	
	public void setIisEnable(Integer iisEnable){
		this.iisEnable= iisEnable;
	}
	/* 是否实时发送 1:实时 2：非实时 */
	private Integer iisRealtime;
	
	public Integer getIisRealtime(){
		return iisRealtime;
	}
	
	public void setIisRealtime(Integer iisRealtime){
		this.iisRealtime= iisRealtime;
	}
	/* 消息类型。 1： 短信 2：邮箱 */
	private Integer imsgType;
	
	public Integer getImsgType(){
		return imsgType;
	}
	
	public void setImsgType(Integer imsgType){
		this.imsgType= imsgType;
	}
	/* 0不超时:单位（分钟） */
	private Integer itimeout;
	
	public Integer getItimeout(){
		return itimeout;
	}
	
	public void setItimeout(Integer itimeout){
		this.itimeout= itimeout;
	}
	/* 用途: 1：验证码  2：普通 */
	private Integer iusage;
	
	public Integer getIusage(){
		return iusage;
	}
	
	public void setIusage(Integer iusage){
		this.iusage= iusage;
	}
	/* 添加人(用户名) */
	private String saddOperator;
	
	public String getSaddOperator(){
		return saddOperator;
	}
	
	public void setSaddOperator(String saddOperator){
		this.saddOperator= saddOperator;
	}
	/* 发送结束时间 */
	private String sendtime;
	
	public String getSendtime(){
		return sendtime;
	}
	
	public void setSendtime(String sendtime){
		this.sendtime= sendtime;
	}
	/* 每日发送次数限制 */
	private Integer sendCountLimit;
	
	public Integer getSendCountLimit(){
		return sendCountLimit;
	}
	
	public void setSendCountLimit(Integer sendCountLimit){
		this.sendCountLimit= sendCountLimit;
	}
	/* 每日单用户发送次数限制 */
	private Integer sendUserCountLimit;
	
	public Integer getSendUserCountLimit(){
		return sendUserCountLimit;
	}
	
	public void setSendUserCountLimit(Integer sendUserCountLimit){
		this.sendUserCountLimit= sendUserCountLimit;
	}
	/* 模板主表id */
	private String smainId;
	
	public String getSmainId(){
		return smainId;
	}
	
	public void setSmainId(String smainId){
		this.smainId= smainId;
	}
	/* 修改人(用户名) */
	private String smodifyOperator;
	
	public String getSmodifyOperator(){
		return smodifyOperator;
	}
	
	public void setSmodifyOperator(String smodifyOperator){
		this.smodifyOperator= smodifyOperator;
	}
	/* 发送开始时间 */
	private String sstarttime;
	
	public String getSstarttime(){
		return sstarttime;
	}
	
	public void setSstarttime(String sstarttime){
		this.sstarttime= sstarttime;
	}
	/* 供应商编号 */
	private String ssupplierCode;
	
	public String getSsupplierCode(){
		return ssupplierCode;
	}
	
	public void setSsupplierCode(String ssupplierCode){
		this.ssupplierCode= ssupplierCode;
	}
	/* 供应商ID */
	private String ssupplierId;
	
	public String getSsupplierId(){
		return ssupplierId;
	}
	
	public void setSsupplierId(String ssupplierId){
		this.ssupplierId= ssupplierId;
	}
	/* 模板内容 */
	private String stemplateContent;
	
	public String getStemplateContent(){
		return stemplateContent;
	}
	
	public void setStemplateContent(String stemplateContent){
		this.stemplateContent= stemplateContent;
	}
	/* 模板名称 */
	private String stemplateName;
	
	public String getStemplateName(){
		return stemplateName;
	}
	
	public void setStemplateName(String stemplateName){
		this.stemplateName= stemplateName;
	}
	/* 所属系统 */
	private String stemplateSourceSystem;
	
	public String getStemplateSourceSystem(){
		return stemplateSourceSystem;
	}
	
	public void setStemplateSourceSystem(String stemplateSourceSystem){
		this.stemplateSourceSystem= stemplateSourceSystem;
	}
	/* 标题模板 */
	private String stemplateTitle;
	
	public String getStemplateTitle(){
		return stemplateTitle;
	}
	
	public void setStemplateTitle(String stemplateTitle){
		this.stemplateTitle= stemplateTitle;
	}

		
}