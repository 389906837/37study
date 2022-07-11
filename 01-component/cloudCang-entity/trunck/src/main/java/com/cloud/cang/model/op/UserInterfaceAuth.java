package com.cloud.cang.model.op;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 用户接口权限表(OP_USER_INTERFACE_AUTH) **/
public class UserInterfaceAuth extends GenericEntity  {

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
	/* 接口权限状态
            10=已开通
            20=已关闭 */
	private Integer iauthStatus;
	
	public Integer getIauthStatus(){
		return iauthStatus;
	}
	
	public void setIauthStatus(Integer iauthStatus){
		this.iauthStatus= iauthStatus;
	}
	/* 是否删除0否1是 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 最近一次关闭时间 */
	private Date ilastCloseTime;
	
	public Date getIlastCloseTime(){
		return ilastCloseTime;
	}
	
	public void setIlastCloseTime(Date ilastCloseTime){
		this.ilastCloseTime= ilastCloseTime;
	}
	/* 开通时间 */
	private Date iopenTime;
	
	public Date getIopenTime(){
		return iopenTime;
	}
	
	public void setIopenTime(Date iopenTime){
		this.iopenTime= iopenTime;
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
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
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
	/* 修改日期 */
	private Date updateTime;
	
	public Date getUpdateTime(){
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime){
		this.updateTime= updateTime;
	}

		
}