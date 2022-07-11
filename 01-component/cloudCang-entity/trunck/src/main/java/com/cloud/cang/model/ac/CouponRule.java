package com.cloud.cang.model.ac;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 活动返券规则记录表(AC_COUPON_RULE) **/
public class CouponRule extends GenericEntity  {

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
	/* 劵面值,如果赠送次数为比例，该字段为比例值 */
	private BigDecimal fcouponValue;
	
	public BigDecimal getFcouponValue(){
		return fcouponValue;
	}
	
	public void setFcouponValue(BigDecimal fcouponValue){
		this.fcouponValue= fcouponValue;
	}
	/* 订单实付金额赠送限制（0不限制） */
	private BigDecimal fgiveLimitAmount;
	
	public BigDecimal getFgiveLimitAmount(){
		return fgiveLimitAmount;
	}
	
	public void setFgiveLimitAmount(BigDecimal fgiveLimitAmount){
		this.fgiveLimitAmount= fgiveLimitAmount;
	}
	/* 最大券值 */
	private BigDecimal fmaxValue;
	
	public BigDecimal getFmaxValue(){
		return fmaxValue;
	}
	
	public void setFmaxValue(BigDecimal fmaxValue){
		this.fmaxValue= fmaxValue;
	}
	/* 最小赠送额度 */
	private BigDecimal fminValue;
	
	public BigDecimal getFminValue(){
		return fminValue;
	}
	
	public void setFminValue(BigDecimal fminValue){
		this.fminValue= fminValue;
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
	/* 券有效期数值 */
	private Integer icouponValidityValue;
	
	public Integer getIcouponValidityValue(){
		return icouponValidityValue;
	}
	
	public void setIcouponValidityValue(Integer icouponValidityValue){
		this.icouponValidityValue= icouponValidityValue;
	}
	/* 是否可修改:是否可供用户修改 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 送券方式
            10:比例
            20:固定额度 */
	private Integer igiveMethod;
	
	public Integer getIgiveMethod(){
		return igiveMethod;
	}
	
	public void setIgiveMethod(Integer igiveMethod){
		this.igiveMethod= igiveMethod;
	}
	/* 是否有效 */
	private Integer iisValid;
	
	public Integer getIisValid(){
		return iisValid;
	}
	
	public void setIisValid(Integer iisValid){
		this.iisValid= iisValid;
	}
	/* 送券数量 */
	private Integer isendNumber;
	
	public Integer getIsendNumber(){
		return isendNumber;
	}
	
	public void setIsendNumber(Integer isendNumber){
		this.isendNumber= isendNumber;
	}
	/* 劵说明 */
	private String sactivityInstruction;
	
	public String getSactivityInstruction(){
		return sactivityInstruction;
	}
	
	public void setSactivityInstruction(String sactivityInstruction){
		this.sactivityInstruction= sactivityInstruction;
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
	/* 券简述 */
	private String sbriefDesc;
	
	public String getSbriefDesc(){
		return sbriefDesc;
	}
	
	public void setSbriefDesc(String sbriefDesc){
		this.sbriefDesc= sbriefDesc;
	}
	/* 优惠记录编号 */
	private String scode;
	
	public String getScode(){
		return scode;
	}
	
	public void setScode(String scode){
		this.scode= scode;
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
	/* 订单商品品牌ID赠送限制（空不限制）多个用，隔开 */
	private String sgiveLimitBrand;
	
	public String getSgiveLimitBrand(){
		return sgiveLimitBrand;
	}
	
	public void setSgiveLimitBrand(String sgiveLimitBrand){
		this.sgiveLimitBrand= sgiveLimitBrand;
	}
	/* 订单商品分类ID赠送限制（空不限制）多个用，隔开 */
	private String sgiveLimitCategory;
	
	public String getSgiveLimitCategory(){
		return sgiveLimitCategory;
	}
	
	public void setSgiveLimitCategory(String sgiveLimitCategory){
		this.sgiveLimitCategory= sgiveLimitCategory;
	}
	/* 订单商品编号赠送限制（空不限制）多个用，隔开 */
	private String sgiveLimitCommodity;
	
	public String getSgiveLimitCommodity(){
		return sgiveLimitCommodity;
	}
	
	public void setSgiveLimitCommodity(String sgiveLimitCommodity){
		this.sgiveLimitCommodity= sgiveLimitCommodity;
	}
	/* 订单设备编号赠送限制（空不限制）多个用，隔开 */
	private String sgiveLimitDevice;
	
	public String getSgiveLimitDevice(){
		return sgiveLimitDevice;
	}
	
	public void setSgiveLimitDevice(String sgiveLimitDevice){
		this.sgiveLimitDevice= sgiveLimitDevice;
	}
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
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