package com.cloud.cang.model.hy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 会员角色表(HY_MBR_ROLE) **/
public class MbrRole extends GenericEntity  {

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
	private Date daddDate;
	
	public Date getDaddDate(){
		return daddDate;
	}
	
	public void setDaddDate(Date daddDate){
		this.daddDate= daddDate;
	}
	/* 修改日期 */
	private Date dmodifyDate;
	
	public Date getDmodifyDate(){
		return dmodifyDate;
	}
	
	public void setDmodifyDate(Date dmodifyDate){
		this.dmodifyDate= dmodifyDate;
	}
	/* 排序号 */
	private Integer isortNo;
	
	public Integer getIsortNo(){
		return isortNo;
	}
	
	public void setIsortNo(Integer isortNo){
		this.isortNo= isortNo;
	}
	/* 添加人 */
	private String saddOperator;
	
	public String getSaddOperator(){
		return saddOperator;
	}
	
	public void setSaddOperator(String saddOperator){
		this.saddOperator= saddOperator;
	}
	/* 修改人 */
	private String smodifyOperator;
	
	public String getSmodifyOperator(){
		return smodifyOperator;
	}
	
	public void setSmodifyOperator(String smodifyOperator){
		this.smodifyOperator= smodifyOperator;
	}
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 角色名 */
	private String sroleName;
	
	public String getSroleName(){
		return sroleName;
	}
	
	public void setSroleName(String sroleName){
		this.sroleName= sroleName;
	}

		
}