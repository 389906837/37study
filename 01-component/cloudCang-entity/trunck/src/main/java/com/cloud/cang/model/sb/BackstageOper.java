package com.cloud.cang.model.sb;

import com.cloud.cang.generic.GenericEntity;

import java.util.Date;
/** 设备后台操作记录表(SB_BACKSTAGE_OPER) **/
public class BackstageOper extends GenericEntity  {

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
	/* 操作对象类型
            10=全部
            20=部分 */
	private Integer ideviceType;
	
	public Integer getIdeviceType(){
		return ideviceType;
	}
	
	public void setIdeviceType(Integer ideviceType){
		this.ideviceType= ideviceType;
	}
	/* 操作类型
            10=调节音量
            20=重启设备
            30=关机
            40=定时开关机
            50=主动盘货 */
	private Integer itype;
	
	public Integer getItype(){
		return itype;
	}
	
	public void setItype(Integer itype){
		this.itype= itype;
	}
	/* 添加人 */
	private String saddUser;
	
	public String getSaddUser(){
		return saddUser;
	}
	
	public void setSaddUser(String saddUser){
		this.saddUser= saddUser;
	}
	/* 操作内容 json */
	private String soperContent;
	
	public String getSoperContent(){
		return soperContent;
	}
	
	public void setSoperContent(String soperContent){
		this.soperContent= soperContent;
	}
	/* 操作对象 json */
	private String soperObject;
	
	public String getSoperObject(){
		return soperObject;
	}
	
	public void setSoperObject(String soperObject){
		this.soperObject= soperObject;
	}
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 操作日期 */
	private Date taddTime;
	
	public Date getTaddTime(){
		return taddTime;
	}
	
	public void setTaddTime(Date taddTime){
		this.taddTime= taddTime;
	}

		
}