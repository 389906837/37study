package com.cloud.cang.model.om;

import com.cloud.cang.generic.GenericEntity;

import java.math.BigDecimal;
import java.util.Date;
/** 订单商品明细表(OM_ORDER_COMMODITY) **/
public class OrderCommodity extends GenericEntity  {

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
	
	/* 实付金额 */
	private BigDecimal factualAmount;
	
	public BigDecimal getFactualAmount(){
		return factualAmount;
	}
	
	public void setFactualAmount(BigDecimal factualAmount){
		this.factualAmount= factualAmount;
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
	/* 订单优惠券抵扣金额 */
	private BigDecimal fcouponDeductionAmount;
	
	public BigDecimal getFcouponDeductionAmount(){
		return fcouponDeductionAmount;
	}
	
	public void setFcouponDeductionAmount(BigDecimal fcouponDeductionAmount){
		this.fcouponDeductionAmount= fcouponDeductionAmount;
	}
	/* 订单优惠总额 */
	private BigDecimal fdiscountAmount;
	
	public BigDecimal getFdiscountAmount(){
		return fdiscountAmount;
	}
	
	public void setFdiscountAmount(BigDecimal fdiscountAmount){
		this.fdiscountAmount= fdiscountAmount;
	}
	/* 订单首单优惠金额 */
	private BigDecimal ffirstDiscountAmount;
	
	public BigDecimal getFfirstDiscountAmount(){
		return ffirstDiscountAmount;
	}
	
	public void setFfirstDiscountAmount(BigDecimal ffirstDiscountAmount){
		this.ffirstDiscountAmount= ffirstDiscountAmount;
	}
	/* 订单数量 */
	private Integer forderCount;
	
	public Integer getForderCount(){
		return forderCount;
	}
	
	public void setForderCount(Integer forderCount){
		this.forderCount= forderCount;
	}
	/* 订单其他优惠金额 */
	private BigDecimal fotherDiscountAmount;
	
	public BigDecimal getFotherDiscountAmount(){
		return fotherDiscountAmount;
	}
	
	public void setFotherDiscountAmount(BigDecimal fotherDiscountAmount){
		this.fotherDiscountAmount= fotherDiscountAmount;
	}
	/* 订单积分优惠金额 */
	private BigDecimal fpointDiscountAmount;
	
	public BigDecimal getFpointDiscountAmount(){
		return fpointDiscountAmount;
	}
	
	public void setFpointDiscountAmount(BigDecimal fpointDiscountAmount){
		this.fpointDiscountAmount= fpointDiscountAmount;
	}
	/* 订单促销优惠金额 */
	private BigDecimal fpromotionDiscountAmount;
	
	public BigDecimal getFpromotionDiscountAmount(){
		return fpromotionDiscountAmount;
	}
	
	public void setFpromotionDiscountAmount(BigDecimal fpromotionDiscountAmount){
		this.fpromotionDiscountAmount= fpromotionDiscountAmount;
	}
	/* 已退款金额 */
	private BigDecimal frefundAmount;
	
	public BigDecimal getFrefundAmount(){
		return frefundAmount;
	}
	
	public void setFrefundAmount(BigDecimal frefundAmount){
		this.frefundAmount= frefundAmount;
	}
	/* 已退款数量 */
	private Integer frefundCount;
	
	public Integer getFrefundCount(){
		return frefundCount;
	}
	
	public void setFrefundCount(Integer frefundCount){
		this.frefundCount= frefundCount;
	}
	/* 已退回积分 */
	private BigDecimal frefundPoint;
	
	public BigDecimal getFrefundPoint(){
		return frefundPoint;
	}
	
	public void setFrefundPoint(BigDecimal frefundPoint){
		this.frefundPoint= frefundPoint;
	}
	/* 商品进价税点 */
	private BigDecimal ftaxPoint;
	
	public BigDecimal getFtaxPoint(){
		return ftaxPoint;
	}
	
	public void setFtaxPoint(BigDecimal ftaxPoint){
		this.ftaxPoint= ftaxPoint;
	}
	/* 抵扣积分 */
	private BigDecimal ipoint;
	
	public BigDecimal getIpoint(){
		return ipoint;
	}
	
	public void setIpoint(BigDecimal ipoint){
		this.ipoint= ipoint;
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