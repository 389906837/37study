package com.cloud.cang.model.sb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 售货机快照（记录最后一次盘点结果）(SB_DEVICE_SNAPSHOT) **/
public class DeviceSnapshot extends GenericEntity  {
	private static final long serialVersionUID = 1L;

	private Date taddTime;
	/**/
	private String id;
	/**/
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		 this.id= id;
	}
	
	/* 当前购买用户 */
	private String currentCustomer;
	
	public String getCurrentCustomer(){
		return currentCustomer;
	}
	
	public void setCurrentCustomer(String currentCustomer){
		this.currentCustomer= currentCustomer;
	}
	/* 最后盘点用户 */
	private String lastInventoryCustomer;
	
	public String getLastInventoryCustomer(){
		return lastInventoryCustomer;
	}
	
	public void setLastInventoryCustomer(String lastInventoryCustomer){
		this.lastInventoryCustomer= lastInventoryCustomer;
	}
	/* 最后一次盘点rfid标签数组 */
	private String lastInventoryRfid;
	
	public String getLastInventoryRfid(){
		return lastInventoryRfid;
	}
	
	public void setLastInventoryRfid(String lastInventoryRfid){
		this.lastInventoryRfid= lastInventoryRfid;
	}
	/* 设备ID */
	private String sdeviceId;

	public String getSdeviceId() {
		return sdeviceId;
	}

	public void setSdeviceId(String sdeviceId) {
		this.sdeviceId = sdeviceId;
	}

	public Date getTaddTime() {
		return taddTime;
	}

	public void setTaddTime(Date taddTime) {
		this.taddTime = taddTime;
	}

	/* 蚂蚁盒子序列号 */
	private String sboxSerialNumber;

	public String getSboxSerialNumber() {
		return sboxSerialNumber;
	}

	public void setSboxSerialNumber(String sboxSerialNumber) {
		this.sboxSerialNumber = sboxSerialNumber;
	}
}