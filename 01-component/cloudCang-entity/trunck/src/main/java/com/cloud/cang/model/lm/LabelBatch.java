package com.cloud.cang.model.lm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 标注批次管理信息表(LM_LABEL_BATCH) **/
public class LabelBatch extends GenericEntity  {

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
	/* 审核状态
            10=待审核
            20=审核通过
            30=审核驳回 */
	private Integer iauditStatus;
	
	public Integer getIauditStatus(){
		return iauditStatus;
	}
	
	public void setIauditStatus(Integer iauditStatus){
		this.iauditStatus= iauditStatus;
	}
	/* 是否删除0否1是 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 文件商品类型
            1 单一商品
            2 多种商品
            3 单一和多种商品 */
	private Integer ifileCommodityType;
	
	public Integer getIfileCommodityType(){
		return ifileCommodityType;
	}
	
	public void setIfileCommodityType(Integer ifileCommodityType){
		this.ifileCommodityType= ifileCommodityType;
	}
	/* 文件类型
            1 zip包 */
	private Integer ifileType;
	
	public Integer getIfileType(){
		return ifileType;
	}
	
	public void setIfileType(Integer ifileType){
		this.ifileType= ifileType;
	}
	/* 添加人 */
	private String saddUser;
	
	public String getSaddUser(){
		return saddUser;
	}
	
	public void setSaddUser(String saddUser){
		this.saddUser= saddUser;
	}
	/* 审核人ID */
	private String sauditOperId;
	
	public String getSauditOperId(){
		return sauditOperId;
	}
	
	public void setSauditOperId(String sauditOperId){
		this.sauditOperId= sauditOperId;
	}
	/* 审核人姓名 */
	private String sauditOperName;
	
	public String getSauditOperName(){
		return sauditOperName;
	}
	
	public void setSauditOperName(String sauditOperName){
		this.sauditOperName= sauditOperName;
	}
	/* 审核驳回原因 */
	private String sauditRefuseReason;
	
	public String getSauditRefuseReason(){
		return sauditRefuseReason;
	}
	
	public void setSauditRefuseReason(String sauditRefuseReason){
		this.sauditRefuseReason= sauditRefuseReason;
	}
	/* 批次编号 */
	private String sbatchCode;
	
	public String getSbatchCode(){
		return sbatchCode;
	}
	
	public void setSbatchCode(String sbatchCode){
		this.sbatchCode= sbatchCode;
	}
	/* 批次名称 */
	private String sbatchName;
	
	public String getSbatchName(){
		return sbatchName;
	}
	
	public void setSbatchName(String sbatchName){
		this.sbatchName= sbatchName;
	}
	/* 文件名 */
	private String sfileName;
	
	public String getSfileName(){
		return sfileName;
	}
	
	public void setSfileName(String sfileName){
		this.sfileName= sfileName;
	}
	/* 文件大小 */
	private String sfileSize;
	
	public String getSfileSize(){
		return sfileSize;
	}
	
	public void setSfileSize(String sfileSize){
		this.sfileSize= sfileSize;
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
	/* 商户名 */
	private String smerchantName;
	
	public String getSmerchantName(){
		return smerchantName;
	}
	
	public void setSmerchantName(String smerchantName){
		this.smerchantName= smerchantName;
	}
	/* 标注人姓名 */
	private String soperatorName;
	
	public String getSoperatorName(){
		return soperatorName;
	}
	
	public void setSoperatorName(String soperatorName){
		this.soperatorName= soperatorName;
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
	/* 训练服务器地址 */
	private String strainServerAddress;
	
	public String getStrainServerAddress(){
		return strainServerAddress;
	}
	
	public void setStrainServerAddress(String strainServerAddress){
		this.strainServerAddress= strainServerAddress;
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
	/* 审核时间 */
	private Date tauditTime;
	
	public Date getTauditTime(){
		return tauditTime;
	}
	
	public void setTauditTime(Date tauditTime){
		this.tauditTime= tauditTime;
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