package com.cloud.cang.model.sys;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 任务配置中心(MYSQL)(SYS_SCHEDULE_CONF) **/
public class ScheduleConf extends GenericEntity  {

	private static final long serialVersionUID = 1L;
	
	/*主键ID*/
	private String id;
	/*主键ID*/
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		 this.id= id;
	}
	
	/* 类路径 */
	private String className;
	
	public String getClassName(){
		return className;
	}
	
	public void setClassName(String className){
		this.className= className;
	}
	/* 是否并行 */
	private Integer concurrent;
	
	public Integer getConcurrent(){
		return concurrent;
	}
	
	public void setConcurrent(Integer concurrent){
		this.concurrent= concurrent;
	}
	/* 表达式 */
	private String cron;
	
	public String getCron(){
		return cron;
	}
	
	public void setCron(String cron){
		this.cron= cron;
	}
	/* 接口信息 */
	private String interfaceInfo;
	
	public String getInterfaceInfo(){
		return interfaceInfo;
	}
	
	public void setInterfaceInfo(String interfaceInfo){
		this.interfaceInfo= interfaceInfo;
	}
	/* 方法名 */
	private String methodName;
	
	public String getMethodName(){
		return methodName;
	}
	
	public void setMethodName(String methodName){
		this.methodName= methodName;
	}
	/* 任务代码 */
	private String moduleCode;
	
	public String getModuleCode(){
		return moduleCode;
	}
	
	public void setModuleCode(String moduleCode){
		this.moduleCode= moduleCode;
	}
	/* 任务名 */
	private String moduleName;
	
	public String getModuleName(){
		return moduleName;
	}
	
	public void setModuleName(String moduleName){
		this.moduleName= moduleName;
	}
	/* 备注 */
	private String remark;
	
	public String getRemark(){
		return remark;
	}
	
	public void setRemark(String remark){
		this.remark= remark;
	}
	/* 服务ID(SPRINGID) */
	private String springId;
	
	public String getSpringId(){
		return springId;
	}
	
	public void setSpringId(String springId){
		this.springId= springId;
	}
	/* 状态
            0:禁用
            1:启用
            3:删除 */
	private Integer status;
	
	public Integer getStatus(){
		return status;
	}
	
	public void setStatus(Integer status){
		this.status= status;
	}
	/* 系统代码 */
	private Integer systemCode;
	
	public Integer getSystemCode(){
		return systemCode;
	}
	
	public void setSystemCode(Integer systemCode){
		this.systemCode= systemCode;
	}
	/* 任务名 */
	private String systemName;
	
	public String getSystemName(){
		return systemName;
	}
	
	public void setSystemName(String systemName){
		this.systemName= systemName;
	}

		
}