package com.cloud.cang.model.ac;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 活动应用范围明细表(AC_USE_RANGE) **/
public class UseRange extends GenericEntity  {

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
	
	/* 应用范围类型
            10:全部
            20:部分设备
            30:部分商品
            40:部分设备部分商品
             */
	private Integer irangeType;
	
	public Integer getIrangeType(){
		return irangeType;
	}
	
	public void setIrangeType(Integer irangeType){
		this.irangeType= irangeType;
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
	/* 商品编号多个用，隔开 */
	private String scommodityCode;
	
	public String getScommodityCode(){
		return scommodityCode;
	}
	
	public void setScommodityCode(String scommodityCode){
		this.scommodityCode= scommodityCode;
	}
	/* 商品ID多个用，隔开 */
	private String scommodityId;
	
	public String getScommodityId(){
		return scommodityId;
	}
	
	public void setScommodityId(String scommodityId){
		this.scommodityId= scommodityId;
	}
	/* 设备编号多个用，隔开 */
	private String sdeviceCode;
	
	public String getSdeviceCode(){
		return sdeviceCode;
	}
	
	public void setSdeviceCode(String sdeviceCode){
		this.sdeviceCode= sdeviceCode;
	}
	/* 设备ID多个用，隔开 */
	private String sdeviceId;
	
	public String getSdeviceId(){
		return sdeviceId;
	}
	
	public void setSdeviceId(String sdeviceId){
		this.sdeviceId= sdeviceId;
	}
	/* 修改人 */
	private String supateUser;
	
	public String getSupateUser(){
		return supateUser;
	}
	
	public void setSupateUser(String supateUser){
		this.supateUser= supateUser;
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