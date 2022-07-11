package com.cloud.cang.model.sm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 单设备库存记录表(SM_DEVICE_STOCK) **/
public class DeviceStock extends GenericEntity  {

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
	
	/* 商品总重量 */
	private BigDecimal fcommodityTotalWeight;
	
	public BigDecimal getFcommodityTotalWeight(){
		return fcommodityTotalWeight;
	}
	
	public void setFcommodityTotalWeight(BigDecimal fcommodityTotalWeight){
		this.fcommodityTotalWeight= fcommodityTotalWeight;
	}
	/* 商品库存销售单价 */
	private BigDecimal fsalePrice;
	
	public BigDecimal getFsalePrice(){
		return fsalePrice;
	}
	
	public void setFsalePrice(BigDecimal fsalePrice){
		this.fsalePrice= fsalePrice;
	}
	/* 实时库存数量 */
	private Integer istock;
	
	public Integer getIstock(){
		return istock;
	}
	
	public void setIstock(Integer istock){
		this.istock= istock;
	}
	/* 单据编号 */
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
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 添加时间 */
	private Date taddTime;
	
	public Date getTaddTime(){
		return taddTime;
	}
	
	public void setTaddTime(Date taddTime){
		this.taddTime= taddTime;
	}
	/* 最后更新时间 */
	private Date tlastUpdateTime;
	
	public Date getTlastUpdateTime(){
		return tlastUpdateTime;
	}
	
	public void setTlastUpdateTime(Date tlastUpdateTime){
		this.tlastUpdateTime= tlastUpdateTime;
	}

		
}