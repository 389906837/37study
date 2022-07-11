package com.cloud.cang.model.fr;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 人脸识别操作日志记录(FR_FACE_OPER_LOG) **/
public class FaceOperLog extends GenericEntity  {

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
	
	/* 操作结果 */
	private Integer ioperResult;
	
	public Integer getIoperResult(){
		return ioperResult;
	}
	
	public void setIoperResult(Integer ioperResult){
		this.ioperResult= ioperResult;
	}
	/* 操作来源
            10：设备
            20：支付宝
            30：微信 */
	private Integer isourceType;
	
	public Integer getIsourceType(){
		return isourceType;
	}
	
	public void setIsourceType(Integer isourceType){
		this.isourceType= isourceType;
	}
	/* AI设备编号 */
	private String saiCode;
	
	public String getSaiCode(){
		return saiCode;
	}
	
	public void setSaiCode(String saiCode){
		this.saiCode= saiCode;
	}
	/* 人脸图片地址 */
	private String sfaceImgUrl;
	
	public String getSfaceImgUrl(){
		return sfaceImgUrl;
	}
	
	public void setSfaceImgUrl(String sfaceImgUrl){
		this.sfaceImgUrl= sfaceImgUrl;
	}
	/* 操作会员编号 */
	private String smemberCode;
	
	public String getSmemberCode(){
		return smemberCode;
	}
	
	public void setSmemberCode(String smemberCode){
		this.smemberCode= smemberCode;
	}
	/* 操作会员ID */
	private String smemberId;
	
	public String getSmemberId(){
		return smemberId;
	}
	
	public void setSmemberId(String smemberId){
		this.smemberId= smemberId;
	}
	/* 操作会员用户名(手机号) */
	private String smemberName;
	
	public String getSmemberName(){
		return smemberName;
	}
	
	public void setSmemberName(String smemberName){
		this.smemberName= smemberName;
	}
	/* 操作描述 */
	private String soperDesc;
	
	public String getSoperDesc(){
		return soperDesc;
	}
	
	public void setSoperDesc(String soperDesc){
		this.soperDesc= soperDesc;
	}
	/* 操作IP */
	private String soperIp;
	
	public String getSoperIp(){
		return soperIp;
	}
	
	public void setSoperIp(String soperIp){
		this.soperIp= soperIp;
	}
	/* 添加日期 */
	private Date taddTime;
	
	public Date getTaddTime(){
		return taddTime;
	}
	
	public void setTaddTime(Date taddTime){
		this.taddTime= taddTime;
	}
	/* 操作时间 */
	private Date toperTime;
	
	public Date getToperTime(){
		return toperTime;
	}
	
	public void setToperTime(Date toperTime){
		this.toperTime= toperTime;
	}
	/* 操作类型
            10：注册
            20：登录
            30：解绑
            40：绑定
            50：删除 */
	private Integer type;
	
	public Integer getType(){
		return type;
	}
	
	public void setType(Integer type){
		this.type= type;
	}

		
}