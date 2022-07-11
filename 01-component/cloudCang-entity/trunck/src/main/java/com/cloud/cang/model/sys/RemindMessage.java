package com.cloud.cang.model.sys;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 温馨提醒配置(SYS_REMIND_MESSAGE) **/
public class RemindMessage extends GenericEntity  {

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
	
	/* 类型：1 web  2 wap 3 app 4 移动端 */
	private Integer itype;
	
	public Integer getItype(){
		return itype;
	}
	
	public void setItype(Integer itype){
		this.itype= itype;
	}
	/* 备注 */
	private String remark;
	
	public String getRemark(){
		return remark;
	}
	
	public void setRemark(String remark){
		this.remark= remark;
	}
	/* 编号 */
	private String scode;
	
	public String getScode(){
		return scode;
	}
	
	public void setScode(String scode){
		this.scode= scode;
	}
	/* 名称 */
	private String sname;
	
	public String getSname(){
		return sname;
	}
	
	public void setSname(String sname){
		this.sname= sname;
	}
	/* 排序号 */
	private Integer sort;
	
	public Integer getSort(){
		return sort;
	}
	
	public void setSort(Integer sort){
		this.sort= sort;
	}
	/* 消息值 */
	private String svalue;
	
	public String getSvalue(){
		return svalue;
	}
	
	public void setSvalue(String svalue){
		this.svalue= svalue;
	}

		
}