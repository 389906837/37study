package com.cloud.cang.model.sys;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 运营快捷菜单配置(SYS_FAST_MENU) **/
public class FastMenu extends GenericEntity  {

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
	
	/* 是否删除 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 菜单图标 */
	private String smenuIcon;
	
	public String getSmenuIcon(){
		return smenuIcon;
	}
	
	public void setSmenuIcon(String smenuIcon){
		this.smenuIcon= smenuIcon;
	}
	/* 菜单名称
            数据字典配置  */
	private String smenuName;
	
	public String getSmenuName(){
		return smenuName;
	}
	
	public void setSmenuName(String smenuName){
		this.smenuName= smenuName;
	}
	/* 菜单路径 */
	private String smenuPath;
	
	public String getSmenuPath(){
		return smenuPath;
	}
	
	public void setSmenuPath(String smenuPath){
		this.smenuPath= smenuPath;
	}
	/* 操作员ID */
	private String soperatorId;
	
	public String getSoperatorId(){
		return soperatorId;
	}
	
	public void setSoperatorId(String soperatorId){
		this.soperatorId= soperatorId;
	}
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 添加时间 */
	private Date taddTime;
	
	public Date getTaddTime(){
		return taddTime;
	}
	
	public void setTaddTime(Date taddTime){
		this.taddTime= taddTime;
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