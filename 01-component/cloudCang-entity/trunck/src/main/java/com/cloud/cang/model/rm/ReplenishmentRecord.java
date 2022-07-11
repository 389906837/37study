package com.cloud.cang.model.rm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 商品补货记录信息表(RM_REPLENISHMENT_RECORD) **/
public class ReplenishmentRecord extends GenericEntity  {

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
	
	/* 实际上架数量 */
	private Integer iactualShelves;
	
	public Integer getIactualShelves(){
		return iactualShelves;
	}
	
	public void setIactualShelves(Integer iactualShelves){
		this.iactualShelves= iactualShelves;
	}
	/* 实际上架总金额 */
	private BigDecimal iactualShelvesAmount;
	
	public BigDecimal getIactualShelvesAmount(){
		return iactualShelvesAmount;
	}
	
	public void setIactualShelvesAmount(BigDecimal iactualShelvesAmount){
		this.iactualShelvesAmount= iactualShelvesAmount;
	}
	/* 实际下架数量 */
	private Integer iactualUnder;
	
	public Integer getIactualUnder(){
		return iactualUnder;
	}
	
	public void setIactualUnder(Integer iactualUnder){
		this.iactualUnder= iactualUnder;
	}
	/* 实际下架总金额 */
	private BigDecimal iactualUnderAmount;
	
	public BigDecimal getIactualUnderAmount(){
		return iactualUnderAmount;
	}
	
	public void setIactualUnderAmount(BigDecimal iactualUnderAmount){
		this.iactualUnderAmount= iactualUnderAmount;
	}
	/* 是否删除0否1是 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
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
	/* 10=待配货
            20=配送中
            30=已完成
            40=取消配货 */
	private Integer istatus;
	
	public Integer getIstatus(){
		return istatus;
	}
	
	public void setIstatus(Integer istatus){
		this.istatus= istatus;
	}
	/* 补货类型
            10=动态配货
            20=普通配货 */
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
	/* 补货单编号 */
	private String scode;
	
	public String getScode(){
		return scode;
	}
	
	public void setScode(String scode){
		this.scode= scode;
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
	/* 设备ID */
	private String sdeviceId;
	
	public String getSdeviceId(){
		return sdeviceId;
	}
	
	public void setSdeviceId(String sdeviceId){
		this.sdeviceId= sdeviceId;
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
	/* 设备读写器序列号 */
	private String sreaderSerialNumber;
	
	public String getSreaderSerialNumber(){
		return sreaderSerialNumber;
	}
	
	public void setSreaderSerialNumber(String sreaderSerialNumber){
		this.sreaderSerialNumber= sreaderSerialNumber;
	}
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 抵扣积分 */
	private String srenewalId;
	
	public String getSrenewalId(){
		return srenewalId;
	}
	
	public void setSrenewalId(String srenewalId){
		this.srenewalId= srenewalId;
	}
	/* 补货员手机号 */
	private String srenewalMobile;
	
	public String getSrenewalMobile(){
		return srenewalMobile;
	}
	
	public void setSrenewalMobile(String srenewalMobile){
		this.srenewalMobile= srenewalMobile;
	}
	/* 补货员姓名 */
	private String srenewalName;
	
	public String getSrenewalName(){
		return srenewalName;
	}
	
	public void setSrenewalName(String srenewalName){
		this.srenewalName= srenewalName;
	}
	/* 修改时间 */
	private Date taddTime;
	
	public Date getTaddTime(){
		return taddTime;
	}
	
	public void setTaddTime(Date taddTime){
		this.taddTime= taddTime;
	}
	/* 补货时间 */
	private Date treplenishmentTime;
	
	public Date getTreplenishmentTime(){
		return treplenishmentTime;
	}
	
	public void setTreplenishmentTime(Date treplenishmentTime){
		this.treplenishmentTime= treplenishmentTime;
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