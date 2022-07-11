package com.cloud.cang.model.ac;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 券码兑换明细表(AC_COUPON_CODE_EX_DETAILS) **/
public class CouponCodeExDetails extends GenericEntity  {

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
	/* 兑换次数 */
	private Integer iexTimes;
	
	public Integer getIexTimes(){
		return iexTimes;
	}
	
	public void setIexTimes(Integer iexTimes){
		this.iexTimes= iexTimes;
	}
	/* 状态1：未兑换 2:兑换中 3：已兑换 4：兑换失败 5:失效 */
	private Integer istatus;
	
	public Integer getIstatus(){
		return istatus;
	}
	
	public void setIstatus(Integer istatus){
		this.istatus= istatus;
	}
	/* 生成券码用户编号（预留） */
	private String saddCouponsUsercode;

	public String getSaddCouponsUsercode(){
		return saddCouponsUsercode;
	}
	
	public void setSaddCouponsUsercode(String saddCouponsUsercode){
		this.saddCouponsUsercode= saddCouponsUsercode;
	}
	/* 生成券码用户ID（预留） */
	private String saddCouponsUserid;
	
	public String getSaddCouponsUserid(){
		return saddCouponsUserid;
	}
	
	public void setSaddCouponsUserid(String saddCouponsUserid){
		this.saddCouponsUserid= saddCouponsUserid;
	}
	/* 添加人 */
	private String saddUser;
	
	public String getSaddUser(){
		return saddUser;
	}
	
	public void setSaddUser(String saddUser){
		this.saddUser= saddUser;
	}
	/* 批次编号 */
	private String sbatchCode;
	
	public String getSbatchCode(){
		return sbatchCode;
	}
	
	public void setSbatchCode(String sbatchCode){
		this.sbatchCode= sbatchCode;
	}
	/* 下发批次ID */
	private String sbatchId;
	
	public String getSbatchId(){
		return sbatchId;
	}
	
	public void setSbatchId(String sbatchId){
		this.sbatchId= sbatchId;
	}
	/* 兑换券码 */
	private String sexCouponsCode;
	
	public String getSexCouponsCode(){
		return sexCouponsCode;
	}
	
	public void setSexCouponsCode(String sexCouponsCode){
		this.sexCouponsCode= sexCouponsCode;
	}
	/* 兑换来源CODE（预留） */
	private String sexSourceCode;
	
	public String getSexSourceCode(){
		return sexSourceCode;
	}
	
	public void setSexSourceCode(String sexSourceCode){
		this.sexSourceCode= sexSourceCode;
	}
	/* 兑换来源ID（预留） */
	private String sexSourceId;
	
	public String getSexSourceId(){
		return sexSourceId;
	}
	
	public void setSexSourceId(String sexSourceId){
		this.sexSourceId= sexSourceId;
	}
	/* 会员编号 */
	private String smemberCode;
	
	public String getSmemberCode(){
		return smemberCode;
	}
	
	public void setSmemberCode(String smemberCode){
		this.smemberCode= smemberCode;
	}
	/* 会员ID */
	private String smemberId;
	
	public String getSmemberId(){
		return smemberId;
	}
	
	public void setSmemberId(String smemberId){
		this.smemberId= smemberId;
	}
	/* 会员用户名（手机号） */
	private String smemberName;
	
	public String getSmemberName(){
		return smemberName;
	}
	
	public void setSmemberName(String smemberName){
		this.smemberName= smemberName;
	}
	/*  */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 来源单编号（预留） */
	private String ssourceCode;
	
	public String getSsourceCode(){
		return ssourceCode;
	}
	
	public void setSsourceCode(String ssourceCode){
		this.ssourceCode= ssourceCode;
	}
	/* 来源单ID（预留） */
	private String ssourceId;
	
	public String getSsourceId(){
		return ssourceId;
	}
	
	public void setSsourceId(String ssourceId){
		this.ssourceId= ssourceId;
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
	/* 兑换失效时间 */
	private Date texEndtime;
	
	public Date getTexEndtime(){
		return texEndtime;
	}
	
	public void setTexEndtime(Date texEndtime){
		this.texEndtime= texEndtime;
	}
	/* 兑换时间 */
	private Date texTime;
	
	public Date getTexTime(){
		return texTime;
	}
	
	public void setTexTime(Date texTime){
		this.texTime= texTime;
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