package com.cloud.cang.model.ac;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 用户持有劵信息表(AC_COUPON_USER) **/
public class CouponUser extends GenericEntity  {

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
	
	/* 券生效日期 */
	private Date dcouponEffectiveDate;
	
	public Date getDcouponEffectiveDate(){
		return dcouponEffectiveDate;
	}
	
	public void setDcouponEffectiveDate(Date dcouponEffectiveDate){
		this.dcouponEffectiveDate= dcouponEffectiveDate;
	}
	/* 券失效日期 */
	private Date dcouponExpiryDate;
	
	public Date getDcouponExpiryDate(){
		return dcouponExpiryDate;
	}
	
	public void setDcouponExpiryDate(Date dcouponExpiryDate){
		this.dcouponExpiryDate= dcouponExpiryDate;
	}
	/* 实际兑换金额 */
	private BigDecimal factualExchangeAmount;
	
	public BigDecimal getFactualExchangeAmount(){
		return factualExchangeAmount;
	}
	
	public void setFactualExchangeAmount(BigDecimal factualExchangeAmount){
		this.factualExchangeAmount= factualExchangeAmount;
	}
	/* 劵面值,如果赠送次数为比例，该字段为比例值 */
	private BigDecimal fcouponValue;
	
	public BigDecimal getFcouponValue(){
		return fcouponValue;
	}
	
	public void setFcouponValue(BigDecimal fcouponValue){
		this.fcouponValue= fcouponValue;
	}
	/* 订单实付金额使用限制（0不限制） */
	private BigDecimal fuseLimitAmount;
	
	public BigDecimal getFuseLimitAmount(){
		return fuseLimitAmount;
	}
	
	public void setFuseLimitAmount(BigDecimal fuseLimitAmount){
		this.fuseLimitAmount= fuseLimitAmount;
	}
	/* 券类型
            10  现金券
            20  满减券
            30  抵扣券
            40  商品券
              数据字典配置 */
	private Integer icouponType;
	
	public Integer getIcouponType(){
		return icouponType;
	}
	
	public void setIcouponType(Integer icouponType){
		this.icouponType= icouponType;
	}
	/* 来源单据类型
            10:平台赠送
            20:会员注册
            30:首次开通支付宝免密
            40:首次开通微信免密
            50:推荐注册
            60:首次下单
            70:下单
            80:促销活动
             */
	private Integer isourceType;
	
	public Integer getIsourceType(){
		return isourceType;
	}
	
	public void setIsourceType(Integer isourceType){
		this.isourceType= isourceType;
	}
	/* 1=未使用
            2=已使用
            3=已过期
            4=冻结
            5=删除 */
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
	/* 券简述 */
	private String sbriefDesc;
	
	public String getSbriefDesc(){
		return sbriefDesc;
	}
	
	public void setSbriefDesc(String sbriefDesc){
		this.sbriefDesc= sbriefDesc;
	}
	/* 劵编号 */
	private String scouponCode;
	
	public String getScouponCode(){
		return scouponCode;
	}
	
	public void setScouponCode(String scouponCode){
		this.scouponCode= scouponCode;
	}
	/* 劵说明 */
	private String scouponInstruction;
	
	public String getScouponInstruction(){
		return scouponInstruction;
	}
	
	public void setScouponInstruction(String scouponInstruction){
		this.scouponInstruction= scouponInstruction;
	}
	/* 券有效期数值 */
	private Integer scouponValidityValue;
	
	public Integer getScouponValidityValue(){
		return scouponValidityValue;
	}
	
	public void setScouponValidityValue(Integer scouponValidityValue){
		this.scouponValidityValue= scouponValidityValue;
	}
	/* 只有当类型为因邀请的好友的行为产生的奖励时才有值 */
	private String sinvestmentId;
	
	public String getSinvestmentId(){
		return sinvestmentId;
	}
	
	public void setSinvestmentId(String sinvestmentId){
		this.sinvestmentId= sinvestmentId;
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
	/* 交易流水号 */
	private String spaySerialNumber;
	
	public String getSpaySerialNumber(){
		return spaySerialNumber;
	}
	
	public void setSpaySerialNumber(String spaySerialNumber){
		this.spaySerialNumber= spaySerialNumber;
	}
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 来源活动编号 */
	private String ssourceAcCode;
	
	public String getSsourceAcCode(){
		return ssourceAcCode;
	}
	
	public void setSsourceAcCode(String ssourceAcCode){
		this.ssourceAcCode= ssourceAcCode;
	}
	/* 来源活动名称 */
	private String ssourceAcName;
	
	public String getSsourceAcName(){
		return ssourceAcName;
	}
	
	public void setSsourceAcName(String ssourceAcName){
		this.ssourceAcName= ssourceAcName;
	}
	/* 来源单据编号 */
	private String ssourceCode;
	
	public String getSsourceCode(){
		return ssourceCode;
	}
	
	public void setSsourceCode(String ssourceCode){
		this.ssourceCode= ssourceCode;
	}
	/* 来源单据ID */
	private String ssourceId;
	
	public String getSsourceId(){
		return ssourceId;
	}
	
	public void setSsourceId(String ssourceId){
		this.ssourceId= ssourceId;
	}
	/* 来源说明 */
	private String ssourceInstruction;
	
	public String getSsourceInstruction(){
		return ssourceInstruction;
	}
	
	public void setSsourceInstruction(String ssourceInstruction){
		this.ssourceInstruction= ssourceInstruction;
	}
	/* 修改人 */
	private String supateUser;
	
	public String getSupateUser(){
		return supateUser;
	}
	
	public void setSupateUser(String supateUser){
		this.supateUser= supateUser;
	}
	/* 订单商品品牌ID使用限制（空不限制）多个用，隔开 */
	private String suseLimitBrand;
	
	public String getSuseLimitBrand(){
		return suseLimitBrand;
	}
	
	public void setSuseLimitBrand(String suseLimitBrand){
		this.suseLimitBrand= suseLimitBrand;
	}
	/* 订单商品分类ID使用限制（空不限制）多个用，隔开 */
	private String suseLimitCategory;
	
	public String getSuseLimitCategory(){
		return suseLimitCategory;
	}
	
	public void setSuseLimitCategory(String suseLimitCategory){
		this.suseLimitCategory= suseLimitCategory;
	}
	/* 订单商品编号限使用制（空不限制）多个用，隔开 */
	private String suseLimitCommodity;
	
	public String getSuseLimitCommodity(){
		return suseLimitCommodity;
	}
	
	public void setSuseLimitCommodity(String suseLimitCommodity){
		this.suseLimitCommodity= suseLimitCommodity;
	}
	/* 订单设备编号使用限制（空不限制）多个用，隔开 */
	private String suseLimitDevice;
	
	public String getSuseLimitDevice(){
		return suseLimitDevice;
	}
	
	public void setSuseLimitDevice(String suseLimitDevice){
		this.suseLimitDevice= suseLimitDevice;
	}
	/* 使用目标说明 */
	private String suseTargetCode;
	
	public String getSuseTargetCode(){
		return suseTargetCode;
	}
	
	public void setSuseTargetCode(String suseTargetCode){
		this.suseTargetCode= suseTargetCode;
	}
	/* 使用目标编码 */
	private String suseTargetId;
	
	public String getSuseTargetId(){
		return suseTargetId;
	}
	
	public void setSuseTargetId(String suseTargetId){
		this.suseTargetId= suseTargetId;
	}
	/* 使用目标说明 */
	private String suseTargetInstruction;
	
	public String getSuseTargetInstruction(){
		return suseTargetInstruction;
	}
	
	public void setSuseTargetInstruction(String suseTargetInstruction){
		this.suseTargetInstruction= suseTargetInstruction;
	}
	/* 实际使用时间 */
	private Date tactualUseDatetime;
	
	public Date getTactualUseDatetime(){
		return tactualUseDatetime;
	}
	
	public void setTactualUseDatetime(Date tactualUseDatetime){
		this.tactualUseDatetime= tactualUseDatetime;
	}
	/* 添加日期 */
	private Date taddTime;
	
	public Date getTaddTime(){
		return taddTime;
	}
	
	public void setTaddTime(Date taddTime){
		this.taddTime= taddTime;
	}
	/* 获券时间 */
	private Date tgetDatetime;
	
	public Date getTgetDatetime(){
		return tgetDatetime;
	}
	
	public void setTgetDatetime(Date tgetDatetime){
		this.tgetDatetime= tgetDatetime;
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