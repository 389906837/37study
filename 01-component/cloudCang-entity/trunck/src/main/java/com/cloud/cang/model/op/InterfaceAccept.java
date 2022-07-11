package com.cloud.cang.model.op;

import com.cloud.cang.generic.GenericEntity;

import java.util.Date;
/** 平台接口业务受理信息表(OP_INTERFACE_ACCEPT) **/
public class InterfaceAccept extends GenericEntity  {

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
	
	/* 添加日期 */
	private Date addTime;
	
	public Date getAddTime(){
		return addTime;
	}
	
	public void setAddTime(Date addTime){
		this.addTime= addTime;
	}
	/* 请求实收收费次数 */
	private Integer iactualTollNum;
	
	public Integer getIactualTollNum(){
		return iactualTollNum;
	}
	
	public void setIactualTollNum(Integer iactualTollNum){
		this.iactualTollNum= iactualTollNum;
	}
	/* 已回调次数 */
	private Integer icallbackNum;
	
	public Integer getIcallbackNum(){
		return icallbackNum;
	}
	
	public void setIcallbackNum(Integer icallbackNum){
		this.icallbackNum= icallbackNum;
	}
	/* 是否删除0否1是 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 是否回调 */
	private Integer iisCallback;
	
	public Integer getIisCallback(){
		return iisCallback;
	}
	
	public void setIisCallback(Integer iisCallback){
		this.iisCallback= iisCallback;
	}
	/* 回调是否成功 */
	private Integer iisCallbackSuccess;
	
	public Integer getIisCallbackSuccess(){
		return iisCallbackSuccess;
	}
	
	public void setIisCallbackSuccess(Integer iisCallbackSuccess){
		this.iisCallbackSuccess= iisCallbackSuccess;
	}
	/* 业务是否处理 */
	private Integer iisDealwith;
	
	public Integer getIisDealwith(){
		return iisDealwith;
	}
	
	public void setIisDealwith(Integer iisDealwith){
		this.iisDealwith= iisDealwith;
	}
	/* 是否需要回调 */
	private Integer iisNeedCallback;
	
	public Integer getIisNeedCallback(){
		return iisNeedCallback;
	}
	
	public void setIisNeedCallback(Integer iisNeedCallback){
		this.iisNeedCallback= iisNeedCallback;
	}
	/* 请求是否成功 */
	private Integer iisRequestSuccess;
	
	public Integer getIisRequestSuccess(){
		return iisRequestSuccess;
	}
	
	public void setIisRequestSuccess(Integer iisRequestSuccess){
		this.iisRequestSuccess= iisRequestSuccess;
	}
	/* 请求收费次数 */
	private Integer itollNum;
	
	public Integer getItollNum(){
		return itollNum;
	}
	
	public void setItollNum(Integer itollNum){
		this.itollNum= itollNum;
	}
	/* 应用编号 */
	private String sappCode;
	
	public String getSappCode(){
		return sappCode;
	}
	
	public void setSappCode(String sappCode){
		this.sappCode= sappCode;
	}
	/* 应用ID */
	private String sappId;
	
	public String getSappId(){
		return sappId;
	}
	
	public void setSappId(String sappId){
		this.sappId= sappId;
	}
	/* 回调地址 */
	private String scallbackAddress;
	
	public String getScallbackAddress(){
		return scallbackAddress;
	}
	
	public void setScallbackAddress(String scallbackAddress){
		this.scallbackAddress= scallbackAddress;
	}
	/* 回调数据 */
	private String scallbackData;
	
	public String getScallbackData(){
		return scallbackData;
	}
	
	public void setScallbackData(String scallbackData){
		this.scallbackData= scallbackData;
	}
	/* 回调数据已加密 */
	private String scallbackEncryData;
	
	public String getScallbackEncryData(){
		return scallbackEncryData;
	}
	
	public void setScallbackEncryData(String scallbackEncryData){
		this.scallbackEncryData= scallbackEncryData;
	}
	/* 业务受理编号 */
	private String scode;
	
	public String getScode(){
		return scode;
	}
	
	public void setScode(String scode){
		this.scode= scode;
	}
	/* 接口编号 */
	private String sinterfaceCode;
	
	public String getSinterfaceCode(){
		return sinterfaceCode;
	}
	
	public void setSinterfaceCode(String sinterfaceCode){
		this.sinterfaceCode= sinterfaceCode;
	}
	/* 操作IP */
	private String soperIp;
	
	public String getSoperIp(){
		return soperIp;
	}
	
	public void setSoperIp(String soperIp){
		this.soperIp= soperIp;
	}
	/* 识别模型编号 */
	private String srecognitionModelCode;
	
	public String getSrecognitionModelCode(){
		return srecognitionModelCode;
	}
	
	public void setSrecognitionModelCode(String srecognitionModelCode){
		this.srecognitionModelCode= srecognitionModelCode;
	}
	/* 请求数据 */
	private String srequestData;
	
	public String getSrequestData(){
		return srequestData;
	}
	
	public void setSrequestData(String srequestData){
		this.srequestData= srequestData;
	}
	/* 请求时间 */
	private Date srequestTime;
	
	public Date getSrequestTime(){
		return srequestTime;
	}
	
	public void setSrequestTime(Date srequestTime){
		this.srequestTime= srequestTime;
	}
	/* 返回数据 */
	private String sresponseData;
	
	public String getSresponseData(){
		return sresponseData;
	}
	
	public void setSresponseData(String sresponseData){
		this.sresponseData= sresponseData;
	}
	/* 返回数据已加密 */
	private String sresponseEncryData;
	
	public String getSresponseEncryData(){
		return sresponseEncryData;
	}
	
	public void setSresponseEncryData(String sresponseEncryData){
		this.sresponseEncryData= sresponseEncryData;
	}
	/* 第三方业务编号 */
	private String stpSerialNumber;
	
	public String getStpSerialNumber(){
		return stpSerialNumber;
	}
	
	public void setStpSerialNumber(String stpSerialNumber){
		this.stpSerialNumber= stpSerialNumber;
	}
	/* 用户编号 */
	private String suserCode;
	
	public String getSuserCode(){
		return suserCode;
	}
	
	public void setSuserCode(String suserCode){
		this.suserCode= suserCode;
	}
	/* 用户ID */
	private String suserId;
	
	public String getSuserId(){
		return suserId;
	}
	
	public void setSuserId(String suserId){
		this.suserId= suserId;
	}
	/* 回调成功时间 */
	private Date tcallbackSuccessTime;
	
	public Date getTcallbackSuccessTime(){
		return tcallbackSuccessTime;
	}
	
	public void setTcallbackSuccessTime(Date tcallbackSuccessTime){
		this.tcallbackSuccessTime= tcallbackSuccessTime;
	}
	/* 请求完成时间 */
	private Date trequestFinishTime;
	
	public Date getTrequestFinishTime(){
		return trequestFinishTime;
	}
	
	public void setTrequestFinishTime(Date trequestFinishTime){
		this.trequestFinishTime= trequestFinishTime;
	}
	/* 修改日期 */
	private Date updateTime;
	
	public Date getUpdateTime(){
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime){
		this.updateTime= updateTime;
	}

		
}