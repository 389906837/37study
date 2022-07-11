package com.cloud.cang.model.sh;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 商户域名配置信息(SH_DOMAIN_CONF) **/
public class DomainConf extends GenericEntity  {

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
	
	/* 是否删除0否1是 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 状态
            10=申请中
            20=审核通过
            30=审核驳回
            40=停用 */
	private Integer istatus;
	
	public Integer getIstatus(){
		return istatus;
	}
	
	public void setIstatus(Integer istatus){
		this.istatus= istatus;
	}
	/* 添加人 */
	private String saddUser;
	
	public String getSaddUser(){
		return saddUser;
	}
	
	public void setSaddUser(String saddUser){
		this.saddUser= saddUser;
	}
	/* 审核人ID */
	private String sauditOperId;
	
	public String getSauditOperId(){
		return sauditOperId;
	}
	
	public void setSauditOperId(String sauditOperId){
		this.sauditOperId= sauditOperId;
	}
	/* 审核人姓名 */
	private String sauditOperName;
	
	public String getSauditOperName(){
		return sauditOperName;
	}
	
	public void setSauditOperName(String sauditOperName){
		this.sauditOperName= sauditOperName;
	}
	/* 审核意见 */
	private String sauditOpinion;
	
	public String getSauditOpinion(){
		return sauditOpinion;
	}
	
	public void setSauditOpinion(String sauditOpinion){
		this.sauditOpinion= sauditOpinion;
	}
	/* 域名 */
	private String sdomainUrl;
	
	public String getSdomainUrl(){
		return sdomainUrl;
	}
	
	public void setSdomainUrl(String sdomainUrl){
		this.sdomainUrl= sdomainUrl;
	}
	/* 商户信息ID */
	private String smerchantId;
	
	public String getSmerchantId(){
		return smerchantId;
	}
	
	public void setSmerchantId(String smerchantId){
		this.smerchantId= smerchantId;
	}
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
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
	/* 审核时间 */
	private Date tauditTime;
	
	public Date getTauditTime(){
		return tauditTime;
	}
	
	public void setTauditTime(Date tauditTime){
		this.tauditTime= tauditTime;
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