package com.cloud.cang.model.sm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 设备标准库存配置明细表(SM_STANDARD_DETAIL) **/
public class StandardDetail extends GenericEntity  {

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
	
	/* 设备层数/货道数 */
	private Integer ilayerNum;
	
	public Integer getIlayerNum(){
		return ilayerNum;
	}
	
	public void setIlayerNum(Integer ilayerNum){
		this.ilayerNum= ilayerNum;
	}
	/* 最低阈值（库存低于最低阈值报警） */
	private Integer iminSillValue;
	
	public Integer getIminSillValue(){
		return iminSillValue;
	}
	
	public void setIminSillValue(Integer iminSillValue){
		this.iminSillValue= iminSillValue;
	}
	/* 标准库存 */
	private Integer istandardStock;
	
	public Integer getIstandardStock(){
		return istandardStock;
	}
	
	public void setIstandardStock(Integer istandardStock){
		this.istandardStock= istandardStock;
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

		
}