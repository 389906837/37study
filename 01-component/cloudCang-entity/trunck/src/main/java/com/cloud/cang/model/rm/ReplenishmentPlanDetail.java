package com.cloud.cang.model.rm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 计划补货商品明细表(RM_REPLENISHMENT_PLAN_DETAIL) **/
public class ReplenishmentPlanDetail extends GenericEntity  {

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
	
	/* 优惠总额 */
	private BigDecimal fcommodityAmount;
	
	public BigDecimal getFcommodityAmount(){
		return fcommodityAmount;
	}
	
	public void setFcommodityAmount(BigDecimal fcommodityAmount){
		this.fcommodityAmount= fcommodityAmount;
	}
	/* 商品单价 */
	private BigDecimal fcommodityPrice;
	
	public BigDecimal getFcommodityPrice(){
		return fcommodityPrice;
	}
	
	public void setFcommodityPrice(BigDecimal fcommodityPrice){
		this.fcommodityPrice= fcommodityPrice;
	}
	/* 订单数量 */
	private Integer forderCount;
	
	public Integer getForderCount(){
		return forderCount;
	}
	
	public void setForderCount(Integer forderCount){
		this.forderCount= forderCount;
	}
	/* 商品编号 */
	private String scommodityCode;
	
	public String getScommodityCode(){
		return scommodityCode;
	}
	
	public void setScommodityCode(String scommodityCode){
		this.scommodityCode= scommodityCode;
	}
	/* 商品ID */
	private String scommodityId;
	
	public String getScommodityId(){
		return scommodityId;
	}
	
	public void setScommodityId(String scommodityId){
		this.scommodityId= scommodityId;
	}
	/* 商品名称 */
	private String scommodityName;
	
	public String getScommodityName(){
		return scommodityName;
	}
	
	public void setScommodityName(String scommodityName){
		this.scommodityName= scommodityName;
	}
	/* 批次备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 订单编号 */
	private String sreplenishmentCode;
	
	public String getSreplenishmentCode(){
		return sreplenishmentCode;
	}
	
	public void setSreplenishmentCode(String sreplenishmentCode){
		this.sreplenishmentCode= sreplenishmentCode;
	}
	/* 订单ID */
	private String sreplenishmentId;
	
	public String getSreplenishmentId(){
		return sreplenishmentId;
	}
	
	public void setSreplenishmentId(String sreplenishmentId){
		this.sreplenishmentId= sreplenishmentId;
	}
	/* 增加时间 */
	private Date taddTime;
	
	public Date getTaddTime(){
		return taddTime;
	}
	
	public void setTaddTime(Date taddTime){
		this.taddTime= taddTime;
	}
	/* 修改时间 */
	private Date tupdateTime;
	
	public Date getTupdateTime(){
		return tupdateTime;
	}
	
	public void setTupdateTime(Date tupdateTime){
		this.tupdateTime= tupdateTime;
	}

		
}