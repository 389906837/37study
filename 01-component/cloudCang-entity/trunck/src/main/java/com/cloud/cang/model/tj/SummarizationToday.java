package com.cloud.cang.model.tj;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 商户每日数据统计(TJ_SUMMARIZATION_TODAY) **/
public class SummarizationToday extends GenericEntity  {

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
	
	/* 统计日期 */
	private Date dsummerEndTime;
	
	public Date getDsummerEndTime(){
		return dsummerEndTime;
	}
	
	public void setDsummerEndTime(Date dsummerEndTime){
		this.dsummerEndTime= dsummerEndTime;
	}
	/* 订单实付金额 */
	private BigDecimal factualPayAmount;
	
	public BigDecimal getFactualPayAmount(){
		return factualPayAmount;
	}
	
	public void setFactualPayAmount(BigDecimal factualPayAmount){
		this.factualPayAmount= factualPayAmount;
	}
	/* 申请退款金额 */
	private BigDecimal fapplyRefundAmount;
	
	public BigDecimal getFapplyRefundAmount(){
		return fapplyRefundAmount;
	}
	
	public void setFapplyRefundAmount(BigDecimal fapplyRefundAmount){
		this.fapplyRefundAmount= fapplyRefundAmount;
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
	/* 订单金额 */
	private BigDecimal forderAmount;
	
	public BigDecimal getForderAmount(){
		return forderAmount;
	}
	
	public void setForderAmount(BigDecimal forderAmount){
		this.forderAmount= forderAmount;
	}
	/* 未支付或支付失败订单金额 */
	private BigDecimal forderFailAmount;
	
	public BigDecimal getForderFailAmount(){
		return forderFailAmount;
	}
	
	public void setForderFailAmount(BigDecimal forderFailAmount){
		this.forderFailAmount= forderFailAmount;
	}
	/* 订单退款成功金额 */
	private BigDecimal forderRefundAmount;
	
	public BigDecimal getForderRefundAmount(){
		return forderRefundAmount;
	}
	
	public void setForderRefundAmount(BigDecimal forderRefundAmount){
		this.forderRefundAmount= forderRefundAmount;
	}
	/* 已支付订单金额 */
	private BigDecimal forderSuccessAmount;
	
	public BigDecimal getForderSuccessAmount(){
		return forderSuccessAmount;
	}
	
	public void setForderSuccessAmount(BigDecimal forderSuccessAmount){
		this.forderSuccessAmount= forderSuccessAmount;
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
	/* 异常订单扣款笔数 */
	private Integer iabnormalChargebackNum;
	
	public Integer getIabnormalChargebackNum(){
		return iabnormalChargebackNum;
	}
	
	public void setIabnormalChargebackNum(Integer iabnormalChargebackNum){
		this.iabnormalChargebackNum= iabnormalChargebackNum;
	}
	/* 处理异常订单笔数 */
	private Integer iabnormalDealwithNum;
	
	public Integer getIabnormalDealwithNum(){
		return iabnormalDealwithNum;
	}
	
	public void setIabnormalDealwithNum(Integer iabnormalDealwithNum){
		this.iabnormalDealwithNum= iabnormalDealwithNum;
	}
	/* 异常订单忽略笔数 */
	private Integer iabnormalIgnoreNum;
	
	public Integer getIabnormalIgnoreNum(){
		return iabnormalIgnoreNum;
	}
	
	public void setIabnormalIgnoreNum(Integer iabnormalIgnoreNum){
		this.iabnormalIgnoreNum= iabnormalIgnoreNum;
	}
	/* 异常订单笔数 */
	private Integer iabnormalNum;
	
	public Integer getIabnormalNum(){
		return iabnormalNum;
	}
	
	public void setIabnormalNum(Integer iabnormalNum){
		this.iabnormalNum= iabnormalNum;
	}
	/* 投放设备数量 */
	private Integer ideviceNum;
	
	public Integer getIdeviceNum(){
		return ideviceNum;
	}
	
	public void setIdeviceNum(Integer ideviceNum){
		this.ideviceNum= ideviceNum;
	}
	/* 总访客次数（包含未购物） */
	private Integer imemberNum;
	
	public Integer getImemberNum(){
		return imemberNum;
	}
	
	public void setImemberNum(Integer imemberNum){
		this.imemberNum= imemberNum;
	}
	/* 总未补货次数 */
	private Integer inotReplenishmentNum;
	
	public Integer getInotReplenishmentNum(){
		return inotReplenishmentNum;
	}
	
	public void setInotReplenishmentNum(Integer inotReplenishmentNum){
		this.inotReplenishmentNum= inotReplenishmentNum;
	}
	/* 订单人数 */
	private Integer iorderManNum;
	
	public Integer getIorderManNum(){
		return iorderManNum;
	}
	
	public void setIorderManNum(Integer iorderManNum){
		this.iorderManNum= iorderManNum;
	}
	/* 订单笔数 */
	private Integer iorderNum;
	
	public Integer getIorderNum(){
		return iorderNum;
	}
	
	public void setIorderNum(Integer iorderNum){
		this.iorderNum= iorderNum;
	}
	/* 退款审核失败笔数 */
	private Integer irefundAuditFailNum;
	
	public Integer getIrefundAuditFailNum(){
		return irefundAuditFailNum;
	}
	
	public void setIrefundAuditFailNum(Integer irefundAuditFailNum){
		this.irefundAuditFailNum= irefundAuditFailNum;
	}
	/* 退款审核笔数 */
	private Integer irefundAuditNum;
	
	public Integer getIrefundAuditNum(){
		return irefundAuditNum;
	}
	
	public void setIrefundAuditNum(Integer irefundAuditNum){
		this.irefundAuditNum= irefundAuditNum;
	}
	/* 退款审核成功笔数 */
	private Integer irefundAuditSuccessNum;
	
	public Integer getIrefundAuditSuccessNum(){
		return irefundAuditSuccessNum;
	}
	
	public void setIrefundAuditSuccessNum(Integer irefundAuditSuccessNum){
		this.irefundAuditSuccessNum= irefundAuditSuccessNum;
	}
	/* 申请退款笔数 */
	private Integer irefundNum;
	
	public Integer getIrefundNum(){
		return irefundNum;
	}
	
	public void setIrefundNum(Integer irefundNum){
		this.irefundNum= irefundNum;
	}
	/* 注册人数 */
	private Integer iregisteNum;
	
	public Integer getIregisteNum(){
		return iregisteNum;
	}
	
	public void setIregisteNum(Integer iregisteNum){
		this.iregisteNum= iregisteNum;
	}
	/* 补货次数 */
	private Integer ireplenishmentNum;
	
	public Integer getIreplenishmentNum(){
		return ireplenishmentNum;
	}
	
	public void setIreplenishmentNum(Integer ireplenishmentNum){
		this.ireplenishmentNum= ireplenishmentNum;
	}
	/* 总未购物次数 */
	private Integer ivisitorsNum;
	
	public Integer getIvisitorsNum(){
		return ivisitorsNum;
	}
	
	public void setIvisitorsNum(Integer ivisitorsNum){
		this.ivisitorsNum= ivisitorsNum;
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
	/* 统计时间 */
	private Date taddTime;
	
	public Date getTaddTime(){
		return taddTime;
	}
	
	public void setTaddTime(Date taddTime){
		this.taddTime= taddTime;
	}
	/* 更新时间 */
	private Date tupdateTime;
	
	public Date getTupdateTime(){
		return tupdateTime;
	}
	
	public void setTupdateTime(Date tupdateTime){
		this.tupdateTime= tupdateTime;
	}

		
}