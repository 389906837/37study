package com.cloud.cang.model.op;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 平台应用管理信息表(OP_APP_MANAGE) **/
public class AppManage extends GenericEntity  {

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
	
	/* 审核状态
            10=待审核
            20=审核通过
            30=审核驳回 */
	private Integer iauditStatus;
	
	public Integer getIauditStatus(){
		return iauditStatus;
	}
	
	public void setIauditStatus(Integer iauditStatus){
		this.iauditStatus= iauditStatus;
	}
	/* 是否删除0否1是 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 应用状态
            10=申请中
            20=已上线，待上架
            30=已上线，已上架
             */
	private Integer istatus;
	
	public Integer getIstatus(){
		return istatus;
	}
	
	public void setIstatus(Integer istatus){
		this.istatus= istatus;
	}
	/* 应用ID */
	private String sappId;
	
	public String getSappId(){
		return sappId;
	}
	
	public void setSappId(String sappId){
		this.sappId= sappId;
	}
	/* 应用私钥 */
	private String sappPrivateKey;
	
	public String getSappPrivateKey(){
		return sappPrivateKey;
	}
	
	public void setSappPrivateKey(String sappPrivateKey){
		this.sappPrivateKey= sappPrivateKey;
	}
	/* 应用公钥 */
	private String sappPublicKey;
	
	public String getSappPublicKey(){
		return sappPublicKey;
	}
	
	public void setSappPublicKey(String sappPublicKey){
		this.sappPublicKey= sappPublicKey;
	}
	/* 应用秘钥 */
	private String sappSecretKey;
	
	public String getSappSecretKey(){
		return sappSecretKey;
	}
	
	public void setSappSecretKey(String sappSecretKey){
		this.sappSecretKey= sappSecretKey;
	}
	/* 审核人姓名 */
	private String sauditOperName;
	
	public String getSauditOperName(){
		return sauditOperName;
	}
	
	public void setSauditOperName(String sauditOperName){
		this.sauditOperName= sauditOperName;
	}
	/* 审核原因 */
	private String sauditReason;
	
	public String getSauditReason(){
		return sauditReason;
	}
	
	public void setSauditReason(String sauditReason){
		this.sauditReason= sauditReason;
	}
	/* 应用编号 */
	private String scode;
	
	public String getScode(){
		return scode;
	}
	
	public void setScode(String scode){
		this.scode= scode;
	}
	/* 数据加密类型 */
	private String sencrypType;
	
	public String getSencrypType(){
		return sencrypType;
	}
	
	public void setSencrypType(String sencrypType){
		this.sencrypType= sencrypType;
	}
	/* 应用名称 */
	private String sname;
	
	public String getSname(){
		return sname;
	}
	
	public void setSname(String sname){
		this.sname= sname;
	}
	/* 应用回调通知地址 */
	private String snoticeAddress;
	
	public String getSnoticeAddress(){
		return snoticeAddress;
	}
	
	public void setSnoticeAddress(String snoticeAddress){
		this.snoticeAddress= snoticeAddress;
	}
	/* 平台秘钥 */
	private String splatformKey;
	
	public String getSplatformKey(){
		return splatformKey;
	}
	
	public void setSplatformKey(String splatformKey){
		this.splatformKey= splatformKey;
	}
	/* 平台公钥 */
	private String splatformPublicKey;
	
	public String getSplatformPublicKey(){
		return splatformPublicKey;
	}
	
	public void setSplatformPublicKey(String splatformPublicKey){
		this.splatformPublicKey= splatformPublicKey;
	}
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 应用系统分属 */
	private String ssystemType;
	
	public String getSsystemType(){
		return ssystemType;
	}
	
	public void setSsystemType(String ssystemType){
		this.ssystemType= ssystemType;
	}
	/* 应用收费类型
             */
	private String stollType;
	
	public String getStollType(){
		return stollType;
	}
	
	public void setStollType(String stollType){
		this.stollType= stollType;
	}
	/* 应用类型 */
	private String stype;
	
	public String getStype(){
		return stype;
	}
	
	public void setStype(String stype){
		this.stype= stype;
	}
	/* 用户编号 */
	private String suserCode;
	
	public String getSuserCode(){
		return suserCode;
	}
	
	public void setSuserCode(String suserCode){
		this.suserCode= suserCode;
	}
	/* 用户ID */
	private String suserId;
	
	public String getSuserId(){
		return suserId;
	}
	
	public void setSuserId(String suserId){
		this.suserId= suserId;
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
	/* 创建时间 */
	private Date tcreateTime;
	
	public Date getTcreateTime(){
		return tcreateTime;
	}
	
	public void setTcreateTime(Date tcreateTime){
		this.tcreateTime= tcreateTime;
	}
	/* 上线时间 */
	private Date tonlineTime;
	
	public Date getTonlineTime(){
		return tonlineTime;
	}
	
	public void setTonlineTime(Date tonlineTime){
		this.tonlineTime= tonlineTime;
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