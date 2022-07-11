package com.cloud.cang.model.tb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 第三方接口调用记录表(TB_INTERFACE_TRANSFER_LOG) **/
public class InterfaceTransferLog extends GenericEntity  {

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
	
	/* 接口类型
            10=请求接口
            20=返回接口 */
	private String iinterfaceType;
	
	public String getIinterfaceType(){
		return iinterfaceType;
	}
	
	public void setIinterfaceType(String iinterfaceType){
		this.iinterfaceType= iinterfaceType;
	}
	/* 用户类型 10普通用户，20管理员 */
	private Integer iuserType;
	
	public Integer getIuserType(){
		return iuserType;
	}
	
	public void setIuserType(Integer iuserType){
		this.iuserType= iuserType;
	}
	/* 记录编号 */
	private String scode;
	
	public String getScode(){
		return scode;
	}
	
	public void setScode(String scode){
		this.scode= scode;
	}
	/* 设备编号 */
	private String sdeviceCode;
	
	public String getSdeviceCode(){
		return sdeviceCode;
	}
	
	public void setSdeviceCode(String sdeviceCode){
		this.sdeviceCode= sdeviceCode;
	}
	/* 设备ID */
	private String sdeviceId;
	
	public String getSdeviceId(){
		return sdeviceId;
	}
	
	public void setSdeviceId(String sdeviceId){
		this.sdeviceId= sdeviceId;
	}
	/* 接口动作 */
	private String sinterfaceAction;
	
	public String getSinterfaceAction(){
		return sinterfaceAction;
	}
	
	public void setSinterfaceAction(String sinterfaceAction){
		this.sinterfaceAction= sinterfaceAction;
	}
	/* 接口名称 */
	private String sinterfaceName;
	
	public String getSinterfaceName(){
		return sinterfaceName;
	}
	
	public void setSinterfaceName(String sinterfaceName){
		this.sinterfaceName= sinterfaceName;
	}
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 请求参数 */
	private String srequestData;
	
	public String getSrequestData(){
		return srequestData;
	}
	
	public void setSrequestData(String srequestData){
		this.srequestData= srequestData;
	}
	/* 返回参数 */
	private String sresponseData;
	
	public String getSresponseData(){
		return sresponseData;
	}
	
	public void setSresponseData(String sresponseData){
		this.sresponseData= sresponseData;
	}
	/* 第三方编号 */
	private String sthirdCode;
	
	public String getSthirdCode(){
		return sthirdCode;
	}
	
	public void setSthirdCode(String sthirdCode){
		this.sthirdCode= sthirdCode;
	}
	/* 第三方名称 */
	private String sthirdName;
	
	public String getSthirdName(){
		return sthirdName;
	}
	
	public void setSthirdName(String sthirdName){
		this.sthirdName= sthirdName;
	}
	/* 用户ID */
	private String suserId;
	
	public String getSuserId(){
		return suserId;
	}
	
	public void setSuserId(String suserId){
		this.suserId= suserId;
	}
	/* 添加时间 */
	private Date taddTime;
	
	public Date getTaddTime(){
		return taddTime;
	}
	
	public void setTaddTime(Date taddTime){
		this.taddTime= taddTime;
	}
	/* 请求时间 */
	private Date trequestTime;
	
	public Date getTrequestTime(){
		return trequestTime;
	}
	
	public void setTrequestTime(Date trequestTime){
		this.trequestTime= trequestTime;
	}
	/* 响应时间 */
	private Date tresponseTime;
	
	public Date getTresponseTime(){
		return tresponseTime;
	}
	
	public void setTresponseTime(Date tresponseTime){
		this.tresponseTime= tresponseTime;
	}

		
}