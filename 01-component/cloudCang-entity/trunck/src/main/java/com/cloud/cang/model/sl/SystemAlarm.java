package com.cloud.cang.model.sl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 系统警报日志(SL_SYSTEM_ALARM) **/
public class SystemAlarm extends GenericEntity  {

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
	
	/* 是否处理 0否 1是 */
	private Integer iisDealwith;
	
	public Integer getIisDealwith(){
		return iisDealwith;
	}
	
	public void setIisDealwith(Integer iisDealwith){
		this.iisDealwith= iisDealwith;
	}
	/* 警报问题类型
            数据字典配置
            10=购物会员库存上架 */
	private Integer iproblemType;
	
	public Integer getIproblemType(){
		return iproblemType;
	}
	
	public void setIproblemType(Integer iproblemType){
		this.iproblemType= iproblemType;
	}
	/* 来源系统
            数据字典配置 */
	private String isystemType;
	
	public String getIsystemType(){
		return isystemType;
	}
	
	public void setIsystemType(String isystemType){
		this.isystemType= isystemType;
	}
	/* 操作类型：
            10=购物会员
            20=补货会员
            30=系统用户 */
	private Integer itype;
	
	public Integer getItype(){
		return itype;
	}
	
	public void setItype(Integer itype){
		this.itype= itype;
	}
	/* 备注 */
	private String sdealwithMan;
	
	public String getSdealwithMan(){
		return sdealwithMan;
	}
	
	public void setSdealwithMan(String sdealwithMan){
		this.sdealwithMan= sdealwithMan;
	}
	/* 会员编号 */
	private String smemberCode;
	
	public String getSmemberCode(){
		return smemberCode;
	}
	
	public void setSmemberCode(String smemberCode){
		this.smemberCode= smemberCode;
	}
	/* 用户ID */
	private String smemberId;
	
	public String getSmemberId(){
		return smemberId;
	}
	
	public void setSmemberId(String smemberId){
		this.smemberId= smemberId;
	}
	/* 会员用户名（手机号） */
	private String smemberName;
	
	public String getSmemberName(){
		return smemberName;
	}
	
	public void setSmemberName(String smemberName){
		this.smemberName= smemberName;
	}
	/* 访问IP */
	private String soperIp;
	
	public String getSoperIp(){
		return soperIp;
	}
	
	public void setSoperIp(String soperIp){
		this.soperIp= soperIp;
	}
	/* 警报问题描叙 */
	private String sproblemDesc;
	
	public String getSproblemDesc(){
		return sproblemDesc;
	}
	
	public void setSproblemDesc(String sproblemDesc){
		this.sproblemDesc= sproblemDesc;
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
	/* 报警时间 */
	private Date talarmTime;
	
	public Date getTalarmTime(){
		return talarmTime;
	}
	
	public void setTalarmTime(Date talarmTime){
		this.talarmTime= talarmTime;
	}
	/* 处理时间 */
	private Date tdealwithTime;
	
	public Date getTdealwithTime(){
		return tdealwithTime;
	}
	
	public void setTdealwithTime(Date tdealwithTime){
		this.tdealwithTime= tdealwithTime;
	}
	/* 修改时间 */
	private Date tupdateTime;
	
	public Date getTupdateTime(){
		return tupdateTime;
	}
	
	public void setTupdateTime(Date tupdateTime){
		this.tupdateTime= tupdateTime;
	}

		
}