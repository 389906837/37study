package com.cloud.cang.model.sb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 设备监控数据配置信息表(SB_MONITOR_DATA_CONF) **/
public class MonitorDataConf extends GenericEntity  {

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
	
	/* 实时音量 */
	private Integer iactualVolume;
	
	public Integer getIactualVolume(){
		return iactualVolume;
	}
	
	public void setIactualVolume(Integer iactualVolume){
		this.iactualVolume= iactualVolume;
	}
	/* 每次盘货次数 */
	private Integer iinventoryNum;
	
	public Integer getIinventoryNum(){
		return iinventoryNum;
	}
	
	public void setIinventoryNum(Integer iinventoryNum){
		this.iinventoryNum= iinventoryNum;
	}
	/* 定时开关机状态 0 否1 是 */
	private Integer iswitchStatus;
	
	public Integer getIswitchStatus(){
		return iswitchStatus;
	}
	
	public void setIswitchStatus(Integer iswitchStatus){
		this.iswitchStatus= iswitchStatus;
	}
	/* 添加人 */
	private String saddUser;
	
	public String getSaddUser(){
		return saddUser;
	}
	
	public void setSaddUser(String saddUser){
		this.saddUser= saddUser;
	}
	/* 设备ID */
	private String sdeviceId;
	
	public String getSdeviceId(){
		return sdeviceId;
	}
	
	public void setSdeviceId(String sdeviceId){
		this.sdeviceId= sdeviceId;
	}
	/* 控制温度1 */
	private BigDecimal slcontrolTemperature;
	
	public BigDecimal getSlcontrolTemperature(){
		return slcontrolTemperature;
	}
	
	public void setSlcontrolTemperature(BigDecimal slcontrolTemperature){
		this.slcontrolTemperature= slcontrolTemperature;
	}
	/* 实时温度1 */
	private String sltemperature;
	
	public String getSltemperature(){
		return sltemperature;
	}
	
	public void setSltemperature(String sltemperature){
		this.sltemperature= sltemperature;
	}
	/* 控制温度2 */
	private BigDecimal srcontrolTemperature;
	
	public BigDecimal getSrcontrolTemperature(){
		return srcontrolTemperature;
	}
	
	public void setSrcontrolTemperature(BigDecimal srcontrolTemperature){
		this.srcontrolTemperature= srcontrolTemperature;
	}
	/* 实时温度2 */
	private String srtemperature;
	
	public String getSrtemperature(){
		return srtemperature;
	}
	
	public void setSrtemperature(String srtemperature){
		this.srtemperature= srtemperature;
	}
	/* 修改人 */
	private String supdateUser;
	
	public String getSupdateUser(){
		return supdateUser;
	}
	
	public void setSupdateUser(String supdateUser){
		this.supdateUser= supdateUser;
	}
	/* 添加日期 */
	private Date taddTime;
	
	public Date getTaddTime(){
		return taddTime;
	}
	
	public void setTaddTime(Date taddTime){
		this.taddTime= taddTime;
	}
	/* 开机时间 */
	private String tbootTime;
	
	public String getTbootTime(){
		return tbootTime;
	}
	
	public void setTbootTime(String tbootTime){
		this.tbootTime= tbootTime;
	}
	/* 自动盘货开始时间 */
	private String tinventoryBeginTime;
	
	public String getTinventoryBeginTime(){
		return tinventoryBeginTime;
	}
	
	public void setTinventoryBeginTime(String tinventoryBeginTime){
		this.tinventoryBeginTime= tinventoryBeginTime;
	}
	/* 自动盘货结束时间 */
	private String tinventoryEndTime;
	
	public String getTinventoryEndTime(){
		return tinventoryEndTime;
	}
	
	public void setTinventoryEndTime(String tinventoryEndTime){
		this.tinventoryEndTime= tinventoryEndTime;
	}
	/* 上次盘货时间 */
	private Date tinventoryTime;
	
	public Date getTinventoryTime(){
		return tinventoryTime;
	}
	
	public void setTinventoryTime(Date tinventoryTime){
		this.tinventoryTime= tinventoryTime;
	}
	/* 温度控制开始时间1 */
	private String tlcontrolBeginTime;
	
	public String getTlcontrolBeginTime(){
		return tlcontrolBeginTime;
	}
	
	public void setTlcontrolBeginTime(String tlcontrolBeginTime){
		this.tlcontrolBeginTime= tlcontrolBeginTime;
	}
	/* 温度控制结束时间1 */
	private String tlcontrolEndTime;
	
	public String getTlcontrolEndTime(){
		return tlcontrolEndTime;
	}
	
	public void setTlcontrolEndTime(String tlcontrolEndTime){
		this.tlcontrolEndTime= tlcontrolEndTime;
	}
	/* 温度控制开始时间2 */
	private String trcontrolBeginTime;
	
	public String getTrcontrolBeginTime(){
		return trcontrolBeginTime;
	}
	
	public void setTrcontrolBeginTime(String trcontrolBeginTime){
		this.trcontrolBeginTime= trcontrolBeginTime;
	}
	/* 温度控制结束时间2 */
	private String trcontrolEndTime;
	
	public String getTrcontrolEndTime(){
		return trcontrolEndTime;
	}
	
	public void setTrcontrolEndTime(String trcontrolEndTime){
		this.trcontrolEndTime= trcontrolEndTime;
	}
	/* 关机时间 */
	private String tshutDownTime;
	
	public String getTshutDownTime(){
		return tshutDownTime;
	}
	
	public void setTshutDownTime(String tshutDownTime){
		this.tshutDownTime= tshutDownTime;
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