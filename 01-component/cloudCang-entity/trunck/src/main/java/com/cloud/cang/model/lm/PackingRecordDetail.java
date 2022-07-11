package com.cloud.cang.model.lm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 打包记录明细表(LM_PACKING_RECORD_DETAIL) **/
public class PackingRecordDetail extends GenericEntity  {

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
	
	/* 标注批次管理信息编号 */
	private String slableBatchCode;
	
	public String getSlableBatchCode(){
		return slableBatchCode;
	}
	
	public void setSlableBatchCode(String slableBatchCode){
		this.slableBatchCode= slableBatchCode;
	}
	/* 标注批次管理信息商品编号 */
	private String slableBatchCommodityCode;
	
	public String getSlableBatchCommodityCode(){
		return slableBatchCommodityCode;
	}
	
	public void setSlableBatchCommodityCode(String slableBatchCommodityCode){
		this.slableBatchCommodityCode= slableBatchCommodityCode;
	}
	/* 标注批次管理信息商品ID */
	private String slableBatchCommodityId;
	
	public String getSlableBatchCommodityId(){
		return slableBatchCommodityId;
	}
	
	public void setSlableBatchCommodityId(String slableBatchCommodityId){
		this.slableBatchCommodityId= slableBatchCommodityId;
	}
	/* 标注批次管理信息ID */
	private String slableBatchId;
	
	public String getSlableBatchId(){
		return slableBatchId;
	}
	
	public void setSlableBatchId(String slableBatchId){
		this.slableBatchId= slableBatchId;
	}
	/* 打包记录表编号 */
	private String spackingRecordCode;
	
	public String getSpackingRecordCode(){
		return spackingRecordCode;
	}
	
	public void setSpackingRecordCode(String spackingRecordCode){
		this.spackingRecordCode= spackingRecordCode;
	}
	/* 批次商品ID */
	private String spackingRecordId;
	
	public String getSpackingRecordId(){
		return spackingRecordId;
	}
	
	public void setSpackingRecordId(String spackingRecordId){
		this.spackingRecordId= spackingRecordId;
	}
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
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