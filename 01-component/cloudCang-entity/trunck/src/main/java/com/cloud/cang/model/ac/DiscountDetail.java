package com.cloud.cang.model.ac;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 活动优惠信息明细表(AC_DISCOUNT_DETAIL) **/
public class DiscountDetail extends GenericEntity  {

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
	
	/* 返现金额 */
	private BigDecimal fcashBackMoney;
	
	public BigDecimal getFcashBackMoney(){
		return fcashBackMoney;
	}
	
	public void setFcashBackMoney(BigDecimal fcashBackMoney){
		this.fcashBackMoney= fcashBackMoney;
	}
	/* 优惠折扣 */
	private BigDecimal fdiscount;
	
	public BigDecimal getFdiscount(){
		return fdiscount;
	}
	
	public void setFdiscount(BigDecimal fdiscount){
		this.fdiscount= fdiscount;
	}
	/* 优惠上限（满减每满可用） */
	private BigDecimal fdiscountLimit;
	
	public BigDecimal getFdiscountLimit(){
		return fdiscountLimit;
	}
	
	public void setFdiscountLimit(BigDecimal fdiscountLimit){
		this.fdiscountLimit= fdiscountLimit;
	}
	/* 优惠金额 */
	private BigDecimal fdiscountMoney;
	
	public BigDecimal getFdiscountMoney(){
		return fdiscountMoney;
	}
	
	public void setFdiscountMoney(BigDecimal fdiscountMoney){
		this.fdiscountMoney= fdiscountMoney;
	}
	/* 目标金额（0不限制） */
	private BigDecimal ftargetMoney;
	
	public BigDecimal getFtargetMoney(){
		return ftargetMoney;
	}
	
	public void setFtargetMoney(BigDecimal ftargetMoney){
		this.ftargetMoney= ftargetMoney;
	}
	/* 目标件数 */
	private Integer ftargetNum;
	
	public Integer getFtargetNum(){
		return ftargetNum;
	}
	
	public void setFtargetNum(Integer ftargetNum){
		this.ftargetNum= ftargetNum;
	}
	/* 优惠类型
            10:首单
            20:打折满X元
            30:打折满X件
            40:打折满X元且满X件
            50:满减满X元
            60:满减每满X元
            70:满减满X件
            80:满减满X元且满Y件
            90:返券
            100:返现
             */
	private Integer idiscountType;
	
	public Integer getIdiscountType(){
		return idiscountType;
	}
	
	public void setIdiscountType(Integer idiscountType){
		this.idiscountType= idiscountType;
	}
	/* 排序号 */
	private Integer isort;
	
	public Integer getIsort(){
		return isort;
	}
	
	public void setIsort(Integer isort){
		this.isort= isort;
	}
	/* 活动编号 */
	private String sacCode;
	
	public String getSacCode(){
		return sacCode;
	}
	
	public void setSacCode(String sacCode){
		this.sacCode= sacCode;
	}
	/* 活动ID */
	private String sacId;
	
	public String getSacId(){
		return sacId;
	}
	
	public void setSacId(String sacId){
		this.sacId= sacId;
	}
	/* 添加人 */
	private String saddUser;
	
	public String getSaddUser(){
		return saddUser;
	}
	
	public void setSaddUser(String saddUser){
		this.saddUser= saddUser;
	}
	/* 修改人 */
	private String supateUser;
	
	public String getSupateUser(){
		return supateUser;
	}
	
	public void setSupateUser(String supateUser){
		this.supateUser= supateUser;
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