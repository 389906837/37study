package com.cloud.cang.model.cr;

import com.cloud.cang.generic.GenericEntity;

import java.util.Date;
/** 识别服务器管理(CR_RECOGNITION_SERVER) **/
public class RecognitionServer extends GenericEntity  {

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
	/* 审核人姓名 */
	private String sauditOperName;
	
	public String getSauditOperName(){
		return sauditOperName;
	}
	
	public void setSauditOperName(String sauditOperName){
		this.sauditOperName= sauditOperName;
	}
	/* 审核原因 */
	private String sauditReason;
	
	public String getSauditReason(){
		return sauditReason;
	}
	
	public void setSauditReason(String sauditReason){
		this.sauditReason= sauditReason;
	}
	/* 服务器编号 */
	private String scode;
	
	public String getScode(){
		return scode;
	}
	
	public void setScode(String scode){
		this.scode= scode;
	}
	/* 服务器IP */
	private String sip;
	
	public String getSip(){
		return sip;
	}
	
	public void setSip(String sip){
		this.sip= sip;
	}
	/* 模型编号 */
	private String smodelCode;
	
	public String getSmodelCode(){
		return smodelCode;
	}
	
	public void setSmodelCode(String smodelCode){
		this.smodelCode= smodelCode;
	}
	/* 模型ID */
	private String smodelId;
	
	public String getSmodelId(){
		return smodelId;
	}
	
	public void setSmodelId(String smodelId){
		this.smodelId= smodelId;
	}
	/* 服务器名称 */
	private String sname;
	
	public String getSname(){
		return sname;
	}
	
	public void setSname(String sname){
		this.sname= sname;
	}
	/* 服务器端口 */
	private String sport;
	
	public String getSport(){
		return sport;
	}
	
	public void setSport(String sport){
		this.sport= sport;
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
	/* 添加人 */
	private String taddUser;
	
	public String getTaddUser(){
		return taddUser;
	}
	
	public void setTaddUser(String taddUser){
		this.taddUser= taddUser;
	}
	/* 审核时间 */
	private Date tauditTime;
	
	public Date getTauditTime(){
		return tauditTime;
	}
	
	public void setTauditTime(Date tauditTime){
		this.tauditTime= tauditTime;
	}
	/* 修改人 */
	private String tupateUser;
	
	public String getTupateUser(){
		return tupateUser;
	}
	
	public void setTupateUser(String tupateUser){
		this.tupateUser= tupateUser;
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