package com.cloud.cang.model.ac;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 优惠券批量下发表(AC_COUPON_BATCH) **/
public class CouponBatch extends GenericEntity  {

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
	/* 劵面值 */
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
	/* 发放数量(统计从表) */
	private Integer ibatchNumber;
	
	public Integer getIbatchNumber(){
		return ibatchNumber;
	}
	
	public void setIbatchNumber(Integer ibatchNumber){
		this.ibatchNumber= ibatchNumber;
	}
	/* 10：只有一次
            20：每月一次 */
	private Integer icouponCodeUseType;
	
	public Integer getIcouponCodeUseType(){
		return icouponCodeUseType;
	}
	
	public void setIcouponCodeUseType(Integer icouponCodeUseType){
		this.icouponCodeUseType= icouponCodeUseType;
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
	/* 是否可修改:是否可供用户修改 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 10：只有一次
            20：每月一次
             */
	private Integer imemberJoinType;
	
	public Integer getImemberJoinType(){
		return imemberJoinType;
	}
	
	public void setImemberJoinType(Integer imemberJoinType){
		this.imemberJoinType= imemberJoinType;
	}
	/* 10:DRAFT:草稿        
            11:NOT:待审核
            20:PASS:审核通过
            21:REFUSED:审核拒绝 */
	private Integer istatus;
	
	public Integer getIstatus(){
		return istatus;
	}
	
	public void setIstatus(Integer istatus){
		this.istatus= istatus;
	}
	/* 下发类型 0 优惠券1:优惠券码 2: 营销活动券 */
	private Integer itype;
	
	public Integer getItype(){
		return itype;
	}
	
	public void setItype(Integer itype){
		this.itype= itype;
	}
	/* 版本号 */
	private Integer iversion;
	
	public Integer getIversion(){
		return iversion;
	}
	
	public void setIversion(Integer iversion){
		this.iversion= iversion;
	}
	/* 活动编号(预留) */
	private String sactivityCode;
	
	public String getSactivityCode(){
		return sactivityCode;
	}
	
	public void setSactivityCode(String sactivityCode){
		this.sactivityCode= sactivityCode;
	}
	/* 活动名称(预留) */
	private String sactivityName;
	
	public String getSactivityName(){
		return sactivityName;
	}
	
	public void setSactivityName(String sactivityName){
		this.sactivityName= sactivityName;
	}
	/* 添加人 */
	private String saddUser;
	
	public String getSaddUser(){
		return saddUser;
	}
	
	public void setSaddUser(String saddUser){
		this.saddUser= saddUser;
	}
	/* 审核人 */
	private String sauditOperatorName;
	
	public String getSauditOperatorName(){
		return sauditOperatorName;
	}
	
	public void setSauditOperatorName(String sauditOperatorName){
		this.sauditOperatorName= sauditOperatorName;
	}
	/* 审核意见 */
	private String sauditOpinion;
	
	public String getSauditOpinion(){
		return sauditOpinion;
	}
	
	public void setSauditOpinion(String sauditOpinion){
		this.sauditOpinion= sauditOpinion;
	}
	/* 发放批次编号 */
	private String sbatchCode;
	
	public String getSbatchCode(){
		return sbatchCode;
	}
	
	public void setSbatchCode(String sbatchCode){
		this.sbatchCode= sbatchCode;
	}
	/* 券简述 */
	private String sbriefDesc;
	
	public String getSbriefDesc(){
		return sbriefDesc;
	}
	
	public void setSbriefDesc(String sbriefDesc){
		this.sbriefDesc= sbriefDesc;
	}
	/* 劵说明 */
	private String scouponInstruction;
	
	public String getScouponInstruction(){
		return scouponInstruction;
	}
	
	public void setScouponInstruction(String scouponInstruction){
		this.scouponInstruction= scouponInstruction;
	}
	/* 劵类型名称 */
	private String scouponTypeName;
	
	public String getScouponTypeName(){
		return scouponTypeName;
	}
	
	public void setScouponTypeName(String scouponTypeName){
		this.scouponTypeName= scouponTypeName;
	}
	/* 券有效期数值 */
	private Integer scouponValidityValue;
	
	public Integer getScouponValidityValue(){
		return scouponValidityValue;
	}
	
	public void setScouponValidityValue(Integer scouponValidityValue){
		this.scouponValidityValue= scouponValidityValue;
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
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 提交人 */
	private String ssubmitOperatorId;
	
	public String getSsubmitOperatorId(){
		return ssubmitOperatorId;
	}
	
	public void setSsubmitOperatorId(String ssubmitOperatorId){
		this.ssubmitOperatorId= ssubmitOperatorId;
	}
	/* 修改人 */
	private String supateUser;
	
	public String getSupateUser(){
		return supateUser;
	}
	
	public void setSupateUser(String supateUser){
		this.supateUser= supateUser;
	}
	/* 订单商品品牌使用限制（空不限制）多个用，隔开 */
	private String suseLimitBrand;
	
	public String getSuseLimitBrand(){
		return suseLimitBrand;
	}
	
	public void setSuseLimitBrand(String suseLimitBrand){
		this.suseLimitBrand= suseLimitBrand;
	}
	/* 订单商品分类使用限制（空不限制）多个用，隔开 */
	private String suseLimitCategory;
	
	public String getSuseLimitCategory(){
		return suseLimitCategory;
	}
	
	public void setSuseLimitCategory(String suseLimitCategory){
		this.suseLimitCategory= suseLimitCategory;
	}
	/* 订单商品限使用制（空不限制）多个用，隔开 */
	private String suseLimitCommodity;
	
	public String getSuseLimitCommodity(){
		return suseLimitCommodity;
	}
	
	public void setSuseLimitCommodity(String suseLimitCommodity){
		this.suseLimitCommodity= suseLimitCommodity;
	}
	/* 订单设备使用限制（空不限制）多个用，隔开 */
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
	/* 审核日期 */
	private Date tauditDatetime;
	
	public Date getTauditDatetime(){
		return tauditDatetime;
	}
	
	public void setTauditDatetime(Date tauditDatetime){
		this.tauditDatetime= tauditDatetime;
	}
	/* 兑券有效截止时间 */
	private Date texEndtime;
	
	public Date getTexEndtime(){
		return texEndtime;
	}
	
	public void setTexEndtime(Date texEndtime){
		this.texEndtime= texEndtime;
	}
	/* 兑券有效开始时间 */
	private Date texStarttime;
	
	public Date getTexStarttime(){
		return texStarttime;
	}
	
	public void setTexStarttime(Date texStarttime){
		this.texStarttime= texStarttime;
	}
	/* 提交日期 */
	private Date tsubmitDatetime;
	
	public Date getTsubmitDatetime(){
		return tsubmitDatetime;
	}
	
	public void setTsubmitDatetime(Date tsubmitDatetime){
		this.tsubmitDatetime= tsubmitDatetime;
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