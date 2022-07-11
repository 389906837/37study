package com.cloud.cang.model.om;

import com.cloud.cang.generic.GenericEntity;

import java.math.BigDecimal;
import java.util.Date;
/** 商品订单退款审核记录信息表(OM_REFUND_AUDIT) **/
public class RefundAudit extends GenericEntity  {

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
	
	/* 订单实付总额 */
	private BigDecimal factualPayAmount;
	
	public BigDecimal getFactualPayAmount(){
		return factualPayAmount;
	}
	
	public void setFactualPayAmount(BigDecimal factualPayAmount){
		this.factualPayAmount= factualPayAmount;
	}
	/* 实际退款总额 */
	private BigDecimal factualRefundAmount;
	
	public BigDecimal getFactualRefundAmount(){
		return factualRefundAmount;
	}
	
	public void setFactualRefundAmount(BigDecimal factualRefundAmount){
		this.factualRefundAmount= factualRefundAmount;
	}
	/* 申请退款总额 */
	private BigDecimal fapplyRefundAmount;
	
	public BigDecimal getFapplyRefundAmount(){
		return fapplyRefundAmount;
	}
	
	public void setFapplyRefundAmount(BigDecimal fapplyRefundAmount){
		this.fapplyRefundAmount= fapplyRefundAmount;
	}
	/* 订单优惠总额 */
	private BigDecimal fdiscountAmount;
	
	public BigDecimal getFdiscountAmount(){
		return fdiscountAmount;
	}
	
	public void setFdiscountAmount(BigDecimal fdiscountAmount){
		this.fdiscountAmount= fdiscountAmount;
	}
	/* 订单总额 */
	private BigDecimal ftotalAmount;
	
	public BigDecimal getFtotalAmount(){
		return ftotalAmount;
	}
	
	public void setFtotalAmount(BigDecimal ftotalAmount){
		this.ftotalAmount= ftotalAmount;
	}
	/* 订单实付积分 */
	private BigDecimal iactualPayIpoint;
	
	public BigDecimal getIactualPayIpoint(){
		return iactualPayIpoint;
	}
	
	public void setIactualPayIpoint(BigDecimal iactualPayIpoint){
		this.iactualPayIpoint= iactualPayIpoint;
	}
	/* 实际退款积分 */
	private BigDecimal iactualRefundIpoint;
	
	public BigDecimal getIactualRefundIpoint(){
		return iactualRefundIpoint;
	}
	
	public void setIactualRefundIpoint(BigDecimal iactualRefundIpoint){
		this.iactualRefundIpoint= iactualRefundIpoint;
	}
	/* 申请退款积分 */
	private BigDecimal iapplyRefundIpoint;
	
	public BigDecimal getIapplyRefundIpoint(){
		return iapplyRefundIpoint;
	}
	
	public void setIapplyRefundIpoint(BigDecimal iapplyRefundIpoint){
		this.iapplyRefundIpoint= iapplyRefundIpoint;
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
	/* 退款状态
            10=待退款
            20=退款失败
            30=退款成功 */
	private Integer irefundStatus;
	
	public Integer getIrefundStatus(){
		return irefundStatus;
	}
	
	public void setIrefundStatus(Integer irefundStatus){
		this.irefundStatus= irefundStatus;
	}
	/* 版本号 */
	private Integer iversion;
	
	public Integer getIversion(){
		return iversion;
	}
	
	public void setIversion(Integer iversion){
		this.iversion= iversion;
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
	/* 审核驳回原因 */
	private String sauditRefuseReason;
	
	public String getSauditRefuseReason(){
		return sauditRefuseReason;
	}
	
	public void setSauditRefuseReason(String sauditRefuseReason){
		this.sauditRefuseReason= sauditRefuseReason;
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
	/* 交易流水号 */
	private String spaySerialNumber;
	
	public String getSpaySerialNumber(){
		return spaySerialNumber;
	}
	
	public void setSpaySerialNumber(String spaySerialNumber){
		this.spaySerialNumber= spaySerialNumber;
	}
	/* 退款申请ID */
	private String srefundApplyId;
	
	public String getSrefundApplyId(){
		return srefundApplyId;
	}
	
	public void setSrefundApplyId(String srefundApplyId){
		this.srefundApplyId= srefundApplyId;
	}
	/* 退款编号 */
	private String srefundCode;
	
	public String getSrefundCode(){
		return srefundCode;
	}
	
	public void setSrefundCode(String srefundCode){
		this.srefundCode= srefundCode;
	}
	/* 退款失败原因 */
	private String srefundFailureReason;
	
	public String getSrefundFailureReason(){
		return srefundFailureReason;
	}
	
	public void setSrefundFailureReason(String srefundFailureReason){
		this.srefundFailureReason= srefundFailureReason;
	}
	/* 退款操作人ID */
	private String srefundOperId;
	
	public String getSrefundOperId(){
		return srefundOperId;
	}
	
	public void setSrefundOperId(String srefundOperId){
		this.srefundOperId= srefundOperId;
	}
	/* 退款操作人姓名 */
	private String srefundOperName;
	
	public String getSrefundOperName(){
		return srefundOperName;
	}
	
	public void setSrefundOperName(String srefundOperName){
		this.srefundOperName= srefundOperName;
	}
	/* 退款原因 */
	private String srefundReason;
	
	public String getSrefundReason(){
		return srefundReason;
	}
	
	public void setSrefundReason(String srefundReason){
		this.srefundReason= srefundReason;
	}
	/* 备注 */
	private Date sremark;
	
	public Date getSremark(){
		return sremark;
	}
	
	public void setSremark(Date sremark){
		this.sremark= sremark;
	}
	/* 修改时间 */
	private Date taddTime;
	
	public Date getTaddTime(){
		return taddTime;
	}
	
	public void setTaddTime(Date taddTime){
		this.taddTime= taddTime;
	}
	/* 申请时间 */
	private Date tapplyTime;
	
	public Date getTapplyTime(){
		return tapplyTime;
	}
	
	public void setTapplyTime(Date tapplyTime){
		this.tapplyTime= tapplyTime;
	}
	/* 审核时间 */
	private Date tauditTime;
	
	public Date getTauditTime(){
		return tauditTime;
	}
	
	public void setTauditTime(Date tauditTime){
		this.tauditTime= tauditTime;
	}
	/* 退款完成时间 */
	private Date trefundCompleteTime;
	
	public Date getTrefundCompleteTime(){
		return trefundCompleteTime;
	}
	
	public void setTrefundCompleteTime(Date trefundCompleteTime){
		this.trefundCompleteTime= trefundCompleteTime;
	}
	/* 退款时间 */
	private Date trefundTime;
	
	public Date getTrefundTime(){
		return trefundTime;
	}
	
	public void setTrefundTime(Date trefundTime){
		this.trefundTime= trefundTime;
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