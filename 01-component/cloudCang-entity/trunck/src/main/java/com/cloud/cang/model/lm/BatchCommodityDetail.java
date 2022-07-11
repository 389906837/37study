package com.cloud.cang.model.lm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 标注批次管理信息商品明细表(LM_BATCH_COMMODITY_DETAIL) **/
public class BatchCommodityDetail extends GenericEntity  {

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
	
	/* 文件位置 */
	private String fileLocation;
	
	public String getFileLocation(){
		return fileLocation;
	}
	
	public void setFileLocation(String fileLocation){
		this.fileLocation= fileLocation;
	}
	/* 是否标记0否1是 */
	private Integer iisMark;
	
	public Integer getIisMark(){
		return iisMark;
	}
	
	public void setIisMark(Integer iisMark){
		this.iisMark= iisMark;
	}
	/* 添加人 */
	private String saddUser;
	
	public String getSaddUser(){
		return saddUser;
	}
	
	public void setSaddUser(String saddUser){
		this.saddUser= saddUser;
	}
	/* 批次商品编号 */
	private String sbatchCommodityCode;
	
	public String getSbatchCommodityCode(){
		return sbatchCommodityCode;
	}
	
	public void setSbatchCommodityCode(String sbatchCommodityCode){
		this.sbatchCommodityCode= sbatchCommodityCode;
	}
	/* 批次商品ID */
	private String sbatchCommodityId;
	
	public String getSbatchCommodityId(){
		return sbatchCommodityId;
	}
	
	public void setSbatchCommodityId(String sbatchCommodityId){
		this.sbatchCommodityId= sbatchCommodityId;
	}
	/* 文件名 */
	private String sfileName;
	
	public String getSfileName(){
		return sfileName;
	}
	
	public void setSfileName(String sfileName){
		this.sfileName= sfileName;
	}
	/* 图片深度 */
	private String spicDepth;
	
	public String getSpicDepth(){
		return spicDepth;
	}
	
	public void setSpicDepth(String spicDepth){
		this.spicDepth= spicDepth;
	}
	/* 图片长度 */
	private String spicHeight;
	
	public String getSpicHeight(){
		return spicHeight;
	}
	
	public void setSpicHeight(String spicHeight){
		this.spicHeight= spicHeight;
	}
	/* 图片分段 */
	private String spicSegmented;
	
	public String getSpicSegmented(){
		return spicSegmented;
	}
	
	public void setSpicSegmented(String spicSegmented){
		this.spicSegmented= spicSegmented;
	}
	/* 图片源数据库 */
	private String spicSourceDatabase;
	
	public String getSpicSourceDatabase(){
		return spicSourceDatabase;
	}
	
	public void setSpicSourceDatabase(String spicSourceDatabase){
		this.spicSourceDatabase= spicSourceDatabase;
	}
	/* 图片宽度 */
	private String spicWidth;
	
	public String getSpicWidth(){
		return spicWidth;
	}
	
	public void setSpicWidth(String spicWidth){
		this.spicWidth= spicWidth;
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
	/* 最后一次标注时间 */
	private Date tlastHoleMarkTime;
	
	public Date getTlastHoleMarkTime(){
		return tlastHoleMarkTime;
	}
	
	public void setTlastHoleMarkTime(Date tlastHoleMarkTime){
		this.tlastHoleMarkTime= tlastHoleMarkTime;
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