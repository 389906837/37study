package com.cloud.cang.model.tb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 第三方商户设备SKU库(TB_THIRD_DEVICE_SKU) **/
public class ThirdDeviceSku extends GenericEntity  {

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
	
	/* 是否下架 */
	private Integer iisLowerShelf;
	
	public Integer getIisLowerShelf(){
		return iisLowerShelf;
	}
	
	public void setIisLowerShelf(Integer iisLowerShelf){
		this.iisLowerShelf= iisLowerShelf;
	}
	/* 当前版本号 */
	private Integer iverson;
	
	public Integer getIverson(){
		return iverson;
	}
	
	public void setIverson(Integer iverson){
		this.iverson= iverson;
	}
	/* 视觉商品重量 */
	private BigDecimal iweight;
	
	public BigDecimal getIweight(){
		return iweight;
	}
	
	public void setIweight(BigDecimal iweight){
		this.iweight= iweight;
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
	/* 视觉商品名称 */
	private String sname;
	
	public String getSname(){
		return sname;
	}
	
	public void setSname(String sname){
		this.sname= sname;
	}
	/* 视觉商品价格 */
	private BigDecimal sprice;
	
	public BigDecimal getSprice(){
		return sprice;
	}
	
	public void setSprice(BigDecimal sprice){
		this.sprice= sprice;
	}
	/* 第三方SKU编号 */
	private String sthirdPartSkuCode;
	
	public String getSthirdPartSkuCode(){
		return sthirdPartSkuCode;
	}
	
	public void setSthirdPartSkuCode(String sthirdPartSkuCode){
		this.sthirdPartSkuCode= sthirdPartSkuCode;
	}
	/* 本地库视觉视觉编号 */
	private String svrCode;
	
	public String getSvrCode(){
		return svrCode;
	}
	
	public void setSvrCode(String svrCode){
		this.svrCode= svrCode;
	}
	/* 本地库视觉识别ID */
	private String svrId;
	
	public String getSvrId(){
		return svrId;
	}
	
	public void setSvrId(String svrId){
		this.svrId= svrId;
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