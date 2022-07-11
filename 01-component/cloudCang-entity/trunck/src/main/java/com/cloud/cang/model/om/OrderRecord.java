package com.cloud.cang.model.om;

import com.cloud.cang.generic.GenericEntity;

import java.math.BigDecimal;
import java.util.Date;
/** 商品订单记录信息表(OM_ORDER_RECORD) **/
public class OrderRecord extends GenericEntity  {

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
	/* 订单总额 */
	private BigDecimal ftotalAmount;
	
	public BigDecimal getFtotalAmount(){
		return ftotalAmount;
	}
	
	public void setFtotalAmount(BigDecimal ftotalAmount){
		this.ftotalAmount= ftotalAmount;
	}
	/* 订单商品总成本 */
	private BigDecimal ftotalCostAmount;
	
	public BigDecimal getFtotalCostAmount(){
		return ftotalCostAmount;
	}
	
	public void setFtotalCostAmount(BigDecimal ftotalCostAmount){
		this.ftotalCostAmount= ftotalCostAmount;
	}
	/* 实际退款积分 */
	private BigDecimal iactualRefundPont;
	
	public BigDecimal getIactualRefundPont(){
		return iactualRefundPont;
	}
	
	public void setIactualRefundPont(BigDecimal iactualRefundPont){
		this.iactualRefundPont= iactualRefundPont;
	}
	/* 10=商户代扣
            20=主动支付 */
	private Integer ichargebackWay;
	
	public Integer getIchargebackWay(){
		return ichargebackWay;
	}
	
	public void setIchargebackWay(Integer ichargebackWay){
		this.ichargebackWay= ichargebackWay;
	}
	/* 是否删除0否1是 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 拆单类型 10=自动 20=手动 */
	private Integer idismantlingType;
	
	public Integer getIdismantlingType(){
		return idismantlingType;
	}
	
	public void setIdismantlingType(Integer idismantlingType){
		this.idismantlingType= idismantlingType;
	}
	/* 是否拆单0否1是 */
	private Integer iisDismantling;
	
	public Integer getIisDismantling(){
		return iisDismantling;
	}
	
	public void setIisDismantling(Integer iisDismantling){
		this.iisDismantling= iisDismantling;
	}
	/* 支付类型
            10=账户余额
            20=银行卡
            30=微信支付
            40=支付宝
            50=银联支付
            60=现金支付
            70=第三方支付
            80=其他 */
	private Integer ipayType;
	
	public Integer getIpayType(){
		return ipayType;
	}
	
	public void setIpayType(Integer ipayType){
		this.ipayType= ipayType;
	}
	/* 支付类型中支付方式
            10：公众号支付
            20：H5支付
            30：扫码支付
            40：APP支付
            50：刷卡支付
            60：小程序支付
            70：代扣 */
	private Integer ipayWay;
	
	public Integer getIpayWay(){
		return ipayWay;
	}
	
	public void setIpayWay(Integer ipayWay){
		this.ipayWay= ipayWay;
	}
	/* 抵扣积分 */
	private BigDecimal ipoint;
	
	public BigDecimal getIpoint(){
		return ipoint;
	}
	
	public void setIpoint(BigDecimal ipoint){
		this.ipoint= ipoint;
	}
	/* 10=微信
            20=支付宝
            30=APP */
	private Integer isourceClientType;
	
	public Integer getIsourceClientType(){
		return isourceClientType;
	}
	
	public void setIsourceClientType(Integer isourceClientType){
		this.isourceClientType= isourceClientType;
	}
	/* 订单来源方式
             */
	private Integer isourceWay;
	
	public Integer getIsourceWay(){
		return isourceWay;
	}
	
	public void setIsourceWay(Integer isourceWay){
		this.isourceWay= isourceWay;
	}
	/* 来源方式编号 */
	private Integer isourceWayCode;
	
	public Integer getIsourceWayCode(){
		return isourceWayCode;
	}
	
	public void setIsourceWayCode(Integer isourceWayCode){
		this.isourceWayCode= isourceWayCode;
	}
	/* 来源方式名称 */
	private String isourceWayName;
	
	public String getIsourceWayName(){
		return isourceWayName;
	}
	
	public void setIsourceWayName(String isourceWayName){
		this.isourceWayName= isourceWayName;
	}
	/* 10=付款成功
            20=付款失败
            30=订单异常
            100=待付款
            110=付款处理中 */
	private Integer istatus;
	
	public Integer getIstatus(){
		return istatus;
	}
	
	public void setIstatus(Integer istatus){
		this.istatus= istatus;
	}
	/* 版本号 */
	private Integer iversion;
	
	public Integer getIversion(){
		return iversion;
	}
	
	public void setIversion(Integer iversion){
		this.iversion= iversion;
	}
	/* 设备地址 */
	private String sdeviceAddress;
	
	public String getSdeviceAddress(){
		return sdeviceAddress;
	}
	
	public void setSdeviceAddress(String sdeviceAddress){
		this.sdeviceAddress= sdeviceAddress;
	}
	/* 设备编号 */
	private String sdeviceCode;
	
	public String getSdeviceCode(){
		return sdeviceCode;
	}
	
	public void setSdeviceCode(String sdeviceCode){
		this.sdeviceCode= sdeviceCode;
	}
	/* 商户ID */
	private String sdeviceId;
	
	public String getSdeviceId(){
		return sdeviceId;
	}
	
	public void setSdeviceId(String sdeviceId){
		this.sdeviceId= sdeviceId;
	}
	/* 设备名称 */
	private String sdeviceName;
	
	public String getSdeviceName(){
		return sdeviceName;
	}
	
	public void setSdeviceName(String sdeviceName){
		this.sdeviceName= sdeviceName;
	}
	/* 拆单编号 */
	private String sdismantlingCode;
	
	public String getSdismantlingCode(){
		return sdismantlingCode;
	}
	
	public void setSdismantlingCode(String sdismantlingCode){
		this.sdismantlingCode= sdismantlingCode;
	}
	/* 管理人员备注 */
	private String shandlerRemark;
	
	public String getShandlerRemark(){
		return shandlerRemark;
	}
	
	public void setShandlerRemark(String shandlerRemark){
		this.shandlerRemark= shandlerRemark;
	}
	/* 处理人ID */
	private String shandlerUserid;
	
	public String getShandlerUserid(){
		return shandlerUserid;
	}
	
	public void setShandlerUserid(String shandlerUserid){
		this.shandlerUserid= shandlerUserid;
	}
	/* 处理人姓名 */
	private String shandlerUsername;
	
	public String getShandlerUsername(){
		return shandlerUsername;
	}
	
	public void setShandlerUsername(String shandlerUsername){
		this.shandlerUsername= shandlerUsername;
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
	/* 付款申请ID */
	private String spayApplyId;
	
	public String getSpayApplyId(){
		return spayApplyId;
	}
	
	public void setSpayApplyId(String spayApplyId){
		this.spayApplyId= spayApplyId;
	}
	/* 订单支付失败原因 */
	private String spayFailureReason;
	
	public String getSpayFailureReason(){
		return spayFailureReason;
	}
	
	public void setSpayFailureReason(String spayFailureReason){
		this.spayFailureReason= spayFailureReason;
	}
	/* 交易流水号 */
	private String spaySerialNumber;
	
	public String getSpaySerialNumber(){
		return spaySerialNumber;
	}
	
	public void setSpaySerialNumber(String spaySerialNumber){
		this.spaySerialNumber= spaySerialNumber;
	}
	/* 设备读写器序列号 */
	private String sreaderSerialNumber;
	
	public String getSreaderSerialNumber(){
		return sreaderSerialNumber;
	}
	
	public void setSreaderSerialNumber(String sreaderSerialNumber){
		this.sreaderSerialNumber= sreaderSerialNumber;
	}
	/* 券使用编号 */
	private String susedCouponCode;
	
	public String getSusedCouponCode(){
		return susedCouponCode;
	}
	
	public void setSusedCouponCode(String susedCouponCode){
		this.susedCouponCode= susedCouponCode;
	}
	/* 券使用ID */
	private String susedCouponId;
	
	public String getSusedCouponId(){
		return susedCouponId;
	}
	
	public void setSusedCouponId(String susedCouponId){
		this.susedCouponId= susedCouponId;
	}
	/* 修改时间 */
	private Date taddTime;
	
	public Date getTaddTime(){
		return taddTime;
	}
	
	public void setTaddTime(Date taddTime){
		this.taddTime= taddTime;
	}
	/* 处理时间 */
	private Date thandlerTime;
	
	public Date getThandlerTime(){
		return thandlerTime;
	}
	
	public void setThandlerTime(Date thandlerTime){
		this.thandlerTime= thandlerTime;
	}
	/* 订单时间 */
	private Date torderTime;
	
	public Date getTorderTime(){
		return torderTime;
	}
	
	public void setTorderTime(Date torderTime){
		this.torderTime= torderTime;
	}
	/* 订单支付完成时间 */
	private Date tpayCompleteTime;
	
	public Date getTpayCompleteTime(){
		return tpayCompleteTime;
	}
	
	public void setTpayCompleteTime(Date tpayCompleteTime){
		this.tpayCompleteTime= tpayCompleteTime;
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