package com.cloud.cang.model.sys;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 数据字典明细(SYS_PARAMETER_GROUP_DETAIL) **/
public class ParameterGroupDetail extends GenericEntity  {

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
	
	/* 排序号 */
	private Integer isort;
	
	public Integer getIsort(){
		return isort;
	}
	
	public void setIsort(Integer isort){
		this.isort= isort;
	}
	/* 主表主键 */
	private String sgroupid;
	
	public String getSgroupid(){
		return sgroupid;
	}
	
	public void setSgroupid(String sgroupid){
		this.sgroupid= sgroupid;
	}
	/* 参数名称 */
	private String sname;
	
	public String getSname(){
		return sname;
	}
	
	public void setSname(String sname){
		this.sname= sname;
	}
	/* 参数说明 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 参数值 */
	private String svalue;
	
	public String getSvalue(){
		return svalue;
	}
	
	public void setSvalue(String svalue){
		this.svalue= svalue;
	}

		
}