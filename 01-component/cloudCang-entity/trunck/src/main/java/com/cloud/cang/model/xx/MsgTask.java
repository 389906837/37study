package com.cloud.cang.model.xx;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 消息任务表(XX_MSG_TASK) **/
public class MsgTask extends GenericEntity  {

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
	
	/* 0:启用 1：删除 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 是否批量发送 */
	private Integer iisBatchSend;
	
	public Integer getIisBatchSend(){
		return iisBatchSend;
	}
	
	public void setIisBatchSend(Integer iisBatchSend){
		this.iisBatchSend= iisBatchSend;
	}
	/* 1：实时 2 ：延时 */
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
	/* 发送类别 1：单发 2：群发 */
	private Integer isenderType;
	
	public Integer getIsenderType(){
		return isenderType;
	}
	
	public void setIsenderType(Integer isenderType){
		this.isenderType= isenderType;
	}
	/* 消息状态 1：待发送 2：正在发送 3：发送成功 4：发送失败 5：超额无效 */
	private Integer istate;
	
	public Integer getIstate(){
		return istate;
	}
	
	public void setIstate(Integer istate){
		this.istate= istate;
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
	/* 附件(邮件特有) */
	private String sattach;
	
	public String getSattach(){
		return sattach;
	}
	
	public void setSattach(String sattach){
		this.sattach= sattach;
	}
	/* 消息内容 */
	private String scontent;
	
	public String getScontent(){
		return scontent;
	}
	
	public void setScontent(String scontent){
		this.scontent= scontent;
	}
	/* 商户编号 */
	private String smerchantCode;
	
	public String getSmerchantCode(){
		return smerchantCode;
	}
	
	public void setSmerchantCode(String smerchantCode){
		this.smerchantCode= smerchantCode;
	}
	/* 商户信息ID */
	private String smerchantId;
	
	public String getSmerchantId(){
		return smerchantId;
	}
	
	public void setSmerchantId(String smerchantId){
		this.smerchantId= smerchantId;
	}
	/* 消息抄送人 */
	private String smsgCc;
	
	public String getSmsgCc(){
		return smsgCc;
	}
	
	public void setSmsgCc(String smsgCc){
		this.smsgCc= smsgCc;
	}
	/* 相关描述信息 */
	private String smsgDesc;
	
	public String getSmsgDesc(){
		return smsgDesc;
	}
	
	public void setSmsgDesc(String smsgDesc){
		this.smsgDesc= smsgDesc;
	}
	/* 消息收件人 */
	private String smsgRecipient;
	
	public String getSmsgRecipient(){
		return smsgRecipient;
	}
	
	public void setSmsgRecipient(String smsgRecipient){
		this.smsgRecipient= smsgRecipient;
	}
	/* 消息来源系统 */
	private String smsgSourceSystem;
	
	public String getSmsgSourceSystem(){
		return smsgSourceSystem;
	}
	
	public void setSmsgSourceSystem(String smsgSourceSystem){
		this.smsgSourceSystem= smsgSourceSystem;
	}
	/* 消息模板，通过消息模板关联通道 */
	private String ssenderTemplateId;
	
	public String getSsenderTemplateId(){
		return ssenderTemplateId;
	}
	
	public void setSsenderTemplateId(String ssenderTemplateId){
		this.ssenderTemplateId= ssenderTemplateId;
	}
	/* 发送流水号 */
	private String sserianNum;
	
	public String getSserianNum(){
		return sserianNum;
	}
	
	public void setSserianNum(String sserianNum){
		this.sserianNum= sserianNum;
	}
	/* 供应商ID */
	private String sspartnerId;
	
	public String getSspartnerId(){
		return sspartnerId;
	}
	
	public void setSspartnerId(String sspartnerId){
		this.sspartnerId= sspartnerId;
	}
	/* 供应商编号 */
	private String ssupplierCode;
	
	public String getSsupplierCode(){
		return ssupplierCode;
	}
	
	public void setSsupplierCode(String ssupplierCode){
		this.ssupplierCode= ssupplierCode;
	}
	/* 标题(邮件特有) */
	private String stitle;
	
	public String getStitle(){
		return stitle;
	}
	
	public void setStitle(String stitle){
		this.stitle= stitle;
	}
	/* 增加时间 */
	private Date taddtime;
	
	public Date getTaddtime(){
		return taddtime;
	}
	
	public void setTaddtime(Date taddtime){
		this.taddtime= taddtime;
	}
	/* 发送开始时间 */
	private Date tbeginSendDatetime;
	
	public Date getTbeginSendDatetime(){
		return tbeginSendDatetime;
	}
	
	public void setTbeginSendDatetime(Date tbeginSendDatetime){
		this.tbeginSendDatetime= tbeginSendDatetime;
	}
	/* 修改时间 */
	private Date tupdatetime;
	
	public Date getTupdatetime(){
		return tupdatetime;
	}
	
	public void setTupdatetime(Date tupdatetime){
		this.tupdatetime= tupdatetime;
	}

		
}