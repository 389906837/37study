package com.cloud.cang.model.om;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 审核订单商品明细表(OM_ORDER_AUDIT_COMMODITY) **/
public class OrderAuditCommodity extends GenericEntity  {

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
	/* 商品成本 */
	private BigDecimal fcostAmount;
	
	public BigDecimal getFcostAmount(){
		return fcostAmount;
	}
	
	public void setFcostAmount(BigDecimal fcostAmount){
		this.fcostAmount= fcostAmount;
	}
	/* 订单数量 */
	private Integer forderCount;
	
	public Integer getForderCount(){
		return forderCount;
	}
	
	public void setForderCount(Integer forderCount){
		this.forderCount= forderCount;
	}
	/* 商品进价税点 */
	private BigDecimal ftaxPoint;
	
	public BigDecimal getFtaxPoint(){
		return ftaxPoint;
	}
	
	public void setFtaxPoint(BigDecimal ftaxPoint){
		this.ftaxPoint= ftaxPoint;
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
	/* 唯一标识集合 */
	private String sidentifies;
	
	public String getSidentifies(){
		return sidentifies;
	}
	
	public void setSidentifies(String sidentifies){
		this.sidentifies= sidentifies;
	}
	/* 订单编号 */
	private String sorderCode;
	
	public String getSorderCode(){
		return sorderCode;
	}
	
	public void setSorderCode(String sorderCode){
		this.sorderCode= sorderCode;
	}
	/* 订单ID */
	private String sorderId;
	
	public String getSorderId(){
		return sorderId;
	}
	
	public void setSorderId(String sorderId){
		this.sorderId= sorderId;
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