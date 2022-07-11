package com.cloud.cang.model.lm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 标注批次管理信息表商品表(LM_LABEL_BATCH_COMMODITY) **/
public class LabelBatchCommodity extends GenericEntity  {

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
	/* 是否删除0否1是 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 是否单一商品 */
	private Integer iisSingleCommodity;
	
	public Integer getIisSingleCommodity(){
		return iisSingleCommodity;
	}
	
	public void setIisSingleCommodity(Integer iisSingleCommodity){
		this.iisSingleCommodity= iisSingleCommodity;
	}
	/* 标记状态
            1  已完全标记
            2  部分标记
            3  未标记 */
	private Integer imarkStatus;
	
	public Integer getImarkStatus(){
		return imarkStatus;
	}
	
	public void setImarkStatus(Integer imarkStatus){
		this.imarkStatus= imarkStatus;
	}
	/* 添加人 */
	private String saddUser;
	
	public String getSaddUser(){
		return saddUser;
	}
	
	public void setSaddUser(String saddUser){
		this.saddUser= saddUser;
	}
	/* 批次编号 */
	private String sbatchCode;
	
	public String getSbatchCode(){
		return sbatchCode;
	}
	
	public void setSbatchCode(String sbatchCode){
		this.sbatchCode= sbatchCode;
	}
	/* 批次ID */
	private String sbatchId;
	
	public String getSbatchId(){
		return sbatchId;
	}
	
	public void setSbatchId(String sbatchId){
		this.sbatchId= sbatchId;
	}
	/* 编号 */
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
	/* 商品名 */
	private String scommodityName;
	
	public String getScommodityName(){
		return scommodityName;
	}
	
	public void setScommodityName(String scommodityName){
		this.scommodityName= scommodityName;
	}
	/* 文件名 */
	private String sfileName;
	
	public String getSfileName(){
		return sfileName;
	}
	
	public void setSfileName(String sfileName){
		this.sfileName= sfileName;
	}
	/* 图片数 */
	private Integer spicNum;
	
	public Integer getSpicNum(){
		return spicNum;
	}
	
	public void setSpicNum(Integer spicNum){
		this.spicNum= spicNum;
	}
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 修改人 */
	private String supdateUser;
	
	public String getSupdateUser(){
		return supdateUser;
	}
	
	public void setSupdateUser(String supdateUser){
		this.supdateUser= supdateUser;
	}
	/* 商品视觉编号 */
	private String svrCode;
	
	public String getSvrCode(){
		return svrCode;
	}
	
	public void setSvrCode(String svrCode){
		this.svrCode= svrCode;
	}
	/* 视觉标识 */
	private String svrIdentification;
	
	public String getSvrIdentification(){
		return svrIdentification;
	}
	
	public void setSvrIdentification(String svrIdentification){
		this.svrIdentification= svrIdentification;
	}
	/* 添加日期 */
	private Date taddTime;
	
	public Date getTaddTime(){
		return taddTime;
	}
	
	public void setTaddTime(Date taddTime){
		this.taddTime= taddTime;
	}
	/* 标记完成时间 */
	private Date tmarkCompletionTime;
	
	public Date getTmarkCompletionTime(){
		return tmarkCompletionTime;
	}
	
	public void setTmarkCompletionTime(Date tmarkCompletionTime){
		this.tmarkCompletionTime= tmarkCompletionTime;
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