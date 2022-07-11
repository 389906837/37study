package com.cloud.cang.model.op;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 接口调用日志表(OP_TRANSFER_LOG) **/
public class TransferLog extends GenericEntity  {

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
	
	/* 是否删除0否1是 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 操作类型
            数据字典配置
            10=image_recognition
            20=image_recognition_async
            30=recognition_query
            40=interface_balance_query */
	private String ioperType;
	
	public String getIoperType(){
		return ioperType;
	}
	
	public void setIoperType(String ioperType){
		this.ioperType= ioperType;
	}
	/* 日志类型
            10=操作日志
            20=错误日志
            30=警告日志 */
	private Integer itype;
	
	public Integer getItype(){
		return itype;
	}
	
	public void setItype(Integer itype){
		this.itype= itype;
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
	/* 业务编号 */
	private String sbusinessCode;
	
	public String getSbusinessCode(){
		return sbusinessCode;
	}
	
	public void setSbusinessCode(String sbusinessCode){
		this.sbusinessCode= sbusinessCode;
	}
	/* 业务受理编号 */
	private String scode;
	
	public String getScode(){
		return scode;
	}
	
	public void setScode(String scode){
		this.scode= scode;
	}
	/* 操作内容 */
	private String scontent;
	
	public String getScontent(){
		return scontent;
	}
	
	public void setScontent(String scontent){
		this.scontent= scontent;
	}
	/* 接口编号 */
	private String sinterfaceCode;
	
	public String getSinterfaceCode(){
		return sinterfaceCode;
	}
	
	public void setSinterfaceCode(String sinterfaceCode){
		this.sinterfaceCode= sinterfaceCode;
	}
	/* 调用IP */
	private String soperIp;
	
	public String getSoperIp(){
		return soperIp;
	}
	
	public void setSoperIp(String soperIp){
		this.soperIp= soperIp;
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
	/* 操作时间 */
	private Date toperTime;
	
	public Date getToperTime(){
		return toperTime;
	}
	
	public void setToperTime(Date toperTime){
		this.toperTime= toperTime;
	}

		
}